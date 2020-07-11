/*
 ************************************************************
 * Name: Safal Bhandari                                     *
 * Project: Java Go Stop                                     *
 * Class: OPL Spring 20                                     *
 * Date: 04/28/2020                                         *
 ************************************************************
 */

package com.example.android.gostop;

public class Card {

    private String face;
    private String suit;

    /**
     * Constructor for card class
     */
    public Card(){

    }

    /**
     * Overloading constructor for card class
     */
    public Card(String inputFace, String inputSuit)
    {
        face = inputFace;
        suit = inputSuit;
    }

    /**
     * get the face value of card
     * @return face, a string representation of face value of card
     */
    public String getFace() {
        return face;
    }

    /**
     * get the suit value of card
     * @return suit, a string representation of suit value of card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * function that sets the face value of a card
     * @param inputFace a string value
     */
    public void setFace(String inputFace){
        face = inputFace;
    }

    /**
     * function that sets the suit value of a card
     * @param inputSuit a string value
     */
    public void setSuit(String inputSuit){
        suit = inputSuit;
    }

    /**
     * function that returns the suitAndFace value combined
     * @return  string value that is suitAndFace string
     */
    public String suitAndFace(){

        String returnVal = this.suit + this.face;
        return returnVal;
    }

    /**
     * function that returns integer value of face card
     * @return int value of face card
     */
    public int getFaceAsInteger()
    {
        if (face == "X" || face == "x")
        {
            return 10;
        }
        else if (face == "J" || face == "j")
        {
            return 11;
        }
        else if (face == "Q" || face == "q")
        {
            return 12;
        }
        else if (face == "K" || face == "k")
        {
            return 13;
        }
        else if (face == "A" || face == "a")
        {
            return 1;
        }
        else
        {
            return Integer.parseInt(face);
        }
    }
}



