package edu.towson.cosc412.martindale.gymrat.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import edu.towson.cosc412.martindale.gymrat.R;

public class EquipmentGuideLegs extends AppCompatActivity {
    Spinner spinnerDrop;
    ImageView firstImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_guide_legs);
        final String[] str = {"ChestOne", "LegOne"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EquipmentGuideLegs.this, android.R.layout.simple_dropdown_item_1line, str);

        spinnerDrop = findViewById(R.id.spinnerDropLegs);
        firstImage = findViewById(R.id.firstImageLegs);
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
