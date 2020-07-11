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
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;


public class serializer {
    private Context mContext;

    int roundNo;
    int computerScore;
    int humanScore;
    Vector <Card> computerHand = new Vector<>();
    Vector<Card> humanHand = new Vector<>();
    Vector<Card> capturePileHuman = new Vector<>();
    Vector<Card>capturePileComputer = new Vector<>();
    Vector<Card> layout = new Vector<>();
    Vector<Card> stockPile = new Vector<>();
    String nextPlayer = "";

    /**
     * Constructor for serializer class
     * @param context, context passed
     */
    public serializer(Context context) {
        this.mContext = context;
    }

    /**
     * to read data from a text file
     */
    public void read() throws IOException {
        AssetManager am = mContext.getAssets();


        boolean scoreForComputer = true;


        boolean handForComputer = true;

        boolean readComputerCapturePile = false;

        try {
            InputStream is = am.open("case.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.contains("Round")) {
                    String numberString = "";
                    for (int i = 7; i < line.length(); i++) {
                        numberString = numberString + line.charAt(i);
                    }
                    roundNo = Integer.parseInt(numberString);
                }


                if (line.contains("Score")) {
                    if (scoreForComputer) {
                        String compScore = "";
                        for (int i = 10; i < line.length(); i++) {
                            compScore = compScore + line.charAt(i);
                        }
                        computerScore = Integer.parseInt(compScore);
                        scoreForComputer = false;
                    }
                    else {
                        String humScore = "";
                        for (int i = 10; i < line.length(); i++) {
                            humScore = humScore + line.charAt(i);
                        }
                        humanScore = Integer.parseInt(humScore);
                    }
                }


                if (line.contains("Hand")) {
                    if (handForComputer) {
                        for (int i = 9; i < line.length(); i++) {
                            if (line.charAt(i) != ' ') {
                                String rank = Character.toString(line.charAt(i));
                                String suit = Character.toString(line.charAt(++i));
                                computerHand.add(new Card(rank, suit));
                            }
                        }
                        handForComputer = false;
                    }
                    else {
                        for (int i = 9; i < line.length(); i++) {
                            if (line.charAt(i) != ' ') {
                                String rank = Character.toString(line.charAt(i));
                                String suit = Character.toString(line.charAt(++i));
                                humanHand.add(new Card(rank, suit));
                            }
                        }
                    }
                }


                if (line.contains("Capture"))
                {

                    if (readComputerCapturePile == false)
                    {

                        //iterating without using iterator
                        for (int i = 17; i < line.length(); i++)
                        {
                            if (line.charAt(i) != ' ')
                            {
                                String rank = Character.toString(line.charAt(i));
                                String suit = Character.toString(line.charAt(++i));
                               capturePileComputer.add(new Card(rank, suit));
                            }
                        }

                        readComputerCapturePile = true;
                    }
                    else
                    {

                        for (int i = 17; i < line.length(); i++)
                        {
                            if (line.charAt(i) != ' ')
                            {
                                String rank = Character.toString(line.charAt(i));
                                String suit = Character.toString(line.charAt(++i));
                                capturePileHuman.add(new Card(rank, suit));
                            }
                        }
                    }
                }


                if (line.contains("Layout")) {
                    for (int i = 8; i < line.length(); i++) {
                        if (line.charAt(i) != ' ') {
                            String rank = Character.toString(line.charAt(i));
                            String suit = Character.toString(line.charAt(++i));
                            layout.add(new Card(rank, suit));
                        }
                    }
                }


                if (line.contains("Stock")) {
                    for (int i = 12; i < line.length(); i++) {
                        if (line.charAt(i) != ' ') {
                            String rank = Character.toString(line.charAt(i));
                            String suit = Character.toString(line.charAt(++i));
                            stockPile.add(new Card(rank, suit));
                        }
                    }
                }




                if (line.contains("Next")) {
                    for (int i = 13; i < line.length(); i++) {
                        nextPlayer = nextPlayer + line.charAt(i);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}