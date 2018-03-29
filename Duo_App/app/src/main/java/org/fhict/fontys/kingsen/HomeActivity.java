package org.fhict.fontys.kingsen;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import org.fhict.fontys.kingsen.Objects.DatabaseReference;
import org.fhict.fontys.kingsen.Objects.Group;
import org.fhict.fontys.kingsen.Objects.SimpleDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    final Context context = this;
    private List<EditText> inputmembers = new ArrayList<>();
    private Integer membercount = 0;
    private ListView lsview;

    //arraylists
    private List<Group> allgroups = new ArrayList<>();
    List<String> allgroupnames = new ArrayList<>();
    private ArrayAdapter<String>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,allgroupnames);
        lsview = findViewById(R.id.lsview);

        lsview.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                membercount = 1;

                LayoutInflater li = LayoutInflater.from(context);
                final View promptsView = li.inflate(R.layout.creategroup_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set creategroup_promptroup_prompt.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText groupname = promptsView
                        .findViewById(R.id.tbgroupname);

                final EditText member1 = promptsView.findViewById(R.id.tbmember1);
                inputmembers.add(member1);

                final ImageButton addtextbox = promptsView.findViewById(R.id.btnaddtext);
                addtextbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        membercount++;
                        EditText toadd = new EditText(promptsView.getContext());
                        toadd.setHint("Member " + membercount);

                        inputmembers.add(toadd);

                      LinearLayout l =  promptsView.findViewById(R.id.layout_root);
                      l.addView(toadd,l.indexOfChild(addtextbox));
                    }
                });

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        List<String> users = new ArrayList<>();
                                        for (EditText t : inputmembers)
                                        {
                                            users.add(t.getText().toString());
                                        }

                                        System.out.println(users);
                                        Group toadd = new Group(groupname.getText().toString(),users);
                                        String username = AuthenticationReference.getAuth().getCurrentUser().getEmail().replace(".",",");
                                        DatabaseReference.getDatabase().child("users").child(username).child(toadd.getName()).setValue(toadd.getUsers());
                                        inputmembers.clear();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
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
        String username = AuthenticationReference.getAuth().getCurrentUser().getEmail().replace(".",",");
        DatabaseReference.getDatabase().child("users").child(username).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    allgroups.add(new Group(dataSnapshot.getKey(),(List<String>) dataSnapshot.getValue()));
                    allgroupnames.add(dataSnapshot.getKey());

                    System.out.println(adapter.getCount());
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                allgroups.add(new Group(dataSnapshot.getKey(),(List<String>) dataSnapshot.getValue()));
                allgroupnames.add(dataSnapshot.getKey());

                System.out.println(adapter.getCount());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (Group g : allgroups)
                {
                    if (dataSnapshot.getKey() == g.getName())
                    {
                        allgroups.remove(g);
                        break;
                    }
                }

                for (String groupname : allgroupnames)
                {
                    if (groupname == dataSnapshot.getKey())
                    {
                        allgroupnames.remove(groupname);
                    }
                }
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
        //@todo add next screen
        String selectedFromList = (lsview.getItemAtPosition(i).toString());
        System.out.println(selectedFromList);
    }
}
