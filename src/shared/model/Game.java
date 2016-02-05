package shared.model;

import java.util.ArrayList;

import shared.model.items.DevelopmentCard;
import shared.model.items.Municipal;
import shared.model.items.ResourceCard;
import shared.model.items.Road;
import shared.model.items.*;
import shared.model.player.*;
import shared.model.board.Board;


/**
 * The Game class is essentially a combination of the traditional game class and the bank. It keeps track of how many un-used resource cards and development cards there are.
 * It also handles all transactions a player may make whether through trading or turning in cards after being robbed. 
 * It also keeps track of how many players there are, who is next, and who is the current player.
 */
public class Game {
	private Player[] players = null;
	private Player currentPlayer;
	//private Player nextPlayer;
	//private ResourceCard[] resourceDeck = null;
	//private DevelopmentCard[] developmentDeck = null;
	private Board board;
	private Player largestArmy;
	private Player longestRoad;
	private static int numberofPlayers = 4;
	private Bank bank;
	
	
	
	
	/**
	 * @pre the player objects passed in are not null, neither is the Board.
	 * 
	 * @param the four player objects to be added to the array.
	 */
	public Game(Player one, Player two, Player three, Player four, Board board1) {
		players = new Player[numberofPlayers];
		players[0] = one;
		players[1] = two;
		players[2] = three;
		players[3] = four;
		
		currentPlayer = players[0];
		bank = new Bank();
		board = board1;
	}
	
	/**
	 * This method will cycle through the array of players and will rotate them through the currentPlayer so the turns can proceed.
	 * This will be helpful when the index of the player array is [3] and we need to bring it back to [0] showing that that person is next.
	 * @pre the players array is not null
	 * @post the next player is set.
	 */
	public void incrementPlayer() {
		for (int i = 0; i < numberofPlayers; i++) {
			if (currentPlayer == players[i]) {
				if (i == numberofPlayers-1) {
					setCurrentPlayer(players[0]);
					return;
				} else {
					setCurrentPlayer(players[i+1]);
					return;
				}
			}
		}
	}
	
	
	/**
	 * This method may be beneficial in the event of some other aspect of the game asking which player's turn it is. Perhaps whether or not to enable the starting of a trade on a players turn.
	 * 
	 * @pre the game has started and a turn has started. 
	 * 
	 * 
	 * @return the CurrentPlayer object
	 */
	public Player getCurrentPlayer() {
		//return the name of the player or the player object itself?
		//What would be the purpose of sending something a player?
		return currentPlayer;
	}
	
	/**
	 * Every time a turn starts, this method is called.
	 * 
	 * @pre a turn has started and the game has started
	 * @post we know who's turn it is.
	 * 
	 */
	private void setCurrentPlayer(Player setPlayer) {
		currentPlayer = setPlayer;
	}
	
	public boolean canDoCurrentPlayerBuildRoad() {
		return currentPlayer.canDoBuyRoad();
		
	}
	
	public boolean canDoCurrentPlayerBuildSettlement() {
		return currentPlayer.canDoBuySettlement();
	}
	
	public boolean canDoCurrentPlayerBuildCity() {
		return currentPlayer.canDoBuyCity();
	}
	
	public boolean canDoCurrentPlayerBuyDevelopmentCard() {
		return currentPlayer.canDoBuyDevelopmentCard(bank);
	}
	
	public boolean canDoCurrentPlayerUseDevelopmentCard() {
		//We need to be able to measure how long a player has owned a card.
		//I'm also under the impression that you cannot play monument cards.
		return currentPlayer.ca
	}
	
	public boolean canDoCurrentPlayerDoMeritimeTrade() {
		//If a player is to meritime trade, he needs to have resources to trade. He needs to have either four of that kind, or a three for one port or a two for one, and again have the proper resources
		if (currentPlayer.getResourceCardHandSize() == 0)
			return false;
		currentPlayer.
		
	}
	/**
	 * @pre: the player in question who calls this method is taking his/her turn currently
	 * @post 
	 */
	public boolean canDoCurrentPlayerDoDomesticTrade() {
		//Is it the Current Players turn and does he have any resources?
		//
		if (currentPlayer.getResourceCardHandSize() == 0)
			return false;
		else
			return true;
	}
	 
	
	
	
		
	/**
	 * This method is responsible for verifying if the game has ended.
	 * 
	 * This method will check after every action if the current player has ten or eleven points. If they do, it returns true.
	 * And then the game is over
	 * 
	 * @pre an action must have just occurred (build a road, play a development card, build a city or settlement, etc.)
	 * 
	 * @post a boolean value as to whether or not the game is over
	 */
	public boolean doWeHaveAWinner() {
		for (int i = 0; i < numberofPlayers; i++) {
			//Player.java needs to have a method that returns victory points so this method can access them via the players to test for it.
			//Player.java also needs to have public or private set for some of the methods, one of which being the method below, which should probably
			//return an integer and not be void
			if (players[i].totalVictoryPoints(0) >= 10)
				return true;
		}
		return false;
	}
	
	
}
