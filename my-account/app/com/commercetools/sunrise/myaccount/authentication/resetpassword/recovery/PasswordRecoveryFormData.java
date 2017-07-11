package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.google.inject.ImplementedBy;

/**
 * The data required to send a reset password email to a customer.
 */
@ImplementedBy(DefaultPasswordRecoveryFormData.class)
public interface PasswordRecoveryFormData {

    /**
     * The customer email address for which a password reset email should be send.
     *
     * @return the customer email.
     */
    String email();
}
