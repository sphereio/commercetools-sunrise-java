package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.commercetools.sunrise.framework.controllers.SunriseFormController;
import com.commercetools.sunrise.framework.controllers.WithFormFlow;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import com.commercetools.sunrise.myaccount.MyAccountController;
import io.sphere.sdk.customers.CustomerToken;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

/**
 * This abstract controller gets an customer email as input, request a customer reset password token for the
 * customer and then sends an email with a link to the reset password page.
 */
public abstract class SunrisePasswordRecoveryController extends SunriseFormController
        implements MyAccountController, WithFormFlow<Void, CustomerToken, PasswordRecoveryFormData> {
    private final PasswordRecoveryControllerAction controllerAction;
    private final PasswordRecoveryFormData formData;

    protected SunrisePasswordRecoveryController(final FormFactory formFactory,
                                                final PasswordRecoveryFormData formData,
                                                final PasswordRecoveryControllerAction controllerAction) {
        super(formFactory);
        this.controllerAction = controllerAction;
        this.formData = formData;
    }

    @Override
    public final Class<? extends PasswordRecoveryFormData> getFormDataClass() {
        return formData.getClass();
    }

    @EnableHooks
    @SunriseRoute(ResetPasswordReverseRouter.REQUEST_RECOVERY_EMAIL_PROCESS)
    public CompletionStage<Result> process(final String languageTag) {
        return processForm(null);
    }

    @Override
    public CompletionStage<CustomerToken> executeAction(final Void input, final PasswordRecoveryFormData formData) {
        return controllerAction.apply(formData);
    }
}
