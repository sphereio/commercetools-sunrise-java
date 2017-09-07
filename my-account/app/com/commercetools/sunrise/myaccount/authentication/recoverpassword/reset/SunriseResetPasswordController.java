package com.commercetools.sunrise.myaccount.authentication.recoverpassword.reset;

import com.commercetools.sunrise.framework.annotations.SunriseRoute;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.reset.viewmodels.ResetPasswordPageContentFactory;
import io.sphere.sdk.client.ClientErrorException;
import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.customers.Customer;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

/**
 * This controller performs the reset of a customer's password.
 *
 * It shows a form to enter and confirm the new password {@link #show(String, String)}.
 *
 * It processes the form and sends a password reset command to the commercetools platform
 * {@link #process(String, String)}.
 */
public abstract class SunriseResetPasswordController extends SunriseContentFormController
        implements MyAccountController, WithContentFormFlow<String, Customer, ResetPasswordFormData> {
    private final ResetPasswordFormData formData;
    private final ResetPasswordControllerAction controllerAction;
    private final ResetPasswordPageContentFactory pageContentFactory;

    public SunriseResetPasswordController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                          final ResetPasswordFormData formData,
                                          final ResetPasswordControllerAction controllerAction,
                                          final ResetPasswordPageContentFactory pageContentFactory) {
        super(contentRenderer, formFactory);
        this.formData = formData;
        this.controllerAction = controllerAction;
        this.pageContentFactory = pageContentFactory;
    }

    @SunriseRoute("showResetPasswordForm")
    public CompletionStage<Result> show(final String languageTag, final String resetToken) {
        return showFormPage(resetToken, formData);
    }

    @SunriseRoute("processResetPasswordForm")
    public CompletionStage<Result> process(final String languageTag, final String resetToken) {
        return processForm(resetToken);
    }

    @Override
    public Class<? extends ResetPasswordFormData> getFormDataClass() {
        return formData.getClass();
    }

    @Override
    public PageContent createPageContent(final String resetToken, final Form<? extends ResetPasswordFormData> form) {
        return pageContentFactory.create(resetToken, form);
    }

    @Override
    public CompletionStage<Customer> executeAction(final String resetToken, final ResetPasswordFormData formData) {
        return controllerAction.apply(resetToken, formData);
    }

    @Override
    public CompletionStage<Result> handleClientErrorFailedAction(final String resetToken, final Form<? extends ResetPasswordFormData> form, final ClientErrorException clientErrorException) {
        if (clientErrorException instanceof NotFoundException) {
            return handleNotFoundToken(resetToken, form);
        }
        return WithContentFormFlow.super.handleClientErrorFailedAction(resetToken, form, clientErrorException);
    }

    protected abstract CompletionStage<Result> handleNotFoundToken(final String resetToken, final Form<? extends ResetPasswordFormData> form);

    @Override
    public void preFillFormData(final String resetToken, final ResetPasswordFormData formData) {
        // Do nothing
    }
}
