package blir.swing.event;

/**
 * Used to describe an event in which a user pressed a yes or no button.
 * 
 * @author Blir
 * @deprecated 
 */
public class YesOrNoEvent extends Event {
    public static enum Result {
        YES, NO
    }
    
    private Result result;
    
    public YesOrNoEvent(Result result) {
        this.result = result;
    }
    
    public Result getResult() {
        return result;
    }
}
