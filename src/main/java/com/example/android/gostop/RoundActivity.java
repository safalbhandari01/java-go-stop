/*
 ************************************************************
 * Name: Safal Bhandari                                     *
 * Project: Java Go Stop                                     *
 * Class: OPL Spring 20                                     *
 * Date: 04/28/2020                                         *
 ************************************************************
 */
package com.example.android.gostop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;



/**
 * Round Activity that provides GUI interface for a round
 */
public class RoundActivity extends AppCompatActivity implements View.OnClickListener,  display {

    private Button displayCapturePileOrHandCardForHuman;
    private Button displayCapturePileOrHandCardForComputer;
    private controller controllerObj;


    /**
     * called when Activity is created
     * @param savedInstancesState
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_round);

        displayCapturePileOrHandCardForHuman = findViewById(R.id.displayCapturePileOrHandCardHuman);
        displayCapturePileOrHandCardForComputer = findViewById(R.id.displayCapturePileOrHandCardComputer);

        displayCapturePileOrHandCardForHuman.setOnClickListener(this);
        displayCapturePileOrHandCardForComputer.setOnClickListener(this);

        //getting the data from the intent
        Intent intentThatStartedThisActivity = getIntent();
        String messagePassed = "";
        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            messagePassed = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
        }

        System.out.println("The message passed through intent is"+ messagePassed);
        controllerObj = new controller( messagePassed, this);
        controllerObj.updateDisplay(this);

            if(controllerObj.whoseTurnFirst() == 2){
                Toast.makeText(getApplicationContext(), "Computers Turn First! Computer Making it's move!",
                        Toast.LENGTH_LONG).show();
                controllerObj.computerMove();

                //update the display after computer move
                updateDisplay();

                //check if the hand of both the player is empty or not if it is
                if(controllerObj.checkHandSizeForPlayers() == true){
                    //get round result
                    displayRoundResult();
                }

            }else{
                Toast.makeText(getApplicationContext(), "Human Turn First! Human make a move!",
                        Toast.LENGTH_LONG).show();
            }


    }

    /**
     * called when Activity is created by the user to inflate the menu
     * @param menu object clicked by user
     * @return returns true always
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu__extras, menu);
        return true;
    }

    /**
     * called when an item on the menu is clicked
     * @param item object of menuItem clicked by user
     * @return returns true always
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.saveGame:
                saveGame();
                return true;
            case R.id.helpMove:
                Vector<String> helpMessageFromHelperBot = controllerObj.executeHelpFunctionality();


                new AlertDialog.Builder(this)
                        .setTitle("Help Message")
                        .setMessage(" Layout Recommendation:"+helpMessageFromHelperBot.elementAt(0) + "Stock Pile Recommendation"+
                                helpMessageFromHelperBot.elementAt(1))

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;

            case R.id.logItem:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /**
     * called when an item on the menu is clicked
     * @param context, file_name used to save the game
     */
    private void saveGame(Context context, String file_name){

        File directory = this.getFilesDir();
        File file = new File(directory, file_name);
        try{
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(controllerObj.serialize().getBytes());
            stream.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }


    /**
     * called when an item on the menu is clicked
     * @param hand which is a vector of card inside another vector
     * @param layout, linear layout where the card is displayed
     */
    private void displayForLayout(Vector<Vector<Card>> hand, LinearLayout layout){
        layout.removeAllViews();
        for(int j=0;j<hand.size();j++){
            Vector<Card> cardSet = hand.elementAt(j);
            for (int i=0;i<cardSet.size();i++){
                Card card = cardSet.elementAt(i);
               // Log.d("humanHandCard", card.suitAndFace().toLowerCase());
                int id = getApplicationContext().getResources().getIdentifier(card.suitAndFace().toLowerCase(), "drawable", getApplicationContext().getPackageName());
               // Log.d("humanHandCardDrawName", getApplicationContext().getResources().getResourceEntryName(id));
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(id);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(250,250));
                imageView.setTag(card.toString());
                String layout_name = getApplicationContext().getResources().getResourceEntryName(layout.getId());
                if ((layout_name.equals("stockPile") || layout_name.equals("layoutPile")) && i==0){
                   // imageView.setOnClickListener(new TableCardClickListenter(card));
                }
                if (layout_name.equals("humanHand")){
                    imageView.setOnClickListener(new HumanHandClickListener(card));
                }
                layout.addView(imageView);
            }
        }
    }


