package com.commercetools.sunrise.myaccount.authentication.resetpassword.sendemail;

import com.commercetools.sunrise.framework.controllers.SunriseFormController;
import com.commercetools.sunrise.framework.controllers.WithFormFlow;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.authentication.AuthenticationReverseRouter;
import com.commercetools.sunrise.myaccount.MyAccountController;
import io.sphere.sdk.customers.CustomerToken;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecution;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

/**
 * This abstract controller gets an customer email as input, request a customer reset password token for the
 * customer and then sends an email with a link to the reset password page.
 */
public abstract class SunriseSendResetPasswordEmailController extends SunriseFormController
        implements MyAccountController, WithFormFlow<Void, CustomerToken, SendResetPasswordEmailFormData> {
    private final ResetPasswordTokenControllerAction controllerAction;
    private final SendResetPasswordEmailFormData formData;

    protected SunriseSendResetPasswordEmailController(final FormFactory formFactory,
                                                      final SendResetPasswordEmailFormData formData,
                                                      final ResetPasswordTokenControllerAction controllerAction) {
        super(formFactory);
        this.controllerAction = controllerAction;
        this.formData = formData;
    }

    @Override
    public final Class<? extends SendResetPasswordEmailFormData> getFormDataClass() {
        return formData.getClass();
    }

    @EnableHooks
    @SunriseRoute(AuthenticationReverseRouter.RESET_PASSWORD_PROCESS)
    public CompletionStage<Result> process(final String languageTag) {
        return processForm(null);
    }

    @Override
    public CompletionStage<CustomerToken> executeAction(final Void input, final SendResetPasswordEmailFormData formData) {
        return controllerAction.apply(formData)
                .thenComposeAsync(customerToken -> sendEmail(customerToken), HttpExecution.defaultContext());
    }

    protected abstract CompletionStage<CustomerToken> sendEmail(final CustomerToken resetPasswordToken);
}
