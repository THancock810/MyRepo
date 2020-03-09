import java.util.Scanner;
public class Blackjack {
    public static void main(String[] args) {

        P1Random rng = new P1Random(); //creates instance of P1Random class in order to use its methods
        Scanner input = new Scanner(System.in);

        //initializing most variables here
        //technically better practice to do this closer to where they are used, but it made it easy for me to change everything in one place
        int gameCounter = 0;
        int userHand;
        int dealerHand;
        boolean keepPlaying = true;
        boolean getCard;
        boolean nextRound;
        int winCounter = 0;
        int lossCounter = 0;
        int tieCounter = 0;
        int userCloseness;
        int dealerCloseness;
        double winPercent;


        while (keepPlaying) {
            //this loop functions both as a variable reset and as a program exit condition
            userHand = 0;
            gameCounter += 1;

            System.out.println("\nSTART GAME #" + gameCounter +"\n");  //let the user know what # game it is

            nextRound = true;
            getCard = true;
            while (nextRound) {
                //this loop functions to re-run the whole card-draw process
                //this was differentiated from the card draw system in case the player needed to bring up the options menu without drawing a card (choice 3 and invalid input)
                if (getCard) {
                    //draw card, determine its value, then tell user its value and add it to the hand
                    int cardVal = rng.nextInt(13) + 1;
                    if (cardVal > 1 && cardVal < 10) {
                        System.out.println("Your card is a " + cardVal + "!");
                        userHand += cardVal;
                    }
                    else if (cardVal == 1) {
                        System.out.println("Your card is a ACE!");
                        userHand += 1;
                    }
                    else if (cardVal > 10) {
                        if (cardVal == 11) {
                            System.out.println("Your card is a JACK!");
                        }
                        if (cardVal == 12) {
                            System.out.println("Your card is a QUEEN!");
                        }
                        if (cardVal == 13) {
                            System.out.println("Your card is a KING!");
                        }
                        userHand += 10; //at the end of this elif because no matter what the value is between 11-13, only 10 gets added to hand
                    }

                    System.out.println("Your hand is: " + userHand + "\n");

                    if (userHand == 21) {
                        //auto-win condition for hitting exactly 21
                        System.out.println("BLACKJACK! You win!");
                        winCounter += 1;
                        break;
                    }
                    if (userHand > 21) {
                        //auto-lose condition for exceeding 21
                        System.out.println("You exceeded 21! You lose.");
                        lossCounter += 1;
                        break;
                    }
                }
                System.out.println("1. Get another card\n2. Hold hand\n3. Print statistics\n4. Exit\n"); //menu
                System.out.println("Choose an option: \n");
                int choice = input.nextInt();

                if (choice >= 1 && choice <= 4) {
                    if (choice == 1) {
                        //HIT: continues card daw loop
                        //exists just in case user previously chose option 3 and turned getCard false
                        getCard = true;
                        continue;
                    }
                    if (choice == 2) {
                        // HOLD: determine dealer's hand, announce winner, and stop running card draw loop
                        dealerHand = rng.nextInt(11) + 16;
                        System.out.println("Dealer's hand: " + dealerHand);
                        System.out.println("Your hand is: " + userHand);

                        if (dealerHand > 21) {
                            //auto-loss check for dealer
                            System.out.println("\nYou win!");
                            winCounter += 1;
                            nextRound = false;
                        }
                        if (dealerHand <= 21) {
                            //variables for checking how closer player is to 21; lower is closer
                            userCloseness = 21 - userHand;
                            dealerCloseness = 21 - dealerHand;
                            if (dealerCloseness < userCloseness) {
                                //this checks closeness to 21, so we can skip the check for ==21, as if the dealer gets 21 its as close as possible regardless
                                System.out.println("\nDealer wins!");
                                lossCounter += 1;
                                nextRound = false; //can be placed at end of parent if
                            }
                            if (dealerCloseness == userCloseness) {
                                //tie game checker
                                System.out.println("\nIt's a tie! No one wins!");
                                tieCounter += 1;
                                nextRound = false; //can be placed at end of parent if
                            }
                            else if (dealerCloseness > userCloseness) { //else if not needed here, once again here for readability
                                System.out.println("You win!");
                                winCounter += 1;
                                nextRound = false; //can be placed at end of parent if
                            }
                        }
                    }
                    if (choice == 3) {
                        //print out the following: number of player wins, number of dealer wins, number of tie games, total # of games played, and percentage of player wins (double)
                        System.out.println("Number of Player wins: " + winCounter);
                        System.out.println("Number of Dealer wins: " + lossCounter);
                        System.out.println("Number of tie games: " + tieCounter);
                        System.out.println("Total # of games played is: " + (gameCounter - 1));
                        winPercent = (double)winCounter/(gameCounter - 1)*100; //gameCounter gets 1 subtracted because the current game has not finished
                        System.out.println("Percentage of Player wins: " + winPercent + "%\n");
                        getCard = false;

                    }
                    if (choice == 4) {
                        nextRound = false;
                        keepPlaying = false;
                    }
                }
                else {
                    System.out.println("Invalid input!\nPlease enter an integer value between 1 and 4.\n");
                    getCard = false;


                    //change qaaaaaaaaaaaaaa
                }
            }
        }
    }
}

