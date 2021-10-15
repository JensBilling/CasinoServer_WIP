package com.example.casinoserver.transfer;

public class ReceiveUserBet {
    private int userId;
    private int userBet;

    public ReceiveUserBet() {
    }

    public ReceiveUserBet(int userId, int userBet) {
        this.userId = userId;
        this.userBet = userBet;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserBet() {
        return userBet;
    }

    public void setUserBet(int userBet) {
        this.userBet = userBet;
    }
}
