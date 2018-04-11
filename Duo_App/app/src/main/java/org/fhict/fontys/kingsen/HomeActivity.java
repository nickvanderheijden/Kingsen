package org.fhict.fontys.kingsen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.fhict.fontys.kingsen.Objects.AuthenticationReference;
import org.fhict.fontys.kingsen.Objects.Card;
import org.fhict.fontys.kingsen.Objects.DatabaseReference;
import org.fhict.fontys.kingsen.Objects.Group;
import org.fhict.fontys.kingsen.Objects.SimpleDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    final Context context = this;
    private List<EditText> inputmembers = new ArrayList<>();
    private Integer membercount = 0;

    //Controls
    private ListView lsview;

    //arraylists
    private List<Group> allgroups = new ArrayList<>();
    List<String> allgroupnames = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //bound adapter to listview
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allgroupnames);
        lsview = findViewById(R.id.lsview);
        lsview.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                membercount = 1;

                LayoutInflater li = LayoutInflater.from(context);
                final View promptsView = li.inflate(R.layout.creategroup_prompt, null);

                //create dialogbuilder
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set creategroup_promptroup_prompt.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                //declare controls in custom dialog
                final EditText groupname = promptsView
                        .findViewById(R.id.tbgroupname);

                final EditText member1 = promptsView.findViewById(R.id.tbmember1);
                inputmembers.add(member1);

                final CheckBox cbcustomrules = promptsView.findViewById(R.id.cbcustomrules);

                //build automatic textbox add-er
                final ImageButton addtextbox = promptsView.findViewById(R.id.btnaddtext);
                addtextbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //if there aren't 7 textboxes
                        if (membercount < 7) {
                            //determain to add the textbox and put it in the list and
                            membercount++;
                            EditText toadd = new EditText(promptsView.getContext());
                            toadd.setHint("Member " + membercount);

                            inputmembers.add(toadd);

                            LinearLayout l = promptsView.findViewById(R.id.layout_root);
                            l.addView(toadd, l.indexOfChild(addtextbox));
                        } else {
                            //add eight textbox and remove addtextbox button
                            membercount++;
                            LinearLayout l = promptsView.findViewById(R.id.layout_root);

                            EditText toadd = new EditText(promptsView.getContext());
                            toadd.setHint("Member " + membercount);

                            inputmembers.add(toadd);
                            l.addView(toadd, l.indexOfChild(addtextbox));

                            l.removeView(addtextbox);
                        }
                    }
                });

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //check if all fields have been filled in
                                        if (TextUtils.isEmpty(groupname.getText().toString()) || TextUtils.isEmpty(member1.getText())) {
                                            //no, give toast
                                        Toast t = Toast.makeText(context,"Please fill in a groupname and atleast one member",Toast.LENGTH_SHORT);
                                        t.show();

                                        } else {
                                            //yes, get all users (via textbox list) and groupname, make the group and save it in firebase under the e-mail
                                            List<String> users = new ArrayList<>();
                                            for (EditText t : inputmembers) {
                                                users.add(t.getText().toString());
                                            }

                                            Group toadd = new Group(groupname.getText().toString(), users);
                                            String username = AuthenticationReference.getAuth().getCurrentUser().getEmail().replace(".", ",");
                                            DatabaseReference.getDatabase().child("users").child(username).child(toadd.getName()).child("members").setValue(toadd.getUsers());
                                            inputmembers.clear();

                                            //use rule method
                                            setRules(cbcustomrules.isChecked(), username, toadd.getName(), dialog);
                                        }

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });


        //get all made games from firebase
        String username = AuthenticationReference.getAuth().getCurrentUser().getEmail().replace(".", ",");
        DatabaseReference.getDatabase().child("users").child(username).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                allgroups.add(new Group(dataSnapshot.getKey(), (List<String>) dataSnapshot.child("members").getValue()));
                allgroupnames.add(dataSnapshot.getKey());

                System.out.println(adapter.getCount());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //need to check if rules are not changed to prevent duplicates
                if (dataSnapshot.child("rules").child(Card.Number.ace.toString()).getValue() == null) {
                    allgroups.add(new Group(dataSnapshot.getKey(), (List<String>) dataSnapshot.child("members").getValue()));
                    allgroupnames.add(dataSnapshot.getKey());

                    System.out.println(adapter.getCount());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (Group g : allgroups) {
                    if (dataSnapshot.getKey().equals(g.getName())) {
                        allgroups.remove(g);
                        break;
                    }
                }

                for (String groupname : allgroupnames) {
                    if (dataSnapshot.getKey().equals(groupname)) {
                        allgroupnames.remove(groupname);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lsview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //if group is pressed, get groupname string
        String selectedFromList = (lsview.getItemAtPosition(i).toString());

        //go to start screen and pass group (serializable) through
        for (Group g : allgroups) {
            if (g.getName() == selectedFromList) {
                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                intent.putExtra("GROUP", g);
                startActivity(intent);
                break;
            }
        }
    }

    private void setRules(boolean iscustom, String username, String groupname, DialogInterface currenttialog) {

        //custom rules is not checked -> putt standerd rules in database
        if (iscustom == false) {
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.ace.toString()).setValue("Andere kant uit");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.two.toString()).setValue("Twee adjes");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.three.toString()).setValue("Adje links");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.four.toString()).setValue("Adje rechts");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.five.toString()).setValue("Duim op tafel leggen");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.six.toString()).setValue("Eén minuut dom lullen");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.seven.toString()).setValue("Juffen");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.eight.toString()).setValue("Regel");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.nine.toString()).setValue("Rijmen");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.ten.toString()).setValue("Atje vraag");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.jack.toString()).setValue("Regel");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.queen.toString()).setValue("Categorie");
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(Card.Number.king.toString()).setValue("Special At");
        } else {
            //go to custom rules screen (pass groupname and username through)
            Intent nextscreen = new Intent(getBaseContext(), CustomRulesActivity.class);
            nextscreen.putExtra("GROUPNAME", groupname);
            nextscreen.putExtra("USERNAME", username);
            currenttialog.dismiss();
            startActivity(nextscreen);
        }
    }
}
