package main;

public enum Pieces {
    KING,
    QUEEN,
    BISHOP,
    KNIGHT,
    ROOK,
    PAWN;

    public int textureLocation(Pieces p) {
        int out = 0;
        switch (p) {
            case KING:
                out = 0;
                break;
            case QUEEN:
                out = 200;
                break;
            case BISHOP:
                out = 400;
                break;
            case KNIGHT:
                out = 600;
                break;
            case ROOK:
                out = 800;
                break;
            case PAWN:
                out = 1000;
                break;
        }
        return out;
    }
}
