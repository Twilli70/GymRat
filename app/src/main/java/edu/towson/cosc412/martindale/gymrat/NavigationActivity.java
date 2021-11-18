package edu.towson.cosc412.martindale.gymrat;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import static androidx.navigation.Navigation.findNavController;


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
    public void launchPrevious(View v) {
        findNavController(v).navigate(R.id.action_workoutMenuFragment_to_previousWorkoutsFragment);
    }

    @Override
    public void launchNextRecommended(View v) {
        findNavController(v).navigate(R.id.action_workoutMenuFragment_to_nextRecommendedWorkoutFragment);
    }

    @Override
    public void launchMainMenu(View v) {
         //To implement later, we want to probably add back buttons to get back to the main menu screen
    }
}
