package edu.towson.cosc412.martindale.gymrat.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.Date;
import java.time.Month;
import java.util.Calendar;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.GymRatDB;
import edu.towson.cosc412.martindale.gymrat.database.entities.User;
import edu.towson.cosc412.martindale.gymrat.old.DBHelper;


public class Registration extends AppCompatActivity {
    DBHelper DB;
    EditText newUsr, newPwd, newPwd1, newHeightInches, newHeightFeet, newName;
    CalendarView newbirthdayCalendar;
    Button regDone, enterBirthdayButton, saveBirthdayButton;
    LinearLayout heightLayout;
    Calendar calendar;
    int curDate, Year, Month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        GymRatDB db = GymRatDB.getInstance();

        //reference layout
        newUsr = findViewById(R.id.newUsr);
        newPwd = findViewById(R.id.newPwd);
        newPwd1 = findViewById(R.id.newPwd1);
        newName = findViewById(R.id.newName);
        newHeightFeet = findViewById(R.id.newHeightFeet);
        newHeightInches = findViewById(R.id.newHeightInches);
        newbirthdayCalendar = findViewById(R.id.newBirthday);
        enterBirthdayButton = findViewById(R.id.enterBirthdayBtn);
        saveBirthdayButton = findViewById(R.id.saveBirthdayBtn);
        heightLayout = findViewById(R.id.heightLinearLayout);
        regDone = findViewById(R.id.regDone);
        calendar = Calendar.getInstance();

        //Initial visibility
        newUsr.setVisibility(View.VISIBLE);
        heightLayout.setVisibility(View.VISIBLE);
        newPwd.setVisibility(View.VISIBLE);
        newPwd1.setVisibility(View.VISIBLE);
        newName.setVisibility(View.VISIBLE);
        enterBirthdayButton.setVisibility(View.VISIBLE);
        saveBirthdayButton.setVisibility(View.INVISIBLE);
        newbirthdayCalendar.setVisibility(View.INVISIBLE);

        //setCalendar
        calendar.set(1999, 1, 1);
        newbirthdayCalendar.setDate(calendar.getTimeInMillis());
        newbirthdayCalendar.setMinDate(calendar.getTimeInMillis());
        calendar.set(2001, 1, 1);
        newbirthdayCalendar.setMaxDate(calendar.getTimeInMillis());

        //listeners
        enterBirthdayButton.setOnClickListener(it -> {
            newUsr.setVisibility(View.INVISIBLE);
            heightLayout.setVisibility(View.INVISIBLE);
            newPwd.setVisibility(View.INVISIBLE);
            newPwd1.setVisibility(View.INVISIBLE);
            newName.setVisibility(View.INVISIBLE);
            enterBirthdayButton.setVisibility(View.INVISIBLE);
            saveBirthdayButton.setVisibility(View.VISIBLE);
            newbirthdayCalendar.setVisibility(View.VISIBLE);
        });

        saveBirthdayButton.setOnClickListener(it -> {
            newUsr.setVisibility(View.VISIBLE);
            heightLayout.setVisibility(View.VISIBLE);
            newPwd.setVisibility(View.VISIBLE);
            newPwd1.setVisibility(View.VISIBLE);
            newName.setVisibility(View.VISIBLE);
            enterBirthdayButton.setVisibility(View.VISIBLE);
            saveBirthdayButton.setVisibility(View.INVISIBLE);
            newbirthdayCalendar.setVisibility(View.INVISIBLE);
        });

        newbirthdayCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                curDate = dayOfMonth;
                Year = year;
                Month = month;
            }
        });



        regDone.setOnClickListener(it -> {
            String user = newUsr.getText().toString();
            Float heightFeet = Float.parseFloat(newHeightFeet.getText().toString());
            Float heightInches = Float.parseFloat(newHeightInches.getText().toString());
            Float height = heightFeet * 12 + heightInches;
            String pass = newPwd.getText().toString();
            String repass = newPwd1.getText().toString();
            String fullName = newName.getText().toString();
            String[] nameSplit = fullName.split(" ");

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
                            userData.firstName = nameSplit[0];
                            if (nameSplit[1] == null) {
                                userData.lastName = "";
                            } else {
                                userData.lastName = nameSplit[1];
                            }
                            userData.height = height; // Hard coded for testing purposes
                            userData.setbirthday(Year, Month, curDate); // Hard coded for testing purposes
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