package com.commercetools.sunrise.myaccount.authentication.changepassword.viemodels;

import com.commercetools.sunrise.myaccount.authentication.AuthenticationPageContent;
import play.data.Form;

public class ChangePasswordPageContent extends AuthenticationPageContent {

    private Form<?> changePasswordForm;

    public Form<?> getChangePasswordForm() {
        return changePasswordForm;
    }

    public void setChangePasswordForm(Form<?> changePasswordForm) {
        this.changePasswordForm = changePasswordForm;
    }
}
