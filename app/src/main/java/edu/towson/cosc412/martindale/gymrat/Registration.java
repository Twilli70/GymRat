package edu.towson.cosc412.martindale.gymrat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Registration extends AppCompatActivity {

    EditText newUsr, newPwd;
    Button regDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference layout
        newUsr= findViewById(R.id.newUsr);
        newPwd= findViewById(R.id.newPwd);
        regDone= findViewById(R.id.regDone);


        //listeners
        regDone.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View it) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }



}