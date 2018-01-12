package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover;

import com.commercetools.sunrise.core.controllers.SunriseContentFormController;
import com.commercetools.sunrise.core.controllers.WithContentForm2Flow;
import com.commercetools.sunrise.core.hooks.EnableHooks;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.reverserouters.SunriseRoute;
import com.commercetools.sunrise.core.reverserouters.myaccount.recoverpassword.RecoverPasswordReverseRouter;
import com.commercetools.sunrise.core.viewmodels.content.PageContent;
import com.commercetools.sunrise.email.EmailDeliveryException;
import com.commercetools.sunrise.models.BlankPageContent;
import com.commercetools.sunrise.myaccount.MyAccountController;
import io.sphere.sdk.client.ClientErrorException;
import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.customers.CustomerToken;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

/**
 * This abstract controller gets an customer email as input, request a customer reset password token for the
 * customer and then sends an email with a link to the reset password page.
 */
public abstract class SunriseRecoverPasswordController extends SunriseContentFormController implements MyAccountController, WithContentForm2Flow<Void, CustomerToken, RecoverPasswordFormData> {

    private final RecoverPasswordControllerAction controllerAction;
    private final RecoverPasswordFormData formData;

    protected SunriseRecoverPasswordController(final ContentRenderer contentRenderer,
                                               final FormFactory formFactory,
                                               final RecoverPasswordFormData formData,
                                               final RecoverPasswordControllerAction controllerAction) {
        super(contentRenderer, formFactory);
        this.controllerAction = controllerAction;
        this.formData = formData;
    }

    @Override
    public final Class<? extends RecoverPasswordFormData> getFormDataClass() {
        return formData.getClass();
    }

    @EnableHooks
    @SunriseRoute(RecoverPasswordReverseRouter.REQUEST_RECOVERY_EMAIL_PAGE)
    public CompletionStage<Result> show() {
        return showFormPage(null, formData);
    }

    @EnableHooks
    @SunriseRoute(RecoverPasswordReverseRouter.REQUEST_RECOVERY_EMAIL_PROCESS)
    public CompletionStage<Result> process() {
        return processForm(null);
    }

    @Override
    public CompletionStage<CustomerToken> executeAction(final Void input, final RecoverPasswordFormData formData) {
        return controllerAction.apply(formData);
    }

    @Override
    public CompletionStage<Result> handleFailedAction(final Void input, final Form<? extends RecoverPasswordFormData> form, final Throwable throwable) {
        final Throwable cause = throwable.getCause();
        if (cause instanceof EmailDeliveryException) {
            final EmailDeliveryException emailDeliveryException = (EmailDeliveryException) cause;
            return handleEmailDeliveryException(form, emailDeliveryException);
        }
        return WithContentForm2Flow.super.handleFailedAction(input, form, throwable);
    }

    @Override
    public CompletionStage<Result> handleClientErrorFailedAction(final Void input, final Form<? extends RecoverPasswordFormData> form, final ClientErrorException clientErrorException) {
        if (clientErrorException instanceof NotFoundException) {
            return handleNotFoundEmail(form);
        }
        return WithContentForm2Flow.super.handleClientErrorFailedAction(input, form, clientErrorException);
    }

    protected abstract CompletionStage<Result> handleNotFoundEmail(final Form<? extends RecoverPasswordFormData> form);

    protected abstract CompletionStage<Result> handleEmailDeliveryException(final Form<? extends RecoverPasswordFormData> form, final EmailDeliveryException emailDeliveryException);

    @Override
    public PageContent createPageContent(final Void input, final Form<? extends RecoverPasswordFormData> form) {
        return new BlankPageContent();
    }

    @Override
    public void preFillFormData(final Void input, final RecoverPasswordFormData formData) {
        // Do nothing
    }
}
