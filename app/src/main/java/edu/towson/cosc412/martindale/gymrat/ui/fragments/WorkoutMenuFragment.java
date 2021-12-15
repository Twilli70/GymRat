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
    private Button workoutHistoryButton;
    private Button routineCreationButton;
    private Button workoutProgressButton;
    private NavController navController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_menu, container, false);

        workoutSummaryButton = view.findViewById(R.id.workoutSummaryBtn);
        workoutHistoryButton = view.findViewById(R.id.workoutHistoryBtn);
        routineCreationButton = view.findViewById(R.id.routineCreationBtn);
        workoutProgressButton = view.findViewById(R.id.workoutProgressBtn);
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
        workoutHistoryButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchWorkoutHistory(v);
            }
        });
        routineCreationButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchRoutineCreation(v);
            }
        });
        workoutProgressButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) { navController.launchWorkoutProgress(v); }
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
