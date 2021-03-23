/****************************************************************
* Created by: Isen Kasoski (Z1875089)
* Date: 2/15/2021
* Purpose: Class that holds information about the Prisoner
* 		   Dilemma game. It is used by PDGameApp class. It also
*  		   uses another class called GameStat.
*****************************************************************/

import java.util.*;

public class PDGame 
{
	private ArrayList<Integer> userHistory = new ArrayList<>(); // Contains all the moves of the user
	private ArrayList<String> strategies = new ArrayList<>();   // Contains all the possible strategies
	private GameStat stats = new GameStat();			      	// Contains the statistics of the game
	private Scanner input = new Scanner(System.in);				// Input
	private int computerStrategy;								// Which strategy is the computer using?
	private String output;										// Output to be sent to the screen

	// Default Constructor
	PDGame()
	{
		strategies.add("Computer Reads Strategy From Input File");
		strategies.add("Tit-For-Tat");
		strategies.add("Tit-For-Two-Tats");
		strategies.add("Random Choice by Computer");
	}
	
	/* userHistory getters and setters */
	
	public ArrayList<Integer> getUserHistory() {
		return userHistory;
	}

	public void setUserHistory(ArrayList<Integer> userHistory) {
		this.userHistory = userHistory;
	}
	
	/* This function clears the user history for later reuse */
	public void clearUserHistory()
	{
		this.userHistory.clear();
	}

	/* strategies getters and setters */
	
	public ArrayList<String> getStrategies() {
		return strategies;
	}

	public void setStrategies(ArrayList<String> strategies) {
		this.strategies = strategies;
	}

	/* stats getters and setters */
	public GameStat getStats() {
		return stats;
	}

	public void setStats(GameStat stats) {
		this.stats = stats;
	}

	/* input getters and setters */
	public Scanner getInput() {
		return input;
	}

	public void setInput(Scanner input) {
		this.input = input;
	}

	/* computerStrategy getters and setters */
	public int getComputerStrategy() {
		return computerStrategy;
	}

	public void setComputerStrategy(int computerStrategy) {
		this.computerStrategy = computerStrategy;
		
		// Update the computer strategy in stats obj
		if (computerStrategy == 1)
			stats.setComputerStrategy(strategies.get(0));
		else if (computerStrategy == 2)
			stats.setComputerStrategy(strategies.get(1));
		else if (computerStrategy == 3)
			stats.setComputerStrategy(strategies.get(2));
		else if (computerStrategy == 4)
			stats.setComputerStrategy(strategies.get(3));
	}
	
	/*
	 * This method increments sentences accordingly for both the computer and the player.
	 * 
	 * @param userDecision is the decision of the user
	 * @param compDecision is the decision of the computer
	 * */
	public void incrementSentences(int userDecision, int compDecision)
	{
		// Increment the players sentences
		if(userDecision == 1 && compDecision == 1)
		{
			stats.update(2, 2);
			output = "You and your partner both remain silent.\n";
			output += "You both get 2 years in prison.\n\n";
		}
		else if (userDecision == 2 && compDecision == 1)
		{
			stats.update(1, 5);
			output = "You testify against your partner and they remain silent.\n";
			output += "You get 1 year in prison and the computer gets 5.\n\n";
		}
		else if (userDecision == 1 && compDecision == 2)
		{
			stats.update(5, 1);
			output = "You remain silent and your partner betrays.\n";
			output += "You get 5 years in prison and the computer gets 1.\n\n";
		}
		else if (userDecision == 2 && compDecision == 2)
		{
			stats.update(3, 3);
			output = "You both betray.\n";
			output += "You get 3 years in prison and the computer gets 3.\n\n";
		}
		else 
		{
			stats.update(0, 0);
			output = "";
		}
	}
	
	/*
	 * This method has the logic of Tit-For-Tat strategy used by the computer.
	 * 
	 * @param decision is the decision that is passed down to the function from the playRound() function.
	 * */
	public void titForTat(int decision)
	{
		// Get the user and comp decisions
		int userDecision = decision;
		int compDecision;
		
		// Tit For Tat computer logic
		if (stats.getRoundsPlayed() == 0)
		{
			compDecision = 1;
		}
		else
		{
			compDecision = userHistory.get(stats.getRoundsPlayed() - 1);
		}
		
		// Increment values and display information to the player
		incrementSentences(userDecision, compDecision);
	}
	
	/*
	 * This method has the logic of Tit-For-Two-Tats strategy used by the computer.
	 * 
	 * @param decision is the decision that is passed down to the function from the playRound() function.
	 * */
	public void titForTwoTats(int decision)
	{
		// Get the user and comp decisions
		int userDecision = decision;
		int compDecision;
		
		// Tit For Tat computer logic
		if (stats.getRoundsPlayed() == 0)
		{
			compDecision = 1;
		}
		else if (stats.getRoundsPlayed() == 1)
		{
			compDecision = 1;
		}
		else
		{
			//compDecision = userHistory.get(stats.getRoundsPlayed() - 1);
			if (userHistory.get(stats.getRoundsPlayed() - 1) == 2 && userHistory.get(stats.getRoundsPlayed() - 2) == 2)
				compDecision = 2;
			else compDecision = 1;
		}
		
		// Increment values and display information to the player
		incrementSentences(userDecision, compDecision);
	}
	
	/*
	 * This method has the logic of randomized selection strategy used by the computer.
	 * 
	 * @param decision is the decision that is passed down to the function from the playRound() function.
	 * */
	public void random(int decision)
	{
		// Get the user and comp decisions
		int userDecision = decision;
		int compDecision = (int) (Math.random()*2+1);
		
		// Increment values and display information to the player
		incrementSentences(userDecision, compDecision);
	}
	
	/*
	 * This method generates the computer's decisions based on the strategy selected,
	 * determines the outcome based on the two decisions, and updates the GameStat object
	 * according to the scoring. It uses helper functions to implement the strategies.
	 * 
	 * @param decision is the decision received by the user
	 * */
	public String playRound(int decision)
	{
		// Add the decision to the user History
		userHistory.add(decision);
		
		// Determine which strategy to use
		if(computerStrategy == 2)
		{
			titForTat(decision);
			return output;
		}
		else if (computerStrategy == 3)
		{
			titForTwoTats(decision);
			return output;
		}
		else if (computerStrategy == 4)
		{
			random(decision);
			return output;
		}
		else return("Not Implemented");
	}
}