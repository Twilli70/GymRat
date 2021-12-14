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

    EditText phoneNumber, emailAddress, currWeight, goalWeight;
    Button saveUserProfileButton;
    CheckBox gainWeight, gainMuscle, loseWeight, maintainWeight, healthyLifestyle, abdominal, legs, glutes, arms, chest, back;
    public UserProfileFragment() {
        super(R.layout.fragment_user_profile);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        //reference layout
        phoneNumber = view.findViewById(R.id.phone_number);
        emailAddress = view.findViewById(R.id.email_address);
        currWeight = view.findViewById(R.id.current_weight);
        goalWeight = view.findViewById(R.id.goal_weight);
        saveUserProfileButton = view.findViewById(R.id.saveUserProfileBtn);
        gainWeight = view.findViewById(R.id.gain_weight);
        gainMuscle = view.findViewById(R.id.gain_mucsle);
        loseWeight = view.findViewById(R.id.lose_weight);
        maintainWeight = view.findViewById(R.id.maintain);
        healthyLifestyle = view.findViewById(R.id.lifestyle);
        abdominal = view.findViewById(R.id.abs);
        legs = view.findViewById(R.id.legs);
        glutes = view.findViewById(R.id.glutes);
        arms = view.findViewById(R.id.arms);
        chest = view.findViewById(R.id.chest);
        back = view.findViewById(R.id.back);

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //save all content to DB
        saveUserProfileButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                //TODO: insert functionality to save to database
                // abdominal.isChecked(); returns a Boolean Value
                // phoneNumber.getText(); returns an EditText Value
            }
        });
    }
}
