package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover;

import com.commercetools.sunrise.common.controllers.SunriseFrameworkController;
import com.commercetools.sunrise.common.controllers.WithFormFlow;
import com.commercetools.sunrise.common.controllers.WithTemplateName;
import com.commercetools.sunrise.framework.annotations.SunriseRoute;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.viewmodels.RecoverPasswordPageContentFactory;
import com.commercetools.sunrise.myaccount.authentication.signup.SignUpFormData;
import com.commercetools.sunrise.shoppingcart.CartInSession;
import io.sphere.sdk.client.ClientErrorException;
import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreateCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import static java.util.Arrays.asList;

/**
 * This abstract controller gets an customer email as input, request a customer reset password token for the
 * customer and then sends an email with a link to the reset password page.
 */
public abstract class SunriseRecoverPasswordController extends SunriseFrameworkController
        implements WithTemplateName, WithFormFlow<RecoverPasswordFormData, Void, CustomerToken> {

    private static final Logger logger = LoggerFactory.getLogger(SunriseRecoverPasswordController.class);

    private final RecoverPasswordPageContentFactory pageContentFactory;
    private final RecoverPasswordControllerAction controllerAction;
    private final RecoverPasswordFormData formData;

    protected SunriseRecoverPasswordController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                               final RecoverPasswordPageContentFactory pageContentFactory,
                                               final RecoverPasswordFormData formData,
                                               final RecoverPasswordControllerAction controllerAction) {
        super(contentRenderer, formFactory);
        this.pageContentFactory = pageContentFactory;
        this.controllerAction = controllerAction;
        this.formData = formData;
    }

    @Override
    public Set<String> getFrameworkTags() {
        return new HashSet<>(asList("my-account", "recover-password", "authentication"));
    }

    @Override
    public String getTemplateName() {
        return "my-account-forgot-password";
    }

    @Override
    public Class<? extends RecoverPasswordFormData> getFormDataClass() {
        return DefaultRecoverPasswordFormData.class;
    }

    @SunriseRoute("showRecoveryPasswordForm")
    public CompletionStage<Result> show(final String languageTag) {
        return doRequest(() -> {
            logger.debug("show recover form in locale={}", languageTag);
            return showForm(null);
        });
    }

    @SunriseRoute("processRecoveryPasswordForm")
    public CompletionStage<Result> process(final String languageTag) {
        return doRequest(() -> {
            logger.debug("process recover form in locale={}", languageTag);
            return validateForm(null);
        });
    }

    @Override
    public CompletionStage<? extends CustomerSignInResult> doAction(final SignUpFormData formData, final Void context) {
        final String cartId = injector().getInstance(CartInSession.class).findCartId().orElse(null);
        final CustomerDraft customerDraft = formData.toCustomerDraftBuilder()
                .customerNumber(generateCustomerNumber())
                .anonymousCartId(cartId)
                .build();
        final CustomerCreateCommand customerCreateCommand = CustomerCreateCommand.of(customerDraft);
        return sphere().execute(customerCreateCommand);
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
        return WithContentFormFlow.super.handleFailedAction(input, form, throwable);
    }

    @Override
    public CompletionStage<Result> handleClientErrorFailedAction(final Void input, final Form<? extends RecoverPasswordFormData> form, final ClientErrorException clientErrorException) {
        if (clientErrorException instanceof NotFoundException) {
            return handleNotFoundEmail(form);
        }
        return WithContentFormFlow.super.handleClientErrorFailedAction(input, form, clientErrorException);
    }

    protected abstract CompletionStage<Result> handleNotFoundEmail(final Form<? extends RecoverPasswordFormData> form);

    protected abstract CompletionStage<Result> handleEmailDeliveryException(final Form<? extends RecoverPasswordFormData> form, final EmailDeliveryException emailDeliveryException);

    @Override
    public PageContent createPageContent(final Void input, final Form<? extends RecoverPasswordFormData> form) {
        return pageContentFactory.create(form);
    }

    @Override
    public void preFillFormData(final Void input, final RecoverPasswordFormData formData) {
        // Do nothing
    }
}
