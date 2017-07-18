package com.commercetools.sunrise.framework.email;

import com.google.inject.Provider;
import io.commercetools.sunrise.email.EmailSender;
import io.commercetools.sunrise.email.smtp.SmtpAuthEmailSender;
import io.commercetools.sunrise.email.smtp.SmtpConfiguration;
import play.Configuration;

import javax.inject.Inject;
import java.util.concurrent.ForkJoinPool;


public class EmailSenderProvider implements Provider<EmailSender> {
    private final SmtpConfiguration smtpConfiguration;
    private final ForkJoinPool executor;
    private final int timeoutMs;

    @Inject
    protected EmailSenderProvider(final Configuration configuration) {
        final String host = configuration.getString("application.smtp.host");
        final int port = configuration.getInt("application.smtp.port", 25);
        final String username = configuration.getString("application.smtp.username");
        final String password = configuration.getString("application.smtp.password");
        final String security = configuration.getString("application.smtp.security");
        timeoutMs = configuration.getInt("application.smtp.timeoutMs", 10 * 1000);
        smtpConfiguration =
                new SmtpConfiguration(host, port, SmtpConfiguration.TransportSecurity.valueOf(security), username, password);
        executor = new ForkJoinPool(5);
    }

    @Override
    public EmailSender get() {
        return new SmtpAuthEmailSender(smtpConfiguration, executor, timeoutMs);
    }
}
