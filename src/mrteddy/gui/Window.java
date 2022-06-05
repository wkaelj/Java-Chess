package mrteddy.gui;

import mrteddy.gui.components.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This is a class to create a manage cross platform Swing GUI easily.
 */
public class Window extends JFrame {

    private static final long serialVersionUID = 1L;
	private final Container content = new Container();
    private final ExitMenu exitMenu = new ExitMenu(this);

    public boolean SafeQuit;
    private Dimension Size;

    /**
     * Constructor for the Window class.
     *
     * @param wndwName String The name of the window
     * @param wndwSize Dimension The size of the window
     * @param safeQuit boolean Should the window ask the user if they should quit.
     */
    public Window(String wndwName, Dimension wndwSize, boolean safeQuit, boolean resizable) {

        //safe quit settings
        SafeQuit = safeQuit;
        this.setResizable(resizable);

        //content settings
        content.setLayout(new GridLayout(0, 1));

        //window frame settings
        this.setBackground(Button.BLUE);
        this.setContentPane(content);
        this.setTitle(wndwName);
        Size = wndwSize;
        this.setPreferredSize(Size);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("Window opened");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(SafeQuit)
                    exitMenu.show();
                else {
                    System.exit(1);
                }
            }
        });
    }

    /**
     * Returns the size of window, as a Dimension
     *
     * @return {@code Dimension} size of window
     */
    public Dimension getSize() {
        return Size;
    } //return window size

    /**
     * Use to set the content of the this. The input JFrame will fill the window, regardless
     * of the size it is set to. It is recommended to use the getSize method of the window to
     * find the size of the panel.
     *
     * @param panel JPanel to set the content to
     */
    public void setContent(JComponent panel) {
        content.removeAll();
        content.add(panel);
        content.repaint();
        content.validate();
    }
    /**
     * Menu that asks if certain to exit, activated using the safeQuit parameter of the {@link Window} constructor
     */
     static class ExitMenu {
        JFrame exitFrame = new JFrame("Are you sure you want to quit?");
        JPanel exitPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Button yes = new Button("Quit", Button.RED, 100);
        Button no = new Button("Cancel", Button.GREEN, 100);
        JLabel sure = new JLabel();

        //constructor
        public ExitMenu(JFrame window) {
            yes.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    System.exit(1);
                }
            });
            yes.requestFocus();
            no.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    hide();
                }
            });
            sure.setText("<html><h2>Are you sure you want to quit?<h2></html>");
            sure.setForeground(Button.CYAN);

            exitPane.setBorder(BorderFactory.createEmptyBorder());
            exitFrame.add(exitPane);
            exitFrame.setBackground(Button.RED);
            setLayout();
            exitFrame.pack();
        }

        void setLayout() {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridwidth = 2;
            gbc.gridx = 1;
            gbc.gridy = 0;
            exitPane.add(sure, gbc);
            gbc.gridwidth = 1;
            gbc.gridx = 1;
            gbc.gridy = 1;
            exitPane.add(no, gbc);
            gbc.gridx = 2;
            exitPane.add(yes, gbc);
        }

        public void show() {
            exitFrame.validate();
            exitFrame.setVisible(true);
        }

        public void hide() {
            exitFrame.setVisible(false);
        }
    }
}

