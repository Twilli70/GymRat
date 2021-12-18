package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import edu.towson.cosc412.martindale.gymrat.R;

public class RoutineCreationFragment extends Fragment {
    public RoutineCreationFragment() { super(R.layout.fragment_routine_creation); }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_creation, container, false);
        return view;
    }
}
