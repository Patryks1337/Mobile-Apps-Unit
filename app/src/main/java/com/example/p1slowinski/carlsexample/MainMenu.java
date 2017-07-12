package com.example.p1slowinski.carlsexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainMenu extends AppCompatActivity {
    Button bPlay, bLevels, bSettings; //just variables for the buttons.

    /*

    Will initilize button play and create a click listener.

    */
    void ButtonPlay()
    {
        bPlay = (Button) findViewById(R.id.bPlay); // Grabs the button

        bPlay.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                Intent GameStart = new Intent(getApplicationContext(), Game.class);
                GameStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(GameStart);
                //startActivity(new Intent(MainMenu.this, Game.class));
            }
        });
    }

    /*

       Will initilize button levels and create a click listener.

    */
    void ButtonLevels()
    {
        bLevels = (Button) findViewById(R.id.bLevels);// Grabs the button

        bLevels.setOnClickListener(new View.OnClickListener() {// Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                Intent Levels = new Intent(getApplicationContext(), Levels.class);
                Levels.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Levels);
                 //startActivity(new Intent(MainMenu.this, Levels.class)); // Starts levels activity
            }
        });
    }

    /*

       Will initilize button Settings and create a click listener.

       */
    void ButtonSettings()
    {
        bSettings = (Button) findViewById(R.id.bSettings);// Grabs the button

        bSettings.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                Intent Settings = new Intent(getApplicationContext(), Settings.class);
                Settings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Settings);
                //startActivity(new Intent(MainMenu.this, Settings.class)); // Starts Settings activity
            }
        });
    }

    /*
    This will initialize all of the buttons above.
     */

    void InitilizeButtons()
    {
        ButtonPlay();
        ButtonLevels();
        ButtonSettings();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        InitilizeButtons(); // Runs them. Its cleaner this way.

    }







}
