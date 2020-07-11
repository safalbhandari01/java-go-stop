/*
 ************************************************************
 * Name: Safal Bhandari                                     *
 * Project: Java Go Stop                                     *
 * Class: OPL Spring 20                                     *
 * Date: 04/28/2020                                         *
 ************************************************************
 */
package com.example.android.gostop;
import java.util.*;


public class Deck {

    private Vector<Card> deckOfCards = new Vector<>();

    /**
     * Constructor for deck class
     */
    public Deck(){
        //filling the deck object with 52 cards
        String faces[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K" };
        String suits[] = { "C", "D", "H", "S" };

        int sizeFaces = faces.length;
        System.out.println(sizeFaces);
        int sizeSuits = suits.length;
        System.out.println(sizeSuits);

        //Adding 52 cards to the vector
        for (int i = 0; i < sizeFaces; i++)
        {

            for (int j = 0; j < sizeSuits; j++)
            {
                //first values are pushed at the end of the stack
                deckOfCards.add(new Card(faces[i], suits[j]));
            }
        }
        System.out.println(deckOfCards.size());
        shuffleDeck();
    }


    /**
     * Constructor for controller class
     * @return deckOfCard, of type Vector of Cards
     */
    public Vector<Card> getDeck()
    {
        return deckOfCards;
    }


    /**
     * function to shuffle the deck of cards
     */
    public void shuffleDeck()
    {
        //shuffle deck of card here
        Collections.shuffle(deckOfCards);

        //printing after shuffling deck of cards for checking purpose
        for(int i=0; i<deckOfCards.size();i++){
            System.out.println(deckOfCards.elementAt(i).getFace()+ " "+ deckOfCards.elementAt(i).getSuit());
        }
    }

}
