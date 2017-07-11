package com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword;

import com.google.inject.ImplementedBy;
import play.mvc.Call;

/**
 * The reverse router for the reset password controller.
 */
@ImplementedBy(SimpleResetPasswordReverseRouterByReflection.class)
public interface SimpleResetPasswordReverseRouter {

    String REQUEST_RECOVERY_EMAIL_PROCESS = "requestRecoveryEmailProcessCall";

    Call requestRecoveryEmailProcessCall(final String languageTag);

    String RESET_PASSWORD_PAGE = "resetPasswordPageCall";

    Call resetPasswordPageCall(final String languageTag, final String resetToken);

    String RESET_PASSWORD_PROCESS = "resetPasswordProcessCall";

    Call resetPasswordProcessCall(final String languageTag, final String resetToken);
}
