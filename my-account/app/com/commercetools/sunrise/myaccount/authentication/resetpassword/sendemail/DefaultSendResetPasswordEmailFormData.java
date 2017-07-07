package com.commercetools.sunrise.myaccount.authentication.resetpassword.sendemail;

import play.data.validation.Constraints.Email;

/**
 * Default implementation of {@link SendResetPasswordEmailFormData}
 */
public class DefaultSendResetPasswordEmailFormData implements SendResetPasswordEmailFormData {
    @Email
    private String email;

    @Override
    public String email() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
