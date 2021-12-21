package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import edu.towson.cosc412.martindale.gymrat.database.GymRatDB;
import edu.towson.cosc412.martindale.gymrat.ui.NavController;
import edu.towson.cosc412.martindale.gymrat.R;

public class MainMenuFragment extends Fragment {
    public MainMenuFragment() {
        super(R.layout.fragment_main_menu);
        }

    private Button userProfileButton;
    private Button workoutDirectoryButton;
    private Button workoutMenuButton;
    private NavController navController;
    TextView textView2;
    GymRatDB db =GymRatDB.getInstance();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        textView2 = view.findViewById(R.id.textView2);

        userProfileButton = view.findViewById(R.id.userProfileBtn);
        workoutDirectoryButton = view.findViewById(R.id.workoutDirectoryBtn);
        workoutMenuButton = view.findViewById(R.id.workoutMenuBtn);
        textView2.setText("Welcome to GYMRAT" + db.currentUser);
        return view;

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userProfileButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchUserProfile(v);
            }
        });
        workoutDirectoryButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchWorkoutDirectory(v);
            }
        });
        workoutMenuButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View v) {
                navController.launchWorkoutMenu(v);
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
