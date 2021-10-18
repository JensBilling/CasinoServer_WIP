package com.example.casinoserver.controllers;
import com.example.casinoserver.entities.BlackJackShuffleLog;
import com.example.casinoserver.entities.GameLog;
import com.example.casinoserver.entities.User;
import com.example.casinoserver.repositories.BlackJackShuffleLogRepository;
import com.example.casinoserver.repositories.GameLogRepository;
import com.example.casinoserver.repositories.UserRepository;
import com.example.casinoserver.service.GameLogicMethods;
import com.example.casinoserver.transfer.ReceiveData;
import com.example.casinoserver.transfer.ReceiveUserBet;
import com.example.casinoserver.transfer.SendingData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/*
winner return values:
"Player" = player wins
"House" = house wins
"Push" = tied game
"0" = game is still ongoing
"-1" = user balance too low
*/


@RestController
public class IndexController {

    private UserRepository userRepository;
    private BlackJackShuffleLogRepository blackJackShuffleLogRepository;
    private GameLogRepository gameLogRepository;

    public IndexController(UserRepository userRepository, BlackJackShuffleLogRepository blackJackShuffleLogRepository, GameLogRepository gameLogRepository) {
        this.userRepository = userRepository;
        this.blackJackShuffleLogRepository = blackJackShuffleLogRepository;
        this.gameLogRepository = gameLogRepository;
    }

    // Start new game
    @PostMapping("/start")
    public SendingData startMethod(@RequestBody ReceiveUserBet userBet, HttpServletRequest request) {
        int gameUserId = userBet.getUserId();
        int gameUserBet = userBet.getUserBet();

        //check user balance
        if (userRepository.getById(gameUserId).getUserBalance() < gameUserBet){
            SendingData sendingDataObject = new SendingData();
            sendingDataObject.setWinner("-1");
            return sendingDataObject;
        }

        // Subtract bet from user balance
        int userBalancePreBet = userRepository.getById(gameUserId).getUserBalance();
        int userBalancePostBet = userBalancePreBet - gameUserBet;
        userRepository.getById(gameUserId).setUserBalance(userBalancePostBet);

        // Shuffle and deal cards
        String[] shuffledDeck = GameLogicMethods.shuffledBlackjackDeck();
        String gameId = UUID.randomUUID().toString();
        blackJackShuffleLogRepository.save(new BlackJackShuffleLog(gameId, shuffledDeck));
        Optional<BlackJackShuffleLog> completeShuffledDeck = blackJackShuffleLogRepository.findById(gameId);

        ArrayList<String> houseCards = new ArrayList<>(Arrays.asList(completeShuffledDeck.get().getCard2(), completeShuffledDeck.get().getCard4()));
        ArrayList<String> playerCards = new ArrayList<>(Arrays.asList(completeShuffledDeck.get().getCard1(), completeShuffledDeck.get().getCard3()));
        ArrayList<String> firstHouseCard = new ArrayList<>(Arrays.asList(houseCards.get(0)));

        String playerCardsAsString = "";
        String houseCardsAsString = "";

        for (String s: playerCards) {
            playerCardsAsString += s + ", " ;
        }
        for (String s: houseCards) {
            houseCardsAsString += s + ", ";
        }

        // Create game log w/ game id, player bet, player ip, player username
        GameLog gameLog = new GameLog();
        gameLog.setGameId(gameId);
        gameLog.setPlayerBet(gameUserBet);
        gameLog.setPlayerIpAddress(request.getRemoteAddr());
        gameLog.setUserId(userRepository.getById(gameUserId).getUserId());
        gameLog.setGamePhase("Game started");
        gameLog.setPlayerCards(playerCardsAsString);
        gameLog.setHouseCards(houseCardsAsString);
        gameLogRepository.save(gameLog);

        // Return data
        SendingData sendingDataObject = new SendingData(gameId, firstHouseCard, playerCards);

        if (sendingDataObject.getPlayerValue() == 21 && sendingDataObject.getHouseValue() < 21){
            User user = userRepository.getById(gameLog.getUserId());

            sendingDataObject.setWinner("Player");
            sendingDataObject.setHouseCards(houseCards);

            // player blackjack pays 3:2
            double blackJackCalculation = gameLog.getPlayerBet() * 2.5;
            int blackJackPayOut = (int) blackJackCalculation;


            //Logging
            user.setUserBalance(user.getUserBalance() + blackJackPayOut);
            user.setGamesPlayed(user.getGamesPlayed() + 1);
            user.setWinLossSum(user.getWinLossSum() + (blackJackPayOut - gameLog.getPlayerBet()));
            userRepository.save(user);

            gameLog.setGamePhase("Player Blackjack");
            gameLog.setPlayerCards(playerCardsAsString);
            gameLog.setHouseCards(houseCardsAsString);
            gameLog.setPlayerBalanceChange(blackJackPayOut - gameLog.getPlayerBet());
            gameLogRepository.save(gameLog);
        }

            return sendingDataObject;
    }

