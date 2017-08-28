package email.smtp;

import com.google.inject.Provider;
import io.commercetools.sunrise.email.EmailSender;
import io.commercetools.sunrise.email.smtp.SmtpAuthEmailSender;

import javax.inject.Inject;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;


public final class SmtpEmailSenderProvider implements Provider<EmailSender> {
    private final SmtpEmailSenderSettings smtpEmailSenderSettings;

    @Inject
    SmtpEmailSenderProvider(final SmtpEmailSenderSettings smtpEmailSenderSettings) {
        this.smtpEmailSenderSettings = smtpEmailSenderSettings;
    }

    @Override
    public EmailSender get() {
        final Executor executor = ForkJoinPool.commonPool();
        return new SmtpAuthEmailSender(smtpEmailSenderSettings.smtpConfiguration(), executor, smtpEmailSenderSettings.timeoutMs());
    }
}
