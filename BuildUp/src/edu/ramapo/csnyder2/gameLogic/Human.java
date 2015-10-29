//Name: Charles Snyder
//Project: Java/Android Build-Up Domino Game
//Class:  Organization of Programming Lanuages
//Date:  11/15/14


package edu.ramapo.csnyder2.gameLogic;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;


public class Human extends Player implements Parcelable {

	
	/**
	*Class constructor.
	*
	*Uses parent class constructor and extends on it initializing a new Set object
	*of the color black and a new ArrayList of tile objects representing the human player's hand.
	*/
	public Human() {
		super();
		try {
			setPlayerSet(new Set('B'));
		} 
		catch(Exception setException) {
			System.err.println(setException.getMessage());
		}
	}

	
	/**
	*Class constructor used to copy a Human object.
	*
	*@param newHuman   The human object whose values will be copied.
	*/
	public Human(Human newHuman) {
		super(newHuman);
	}
	
	
	/**
	*Class constructor used to preserve the win total of the human player.
	*
	*@param wins   Resets all variables of the object except for the integer value of wins.
	*@throws  Exception if invalid set is created.
	*/
	public Human(int wins) {
		super(wins);
		try {
			setPlayerSet(new Set('B'));
		}
		catch(Exception setException) {
			System.err.println(setException.getMessage());
		}
	}
	
	/**
	*Executes the human player's turn.  It checks to see if the selected tile to play is permitted
	*to be played on the selected location.  If so the move will be executed.
	*
	*@param userTile   Tile object that the human player chose to play from their hand.
	*@param boardTile  Tile object that the human player chose to place userTile on top of.
	*@param location   The location as a string on the board where the move is to take place.
	*@param gameBoard  Board object that holds all the current tiles on the board spaces.
	*@return True if the move was executed, false otherwise.
	*@throws  Exception if invalid move is attempted.
	*/
	public boolean humanTurn(Tile userTile, Tile boardTile, String location, Board gameBoard) {
		if (!isValidMove(userTile, boardTile)) {
			return false;
		} else {
			try {
				placeTile(gameBoard, location, userTile);
			} catch (Exception invalidMove) {
				System.err.println(invalidMove.getMessage());
				return false;
			}
			return true;
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
		if (boardTile.getColor() == 'B') {
			int newScore = getScore() + boardTile.getPipsLeftEnd() + boardTile.getPipsRightEnd();
			setScore(newScore);
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
	}
	
	public static final Parcelable.Creator<Human> CREATOR = new Parcelable.Creator<Human>() {
		public Human createFromParcel(Parcel in) {
			return new Human(in);
		}
		public Human[] newArray(int size) {
			return new Human[size];
		}
	};
	
	private Human(Parcel in) {
		setScore(in.readInt());
		setWins(in.readInt());
		setTurn((Boolean) in.readValue(Boolean.class.getClassLoader()));
		setPlayerSet((Set)in.readParcelable(Set.class.getClassLoader()));
		ArrayList<Tile> temp = new ArrayList<Tile>();
		in.readTypedList(temp, Tile.CREATOR);
		setHand(temp);
		setEndTurn((Boolean) in.readValue(Boolean.class.getClassLoader()));
		setPassTurn((Boolean) in.readValue(Boolean.class.getClassLoader()));
	}


}
