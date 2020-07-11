/*
 ************************************************************
 * Name: Safal Bhandari                                     *
 * Project: Java Go Stop                                     *
 * Class: OPL Spring 20                                     *
 * Date: 04/28/2020                                         *
 ************************************************************
 */
package com.example.android.gostop;

import android.util.Log;
import java.util.Vector;

public class human extends Player {

    Card pickedCard;
    int stackedPairIndex = -1;

    /**
     * constructor for the human class
     * @param name, name of the human player
     */
    public human(String name)
    {
        super(name);
    }


    /** function that implement strategy of play for human player
     * @param cardsOnLayout, vector of vector of pointer to cards which is in the layout
     * @param cardsOnStockPile, vector of pointer to cards which is in the stockpile
     * Algorithm:
     *                   calls playCardFromHand function which is an implementation of players move
     *                   from their hand. This function implements the strategy for playing card from
     *                   hand and returns number of card matched on the layout with the card chosen by
     *                   the player from hand. After playCardFromHand playCardFromStockPile function is
     *                   called that implements the strategy for the game when card is drawn from stock
     *                   pile in the parent class Player
     * Assistance Received: None
     */
    @Override
    public void play(Vector<Vector<Card>> cardsOnLayout, Vector<Card> cardsOnStockPile){

        System.out.println("HUMAN MOVE");
        //play card from hand
        int numMatchedCards = playCardFromHand(cardsOnLayout);

        //checking
        System.out.println("Stacked Pair Index: "+ stackedPairIndex);

        //play card from stock pile
        playCardFromStockPile(cardsOnLayout, cardsOnStockPile, numMatchedCards, stackedPairIndex);
    }


    /**
     * sets the card picked by the human
     * @param card, card picked by the human
     */
    public void humanPickedCard(Card card){
       // Log.d("HUMAN CLASS", " HUMAN PICKED CARD FUNCTION REACHED");
        pickedCard = card;
    }


    /**
     * matches the chosen card and returns the index of the card chosen by the human
     * @return chosenCardIndex, an int value of the index of the card chosen in the human hand
     */
    public int chooseCardFromHand(){
       // Log.d("HUMAN CLASS", " CHOOSE CARD FROM HAND FUNCTION REACHED");

        int playedCardIndex=0;

        for (int i = 0; i < cardsOnHand.size(); i++)
        {
            //System.out.println(i + "-" + cardsOnHand.elementAt(i).getFace() + cardsOnHand.elementAt(i).getSuit() + " ");
            if(pickedCard.getFace().equals(cardsOnHand.elementAt(i).getFace()) && pickedCard.getSuit().equals(cardsOnHand.elementAt(i).getSuit())){
                playedCardIndex = i;
                System.out.println("HUMAN.JAVA The played card index by human is "+ playedCardIndex);
                break;
            }
        }


        return playedCardIndex;
    }

