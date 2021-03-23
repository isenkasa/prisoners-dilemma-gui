/****************************************************************
* Created by: Isen Kasoski (Z1875089)
* Date: 3/20/2021
* Purpose: Class that creates GUI and runs the Prisoners Dilemma game.
*****************************************************************/
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PDGameGui extends JFrame implements ActionListener , ListSelectionListener 
{

	private final DefaultListModel<String> listModelPtr = new DefaultListModel<String>();
    // Default List model is the standard “mode for how a Jlist will be operated, will put in next statement below

	private JList<String> finishedGamesListPtr = new JList<String>(listModelPtr); 
    // this  is list on top left side and will  show times of games played that user will click to see stats of a game 

	private  JComboBox<Object> computerStrategyCB=null; //combo box on right side, pointer will be filled in constructor   

	private final JTextArea gameResultsTA = new JTextArea(15, 30); //this is  large text area on right side
	
	private PDGame currentPDGame = null;
	private String gameStartTimeStr = null;
	private final HashMap<String, GameStat> stats = new HashMap<>(); //keep same hashmap for games played

	private int computerStrategy = 1;  //this will be filled in by the choice made by user in combo box
	
	// Text Fields - WEST
	private final JTextField roundsTF = new JTextField(10);
	private final JTextField computerStrategyTF = new JTextField(10);
	private final JTextField playerSentenceTF = new JTextField(10);
	private final JTextField computerSentenceTF = new JTextField(10);
	private final JTextField winnerTF = new JTextField(10);

	// EAST
	private final JLabel computerStrategyL = new JLabel("Computer Strategy-Combo Box");
	private final JButton startB = new JButton("Start New Game");
	private final JButton silentB = new JButton("Remain silent");
	private final JButton betrayB = new JButton("Testify");
	private final JLabel decisionL = new JLabel("Your decision this round?");
	
	// Text
	JTextArea output = new JTextArea(15, 35);
	
	private String searchKey;
	private String gameStartTime;

	// main method - creates and shows GUI
	public static void main(String[] args)
	{
		createAndShowGUI();
	}
	
	/*
	 * This method creates and shows GUI.
	 * */
	public static void createAndShowGUI()
	{
		// Create and set up the window
		PDGameGui pdgame = new PDGameGui();
		pdgame.addListeners();	// add listeners to buttons
		// Pack and display panels
		pdgame.pack();
		pdgame.setVisible(true);
	}
	
	// Constructor where the swing interface is built
	public PDGameGui()
	{
		super("Prisoner's Dilemma");
		// Create the game
		currentPDGame = new PDGame();
				
		// Create panels
		JPanel westPanel = new JPanel(new BorderLayout());
		JPanel eastPanel = new JPanel(new BorderLayout());
				
		// Different colors
		westPanel.setBackground(Color.white);
		eastPanel.setBackground(Color.white);
				
		// Set sizes
		westPanel.setPreferredSize(new Dimension(350, 500));
		eastPanel.setPreferredSize(new Dimension(500, 500));	
				
		// Add the panels to the frame container
		add(westPanel, BorderLayout.WEST);
		add(eastPanel, BorderLayout.EAST);
				
		// --- WEST PANEL ---
		// Add JList -> JScrollPane -> westPanel (NORTH)
		finishedGamesListPtr.setFont(new Font("SansSerif", Font.BOLD, 12));
		finishedGamesListPtr.setVisibleRowCount(10);
		finishedGamesListPtr.setFixedCellWidth(350);
		finishedGamesListPtr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		finishedGamesListPtr.setBackground(new Color(236, 242, 248));
		westPanel.add(new JScrollPane(finishedGamesListPtr), BorderLayout.NORTH);
				
		// Add JPanel -> GridLayout -> westPanel (SOUTH)
		JPanel statistics = new JPanel(new BorderLayout());
		statistics.setVisible(true);
		statistics.setBackground(new Color (217, 230, 242));
		statistics.setPreferredSize(new Dimension(200, 300));
		westPanel.add(statistics, BorderLayout.SOUTH);
		
		// Add content to statistics
		statistics.setLayout(new GridLayout(5, 2));
		statistics.add(new JLabel("Rounds Played"), BorderLayout.WEST);
		statistics.add(roundsTF, BorderLayout.EAST);
		
		statistics.add(new JLabel("Computer Strategy"), BorderLayout.WEST);
		statistics.add(computerStrategyTF, BorderLayout.EAST);
		
		statistics.add(new JLabel("Player Sentence"), BorderLayout.WEST);
		statistics.add(playerSentenceTF, BorderLayout.EAST);
		
		statistics.add(new JLabel("Computer Sentence"), BorderLayout.WEST);
		statistics.add(computerSentenceTF, BorderLayout.EAST);
		
		statistics.add(new JLabel("Winner"), BorderLayout.WEST);
		statistics.add(winnerTF, BorderLayout.EAST);
				
		// --- EAST PANEL ---
		// Add JPanel -> GridLayout -> eastPanel (NORTH)
		JPanel gameplay = new JPanel(new BorderLayout(2, 1));
		gameplay.setVisible(true);
		gameplay.setBackground(new Color (51, 102, 153));
		gameplay.setPreferredSize(new Dimension(150, 205));
		eastPanel.add(gameplay, BorderLayout.NORTH);
				
		// Add FlowLayouts
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.setBackground(new Color(0,102,150));
		topPanel.setPreferredSize(new Dimension(100, 105));
		gameplay.add(topPanel, BorderLayout.NORTH);
				
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.setBackground(new Color(0,143,185));
		bottomPanel.setPreferredSize(new Dimension(100, 105));
		gameplay.add(bottomPanel, BorderLayout.SOUTH);
		
		// Add content to FlowLayouts
		topPanel.add(new JLabel("Computer Strategy"));
		
		//Two statements below prepare the combo box with computer strategies, need to convert the strategies array list to an array , and then it gets placed in combo box
		Object[] strategyArray = currentPDGame.getStrategies().toArray();//convert AL to array
		computerStrategyCB = new JComboBox<Object>(strategyArray);   //place array in combo box
		computerStrategyCB.setEditable(false);
		computerStrategyCB.setSelectedIndex(0); //this sets starting value to first string in array
		topPanel.add(computerStrategyCB);
		
		topPanel.add(startB);
		
		bottomPanel.add(new JLabel("Your decision this round?"));
		bottomPanel.add(silentB);
		bottomPanel.add(betrayB);
		
		// Add JTextArea -> JScrollPane -> eastPanel (SOUTH) - 15 x 35
		JScrollPane outputScrollPane = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		eastPanel.add(outputScrollPane, BorderLayout.SOUTH);
		output.append("***Prisoner's Dilemma***\n\n");
	}
	
	/*
	 * This method adds listeners to buttons and other components.
	 * */
	public void addListeners() 
	{
		// Add Action Listeners
		startB.addActionListener(this);
		silentB.addActionListener(this);
		betrayB.addActionListener(this);
		computerStrategyCB.addActionListener(this);
		
		finishedGamesListPtr.addListSelectionListener(this); //the  JLIST event listener code is addListSelectionListener
	}
	
	/*
	 * This method catches the actions performed and calls the respective functions.
	 * 
	 * @param e is the action event
	 * */
	public void actionPerformed(ActionEvent e) 
	{
		// Start the game
	    if (e.getSource() == startB) 
	    {
	       startGame();
	    } // Cooperate
	    else if (e.getSource() == silentB) 
	    {
	       cooperate();
	    } // Betray
	    else if (e.getSource() == betrayB) 
	    {
	       betray();
	    }  // Choose a Strategy
	    else if (e.getSource() == computerStrategyCB) 
	    {  
	       //when user chooses an item in combo box, this handles it
	       computerStrategy = computerStrategyCB.getSelectedIndex() + 1; //fills in this variable up top
	    }
	}

	/*
	 * This method catches the changed values.
	 * 
	 * @param e is the list selection event
	 * */
	public void valueChanged(ListSelectionEvent e) 
	{
	      if (!finishedGamesListPtr.isSelectionEmpty()) 
	      {
	    	  // Establish a search key
	         searchKey = (String) finishedGamesListPtr.getSelectedValue(); //get out time of game and look up in hash map
	         
	         // Select the stat
	         GameStat selectedStat = stats.get(searchKey);
	         
	         // Get rounds
	         roundsTF.setText(new Integer(selectedStat.getRoundsPlayed()).toString());
	         roundsTF.setFont( new Font("SansSerif", Font.BOLD, 15));

	         // Get comp strategy
	         computerStrategyTF.setFont(new Font("SansSerif", Font.BOLD, 15));
	         computerStrategyTF.setText(String.format("%s", selectedStat.getComputerStrategy()));
	         
	         // Get player sentence
	         playerSentenceTF.setFont( new Font("SansSerif", Font.BOLD, 15));
	         playerSentenceTF.setText(String.format("%d %s", selectedStat.getPlayerYears(), 
	                 ((selectedStat.getPlayerYears() > 1) ? " years" : " year")));
	         
	         // Get computer sentence
	         computerSentenceTF.setFont( new Font("SansSerif", Font.BOLD, 15));
	         computerSentenceTF.setText(String.format("%d %s", selectedStat.getComputerYears(), 
	                 ((selectedStat.getComputerYears() > 1) ? " years" : " year")));
	         
	         // Get winner
	         winnerTF.setFont(new Font("SansSerif", Font.BOLD, 15));
	         winnerTF.setText(String.format("%s", selectedStat.getWinner()));
	       }
	}

	
	/*
	 * This method begins a new game.
	 * */
	public void startGame() 
	{
	    currentPDGame = new PDGame();
	    currentPDGame.setComputerStrategy(computerStrategy);
	      
	    gameStartTime = new Date().toString();
	    //stats.put(gameStartTime, currentPDGame.getStats());
	    promptPlayer();
	}
	
	/*
	 * This method prompts the player to make a selection.
	 * */
	public void promptPlayer()
	{
		// On the fifth round, determine the winner.
		if (currentPDGame.getStats().getRoundsPlayed() == 5)
		{
			determineWinner();
		}
		else
		{	// Display options
			output.append("BEGIN A ROUND - Here are your 2 choices\n\n");
			output.append("1. Remain Silent.\n");
			output.append("2. Betray and testify against.\n\n");
			output.append("What is your decision this round?\n\n");
		}
	}
	
	/*
	 * This method is called when the player decides to cooperate.
	 * */
	public void cooperate()
	{
		output.append(currentPDGame.playRound(1));
		promptPlayer();
	}
	
	/*
	 * This method is called when the player decides to betray.
	 * */
	public void betray()
	{
		output.append(currentPDGame.playRound(2));
		promptPlayer();
	}
	
	/*
	 * This method determines the winner of the game.
	 * */
	public void determineWinner()
	{
		// Display sentences
		output.append("\nEND OF ROUNDS, GAME OVER\n");
		output.append("-- Your prison sentence is: " + currentPDGame.getStats().getPlayerYears());
		output.append("\n-- The computer's sentence is: " + currentPDGame.getStats().getComputerYears() + "\n");
		
		// Display the winner
		output.append(currentPDGame.getStats().getWinner());
		output.append(" is the winner");
		output.append("\n\n");
		
		// Store the information in the HashMap
		GameStat statistics = new GameStat(currentPDGame.getStats());
		stats.put(gameStartTime, statistics);
		listModelPtr.addElement(gameStartTime);
		
		// Reset the variables for reuse
		currentPDGame.clearUserHistory();
		currentPDGame.getStats().reset();
	}
}