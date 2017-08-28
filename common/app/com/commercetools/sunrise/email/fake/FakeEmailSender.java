package com.commercetools.sunrise.email.fake;

import io.commercetools.sunrise.email.EmailSender;
import io.commercetools.sunrise.email.MessageEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public final class FakeEmailSender implements EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    @Nonnull
    @Override
    public CompletionStage<String> send(@Nonnull final MessageEditor messageEditor) {
        final Session session = Session.getInstance(new Properties());
        final MimeMessage message = new MimeMessage(session);
        final String emailId = UUID.randomUUID().toString();
        try {
            messageEditor.edit(message);
            LOGGER.debug("Writing email " + emailId);
            try {
                message.writeTo(new FileOutputStream(createEmailFile(emailId)));
            } catch (FileNotFoundException e) {
                LOGGER.error("Could not write email file", e);
            }
        } catch (Exception e) {
            LOGGER.error("Could not send fake email", e);
        }
        return completedFuture(emailId);
    }

    private File createEmailFile(final String emailId) {
        return new File("email-" + emailId + ".eml");
    }
}
