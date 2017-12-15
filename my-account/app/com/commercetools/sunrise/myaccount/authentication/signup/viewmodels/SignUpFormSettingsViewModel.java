package com.commercetools.sunrise.myaccount.authentication.signup.viewmodels;

import com.commercetools.sunrise.core.viewmodels.ViewModel;
import com.commercetools.sunrise.core.viewmodels.forms.titles.TitleFormFieldViewModel;

public class SignUpFormSettingsViewModel extends ViewModel {

    private TitleFormFieldViewModel title;

    public SignUpFormSettingsViewModel() {
    }

    public TitleFormFieldViewModel getTitle() {
        return title;
    }

    public void setTitle(final TitleFormFieldViewModel title) {
        this.title = title;
    }
}
