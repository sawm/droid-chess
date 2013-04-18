package piece;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Queen extends Piece {

	public Queen(Context context, String color, Point position) {
		super(context, color, position);
		// TODO Auto-generated constructor stub
	}

	public Queen(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Queen(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getMoves(Square[][] board) {
		int inc=1;
		while (this.boardPosition.x+inc <= 7 && this.boardPosition.y+inc <= 7 && board[this.boardPosition.x+inc][this.boardPosition.y+inc].getState() != this.color){
			board[this.boardPosition.x+inc][this.boardPosition.y+inc].setColorFilter(0xAAFF0000, PorterDuff.Mode.SRC_ATOP);
			board[this.boardPosition.x+inc][this.boardPosition.y+inc].setAvailable(true);
			if (board[this.boardPosition.x+inc][this.boardPosition.y+inc].getState() == this.opp_color) {break;}
			inc ++;
		}
		inc=1;
		while (this.boardPosition.x-inc >= 0 && this.boardPosition.y+inc <= 7 && board[this.boardPosition.x-inc][this.boardPosition.y+inc].getState() != this.color){
			board[this.boardPosition.x-inc][this.boardPosition.y+inc].setColorFilter(0xAAFF0000, PorterDuff.Mode.SRC_ATOP);
			board[this.boardPosition.x-inc][this.boardPosition.y+inc].setAvailable(true);
			if (board[this.boardPosition.x-inc][this.boardPosition.y+inc].getState() == this.opp_color) {break;}
			inc ++;
		}
		inc=1;
		while (this.boardPosition.x+inc <= 7 && this.boardPosition.y-inc >= 0 && board[this.boardPosition.x+inc][this.boardPosition.y-inc].getState() != this.color){
			board[this.boardPosition.x+inc][this.boardPosition.y-inc].setColorFilter(0xAAFF0000, PorterDuff.Mode.SRC_ATOP);
			board[this.boardPosition.x+inc][this.boardPosition.y-inc].setAvailable(true);
			if (board[this.boardPosition.x+inc][this.boardPosition.y-inc].getState() == this.opp_color) {break;}
			inc ++;
		}
		inc=1;
		while (this.boardPosition.x-inc >= 0 && this.boardPosition.y-inc >= 0 && board[this.boardPosition.x-inc][this.boardPosition.y-inc].getState() != this.color){
			board[this.boardPosition.x-inc][this.boardPosition.y-inc].setColorFilter(0xAAFF0000, PorterDuff.Mode.SRC_ATOP);
			board[this.boardPosition.x-inc][this.boardPosition.y-inc].setAvailable(true);
			if (board[this.boardPosition.x-inc][this.boardPosition.y-inc].getState() == this.opp_color) {break;}
			inc ++;
		}
	
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
