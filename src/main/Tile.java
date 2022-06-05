package main;

import javax.swing.*;

public class Tile extends JComponent {

    private static final long serialVersionUID = 1L;
	public Piece piece;
    final boolean white;

    public Tile(boolean w, Piece p) {
        white = w;
        piece = p;
    }

    void setPiece(Piece p) {
        piece = p;
    }
}
