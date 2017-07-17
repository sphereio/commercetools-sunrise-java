package controllers.myaccount;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.framework.reverserouters.myaccount.authentication.AuthenticationReverseRouter;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.PasswordRecoveryControllerAction;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.PasswordRecoveryControllerComponent;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.PasswordRecoveryFormData;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.SunrisePasswordRecoveryController;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels.PasswordRecoveryPageContentFactory;
import io.sphere.sdk.customers.CustomerToken;
import play.data.FormFactory;
import play.mvc.Result;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class,
        PasswordRecoveryControllerComponent.class
})
public final class PasswordRecoveryController extends SunrisePasswordRecoveryController{
    private final AuthenticationReverseRouter authenticationReverseRouter;

    @Inject
    public PasswordRecoveryController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                      final PasswordRecoveryPageContentFactory pageContentFactory,
                                      final PasswordRecoveryFormData formData,
                                      final PasswordRecoveryControllerAction controllerAction,
                                      final AuthenticationReverseRouter authenticationReverseRouter) {
        super(contentRenderer, formFactory, pageContentFactory, formData, controllerAction);
        this.authenticationReverseRouter = authenticationReverseRouter;
    }

    @Nullable
    @Override
    public String getTemplateName() {
        return "my-account-forgot-password";
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final CustomerToken output, final PasswordRecoveryFormData formData) {
        return redirectToCall(authenticationReverseRouter.logInProcessCall());
    }
}
