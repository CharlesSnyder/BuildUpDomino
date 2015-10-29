//Name: Charles Snyder
//Project: Java/Android Build-Up Domino Game
//Class:  Organization of Programming Lanuages
//Date:  11/15/14


package edu.ramapo.csnyder2.BuildUp;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import android.support.v7.app.ActionBarActivity;
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
import edu.ramapo.csnyder2.BuildUp.R;
import edu.ramapo.csnyder2.gameLogic.*;

public class DetermineFirstPlayer extends ActionBarActivity {
	
	public final static String GAME_AFTER_FIRST_PLAYER = "edu.ramapo.csnyder2.buildUp.GAME_AFTER_FIRST_PLAYER";
	private final static int PLAYER_WIN = 0;
	private final static int CPU_WIN = 1;
	private final static int DRAW = 2;
	private static Game currentGame;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_player);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		setContentView(R.layout.activity_first_player);
		
		Intent intent = getIntent();
		
		//Check to see if intent came from the play screen activity.  This would happen at the end of a round
		//once a new game is started.
		if(intent.getFlags() == 1) {
			currentGame = new Game ((Game) intent.getExtras().getParcelable(PlayScreen.EXTRA_NEW_GAME));
		}
		//Intent came from main screen.
		else {
			currentGame = new Game((Game) intent.getExtras().getParcelable(MainActivity.EXTRA_GAME));
		}
		
		//Set up the board with the correct domino images.
		displayDominoImage((Button) findViewById(R.id.board_B1), currentGame.getBoardTileAsString(0));
		displayDominoImage((Button) findViewById(R.id.board_B2), currentGame.getBoardTileAsString(1));
		displayDominoImage((Button) findViewById(R.id.board_B3), currentGame.getBoardTileAsString(2));
		displayDominoImage((Button) findViewById(R.id.board_B4), currentGame.getBoardTileAsString(3));
		displayDominoImage((Button) findViewById(R.id.board_B5), currentGame.getBoardTileAsString(4));
		displayDominoImage((Button) findViewById(R.id.board_B6), currentGame.getBoardTileAsString(5));
		displayDominoImage((Button) findViewById(R.id.board_W1), currentGame.getBoardTileAsString(6));
		displayDominoImage((Button) findViewById(R.id.board_W2), currentGame.getBoardTileAsString(7));
		displayDominoImage((Button) findViewById(R.id.board_W3), currentGame.getBoardTileAsString(8));
		displayDominoImage((Button) findViewById(R.id.board_W4), currentGame.getBoardTileAsString(9));
		displayDominoImage((Button) findViewById(R.id.board_W5), currentGame.getBoardTileAsString(10));
		displayDominoImage((Button) findViewById(R.id.board_W6), currentGame.getBoardTileAsString(11));
		
		//Display current win totals of both player's.
		TextView humanWins = ((TextView) findViewById(R.id.human_wins_value));
		humanWins.setText(currentGame.getHumanWinTotal());
		TextView cpuWins = ((TextView) findViewById(R.id.cpu_wins_value));
		cpuWins.setText(currentGame.getCpuWinTotal());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.first_player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if(id == R.id.save_game_action) {
			saveGame();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	*Saves game internally to app specific location.  All relevant game data is written
	*to a string which is written to a fileoutput stream.
	*				   
	*/
	public void saveGame() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.save_game_name);
		//Used to get file name.
		final EditText input = new EditText(this);
		builder.setView(input);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//User confirmed to save the file.
				String fileName = input.getText().toString();
				String savedGame = currentGame.saveGame();

				try {
					//Writes a save file internally, will overwrite any existing file with same name.
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
	*After the first draw has determined a winner this button
	*launches the game to the next screen.  It also draws the remaining
	*tiles to each players hand and sorts the computers hand.
	*
	*@param view    View object.				   
	*/
	public void continueGame(View view) {
		currentGame.drawCurrentPlayerHand();
		currentGame.sortComputerPlayersHand();
		Intent intent = new Intent(this, PlayScreen.class);
		intent.putExtra(GAME_AFTER_FIRST_PLAYER, currentGame);
		startActivity(intent);
	}

	
	/**
	*If tiles the player's drew are equal this button will appear.
	*It simply calls displayDraw to repeat the process of
	*determining the first player for the game.
	*
	*@param view    View object.				   
	*/
	public void drawAgain(View view) {
		Button redrawButton = ((Button) findViewById(R.id.redraw));
		redrawButton.setVisibility(View.GONE);
		displayDraw(view);
	}

	/**
	*Displays the draw of the players to the screen.
	*
	*@param view    View object.				   
	*/
	public void displayDraw(View view) {
		Button drawButton = ((Button) findViewById(R.id.draw));
		Button continueButton = ((Button) findViewById(R.id.continue_game));
		Button redrawButton = ((Button) findViewById(R.id.redraw));
		TextView drawResultMessage = ((TextView) findViewById(R.id.draw_result_message));
		Button humanTileDrawn = ((Button) findViewById(R.id.human_tile_drawn));
		Button cpuTileDrawn = ((Button) findViewById(R.id.cpu_tile_drawn));
		//Draw tiles from set.
		Tile humanTile = new Tile(currentGame.drawSingleTileHuman());
		Tile cpuTile = new Tile(currentGame.drawSingleTileCpu());
		//Determine result of first player.
		int result = currentGame.determineFirstPlayer(humanTile, cpuTile);
		switch(result) {
			case PLAYER_WIN:
				drawButton.setVisibility(View.GONE);
				continueButton.setVisibility(View.VISIBLE);
				drawResultMessage.setVisibility(View.VISIBLE);
				drawResultMessage.setText(R.string.win_draw);
				humanTileDrawn.setVisibility(View.VISIBLE);
				cpuTileDrawn.setVisibility(View.VISIBLE);
				displayDominoImage(humanTileDrawn, humanTile.tileToString());
				displayDominoImage(cpuTileDrawn, cpuTile.tileToString());
				break;
			case CPU_WIN:
				drawButton.setVisibility(View.GONE);
				continueButton.setVisibility(View.VISIBLE);
				drawResultMessage.setVisibility(View.VISIBLE);
				drawResultMessage.setText(R.string.lose_draw);
				humanTileDrawn.setVisibility(View.VISIBLE);
				cpuTileDrawn.setVisibility(View.VISIBLE);
				displayDominoImage(humanTileDrawn, humanTile.tileToString());
				displayDominoImage(cpuTileDrawn, cpuTile.tileToString());
				break;
			case DRAW:
				drawButton.setVisibility(View.GONE);
				redrawButton.setVisibility(View.VISIBLE);
				drawResultMessage.setVisibility(View.VISIBLE);
				drawResultMessage.setText(R.string.tie);
				humanTileDrawn.setVisibility(View.VISIBLE);
				cpuTileDrawn.setVisibility(View.VISIBLE);
				displayDominoImage(humanTileDrawn, humanTile.tileToString());
				displayDominoImage(cpuTileDrawn, cpuTile.tileToString());
				break;
		}
	}
	
	/**
	*Associates domino image file with specific tile in string format
	*
	*@param myButton   The button whose background will be changed.
	*@param myTile    The tile to match the image of.				   
	*/
	public static void displayDominoImage(Button myButton, String myTile) {
		switch(myTile) {
		case "B00":
			myButton.setBackgroundResource(R.drawable.b00);
			break;
		case "B01":
			myButton.setBackgroundResource(R.drawable.b01);
			break;
		case "B02":
			myButton.setBackgroundResource(R.drawable.b02);
			break;		
		case "B03":
			myButton.setBackgroundResource(R.drawable.b03);
			break;		
		case "B04":
			myButton.setBackgroundResource(R.drawable.b04);
			break;	
		case "B05":
			myButton.setBackgroundResource(R.drawable.b05);
			break;		
		case "B06":
			myButton.setBackgroundResource(R.drawable.b06);
			break;		
		case "B11":
			myButton.setBackgroundResource(R.drawable.b11);
			break;		
		case "B12":
			myButton.setBackgroundResource(R.drawable.b12);
			break;		
		case "B13":
			myButton.setBackgroundResource(R.drawable.b13);
			break;		
		case "B14":
			myButton.setBackgroundResource(R.drawable.b14);
			break;		
		case "B15":
			myButton.setBackgroundResource(R.drawable.b15);
			break;		
		case "B16":
			myButton.setBackgroundResource(R.drawable.b16);
			break;		
		case "B22":
			myButton.setBackgroundResource(R.drawable.b22);
			break;		
		case "B23":
			myButton.setBackgroundResource(R.drawable.b23);
			break;		
		case "B24":
			myButton.setBackgroundResource(R.drawable.b24);
			break;		
		case "B25":
			myButton.setBackgroundResource(R.drawable.b25);
			break;		
		case "B26":
			myButton.setBackgroundResource(R.drawable.b26);
			break;		
		case "B33":
			myButton.setBackgroundResource(R.drawable.b33);
			break;		
		case "B34":
			myButton.setBackgroundResource(R.drawable.b34);
			break;		
		case "B35":
			myButton.setBackgroundResource(R.drawable.b35);
			break;		
		case "B36":
			myButton.setBackgroundResource(R.drawable.b36);
			break;		
		case "B44":
			myButton.setBackgroundResource(R.drawable.b44);
			break;		
		case "B45":
			myButton.setBackgroundResource(R.drawable.b45);
			break;		
		case "B46":
			myButton.setBackgroundResource(R.drawable.b46);
			break;		
		case "B55":
			myButton.setBackgroundResource(R.drawable.b55);
			break;		
		case "B56":
			myButton.setBackgroundResource(R.drawable.b56);
			break;	
		case "B66":
			myButton.setBackgroundResource(R.drawable.b66);
			break;			
		case "W00":
			myButton.setBackgroundResource(R.drawable.w00);
			break;			
		case "W01":
			myButton.setBackgroundResource(R.drawable.w01);
			break;			
		case "W02":
			myButton.setBackgroundResource(R.drawable.w02);
			break;			
		case "W03":
			myButton.setBackgroundResource(R.drawable.w03);
			break;			
		case "W04":
			myButton.setBackgroundResource(R.drawable.w04);
			break;			
		case "W05":
			myButton.setBackgroundResource(R.drawable.w05);
			break;			
		case "W06":
			myButton.setBackgroundResource(R.drawable.w06);
			break;			
		case "W11":
			myButton.setBackgroundResource(R.drawable.w11);
			break;			
		case "W12":
			myButton.setBackgroundResource(R.drawable.w12);
			break;			
		case "W13":
			myButton.setBackgroundResource(R.drawable.w13);
			break;			
		case "W14":
			myButton.setBackgroundResource(R.drawable.w14);
			break;			
		case "W15":
			myButton.setBackgroundResource(R.drawable.w15);
			break;			
		case "W16":
			myButton.setBackgroundResource(R.drawable.w16);
			break;			
		case "W22":
			myButton.setBackgroundResource(R.drawable.w22);
			break;			
		case "W23":
			myButton.setBackgroundResource(R.drawable.w23);
			break;			
		case "W24":
			myButton.setBackgroundResource(R.drawable.w24);
			break;		
		case "W25":
			myButton.setBackgroundResource(R.drawable.w25);
			break;			
		case "W26":
			myButton.setBackgroundResource(R.drawable.w26);
			break;			
		case "W33":
			myButton.setBackgroundResource(R.drawable.w33);
			break;			
		case "W34":
			myButton.setBackgroundResource(R.drawable.w34);
			break;			
		case "W35":
			myButton.setBackgroundResource(R.drawable.w35);
			break;			
		case "W36":
			myButton.setBackgroundResource(R.drawable.w36);
			break;			
		case "W44":
			myButton.setBackgroundResource(R.drawable.w44);
			break;			
		case "W45":
			myButton.setBackgroundResource(R.drawable.w45);
			break;			
		case "W46":
			myButton.setBackgroundResource(R.drawable.w46);
			break;			
		case "W55":
			myButton.setBackgroundResource(R.drawable.w55);
			break;			
		case "W56":
			myButton.setBackgroundResource(R.drawable.w56);
			break;			
		case "W66":
			myButton.setBackgroundResource(R.drawable.w66);
			break;
		}
	}

}
