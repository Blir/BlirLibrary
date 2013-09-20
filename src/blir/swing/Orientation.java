package blir.swing;

/**
 * Used to describe the orientation of related labels and text fields in frames.
 * Used by the CWindow class to quickly set up a frame under general standards.
 * 
 * @author Blir
 */
public enum Orientation {

    /**
     * Used to describe the orientation in a frame where the label relating to a
     * text field is to the left of the text field, and the text field to the
     * right of the label.
     */
    LEFT_TO_RIGHT,
    /**
     * Used to describe the orientation in a frame where the label relating to a
     * text field is above the text field, and the text field below the label.
     */
    TOP_TO_BOTTOM
}
