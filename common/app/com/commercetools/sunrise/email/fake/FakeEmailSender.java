package com.commercetools.sunrise.email.fake;

import io.commercetools.sunrise.email.EmailSender;
import io.commercetools.sunrise.email.MessageEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public final class FakeEmailSender implements EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeEmailSender.class);

    @Nonnull
    @Override
    public CompletionStage<String> send(@Nonnull final MessageEditor messageEditor) {
        final Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "" + true);
        final Session session = Session.getInstance(props);
        final MimeMessage message = new MimeMessage(session);
        try {
            messageEditor.edit(message);
            LOGGER.info("Sending email");
            message.writeTo(new FileOutputStream(new File("mail.eml")));
        } catch (Throwable t) {
            LOGGER.error("Error", t);
        }
//        msg.setSubject(recoverPasswordEmailPageContent.getSubject(), "UTF-8");
//        msg.setContent(emailText, "text/html");
//        try {
//            final String recipients = Arrays.stream(message.getAllRecipients())
//                    .map(Address::toString)
//                    .collect(joining(","));
//            LOGGER.debug("TO: " + recipients + "\nSUBJECT: " + message.getSubject());

        return completedFuture("fake-email");
    }
}
