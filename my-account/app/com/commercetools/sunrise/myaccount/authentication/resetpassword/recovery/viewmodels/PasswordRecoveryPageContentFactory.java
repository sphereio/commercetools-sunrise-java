package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.PageTitleResolver;
import com.commercetools.sunrise.framework.viewmodels.content.FormPageContentFactory;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.PasswordRecoveryFormData;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.reset.ResetPasswordFormData;
import play.data.Form;

import javax.inject.Inject;

/**
 * Creates the page content for resetting the customers password.
 *
 * @see PasswordRecoveryPageContent
 * @see ResetPasswordFormData
 */
public class PasswordRecoveryPageContentFactory extends FormPageContentFactory<PasswordRecoveryPageContent, Void, PasswordRecoveryFormData> {

    private final PageTitleResolver pageTitleResolver;

    @Inject
    protected PasswordRecoveryPageContentFactory(final PageTitleResolver pageTitleResolver) {
        this.pageTitleResolver = pageTitleResolver;
    }

    protected final PageTitleResolver getPageTitleResolver() {
        return pageTitleResolver;
    }

    @Override
    protected PasswordRecoveryPageContent newViewModelInstance(final Void input, final Form<? extends PasswordRecoveryFormData> form) {
        return new PasswordRecoveryPageContent();
    }

    @Override
    protected void fillTitle(final PasswordRecoveryPageContent viewModel, final Void input, final Form<? extends PasswordRecoveryFormData> form) {
        viewModel.setTitle(pageTitleResolver.getOrEmpty("my-account:paswordRecovery.title"));
    }

    public final PasswordRecoveryPageContent create(final Form<? extends PasswordRecoveryFormData> form) {
        return super.create(null, form);
    }

    @Override
    protected final void initialize(final PasswordRecoveryPageContent viewModel, final Void input, final Form<? extends PasswordRecoveryFormData> form) {
        super.initialize(viewModel, input, form);
        fillResetPasswordForm(viewModel, input, form);
    }

    private void fillResetPasswordForm(final PasswordRecoveryPageContent viewModel, final Void input, final Form<? extends PasswordRecoveryFormData> form) {
        viewModel.setPasswordRecoveryForm(form);
    }
}
