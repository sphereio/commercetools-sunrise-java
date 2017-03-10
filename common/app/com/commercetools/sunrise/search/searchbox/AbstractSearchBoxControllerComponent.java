package com.commercetools.sunrise.search.searchbox;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.consumers.PageDataReadyHook;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.search.searchbox.viewmodels.WithSearchBoxViewModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStringEntry;
import play.mvc.Http;

import java.util.Locale;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findSelectedValueFromQueryString;

public abstract class AbstractSearchBoxControllerComponent extends Base implements ControllerComponent, PageDataReadyHook {

    private final LocalizedStringEntry searchText;

    protected AbstractSearchBoxControllerComponent(final SearchBoxSettings searchBoxSettings, final Http.Request httpRequest, final Locale locale) {
        this.searchText = LocalizedStringEntry.of(locale, findSelectedValueFromQueryString(searchBoxSettings, httpRequest));
    }

    public final LocalizedStringEntry getSearchText() {
        return searchText;
    }

    @Override
    public void onPageDataReady(final PageData pageData) {
        if (!searchText.getValue().isEmpty() && pageData.getContent() instanceof WithSearchBoxViewModel) {
            final WithSearchBoxViewModel content = (WithSearchBoxViewModel) pageData.getContent();
            content.setSearchTerm(searchText.getValue());
        }
    }
}
