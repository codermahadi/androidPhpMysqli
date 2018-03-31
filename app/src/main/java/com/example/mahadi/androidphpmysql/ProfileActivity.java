package com.example.mahadi.androidphpmysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView username, email, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!SharePrefMng.getInstance(this).isLoggedIn()){

            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            finish();
        } // end


        username = (TextView) findViewById(R.id.uname);
        email = (TextView) findViewById(R.id.email);
        id = (TextView) findViewById(R.id.uid);



    }
}
