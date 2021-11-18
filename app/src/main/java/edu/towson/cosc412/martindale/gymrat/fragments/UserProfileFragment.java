package edu.towson.cosc412.martindale.gymrat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import edu.towson.cosc412.martindale.gymrat.R;

public class UserProfileFragment extends Fragment {
    public UserProfileFragment() {
        super(R.layout.fragment_user_profile);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        return view;
    }
}
