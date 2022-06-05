package main.pieces;

import main.Piece;
import main.Pieces;
import main.Render;

public class Bishop extends Piece {
    public Bishop(boolean w, Render render) {
        super(w, Pieces.BISHOP, render);
    }

    @Override
    public boolean isAllowedMove(int x1, int y1, int x2, int y2) {
        return diagonal(x1, y1, x2, y2, r);
    }
}
