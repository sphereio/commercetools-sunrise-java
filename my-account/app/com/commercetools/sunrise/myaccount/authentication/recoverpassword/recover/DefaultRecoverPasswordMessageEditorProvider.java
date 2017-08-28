package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover;

import com.commercetools.sunrise.framework.reverserouters.myaccount.recoverpassword.RecoverPasswordReverseRouter;
import com.commercetools.sunrise.framework.template.engine.TemplateContext;
import com.commercetools.sunrise.framework.template.engine.TemplateEngine;
import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import io.commercetools.sunrise.email.MessageEditor;
import io.sphere.sdk.customers.CustomerToken;
import play.mvc.Http;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Default provider which obtains the email contents from the template {@code password-reset-email}.
 * Subject and recipients are populated too.
 *
 * If your email requires any additional data (e.g. {@code from} field) you can extend this class, override the method
 * {@link #editMessage(MimeMessage, CustomerToken, RecoverPasswordFormData)} and add the necessary
 * lines of code. Alternatively you can create your own implementation of {@link RecoverPasswordMessageEditorProvider}.
 */
public class DefaultRecoverPasswordMessageEditorProvider implements RecoverPasswordMessageEditorProvider {

    private final Locale locale;
    private final TemplateEngine templateEngine;
    private final I18nIdentifierResolver i18nIdentifierResolver;
    private final RecoverPasswordReverseRouter recoverPasswordReverseRouter;

    @Inject
    protected DefaultRecoverPasswordMessageEditorProvider(final Locale locale, final TemplateEngine templateEngine,
                                                          final I18nIdentifierResolver i18nIdentifierResolver,
                                                          final RecoverPasswordReverseRouter recoverPasswordReverseRouter) {
        this.locale = locale;
        this.templateEngine = templateEngine;
        this.i18nIdentifierResolver = i18nIdentifierResolver;
        this.recoverPasswordReverseRouter = recoverPasswordReverseRouter;
    }

    @Override
    public CompletionStage<MessageEditor> get(final CustomerToken resetPasswordToken, final RecoverPasswordFormData formData) {
        return completedFuture(msg -> editMessage(msg, resetPasswordToken, formData));
    }

    protected void editMessage(final MimeMessage msg, final CustomerToken resetPasswordToken, final RecoverPasswordFormData formData) throws MessagingException {
        msg.setRecipients(Message.RecipientType.TO, formData.email());
        msg.setSubject(createSubject(), "UTF-8");
        msg.setContent(createEmailContent(resetPasswordToken), "text/html");
    }

    private String createSubject() {
        return i18nIdentifierResolver.resolveOrEmpty("my-account:forgotPassword.email.subject");
    }

    private String createEmailContent(final CustomerToken resetPasswordToken) {
        final PageData pageData = createPageData(resetPasswordToken);
        final TemplateContext templateContext = new TemplateContext(pageData, Collections.singletonList(locale), null);
        return templateEngine.render("password-reset-email", templateContext);
    }

    private PageData createPageData(final CustomerToken resetPasswordToken) {
        final PageData pageData = new PageData();
        pageData.put("passwordResetUrl", createPasswordResetUrl(resetPasswordToken));
        return pageData;
    }

    private String createPasswordResetUrl(final CustomerToken resetPasswordToken) {
        final Http.Request request = Http.Context.current().request();
        return recoverPasswordReverseRouter.resetPasswordPageCall(resetPasswordToken.getValue())
                .absoluteURL(request);
    }
}
