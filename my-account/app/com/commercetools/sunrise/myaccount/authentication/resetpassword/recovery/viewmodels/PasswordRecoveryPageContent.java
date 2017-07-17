package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.reset.ResetPasswordFormData;
import play.data.Form;

/**
 * The rest password page contains a {@link ResetPasswordFormData}.
 */
public class PasswordRecoveryPageContent extends PageContent {
    private Form<?> passwordRecoveryForm;

    public Form<?> getPasswordRecoveryForm() {
        return passwordRecoveryForm;
    }

    public void setPasswordRecoveryForm(final Form<?> passwordRecoveryForm) {
        this.passwordRecoveryForm = passwordRecoveryForm;
    }
}
