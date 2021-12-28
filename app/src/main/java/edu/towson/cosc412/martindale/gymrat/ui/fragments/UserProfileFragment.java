package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.GymRatDB;
import edu.towson.cosc412.martindale.gymrat.database.entities.User;
import edu.towson.cosc412.martindale.gymrat.ui.activities.MainActivity;
import edu.towson.cosc412.martindale.gymrat.ui.activities.Registration;

public class UserProfileFragment extends Fragment {

    TextView username, firstName, lastName, birthday, weight, height;
    GymRatDB db = GymRatDB.getInstance();

    public UserProfileFragment() {
        super(R.layout.fragment_user_profile);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        //reference layout
        username = view.findViewById(R.id.username);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        birthday = view.findViewById(R.id.birthday);
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        //emailAddress = view.findViewById(R.id.email_address);
        //saveUserProfileButton = view.findViewById(R.id.saveUserProfileBtn);


        username.setText(db.currentUser);
        new Thread(() -> {
            Looper.prepare();
            User user =  db.getUser(db.currentUser);

            username.setText(user.username);
            height.setText(String.format("%f", user.height));
            weight.setText(String.format("%f", db.getCurrentUserWeight(db.currentUser)));
            firstName.setText(user.firstName);
            lastName.setText(user.lastName);


        }).start();

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //save all content to DB
        /*saveUserProfileButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                //TODO: insert functionality to save to database
            }
        });
    }*/
    }
}
