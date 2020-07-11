/*
 ************************************************************
 * Name: Safal Bhandari                                     *
 * Project: Java Go Stop                                     *
 * Class: OPL Spring 20                                     *
 * Date: 04/28/2020                                         *
 ************************************************************
 */
package com.example.android.gostop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Result Activity that displays the result of the round and
 * gives option to display winner, move to next round, or exit the game
 */
public class resultActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView  humanRoundScore;
    private TextView  humanTotalScore;
    private TextView  computerRoundScore;
    private TextView  computerTotalScore;
    private Button    nextRoundBegin;
    private Button    exitGame;
    private Button     displayWinner;
    private String    receivedData = "";
    String winner="";

    /**
     * called when result Activity is created
     * @param savedInstancesState
     */
    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_result);

        humanRoundScore = findViewById(R.id.humanRoundScore);
        humanTotalScore = findViewById(R.id.humanTotalScore);
        computerRoundScore = findViewById(R.id.computerRoundScore);
        computerTotalScore = findViewById(R.id.computerTotalScore);
        nextRoundBegin = findViewById(R.id.nextRound);
        exitGame = findViewById(R.id.exitGame);
        displayWinner = findViewById(R.id.winnerBtn);

        System.out.println("Data Received From RoundActivity 1 ");

        Intent intentFromRoundActivity = getIntent();
        if(intentFromRoundActivity.hasExtra(Intent.EXTRA_TEXT)){
            receivedData = intentFromRoundActivity.getStringExtra(Intent.EXTRA_TEXT);
            updateDisplayVariables();
        }

        System.out.println("Data Received From RoundActivity 2 ");
        System.out.println(receivedData);

        nextRoundBegin.setOnClickListener(this);
        exitGame.setOnClickListener(this);
        displayWinner.setOnClickListener(this);


    }

    /**
     * to properly update the variables to be displayed on the screen
     */
    public void updateDisplayVariables(){

        int computerTotalScoreWinner = 0;
        int humanTotalScoreWinner =0;

        JSONObject obj= null;
        try {
            obj = new JSONObject(receivedData);
        }catch(JSONException e){

        }

        try {
            humanRoundScore.setText(obj.getString("humanRoundScore"));
            System.out.println(obj.getString("humanRoundScore"));
            humanTotalScore.setText(obj.getString("humanTotalScore"));
            humanTotalScoreWinner = Integer.parseInt(obj.getString("humanTotalScore"));
            System.out.println(obj.getString("humanTotalScore"));
            computerRoundScore.setText(obj.getString("computerRoundScore"));
            System.out.println(obj.getString("computerRoundScore"));
            computerTotalScore.setText(obj.getString("computerTotalScore"));
            computerTotalScoreWinner = Integer.parseInt(obj.getString("computerTotalScore"));
            System.out.println(obj.getString("computerTotalScore"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (humanTotalScoreWinner>computerTotalScoreWinner){
            winner = "Human";
        }else if(computerTotalScoreWinner > humanTotalScoreWinner){
            winner = "Computer";
        }else{
            winner = "Draw";
        }
    }


    /**
     * when a button is clicked by user to choose on next round, display winner, and exit game
     * @param v viewObject clicked by user
     */
    @Override
    public void onClick(View v) {

        //Intent that starts the next activity which displays all the cards and sets up the game
        final Context context  = resultActivity.this;

        if(v == nextRoundBegin){
            Class destinationActivity = RoundActivity.class;
            Intent intent = new Intent(context, destinationActivity);
            intent.putExtra(Intent.EXTRA_TEXT, receivedData);
            startActivity(intent);
        }else if(v == exitGame){
            Class destinationActivity = MainActivity.class;
            Intent intent = new Intent(context, destinationActivity);
            startActivity(intent);
        }else if(v == displayWinner){

            new AlertDialog.Builder(this)
                    .setTitle("***** WINNER ******")
                    .setMessage( winner + " won the game ! Congratulations!!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }
}
