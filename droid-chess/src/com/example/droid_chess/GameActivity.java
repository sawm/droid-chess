package com.example.droid_chess;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class GameActivity extends Activity implements View.OnClickListener {

	
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
		

		///////////////////////////////////////////////////////////////////////
		//Setting up the pieces, the content description of each piece is a set
		//of flags. The flags are <color>:<type>:<location>
		///////////////////////////////////////////////////////////////////////
		ImageView[] white = new ImageView[16];
		white[0] = new ImageView(this); white[0].setImageResource(R.drawable.rook);
		white[0].setContentDescription("1:1:11");
		white[1] = new ImageView(this);	white[1].setImageResource(R.drawable.knight);
		white[1].setContentDescription("1:2:12");
		white[2] = new ImageView(this);	white[2].setImageResource(R.drawable.bishop);
		white[2].setContentDescription("1:3:13");
		white[3] = new ImageView(this);	white[3].setImageResource(R.drawable.king);
		white[3].setContentDescription("1:4:14");
		white[4] = new ImageView(this);	white[4].setImageResource(R.drawable.queen);
		white[4].setContentDescription("1:5:15");
		white[5] = new ImageView(this);	white[5].setImageResource(R.drawable.bishop);
		white[5].setContentDescription("1:3:16");
		white[6] = new ImageView(this);	white[6].setImageResource(R.drawable.knight);
		white[6].setContentDescription("1:2:17");
		white[7] = new ImageView(this); white[7].setImageResource(R.drawable.rook);
		white[7].setContentDescription("1:1:18");

		ImageView[] black = new ImageView[16];
		black[0] = new ImageView(this); black[0].setImageResource(R.drawable.rook);
		black[0].setContentDescription("2:1:81");
		black[1] = new ImageView(this);	black[1].setImageResource(R.drawable.knight);
		black[1].setContentDescription("2:2:82");
		black[2] = new ImageView(this);	black[2].setImageResource(R.drawable.bishop);
		black[2].setContentDescription("2:3:83");
		black[3] = new ImageView(this);	black[3].setImageResource(R.drawable.queen);
		black[3].setContentDescription("2:5:84");
		black[4] = new ImageView(this);	black[4].setImageResource(R.drawable.king);
		black[4].setContentDescription("2:4:85");
		black[5] = new ImageView(this);	black[5].setImageResource(R.drawable.bishop);
		black[5].setContentDescription("2:3:86");
		black[6] = new ImageView(this);	black[6].setImageResource(R.drawable.knight);
		black[6].setContentDescription("2:2:87");
		black[7] = new ImageView(this); black[7].setImageResource(R.drawable.rook);
		black[7].setContentDescription("2:1:88");
		
		for (int x = 8; x<16 ; x++){
			white[x] = new ImageView(this);	white[x].setImageResource(R.drawable.pawn);	
			white[x].setContentDescription("1:6:2" + (x-7));
			black[x] = new ImageView(this);	black[x].setImageResource(R.drawable.pawn);	
			black[x].setContentDescription("2:6:7" + (x-7));	
		}
		
		for (int x = 0 ; x<16 ; x++){
			white[x].setColorFilter(0x88ffffff,PorterDuff.Mode.SRC_ATOP);
			black[x].setColorFilter(0x88000000,PorterDuff.Mode.SRC_ATOP);
			white[x].setOnClickListener(this);
			black[x].setOnClickListener(this);
			
		}
		
		position.x = 0;
		position.y = 0;
			for (int x = 0; x < 16; x++) {
				RelativeLayout.LayoutParams pieceParams = new RelativeLayout.LayoutParams(width/8,width/8);
				pieceParams.leftMargin=position.x;
				pieceParams.topMargin=position.y;
				position.x = (width / 8) * ((x+1) % 8);
				position.y = (width / 8) * ((int) Math.floor((x+1) / 8));
				layout.addView(white[x],pieceParams);
			}

		position.x = 0;
		position.y = 7 *(width/8);
			for (int x = 0; x < 16; x++) {
				RelativeLayout.LayoutParams pieceParams = new RelativeLayout.LayoutParams(width/8,width/8);
				pieceParams.leftMargin=position.x;
				pieceParams.topMargin=position.y;
				position.x = (width / 8) * ((x+1) % 8);
				position.y = (7 *(width/8)) - ((width / 8) * ((int) Math.floor((x+1) / 8)));
				layout.addView(black[x],pieceParams);
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
	public void onClick(View view) {
		Context context = getApplicationContext();
		String message = "";
		int duration = Toast.LENGTH_SHORT;
		//Toast.makeText(context, "" + view.getContentDescription(), duration).show();
		
		if (Integer.parseInt(((String) view.getContentDescription()).split(":")[0]) == 1) {
			message += "white";
		} else if (Integer.parseInt(((String) view.getContentDescription()).split(":")[0]) == 2) {
			message += "black";
		} else {
			message += "Invalid Color Code";
		}
		switch (Integer.parseInt(((String) view.getContentDescription()).split(":")[1])){
		case 1:
			Toast.makeText(context, message + "Rook", duration).show();
			break;
		case 2:
			Toast.makeText(context, message + "Knight", duration).show();
			break;
		case 3:
			Toast.makeText(context, message + "Bishop", duration).show();
			break;
		case 4:
			Toast.makeText(context, message + "King", duration).show();
			break;
		case 5:
			Toast.makeText(context, message + "Queen", duration).show();
			break;
		case 6:
			Toast.makeText(context, message + "Pawn", duration).show();
			break;
		default:
			Toast.makeText(context, message + "Invalid Piece Code", duration).show();
			
			
		}
	}
}
