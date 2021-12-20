package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.GymRatDB;

public class UserProfileFragment extends Fragment {

    EditText phoneNumber, emailAddress;
    Button saveUserProfileButton;
    public UserProfileFragment() {
        super(R.layout.fragment_user_profile);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        //reference layout
        phoneNumber = view.findViewById(R.id.phone_number);
        emailAddress = view.findViewById(R.id.email_address);
        saveUserProfileButton = view.findViewById(R.id.saveUserProfileBtn);


        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //save all content to DB
        saveUserProfileButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                //TODO: insert functionality to save to database
            }
        });
    }
}
