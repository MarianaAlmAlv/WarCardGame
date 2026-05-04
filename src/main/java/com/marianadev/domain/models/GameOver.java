package com.marianadev.domain.models;

import java.time.LocalDateTime;

public class GameOver {
    private LocalDateTime createdAt;
    private Integer totalPlayers;
    private String winnerName;
    private Integer winner;

    public GameOver(Integer totalPlayers, Integer winner, LocalDateTime createAt){
        this.totalPlayers=totalPlayers;
        this.winnerName="Player "+winner;
        this.winner=winner;
        this.createdAt=createAt;

    }

    public GameOver(Integer totalPlayers, Integer winner){
        this.totalPlayers=totalPlayers;
        this.winnerName="Player "+winnerName;
        this.winner=winner;

    }

    //setters and getters
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createAt){
        this.createdAt=createAt;
    }

    public Integer getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(Integer totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

     public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }


    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }


    

}
