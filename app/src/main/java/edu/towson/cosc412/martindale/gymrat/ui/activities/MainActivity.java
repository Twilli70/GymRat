package edu.towson.cosc412.martindale.gymrat.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.GymRatDB;
import edu.towson.cosc412.martindale.gymrat.database.UserData;
import edu.towson.cosc412.martindale.gymrat.old.DBHelper;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                System.out.println("Attempt to Connect");
                Connection connection = DriverManager.getConnection("jdbc:mysql://gymratdb.cektgjjcjjdb.us-east-2.rds.amazonaws.com:3306/gymratdb?enabledTLSProtocols=TLSv1.2", "admin", "VobGjT47CiM2A");
                System.out.println("Connected!");
            }
            catch (Exception e){
                System.out.println("Failed To Connect");
                e.printStackTrace();
            }
            return null;
        }
    }


     Button loginBtn, regBtn;
     EditText editUsr, editPwd;
     DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Task().execute();
        //GymRatDB.getInstance();
        /*

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
        */
    }


    public void openNavigation() {
        Intent intent = new Intent((Context)this, NavigationActivity.class);
        this.startActivity(intent);
    }
}