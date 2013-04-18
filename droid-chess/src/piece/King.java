package piece;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

public class King extends Piece {
	private boolean firstMove = true;

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
	public void getMoves(Square[][] board){
		//Toast.makeText(this.getContext(), "Incorrect getMoves() overload", Toast.LENGTH_SHORT).show();
	}
	
	public void moved(){
		firstMove = false;
	}
	
	public boolean hasMoved(){
		return !firstMove;
	}
	
	public void getMoves(Square[][] board , ImageView[] enemy_piece, Boolean castlable) {
		
		if (castlable) {
			if (this.color == "white"){					
				if (!checkTaken(board, enemy_piece,new Point(0,0),this.boardPosition)){
					board[0][0].becomeAvailable(this);
					board[0][0].castle();
				}
				else
					board[0][0].showTaken();
			}
			if (this.color == "black"){
				if (!checkTaken(board, enemy_piece,new Point(7,7),this.boardPosition)){
					board[7][7].becomeAvailable(this);
					board[7][7].castle();
				}
				else
					board[7][7].showTaken();
			}
			
		}

		if(this.boardPosition.x+1 <= 7 && board[this.boardPosition.x+1][this.boardPosition.y].getState() != this.color){
			if (board[this.boardPosition.x+1][this.boardPosition.y].isTaken() || checkTaken(board, enemy_piece,new Point(this.boardPosition.x+1,this.boardPosition.y),this.boardPosition))
				board[this.boardPosition.x+1][this.boardPosition.y].showTaken();
			else
				board[this.boardPosition.x+1][this.boardPosition.y].becomeAvailable(this);			
		}
		
		if(this.boardPosition.x-1 >=0 && board[this.boardPosition.x-1][this.boardPosition.y].getState() != this.color){
			if (board[this.boardPosition.x-1][this.boardPosition.y].isTaken() || checkTaken(board, enemy_piece,new Point(this.boardPosition.x-1,this.boardPosition.y),this.boardPosition))
				board[this.boardPosition.x-1][this.boardPosition.y].showTaken();
			else
				board[this.boardPosition.x-1][this.boardPosition.y].becomeAvailable(this);
		}
		if(this.boardPosition.y+1 <= 7 && board[this.boardPosition.x][this.boardPosition.y+1].getState() != this.color){
			if (board[this.boardPosition.x][this.boardPosition.y+1].isTaken() || checkTaken(board, enemy_piece,new Point(this.boardPosition.x,this.boardPosition.y+1),this.boardPosition))
				board[this.boardPosition.x][this.boardPosition.y+1].showTaken();
			else
				board[this.boardPosition.x][this.boardPosition.y+1].becomeAvailable(this);
		}
		if(this.boardPosition.y-1 >= 0 && board[this.boardPosition.x][this.boardPosition.y-1].getState() != this.color){
			if (board[this.boardPosition.x][this.boardPosition.y-1].isTaken() || checkTaken(board, enemy_piece,new Point(this.boardPosition.x,this.boardPosition.y-1),this.boardPosition))
				board[this.boardPosition.x][this.boardPosition.y-1].showTaken();
			else
				board[this.boardPosition.x][this.boardPosition.y-1].becomeAvailable(this);
		}
		
		if(this.boardPosition.y-1 >= 0 && this.boardPosition.x-1 >=0 && board[this.boardPosition.x-1][this.boardPosition.y-1].getState() != this.color){
			if (board[this.boardPosition.x-1][this.boardPosition.y-1].isTaken() || checkTaken(board, enemy_piece,new Point(this.boardPosition.x-1,this.boardPosition.y-1),this.boardPosition))
				board[this.boardPosition.x-1][this.boardPosition.y-1].showTaken();
			else
				board[this.boardPosition.x-1][this.boardPosition.y-1].becomeAvailable(this);
		}
		if(this.boardPosition.y-1 >= 0 && this.boardPosition.x+1 <=7 && board[this.boardPosition.x+1][this.boardPosition.y-1].getState() != this.color){
			if (board[this.boardPosition.x+1][this.boardPosition.y-1].isTaken() || checkTaken(board, enemy_piece,new Point(this.boardPosition.x+1,this.boardPosition.y-1),this.boardPosition))
				board[this.boardPosition.x+1][this.boardPosition.y-1].showTaken();
			else
				board[this.boardPosition.x+1][this.boardPosition.y-1].becomeAvailable(this);
		}
		if(this.boardPosition.y+1 <= 7 && this.boardPosition.x-1 >=0 && board[this.boardPosition.x-1][this.boardPosition.y+1].getState() != this.color){
			if (board[this.boardPosition.x-1][this.boardPosition.y+1].isTaken() || checkTaken(board, enemy_piece,new Point(this.boardPosition.x-1,this.boardPosition.y+1),this.boardPosition))
				board[this.boardPosition.x-1][this.boardPosition.y+1].showTaken();
			else
				board[this.boardPosition.x-1][this.boardPosition.y+1].becomeAvailable(this);
		}
		if(this.boardPosition.y+1 <= 7 && this.boardPosition.x+1 <=7 && board[this.boardPosition.x+1][this.boardPosition.y+1].getState() != this.color){
			if (board[this.boardPosition.x+1][this.boardPosition.y+1].isTaken() || checkTaken(board, enemy_piece,new Point(this.boardPosition.x+1,this.boardPosition.y+1),this.boardPosition))
				board[this.boardPosition.x+1][this.boardPosition.y+1].showTaken();
			else
				board[this.boardPosition.x+1][this.boardPosition.y+1].becomeAvailable(this);
		}


	}

	
	public boolean checkTaken(Square[][] board, ImageView[] enemy_piece,Point movPos, Point curPos){

		if (movPos.x < 0 || movPos.x > 7)
			return true;
		if (movPos.y < 0 || movPos.y > 7)
			return true;
		
		Square testboard[][] = new Square[8][8];
		for (int y = 0; y < 8; y ++){
			for (int x = 0; x < 8; x++){
				testboard[x][y] = new Square(board[x][y]);
			}
		}
		
		testboard[curPos.x][curPos.y].setState("empty");
		testboard[movPos.x][movPos.y].setState(this.color);
		
		for (int z = 0 ; z < 16; z ++){
			if (((Piece) enemy_piece[z]).isActive()){
				if (enemy_piece[z] instanceof Pawn)
					((Pawn) enemy_piece[z]).getMoves(testboard,this,"kingtest");
				else	
					((Piece) enemy_piece[z]).getMoves(testboard);
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
		return testboard[movPos.x][movPos.y].isTaken();
	}
}
