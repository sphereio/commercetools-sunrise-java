package com.commercetools.sunrise.common.localization.changelanguage;

import com.commercetools.sunrise.core.controllers.SunriseFormController;
import com.commercetools.sunrise.core.controllers.WithForm2Flow;
import com.commercetools.sunrise.core.hooks.EnableHooks;
import com.commercetools.sunrise.core.reverserouters.SunriseRoute;
import com.commercetools.sunrise.core.reverserouters.common.localization.LocalizationReverseRouter;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public abstract class SunriseChangeLanguageController extends SunriseFormController implements WithForm2Flow<Void, Void, ChangeLanguageFormData> {

    private final ChangeLanguageFormData formData;
    private final ChangeLanguageControllerAction controllerAction;

    protected SunriseChangeLanguageController(final FormFactory formFactory, final ChangeLanguageFormData formData,
                                              final ChangeLanguageControllerAction controllerAction) {
        super(formFactory);
        this.formData = formData;
        this.controllerAction = controllerAction;
    }

    @Override
    public final Class<? extends ChangeLanguageFormData> getFormDataClass() {
        return formData.getClass();
    }

    @EnableHooks
    @SunriseRoute(LocalizationReverseRouter.CHANGE_LANGUAGE_PROCESS)
    public CompletionStage<Result> process() {
        return processForm(null);
    }

    @Override
    public CompletionStage<Void> executeAction(final Void input, final ChangeLanguageFormData formData) {
        controllerAction.accept(formData);
        return completedFuture(null);
    }
}
