/*
 ************************************************************
 * Name: Safal Bhandari                                     *
 * Project: Java Go Stop                                     *
 * Class: OPL Spring 20                                     *
 * Date: 04/28/2020                                         *
 ************************************************************
 */
package com.example.android.gostop;
import java.util.Arrays;
import java.util.Vector;
import java.util.HashMap;


public class Round {

    //constant variable declarations
    final int HUMAN_HELPER_BOT = 0;
    final int HUMAN_INDEX = 1;
    final int COMPUTER_INDEX = 2;

    //variable declarations
    private int roundNumber;
    private int roundWinnerIndex = -1;
    private boolean isRoundComplete = false;
    private int nextPlayer;
    private boolean newGame;
    private String nextPlayerTurn;
    private Vector<Card> cardDeck = new Vector<>();
    private Vector<Vector<Card>> cardOnLayout = new Vector<>();
    private Vector<Card> cardOnStockPile= new Vector<>();
    private Player[] players = new Player[3];




    /**
     * Constructor for Round class
     */
    public Round(Player humanPlayer, Player computerPlayer, int currentRound, int nextPlayerToPlay ){
        this.roundNumber = currentRound;
        this.nextPlayerTurn = "";
        this.newGame = true;
        players[HUMAN_HELPER_BOT] = new computer("BOT_HELPER");
        players[HUMAN_HELPER_BOT].setHelperBot(true);
        players[HUMAN_INDEX] = humanPlayer;
        players[COMPUTER_INDEX] = computerPlayer;

        //setting up round
        roundSetup();

        if(nextPlayerToPlay == -1){
            setUpFirstPlayer();
        }else if(nextPlayerToPlay == HUMAN_INDEX){
            this.nextPlayer = HUMAN_INDEX;
        }else{
            this.nextPlayer = COMPUTER_INDEX;
        }

    }

    /**
     * Overloading Constructor for player class to load the state of previously
     * played game round from a file
     */
    //Overloading constructor for Round class to load the state of previously played incomplete round from a file
    public Round(Player humanPlayer, int humanScore, Vector<Card> humanCardHand, Vector<Card> humanCardCapturePile,
                 Player computerPlayer,int computerScore,Vector<Card> computerCardHand, Vector<Card > computerCardCapturePile, int currentRound, int nextPlayer,
                 Vector<Vector<Card>> cardsOnLayoutPassed,Vector<Card> cardsOnStockPilePassed, String nextPlayerTurn){
        this.roundNumber = currentRound;
        this.nextPlayerTurn = nextPlayerTurn;
        this.nextPlayer = nextPlayer;
        this.newGame = true;

        players[HUMAN_HELPER_BOT] = new computer("BOT_HELPER");
        players[HUMAN_HELPER_BOT].setHelperBot(true);

        players[HUMAN_INDEX] = humanPlayer;
        players[HUMAN_INDEX].setCardsOnHand(humanCardHand);
        players[HUMAN_INDEX].setCardOnCapturePile(humanCardCapturePile);
        players[HUMAN_INDEX].setTotalScore(humanScore);
        players[COMPUTER_INDEX] = computerPlayer;
        players[COMPUTER_INDEX].setCardsOnHand(computerCardHand);
        players[COMPUTER_INDEX].setCardOnCapturePile(computerCardCapturePile);
        players[COMPUTER_INDEX].setTotalScore(computerScore);

        this.cardOnLayout = cardsOnLayoutPassed;
        this.cardOnStockPile = cardsOnStockPilePassed;

    }

    /**
     * return the player array
     * @return players, array of players
     */
    public Player[] getPlayers(){
        return this.players;
    }

    /**
     * get the next player turn
     * @return nextPlayerTurn
     */
    public String getNextPlayerTurn(){
        return this.nextPlayerTurn;
    }

    /**
     * set the next player turn
     * @param turn, player whose turn is to play next
     */
    public void setNextPlayerTurn(String turn){
        this.nextPlayerTurn = turn;
    }

    /**
     * get the next player to play
     * @return nextPlayer, int value of whose turn is to play next
     */
    public int getNextPlayer(){
        return this.nextPlayer;
    }

    /**
     * sex next player using index
     * @param playerIndexPassed, index of next player to play
     */
    public void setNextPlayer(int playerIndexPassed){
        this.nextPlayer = playerIndexPassed;
    }

    /**
     * clear all cards from deck
     */
    public void clearCardsFromDeck(){
        this.cardDeck.clear();
    }

    /**
     * clear all cards from layout
     */
    public void clearCardsFromLayout(){
        this.cardOnLayout.clear();
    }

    /**
     * clear all cards from stock pile
     */
    public void clearCardsFromStockPile(){
        this.cardOnStockPile.clear();
    }

