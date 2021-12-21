package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.entities.Session;
import edu.towson.cosc412.martindale.gymrat.database.entities.Workout;

public class SessionListAdapter extends ArrayAdapter<Session> {
    private static final String TAG = "SessionListAdapter";
    private Context mContext;
    private int mResource;

    public SessionListAdapter(Context context, int resource, List<Session> sessions){
        super(context, resource, sessions);
        mContext = context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Session session = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView startTimeLabel = (TextView) convertView.findViewById(R.id.startTimeLabel);
        TextView durationLabel = (TextView) convertView.findViewById(R.id.durationLabel);
        TextView exerciseLabel = (TextView) convertView.findViewById(R.id.exerciseLabel);
        TextView caloriesBurnedLabel = (TextView) convertView.findViewById(R.id.caloriesBurnedLabel);

        StringBuilder exerciseNames = new StringBuilder();

        int workoutCount = session.routine.workouts.size();
        workoutCount = Math.min(3, workoutCount);

        for (int i = 0; i < workoutCount; i++){
            Workout workout = session.routine.workouts.get(i);
            exerciseNames.append(workout.exercise.name);
            if (i < workoutCount - 1)
                exerciseNames.append("\n");
        }

        startTimeLabel.setText(session.getStartDateTime());
        durationLabel.setText(String.format(Locale.ENGLISH,"%d", session.getDuration()));
        exerciseLabel.setText(exerciseNames);

        return convertView;
    }

}
