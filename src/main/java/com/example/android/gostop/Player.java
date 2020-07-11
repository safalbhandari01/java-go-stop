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

public class Player {

    protected String playerName;
    protected int totalScore;
    protected String helperMessageHand;
    protected String helperMessageStock;
    protected boolean isHelperBot;
    protected Vector<Card> cardsOnHand= new Vector<>();
    protected Vector<Card> cardsOnCapturePile = new Vector<>();

    /**
     * Constructor for player class
     */
    public Player(){

    }
    /**
     * play function that is overriden by human and computer class
     */
    public void play(Vector<Vector<Card>> cardsOnLayout, Vector<Card> cardsOnStockPile){

    }

    /**
     * function that is overriden by human class
     */
    public void humanPickedCard(Card card){

    }

    /**
     * Constructor for player class
     * @param name, name of the player
     */
    public Player(String name){
        this.playerName = name;
        this.totalScore = 0;
        this.isHelperBot = false;
    }

    /**
     * returns the helper message of hand
     * @return helperMessageHand, String value
     */
    public String getHelperMessageHand(){
        return this.helperMessageHand;
    }

    /**
     * set the helper message of hand
     * @param msg, String value
     */
    public void setHelperMessageHand(String msg){
        this.helperMessageHand = msg;
    }

    /**
     * returns the helper message of stock
     * @return helperMessageStock, String value
     */
    public String getHelperMessageStock(){
        return this.helperMessageStock;
    }

    /**
     * set the helper message of stock
     * @param msg, String value
     */
    public void setHelperMessageStock(String msg){
        this.helperMessageStock = msg;
    }


    /**
     * calculates the score of the player
     */
    public int calculateScore(){

        int score =0;
        //create a hasmap of frequency
        HashMap<String, Integer> capturePileFrequencyMapper = new HashMap<String, Integer>();

        for(int i=0; i<cardsOnCapturePile.size(); i++){
            if(capturePileFrequencyMapper.containsKey(cardsOnCapturePile.elementAt(i).getFace())){
                capturePileFrequencyMapper.put(cardsOnCapturePile.elementAt(i).getFace(),capturePileFrequencyMapper.get(cardsOnCapturePile.elementAt(i).getFace())+1);
            }else{
                capturePileFrequencyMapper.put(cardsOnCapturePile.elementAt(i).getFace(), 1);
            }
        }

        //printing the content of a capture pile hash map
        System.out.println( "Calculate Score hashmap " + Arrays.asList(capturePileFrequencyMapper)); // method 1


        //calculate the score iterating through frequency hash map
        for (String key : capturePileFrequencyMapper.keySet()) {
            if(capturePileFrequencyMapper.get(key) != null) {
                if (capturePileFrequencyMapper.get(key) == 4 || capturePileFrequencyMapper.get(key) == 6) {
                    score++;
                } else if (capturePileFrequencyMapper.get(key) == 8) {
                    score += 2;
                }
            }
        }

        System.out.println("The calculated score is "+ score);

        return score;
    }

    /**
     * function that checks if the card is on layout or not
     * @param cardsOnLayout, Vector of Cards on layout
     * @param faceCardChosen, face card chosen
     * @return numCardsMatched, total number of cards matched on layout
     */
    public int checkCardOnLayout(Vector<Vector<Card>> cardsOnLayout, String faceCardChosen){
        int numCardsMatched =0;

        for(Vector<Card> i: cardsOnLayout){
            if(i.size()==1){
                Card cardOnLayoutToMatch = i.elementAt(0);
                if(cardOnLayoutToMatch.getFace().equals(faceCardChosen)){
                    numCardsMatched++;
                }
            }
        }

        System.out.println("\t Cards Matched on Layout: "+ numCardsMatched);

        return numCardsMatched;
    }

    /**
     * function that get index of matched card on layout
     * @param cardsOnLayout, Vector of Cards on layout
     * @param faceCardChosen, face card chosen
     * @param numCardsMatched, cards matched on layout with the facecard chosen
     * @return matches, Vector of integer that contains index of matched card on layout
     */
    public Vector<Integer> getIndexOfMatchedCardOnLayout(Vector<Vector<Card>> cardsOnLayout, String faceCardChosen, int numCardsMatched){

        Vector<Integer> matches= new Vector<>();

        for(int i=0; i<cardsOnLayout.size(); i++){
            Vector<Card > insideVector = cardsOnLayout.elementAt(i);

            if(insideVector.size()==1){
                Card cardOnLayoutToMatch = insideVector.elementAt(0);

                if( cardOnLayoutToMatch.getFace().equals(faceCardChosen)){
                    numCardsMatched++;
                    matches.add(i);
                }
            }
        }


        return matches;
    }

//    /**
//     * function that prints layout in stack format
//     * @param cardsOnLayout, Vector of Cards on layout
//     */
//    public void printLayoutStackFormat(Vector<Vector<Card>> cardsOnLayout){
//
//        return;
//    }

