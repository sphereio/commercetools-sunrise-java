package demo.myaccount;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.framework.reverserouters.myaccount.recoverpassword.RecoverPasswordReverseRouter;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.RecoverPasswordControllerAction;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.RecoverPasswordFormData;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.SunriseRecoverPasswordController;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.viewmodels.RecoverPasswordPageContentFactory;
import com.commercetools.sunrise.email.EmailDeliveryException;
import io.sphere.sdk.customers.CustomerToken;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class
})
public class RecoverPasswordController extends SunriseRecoverPasswordController {

}
