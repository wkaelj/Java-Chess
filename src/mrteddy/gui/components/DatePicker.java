package mrteddy.gui.components;

import mrteddy.gui.types.t_Date;

import javax.swing.*;
import java.awt.*;

public class DatePicker extends JComponent {

    private static final long serialVersionUID = 1L;
	TextBox dayBox;
    TextBox monthBox;
    TextBox yearBox;
    TextBox hourBox;
    TextBox minBox;

    public DatePicker(int width, Color c) {
        setLayout(new GridLayout(0, 5));
        setPreferredSize(new Dimension(width, 60));
        setBorder(BorderFactory.createEmptyBorder());
        yearBox = new TextBox(width / 5, c);
        monthBox = new TextBox(width / 5, c);
        dayBox = new TextBox(width / 5, c);
        hourBox = new TextBox(width / 5, c);
        minBox = new TextBox(width / 5, c);
        yearBox.setInt(true);
        monthBox.setInt(true);
        dayBox.setInt(true);
        hourBox.setInt(true);
        minBox.setInt(true);

        yearBox.setPreviewText("yy");
        add(yearBox);
        monthBox.setPreviewText("mm");
        add(monthBox);
        dayBox.setPreviewText("dd");
        add(dayBox);
        hourBox.setPreviewText("hr");
        add(hourBox);
        minBox.setPreviewText("min");
        add(minBox);
    }

    public t_Date getDate() {
        if(!yearBox.getText().equals("") && !monthBox.getText().equals("") && !dayBox.getText().equals("") &&
                !hourBox.getText().equals("") && !minBox.getText().equals(""))
        return new t_Date(Integer.parseInt(yearBox.getText()), Integer.parseInt(monthBox.getText()), Integer.parseInt(dayBox.getText()),
                new t_Date.t_Time(Integer.parseInt(hourBox.getText()), Integer.parseInt(minBox.getText())));
        else
            return null;
    }
}
