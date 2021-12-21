package edu.towson.cosc412.martindale.gymrat.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.time.Month;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.GymRatDB;
import edu.towson.cosc412.martindale.gymrat.database.entities.User;
import edu.towson.cosc412.martindale.gymrat.old.DBHelper;


public class Registration extends AppCompatActivity {
    EditText newUsr, newPwd, newPwd1;
    Button regDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        GymRatDB db = GymRatDB.getInstance();

        //reference layout
        newUsr = findViewById(R.id.newUsr);
        newPwd = findViewById(R.id.newPwd);
        newPwd1 = findViewById(R.id.newPwd1);
        regDone = findViewById(R.id.regDone);

        //listeners
        regDone.setOnClickListener(it -> {
            String user = newUsr.getText().toString();
            String pass = newPwd.getText().toString();
            String repass = newPwd1.getText().toString();

            if (user.equals("") || pass.equals("") || repass.equals(""))
                Toast.makeText(Registration.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            else {
                if (pass.equals(repass)) {
                    new Thread(() -> {
                        Looper.prepare();
                        if (!db.hasUser(user)) {
                            User userData = new User();
                            userData.username = user;
                            userData.password = pass;
                            userData.firstName = "Ezekiel"; // Hard coded for testing purposes
                            userData.lastName = "Halley"; // Hard coded for testing purposes
                            userData.height = 6; // Hard coded for testing purposes
                            userData.setbirthday(2000, 1, 10); // Hard coded for testing purposes
                            boolean insert = db.addNewUser(userData);
                            if (insert) {
                                Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Registration.this, "User already exists, Try again!", Toast.LENGTH_SHORT).show();
                        }
                    }).start();
                } else {
                    Toast.makeText(Registration.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}