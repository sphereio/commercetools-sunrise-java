package controllers.myaccount;

import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.framework.reverserouters.myaccount.authentication.AuthenticationReverseRouter;
import com.commercetools.sunrise.framework.reverserouters.myaccount.changepassword.ChangePasswordReverseRouter;
import com.commercetools.sunrise.framework.template.engine.TemplateEngine;
import com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.sendemail.ResetPasswordTokenControllerAction;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.sendemail.SendResetPasswordEmailFormData;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.sendemail.SunriseSendResetPasswordEmailController;
import io.sphere.sdk.client.ClientErrorException;
import io.sphere.sdk.customers.CustomerToken;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.concurrent.CompletableFuture.completedFuture;

@LogMetrics
@NoCache
public final class SendResetPasswordEmailController extends SunriseSendResetPasswordEmailController {
    private final TemplateEngine templateEngine;
    private final AuthenticationReverseRouter authenticationReverseRouter;
    private final ChangePasswordReverseRouter changePasswordReverseRouter;

    @Inject
    public SendResetPasswordEmailController(final FormFactory formFactory,
                                            final SendResetPasswordEmailFormData formData,
                                            final ResetPasswordTokenControllerAction controllerAction,
                                            final TemplateEngine templateEngine,
                                            final AuthenticationReverseRouter authenticationReverseRouter,
                                            final ChangePasswordReverseRouter changePasswordReverseRouter) {
        super(formFactory, formData, controllerAction);
        this.templateEngine = templateEngine;
        this.authenticationReverseRouter = authenticationReverseRouter;
        this.changePasswordReverseRouter = changePasswordReverseRouter;
    }

    @Override
    public CompletionStage<Result> handleInvalidForm(final Void input, final Form<? extends SendResetPasswordEmailFormData> form) {
        return null;
    }

    @Override
    public CompletionStage<Result> handleClientErrorFailedAction(final Void input, final Form<? extends SendResetPasswordEmailFormData> form, final ClientErrorException clientErrorException) {
        return handleGeneralFailedAction(clientErrorException);
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final CustomerToken output, final SendResetPasswordEmailFormData formData) {
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
        final String absoluteURL = changePasswordReverseRouter.changePasswordProcessCall()
                .absoluteURL(request);
        final Map<String, List<String>> queryParam =
                singletonMap("token", singletonList(resetPasswordToken.getValue()));

        return QueryStringUtils.buildUri(absoluteURL, queryParam);
    }
}
