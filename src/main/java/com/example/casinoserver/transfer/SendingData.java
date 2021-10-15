package com.example.casinoserver.transfer;

import com.example.casinoserver.service.GameLogicMethods;

import java.util.ArrayList;

public class SendingData {
    private String gameId;
    private ArrayList<String> houseCards;
    private ArrayList<String> playerCards;
    private int playerValue;
    private int houseValue;
    private String winner;


    public SendingData() {
    }

    public SendingData(String gameId, ArrayList<String> houseCards, ArrayList<String> playerCards) {
        this.gameId = gameId;
        this.houseCards = houseCards;
        this.playerCards = playerCards;
        this.winner = "0";

        this.playerValue = GameLogicMethods.getPlayerCardValue(this);
        this.houseValue = GameLogicMethods.getHouseCardValue(this);

    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public ArrayList<String> getHouseCards() {
        return houseCards;
    }

    public void setHouseCards(ArrayList<String> houseCards) {
        this.houseCards = houseCards;
    }

    public ArrayList<String> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(ArrayList<String> playerCards) {
        this.playerCards = playerCards;
    }

    public int getPlayerValue() {
        return playerValue;
    }

    public void setPlayerValue(int playerValue) {
        this.playerValue = playerValue;
    }

    public int getHouseValue() {
        return houseValue;
    }

    public void setHouseValue(int houseValue) {
        this.houseValue = houseValue;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
