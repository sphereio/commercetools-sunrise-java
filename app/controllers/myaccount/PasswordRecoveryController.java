package controllers.myaccount;

import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.framework.reverserouters.myaccount.authentication.AuthenticationReverseRouter;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.PasswordRecoveryControllerAction;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.PasswordRecoveryControllerComponent;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.PasswordRecoveryFormData;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.SunrisePasswordRecoveryController;
import io.sphere.sdk.client.ClientErrorException;
import io.sphere.sdk.customers.CustomerToken;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
@RegisteredComponents(PasswordRecoveryControllerComponent.class)
public final class PasswordRecoveryController extends SunrisePasswordRecoveryController{
    private final AuthenticationReverseRouter authenticationReverseRouter;

    @Inject
    public PasswordRecoveryController(final FormFactory formFactory,
                                      final PasswordRecoveryFormData formData,
                                      final PasswordRecoveryControllerAction controllerAction,
                                      final AuthenticationReverseRouter authenticationReverseRouter) {
        super(formFactory, formData, controllerAction);
        this.authenticationReverseRouter = authenticationReverseRouter;
    }

    @Override
    public CompletionStage<Result> handleInvalidForm(final Void input, final Form<? extends PasswordRecoveryFormData> form) {
        return null;
    }

    @Override
    public CompletionStage<Result> handleClientErrorFailedAction(final Void input, final Form<? extends PasswordRecoveryFormData> form, final ClientErrorException clientErrorException) {
        return handleGeneralFailedAction(clientErrorException);
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final CustomerToken output, final PasswordRecoveryFormData formData) {
        return redirectToCall(authenticationReverseRouter.logInProcessCall());
    }
}
