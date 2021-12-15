package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import edu.towson.cosc412.martindale.gymrat.R;

public class WorkoutProgressFragment extends Fragment {
    public WorkoutProgressFragment() {super(R.layout.fragment_workout_progress);}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_progress, container, false);
        return view;
    }
}
