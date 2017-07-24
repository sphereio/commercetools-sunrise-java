package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.content.PageContent;

/**
 * The page content for the password reset email template.
 */
public class PasswordResetEmailPageContent extends PageContent {
    private String passwordResetUrl;
    private String subject;

    public String getPasswordResetUrl() {
        return passwordResetUrl;
    }

    public void setPasswordResetUrl(final String passwordResetUrl) {
        this.passwordResetUrl = passwordResetUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }
}