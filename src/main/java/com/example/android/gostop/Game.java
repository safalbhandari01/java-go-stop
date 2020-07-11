/*
 ************************************************************
 * Name: Safal Bhandari                                     *
 * Project: Java Go Stop                                     *
 * Class: OPL Spring 20                                     *
 * Date: 04/28/2020                                         *
 ************************************************************
 */
package com.example.android.gostop;
import java.util.Scanner;

public class Game {

    private Round roundObj;
    /**
     * Constructor for game class
     */
    public Game(){

    }

    /**
     * to start the game
     * @param beginGame, contains string value that contains either new or load message
     */
    public void startGame(String beginGame){

        int newGameOrLoadExistingGame;
        int currentRound = 0;

        if(beginGame.equals("new")){
            //round is first if it is a new game
             currentRound = 0;
            newGameOrLoadExistingGame = 1;
        }else{

            newGameOrLoadExistingGame = 2;
        }
        //create player objects and pass it to  round object
        Player humanPlayer = new human("Human");
        Player computerPlayer = new computer("Computer");



        //when a round is complete ask user here if he wants to play another round. If he does
        //then create new round and continue rounds until user wants to exit the game
        int newOrExit;

        //holds the next player whose turn is to play next
        //initialized to -1 which is checked in round
        //if -1 determine first player function is executed in Round class object which determines the first player.
        //This happens the first time round object is created
        //for every other round index of winner of the last round is passed as the next player turn.
        //if draw -1 is passed
        int nextPlayerTurn = -1;


           // if (newGameOrLoadExistingGame == 1)
           // {
                System.out.println("Start new game here");
                //start new game
                //incrementing round value to pass which round it is
                currentRound= currentRound + 1;

                //Here create a round object and start the round
                roundObj = new Round(humanPlayer, computerPlayer, currentRound, nextPlayerTurn);


                //Here get the winner of the round and set it to nextPlayerTurn in case the plyaer wants to play another round

           // }
           // else
           // {
                System.out.println("Load and continue the game here");
                //LOAD AN EXISTING GAME FROM SERIALIZED FILE

          //  } //end of if else statement

    }


    /**
     * to get the round object
     * @return roundObject, contains reference to the round object of the game
     */
    public Round getRoundObj(){
        return this.roundObj;
    }

}
