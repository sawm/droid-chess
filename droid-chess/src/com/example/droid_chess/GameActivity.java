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
	ImageView[][] squareArray;
	ImageView[] white;
	ImageView[] black;
	char pieceLayout[][];
	
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
		squareArray = new ImageView[8][8];
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
		//of flags. The flags are <color>:<type>:x-location:y-location
		///////////////////////////////////////////////////////////////////////
		white = new ImageView[16];
		white[0] = new ImageView(this); white[0].setImageResource(R.drawable.rook);
		white[0].setContentDescription("1:1:0:0");
		white[1] = new ImageView(this);	white[1].setImageResource(R.drawable.knight);
		white[1].setContentDescription("1:2:1:0");
		white[2] = new ImageView(this);	white[2].setImageResource(R.drawable.bishop);
		white[2].setContentDescription("1:3:2:0");
		white[3] = new ImageView(this);	white[3].setImageResource(R.drawable.king);
		white[3].setContentDescription("1:4:3:0");
		white[4] = new ImageView(this);	white[4].setImageResource(R.drawable.queen);
		white[4].setContentDescription("1:5:4:0");
		white[5] = new ImageView(this);	white[5].setImageResource(R.drawable.bishop);
		white[5].setContentDescription("1:3:5:0");
		white[6] = new ImageView(this);	white[6].setImageResource(R.drawable.knight);
		white[6].setContentDescription("1:2:6:0");
		white[7] = new ImageView(this); white[7].setImageResource(R.drawable.rook);
		white[7].setContentDescription("1:1:7:0");

		black = new ImageView[16];
		black[0] = new ImageView(this); black[0].setImageResource(R.drawable.rook);
		black[0].setContentDescription("2:1:0:7");
		black[1] = new ImageView(this);	black[1].setImageResource(R.drawable.knight);
		black[1].setContentDescription("2:2:1:7");
		black[2] = new ImageView(this);	black[2].setImageResource(R.drawable.bishop);
		black[2].setContentDescription("2:3:2:7");
		black[3] = new ImageView(this);	black[3].setImageResource(R.drawable.queen);
		black[3].setContentDescription("2:5:3:7");
		black[4] = new ImageView(this);	black[4].setImageResource(R.drawable.king);
		black[4].setContentDescription("2:4:4:7");
		black[5] = new ImageView(this);	black[5].setImageResource(R.drawable.bishop);
		black[5].setContentDescription("2:3:5:7");
		black[6] = new ImageView(this);	black[6].setImageResource(R.drawable.knight);
		black[6].setContentDescription("2:2:6:7");
		black[7] = new ImageView(this); black[7].setImageResource(R.drawable.rook);
		black[7].setContentDescription("2:1:7:7");
		
		for (int x = 8; x<16 ; x++){
			white[x] = new ImageView(this);	white[x].setImageResource(R.drawable.pawn);	
			white[x].setContentDescription("1:6:" + (x-8) + ":1");
			black[x] = new ImageView(this);	black[x].setImageResource(R.drawable.pawn);	
			black[x].setContentDescription("2:6:" + (x-8) + ":6");	
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
		
					
			resetBoard();

			String message = "";
			for (int y = 0 ; y < 8 ; y++){
				for (int x = 0 ; x < 8 ; x++){
					message += squareArray[y][x].getContentDescription();
				}
				message +="\n";
			}			
			Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();		setupActionBar();
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
		Point piecePos = new Point();
		//Toast.makeText(context, "" + view.getContentDescription(), duration).show();
		
		if (getPieceParams(view,0) == 1) {
			message += "White";
		} else if (getPieceParams(view,0) == 2) {
			message += "Black";
		} else {
			message += "Invalid Color Code";
		}
		
		switch (getPieceParams(view,1)){
		case 1:
			Toast.makeText(context, message + " Rook (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
//			piecePos.x = getPieceParams(view, 2)-1;
//			piecePos.y = getPieceParams(view, 3)-1;
//			for(int x = piecePos.x; x < 8; x++){
//				squareArray[x]
//			}
//			for(int x = piecePos.x; x >= 0; x--)
			break;
		case 2:
			Toast.makeText(context, message + " Knight (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
			break;
		case 3:
			Toast.makeText(context, message + " Bishop (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
			break;
		case 4:
			Toast.makeText(context, message + " King (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
			break;
		case 5:
			Toast.makeText(context, message + " Queen (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
			break;
		case 6:
			Toast.makeText(context, message + " Pawn (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
			break;
		default:
			Toast.makeText(context, message + " Invalid Piece Code (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
			//test
			
		}
	}
	
	private int getPieceParams(View view, int param)//Gets the parameters of each piece <color>:<type>:<xloc>:<yloc>
	{
		return Integer.parseInt(((String) view.getContentDescription()).split(":")[param]);
	}

	private void resetBoard()//Sets the flags on the board to determine what color of piece is where
{
		for (int y = 0 ; y < 8 ; y++){
			for (int x = 0 ; x < 8 ; x++){
				squareArray[y][x].setContentDescription("n");
			}
		}
		for (int x = 0 ; x < 16 ; x ++){
			squareArray[getPieceParams(white[x],3)-1][getPieceParams(white[x],2)-1].setContentDescription("w");
		}
		for (int x = 0 ; x < 16 ; x ++){
			squareArray[getPieceParams(black[x],3)-1][getPieceParams(black[x],2)-1].setContentDescription("b");
		}
	}
}
