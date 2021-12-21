package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.entities.Routine;

public class RoutineListAdapter extends ArrayAdapter<Routine> {
    private static final String TAG = "SessionListAdapter";
    private Context mContext;
    private int mResource;

    public RoutineListAdapter(Context context, int resource, List<Routine> sessions){
        super(context, resource, sessions);
        mContext = context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Routine routine = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView routineNameLabel = (TextView) convertView.findViewById(R.id.routineNameLabel);
        Button startButton = (Button) convertView.findViewById(R.id.startButton);

        routineNameLabel.setText(routine.name);

        startButton.setOnClickListener(it -> {
            // Open timer page
            // update timer routine
            System.out.println("HELLOW WORLD");
        });

        return convertView;
    }

}