    /**
     * function that chooses Index of matched card on layout
     * @param matches, Vector of integer that contains the index matched on layout
     * @return 0, the first index from the matches vector i.e, 0
     */
    public int chooseIndexOfMatchedCardOnLayout(Vector<Integer> matches){
        //change later if this function is required
        return 0;
    }

    /**
     * function that removes cards from layout
     * @param cardsOnLayout, Vector of Cards on layout
     * @param faceCard, face card chosen
     * @param numCardsToRemove, total number of cards to remove
     */
    public void removeCardsFromLayout(Vector<Vector<Card>> cardsOnLayout, String faceCard, int numCardsToRemove){
        int numErasedCards = 0;
        int valueToPop = 0;

        while(numErasedCards < numCardsToRemove){

            for(int i=0; i< cardsOnLayout.size(); i++){

                if(cardsOnLayout.elementAt(i).size()>0){

                    if(cardsOnLayout.elementAt(i).elementAt(0).getFace().equals(faceCard)){
                        valueToPop = i;
                        break;
                    }
                }

            }
            //THIS MIGHT BE WRONG CHECK LATER FOR VERIFICATION
            cardsOnLayout.elementAt(valueToPop).remove(cardsOnLayout.elementAt(valueToPop).size()-1);
            numErasedCards++;
        }

        //checking and removing if there are any inner vectors that are empty
        numErasedCards =0;
        while(numErasedCards<numCardsToRemove){
            //deletes the first empty vector inside layout when the function is called
            deleteEmptyVectorsInsideLayout(cardsOnLayout);
            numErasedCards++;
        }

        return;
    }

    /**
     * prints capture pile cards
     * @param capturePilePassed, vector of card of capture pile
     */
    public void printCapturePileCards(Vector<Card> capturePilePassed){
    }


    /**
     * function that deletes empty vectors inside layout
     * @param cardsOnLayout, cards on layout pile in a vector
     */
    public void deleteEmptyVectorsInsideLayout(Vector<Vector<Card>> cardsOnLayout){

        int indexOfInnerVectorToRemove = -1;

        for(int i = 0; i<cardsOnLayout.size();i++){
            if(cardsOnLayout.elementAt(i).size()==0){
                indexOfInnerVectorToRemove = i;
                break;
            }
        }

        cardsOnLayout.remove(indexOfInnerVectorToRemove);
    }

    /**
     * function that finds index matched if there is a triple stack
     * @param cardsOnLayout, cards on layout pile in a vector
     * @param faceCardChosen, string value of face card chosen
     * @return tripleStackMatchedIndex, int value of triple stack matched index
     */
    public int findIndexMatchedTripleStack(Vector<Vector<Card>> cardsOnLayout, String faceCardChosen){

        int tripleStackMatchedIndex = -1;

        for(int i=0; i<cardsOnLayout.size();i++){

            Vector<Card> insideVector = cardsOnLayout.elementAt(i);

            if(insideVector.size() == 3){

                Card cardOnLayoutToMatch = insideVector.elementAt(0);

                if(cardOnLayoutToMatch.getFace().equals(faceCardChosen)){
                    tripleStackMatchedIndex = i;
                    break;
                }
            }

        }
        return tripleStackMatchedIndex;
    }

    /**
     * function that empties cards on hand
     */
    public void emptyCardsOnHand(){
        this.cardsOnHand.clear();
    }

    /**
     * function that empties cards on capture pile
     */
    public void emptyCardsOnCapturePile(){
        this.cardsOnCapturePile.clear();
    }

    /**
     * function that gets name of the player
     * @return playerName
     */
    public String getName(){
        return this.playerName;
    }

    /**
     * function that set name of the player
     * @param name, name of the player
     */
    public void setName(String name){
        this.playerName = name;
    }

    /**
     * function that gets cards on hand
     * @return cardsOnHand, a vector of cards
     */
    public Vector<Card> getCardsOnHand(){
        return this.cardsOnHand;
    }

    /**
     * function that sets cards on hand
     * @param cardsForHand, a vector of card to set as cardsForHand
     */
    public void setCardsOnHand(Vector<Card> cardsForHand){
        this.cardsOnHand = cardsForHand;
    }

