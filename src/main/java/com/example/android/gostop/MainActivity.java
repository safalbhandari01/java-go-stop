/*
 ************************************************************
 * Name: Safal Bhandari                                     *
 * Project: Java Go Stop                                     *
 * Class: OPL Spring 20                                     *
 * Date: 04/28/2020                                         *
 ************************************************************
 */
package com.example.android.gostop;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Starting Activity. Allows user to start a new game or load the game.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

     Button startNewGame;
     Button loadNewGame;


    /**
     * activity that provide entry to the program and allows to select new game or load game
     * @param  savedInstanceState
     * @return vector of card objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //using findViewById to get reference to elements of the view
        startNewGame = findViewById(R.id.newGame);
        loadNewGame = findViewById(R.id.loadGame);

        Log.i("MainActivity","test");

        startNewGame.setOnClickListener(this);
        loadNewGame.setOnClickListener(this);

    }

    /**
     * onClick listens to when the new or load button is clicked and creates a intent with
     * new or load as message passed to the round activity
     * @param v a view object that the user clicked
     */
    @Override
    public void onClick(View v){
        System.out.println("CHECK");

        Log.i("MainActivity","test");

        //Intent that starts the next activity which displays all the cards and sets up the game
        Context context  = MainActivity.this;
        Class destinationActivity = RoundActivity.class;
        Intent intent = new Intent(context, destinationActivity);
        String message;

        //if new game is clicked display the round without loading
        if(v == startNewGame){
            //pass the activity with data that says new game
            message = "new";
        }else{//if load game is clicked display the round loading the game from prev state
            //pass the activity with data that says load game
            message = "load";
        }
        intent.putExtra(Intent.EXTRA_TEXT, message);

        //Call the intent to move to another activity
        startActivity(intent);


    }
}
