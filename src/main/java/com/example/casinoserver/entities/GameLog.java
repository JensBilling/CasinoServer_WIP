package com.example.casinoserver.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GameLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int gameId;
    private String userName;
    private String houseCards;
    private String playerCards;
    private int playerBet;
    private int playerBalanceChange;
    private String playerIpAddress;

    public GameLog() {
    }

    public GameLog(int gameId, String userName, String houseCards, String playerCards, int playerBet, int playerBalanceChange, String playerIpAddress) {
        this.gameId = gameId;
        this.userName = userName;
        this.houseCards = houseCards;
        this.playerCards = playerCards;
        this.playerBet = playerBet;
        this.playerBalanceChange = playerBalanceChange;
        this.playerIpAddress = playerIpAddress;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHouseCards() {
        return houseCards;
    }

    public void setHouseCards(String houseCards) {
        this.houseCards = houseCards;
    }

    public String getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(String playerCards) {
        this.playerCards = playerCards;
    }

    public int getPlayerBet() {
        return playerBet;
    }

    public void setPlayerBet(int playerBet) {
        this.playerBet = playerBet;
    }

    public int getPlayerBalanceChange() {
        return playerBalanceChange;
    }

    public void setPlayerBalanceChange(int playerBalanceChange) {
        this.playerBalanceChange = playerBalanceChange;
    }

    public String getPlayerIpAddress() {
        return playerIpAddress;
    }

    public void setPlayerIpAddress(String playerIpAddress) {
        this.playerIpAddress = playerIpAddress;
    }
}
