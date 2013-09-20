package blir.swing.listener;

/**
 * Used to listen to the user inputting new passwords.
 *
 * @author Blir
 */
public interface NewPasswordListener extends InputOrCancelListener {

    /**
     * Called if the passwords do not match.
     */
    public void onPasswordMismatch();
}
