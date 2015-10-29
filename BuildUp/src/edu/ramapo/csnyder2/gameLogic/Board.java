//Name: Charles Snyder
//Project: Java/Android Build-Up Domino Game
//Class:  Organization of Programming Lanuages
//Date:  11/15/14

package edu.ramapo.csnyder2.gameLogic;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Board implements Parcelable {

	final static int B1 = 0;
	final static int B2 = 1;
	final static int B3 = 2;
	final static int B4 = 3;
	final static int B5 = 4;
	final static int B6 = 5;
	final static int W1 = 6;
	final static int W2 = 7;
	final static int W3 = 8;
	final static int W4 = 9;
	final static int W5 = 10;
	final static int W6 = 11;

	private ArrayList<String> boardStacks;

	
	/**
	*Class constructor
	*
	*Creates a new board object and sets all spaces to "-".							   
	*
	*/
	public Board() {
		final int BOARD_SIZE = 12;
		boardStacks = new ArrayList<String>();

		for (int index = 0; index < BOARD_SIZE; index++) {
			boardStacks.add("-");
		}
	}
	
	/**
	*Class constructor used to copy a board object.
	*
	*@param newBoard  The board to be copied.						   
	*
	*/
	public Board(Board newBoard) {
		boardStacks = new ArrayList<String>(newBoard.getBoardStacks());
	}

	
	/**
	*Get the entire set of board stacks.
	*
	*@return  copyBoardStacks  An ArrayList of strings that holds all current tile values at each location on the board.					   
	*
	*/
	public ArrayList<String> getBoardStacks() {
		ArrayList<String> copyBoardStacks = new ArrayList<String>(boardStacks);
		return copyBoardStacks;
	}

	/**
	*Gets a specific board tile as a string based on a specified location on the board.
	*
	*@param location  A string to represent a space such as "B1".
	*@return A string of the tile on the specified space.
	*@throws outOfRange if an invalid board location is passed in. 						   
	*
	*/
	public String getBoardTile(String location) throws Exception {
		switch(location) {
		case "B1": return getB1();
		case "B2": return getB2();
		case "B3": return getB3();
		case "B4": return getB4();
		case "B5": return getB5();
		case "B6": return getB6();
		case "W1": return getW1();
		case "W2": return getW2();
		case "W3": return getW3();
		case "W4": return getW4();
		case "W5": return getW5();
		case "W6": return getW6();
		default:
			Exception outOfRange = new Exception("Invalid location");
			throw outOfRange;
		}
	}

	
	/**
	*Gets the tile as a string located at space B1.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getB1() {
		return boardStacks.get(B1);
	}

	
	/**
	*Gets the tile as a string located at space B2.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getB2() {
		return boardStacks.get(B2);
	}

	
	/**
	*Gets the tile as a string located at space B3.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getB3() {
		return boardStacks.get(B3);
	}

	
	/**
	*Gets the tile as a string located at space B4.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getB4() {
		return boardStacks.get(B4);
	}

	
	/**
	*Gets the tile as a string located at space B5.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getB5() {
		return boardStacks.get(B5);
	}

	
	/**
	*Gets the tile as a string located at space B6.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getB6() {
		return boardStacks.get(B6);
	}

	
	/**
	*Gets the tile as a string located at space W1.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getW1() {
		return boardStacks.get(W1);
	}

	
	/**
	*Gets the tile as a string located at space W2.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getW2() {
		return boardStacks.get(W2);
	}

	
	/**
	*Gets the tile as a string located at space W3.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getW3() {
		return boardStacks.get(W3);
	}

	
	/**
	*Gets the tile as a string located at space W4.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getW4() {
		return boardStacks.get(W4);
	}

	
	/**
	*Gets the tile as a string located at space W5.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getW5() {
		return boardStacks.get(W5);
	}

	
	/**
	*Gets the tile as a string located at space W6.
	*
	*@return A string representing a tile.					   
	*
	*/
	public String getW6() {
		return boardStacks.get(W6);
	}
	

	/**
	*Sets the tile as a string located at space B1.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setB1(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(B1, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	/**
	*Sets the tile as a string located at space B2.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setB2(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(B2, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	/**
	*Sets the tile as a string located at space B3.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setB3(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(B3, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	/**
	*Sets the tile as a string located at space B4.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setB4(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(B4, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	
	/**
	*Sets the tile as a string located at space B5.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setB5(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(B5, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	/**
	*Sets the tile as a string located at space B6.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setB6(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(B6, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	/**
	*Sets the tile as a string located at space W1.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setW1(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(W1, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	/**
	*Sets the tile as a string located at space W2.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setW2(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(W2, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	/**
	*Sets the tile as a string located at space W3.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setW3(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(W3, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	
	/**
	*Sets the tile as a string located at space W4.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setW4(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(W4, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	/**
	*Sets the tile as a string located at space W5.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setW5(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(W5, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}

	
	/**
	*Sets the tile as a string located at space W6.
	*
	*@param myTile  A tile object while will be converted to a string and represent the current tile on the location.					   
	*@throws  Exception if an invalid tile is attempted to be set on the location.
	*/
	public void setW6(Tile myTile) throws Exception {
		if (myTile.isTileValid() == true) {
			boardStacks.set(W6, myTile.tileToString());
		} else {
			Exception invalidTile = new Exception("Invalid tile");
			throw invalidTile;
		}
	}
	
	
	
	/**
	*Sets the entire board with new tiles.
	*
	*@param newStacks  An ArrayList of strings that hold tile values which will be set to the current board.
	*/
	public void setBoardStacks(ArrayList<String> newStacks) {
		boardStacks = new ArrayList<String>(newStacks);
	}

	
	/**
	*Gets the locations of all board spaces.
	*
	*@return allBoardSpaces   An ArrayList of strings that holds each board location, i.e. B1, B2, etc...
	*/
	public ArrayList<String> getAllBoardSpaces() {
		ArrayList<String> allBoardSpaces = new ArrayList<String>();
		allBoardSpaces.add("B1");
		allBoardSpaces.add("B2");
		allBoardSpaces.add("B3");
		allBoardSpaces.add("B4");
		allBoardSpaces.add("B5");
		allBoardSpaces.add("B6");
		allBoardSpaces.add("W1");
		allBoardSpaces.add("W2");
		allBoardSpaces.add("W3");
		allBoardSpaces.add("W4");
		allBoardSpaces.add("W5");
		allBoardSpaces.add("W6");
		return allBoardSpaces;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(boardStacks);
	}
	
	public static final Parcelable.Creator<Board> CREATOR = new Parcelable.Creator<Board>() {
		public Board createFromParcel(Parcel in) {
			return new Board(in);
		}
		public Board[] newArray(int size) {
			return new Board[size];
		}
	};
	
	private Board(Parcel in) {
		boardStacks = new ArrayList<String>();
		in.readList(boardStacks, String.class.getClassLoader());
	}
}
