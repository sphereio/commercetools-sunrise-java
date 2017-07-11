package com.commercetools.sunrise.framework.reverserouters.myaccount.authentication;

import com.commercetools.sunrise.framework.reverserouters.AbstractLinksControllerComponent;
import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import com.commercetools.sunrise.framework.viewmodels.meta.PageMeta;

import javax.inject.Inject;

public class AuthenticationLinksControllerComponent extends AbstractLinksControllerComponent<AuthenticationReverseRouter> {
    private final AuthenticationReverseRouter reverseRouter;
    private final ResetPasswordReverseRouter resetPasswordReverseRouter;

    @Inject
    protected AuthenticationLinksControllerComponent(final AuthenticationReverseRouter reverseRouter,
                                                     final ResetPasswordReverseRouter resetPasswordReverseRouter) {
        this.reverseRouter = reverseRouter;
        this.resetPasswordReverseRouter = resetPasswordReverseRouter;
    }

    @Override
    public final AuthenticationReverseRouter getReverseRouter() {
        return reverseRouter;
    }

    @Override
    protected void addLinksToPage(final PageMeta meta, final AuthenticationReverseRouter reverseRouter) {
        meta.addHalLink(reverseRouter.logInPageCall(), "signIn", "logIn");
        meta.addHalLink(reverseRouter.logInProcessCall(), "logInSubmit");
        meta.addHalLink(reverseRouter.logOutProcessCall(), "logOut");
        meta.addHalLink(reverseRouter.logInPageCall(), "signUp");
        meta.addHalLink(reverseRouter.signUpProcessCall(), "signUpSubmit");
        meta.addHalLink(resetPasswordReverseRouter.requestRecoveryEmailProcessCall(), "recoveryEmail");
    }
}
