package logic;

import java.util.ArrayList;

/**
 * Created by up201404990 on 25-05-2017.
 */
public class GameLogic {
    /*test variables*/
    WhiteCard wCard;
    Player player;

    /*test variables*/

    Board board;
    public static final int PLAYERS_PICKING = 0, PICK_WINNER = 1, END_ROUND = 2;
    public static int gameState;
    public static final int WHITECARDS_PER_PLAYER = 5;

    public GameLogic(int gameState){
        board  =  new Board();
        this.gameState = gameState;
    }

    public void makePlay(){

        board.pickBlackCard();
        board.dealWhiteCards(WHITECARDS_PER_PLAYER);

        if(gameState == PLAYERS_PICKING){
            for(int i = 0; i < board.players.size(); i++){
                playerPickedCard(player ,wCard);
            }
        }

        if(gameState == PICK_WINNER){
            selectWinner(wCard);
        }

        if(gameState == END_ROUND){
            endRound();
        }
    }

    public void playerPickedCard(Player player, WhiteCard whiteCard){
        player.removeCard(whiteCard);
        board.addWhiteCardToBoard(whiteCard);
    }


    public void selectWinner(WhiteCard winnerCard){
        winnerCard.getOwner().addPoints(1);
    }


    public void endRound(){
        board.clearBoard();
    }







}