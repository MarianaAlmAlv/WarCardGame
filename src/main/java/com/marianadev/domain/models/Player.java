package com.marianadev.domain.models;

import java.util.LinkedList;
import java.util.Queue;

public class Player {
    public Integer id;
    public String name;
    public Queue<Card> hand;


    public Player(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.hand = new LinkedList<>();
    }

    //setters and getters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //add new card to hand
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public Queue<Card> getHand() {
        return hand;
    }

    public void setHand(Queue<Card> hand) {
        this.hand = hand;
    }

    //play (poll a card from player)
    public Card playCard() {
        return hand.poll();
    }

    //validates if has cards
    public boolean hasCards() {
        return !hand.isEmpty();
    }

    //return total of cards
    public int getHandSize() {
        return hand.size();
    }
}


