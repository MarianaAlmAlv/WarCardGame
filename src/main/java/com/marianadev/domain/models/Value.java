package com.marianadev.domain.models;

//ranking by value
public enum Value {
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
    ACE("A", 14);

    private final String value;
    private final int ranking;

    Value(String value, int ranking) {
        this.value = value;
        this.ranking = ranking;
    }

    //getters and setters
    public String getValue() {
        return value;
    }

    public int getRanking() {
        return ranking;
    }
}
