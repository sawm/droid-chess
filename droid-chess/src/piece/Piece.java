package piece;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.ImageView;

public abstract class Piece extends ImageView {
	protected String color = new String();
	protected String opp_color = new String();
	protected Point boardPosition = new Point();
	protected boolean active = true;
	protected boolean taken = false;

	public Piece(Context context, String color, Point position) {
		super(context);
		this.setColor(color);
		this.setBoardPosition(position);
		if(this.color == "white"){
			this.opp_color = "black";
		} else {
			this.opp_color = "white";
		}
	}

	public Piece(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Piece(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public abstract void getMoves(Square[][] board);
	
	public Point getBoardPosition() {
		return boardPosition;
	}

	public void setBoardPosition(Point boardPosition) {
		this.boardPosition = boardPosition;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isTaken(){
		return taken;
	}
	
	public void setTaken(boolean taken){
		this.taken = taken;
	}
}
