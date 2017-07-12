package com.example.p1slowinski.carlsexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by p1slowinski on 09/05/2017.
 */

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BackSettings();
    }

    void BackSettings()
    {
        Button bBack = (Button) findViewById(R.id.BTNBackSettings); // Grabs the button

        bBack.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                //TODO: Clear all activities.
                Intent MainMenu = new Intent(getApplicationContext(), MainMenu.class);
                MainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(MainMenu);
               // startActivity(new Intent(Settings.this, MainMenu.class));
            }
        });
    }

}
