package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import edu.towson.cosc412.martindale.gymrat.R;

public class Timer extends AppCompatActivity {

    private Button startRoutineButton, endRoutineButton, pauseRoutineButton;
    private TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        //reference layout
        startRoutineButton = findViewById(R.id.startRoutineBtn);
        endRoutineButton = findViewById(R.id.endRoutineBtn);
        pauseRoutineButton = findViewById(R.id.pauseRoutineBtn);
        timer = findViewById(R.id.timerTV);

        //initialize layout
        pauseRoutineButton.setVisibility(View.INVISIBLE);

        startRoutineButton.setOnClickListener(it -> {
            startRoutineButton.setVisibility(View.INVISIBLE);
            pauseRoutineButton.setVisibility(View.VISIBLE);
            timer.setText("1");
            //need to implement actual timer
        });

        pauseRoutineButton.setOnClickListener(it -> {
            startRoutineButton.setVisibility(View.VISIBLE);
            pauseRoutineButton.setVisibility(View.INVISIBLE);
            timer.setText("paused");
            //need to implement timer being paused
        });

        endRoutineButton.setOnClickListener(it -> {
            //implement where this screen should take you
        });
    }
}