    /**
     * function that gets card on capture pile
     * @return cardsOnCapturePile
     */
    public Vector<Card> getCardOnCapturePile(){
        return this.cardsOnCapturePile;
    }

    /**
     * function that set cards on capture pile
     * @param cardsForCapturePile, Vector of Cards to set the capture pile to
     */
    public void setCardOnCapturePile(Vector<Card> cardsForCapturePile){
        this.cardsOnCapturePile = cardsForCapturePile;
    }

    /**
     * function that set helper bot to true or false
     * @param trueOrFalse, true or false value boolean
     */
    public void setHelperBot(boolean trueOrFalse){
        this.isHelperBot = trueOrFalse;
    }

    /**
     * function that gets the total score of the player
     * @return totalScore, int value which is total score of the player
     */
    public int getTotalScore(){
        return totalScore;
    }

    /**
     * function that sets total score
     * @param totalScorePassed, int value to set the score to
     */
    public void setTotalScore(int totalScorePassed){
        this.totalScore = totalScorePassed;
    }

    /**
     * function that adds a card on the hand of the player
     * @param cardToAdd, a card to add to the hand of the player
     */
    public void addCardOnHand(Card cardToAdd){
        cardsOnHand.add(cardToAdd);
    }

    /**
     * function that adds a card on the capture pile of the player
     * @param cardToAdd, a card to add to the capture pile of the player
     */
    public void addCardOnCapturePile(Card cardToAdd){
        cardsOnCapturePile.add(cardToAdd);
    }


