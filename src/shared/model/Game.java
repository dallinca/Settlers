package shared.model;

import java.util.ArrayList;
import java.util.Random;

import client.data.TradeInfo;
import server.commands.CommandHistory;
import shared.communication.params.move.devcard.PlaySoldier_Params;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.items.*;
import shared.model.player.*;
import shared.model.player.exceptions.CannotBuyException;
import shared.model.player.exceptions.InsufficientPlayerResourcesException;
import shared.model.board.Board;
import shared.model.board.Hex;
import shared.model.board.Vertex;


/**
 * The Game class handles all of the classic actions that occur during a game and their various can-dos. Since the last update this class was split into two with a more specialized version now called "Bank.java"
 * Now the game class has a primary function of performing operations on model classes and verifying if they can be done or not. This will be used heavily when executing commands. 
 * It also keeps track of how many players there are, who is next, and who is the current player.
 */
public class Game {

	//private boolean inSetUpPhase = true; // This is a state boolean for the first two setup rounds
	private Player[] players = null;
	private Player currentPlayer = null;
	private Board board = null;
	private Player largestArmy;
	private Player longestRoad;
	private static int numberofPlayers = 4;
	private Bank bank = null;
	private int turnNumber = 0;
	private int versionNumber = 1;
	private int indexOfLargestArmy = -1;
	private String status = "";
	private Line[] chat;
	private Line[] history;
	private int winner = -1;
	private CommandHistory gameHistory = new CommandHistory();


	//How do we create this / initialize it????
	private int gameID;
	private String title;


	private TradeInfo tradeOffer;

	// CONSTRUCTORS
	//////////////////////////////////////////

	/**
	 * For testing use, self initializing
	 * 
	 */
	public Game() {
		// init bank
		bank = new Bank();
		// init players
		players = new Player[numberofPlayers];
		for(int i = 0; i < 3; i++) {
			players[i] = new Player(i, bank);
			players[i].setPlayerId(24);
			players[i].setPlayerName("bob number: " + i);;
			players[i].setPlayerColor(CatanColor.values()[i]);
		}
		// init board to normal setup
		board = new Board(true, true, true);
		history = new Line[10];
		chat = null;
	}

