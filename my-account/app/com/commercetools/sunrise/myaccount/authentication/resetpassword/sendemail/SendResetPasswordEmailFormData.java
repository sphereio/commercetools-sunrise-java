package com.commercetools.sunrise.myaccount.authentication.resetpassword.sendemail;

import com.google.inject.ImplementedBy;

/**
 * The data required to send a reset password email to a customer.
 */
@ImplementedBy(DefaultSendResetPasswordEmailFormData.class)
public interface SendResetPasswordEmailFormData {

    /**
     * The customer email address for which a password reset email should be send.
     *
     * @return the customer email.
     */
    String email();
}
