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

public class WorkoutMenuFragment extends Fragment {
    public WorkoutMenuFragment() {
        super(R.layout.fragment_workout_menu);
    }

    private Button workoutSummaryButton;
    private Button nextRecommendedButton;
    private Button sessionHistoryButton;
    private Button routineListButton;
    private NavController navController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_menu, container, false);

        workoutSummaryButton = view.findViewById(R.id.workoutSummaryBtn);
        sessionHistoryButton = view.findViewById(R.id.workoutHistoryBtn);
        routineListButton = view.findViewById(R.id.routineCreationBtn);
        nextRecommendedButton = view.findViewById(R.id.nextRecommendedBtn);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workoutSummaryButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchWorkoutSummary(v);
            }
        });
        sessionHistoryButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchSessionHistory(v);
            }
        });
        routineListButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchRoutineListFromWorkoutMenu(v);
            }
        });
        nextRecommendedButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchNextRecommended(v);
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
