package edu.towson.cosc412.martindale.gymrat.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.towson.cosc412.martindale.gymrat.R;


public class WorkoutDirectoryFragment extends Fragment {
    public WorkoutDirectoryFragment() {
        super(R.layout.fragment_workout_directory);
    }

    public Button btn_chestDay,
            btn_backDay, btn_shoulderDay, btn_legDay, btn_armDay;




    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_directory,
                container, false);

        btn_chestDay = view.findViewById(R.id.chestDayBtn);
        btn_backDay = view.findViewById(R.id.backDayBtn);
        btn_shoulderDay = view.findViewById(R.id.shoulderDayBtn);
        btn_legDay = view.findViewById(R.id.legDayBtn);
        btn_armDay = view.findViewById(R.id.armsDayBtn);
/*
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .uri(URI.create("https://exercisedb.p.rapidapi.com/exercises/bodyPart/chest"))
                        .header("x-rapidapi-host", "exercisedb.p.rapidapi.com")
                        .header("x-rapidapi-key", "0e6f4f553amsh1d0cfd646dc470dp1a031ajsnbe308aff186a")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                Response response = null;

                try{
                    response= client.newCall(request).execute();
                    return response.body().string();
                }catch(IOException e){
                    e.printStackTrace();


                }

                return null;
            }
        };*/

        return view;
    }
    ///////////////////
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_chestDay.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) {
            }
        });
        btn_backDay.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) {

            }
        });
        btn_shoulderDay.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) {

            }
        });
        btn_legDay.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) {

            }
        });
        btn_armDay.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View v) {

            }
        });
    }
}
