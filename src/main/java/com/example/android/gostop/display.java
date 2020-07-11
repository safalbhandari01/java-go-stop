/*
 ************************************************************
 * Name: Safal Bhandari                                     *
 * Project: Java Go Stop                                     *
 * Class: OPL Spring 20                                     *
 * Date: 04/28/2020                                         *
 ************************************************************
 */
package com.example.android.gostop;

import java.util.Vector;

/**
 * Interface class to be implemented by activity to update displays.
 */
public interface display {

    /**
     * To update human hand
     * @param hand Collection of cards in human player's hand
     */
    void displayHumanHand(Vector<Card> hand);

    /**
     * To update computer hand
     * @param hand Collection of cards in computer player's hand
     */
    void displayComputerHand(Vector<Card> hand);

    /**
     * To update discard pile
     * @param hand Collection of cards in discard pile
     */
    void displayStockPile(Vector<Card> hand);

    /**
     * To update draw pile
     * @param hand Collection of cards in draw pile
     */
    void displayLayoutPile(Vector<Vector<Card>> hand);

    /**
     * To update round
     * @param num Number representing the round
     */
    void updateRound(int num);

    /**
     * To update cumulative computer score
     * @param num Number representing computer score
     */
    void updateComputerScore(int num);

    /**
     * To update human score
     * @param num Number representing human score
     */
    void updateHumanScore(int num);
}