    /** To implement strategy to play for hand for human player
     * @param cardsOnLayout, vector of vector of pointer to cards which is in the layout
     * @return numCardsMatchedOnLayout, int value of number of card matched on layout with the
     *                   card chosen by human player from the hand
     * Local Variables:
     *                   cardChosen, pointer to card holds the card chosen by human from hand
     *                   faceCardChosen, string value of the face value of the card chosen
     *                   numCardsMatchedOnLayout, int value of the num cards matched on layout
     *                   playedCard, pointer to card of the card played
     *                   tripleStackIndexReturn, int value of the stack of the triple stack if there is one
     *                   faceValue, string value of the face value of the card
     *                   numCardsMatched, int value of the number of cards matched
     *                   matches, vector of int that contains index of the card with same face value as picked
     *                   by the human player from the hand
     *                   optionChosen, int value of the option chosen by the human from the matches

     * Algorithm:
     *                   1.checks num of cards matched on layout by calling checkCardOnLayout
     *                   2.then check if there is a triple stack present and if the card chosen
     *                    matches with the triple stack if yes, capture the cards and return
     *                   3. if no for number, and if number of cards matched is:
     *                   0: no card in the layout, the card is added to the layout.
     *                   1: one card in the layout, the player creates a stack pair of the two cards and leaves it in the layout.
     *                   2: two cards in the layout, the player picks one of the two cards and creates a stack pair with it and
     *                   the card played from the hand, leaving the stack pair in the layout.
     *                   3: three cards in the layout, the player captures all four cards, i.e., adds them to their capture pile.2,
     *
     * Assistance Received: None
     */
    public int playCardFromHand(Vector<Vector<Card>> cardsOnLayout){

        System.out.println("HAND PILE MOVE");
        int playedCardIndex = chooseCardFromHand();

        //get string value of facecard chosen and pass it to check card on layout
        Card cardChosen = pickedCard;
        String faceCardChosen = cardChosen.getFace();

        //get number of cards matched
        int numCardsMatchedOnLayout = checkCardOnLayout(cardsOnLayout,faceCardChosen);

        //if cards matched on layout is greater than 3 then it is given the value to 3 because we handle only 3 cards matching at the most in this game.
        if(numCardsMatchedOnLayout>3){
            numCardsMatchedOnLayout = 3;
        }

        //take the card out from the vector and make a move
        //MIGHT HAVE TO CHANGE THIS IF THERE IS AN ERROR******************************
        Card playedCard = new Card(cardsOnHand.elementAt(playedCardIndex).getFace(), cardsOnHand.elementAt(playedCardIndex).getSuit());
        //MIGHT HAVE TO CHANGE THIS IF THERE IS AN ERROR******************************


        System.out.println("\tCard Chosen From Hand: " +playedCard.getFace() + playedCard.getSuit() +"\n");

        //check for triple stack and assigns triple stack index to tripleStackIndexReturn
        int tripleStackIndexReturn = findIndexMatchedTripleStack(cardsOnLayout, playedCard.getFace());
        if (tripleStackIndexReturn != -1)
        {
            //capture triple stack and played card if triple stack is present and there is a card on hand with
            //same face value of triple stack present
            //Pushing the stacked pair to the capture pile
            for (int i = 0; i < 3; i++)
            {
                Card capturedCard = new Card(cardsOnLayout.elementAt(tripleStackIndexReturn).elementAt(i).getFace(),cardsOnLayout.elementAt(tripleStackIndexReturn).elementAt(i).getSuit());
                this.cardsOnCapturePile.add(capturedCard);
            }

            //remove the triple stack from layout
            cardsOnLayout.remove(tripleStackIndexReturn);

            //push the face card to the capture pile
            this.cardsOnCapturePile.add(playedCard);

            //print triple stack captured
            System.out.println("Just captured triple stack \n");

            //number of cards matched on layout set to 3 because triple stack is captured
            numCardsMatchedOnLayout = 3;

            //remove the card from the hand
            cardsOnHand.remove(playedCardIndex);

            return numCardsMatchedOnLayout;
        }

        //inner vector to add the card to incase if needed to add to the layout
        //could not  initialize the inner vector inside each cases because of restriction placed by C++
        //so declared here and pushbacked in the cases
        String faceValue;
        int numErasedCards;

        Vector<Card > innerVector= new Vector<>();
        //passed by reference to get the num of cards matched from get Index of Matched Card On Layout function
        int numCardsMatched = 0;
        Vector<Integer> matches;
        Vector<Card > capturePileVector;
        int optionChosen;


        //FOR CARD PLAYED FROM THE HAND STARTS ************************
        //make a move depending on the number of cards matched
        switch (numCardsMatchedOnLayout)
        {
            case 0: //if 0 cards matched add the card to the layout
                System.out.println("Case 0");
                innerVector.add(playedCard);
                cardsOnLayout.add(innerVector);
                break;
            case 1: //get the index of the matched card from layout
                //add to the end of the vector in the matched index
                System.out.println("Case 1");
                matches = getIndexOfMatchedCardOnLayout(cardsOnLayout, faceCardChosen, numCardsMatched);
                //optionChosen = chooseIndexOfMatchedCardOnLayout(matches);
                //only one card matched so get the first index of matches vector which is 0
                optionChosen = 0;
                cardsOnLayout.elementAt(matches.elementAt(optionChosen)).add(playedCard);
                //setting the stackedpairvalue
                stackedPairIndex = matches.elementAt(optionChosen);

                break;
            case 2: //SAME AS CASE 1. HERE THE HUMAN HAS THE OPTION TO CHOSE FROM 2 possible cards
                System.out.println("Case 2");
                matches = getIndexOfMatchedCardOnLayout(cardsOnLayout, faceCardChosen, numCardsMatched);
                optionChosen = chooseIndexOfMatchedCardOnLayout(matches);
                cardsOnLayout.elementAt(matches.elementAt(optionChosen)).add(playedCard);
                //setting the stackedpairvalue
                stackedPairIndex = matches.elementAt(optionChosen);
                //print the cards on layout on stack format
                //printLayoutStackFormat(cardsOnLayout);

                break;

            case 3:
                matches = getIndexOfMatchedCardOnLayout(cardsOnLayout, faceCardChosen, numCardsMatched);
                // matches = getIndexOfMatchedCardOnLayout(cardsOnLayout, playedCardIndex, numCardsMatched);
                //cout<<"\tThe size of the vector of matches is "<<matches.size()<<endl;
               System.out.println("\tYou just captured 3 cards from layout and one played by you\n");

                //add played card to the capture pile
                cardsOnCapturePile.add(playedCard);

                //create three deep copies of cards to push on the capture pile
                for (int i = 0; i < 3; i++)
                {
                    //capture first three cards from the vector and create a deep copy and add it to capture pile of human
                    Card capturedCard = new Card(cardsOnLayout.elementAt(matches.elementAt(i)).elementAt(0).getFace(),cardsOnLayout.elementAt(matches.elementAt(i)).elementAt(0).getSuit());
                    this.cardsOnCapturePile.add(capturedCard);
                }
                //erase the first three cards from the vector after capturing it
                numErasedCards = 0;
                //card with face value to delete
                faceValue = playedCard.getFace();

                //Deleting cards from layout one at a  time
                //Deleting all similar card will cause the vector to collapse or go out of range
                //so first face value match taken index and then break out from the loop and delete the card on that index
                //then get inside loop and find another face value and break out and delete the card and so on...
                removeCardsFromLayout(cardsOnLayout, faceValue, 3);

                //printing the capture pile after capturing the cards
                printCapturePileCards(getCardOnCapturePile());
                break;
        }

        //remove the card from the hand
        cardsOnHand.remove(playedCardIndex);

        Log.d("HUMAN CLASS", " PLAY FROM HAND END OF FUNCTION BEFORE RETURN STATEMENT REACHED");

        return numCardsMatchedOnLayout;
    }


}
