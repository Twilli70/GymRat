package edu.towson.cosc412.martindale.gymrat.ui.activities;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.towson.cosc412.martindale.gymrat.R;
import edu.towson.cosc412.martindale.gymrat.database.GymRatDB;
import edu.towson.cosc412.martindale.gymrat.database.UserData;

public class EquipmentGuide extends AppCompatActivity {
    Spinner spinnerDrop;
    ImageView firstImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_guide);
        final String[] str = {"ChestOne", "LegOne"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EquipmentGuide.this, android.R.layout.simple_dropdown_item_1line, str);

        spinnerDrop = findViewById(R.id.spinnerDrop);
        firstImage = findViewById(R.id.firstImage);
        spinnerDrop.setAdapter(arrayAdapter);
        spinnerDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (str[0].equals(spinnerDrop.getItemAtPosition(position).toString())) {
                    firstImage.setImageResource(R.drawable.chest_stick_figure);
                } else if (str[1].equals(spinnerDrop.getItemAtPosition(position).toString())) {
                    firstImage.setImageResource(R.drawable.legs1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }

        });


    }


}
