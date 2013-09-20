package blir.swing.listener;

/**
 * Used to listen to the user responding to a prompt with yes or no.
 *
 * @author Blir
 */
public interface YesOrNoListener extends Listener {

    /**
     * Called when the user responds yes.
     */
    public void onYes();

    /**
     * Called when the user responds no.
     */
    public void onNo();
}
