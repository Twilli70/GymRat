package edu.towson.cosc412.martindale.gymrat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

     Button loginBtn, regBtn;
     EditText editUsr, editPwd;
     DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference layout
        loginBtn= findViewById(R.id.loginBtn);
        regBtn= findViewById(R.id.regBtn);
        editUsr = findViewById(R.id.editUsr);
        editPwd = findViewById(R.id.editPwd);
        DB = new DBHelper(this);


        //listeners
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View it) {

                String user = editUsr.getText().toString();
                String pass = editPwd.getText().toString();

                if(user.equals("") || pass.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkUserPass = DB.checkUsernamePassword(user,pass);
                    if(checkUserPass){
                        Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        openNavigation();
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
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