package mrteddy.gui.components;

import mrteddy.gui.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;


public class TextBox extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;
	private final Dimension Size;
    private final int BorderRadius;
    private final Color Colour;
    BasicStroke defaultStroke = new BasicStroke(3);
    private Font defaultFont;
    private boolean Focused = false;
    private String Text = "";
    private String PreviewText = "";
    private boolean isInt = false;
    private boolean isDouble = false;

    /**
     * Constuctor for {@link TextBox}
     *
     * @param width <code>int</code> width of component
     * @param height <code>int</code> height of component
     * @param backgroundColour <code>{@link Color}</code> background color of component
     * @param roundness <code>int</code> border radius of component
     *
     * @author MisterTeddy
     */
    public TextBox(int width, int height, Color backgroundColour, int roundness) {
        Size = new Dimension(width, height);
        setFocusable(true);
        setOpaque(false);
        setPreferredSize(Size);
        BorderRadius = roundness;
        Colour = backgroundColour;
        try {
            defaultFont = Font.createFont(Font.BOLD, new FileInputStream("fonts/Oxygen-Regular.ttf"));
        } catch (Exception e) {
            defaultFont = new Font("Serif", Font.PLAIN, 12);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Focused = true;
                repaint();
            }
        });
        addKeyListener(this);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Focused = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                Focused = false;
                repaint();
            }
        });
        repaint();
    }

    /**
     * Alt constructor, height default to 60, border radius default to 16
     *
     * @param width <code>int</code> Width of component
     * @param backgroundColour <code>{@link Color}</code> Background color of image
     * @param roundness <code>int</code> border radius of component
     *
     * @author MisterTeddy
     */
    public TextBox(int width, Color backgroundColour, int roundness) {
        Size = new Dimension(width, 60);
        setOpaque(false);
        setFocusable(true);
        setPreferredSize(Size);
        BorderRadius = roundness;
        Colour = backgroundColour;
        try {
            defaultFont = Font.createFont(Font.BOLD, new FileInputStream("fonts/Oxygen-Regular.ttf"));
        } catch (Exception e) {
            defaultFont = new Font("Sans-Serif", Font.PLAIN, 12);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Focused = true;
                requestFocus();
                repaint();
            }
        });
        addKeyListener(this);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Focused = true;
//                caratInterrupt.run();
                
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                Focused = false;
                repaint();
            }
        });
        repaint();
    }

    /**
     * Alt constructor, height default to 60, border radius default to 16
     *
     * @param width <code>int</code> Width of component
     * @param backgroundColour <code>{@link Color}</code> Background color of image
     *
     * @author MisterTeddy
     */
    public TextBox(int width, Color backgroundColour) {
        Size = new Dimension(width, 60);
        setOpaque(false);
        setFocusable(true);
        setPreferredSize(Size);
        BorderRadius = 16;
        Colour = backgroundColour;
        try {
            defaultFont = Font.createFont(Font.BOLD, new FileInputStream("fonts/Oxygen-Regular.ttf"));
        } catch (Exception e) {
            defaultFont = new Font("Sans-Serif", Font.PLAIN, 12);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Focused = true;
                requestFocus();
                repaint();
            }
        });
        addKeyListener(this);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Focused = true;
//                caratInterrupt.run();
                
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                Focused = false;
                repaint();
            }
        });
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(defaultStroke);
        g2d.setColor(Colour);

        g2d.fillRoundRect(5, 0, (int) Size.getWidth() - 5, (int) Size.getHeight(),
                BorderRadius, BorderRadius);

        //used by paintComponent
        String renderText;
        if(Text.length() <= 0) {
            g2d.setColor(Button.UNFOCUSED_TEXT);
            renderText = PreviewText;
        } else {
            g2d.setColor(Button.TEXT);
            renderText = Text;
        }
        Utilities.drawCenteredString(g2d, renderText, new Rectangle((int) Size.getWidth() - 1,
                (int) Size.getHeight() - 1), defaultFont);
        if (Focused) {
            g2d.setColor(Utilities.changeValue(Colour, 40));
            g2d.drawRoundRect(5, 0, (int) Size.getWidth() - 5, (int) Size.getHeight(),
                    BorderRadius, BorderRadius);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    //completed
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
            if (Text.length() != 0) {
                StringBuilder buffer = new StringBuilder(Text);
                Text = buffer.deleteCharAt(Text.length() - 1).toString();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setFocusable(false);
            setFocusable(true);
        } else if (e.getKeyCode() != KeyEvent.VK_SHIFT) {
            try {
                if (isInt) {
                    try {
                        Text = "" + Integer.parseInt(Text + e.getKeyChar());
                    } catch (NumberFormatException ignored) { }
                } else if (isDouble) {
                    try {
                        Text = "" + Double.parseDouble(Text + e.getKeyChar());
                    } catch (NumberFormatException ignored) { }
                } else
                    Text = Text + e.getKeyChar();
            } catch (StringIndexOutOfBoundsException exception) {
                System.out.println("Invalid Char");
            }
        }
        repaint();
    }

    //return carat point
    public int caratPoint(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        return x + metrics.stringWidth(text);
    }

    /**
     * Get text currently in text box
     *
     * @return <code>{@link String}</code> current text in {@link TextBox}
     * @author MisterTeddy
     */
    public String getText() {
        return Text;
    }

    //IO methods

    /**
     * Set the text currently in the {@link TextBox}
     *
     * @param text <code>{@link String}</code> text input
     * @author MisterTeddy
     */
    public void setText(String text) {
        Text = text;
        repaint();
    }

    /**
     * Delete all chars in {@link TextBox}
     *
     * @author MisterTeddy
     */
    public void clear() {
        Text = "";
        repaint();
    }

    public void setPreviewText(String text) {
        PreviewText = text;
        repaint();
    }

    public void setNumeric(boolean isNumeric) {
        isDouble = isNumeric;
    }

    public void setInt(boolean isInteger) {
        isInt = isInteger;
    }
}
