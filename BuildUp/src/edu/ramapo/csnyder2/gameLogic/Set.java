//Name: Charles Snyder
//Project: Java/Android Build-Up Domino Game
//Class:  Organization of Programming Lanuages
//Date:  11/15/14


package edu.ramapo.csnyder2.gameLogic;

import java.util.ArrayList;
import java.util.Random;

import android.os.Parcel;
import android.os.Parcelable;

public class Set implements Parcelable{

	private ArrayList<Tile> tiles;

	/**
	*Class Constructor.								   
	*
	*Creates a new Set object composed of 28 tile objects meant to represent a double six domino set, default color is set to 'B'.
	*@throws Exception of an invalid tile is created.  
	*/
	public Set() {
		final int MAX_PIP_SIZE = 6;
		tiles = new ArrayList<Tile>();
		
		int count = 0;
		int rightSide;
		for (int leftSide = 0; leftSide <= MAX_PIP_SIZE; leftSide++) {
			for (rightSide = count; rightSide <= MAX_PIP_SIZE; rightSide++) {
				try {
					Tile myTile = new Tile('B', leftSide, rightSide);
					tiles.add(myTile);
				} catch (Exception tileException) {
					System.err.println(tileException.getMessage());
				}
			}
			count++;
		}
	}
	
	/**
	*Class Constructor used to copy one set to another.								   
	*
	*@param mySet  A set object to be copied. 
	*/
	public Set(Set mySet) {
		tiles = new ArrayList<Tile>(mySet.getSet());
	}

	/**
	*Class Constructor used to create a Set with a specific color.								   
	*
	*@param color   The color character which to make the set, permissible values are 'B' and 'W'.
	*@throws  Exception if invalid tile is created. 
	*/
	public Set(char color) throws Exception {
		final int MAX_PIP_SIZE = 6;
		tiles = new ArrayList<Tile>();

		color = Character.toUpperCase(color);
		if (color != 'B' && color != 'W') {
			Exception invalidColor = new Exception("Invalid Color");
			throw invalidColor;
		} else {
			int count = 0;
			int rightSide;
			for (int leftSide = 0; leftSide <= MAX_PIP_SIZE; leftSide++) {
				for (rightSide = count; rightSide <= MAX_PIP_SIZE; rightSide++) {
					try {
						Tile myTile = new Tile(color, leftSide, rightSide);
						tiles.add(myTile);
					} catch (Exception tileException) {
						System.err.println(tileException.getMessage());
					}
				}
				count++;
			}
		}
	}

	
	/**
	*Get the current size of the Set object.							   
	*
	*@param None.
	*@return An integer value of the size of the set.
	*/
	public final int getSetSize() {
		return tiles.size();
	}

	/**
	*Get the current state of a Set object.							   
	*
	*@param None.
	*@return An ArrayList of type Tile which represents the current tiles in the Set.
	*/
	public ArrayList<Tile> getSet() {
		return tiles;
	}

	/**
	*Get the current state of a Set object as a string value.							   
	*
	*@param None.
	*@return returnString   A String which represents the current tiles in the Set, each tile is separated by a space.
	*/
	public String getSetAsString() {
		String returnString = "";
		for (int index = 0; index < tiles.size(); index++) {
			returnString = returnString + tiles.get(index).tileToString() + " ";
		}
		return returnString;
	}
	

	/**
	*Moves tiles in the ArrayList to random locations to simulate the act of shuffling the Set.							   
	*
	*@param None.
	*/	
	public void shuffleTiles() {
		Random randomNum = new Random();

		for (int index = 0; index < tiles.size(); index++) {
			int randomInt = randomNum.nextInt(getSetSize());
			Tile tmp = new Tile((Tile) tiles.get(randomInt));
			tiles.set(randomInt, tiles.get(index));
			tiles.set(index, tmp);
		}
	}

	/**
	*Remove and return a tile object from the Set.							   
	*
	*@param None.
	*@return returnTile   A newly created tile that gets set to the first tile in the ArrayList of the Set.  This tile then gets removed from the ArrayList.
	*/
	public Tile removeTile() {
		Tile returnTile = new Tile();
		returnTile = (Tile) tiles.get(0);
		tiles.remove(returnTile);
		return returnTile;
	}

	
	/**
	*Return a tile to a Set.							   
	*
	*@param myTile   A Tile object that gets added to the ArrayList of the Set.
	*/
	public void returnTileToSet(Tile myTile) {
		tiles.add(myTile);
	}

	
	/**
	*Erases all tiles from a Set.  Makes the ArrayList empty.							   
	*
	*/
	public void clearSet() {
		tiles.clear();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(tiles);
	}
	
	public static final Parcelable.Creator<Set> CREATOR = new Parcelable.Creator<Set>() {
		public Set createFromParcel(Parcel in) {
			return new Set(in);
		}
		public Set[] newArray(int size) {
			return new Set[size];
		}
	};
	
	private Set(Parcel in) {
		tiles = new ArrayList<Tile>();
		in.readTypedList(tiles, Tile.CREATOR);
	}	
}