    /** function to implement strategy for when the card is drawn for the stock pile
     *  @param cardsOnLayout, vector of vector of pointer to cards on the layout
     *  @param cardsOnStockPile, vector of pointer to cards on the capture pile
     *  @param numCardsMatchedOnHand, number of cards that was matched on hand
     *  @param stackedPairIndex, index of stackedPair on the layout of cards
     *  when played from the hand
     * Local Variables: drawnCardFromStockPile, card that is picked from stock pile of type pointer to card
     *                  numCardsMatchedFromStockPile, cards matched to the layout with drawn card
     *                  innerVector,vector of pointer to card
     *                  optionChosen, int value of option chosen by user
     *                  matches, vector of int that contains index of matched card in the layout
     *                  valueToPop, int value of value to pop
     *                  numErasedCard, total number of cards to  remove from layout
     *                  capturePileVector, vector of pointer to card
     *
     * Algorithm:        H0 or H3 - If the card from the stock pile matches:
     *                   no card in the layout, the card is added to the layout.
     *                   one card in the layout, the player captures both the cards: the card played from the stock pile and the card from the layout that matches it.
     *                   two cards in the layout, the player picks one of the two matching cards and captures it along with the card played from the stock pile.
     *                   three cards in the layout or triple stack, the player captures all four cards.
     *                   H1 or H2: If the card from the stock pile matches:
     *                   no card in the layout, the card is added to the layout. The pair of cards stacked in H1/H2 are captured.
     *                   three cards in the layout or triple stack, the player captures all four cards. The pair of cards stacked in H1/H2 are also captured.
     *                   any card in the layout other than the stack pair from H1/H2, the player captures both the pairs - the stacked pair and the current pair.
     *                   only the stack pair from H1/H2, the player does not capture any card - the player leaves all three cards as a triple stack in the layout.
     *
     * Assistance Received: None
     */
    public void playCardFromStockPile(Vector<Vector<Card>> cardsOnLayout, Vector<Card> cardsOnStockPile, int numCardsMatchedOnHand, int stackedPairIndex){

        //get the last element from the vector every time from the stock pile as the next card to draw
        //When printing the content of the stock pile always print it from the end so the display will show the last element of the stock pile
        //as the first element on the console(or GUI). Done this way for readability of the stock pile

        //card drawn from the stock pile get a deep copy
        Card drawnCardFromStockPile = new Card(cardsOnStockPile.elementAt(cardsOnStockPile.size() - 1).getFace(),cardsOnStockPile.elementAt(cardsOnStockPile.size() - 1).getSuit());
        //remove the card from the stock pile after copying it
        cardsOnStockPile.remove(cardsOnStockPile.size()-1);

        //CHECKING IF CARD IS REMOVED AND COPIED OR NOT
        System.out.println( "\n\n\tSTOCK PILE MOVE\n");
        System.out.println("\tCard drawn from Stock Pile: " + drawnCardFromStockPile.getFace() + drawnCardFromStockPile.getSuit());

        //cout<<"\tThe size of the stock pile after removal is"<<cardsOnStockPile.size()<<endl;

        //get face value of card drawn from stock pile
        String faceCardDrawnFromStockPile = drawnCardFromStockPile.getFace();
        //get the numbers of cards matched on the layout with face value of card chosen from stock pile
        int numCardsMatchedFromStockPileCard = checkCardOnLayout(cardsOnLayout, faceCardDrawnFromStockPile);

        // if cards matched on layout is greater than 3 then it is given the value to 3 because we handle only 3 cards matching at the most in this game.
        if (numCardsMatchedFromStockPileCard > 3)
        {
            numCardsMatchedFromStockPileCard = 3;
        }

        //TRIPLE STACK HANDLING CODE PART STARTS**************************************************************************************************************
        int tripleStackIndexReturn = findIndexMatchedTripleStack(cardsOnLayout, faceCardDrawnFromStockPile);
        if (tripleStackIndexReturn != -1)
        {

            if (numCardsMatchedOnHand == 1 || numCardsMatchedOnHand == 2)
            {

                //now capture the h1/h2 stacked pair as well because there is a stacked pair
                for (int i = 0; i < 2; i++)
                {
                    Card capturedCard = new Card(cardsOnLayout.elementAt(stackedPairIndex).elementAt(i).getFace(),cardsOnLayout.elementAt(stackedPairIndex).elementAt(i).getSuit());
                    this.cardsOnCapturePile.add(capturedCard);
                }

                //remove the stacked pair and the vector from the capture pile
                cardsOnLayout.remove(stackedPairIndex);

            } //end of if statement for numCardsMatchedOnHand
            //means there is a match with the triple stack and face card chosen
            //Pushing the stacked pair to the capture pile

            //calling findIndexMatchedTriple stack inside again because removing the stacked pair index changes the index of the triplestack called before
            tripleStackIndexReturn = findIndexMatchedTripleStack(cardsOnLayout, faceCardDrawnFromStockPile);
            for (int i = 0; i < 3; i++)
            {
                Card capturedCard = new Card(cardsOnLayout.elementAt(tripleStackIndexReturn).elementAt(i).getFace(),cardsOnLayout.elementAt(tripleStackIndexReturn).elementAt(i).getSuit());
                this.cardsOnCapturePile.add(capturedCard);
            }

            //remove the triple stack from layout
            cardsOnLayout.remove(tripleStackIndexReturn);

            //push the face card to the capture pile
            this.cardsOnCapturePile.add(drawnCardFromStockPile);

            if (isHelperBot == true)
            {
                System.out.println("\t Recommend to capture the triple stack on the layout with the drawn card.");
                helperMessageStock = "\t Recommend to capture the triple stack on the layout with the drawn card.";
            }
            else
            {
                //printing computer move here for strategy display
                System.out.println("\t" + this.playerName + " captured triple stack on the layout and the drawn card with it \n");
            } //end of ishelperBot if statement

            if (numCardsMatchedOnHand == 1 || numCardsMatchedOnHand == 2)
            {
                if (isHelperBot == true)
                {
                    System.out.println("and capture the previously stacked pair index from last hand move .\n");
                    helperMessageStock = helperMessageStock + "\tRECOMMENDATION: Recommend to capture the triple stack on the layout with the drawn card.";
                }
                else
                {
                    //printing computer move here for strategy display
                    System.out.println(this.playerName +" also captured the previously stacked pair from last hand move.\n");
                } //end of ishelperBot if statement
            }

            return;
        }
        //TRIPLE STACK HANDLING CODE PART ENDS**********************************************************************************************

        Vector<Card > innerVector = new Vector<>();
        Vector<Card > innerVectorForOneCardFromStackPair;
        int numCardsMatched = 0;
        Vector<Integer> matches;
        int optionChosen=0;         //MIGHT HAVE TO CHANGE LATER. IT WAS UNINITIALIZED BEFORE. INITIALIZED TO REMOVE ONE ERROR down the line
        int numErasedCards;
        int valueToPop;
        Vector<Card > capturePileVector;

        if (numCardsMatchedOnHand == 0 || numCardsMatchedOnHand == 3) {

            //if card chosen from the stock pile matches 0,1,2,3 cards

            switch (numCardsMatchedFromStockPileCard)
            {
                case 0:
                    //cout<<"CHECK2";
                    innerVector.add(drawnCardFromStockPile);
                    cardsOnLayout.add(innerVector);
                    //cout<<"CHECK3";

                    //
                    if (isHelperBot == true)
                    {
                        System.out.println("\tRecommend to add a card to the layout because there is no match in the layout. "
                                + "That is the only option here. Follow the rules!\n ");
                        helperMessageStock = "\tRecommend to add a card to the layout because there is no match in the layout. "
                                + "That is the only option here. Follow the rules!\n ";
                    }
                    else
                    {
                        System.out.println(this.playerName + " added the card to the layout because there is no match in the layout.\n");
                    }
                    //

                    break;
                case 1:
                    //returns vector of matches that contains indexes of card  matched on layout with the card played from hand or stock
                    matches = getIndexOfMatchedCardOnLayout(cardsOnLayout, faceCardDrawnFromStockPile, numCardsMatched);

                    if (isHelperBot == true)
                    {
                        System.out.println("\tPair the drawn card with " + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getFace()
                            + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getSuit() + " from the layout to create a stack pair and capture it" );
                        helperMessageStock = "\tPair the drawn card with " + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getFace()
                                + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getSuit() + " from the layout to create a stack pair and capture it";
                    }
                    else
                    {
                        //returns the option chosen by the human to match the card with and create a stack or capture it
                        System.out.println(this.playerName + " has the following cards on layout to choose from.\n");
                        optionChosen = chooseIndexOfMatchedCardOnLayout(matches);
                        System.out.println(this.playerName + " paired the drawn card with " + cardsOnLayout.elementAt(matches.elementAt(optionChosen)).elementAt(0).getFace()
                            + cardsOnLayout.elementAt(matches.elementAt(optionChosen)).elementAt(0).getSuit() + " from the layout to create a stack pair and capture it" );
                    }

                    //push the card drawn from the stock pile to the capture pile
                    cardsOnCapturePile.add(drawnCardFromStockPile);

                    //get the card from the layout and push it to the capture pile
                    //size of matches is 1;

                    for (int i = 0; i < matches.size(); i++)
                    {
                        Card capturedCard = new Card(cardsOnLayout.elementAt(matches.elementAt(i)).elementAt(0).getFace(),cardsOnLayout.elementAt(matches.elementAt(i)).elementAt(0).getSuit());
                        this.cardsOnCapturePile.add(capturedCard);
                    }

                    //erase the first one card from the vector after capturing it
                    numErasedCards = 0;

                    //Deleting cards from layout one at a  time
                    //Deleting all similar card inside one loop will cause the vector to collapse or go out of range
                    //so first face value match taken index and then break out from the loop and delete the card on that index
                    //then get inside loop and find another face value and break out and delete the card and so on...
                    //HERE Card to erase is one because we have one match on the layout

                    removeCardsFromLayout(cardsOnLayout, faceCardDrawnFromStockPile, 1);

                    //printing the capture pile after capturing the cards
                    //printCapturePileCards(getCardOnCapturePile());

                    break;
                case 2:
                    matches = getIndexOfMatchedCardOnLayout(cardsOnLayout, faceCardDrawnFromStockPile, numCardsMatched);

                    if (isHelperBot == true)
                    {
                        System.out.println("\tRecommend to pair the drawn card with " + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getFace()
                             + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getSuit() + " from the layout to create a stack pair and capture it");
                        helperMessageStock = "\tRecommend to pair the drawn card with " + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getFace()
                                + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getSuit() + " from the layout to create a stack pair and capture it";
                    }
                    else
                    {
                        System.out.println( this.playerName + " has the following cards on layout to choose from.\n");
                        optionChosen = chooseIndexOfMatchedCardOnLayout(matches);
                        System.out.println(this.playerName + " paired the drawn card with " + cardsOnLayout.elementAt(matches.elementAt(optionChosen)).elementAt(0).getFace()
                            + cardsOnLayout.elementAt(matches.elementAt(optionChosen)).elementAt(0).getSuit() + " from the layout to create a stack pair and capture it");
                    }

                    //push the card drawn from the stock pile to the capture pile
                    cardsOnCapturePile.add(drawnCardFromStockPile);

                    //get the card from the layout chosen by the human  from the layout pile and push it to the capture pile
                    //size of matches is 1;

                    //throwing it inside the loop to make the initialization of the capturedCard work otherwise declaring the captureCard inside the switch statement is not
                    //allowed by c++
                    for (int i = 0; i < 1; i++)
                    {
                        Card capturedCard = new Card(cardsOnLayout.elementAt(matches.elementAt(optionChosen)).elementAt(0).getFace(),cardsOnLayout.elementAt(matches.elementAt(optionChosen)).elementAt(0).getSuit());
                        this.cardsOnCapturePile.add(capturedCard);
                    }

                    //delete the card chosen by human from the layout
                    cardsOnLayout.elementAt(matches.elementAt(optionChosen)).remove(cardsOnLayout.elementAt(matches.elementAt(optionChosen)).size()-1);
                    //remove if there is any empty vector in layout after deletion
                    deleteEmptyVectorsInsideLayout(cardsOnLayout);

                    //printing the capture pile after capturing the cards: one from layout and another one is drawn from the stock pile
                    //print the cards on layout on stack format HUMAN CAPTURE CARDS
                    //printCapturePileCards(getCardOnCapturePile());

                    break;

                case 3:
                    matches = getIndexOfMatchedCardOnLayout(cardsOnLayout, faceCardDrawnFromStockPile, numCardsMatched);

                    System.out.println("\t Three or more cards match with the card drawn from stock pile! \n");

                    //add played card to the capture pile
                    cardsOnCapturePile.add(drawnCardFromStockPile);

                    if (isHelperBot == true)
                    {
                        System.out.println( "\tRecommend to pair the drawn card with ");
                        helperMessageStock ="\tRecommend to pair the drawn card with ";
                    }

                    //create three deep copies of cards to push on the capture pile and remaining matches are left untouched as per the algorithm
                    for (int i = 0; i < 3; i++)
                    {
                        //capture first three cards from the vector and create a deep copy and add it to
                        //capture pile of human
                        Card capturedCard = new Card(cardsOnLayout.elementAt(matches.elementAt(i)).elementAt(0).getFace(),cardsOnLayout.elementAt(matches.elementAt(i)).elementAt(0).getSuit());
                        if (isHelperBot == true)
                        {
                            System.out.print(capturedCard.getFace() + capturedCard.getSuit() + " ");
                            helperMessageStock = helperMessageStock + capturedCard.getFace() + capturedCard.getSuit() + " ";
                        }
                        this.cardsOnCapturePile.add(capturedCard);
                    }
                    if (isHelperBot == true)
                    {
                        System.out.println(" from the layout to capture four cards.\n");
                        helperMessageStock = helperMessageStock + " from the layout to capture four cards.\n";
                    }
                    else
                    {
                        //printing computer move here for strategy display
                        System.out.println("\t" + this.playerName + " captured 3 cards of same face as drawn card from the layout and the drawn card with it \n");
                    }

                    //Deleting cards from layout one at a  time
                    //Deleting all similar card will cause the vector to collapse or go out of range
                    //so first face value match taken index and then break out from the loop and delete the card on that index
                    //then get inside loop and find another face value and break out and delete the card and so on...

                    removeCardsFromLayout(cardsOnLayout, faceCardDrawnFromStockPile, 3);

                    //printing the capture pile after capturing the cards
                    //print the cards on layout on stack format HUMAN CAPTURE CARDS

                    //printCapturePileCards(getCardOnCapturePile());

                    break;
            }
            //in this else if value of stacked pair index will never be -1 because it executes only when stackedpair is created
            //so no need to check if it is negative


        }else if(numCardsMatchedOnHand == 1 || numCardsMatchedOnHand == 2) {
            switch (numCardsMatchedFromStockPileCard)
            {
                case 0:
                    //if only the stack pair from h1/h2 is matched then create a triple stack and push it to layout
                    //add three cards on the layout as separate vector so rather there are 3 card present than a
                    //stack
                    System.out.println("CHECK TRIPLE");
                    if (cardsOnLayout.elementAt(stackedPairIndex).elementAt(0).getFace().equals(faceCardDrawnFromStockPile))
                {

                    //COMMENTING OUT FOR THE MOMENT TO TEST TRIPLE STACK *******************************************************************************************
                    //only removing and pushing the last card from the stacked pair to another vector of the
                    //stack layout

                    //COMMENTED OUT FROM HERE *******************************************************************************************
                    // Card * captureCard = new Card(*cardsOnLayout.at(stackedPairIndex).at(1));
                    // //removing from stacked pair last card
                    // cardsOnLayout.at(stackedPairIndex).pop_back();
                    // //adding that removed card to the layout with a new inner vector
                    // innerVectorForOneCardFromStackPair.push_back(captureCard);

                    // //adding the innerVector to the layout
                    // //cardsOnLayout.push_back(innerVectorForOneCardFromStackPair);

                    // //pushing the triple stack element one after another
                    // cardsOnLayout.insert(cardsOnLayout.begin()+stackedPairIndex,innerVectorForOneCardFromStackPair);

                    // //push the drawn card on cards on layout with another innervector
                    // innerVector.push_back(drawnCardFromStockPile);

                    // //cardsOnLayout.push_back(innerVector);

                    // cardsOnLayout.insert(cardsOnLayout.begin()+stackedPairIndex,innerVector);
                    //set is triple stack to true

                    //COMMENTED OUT UNTIL HERE*******************************************************************************************

                    //ADDED LINE TO CREATE A TRIPLE STACK
                    cardsOnLayout.elementAt(stackedPairIndex).add(drawnCardFromStockPile);

                    if (isHelperBot == true)
                    {
                        System.out.println("\tThe card will match only with h1/h2 stack pair created from hand. So creating"
                                + " a triple stack and pushing it to the layout is recommended\n");
                        helperMessageStock = "\tThe card will match only with h1/h2 stack pair created from hand. So creating"
                                + " a triple stack and pushing it to the layout is recommended\n";
                    }
                    else
                    {
                        System.out.println(this.playerName + " created a triple stack by pushing the drawn card to the stacked pair of the layout because the"
                            + " drawn card only matched with h1/h2 stack pair created from the hand.\n");

                    }

                    //COMMENTING OUT FOR THE MOMENT TO TEST TRIPLE STACK **************************************************************************************************
                }
            else
                { //if no card in the layout, the card is added to the layout.The pair of cards stacked in
                    //h1/h2 are captured

                    //Pushing the stacked pair to the capture pile
                    for (int i = 0; i < 2; i++)
                    {
                        Card capturedCard = new Card(cardsOnLayout.elementAt(stackedPairIndex).elementAt(i).getFace(),cardsOnLayout.elementAt(stackedPairIndex).elementAt(i).getSuit());
                        this.cardsOnCapturePile.add(capturedCard);
                    }

                    //remove the stacked pair and the vector from the capture pile
                    cardsOnLayout.remove(stackedPairIndex);

                    innerVector.add(drawnCardFromStockPile);
                    cardsOnLayout.add(innerVector);

                    if (isHelperBot == true)
                    {
                        System.out.println("\tThe card does not match any card in layout so adding the drawn card to "
                                + "the layout is recommended and capture the previously stacked pair from hand. Play by the rules!\n");
                        helperMessageStock = "\tThe card does not match any card in layout so adding the drawn card to "
                                + "the layout is recommended and capture the previously stacked pair from hand. Play by the rules!\n";
                    }
                    else
                    {
                        System.out.println(this.playerName + " added the drawn card to the layout because there is no match and captured the previously stacked pair from hand\n");
                    }
                }

                //printing the capture pile after capturing the cards
                //print the cards on layout on stack format HUMAN CAPTURE CARDS
                //printCapturePileCards(getCardOnCapturePile());

                // cout<<"\n\tLayout After Move: ";
                // printLayoutStackFormat(cardsOnLayout);

                break;

                case 3:
                    matches = getIndexOfMatchedCardOnLayout(cardsOnLayout, faceCardDrawnFromStockPile, numCardsMatched);

                    //cout<<"\tThe size of the vector of matches is "<<matches.size()<<endl;
                    //cout<<"\t You have 3 or more cards match. Congratulations!\n";
                    //cout<<"\t You just captured 3 cards from layout and one played by you\n";

                    //add played card to the capture pile
                    cardsOnCapturePile.add(drawnCardFromStockPile);

                    if (isHelperBot == true)
                    {
                        System.out.println("\tRecommend to pair the drawn card with ");
                        helperMessageStock = "\tRecommend to pair the drawn card with ";
                    }

                    //create three deep copies of cards to push on the capture pile and remaining matches are left untouched as per the algorithm
                    for (int i = 0; i < 3; i++)
                    {
                        //capture first three cards from the vector and create a deep copy and add it to
                        //capture pile of human
                        Card capturedCard = new Card(cardsOnLayout.elementAt(matches.elementAt(i)).elementAt(0).getFace(),cardsOnLayout.elementAt(matches.elementAt(i)).elementAt(0).getSuit());
                        if (isHelperBot == true)
                        {
                            System.out.println(capturedCard.getFace() + capturedCard.getSuit() + " ");
                            helperMessageStock = helperMessageStock + capturedCard.getFace() + capturedCard.getSuit() + " ";
                        }
                        this.cardsOnCapturePile.add(capturedCard);
                    }
                    if (isHelperBot == true)
                    {
                        System.out.println(" from the layout to capture four cards. Also capture the h1/h2 pair stacked before.\n");
                        helperMessageStock = helperMessageStock + " from the layout to capture four cards. Also capture the h1/h2 pair stacked before.\n";
                    }

                    else
                    {
                        //printing computer move here for strategy display
                        System.out.println(this.playerName + " captured 3 cards of same face as drawn card from the layout and the drawn card with it. Also captured the"
                            + " h1/h2 pair stacked from hand before.\n");
                    }

                    //now capture the h1/h2 stacked pair as well

                    //Pushing the stacked pair to the capture pile
                    for (int i = 0; i < 2; i++)
                    {
                        Card capturedCard = new Card(cardsOnLayout.elementAt(stackedPairIndex).elementAt(i).getFace(),cardsOnLayout.elementAt(stackedPairIndex).elementAt(i).getSuit() );
                        this.cardsOnCapturePile.add(capturedCard);
                    }

                    //remove the stacked pair and the vector from the capture pile
                    cardsOnLayout.remove(stackedPairIndex);

                    //Deleting cards from layout one at a  time
                    //Deleting all similar card will cause the vector to collapse or go out of range
                    //so first face value match taken index and then break out from the loop and delete the card on that index
                    //then get inside loop and find another face value and break out and delete the card and so on...

                    removeCardsFromLayout(cardsOnLayout, faceCardDrawnFromStockPile, 3);

                    //printing the capture pile after capturing the cards
                    //print the cards on layout on stack format HUMAN CAPTURE CARDS
                    //printCapturePileCards(getCardOnCapturePile());

                    break;
                case 1:
                case 2:

                    //now capture the h1/h2 stacked pair as well

                    //Pushing the stacked pair to the capture pile
                    for (int i = 0; i < 2; i++)
                    {
                        Card capturedCard = new Card(cardsOnLayout.elementAt(stackedPairIndex).elementAt(i).getFace(),cardsOnLayout.elementAt(stackedPairIndex).elementAt(i).getSuit());
                        this.cardsOnCapturePile.add(capturedCard);
                    }

                    //remove the stacked pair and the vector from the capture pile
                    cardsOnLayout.remove( stackedPairIndex);

                    matches = getIndexOfMatchedCardOnLayout(cardsOnLayout, faceCardDrawnFromStockPile, numCardsMatched);

                    if (isHelperBot == true)
                    {
                        System.out.println("\tRecommend to pair the drawn card with " + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getFace()
                            + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getSuit() + " from the layout to create a stack pair and capture it as well as the previous"
                            + " stack pair created from hand" );
                        helperMessageStock = "\tRecommend to pair the drawn card with " + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getFace()
                                + cardsOnLayout.elementAt(matches.elementAt(0)).elementAt(0).getSuit() + " from the layout to create a stack pair and capture it as well as the previous"
                                + " stack pair created from hand";
                    }
                    else
                    {
                        System.out.println(this.playerName + " has the following cards on layout to choose from.\n");
                        optionChosen = chooseIndexOfMatchedCardOnLayout(matches);
                        System.out.println(this.playerName + " paired the drawn card with " + cardsOnLayout.elementAt(matches.elementAt(optionChosen)).elementAt(0).getFace()
                            + cardsOnLayout.elementAt(matches.elementAt(optionChosen)).elementAt(0).getSuit() + " from the layout to create a stack pair and capture it as well as the previous"
                            + " stack pair created from hand" );

                        //push the card drawn from the stock pile to the capture pile
                        cardsOnCapturePile.add(drawnCardFromStockPile);

                        //get the card from the layout chosen by the human  from the layout pile and push it to the capture pile
                        //size of matches is 1;

                        //throwing it inside the loop to make the initialization of the capturedCard work otherwise declaring the captureCard inside the switch statement is not
                        //allowed by c++
                        for (int i = 0; i < 1; i++)
                        {
                            Card capturedCard = new Card(cardsOnLayout.elementAt(matches.elementAt(optionChosen)).elementAt(0).getFace(),cardsOnLayout.elementAt(matches.elementAt(optionChosen)).elementAt(0).getSuit());
                            this.cardsOnCapturePile.add(capturedCard);
                        }

                        //delete the card chosen by human from the layout
                        cardsOnLayout.elementAt(matches.elementAt(optionChosen)).remove(cardsOnLayout.elementAt(matches.elementAt(optionChosen)).size()-1);

                        //printing the capture pile after capturing the cards: one from layout and another one is drawn from the stock pile
                        //print the cards on layout on stack format HUMAN CAPTURE CARDS
                        //printCapturePileCards(getCardOnCapturePile());
                        deleteEmptyVectorsInsideLayout(cardsOnLayout);
                    }

                    break;
            }
        }else {
            //Throw an exception saying numCardsMatchedOnHand can be 0,1,2, or 3
            //if this lock executes it means the value is not what is desired by program(the value is <0 so thrown an error)
        }

        return;
    }

}//end of playCardFromStocKPile
