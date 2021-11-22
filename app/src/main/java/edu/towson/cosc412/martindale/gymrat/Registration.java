package edu.towson.cosc412.martindale.gymrat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.*;


public class Registration extends AppCompatActivity {
    DBHelper DB;
    EditText newUsr, newPwd, newPwd1;
    Button regDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference layout
        newUsr= findViewById(R.id.newUsr);
        newPwd= findViewById(R.id.newPwd);
        newPwd1 = findViewById(R.id.newPwd1);
        regDone= findViewById(R.id.regDone);

        //listeners
        regDone.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View it) {
                String user = newUsr.getText().toString();
                String pass = newPwd.getText().toString();
                String repass = newPwd1.getText().toString();

                if(user.equals("") || pass.equals("")||repass.equals(""))
                    Toast.makeText(Registration.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else {
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false){
                            Boolean insert = DB.insertData(user,pass);
                            if(insert==true){
                                Toast.makeText(Registration.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }



}