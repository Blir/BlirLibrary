package blir.swing.listener;

/**
 * Used to listen to the user confirming or canceling an action.
 *
 * @author Blir
 */
public interface ConfirmOrCancelListener extends Listener {

    /**
     * Called when the user confirms.
     */
    public void onConfirm();

    /**
     * Called when the user cancels.
     */
    public void onCancel();
}
