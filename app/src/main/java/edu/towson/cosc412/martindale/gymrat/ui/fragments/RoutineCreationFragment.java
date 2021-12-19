package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import edu.towson.cosc412.martindale.gymrat.ui.NavController;
import edu.towson.cosc412.martindale.gymrat.R;

public class RoutineCreationFragment extends Fragment {
    public RoutineCreationFragment() { super(R.layout.fragment_routine_creation); }

    private Button createNewRoutineButton;
    private NavController navController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_creation, container, false);
        createNewRoutineButton = view.findViewById(R.id.addNewRoutine);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createNewRoutineButton.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchRoutineListFromCreation(v);
            }
        });
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavController) {
            this.navController = (NavController)context;
        }
    }
}
