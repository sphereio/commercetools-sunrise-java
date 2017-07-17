package com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword;

import com.commercetools.sunrise.framework.reverserouters.LocalizedReverseRouter;
import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(DefaultResetPasswordReverseRouter.class)
public interface ResetPasswordReverseRouter extends SimpleResetPasswordReverseRouter, LocalizedReverseRouter {

    default Call resetPasswordCall(final String resetToken) {
        return resetPasswordProcessCall(locale().toLanguageTag(), resetToken);
    }

    default Call resetPasswordPageCall(final String resetToken) {
        return resetPasswordPageCall(locale().toLanguageTag(), resetToken);
    }

    default Call requestRecoveryEmailPageCall() {
        return requestRecoveryEmailPageCall(locale().toLanguageTag());
    }

    default Call requestRecoveryEmailProcessCall() {
        return requestRecoveryEmailProcessCall(locale().toLanguageTag());
    }

}
