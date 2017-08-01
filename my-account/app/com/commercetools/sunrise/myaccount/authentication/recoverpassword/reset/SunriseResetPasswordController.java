package com.commercetools.sunrise.myaccount.authentication.recoverpassword.reset;

import com.commercetools.sunrise.framework.controllers.SunriseContentFormController;
import com.commercetools.sunrise.framework.controllers.WithContentFormFlow;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.SimpleResetPasswordReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.MyAccountController;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.reset.viewmodels.ResetPasswordPageContentFactory;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * This controller performs the reset of a customer's password.
 *
 * It shows a form to enter and confirm the new password {@link #show(String, String)}.
 *
 * It processes the form and sends a password reset command to the commercetools platform
 * {@link #process(String, String)}.
 */
public abstract class SunriseResetPasswordController extends SunriseContentFormController
        implements MyAccountController, WithContentFormFlow<String, ResetPasswordFormData, ResetPasswordFormData> {
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

    @EnableHooks
    @SunriseRoute(SimpleResetPasswordReverseRouter.RESET_PASSWORD_PAGE)
    public CompletionStage<Result> show(final String languageTag, final String resetToken) {
        return showFormPage(resetToken, formData);
    }

    @EnableHooks
    @SunriseRoute(SimpleResetPasswordReverseRouter.RESET_PASSWORD_PROCESS)
    public CompletionStage<Result> process(final String languageTag, final String resetToken) {
        return processForm(resetToken);
    }

    @Override
    public CompletionStage<Form<? extends ResetPasswordFormData>> validateForm(final String resetToken, final Form<? extends ResetPasswordFormData> form) {
        final ResetPasswordFormData resetPasswordFormData = form.get();
        final String newPassword = resetPasswordFormData.newPassword();
        if (newPassword == null) {
            saveFormError(form, "New password is required");

        } else {
            final boolean isValid = newPassword.equals(resetPasswordFormData.confirmPassword()) &&
                    !newPassword.isEmpty();

            if (!isValid) {
                saveFormError(form, "Confirm password invalid");
            }
        }
        return completedFuture(form);
    }

    @Override
    public Class<? extends ResetPasswordFormData> getFormDataClass() {
        return formData.getClass();
    }

    @Override
    public CompletionStage<Result> handleInvalidForm(final String resetToken, final Form<? extends ResetPasswordFormData> form) {
        return showFormPageWithErrors(resetToken, form);
    }

    @Override
    public PageContent createPageContent(final String resetToken, final Form<? extends ResetPasswordFormData> form) {
        return pageContentFactory.create(form);
    }

    @Override
    public CompletionStage<ResetPasswordFormData> executeAction(final String resetToken, final ResetPasswordFormData formData) {
        return controllerAction.apply(resetToken, formData).thenApplyAsync(customer -> formData);
    }

    @Override
    public void preFillFormData(final String resetToken, final ResetPasswordFormData formData) {
    }
}
