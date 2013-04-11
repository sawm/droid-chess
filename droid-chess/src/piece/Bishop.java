package piece;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Bishop extends Piece {

	public Bishop(Context context, String color, Point position) {
		super(context, color, position);
		// TODO Auto-generated constructor stub
	}

	public Bishop(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Bishop(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

//	public void setboardPosition(Point point)
//	{
//		this.boardPosition.x = point.x;
//		this.boardPosition.y = point.y;
//	}
	
	@Override
	public void getMoves(Square[][] board, King king, ImageView[] enemy_piece) {
		{
			int inc=1;
			while (this.boardPosition.x+inc <= 7 && this.boardPosition.y+inc <= 7 && board[this.boardPosition.x+inc][this.boardPosition.y+inc].getState() != this.color){
				board[this.boardPosition.x+inc][this.boardPosition.y+inc].becomeAvailable(this);
				if (board[this.boardPosition.x+inc][this.boardPosition.y+inc].getState() == this.opp_color) {break;}
				inc ++;
			}
		}
		{
			int inc=1;
			while (this.boardPosition.x-inc >= 0 && this.boardPosition.y+inc <= 7 && board[this.boardPosition.x-inc][this.boardPosition.y+inc].getState() != this.color){
				board[this.boardPosition.x-inc][this.boardPosition.y+inc].becomeAvailable(this);
				if (board[this.boardPosition.x-inc][this.boardPosition.y+inc].getState() == this.opp_color) {break;}
				inc ++;
			}
		}
		{
			int inc=1;
			while (this.boardPosition.x+inc <= 7 && this.boardPosition.y-inc >= 0 && board[this.boardPosition.x+inc][this.boardPosition.y-inc].getState() != this.color){
				board[this.boardPosition.x+inc][this.boardPosition.y-inc].becomeAvailable(this);
				if (board[this.boardPosition.x+inc][this.boardPosition.y-inc].getState() == this.opp_color) {break;}
				inc ++;
			}
		}
		{
			int inc=1;
			while (this.boardPosition.x-inc >= 0 && this.boardPosition.y-inc >= 0 && board[this.boardPosition.x-inc][this.boardPosition.y-inc].getState() != this.color){
				board[this.boardPosition.x-inc][this.boardPosition.y-inc].becomeAvailable(this);
				if (board[this.boardPosition.x-inc][this.boardPosition.y-inc].getState() == this.opp_color) {break;}
				inc ++;
			}
		}

	}

}
