package blir.mail;

import java.io.File;
import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * A utility class for sending an email in a single method call. <p>
 * Currently only supports Yahoo!, GMail, Hotmail, and Facebook. Implements the
 * JavaMail API.
 *
 * @author Blir
 */
public class QuickMail {

    /**
     * Controls whether debug data is printed to System.out
     * during execution.
     */
    private static boolean debug = false;

    /**
     * Sends an email with the specified subject and body to the specified
     * recipients using the given password and username.
     *
     * @param subject the subject of the email
     * @param body the body of the email
     * @param user the username of the email account this is being sent from
     * @param pass the password of the email account this is being sent from
     * @param recipients the recipients of this email
     * @throws MessagingException
     * @throws UnsupportedOperationException if the sender is not a Yahoo!,
     * Gmail, Hotmail, or Facebook account.
     */
    public static void email(String subject, String body, String user, String pass, String[] recipients) throws MessagingException {
        Authenticator auth = new Authenticator(user, pass);

        Properties p = System.getProperties();

        p.setProperty("mail.smtp.submitter", auth.getPasswordAuthentication().getUserName());
        p.setProperty("mail.smtp.auth", "true");

        if (user.toLowerCase().endsWith("@yahoo.com") || user.toLowerCase().endsWith("@ymail.com") || user.toLowerCase().endsWith("@rocktetmail.com")) {
            p.setProperty("mail.smtp.host", "smtp.mail.yahoo.com");
        } else if (user.toLowerCase().endsWith("@gmail.com")) {
            p.setProperty("mail.smtp.host", "smtp.gmail.com");
        } else if (user.toLowerCase().endsWith("@hotmail.com")) {
            p.setProperty("mail.smtp.host", "smtp.live.com");
        } else if (user.toLowerCase().endsWith("@facebook.com")) {
            p.setProperty("mail.smtp.host", "smtpout.mx.facebook.com");
        } else {
            throw new UnsupportedOperationException("Unsupported smtp host");
        }

        Session session = Session.getInstance(p, auth);
        session.setDebug(debug);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(user));

        for (String recip : recipients) {
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recip));
        }

        msg.setSubject(subject);
        msg.setText(body);

        Transport.send(msg);
    }

    /**
     * Sends an email with the specified subject and body to the specified
     * recipients using the given password and username.
     *
     * @param subject the subject of the email
     * @param body the body of the email
     * @param user the username of the email account this is being sent from
     * @param pass the password of the email account this is being sent from
     * @param recipients the recipients of this email
	  * @param attachments Files attached to this email
     * @throws MessagingException
     * @throws UnsupportedOperationException if the sender is not a Yahoo!,
     * Gmail, Hotmail, or Facebook account.
     */
    public static void email(String subject, String body, String user, String pass, String[] recipients, File[] attachments) throws MessagingException {
        Authenticator authenticator = new Authenticator(user, pass);

        Properties p = System.getProperties();

        p.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
        p.setProperty("mail.smtp.auth", "true");

        if (user.toLowerCase().endsWith("@yahoo.com") || user.toLowerCase().endsWith("@ymail.com") || user.toLowerCase().endsWith("@rocktetmail.com")) {
            p.setProperty("mail.smtp.host", "smtp.mail.yahoo.com");
        } else if (user.toLowerCase().endsWith("@gmail.com")) {
            p.setProperty("mail.smtp.host", "smtp.gmail.com");
        } else if (user.toLowerCase().endsWith("@hotmail.com")) {
            p.setProperty("mail.smtp.host", "smtp.live.com");
        } else if (user.toLowerCase().endsWith("@facebook.com")) {
            p.setProperty("mail.smtp.host", "smtpout.mx.facebook.com");
        } else {
            throw new UnsupportedOperationException("Unsupported smtp host");
        }

        Session session = Session.getInstance(p, authenticator);
        session.setDebug(debug);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(user));

        for (String recip : recipients) {
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recip));
        }

        msg.setSubject(subject);
        msg.setText(body);

        Multipart mp = new MimeMultipart();
        for (File attachment : attachments) {
            BodyPart bp = new MimeBodyPart();
            bp.setDataHandler(new DataHandler(new FileDataSource(attachment)));
            bp.setFileName(attachment.getName());
            mp.addBodyPart(bp);
        }

        msg.setContent(mp);
        Transport.send(msg);
    }

    private static class Authenticator extends javax.mail.Authenticator {

        private PasswordAuthentication authentication;

        public Authenticator(String user, String pass) {
            super();
            authentication = new PasswordAuthentication(user, pass);
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }
    }

    /**
     * Sets the debug mode for this class.
     *
     * @param debug the value to set debug mode to
     */
    public static void setDebug(boolean debug) {
        QuickMail.debug = debug;
    }
}
