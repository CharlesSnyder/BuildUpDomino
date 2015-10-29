//Name: Charles Snyder
//Project: Java/Android Build-Up Domino Game
//Class:  Organization of Programming Lanuages
//Date:  11/15/14


package edu.ramapo.csnyder2.gameLogic;

import java.util.ArrayList;

public abstract class Player {

	private int score;
	private boolean turn;
	private int wins;
	private Set playerSet;
	private ArrayList<Tile> playerHand;
	boolean endTurn;
	boolean passTurn;

	
	/**
	*Class constructor.
	*
	*Initializes score and wins to 0, turn, endTurn, and passTurn to false, 
	*creates a new Set defaulted to black color, a new ArrayList for the hand.
	*/
	public Player() {
		score = 0;
		wins = 0;
		turn = false;
		playerSet = new Set();
		playerHand = new ArrayList<Tile>();
		endTurn = false;
		passTurn = false;
	}
	
	/**
	*Class constructor used to copy a Player object.
	*
	*@param newPlayer  The player object whose variables will be copied.
	*/
	public Player(Player newPlayer) {
		score = newPlayer.getScore();
		wins = newPlayer.getWins();
		turn = newPlayer.getTurn();
		playerSet = newPlayer.getSet();
		playerHand = newPlayer.getHand();
		endTurn = newPlayer.getEndTurn();
		passTurn = newPlayer.getPassTurn();
	}

	
	/**
	*Class constructor used to maintain a player's win total, while resetting the other variables.
	*
	*@param winTotal  The number of wins the player has represented as an integer.
	*/
	public Player(int winTotal) {
		score = 0;
		wins = winTotal;
		turn = false;
		playerSet = new Set();
		playerHand = new ArrayList<Tile>();
		endTurn = false;
		passTurn = false;
	}
	
	
	/**
	*Get the score of the player.
	*
	*@return score   The score of the player represented as an integer.
	*/
	public final int getScore() {
		return score;
	}

	
	/**
	*Get the score of the player as a string.  Useful when writing to a file.
	*
	*@return score   The score of the player represented as an string.
	*/
	public String getScoreAsString() {
		return Integer.toString(score);
	}

	
	/**
	*Get the current turn status of a player.
	*
	*@return turn   True represents it is the player's turn, false represents it is not the player's turn.
	*/
	public final boolean getTurn() {
		return turn;
	}

	
	/**
	*Get the win total of a player.
	*
	*@return wins   Integer value of the number of wins a player has.
	*/
	public final int getWins() {
		return wins;
	}

	
	/**
	*Get the win total of a player as a string.  Useful for writing to a file.
	*
	*@return wins   String value of the number of wins a player has.
	*/
	public String getWinsAsString() {
		return Integer.toString(wins);
	}
	

	/**
	*Gets the player's end turn status for a hand.
	*
	*@return boolean true if the player's turn is over, false otherwise.
	*/
	public final boolean getEndTurn() {
		return endTurn;
	}

	/**
	*Gets the player's pass turn status for a hand.
	*
	*@return boolean true if the player passed, false otherwise.
	*/
	public final boolean getPassTurn() {
		return passTurn;
	}
	
	/**
	*Get the Set of the player.
	*
	*@return copySet    A Set object representing the player's current set of tiles.
	*/
	public Set getSet() {
		Set copySet = new Set(playerSet);
		return copySet;
	}
	
	
	/**
	*Get the size of the player's set, that is how many tiles are in it.
	*
	*@return Integer value representing the number of tiles in the player's set.
	*/
	public final int getBoneyardSize() {
		return playerSet.getSetSize();
	}

	/**
	*Get the player's set as a string.
	*
	*@return String containing all the tiles in the player's set delimited by spaces.
	*/	
	public String getBoneyardAsString() {
		return playerSet.getSetAsString();
	}
	
	
	/**
	*Get the hand of the player.
	*
	*@return handCopy   An ArrayList of type Tile which holds the current tile objects in the player's hand.
	*/	
	public ArrayList<Tile> getHand() {
		ArrayList<Tile> handCopy = new ArrayList<Tile>(playerHand);
		return handCopy;
	}
	
	
	/**
	*Get the size of the player's hand, that is how many tiles are in it.
	*
	*@return Integer value representing the number of tiles in the player's hand.
	*/
	public final int getHandSize() {
		return playerHand.size();
	}
	

	/**
	*Get the player's hand as a string.
	*
	*@return String containing all the tiles in the player's hand delimited by spaces.
	*/		
	public String getHandAsString() {
		String handString = "";
		for (int index = 0; index < playerHand.size(); index++) {
			handString = handString + playerHand.get(index).tileToString() + " ";
		}
		return handString;
	}
	
