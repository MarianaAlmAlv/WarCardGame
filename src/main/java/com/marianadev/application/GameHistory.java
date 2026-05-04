package com.marianadev.application;

import java.util.List;

import com.marianadev.domain.models.GameOver;
import com.marianadev.infrastructure.Repositories.GameRepository;

public class GameHistory {

    private GameRepository repository;

    public GameHistory(){
        repository= new GameRepository();
    }
    
    /*
    *Saves game over in database
    */
    public void saveHistory(GameOver game){
        repository.saveGame(game); 
    }

    /*
    *Gets all games of the database
    */
    public List<GameOver> getGameHistory(){
        return repository.getAllGames();
    }


}