    /**
     * set roundNumber
     * @param roundNumberPassed
     */
    public void setRoundNumber(int roundNumberPassed){
        this.roundNumber = roundNumberPassed;
    }

    /**
     * get round number
     * @return this.roundNumber
     */
    public int getRoundNumber(){

        return this.roundNumber;
    }

    /**
     * get round winner Index
     * @return roundWinnerIndex
     */
    public int getRoundWinnerIndex(){

        return this.roundWinnerIndex;
    }

    /**
     * set round Winner Index
     * @param index
     */
    public void setRoundWinnerIndex(int index){
        this.roundWinnerIndex = index;
    }

    /**
     * gets isRoundComplete
     * @return isRoundComplete, a boolean value
     */
    public boolean getIsRoundComplete(){

        return isRoundComplete;
    }

    /**
     * set isRoundComplete
     * @param trueOrFalse, a boolean value
     */
    public void setIsRoundComplete(boolean trueOrFalse){
        this.isRoundComplete = trueOrFalse;
    }

    /**
     * getter for cardsOnLayout
     * @return cardOnLayout
     */
    public Vector<Vector<Card > > getCardsOnLayout(){
        return this.cardOnLayout;
    }

    /**
     * setter for cardsOnLayout
     * @param cardsOnLayoutPassed
     */
    public void setCardsOnLayout(Vector<Vector<Card> > cardsOnLayoutPassed){
        this.cardOnLayout = cardsOnLayoutPassed;
    }

    /**
     * getter for cardOnStockPile
     * @return cardOnStockPile
     */
    public Vector<Card > getCardsOnStockPile(){
        return cardOnStockPile;
    }

    /**
     * setter for cardOnStockPile
     * @param cardsOnStockPilePassed
     */
    public void setCardsOnStockPile(Vector<Card> cardsOnStockPilePassed){
        this.cardOnStockPile = cardsOnStockPilePassed;
    }


    /**
     * sets up the round before the round begins
     */
    public void roundSetup(){
        //create a deck object of cards of 104 cards and shuffle the deck
        Deck deckObj = new Deck();
        deckObj.shuffleDeck();

        //another deck object
        Deck deckObj2 = new Deck();
        deckObj2.shuffleDeck();

        Vector<Card> tempVect = deckObj.getDeck();
        Vector<Card> tempVect2 = deckObj2.getDeck();

        //adding two decks and assigning it to cardeck

        System.out.println("CHECK before add all");

        this.cardDeck.addAll(deckObj.getDeck());
        this.cardDeck.addAll(deckObj2.getDeck());

        //print the shuffled deck i.e, the contents of cardDeck
        for(int i=0; i<this.cardDeck.size();i++){
           // System.out.println(this.cardDeck.elementAt(i).getFace()+ this.cardDeck.elementAt(i).getSuit());
            if(i==50){
                System.out.println();
            }
        }
        //printing the size of the shuffled deck
        System.out.println("The size of the shuffled deck is"+ this.cardDeck.size());

        //function that deals all the cards to respective player, computer, layout and stock pile
        dealAllCards();

        //printing the game table after setup and before beginning turns
        printVectors();

    }

    /**
     * deals card to player,computer, layout, and stock pile
     */
    public void dealAllCards(){
        System.out.println("CHECK1");
        int i=0;
        while(this.cardDeck.size()!=0){
            if (i <= 4)
            {
                players[HUMAN_INDEX].addCardOnHand(cardDeck.firstElement());
            }
            else if (i > 4 && i <= 9)
            {
                players[COMPUTER_INDEX].addCardOnHand(cardDeck.firstElement());
            }
            else if (i > 9 && i <= 13)
            {
                Vector<Card > innerVector= new Vector<>();
                innerVector.add(cardDeck.firstElement());
                cardOnLayout.add(innerVector);
            }
            else if (i > 13 && i <= 18)
            {
                players[HUMAN_INDEX].addCardOnHand(cardDeck.firstElement());
            }
            else if (i > 18 && i <= 23)
            {
                players[COMPUTER_INDEX].addCardOnHand(cardDeck.firstElement());
            }
            else if (i > 23 && i <= 27)
            {
                Vector<Card > innerVector= new Vector<>();
                innerVector.add(cardDeck.firstElement());
                cardOnLayout.add(innerVector);
            }
            else
            {
                cardOnStockPile.add(cardDeck.firstElement());
            }

            //remove the first element from the vector every time you add a card

            //CHECK FOR ERROR LATER HERE
            this.cardDeck.remove(0);

            i++;
        }
    }


