package blir.swing.event;

/**
 * Used to describe an event in which the user confirmed or canceled an action.
 * 
 * @author Blir
 * @deprecated 
 */
public class ConfirmOrCancelEvent extends Event {
    public static enum Result {
        CONFIRMED, CANCELED
    }
    
    private Result result;
    
    public ConfirmOrCancelEvent(Result result) {
        this.result = result;
    }
    
    public Result getResult() {
        return result;
    }
}
