package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import edu.towson.cosc412.martindale.gymrat.R;

public class SessionHistoryFragment extends Fragment {
    public SessionHistoryFragment() {
        super(R.layout.fragment_session_history);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session_history, container, false);
        return view;
    }
}
