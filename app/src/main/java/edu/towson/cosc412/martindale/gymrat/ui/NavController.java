package edu.towson.cosc412.martindale.gymrat.ui;

import android.view.View;

public interface NavController {
    public void launchWorkoutMenu(View v);
    public void launchWorkoutDirectory(View v);
    public void launchUserProfile(View v);
    public void launchPrevious(View v);
    public void launchNextRecommended(View v);
    public void launchMainMenu(View v);
}
