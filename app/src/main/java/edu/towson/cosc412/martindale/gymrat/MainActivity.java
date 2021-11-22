package edu.towson.cosc412.martindale.gymrat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.*;

public class MainActivity extends AppCompatActivity {

     Button loginBtn, regBtn;
     EditText editUsr, editPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference layout
        loginBtn= findViewById(R.id.loginBtn);
        regBtn= findViewById(R.id.loginBtn);
        editUsr = findViewById(R.id.editUsr);
        editPwd = findViewById(R.id.editPwd);

        //listeners
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View it) {
                openNavigation();
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View it) {

                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);

            }
        });
    }


    public void openNavigation() {
        Intent intent = new Intent((Context)this, NavigationActivity.class);
        this.startActivity(intent);
    }
}