package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels;

import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;

import javax.inject.Inject;

/**
 * Creates the page content to render the password reset email content.
 */
public class PasswordResetEmailPageContentFactory extends SimpleViewModelFactory<PasswordResetEmailPageContent, String> {
    private final I18nIdentifierResolver i18nIdentifierResolver;

    @Inject
    protected PasswordResetEmailPageContentFactory(final I18nIdentifierResolver i18nIdentifierResolver) {
        this.i18nIdentifierResolver = i18nIdentifierResolver;
    }

    @Override
    protected PasswordResetEmailPageContent newViewModelInstance(final String passwordResetUrl) {
        return new PasswordResetEmailPageContent();
    }

    @Override
    protected final void initialize(final PasswordResetEmailPageContent viewModel, final String passwordResetUrl) {
        fillPasswordResetUrl(viewModel, passwordResetUrl);
        fillSubject(viewModel);
    }

    protected void fillSubject(final PasswordResetEmailPageContent viewModel) {
        viewModel.setSubject(i18nIdentifierResolver.resolveOrEmpty("my-account:forgotPassword.email.subject"));
    }

    protected void fillPasswordResetUrl(final PasswordResetEmailPageContent viewModel, final String passwordResetUrl) {
        viewModel.setPasswordResetUrl(passwordResetUrl);
    }
}