	/**
	*Set the player's turn status to active or inactive.
	*
	*@param isTurn  A boolean value to set the player's turn to true or false.
	*/
	public void setTurn(boolean isTurn) {
		turn = isTurn;
	}

	
	/**
	*Set the player's score to a certain value.
	*
	*@param newScore  Integer value which will become the player's current score.
	*/
	public void setScore(int newScore) {
		score = newScore;
	}

	
	/**
	*Set the player's wins to a certain value.
	*
	*@param winTotal  Integer value which will become the player's current win total.
	*/
	public void setWins(int winTotal) {
		wins = winTotal;
	}

	/**
	*Set a tile in the hand at a specific index.
	*
	*@param index  Integer value specifying where the tile should be placed in the hand.
	*@param replaceTile   The tile object to be placed in the hand.
	*/		
	public void setTileInHand(int index, Tile replaceTile) {
		playerHand.set(index, replaceTile);
	}

	
	/**
	*Set the hand ArrayList of the player.
	*
	*@param newHand  ArrayList of type Tile which gets copied to the player's hand.
	*/	
	public void setHand(ArrayList<Tile> newHand) {
		playerHand = new ArrayList<Tile>(newHand);
	}

	
	/**
	*Set the Set object of the player.
	*
	*@param newSet  A Set object which gets copied to the player's set.
	*/	
	public void setPlayerSet(Set newSet) {
		playerSet = new Set(newSet);
	}
	
	/**
	*Sets the player's end turn status for a hand.
	*
	*@param newEndTurn   A boolean value if true will end the player's turn for a hand, if false it
	*means the player still has moves to make.
	*/
	public void setEndTurn(boolean newEndTurn) {
		endTurn = newEndTurn;
	}
	
	/**
	*Sets the player's pass turn status for a hand.
	*
	*@param newPassTurn   A boolean value if true will pass the player's turn, if false it
	*means the player did not pass their last turn.
	*/
	public void setPassTurn(boolean newPassTurn) {
		passTurn = newPassTurn;
	}

	
	/**
	*Determine's the proper size of the player's hand depending on the location in the game.
	*
	*@return handSize  Integer value of either 4, 5, or 6 depending on the current round of the game.
	*/	
	public int determineHandSize() {
		final int MAX_HAND_SIZE = 6;
		final int MIN_HAND_SIZE = 4;
		final int SET_AFTER_FIRST_PLAYER = 21;
		final int REMAINING_HAND_FIRST_PLAYER = 5;

		int handSize = 0;
		// Condition for second and third rounds
		if (playerSet.getSetSize() > MIN_HAND_SIZE
				&& playerSet.getSetSize() != SET_AFTER_FIRST_PLAYER) {
			handSize = MAX_HAND_SIZE;
			// Condition for first round, only 5 because 1 already in hand from
			// determining first player
		} else if (playerSet.getSetSize() == SET_AFTER_FIRST_PLAYER) {
			handSize = REMAINING_HAND_FIRST_PLAYER;
			// Condition for final hand in round
		} else if (playerSet.getSetSize() == MIN_HAND_SIZE) {
			handSize = MIN_HAND_SIZE;
		}
		return handSize;
	}

	/**
	*Draw tiles into the player's hand. Concurrently removes the tile added to the hand from the player's set.
	*
	*/	
	public void drawHand() {
		int handSize = determineHandSize();
		if (handSize > 0) {
			for (int index = 0; index < handSize; index++) {
				playerHand.add(playerSet.removeTile());
			}
		}
	}

	
	/**
	*Shuffle the player's set using Set object's shuffle method.
	*
	*/	
	public void shuffleSet() {
		playerSet.shuffleTiles();
	}
	
	/**
	*Check to see if a move is within the rules of the game.
	*
	*@param userTile   A tile object that represents the tile the player is attempting to move.
	*@param boardTile  A tile object that represents the tile the player is trying to replace on the board.
	*@return True if the move is within the rules,  false otherwise.
	*/
	public boolean isValidMove(Tile userTile, Tile boardTile) {
		// Get pip total
		int userPipsTotal = userTile.getTotalTilePipScore();
		int boardPipsTotal = boardTile.getTotalTilePipScore();
		
		// User wants to play double tile
		if (userTile.getPipsLeftEnd() == userTile.getPipsRightEnd()) {
			// Tile on board is a double tile
			if (boardTile.getPipsLeftEnd() == boardTile.getPipsRightEnd()) {
				if (userPipsTotal > boardPipsTotal) {
					return true;
				} else {
					return false;
				}
				// Tile on board is non-double
			} else {
				return true;
			}
			// UserTile is a non-double tile
		} else {
			// Tile on board double and non-double
			if(userPipsTotal >= boardPipsTotal) {
				return true;
			} 
			else {
				return false;
			}
		}
	}

