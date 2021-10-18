package com.example.casinoserver.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GameLog {

    @Id
    private String gameId;
    private int userId;
    private String houseCards;
    private String playerCards;
    private int playerBet;
    private int playerBalanceChange;
    private String playerIpAddress;
    private String gamePhase;

    public GameLog() {
    }

    public GameLog(String gameId, int userId, String houseCards, String playerCards, int playerBet, int playerBalanceChange, String playerIpAddress) {
        this.gameId = gameId;
        this.userId = userId;
        this.houseCards = houseCards;
        this.playerCards = playerCards;
        this.playerBet = playerBet;
        this.playerBalanceChange = playerBalanceChange;
        this.playerIpAddress = playerIpAddress;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(String gamePhase) {
        this.gamePhase = gamePhase;
    }
}
