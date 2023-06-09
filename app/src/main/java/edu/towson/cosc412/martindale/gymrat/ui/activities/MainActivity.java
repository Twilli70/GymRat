package edu.towson.cosc412.martindale.gymrat.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.GymRatDB;
import edu.towson.cosc412.martindale.gymrat.database.entities.Exercise;
import edu.towson.cosc412.martindale.gymrat.database.entities.Session;
import edu.towson.cosc412.martindale.gymrat.database.entities.Workout;
import edu.towson.cosc412.martindale.gymrat.old.DBHelper;

public class MainActivity extends AppCompatActivity {
    Button loginBtn, regBtn;
    EditText editUsr, editPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GymRatDB db = GymRatDB.getInstance();

        //reference layout
        loginBtn = findViewById(R.id.loginBtn);
        regBtn = findViewById(R.id.regBtn);
        editUsr = findViewById(R.id.editUsr);
        editPwd = findViewById(R.id.editPwd);

        //listeners
        loginBtn.setOnClickListener(it -> {

            String user = editUsr.getText().toString();
            String pass = editPwd.getText().toString();

            if (user.equals("") || pass.equals(""))
                Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            else {
                new Thread(() -> {
                    Looper.prepare();
                    boolean checkUserPass = db.login(user, pass);
                    if (checkUserPass) {
                        Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        openNavigation();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }).start();

            }
        });

        regBtn.setOnClickListener(it -> {
            Intent intent = new Intent(getApplicationContext(), Registration.class);
            startActivity(intent);
        });
    }


    public void openNavigation() {
        Intent intent = new Intent((Context) this, NavigationActivity.class);
        this.startActivity(intent);
    }
}