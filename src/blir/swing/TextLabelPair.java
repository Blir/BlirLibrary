package blir.swing;

import java.awt.Rectangle;
import javax.swing.*;

/**
 *
 * @author Blir
 */
public class TextLabelPair {
    
    private JTextArea text;
    private JLabel label;
    private Orientation orientation;

    public TextLabelPair(JTextArea text, JLabel label) {
        this.text = text;
        this.label = label;
    }

    public TextLabelPair(String contents, String labelName, int i, int i2, int i3, int i4, Orientation orientation) {
        text = new JTextArea(contents);
        label = new JLabel(labelName);
        this.orientation = orientation;
        switch (this.orientation) {
            case LEFT_TO_RIGHT:
                label.setBounds(i, i2, i3, i4);
                text.setBounds(i + i3, i2, i3, i4);
                break;
            case TOP_TO_BOTTOM:
                label.setBounds(i, i2, i3, i4);
                text.setBounds(i, i2 + i4, i3, i4);
                break;
        }
    }

    public TextLabelPair(String contents, String labelName, Rectangle rect, Orientation orientation) {
        text = new JTextArea(contents);
        label = new JLabel(labelName);
        this.orientation = orientation;
        switch (this.orientation) {
            case LEFT_TO_RIGHT:
                label.setBounds(rect);
                text.setBounds(rect.x + rect.width, rect.y, rect.width, rect.height);
                break;
            case TOP_TO_BOTTOM:
                label.setBounds(rect);
                text.setBounds(rect.x, rect.y + rect.height, rect.width, rect.height);
                break;
        }
    }
    
    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public JTextArea getTextArea() {
        return text;
    }

    public JLabel getLabel() {
        return label;
    }
    
    public String getTextAreaText() {
        return text.getText();
    }
    
    public String getLabelText() {
        return label.getText();
    }

    public void repaint() {
        text.repaint();
        label.repaint();
    }
}
