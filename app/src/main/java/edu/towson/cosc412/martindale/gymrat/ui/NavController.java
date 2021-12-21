package edu.towson.cosc412.martindale.gymrat.ui;

import android.view.View;

public interface NavController {
    public void launchWorkoutMenu(View v);
    public void launchWorkoutDirectory(View v);
    public void launchUserProfile(View v);
    public void launchSessionHistory(View v);
    public void launchNextRecommended(View v);
    public void launchMainMenu(View v);
    public void launchWorkoutSummary(View v);
    public void launchRoutineCreation(View v);
    public void launchRoutineListFromWorkoutMenu(View v);
    public void launchRoutineListFromCreation(View v);
    public void launchTimer();
}
