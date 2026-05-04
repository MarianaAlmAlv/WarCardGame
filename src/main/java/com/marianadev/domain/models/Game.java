package com.marianadev.domain.models;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Deck deck;
    public List<Player> players;
    public boolean isGameOver;
    public int totalPlayers;
    public int cardsInGame;
    public int totalIterations;


    //setters and getters
    public Game(int totalPlayers, Deck deckShuffled) {
        setPlayers(totalPlayers);
        setDeck(deckShuffled);
        setGameOver(false);
        setTotalIterations(0);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(int totalPlayers) {
        setTotalPlayers(totalPlayers);
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 1; i <= totalPlayers; i++) {
            players.add(new Player(i, "Player " + i));
        }
        this.players = players;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public int getCardsInGame() {
        return cardsInGame;
    }

    public void setCardsInGame(int cardsInGame) {
        this.cardsInGame = cardsInGame;
    }

    public int getTotalIterations() {
        return totalIterations;
    }

    public void setTotalIterations(int totalIterations) {
        this.totalIterations = totalIterations;
    }

    /**
     * Distribute cards from the deck to players
     * - validate cards per player (same quantity)
     * - set total cards in game
     */
    private void distributeCards() {
       int cardsPerPlayer = deck.getCards().size() / totalPlayers;
       setCardsInGame(cardsPerPlayer * totalPlayers);
       int playerIndex = 0;
       for(Card card: deck.getCards()){
            if (playerIndex>=players.size()) {
                playerIndex=0;
            }
            if (players.get(playerIndex).getHandSize() < cardsPerPlayer) {
                players.get(playerIndex).addCardToHand(card);
                playerIndex++;
            }
        }
    }

    public void startGame() {
        distributeCards();
    }

    public void addIteration() {
        setTotalIterations(getTotalIterations() + 1);
    }

    public void endGame() {
        setGameOver(true);
    }

}
