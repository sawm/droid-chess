package com.example.droid_chess;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;
import piece.Square;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GameActivity extends Activity {
	private Square[][] squareArray;
	private Piece[] white;
	private Piece[] black;
	private Piece activePiece;
	
	OnClickListener whitePieceListener = new OnClickListener() {
		@Override
		public void onClick(View view)
		{
			resetColorFilter();
			resetAvailableMoves();
			activePiece = (Piece) view;
	
			//Disable the onclick listeners of the opposing pieaces
			for (int x=0; x < 16 ; x ++) {
			black[x].setOnClickListener(squareListener);
			}
	
			//Get the available moves for the piece
			if (view instanceof King){
				((King) view).getMoves(squareArray,black,!((King)white[3]).hasMoved() && !((Rook)white[0]).hasMoved());
				if (squareArray[0][0].isAvailable())
					white[0].setOnClickListener(squareListener);
			}
			else
				((Piece) view).getMoves(squareArray);
				
			//Test to see if any available moves would put the king in check
			for (int y = 0; y < 8; y ++) {
				for (int x = 0; x < 8; x++) {
					if (squareArray[x][y].isAvailable()) {
						//Store temporary variables
						String tmp = squareArray[x][y].getState();
						squareArray[x][y].setState(activePiece.getColor());
						squareArray[activePiece.getBoardPosition().x][activePiece.getBoardPosition().y].setState("empty");
						Piece king = (King) white[3];
	
						//Temporarily set the active value of any pieces that can be taken as false (This prevents false positives)
						for (int z = 0 ; z < 16; z ++) {
							if (((Piece) black[z]).getBoardPosition().x == x && ((Piece) black[z]).getBoardPosition().y == y)
							((Piece) black[z]).setActive(false);
						}
						
						//Check if the possible move would put the king in check
						if (!(view instanceof King) && ((King) king).checkTaken(squareArray, black, new Point(king.getBoardPosition().x, king.getBoardPosition().y), king.getBoardPosition())) {
							squareArray[x][y].setAvailable(false);
							squareArray[x][y].showTaken();
						}
						
						//Change the active value of any piece that can be taken back to true
						for (int z = 0 ; z < 16; z ++) {
							if (!((Piece) black[z]).getBoardPosition().equals(new Point(-1,-1)) && !((Piece) black[z]).isActive()) {
							((Piece) black[z]).setActive(true);
							}
						}
						
						//Restore temporary variables
						squareArray[x][y].setState(tmp);
						squareArray[activePiece.getBoardPosition().x][activePiece.getBoardPosition().y].setState(activePiece.getColor());
					}
				}
			}
		}
	};
	
	OnClickListener blackPieceListener = new OnClickListener() {
		@Override
		public void onClick(View view)
		{
			resetColorFilter();
			resetAvailableMoves();
			activePiece = (Piece) view;
	
			//Disable the onclick listeners of the opposing pieaces
			for (int x=0; x < 16 ; x ++) {
			white[x].setOnClickListener(squareListener);
			}
	
			//Get the available moves for the piece
			if (view instanceof King){
				((King) view).getMoves(squareArray,white,!((King)black[4]).hasMoved() && !((Rook)black[7]).hasMoved());
				if (squareArray[7][7].isAvailable())
				black[7].setOnClickListener(squareListener);

			}
			else
				((Piece) view).getMoves(squareArray);
	
			//Test to see if any available moves would put the king in check
			for (int y = 0; y < 8; y ++) {
				for (int x = 0; x < 8; x++) {
					if (squareArray[x][y].isAvailable()) {
						//Store temporary variables
						String tmp = squareArray[x][y].getState();
						squareArray[x][y].setState(activePiece.getColor());
						squareArray[activePiece.getBoardPosition().x][activePiece.getBoardPosition().y].setState("empty");
						Piece king = (King) black[4];
	
						//Temporarily set the active value of any pieces that can be taken as false (This prevents false positives)
						for (int z = 0 ; z < 16; z ++) {
							if (((Piece) white[z]).getBoardPosition().x == x && ((Piece) white[z]).getBoardPosition().y == y)
							((Piece) white[z]).setActive(false);
						}
						
						//Check if the possible move would put the king in check
						if (!(view instanceof King) && ((King) king).checkTaken(squareArray, white, new Point(king.getBoardPosition().x, king.getBoardPosition().y), king.getBoardPosition())) {
							squareArray[x][y].setAvailable(false);
							squareArray[x][y].showTaken();
						}
						
						//Change the active value of any piece that can be taken back to true
						for (int z = 0 ; z < 16; z ++) {
							if (!((Piece) white[z]).getBoardPosition().equals(new Point(-1,-1)) && !((Piece) white[z]).isActive()) {
							((Piece) white[z]).setActive(true);
							}
						}
						
						//Restore temporary variables
						squareArray[x][y].setState(tmp);
						squareArray[activePiece.getBoardPosition().x][activePiece.getBoardPosition().y].setState(activePiece.getColor());
					}
				}
			}
		}
	};
	
	OnClickListener squareListener = new OnClickListener() {
		@Override
		public void onClick(View view) 
		{
			Square square;
									
			//If the clicked item is a piece, and it is not on an available spot, reset movement and deselect piece
			if ((view instanceof Piece) && !(squareArray[((Piece) view).getBoardPosition().x][((Piece) view).getBoardPosition().y].isAvailable())) {
				resetColorFilter();
				resetAvailableMoves();
				resetOnClicks();
				return;
			}
			
			//If clicked piece is on an available spot, and has opposing piece on it, reference spot under piece, prepare to remove piece.
			if ((view instanceof Piece) && (squareArray[((Piece) view).getBoardPosition().x][((Piece) view).getBoardPosition().y].isAvailable())) {
			square = (Square) squareArray[((Piece) view).getBoardPosition().x][((Piece) view).getBoardPosition().y];
			} else { //if click spot is available and empty, reference spot
			square = (Square) view;
			}
			
			//If the clicked square would put the king in check....
			if(square.isTaken()){
				Toast.makeText(getApplicationContext(), "This move would place your king in check", Toast.LENGTH_SHORT).show();
				resetColorFilter();
				resetAvailableMoves();
				resetOnClicks();
			}

			//If the clicked item is a square, and it is not an available spot, reset movement
			if(!square.isAvailable()) {
				resetColorFilter();
				resetAvailableMoves();
				resetOnClicks();
			}
			
			//If the the second above condition is true, in either case, occupied or empty, process movement.
			if(square.isAvailable())
			{
				//Get the screen information to redraw the moved piece
				Display display = getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int width = size.x;
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width / 8, width / 8);


				//Move the piece in the data structures and arrays that keep track of the board
				squareArray[activePiece.getBoardPosition().x][activePiece.getBoardPosition().y].setState("empty");
				if (view instanceof Rook && ((Piece)view).getColor() == activePiece.getColor()){
					RelativeLayout.LayoutParams mp = new RelativeLayout.LayoutParams(width / 8, width / 8);
					Point p = new Point(activePiece.getBoardPosition());
					activePiece.setBoardPosition(((Piece) view).getBoardPosition());
					((Piece)view).setBoardPosition(p);
					squareArray[((Piece) view).getBoardPosition().x][((Piece) view).getBoardPosition().y].setState(activePiece.getColor());
					squareArray[p.x][p.y].setState(((Piece)view).getColor());
					
					mp.topMargin = p.y * (width/8);
					mp.leftMargin = p.x * (width/8);
					((Piece)view).setLayoutParams(mp);
					((Piece)view).setBoardPosition(p);
				} else if (view instanceof Piece) {					
					activePiece.setBoardPosition(((Piece) view).getBoardPosition());
					squareArray[((Piece) view).getBoardPosition().x][((Piece) view).getBoardPosition().y].setState(activePiece.getColor());

					((Piece) view).setActive(false);
					((Piece) view).setVisibility(View.GONE);
					((Piece) view).setBoardPosition(new Point(-1,-1));

				} else {
					activePiece.setBoardPosition(((Square) view).getPosition());
					((Square) view).setState(activePiece.getColor());						
				}

				//Move the piece on the screen
				lp.topMargin = square.getPosition().y * (width/8);
				lp.leftMargin = square.getPosition().x * (width/8);
				activePiece.setLayoutParams(lp);
				activePiece.setBoardPosition(square.getPosition());
				if (activePiece instanceof Pawn)
					((Pawn) activePiece).moved();
				if (activePiece instanceof King)
					((King) activePiece).moved();
				if (activePiece instanceof Rook)
					((Rook) activePiece).moved();
				
				
				//Refresh the data structures that keep track of the board, test for checks and pawn promotion.
				if (activePiece instanceofPawn)
				resetColorFilter();
				resetAvailableMoves();
				resetOnClicks();
				if (view instanceof King)
					Toast.makeText(getApplicationContext(), "Winner!", Toast.LENGTH_SHORT).show(); //TODO this must be changed quit the game and declare an actual winner
				else
					testForChecks();
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		if (getResources().getString(R.string.debugging) == "true")
			{((Button) findViewById(R.id.debugButton)).setVisibility(View.GONE);}
		
		
		// Get width of the screen to properly calculate board size
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.screen);

		// Create the board layout
		createBoardLayout(width, layout);
		setupPieceImageViews();
		displayPieces(width, layout);
	}

	private void createBoardLayout(int width,RelativeLayout layout) {
		Point position = new Point(0,0);
		squareArray = new Square[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if ((x + y) % 2 == 0) {
					squareArray[x][y] = new Square(this, new Point(x, y),
							"white");
					squareArray[x][y].setImageResource(R.drawable.white);
				} else {
					squareArray[x][y] = new Square(this, new Point(x, y),
							"black");
					squareArray[x][y].setImageResource(R.drawable.black);
				}
				RelativeLayout.LayoutParams squareParams = new RelativeLayout.LayoutParams(
						width / 8, width / 8);
				squareParams.leftMargin = position.x;
				squareParams.topMargin = position.y;
				position.x += width / 8;
				layout.addView(squareArray[x][y], squareParams);
				squareArray[x][y].setOnClickListener(squareListener);
				if (y == 0 || y == 1) {
					squareArray[x][y].setState("white");
				}
				if (y == 6 || y == 7) {
					squareArray[x][y].setState("black");
				}
			}
			position.x = 0;
			position.y += width / 8;
		}
	}

	private void setupPieceImageViews() {
		white = new Piece[16];
		white[0] = new Rook(this, "white", new Point(0, 0));
		white[0].setImageResource(R.drawable.rook);
		white[1] = new Knight(this, "white", new Point(1, 0));
		white[1].setImageResource(R.drawable.knight);
		white[2] = new Bishop(this, "white", new Point(2, 0));
		white[2].setImageResource(R.drawable.bishop);
		white[3] = new King(this, "white", new Point(3, 0));
		white[3].setImageResource(R.drawable.king);
		white[4] = new Queen(this, "white", new Point(4, 0));
		white[4].setImageResource(R.drawable.queen);
		white[5] = new Bishop(this, "white", new Point(5, 0));
		white[5].setImageResource(R.drawable.bishop);
		white[6] = new Knight(this, "white", new Point(6, 0));
		white[6].setImageResource(R.drawable.knight);
		white[7] = new Rook(this, "white", new Point(7, 0));
		white[7].setImageResource(R.drawable.rook);

		black = new Piece[16];
		black[0] = new Rook(this, "black", new Point(0, 7));
		black[0].setImageResource(R.drawable.rook);
		black[1] = new Knight(this, "black", new Point(1, 7));
		black[1].setImageResource(R.drawable.knight);
		black[2] = new Bishop(this, "black", new Point(2, 7));
		black[2].setImageResource(R.drawable.bishop);
		black[3] = new Queen(this, "black", new Point(3, 7));
		black[3].setImageResource(R.drawable.queen);
		black[4] = new King(this, "black", new Point(4, 7));
		black[4].setImageResource(R.drawable.king);
		black[5] = new Bishop(this, "black", new Point(5, 7));
		black[5].setImageResource(R.drawable.bishop);
		black[6] = new Knight(this, "black", new Point(6, 7));
		black[6].setImageResource(R.drawable.knight);
		black[7] = new Rook(this, "black", new Point(7, 7));
		black[7].setImageResource(R.drawable.rook);

		for (int x = 8; x < 16; x++) {
			white[x] = new Pawn(this, "white", new Point(x - 8, 1));
			white[x].setImageResource(R.drawable.pawn);
			black[x] = new Pawn(this, "black", new Point(x - 8, 6));
			black[x].setImageResource(R.drawable.pawn);
		}

		for (int x = 0; x < 16; x++) {
			white[x].setColorFilter(0x88ffffff, PorterDuff.Mode.SRC_ATOP);
			black[x].setColorFilter(0x88000000, PorterDuff.Mode.SRC_ATOP);
			white[x].setOnClickListener(whitePieceListener);
			black[x].setOnClickListener(blackPieceListener);

		}

	}

	private void displayPieces(int width, RelativeLayout layout) {
		Point position = new Point(0,0);
		for (int x = 0; x < 16; x++) {
			RelativeLayout.LayoutParams pieceParams = new RelativeLayout.LayoutParams(
					width / 8, width / 8);
			pieceParams.leftMargin = position.x;
			pieceParams.topMargin = position.y;
			position.x = (width / 8) * ((x + 1) % 8);
			position.y = (width / 8) * ((int) Math.floor((x + 1) / 8));
			layout.addView(white[x], pieceParams);
		}

		position.x = 0;
		position.y = 7 * (width / 8);
		for (int x = 0; x < 16; x++) {
			RelativeLayout.LayoutParams pieceParams = new RelativeLayout.LayoutParams(
					width / 8, width / 8);
			pieceParams.leftMargin = position.x;
			pieceParams.topMargin = position.y;
			position.x = (width / 8) * ((x + 1) % 8);
			position.y = (7 * (width / 8))
					- ((width / 8) * ((int) Math.floor((x + 1) / 8)));
			layout.addView(black[x], pieceParams);
		}

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
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

	private void resetColorFilter() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				squareArray[x][y].setColorFilter(0x00000000,
						PorterDuff.Mode.SRC_ATOP);
			}
		}
	}
	
	private void resetAvailableMoves() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				squareArray[x][y].setAvailable(false);
				squareArray[x][y].setTaken(false);
			}
		}
	}
	
	private void resetOnClicks(){
		for (int x=0; x < 16 ; x ++){
			black[x].setOnClickListener(blackPieceListener);
			white[x].setOnClickListener(whitePieceListener);
		}

	}
	
	private void testForChecks(){
		Piece king = ((King) black[4]);
		int kingx = ((Piece) black[4]).getBoardPosition().x;
		int kingy = ((Piece) black[4]).getBoardPosition().y;

		if (((King) king).checkTaken(squareArray, white, new Point(kingx, kingy), king.getBoardPosition())){
			if (((King) king).checkTaken(squareArray, white, new Point(kingx+1, kingy), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, white, new Point(kingx-1, kingy), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, white, new Point(kingx, kingy+1), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, white, new Point(kingx, kingy-1), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, white, new Point(kingx+1, kingy+1), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, white, new Point(kingx+1, kingy-1), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, white, new Point(kingx-1, kingy+1), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, white, new Point(kingx-1, kingy-1), king.getBoardPosition()))
				Toast.makeText(getApplicationContext(), "The black king is in checkmate+!", Toast.LENGTH_SHORT).show();
			else	
				Toast.makeText(getApplicationContext(), "The black king is in check!", Toast.LENGTH_SHORT).show();
		}

		king = ((King) white[3]);
		kingx = ((Piece) white[3]).getBoardPosition().x;
		kingy = ((Piece) white[3]).getBoardPosition().y;

		if (((King) king).checkTaken(squareArray, black, new Point(kingx, kingy), king.getBoardPosition())){
			if (((King) king).checkTaken(squareArray, black, new Point(kingx+1, kingy), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, black, new Point(kingx-1, kingy), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, black, new Point(kingx, kingy+1), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, black, new Point(kingx, kingy-1), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, black, new Point(kingx+1, kingy+1), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, black, new Point(kingx+1, kingy-1), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, black, new Point(kingx-1, kingy+1), king.getBoardPosition())
				&& ((King) king).checkTaken(squareArray, black, new Point(kingx-1, kingy-1), king.getBoardPosition()))
				Toast.makeText(getApplicationContext(), "The white king is in checkmate+!", Toast.LENGTH_SHORT).show();
			else	
				Toast.makeText(getApplicationContext(), "The white king is in check!", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void displayDebug(View view){
		String message = "";
		/*This is a collection of debugging methods I've created to help in managing the game. If They are activated
		 * by the debug button on the main screen, which is only viewable when the debug variable is set to true.*/

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Code to create an alert and display it.
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	    final CharSequence[] items = {"Info", "Rename", "Delete"};
//
//	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//	    builder.setTitle("Options for test");
//	    builder.setItems(items, new DialogInterface.OnClickListener() {
//	        public void onClick(DialogInterface dialog, int item) {
//	            Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
//	        }
//	    }).show();		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Code to draw a color map
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		for (int y = 0; y < 8; y ++){
//			for (int x = 0; x < 8; x++){
//				if (squareArray[x][y].isTaken())
//					message+= "1";
//				else if (!squareArray[x][y].isTaken())
//					message+= "2";
//				else
//					message+= "0";
//				
//			}
//			message += "\n";
//		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Code to draw a map of active pieces
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		for (int y = 0; y < 8; y ++){
//			for (int x = 0; x < 8; x++){
//				for (int z = 0; z < 16 ; z++){
//					if (((Piece)white[z]).getBoardPosition().equals(new Point(x,y)) && ((Piece)white[z]).isActive()){
//						message += "" + 1;
//						break;
//					} else 
//					if (((Piece)black[z]).getBoardPosition().equals(new Point(x,y)) && ((Piece)black[z]).isActive()){
//						message += "" + 2;
//						break;
//					}
//					else if (z == 15)
//						message += "" + 0;
//				}
//			}
//			message += "\n";
//		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
	
}
