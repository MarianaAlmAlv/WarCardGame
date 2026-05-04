package com.marianadev.domain.models;

public class Card {
    private  Value valueCard;
    private  Symbol symbol;


    public Card(Value valueCard, Symbol symbol) {
        this.valueCard = valueCard;
        this.symbol = symbol;
    }

    //setters and getters
    public Value getValue() {
        return valueCard;
    }

    public void setValue(Value valueCard) {
        this.valueCard = valueCard;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    //Get ranking card by value
    public int getRankingValue() {
        return valueCard.getRanking();
    }

    //Get ranking card by symbol
    public int getRankingSymbol() {
        return symbol.getRanking();
    }

    //returns card to string
    @Override
    public String toString(){
        return valueCard.getValue() + symbol.getSymbol();
    }
}
