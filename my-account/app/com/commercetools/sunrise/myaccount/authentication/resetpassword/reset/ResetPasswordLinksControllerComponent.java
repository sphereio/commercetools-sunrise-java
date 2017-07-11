package com.commercetools.sunrise.myaccount.authentication.resetpassword.reset;

import com.commercetools.sunrise.framework.reverserouters.AbstractLinksControllerComponent;
import com.commercetools.sunrise.framework.reverserouters.myaccount.authentication.AuthenticationReverseRouter;
import com.commercetools.sunrise.framework.viewmodels.meta.PageMeta;
import play.mvc.Http;

import javax.inject.Inject;

/**
 * This controller component adds a link to the reset password process to the pages hal links.
 */
public class ResetPasswordLinksControllerComponent extends AbstractLinksControllerComponent<AuthenticationReverseRouter> {
    private static final String RESET_PASSWORD_REL = "resetPassword";

    private final AuthenticationReverseRouter authenticationReverseRouter;

    @Inject
    protected ResetPasswordLinksControllerComponent(final AuthenticationReverseRouter authenticationReverseRouter) {
        this.authenticationReverseRouter = authenticationReverseRouter;
    }

    @Override
    public AuthenticationReverseRouter getReverseRouter() {
        return authenticationReverseRouter;
    }

    @Override
    protected void addLinksToPage(final PageMeta meta, final AuthenticationReverseRouter reverseRouter) {
        final String uri = Http.Context.current().request().uri();
        meta.addHalLinkOfHrefAndRel(uri, RESET_PASSWORD_REL);
    }
}
