package com.example.droid_chess;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.screen);
		Point position = new Point();
		position.x = 0;
		position.y = 0;
		ImageView[][] squareArray = new ImageView[8][8];
		String color;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				squareArray[x][y] = new ImageView(this);
				if ((x + y) % 2 == 0) {
					squareArray[x][y].setImageResource(R.drawable.white);
					color = "white";
				} else {
					squareArray[x][y].setImageResource(R.drawable.black);
					color = "black";
				}
				RelativeLayout.LayoutParams squareParams = new RelativeLayout.LayoutParams(width/8, width/8);
				squareParams.leftMargin = position.x;
				squareParams.topMargin = position.y;
				position.x += width / 8;
				layout.addView(squareArray[x][y], squareParams);
				System.out.println("[" + x + "][" + y + "] " + color + " ("
						+ position.x + ", " + position.y + ")");
			}
			position.x = 0;
			position.y += width / 8;
		}

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
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				View image = new View(this); 
				image.findViewWithTag("square"+x+y);
				System.out.println(image.getTag());
				//System.out.println("("+x+", "+y+"): ("+image.getWidth()+", "+image.getHeight()+")");
			}
		}
	}

}
