package com.example.casinoserver.service;

import com.example.casinoserver.entities.BlackJackShuffleLog;
import com.example.casinoserver.repositories.BlackJackShuffleLogRepository;
import com.example.casinoserver.transfer.ReceiveData;
import com.example.casinoserver.transfer.SendingData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class GameLogicMethods {

    private BlackJackShuffleLogRepository blackJackShuffleLogRepository;

    private static String[] fourOrderedCardDecks = {
            "S-2", "S-3", "S-4", "S-5", "S-6", "S-7", "S-8", "S-9", "S-10", "S-J", "S-Q", "S-K", "S-A",
            "C-2", "C-3", "C-4", "C-5", "C-6", "C-7", "C-8", "C-9", "C-10", "C-J", "C-Q", "C-K", "C-A",
            "D-2", "D-3", "D-4", "D-5", "D-6", "D-7", "D-8", "D-9", "D-10", "D-J", "D-Q", "D-K", "D-A",
            "H-2", "H-3", "H-4", "H-5", "H-6", "H-7", "H-8", "H-9", "H-10", "H-J", "H-Q", "H-K", "H-A",
    };

    public static String[] shuffledBlackjackDeck() {
        Collections.shuffle(Arrays.asList(fourOrderedCardDecks));

        return fourOrderedCardDecks;
    }

    public static int getPlayerCardValue(SendingData sendingDataObject) {
        int cardsValue = 0;
        for (int i = 0; i < sendingDataObject.getPlayerCards().size(); i++) {
            String cardAsString = sendingDataObject.getPlayerCards().get(i);
            String[] splitCard = cardAsString.split("-");
            switch (splitCard[1]) {
                case "J":
                case "K":
                case "Q":
                    splitCard[1] = "10";
                    break;

                case "A":
                    splitCard[1] = "11";
                    break;
            }
            cardsValue += Integer.parseInt(splitCard[1]);
        }
        return cardsValue;
    }

    public static int getHouseCardValue(SendingData sendingDataObject) {
        int cardsValue = 0;
        for (int i = 0; i < sendingDataObject.getHouseCards().size(); i++) {
            String cardAsString = sendingDataObject.getHouseCards().get(i);
            String[] splitCard = cardAsString.split("-");
            switch (splitCard[1]) {
                case "J":
                case "K":
                case "Q":
                    splitCard[1] = "10";
                    break;

                case "A":
                    splitCard[1] = "11";
                    break;
            }
            cardsValue += Integer.parseInt(splitCard[1]);
        }
        return cardsValue;
    }

    public static ArrayList<String> getRemainingDeck(Optional<BlackJackShuffleLog> completeShuffledDeck, int playerCardCount) {
        ArrayList<String> remainingDeck = new ArrayList<String>(Arrays.asList(completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard2(),
                completeShuffledDeck.get().getCard3(), completeShuffledDeck.get().getCard4(),
                completeShuffledDeck.get().getCard5(), completeShuffledDeck.get().getCard6(),
                completeShuffledDeck.get().getCard7(), completeShuffledDeck.get().getCard8(),
                completeShuffledDeck.get().getCard9(), completeShuffledDeck.get().getCard10(),
                completeShuffledDeck.get().getCard11(), completeShuffledDeck.get().getCard12(),
                completeShuffledDeck.get().getCard13(), completeShuffledDeck.get().getCard14(),
                completeShuffledDeck.get().getCard15(), completeShuffledDeck.get().getCard16(),
                completeShuffledDeck.get().getCard17(), completeShuffledDeck.get().getCard18(),
                completeShuffledDeck.get().getCard19(), completeShuffledDeck.get().getCard20(),
                completeShuffledDeck.get().getCard21(), completeShuffledDeck.get().getCard22(),
                completeShuffledDeck.get().getCard23(), completeShuffledDeck.get().getCard24(),
                completeShuffledDeck.get().getCard25(), completeShuffledDeck.get().getCard26(),
                completeShuffledDeck.get().getCard27(), completeShuffledDeck.get().getCard28(),
                completeShuffledDeck.get().getCard29(), completeShuffledDeck.get().getCard30()));
        int cardsToRemove = playerCardCount + 2;
        for (int i = 0; i < cardsToRemove; i++) {
            remainingDeck.remove(0);
        }
        return remainingDeck;
    }

    public static ArrayList<String> fillPlayerHand(int cardsInHand, Optional<BlackJackShuffleLog> completeShuffledDeck) {
        ArrayList<String> filledPlayerHand;
        switch (cardsInHand) {
            case 2:
                filledPlayerHand = new ArrayList<>(Arrays.asList(
                        completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3()
                ));
                break;
            case 3:
                filledPlayerHand = new ArrayList<>(Arrays.asList(
                        completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3(), completeShuffledDeck.get().getCard5()
                ));
                break;
            case 4:
                filledPlayerHand = new ArrayList<>(Arrays.asList(
                        completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3(), completeShuffledDeck.get().getCard5(),
                        completeShuffledDeck.get().getCard6()
                ));
                break;
            case 5:
                filledPlayerHand = new ArrayList<>(Arrays.asList(
                        completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3(), completeShuffledDeck.get().getCard5(),
                        completeShuffledDeck.get().getCard6(), completeShuffledDeck.get().getCard7()
                ));
                break;
            case 6:
                filledPlayerHand = new ArrayList<>(Arrays.asList(
                        completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3(), completeShuffledDeck.get().getCard5(),
                        completeShuffledDeck.get().getCard6(), completeShuffledDeck.get().getCard7(), completeShuffledDeck.get().getCard8()
                ));
                break;
            case 7:
                filledPlayerHand = new ArrayList<>(Arrays.asList(
                        completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3(), completeShuffledDeck.get().getCard5(),
                        completeShuffledDeck.get().getCard6(), completeShuffledDeck.get().getCard7(), completeShuffledDeck.get().getCard8(),
                        completeShuffledDeck.get().getCard9()
                ));
                break;
            case 8:
                filledPlayerHand = new ArrayList<>(Arrays.asList(
                        completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3(), completeShuffledDeck.get().getCard5(),
                        completeShuffledDeck.get().getCard6(), completeShuffledDeck.get().getCard7(), completeShuffledDeck.get().getCard8(),
                        completeShuffledDeck.get().getCard9(), completeShuffledDeck.get().getCard10()
                ));
                break;
            case 9:
                filledPlayerHand = new ArrayList<>(Arrays.asList(
                        completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3(), completeShuffledDeck.get().getCard5(),
                        completeShuffledDeck.get().getCard6(), completeShuffledDeck.get().getCard7(), completeShuffledDeck.get().getCard8(),
                        completeShuffledDeck.get().getCard9(), completeShuffledDeck.get().getCard10(), completeShuffledDeck.get().getCard11()
                ));
                break;
            case 10:
                filledPlayerHand = new ArrayList<>(Arrays.asList(
                        completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3(), completeShuffledDeck.get().getCard5(),
                        completeShuffledDeck.get().getCard6(), completeShuffledDeck.get().getCard7(), completeShuffledDeck.get().getCard8(),
                        completeShuffledDeck.get().getCard9(), completeShuffledDeck.get().getCard10(), completeShuffledDeck.get().getCard11(),
                        completeShuffledDeck.get().getCard12()
                ));
                break;
            default:
                filledPlayerHand = new ArrayList<>(Arrays.asList(
                        completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3(), completeShuffledDeck.get().getCard5(),
                        completeShuffledDeck.get().getCard6(), completeShuffledDeck.get().getCard7(), completeShuffledDeck.get().getCard8(),
                        completeShuffledDeck.get().getCard9(), completeShuffledDeck.get().getCard10(), completeShuffledDeck.get().getCard11(),
                        completeShuffledDeck.get().getCard12(), completeShuffledDeck.get().getCard13()
                ));


        }

        return filledPlayerHand;
    }
}