    // Hit
    @PostMapping("/hit")
    public SendingData hitMethod(@RequestBody ReceiveData receiveDataObject) {
        // Find deck shuffle in DB
        Optional<BlackJackShuffleLog> completeShuffledDeck = blackJackShuffleLogRepository.findById(receiveDataObject.getGameId());

        // Deal cards
        ArrayList<String> playerCards = GameLogicMethods.fillPlayerHand(receiveDataObject.getCardsInHand(), completeShuffledDeck);
        ArrayList<String> houseCards = new ArrayList<>(Arrays.asList(completeShuffledDeck.get().getCard2(), completeShuffledDeck.get().getCard4()));
        ArrayList<String> firstHouseCard = new ArrayList<>(Arrays.asList(houseCards.get(0)));

        // Give player new card
        int playerCardCount = receiveDataObject.getCardsInHand();
        ArrayList<String> remainingDeck = GameLogicMethods.getRemainingDeck(completeShuffledDeck, playerCardCount);
        playerCards.add(remainingDeck.get(0));

        // change game log phase
        GameLog gameLog = gameLogRepository.getById(receiveDataObject.getGameId());
        gameLog.setGamePhase("Player hit");
        gameLogRepository.save(gameLog);


        SendingData sendingDataObject = new SendingData(receiveDataObject.getGameId(), firstHouseCard, playerCards);

        User user = userRepository.getById(gameLog.getUserId());
        String playerCardsAsString = "";
        String houseCardsAsString = "";

        for (String s: playerCards) {
            playerCardsAsString += s + ", " ;
        }
        for (String s: houseCards) {
            houseCardsAsString += s + ", ";
        }

        if (sendingDataObject.getPlayerValue() == 21 && sendingDataObject.getHouseValue() == 21) {
            sendingDataObject.setWinner("Push");
            sendingDataObject.setHouseCards(houseCards);

            // logging
            user.setUserBalance(user.getUserBalance() + gameLog.getPlayerBet());
            user.setGamesPlayed(user.getGamesPlayed() +1);
            userRepository.save(user);

            gameLog.setGamePhase("Push");
            gameLog.setPlayerCards(playerCardsAsString);
            gameLog.setHouseCards(houseCardsAsString);
            gameLog.setPlayerBalanceChange(0);
            gameLogRepository.save(gameLog);
        } else if (sendingDataObject.getPlayerValue() == 21){
            sendingDataObject.setWinner("Player");
            sendingDataObject.setHouseCards(houseCards);

            //Logging
            user.setUserBalance(user.getUserBalance() + gameLog.getPlayerBet() * 2);
            user.setGamesPlayed(user.getGamesPlayed() + 1);
            user.setWinLossSum(user.getWinLossSum() + (gameLog.getPlayerBet() * 2));
            userRepository.save(user);

            gameLog.setGamePhase("Player > House");
            gameLog.setPlayerCards(playerCardsAsString);
            gameLog.setHouseCards(houseCardsAsString);
            gameLog.setPlayerBalanceChange(gameLog.getPlayerBet());
            gameLogRepository.save(gameLog);
        } else if (sendingDataObject.getPlayerValue() > 21){
            sendingDataObject.setWinner("House");
            sendingDataObject.setHouseCards(houseCards);

            // logging
            gameLog.setGamePhase("Player bust");
            gameLog.setPlayerCards(playerCardsAsString);
            gameLog.setHouseCards(houseCardsAsString);
            gameLog.setPlayerBalanceChange(-gameLog.getPlayerBet());
            gameLogRepository.save(gameLog);

            user.setGamesPlayed(user.getGamesPlayed() + 1);
            user.setWinLossSum(user.getWinLossSum() - gameLog.getPlayerBet());
            userRepository.save(user);
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

        GameLog gameLog = gameLogRepository.getById(receiveDataObject.getGameId());
        User user = userRepository.getById(gameLog.getUserId());
        String playerCardsAsString = "";
        String houseCardsAsString = "";

        for (String s: playerCards) {
            playerCardsAsString += s + ", " ;
        }
        for (String s: houseCards) {
            houseCardsAsString += s + ", ";
        }
        gameLog.setHouseCards(houseCardsAsString);
        gameLog.setPlayerCards(playerCardsAsString);

        if (sendingDataObject.getHouseValue() > 21){
            gameLog.setGamePhase("House bust");
            gameLog.setPlayerBalanceChange(gameLog.getPlayerBet());
            gameLogRepository.save(gameLog);

            user.setUserBalance(user.getUserBalance() + gameLog.getPlayerBet() * 2);
            user.setGamesPlayed(user.getGamesPlayed() + 1);
            user.setWinLossSum(user.getWinLossSum() + gameLog.getPlayerBet());
            userRepository.save(user);
        } else if (sendingDataObject.getPlayerValue() > sendingDataObject.getHouseValue()){
            gameLog.setGamePhase("Player > House");
            gameLog.setPlayerBalanceChange(gameLog.getPlayerBet());
            gameLogRepository.save(gameLog);

            user.setUserBalance(user.getUserBalance() + gameLog.getPlayerBet() * 2);
            user.setGamesPlayed(user.getGamesPlayed() + 1);
            user.setWinLossSum(user.getWinLossSum() + gameLog.getPlayerBet());
            userRepository.save(user);
        } else if (sendingDataObject.getPlayerValue() < sendingDataObject.getHouseValue()){
            gameLog.setGamePhase("House > Player");
            gameLog.setPlayerBalanceChange(-gameLog.getPlayerBet());
            gameLogRepository.save(gameLog);

            user.setGamesPlayed(user.getGamesPlayed() + 1);
            user.setWinLossSum(user.getWinLossSum() - gameLog.getPlayerBet());
            userRepository.save(user);
        } else if (sendingDataObject.getPlayerValue() == sendingDataObject.getHouseValue()){
            gameLog.setGamePhase("Push");
            gameLog.setPlayerBalanceChange(0);
            gameLogRepository.save(gameLog);

            user.setUserBalance(user.getUserBalance() + gameLog.getPlayerBet());
            user.setGamesPlayed(user.getGamesPlayed() + 1);
            userRepository.save(user);
        }

        return sendingDataObject;
    }
}
