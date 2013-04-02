package com.example.droid_chess;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class GameActivity extends Activity {

	private static final String TAG = "MyActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		///////////////////////////////////////////////////////////////////////
		//Get width of the screen to properly calculate board size
		///////////////////////////////////////////////////////////////////////
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		
		///////////////////////////////////////////////////////////////////////
		//Create the board layout
		///////////////////////////////////////////////////////////////////////
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.screen);
		Point position = new Point();
		position.x = 0;
		position.y = 0;
		ImageView[][] squareArray = new ImageView[8][8];
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				squareArray[x][y] = new ImageView(this);
				if ((x + y) % 2 == 0) {
					squareArray[x][y].setImageResource(R.drawable.white);
				} else {
					squareArray[x][y].setImageResource(R.drawable.black);
				}
				RelativeLayout.LayoutParams squareParams = new RelativeLayout.LayoutParams(width/8, width/8);
				squareParams.leftMargin = position.x;
				squareParams.topMargin = position.y;
				position.x += width / 8;
				layout.addView(squareArray[x][y], squareParams);
			}
			position.x = 0;
			position.y += width / 8;
		}
		
		
		ImageView[] white = new ImageView[16];
		white[0] = new ImageView(this);
		white[0].setImageResource(R.drawable.rook);
		white[1] = new ImageView(this);
		white[1].setImageResource(R.drawable.knight);
		white[2] = new ImageView(this);
		white[2].setImageResource(R.drawable.bishop);
		white[3] = new ImageView(this);
		white[3].setImageResource(R.drawable.king);
		white[4] = new ImageView(this);
		white[4].setImageResource(R.drawable.queen);
		white[5] = new ImageView(this);
		white[5].setImageResource(R.drawable.bishop);
		white[6] = new ImageView(this);
		white[6].setImageResource(R.drawable.knight);
		for (int x = 7; x<16 ; x++){
			white[x] = new ImageView(this);
			white[x].setImageResource(R.drawable.pawn);			
		}

		position.x = 0;
		position.y = 0;
		for (int y = 0; y < 2; y++ ){
			for (int x = 0; x < 8; x++) {
				RelativeLayout.LayoutParams pieceParams = new RelativeLayout.LayoutParams(width/8,width/8);
				pieceParams.leftMargin=position.x;
				pieceParams.topMargin=position.y;
				layout.addView(white[x],pieceParams);
				position.x += width / 8;
			}
			//position.x = 0;
			//position.y = width / 8;
		}
		
		ImageView rook = new ImageView(this);
		rook.setImageResource(R.drawable.king);
		rook.setColorFilter(0xff222222,PorterDuff.Mode.SRC_ATOP);
		RelativeLayout.LayoutParams pieceParams = new RelativeLayout.LayoutParams(width/8,width/8);
		pieceParams.leftMargin=0;
		pieceParams.topMargin=0;
		layout.addView(rook,pieceParams);
		
		
		///////////////////////////////////////////////////////////////////////
		//Set the pieces
		///////////////////////////////////////////////////////////////////////
		/*ImageView[] white = new ImageView[16];
		white[0].setImageResource(R.drawable.rook);
		RelativeLayout.LayoutParams pieceParams = new RelativeLayout.LayoutParams(width/8,width/8);
		pieceParams.leftMargin=0;
		pieceParams.topMargin=0;
		layout.addView(white[0],pieceParams);*/
		
		
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
