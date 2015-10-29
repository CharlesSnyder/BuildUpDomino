//Name: Charles Snyder
//Project: Java/Android Build-Up Domino Game
//Class:  Organization of Programming Lanuages
//Date:  11/15/14

package edu.ramapo.csnyder2.BuildUp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import edu.ramapo.csnyder2.BuildUp.R;
import edu.ramapo.csnyder2.gameLogic.*;

public class MainActivity extends ActionBarActivity {

	public final static String EXTRA_GAME = "edu.ramapo.csnyder2.buildUp.GAME";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	*Creates a new instance of a build up domino game. Shuffles the sets
	*of both players and draws the first hand to the board.
	*
	*
	*@param view					   
	*
	*/
	public void newGame(View view) {
		Game currentGame = new Game();
		currentGame.shufflePlayerSets();
		currentGame.drawFirstHand();
		Intent intent = new Intent(MainActivity.this, DetermineFirstPlayer.class);
		intent.putExtra(EXTRA_GAME, currentGame);
		startActivity(intent);
	}
	
	
	
	/**
	*Loads a game from internal location on device specific to application.
	*
	*
	*@param view					   
	*
	*/
	public void loadGame(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.load);
		//Get files located in interal app folder.
		final String[] myFiles = getApplicationContext().fileList();
		builder.setItems(myFiles, new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				try {
					//BufferedReader which will be used to read game data from.
					BufferedReader inputReader = new BufferedReader(new InputStreamReader(openFileInput(myFiles[which])));
				    Game loadedGame = new Game();
				    loadedGame.loadGame(inputReader);
				    //No turn yet determined must still be in determine first player phase.
					if(loadedGame.isTurnDetermined() == false) {
						Intent intent = new Intent(MainActivity.this, DetermineFirstPlayer.class);
						intent.putExtra(EXTRA_GAME, loadedGame);
						startActivity(intent);
					}
					//Turn was determined but saved immediately after, so must draw remaining tiles for hand and
					//sort the computer's hand.
					else if(loadedGame.getHumansBoneyardSize() == 21) {
						loadedGame.drawCurrentPlayerHand();
						loadedGame.sortComputerPlayersHand();
						Intent intent = new Intent(MainActivity.this, PlayScreen.class);
						intent.putExtra(EXTRA_GAME, loadedGame);
						startActivity(intent);
					}
					//In hand playing phase of the game.
					else {
						Intent intent = new Intent(MainActivity.this, PlayScreen.class);
						intent.putExtra(EXTRA_GAME, loadedGame);
						startActivity(intent);
					}
				}
				catch (IOException e) {
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

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	*Delete a saved game from the internal save location of app.
	*
	*
	*@param view					   
	*
	*/
	public void deleteSave(View view) {
		
		// Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final String[] myFiles = getApplicationContext().fileList();
		// Chain together various setter methods to set the dialog
		// characteristics
		builder.setTitle(R.string.dialog_title);
		builder.setItems(myFiles,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// The 'which' argument contains the index position
						// of the selected item
						File dir = getFilesDir();
						File file = new File(dir, myFiles[which]);
						file.delete();
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


}