	/**
	*Place a chosen tile onto a specific board location.  Also removes the chosen tile from the player's hand and updates the board stacks.
	*
	*@param gameBoard   A board object that contains all the current tiles on the board.
	*@param location   A string value that indicates which space the player would like to place a tile.
	*@param selectedTile  The tile the player wishes to place on the board.
	*@throws Exception if either the selectedTile is not in the player's hand or an invalid board location was specified.
	*/	
	public void placeTile(Board gameBoard, String location, Tile selectedTile)
			throws Exception {
		if (isInHand(selectedTile) == true) {
			// check all board locations
			if (location.equalsIgnoreCase("B1")) {
				try {
					gameBoard.setB1(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("B2")) {
				try {
					gameBoard.setB2(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("B3")) {
				try {
					gameBoard.setB3(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("B4")) {
				try {
					gameBoard.setB4(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("B5")) {
				try {
					gameBoard.setB5(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("B6")) {
				try {
					gameBoard.setB6(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("W1")) {
				try {
					gameBoard.setW1(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("W2")) {
				try {
					gameBoard.setW2(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("W3")) {
				try {
					gameBoard.setW3(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("W4")) {
				try {
					gameBoard.setW4(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("W5")) {
				try {
					gameBoard.setW5(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else if (location.equalsIgnoreCase("W6")) {
				try {
					gameBoard.setW6(selectedTile);
					removeTileFromHand(selectedTile);
				} catch (Exception setError) {
					System.err.println(setError.getMessage());
				}
			} else {
				Exception invalidLocation = new Exception("Invalid location");
				throw invalidLocation;
			}
		} else {
			Exception invalidTile = new Exception("Tile not in hand");
			throw invalidTile;
		}
	}

	
	/**
	*Checks to see if a specified tile is in the player's hand.
	*
	*@param myTile  Tile object to find in the hand ArrayList.
	*@return  True if myTile is found in the ArrayList, false otherwise.
	*/	
	public boolean isInHand(Tile myTile) {
		boolean found = false;
		for (int index = 0; index < playerHand.size(); index++) {
			if (myTile.equals(playerHand.get(index)) ) {
				found = true;
				break;
			}
		}
		return found;
	}

	
	/**
	*Removes a tile from the player's hand ArrayList.
	*
	*@param myTile  Tile object to be removed from the hand.
	*@throws  Exception if the tile could not be removed, if it does not exist in the hand.
	*/	
	public void removeTileFromHand(Tile myTile) throws Exception {
		if (!playerHand.remove(myTile)) {
			Exception removeException = new Exception(
					"Tile could not be removed");
			throw removeException;
		}
	}
	
	
	/**
	*Removes a tile from the player's Set.
	*
	*@return  A Tile object from the front of the player's Set object.
	*/	
	public Tile drawSingleTileFromSet() {
		return playerSet.removeTile();
	}

	
	/**
	*Adds a tile to the player's Set.
	*
	*@param myTile   Tile object to be added to the player's set.
	*/	
	public void placeTileBackInSet(Tile myTile) {
		playerSet.returnTileToSet(myTile);
	}

	/**
	*Adds a tile to the player's hand.
	*
	*@param myTile   Tile object to be added to the player's hand.
	*/	
	public void addSingleTileToHand(Tile myTile) {
		playerHand.add(myTile);
	}

	
	/**
	*Checks to see if the player's hand is currently empty.
	*
	*@return True if the hand ArrayList is not empty, false if it is empty.
	*/	
	public boolean isHandEmpty() {
		if (playerHand.isEmpty() == false) {
			return false;
		} else {
			return true;
		}
	}

	
	/**
	*Add a win or multiple wins to the player's current win total.
	*
	*@param victory  An integer value representing the number of wins to increase the total by.
	*/
	public void addWin(int victory) {
		wins = wins + victory;
	}
	
	
	/**
	*Clears the player's hand of all tiles.
	*
	*/	
	public void clearHand() {
		playerHand.clear();
	}

	
	/**
	*Clears the player's set of all tiles.
	*
	*/	
	public void clearPlayerSet() {
		playerSet.clearSet();
	}
	
	
	/**
	*Calculate the score of a player based on rules of the game.
	*
	*@param gameBoard  A Board object which is necessary to determine the state of the board when calculating.
	*/
	abstract void calculateScore(Board gameBoard);
	
	/**
	*Finds which locations on the board belong to the player, that is which locations have a tile on them
	*which belongs to the player.
	*
	*@param boardSpace   The location on the board to check.
	*/	
	abstract void findControlledStacks(String boardSpace);
	
}
