//package edu.towson.cosc412.martindale.gymrat.fragments;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import android.widget.Toast;
//
//import edu.towson.cosc412.martindale.gymrat.R;
//
//public class UserProfile extends AppCompatActivity  {
//    Button save_contact; //this is the button that saves the user email and phone number
//    EditText phone_number, email_address; //this is the text box for the phone number and email address
//
//    public static final String MyPREFERENCES = "MyPrefs";
//    public static final String Email = "email_address";
//    public static final String Phone = "phone_number";
//
//    SharedPreferences sharedpreferences;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_user_profile);
//
//        save_contact = findViewById(R.id.save_contact);
//        phone_number = findViewById(R.id.phone_number);
//        email_address = findViewById(R.id.email_address);
//
//        save_contact=(Button)findViewById(R.id.save_contact);
//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//
//        save_contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String n  = phone_number.getText().toString();
//                String ph  = email_address.getText().toString();
//
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//
//                editor.putString(phone_number, n);
//                editor.putString(Phone, ph);
//                editor.putString(Email, e);
//                editor.commit();
//                Toast.makeText(MainActivity.this,"Thanks",Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//}



