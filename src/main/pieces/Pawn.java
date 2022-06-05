package main.pieces;

import main.Piece;
import main.Pieces;
import main.Render;

public class Pawn extends Piece {

    int moveDir;
    public Pawn(boolean w, Render render, boolean whiteAtTop) {
        super(w, Pieces.PAWN, render);
        super.white = w;

       moveDir =  w ? 1 : -1;
       if (whiteAtTop) moveDir = -moveDir;
    }

    @Override
    public boolean isAllowedMove(int x1, int y1, int x2, int y2) {

        //ordinary move

        if (y1 - y2 == moveDir && x1 == x2 && r.getTile(x1, y2).piece == null) {
        	return true;
        }

        //first move
        if(firstMove && x1 == x2 && y1 - y2 == moveDir * 2) {
            return straight(x1, y1, x2, y2, r);
        }

        //taking
        if(y1 - y2 == moveDir && Math.abs(x1 - x2) == 1 && r.getTile(x2, y2).piece != null)
            return true;
        return false;
    }
}
