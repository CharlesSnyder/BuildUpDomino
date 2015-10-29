//Name: Charles Snyder
//Project: Java/Android Build-Up Domino Game
//Class:  Organization of Programming Lanuages
//Date:  11/15/14

package edu.ramapo.csnyder2.gameLogic;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Computer extends Player implements Parcelable {
	private Tile tilePlayed;
	private Tile tileReplaced;
	private String location;

	
	/**
	*Class constructor.
	*
	*Uses parent class constructor and extends on it initializing a new Set object
	*of the color white and a new ArrayList of tile objects representing the computer player's hand.
	*Also sets tilePlayed and tileReplaced to default B00 tile and location to a blank string.
	*/
	public Computer() {
		super();
		try {
			setPlayerSet(new Set('W'));
			tilePlayed = new Tile();
			tileReplaced = new Tile();
			location = "";
		}
		catch(Exception setException) {
			System.err.println(setException.getMessage());
		}
	}
	
	
	/**
	*Class constructor used to copy a Computer object.
	*
	*@param newComputer   The computer object whose values will be copied.
	*/
	public Computer(Computer newComputer) {
		super(newComputer);
		tilePlayed = new Tile(newComputer.getTilePlayed());
		tileReplaced = new Tile(newComputer.getTileReplaced());
		location = newComputer.getLocationPlayed();
	}
	
	
	/**
	*Class constructor used to preserve the win total of the computer player.
	*
	*@param wins   Resets all variables of the object except for the integer value of wins.
	*@throws  Exception if invalid Set is created.
	*/
	public Computer(int wins) {
		super(wins);
		try {
			setPlayerSet(new Set('W'));
			tilePlayed = new Tile();
			tileReplaced = new Tile();
			location = "";
		}
		catch(Exception setException) {
			System.err.println(setException.getMessage());
		}
	}
	
	
	/**
	*Get the tile that the computer player played on its last turn.
	*
	*@return tileCopy  The tile object the computer last made a move with.
	*/	
	public Tile getTilePlayed() {
		Tile tileCopy = new Tile(tilePlayed);
		return tileCopy;
	}
	
	
	/**
	*Get the tile the computer replaced on the board with its last turn.
	*
	*@return tileCopy   The tile object which was on the board and when the computer made its last move.
	*/	
	public Tile getTileReplaced() {
		Tile tileCopy = new Tile(tileReplaced);
		return tileCopy;
	}
	
	
	/**
	*Get the location on the board the computer made its last turn.
	*
	*@return String value representing the board space the computers last move occurred.
	*/	
	public String getLocationPlayed() {
		return location;
	}
	
	
	/**
	*Set the last tile played for the computer player.
	*
	*@param played  A tile object which represents the tile that the computer player used in its last move.
	*@throws Exception if invalid tile is set.
	*/	
	public void setTilePlayed(Tile played) {
		try {
			tilePlayed.setTile(played);
		} catch (Exception setTileException) {
			System.err.println(setTileException.getMessage());
		}
	}
	
	/**
	*Set the last tile replaced for the computer player.
	*
	*@param replaced  A tile object which represents the tile that the computer player replaced in its last move.
	*@throws Exception if invalid tile is set.
	*/	
	public void setTileReplaced(Tile replaced) {
		try {
			tileReplaced.setTile(replaced);
		} catch (Exception setTileException) {
			System.err.println(setTileException.getMessage());
		}
	}

	/**
	*Set the last tile replaced for the computer player.
	*
	*@param locationReplaced  A tile object which represents the tile that the computer player replaced in its last move.
	*@throws Exception if invalid tile is set.
	*/	
	public void setLocationReplaced(String locationReplaced) throws Exception {
		if(locationReplaced.equalsIgnoreCase("B1") || locationReplaced.equalsIgnoreCase("B2")  || locationReplaced.equalsIgnoreCase("B3")
				 || locationReplaced.equalsIgnoreCase("B4") || locationReplaced.equalsIgnoreCase("B5") || locationReplaced.equalsIgnoreCase("B6")
				 || locationReplaced.equalsIgnoreCase("W1") || locationReplaced.equalsIgnoreCase("W2") || locationReplaced.equalsIgnoreCase("W3")
				 || locationReplaced.equalsIgnoreCase("W4") || locationReplaced.equalsIgnoreCase("W5") || locationReplaced.equalsIgnoreCase("W6")) {
			location = locationReplaced;
		}
		else {
			Exception invalidLocation = new Exception("Invalid location");
			throw invalidLocation;
		}
	}


	
	@Override
	public void calculateScore(Board gameBoard) {
		findControlledStacks(gameBoard.getB1());
		findControlledStacks(gameBoard.getB2());
		findControlledStacks(gameBoard.getB3());
		findControlledStacks(gameBoard.getB4());
		findControlledStacks(gameBoard.getB5());
		findControlledStacks(gameBoard.getB6());
		findControlledStacks(gameBoard.getW1());
		findControlledStacks(gameBoard.getW2());
		findControlledStacks(gameBoard.getW3());
		findControlledStacks(gameBoard.getW4());
		findControlledStacks(gameBoard.getW5());
		findControlledStacks(gameBoard.getW6());
		// Checking to see if any tiles remain in hand
		if (getHandSize() > 0) {
			for (int index = 0; index < getHandSize(); index++) {
				Tile tmp = new Tile();
				tmp = getHand().get(index);
				int newScore = getScore() - tmp.getPipsLeftEnd() - tmp.getPipsRightEnd();
				setScore(newScore);
			}
		}
	}

	
	@Override
	public void findControlledStacks(String boardSpace) {
		Tile boardTile = new Tile();
		boardTile = boardTile.stringToTile(boardSpace);
		if (boardTile.getColor() == 'W') {
			int newScore = getScore() + boardTile.getPipsLeftEnd() + boardTile.getPipsRightEnd();
			setScore(newScore);
		}		
	}

	
	/**
	*Gets a tile from the computer player's hand for it to use in a move.
	*
	*@param handLocation   Integer value representing the index in the hand ArrayList of the computer player.
	*@return Tile object from the computer player's hand at a specified index.
	*/	
	public Tile selectTileToPlay(int handLocation) {
		ArrayList<Tile> cpuHand = new ArrayList<Tile>(getHand());
		return cpuHand.get(handLocation);
	}
	
	
	/**
	*Selects a location on the board to play the selected tile.
	*
	*@param gameBoard   A Board object that holds all the tiles that are currently on the board.
	*@param bestMove    An ArrayList of integers which rates the best possible moves to make based on score differential.
	*					The higher the number the more desirable the move.
	*@return maxLocationString   A string of the location on the board where a move should be attempted. 
	*/	
	public String selectLocationToPlay(Board gameBoard, ArrayList<Integer> bestMove) {
		int maxScore = -100;
		int maxLocation = 0;
		String maxLocationString = "";

		//Finding highest value in bestMove vector
		for(int index = 0; index < bestMove.size(); index++) {
			if(bestMove.get(index) > maxScore) {
				maxScore = bestMove.get(index);
				maxLocation = index;
			}
		}
		//At index where highest value found assign maxLocationString the name of the board space at that index
		for(int boardIndex = 0; boardIndex < gameBoard.getAllBoardSpaces().size(); boardIndex++) {
			if(maxLocation == boardIndex) {
				maxLocationString = gameBoard.getAllBoardSpaces().get(boardIndex);
				bestMove.set(maxLocation, -100);
				break;
			}
		}
		return maxLocationString;
	}	

	
	/**
	*Sorts the hand of the computer player according to playing algorithm.  That is Non-doubles first ordered from highest to lowest,
	*followed by doubles ordered from lowest to highest.
	*
	*/	
	public void sortHand() {
		//Used to keep place in the two loops.
		int tileCount = 1;
	
		for(int handCount = 0; handCount < getHand().size(); handCount++) {
			//Max gets set to tile at handCount index value.
			Tile max = new Tile();
			max = getHand().get(handCount);
			int maxPipScore = 0;
			//Determine pip score, non-doubles valued higher than doubles.
			if(max.getPipsLeftEnd() != max.getPipsRightEnd()) {
				maxPipScore = max.getTotalTilePipScore();
			}
			else {
				maxPipScore = ~ (max.getTotalTilePipScore());
			}
			
			int maxLocation = handCount;
			//One index higher than handCount the inner loop begins.
			for(tileCount = handCount + 1; tileCount < getHand().size(); tileCount++) {
				Tile compareTile = new Tile();
				compareTile = getHand().get(tileCount);
				int compareTilePipScore = 0;
				//Determining pip score.
				if(compareTile.getPipsLeftEnd() != compareTile.getPipsRightEnd()) {
					compareTilePipScore = compareTile.getTotalTilePipScore();
				}
				else {
					compareTilePipScore = ~ (compareTile.getTotalTilePipScore());
				}
				//Comparing to find max value.
				if(compareTilePipScore > maxPipScore) {
					max = compareTile;
					maxPipScore = compareTilePipScore;
					maxLocation = tileCount;
				}
					
			}
			//If max value is not located where the outer loop started it swaps the two tiles.
			if(maxLocation != handCount) {
				Tile tmp = new Tile();
				tmp = getHand().get(handCount);
				setTileInHand(handCount, max);
				setTileInHand(maxLocation, tmp);
			}
		
		}
	}
	

	/**
	*Creates an ArrayList of integers that represent the best possible moves on the board for a given tile.
	*
	*@param cpuSelectedTile   A Tile object that the computer player chose from its hand.
	*@param gameBoard    A Board object that holds all the tiles currently on the board.
	*
	*@return bestMove   An ArrayList of integers whose values represent the desirability of a move at each location on the board. 
	*				    The higher the number the more desirable the move.
	*/
	public ArrayList<Integer> calculateBestMoveVector(Tile cpuSelectedTile, Board gameBoard) {
		ArrayList<Integer> bestMove = new ArrayList<Integer>();
		for(int space = 0; space < gameBoard.getBoardStacks().size(); space++) {
			Tile boardTile = new Tile();
			//Get boardTile from the game board.
			String boardTileString = gameBoard.getBoardStacks().get(space);
			//Convert the tile from string to Tile.
			boardTile = boardTile.stringToTile(boardTileString);
			int moveScore = 0;
			//Positive score computer tile on human tile.
			if(cpuSelectedTile.getColor() == 'W' && boardTile.getColor() == 'B') {
				moveScore = (cpuSelectedTile.getPipsLeftEnd() + cpuSelectedTile.getPipsRightEnd()) + (boardTile.getPipsLeftEnd() + boardTile.getPipsRightEnd());
			}
			//Negative score computer tile on own computer tile.
			else if(cpuSelectedTile.getColor() == 'W' && boardTile.getColor() == 'W') {
				moveScore = (cpuSelectedTile.getPipsLeftEnd() + cpuSelectedTile.getPipsRightEnd()) - (boardTile.getPipsLeftEnd() + boardTile.getPipsRightEnd()) - 10;
			}
			//This is for help function, positive score human tile on computer tile.
			else if(cpuSelectedTile.getColor() == 'B' && boardTile.getColor() == 'W') {
				moveScore = (cpuSelectedTile.getPipsLeftEnd() + cpuSelectedTile.getPipsRightEnd()) + (boardTile.getPipsLeftEnd() + boardTile.getPipsRightEnd());
			}
			//Also for help function, negative score human tile on own human tile.
			else if(cpuSelectedTile.getColor() == 'B' && boardTile.getColor() == 'B') {
				moveScore = (cpuSelectedTile.getPipsLeftEnd() + cpuSelectedTile.getPipsRightEnd()) - (boardTile.getPipsLeftEnd() + boardTile.getPipsRightEnd()) - 10;
			}
			bestMove.add(moveScore);
		}
		return bestMove;
	}

	
	/**
	*Checks to see whether the computer has any moves available with the chosen tile from its hand.
	*
	*@param cpuSelectTile   A Tile object that the computer player chose from its hand.
	*@param gameBoard    A Board object that holds all the tiles currently on the board.
	*
	*@return True if there is at least one possible move, false otherwise.
	*/
	public boolean canCPUMakeMove(Tile cpuSelectTile, Board gameBoard) {
		boolean makeMove = false;
		Tile boardTile = new Tile();
		for(int space = 0; space < gameBoard.getAllBoardSpaces().size(); space++) {
			if(isValidMove(cpuSelectTile, boardTile.stringToTile(gameBoard.getBoardStacks().get(space))) == true) {
				makeMove = true;
				break;
			}
		}
		return makeMove;
	}

	
	
	/**
	*Performs the action of the computer player choosing a tile, choosing a location, and then executing the move if possible.
	*Also used for help function to determine best move for human player but a move will not be executed.
	*
	*@param gameBoard    A Board object that holds all the tiles currently on the board.
	*@param help    Boolean value used to determine if this is being used for the computer's move or help functionality.
	*			    It is set to true if used for the help functionality, false for the computer's turn.
	*
	*@return True if a move was executed. A return value of false means no moves were possible and the computer must pass or
	*a help recommendation will be to pass.
	*@throws Exception if an invalid tile or location is specified for placeTile function.
	*/
	public boolean playTile(Board gameBoard, boolean help) throws Exception {
		int handLocation = 0;
		boolean canMove = false;
		
		while(handLocation < getHand().size()) {
			//Select tile from hand.
			Tile cpuTile = new Tile(selectTileToPlay(handLocation));
			//See if any moves are possible with that tile.
			boolean possibleMove = canCPUMakeMove(cpuTile, gameBoard);

			if(possibleMove == true) {
				ArrayList<Integer> bestMove = new ArrayList<Integer>(calculateBestMoveVector(cpuTile, gameBoard));
				boolean foundLocation;
				do {
					//Select location to play tile.
					String cpuLocation = selectLocationToPlay(gameBoard, bestMove);

					//See if selected location would yield a valid move.
					Tile boardTile = new Tile();
					if(isValidMove(cpuTile, boardTile.stringToTile(gameBoard.getBoardTile(cpuLocation))) == true) {
						//Record the tile played, tile replaced, and location for display purposes.
						setTilePlayed(cpuTile);
						setTileReplaced(boardTile.stringToTile(gameBoard.getBoardTile(cpuLocation)));
						setLocationReplaced(cpuLocation);
						//Flag to skip this step if this is for the user's help function
						if(help == false) {
							//To prevent the computer from placing a non-double tile on its own tile if it has a double tile it can play.
							if(!(gameBoard.getBoardTile(cpuLocation).startsWith("W") && handLocation != (getHand().size() - 1))) {
								placeTile(gameBoard, cpuLocation, cpuTile);
								foundLocation = true;
							}
							else {
								foundLocation = false;
								break;
							}
						}
						else {
							//Same as above to prevent from placing non-double tile on its own tile if double tile can be played, but for help mode.
							if(!(gameBoard.getBoardTile(cpuLocation).startsWith("B") && handLocation != (getHand().size() - 1))) {
								foundLocation = true;
							}
							else {
								foundLocation = false;
								break;
							}
						}
					}
					else {
						//Selected location and tile combination was not a valid move.
						foundLocation = false;
					}
				} while(foundLocation == false);
				
				//A location was found and a move was made.
				if(foundLocation == true) {
					canMove = true;
					break;
				}
				//Chooses the next tile in the hand.
				else {
					handLocation++;
				}
				
			}
			//The selected tile was unplayable.
			else {
				handLocation++;
				canMove = false;
			}
		}
		return canMove;
	}
	
	
	/**
	*Puts the logic behind the computer's move into a string based on the move it made.
	*
	*@return String value cpuMove, cpuMoveDouble, or cpuMoveOwn.  Depending on the move made a different message will
	*be returned.
	*/
	public String displayCpuMove() {
		int cpuTilePipTotal = tilePlayed.getTotalTilePipScore();
		int prevTilePipTotal = tileReplaced.getTotalTilePipScore();
		//Previous tile was human owned.
		if(tileReplaced.getColor() == 'B') {
			//A non-double, 5-5, or 6-6 double was played.
			if((cpuTilePipTotal >= prevTilePipTotal) || cpuTilePipTotal == 10 || cpuTilePipTotal == 12  ) {
				StringBuffer cpuMove = new StringBuffer();
				cpuMove.append("CPU moves domino ");
				cpuMove.append(tilePlayed.tileToString());
				cpuMove.append(" to location ");
				cpuMove.append(location);
				cpuMove.append(" because tile ");
				cpuMove.append(tileReplaced.tileToString());
				cpuMove.append(" presented the\n");
				cpuMove.append("highest available net score increase for the tile played.");
				return cpuMove.toString();
			}
			//Any other double was played.
			else {
				StringBuffer cpuMoveDouble = new StringBuffer();
				cpuMoveDouble.append("CPU moves domino ");
				cpuMoveDouble.append(tilePlayed.tileToString());
				cpuMoveDouble.append(" to location ");
				cpuMoveDouble.append(location);
				cpuMoveDouble.append(" to reset a stack since location\n");
				cpuMoveDouble.append(location);
				cpuMoveDouble.append(" had a higher value tile ");
				cpuMoveDouble.append(tileReplaced.tileToString());
				return cpuMoveDouble.toString();
			}
		}
		//Previous tile was cpu owned.
		else {
			StringBuffer cpuMoveOwn = new StringBuffer();
			cpuMoveOwn.append("CPU moves domino ");
			cpuMoveOwn.append(tilePlayed.tileToString());
			cpuMoveOwn.append(" to location ");
			cpuMoveOwn.append(location);
			cpuMoveOwn.append(" because tile ");
			cpuMoveOwn.append(tileReplaced.tileToString());
			cpuMoveOwn.append(" was a low valued tile\n");
			cpuMoveOwn.append("of its own color.");
			return cpuMoveOwn.toString();
		}
	}
	
	
	/**
	*Puts the logic behind the computer's move into a string based on the move it made, wording slightly changed
	*to accommodate for help mode.
	*
	*@return String value cpuMove, cpuMoveDouble, or cpuMoveOwn.  Depending on the move made a different message will
	*be returned.
	*/
	public String displayHelpMove() {
		int cpuTilePipTotal = tilePlayed.getTotalTilePipScore();
		int prevTilePipTotal = tileReplaced.getTotalTilePipScore();
		//Previous tile was human owned.
		if(tileReplaced.getColor() == 'W') {
			//A non-double, 5-5, or 6-6 double was played.
			if((cpuTilePipTotal >= prevTilePipTotal) || cpuTilePipTotal == 10 || cpuTilePipTotal == 12  ) {
				StringBuffer cpuMove = new StringBuffer();
				cpuMove.append("CPU recommends moving domino ");
				cpuMove.append(tilePlayed.tileToString());
				cpuMove.append(" to location ");
				cpuMove.append(location);
				cpuMove.append(" because tile ");
				cpuMove.append(tileReplaced.tileToString());
				cpuMove.append(" presents the\n");
				cpuMove.append("highest available net score increase for that tile.");
				return cpuMove.toString();
			}
			//Any other double was played.
			else {
				StringBuffer cpuMoveDouble = new StringBuffer();
				cpuMoveDouble.append("CPU recommends moving domino ");
				cpuMoveDouble.append(tilePlayed.tileToString());
				cpuMoveDouble.append(" to location ");
				cpuMoveDouble.append(location);
				cpuMoveDouble.append(" to reset a stack since location\n");
				cpuMoveDouble.append(location);
				cpuMoveDouble.append(" had a higher value tile ");
				cpuMoveDouble.append(tileReplaced.tileToString());
				return cpuMoveDouble.toString();
			}
		}
		//Previous tile was cpu owned.
		else {
			StringBuffer cpuMoveOwn = new StringBuffer();
			cpuMoveOwn.append("CPU recommends moving domino ");
			cpuMoveOwn.append(tilePlayed.tileToString());
			cpuMoveOwn.append(" to location ");
			cpuMoveOwn.append(location);
			cpuMoveOwn.append(" because tile ");
			cpuMoveOwn.append(tileReplaced.tileToString());
			cpuMoveOwn.append(" was a low valued tile\n");
			cpuMoveOwn.append("of its own color.");
			return cpuMoveOwn.toString();
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getScore());
		dest.writeInt(getWins());
		dest.writeValue(getTurn());
		dest.writeParcelable(getSet(), flags);
		dest.writeTypedList(getHand());
		dest.writeValue(getEndTurn());
		dest.writeValue(getPassTurn());
		dest.writeParcelable(getTilePlayed(), flags);
		dest.writeParcelable(getTileReplaced(), flags);
		dest.writeString(location);
	}
	
	public static final Parcelable.Creator<Computer> CREATOR = new Parcelable.Creator<Computer>() {
		public Computer createFromParcel(Parcel in) {
			return new Computer(in);
		}
		public Computer[] newArray(int size) {
			return new Computer[size];
		}
	};
	
	private Computer(Parcel in) {
		setScore(in.readInt());
		setWins(in.readInt());
		setTurn((Boolean) in.readValue(Boolean.class.getClassLoader()));
		setPlayerSet((Set)in.readParcelable(Set.class.getClassLoader()));
		ArrayList<Tile> temp = new ArrayList<Tile>();
		in.readTypedList(temp, Tile.CREATOR);
		setHand(temp);
		setEndTurn((Boolean) in.readValue(Boolean.class.getClassLoader()));
		setPassTurn((Boolean) in.readValue(Boolean.class.getClassLoader()));
		tilePlayed = new Tile();
		setTilePlayed((Tile) in.readParcelable(Tile.class.getClassLoader()));
		tileReplaced = new Tile();
		setTileReplaced((Tile) in.readParcelable(Tile.class.getClassLoader()));
		try {
			setLocationReplaced(in.readString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
