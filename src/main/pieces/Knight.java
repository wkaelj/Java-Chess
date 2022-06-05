package main.pieces;

import main.Piece;
import main.Pieces;

public class Knight extends Piece {
    public Knight(boolean w) {
        super(w, Pieces.KNIGHT, null);
    }

    @Override
    public boolean isAllowedMove(int x1, int y1, int x2, int y2) {
        int xAbs = Math.abs(x1 - x2);
        int yAbs = Math.abs(y1 - y2);


        return (1 <= xAbs && xAbs <= 2) && (1 <= yAbs && yAbs <= 2) &&
                xAbs != yAbs;
    }
}
