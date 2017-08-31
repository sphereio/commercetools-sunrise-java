package email.smtp;

import com.commercetools.sunrise.email.fake.FakeEmailSender;
import com.google.inject.Provider;
import io.commercetools.sunrise.email.EmailSender;
import io.commercetools.sunrise.email.smtp.SmtpAuthEmailSender;

import javax.inject.Inject;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * If the SMTP configuration has been defined, it provides a {@link SmtpAuthEmailSender}.
 * Otherwise a {@link FakeEmailSender} is injected instead.
 */
public final class EmailSenderProvider implements Provider<EmailSender> {
    private final SmtpEmailSenderSettings smtpEmailSenderSettings;

    @Inject
    EmailSenderProvider(final SmtpEmailSenderSettings smtpEmailSenderSettings) {
        this.smtpEmailSenderSettings = smtpEmailSenderSettings;
    }

    @Override
    public EmailSender get() {
        if (isSmtpSettingsDefined()) {
            final Executor executor = ForkJoinPool.commonPool();
            return new SmtpAuthEmailSender(smtpEmailSenderSettings.smtpConfiguration(), executor, smtpEmailSenderSettings.timeoutMs());
        } else {
            return new FakeEmailSender();
        }
    }

    private boolean isSmtpSettingsDefined() {
        return smtpEmailSenderSettings.smtpConfiguration().getHost() != null;
    }
}
