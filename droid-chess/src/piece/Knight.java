package piece;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;

public class Knight extends Piece {

	public Knight(Context context, String color, Point position) {
		super(context, color, position);
		// TODO Auto-generated constructor stub
	}

	public Knight(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Knight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getMoves(Square[][] board) {
		if(this.boardPosition.x+2 <= 7 && this.boardPosition.y+1 <=7 && board[this.boardPosition.x+2][this.boardPosition.y+1].getState() != this.color){
			board[this.boardPosition.x+2][this.boardPosition.y+1].becomeAvailable(this);
		}
		if(this.boardPosition.x+1 <= 7 && this.boardPosition.y+2 <=7 && board[this.boardPosition.x+1][this.boardPosition.y+2].getState() != this.color){
			board[this.boardPosition.x+1][this.boardPosition.y+2].becomeAvailable(this);
		}
		if(this.boardPosition.x-2 >= 0 && this.boardPosition.y+1 <=7 && board[this.boardPosition.x-2][this.boardPosition.y+1].getState() != this.color){
			board[this.boardPosition.x-2][this.boardPosition.y+1].becomeAvailable(this);
		}
		if(this.boardPosition.x-1 >= 0 && this.boardPosition.y+2 <=7 && board[this.boardPosition.x-1][this.boardPosition.y+2].getState() != this.color){
			board[this.boardPosition.x-1][this.boardPosition.y+2].becomeAvailable(this);
		}
		if(this.boardPosition.x+2 <= 7 && this.boardPosition.y-1 >=0 && board[this.boardPosition.x+2][this.boardPosition.y-1].getState() != this.color){
			board[this.boardPosition.x+2][this.boardPosition.y-1].becomeAvailable(this);
		}
		if(this.boardPosition.x+1 <= 7 && this.boardPosition.y-2 >=0 && board[this.boardPosition.x+1][this.boardPosition.y-2].getState() != this.color){
			board[this.boardPosition.x+1][this.boardPosition.y-2].becomeAvailable(this);
		}
		if(this.boardPosition.x-2 >= 0 && this.boardPosition.y-1 >=0 && board[this.boardPosition.x-2][this.boardPosition.y-1].getState() != this.color){
			board[this.boardPosition.x-2][this.boardPosition.y-1].becomeAvailable(this);
		}
		if(this.boardPosition.x-1 >= 0 && this.boardPosition.y-2 >=0 && board[this.boardPosition.x-1][this.boardPosition.y-2].getState() != this.color){
			board[this.boardPosition.x-1][this.boardPosition.y-2].becomeAvailable(this);
		}

	}

}
