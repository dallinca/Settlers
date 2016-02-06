package shared.model;

import java.util.ArrayList;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.model.items.*;
import shared.model.player.*;
import shared.model.player.exceptions.CannotBuyException;
import shared.model.player.exceptions.InsufficientPlayerResourcesException;
import shared.model.board.Board;
import shared.model.board.Edge;


/**
 * The Game class is essentially a combination of the traditional game class and the bank. It keeps track of how many un-used resource cards and development cards there are.
 * It also handles all transactions a player may make whether through trading or turning in cards after being robbed. 
 * It also keeps track of how many players there are, who is next, and who is the current player.
 */
public class Game {
	private Player[] players = null;
	private Player currentPlayer = null;
	//private Player nextPlayer;
	//private ResourceCard[] resourceDeck = null;
	//private DevelopmentCard[] developmentDeck = null;
	private Board board = null;;
	private Player largestArmy;
	private Player longestRoad;
	private static int numberofPlayers = 4;
	private Bank bank = null;
	private int turnNumber = 0;
	private int versionNumber = 1;
	
	
	
	public Game() {
		
	}
	
	
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
					turnNumber++;
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
	
	/**
	 * This method polls the player to see if the player can build a road and then returns the result to that which called it (most likely the client)
	 * @return a true or false to if the player can build a road there.
	 */
	public boolean canDoCurrentPlayerBuildRoad() {
		return currentPlayer.canDoBuyRoad();	
	}
	
	/*
	 * This method is commented out for now because I believe that if it isn't, then you will have half the roads you need, because the buildRoadOnEdge function below also
	 * eventually calls a method that commands a road to be built. If both canDos and regular methods are run you will run out of roads. So, in the buildRoadOnEdge function 
	 * it now asks for the canDoCurrentPlayerBuildRoad() and the other verification.
	 * 
	 * But the canDoCurrentPlayerBuildRoad() is a good stand-a-lone to tell the client whether or not to gray it out, but when it comes to building the road we will leave it
	 * to the professional methods below. I hope this makes sense.
	 * @pre Player can buy road
	 */
	/*
	public void buyRoad(Edge edge) throws Exception {
		if (canDoCurrentPlayerBuildRoad()) {
			currentPlayer.buyRoad(edge);
		}
		else
			throw new Exception("Can't buy a road");
	}*/
	
	/**
	 * Asks the player if he or she can build a settlement and tells the client that.
	 * @return
	 */
	public boolean canDoCurrentPlayerBuildSettlement() {
		return currentPlayer.canDoBuySettlement();
	}
	
	/**
	 * Asks the player if he or she can build a city and tells the client that.
	 * @return
	 */
	public boolean canDoCurrentPlayerBuildCity() {
		return currentPlayer.canDoBuyCity();
	}
	
	public void buildCity() {
		
	}
	
	/**
	 * Asks the player if he or she can buy a development card and tells the client that.
	 * @return
	 */
	public boolean canDoCurrentPlayerBuyDevelopmentCard() {
		return currentPlayer.canDoBuyDevelopmentCard(bank);
	}
	
	public void buyDevelopmentCard() throws CannotBuyException, InsufficientPlayerResourcesException {
		if (currentPlayer.canDoBuyDevelopmentCard(bank)) {
			currentPlayer.buyDevelopmentCard(bank);
		}
	}
	
	/**
	 * This method stands to show if the player CAN use one of his/her un-used dev Cards of the type you ask it about.
	 * @param devCardType
	 * @return
	 */
	public boolean canDoCurrentPlayerUseDevelopmentCard(DevCardType devCardType) {
		//We need to be able to measure how long a player has owned a card.
		return currentPlayer.canDoPlayDevelopmentCard(turnNumber, devCardType);		
		
	}
	
	/**
	 * This method counts up and returns how many unused cards the player has
	 * @return
	 */
	public int numberUnplayedDevCards(DevCardType devCardType) {
		return currentPlayer.numberUnplayedDevCards(devCardType);
		
	}
	
	
	public boolean useDevelopmentCard(DevCardType devCardType, ResourceType[] resourceType) throws Exception {
		if (currentPlayer.canDoPlayDevelopmentCard(turnNumber, devCardType)) {
			switch (devCardType) {
				case MONOPOLY:
					if (resourceType == null || resourceType.length > 1) {
						throw new Exception("Trying to use monopoly on more than one resource!");
					}
					//Declare a Resource the player wants, and then extract it from all players who have it.
					for (int i = 0; i < players.length; i++) {
						if (players[i] != currentPlayer) {
							//If not the current player, ask the player for an Array list of it's resource card of that type
							currentPlayer.conformToMonopoly(resourceType[0]).addAll(players[i].conformToMonopoly(resourceType[0]));
							players[i].conformToMonopoly(resourceType[0]) = new ArrayList<ResourceCard>();
						}
					}
					setVersionNumber(versionNumber++);
					return doWeHaveAWinner();
				case YEAR_OF_PLENTY:
					//Add two resources of the types specified to the currentPlayers hand
					if (resourceType.length == 1) {
						ResourceCard resource = bank.playerTakeResource(resource.getResourceType());
						//some sneaky idea that realizes conformToMonopoly returns the arraylist of that players cards of a specified type.
						currentPlayer.conformToMonopoly(resourceType[0]).add(resource);
						//repeat
						resource = bank.playerTakeResource(resource.getResourceType());
						currentPlayer.conformToMonopoly(resourceType[0]).add(resource);
					}
					else if (resourceType.length == 2) {
						for (int g = 0; g < resourceType.length; g++) {
							ResourceCard resource = bank.playerTakeResource(resource.getResourceType());
							//some sneaky idea that realizes conformToMonopoly returns the arraylist of that players cards of a specified type.
							currentPlayer.conformToMonopoly(resourceType[g]).add(resource);
						}
					}
					setVersionNumber(versionNumber++);
					return doWeHaveAWinner();
				default: throw new Exception("Wrong declaration for a development card of this type.");
			}
		}
	}
	
