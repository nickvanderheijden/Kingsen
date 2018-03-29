package org.fhict.fontys.kingsen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.fhict.fontys.kingsen.Objects.Group;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Group currentgroup = (Group) getIntent().getSerializableExtra("GROUP");

        System.out.println(currentgroup.getName());
        System.out.println(currentgroup.getUsers().toString());

        TextView tvgroupname = findViewById(R.id.tvgroupname);
        tvgroupname.setText(currentgroup.getName());

        TextView tvmembers = findViewById(R.id.tvmembers);

        int membercount = 0;
        for (String member : currentgroup.getUsers())
        {
            membercount++;
            tvmembers.append("Member " + membercount + " : " + member + "\n");
        }
    }
}