    /**
     * prints all the vectors and necessary information on the console for display
     */
    public void printVectors(){
        System.out.println();
        System.out.println("************************ CURRENT GAME STATE ***********************\n");

        //Printing the round number
        System.out.println("\n Current Round: " + this.roundNumber);

        //HUMAN INFORMATION DISPLAY
        //Printing the human hand
        Vector<Card> vectorHumanHand = players[HUMAN_INDEX].getCardsOnHand();
        System.out.println("\n Human Hand \t Size:" + vectorHumanHand.size()+"\t");

        for (int i = 0; i < vectorHumanHand.size(); i++)
        {
            System.out.print(i + "-" + vectorHumanHand.elementAt(i).getFace() + vectorHumanHand.elementAt(i).getSuit() + " ");
        }

        //Printing the human capture pile
        Vector<Card> capturePileHuman = players[HUMAN_INDEX].getCardOnCapturePile();

        //sort the human capture pile here later
        //**************************************

        System.out.println();
        System.out.println("Capture Pile \t Size:" + capturePileHuman.size()+ " \t");

        for (int i = 0; i < capturePileHuman.size(); i++)
        {
            System.out.print(i + "-" + capturePileHuman.elementAt(i).getFace() + capturePileHuman.elementAt(i).getSuit() + " ");
        }

        System.out.println("Score: "+ players[HUMAN_INDEX].getTotalScore());

        //COMPUTER INFORMATION DISPLAY
        //Print the computer hand
        Vector<Card> vectorComputerHand = players[COMPUTER_INDEX].getCardsOnHand();
        System.out.println("\n Computer Hand \t Size:" + vectorHumanHand.size()+"\t");

        for (int i = 0; i < vectorComputerHand.size(); i++)
        {
            System.out.print(i + "-" + vectorComputerHand.elementAt(i).getFace() + vectorComputerHand.elementAt(i).getSuit() + " ");
        }

        System.out.println();
        //Print the computer capture pile
        Vector<Card> capturePileComputer = players[COMPUTER_INDEX].getCardOnCapturePile();

        //sort the computer capture pile here later
        //**************************************

        System.out.println("Capture Pile \t Size:" + capturePileComputer.size()+ " \t");

        for (int i = 0; i < capturePileComputer.size(); i++)
        {
            System.out.print(i + "-" + capturePileComputer.elementAt(i).getFace() + capturePileComputer.elementAt(i).getSuit() + " ");
        }
        System.out.println();

        System.out.println("Score: "+ players[COMPUTER_INDEX].getTotalScore());


        //Printing the layout
        int k=0;
        System.out.println("\n Layout Hand  \t Size:" + cardOnLayout.size()+ " \t");
        for (Vector<Card > i : cardOnLayout)
        {
            System.out.print(k + "-");
            if (i.size() > 1)
            {
                // for (Card *j : i)
                // {

                //     cout << j->getFace() << j->getSuit() << "-";
                // }

                for (int j = 0; j < i.size(); j++)
                {
                    if (j == i.size() - 1)
                    {
                        System.out.print(i.elementAt(j).getFace() + i.elementAt(j).getSuit());

                    }
                    else
                    {
                        System.out.print(i.elementAt(j).getFace()+ i.elementAt(j).getSuit()+ "-");

                    }
                }
            }
            else
            {
                for (Card j : i)
                {
                    System.out.print(j.getFace()+ j.getSuit()+ " ");
                }
            }

            System.out.print("\t");
            k++;
        }
        System.out.println();

        //Printing the stock pile from front to make it readable on the screen
        System.out.println("\n\t Stock Pile \t Size:" + cardOnStockPile.size()+ " \t");

        for( int i = cardOnStockPile.size()-1; i>=0; i--){
            System.out.print(cardOnStockPile.elementAt(i).getFace() + cardOnStockPile.elementAt(i).getSuit() + " ");
            if(i==40){
                System.out.println("\t");
            }
        }
        System.out.println();
        System.out.println("************************ CURRENT GAME STATE ***********************\n");

    }