	/**
	 * @pre the player objects passed in are not null, neither is the Board.
	 * 
	 * @param the four player objects to be added to the array.
	 */
	public Game(Bank bank, Player one, Player two, Player three, Player four, Board board) {
		this.bank = bank;
		players = new Player[numberofPlayers];
		players[0] = one;
		players[1] = two;
		players[2] = three;
		players[3] = four;

		currentPlayer = players[0];
		this.board = board;
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
	 * @pre the player objects passed in are not null, neither is the Board.
	 * 
	 * @param the four player objects to be added to the array.
	 */
	public Game(Player[] players, Board board, Bank bank) {
		this.players = players;

		this.bank = bank;
		this.board = board;
	}


	// CURRENT PLAYER CALLS
	//////////////////////////////////////////
	public boolean canDoPlayerEndTurn(int UserId) {	
		if(!isPlayersTurn(UserId)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns whether it is currently the players turn or not
	 * 
	 * @param UserId
	 * @return
	 */
	public boolean isPlayersTurn(int UserId) {	
		if(UserId != currentPlayer.getPlayerId()) {
			return false;
		}
		return true;
	}

	/**
	 * This method will cycle through the array of players and will rotate them through the currentPlayer so the turns can proceed.
	 * This will be helpful when the index of the player array is [3] and we need to bring it back to [0] showing that that person is next.
	 * @pre the players array is not null
	 * @post the next player is set.
	 */
	public void incrementPlayer() {
		// If we are done with the first two rounds of the Game (for setup
		for (int i = 0; i < numberofPlayers; i++) {
			if (currentPlayer.getPlayerIndex() == players[i].getPlayerIndex()) {
				//players[i] = currentPlayer; // This should probably be omitted
				// If we are no longer in the setup phase
				if(turnNumber > 1) {
					if (i == numberofPlayers-1) {
						setCurrentPlayer(players[0]);
						currentPlayer.setHasDiscarded(false);
						turnNumber++;
						versionNumber++;
						status = "Rolling";
						return;
					} else {
						setCurrentPlayer(players[i+1]);
						currentPlayer.setHasDiscarded(false);
						versionNumber++;
						status = "Rolling";
						return;
					}
				}
				// We must still be in the setup phase
				else {
					// We are still in the first round
					if(turnNumber == 0) {
						// If we are on the last person in the round, he/she gets to go again
						if(i == numberofPlayers - 1) {
							//setCurrentPlayer(players[numberofPlayers - 1]); // Could probably omit this line
							turnNumber++;
							versionNumber++;
							status = "SecondRound";
							return;
						} else {
							setCurrentPlayer(players[i+1]);
							versionNumber++;
							return;
						}
					}
					// We are still in the second round
					else if(turnNumber == 1) {
						if(i == 0) {
							setCurrentPlayer(players[0]);
							//inSetUpPhase = false;
							turnNumber++;
							versionNumber++;
							status = "Rolling";
							return;
						} else {
							setCurrentPlayer(players[i-1]);
							versionNumber++;
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
	public void setCurrentPlayer(Player setPlayer) {
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
		if(!isPlayersTurn(UserId)) {
			return false;
		}
		else if(getStatus().equals("Rolling")){
			return true;
		}

		return false;
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
		/*
		if(rollValue != 7) {
			playersCollectResources(rollValue);
		}*/
		// If the roll is a seven, tell the client and wait for attempts to move the robber

		return rollValue;
	}

	public void setRollDice(int UserId, int rollValue) throws Exception {
		if(canDoRollDice(UserId) == false) {
			throw new Exception("canDoRollDice == false");
		}
		if(rollValue == 7) {
			status = "Robbing";
			// If there is any player that has more than 7 cards and has not yet discarded this turn
			// must discard, so we set the game status to "Discarding"
			for(Player player: players) {
				if(player.getResourceCardHandSize() > 7 && player.isHasDiscarded() == false) {
					status = "Discarding";
				}
			}
		} else {
			status = "Playing";
			playersCollectResources(rollValue);
		}
		versionNumber++;
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
			versionNumber++;
		}
	}


	// RESOURCE BAR CAN-DO CALLS
	//////////////////////////////////////////

	/**
	 * Returns the number of unplayed roads for the specified user, for the gui count.<br>
	 * return 0 if the given id is not found in the game
	 * 
	 * @return the number of unplayed roads, for the gui count
	 */
	public int getNumberUnplayedRoads(int UserId) {
		return getPlayerByID(UserId).getNumberUnplayedRoads();
	}

	/**
	 * Returns the number of unplayed cities for the specified user, for the gui count.<br>
	 * return 0 if the given id is not found in the game
	 * 
	 * @return the number of unplayed cities, for the gui count
	 */
	public int getNumberUnplayedCities(int UserId) {
		return getPlayerByID(UserId).getNumberUnplayedCities();
	}	

	/**
	 * Returns the number of unplayed settlements for the specified user, for the gui count.<br>
	 * return 0 if the given id is not found in the game
	 * 
	 * @return the number of unplayed settlements, for the gui count
	 */
	public int getNumberUnplayedSettlements(int UserId) {
		return getPlayerByID(UserId).getNumberUnplayedSettlements();
	}

	/**
	 * This method polls the player to see if the player can build a road and then returns the result to that which called it (most likely the client)
	 * @return a true or false to if the player can build a road there.
	 */
	public boolean canDoPlayerBuildRoad(int UserId) {
		if(!isPlayersTurn(UserId)) {
			return false;
		}
		if(turnNumber == 0) {
			if(currentPlayer.getNumberUnplayedRoads() != 15 ) {
				return false;
			}
			return true;
		} else if(turnNumber == 1) {
			if(currentPlayer.getNumberUnplayedRoads() != 14 ) {
				return false;
			}
			return true;
		} else {
			return currentPlayer.canDoBuyRoad();
		}
	}

	/**
	 * Asks the player if he or she can build a settlement and tells the client that.
	 * @return
	 */
	public boolean canDoPlayerBuildSettlement(int UserId) {
		if(!isPlayersTurn(UserId)) {
			return false;
		}
		if(turnNumber == 0) {
			if(currentPlayer.getNumberUnplayedSettlements() != 5) {
				return false;
			}
			return true;
		} else if(turnNumber == 1) {
			if(currentPlayer.getNumberUnplayedSettlements() != 4) {
				return false;
			}
			return true;
		}
		return currentPlayer.canDoBuySettlement();
	}

	/**
	 * Asks the player if he or she can build a city and tells the client that.
	 * @return
	 */
	public boolean canDoPlayerBuildCity(int UserId) {
		if(!isPlayersTurn(UserId)) {
			return false;
		}
		return currentPlayer.canDoBuyCity();
	}

	/**
	 * Asks the player if he or she can buy a development card and tells the client that.
	 * @return
	 */
	public boolean canDoPlayerBuyDevelopmentCard(int UserId) {
		if(!isPlayersTurn(UserId)) {
			return false;
		}
		return currentPlayer.canDoBuyDevelopmentCard(bank);
	}

	// DEVELOPMENT CARD ACTION CALLS
	//////////////////////////////////////////

	public int getNumberOfSoldiersPlayed(int userID) {
		Player player = getPlayerByID(userID);
		if(player == null) {
			return -1;
		}
		return player.getNumberOfSoldiersPlayed();
	}

	/**
	 * TODO Javadoc and Implement
	 * 
	 * @throws CannotBuyException
	 * @throws InsufficientPlayerResourcesException
	 */
	public void buyDevelopmentCard() throws CannotBuyException, InsufficientPlayerResourcesException {
		if (currentPlayer.canDoBuyDevelopmentCard(bank)) {
			currentPlayer.buyDevelopmentCard(turnNumber, bank);
		}
	}

	/**
	 * This method stands to show if the player CAN use one of his/her un-used dev Cards of the type you ask it about.
	 * @param devCardType
	 * @return
	 */
	public boolean canDoPlayerUseDevelopmentCard(int userID, DevCardType devCardType) {
		//We need to be able to measure how long a player has owned a card.
		if (currentPlayer.getPlayerId() == userID) {
			return currentPlayer.canDoPlayDevelopmentCard(turnNumber, devCardType);
		} 
		return false;
	}

	/**
	 * This method counts up and returns how many unused cards the player has of the specified devCardType
	 * 
	 * @return how many unused cards the player has of the specified devCardType
	 */
	public int numberUnplayedDevCards(int userID, DevCardType devCardType) {

		int unplayed = -1;

		for (int i = 0; i < players.length; i++) {
			if (players[i].getPlayerId() == userID) {
				unplayed = players[i].numberUnplayedDevCards(devCardType);
			}
		}

		return unplayed;
		//what it used to say: currentPlayer.numberUnplayedDevCards(devCardType)
	}


	/**
	 * This card is special because it is the only development card that references the bank, so additional checks need to be made, and so for security reasons it gets its own method to do so.
	 */
	public boolean canDoPlayerUseYearOfPlenty(ResourceType[] resourceType, int userID) {
		//This is important because the bank not have the cards they need.
		if (currentPlayer.getPlayerId() == userID) {
			if (resourceType.length == 1) {
				if (!canDoPlayerTake2OfResource(resourceType[0])) {
					return false;
				}
			} else {
				for (int i = 0; i < resourceType.length; i++) {
					if (!bank.canDoPlayerTakeResource(resourceType[i]))
						return false;
				}
			}
			return currentPlayer.canDoPlayDevelopmentCard(turnNumber, DevCardType.YEAR_OF_PLENTY);

		}
		return false;
	}

	/**
	 * TODO Javadoc and Implement
	 * 
	 * @param devCardType
	 * @param resourceType
	 * @return
	 * @throws Exception
	 */
	public boolean useDevelopmentCard(int userID, DevCardType devCardType, ResourceType[] resourceType) throws Exception {

		if (currentPlayer.getPlayerId() == userID) {

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
					currentPlayer.playDevelopmentCard(turnNumber, DevCardType.MONOPOLY);
					currentPlayer.setHasPlayedDevCardThisTurn(true);
					setVersionNumber(versionNumber++);
					return doWeHaveAWinner();
				case YEAR_OF_PLENTY:
					if (canDoPlayerUseYearOfPlenty(resourceType, userID)) {
						//Add two resources of the types specified to the currentPlayers hand
						if (resourceType.length == 1) {
							ResourceCard resource = bank.playerTakeResource(resourceType[0]);
							//some sneaky idea that realizes conformToMonopoly returns the arraylist of that players cards of a specified type.
							currentPlayer.conformToMonopoly(resourceType[0]).add(resource);;
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
						currentPlayer.playDevelopmentCard(turnNumber, DevCardType.YEAR_OF_PLENTY);
						currentPlayer.setHasPlayedDevCardThisTurn(true);
						setVersionNumber(versionNumber++);
						return doWeHaveAWinner();
					} else {
						throw new Exception("The bank has not what you seek.");
					}
				default: throw new Exception("Wrong declaration for a development card of this type.");
				}
			}
		} else {
			throw new Exception("You cannot use a development Card");
		}

		return doWeHaveAWinner();
	}

	/**
	 * This method Uses a development card and marks it as played
	 * 
	 * @param devCardType is valid and you own that card
	 * @return whether or not you just won the game
	 * @throws Exception
	 */
	public boolean useMonumentCard(int userID, DevCardType devCardType) throws Exception {
		if (currentPlayer.getPlayerId() == userID) {

			if (currentPlayer.canDoPlayDevelopmentCard(turnNumber, devCardType)) {
				//Marks the card as played
				currentPlayer.playDevelopmentCard(turnNumber, devCardType);
				currentPlayer.setHasPlayedDevCardThisTurn(true);
				
				//Now do what monuments do:
				currentPlayer.incrementVictoryPoints();
				setVersionNumber(versionNumber++);
				return doWeHaveAWinner();
			}
			else 
				throw new Exception("Cannot Play Monument card!");
		} else
			throw new Exception("Cannot Play Monument card, you are not the current Player!");
	}

	public boolean useSoldierCard(int userID, PlaySoldier_Params params) {
		//Do they have more than three soldiers? Do they have the most soldiers? If so, award them the Largest Army award (assuming they don't already have it) and take it from the previous title holder
		//Must move robber to a different hex

		if (currentPlayer.getPlayerId() == userID) {

			if (currentPlayer.canDoPlayDevelopmentCard(turnNumber, DevCardType.SOLDIER)) {
				if (canDoStealPlayerResource(userID, params.getVictimIndex())) {

					try {
						moveRobberToHex(userID, params.getLocation());
						stealPlayerResource(userID, params.getVictimIndex());
						currentPlayer.playDevelopmentCard(turnNumber, DevCardType.SOLDIER);
						currentPlayer.setHasPlayedDevCardThisTurn(true);
					} catch (Exception e) {
						System.out.println("Something went wrong when trying to move the robber or steal resources");
						e.printStackTrace();
					}


					boolean firstTime = true;

					//Initial selection of LargestArmy recipient.
					if (currentPlayer.getNumberOfSoldiersPlayed() == 3) {
						for (int i = 0; i < players.length; i++) {
							if (players[i].getPlayerId() != currentPlayer.getPlayerId() && players[i].getNumberOfSoldiersPlayed() >= 3) {
								firstTime = false;
							}
						}
						if (firstTime) {
							indexOfLargestArmy = currentPlayer.getPlayerIndex();
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

				}




			}
		}
		return doWeHaveAWinner();
	}


	/**
	 * This method calls two others to verify that you can do a maritime trade
	 * @pre the resource types are valid
	 * @post returns whether or not you can do the trade or not
	 */
	public boolean canDoPlayerDoMaritimeTrade(ResourceType tradeIn, ResourceType receive) {

		if (canDoPlayerTakeResource(receive) && canTradeResourcesToBank(tradeIn)) {
			return true;
		} else
			return false;

		//If a player is to maritime trade, they needs to have resources to trade. They needs to have either four of that kind, or a three for one port or a two for one, and again have the proper resources
		/*if (currentPlayer.getResourceCardHandSize() == 0)
			return false;
		currentPlayer.*/	
	}

	/**
	 * This method does the actual trade by interacting with the bank and the Current Player to ascertain the port type and what if they can trade or not. 
	 * @pre the resource types it receives are valid, and that tradeIn is what the player performing the trade already has
	 * @throws Exception 
	 * 
	 */
	public void doMaritimeTrade(ResourceType tradeIn, ResourceType receive) throws Exception  {

		if (canDoPlayerDoMaritimeTrade(tradeIn, receive)) {

			ResourceCard[] tradingCards = currentPlayer.prepareBankTrade(tradeIn);

			bank.playerTurnInResources(tradingCards);

			currentPlayer.getResourceCardHand().addCard(bank.playerTakeResource(receive));

		} else
			throw new Exception("Cannot do Maritime Trade");

	}

	/**
	 * This method returns a 3, 4, or 2 depending on the port type you have, if any.
	 * @pre valid resource type
	 * @return
	 */
	public int getTradeRate(ResourceType resourceType) {
		return currentPlayer.getTradeRate(resourceType);	
	}

	/**
	 * Does the player have cards to trade?
	 * @pre valid resource type
	 * @param resourceType
	 * @return yes you can trade or no you can't.
	 */
	public boolean canTradeResourcesToBank(ResourceType resourceType) {
		//Does the Player have cards to trade?
		//Does the Player have a port?
		//Does the Player have enough cards to trade?
		int size = currentPlayer.getResourceCardHand().getNumberResourcesOfType(resourceType);
		int rate = getTradeRate(resourceType);
		if (size >= rate) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Can you take a resource? It calls the bank to see if it has cards to take or not
	 * @pre valid resource type
	 * @param resourceType
	 * @return whether or not the bank has cards to take
	 */
	public boolean canDoPlayerTakeResource(ResourceType resourceType) {
		return bank.canDoPlayerTakeResource(resourceType);
	}

	/**
	 * For Year of Plenty
	 * Retrieves whether the player can take 2 of the specified resource from the bank
	 * 
	 * @param resourceType
	 * @return whether the player can take 2 of the specified resource from the bank
	 */
	public boolean canDoPlayerTake2OfResource(ResourceType resourceType) {
		return bank.canDoPlayerTake2OfResource(resourceType);
	}

	/**
	 * Can the player do domestic trade?
	 * In the integer arrays passed in (p1 and p2 resources) the values at the index represent how many of a specific type of resource you are wanting to trade.
	 * Each index is represented as follows:
	 * 0 = brick
	 * 1 = wood
	 * 2 = wheat
	 * 3 = ore
	 * 4 = sheep
	 * 
	 * @pre: the player in question who calls this method is taking his/her turn currently
	 * @post 
	 */
	public boolean canDoPlayerDoDomesticTrade(int p1id, int[] p1resources, int p2id, int[] p2resources) {
		//Is it the Current Players turn and do they have any resources?
		//Do they have the resources he said he would trade

		ResourceType resourceType = null;

		for (int i = 0; i < p1resources.length; i++) {

			if (i == 0) {
				resourceType = ResourceType.BRICK;				
			} else if (i == 1) {
				resourceType = ResourceType.WOOD;
			} else if (i == 2) {
				resourceType = ResourceType.WHEAT;
			} else if (i == 3) {
				resourceType = ResourceType.ORE;
			} else if (i == 4) {
				resourceType = ResourceType.SHEEP;
			}

			if(players[p1id].getNumberResourcesOfType(resourceType) < p1resources[i]) {
				return false;
			}
		}

		for (int g = 0; g < p2resources.length; g++) {

			if (g == 0) {
				resourceType = ResourceType.BRICK;				
			} else if (g == 1) {
				resourceType = ResourceType.WOOD;
			} else if (g == 2) {
				resourceType = ResourceType.WHEAT;
			} else if (g == 3) {
				resourceType = ResourceType.ORE;
			} else if (g == 4) {
				resourceType = ResourceType.SHEEP;
			}

			if(players[p2id].getNumberResourcesOfType(resourceType) < p2resources[g]) {
				return false;
			}
		}


		int p1size = 0;

		p1size += players[p1id].getNumberResourcesOfType(ResourceType.BRICK);
		p1size += players[p1id].getNumberResourcesOfType(ResourceType.WOOD);
		p1size += players[p1id].getNumberResourcesOfType(ResourceType.ORE);
		p1size += players[p1id].getNumberResourcesOfType(ResourceType.SHEEP);
		p1size += players[p1id].getNumberResourcesOfType(ResourceType.WHEAT);

		int p2size = 0;

		p2size += players[p2id].getNumberResourcesOfType(ResourceType.BRICK);
		p2size += players[p2id].getNumberResourcesOfType(ResourceType.WOOD);
		p2size += players[p2id].getNumberResourcesOfType(ResourceType.ORE);
		p2size += players[p2id].getNumberResourcesOfType(ResourceType.SHEEP);
		p2size += players[p2id].getNumberResourcesOfType(ResourceType.WHEAT);

		if (p1size == 0 || p2size == 0)
			return false;
		else
			return true;

	}


	/**
	 * Does the actual Domestic trade
	 * In the integer arrays passed in (p1 and p2 resources) the values at the index represent 
	 * how many of a specific type of resource you are wanting to trade.
	 * Each index is represented as follows:
	 * 0 = brick
	 * 1 = wood
	 * 2 = wheat
	 * 3 = ore
	 * 4 = sheep
	 * 
	 * @pre the players are able to trade, they have accepted the trade, and they have valid playerIDs
	 * @post
	 * 
	 */
	public void doDomesticTrade(int p1id, int[] p1resources, int p2id, int[] p2resources) throws Exception {

		if (canDoPlayerDoDomesticTrade(p1id, p1resources, p2id, p2resources)) {

			ResourceType resourceType = null;

			System.out.println("PLAYER 1");

			for (int i = 0; i < p1resources.length; i++) {

				if (p1resources[i] > 0) {

					if (i == 0){

						resourceType = ResourceType.BRICK;

						System.out.println("BRICK");
					}

					else if (i == 1){
						resourceType = ResourceType.WOOD;

						System.out.println("WOOD");
					}
					else if (i == 2){
						resourceType = ResourceType.WHEAT;

						System.out.println("WHEAT");
					}
					else if (i == 3){
						resourceType = ResourceType.ORE;

						System.out.println("ORE");
					}
					else if (i == 4){
						resourceType = ResourceType.SHEEP;
						System.out.println("SHEEP");
					}	

					ResourceCard[] trade = players[p1id].preparePlayerTrade(resourceType, p1resources[i]);
					System.out.println("TRADE: "+trade.length);
					
					for (int g = 0; g < p1resources[i]; g++) {
						players[p2id].getResourceCardHand().addCard(trade[g]);
						System.out.println("HAPPENING");
					}
				}
			}

			System.out.println("PLAYER 2");

			for (int t = 0; t < p2resources.length; t++) {

				if (p2resources[t] < 0) {

					if (t == 0){
						resourceType = ResourceType.BRICK;
						System.out.println("BRICK");
					}
					else if (t == 1){
						resourceType = ResourceType.WOOD;
						System.out.println("WOOD");
					}
					else if (t == 2){
						resourceType = ResourceType.WHEAT;
						System.out.println("WHEAT");
					}
					else if (t == 3){
						resourceType = ResourceType.ORE;

						System.out.println("ORE");
					}
					else if (t == 4){
						resourceType = ResourceType.SHEEP;
						System.out.println("SHEEP");
					}
					
					ResourceCard[] trade = players[p2id].preparePlayerTrade(resourceType, -p2resources[t]);
					System.out.println("TRADE: "+trade.length);
					
					for (int g = 0; g < trade.length; g++) {
						players[p1id].getResourceCardHand().addCard(trade[g]);
						System.out.println("HAPPENING");
					}
				}
			}

		} else			
			throw new Exception("You cannot trade!!");


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
			System.out.println("Game1: false");
			return false;
		}
		// If we are in the setup phase, the rules for placing a road are slightly different
		if(turnNumber == 0) {
			if(currentPlayer.getNumberUnplayedRoads() != 15 ) {
				return false;
			}
			return board.canDoPlaceInitialRoadOnEdge(getCurrentPlayer(), edgeLocation);
		} else if(turnNumber == 1) {
			if(currentPlayer.getNumberUnplayedRoads() != 14 ) {
				return false;
			}
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
		if(turnNumber < 2) {
			board.placeInitialRoadOnEdge(getCurrentPlayer(), edgeLocation);
			versionNumber++;
		} else {
			board.placeRoadOnEdge(getCurrentPlayer(), edgeLocation);
		}
	}


	public void placeRoadOnEdge(int userId, EdgeLocation edgeLocation, boolean usingRoadBuilder) throws Exception {
		if(canDoPlaceRoadOnEdge(userId, edgeLocation) == false) {
			throw new Exception("Specified Player cannot place a road on the given edgeLocation");
		}
		// If we are in the setup phase, the rules for placing a road are slightly different
		if(usingRoadBuilder == true) {
			if (currentPlayer.getPlayerId() == userId && currentPlayer.canDoPlayDevelopmentCard(turnNumber, DevCardType.ROAD_BUILD) && currentPlayer.getNumberUnplayedRoads() >= 2) {
				board.placeRoadBuildRoadOnEdge(getCurrentPlayer(), edgeLocation);
				versionNumber++;
			} else {
				throw new Exception("Unable to play Road Builder!");
			}
		} else {
			board.placeRoadOnEdge(getCurrentPlayer(), edgeLocation);
			versionNumber++;
		}
	}

	/**
	 * Can we place a settlement on that vertex?
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
	 * The art of placing a settlement on the vertex.
	 * 
	 * @pre the Can do is true
	 * @param vertexLocation
	 * @throws Exception
	 * @post a settlement is placed on a vertex
	 */
	public void placeSettlementOnVertex(int UserId, VertexLocation vertexLocation) throws Exception {
		if(canDoPlaceSettlementOnVertex(UserId, vertexLocation) == false) {
			throw new Exception("Cannot build Settlement on this vertex, this should not have been allowed to get this far.");
		}
		// If we are in the first two rounds of the game
		if(turnNumber < 2) {
			board.placeInitialSettlementOnVertex(currentPlayer, vertexLocation);
			versionNumber++;
			// Here we collect the resources for the second placed settlement in setup round
			if(turnNumber == 1) {
				Hex[] adjacentHexes = board.getVertex(vertexLocation).getAdjacentHexes();
				for(Hex hex: adjacentHexes) {
					if(hex != null) {
						ResourceType hexResourceType = hex.getHexResourceType();
						ResourceCard card = bank.playerTakeResource(hexResourceType);
						currentPlayer.getResourceCardHand().addCard(card);
					}
				}
			}
		} else {
			board.placeSettlementOnVertex(currentPlayer, vertexLocation);
			versionNumber++;
		}
	}

	/**
	 * This method ensures we can place a city on the given vertex
	 * 
	 * @pre the vertex location is not null
	 * @param vertexLocation
	 * @post it tells you whether or not you can build a Settlement on that vertex
	 */
	public boolean canDoPlaceCityOnVertex(VertexLocation vertexLocation) {
		return board.canDoPlaceCityOnVertex(currentPlayer, vertexLocation);
	}

	/**
	 * Places the beautiful city on it's designated spot.
	 * 
	 * @pre the Can do is true
	 * @param vertexLocation
	 * @throws Exception
	 * @post a city is placed on a vertex
	 */
	public void placeCityOnVertex(VertexLocation vertexLocation) throws Exception {
		if(canDoPlaceCityOnVertex(vertexLocation)) {
			board.placeCityOnVertex(currentPlayer, vertexLocation);
			versionNumber++;
			versionNumber++;
		}
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
			if (vertex.getMunicipal()!=null){
				int municipalID = vertex.getMunicipal().getPlayer().getPlayerIndex();
				Player player = vertex.getMunicipal().getPlayer();
				if(vertex.hasMunicipal() && UserId != municipalID && !adjacentPlayerIDs.contains(municipalID) && player.getResourceCardHandSize() > 0) {
					adjacentPlayerIDs.add(municipalID);
					adjacentPlayers.add(player);
				}
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
		status = "Playing";
		versionNumber++;
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
			if (players[i]==null){

			}
			else if (playerID == players[i].getPlayerId()) {
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
		getPlayerByID(UserId).setHasDiscarded(true);
		status = "Robbing";
		System.out.println("Status: " + status);
		// If there is any player that has more than 7 cards and has not yet discarded this turn
		// must discard, so we set the game status to "Discarding"
		for(Player player: players) {
			if(player.getResourceCardHandSize() > 7 && player.isHasDiscarded() == false) {
				status = "Discarding";
				System.out.println("Status: " + status);
			}
		}
		
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

	/**
	 * This is only intended for extracting information from the hexes for the map initialization
	 * 
	 * @pre None
	 * @return Hex[][]
	 */
	public Hex[][] getMapHexes() {
		return board.getMapHexes();
	}

	/**
	 * This is only intended for extracting information from the hexes for the map initialization
	 * 
	 * @pre None
	 * @return PortType[][]
	 */
	public PortType[] getMapPorts() {
		return board.getMapPorts();
	}

	/**
	 * This is only inteded for extracting information from the players.
	 * 
	 * @return
	 */
	public Player[] getAllPlayers() {
		return players;
	}

	public void setVersionNumber(int version) {
		versionNumber = version;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public boolean isInSetUpPhase() {
		if(turnNumber > 1) {
			return false;
		}
		return true;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public Player getLargestArmy() {
		return largestArmy;
	}

	public void setLargestArmy(Player largestArmy) {
		// Take the largest army away from the other player if he has it
		if(this.largestArmy != null) {
			this.largestArmy.setHasLargestArmy(false);
		}
		// Give to new player
		this.largestArmy = largestArmy;
		largestArmy.setHasLargestArmy(true);
	}

	public Player getLongestRoad() {
		return longestRoad;
	}

	public void setLongestRoad(Player longestRoad) {
		// Take the longest road away from the other player if he has it
		if(this.longestRoad != null) {
			this.longestRoad.setHasLongestRoad(false);
		}
		// Give to new player
		this.longestRoad = longestRoad;
		longestRoad.setHasLongestRoad(true);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setChat(Line[] lines) {
		chat = lines;
	}
	public Line[] getChat() {
		return chat;
	}

	public void setHistory(Line[] lines) {
		history = lines;
	}
	public Line[] getHistory() {
		return history;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public Board getBoard() {
		return board;
	}

	public TradeInfo getTradeOffer() {
		return tradeOffer;
	}

	public void setTradeOffer(TradeInfo tradeOffer) {
		this.tradeOffer = tradeOffer;
	}

	public CommandHistory getGameHistory() {
		return gameHistory;
	}

	public void setGameHistory(CommandHistory gameHistory) {
		this.gameHistory = gameHistory;
	}



	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}



	/**
	 * Hear ye, hear ye! We have a class line that is designated for the functionality of the ChatController and the GameHistoryController
	 * its purpose is to keep track of all the message type objects we get from the server so it can be accessed and printed by the two controllers mentioned above.
	 *
	 */
	public class Line {
		private String message;
		private String source;

		public Line() {
			message = "";
			source = "";
		}

		public void setMessage(String chatterbox) {
			message = chatterbox;
		}
		public String getMessage() {
			return message;
		}
		public void setSource(String name) {
			source = name;
		}
		public String getSource() {
			return source;
		}
	}



	public int getPlayerCount(){

		Player[] players = getAllPlayers();
		int playerCount = 0;

		for (int i = 0; i < 4; i ++){
			if (players[i]!=null){ //Check for vacancy in game roster.
				playerCount++;
			}
		}
		return playerCount;
	}

	public void addPlayer(int userID, CatanColor playerColor) {

		System.out.println("Game.addPlayer");
		if(players == null)
			players = new Player[numberofPlayers];

		int playerCount = 0;
		while (players[playerCount]!=null){
			playerCount++;
			if (playerCount==4){
				break;
			}
		}

		System.out.println("Player count == "+playerCount);

		if( playerCount < 4){
			System.out.println("Adding new player.");
			Player p = new Player(playerCount, bank);
			p.setPlayerId(userID);
			p.setPlayerColor(playerColor);			
			players[playerCount] = p;
		}

		if(playerCount == 4){
			System.out.println("Beginning game.");
			status = "FirstRound";
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public int getIndexOfPlayer(Player player) {
		if(player == null) {
			return -1;
		}
		int index = -1;
		for(int i = 0; i < players.length; i++) {
			if(player.getPlayerId() == players[i].getPlayerId()) {
				index = i;
			}
		}
		return index;
	}



}
