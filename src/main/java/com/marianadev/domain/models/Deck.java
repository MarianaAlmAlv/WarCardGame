package com.marianadev.domain.models;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards;

    //create deck (prepare 52 cards)
    public Deck() {
        cards = new ArrayList<>();
        for (Symbol symbol : Symbol.values()) {
            for (Value value : Value.values()) {
                cards.add(new Card(value, symbol));
            }
        }
    }

    //setters and getters
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    //shuffle list of cards
    public void shuffle() {
       Collections.shuffle(this.cards);
    }
}