package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.ui.NavController;

public class RoutineListFragment extends Fragment {
    public RoutineListFragment() { super(R.layout.fragment_routine_list); }

    private Button rountineCreationButton;
    private NavController navController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_list, container, false);
        rountineCreationButton = view.findViewById(R.id.createNewRoutineBtn);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rountineCreationButton.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchRoutineCreation(v);
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
