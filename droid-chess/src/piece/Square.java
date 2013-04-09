package piece;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Square extends ImageView {
	private Point position = new Point();
	private String color = new String();
	private String state = new String(); //states will be "black", "white", or "empty" depending on the piece residing on the square
	private boolean available = false;
	
	public Square(Context context, Point pos, String col) {
		super(context);
		this.position = pos;
		this.setColor(col);
		this.state = "empty";
	}

	public Square(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Square(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	
	public String getState() {
		return state;
	}
	
	public void setState(String newState){
		this.state = newState;
	}
	
	public Point getPosition(){
		return position;
	}

	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean availableMove) {
		this.available = availableMove;
	}
	
	public void becomeAvailable(Piece piece){
		this.setColorFilter(0xAAFF0000, PorterDuff.Mode.SRC_ATOP);
		this.setAvailable(true);
	}

}
