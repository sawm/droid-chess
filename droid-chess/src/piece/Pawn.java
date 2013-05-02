package piece;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.Toast;

public class Pawn extends Piece {
	private boolean firstMove = true;
	private String pieceType  = "pawn";
	
	public Pawn(Context context, String color, Point position, int index) {
		super(context, color, position, index);
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
	
	public void promote(String type){
		pieceType = type;
	}

	public boolean isPromoted(){
		if (pieceType == "pawn")
			return false;
		else
			return true;
	}
	
	public void moved(){
		firstMove = false;
	}
	
	public boolean hasMoved(){
		return !firstMove;
	}

	@Override
	public void getMoves(Square[][] board) {
		if (pieceType == "queen")
			getQueenMoves(board);
		else if (pieceType == "rook")
			getRookMoves(board);
		else if (pieceType == "bishop")
			getBishopMoves(board);
		else if (pieceType == "knight")
			getKnightMoves(board);
		else
		{	
	
			int movement = 0;
			if(this.color == "white"){
				movement = 1;
			} else {
				movement = -1;
			}
			if(this.boardPosition.y + movement >= 0 && this.boardPosition.y + movement <= 7){
				if (board[this.boardPosition.x][this.boardPosition.y+movement].getState() == "empty"){					
					board[this.boardPosition.x][this.boardPosition.y+movement].becomeAvailable(this);
				}
				if(this.boardPosition.x+1 <= 7 && board[this.boardPosition.x+1][this.boardPosition.y+movement].getState() == this.opp_color){
					board[this.boardPosition.x+1][this.boardPosition.y+movement].becomeAvailable(this);
				}
				if(this.boardPosition.x-1 >= 0 && board[this.boardPosition.x-1][this.boardPosition.y + movement].getState() == this.opp_color){
					board[this.boardPosition.x-1][this.boardPosition.y+movement].becomeAvailable(this);
				}
				if (board[this.boardPosition.x][this.boardPosition.y+movement].getState() == "empty"){
					if(this.firstMove == true &&  board[this.boardPosition.x][this.boardPosition.y+(movement*2)].getState() == "empty")
						board[this.boardPosition.x][this.boardPosition.y+(movement*2)].becomeAvailable(this);
					board[this.boardPosition.x][this.boardPosition.y+movement].becomeAvailable(this);
				}
			}
		}	
	}		
	
	public void getMoves(Square[][] board, King king, String string) {
		if (pieceType == "queen")
			getQueenMoves(board);
		else if (pieceType == "rook")
			getRookMoves(board);
		else if (pieceType == "bishop")
			getBishopMoves(board);
		else if (pieceType == "knight")
			getKnightMoves(board);
		else
		{
			int movement = 0;
			if(this.color == "white"){
				movement = 1;
			} else {
				movement = -1;
			}
			if(this.boardPosition.y + movement >= 0 && this.boardPosition.y + movement <= 7){
				if(this.boardPosition.x+1 <= 7 && board[this.boardPosition.x+1][this.boardPosition.y+movement].getState() == this.opp_color){
					board[this.boardPosition.x+1][this.boardPosition.y+movement].becomeAvailable(this);
				}
				if(this.boardPosition.x-1 >= 0 && board[this.boardPosition.x-1][this.boardPosition.y + movement].getState() == this.opp_color){
					board[this.boardPosition.x-1][this.boardPosition.y+movement].becomeAvailable(this);
				}
			}
		}
	}
	
	private void getQueenMoves(Square[][] board){
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

	private void getRookMoves(Square[][] board){
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
	private void getBishopMoves(Square[][] board){
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
	private void getKnightMoves(Square[][] board){
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
