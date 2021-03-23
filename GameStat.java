/****************************************************************
* Created by: Isen Kasoski (Z1875089)
* Date: 2/15/2021
* Purpose: Class that holds information about Game Statistics.
* 		   It holds information such as the sentences for both
* 		   the player and the computer, as well as rounds played
* 		   in a game and the computer strategy used in that game.
*****************************************************************/

public class GameStat 
{
	private int playerYears;
	private int computerYears;
	private int roundsPlayed;
	private String computerStrategy;
	
	// Default Constructor
	GameStat()
	{
		playerYears = 0;
		computerYears = 0;
		roundsPlayed = 0;
	}
	
	// Copy Constructor
	GameStat(GameStat gameStat)
	{
		playerYears = gameStat.getPlayerYears();
		computerYears = gameStat.getComputerYears();
		roundsPlayed = gameStat.getRoundsPlayed();
		computerStrategy = gameStat.getComputerStrategy();
	}
	
	/* playerYears getter and setter */
	public int getPlayerYears() {
		return playerYears;
	}
	
	public void setPlayerYears(int playerYears) {
		this.playerYears = playerYears;
	}

	
	/* computerYears getter and setter */
	public int getComputerYears() {
		return computerYears;
	}

	public void setComputerYears(int computerYears) {
		this.computerYears = computerYears;
	}

	/* roundsPlayed getter and setter */
	public int getRoundsPlayed() {
		return roundsPlayed;
	}

	public void setRoundsPlayed(int roundsPlayed) {
		this.roundsPlayed = roundsPlayed;
	}

	/* computerStrategy getter and setter */
	public String getComputerStrategy() {
		return computerStrategy;
	}

	public void setComputerStrategy(String computerStrategy) {
		this.computerStrategy = computerStrategy;
	}
	
	/*
	 * This method updates the sentences for both user and computer, as well as increments
	 * the roundsPlayed variable.
	 * 
	 * @param userSentence - user sentence increment
	 * @param compSentence - computer sentence increment
	 * */
	public void update(int userSentence, int compSentence) {
		playerYears += userSentence;
		computerYears += compSentence;
		roundsPlayed++;
	}
	
	/*
	 * This method determines the winner and returns it to the caller.
	 * 
	 * @return String specifying the winner of the game.
	 * */
	public String getWinner()
	{		
		if(playerYears < computerYears)
			return "Player";
		else if(playerYears > computerYears)
			return "Computer";
		else if(playerYears == computerYears)
			return "Tie";
		else return "N/A";
	}
	
	/*
	 * Method that resets the class variables for later reuse in a different round.
	 * */
	public void reset()
	{
		playerYears = 0;
		computerYears = 0;
		roundsPlayed = 0;
	}
}