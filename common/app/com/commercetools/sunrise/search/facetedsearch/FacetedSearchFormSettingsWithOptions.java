package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;

public interface FacetedSearchFormSettingsWithOptions<T, O extends FacetedSearchFormOption> extends FacetedSearchFormSettings<T>, FormSettingsWithOptions<O, String> {

}
