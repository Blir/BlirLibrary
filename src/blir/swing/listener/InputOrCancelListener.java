package blir.swing.listener;

/**
 *
 * @author Blir
 */
public interface InputOrCancelListener extends InputListener {

    /**
     * Called when the user cancels their input.
     */
    public void onCancel();
}
