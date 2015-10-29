//Name: Charles Snyder
//Project: Java/Android Build-Up Domino Game
//Class:  Organization of Programming Lanuages
//Date:  11/15/14

package edu.ramapo.csnyder2.BuildUp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import edu.ramapo.csnyder2.BuildUp.R;
import edu.ramapo.csnyder2.gameLogic.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PlayScreen extends Activity {
	public final static String EXTRA_NEW_GAME = "edu.ramapo.csnyder2.buildUp.NEWGAME";
	private final static float unselected = (float) 1.0;
	private final static float selected = (float) 0.5;
	
	private static Game currentGamePlayHands;
	private static Tile userTile;
	private static Tile boardTile;
	private static String location;
	private static Tile handOne;
	private static Tile handTwo;
	private static Tile handThree;
	private static Tile handFour;
	private static Tile handFive;
	private static Tile handSix;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_screen);
		currentGamePlayHands = new Game();
		handOne = new Tile();
		handTwo = new Tile();
		handThree = new Tile();
		handFour = new Tile();
		handFive = new Tile();
		handSix = new Tile();

		userTile = new Tile();
		boardTile = new Tile();
		location = "";
		
		Intent intent = getIntent();
		//Intent came from determine first player screen.
		currentGamePlayHands = intent.getExtras().getParcelable(DetermineFirstPlayer.GAME_AFTER_FIRST_PLAYER);
		//If game was loaded intent comes from main activity.
		if(currentGamePlayHands == null) {
			currentGamePlayHands = intent.getExtras().getParcelable(MainActivity.EXTRA_GAME);
		}
		//Associates domino images with current state of board.
		refreshBoard();
		
		//Associates hand buttons with the current tiles in the human player's hand.
		associateHandTileWithButtons();
		
		//Associate the domino images with the hand buttons.
		refreshHand();
		
		//Display the correct amount of hand buttons based on number of tiles in hand.
		initializeHandButtons();
		
		//Display correct player scores.
		refreshPlayerScores();
		
		//Display correct player wins.
		refreshPlayerWins();

		//Check to see if either player's turn may be over after loading a game.
		currentGamePlayHands.determineEndTurn();
		
		//If loaded state is at end of hand display correct buttons.
		if (currentGamePlayHands.isEndHand()) {
			findViewById(R.id.draw_next_hand_button)
					.setVisibility(View.VISIBLE);
			TextView message = (TextView) findViewById(R.id.game_message);
			
			message.setText(R.string.end_of_hand);
			findViewById(R.id.pass_button).setVisibility(View.GONE);
			findViewById(R.id.help_button).setVisibility(View.GONE);
			if(currentGamePlayHands.getCpuTurn() == true) {
				findViewById(R.id.move_messages).setVisibility(View.GONE);
			}
			message.setVisibility(View.VISIBLE);
		}
		
		//If loaded state is end of round display correct buttons.
		if (currentGamePlayHands.isEndRound()) {
			
			findViewById(R.id.pass_button).setVisibility(View.GONE);
			findViewById(R.id.help_button).setVisibility(View.GONE);
			if(currentGamePlayHands.getCpuTurn() == true) {
				findViewById(R.id.move_messages).setVisibility(View.GONE);
			}
			
			findViewById(R.id.play_again_button).setVisibility(View.VISIBLE);
			findViewById(R.id.end_tourny_button).setVisibility(View.VISIBLE);
			TextView message = (TextView) findViewById(R.id.game_message);
			message.setVisibility(View.VISIBLE);
			
			switch (currentGamePlayHands.determineRoundWinner()) {
			case 0:
				message.setText(R.string.human_win_round);
				break;
			case 1:
				message.setText(R.string.cpu_win_round);
				break;
			case 2:
				message.setText(R.string.draw_round);
				break;
			}
		}	
		

		//If the activity begins with the computer going first it must execute its turn.
		if (currentGamePlayHands.getCpuTurn() == true) {
			cpuMakeMove();
		}
	}
	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.play_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == R.id.save_game_action) {
			// perform save function
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	*Saves game internally to app specific location.  All relevant game data is written
	*to a string which is written to a fileoutput stream.
	*
	*@param view   View object				   
	*/
	public void saveGame(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.save_game_name);
		final EditText input = new EditText(this);
		builder.setView(input);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				String fileName = input.getText().toString();
				String savedGame = currentGamePlayHands.saveGame();
				try {
					FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
					fos.write(savedGame.getBytes());
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog
						dialog.cancel();
					}
				});

		// Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}



	/**
	*Event that occurs when the user selects the first hand button.
	*It gets set as selected and a slight alpha reduction to appear
	*as such.  Also sets the user selected tile to the tile associated
	*with this button.
	*	
	*@param view   View object			   
	*/
	public void handOneSelected(View view) {
		findViewById(R.id.game_message).setVisibility(View.GONE);
		findViewById(R.id.move_messages).setVisibility(View.GONE);
		findViewById(R.id.hand_1).setSelected(true);
		findViewById(R.id.hand_2).setSelected(false);
		findViewById(R.id.hand_3).setSelected(false);
		findViewById(R.id.hand_4).setSelected(false);
		findViewById(R.id.hand_5).setSelected(false);
		findViewById(R.id.hand_6).setSelected(false);
		findViewById(R.id.hand_1).setAlpha(selected);
		findViewById(R.id.hand_2).setAlpha(unselected);
		findViewById(R.id.hand_3).setAlpha(unselected);
		findViewById(R.id.hand_4).setAlpha(unselected);
		findViewById(R.id.hand_5).setAlpha(unselected);
		findViewById(R.id.hand_6).setAlpha(unselected);

		try {
			userTile.setTile(handOne);
		} catch (Exception setException) {
			System.err.println(setException.getMessage());
		}
	}

	
	/**
	*Event that occurs when the user selects the second hand button.
	*It gets set as selected and a slight alpha reduction to appear
	*as such.  Also sets the user selected tile to the tile associated
	*with this button.
	*	
	*@param view   View object			   
	*/
	public void handTwoSelected(View view) {
		findViewById(R.id.game_message).setVisibility(View.GONE);
		findViewById(R.id.move_messages).setVisibility(View.GONE);
		findViewById(R.id.hand_1).setSelected(false);
		findViewById(R.id.hand_2).setSelected(true);
		findViewById(R.id.hand_3).setSelected(false);
		findViewById(R.id.hand_4).setSelected(false);
		findViewById(R.id.hand_5).setSelected(false);
		findViewById(R.id.hand_6).setSelected(false);
		findViewById(R.id.hand_1).setAlpha(unselected);
		findViewById(R.id.hand_2).setAlpha(selected);
		findViewById(R.id.hand_3).setAlpha(unselected);
		findViewById(R.id.hand_4).setAlpha(unselected);
		findViewById(R.id.hand_5).setAlpha(unselected);
		findViewById(R.id.hand_6).setAlpha(unselected);


		try {
			userTile.setTile(handTwo);
		} catch (Exception setException) {
			System.err.println(setException.getMessage());
		}

	}

	/**
	*Event that occurs when the user selects the third hand button.
	*It gets set as selected and a slight alpha reduction to appear
	*as such.  Also sets the user selected tile to the tile associated
	*with this button.
	*	
	*@param view   View object			   
	*/
	public void handThreeSelected(View view) {
		findViewById(R.id.game_message).setVisibility(View.GONE);
		findViewById(R.id.move_messages).setVisibility(View.GONE);
		findViewById(R.id.hand_1).setSelected(false);
		findViewById(R.id.hand_2).setSelected(false);
		findViewById(R.id.hand_3).setSelected(true);
		findViewById(R.id.hand_4).setSelected(false);
		findViewById(R.id.hand_5).setSelected(false);
		findViewById(R.id.hand_6).setSelected(false);
		findViewById(R.id.hand_1).setAlpha(unselected);
		findViewById(R.id.hand_2).setAlpha(unselected);
		findViewById(R.id.hand_3).setAlpha(selected);
		findViewById(R.id.hand_4).setAlpha(unselected);
		findViewById(R.id.hand_5).setAlpha(unselected);
		findViewById(R.id.hand_6).setAlpha(unselected);


		try {
			userTile.setTile(handThree);
		} catch (Exception setException) {
			System.err.println(setException.getMessage());
		}
	}

	/**
	*Event that occurs when the user selects the fourth hand button.
	*It gets set as selected and a slight alpha reduction to appear
	*as such.  Also sets the user selected tile to the tile associated
	*with this button.
	*	
	*@param view   View object			   
	*/
	public void handFourSelected(View view) {
		findViewById(R.id.game_message).setVisibility(View.GONE);
		findViewById(R.id.move_messages).setVisibility(View.GONE);
		findViewById(R.id.hand_1).setSelected(false);
		findViewById(R.id.hand_2).setSelected(false);
		findViewById(R.id.hand_3).setSelected(false);
		findViewById(R.id.hand_4).setSelected(true);
		findViewById(R.id.hand_5).setSelected(false);
		findViewById(R.id.hand_6).setSelected(false);
		findViewById(R.id.hand_1).setAlpha(unselected);
		findViewById(R.id.hand_2).setAlpha(unselected);
		findViewById(R.id.hand_3).setAlpha(unselected);
		findViewById(R.id.hand_4).setAlpha(selected);
		findViewById(R.id.hand_5).setAlpha(unselected);
		findViewById(R.id.hand_6).setAlpha(unselected);
		
		try {
			userTile.setTile(handFour);
		} catch (Exception setException) {
			System.err.println(setException.getMessage());
		}
	}

	
	/**
	*Event that occurs when the user selects the fifth hand button.
	*It gets set as selected and a slight alpha reduction to appear
	*as such.  Also sets the user selected tile to the tile associated
	*with this button.
	*	
	*@param view   View object			   
	*/
	public void handFiveSelected(View view) {
		findViewById(R.id.game_message).setVisibility(View.GONE);
		findViewById(R.id.move_messages).setVisibility(View.GONE);
		findViewById(R.id.hand_1).setSelected(false);
		findViewById(R.id.hand_2).setSelected(false);
		findViewById(R.id.hand_3).setSelected(false);
		findViewById(R.id.hand_4).setSelected(false);
		findViewById(R.id.hand_5).setSelected(true);
		findViewById(R.id.hand_6).setSelected(false);
		findViewById(R.id.hand_1).setAlpha(unselected);
		findViewById(R.id.hand_2).setAlpha(unselected);
		findViewById(R.id.hand_3).setAlpha(unselected);
		findViewById(R.id.hand_4).setAlpha(unselected);
		findViewById(R.id.hand_5).setAlpha(selected);
		findViewById(R.id.hand_6).setAlpha(unselected);
		
		try {
			userTile.setTile(handFive);
		} catch (Exception setException) {
			System.err.println(setException.getMessage());
		}
	}

	/**
	*Event that occurs when the user selects the sixth hand button.
	*It gets set as selected and a slight alpha reduction to appear
	*as such.  Also sets the user selected tile to the tile associated
	*with this button.
	*	
	*@param view   View object			   
	*/
	public void handSixSelected(View view) {
		findViewById(R.id.game_message).setVisibility(View.GONE);
		findViewById(R.id.move_messages).setVisibility(View.GONE);
		findViewById(R.id.hand_1).setSelected(false);
		findViewById(R.id.hand_2).setSelected(false);
		findViewById(R.id.hand_3).setSelected(false);
		findViewById(R.id.hand_4).setSelected(false);
		findViewById(R.id.hand_5).setSelected(false);
		findViewById(R.id.hand_6).setSelected(true);
		findViewById(R.id.hand_1).setAlpha(unselected);
		findViewById(R.id.hand_2).setAlpha(unselected);
		findViewById(R.id.hand_3).setAlpha(unselected);
		findViewById(R.id.hand_4).setAlpha(unselected);
		findViewById(R.id.hand_5).setAlpha(unselected);
		findViewById(R.id.hand_6).setAlpha(selected);
		
		try {
			userTile.setTile(handSix);
		} catch (Exception setException) {
			System.err.println(setException.getMessage());
		}
	}

	
	/**
	*Event that occurs when the user selects a board tile.
	*If no hand button is selected nothing happens.  If a hand button
	*is selected then a move is attempted to be made.  If one is executed
	*then the computer also makes its move and end of hand and round conditions
	*are checked.
	*	
	*@param view   View object			   
	*/
	public void humanMakeMove(View view) {
		if (isHandTileSelected()) {
			determineBoardIndex(view);

			if (!currentGamePlayHands.executeHumanTurn(userTile, boardTile, location, currentGamePlayHands.getGameBoard())) {
				//Move was invalid.
				deselectAllHandTiles();
				findViewById(R.id.game_message).setVisibility(View.VISIBLE);
				TextView message = (TextView) findViewById(R.id.game_message);
				message.setText(R.string.invalid_move);
				return;
			} else {
				//Move was executed.
				refreshBoard();
				deleteSelectedHandButton();
				currentGamePlayHands.switchTurns();
				currentGamePlayHands.determineEndTurn();		
			}
			
			//Computer's turn.
			if(currentGamePlayHands.isEndTurnHuman()) {
				//In case computer has multiple tiles to play and humans turn is over.
				while(currentGamePlayHands.isEndTurnComputer() == false) {
					cpuMakeMove();
				}
			}
			else {
				cpuMakeMove();
			}

			// end hand logic
			endHand();

			// end round logic
			endRound();
		}

	}
	
	
	/**
	*View events that occur once the end of a hand has been reached.
	*			   
	*/
	public void endHand() {
		if (currentGamePlayHands.isEndHand()) {
			//Hand is over.
			findViewById(R.id.draw_next_hand_button).setVisibility(View.VISIBLE);
			TextView message = (TextView) findViewById(R.id.game_message);
			
			message.setText(R.string.end_of_hand);
			findViewById(R.id.pass_button).setVisibility(View.GONE);
			findViewById(R.id.help_button).setVisibility(View.GONE);
			if(currentGamePlayHands.getCpuTurn() == true) {
				findViewById(R.id.move_messages).setVisibility(View.GONE);
			}
			message.setVisibility(View.VISIBLE);
			currentGamePlayHands.calculateScores();
			
			//Refresh the player score display.
			refreshPlayerScores();
			
			//Clear any remaining tiles from the player's hands.
			currentGamePlayHands.clearHands();
		}
	}
	
	
	/**
	*View events that occur once the end of a round has been reached.
	*			   
	*/
	public void endRound() {
		if (currentGamePlayHands.isEndRound()) {
			//Round is over.
			currentGamePlayHands.calculateScores();
			
			findViewById(R.id.pass_button).setVisibility(View.GONE);
			findViewById(R.id.help_button).setVisibility(View.GONE);
			
			if(currentGamePlayHands.getCpuTurn() == true) {
				findViewById(R.id.move_messages).setVisibility(View.GONE);
			}
			//Refresh the player score display.
			refreshPlayerScores();

			currentGamePlayHands.clearHands();
			
			findViewById(R.id.play_again_button).setVisibility(View.VISIBLE);
			findViewById(R.id.end_tourny_button).setVisibility(View.VISIBLE);
			
			TextView message = (TextView) findViewById(R.id.game_message);
			message.setVisibility(View.VISIBLE);
			//Display winner of round.
			switch (currentGamePlayHands.determineRoundWinner()) {
			case 0:
				message.setText(R.string.human_win_round);
				break;
			case 1:
				message.setText(R.string.cpu_win_round);
				break;
			case 2:
				message.setText(R.string.draw_round);
				break;
			}
			//Refresh player win display.
			refreshPlayerWins();
		}		
	}

	/**
	*Event of the computer's move.
	*			   
	*/
	public void cpuMakeMove() {
		if (currentGamePlayHands.isEndTurnComputer() == false && currentGamePlayHands.getCpuTurn() == true) {
			if (currentGamePlayHands.executeCpuTurn(currentGamePlayHands.getGameBoard(), false)) {
				refreshBoard();
				TextView cpuMessage = (TextView) findViewById(R.id.move_messages);
				cpuMessage.setVisibility(View.VISIBLE);
				cpuMessage.setText(currentGamePlayHands.getCpuMoveInfo());
				currentGamePlayHands.determineEndTurn();
			} else {
				TextView cpuMessage = (TextView) findViewById(R.id.move_messages);
				cpuMessage.setVisibility(View.VISIBLE);
				cpuMessage.setText(R.string.cpu_pass);
				currentGamePlayHands.setPassComputer();
			}
		}
		currentGamePlayHands.switchTurns();
	}

	/**
	*Event when the user clicks the pass button.
	*First it is determined whether any moves are possible for the player's current tiles.
	*If so then the pass is rejected, otherwise the pass indicator is set and the computer
	*executes its move.
	*		
	*@param view   View object	   
	*/
	public void passHuman(View view) {
		//Used to test if any moves are possible.
		Computer testMove = new Computer();
		testMove.setHand(currentGamePlayHands.getHumansHand());
		try {
			if (testMove.playTile(currentGamePlayHands.getGameBoard(), true) == false) {
				//Human passed.
				currentGamePlayHands.setPassHuman();
				currentGamePlayHands.switchTurns();
				if(currentGamePlayHands.isEndTurnHuman() == true) {
					while(currentGamePlayHands.isEndTurnComputer() == false) {
						cpuMakeMove();
					}
				}
				else {
					cpuMakeMove();
				}

				// end hand logic
				endHand();

				// end round logic
				endRound();
				
			} else {
				//Human had moves available.
				TextView passMessage = (TextView) findViewById(R.id.move_messages);
				passMessage.setText(R.string.no_pass);
				passMessage.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	*Event when the user clicks the help button.
	*Using the computer's logic a best move is determined
	*and the corresponding tiles are highlighted.
	*		
	*@param view   View object	   
	*/
	public void helpHuman(View view) {
		Computer helpMove = new Computer();
		helpMove.setHand(currentGamePlayHands.getHumansHand());
		helpMove.sortHand();
		findViewById(R.id.game_message).setVisibility(View.GONE);
		try {
			if (helpMove.playTile(currentGamePlayHands.getGameBoard(), true) == true) {
				//Move was possible.
				TextView helpMessage = (TextView) findViewById(R.id.move_messages);
				helpMessage.setVisibility(View.VISIBLE);
				helpMessage.setText(helpMove.displayHelpMove());
				
				//Find where in the board stack was the replaced tile located.
				int boardIndex = findHelpBoardIdIndex(helpMove.getTileReplaced());
				//Get the corresponding id for the tile.
				Button helpBoardButton = findBoardButtonId(boardIndex);
				//Change the image to show it as highlighted.
				displayDominoImageSelected(helpBoardButton, helpMove.getTileReplaced().tileToString());
				
				//Find where in the hand list was the selected tile located.
				int handIndex = findHelpHandIdIndex(helpMove.getTilePlayed());
				//Find corresponding button for that tile.
				Button helpHandButton = findHandButtonId(handIndex);
				//Change the image to show it as highlighted.
				displayDominoImageSelected(helpHandButton, helpMove.getTilePlayed().tileToString());
			} else {
				//No moves were possible.
				TextView helpMessage = (TextView) findViewById(R.id.move_messages);
				helpMessage.setVisibility(View.VISIBLE);
				helpMessage.setText(R.string.help_no_moves);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	*Event when the round is over and the user chooses to play another game.
	*A new game is created, sets are shuffled and the first hand drawn.
	*A flag is set to tell the main activity where the intent is coming from.
	*		
	*@param view  View object  
	*/
	public void playAgain(View view) {
		Game newGame = new Game(currentGamePlayHands.getHumanWins(),
				currentGamePlayHands.getComputerWins());
		newGame.shufflePlayerSets();
		newGame.drawFirstHand();
		Intent intent = new Intent(PlayScreen.this,
				DetermineFirstPlayer.class);
		intent.putExtra(EXTRA_NEW_GAME, newGame);
		intent.setFlags(1);
		startActivity(intent);
	}

	/**
	*Event when the round is over and the user chooses to end the tournament.
	*The result is displayed to the screen.
	*		
	*@param view  View object  
	*/
	public void endTournament(View view) {
		TextView message = (TextView) findViewById(R.id.game_message);
		findViewById(R.id.play_again_button).setVisibility(View.GONE);
		findViewById(R.id.end_tourny_button).setVisibility(View.GONE);
		findViewById(R.id.exit_game_button).setVisibility(View.VISIBLE);
		findViewById(R.id.move_messages).setVisibility(View.GONE);

		switch (currentGamePlayHands.determineTournamentWinner()) {
		case 0:
			message.setText(R.string.human_win_tourny);
			break;
		case 1:
			message.setText(R.string.cpu_win_tourny);
			break;
		case 2:
			message.setText(R.string.draw_tourny);
			break;
		}
	}

	/**
	*Event when the round is over and the user chooses to end the tournament.
	*Button will return the app back to the main menu.
	*		
	*@param view  View object  
	*/
	public void exitGame(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	
	/**
	*Event when the hand is over and the next hands have to be drawn.
	*		
	*@param view  View object  
	*/
	public void drawNextHand(View view) {
		findViewById(R.id.game_message).setVisibility(View.GONE);
		findViewById(R.id.move_messages).setVisibility(View.GONE);
		findViewById(R.id.draw_next_hand_button).setVisibility(View.GONE);
		findViewById(R.id.pass_button).setVisibility(View.VISIBLE);
		findViewById(R.id.help_button).setVisibility(View.VISIBLE);
		currentGamePlayHands.drawCurrentPlayerHand();
		currentGamePlayHands.sortComputerPlayersHand();
		refreshHand();
		associateHandTileWithButtons();
		
		currentGamePlayHands.resetEndPassTurnValues();
		
		initializeHandButtons();
		
		if(currentGamePlayHands.getCpuTurn() == true) {
			cpuMakeMove();
		}
	}
	
	/**
	*Sets private variables to the current tiles in the human player's hand.
	*This is to prevent the the image of a button displaying a different value
	*than the tile has in the hand array.  The arrayList shrinks in size if
	*a tile gets removed so the numbers of the indices are not static.
	*				   
	*/
	private void associateHandTileWithButtons() {
		try {
			switch(currentGamePlayHands.getHumansHandSize()) {
			case 6:
				handSix.setTile(currentGamePlayHands.getTileFromHand(5));
			case 5:
				handFive.setTile(currentGamePlayHands.getTileFromHand(4));			
			case 4:
				handFour.setTile(currentGamePlayHands.getTileFromHand(3));	
			case 3:
				handThree.setTile(currentGamePlayHands.getTileFromHand(2));
			case 2:
				handTwo.setTile(currentGamePlayHands.getTileFromHand(1));	
			case 1:
				handOne.setTile(currentGamePlayHands.getTileFromHand(0));
				break;
			default:
				break;	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	*Find the index position in the hand list of the selected tile.
	*		
	*@param helpHandTile   Tile object to be matched in the hand list.
	*@return integer value of index where helpHandTile is located.   
	*/
	private int findHelpHandIdIndex(Tile helpHandTile) {
		if(helpHandTile.equals(handOne)) {
			return 0;
		}
		else if(helpHandTile.equals(handTwo)) {
			return 1;
		}
		else if(helpHandTile.equals(handThree)) {
			return 2;
		}
		else if(helpHandTile.equals(handFour)) {
			return 3;
		}
		else if(helpHandTile.equals(handFive)) {
			return 4;
		}
		else if(helpHandTile.equals(handSix)) {
			return 5;
		}
		else {
			return -1;
		}
	}

	
	/**
	*Using the index position find the corresponding button.
	*		
	*@param idNum  The index position of the selected tile.
	*@return Button object that matches the selected tile.   
	*/
	private Button findHandButtonId(int idNum) {
		switch(idNum) {
		case 0: return (Button) findViewById(R.id.hand_1);
		case 1: return (Button) findViewById(R.id.hand_2);
		case 2: return (Button) findViewById(R.id.hand_3);
		case 3: return (Button) findViewById(R.id.hand_4);
		case 4: return (Button) findViewById(R.id.hand_5);
		case 5: return (Button) findViewById(R.id.hand_6);
		default: return null;
		}
	}
	
	
	/**
	*Find the index position in the board stacks list of the replaced tile.
	*		
	*@param helpBoardTile   Tile object to be matched in the board stacks list.
	*@return integer value of index where helpBoardTile is located.   
	*/
	private int findHelpBoardIdIndex(Tile helpBoardTile) {
		String helpBoardString = helpBoardTile.tileToString();
		ArrayList<String> helpBoardStacks = currentGamePlayHands.getGameBoardStacks();
		int boardIndex = 0;
		for(int index = 0; index < helpBoardStacks.size(); index++) {
			if(helpBoardString.equals(helpBoardStacks.get(index))) {
				boardIndex = index;
				break;
			}
		}
		return boardIndex;
	}

	/**
	*Using the index position find the corresponding button.
	*		
	*@param idNum  The index position of the replaced tile.
	*@return Button object that matches the replaced tile.   
	*/
	private Button findBoardButtonId(int idNum) {
		switch(idNum) {
		case 0: return (Button) findViewById(R.id.board_B1);
		case 1: return (Button) findViewById(R.id.board_B2);
		case 2: return (Button) findViewById(R.id.board_B3);
		case 3: return (Button) findViewById(R.id.board_B4);
		case 4: return (Button) findViewById(R.id.board_B5);
		case 5: return (Button) findViewById(R.id.board_B6);
		case 6: return (Button) findViewById(R.id.board_W1);
		case 7: return (Button) findViewById(R.id.board_W2);
		case 8: return (Button) findViewById(R.id.board_W3);
		case 9: return (Button) findViewById(R.id.board_W4);
		case 10: return (Button) findViewById(R.id.board_W5);
		case 11: return (Button) findViewById(R.id.board_W6);
		default: return null;
		}
	}

	


	/**
	*Depending on which board button is pressed the board tile
	*gets its value and the location is set to that board space.
	*		
	*@param view  View object  
	*/
	private void determineBoardIndex(View view) {
		switch (view.getId()) {
		case R.id.board_B1:
			setBoardTileSelected(0);
			location = "B1";
			break;
		case R.id.board_B2:
			setBoardTileSelected(1);
			location = "B2";
			break;
		case R.id.board_B3:
			setBoardTileSelected(2);
			location = "B3";
			break;
		case R.id.board_B4:
			setBoardTileSelected(3);
			location = "B4";
			break;
		case R.id.board_B5:
			setBoardTileSelected(4);
			location = "B5";
			break;
		case R.id.board_B6:
			setBoardTileSelected(5);
			location = "B6";
			break;
		case R.id.board_W1:
			setBoardTileSelected(6);
			location = "W1";
			break;
		case R.id.board_W2:
			setBoardTileSelected(7);
			location = "W2";
			break;
		case R.id.board_W3:
			setBoardTileSelected(8);
			location = "W3";
			break;
		case R.id.board_W4:
			setBoardTileSelected(9);
			location = "W4";
			break;
		case R.id.board_W5:
			setBoardTileSelected(10);
			location = "W5";
			break;
		case R.id.board_W6:
			setBoardTileSelected(11);
			location = "W6";
			break;
		}
	}

	
	/**
	*If an invalid move occurs all hand buttons are set
	*to their default state.
	*		
	*/
	private void deselectAllHandTiles() {
		findViewById(R.id.hand_1).setSelected(false);
		findViewById(R.id.hand_2).setSelected(false);
		findViewById(R.id.hand_3).setSelected(false);
		findViewById(R.id.hand_4).setSelected(false);
		findViewById(R.id.hand_5).setSelected(false);
		findViewById(R.id.hand_6).setSelected(false);
		findViewById(R.id.hand_1).setAlpha((float) 1.0);
		findViewById(R.id.hand_2).setAlpha((float) 1.0);
		findViewById(R.id.hand_3).setAlpha((float) 1.0);
		findViewById(R.id.hand_4).setAlpha((float) 1.0);
		findViewById(R.id.hand_5).setAlpha((float) 1.0);
		findViewById(R.id.hand_6).setAlpha((float) 1.0);
	}

	/**
	*Sets the board tile
	*	
	*@param index   Integer value representing location in board stack list.	
	*/
	private void setBoardTileSelected(int index) {
		try {
			boardTile.setTile(currentGamePlayHands.getBoardTile(index));
		} catch (Exception setException) {
			System.err.println(setException.getMessage());
		}
	}

	
	/**
	*Checks to see if any hand buttons are in the selected state.
	*	
	*@return  True if at least one button is selected, false otherwise.	
	*/
	private boolean isHandTileSelected() {
		if (findViewById(R.id.hand_1).isSelected() == true
				|| findViewById(R.id.hand_2).isSelected() == true
				|| findViewById(R.id.hand_3).isSelected() == true
				|| findViewById(R.id.hand_4).isSelected() == true
				|| findViewById(R.id.hand_5).isSelected() == true
				|| findViewById(R.id.hand_6).isSelected() == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	*Once a move has been made with the tile associated with the button
	*it must be removed from the screen.
	*	
	*/
	private void deleteSelectedHandButton() {
		if (findViewById(R.id.hand_1).isSelected() == true) {
			findViewById(R.id.hand_1).setVisibility(View.GONE);
			
		} else if (findViewById(R.id.hand_2).isSelected() == true) {
			findViewById(R.id.hand_2).setVisibility(View.GONE);
			
		} else if (findViewById(R.id.hand_3).isSelected() == true) {
			findViewById(R.id.hand_3).setVisibility(View.GONE);
			
		} else if (findViewById(R.id.hand_4).isSelected() == true) {
			findViewById(R.id.hand_4).setVisibility(View.GONE);
			
		} else if (findViewById(R.id.hand_5).isSelected() == true) {
			findViewById(R.id.hand_5).setVisibility(View.GONE);
			
		} else if (findViewById(R.id.hand_6).isSelected() == true) {
			findViewById(R.id.hand_6).setVisibility(View.GONE);
			
		}
	}
	
	/**
	*Updates the player's score display.
	*				   
	*/
	private void refreshPlayerScores() {
		TextView humanScore = (TextView) findViewById(R.id.human_score_value);
		TextView cpuScore = (TextView) findViewById(R.id.cpu_score_value);
		humanScore.setText(currentGamePlayHands.getHumanScoreAsString());
		cpuScore.setText(currentGamePlayHands.getCpuScoreAsString());
	}
	
	/**
	*Updates the player's win display.
	*				   
	*/
	private void refreshPlayerWins() {
		TextView humanWins = ((TextView) findViewById(R.id.human_wins_value));
		humanWins.setText(currentGamePlayHands.getHumanWinTotal());
		TextView cpuWins = ((TextView) findViewById(R.id.cpu_wins_value));
		cpuWins.setText(currentGamePlayHands.getCpuWinTotal());	
	}

	/**
	*Based on the number of tiles in the human players hand
	*the correct number of hand buttons will be displayed.
	*				   
	*/
	private void initializeHandButtons() {
		switch(currentGamePlayHands.getHumansHandSize()) {
		case 6:
			findViewById(R.id.hand_6).setVisibility(View.VISIBLE);
			findViewById(R.id.hand_6).setAlpha((float) 1.0);
			
		case 5:
			findViewById(R.id.hand_5).setVisibility(View.VISIBLE);
			findViewById(R.id.hand_5).setAlpha((float) 1.0);
			
		case 4:
			findViewById(R.id.hand_4).setVisibility(View.VISIBLE);
			findViewById(R.id.hand_4).setAlpha((float) 1.0);
			
		case 3:
			findViewById(R.id.hand_3).setVisibility(View.VISIBLE);
			findViewById(R.id.hand_3).setAlpha((float) 1.0);
			
		case 2:
			findViewById(R.id.hand_2).setVisibility(View.VISIBLE);
			findViewById(R.id.hand_2).setAlpha((float) 1.0);
			
		case 1:
			findViewById(R.id.hand_1).setVisibility(View.VISIBLE);
			findViewById(R.id.hand_1).setAlpha((float) 1.0);
			
			break;
		default:
			break;
		}

	}

	/**
	*Associates the correct images with the current tiles on the board.
	*	
	*/
	private void refreshBoard() {
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_B1),
				currentGamePlayHands.getBoardTileAsString(0));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_B2),
				currentGamePlayHands.getBoardTileAsString(1));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_B3),
				currentGamePlayHands.getBoardTileAsString(2));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_B4),
				currentGamePlayHands.getBoardTileAsString(3));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_B5),
				currentGamePlayHands.getBoardTileAsString(4));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_B6),
				currentGamePlayHands.getBoardTileAsString(5));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_W1),
				currentGamePlayHands.getBoardTileAsString(6));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_W2),
				currentGamePlayHands.getBoardTileAsString(7));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_W3),
				currentGamePlayHands.getBoardTileAsString(8));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_W4),
				currentGamePlayHands.getBoardTileAsString(9));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_W5),
				currentGamePlayHands.getBoardTileAsString(10));
		DetermineFirstPlayer.displayDominoImage(
				(Button) findViewById(R.id.board_W6),
				currentGamePlayHands.getBoardTileAsString(11));
	}

	/**
	*Associates the correct images with the current tiles in the humans hand.
	*	
	*/
	private void refreshHand() {
		switch(currentGamePlayHands.getHumansHandSize()) {
		case 6:
			DetermineFirstPlayer.displayDominoImage(
					(Button) findViewById(R.id.hand_6),
					currentGamePlayHands.getTileFromHandAsString(5));
		case 5:
			DetermineFirstPlayer.displayDominoImage(
					(Button) findViewById(R.id.hand_5),
					currentGamePlayHands.getTileFromHandAsString(4));
		case 4:
			DetermineFirstPlayer.displayDominoImage(
					(Button) findViewById(R.id.hand_4),
					currentGamePlayHands.getTileFromHandAsString(3));
		case 3:
			DetermineFirstPlayer.displayDominoImage(
					(Button) findViewById(R.id.hand_3),
					currentGamePlayHands.getTileFromHandAsString(2));
		case 2:
			DetermineFirstPlayer.displayDominoImage(
					(Button) findViewById(R.id.hand_2),
					currentGamePlayHands.getTileFromHandAsString(1));
		case 1:
			DetermineFirstPlayer.displayDominoImage(
					(Button) findViewById(R.id.hand_1),
					currentGamePlayHands.getTileFromHandAsString(0));
			break;
		default:
			break;
		}
	}
	
	/**
	*Associates the correct highlighted or selected images for the help function
	*with the tiles in the hand and on the board.
	*	
	*/
	private void displayDominoImageSelected(Button myButton, String myTile) {
		switch(myTile) {
		case "B00":
			myButton.setBackgroundResource(R.drawable.b00_selected);
			break;
		case "B01":
			myButton.setBackgroundResource(R.drawable.b01_selected);
			break;
		case "B02":
			myButton.setBackgroundResource(R.drawable.b02_selected);
			break;		
		case "B03":
			myButton.setBackgroundResource(R.drawable.b03_selected);
			break;		
		case "B04":
			myButton.setBackgroundResource(R.drawable.b04_selected);
			break;	
		case "B05":
			myButton.setBackgroundResource(R.drawable.b05_selected);
			break;		
		case "B06":
			myButton.setBackgroundResource(R.drawable.b06_selected);
			break;		
		case "B11":
			myButton.setBackgroundResource(R.drawable.b11_selected);
			break;		
		case "B12":
			myButton.setBackgroundResource(R.drawable.b12_selected);
			break;		
		case "B13":
			myButton.setBackgroundResource(R.drawable.b13_selected);
			break;		
		case "B14":
			myButton.setBackgroundResource(R.drawable.b14_selected);
			break;		
		case "B15":
			myButton.setBackgroundResource(R.drawable.b15_selected);
			break;		
		case "B16":
			myButton.setBackgroundResource(R.drawable.b16_selected);
			break;		
		case "B22":
			myButton.setBackgroundResource(R.drawable.b22_selected);
			break;		
		case "B23":
			myButton.setBackgroundResource(R.drawable.b23_selected);
			break;		
		case "B24":
			myButton.setBackgroundResource(R.drawable.b24_selected);
			break;		
		case "B25":
			myButton.setBackgroundResource(R.drawable.b25_selected);
			break;		
		case "B26":
			myButton.setBackgroundResource(R.drawable.b26_selected);
			break;		
		case "B33":
			myButton.setBackgroundResource(R.drawable.b33_selected);
			break;		
		case "B34":
			myButton.setBackgroundResource(R.drawable.b34_selected);
			break;		
		case "B35":
			myButton.setBackgroundResource(R.drawable.b35_selected);
			break;		
		case "B36":
			myButton.setBackgroundResource(R.drawable.b36_selected);
			break;		
		case "B44":
			myButton.setBackgroundResource(R.drawable.b44_selected);
			break;		
		case "B45":
			myButton.setBackgroundResource(R.drawable.b45_selected);
			break;		
		case "B46":
			myButton.setBackgroundResource(R.drawable.b46_selected);
			break;		
		case "B55":
			myButton.setBackgroundResource(R.drawable.b55_selected);
			break;		
		case "B56":
			myButton.setBackgroundResource(R.drawable.b56_selected);
			break;	
		case "B66":
			myButton.setBackgroundResource(R.drawable.b66_selected);
			break;			
		case "W00":
			myButton.setBackgroundResource(R.drawable.w00_selected);
			break;			
		case "W01":
			myButton.setBackgroundResource(R.drawable.w01_selected);
			break;			
		case "W02":
			myButton.setBackgroundResource(R.drawable.w02_selected);
			break;			
		case "W03":
			myButton.setBackgroundResource(R.drawable.w03_selected);
			break;			
		case "W04":
			myButton.setBackgroundResource(R.drawable.w04_selected);
			break;			
		case "W05":
			myButton.setBackgroundResource(R.drawable.w05_selected);
			break;			
		case "W06":
			myButton.setBackgroundResource(R.drawable.w06_selected);
			break;			
		case "W11":
			myButton.setBackgroundResource(R.drawable.w11_selected);
			break;			
		case "W12":
			myButton.setBackgroundResource(R.drawable.w12_selected);
			break;			
		case "W13":
			myButton.setBackgroundResource(R.drawable.w13_selected);
			break;			
		case "W14":
			myButton.setBackgroundResource(R.drawable.w14_selected);
			break;			
		case "W15":
			myButton.setBackgroundResource(R.drawable.w15_selected);
			break;			
		case "W16":
			myButton.setBackgroundResource(R.drawable.w16_selected);
			break;			
		case "W22":
			myButton.setBackgroundResource(R.drawable.w22_selected);
			break;			
		case "W23":
			myButton.setBackgroundResource(R.drawable.w23_selected);
			break;			
		case "W24":
			myButton.setBackgroundResource(R.drawable.w24_selected);
			break;		
		case "W25":
			myButton.setBackgroundResource(R.drawable.w25_selected);
			break;			
		case "W26":
			myButton.setBackgroundResource(R.drawable.w26_selected);
			break;			
		case "W33":
			myButton.setBackgroundResource(R.drawable.w33_selected);
			break;			
		case "W34":
			myButton.setBackgroundResource(R.drawable.w34_selected);
			break;			
		case "W35":
			myButton.setBackgroundResource(R.drawable.w35_selected);
			break;			
		case "W36":
			myButton.setBackgroundResource(R.drawable.w36_selected);
			break;			
		case "W44":
			myButton.setBackgroundResource(R.drawable.w44_selected);
			break;			
		case "W45":
			myButton.setBackgroundResource(R.drawable.w45_selected);
			break;			
		case "W46":
			myButton.setBackgroundResource(R.drawable.w46_selected);
			break;			
		case "W55":
			myButton.setBackgroundResource(R.drawable.w55_selected);
			break;			
		case "W56":
			myButton.setBackgroundResource(R.drawable.w56_selected);
			break;			
		case "W66":
			myButton.setBackgroundResource(R.drawable.w66_selected);
			break;
		}
	}
}
