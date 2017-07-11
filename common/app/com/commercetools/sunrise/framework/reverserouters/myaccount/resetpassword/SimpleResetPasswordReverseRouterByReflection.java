package com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword;

import com.commercetools.sunrise.framework.reverserouters.AbstractReflectionReverseRouter;
import com.commercetools.sunrise.framework.reverserouters.ParsedRoutes;
import com.commercetools.sunrise.framework.reverserouters.ReverseCaller;
import play.mvc.Call;

import javax.inject.Inject;


final class SimpleResetPasswordReverseRouterByReflection extends AbstractReflectionReverseRouter implements SimpleResetPasswordReverseRouter {
    private final ReverseCaller resetPasswordPageCaller;
    private final ReverseCaller resetPasswordProcessCaller;
    private final ReverseCaller requestRecoveryEmailProcessCaller;

    @Inject
    public SimpleResetPasswordReverseRouterByReflection(final ParsedRoutes parsedRoutes) {
        resetPasswordPageCaller = getReverseCallerForSunriseRoute(RESET_PASSWORD_PAGE, parsedRoutes);
        resetPasswordProcessCaller = getReverseCallerForSunriseRoute(RESET_PASSWORD_PROCESS, parsedRoutes);
        requestRecoveryEmailProcessCaller = getReverseCallerForSunriseRoute(REQUEST_RECOVERY_EMAIL_PROCESS,
                parsedRoutes);
    }

    @Override
    public Call resetPasswordPageCall(final String languageTag, final String resetToken) {
        return resetPasswordPageCaller.call(languageTag, resetToken);
    }

    @Override
    public Call resetPasswordProcessCall(final String languageTag, final String resetToken) {
        return resetPasswordProcessCaller.call(languageTag, resetToken);
    }

    @Override
    public Call requestRecoveryEmailProcessCall(final String languageTag) {
        return requestRecoveryEmailProcessCaller.call(languageTag);
    }
}
