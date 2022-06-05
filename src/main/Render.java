package main;

import mrteddy.gui.Utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Render extends JComponent {

    private static final long serialVersionUID = 1L;
	final Tile[][] board = new Tile[8][8];
    public final Color WHITE_TILE_COLOR = Color.LIGHT_GRAY;
    public final Color BLACK_TILE_COLOR = Color.DARK_GRAY;
    private final Color SELECTED_COLOR = new Color(50, 50, 255);
    public BufferedImage whiteTexture = null;
    public BufferedImage blackTexture = null;
    private int boardX;
    private int boardY;
    private int boardSize;

    public Render() {
        try {
            whiteTexture = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("textures/White.png")));
        } catch (IOException e) {
            System.out.println("White texture failed");
        }
        try {
            blackTexture = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("textures/Black.png")));
        } catch (IOException e) {
            System.out.println("ERROR: Failed to load texture");
            System.exit(1);
        }
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tileClicked(e.getX(), e.getY(), board) != null)
                    Main.moveManager.tileClicked(Objects.requireNonNull(tileClicked(e.getX(), e.getY(), board)));
            }
        });
        
        addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// refresh board on key r
				if(e.getKeyCode() == KeyEvent.VK_R) {
					System.out.println("DEBUG: refreshing board");
					Main.moveManager.getRender().repaint();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

        boolean w = true;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                board[x][y] = new Tile(w, null);
                w = !w;
            }
            w = !w;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (getWidth() < getHeight()) {
            boardX = 0;
            boardY = getHeight() / 2 - minDimension(getSize()) / 2;
        } else {
            boardX = getWidth() / 2 - minDimension(getSize()) / 2;
            boardY = 0;
        }
        drawBoard(boardX, boardY, board, g);
        boardSize = minDimension(getSize());
    }

    private void drawBoard(int x, int y, Tile[][] b, Graphics2D g) {

        int yPos = y;
        int xPos = x;
        int tileSize = minDimension(getSize()) / 8;
        for (Tile[] width : b) {
            for (Tile i : width) {
                //draw tile
                if (i.white)
                    g.setColor(WHITE_TILE_COLOR);
                else
                    g.setColor(BLACK_TILE_COLOR);
                g.fillRect(xPos, yPos, tileSize, tileSize);

                //draw piece
                if (i.piece != null) {
                    int xLoc = i.piece.getPiece().textureLocation(i.piece.getPiece());
                    if (i.piece.isWhite())
                        g.drawImage(Utilities.readPixels(whiteTexture, xLoc, 0, xLoc + 200, whiteTexture.getHeight()),
                                xPos, yPos, tileSize, tileSize, null);
                    else
                        g.drawImage(Utilities.readPixels(blackTexture, xLoc, 0, xLoc + 200, blackTexture.getHeight()),
                                xPos, yPos, tileSize, tileSize, null);
                }

                yPos += tileSize;
            }
            xPos += tileSize;
            yPos = y;
        }
    }

    //return shortest dimension of window
    int minDimension(Dimension d) {
        if (d.getHeight() > d.getWidth())
            return (int) d.getWidth();
        else
            return (int) d.getHeight();
    }

    /**
     * Check if not null, if click is outside of board will return null
     *
     * @param clickedX clicked position x-axis
     * @param clickedY clicked position y-axis
     * @return int[] x,y board tiles
     */
    private int[] tileClicked(int clickedX, int clickedY, Tile[][] b) {
        int[] out = new int[2];
        if (clickedX < boardX || clickedX < boardY || clickedX > boardX + boardSize || clickedY > boardY + boardSize)
            return null;
        int tileSize = boardSize / b.length;
        int xPos = boardX + tileSize;
        int yPos = boardY + tileSize;
        for (int x = 0; x < b.length; x++) {

            //if x is true run y
            if (clickedX < xPos) {
                for (int y = 0; y < b[0].length; y++) {
                    //if clicked less than end of current row / column
                    if (clickedY < yPos) {
                        out[0] = x;
                        out[1] = y;
                        return out;
                    }
                    yPos += tileSize;
                }
            }
            xPos += tileSize;
        }

        return out;
    }

    //draw selected tile
    public void drawSelectTile(Graphics g, int[] selected, int[] lastSelected) {
        //color new tile
        int tileSize = boardSize / 8;
        int x = boardX + tileSize * selected[0];
        int y = boardY + tileSize * selected[1];
        g.setColor(SELECTED_COLOR);
        g.fillRect(x, y, tileSize, tileSize);
        //draw piece
        if (board[selected[0]][selected[1]].piece != null) {
            int xLoc = board[selected[0]][selected[1]].piece.getPiece().textureLocation(board[selected[0]][selected[1]].
                    piece.getPiece());
            if (board[selected[0]][selected[1]].piece.isWhite())
                g.drawImage(Utilities.readPixels(whiteTexture, xLoc, 0, xLoc + 200, whiteTexture.getHeight()),
                        x, y, tileSize, tileSize, null);
            else
                g.drawImage(Utilities.readPixels(blackTexture, xLoc, 0, xLoc + 200, blackTexture.getHeight()),
                        x, y, tileSize, tileSize, null);
        }
        //fix old tile
        if(lastSelected != selected) {
            int ox = boardX + tileSize * lastSelected[0];
            int oy = boardY + tileSize * lastSelected[1];
            if (board[lastSelected[0]][lastSelected[1]].white)
                g.setColor(WHITE_TILE_COLOR);
            else
                g.setColor(BLACK_TILE_COLOR);
            g.fillRect(ox, oy, tileSize, tileSize);
            //draw piece
            if (board[lastSelected[0]][lastSelected[1]].piece != null) {
                int xLoc = board[lastSelected[0]][lastSelected[1]].piece.getPiece().textureLocation(board[lastSelected[0]]
                        [lastSelected[1]].piece.getPiece());
                if (board[lastSelected[0]][lastSelected[1]].piece.isWhite())
                    g.drawImage(Utilities.readPixels(whiteTexture, xLoc, 0, xLoc + 200, whiteTexture.getHeight()),
                            ox, oy, tileSize, tileSize, null);
                else
                    g.drawImage(Utilities.readPixels(blackTexture, xLoc, 0, xLoc + 200, blackTexture.getHeight()),
                            ox, oy, tileSize, tileSize, null);
            }
        }
    }

    public void setTile(int x, int y, Tile t) throws ArrayIndexOutOfBoundsException {
        board[x][y] = t;
        repaintTile(x, y);
    }
    public void setTile(int[] i, Tile t) {
        setTile(i[0], i[1], t);
    }

    public Tile getTile(int x, int y) {
        return board[x][y];
    }

    public Tile getTile(int[] i) {
        return board[i[0]][i[1]];
    }

    public void setTilePiece(int x, int y, Piece p) {
        board[x][y].piece = p;
        repaintTile(x, y);
    }

    public void setTilePiece(int[] i, Piece p) {
        setTilePiece(i[0], i[1], p);
    }

    public void repaintTile(int x, int y) {
        int tileSize = boardSize / 8;
        int xPos = boardX + tileSize * x;
        int yPos = boardY + tileSize * y;
        Graphics g = getGraphics();
        if (board[x][y].white)
            g.setColor(WHITE_TILE_COLOR);
        else
            g.setColor(BLACK_TILE_COLOR);
        g.fillRect(xPos, yPos, tileSize, tileSize);
        //draw piece
        if (board[x][y].piece != null) {
            int xLoc = board[x][y].piece.getPiece().textureLocation(board[x][y].
                    piece.getPiece());
            if (board[x][y].piece.isWhite())
                g.drawImage(Utilities.readPixels(whiteTexture, xLoc, 0, xLoc + 200, whiteTexture.getHeight()),
                        xPos, yPos, tileSize, tileSize, null);
            else
                g.drawImage(Utilities.readPixels(blackTexture, xLoc, 0, xLoc + 200, blackTexture.getHeight()),
                        xPos, yPos, tileSize, tileSize, null);
        }
    }

    public void repaintTile(int x, int y, Color c) {
        int tileSize = boardSize / 8;
        int xPos = boardX + tileSize * x;
        int yPos = boardY + tileSize * y;
        Graphics g = getGraphics();
        g.setColor(c);
        g.fillRect(xPos, yPos, tileSize, tileSize);
        //draw piece
        if (board[x][y].piece != null) {
            int xLoc = board[x][y].piece.getPiece().textureLocation(board[x][y].
                    piece.getPiece());
            if (board[x][y].piece.isWhite())
                g.drawImage(Utilities.readPixels(whiteTexture, xLoc, 0, xLoc + 200, whiteTexture.getHeight()),
                        xPos, yPos, tileSize, tileSize, null);
            else
                g.drawImage(Utilities.readPixels(blackTexture, xLoc, 0, xLoc + 200, blackTexture.getHeight()),
                        xPos, yPos, tileSize, tileSize, null);
        }
    }

    public void repaintTile(int[] i) {
        repaintTile(i[0], i[1]);
    }

    public void repaintTile(int[] i, Color c) {
        repaintTile(i[0], i[1], c);
    }

    public void win (boolean white) {
        Font winFont = new Font("Helvetica", Font.BOLD, (int) (getHeight() * 0.1));
        Graphics2D g = (Graphics2D) getGraphics();
        g.setColor(Color.CYAN);
        if (white) {
            Utilities.drawCenteredString(g, "White Wins", new Rectangle(0,0,getWidth(),getHeight()),
            winFont);
        } else {
            Utilities.drawCenteredString(g, "Black Wins", new Rectangle(0,0,getWidth(),getHeight()),
                    winFont);
        }
    }
}

