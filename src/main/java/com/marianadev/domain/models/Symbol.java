package com.marianadev.domain.models;

//ranking by color(symbol)
public enum Symbol {
    HEARTS("♥ Coeur Rouge", 1),
    DIAMONDS("♦ Carreau Rouge", 2),
    CLUBS("♣ Trefle Noir", 3),
    SPADES("♠ Pique Noir", 4);

    private final String symbol;
    private final int ranking;

    Symbol(String symbol, int ranking) {
        this.symbol = symbol;
        this.ranking = ranking;
    }

    //getters and setters
    public String getSymbol(){
        return symbol;
    }

    public int getRanking(){
        return ranking;
    }

}