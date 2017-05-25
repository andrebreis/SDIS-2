package logic.login;

import logic.Player;

import java.util.ArrayList;

public class Room{

    private String id;
    private boolean gameStarted, gameEnded;
    private ArrayList<Player> players;
    private int numPlayers;

    public Room(String id) {
        this(id, 0, false, false);
    }

    public Room(String id, int players, boolean gameStarted, boolean gameEnded) {

        this.id = id;
        this.numPlayers = players;
        this.players = new ArrayList<>();
        this.gameStarted = gameStarted;
        this.gameEnded = gameEnded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public void addPlayer(Player p) {
        if(!players.contains(p))
            this.players.add(p);
        this.numPlayers = players.size();
    }
    
    public void removePlayer(Player p) {
        for(int i = 0; i < this.players.size(); i++)
            if(players.get(i).equals(p)) players.remove(i);
        this.numPlayers = players.size();
    }
}