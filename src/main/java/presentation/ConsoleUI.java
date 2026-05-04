package presentation;

import java.util.List;
import java.util.Scanner;
import com.marianadev.application.GameService;
import com.marianadev.domain.models.Deck;
import com.marianadev.domain.models.Game;
import com.marianadev.domain.models.GameOver;
import com.marianadev.domain.models.Player;
import com.marianadev.domain.models.ResponseHand;
import com.marianadev.domain.models.Card;

public class ConsoleUI {

    private Scanner scanner;
    private GameService gameService;
    private Deck currentDeck;
    private Game currentGame;
    private ResponseHand currentHand;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.gameService = new GameService();
        this.currentDeck = gameService.createDeck();
    }

    public void start() {
        displayMainMenu();
    }

    /**
     * Shows main menu
     */
    private void displayMainMenu(){
        try {
            System.out.println("***** Menu principal *****");
            System.out.println("1.  Afficher les cartes");
            System.out.println("2.  Mélanger les cartes");
            System.out.println("3.  Commencer la partie");
            System.out.println("4.  Jouer une partie avec les joueurs presedents");
            System.out.println("5.  Consulter les parties sauvegardées en base de données");
            int choice = scanner.nextInt();
            handleMainMenuChoice(choice);
        } catch (Exception e) {
           System.err.println("Une erreur est survenue : " + e.getMessage());
        }
        finally{
            scanner.close();
        }
    }

     /**
     * Handle choice of main menu
     */
    private void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                // Afficher les cartes
                displayCards(this.currentDeck);
                displayMainMenu();
                break;
            case 2:
                // Mélanger les cartes
                gameService.shuffleDeck(this.currentDeck);
                displayCards(this.currentDeck);
                displayMainMenu();
                break;
            case 3:
                // Commencer la partie
                int numPlayers = askNumberOfPlayers();
                gameService.shuffleDeck(this.currentDeck);
                this.currentGame = gameService.initializeGame(numPlayers, this.currentDeck);
                this.currentHand=new ResponseHand(currentGame.getPlayers());
                displayPlayMenu();
                break;
            case 4:
                // Jouer une partie avec les joueurs précédents
                gameService.shuffleDeck(this.currentDeck);
                this.currentGame=gameService.prepareLastGame(currentDeck);
                this.currentHand=new ResponseHand(currentGame.getPlayers());
                displayPlayMenu();
                break;
            case 5:
                // Consulter les parties sauvegardées en base de données
                displayRepport();
                displayMainMenu();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }


     /**
     * Handle ask number of players
     */
    private int askNumberOfPlayers(){
        try {
            System.out.println("Entrez le nombre de joueurs (2-4)");
            int numPlayers = scanner.nextInt();
            while (numPlayers < 2 || numPlayers > 4) {
                System.out.println("Nombre de joueurs invalide. Veuillez entrer un nombre entre 2 et 4.");
                numPlayers = scanner.nextInt();
            }
            return numPlayers;
        } catch (Exception e) {
            System.err.println("Une erreur est survenue : " + e.getMessage());
            scanner.close();
            return 0;
        }
    }

     /**
     * Shows play menu
     */
    private void displayPlayMenu(){
        try { 
            System.out.println("***** Menu jeu *****");
            System.out.println("1.  Main Suivante");
            System.out.println("2.  Afficher la distribution des cartes par joueur");
            System.out.println("3.  Automatiser les mains jusqu'à la fin de la partie");
            int choice = scanner.nextInt();
            handlePlayMenuChoice(choice);
        } catch (Exception e) {
            System.err.println("Une erreur est survenue : " + e.getMessage());
        }
        finally{
            scanner.close();
        }

    }

     /**
     * Handle choice of play menu
     */
    private void handlePlayMenuChoice(int choice){
        switch (choice) {
            case 1:
                // Main suivante
                this.currentHand= gameService.playHand(currentGame.getPlayers());
                handleHand(currentHand);
                this.currentGame=gameService.validateEndGame(currentGame);
                if(currentGame.isGameOver){
                    displayEndGame();
                }
                displayPlayMenu();
                break;
            case 2:
                // Afficher la distribution des cartes par joueur
                displayPlayerHands(currentGame.getPlayers());
                displayPlayMenu();
                break;
            case 3:
                // Automatiser les mains jusqu'à la fin de la partie
                displayAutoPlay();
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }

     /**
     * Show current deck
     */
    private void displayCards(Deck deck){
        System.out.println("***** Cartes disponibles *****");
        for (Card card :deck.getCards()) {
            System.out.println(card.toString());
        }
    }

    /**
    * Show card of each player
    */
    private void displayPlayerHands(List<Player> players){
        System.out.println("***** Distribution des cartes par joueur *****");
        for (Player player : players) {
            System.out.println(player.getName() + " Quantité total de cartes: "+player.getHandSize());
            System.out.println(player.getHand().toString());
            System.out.println("");
        }
    }

    /**
    * Handle each hand played
    */
    private void handleHand(ResponseHand responseHand){
        currentGame.addIteration();
        System.out.println("***** Main joué *****");
        System.out.println(responseHand.getcardsPlayedSring());
        if (responseHand.needWar) {
            System.out.println("***** Bataille entre égalitées *****");
            ResponseHand battleHand = gameService.playHand(currentHand.playersHand);
            if(battleHand.needWar){
                handleHand(battleHand);
            }
        }else
        {
            System.out.println("Vainqueur: " + currentHand.playersHand.getFirst().getName());
            gameService.addCardsToWinner(currentHand.playersHand.getFirst(), responseHand.getcardsPlayed());
            return;
        }
    }

     /**
    * Show end of game
    */
    private void displayEndGame(){
        System.out.println("***** Game Over *****");
        Integer winner=0;
        if(currentGame.players.size()>0){
            System.out.println("Gagnant " +currentGame.getPlayers().getFirst().getName());
            winner= currentGame.getPlayers().getFirst().getId();
        } 
        else
            System.out.println("Match null");
        gameService.gameOver(new GameOver(currentGame.getTotalPlayers(), winner));
        displayMainMenu();
    }

     /**
    * Show games played
    */
    private void displayRepport(){
        System.out.println("***** Parties précedentes *****");
        List<GameOver> games= gameService.getGamesRepport();
        for(GameOver gameOver: games){
            System.out.println("Date: "+ gameOver.getCreatedAt() +" Total players: "+ gameOver.getTotalPlayers() +" Winner: " +gameOver.getWinnerName() );
        }
    }

     /**
    * Show autoplay mode
    */
    private void displayAutoPlay(){
        System.out.println("***** Mode Auto Playing..... *****");
        Game game=gameService.AutoPlay(currentGame);
        if(game.isGameOver){
            this.currentGame= game;
            displayEndGame();
        }

    }

    

}
