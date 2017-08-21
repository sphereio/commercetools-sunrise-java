package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.viewmodels;

import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;

import javax.inject.Inject;

/**
 * Creates the page content to render the password reset email content.
 */
public class RecoverPasswordEmailPageContentFactory extends SimpleViewModelFactory<RecoverPasswordEmailPageContent, String> {
    private final I18nIdentifierResolver i18nIdentifierResolver;

    @Inject
    protected RecoverPasswordEmailPageContentFactory(final I18nIdentifierResolver i18nIdentifierResolver) {
        this.i18nIdentifierResolver = i18nIdentifierResolver;
    }

    @Override
    protected RecoverPasswordEmailPageContent newViewModelInstance(final String passwordResetUrl) {
        return new RecoverPasswordEmailPageContent();
    }

    @Override
    protected final void initialize(final RecoverPasswordEmailPageContent viewModel, final String passwordResetUrl) {
        fillPasswordResetUrl(viewModel, passwordResetUrl);
        fillSubject(viewModel, passwordResetUrl);
    }

    protected void fillSubject(final RecoverPasswordEmailPageContent viewModel, final String passwordResetUrl) {
        viewModel.setSubject(i18nIdentifierResolver.resolveOrEmpty("my-account:forgotPassword.email.subject"));
    }

    protected void fillPasswordResetUrl(final RecoverPasswordEmailPageContent viewModel, final String passwordResetUrl) {
        viewModel.setPasswordResetUrl(passwordResetUrl);
    }
}
