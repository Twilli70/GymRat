package edu.towson.cosc412.martindale.gymrat.ui;

import android.view.View;

import edu.towson.cosc412.martindale.gymrat.database.entities.Routine;

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

    public void launchEquipmentGuideBack();
    public void launchEquipmentGuideChest();
    public void launchEquipmentGuideArms();
    public void launchEquipmentGuideLegs();
    public void launchEquipmentGuideShoulders();

    public void launchTimer(Routine routine);

}
