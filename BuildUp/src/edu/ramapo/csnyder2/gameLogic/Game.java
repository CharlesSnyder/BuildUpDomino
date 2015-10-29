//Name: Charles Snyder
//Project: Java/Android Build-Up Domino Game
//Class:  Organization of Programming Lanuages
//Date:  11/15/14

package edu.ramapo.csnyder2.gameLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {
	private Player humanPlayer;
	private Player computerPlayer;
	private Board gameBoard;

	
	/**
	*Class constructor.
	*
	*Creates new instances of Human and Computer objects and instantiates gameBoard to an empty Board object.
	*/
	public Game() {
		humanPlayer = new Human();
		computerPlayer = new Computer();
		gameBoard = new Board();
	}

	/**
	*Class constructor used to maintain the win totals of each player.
	*
	*/
	public Game(int humanWins, int cpuWins) {
		humanPlayer = new Human(humanWins);
		computerPlayer = new Computer(cpuWins);
		gameBoard = new Board();
	}

	
	/**
	*Class constructor used to set humanPlayer, computerPlayer, and gameBoard to the parameters passed in.
	*
	*/
	public Game(Human newHuman, Computer newCpu, Board newBoard) {
		humanPlayer = new Human(newHuman);
		computerPlayer = new Computer(newCpu);
		gameBoard = new Board(newBoard);
	}

	/**
	*Class constructor used to copy contents from one game to another.
	*
	*/
	public Game(Game newGame) {
		humanPlayer = new Human(newGame.getHumanPlayer());
		computerPlayer = new Computer(newGame.getComputerPlayer());
		gameBoard = new Board(newGame.getGameBoard());
	}
	
	

	/**
	*Gets the human player object.
	*
	*@return humanCopy  Human object copied from current instance of human player.
	*
	*/
	public Human getHumanPlayer() {
		Human humanCopy = new Human((Human) humanPlayer);
		return humanCopy;
	}

	/**
	*Gets the computer player object.
	*
	*@return cpuCopy  Computer object copied from current instance of computer player.
	*
	*/
	public Computer getComputerPlayer() {
		Computer cpuCopy = new Computer((Computer) computerPlayer);
		return cpuCopy;
	}

	/**
	*Gets the current Board object.
	*
	*@return gameBoard  Board object of current game Board.
	*
	*/
	public Board getGameBoard() {
		return gameBoard;
	}

	/**
	*Gets the list of board stacks.
	*
	*@return An ArrayList of strings that holds a tile at each location on the board.
	*
	*/
	public ArrayList<String> getGameBoardStacks() {
		return gameBoard.getBoardStacks();
	}

	
	/**
	*Gets the human player's score as a string.
	*
	*@return Human player's score in string format.
	*
	*/
	public String getHumanScoreAsString() {
		return humanPlayer.getScoreAsString();
	}

	
	/**
	*Gets the human player's score.
	*
	*@return Human player's score as in integer.
	*
	*/
	public final int getHumanScore() {
		return humanPlayer.getScore();
	}

	/**
	*Gets the computer player's score.
	*
	*@return Computer player's score as a string.
	*
	*/
	public String getCpuScoreAsString() {
		return computerPlayer.getScoreAsString();
	}

	/**
	*Gets the computer player's score.
	*
	*@return Computer player's score as in integer.
	*
	*/
	public final int getCpuScore() {
		return computerPlayer.getScore();
	}

	/**
	*Gets the human player's win total as a string.
	*
	*@return Human player's win total in string format.
	*
	*/
	public String getHumanWinTotal() {
		return humanPlayer.getWinsAsString();
	}

	/**
	*Gets the computer player's win total as a string.
	*
	*@return Computer player's win total in string format.
	*
	*/
	public String getCpuWinTotal() {
		return computerPlayer.getWinsAsString();
	}

	
	/**
	*Gets the human player's win total.
	*
	*@return Human player's win total as an integer.
	*
	*/
	public int getHumanWins() {
		return humanPlayer.getWins();
	}

	
	/**
	*Gets the computer player's win total.
	*
	*@return Computer player's win total as an integer.
	*
	*/
	public int getComputerWins() {
		return computerPlayer.getWins();
	}

	
	/**
	*Gets the Human player's boneyard size.
	*
	*@return Integer value of the number of tiles in the human player's boneyard.
	*
	*/
	public int getHumansBoneyardSize() {
		return humanPlayer.getBoneyardSize();
	}

	/**
	*Gets the Human player's hand size.
	*
	*@return Integer value of the number of tiles in the human player's hand.
	*
	*/
	public int getHumansHandSize() {
		return humanPlayer.getHandSize();
	}

	
	/**
	*Gets the computer player's hand size.
	*
	*@return Integer value of the number of tiles in the computer player's hand.
	*
	*/
	public int getComputersHandSize() {
		return computerPlayer.getHandSize();
	}

	/**
	*Determine the current turn status of the players.
	*
	*@return True if it is the human players turn, false otherwise.
	*
	*/
	public boolean getCurrentPlayerTurn() {
		if (humanPlayer.getTurn() == true) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	*Gets the tile most recently played by the computer player.
	*
	*@return  Tile object of cpu tile played.
	*/
	public Tile getComputerTilePlayed() {
		return ((Computer) computerPlayer).getTilePlayed();
	}

	
	/**
	*Gets the tile most recently replaced on the board by the computer player.
	*
	*@return  Tile object of cpu tile replaced.
	*/
	public Tile getComputerTileReplaced() {
		return ((Computer) computerPlayer).getTileReplaced();
	}

	
	/**
	*Gets the location most recently played by the computer player.
	*
	*@return  String value of the location on the board the cpu made its last move.
	*/
	public String getComputerLocationPlayed() {
		return ((Computer) computerPlayer).getLocationPlayed();
	}

	
	/**
	*Get the message concerning the computer's move logic.
	*
	*@return String value of reasoning behind cpu's move.
	*
	*/
	public String getCpuMoveInfo() {
		return ((Computer) computerPlayer).displayCpuMove();
	}

	/**
	*Get the message concerning the computer's move logic for help purposes.
	*
	*@return String value of reasoning behind cpu's recommendation.
	*
	*/
	public String getHelpMoveInfo() {
		return ((Computer) computerPlayer).displayHelpMove();
	}

	/**
	*Get the status of the human player's turn.
	*
	*@return True if it is the human player's turn, false otherwise.
	*
	*/
	public boolean getHumanTurn() {
		return humanPlayer.getTurn();
	}

	
	/**
	*Get the status of the computer player's turn.
	*
	*@return True if it is the computer player's turn, false otherwise.
	*
	*/
	public boolean getCpuTurn() {
		return computerPlayer.getTurn();
	}

	
	/**
	*Get the human player's full hand.
	*
	*@return An ArrayList of tiles filled with the current tiles in the human player's hand.
	*/
	public ArrayList<Tile> getHumansHand() {
		return humanPlayer.getHand();
	}
	
	
	/**
	*Gets a tile from the board based on specified index.
	*
	*@param index    The location in the board stacks array list in which the desired tile is stored.
	*@return  Tile in string format of the tile at the specified index.
	*
	*/
	public String getBoardTileAsString(int index) {
		switch (index) {
		case 0:
			return gameBoard.getB1();
		case 1:
			return gameBoard.getB2();
		case 2:
			return gameBoard.getB3();
		case 3:
			return gameBoard.getB4();
		case 4:
			return gameBoard.getB5();
		case 5:
			return gameBoard.getB6();
		case 6:
			return gameBoard.getW1();
		case 7:
			return gameBoard.getW2();
		case 8:
			return gameBoard.getW3();
		case 9:
			return gameBoard.getW4();
		case 10:
			return gameBoard.getW5();
		case 11:
			return gameBoard.getW6();
		default:
			return "Invalid";
		}
	}

	
	/**
	*Gets a tile from the board based on specified index.
	*
	*@param index    The location in the board stacks array list in which the desired tile is stored.
	*@return  Tile object from the board stacks list at the specified index.
	*
	*/
	public Tile getBoardTile(int index) {
		Tile boardTile = new Tile();
		switch (index) {
		case 0:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getB1()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 1:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getB2()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 2:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getB3()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 3:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getB4()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 4:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getB5()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 5:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getB6()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 6:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getW1()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 7:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getW2()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 8:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getW3()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 9:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getW4()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 10:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getW5()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		case 11:
			try {
				boardTile.setTile(boardTile.stringToTile(gameBoard.getW6()));
			} catch (Exception setTileException) {
				System.err.println(setTileException.getMessage());
			}
			break;
		}
		return boardTile;
	}

	
	/**
	*Gets a tile from the human player's hand at a certain index.
	*
	*@param index    The location in the hand array list in which the desired tile is stored.
	*@return  Tile in string format from the hand arrayList at the specified index.
	*
	*/
	public String getTileFromHandAsString(int index) {
		if(index >= 0 && index <= 6) {
			ArrayList<Tile> myHand = new ArrayList<Tile>(humanPlayer.getHand());
			return myHand.get(index).tileToString();
		}
		else {
			return null;
		}
	}

	
	/**
	*Gets a tile from the human player's hand at a certain index.
	*
	*@param index    The location in the hand array list in which the desired tile is stored.
	*@return  Tile object from the hand arrayList at the specified index.
	*
	*/
	public Tile getTileFromHand(int index) {
		if(index >= 0 && index <= 6) {
			ArrayList<Tile> myHand = new ArrayList<Tile>(humanPlayer.getHand());
			return myHand.get(index);
		}
		else {
			return null;
		}
	}

	
	/**
	*Draw the first hand of the game.  Once a new game is started hands are drawn and placed on the board.
	*
	*/
	public void drawFirstHand() {
		try {
			humanPlayer.drawHand();
		} catch (Exception drawException) {
			System.err.println(drawException.getMessage());
		}
		try {
			humanPlayer.placeTile(gameBoard, "B1", humanPlayer.getHand().get(0));
			humanPlayer.placeTile(gameBoard, "B2", humanPlayer.getHand().get(0));
			humanPlayer.placeTile(gameBoard, "B3", humanPlayer.getHand().get(0));
			humanPlayer.placeTile(gameBoard, "B4", humanPlayer.getHand().get(0));
			humanPlayer.placeTile(gameBoard, "B5", humanPlayer.getHand().get(0));
			humanPlayer.placeTile(gameBoard, "B6", humanPlayer.getHand().get(0));
		} catch (Exception placeException) {
			System.err.println(placeException.getMessage());
		}
		try {
			computerPlayer.drawHand();
		} catch (Exception drawException) {
			System.err.println(drawException.getMessage());
		}
		try {
			computerPlayer.placeTile(gameBoard, "W1", computerPlayer.getHand().get(0));
			computerPlayer.placeTile(gameBoard, "W2", computerPlayer.getHand().get(0));
			computerPlayer.placeTile(gameBoard, "W3", computerPlayer.getHand().get(0));
			computerPlayer.placeTile(gameBoard, "W4", computerPlayer.getHand().get(0));
			computerPlayer.placeTile(gameBoard, "W5", computerPlayer.getHand().get(0));
			computerPlayer.placeTile(gameBoard, "W6", computerPlayer.getHand().get(0));
		} catch (Exception placeException) {
			System.err.println(placeException.getMessage());
		}
	}

	/**
	*Draw a single tile from the human player's boneyard.
	*
	*@return A Tile object from the front of the human player's set.
	*/
	public Tile drawSingleTileHuman() {
		return humanPlayer.drawSingleTileFromSet();
	}

	
	/**
	*Draw a single tile from the computer player's boneyard.
	*
	*@return A Tile object from the front of the computer player's set.
	*/
	public Tile drawSingleTileCpu() {
		return computerPlayer.drawSingleTileFromSet();
	}

	
	/**
	*Place a tile back into the human player's boneyard.
	*
	*@param myTile   The tile object to be placed back into the player's boneyard.
	*/
	public void placeTileInSetHuman(Tile myTile) {
		humanPlayer.placeTileBackInSet(myTile);
	}

	
	/**
	*Place a tile back into the computer player's boneyard.
	*
	*@param myTile   The tile object to be placed back into the player's boneyard.
	*/
	public void placeTileInSetCpu(Tile myTile) {
		computerPlayer.placeTileBackInSet(myTile);
	}

	
	/**
	*Shuffle the sets of both players.
	*
	*/
	public void shufflePlayerSets() {
		humanPlayer.shuffleSet();
		computerPlayer.shuffleSet();
	}



	
	/**
	*Determines the first player at the start of the game.
	*
	*@param humanTile   The tile the human player drew from their set.
	*@param cpuTile     The tile the computer player drew from their set.
	*@return integer value based on results, 0 for player win, 1 for cpu win, and 2 for draw.
	*
	*/
	public int determineFirstPlayer(Tile humanTile, Tile cpuTile) {
		final int PLAYER_WIN = 0;
		final int CPU_WIN = 1;
		final int DRAW = 2;

		int humanPipCount = humanTile.getTotalTilePipScore();
		int cpuPipCount = cpuTile.getTotalTilePipScore();
		// Human win
		if (humanPipCount > cpuPipCount) {
			humanPlayer.setTurn(true);
			computerPlayer.setTurn(false);
			humanPlayer.addSingleTileToHand(humanTile);
			computerPlayer.addSingleTileToHand(cpuTile);
			return PLAYER_WIN;
		}
		// Computer win
		else if (humanPipCount < cpuPipCount) {
			humanPlayer.setTurn(false);
			computerPlayer.setTurn(true);
			humanPlayer.addSingleTileToHand(humanTile);
			computerPlayer.addSingleTileToHand(cpuTile);
			return CPU_WIN;
		}
		// Draw
		else {
			humanPlayer.placeTileBackInSet(humanTile);
			computerPlayer.placeTileBackInSet(cpuTile);
			humanPlayer.shuffleSet();
			computerPlayer.shuffleSet();
			return DRAW;
		}
	}

	
	/**
	*Executes the human player's turn.
	*
	*@param userTile   The tile the human player selected to play.
	*@param boardTile  The tile on the board to be replaced.
	*@param location   String value that represents location on the board where the board tile resides.
	*@param gameBoard  Board object that holds all current board tiles.
	*@return True if move was made, false if move was invalid.
	*
	*/
	public boolean executeHumanTurn(Tile userTile, Tile boardTile, String location, Board gameBoard) {
		return ((Human) humanPlayer).humanTurn(userTile, boardTile, location, gameBoard);
	}

	
	/**
	*Executes the computer player's turn.
	*
	*@param gameBoard  Board object that holds all current board tiles.
	*@param help   A boolean variable used to represent if the function call if for human player help or
	*			   the computer players move.  True if it is for help, false otherwise.
	*@return True if move was made, false if no moves were possible.
	*
	*/
	public boolean executeCpuTurn(Board gameBoard, boolean help) {
		try {
			return ((Computer) computerPlayer).playTile(gameBoard, help);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	*If the player's hands are empty their end turn values are set to true.
	*
	*
	*/
	public void determineEndTurn() {
		if(humanPlayer.isHandEmpty() == true) {
			humanPlayer.setEndTurn(true);
		}
		if(computerPlayer.isHandEmpty() == true) {
			computerPlayer.setEndTurn(true);
		}
		
	}

	/**
	*Check to see if the human player's turn is over for the current hand.
	*
	@return  True if end turn is set to true, false otherwise.
	*/
	public boolean isEndTurnHuman() {
		return humanPlayer.getEndTurn();
	}

	
	/**
	*Check to see if the computer player's turn is over for the current hand.
	*
	@return  True if end turn is set to true, false otherwise.
	*/
	public boolean isEndTurnComputer() {
		return computerPlayer.getEndTurn();
	}

	
	/**
	*Check to see if the human player passed for their last turn.
	*
	@return  True if pass turn is set to true, false otherwise.
	*/
	public boolean isPassTurnHuman() {
		return humanPlayer.getPassTurn();
	}

	
	/**
	*Check to see if the computer player passed for their last turn.
	*
	@return  True if pass turn is set to true, false otherwise.
	*/
	public boolean isPassTurnComputer() {
		return computerPlayer.getPassTurn();
	}
	
	/**
	*Conditions for when the computer must pass its turn.  Will end turn for both player's if user had passed
	*its previous turn.
	*
	*/
	public void setPassComputer() {
		if (isPassTurnHuman() == true || isEndTurnHuman() == true) {
			computerPlayer.setEndTurn(true);
			humanPlayer.setEndTurn(true);
		} else {
			computerPlayer.setPassTurn(true);
		}
	}
	
	/**
	*Conditions for when the human must pass its turn.  Will end turn for both player's if computer had passed
	*its previous turn.
	*
	*/
	public void setPassHuman() {
		if (isPassTurnComputer() == true || isEndTurnComputer() == true) {
			humanPlayer.setEndTurn(true);
			computerPlayer.setEndTurn(true);
		} else {
			humanPlayer.setPassTurn(true);
		}		
	}
	
	/**
	*Sets all end turn and pass turn values to false for both players.
	*
	*/
	public void resetEndPassTurnValues() {
		humanPlayer.setEndTurn(false);
		computerPlayer.setEndTurn(false);
		humanPlayer.setPassTurn(false);
		computerPlayer.setPassTurn(false);
	}
	
	/**
	*Conditions to determine if the game is at the end of a hand.
	*
	*@return True if the hand is over, false otherwise.
	*/
	public boolean isEndHand() {
		if(getHumansBoneyardSize() != 0 && computerPlayer.getEndTurn() == true && humanPlayer.getEndTurn() == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	*Conditions to determine if the game is at the end of a round.
	*
	*@return True if the round is over, false otherwise.
	*/
	public boolean isEndRound() {
		if(getHumansBoneyardSize() == 0 && computerPlayer.getEndTurn() == true && humanPlayer.getEndTurn() == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	*Removes a specified tile from the computer player's hand.
	*
	*@param myTile   The tile to be removed from the hand ArrayList.
	*/
	public void removeTileFromCpuHand(Tile myTile) {
		try {
			computerPlayer.removeTileFromHand(myTile);
		} catch (Exception removeException) {
			System.err.println(removeException.getMessage());
		}
	}

	


	/**
	*Alternates the turns of the players.
	*
	*/
	public void switchTurns() {
		if (humanPlayer.getTurn() == true) {
			humanPlayer.setTurn(false);
			computerPlayer.setTurn(true);
		} else {
			humanPlayer.setTurn(true);
			computerPlayer.setTurn(false);
		}
	}

	/**
	*Calculates the scores for both players.
	*
	*/
	public void calculateScores() {
		humanPlayer.calculateScore(gameBoard);
		computerPlayer.calculateScore(gameBoard);
	}

	/**
	*Removes all tiles from the hands of both players.
	*
	*/
	public void clearHands() {
		humanPlayer.clearHand();
		computerPlayer.clearHand();
	}

	/**
	*Sorts the computer players hand according the the cpu sorting algorithm.
	*
	*/
	public void sortComputerPlayersHand() {
		((Computer) computerPlayer).sortHand();
	}

	
	/**
	*Draws a hand for both players.
	*
	*/
	public void drawCurrentPlayerHand() {
		try {
			humanPlayer.drawHand();
			computerPlayer.drawHand();
		} catch (Exception drawException) {
			System.err.println(drawException.getMessage());
		}
	}

	
	/**
	*Determines which player won the round based on the score of the players.
	*
	*@return Integer value 0 for a human win, 1 for a computer win, 2 for a draw.
	*/
	public int determineRoundWinner() {
		final int HUMAN_WIN = 0;
		final int CPU_WIN = 1;
		final int DRAW = 2;

		if (humanPlayer.getScore() > computerPlayer.getScore()) {
			humanPlayer.addWin(1);
			return HUMAN_WIN;
		} else if (humanPlayer.getScore() < computerPlayer.getScore()) {
			computerPlayer.addWin(1);
			return CPU_WIN;
		} else {
			return DRAW;
		}
	}

	
	/**
	*Determines which player won the tournament based on the number of wins of the players.
	*
	*@return Integer value 0 for a human win, 1 for a computer win, 2 for a draw.
	*/
	public int determineTournamentWinner() {
		final int HUMAN_WIN = 0;
		final int CPU_WIN = 1;
		final int DRAW = 2;

		if (humanPlayer.getWins() > computerPlayer.getWins()) {
			return HUMAN_WIN;
		} else if (humanPlayer.getWins() < computerPlayer.getWins()) {
			return CPU_WIN;
		} else {
			return DRAW;
		}
	}

	
	/**
	*Checks to see if either player's turn status is set to true.
	*
	*@return True if at least one player has their turn status set to true, false otherwise.
	*/
	public boolean isTurnDetermined() {
		if (humanPlayer.getTurn() == true || computerPlayer.getTurn() == true) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	*Saves the current game state to a string in specific format.
	*
	*@return String value that holds all relevant data from the game.
	*/
	public String saveGame() {
		StringBuffer savedGame = new StringBuffer();
		savedGame.append("Computer:\n");
		savedGame.append("   Stacks: ");
		savedGame.append(gameBoard.getW1() + " " + gameBoard.getW2() + " "
				+ gameBoard.getW3() + " " + gameBoard.getW4()
				+ gameBoard.getW5() + gameBoard.getW6() + "\n");
		savedGame.append("   Boneyard: " + computerPlayer.getBoneyardAsString()
				+ "\n");
		savedGame.append("   Hand: " + computerPlayer.getHandAsString() + "\n");
		savedGame.append("   Score: " + computerPlayer.getScoreAsString()
				+ "\n");
		savedGame.append("   Rounds Won: " + computerPlayer.getWinsAsString()
				+ "\n");
		savedGame.append("\n");
		savedGame.append("Human:\n");
		savedGame.append("   Stacks: ");
		savedGame.append(gameBoard.getB1() + " " + gameBoard.getB2() + " "
				+ gameBoard.getB3() + " " + gameBoard.getB4()
				+ gameBoard.getB5() + gameBoard.getB6() + "\n");
		savedGame.append("   Boneyard: " + humanPlayer.getBoneyardAsString()
				+ "\n");
		savedGame.append("   Hand: " + humanPlayer.getHandAsString() + "\n");
		savedGame.append("   Score: " + humanPlayer.getScoreAsString() + "\n");
		savedGame.append("   Rounds Won: " + humanPlayer.getWinsAsString()
				+ "\n");
		savedGame.append("\n");
		savedGame.append("Turn: ");
		if (isTurnDetermined()) {
			if (humanPlayer.getTurn() == true) {
				savedGame.append("Human");
			} else {
				savedGame.append("Computer");
			}
		}
		return savedGame.toString();
	}

	
	/**
	*Loads all game parameters from a text file.
	*
	*@param inputStream    The BufferedReader which holds all the information of the game.
	*/
	public void loadGame(BufferedReader inputStream) {
		String inputString;
		Pattern dominoes = Pattern.compile("((B|W)[0-9]{2}|-)");
		try {
			inputStream.readLine();
			// Computer stacks
			inputString = inputStream.readLine();
			Matcher findDominoes = dominoes.matcher(inputString);
			ArrayList<String> newBoardStacks = new ArrayList<String>();
			while (findDominoes.find()) {
				newBoardStacks.add(findDominoes.group());
			}

			// Computer boneyard
			computerPlayer.clearPlayerSet();
			Tile tempTile = new Tile();
			inputString = inputStream.readLine();
			findDominoes = dominoes.matcher(inputString);
			while (findDominoes.find()) {
				computerPlayer.placeTileBackInSet(tempTile
						.stringToTile(findDominoes.group()));
			}

			// Computer hand
			inputString = inputStream.readLine();
			findDominoes = dominoes.matcher(inputString);
			while (findDominoes.find()) {
				computerPlayer.addSingleTileToHand(tempTile
						.stringToTile(findDominoes.group()));
			}

			// Computer score
			inputString = inputStream.readLine();
			Pattern scoreOrWins = Pattern.compile("[0-9]{1,20}");
			Matcher findScoreOrWins = scoreOrWins.matcher(inputString);
			findScoreOrWins.find();
			computerPlayer.setScore(Integer.parseInt(findScoreOrWins.group()));

			// Computer wins
			inputString = inputStream.readLine();
			findScoreOrWins = scoreOrWins.matcher(inputString);
			findScoreOrWins.find();
			computerPlayer.setWins(Integer.parseInt(findScoreOrWins.group()));

			// space
			inputStream.readLine();
			// Human declaration
			inputStream.readLine();

			// Human stacks
			inputString = inputStream.readLine();
			findDominoes = dominoes.matcher(inputString);
			int index = 0;
			while (findDominoes.find()) {
				newBoardStacks.add(index, findDominoes.group());
				index++;
			}
			
			//Set the game board
			gameBoard.setBoardStacks(newBoardStacks);

			// Human boneyard
			humanPlayer.clearPlayerSet();
			inputString = inputStream.readLine();
			findDominoes = dominoes.matcher(inputString);
			while (findDominoes.find()) {
				humanPlayer.placeTileBackInSet(tempTile
						.stringToTile(findDominoes.group()));
			}

			// Human hand
			inputString = inputStream.readLine();
			findDominoes = dominoes.matcher(inputString);
			while (findDominoes.find()) {
				humanPlayer.addSingleTileToHand(tempTile
						.stringToTile(findDominoes.group()));
			}

			// Human score
			inputString = inputStream.readLine();
			findScoreOrWins = scoreOrWins.matcher(inputString);
			findScoreOrWins.find();
			humanPlayer.setScore(Integer.parseInt(findScoreOrWins.group()));

			// Human wins
			inputString = inputStream.readLine();
			findScoreOrWins = scoreOrWins.matcher(inputString);
			findScoreOrWins.find();
			humanPlayer.setWins(Integer.parseInt(findScoreOrWins.group()));

			// space
			inputStream.readLine();
			// Turn
			inputString = inputStream.readLine();

			Pattern turn = Pattern.compile("Human|Computer");
			Matcher findTurn = turn.matcher(inputString);
			if (findTurn.find()) {
				String result = findTurn.group();
				if (result.equals("Human")) {
					humanPlayer.setTurn(true);
					computerPlayer.setTurn(false);
				} else if (result.equals("Computer")) {
					computerPlayer.setTurn(true);
					humanPlayer.setTurn(false);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable((Human) humanPlayer, flags);
		dest.writeParcelable((Computer) computerPlayer, flags);
		dest.writeParcelable(gameBoard, flags);
	}

	public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
		public Game createFromParcel(Parcel in) {
			return new Game(in);
		}

		public Game[] newArray(int size) {
			return new Game[size];
		}
	};

	private Game(Parcel in) {
		humanPlayer = (Human) in.readParcelable(Human.class.getClassLoader());
		computerPlayer = (Computer) in.readParcelable(Computer.class
				.getClassLoader());
		gameBoard = (Board) in.readParcelable(Board.class.getClassLoader());
	}

}
