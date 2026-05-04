package com.marianadev.domain.models;

import java.util.List;
import java.util.HashMap;

public class ResponseHand {

    public List<Player> playersGame;
    public boolean needWar;
    public List<Player> playersHand;
    public HashMap<Integer, Card> cardsPlayed;

    public ResponseHand(List<Player> players){
        this.playersGame=players;
        this.needWar=false;
        this.cardsPlayed= new HashMap<>();

    }

    //getters and setters
    public List<Player> getPlayersGame(){
        return this.playersGame;
    }

    public void setPlayersGame(List<Player> playersGame){
        this.playersGame=playersGame;
    }

    public List<Player> getPlayerssHand(){
        return this.playersGame;
    }

    public void setPlayersHand(List<Player> playersHand){
        this.playersHand=playersHand;
        this.needWar=this.playersHand.size()>1;

    }

    public boolean needWar(){
        return this.needWar;
    }

    public void setcardsPlayed(HashMap<Integer, Card> cardsPlayed){
        this.cardsPlayed=cardsPlayed;

    }

    public HashMap<Integer, Card> getcardsPlayed(){
        return this.cardsPlayed;
    }

    //returns cards played to string
    public String getcardsPlayedSring(){
        String cards="";
       for (Integer id : cardsPlayed.keySet()) {
            cards+= "Player "+ id+ " Card " + cardsPlayed.get(id).toString()+".  ";
       }
        return cards;
    }



}
