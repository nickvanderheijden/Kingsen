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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import org.fhict.fontys.kingsen.Objects.Group;
import org.fhict.fontys.kingsen.Objects.HelperFireBase;

public class HomeActivity extends AppCompatActivity {

    final Context context = this;
    DatabaseReference db;
    HelperFireBase help;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = FirebaseDatabase.getInstance().getReference();
        help = new HelperFireBase(db);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.creategroup_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set creategroup_promptroup_prompt.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        String groupname = userInput.getText().toString();
                                        Group g = new Group(groupname);
                                        g.setName(groupname);

                                        if(groupname != null && groupname.length()>0){
                                            if(help.save(g)){
                                            userInput.setText("");

                                            }
                                        }else{
                                            Toast.makeText(HomeActivity.this,"GroupName may not be Empty",Toast.LENGTH_SHORT).show();
                                        }

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
    }


}
