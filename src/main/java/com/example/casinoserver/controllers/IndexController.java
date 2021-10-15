package com.example.casinoserver.controllers;

import com.example.casinoserver.entities.BlackJackShuffleLog;
import com.example.casinoserver.repositories.BlackJackShuffleLogRepository;
import com.example.casinoserver.repositories.UserRepository;
import com.example.casinoserver.service.GameLogicMethods;
import com.example.casinoserver.transfer.ReceiveData;
import com.example.casinoserver.transfer.ReceiveUserBet;
import com.example.casinoserver.transfer.SendingData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
public class IndexController {

    private UserRepository userRepository;
    private BlackJackShuffleLogRepository blackJackShuffleLogRepository;

    public IndexController(UserRepository userRepository, BlackJackShuffleLogRepository blackJackShuffleLogRepository) {
        this.userRepository = userRepository;
        this.blackJackShuffleLogRepository = blackJackShuffleLogRepository;
    }

    // Start new game
    @PostMapping("/start")
    public SendingData startMethod(ReceiveUserBet userBet) {
        String[] shuffledDeck = GameLogicMethods.shuffledBlackjackDeck();
        String gameId = UUID.randomUUID().toString();
        blackJackShuffleLogRepository.save(new BlackJackShuffleLog(gameId, shuffledDeck));
        Optional<BlackJackShuffleLog> completeShuffledDeck = blackJackShuffleLogRepository.findById(gameId);

        ArrayList<String> houseCards = new ArrayList<>(Arrays.asList(completeShuffledDeck.get().getCard2(), completeShuffledDeck.get().getCard4()));
        ArrayList<String> playerCards = new ArrayList<>(Arrays.asList(completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3()));
        ArrayList<String> firstHouseCard = new ArrayList<>(Arrays.asList(houseCards.get(0)));

        SendingData sendingDataObject = new SendingData(gameId, firstHouseCard, playerCards);

        return sendingDataObject;
    }

    // Hit
    @PostMapping("/hit")
    public SendingData hitMethod(@RequestBody ReceiveData receiveDataObject) {
        Optional<BlackJackShuffleLog> completeShuffledDeck = blackJackShuffleLogRepository.findById(receiveDataObject.getGameId());

        ArrayList<String> playerCards = GameLogicMethods.fillPlayerHand(receiveDataObject.getCardsInHand(), completeShuffledDeck);
        ArrayList<String> houseCards = new ArrayList<>(Arrays.asList(completeShuffledDeck.get().getCard2(), completeShuffledDeck.get().getCard4()));
        ArrayList<String> firstHouseCard = new ArrayList<>(Arrays.asList(houseCards.get(0)));

        int playerCardCount = receiveDataObject.getCardsInHand();
        ArrayList<String> remainingDeck = GameLogicMethods.getRemainingDeck(completeShuffledDeck, playerCardCount);
        playerCards.add(remainingDeck.get(0));

        SendingData sendingDataObject = new SendingData(receiveDataObject.getGameId(), firstHouseCard, playerCards);

        if (sendingDataObject.getPlayerValue() == 21){
            sendingDataObject.setWinner("Player");
        } else if (sendingDataObject.getPlayerValue() > 21){
            sendingDataObject.setWinner("House");
        }
        return sendingDataObject;
    }

    // Stand
    @PostMapping("/stand")
    public SendingData standMethod(@RequestBody ReceiveData receiveDataObject){
        receiveDataObject.setPlayerHit(false);
        int playerCardCount = receiveDataObject.getCardsInHand();

        Optional<BlackJackShuffleLog> completeShuffledDeck = blackJackShuffleLogRepository.findById(receiveDataObject.getGameId());

        ArrayList<String> playerCards = GameLogicMethods.fillPlayerHand(playerCardCount, completeShuffledDeck);
        ArrayList<String> houseCards = new ArrayList<>(Arrays.asList(completeShuffledDeck.get().getCard2(), completeShuffledDeck.get().getCard4()));

        SendingData sendingDataObject = GameLogicMethods.calculateWinner(receiveDataObject.getGameId(), houseCards, playerCards, completeShuffledDeck);




        return sendingDataObject;
    }
}
