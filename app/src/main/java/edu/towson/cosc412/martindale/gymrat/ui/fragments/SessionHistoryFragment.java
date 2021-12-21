package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.GymRatDB;
import edu.towson.cosc412.martindale.gymrat.database.entities.Session;

public class SessionHistoryFragment extends Fragment {
    public SessionHistoryFragment() {
        super(R.layout.fragment_session_history);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session_history, container, false);
        ListView listView = view.findViewById(R.id.listView);

        GymRatDB db = GymRatDB.getInstance();
        new Thread(()->{
            ArrayList<Session> sessions = db.getAllSessions(db.currentUser);
            getActivity().runOnUiThread(() -> {
                SessionListAdapter adapter = new SessionListAdapter(getActivity(), R.layout.session_item, sessions);
                listView.setAdapter(adapter);
            });
        }).start();


        return view;
    }
}
