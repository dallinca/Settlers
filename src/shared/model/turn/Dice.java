package shared.model.turn;

import java.util.Random;

import client.Client;
import shared.model.Game;

/**
 * All action methods associated with dice rolls.
 *
 */
public class Dice {


	
	Dice() {
		System.out.println("Dice Dice()");
	}
	/**
	 * Rolls the game dice.
	 * 
	 * @pre None.
	 * @post Rolls dice and enables players to perform associated action.
	 */
	public void rollDice(){
		System.out.println("Dice rollDice()");
		
		Random ran = new Random();
		
		int roll = ran.nextInt(6) + ran.nextInt(6) +2;	//Roll 2d6	
		
		if (roll == 7){
			moveRobber();
			stealResource();
		}
		else assignResources(roll);		
		
	}
	
	/**
	 * Assigns resources to players based on dice result. Robber's location is skipped.
	 * 
	 * @pre diceResult must be an integer between 2 and 12.
	 * @post Resources will be assigned to various players if possible.
	 */
	public void assignResources(int diceResult){
		System.out.println("Dice assignResources()");
				
	}
	
	/**
	 * Allows player to move robber to any hex of their choosing.
	 * 
	 * @pre None.
	 * @post Robber will be moved to a new hex.
	 * 
	 */
	public void moveRobber(){
		System.out.println("Dice moveRobber()");
						
	}	
	
	/**
	 * Allows player to steal a resource from another player who has settlement adjacent to location of robber.
	 * If no adjacent players have resources or are there are adjacent players, nothing happens.
	 * 
	 * @pre None.
	 * @post Player steals resource from chosen player, or not.
	 * 
	 */
	public void stealResource(){
		System.out.println("Dice stealResource()");
		
		
	}	
}
