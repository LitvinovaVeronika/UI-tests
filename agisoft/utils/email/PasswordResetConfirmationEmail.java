package com.agisoft.utils.email;

import com.agisoft.account.utils.pages.AccountBasePage;
import com.agisoft.account.utils.pages.ResetPasswordPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.codeborne.selenide.Selenide.sleep;
import static java.lang.System.currentTimeMillis;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class PasswordResetConfirmationEmail {

    private Message message;
    private Mailbox mailbox;
    private Date emailSendingDate;

    private static final String SUBJECT = "Password reset confirmation";

    private PasswordResetConfirmationEmail(String email, String password, Date emailSendingDate) throws Exception {
        this.mailbox = new Mailbox(email, password);
        this.emailSendingDate = emailSendingDate;
        message = mailbox.findMessageContains(SUBJECT, emailSendingDate);
        if (isNull(message))
            waitForAppearance(TimeUnit.SECONDS.toMillis(10));
    }

    private void delete() throws Exception {
        message.setFlag(Flags.Flag.DELETED, true);
        mailbox.close();
    }

    private Multipart findTextPart (Multipart multipart) throws Exception {
        if (multipart.getBodyPart(0).isMimeType("text/*"))
            return multipart;
        return findTextPart((Multipart) multipart.getBodyPart(0).getContent());
    }

    private String getLink () {

        if (isNull(message))
            return null;

        try {
            String html = null;
            Multipart multipart = findTextPart((Multipart) message.getContent());

            for (int i = 0; i < multipart.getCount(); i++) {
                Part part = multipart.getBodyPart(i);
                if (part.isMimeType("text/html")) {
                    html = part.getContent().toString();
                    break;
                }
            }

            Document document = Jsoup.parse(html);
            Elements withLink = document.getElementsByAttributeValueStarting(
                    "href", AccountBasePage.BASE_URL + ResetPasswordPage.RELATIVE_URL);
            return withLink.get(0).attr("href");

        } catch (Exception e) {
            return null;
        }
    }

    private boolean exist() throws Exception {
        mailbox.updateInbox();
        message = mailbox.findMessageContains(SUBJECT, emailSendingDate);
        return nonNull(message);
    }

    private void checkTimeout(long startTime, long timeout) throws TimeoutException {
        if (timeout > 0L && currentTimeMillis() > startTime + timeout) {
            throw new TimeoutException(String.format("timed out after %s milliseconds", timeout));
        }
    }

    private void waitForAppearance(long timeout) throws Exception {
        if (timeout <= 0L) {
            throw new IllegalArgumentException("Timeout must be > 0");
        }
        long startTime = System.currentTimeMillis();
        while (true) {

            if (exist())
                break;

            checkTimeout(startTime, timeout);
            sleep(2000);
        }
    }

    public static String getEmailLink (String email, String password, Date passwordRequestDate) throws Exception {
        PasswordResetConfirmationEmail message =
                new PasswordResetConfirmationEmail(email, password, passwordRequestDate);
        String link = message.getLink();
        message.delete();
        return link;
    }
}
