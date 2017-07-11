package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import play.data.validation.Constraints.Email;

/**
 * Default implementation of {@link PasswordRecoveryFormData}
 */
public class DefaultPasswordRecoveryFormData implements PasswordRecoveryFormData {
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
