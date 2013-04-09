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
import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GameActivity extends Activity {
	private Square[][] squareArray;
	private ImageView[] white;
	private ImageView[] black;
	private Piece activePiece;

	OnClickListener whitePieceListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			resetColorFilter();
			resetAvailableMoves();
			activePiece = (Piece) view;
			for (int x=0; x < 16 ; x ++){
				black[x].setOnClickListener(squareListener);
			}
			((Piece) view).getMoves(squareArray);
		}
	};
	
	OnClickListener blackPieceListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			resetColorFilter();
			resetAvailableMoves();
			activePiece = (Piece) view;
			for (int x=0; x < 16 ; x ++){
				white[x].setOnClickListener(squareListener);
			}
			((Piece) view).getMoves(squareArray);
		}
	};
	
	OnClickListener squareListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Square square;
			if ((view instanceof Piece) && !(squareArray[((Piece) view).getBoardPosition().x][((Piece) view).getBoardPosition().y].isAvailable())){
				resetColorFilter();
				resetAvailableMoves();
				resetOnClicks();
				return;
			}

			if ((view instanceof Piece) && (squareArray[((Piece) view).getBoardPosition().x][((Piece) view).getBoardPosition().y].isAvailable())){
				square = (Square) squareArray[((Piece) view).getBoardPosition().x][((Piece) view).getBoardPosition().y];
				((Piece) view).setActive(false);
				((Piece) view).setVisibility(View.GONE);
				
			} else {
				square = (Square) view;
			}
			
			if(square.isAvailable()){
				Display display = getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int width = size.x;
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width / 8, width / 8);
				
				Context context = getApplicationContext();
				CharSequence text = "Hello toast!";
				int duration = Toast.LENGTH_SHORT;

			//	Toast toast = Toast.makeText(context, "" + activePiece.getBoardPosition().x + "," + activePiece.getBoardPosition().y, duration);
			//	toast.show();
				
				try{
					squareArray[activePiece.getBoardPosition().x][activePiece.getBoardPosition().y].setState("empty");
					if (view instanceof Piece){
						activePiece.setBoardPosition(((Piece) view).getBoardPosition());
						squareArray[((Piece) view).getBoardPosition().x][((Piece) view).getBoardPosition().y].setState(activePiece.getColor());
					} else {
						activePiece.setBoardPosition(((Square) view).getPosition());
						((Square) view).setState(activePiece.getColor());						
					}
					
				}
				catch (Exception e) {Toast toast = Toast.makeText(context, ""+ e, duration);
						toast.show();}
			
				lp.topMargin = square.getPosition().y * (width/8);
				lp.leftMargin = square.getPosition().x * (width/8);
				activePiece.setLayoutParams(lp);
				activePiece.setBoardPosition(square.getPosition());
				resetColorFilter();
				resetAvailableMoves();
				resetOnClicks();
			}
			else {
				resetColorFilter();
				resetAvailableMoves();
				resetOnClicks();
			}
		}
	};

	public static boolean debugging;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		// Get width of the screen to properly calculate board size
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.screen);
		Point position = new Point();

//		// Create the board layout
		createBoardLayout(width, position, layout);
		setupPieceImageViews();
		displayPieces(width, position, layout);
	}

	private void createBoardLayout(int width, Point position,
			RelativeLayout layout) {
		position.x = 0;
		position.y = 0;
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
		white = new ImageView[16];
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

		black = new ImageView[16];
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

	private void displayPieces(int width, Point position, RelativeLayout layout) {
		position.x = 0;
		position.y = 0;
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
			}
		}
	}

	private void resetOnClicks(){
		for (int x=0; x < 16 ; x ++){
			black[x].setOnClickListener(blackPieceListener);
			white[x].setOnClickListener(whitePieceListener);
		}

	}
	
}
