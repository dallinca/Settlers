package shared.model;

import java.util.ArrayList;
import java.util.Random;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.items.*;
import shared.model.player.*;
import shared.model.player.exceptions.CannotBuyException;
import shared.model.player.exceptions.InsufficientPlayerResourcesException;
import shared.model.turn.Dice;
import shared.model.board.Board;
import shared.model.board.Edge;
import shared.model.board.Hex;
import shared.model.board.Vertex;


/**
 * The Game class is essentially a combination of the traditional game class and the bank. It keeps track of how many un-used resource cards and development cards there are.
 * It also handles all transactions a player may make whether through trading or turning in cards after being robbed. 
 * It also keeps track of how many players there are, who is next, and who is the current player.
 */
public class Game {
	private boolean inSetUpPhase = true; // This is a state boolean for the first two setup rounds
	private Player[] players = null;
	private Player currentPlayer = null;
	private Board board = null;;
	private Player largestArmy;
	private Player longestRoad;
	private static int numberofPlayers = 4;
	private Bank bank = null;
	private int turnNumber = 0;
	private int versionNumber = 1;
	private int indexOfLargestArmy = -1;
	private Dice dice = new Dice();
	
	// CONSTRUCTORS
	//////////////////////////////////////////
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

	// CURRENT PLAYER CALLS
	//////////////////////////////////////////
	/**
	 * This method will cycle through the array of players and will rotate them through the currentPlayer so the turns can proceed.
	 * This will be helpful when the index of the player array is [3] and we need to bring it back to [0] showing that that person is next.
	 * @pre the players array is not null
	 * @post the next player is set.
	 */
	public void incrementPlayer() {
		// If we are done with the first two rounds of the Game (for setup
		for (int i = 0; i < numberofPlayers; i++) {
			if (currentPlayer.getPlayerId() == players[i].getPlayerId()) {
				//players[i] = currentPlayer; // This should probably be omitted
				// If we are no longer in the setup phase
				if(inSetUpPhase == false) {
					if (i == numberofPlayers-1) {
						setCurrentPlayer(players[0]);
						turnNumber++;
						return;
					} else {
						setCurrentPlayer(players[i+1]);
						return;
					}
				}
				// We must still be in the setup phase
				else {
					// We are still in the first round
					if(turnNumber == 0) {
						// If we are on the last person in the round, he/she gets to go again
						if(i == numberofPlayers - 1) {
							setCurrentPlayer(players[numberofPlayers - 1]); // Could probably omit this line
							turnNumber++;
							return;
						} else {
							setCurrentPlayer(players[i+1]);
							return;
						}
					}
					// We are still in the second round
					else if(turnNumber == 1) {
						if(i == 0) {
							setCurrentPlayer(players[0]);
							inSetUpPhase = false;
							return;
						} else {
							setCurrentPlayer(players[i-1]);
							return;
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * This method may be beneficial in the event of some other aspect of the game asking which player's turn it is. Perhaps whether or not to enable the starting of a trade on a players turn.
	 * 
	 * @pre the game has started and a turn has started. 
	 * 
	 * @return the CurrentPlayer object
	 */
	public Player getCurrentPlayer() {
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

	// ROLL CALLS
	//////////////////////////////////////////
	
	/**
	 * Checks to see if the specified player can roll the dice
	 * 
	 * @pre None
	 * @param UserId
	 * @return whether the specified player can roll the dice
	 */
	public boolean canDoRollDice(int UserId) {
		// Check if the user is the current player
		if(UserId != currentPlayer.getPlayerId()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Rolls the Dice
	 * 
	 * @param UserId
	 * @throws Exception
	 * @return the roll number
	 */
	public int RollDice(int UserId) throws Exception {
		if(canDoRollDice(UserId) == false) {
			throw new Exception("canDoRollDice == false");
		}
		Random ran = new Random();
		int rollValue = ran.nextInt(6) + ran.nextInt(6) + 2;
		// If the roll is not a 7 then we have the players all collect their resources
		if(rollValue != 7) {
			playersCollectResources(rollValue);
		}
		// If the roll is a seven, tell the client and wait for attempts to move the robber
		return rollValue;
	}
	
	/**
	 * The players all collect resources on the specified rollValue, from the given Bank
	 * 
	 * @param rollValue
	 * @throws Exception
	 */
	private void playersCollectResources(int rollValue) throws Exception {
		if(players == null) {
			throw new Exception("The players Array is null, cannot have players collect Resources");
		}
		for(Player player: players) {
			player.collectResources(rollValue, bank);
		}
	}
	
	
	// RESOURCE BAR CAN-DO CALLS
	//////////////////////////////////////////
	
	/**
	 * This method polls the player to see if the player can build a road and then returns the result to that which called it (most likely the client)
	 * @return a true or false to if the player can build a road there.
	 */
	public boolean canDoCurrentPlayerBuildRoad(int UserId) {
		// Check if the user is the current player
		if(UserId != currentPlayer.getPlayerId()) {
			return false;
		}
		return currentPlayer.canDoBuyRoad();	
	}
	
	/**
	 * Asks the player if he or she can build a settlement and tells the client that.
	 * @return
	 */
	public boolean canDoCurrentPlayerBuildSettlement(int UserId) {
		// Check if the user is the current player
		if(UserId != currentPlayer.getPlayerId()) {
			return false;
		}
		return currentPlayer.canDoBuySettlement();
	}
	
	/**
	 * Asks the player if he or she can build a city and tells the client that.
	 * @return
	 */
	public boolean canDoCurrentPlayerBuildCity(int UserId) {
		// Check if the user is the current player
		if(UserId != currentPlayer.getPlayerId()) {
			return false;
		}
		return currentPlayer.canDoBuyCity();
	}
	
	/**
	 * Asks the player if he or she can buy a development card and tells the client that.
	 * @return
	 */
	public boolean canDoCurrentPlayerBuyDevelopmentCard(int UserId) {
		// Check if the user is the current player
		if(UserId != currentPlayer.getPlayerId()) {
			return false;
		}
		return currentPlayer.canDoBuyDevelopmentCard(bank);
	}

	// DEVELOPMENT CARD ACTION CALLS
	//////////////////////////////////////////
	
	/**
	 * TODO Javadoc and Implement
	 * 
	 * @throws CannotBuyException
	 * @throws InsufficientPlayerResourcesException
	 */
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
	 * This method counts up and returns how many unused cards the player has of the specified devCardType
	 * 
	 * @return how many unused cards the player has of the specified devCardType
	 */
	public int numberUnplayedDevCards(DevCardType devCardType) {
		return currentPlayer.numberUnplayedDevCards(devCardType);
	}
	
	/**
	 * TODO Javadoc and Implement
	 * 
	 * @param devCardType
	 * @param resourceType
	 * @return
	 * @throws Exception
	 */
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
							players[i].conformToMonopoly(resourceType[0]).clear();
						}
					}
					setVersionNumber(versionNumber++);
					return doWeHaveAWinner();
				case YEAR_OF_PLENTY:
					//Add two resources of the types specified to the currentPlayers hand
					if (resourceType.length == 1) {
						ResourceCard resource = bank.playerTakeResource(resourceType[0]);
						//some sneaky idea that realizes conformToMonopoly returns the arraylist of that players cards of a specified type.
						currentPlayer.conformToMonopoly(resourceType[0]);
						//repeat
						resource = bank.playerTakeResource(resource.getResourceType());
						currentPlayer.conformToMonopoly(resourceType[0]).add(resource);
					}
					else if (resourceType.length == 2) {
						for (int g = 0; g < resourceType.length; g++) {
							ResourceCard resource = bank.playerTakeResource(resourceType[g]);
							//some sneaky idea that realizes conformToMonopoly returns the arraylist of that players cards of a specified type.
							currentPlayer.conformToMonopoly(resourceType[g]).add(resource);
						}
					}
					setVersionNumber(versionNumber++);
					return doWeHaveAWinner();
				default: throw new Exception("Wrong declaration for a development card of this type.");
			}
		}
		return doWeHaveAWinner();
	}
	
	/**
	 * TODO Javadoc and Implement
	 * 
	 * @param devCardType
	 * @return
	 * @throws Exception
	 */
	public boolean useDevelopmentCard(DevCardType devCardType) throws Exception {
		if (currentPlayer.canDoPlayDevelopmentCard(turnNumber, devCardType)) {
		//Marks the card as played
		currentPlayer.playDevelopmentCard(turnNumber, devCardType);
		
		switch (devCardType) {
			case SOLDIER:
				//Do they have more than three soldiers? Do they have the most soldiers? If so, award them the Largest Army award (assuming they don't already have it) and take it from the previous title holder
				//Must move robber to a different hex
								
				boolean firstTime = true;
				
				//Initial selection of LargestArmy recipient.
				if (currentPlayer.getNumberOfSoldiersPlayed() == 3) {
					for (int i = 0; i < players.length; i++) {
						if (players[i].getPlayerId() != currentPlayer.getPlayerId() && players[i].getNumberOfSoldiersPlayed() >= 3) {
								firstTime = false;
						}
					}
					if (firstTime) {
						indexOfLargestArmy = currentPlayer.getPlayerId();
						largestArmy = currentPlayer;
						currentPlayer.incrementVictoryPoints();
						currentPlayer.incrementVictoryPoints();
					}
				}
				int test = 0;
				//Check for competition
				if (currentPlayer.getNumberOfSoldiersPlayed() >= 3 && !firstTime) {
					if (currentPlayer.getNumberOfSoldiersPlayed() > largestArmy.getNumberOfSoldiersPlayed() && currentPlayer.getPlayerId() != largestArmy.getPlayerId()) {
						//indexOfLargestArmy = currentPlayer.getPlayerId();
						
						for (int i = 0; i < players.length; i++) {
							if (largestArmy.getPlayerId() == players[i].getPlayerId()) {
								players[i].decrementVictoryPoints();
								players[i].decrementVictoryPoints();
							}
						}
					
						largestArmy = currentPlayer;
						currentPlayer.incrementVictoryPoints();
						currentPlayer.incrementVictoryPoints();
					}
					
				}
				//Did the Largest Army award win the game?!
				setVersionNumber(versionNumber++);
				return doWeHaveAWinner();
				
			case MONUMENT:
				//Give the player their due reward
				currentPlayer.incrementVictoryPoints();
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
		return doWeHaveAWinner();
		
	}
	
	
	/**
	 * TODO Javadoc and Implement
	 * 
	 * @return
	 */
	public boolean canDoCurrentPlayerDoMeritimeTrade() {
		//If a player is to meritime trade, he needs to have resources to trade. He needs to have either four of that kind, or a three for one port or a two for one, and again have the proper resources
		/*if (currentPlayer.getResourceCardHandSize() == 0)
			return false;
		currentPlayer.*/
		return true;
		
	}
	
	/**
	 * TODO Javadoc and Implement
	 * 
	 */
	public void doMeritimeTrade()  {
		
	}
	
	/**
	 * TODO Javadoc and Implement
	 * 
	 * @pre: the player in question who calls this method is taking his/her turn currently
	 * @post 
	 */
	public boolean canDoCurrentPlayerDoDomesticTrade() {
		return true;
		//Is it the Current Players turn and does he have any resources?
		//
		/*if (currentPlayer.getResourceCardHandSize() == 0)
			return false;
		else
			return true;*/
	}
	 
	/**
	 * TODO Javadoc and Implement
	 * 
	 */
	public void doDomesticTrade() {
		
	}

	// MAP LOCATION PLACEMENT CALLS
	//////////////////////////////////////////
	
	/**
	 * TODO - Verify Completion
	 * 
	 * Checks to see if the given Player can place a road on the specified edge
	 * 
	 * @pre None
	 * @param UserId
	 * @param edgeLocation
	 * @return whether the Specified player can place a road on the specified edge
	 */
	public boolean canDoPlaceRoadOnEdge(int UserId, EdgeLocation edgeLocation) {
		// Check if the user is the current player
		if(UserId != currentPlayer.getPlayerId()) {
			return false;
		}
		// If we are in the setup phase, the rules for placing a road are slightly different
		if(inSetUpPhase == true) {
			return board.canDoPlaceInitialRoadOnEdge(getCurrentPlayer(), edgeLocation);
		} else {
			return board.canDoPlaceRoadOnEdge(getCurrentPlayer(), edgeLocation);	
		}
	}
	
	/**
	 * TODO - Verify Completion
	 * 
	 * Places a Road for the specified player at specified edgeLocation
	 * @pre canDoPlaceRoadOnEdge != false
	 * @param UserId
	 * @throws Exception
	 * @return Void
	 */
	public void placeRoadOnEdge(int UserId, EdgeLocation edgeLocation) throws Exception {
		if(canDoPlaceRoadOnEdge(UserId, edgeLocation) == false) {
			throw new Exception("Specified Player cannot place a road on the given edgeLocation");
		}
		// If we are in the setup phase, the rules for placing a road are slightly different
		if(inSetUpPhase == true) {
			board.placeInitialRoadOnEdge(getCurrentPlayer(), edgeLocation);
		} else {
			board.placeRoadOnEdge(getCurrentPlayer(), edgeLocation);
		}
	}
	
	/**
	 * TODO
	 * 
	 * @pre the vertex location is not null
	 * @param vertexLocation
	 * @post it tells you whether or not you can build a Settlement on that vertex
	 */
	public boolean canDoPlaceSettlementOnVertex(int UserId, VertexLocation vertexLocation) {
		// Check if the user is the current player
		if(UserId != currentPlayer.getPlayerId()) {
			return false;
		}
		return board.canDoPlaceSettlementOnVertex(currentPlayer, vertexLocation);
	}
	
	/**
	 * TODO
	 * 
	 * @pre the Can do is true
	 * @param vertexLocation
	 * @throws Exception
	 * @post a settlement is placed on a vertex
	 */
	public void placeSettlementOnVertex(int UserId, VertexLocation vertexLocation) throws Exception {
		if(canDoPlaceSettlementOnVertex(UserId, vertexLocation) && canDoCurrentPlayerBuildSettlement(UserId))
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

	/**
	 * Retrieves whether the Robber can be moved to the specified hexLocation
	 * 
	 * @pre board != null
	 * @param hex
	 * @return whether the Robber can be moved to the specified hexLocation
	 */
	public boolean canDoMoveRobberToHex(int UserId, HexLocation hexLocation) {
		// Check if the user is the current player
		if(UserId != currentPlayer.getPlayerId()) {
			return false;
		}
		return board.canDoMoveRobberToHex(hexLocation);
	}
	
	
	/**
	 * Moves the robber to the specified HexLocation, and retrieves the players that own municipals
	 * next to that hex, excluding those that don't have resources to steal and the player that moved
	 * the robber
	 * 
	 * @pre canDoMoveRobberToHex != null
	 * @param hex
	 * @throws Exception
	 * @return array of Players that are adjacent to the new Robber Hex, that have resources to steal.
	 */
	public Player[] moveRobberToHex(int UserId, HexLocation hexLocation) throws Exception {
		if (canDoMoveRobberToHex(UserId, hexLocation) == false) {
			throw new Exception("Cannot move Robber to that Hex");
		}
		board.moveRobberToHex(hexLocation);
		Hex hex = board.getHex(hexLocation);
		Vertex[] adjacentVertices = hex.getAdjacentVertices();
		ArrayList<Integer> adjacentPlayerIDs = new ArrayList<Integer>();
		ArrayList<Player> adjacentPlayers = new ArrayList<Player>();
		for(Vertex vertex: adjacentVertices) {
			// If the vertex has a municipal, that municipal is not owned by the player moving the robber
			// and the Player is not already in the list, and the Player has resources to be stolen
			int municipalID = vertex.getMunicipal().getPlayer().getPlayerId();
			Player player = vertex.getMunicipal().getPlayer();
			if(vertex.hasMunicipal() && UserId != municipalID && !adjacentPlayerIDs.contains(municipalID) && player.getResourceCardHandSize() > 0) {
				adjacentPlayerIDs.add(municipalID);
				adjacentPlayers.add(player);
			}
		}
		// Convert the found players into an array to return
		Player[] players = new Player[adjacentPlayers.size()];
		return adjacentPlayers.toArray(players);
	}

	// OTHER
	//////////////////////////////////////////
	
	/**
	 * Checks whether the Specified User can Steal from the Specified Victim
	 * 
	 * @pre None
	 * @param UserId
	 * @param victimId
	 * @return whether the Specified User can Steal from the Specified Victim
	 */
	public boolean canDoStealPlayerResource(int UserId, int victimId) {
		// Check if the user is the current player
		if(UserId != currentPlayer.getPlayerId()) {
			return false;
		}
		if(UserId == victimId) {
			return false;
		}
		Player victim = getPlayerByID(victimId);
		if(victim == null || victim.getResourceCardHandSize() <= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * The Specified User will Steal a Random ResourceCard from the specified Victim
	 * 
	 * @pre canDoStealPlayerResource != false
	 * @param UserId
	 * @param victimId
	 * @throws Exception
	 * @post the Victim will have one less Resource Card, which the User will now have in possession
	 */
	public void stealPlayerResource(int UserId, int victimId) throws Exception {
		if(canDoStealPlayerResource(UserId, victimId) == false) {
			throw new Exception("canDoStealPlayerResource == false");
		}
		currentPlayer.stealPlayerResource(getPlayerByID(victimId));
	}
	
	/**
	 * Retrieves the Player Object for the given playerID
	 * 
	 * @pre None
	 * @param playerID
	 * @return Player Object if in Game, or null if not found
	 */
	public Player getPlayerByID(int playerID) {
		for (int i = 0; i < numberofPlayers; i++) {
			if (playerID == players[i].getPlayerId()) {
				return players[i];
			}
		}
		return null;
	}
	
	/**
	 * Will return the number of the specified resource that the player currently has
	 * 
	 * @pre None
	 * 
	 * @param resourceType
	 * @return the number of the specified resource that the player currently has
	 */
	public int getNumberResourcesOfType(int UserId, ResourceType resourceType) {
		Player player = getPlayerByID(UserId);
		if(player != null) {
			return player.getNumberResourcesOfType(resourceType);
		}
		return 0;
	}
	
	/**
	 * Checks whether the Specified User can discard the specified number of resources of the specified
	 * ResourceType
	 * 
	 * @param UserId
	 * @param removal
	 * @param resourceType
	 * @return whether the Specified User can discard the specified number of resources of the specified
	 * ResourceType
	 */
	public boolean canDoDiscardNumberOfResourceType(int UserId, int numberToDiscard, ResourceType resourceType) {
		Player player = getPlayerByID(UserId);
		if(player == null || resourceType == null) {
			return false;
		}
		if(getPlayerByID(UserId).canDoDiscardResourceOfType(resourceType, numberToDiscard) == false) {
			return false;
		}
		return true;
	}
	
	/**
	 * The Specified Player will discard the specified number of Resource Cards back to the bank
	 * from his hand
	 * 
	 * @pre canDoDiscardNumberOfResourceType != false
	 * @param UserId
	 * @param numberToDiscard
	 * @param resourceType
	 * @throws Exception 
	 * @post The Specified Player will have discarded the specified number of Resource Cards back to the bank
	 * from his hand
	 */
	public void discardNumberOfResourceType(int UserId, int numberToDiscard, ResourceType resourceType) throws Exception {
		getPlayerByID(UserId).discardResourcesOfType(resourceType, numberToDiscard);
		
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
