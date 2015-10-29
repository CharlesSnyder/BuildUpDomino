//Name: Charles Snyder
//Project: Java/Android Build-Up Domino Game
//Class:  Organization of Programming Lanuages
//Date:  11/15/14


package edu.ramapo.csnyder2.gameLogic;

import android.os.Parcel;
import android.os.Parcelable;


public class Tile implements Parcelable {
	private char color;
	private int pipsLeftEnd;
	private int pipsRightEnd;
	
	
	/**
	*Class Constructor.								   
	*
	*Sets each newly created tile to B00.  
	*/
	public Tile() {
		color = 'B';
		pipsLeftEnd = 0;
		pipsRightEnd = 0;
	}

	/**
	*Class Constructor specifying the color and number of pips for each side.								   
	*
	*@param userColor  The color of the tile represented with a character.
	*@param userPipsLeftEnd  The integer value of the left side of the tile.
	*@param userPipsRightEnd  The integer value of the right side of the tile. 
	*/
	public Tile(char userColor, int userPipsLeftEnd, int userPipsRightEnd)
			throws Exception {
		setColor(userColor);
		setPipsLeftEnd(userPipsLeftEnd);
		setPipsRightEnd(userPipsRightEnd);
	}
	
	/**
	*Copy constructor.								   
	*
	*@param copyTile  The tile to copy.
	*/
	public Tile(Tile copyTile) {
		color = copyTile.getColor();
		pipsLeftEnd = copyTile.getPipsLeftEnd();
		pipsRightEnd = copyTile.getPipsRightEnd();
	}

	/**
	*Returns the character that represents the color of the tile.								   
	*
	*@param  None.
	*@return  color   The character that represents the color of the tile. 
	*/
	public final char getColor() {
		return color;
	}

	/**
	*Returns the value of the left end of the tile.								   
	*
	*@param None.
	*@return  pipsLeftEnd   The integer value of the pip count for the left side of the tile.
	*/
	public final int getPipsLeftEnd() {
		return pipsLeftEnd;
	}

	/**
	*Returns the value of the right end of the tile.								   
	*
	*@param None.
	*@return  pipsRightEnd  The integer value of the pip count for the right side of the tile.
	*/
	public final int getPipsRightEnd() {
		return pipsRightEnd;
	}
	
	/**
	*Sets tile values of current tile equal to passed in tile.							   
	*
	*@param myTile    The tile to be copied
	*@throws Exception if color besides 'B' or 'W' used or if value outside of pip range used; less than
	*0 or greater than 6. 
	*/
	public void setTile(Tile myTile) throws Exception {
		setColor(myTile.getColor());
		setPipsLeftEnd(myTile.getPipsLeftEnd());
		setPipsRightEnd(myTile.getPipsRightEnd());
	}

	/**
	*Sets the color value of the tile.							   
	*
	*@param userColor  The character value used to represent the color, i.e. 'B' for black.
	*@throws Exception if color besides 'B' or 'W' used.
	*/
	public void setColor(char userColor) throws Exception {
		userColor = Character.toUpperCase(userColor);
		if (userColor != 'B' && userColor != 'W') {
			Exception colorException = new Exception("Invalid color");
			throw colorException;
		} else
			color = userColor;
	}
	
	/**
	*Sets the pip count for the left end of the tile.							   
	*
	*@param userPipsLeftEnd  The integer value used to specify the pip count.
	*@throws Exception if value outside of pip range used; less than 0 or greater than 6.
	*/
	public void setPipsLeftEnd(int userPipsLeftEnd) throws Exception {
		final int MIN_PIP_NUMBER = 0;
		final int MAX_PIP_NUMBER = 6;

		if (userPipsLeftEnd < MIN_PIP_NUMBER
				|| userPipsLeftEnd > MAX_PIP_NUMBER) {
			Exception pipRangeException = new Exception(
					"Pip number out of range");
			throw pipRangeException;
		} else
			pipsLeftEnd = userPipsLeftEnd;
	}

