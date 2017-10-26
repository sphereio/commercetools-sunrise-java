package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.reset.ResetPasswordFormData;
import play.data.Form;

import java.util.List;

/**
 * The rest password page contains a {@link ResetPasswordFormData}.
 */
public class RecoverPasswordPageContent extends PageContent {
    private Form<?> passwordRecoveryForm;
    private  List<String> messages ;

    public Form<?> getPasswordRecoveryForm() {
        return passwordRecoveryForm;
    }

    public void setPasswordRecoveryForm(final Form<?> passwordRecoveryForm) {
        this.passwordRecoveryForm = passwordRecoveryForm;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
