package piece;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Rook extends Piece {
	private boolean firstMove = true;

	public Rook(Context context, String color, Point position, int index) {
		super(context, color, position, index);
		// TODO Auto-generated constructor stub
	}

	public Rook(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Rook(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void moved(){
		firstMove = false;
	}
	
	public boolean hasMoved(){
		return !firstMove;
	}
	
	@Override
	public void getMoves(Square[][] board) {
		for(int x = this.boardPosition.x+1; x < 8; x++){ //positive x direction
			if( board[x][this.boardPosition.y].getState() == "empty" ){
				board[x][this.boardPosition.y].becomeAvailable(this);
			} else if( board[x][this.boardPosition.y].getState() == this.opp_color ){
				board[x][this.boardPosition.y].becomeAvailable(this);
				break;
			} else {
				 break;
			}
		}
		for(int y = this.boardPosition.y+1; y < 8; y++){ //negative y direction
			if( board[this.boardPosition.x][y].getState() == "empty"){
				board[this.boardPosition.x][y].becomeAvailable(this);
			} else if( board[this.boardPosition.x][y].getState() == this.opp_color){
				board[this.boardPosition.x][y].becomeAvailable(this);
				break;
			} else {
				break;
			}
		}
		for(int x = this.boardPosition.x-1; x >= 0; x--){ //negative x direction
			if( board[x][this.boardPosition.y].getState() == "empty" ){
				board[x][this.boardPosition.y].becomeAvailable(this);
			} else if( board[x][this.boardPosition.y].getState() == this.opp_color ){
				board[x][this.boardPosition.y].becomeAvailable(this);
				break;
			} else {
				break;
			}
		}
		for(int y = this.boardPosition.y-1; y >= 0; y--){ //positive y direction
			if( board[this.boardPosition.x][y].getState() == "empty"){
				board[this.boardPosition.x][y].becomeAvailable(this);
			} else if( board[this.boardPosition.x][y].getState() == this.opp_color){
				board[this.boardPosition.x][y].becomeAvailable(this);
				break;
			} else {
				break;
			}
		}

	}

}