    /**
     * determine first player turn-- returns HUMAN_INDEX OR COMPUTER_INDEX
     * @return firstPlayerIndex
     */
    public int determineFirstPlayer(){
        HashMap<String, Integer> cardFrequencyMapperHuman = new HashMap<String, Integer>();
        HashMap<String, Integer> cardFrequencyMapperComputer = new HashMap<String, Integer>();

        Vector<Card> humanHand = players[HUMAN_INDEX].getCardsOnHand();
        Vector<Card> computerHand = players[COMPUTER_INDEX].getCardsOnHand();

        //Iterate over human card on hand and store the frequency of occurences of the card face value
        for(int i=0; i<humanHand.size(); i++){
            if(cardFrequencyMapperHuman.containsKey(humanHand.elementAt(i).getFace())){
                cardFrequencyMapperHuman.put(humanHand.elementAt(i).getFace(),cardFrequencyMapperHuman.get(humanHand.elementAt(i).getFace())+1);
            }else{
                cardFrequencyMapperHuman.put(humanHand.elementAt(i).getFace(), 1);
            }
        }

        //Iterate over computer card on hand and store the frequency of occurences of the card face value
        for(int i=0; i<computerHand.size(); i++){
            if(cardFrequencyMapperComputer.containsKey(computerHand.elementAt(i).getFace())){
                cardFrequencyMapperComputer.put(computerHand.elementAt(i).getFace(),cardFrequencyMapperComputer.get(computerHand.elementAt(i).getFace())+1);
            }else{
                cardFrequencyMapperComputer.put(computerHand.elementAt(i).getFace(), 1);
            }
        }

        System.out.println("Computer.asList(map)");
        System.out.println(Arrays.asList(cardFrequencyMapperComputer));

        System.out.println("Human.asList(map)");
        System.out.println(Arrays.asList(cardFrequencyMapperHuman));

        String faces[] = {"K", "Q", "J", "X", "9", "8", "7", "6", "5", "4", "3", "2", "A"};
        int sizeFaces = faces.length;

        int countHuman = 0;
        int countComputer = 0;

        for (int i = 0; i < sizeFaces; i++)
        {
            //map contains the element
            if (cardFrequencyMapperHuman.containsKey(faces[i]))
            {
                countHuman = cardFrequencyMapperHuman.get(faces[i]);
            }
            //map does not contain the element
            else
            {
                countHuman = 0;
            }

            if (cardFrequencyMapperComputer.containsKey(faces[i]))
            {
                countComputer = cardFrequencyMapperComputer.get(faces[i]);
            }
            else
            {
                countComputer = 0;
            }

            if (countHuman > countComputer)
            {
                return HUMAN_INDEX;
            }
            if (countHuman < countComputer)
            {
                return COMPUTER_INDEX;
            }
        }


        //change later
        return Integer.MAX_VALUE;
    }

    /**
     * displays scores and other info when round is complete and updates the roundWinnerIndex
     */
    public void roundCompleteInfo(){
        int humanScore = players[HUMAN_INDEX].calculateScore();
        int computerScore = players[COMPUTER_INDEX].calculateScore();

        //set the winner index to whoever won the round
        if(humanScore>computerScore){
            this.roundWinnerIndex = HUMAN_INDEX;
        }else if(humanScore<computerScore){
            this.roundWinnerIndex = COMPUTER_INDEX;
        }else{
            this.roundWinnerIndex = -1;
        }

        //also set the total score of the players
        int humanUpdatedTotalScore = players[HUMAN_INDEX].getTotalScore()+ humanScore;
        int computerUpdatedTotalScore = players[COMPUTER_INDEX].getTotalScore() + computerScore;

        //update total score for human and computer
        players[HUMAN_INDEX].setTotalScore(humanUpdatedTotalScore);
        players[COMPUTER_INDEX].setTotalScore(computerUpdatedTotalScore);

        //Printing the result of the round
        System.out.println("\n\tComputer Score: Current Round: " + computerScore + " \t Total Score: " + computerUpdatedTotalScore);
        System.out.println("\n\tHuman Score: Current Round:    " + humanScore + " \t Total Score: " + humanUpdatedTotalScore);

    }

    /**
     * first player determines and checks for modulo suit
     */
    public void setUpFirstPlayer(){

        int firstPlayerIndex = determineFirstPlayer();

        while(firstPlayerIndex == Integer.MAX_VALUE){

            System.out.println("There is MODULO SUIT. Dealing again\n");

            //clear all the cards
            players[HUMAN_INDEX].emptyCardsOnHand();
            players[COMPUTER_INDEX].emptyCardsOnHand();
            clearCardsFromDeck();
            clearCardsFromLayout();
            clearCardsFromStockPile();

            Deck deckObj = new Deck();
            deckObj.shuffleDeck();
            Vector<Card > tempVect = deckObj.getDeck();

            Deck deckObj2 = new Deck();
            Vector<Card> tempVect2 = deckObj2.getDeck();

            //set the deck
            //adding two decks and assigning it to cardeck
            this.cardDeck.addAll(tempVect);
            this.cardDeck.addAll(tempVect2);

            //deal the cards again
            dealAllCards();

            //Print to show
            printVectors();

            //determines first player again to determine the first player
            firstPlayerIndex = determineFirstPlayer();
        }

        this.nextPlayer = firstPlayerIndex;

    }


}