	/**
	*Sets the pip count for the right end of the tile.							   
	*
	*@param userPipsLeftEnd  The integer value used to specify the pip count.
	*@throws Exception if value outside of pip range used; less than 0 or greater than 6.
	*/
	public void setPipsRightEnd(int userPipsRightEnd) throws Exception {
		final int MIN_PIP_NUMBER = 0;
		final int MAX_PIP_NUMBER = 6;

		if (userPipsRightEnd < MIN_PIP_NUMBER
				|| userPipsRightEnd > MAX_PIP_NUMBER) {
			Exception pipRangeException = new Exception(
					"Pip number out of range");
			throw pipRangeException;
		} else
			pipsRightEnd = userPipsRightEnd;
	}

	/**
	*Performs a check on the tile to see if all parameters are valid.							   
	*
	*@param None.
	*@return True if all parameters (color, left pips, right pips) meet the requirements of the set properties, false otherwise.
	*/
	public boolean isTileValid() {
		final int MIN_PIP_NUMBER = 0;
		final int MAX_PIP_NUMBER = 6;

		if (color != 'B' && color != 'W') {
			return false;
		} else if (pipsLeftEnd < MIN_PIP_NUMBER || pipsLeftEnd > MAX_PIP_NUMBER) {
			return false;
		} else if (pipsRightEnd < MIN_PIP_NUMBER
				|| pipsRightEnd > MAX_PIP_NUMBER) {
			return false;
		} else if (pipsLeftEnd > pipsRightEnd) {
			return false;
		} else
			return true;
	}

	/**
	*Calculate the total pip count of a tile.					   
	*
	*@param None.
	*@return The integer value of the left and right pips added together.
	*/
	public final int getTotalTilePipScore() {
		return getPipsLeftEnd() + getPipsRightEnd();
	}

	/**
	*Convert a tile object to a string value.						   
	*
	*@param None.
	*@return The variables of a tile object, color, left pip count, and right pip count in string format, for example "B00".
	*/
	public String tileToString() {
		StringBuffer ssColor = new StringBuffer();
		StringBuffer ssPipsLeft = new StringBuffer();
		StringBuffer ssPipsRight = new StringBuffer();

		ssColor.append(color);
		ssPipsLeft.append(pipsLeftEnd);
		ssPipsRight.append(pipsRightEnd);
		return ssColor.toString() + ssPipsLeft.toString()
				+ ssPipsRight.toString();
	}

	/**
	*Convert a string representing a tile into a tile object.						   
	*
	*@param myTileString  The tile to be converted as a string value.
	*@return  tmp  A tile created from myTileString.
	*@throws Exception if color besides 'B' or 'W' used or if value outside of pip range used; less than
	*0 or greater than 6. 
	*/
	public Tile stringToTile(String myTileString) {
		Tile tmp = new Tile();
		try {
			tmp.setColor(myTileString.charAt(0));
		} catch (Exception colorException) {
			System.err.println(colorException.getMessage());
		}
		String leftPipString, rightPipString;
		leftPipString = myTileString.substring(1, 2);
		rightPipString = myTileString.substring(2, 3);
		int leftPip = Integer.parseInt(leftPipString);
		int rightPip = Integer.parseInt(rightPipString);
		try {
			tmp.setPipsLeftEnd(leftPip);
			tmp.setPipsRightEnd(rightPip);
		} catch (Exception myError) {
			System.err.println(myError.getMessage());
		}
		return tmp;
	}

	/**
	*Perform check to determine if two tiles are equal.						   
	*
	*@param o Object which will be cast to a tile object.
	*@return  True if the color, left pip value, and right pip value all are equivalent; false otherwise.
	*/
	@Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		if(!(o instanceof Tile)) {
			return false;
		}
		
		Tile myTile = (Tile) o;
		return Character.toString(color).compareTo(Character.toString(myTile.getColor())) == 0 && pipsLeftEnd == myTile.getPipsLeftEnd() && pipsRightEnd == myTile.getPipsRightEnd();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Character.toString(color));
		dest.writeInt(pipsLeftEnd);
		dest.writeInt(pipsRightEnd);
	}
	
	public static final Parcelable.Creator<Tile> CREATOR = new Parcelable.Creator<Tile>() {
		public Tile createFromParcel(Parcel in) {
			return new Tile(in);
		}
		public Tile[] newArray(int size) {
			return new Tile[size];
		}
	};
	
	private Tile(Parcel in) {
		color = in.readString().charAt(0);
		pipsLeftEnd = in.readInt();
		pipsRightEnd = in.readInt();
	}
}

