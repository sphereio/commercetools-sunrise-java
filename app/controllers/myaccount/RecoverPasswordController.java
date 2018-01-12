package controllers.myaccount;

import com.commercetools.sunrise.core.controllers.cache.NoCache;
import com.commercetools.sunrise.core.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.core.viewmodels.content.messages.MessageType;
import com.commercetools.sunrise.email.EmailDeliveryException;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.RecoverPasswordControllerAction;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.RecoverPasswordFormData;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.SunriseRecoverPasswordController;
import io.sphere.sdk.customers.CustomerToken;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
public final class RecoverPasswordController extends SunriseRecoverPasswordController {

    @Inject
    RecoverPasswordController(final ContentRenderer contentRenderer,
                              final FormFactory formFactory,
                              final RecoverPasswordFormData formData,
                              final RecoverPasswordControllerAction controllerAction) {
        super(contentRenderer, formFactory, formData, controllerAction);
    }

    @Override
    public String getTemplateName() {
        return "my-account-forgot-password";
    }

    @Override
    public String getCmsPageKey() {
        return "default";
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final CustomerToken customerToken, final RecoverPasswordFormData formData) {
        saveMessage(MessageType.SUCCESS, "my-account:forgotPassword.feedbackMessage");
        return redirectAsync(routes.RecoverPasswordController.show());
    }

    @Override
    protected CompletionStage<Result> handleNotFoundEmail(final Form<? extends RecoverPasswordFormData> form) {
        saveFormError(form, "Email not found");
        return showFormPageWithErrors(null, form);
    }

    @Override
    protected CompletionStage<Result> handleEmailDeliveryException(final Form<? extends RecoverPasswordFormData> form, final EmailDeliveryException emailDeliveryException) {
        saveFormError(form, "Email delivery error");
        return internalServerErrorResult(createPageContent(null, form));
    }
}
