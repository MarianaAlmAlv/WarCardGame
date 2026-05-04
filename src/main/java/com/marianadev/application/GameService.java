package com.marianadev.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.marianadev.domain.models.Deck;
import com.marianadev.domain.models.Game;
import com.marianadev.domain.models.GameOver;
import com.marianadev.domain.models.Player;
import com.marianadev.domain.models.ResponseHand;
import com.marianadev.domain.models.Card;

public class GameService {

    private GameHistory gameHistory;
    
    public GameService(){
        this.gameHistory= new GameHistory();
    }

    /**
     * Retrieve a new deck
     * @return Deck
     */
    public Deck createDeck() {
        return new Deck();
    }

    /**
     * Shuffle Deck
     */
    public void shuffleDeck(Deck deck) {
        deck.shuffle();
    }

    /**
     * Inicialize a new game
     * - startGame
     * - Returns TaskDTO
     * @param numPlayers
     * @param deck
     * @return Game
     */
    public Game initializeGame(int numPlayers, Deck deck) {
        Game currentGame = new Game(numPlayers, deck);
        currentGame.startGame();
        return currentGame;
    }


     /**
     * Play a hand
     * - play a hand of each player
     * - Returns ResponseHand (cards played, players with max card, needWar)
     * @param players
     * @return ResponseHand
     */
    public ResponseHand playHand(List<Player> players){
        ResponseHand responseHand =new ResponseHand(players);
        List<Player> currentPlayers= players;
        HashMap<Integer, Card> currentHand = new HashMap<>();
        for (Player player : players) {
            var cardPlayed=player.playCard();
            currentHand.put(player.getId(), cardPlayed);
        }
        responseHand.setcardsPlayed(currentHand);
        currentHand= compareCards(currentHand);
        List<Integer> playersIds = new ArrayList<>(currentHand.keySet());
        responseHand.setPlayersHand(filterPlayersById(playersIds, currentPlayers));
        return responseHand;
    }

    /**
     * Compare a HashMap of Cards with player Id
     * - compares for ranking 
     * - Returns HashMap<Integer,Card> with winner card(s) 
     * @param cardsByPlayer
     * @return HashMap<Integer,Card>
     */
    private HashMap<Integer,Card> compareCards(HashMap<Integer,Card> cardsByPlayer){
        HashMap<Integer,Card> maxElement= new HashMap<>();
        Integer lastKey=0;
        for (Integer id : cardsByPlayer.keySet()) {
            if(maxElement.isEmpty()){
                maxElement.put(id, cardsByPlayer.get(id));
                lastKey=id;
            }else{
                Integer compare= compareCardRanking(maxElement.get(lastKey), cardsByPlayer.get(id));
                if(compare<0)
                {
                    maxElement.remove(lastKey);
                    maxElement.put(id, cardsByPlayer.get(id));
                    lastKey=id;
                }            
            }
        }
        return maxElement;
    }

    /**
     * Compare two cards
     * - compares for ranking (value and symbol) 
     * - Returns 
     *      0 equality value
     *      positive card1 max value
     *      negative card2 max value
     * @param c1
     * @param c2
     * @return Integer
     */
    private Integer compareCardRanking(Card c1, Card c2){
        if(c1.getRankingValue()!=c2.getRankingValue())
            return c1.getRankingValue()- c2.getRankingValue();
        return c1.getRankingSymbol() -c2.getRankingSymbol();
    }

     /**
     * Filter list of players from list ids
     * - Returns list of players in playersIds
     * @param playerIds
     * @param players
     * @return List<Player>
     */
    private List<Player> filterPlayersById(List<Integer> playerIds, List<Player> players){
        List<Player> listPlayers= new LinkedList<>();
        for (Player player : players) {
           if( playerIds.contains(player.getId()))
                listPlayers.add(player);
        }
        return listPlayers;
    }

    /**
     * Add cards to queue of player
     * @param player
     * @param handPlayed
     */
    public void addCardsToWinner(Player player, HashMap<Integer,Card> handPlayed){
        for (Card i : handPlayed.values()) {
           player.addCardToHand(i);
        }
    }

     /**
     * Validate end of game
     * Remove players without cards
     * Validate if player has all card in game
     * - Returns Game updated 
     * @param game
     * @return Game
     */
    public Game validateEndGame(Game game)
    {
        if(game.getPlayers().size()==0)
            game.setGameOver(true);
        for(Player player: game.getPlayers()){
            if(!player.hasCards()){
                game.getPlayers().remove(player);
            }else{
                if (player.getHandSize()== game.getCardsInGame()) {
                    game.setGameOver(true);
                }
            }
        }
        return game;
    }

    /**
     * Game over
     * calls save history 
     * @param gameOver
     */
    public void gameOver(GameOver gameOver ){
        gameHistory.saveHistory(gameOver);
    }

     /**
     * Gets game history
     * calls getGameHistory
     * - Returns List of games over
     * @return List<GameOver>
     */
    public List<GameOver>  getGamesRepport(){
       return gameHistory.getGameHistory();
    }

    public Game prepareLastGame(Deck deck){
        GameOver lastGame= gameHistory.getGameHistory().getFirst();
        this.gameHistory= new GameHistory();
        return initializeGame(lastGame.getTotalPlayers(), deck);

    }


     /**
     * Autoplay mode
     * Atomatize play hand until game over
     * Validates if war is needed
     * Validates if game is over
     * - Returns Game Over 
     * @param game
     * @return Game
     */
    public Game AutoPlay(Game game){
        while (!validateEndGame(game).isGameOver) {
            ResponseHand response=playHand(game.getPlayers());
            game.addIteration();
            System.out.println("***** Main joué *****");
            System.out.println(response.getcardsPlayedSring());
            if(response.needWar){
                System.out.println("***** Bataille entre égalitées *****");
                ResponseHand battleHand = playHand(response.playersHand);
                if(battleHand.needWar){
                    validateEndGame(game);
                    playHand(battleHand.playersHand);
                    game.addIteration();
                }
                playHand(response.playersHand);
            }else{
                 System.out.println("Vainqueur: " + response.playersHand.getFirst().getName());
                 addCardsToWinner(response.playersHand.getLast(), response.cardsPlayed);
                 response.setPlayersHand(new LinkedList<>());
            }
            
        }
        return game;
    }


}