    /**
     * Used for displaying cards on android screen
     * Adds imageview related to the card in cardcollection to the passed layout view
     * @param cardSet Collection of cards represented by Vector of card
     * @param layout layout to which the views are added
     */
    private void display(Vector<Card> cardSet, LinearLayout layout){
        //check if this removes all the previous cards or not
        layout.removeAllViews();


        for (int i=0;i<cardSet.size();i++){
            Card card = cardSet.elementAt(i);
            //Log.d("humanHandCard", card.suitAndFace().toLowerCase());
            int id = getApplicationContext().getResources().getIdentifier(card.suitAndFace().toLowerCase(), "drawable", getApplicationContext().getPackageName());
           // Log.d("humanHandCardDrawName", getApplicationContext().getResources().getResourceEntryName(id));
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(id);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(250,250));
            imageView.setTag(card.toString());
            String layout_name = getApplicationContext().getResources().getResourceEntryName(layout.getId());

            if (layout_name.equals("humanHand")){
                imageView.setOnClickListener(new HumanHandClickListener(card));
            }
            layout.addView(imageView);
        }

    }


    /**
     * Used for calling updateDisplay function using controllerObj
     */
    public void updateDisplay(){
        if(controllerObj.updateDisplay(this)){
            //display game end if true
        }
    }


    /**
     * Allows the user to save the state of game. Asks for filename while saving the game.
     */
    public void saveGame() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        final EditText file_name = (EditText) dialogView.findViewById(R.id.file_name);

        builder.setTitle("Save Game");
        builder.setMessage("Enter File Name");
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                //controllerObj.saveGame(getApplicationContext(), file_name.getText().toString());
                saveGame(RoundActivity.this, file_name.getText().toString());
                closeGame();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog save_alert_dialog = builder.create();
        save_alert_dialog.show();

    }

    /**
     * Called by after state of the game is saved in order to exit the current round activity.
     */
    void closeGame(){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    /**
     * Displays the cards in human hand on screen
     * @param hand vector of cards in human hand
     */
    @Override
    public void displayHumanHand(Vector<Card> hand) {
        display(hand, (LinearLayout) findViewById(R.id.humanHand));
    }

    /**
     * Displays the cards in computer hand on screen
     * @param hand vector of cards in computer hand
     */
    @Override
    public void displayComputerHand(Vector<Card> hand) {
        display(hand, (LinearLayout) findViewById(R.id.computerHand));
    }

    /**
     * Displays the cards in discard pile on screen
     * @param hand vector of cards in stock pile
     */
    @Override
    public void displayStockPile(Vector<Card> hand) {

        Vector<Card> handCopy = new Vector<>();
        for(int i=hand.size()-1; i>=0;i--){
            handCopy.add(new Card(hand.elementAt(i).getFace(), hand.elementAt(i).getSuit()));
        }
        System.out.println("Stock pile copy size"+ handCopy.size());
        display(handCopy, (LinearLayout) findViewById(R.id.stockPile));

    }

    /**
     * Displays the cards in draw pile on screen
     * @param hand vector of cards in layout pile
     */
    @Override
    public void displayLayoutPile(Vector<Vector<Card>> hand) {
        displayForLayout(hand, (LinearLayout) findViewById(R.id.layoutPile));
    }

    /**
     * Updates round number on the screen
     * @param num Number representing round
     */
    @Override
    public void updateRound(int num) {
        TextView textView = findViewById(R.id.roundNo);
        textView.setText("Round No: " + num);
    }

    /**
     * Updates computer score on screen
     * @param num Number representing computer's cumulative score
     */
    @Override
    public void updateComputerScore(int num) {
        TextView textView = findViewById(R.id.computerScore);
        textView.setText("Computer Score: " + num);
    }

    /**
     * Updates human score on screen
     * @param num Number representing human's cumulative score
     */
    @Override
    public void updateHumanScore(int num) {
        TextView textView = findViewById(R.id.humanScore);
        textView.setText("Human Score: " + num);
    }


    /**
     * when a button is clicked by user for displaying capture pile for human and computer
     * Adds imageview related to the card in cardcollection to the passed layout view
     * @param v viewObject clicked by user
     */
    @Override
    public void onClick(View v){
        if(v==displayCapturePileOrHandCardForHuman){


            Log.d("CapturePileClicked","Human");

            LinearLayout lay = findViewById(R.id.humanHand);
            lay.removeAllViews();

            if(displayCapturePileOrHandCardForHuman.getText().equals("Capture Pile")){



                //get the human player capture pile and update to display when user wants to see the capture pile
                display(controllerObj.getCapturePileHuman(),(LinearLayout) findViewById(R.id.humanHand));
                displayCapturePileOrHandCardForHuman.setText("Human Hand");
            }else{
                //get the human player hand and update to display
                display(controllerObj.getHumanHand(),(LinearLayout) findViewById(R.id.humanHand));
                displayCapturePileOrHandCardForHuman.setText("Capture Pile");
            }

        } else if (v == displayCapturePileOrHandCardForComputer) {

            Log.d("CapturePileCLicked","Computer");
            LinearLayout lay = findViewById(R.id.computerHand);
            lay.removeAllViews();

            if(displayCapturePileOrHandCardForComputer.getText().equals("Capture Pile")){

                //get the human player capture pile and update to display when user wants to see the capture pile
                display(controllerObj.getCapturePileComputer(),(LinearLayout) findViewById(R.id.computerHand));
                displayCapturePileOrHandCardForComputer.setText("Computer Hand");
            }else{
                //get the human player hand and update to display

                display(controllerObj.getComputerHand(),(LinearLayout) findViewById(R.id.computerHand));
                displayCapturePileOrHandCardForComputer.setText("Capture Pile");
            }

        }

    }

    /**
     * used to listen to cards clicked by the user on the hand of the human
     */
    class HumanHandClickListener implements View.OnClickListener{
        private Card card;
        HumanHandClickListener(Card card){
            this.card = card;
        }
        @Override
        public void onClick(View view) {


                if (R.id.humanHand == ((LinearLayout) view.getParent()).getId()){
                    Log.d("Human hand", card.suitAndFace() + " clicked");
                    //remove the card picked from the human hand
                    LinearLayout lay = findViewById(R.id.humanHand);
                    int index = lay.indexOfChild(lay);
                    controllerObj.pickCard(card);

                    Log.d("Index of card","Index"+index);
                    //controllerObj.pickCard(card,index );
                    updateDisplay();


                    //check if the hand of both the player is empty or not if it is
                    if(controllerObj.checkHandSizeForPlayers() == true){
                        //get round result

                        displayRoundResult();
                        //Use intent  to get to another activity and display the result of the round
                        //in that activity display the results of the round
                        //ask the user if he/she wants to play another round
                        //if want to play another then redirect to round activity and re distribute all the cards


                    }else{

                        Toast.makeText(getApplicationContext(), "Computers Turn! Computer Making it's move!",
                                Toast.LENGTH_SHORT).show();

                        //make the computer move after human move
                        controllerObj.computerMove();

                        //update the display after computer move
                        updateDisplay();

                        //check if the hand of both the player is empty or not if it is
                        if(controllerObj.checkHandSizeForPlayers() == true){
                            //get round result

                            displayRoundResult();
                            //Use intent  to get to another activity and display the result of the round
                            //in that activity display the results of the round
                            //ask the user if he/she wants to play another round
                            //if want to play another then redirect to round activity and re distribute all the cards


                        }

                    }
                }
            }

    }

    /**
     * Used for displaying result of the round by calling another activity and passing result of the round
     */
    public void displayRoundResult(){

        JSONObject obj = controllerObj.createJsonObjectWithRoundResult();
        //Intent that starts the next activity which displays all the cards and sets up the game

        Context context  = RoundActivity.this;
        Class destinationActivity = resultActivity.class;
        Intent intentToSend = new Intent(context, destinationActivity);
        System.out.println(obj.toString());
        String message = obj.toString();
        intentToSend.putExtra(Intent.EXTRA_TEXT, message);

        //Call the intent to move to another activity
        startActivity(intentToSend);
    }



}
