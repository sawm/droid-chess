package piece;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

public class King extends Piece {

	public King(Context context, String color, Point position) {
		super(context, color, position);
		// TODO Auto-generated constructor stub
	}

	public King(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public King(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getMoves(Square[][] board, King king, ImageView[] enemy_piece){
		//Toast.makeText(this.getContext(), "Incorrect getMoves() overload", Toast.LENGTH_SHORT).show();
	}
	
	public void getMoves(Square[][] board, ImageView[] enemy_piece, Context context) {
		

		checkTaken(board, enemy_piece);		
		
		if(this.boardPosition.x+1 <= 7 && board[this.boardPosition.x+1][this.boardPosition.y].getState() != this.color){
			if (board[this.boardPosition.x+1][this.boardPosition.y].isTaken() || checkTaken(board, enemy_piece,this.boardPosition.x+1,this.boardPosition.y,this.boardPosition))
				board[this.boardPosition.x+1][this.boardPosition.y].showTaken();
			else
				board[this.boardPosition.x+1][this.boardPosition.y].becomeAvailable(this);			
		}
		
		if(this.boardPosition.x-1 >=0 && board[this.boardPosition.x-1][this.boardPosition.y].getState() != this.color){
			if (board[this.boardPosition.x-1][this.boardPosition.y].isTaken() || checkTaken(board, enemy_piece,this.boardPosition.x-1,this.boardPosition.y,this.boardPosition))
				board[this.boardPosition.x-1][this.boardPosition.y].showTaken();
			else
				board[this.boardPosition.x-1][this.boardPosition.y].becomeAvailable(this);
		}
		if(this.boardPosition.y+1 <= 7 && board[this.boardPosition.x][this.boardPosition.y+1].getState() != this.color){
			if (board[this.boardPosition.x][this.boardPosition.y+1].isTaken() || checkTaken(board, enemy_piece,this.boardPosition.x,this.boardPosition.y+1,this.boardPosition))
				board[this.boardPosition.x][this.boardPosition.y+1].showTaken();
			else
				board[this.boardPosition.x][this.boardPosition.y+1].becomeAvailable(this);
		}
		if(this.boardPosition.y-1 >= 0 && board[this.boardPosition.x][this.boardPosition.y-1].getState() != this.color){
			if (board[this.boardPosition.x][this.boardPosition.y-1].isTaken() || checkTaken(board, enemy_piece,this.boardPosition.x,this.boardPosition.y-1,this.boardPosition))
				board[this.boardPosition.x][this.boardPosition.y-1].showTaken();
			else
				board[this.boardPosition.x][this.boardPosition.y-1].becomeAvailable(this);
		}
		
		if(this.boardPosition.y-1 >= 0 && this.boardPosition.x-1 >=0 && board[this.boardPosition.x-1][this.boardPosition.y-1].getState() != this.color){
			if (board[this.boardPosition.x-1][this.boardPosition.y-1].isTaken() || checkTaken(board, enemy_piece,this.boardPosition.x-1,this.boardPosition.y-1,this.boardPosition))
				board[this.boardPosition.x-1][this.boardPosition.y-1].showTaken();
			else
				board[this.boardPosition.x-1][this.boardPosition.y-1].becomeAvailable(this);
		}
		if(this.boardPosition.y-1 >= 0 && this.boardPosition.x+1 <=7 && board[this.boardPosition.x+1][this.boardPosition.y-1].getState() != this.color){
			if (board[this.boardPosition.x+1][this.boardPosition.y-1].isTaken() || checkTaken(board, enemy_piece,this.boardPosition.x+1,this.boardPosition.y-1,this.boardPosition))
				board[this.boardPosition.x+1][this.boardPosition.y-1].showTaken();
			else
				board[this.boardPosition.x+1][this.boardPosition.y-1].becomeAvailable(this);
		}
		if(this.boardPosition.y+1 <= 7 && this.boardPosition.x-1 >=0 && board[this.boardPosition.x-1][this.boardPosition.y+1].getState() != this.color){
			if (board[this.boardPosition.x-1][this.boardPosition.y+1].isTaken() || checkTaken(board, enemy_piece,this.boardPosition.x-1,this.boardPosition.y+1,this.boardPosition))
				board[this.boardPosition.x-1][this.boardPosition.y+1].showTaken();
			else
				board[this.boardPosition.x-1][this.boardPosition.y+1].becomeAvailable(this);
		}
		if(this.boardPosition.y+1 <= 7 && this.boardPosition.x+1 <=7 && board[this.boardPosition.x+1][this.boardPosition.y+1].getState() != this.color){
			if (board[this.boardPosition.x+1][this.boardPosition.y+1].isTaken() || checkTaken(board, enemy_piece,this.boardPosition.x+1,this.boardPosition.y+1,this.boardPosition))
				board[this.boardPosition.x+1][this.boardPosition.y+1].showTaken();
			else
				board[this.boardPosition.x+1][this.boardPosition.y+1].becomeAvailable(this);
		}


	}

	private boolean checkTaken(Square[][] board, ImageView[] enemy_piece){		
		for (int z = 0 ; z < 16; z ++){
			if (((Piece) enemy_piece[z]).isActive()){
				if (enemy_piece[z] instanceof Pawn)
					((Pawn) enemy_piece[z]).getMoves(board,this,"kingtest");
				else	
					((Piece) enemy_piece[z]).getMoves(board, this, enemy_piece);
			}
			for (int y = 0; y < 8; y ++){
				for (int x = 0; x < 8; x++){
					if (board[x][y].isAvailable()){
						board[x][y].setAvailable(false);
						board[x][y].setTaken(true);
						board[x][y].setColorFilter(0x00000000,PorterDuff.Mode.SRC_ATOP);
					}
				}
			}
		}
		return true;
	}
	
	public boolean checkTaken(Square[][] board, ImageView[] enemy_piece,int i,int j, Point actualLocation){

		if (i < 0 || i > 7)
			return true;
		if (j < 0 || j > 7)
			return true;
		
		Square testboard[][] = new Square[8][8];
		for (int y = 0; y < 8; y ++){
			for (int x = 0; x < 8; x++){
				testboard[x][y] = new Square(board[x][y]);
			}
		}
		
		testboard[actualLocation.x][actualLocation.y].setState("empty");
		testboard[i][j].setState(this.color);
//		
//		
//		String message1 = "";
//		String message2 = "";
//		for (int y = 0; y < 8; y ++){
//			for (int x = 0; x < 8; x++){
//				message1 += ""+testboard[x][y].getState();
//				message2 += ""+board[x][y].getState();
//			}
//			message1+="\n";
//			message2+="\n";
//		}
//		
//		Toast.makeText(context, message1, Toast.LENGTH_SHORT).show();
//		Toast.makeText(context, message2, Toast.LENGTH_SHORT).show();
		
		for (int z = 0 ; z < 16; z ++){
			if (((Piece) enemy_piece[z]).isActive()){
				if (enemy_piece[z] instanceof Pawn)
					((Pawn) enemy_piece[z]).getMoves(testboard,this,"kingtest");
				else	
					((Piece) enemy_piece[z]).getMoves(testboard,this,enemy_piece);
			}
			for (int y = 0; y < 8; y ++){
				for (int x = 0; x < 8; x++){
					if (testboard[x][y].isAvailable()){
						testboard[x][y].setAvailable(false);
						testboard[x][y].setTaken(true);
						testboard[x][y].setColorFilter(0x00000000,PorterDuff.Mode.SRC_ATOP);
					}
				}
			}
		}
		return testboard[i][j].isTaken();
	}
}
