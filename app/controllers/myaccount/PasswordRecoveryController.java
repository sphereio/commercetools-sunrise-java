package controllers.myaccount;

import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.framework.reverserouters.myaccount.authentication.AuthenticationReverseRouter;
import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.PasswordRecoveryControllerAction;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.PasswordRecoveryFormData;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.SunrisePasswordRecoveryController;
import io.sphere.sdk.client.ClientErrorException;
import io.sphere.sdk.customers.CustomerToken;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

@LogMetrics
@NoCache
public final class PasswordRecoveryController extends SunrisePasswordRecoveryController {
    private final AuthenticationReverseRouter authenticationReverseRouter;
    private final ResetPasswordReverseRouter changePasswordReverseRouter;

    @Inject
    public PasswordRecoveryController(final FormFactory formFactory,
                                      final PasswordRecoveryFormData formData,
                                      final PasswordRecoveryControllerAction controllerAction,
                                      final AuthenticationReverseRouter authenticationReverseRouter,
                                      final ResetPasswordReverseRouter changePasswordReverseRouter) {
        super(formFactory, formData, controllerAction);
        this.authenticationReverseRouter = authenticationReverseRouter;
        this.changePasswordReverseRouter = changePasswordReverseRouter;
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

    @Override
    protected CompletionStage<CustomerToken> sendEmail(final CustomerToken resetPasswordToken) {
        final String passwordResetLink = createPasswordResetLink(resetPasswordToken);
        getLogger().debug("Password reset link {}", passwordResetLink);
        return completedFuture(null);
    }

    protected String createPasswordResetLink(final CustomerToken resetPasswordToken) {
        final Http.Request request = Http.Context.current().request();
        final String absoluteURL = changePasswordReverseRouter.resetPasswordPageCall(resetPasswordToken.getValue())
                .absoluteURL(request);

        return absoluteURL;
    }
}
