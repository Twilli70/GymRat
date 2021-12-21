package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.ui.NavController;


public class WorkoutDirectoryFragment extends Fragment {
    public WorkoutDirectoryFragment() {
        super(R.layout.fragment_workout_directory);
    }
    private Button chestButton, backButton, shouldersButton, legsButton, armsButton;
    private NavController navController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_directory,
                container, false);

        chestButton = view.findViewById(R.id.chestDayBtn);
        backButton = view.findViewById(R.id.backDayBtn);
        shouldersButton = view.findViewById(R.id.shoulderDayBtn);
        legsButton = view.findViewById(R.id.legDayBtn);
        armsButton = view.findViewById(R.id.armsDayBtn);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chestButton.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) { navController.launchEquipmentGuideChest(); }
        });
        backButton.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) { navController.launchEquipmentGuideBack(); }
        });
        shouldersButton.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) { navController.launchEquipmentGuideShoulders(); }
        });
        legsButton.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) { navController.launchEquipmentGuideLegs(); }
        });
        armsButton.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) { navController.launchEquipmentGuideArms(); }
        });
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavController) {
            this.navController = (NavController)context;
        }
    }
}
