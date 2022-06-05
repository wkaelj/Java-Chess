package main;

import main.pieces.*;
import java.awt.*;

public class MoveManager {

    private int firstSelected[] = null;
    private final Color FIRST_SELECTED = new Color(0.8f, 0.2f, 0.2f, 0.5f);
    private int secondSelected[] = null;
    private int lastMoved[];
    private final Color SECOND_SELECTED = new Color(0.2f, 0.8f, 0.5f);
    private boolean whitesTurn = true;

    private Render r;
    
    public Render getRender() {
    	return r;
    }

    public MoveManager(Render render) {
        r = render;
    }

    public void tileClicked(int x, int y) {
   
    	// reset colours when move complete
        if (firstSelected != null && secondSelected != null) {
            r.repaintTile(firstSelected);
            r.repaintTile(secondSelected);
            firstSelected = null;
            secondSelected = null;
            r.repaintTile(lastMoved, SECOND_SELECTED);
        }
        
        // castling (hacked)
        // could have gone between first and second selected
        // but why bother the code is already past gone
        if(firstSelected != null && r.getTile(firstSelected).piece.getPiece() == Pieces.KING &&
        		((King)r.getTile(firstSelected).piece).isLegalCastle(firstSelected[0], firstSelected[1], x, y)) {
        	
        	// in this function I convert coordinates to be just X values, as casting is only horizontal
        	// the functions y direction can simply use the y value passed to the function,
        	// because the isLegalCastle function will fail if both y values are not the same
        	System.out.println("Trying to castle");
        	
        	// used to switch directions if the king is going left or right
        	int kingPos = firstSelected[0];
        	int rookPos = x;
        	
        	int directionMultiplier = 0;
        	// if going right
        	if (rookPos > kingPos) {
        		directionMultiplier = 1;
        	} else directionMultiplier = -1;
        	
        	// move king two spaces toward rook
        	r.setTilePiece(kingPos + 2 * directionMultiplier, y, r.getTile(firstSelected).piece);
        	r.setTilePiece(kingPos,  y, null);
        	// repaint tiles
        	r.repaintTile(kingPos, y);
        	kingPos += 2 * directionMultiplier;
        	lastMoved = new int[] {kingPos, y};
        	r.repaintTile(kingPos, y, SECOND_SELECTED);
        	
        	// move rook to outside of king
        	r.setTilePiece(kingPos - 1 * directionMultiplier, y, r.getTile(rookPos, y).piece);
        	r.setTilePiece(rookPos, y, null);
        	// repaint tiles
        	r.repaintTile(rookPos, y);
        	rookPos = kingPos - 1 * directionMultiplier;
        	r.repaintTile(rookPos, y);
        	
        	whitesTurn = !whitesTurn;
        	firstSelected = null;
        	secondSelected = null;
        	
        	return;
        }

        //selecting first piece
        if (firstSelected == null && r.getTile(x, y).piece != null && r.getTile(x, y).piece.isWhite() == whitesTurn ||
                r.getTile(x, y).piece != null && r.getTile(x, y).piece.isWhite() == whitesTurn) {

            if(firstSelected != null)
                r.repaintTile(firstSelected);
            if (lastMoved != null) r.repaintTile(lastMoved, SECOND_SELECTED);

            firstSelected = new int[]{x, y};
            r.repaintTile(firstSelected, FIRST_SELECTED);
        }
        //selecting second piece
        else if (firstSelected != null && r.getTile(firstSelected).piece.isAllowedMove(firstSelected[0], firstSelected[1], x, y)) {

            //check if piece is not same color and tile is not empty
            if (r.getTile(x, y).piece != null && r.getTile(x, y).piece.isWhite() == r.getTile(firstSelected).piece.isWhite())
                return;
            secondSelected = new int[]{x, y};

            //repaint
            if (lastMoved != null)
                r.repaintTile(lastMoved);
            lastMoved = secondSelected;
            r.repaintTile(x, y, SECOND_SELECTED);

            //pawn -> queen

            //check if team won
            boolean won = false;
            if (r.getTile(secondSelected).piece != null && r.getTile(secondSelected).piece.piece == Pieces.KING) {
                won = true;
            }

            //TODO print move

            r.setTilePiece(secondSelected, r.getTile(firstSelected).piece);
            r.getTile(secondSelected).piece.firstMove = false;
            r.setTilePiece(firstSelected, null);

            System.out.println(secondSelected[0] + ", " + secondSelected[1]);
            // set pawn to queen if it gets to last spot
            if(r.getTile(secondSelected).piece.getPiece() == Pieces.PAWN && (secondSelected[1] == 0 || secondSelected[1] == 7))
                r.setTilePiece(secondSelected, new Queen(whitesTurn, r));
                r.repaintTile(lastMoved, SECOND_SELECTED);
            whitesTurn = !whitesTurn;
            
            if(won) r.win(r.getTile(secondSelected).piece.isWhite());

            //TODO send to server
        }
    }

    public void tileClicked(int[] i) {
        tileClicked(i[0], i[1]);
    }

    public void setupBoard(boolean whiteAtTop) {
    	
        //bottom
        r.setTilePiece(0, 7, new Rook(!whiteAtTop, r));
        r.setTilePiece(1, 7, new Knight(!whiteAtTop));
        r.setTilePiece(2, 7, new Bishop(!whiteAtTop, r));
        if(!whiteAtTop) {
            r.setTilePiece(3, 7, new King(true, r));
            r.setTilePiece(4, 7, new Queen(true, r));
        } else {
            r.setTilePiece(3, 7, new Queen(false, r));
            r.setTilePiece(4, 7, new King(false, r));
        }
        r.setTilePiece(5, 7, new Bishop(!whiteAtTop, r));
        r.setTilePiece(6, 7, new Knight(!whiteAtTop));
        r.setTilePiece(7, 7, new Rook(!whiteAtTop, r));
        for (int i = 0; i < 8; i++) {
            r.setTilePiece(i, 6, new Pawn(!whiteAtTop, r, whiteAtTop));
        }

        //top
        r.setTilePiece(0, 0, new Rook(whiteAtTop, r));
        r.setTilePiece(1, 0, new Knight(whiteAtTop));
        r.setTilePiece(2, 0, new Bishop(whiteAtTop, r));
        if(whiteAtTop) {
            r.setTilePiece(3, 0, new King(true, r));
            r.setTilePiece(4, 0, new Queen(true, r));
        } else {
            r.setTilePiece(3, 0, new Queen(false, r));
            r.setTilePiece(4, 0, new King(false, r));
        }
        r.setTilePiece(5, 0, new Bishop(whiteAtTop, r));
        r.setTilePiece(6, 0, new Knight(whiteAtTop));
        r.setTilePiece(7, 0, new Rook(whiteAtTop, r));
        //pawns
        for (int i = 0; i < 8; i++) {
            r.setTilePiece(i, 1, new Pawn(whiteAtTop, r, whiteAtTop));
            // r.repaint();
        }
        
        r.repaint();
    }
}
