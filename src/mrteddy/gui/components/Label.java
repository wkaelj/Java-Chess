package mrteddy.gui.components;

import mrteddy.gui.Utilities;

import javax.swing.*;
import java.awt.*;

public class Label extends JComponent {
    private static final long serialVersionUID = 1L;
	private Color Colour;
    private String Text;
    private final Dimension Size;
    private final Font font = new Font("Sans-Serif", Font.BOLD, 12);


    public Label(String text, Color color, int width) {
        Size = new Dimension(width, 60);
        setPreferredSize(Size);
        Colour = color;
        Text = text;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(Colour);
        Utilities.drawCenteredString(g, Text, new Rectangle((int) Size.getWidth(), (int) Size.getHeight()), font);
    }

    public void setText(String text) {
        Text = text;
        repaint();
    }

    public void setColour(Color color) {
        Colour = color;
        repaint();
    }
}
