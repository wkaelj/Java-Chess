package main;

import mrteddy.gui.Utilities;
import mrteddy.gui.Window;

import java.awt.*;


public class Main {

    static Window window = new Window("Chess", new Dimension(800,600), false, true);
    static Render render = new Render();
    public static MoveManager moveManager = new MoveManager(render);

    public static void main(String[] args) {
        window.setContent(render);
        Image icon = Utilities.readPixels(render.whiteTexture, 200, 0, 400, 200);
        window.setIconImage(icon);
        window.setVisible(true);
        moveManager.setupBoard(false);
    }

    public static void win(boolean isWhite) {
        String winningName;
        if (isWhite)
            winningName = "White";
        else
            winningName = "Black";
        System.out.println(winningName + " wins! Congratulations");
    }

}
