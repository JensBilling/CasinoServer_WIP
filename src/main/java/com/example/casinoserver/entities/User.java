package com.example.casinoserver.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String userName;
    private String userPassword;
    private int userBalance;
    private int gamesPlayed;
    private int winLossSum;

    public User() {
    }

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userBalance = 10000;
        this.gamesPlayed = 0;
        this.winLossSum = 0;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public int getUserBalance() {
        return userBalance;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getWinLossSum() {
        return winLossSum;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserBalance(int userBalance) {
        this.userBalance = userBalance;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setWinLossSum(int winLossSum) {
        this.winLossSum = winLossSum;
    }
}
