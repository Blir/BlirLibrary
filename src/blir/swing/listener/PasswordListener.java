package blir.swing.listener;

/**
 * Used instead of InputListener if you want to let the user indicate if they
 * forgot their password.
 *
 * @author Blir
 */
public interface PasswordListener extends InputListener {

    /**
     * Called when the user indicates that they have forgotten their password.
     */
    public void onPasswordForgotten();
}
