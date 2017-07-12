package com.example.p1slowinski.carlsexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.test.espresso.core.deps.guava.primitives.Ints;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by p1slowinski on 09/05/2017.
 */

public class Game extends AppCompatActivity {
    Button A1 = null, A2 = null, A3 = null, TryNextLevel = null, MainMenu = null;
    TextView Question = null, HighScore = null, Level = null, Stage = null, Timer = null, HighScoreText = null;

   //float CorrectAwnser = 0.f;

    int type = 0, var1 = 0, var2 = 0, CorrectSpot = 0, Score = 0, iStage = 0, iLevel = 1, CorrectAwnser = 0;

    String sQuestion = "", sLevel = "";

    boolean EndGame = false, NextLevel = false, LastAdd = false;

    private cTimer timerL1, timerL2;

    private class cTimer extends CountDownTimer {

        public cTimer(long millisInFuture,
                                long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Timer.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            Endgamefunc();
        }
    }

    void StartTimers()
    {
        if(iLevel == 2) {
            Timer.setVisibility(View.VISIBLE);
            Timer.setText("1");

            // CreateTimer(20000);
            timerL1 = new cTimer(20300, 1003); // 1 second intervals extra 3 is to compensate for 300 ms delay for timer to display correctly
            timerL1.start();
        } else if (iLevel == 3){
            Timer.setVisibility(View.VISIBLE);
            Timer.setText("1");

            //CreateTimer(10000);
            timerL2 = new cTimer(10300, 1003); // 1 second intervals extra 3 is to compensate for 300 ms delay for timer to display correctly
            timerL2.start();
        } else {
            Timer.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ResetAll();

        InitilizeAwnserButtons();
        InitilizeTextView();

        Bundle intentKey = getIntent().getExtras();

        if(intentKey != null)
        {
            sLevel = intentKey.getString("Level");

            if(sLevel.contains("1"))
            {
                iLevel = 1;
                Level.setText("Level 1");
            } else if(sLevel.contains("2")){
                iLevel = 2;
                Level.setText("Level 2");
            } else if(sLevel.contains("3")){
                iLevel = 3;
                Level.setText("Level 3");
            } else {
                iLevel = 0;
                Level.setText("Level ERROR");
            }
        }


        GenerateQuestion();
    }

    /*void CreateTimer(int Miniseconds)
    {
        new CountDownTimer(Miniseconds, 1000) {

            public void onTick(long millisUntilFinished) {
                Timer.setText(millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                Endgamefunc();
            }

        }.start();
    }*/

    /*
    Just in case java does not reset vars on a new activity
     */

    void ResetAll() //TODO: Check if varibles reset when activity is started. Check if memory management is similar to C++
    {
        timerL1 = null;
        timerL2 = null;

        NextLevel = false;
        EndGame = false;

        sQuestion = "";
        sLevel = "";

        CorrectAwnser = 0;
        type = 0;
        Score = 0;
        iStage = 0;
        CorrectSpot = 0;
        sQuestion = "";
        iLevel = 1; // in case others fail

        var1 = 0;
        var2 = 0;

        A1 = null;
        A2 = null;
        A3 = null;
        TryNextLevel = null;
        MainMenu = null;

        Question = null;
        HighScore = null;
        Level = null;
        Stage = null;
        Timer = null;
        HighScoreText = null;



    }

    void InitilizeTextView()
    {
        Question = (TextView)findViewById(R.id.txQuestion);
        HighScore = (TextView)findViewById(R.id.txScore);
        Level = (TextView)findViewById(R.id.txLevel);
        Stage = (TextView)findViewById(R.id.txStage);
        Timer = (TextView)findViewById(R.id.txTimer);
        HighScoreText = (TextView)findViewById(R.id.txHighScore);

        HighScore.setText("Score: 0");
        Level.setText("Level 1");
        Stage.setText("Stage 1 of 10");
        Timer.setVisibility(View.INVISIBLE);
        HighScoreText.setVisibility(View.INVISIBLE);
        MainMenu.setText("End Game");
    }

    boolean VerifyResult(int spotclicked)
    {
        if(spotclicked == CorrectSpot)
        {
            return true;
        } else {
            return false;
        }
    }

    void GetHighScore()
    {
        int HighScore = 1;

        /*

        SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("SCORE", Score);// TODO: localize
                    // Commit the edits!
                    editor.commit();

         */

        HighScoreText.setText("Highscore: " + Integer.toString(HighScore));
        HighScoreText.setVisibility(View.VISIBLE);
    }

    void Endgamefunc()
    {
        if(timerL1 != null)
        {
            timerL1.cancel();
        }

        if(timerL2 != null)
        {
            timerL2.cancel();
        }

        A1.setVisibility(View.INVISIBLE);
        A2.setVisibility(View.INVISIBLE);
        A3.setVisibility(View.INVISIBLE);
        HighScore.setVisibility(View.INVISIBLE);
        Stage.setVisibility(View.INVISIBLE);
        Level.setVisibility(View.INVISIBLE);
        Timer.setVisibility(View.INVISIBLE);

        GetHighScore();


        Question.setText("Score: " + Integer.toString(Score));

        if(Score == 10)
        {
            TryNextLevel.setText("Next Level");
            NextLevel = true;
        } else if (Score == 10 && iLevel == 3) {
            TryNextLevel.setText("Start Over");
            NextLevel = false;
        } else {
            TryNextLevel.setText("Try Again");
            NextLevel = false;
        }

        TryNextLevel.setVisibility(View.VISIBLE);
       // MainMenu.setVisibility(View.VISIBLE);
        MainMenu.setText("Main Menu");

        //Optimization
        TryNextLevel.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                if(NextLevel == true)
                {
                    if(iLevel == 1)
                    {
                        Intent GameStart = new Intent(getApplicationContext(), Game.class);
                        GameStart.putExtra("Level", "2");// TODO: localize
                        GameStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(GameStart);
                    } else if(iLevel == 2)
                    {
                        Intent GameStart = new Intent(getApplicationContext(), Game.class);
                        GameStart.putExtra("Level", "3");// TODO: localize
                        GameStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(GameStart);
                    } else if (Score == 10 && iLevel == 3) {
                        Intent GameStart = new Intent(getApplicationContext(), Game.class);
                        GameStart.putExtra("Level", "1");// TODO: localize
                        GameStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(GameStart);

                        //TODO: WIPE HIGH SCORE
                    }

                } else {

                    if(iLevel == 1)
                    {
                        Intent GameStart = new Intent(getApplicationContext(), Game.class);
                        GameStart.putExtra("Level", "1");// TODO: localize
                        GameStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(GameStart);
                    } else if(iLevel == 2) {
                        Intent GameStart = new Intent(getApplicationContext(), Game.class);
                        GameStart.putExtra("Level", "2");// TODO: localize
                        GameStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(GameStart);
                    } else if(iLevel == 3) {
                        Intent GameStart = new Intent(getApplicationContext(), Game.class);
                        GameStart.putExtra("Level", "3");// TODO: localize
                        GameStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(GameStart);
                    }

                }
            }
        });


    }

    void CheckStage()
    {
        if(iStage == 10)
        {
            EndGame = true;
            Endgamefunc();
        }
    }

    void UpdateStage()
    {
        iStage = iStage + 1;
        Stage.setText("Stage " + Integer.toString(iStage) + " of 10");
    }

    void Correct()
    {
        Score = Score + 1;

        UpdateStage();
        CheckStage();

        HighScore.setText("Score: " + Integer.toString(Score));

        if(iStage != 10 && Score != 10 && EndGame != true)
        {
            GenerateQuestion();
        }
    }

    void Incorrect()
    {
        UpdateStage();
        CheckStage();

        if(iStage != 10 && Score != 10 && EndGame != true)
        {
            GenerateQuestion();
        }
    }

    void btnA1()
    {
        A1 = (Button) findViewById(R.id.btnA1); // Grabs the button

        A1.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                if(VerifyResult(1))
                {
                    Correct();
                }else {
                    Incorrect();
                }
            }
        });
    }

    void btnA2()
    {
        A2 = (Button) findViewById(R.id.btnA2); // Grabs the button

        A2.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                if(VerifyResult(2))
                {
                    Correct();
                }else {
                    Incorrect();
                }
            }
        });
    }

    void btnA3()
    {
        A3 = (Button) findViewById(R.id.btnA3); // Grabs the button

        A3.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                if(VerifyResult(3))
                {
                    Correct();
                }else {
                    Incorrect();
                }
            }
        });
    }

    void InitilizeAwnserButtons()
    {
        btnA1();
        btnA2();
        btnA3();


        TryNextLevel = (Button) findViewById(R.id.btnLevelTry);
        MainMenu = (Button)findViewById(R.id.btnMainMenu);

        TryNextLevel.setVisibility(View.INVISIBLE);
        MainMenu.setVisibility(View.VISIBLE);
        MainMenu.setText("End Game");


        MainMenu.setOnClickListener(new View.OnClickListener() { // Creates a new listner for the button
            @Override
            public void onClick(View view) { // OnClick Hook
                Intent MainMenu = new Intent(getApplicationContext(), MainMenu.class);
                MainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(MainMenu);

                // startActivity(new Intent(getApplicationContext(), MainMenu.class));
            }
        });
    }

    //Generates random number between x and y
    public static int randInt(int min, int max) {
        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    void GenerateQuestion()
    {
        if(timerL1 != null)
        {
            timerL1.cancel();
        }

        if(timerL2 != null)
        {
            timerL2.cancel();
        }

        StartTimers();

        /*
        Types:
        1 = +
        2 = -
        3 = /
        4 = *

         */
        type = randInt(1,4);

        var1 = randInt(2,12);
        var2 = randInt(1,12);

        switch(type)
        {
            case 1:
                CorrectAwnser = var1 + var2;
                sQuestion = Integer.toString(var1) + " + " + Integer.toString(var2);
                break;
            case 2:

                if(var1 < var2) // Switch if var 1 is smaller than 2 so we dont have negatives
                {
                    int temp1 = var1;
                    int temp2 = var2;

                    var1 = temp2;
                    var2 = temp1;
                }

                CorrectAwnser = var1 - var2;
                sQuestion = Integer.toString(var1) + " - " + Integer.toString(var2);
                break;
            case 3:

                ArrayList<Integer> factors = new ArrayList<Integer>();

               //Get factors
               for (int i = 1; i < var1; i++) {
                    if (var1 % i == 0) {
                        factors.add(i);
                    }
                }

                if(factors.size() > 0)
                {
                    int Index = randInt(0,factors.size());

                    if(Index >= factors.size()) // probly not needed but just in case :P
                    {
                        Index = factors.size() - 1;
                    }

                    var2 = factors.get(Index);
                } else {
                    var2 = randInt(1,12);
                }

                factors.clear(); // Garbage Collecting

                CorrectAwnser = var1 / var2;
                sQuestion = Integer.toString(var1) + " / " + Integer.toString(var2);
                break;
            case 4:
                CorrectAwnser = var1 * var2;
                sQuestion = Integer.toString(var1) + " * " + Integer.toString(var2);
                break;
            default:
                sQuestion = "ERROR";
        }

        Question.setText(sQuestion + " = ");

        GenerateAwnsers();

    }

    /*
    Simple algorithim as this is only for college assigement. Will add / Minus depending on vars

     */

    int GeneratePlaceholder()
    {
        int iPlaceHolder = randInt(1,10);

        if(LastAdd)
        {
            iPlaceHolder = CorrectAwnser - randInt(1,7);

            LastAdd = false;
        } else if (!LastAdd)
        {
            iPlaceHolder = CorrectAwnser + randInt(1,7);
            LastAdd = true;
        }

        if(iPlaceHolder <= 0)
        {
            iPlaceHolder = CorrectAwnser + randInt(1,4);
        }

        return iPlaceHolder;
    }

    void GenerateAwnsers()
    {
        /*int Placeholder1 = randInt(1, 12), Placeholder2 = randInt(1,12);

        //Loop till we get different awnsers.
        //TODO: Replace this with a more efficient system and add more checks
        while(Placeholder1 == CorrectAwnser)
        {
            Placeholder1 = randInt(1,12);
        }

        while(Placeholder2 == CorrectAwnser)
        {
            Placeholder2 = randInt(1,12);
        }

        //Ghetto fix incase they are identical
        if(Placeholder1 == Placeholder2)
        {
            Placeholder2 = Placeholder2 + randInt(1,5);
        }*/

        int Placeholder1 = GeneratePlaceholder(), Placeholder2 = GeneratePlaceholder();

        if(Placeholder1 == CorrectAwnser || Placeholder1 == Placeholder2 || Placeholder2 == CorrectAwnser)
        {
            Placeholder1 = GeneratePlaceholder();
            Placeholder2 = GeneratePlaceholder();
        }

        CorrectSpot = randInt(1,3);

        if(CorrectSpot == 1)
        {
            A1.setText(Integer.toString(CorrectAwnser));
            A2.setText(Integer.toString(Placeholder1));
            A3.setText(Integer.toString(Placeholder2));
        } else if(CorrectSpot == 2)
        {
            A1.setText(Integer.toString(Placeholder1));
            A2.setText(Integer.toString(CorrectAwnser));
            A3.setText(Integer.toString(Placeholder2));
        }else if(CorrectSpot == 3)
        {
            A1.setText(Integer.toString(Placeholder1));
            A2.setText(Integer.toString(Placeholder2));
            A3.setText( Integer.toString(CorrectAwnser));
        } else {
            String Err = "ERROR";

            A1.setText(Err);
            A2.setText(Err);
            A3.setText(Err);
        }
    }

    /*void GenerateAwnsers()
    {
        int Placeholder1 = randInt(1, 12), Placeholder2 = randInt(1,12);

        if(Placeholder1 == CorrectAwnser)
        {
            Placeholder1 = randInt(1,12);
        }

        if(Placeholder2 == CorrectAwnser)
        {
            Placeholder2 = randInt(1,12);
        }

        CorrectSpot = randInt(1,3);

        if(CorrectSpot == 1)
        {
            A1.setText(Integer.toString(Math.round(CorrectAwnser)));
            A2.setText(Integer.toString(Placeholder1));
            A3.setText(Integer.toString(Placeholder2));
        } else if(CorrectSpot == 2)
        {
            A1.setText(Integer.toString(Placeholder1));
            A2.setText(Integer.toString(Math.round(CorrectAwnser)));
            A3.setText(Integer.toString(Placeholder2));
        }else if(CorrectSpot == 3)
        {
            A1.setText(Integer.toString(Placeholder1));
            A2.setText(Integer.toString(Placeholder2));
            A3.setText( Integer.toString(Math.round(CorrectAwnser)));
        } else {
            String Err = "ERROR";

            A1.setText(Err);
            A2.setText(Err);
            A3.setText(Err);
        }
    }*/
}
