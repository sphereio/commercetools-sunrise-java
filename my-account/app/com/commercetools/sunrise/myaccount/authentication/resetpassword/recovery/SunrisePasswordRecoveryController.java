package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.commercetools.sunrise.framework.controllers.SunriseContentFormController;
import com.commercetools.sunrise.framework.controllers.WithContentFormFlow;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.MyAccountController;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels.PasswordRecoveryPageContentFactory;
import io.sphere.sdk.customers.CustomerToken;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

/**
 * This abstract controller gets an customer email as input, request a customer reset password token for the
 * customer and then sends an email with a link to the reset password page.
 */
public abstract class SunrisePasswordRecoveryController extends SunriseContentFormController
        implements MyAccountController, WithContentFormFlow<Void, CustomerToken, PasswordRecoveryFormData> {
    private final PasswordRecoveryPageContentFactory pageContentFactory;
    private final PasswordRecoveryControllerAction controllerAction;
    private final PasswordRecoveryFormData formData;

    protected SunrisePasswordRecoveryController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                                final PasswordRecoveryPageContentFactory pageContentFactory,
                                                final PasswordRecoveryFormData formData,
                                                final PasswordRecoveryControllerAction controllerAction) {
        super(contentRenderer, formFactory);
        this.pageContentFactory = pageContentFactory;
        this.controllerAction = controllerAction;
        this.formData = formData;
    }

    @Override
    public final Class<? extends PasswordRecoveryFormData> getFormDataClass() {
        return formData.getClass();
    }

    @EnableHooks
    @SunriseRoute(ResetPasswordReverseRouter.REQUEST_RECOVERY_EMAIL_PAGE)
    public CompletionStage<Result> show(final String languageTag) {
        return showFormPage(null, formData);
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

    @Override
    public PageContent createPageContent(final Void input, final Form<? extends PasswordRecoveryFormData> form) {
        return pageContentFactory.create(form);
    }

    @Override
    public void preFillFormData(final Void input, final PasswordRecoveryFormData formData) {

    }
}
