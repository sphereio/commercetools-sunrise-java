package com.commercetools.sunrise.search.facetedsearch.mappers;

import java.util.List;

class FacetMapperSettingsImpl implements FacetMapperSettings {

    private final FacetMapperType type;
    private final List<String> values;

    FacetMapperSettingsImpl(final FacetMapperType type, final List<String> values) {
        this.type = type;
        this.values = values;
    }

    @Override
    public FacetMapperType getType() {
        return type;
    }

    @Override
    public List<String> getValues() {
        return values;
    }
}
