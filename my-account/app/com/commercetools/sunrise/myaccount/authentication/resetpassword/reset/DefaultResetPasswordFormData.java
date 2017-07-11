package com.commercetools.sunrise.myaccount.authentication.resetpassword.reset;

import play.data.validation.Constraints;

public class DefaultResetPasswordFormData implements ResetPasswordFormData {
    @Constraints.MinLength(1)
    private String newPassword;

    @Constraints.MinLength(1)
    private String confirmPassword;

    @Override
    public String newPassword() {
        return newPassword;
    }

    @Override
    public String confirmPassword() {
        return confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
