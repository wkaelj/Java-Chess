package main.pieces;

import main.Piece;
import main.Pieces;
import main.Render;

public class King extends Piece {

    public King(boolean w, Render render) {
		super(w, Pieces.KING, render);
		// TODO Auto-generated constructor stub
	}

	public boolean isAllowedMove(int x1, int y1, int x2, int y2) {
    	// ordinary move
    	return Math.abs(x2 - x1) <= 1 && Math.abs(y2 - y1) <= 1;
    }
    
    public boolean isLegalCastle(int x1, int y1, int x2, int y2) {
    	// castle
    	Piece rookTile = r.getTile(x2, y2).piece;
    	boolean viablePiece = firstMove && rookTile != null;
    	viablePiece = viablePiece && rookTile.getPiece() == Pieces.ROOK && rookTile.white == white && rookTile.firstMove;
    	return viablePiece && straight(x1, y1, x2, y2, r);
    }
}
