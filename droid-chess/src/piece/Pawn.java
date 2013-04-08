package piece;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Pawn extends Piece {
	private boolean firstMove = true;

	public Pawn(Context context, String color, Point position) {
		super(context, color, position);
		// TODO Auto-generated constructor stub
	}

	public Pawn(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Pawn(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getMoves(Square[][] board) {
		int movement = 0;
		if(this.color == "white"){
			movement = 1;
		} else {
			movement = -1;
		}
		if(this.boardPosition.y + movement >= 0 && this.boardPosition.y + movement <= 7 && board[this.boardPosition.x][this.boardPosition.y+movement].getState() == "empty"){
			if(this.boardPosition.x+1 <= 7 && board[this.boardPosition.x+1][this.boardPosition.y+movement].getState() == this.opp_color){
				board[this.boardPosition.x+1][this.boardPosition.y+movement].setColorFilter(0xAAFF0000, PorterDuff.Mode.SRC_ATOP);
			}
			if(this.boardPosition.x-1 >= 0 && board[this.boardPosition.x-1][this.boardPosition.y + movement].getState() == this.opp_color){
				board[this.boardPosition.x-1][this.boardPosition.y+movement].setColorFilter(0xAAFF0000, PorterDuff.Mode.SRC_ATOP);
			}
			if(this.firstMove == true &&  board[this.boardPosition.x][this.boardPosition.y+(movement*2)].getState() == "empty"){
				board[this.boardPosition.x][this.boardPosition.y+(movement*2)].setColorFilter(0xAAFF0000, PorterDuff.Mode.SRC_ATOP);
			}
			board[this.boardPosition.x][this.boardPosition.y+movement].setColorFilter(0xAAFF0000, PorterDuff.Mode.SRC_ATOP);
		}		
	}

}
