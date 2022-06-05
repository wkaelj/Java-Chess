package mrteddy.gui.components;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import mrteddy.gui.Utilities;

/**
 * Class to create a color
 */
public class ColorPicker extends JButton implements ActionListener {

    private static final long serialVersionUID = 1L;
	private Color Colour; //stores main Colour of colour picker
    private final Dimension Size = new Dimension(50, 50);
    private final PickerWindow pickerWindow = new PickerWindow(this);

    /**
     * Creates a 50x50 box to display the colour. On click opens window in new thread to select colour in, and then when
     * closed will set colour on box again
     *
     * @param colour {@code Color} The default colour for the picker
     */
    public ColorPicker(Color colour) {
        setPreferredSize(Size);
        addActionListener(this);
        Colour = colour;
        repaint();
        pickerWindow.run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pickerWindow.setActive(!pickerWindow.isActive());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(Colour);
        g.fillRect(0,0, getWidth(), getHeight());
    }

    @Override public int getWidth() { return (int) Size.getWidth(); }
    @Override public int getHeight() { return (int) Size.getHeight(); }
    @Override public void setSize(Dimension dimension) { }

    /**
     * Returns currently selected colour
     *
     * @return {@code Color Colour} colour currently selected by colour picker
     */
    public Color getColour() {
        return Colour;
    }

    /**
     * Method to set the colour of the colour picker
     *
     * @param input Color to set as the colour
     *
     * @author MisterTeddy
     */
    public void setColour(Color input) {
        Colour = input;
        repaint();
    }
}

/**
 * class to create colour picking window
 */
class PickerWindow extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
	public ColorPanel colorPanel;
    Container content = new Container();
    static ColorPicker picker;

    private final JSlider saturationSlider = new JSlider();

    private boolean active = false;

    //picker class
    public PickerWindow(ColorPicker pick) {
        setPreferredSize(new Dimension(400, 500));

        //content settings
        picker = pick;
        colorPanel = new ColorPanel();

        Button exit = new Button("Submit", Button.GREEN, new Dimension(200, 70));
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setActive(false);
            }
        });
        saturationSlider.setMaximum(1000);
        saturationSlider.setMinimum(0);
        saturationSlider.setValue(1000);
        saturationSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                colorPanel.setSaturation(saturationSlider.getValue() / 1000f);
            }
        });
        //layout
        content.setLayout(new FlowLayout(FlowLayout.CENTER));
        content.add(colorPanel);
        content.add(saturationSlider);
        content.add(exit);



        //window frame settings
        setContentPane(content);
        setTitle("Color Picker");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                setActive(false);
            }
        });
        pack();
        validate();
        setVisible(false);
    }

    @Override
    public void run() {
        setActive(false);
    }

    private static float saturation = 1f;
    /**
     * Draws a menu where you can select any RGB color. Use the getColor method to return the color
     */
    private static class ColorPanel extends JPanel {

        private static final long serialVersionUID = 1L;
		private Point position = new Point(0,0);
        private static Color colour = null;
        private static BufferedImage texture;
        private int W;
        private int H;

        DrawTexture drawTexture = new DrawTexture();

        /**
         * Create new colour picking window
         */
        public ColorPanel() {
            setPreferredSize(new Dimension(400,250));
            drawTexture.run();
            texture = Utilities.colorArrayToImage(Utilities.drawColorMap(400,250, 0.5f));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    position = e.getPoint();
                    drawTexture.redraw(saturation);
                    repaint();
                }
            });

            addComponentListener(new ComponentAdapter() {
                 @Override
                 public void componentResized(ComponentEvent componentEvent) {
                     W = getWidth();
                     H = getHeight();
                     drawTexture.redraw(saturation);
                 }
            });
            repaint();
        }

        public void setSaturation(float s) {
            saturation = s;
            drawTexture.redraw(saturation);
        }

        private class DrawTexture implements Runnable {

            @Override
            public void run() {}

            public void redraw(float saturation) {
                texture = Utilities.colorArrayToImage(Utilities.drawColorMap(W, H, saturation));
                repaint();
            }
        }

        //TODO draw white values
        @Override protected void paintComponent(Graphics graphics) {
            Graphics2D d = (Graphics2D) graphics;
            W = getWidth();
            H = getHeight();

            colour = Utilities.intToColor(texture.getRGB(position.x, position.y));
            picker.setColour(colour);
            d.drawImage(texture, 0,0, W, H, null);

            d.setColor(Color.WHITE);
            d.setStroke(new BasicStroke(3));
            d.drawOval(position.x - 5,position.y - 5,10,10);
        } //end graphics

        /**
         * Method to return currently selected colour
         *
         * @return {@code Color colour} The colour that is currently selected in the PickerWindow
         */
        @SuppressWarnings("unused")
		public Color getColour() {
            return colour;
        }

    } //end colour

    /**
     * use to set visibility of window
     *
     * @param isActive boolean
     *
     * @author MisterTeddy
     */
    public void setActive(boolean isActive) {
        active = isActive;
        setVisible(active);
    }

    /**
     * Method returns true if frame is currently visible
     *
     * @return {@code boolean} Is frame currently visible
     *
     * @author MisterTeddy
     */
    public boolean isActive() {
        return active;
    }
}
