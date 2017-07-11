package com.commercetools.sunrise.myaccount.authentication.resetpassword.reset;

import com.google.inject.ImplementedBy;

/**
 * The data required to reset a customers password.
 */
@ImplementedBy(DefaultResetPasswordFormData.class)
public interface ResetPasswordFormData {
    /**
     * The new password.
     *
     * @return the new password
     */
    String newPassword();

    /**
     * The confirm password.
     *
     * @return the repeated new password
     */
    String confirmPassword();
}
