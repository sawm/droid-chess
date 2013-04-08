package com.example.droid_chess;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;
import piece.Square;
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
	Square[][] squareArray;
	ImageView[] white;
	ImageView[] black;
	
	public static boolean debugging;
	
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
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.screen);
		Point position = new Point();
		
		///////////////////////////////////////////////////////////////////////
		//Create the board layout
		///////////////////////////////////////////////////////////////////////
		createBoardLayout(width, position, layout);
		setupPieceImageViews();
		displayPieces(width, position, layout);
		///////////////////////////////////////////////////////////////////////
		//Debugging code, flashes an array map to see where pieces are located
		///////////////////////////////////////////////////////////////////////
		resetBoard();

		if (debugging){displayDebugBoard();}
	}
	private void createBoardLayout(int width, Point position, RelativeLayout layout){
		position.x = 0;
		position.y = 0;		
		squareArray = new Square[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if ((x + y) % 2 == 0) {
					squareArray[x][y] = new Square(this, new Point(x,y), "white");
					squareArray[x][y].setImageResource(R.drawable.white);
				} else {
					squareArray[x][y] = new Square(this, new Point(x,y), "black");
					squareArray[x][y].setImageResource(R.drawable.black);
				}
				RelativeLayout.LayoutParams squareParams = new RelativeLayout.LayoutParams(width/8, width/8);
				squareParams.leftMargin = position.x;
				squareParams.topMargin = position.y;
				position.x += width / 8;
				layout.addView(squareArray[x][y], squareParams);
				if(y == 0 || y == 1){
					squareArray[x][y].setState("white");
				}
				if(y == 6 || y == 7){
					squareArray[x][y].setState("black");
				}
			}
			position.x = 0;
			position.y += width / 8;
		} 
	}
	private void setupPieceImageViews(){
		white = new ImageView[16];
		white[0] = new Rook(this, "white", new Point(0,0)); white[0].setImageResource(R.drawable.rook);
		white[0].setContentDescription("1:1:0:0");
		white[1] = new Knight(this, "white", new Point(1,0));	white[1].setImageResource(R.drawable.knight);
		white[1].setContentDescription("1:2:1:0");
		white[2] = new Bishop(this, "white", new Point(2,0));	white[2].setImageResource(R.drawable.bishop);
		white[2].setContentDescription("1:3:2:0");
		white[3] = new King(this, "white", new Point(3,0));	white[3].setImageResource(R.drawable.king);
		white[3].setContentDescription("1:4:3:0");
		white[4] = new Queen(this, "white", new Point(4,0));	white[4].setImageResource(R.drawable.queen);
		white[4].setContentDescription("1:5:3:3");
		white[5] = new Bishop(this, "white", new Point(5,0));	white[5].setImageResource(R.drawable.bishop);
		white[5].setContentDescription("1:3:5:0");
		white[6] = new Knight(this, "white", new Point(6,0));	white[6].setImageResource(R.drawable.knight);
		white[6].setContentDescription("1:2:6:0");
		white[7] = new Rook(this, "white", new Point(7,0)); white[7].setImageResource(R.drawable.rook);
		white[7].setContentDescription("1:1:7:0");

		black = new ImageView[16];
		black[0] = new Rook(this, "black", new Point(0,7)); black[0].setImageResource(R.drawable.rook);
		black[0].setContentDescription("2:1:0:7");
		black[1] = new Knight(this, "black", new Point(1,7));	black[1].setImageResource(R.drawable.knight);
		black[1].setContentDescription("2:2:1:7");
		black[2] = new Bishop(this, "black", new Point(2,7));	black[2].setImageResource(R.drawable.bishop);
		black[2].setContentDescription("2:3:2:7");
		black[3] = new Queen(this, "black", new Point(3,7));	black[3].setImageResource(R.drawable.queen);
		black[3].setContentDescription("2:5:3:7");
		black[4] = new King(this, "black", new Point(4,7));	black[4].setImageResource(R.drawable.king);
		black[4].setContentDescription("2:4:4:7");
		black[5] = new Bishop(this, "black", new Point(5,7));	black[5].setImageResource(R.drawable.bishop);
		black[5].setContentDescription("2:3:5:7");
		black[6] = new Knight(this, "black", new Point(6,7));	black[6].setImageResource(R.drawable.knight);
		black[6].setContentDescription("2:2:6:7");
		black[7] = new Rook(this, "black", new Point(7,7)); black[7].setImageResource(R.drawable.rook);
		black[7].setContentDescription("2:1:7:7");
		
		for (int x = 8; x<16 ; x++){
			white[x] = new Pawn(this, "white", new Point(x-8,1));	white[x].setImageResource(R.drawable.pawn);	
			white[x].setContentDescription("1:6:" + (x-8) + ":1:1");
			black[x] = new Pawn(this, "black", new Point(x-8,6));	black[x].setImageResource(R.drawable.pawn);	
			black[x].setContentDescription("2:6:" + (x-8) + ":6:1");	
		}
		
		for (int x = 0 ; x<16 ; x++){
			white[x].setColorFilter(0x88ffffff,PorterDuff.Mode.SRC_ATOP);
			black[x].setColorFilter(0x88000000,PorterDuff.Mode.SRC_ATOP);
			white[x].setOnClickListener(this);
			black[x].setOnClickListener(this);
			
		}

	}
	private void displayPieces(int width, Point position, RelativeLayout layout){
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

	}
	private void displayDebugBoard(){
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
		view.getMoves(squareArray);
//		String message = "";
//		int duration = Toast.LENGTH_SHORT;
//		//Toast.makeText(context, "" + view.getContentDescription(), duration).show();
//		switch (getPieceParams(view,1)){
//		case 1:
//			resetColorFilter();
//			Toast.makeText(context, message + " Rook (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
//			((Rook) view).getMoves(squareArray);
//			break;
//		case 2:
//			Toast.makeText(context, message + " Knight (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
//			resetColorFilter();
//			((Knight) view).getMoves(squareArray);
//			break;
//		case 3:
//			Toast.makeText(context, message + " Bishop (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
//			resetColorFilter();
//			((Bishop) view).getMoves(squareArray);
//			break;
//		case 4:
//			Toast.makeText(context, message + " King (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
//			resetColorFilter();
//			((King) view).getMoves(squareArray);
//			break;
//		case 5:
//			Toast.makeText(context, message + " Queen (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
//			resetColorFilter();
//			((Queen) view).getMoves(squareArray);
//			break;
//		case 6:
//			resetColorFilter();
//			((Pawn) view).getMoves(squareArray);
//			break;
//		default:
//			Toast.makeText(context, message + " Invalid Piece Code (" + getPieceParams(view,2) + "," + getPieceParams(view,3) + ")", duration).show();
//		}
	}
	
	public void onClick(Piece piece){
		piece.getMoves(squareArray);
	}

	private int getPieceParams(View view, int param)//Gets the parameters of each piece <color>:<type>:<xloc>:<yloc>
	{
		return Integer.parseInt(((String) view.getContentDescription()).split(":")[param]);
	}

	private void resetColorFilter(){
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				squareArray[x][y].setColorFilter(0x00000000, PorterDuff.Mode.SRC_ATOP);
			}
		}
	}
	private void resetBoard()//Sets the flags on the board to determine what color of piece is where
{
		for (int y = 0 ; y < 8 ; y++){
			for (int x = 0 ; x < 8 ; x++){
				squareArray[x][y].setContentDescription("0");
			}
		}
		for (int x = 0 ; x < 16 ; x ++){
			squareArray[getPieceParams(white[x],3)][getPieceParams(white[x],2)].setContentDescription("1");
		}
		for (int x = 0 ; x < 16 ; x ++){
			squareArray[getPieceParams(black[x],3)][getPieceParams(black[x],2)].setContentDescription("2");
		}
	}

}
