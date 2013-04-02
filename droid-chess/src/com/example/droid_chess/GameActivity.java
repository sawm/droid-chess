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
		white[0].setContentDescription("1:1:1:1");
		white[1] = new ImageView(this);	white[1].setImageResource(R.drawable.knight);
		white[1].setContentDescription("1:2:2:1");
		white[2] = new ImageView(this);	white[2].setImageResource(R.drawable.bishop);
		white[2].setContentDescription("1:3:3:1");
		white[3] = new ImageView(this);	white[3].setImageResource(R.drawable.king);
		white[3].setContentDescription("1:4:4:1");
		white[4] = new ImageView(this);	white[4].setImageResource(R.drawable.queen);
		white[4].setContentDescription("1:5:5:1");
		white[5] = new ImageView(this);	white[5].setImageResource(R.drawable.bishop);
		white[5].setContentDescription("1:3:6:1");
		white[6] = new ImageView(this);	white[6].setImageResource(R.drawable.knight);
		white[6].setContentDescription("1:2:7:1");
		white[7] = new ImageView(this); white[7].setImageResource(R.drawable.rook);
		white[7].setContentDescription("1:1:8:1");

		black = new ImageView[16];
		black[0] = new ImageView(this); black[0].setImageResource(R.drawable.rook);
		black[0].setContentDescription("2:1:1:8");
		black[1] = new ImageView(this);	black[1].setImageResource(R.drawable.knight);
		black[1].setContentDescription("2:2:2:8");
		black[2] = new ImageView(this);	black[2].setImageResource(R.drawable.bishop);
		black[2].setContentDescription("2:3:3:8");
		black[3] = new ImageView(this);	black[3].setImageResource(R.drawable.queen);
		black[3].setContentDescription("2:5:4:8");
		black[4] = new ImageView(this);	black[4].setImageResource(R.drawable.king);
		black[4].setContentDescription("2:4:5:8");
		black[5] = new ImageView(this);	black[5].setImageResource(R.drawable.bishop);
		black[5].setContentDescription("2:3:6:8");
		black[6] = new ImageView(this);	black[6].setImageResource(R.drawable.knight);
		black[6].setContentDescription("2:2:7:8");
		black[7] = new ImageView(this); black[7].setImageResource(R.drawable.rook);
		black[7].setContentDescription("2:1:8:8");
		
		for (int x = 8; x<16 ; x++){
			white[x] = new ImageView(this);	white[x].setImageResource(R.drawable.pawn);	
			white[x].setContentDescription("1:6:" + (x-7) + ":2");
			black[x] = new ImageView(this);	black[x].setImageResource(R.drawable.pawn);	
			black[x].setContentDescription("2:6:" + (x-7) + ":7");	
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
		
		
			pieceLayout = new char[][] {{'n','n','n','n','n','n','n','n'},
					{'n','n','n','n','n','n','n','n'},
					{'n','n','n','n','n','n','n','n'},
					{'n','n','n','n','n','n','n','n'},
					{'n','n','n','n','n','n','n','n'},
					{'n','n','n','n','n','n','n','n'},
					{'n','n','n','n','n','n','n','n'},
					{'n','n','n','n','n','n','n','n'}};
					
//			for (int y = 0 ; y < 8 ; y++){
//				for (int x = 0 ; x < 8 ; x++){
//					pieceLayout[y][x] = 'n';
//				}
//			}
			for (int x = 0 ; x < 16 ; x ++){
				pieceLayout[getPieceParams(white[x],3)-1][getPieceParams(white[x],2)-1]='w';
			}
			for (int x = 0 ; x < 16 ; x ++){
				pieceLayout[getPieceParams(black[x],3)-1][getPieceParams(black[x],2)-1]='w';
			}

Toast.makeText(getApplicationContext(), "" + pieceLayout[0][0] + pieceLayout[0][1] + pieceLayout[0][2] + pieceLayout[0][3] + pieceLayout[0][4] + pieceLayout[0][5] + pieceLayout[0][6] + pieceLayout[0][7] + "\n" +
pieceLayout[1][0] + pieceLayout[1][1] + pieceLayout[1][2] + pieceLayout[1][3] + pieceLayout[1][4] + pieceLayout[1][5] + pieceLayout[1][6] + pieceLayout[1][7] + "\n" +
pieceLayout[2][0] + pieceLayout[2][1] + pieceLayout[2][2] + pieceLayout[2][3] + pieceLayout[2][4] + pieceLayout[2][5] + pieceLayout[2][6] + pieceLayout[2][7] + "\n" +
pieceLayout[3][0] + pieceLayout[3][1] + pieceLayout[3][2] + pieceLayout[3][3] + pieceLayout[3][4] + pieceLayout[3][5] + pieceLayout[3][6] + pieceLayout[3][7] + "\n" +
pieceLayout[4][0] + pieceLayout[4][1] + pieceLayout[4][2] + pieceLayout[4][3] + pieceLayout[4][4] + pieceLayout[4][5] + pieceLayout[4][6] + pieceLayout[4][7] + "\n" +
pieceLayout[5][0] + pieceLayout[5][1] + pieceLayout[5][2] + pieceLayout[5][3] + pieceLayout[5][4] + pieceLayout[5][5] + pieceLayout[5][6] + pieceLayout[5][7] + "\n" +
pieceLayout[6][0] + pieceLayout[6][1] + pieceLayout[6][2] + pieceLayout[6][3] + pieceLayout[6][4] + pieceLayout[6][5] + pieceLayout[6][6] + pieceLayout[6][7] + "\n" +
pieceLayout[7][0] + pieceLayout[7][1] + pieceLayout[7][2] + pieceLayout[7][3] + pieceLayout[7][4] + pieceLayout[7][5] + pieceLayout[7][6] + pieceLayout[7][7]
,Toast.LENGTH_LONG).show();		setupActionBar();
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
			
			
		}
	}
	
	private int getPieceParams(View view, int param){
		return Integer.parseInt(((String) view.getContentDescription()).split(":")[param]);
	}
}
