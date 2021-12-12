package edu.towson.cosc412.martindale.gymrat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import edu.towson.cosc412.martindale.gymrat.NavController;
import edu.towson.cosc412.martindale.gymrat.R;

public class WorkoutMenuFragment extends Fragment {
    public WorkoutMenuFragment() {
        super(R.layout.fragment_workout_menu);
    }

    private Button previousWorkoutsButton;
    private Button nextRecommendedButton;
    private NavController navController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_menu, container, false);

        nextRecommendedButton = view.findViewById(R.id.nextRecommendedBtn);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        previousWorkoutsButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchPrevious(v);
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
