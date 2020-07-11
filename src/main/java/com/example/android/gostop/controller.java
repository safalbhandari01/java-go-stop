/*
 ************************************************************
 * Name: Safal Bhandari                                     *
 * Project: Java Go Stop                                     *
 * Class: OPL Spring 20                                     *
 * Date: 04/28/2020                                         *
 ************************************************************
 */
package com.example.android.gostop;

import android.content.Context;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class controller {

    private final int  HUMAN_INDEX = 1;
    private final int COMPUTER_INDEX = 2;
    private final int COMPUTER_HELPER_BOT = 0;

    private Game gameObj;
    private Round roundObjActivity;
    Player[] players;

    /**
     * Constructor for controller class
     * @param context, context passed
     * @param newOrLoad , option chosen by player
     */
    controller(String newOrLoad, Context context) {

        gameObj = new Game();
        if(newOrLoad.equals("new")|| newOrLoad.equals("load")){
            //Round object provides us with all the cards, and info in the round
            gameObj.startGame(newOrLoad);
            roundObjActivity = gameObj.getRoundObj();
            players = roundObjActivity.getPlayers();
        }else{
            gameObj.startGame("new");
            roundObjActivity = gameObj.getRoundObj();
            players = roundObjActivity.getPlayers();
            setupNextSuccessiveRound(newOrLoad);
        }

        //change the variables of the new round to data taken from the file
        if(newOrLoad.equals("load")){
            uploadDataFromFile(context);
            roundObjActivity.printVectors();
            //uploadGame();
        }

    }


    /**
     * to upload data from a file
     * @param context, context passed
     */
    public void  uploadDataFromFile(Context context)  {


        serializer dataFromTextFile = new serializer(context);
        try {
            dataFromTextFile.read();
            //dataFromTextFile.read("case1.txt");


        } catch (IOException e) {

        }

        roundObjActivity.setRoundNumber(dataFromTextFile.roundNo);
        roundObjActivity.setNextPlayerTurn(dataFromTextFile.nextPlayer);

        if(dataFromTextFile.nextPlayer.equals("Human")){
            roundObjActivity.setNextPlayer(1);
        }else{
            roundObjActivity.setNextPlayer(2);
        }

        players[HUMAN_INDEX].setCardsOnHand(dataFromTextFile.humanHand);
        players[HUMAN_INDEX].setCardOnCapturePile(dataFromTextFile.capturePileHuman);
        players[HUMAN_INDEX].setTotalScore(dataFromTextFile.humanScore);

        players[COMPUTER_INDEX].setCardsOnHand(dataFromTextFile.computerHand);
        players[COMPUTER_INDEX].setCardOnCapturePile(dataFromTextFile.capturePileComputer);
        players[COMPUTER_INDEX].setTotalScore(dataFromTextFile.computerScore);

        Vector<Card> stockPileTemp = dataFromTextFile.stockPile;
        Collections.reverse(stockPileTemp);

        roundObjActivity.setCardsOnStockPile(stockPileTemp);

        Vector<Vector<Card>> tempLayout = new Vector<>();
        for(int i=0; i<dataFromTextFile.layout.size();i++){
            Vector<Card> tempCardOnLayout = new Vector<>();
            tempCardOnLayout.add(dataFromTextFile.layout.elementAt(i));
            tempLayout.add(tempCardOnLayout);
        }
        roundObjActivity.setCardsOnLayout(tempLayout);

    }


    /**
     * To Serializes the game state to a string
     * @return String representing the state of the game
     */
    String serialize(){
        StringBuilder output = new StringBuilder();
        output.append("Round: ").append(roundObjActivity.getRoundNumber()).append("\n");
        output.append("Computer: \n");
        output.append("\tScore: ").append(players[COMPUTER_INDEX].getTotalScore()).append("\n");
        output.append("\tHand: ").append(players[COMPUTER_INDEX].getCardsOnHand().toString()).append("\n");
        output.append("\tCapture Pile: ").append(players[COMPUTER_INDEX].getCardOnCapturePile().toString()).append("\n");
        output.append("\n");
        output.append("Human: \n");
        output.append("\tScore: ").append(players[HUMAN_INDEX].getTotalScore()).append("\n");
        output.append("\tHand: ").append(players[HUMAN_INDEX].getCardsOnHand().toString()).append("\n");
        output.append("\tCapture Pile: ").append(players[HUMAN_INDEX].getCardOnCapturePile().toString()).append("\n");
        output.append("\n");
        output.append("Layout: ").append(roundObjActivity.getCardsOnLayout().toString()).append("\n");
        output.append("\n");
        output.append("Stock Pile: ").append(roundObjActivity.getCardsOnStockPile().toString()).append("\n");
        output.append("\n");
        output.append("Next Player: ").append(roundObjActivity.getNextPlayerTurn()).append("\n");
        String str_output = output.toString();
        Log.d("Debug", str_output);
        return str_output;
    }

    /**
     * to set up the variables when user decides to play next round after completion of a round
     * @param newOrLoad , option chosen by player
     */
    public void setupNextSuccessiveRound(String newOrLoad){
        JSONObject obj= null;

        try {
            obj = new JSONObject(newOrLoad);
        }catch(JSONException e){

        }

        try {
            //set the round number here if it is next round for that need to pass the round number from result activity
            //set the total score upto now here
            players[HUMAN_INDEX].setTotalScore(Integer.parseInt(obj.getString("humanTotalScore")));
            players[COMPUTER_INDEX].setTotalScore(Integer.parseInt(obj.getString("computerTotalScore")));
            roundObjActivity.setRoundNumber(Integer.parseInt(obj.getString("roundNumber")) + 1);

            if(Integer.parseInt(obj.getString("humanRoundScore")) > Integer.parseInt(obj.getString("computerRoundScore"))){
                roundObjActivity.setNextPlayer(1);
            }else if(Integer.parseInt(obj.getString("humanRoundScore"))< Integer.parseInt(obj.getString("computerRoundScore"))){
                roundObjActivity.setNextPlayer(2);
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }


    /**
     * To update display of the round activity class.
     * @param display Activity class that implements the IDisplay interface.
     * @return True if the Game has completed and Round class needs to start GameEndActivity
     */
    boolean updateDisplay(RoundActivity display){

        if (display!=null){
            display.displayHumanHand(players[HUMAN_INDEX].getCardsOnHand());
            display.displayComputerHand(players[COMPUTER_INDEX].getCardsOnHand());
            display.displayStockPile(roundObjActivity.getCardsOnStockPile());
            display.displayLayoutPile(roundObjActivity.getCardsOnLayout());
            display.updateComputerScore(players[COMPUTER_INDEX].getTotalScore());
            display.updateHumanScore(players[HUMAN_INDEX].getTotalScore());
            display.updateRound(roundObjActivity.getRoundNumber());

        }

        return false;
    }

    /**
     * gets capture pile of human
     */
    Vector<Card> getCapturePileHuman(){
        return players[HUMAN_INDEX].getCardOnCapturePile();
    }


    /**
     * gets capture pile of computer
     */
    Vector<Card> getCapturePileComputer(){
        return players[COMPUTER_INDEX].getCardOnCapturePile();
    }

    /**
     * gets hand pile of human
     */
    Vector<Card> getHumanHand(){
        return players[HUMAN_INDEX].getCardsOnHand();
    }

    /**
     * gets hand pile of computer
     */
    Vector<Card> getComputerHand(){
        return players[COMPUTER_INDEX].getCardsOnHand();
    }



    /**
     * To pick the card picked by user and add it to either layout or
     * @param card Actual card picked by user
     */
    void pickCard(Card card){
        //repoService.throwCard( card);
        //get the card out of the hand of the player
        //players[HUMAN_INDEX].removeCard(Card);
        //make human play here
        players[HUMAN_INDEX].humanPickedCard(card);

        //make the human play after the human selects a card
        players[HUMAN_INDEX].play(roundObjActivity.getCardsOnLayout(), roundObjActivity.getCardsOnStockPile());
        System.out.println("*****************After Human Makes a play***************");
        roundObjActivity.printVectors();


    }

    /**
     * returns whose turn it is to play
     * @return int value of player to play next
     */
    public int whoseTurnFirst(){
        return roundObjActivity.getNextPlayer();
    }


    /**
     * to make the computer move
     */
    void computerMove(){
        players[COMPUTER_INDEX].play(roundObjActivity.getCardsOnLayout(),roundObjActivity.getCardsOnStockPile());
        roundObjActivity.printVectors();
    }


    /**
     * To checl the hand size of the player
     */
    boolean checkHandSizeForPlayers(){

        int humanHandSize = players[HUMAN_INDEX].getCardsOnHand().size();
        int computerHandSize = players[COMPUTER_INDEX].getCardsOnHand().size();
        int stockPileSize = roundObjActivity.getCardsOnStockPile().size();

        if((humanHandSize == 0 && computerHandSize == 0 ) || stockPileSize == 0){
            roundObjActivity.setIsRoundComplete(true);
            roundObjActivity.roundCompleteInfo();
            return true;
        }else{
            return false;
        }
    }

    /**
     * Executes help functionality and set the variables accordingly
     * @reutrn Vector of String that contains the suggestion provided by the help option
     */
    public Vector<String> executeHelpFunctionality(){

        Vector<String > vecString = new Vector<>();
        int nextPlayer = 0;

        //first we need to get the hand of human index, and capture pile of human index
        //set the helper computer player hand and capture pile to the hand and capture pile taken from human object

        //Also check if capture pile and cards on hand of human player is passed by referenceo
        //or passed by value. Make sure it is passed by value so any changes on helper object
        //won't end up effecting the human cards from the human player object

        players[nextPlayer].setCardOnCapturePile((Vector)players[HUMAN_INDEX].getCardOnCapturePile().clone());
        players[nextPlayer].setCardsOnHand((Vector)players[HUMAN_INDEX].getCardsOnHand().clone());



        Vector<Vector<Card>> cardsOnLayoutTempClone= new Vector<>();
        Vector<Vector<Card > > cardsOnLayoutTemp = roundObjActivity.getCardsOnLayout();

        for(int i=0;i<cardsOnLayoutTemp.size();i++){
            cardsOnLayoutTempClone.add((Vector)cardsOnLayoutTemp.elementAt(i).clone());
        }

        Vector<Card > cardsOnStockPileTemp = (Vector)roundObjActivity.getCardsOnStockPile().clone();

        //players[nextPlayer]->play(getCardsOnLayout(), getCardsOnStockPile());
        //passing by value so any changes won't affect the actual game
        players[nextPlayer].play(cardsOnLayoutTempClone, cardsOnStockPileTemp);

        //clear the hand and capture pile from the helper player
        players[nextPlayer].emptyCardsOnCapturePile();
        players[nextPlayer].emptyCardsOnHand();

        //Printing the state of the game after executing help function to check if the state of the game remains same or not
        System.out.println("AFTER EXECUTION OF HELP FUNCTION");
        roundObjActivity.printVectors();

        //then display the recommendation and clear the recommendation holder for next play
        players[nextPlayer].getHelperMessageHand();
        players[nextPlayer].getHelperMessageStock();

        vecString.add(players[nextPlayer].getHelperMessageHand());
        vecString.add(players[nextPlayer].getHelperMessageStock());

        players[nextPlayer].setHelperMessageHand("");
        players[nextPlayer].setHelperMessageStock("");

        //Display the recommendation

        //Empty the recommendation for next turn recommendation
        return vecString;


    }


    /**
     * To create a JSON object with the result of a round
     * @return  a json object with result of a round
     */
    public JSONObject createJsonObjectWithRoundResult(){

        JSONObject obj = new JSONObject();
        try {

            //adding
            obj.put("humanRoundScore",players[HUMAN_INDEX].calculateScore() );
            obj.put("humanTotalScore", players[HUMAN_INDEX].getTotalScore());
            obj.put("computerRoundScore",players[COMPUTER_INDEX].calculateScore());
            obj.put("computerTotalScore", players[COMPUTER_INDEX].getTotalScore());
            obj.put("roundNumber", roundObjActivity.getRoundNumber());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }


}
