package main;

public abstract class Piece {

    public boolean white;
    public boolean firstMove = true;
    public Render r;
    Pieces piece;

    public Piece(boolean w, Pieces p, Render render) {
        white = w;
        piece = p;
        r = render;
    }

    public boolean isWhite() {
        return white;
    }

    public Pieces getPiece() {
        return piece;
    }

    abstract public boolean isAllowedMove(int x1, int y1, int x2, int y2);

    public boolean diagonal(int x1, int y1, int x2, int y2, Render r) {
        if (Math.abs(x1 - x2) == Math.abs(y1 - y2)) {
            int xAxis = 1;
            if (x1 - x2 > 0)
                xAxis = -1;
            int yAxis = 1;
            if (y1 - y2 > 0)
                yAxis = -1;

            int x = x1 + xAxis, y = y1 + yAxis;

            while (x != x2) {
                if (r.getTile(x, y).piece != null) {
                    System.out.println("x: " + x + ", y: " + y);
                    return false;
                }
                x += xAxis;
                y += yAxis;
            }
            return true;
        }
        return false;
    }

    //is move allowed for straight movement
    public boolean straight(int x1, int y1, int x2, int y2, Render r) {
        //if move is allowed
        if (x1 == x2 ^ y1 == y2) {

            //check if vertical
            if (x1 == x2) {
                int increment = 1; //weather to move up or down column
                if (y1 > y2)
                    increment = -1;
                for (int i = y1 + increment; (y1 > y2 && i > y2) || (i < y2); i += increment) {
                    //if piece is not null, return false
                    if (r.getTile(x1, i).piece != null)
                        return false;
                }
            }

            //check if horizontal
            if (y1 == y2) {
                int increment = 1; //wether to move up or down column
                if (x1 > x2)
                    increment = -1;
                for (int i = x1 + increment; (x1 < x2 && i < x2) || (i > x2); i += increment) {
                    //if piece is not null, return false
                    if (r.getTile(i, y1).piece != null)
                        return false;
                }
            }
            return true;
        }
        return false;
    }
}

