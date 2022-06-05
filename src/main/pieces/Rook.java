package main.pieces;

import main.Piece;
import main.Pieces;
import main.Render;

public class Rook extends Piece {


    public Rook(boolean w, Render render) {
        super(w, Pieces.ROOK, render);
    }

    @Override
    public boolean isAllowedMove(int x1, int y1, int x2, int y2) {
        return straight(x1, y1, x2, y2, r);
    }
}
