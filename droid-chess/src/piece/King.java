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
	public void getMoves(Square[][] board){
		//Toast.makeText(this.getContext(), "Incorrect getMoves() overload", Toast.LENGTH_SHORT).show();
	}
	
	public void getMoves(Square[][] board, ImageView[] enemy_piece, Context context) {
		

		checkTaken(board, enemy_piece);		
		
		if(this.boardPosition.x+1 <= 7 && board[this.boardPosition.x+1][this.boardPosition.y].getState() != this.color){
			if (board[this.boardPosition.x+1][this.boardPosition.y].isTaken() || checkTaken(board, enemy_piece,this.boardPosition.x+1,this.boardPosition.y,context))
				board[this.boardPosition.x+1][this.boardPosition.y].showTaken();
			else
				board[this.boardPosition.x+1][this.boardPosition.y].becomeAvailable(this);			
		}
		
		if(this.boardPosition.x-1 >=0 && board[this.boardPosition.x-1][this.boardPosition.y].getState() != this.color){
			if (board[this.boardPosition.x-1][this.boardPosition.y].isTaken() || checkTaken(board, enemy_piece,this.boardPosition.x-1,this.boardPosition.y,context))
				board[this.boardPosition.x-1][this.boardPosition.y].showTaken();
			else
				board[this.boardPosition.x-1][this.boardPosition.y].becomeAvailable(this);
		}
		if(this.boardPosition.y+1 <= 7 && board[this.boardPosition.x][this.boardPosition.y+1].getState() != this.color){
			if (board[this.boardPosition.x][this.boardPosition.y+1].isTaken())
				board[this.boardPosition.x][this.boardPosition.y+1].showTaken();
			else
				board[this.boardPosition.x][this.boardPosition.y+1].becomeAvailable(this);
		}
		if(this.boardPosition.y-1 >= 0 && board[this.boardPosition.x][this.boardPosition.y-1].getState() != this.color){
			if (board[this.boardPosition.x][this.boardPosition.y-1].isTaken())
				board[this.boardPosition.x][this.boardPosition.y-1].showTaken();
			else
				board[this.boardPosition.x][this.boardPosition.y-1].becomeAvailable(this);
		}
		
		if(this.boardPosition.y-1 >= 0 && this.boardPosition.x-1 >=0 && board[this.boardPosition.x-1][this.boardPosition.y-1].getState() != this.color){
			if (board[this.boardPosition.x-1][this.boardPosition.y-1].isTaken())
				board[this.boardPosition.x-1][this.boardPosition.y-1].showTaken();
			else
				board[this.boardPosition.x-1][this.boardPosition.y-1].becomeAvailable(this);
		}
		if(this.boardPosition.y-1 >= 0 && this.boardPosition.x+1 <=7 && board[this.boardPosition.x+1][this.boardPosition.y-1].getState() != this.color){
			if (board[this.boardPosition.x+1][this.boardPosition.y-1].isTaken())
				board[this.boardPosition.x+1][this.boardPosition.y-1].showTaken();
			else
				board[this.boardPosition.x+1][this.boardPosition.y-1].becomeAvailable(this);
		}
		if(this.boardPosition.y+1 <= 7 && this.boardPosition.x-1 >=0 && board[this.boardPosition.x-1][this.boardPosition.y+1].getState() != this.color){
			if (board[this.boardPosition.x-1][this.boardPosition.y+1].isTaken())
				board[this.boardPosition.x-1][this.boardPosition.y+1].showTaken();
			else
				board[this.boardPosition.x-1][this.boardPosition.y+1].becomeAvailable(this);
		}
		if(this.boardPosition.y+1 <= 7 && this.boardPosition.x+1 <=7 && board[this.boardPosition.x+1][this.boardPosition.y+1].getState() != this.color){
			if (board[this.boardPosition.x+1][this.boardPosition.y+1].isTaken())
				board[this.boardPosition.x+1][this.boardPosition.y+1].showTaken();
			else
				board[this.boardPosition.x+1][this.boardPosition.y+1].becomeAvailable(this);
		}


	}

	private boolean checkTaken(Square[][] board, ImageView[] enemy_piece){		
		for (int z = 0 ; z < 16; z ++){
			if (((Piece) enemy_piece[z]).isActive()){
				if (enemy_piece[z] instanceof Pawn)
					((Pawn) enemy_piece[z]).getMoves(board,"kingtest");
				else	
					((Piece) enemy_piece[z]).getMoves(board);
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
	
	private boolean checkTaken(Square[][] board, ImageView[] enemy_piece,int i,int j, Context context){

		Square testboard[][] = new Square[8][8];
		for (int y = 0; y < 8; y ++){
			for (int x = 0; x < 8; x++){
				testboard[x][y] = new Square(board[x][y]);
			}
		}
		
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
					((Pawn) enemy_piece[z]).getMoves(testboard,"kingtest");
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
		return testboard[i][j].isTaken();
	}
}
