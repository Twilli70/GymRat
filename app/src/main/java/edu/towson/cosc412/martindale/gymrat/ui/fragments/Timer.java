package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.GymRatDB;
import edu.towson.cosc412.martindale.gymrat.ui.activities.NavigationActivity;

public class Timer extends AppCompatActivity {

    class TimerRunnable implements Runnable {

        Handler handler;
        public long startTime = SystemClock.uptimeMillis();
        long timeWhenPaused = 0;
        boolean isPaused = true;

        public TimerRunnable(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            if (!isPaused) {
                long time = SystemClock.uptimeMillis() - startTime;
                long seconds = time / 1000;
                long minutes = seconds / 60;
                long hour = minutes / 60;
                timer.setText(String.format(Locale.ENGLISH, "%02d:%02d:%02d", hour, minutes, seconds % 60));
            }
            handler.postDelayed(this, 0);
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public void unpause() {
            isPaused = false;
        }

        public void pause() {
            isPaused = true;
            timeWhenPaused = SystemClock.uptimeMillis();
        }
    }

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

        Handler handler = new Handler();

        TimerRunnable runnable = new TimerRunnable(handler);
        handler.postDelayed(runnable, 0);

        startRoutineButton.setOnClickListener(it -> {
            startRoutineButton.setVisibility(View.INVISIBLE);
            runnable.unpause();
        });

        pauseRoutineButton.setOnClickListener(it -> {
        });

        endRoutineButton.setOnClickListener(it -> {
            GymRatDB db = GymRatDB.getInstance();
            int routineID = getIntent().getIntExtra("routineID", -1);
            LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(runnable.startTime), TimeZone.getDefault().toZoneId());
            new Thread(() -> {
                db.saveSession(db.currentUser, routineID, startTime, LocalDateTime.now());
            }).start();

            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            startActivity(intent);
        });
    }
}
