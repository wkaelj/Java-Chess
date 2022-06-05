package mrteddy.gui.components;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

//TODO fix
/**
 * Creates a slider that will return a value from 0f - 1f, left to right.
 *
 * @extends {@link JSlider}
 * @implements {@link ChangeListener}
 *
 * @author MisterTeddy
 */
public class Slider extends JSlider implements ChangeListener {

    private static final long serialVersionUID = 1L;
	int Width = 30;
    final int MIDDLE = 7;
    final int HEIGHT = 15;
    final int BORDER_RADIUS = 2;
    int innerMin = 20;
    int innerMax = -20;

    final Color COLOR;
    Color test = Color.RED;

    private float percent = 0.5f;

    public Slider(int width, Color sliderColor) {

        Width = width;
        COLOR = sliderColor;
        setPreferredSize(new Dimension(Width, HEIGHT));
        addChangeListener(this);
        setValue(500);

        setMaximum(1000);
        setMinimum(0);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        //line
        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(30, 5, Width - 30, 5, BORDER_RADIUS, BORDER_RADIUS);

        //draw circle full height at getWidth() * percent, middle
        g.setColor(COLOR);
        //handle edge cases
        int renderPos = Math.round(Width * percent);
        if(renderPos < 20)
            g.fillOval(20, 0, HEIGHT, HEIGHT);
        else g.fillOval(Math.min(renderPos, Width - 20), 0, HEIGHT, HEIGHT);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        //clamping


        percent = getValue() / 1000f;
        System.out.println(percent);
        repaint();
    }

    /**
     * Return decimal value slider position, 0 = left, 1 = right;
     *
     * @return <code>float</code> float 0-1 amount completed
     *
     * @author MisterTeddy
     */
    public float getFloat() {
        return percent;
    }
}
