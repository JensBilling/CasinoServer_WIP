package com.example.casinoserver.transfer;

public class ReceiveData {
    private String gameId;
    private int cardsInHand;
    private boolean playerHit;

    public ReceiveData() {
    }

    public ReceiveData(String gameId, int cardsInHand, boolean playerHit) {
        this.gameId = gameId;
        this.cardsInHand = cardsInHand;
        this.playerHit = playerHit;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public boolean isPlayerHit() {
        return playerHit;
    }

    public void setPlayerHit(boolean playerHit) {
        this.playerHit = playerHit;
    }

    public int getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(int cardsInHand) {
        this.cardsInHand = cardsInHand;
    }
}
