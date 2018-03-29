package org.fhict.fontys.kingsen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.fhict.fontys.kingsen.Objects.Group;

public class StartActivity extends AppCompatActivity {

    private Group currentgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        currentgroup = (Group) getIntent().getSerializableExtra("GROUP");

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

    public void Startgame (View view)
    {
        Intent intent = new Intent(getBaseContext(), GameActivity.class);
        intent.putExtra("GROUP",currentgroup);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
