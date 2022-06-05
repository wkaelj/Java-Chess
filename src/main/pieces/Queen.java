package main.pieces;

import main.Piece;
import main.Pieces;
import main.Render;

public class Queen extends Piece {
    public Queen(boolean w, Render r) {
        super(w, Pieces.QUEEN, r);
    }

    @Override
    public boolean isAllowedMove(int x1, int y1, int x2, int y2) {
        return diagonal(x1, y1, x2, y2, r) || straight(x1, y1, x2, y2, r);
    }
}
