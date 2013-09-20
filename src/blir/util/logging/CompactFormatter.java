package blir.util.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author Blir
 */
public class CompactFormatter extends Formatter {

	/*
	 * Used to format the time at which the LogRecord was produced.
	 */
    private final Calendar CAL;
	 /*
	  * Used to add line separators to the formatted String.
	  */
    private final String LINE_SEPARATOR;

	 /*
	  * Creates a new CompactFormatter.
	  */
    public CompactFormatter() {
        CAL = Calendar.getInstance();
        LINE_SEPARATOR = System.getProperty("line.separator");
    }

    @Override
	 /*
	  * Formats the given LogRecord into a String.
	  * @param record the LogRecord to format
	  * @returns the formatted String
	  */
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        CAL.setTimeInMillis(record.getMillis());
        String hour = String.valueOf(CAL.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(CAL.get(Calendar.MINUTE));
        String second = String.valueOf(CAL.get(Calendar.SECOND));
        sb
                .append(hour.length() == 1 ? "0" : "")
                .append(hour)
                .append(":")
                .append(minute.length() == 1 ? "0" : "")
                .append(minute)
                .append(":")
                .append(second.length() == 1 ? "0" : "")
                .append(second)
                .append(" [")
                .append(record.getLevel())
                .append("] ");
        if (record.getMessage() != null) {
            String msg = record.getMessage();
            if (record.getParameters() != null) {
                for (int idx = 0; idx < record.getParameters().length; idx++) {
                    msg = msg.replace("{" + idx + "}",
                            String.valueOf(record.getParameters()[idx]));
                }
            }
            sb.append(msg);
        }
        if (record.getThrown() != null) {
            StringWriter sw;
            PrintWriter pw = null;
            try {
                sw = new StringWriter();
                pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                sb
                        .append(LINE_SEPARATOR)
                        .append(sw.toString());
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }
        }
        return sb.append(LINE_SEPARATOR).toString();
    }
}
