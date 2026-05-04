package com.marianadev.infrastructure.Repositories;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.persister.collection.mutation.RowMutationOperations.Restrictions;

import com.marianadev.domain.models.GameOver;
import com.marianadev.infrastructure.Config.SessionFactoryProvider;
import com.marianadev.infrastructure.Entities.GameEntity;

public class GameRepository {

    /**
     * save Game in database
     * @param gameP
     */
    public void saveGame(GameOver gameP) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            GameEntity game=new GameEntity();
            game.setCreatedAt(LocalDateTime.now());
            game.setTotalPlayers(gameP.getTotalPlayers());
            game.setWinner(gameP.getWinner());
            transaction = session.beginTransaction();
            session.persist(game);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Get All Games from data base
     * - Returns List<GameOver>
     * @return List<GameOver>
     */
    public List<GameOver> getAllGames() {
        List<GameOver> all= new LinkedList<>();
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            List<GameEntity> games= session.createQuery("from GameEntity g order by g.createdAt desc", GameEntity.class).list();
            
            for(GameEntity g: games){
                all.add(new GameOver(g.getTotalPlayers(), g.getWinner(), g.getCreatedAt()));
            }
            return all;
        }
    }

}
