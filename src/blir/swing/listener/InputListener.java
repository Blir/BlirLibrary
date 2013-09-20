package blir.swing.listener;

/**
 * An interface for listening for String input from a user.
 * 
 * @author Blir
 */
public interface InputListener extends Listener {

    /**
     * Called when the input is made.
     * 
     * @param input The input that has been made
     */
    public void onInput(String input);
}
