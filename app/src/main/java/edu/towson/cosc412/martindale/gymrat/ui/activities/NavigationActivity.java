package edu.towson.cosc412.martindale.gymrat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import static androidx.navigation.Navigation.findNavController;

import edu.towson.cosc412.martindale.gymrat.database.entities.Routine;
import edu.towson.cosc412.martindale.gymrat.ui.NavController;
import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.ui.fragments.Timer;


public class NavigationActivity extends AppCompatActivity implements NavController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_main);
    }

    @Override
    public void launchWorkoutMenu(View v) {
        findNavController(v).navigate(R.id.action_mainMenuFragment_to_workoutMenuFragment);
    }

    @Override
    public void launchWorkoutDirectory(View v) {
        findNavController(v).navigate(R.id.action_mainMenuFragment_to_workoutDirectoryFragment);
    }

    @Override
    public void launchUserProfile(View v) {
        findNavController(v).navigate(R.id.action_mainMenuFragment_to_userProfileFragment);
    }

    @Override
    public void launchSessionHistory(View v) {
        findNavController(v).navigate(R.id.action_workoutMenuFragment_to_sessionHistoryFragment);
    }

    @Override
    public void launchNextRecommended(View v) {
        findNavController(v).navigate(R.id.action_workoutMenuFragment_to_nextRecommendedWorkoutFragment);
    }

    @Override
    public void launchWorkoutSummary(View v) {
        findNavController(v).navigate(R.id.action_workoutMenuFragment_to_workoutSummaryFragment);
    }

    @Override
    public void launchRoutineCreation(View v) {
        findNavController(v).navigate(R.id.action_routineListFragment_to_routineCreationFragment);
    }

    @Override
    public void launchRoutineListFromWorkoutMenu(View v) {
        findNavController(v).navigate(R.id.action_workoutMenuFragment_to_routineListFragment);
    }

    @Override
    public void launchRoutineListFromCreation(View v) {
        findNavController(v).navigate(R.id.action_routineCreationFragment_to_routineListFragment);
    }

    @Override

    public void launchEquipmentGuideBack() {
        Intent intent = new Intent(getApplicationContext(), EquipmentGuideBack.class);
        startActivity(intent);
    }

    @Override
    public void launchEquipmentGuideChest() {
        Intent intent = new Intent(getApplicationContext(), EquipmentGuideChest.class);
        startActivity(intent);
    }

    @Override
    public void launchEquipmentGuideArms() {
        Intent intent = new Intent(getApplicationContext(), EquipmentGuideArms.class);
        startActivity(intent);
    }

    @Override
    public void launchEquipmentGuideLegs() {
        Intent intent = new Intent(getApplicationContext(), EquipmentGuideLegs.class);
        startActivity(intent);
    }

    @Override
    public void launchEquipmentGuideShoulders() {
        Intent intent = new Intent(getApplicationContext(), EquipmentGuideShoulders.class);
        startActivity(intent);
    }
  
    public void launchTimer(Routine routine) {
        Intent intent = new Intent(getApplicationContext(), Timer.class);
        intent.putExtra("routineID", routine.id);
      
        startActivity(intent);
    }

    @Override
    public void launchMainMenu(View v) {
         //To implement later, we want to probably add back buttons to get back to the main menu screen
    }
}
