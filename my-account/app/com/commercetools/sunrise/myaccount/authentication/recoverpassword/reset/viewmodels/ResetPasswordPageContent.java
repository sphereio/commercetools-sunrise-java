package com.commercetools.sunrise.myaccount.authentication.recoverpassword.reset.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.reset.ResetPasswordFormData;
import play.data.Form;

/**
 * The rest password page contains a {@link ResetPasswordFormData}.
 */
public class ResetPasswordPageContent extends PageContent {
    private Form<?> resetPasswordForm;

    public Form<?> getResetPasswordForm() {
        return resetPasswordForm;
    }

    public void setResetPasswordForm(final Form<?> resetPasswordForm) {
        this.resetPasswordForm = resetPasswordForm;
    }
}
