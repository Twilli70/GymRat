package edu.towson.cosc412.martindale.gymrat.ui;

import android.view.View;

public interface NavController {
    public void launchWorkoutMenu(View v);
    public void launchWorkoutDirectory(View v);
    public void launchUserProfile(View v);
    public void launchWorkoutHistory(View v);
    public void launchNextRecommended(View v);
    public void launchMainMenu(View v);
    public void launchWorkoutSummary(View v);
    public void launchWorkoutProgress(View v);
    public void launchRoutineCreation(View v);
}
