package blir.swing;

import java.awt.Rectangle;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Blir
 */
public class CWindow {

    public final static Rectangle LTR_POS_1 = new Rectangle(5, 5, 100, 30);
    public final static Rectangle LTR_POS_2 = new Rectangle(5, 40, 100, 30);
    public final static Rectangle LTR_POS_3 = new Rectangle(5, 75, 100, 30);
    public final static Rectangle LTR_POS_4 = new Rectangle(5, 110, 100, 30);
    public final static Rectangle LTR_OUTPUT_POS = new Rectangle(210, 5, 100, 135);
    public final static int LTR_OUTPUT_MAXCHARS = 11;
    public final static Rectangle TTB_POS_1 = new Rectangle(5, 5, 100, 30);
    public final static Rectangle TTB_POS_2 = new Rectangle(110, 5, 100, 30);
    public final static Rectangle TTB_POS_3 = new Rectangle(215, 5, 100, 30);
    public final static Rectangle TTB_POS_4 = new Rectangle(320, 5, 100, 30);
    public final static Rectangle TTB_OUTPUT_POS = new Rectangle(5, 75, 415, 30);
    private JFrame frame;
    private Orientation orientation;
    private ArrayList<JButton> buttons = new ArrayList<>(0);
    private ArrayList<JLabel> labels = new ArrayList<>(0);
    private ArrayList<TextLabelPair> buttonLabelPairs = new ArrayList<>(0);
    private JTextArea output;
    private JLabel outputLbl;
    private JButton bugfix = new JButton();

    /**
     * Creates a new CWindow with the given name.
     *
     * @param name the name to create the CWindow with
     */
    public CWindow(String name, Orientation orientation) {
        frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        bugfix.setVisible(false);
        this.orientation = orientation;
    }

    public void addButton(String name, ActionListener al, int i, int i2, int i3, int i4) {
        JButton button = new JButton(name);
        buttons.add(button);
        if (al != null) {
            button.addActionListener(al);
        }
        button.setBounds(i, i2, i3, i4);
        frame.remove(bugfix);
        frame.add(button);
        frame.add(bugfix);
    }

    public void addButton(String name, ActionListener al, Rectangle rect) {
        JButton button = new JButton(name);
        buttons.add(button);
        if (al != null) {
            button.addActionListener(al);
        }
        button.setBounds(rect);
        frame.remove(bugfix);
        frame.add(button);
        frame.add(bugfix);
    }

    public void addLabel(String name, int i, int i2, int i3, int i4) {
        JLabel label = new JLabel(name);
        labels.add(label);
        label.setBounds(i, i2, i3, i4);
        frame.remove(bugfix);
        frame.add(label);
        frame.add(bugfix);
    }

    public void addLabel(String name, Rectangle rect) {
        JLabel label = new JLabel(name);
        labels.add(label);
        label.setBounds(rect);
        frame.remove(bugfix);
        frame.add(label);
        frame.add(bugfix);
    }

    public void addTextLabelPair(String content, String labelName, int i, int i2, int i3, int i4) {
        TextLabelPair pair = new TextLabelPair(content, labelName, i, i2, i3, i4, orientation);
        buttonLabelPairs.add(pair);
        frame.remove(bugfix);
        frame.add(pair.getTextArea());
        frame.add(pair.getLabel());
        frame.add(bugfix);
    }

    public void addTextLabelPair(String content, String labelName, Rectangle rect) {
        TextLabelPair pair = new TextLabelPair(content, labelName, rect, orientation);
        buttonLabelPairs.add(pair);
        frame.remove(bugfix);
        frame.add(pair.getTextArea());
        frame.add(pair.getLabel());
        frame.add(bugfix);
    }

    public void configureOutput(int i, int i2, int i3, int i4, String content) {
        output = new JTextArea(content);
        output.setBounds(i, i2, i3, i4);
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        frame.remove(bugfix);
        frame.add(output);
        frame.add(bugfix);
    }

    public void configureOutput(Rectangle rect, String content) {
        output = new JTextArea(content);
        output.setBounds(rect);
        output.setEditable(false);
        output.setLineWrap(true);
        frame.remove(bugfix);
        frame.add(output);
        frame.add(bugfix);
    }

    public void configureOutput(String content) {
        output = new JTextArea(content);
        outputLbl = new JLabel("Output:");
        switch (orientation) {
            case LEFT_TO_RIGHT:
                outputLbl.setBounds(235, 5, 50, 30);
                output.setBounds(210, 40, 100, 100);
                break;
            case TOP_TO_BOTTOM:
                outputLbl.setBounds(5, 75, 50, 30);
                output.setBounds(60, 75, 360, 30);
                break;
        }
        output.setEditable(false);
        output.setLineWrap(true);
        frame.remove(bugfix);
        frame.add(output);
        frame.add(outputLbl);
        frame.add(bugfix);
    }

    public void configure(int i, int i2, int i3, int i4) {
        frame.setBounds(i, i2, i3, i4);
        frame.setVisible(true);
    }

    public void configure(Rectangle rect) {
        frame.setBounds(rect);
        frame.setVisible(true);
    }

    public void configure() {
        switch (orientation) {
            case LEFT_TO_RIGHT:
                frame.setBounds(500, 300, 325, 175);
                break;
            case TOP_TO_BOTTOM:
                frame.setBounds(500, 300, 450, 150);
                break;
        }
        frame.setVisible(true);
    }

    public void output(String content) {
        output.setText(content);
    }

    public void output(Object obj) {
        output.setText(String.valueOf(obj));
    }

    public JButton getButton(String name) {
        for (JButton button : buttons) {
            if (button.getName().equals(name)) {
                return button;
            }
        }
        return null;
    }

    public JLabel getLabel(String name) {
        for (JLabel label : labels) {
            if (label.getName().equals(name)) {
                return label;
            }
        }
        return null;
    }

    public TextLabelPair getTextLabelPairByTextContents(String content) {
        for (TextLabelPair pair : buttonLabelPairs) {
            if (pair.getTextAreaText().equals(content)) {
                return pair;
            }
        }
        return null;
    }

    public TextLabelPair getTextLabelPairByLabelText(String name) {
        for (TextLabelPair pair : buttonLabelPairs) {
            if (pair.getLabelText().equals(name)) {
                return pair;
            }
        }
        return null;
    }

    /**
     * Repaints everything in this CWindow.
     */
    public void repaintAll() {
        frame.repaint();
        for (JButton button : buttons) {
            button.repaint();
        }
        for (JLabel label : labels) {
            label.repaint();
        }
        for (TextLabelPair pair : buttonLabelPairs) {
            pair.repaint();
        }
        output.repaint();
    }
}
