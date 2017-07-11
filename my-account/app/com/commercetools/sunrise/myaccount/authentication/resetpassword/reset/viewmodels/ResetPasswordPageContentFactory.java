package com.commercetools.sunrise.myaccount.authentication.resetpassword.reset.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.PageTitleResolver;
import com.commercetools.sunrise.framework.viewmodels.content.FormPageContentFactory;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.reset.ResetPasswordFormData;
import play.data.Form;

import javax.inject.Inject;

/**
 * Creates the page content for resetting the customers password.
 *
 * @see ResetPasswordPageContent
 * @see ResetPasswordFormData
 */
public class ResetPasswordPageContentFactory extends FormPageContentFactory<ResetPasswordPageContent, String, ResetPasswordFormData> {

    private final PageTitleResolver pageTitleResolver;

    @Inject
    protected ResetPasswordPageContentFactory(final PageTitleResolver pageTitleResolver) {
        this.pageTitleResolver = pageTitleResolver;
    }

    protected final PageTitleResolver getPageTitleResolver() {
        return pageTitleResolver;
    }

    @Override
    protected ResetPasswordPageContent newViewModelInstance(final String input, final Form<? extends ResetPasswordFormData> form) {
        return new ResetPasswordPageContent();
    }

    @Override
    protected void fillTitle(final ResetPasswordPageContent viewModel, final String input, final Form<? extends ResetPasswordFormData> form) {
        viewModel.setTitle(pageTitleResolver.getOrEmpty("my-account:resetPassword.title"));
    }

    public final ResetPasswordPageContent create(final Form<? extends ResetPasswordFormData> form) {
        return super.create(null, form);
    }

    @Override
    protected final void initialize(final ResetPasswordPageContent viewModel, final String input, final Form<? extends ResetPasswordFormData> form) {
        super.initialize(viewModel, input, form);
        fillResetPasswordForm(viewModel, input, form);
    }

    private void fillResetPasswordForm(final ResetPasswordPageContent viewModel, final String input, final Form<? extends ResetPasswordFormData> form) {
        viewModel.setResetPasswordForm(form);
    }
}
