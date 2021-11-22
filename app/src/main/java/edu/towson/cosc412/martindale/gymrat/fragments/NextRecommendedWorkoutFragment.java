package edu.towson.cosc412.martindale.gymrat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import edu.towson.cosc412.martindale.gymrat.R;

public class NextRecommendedWorkoutFragment extends Fragment {
    public NextRecommendedWorkoutFragment() {
        super(R.layout.fragment_next_recommended);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_next_recommended, container, false);
        return view;
    }
}