	public boolean useDevelopmentCard(DevCardType devCardType) throws Exception {
		if (currentPlayer.canDoPlayDevelopmentCard(turnNumber, devCardType)) {
		//Marks the card as played
		currentPlayer.playDevelopmentCard(turnNumber, devCardType);
		
		switch (devCardType) {
			case SOLDIER:
				//Must move robber to a different hex
				//Steal Resource from a person
				//Do they have more than three soldiers? Do they have the most soldiers? If so, award them the Largest Army award (assuming they don't already have it) and take it from the previous title holder
				//if (currentPlayer.)
				
				//Did the Largest Army award win the game?!
				setVersionNumber(versionNumber++);
				return doWeHaveAWinner();
				
			case MONUMENT:
				//Give the player their due reward
				currentPlayer.incrementVictoryPoints();
				
				//Mark the monument card as used
				currentPlayer.get
				setVersionNumber(versionNumber++);
				return doWeHaveAWinner();
				
			case ROAD_BUILD:
				//Perhaps to avoid the cost of the roads, we can have an overridden method that asserts true on the canDoBuy and asserts true and avoids the cost. Who knows
				
				setVersionNumber(versionNumber++);
				//Did the two extra roads with the game?!?! This may result in the longest road being awarded. 
				return doWeHaveAWinner();
		}
		
		}
	
		else 
			throw new Exception("Cannot Play development card!");
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canDoCurrentPlayerDoMeritimeTrade() {
		//If a player is to meritime trade, he needs to have resources to trade. He needs to have either four of that kind, or a three for one port or a two for one, and again have the proper resources
		if (currentPlayer.getResourceCardHandSize() == 0)
			return false;
		currentPlayer.
		
	}
	
	public void doMeritimeTrade()  {
		
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
	 
	public void doDomesticTrade() {
		
	}
	
	
	//
	//
	//
	//
	//
	//
	
	/**
	 * @pre the edge location is not null
	 * @param edgeLocation
	 * @post it tells you whether or not you can build a road on that edge
	 */
	public boolean canDoPlaceRoadOnEdge(EdgeLocation edgeLocation) {
		return board.canDoPlaceRoadOnEdge(currentPlayer, edgeLocation);
	}
	
	/**
	 * @pre The can do is true
	 * @param edgeLocation
	 * @throws Exception
	 * @post a road is placed on an edge
	 */
	public void placeRoadOnEdge(EdgeLocation edgeLocation) throws Exception {
		if(canDoPlaceRoadOnEdge(edgeLocation) && canDoCurrentPlayerBuyRoad())
			board.placeRoadOnEdge(currentPlayer, edgeLocation);
		else
			throw new Exception("Cannot build road on this edge, this should not have been allowed to get this far.");
	}
	
	/**
	 * @pre the vertex location is not null
	 * @param vertexLocation
	 * @post it tells you whether or not you can build a Settlement on that vertex
	 */
	public boolean canDoPlaceSettlementOnVertex(VertexLocation vertexLocation) {
		return board.canDoPlaceSettlementOnVertex(currentPlayer, vertexLocation);
	}
	
	/**
	 * @pre the Can do is true
	 * @param vertexLocation
	 * @throws Exception
	 * @post a settlement is placed on a vertex
	 */
	public void placeSettlementOnVertex(VertexLocation vertexLocation) throws Exception {
		if(canDoPlaceSettlementOnVertex(vertexLocation) && canDoCurrentPlayerBuildSettlement())
			board.placeSettlementOnVertex(currentPlayer, vertexLocation);
		else
			throw new Exception("Cannot build Settlement on this vertex, this should not have been allowed to get this far.");
	}
	
	/**
	 * @pre the vertex location is not null
	 * @param vertexLocation
	 * @post it tells you whether or not you can build a Settlement on that vertex
	 */
	public boolean canDoPlaceCityOnVertex(VertexLocation vertexLocation) {
		return board.canDoPlaceCityOnVertex(currentPlayer, vertexLocation);
	}
	
	/**
	 * @pre the Can do is true
	 * @param vertexLocation
	 * @throws Exception
	 * @post a city is placed on a vertex
	 */
	public void placeCityOnVertex(VertexLocation vertexLocation) throws Exception {
		if(canDoPlaceCityOnVertex(vertexLocation))
			board.placeCityOnVertex(currentPlayer, vertexLocation);
		else
			throw new Exception("Cannot build City on this vertex, this should not have been allowed to get this far.");
	}
	
	public int numberOfResouceType(ResourceType resourceType) {
		//need a hand?
		return currentPlayer.numberOfResourceType(resourceType);
	}
	
	public void discardNumberOfResourceType(int removal, ResourceType resourceType) {
		currentPlayer.discardNumberOfResourceType(removal, resourceType);
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
			if (players[i].getVictoryPoints() >= 10)
				return true;
		}
		return false;
	}
	
	public void setVersionNumber(int version) {
		versionNumber = version;
	}
	public int getVersionNumber() {
		return versionNumber;
	}
	
	
}
