package com.commercetools.sunrise.framework.reverserouters.common.localization;

import com.commercetools.sunrise.framework.reverserouters.ReverseRouter;
import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(DefaultLocalizationReverseRouter.class)
public interface LocalizationReverseRouter extends ReverseRouter {

    String CHANGE_LANGUAGE_PROCESS = "changeLanguageProcessCall";

    Call changeLanguageProcessCall();

    String CHANGE_COUNTRY_PROCESS = "changeCountryProcessCall";

    Call changeCountryProcessCall();
}
