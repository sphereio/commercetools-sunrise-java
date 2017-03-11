package com.commercetools.sunrise.search.facetedsearch.mappers;

import java.util.List;

public interface FacetMapperSettings {

    FacetMapperType getType();

    List<String> getValues();

    static FacetMapperSettings of(final FacetMapperType type, final List<String> values) {
        return new FacetMapperSettingsImpl(type, values);
    }
}
