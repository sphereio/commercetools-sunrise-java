package com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword;

import com.commercetools.sunrise.framework.reverserouters.AbstractLocalizedReverseRouter;
import play.mvc.Call;

import javax.inject.Inject;
import java.util.Locale;

public class DefaultResetPasswordReverseRouter extends AbstractLocalizedReverseRouter implements ResetPasswordReverseRouter {
    private final SimpleResetPasswordReverseRouter delegate;

    @Inject
    protected DefaultResetPasswordReverseRouter(final Locale locale, final SimpleResetPasswordReverseRouter delegate) {
        super(locale);
        this.delegate = delegate;
    }

    @Override
    public Call resetPasswordPageCall(final String languageTag, final String token) {
        return delegate.resetPasswordPageCall(languageTag, token);
    }

    @Override
    public Call resetPasswordProcessCall(final String languageTag, final String token) {
        return delegate.resetPasswordProcessCall(languageTag, token);
    }

    @Override
    public Call requestRecoveryEmailProcessCall(final String languageTag) {
        return delegate.requestRecoveryEmailProcessCall(languageTag);
    }
}
