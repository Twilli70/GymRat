package edu.towson.cosc412.martindale.gymrat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginBtn);

        loginButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public final void onClick(View it) {
                openNavigation();
            }

        });
    }


    public void openNavigation() {
        Intent intent = new Intent((Context)this, NavigationActivity.class);
        this.startActivity(intent);
    }
}