package mrteddy.gui.components;

import mrteddy.gui.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DropDown extends Button {

    private static final long serialVersionUID = 1L;

	ArrayList<DropItem> items = new ArrayList<>();

    private boolean Dropped = false;
    private DropItem selected = null;

    public DropDown(String name, Color color, int width) {
        super(name, color, width);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Dropped = !Dropped;
                System.out.println(Dropped);
                repaint();
            }
        });
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(currentColour);
        g2d.fillRoundRect(5,0, (int) Size.getWidth() - 5, (int) Size.getHeight(), BORDER_RADIUS, BORDER_RADIUS);

        g2d.setColor(new Color(200,200,200));
        Utilities.drawCenteredString(g2d, Name, new Rectangle((int) Size.getWidth(), (int) Size.getHeight()), font);
    }

    /**
     * Set selected menu item
     *
     * @param i <code>{@link DropItem}</code> to set
     */
    public void setSelected(DropItem i) {
        selected = i;
    }

    /**
     * Return the currently selected dropdown menu item, check for <code>!= null</code>
     *
     * @return <code>DropItem</code> selected menu item
     */
    public DropItem getSelected() {
        return selected;
    }


    class ItemPanel extends JPanel {
        private static final long serialVersionUID = 1L;

		public ItemPanel() {
            for ( DropItem i : items ) {
                this.add(i);
            }
        }

        void activate() {

        }

        void deactivate() {

        }
    }
}

class DropItem extends Button {

    private static final long serialVersionUID = 1L;
	public String Text;

    public DropItem(String name, Color buttonColour, int width) {
        super(name, buttonColour, width);
        Text = name;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
}
