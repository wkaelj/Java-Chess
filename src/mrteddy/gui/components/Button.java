package mrteddy.gui.components;

import mrteddy.gui.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Custom button class, extends <code>{@link JButton}</code>
 * @apiNote <code>Use addMouseListener(new MouseAdapter() {</p>public void mouseClicked{ code </p>}</p>});</code>
 * instead of addActionListener
 */
public class Button extends JComponent {

    private static final long serialVersionUID = 1L;
	//Color constants
    public static final Color RED = new Color(120, 50, 50);
    public static final Color CYAN = new Color(50, 120, 120);
    public static final Color GREEN = new Color(50, 120, 50);
    public static final Color BLUE = new Color(50,50,120);
    public static final Color YELLOW= new Color(150,150,20);
    public static final Color PURPLE = new Color(120,50,120);
    public static final Color GRAY = new Color(20,30,50);
    public static final Color LIGHT_GRAY = new Color(50,60,70);
    public static final Color TEXT = new Color(200, 200, 200);
    public static final Color UNFOCUSED_TEXT = new Color(140, 140, 140);


    String Name;
    Color Colour;
    Color currentColour;
    Dimension Size;
    Font font = new Font("Sans-Serif", Font.BOLD, 12);

    //private variable
    private boolean isHovered = false;
    private final int HOVER_COLOR_CHANGE = 50;
    private final int PRESS_COLOR_CHANGE = -20;
    final int BORDER_RADIUS;

    /**
     * Create a button, with custom size and color, with animation.
     *
     * @param buttonName <code>{@link String}</code> name of the button
     * @param buttonColour <code>{@link Color}</code> of the button
     * @param buttonSize <code>{@link Dimension}</code> size of the button
     * @param borderRadius <code>int</code> Radius of border, in pixels
     *
     * @author MisterTeddy
     */
    public Button (String buttonName, Color buttonColour, Dimension buttonSize, int borderRadius) {
        Name = buttonName;
        Colour = buttonColour;
        Size = buttonSize;
        currentColour = Colour;
        BORDER_RADIUS = borderRadius;
        setPreferredSize(Size);
        addListener();
        repaint();
    }

    /**
     * Create new {@link Button}
     * @param buttonName {@link String} name of button
     * @param buttonColour {@link Color} of button
     * @param width {@code int} width of button (px)
     */
    public Button (String buttonName, Color buttonColour, int width) {
        Name = buttonName;
        Colour = buttonColour;
        Size = new Dimension(width, 60);
        currentColour = Colour;
        BORDER_RADIUS = 16;
        setPreferredSize(Size);
        addListener();

        setBorder(BorderFactory.createEmptyBorder());
        repaint();
    }
    /**
     * Create a button, with custom size and color, with animation.
     *
     * @param buttonName <code>{@link String}</code> name of the button
     * @param buttonColour <code>{@link Color}</code> of the button
     * @param buttonSize <code>{@link Dimension}</code> size of the button
     *
     * @author MisterTeddy
     */
    public Button (String buttonName, Color buttonColour, Dimension buttonSize) {
        Name = buttonName;
        Colour = buttonColour;
        Size = buttonSize;
        currentColour = Colour;
        BORDER_RADIUS = 16;
        addListener();

        setOpaque(false);
        setPreferredSize(Size);
        repaint();
    }

    private void addListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentColour = Utilities.changeValue(Colour, PRESS_COLOR_CHANGE);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(isHovered)
                    currentColour = Utilities.changeValue(Colour, HOVER_COLOR_CHANGE);
                else
                    currentColour = Colour;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                currentColour = Utilities.changeValue(Colour, 50);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                currentColour = Colour;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(currentColour);
        g2d.fillRoundRect(5,0, (int) Size.getWidth() - 5, (int) Size.getHeight(), BORDER_RADIUS, BORDER_RADIUS);

        g2d.setColor(new Color(200,200,200));
        Utilities.drawCenteredString(g2d, Name, new Rectangle((int) Size.getWidth(), (int) Size.getHeight()), font);
    }


    public void setWidth(int width) {
        Size = new Dimension(width, (int) Size.getHeight());
        repaint();
    }
}
