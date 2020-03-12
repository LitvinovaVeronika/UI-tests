package com.agisoft.utils.email;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Date;
import java.util.Properties;

class Mailbox {

    private Folder inbox;
    private Store store;

    Mailbox(String email, String password) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getInstance(props);
        store = session.getStore();
        store.connect("imap.yandex.ru", email, password);
        getInbox();
    }

    Message findMessageContains(String subject, Date requestDate) throws Exception {
        Message[] messages = inbox.getMessages();
        for (int i = messages.length - 1; i >= 0 && i >= messages.length - 30; i--)
            if (messages[i].getSubject().contains(subject) && messages[i].getReceivedDate().after(requestDate))
                return messages[i];
        return null;
    }

    void updateInbox() throws Exception {
        inbox.close(false);
        getInbox();
    }

    private void getInbox() throws Exception {
        inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
    }

    void close() throws Exception {
        inbox.close(true);
        store.close();
    }
}
