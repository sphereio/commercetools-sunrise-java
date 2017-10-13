package controllers.myaccount;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.SunriseContentController;
import com.commercetools.sunrise.framework.controllers.WithQueryFlow;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.recoverpassword.RecoverPasswordReverseRouter;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.viewmodels.SuccessfulRecoveryPageContentFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class
})
public final class SuccessfulPasswordRecoverController extends SunriseContentController implements WithQueryFlow<Void> {

    private final SuccessfulRecoveryPageContentFactory contentFactory;

    @Inject
    public SuccessfulPasswordRecoverController(final ContentRenderer contentRenderer, SuccessfulRecoveryPageContentFactory contentFactory) {
        super(contentRenderer);
        this.contentFactory = contentFactory;
    }


    @EnableHooks
    @SunriseRoute(RecoverPasswordReverseRouter.REQUEST_RECOVERY_EMAIL_SUCCESS)
    public CompletionStage<Result> show(final String languageTag) {
        return showPage(null);
    }

    @Override
    public PageContent createPageContent(Void input) {
        return contentFactory.create(input);
    }

    @Override
    public String getTemplateName() {
        return "my-account-forgot-password-success";
    }

    @Override
    public String getCmsPageKey() {
        return "default";
    }
}
