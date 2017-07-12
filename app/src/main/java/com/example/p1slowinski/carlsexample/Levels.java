package com.example.p1slowinski.carlsexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by p1slowinski on 09/05/2017.
 */

public class Levels extends AppCompatActivity {

    Button bBack, blevel1, bLevel2, bLevel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        BackLevels();
        InitilizeLevels();
    }

    void BackLevels()
    {
        bBack = (Button) findViewById(R.id.BackBTN); // Grabs the button

        bBack.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                //TODO: Clear all activities.

                Intent MainMenu = new Intent(getApplicationContext(), MainMenu.class);
                MainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(MainMenu);
               // startActivity(new Intent(getApplicationContext(), MainMenu.class)); //Starts mainMenu Activity
            }
        });
    }

    void Level1()
    {
        blevel1 = (Button) findViewById(R.id.Level1); // Grabs the button

        blevel1.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                //TODO: Create activity with key L1

                Intent GameStart = new Intent(getApplicationContext(), Game.class);
                GameStart.putExtra("Level", "1");// TODO: localize
                GameStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(GameStart);
            }
        });
    }

    void Level2()
    {
        bLevel2 = (Button) findViewById(R.id.Level2); // Grabs the button

        bLevel2.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                //TODO: Create activity with key L2

                Intent GameStart = new Intent(getApplicationContext(), Game.class);
                GameStart.putExtra("Level", "2");// TODO: localize
                GameStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(GameStart);
            }
        });
    }

    void Level3()
    {
        bLevel3 = (Button) findViewById(R.id.Level3); // Grabs the button

        bLevel3.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                //TODO: Create activity with key L3

                Intent GameStart = new Intent(getApplicationContext(), Game.class);
                GameStart.putExtra("Level", "3");// TODO: localize
                GameStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(GameStart);
            }
        });
    }


    void InitilizeLevels()
    {
        Level1();
        Level2();
        Level3();
    }

}
