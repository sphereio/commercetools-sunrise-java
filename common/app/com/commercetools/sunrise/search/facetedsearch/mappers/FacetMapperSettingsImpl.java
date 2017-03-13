package com.commercetools.sunrise.search.facetedsearch.mappers;

import javax.annotation.Nullable;
import java.util.List;

class FacetMapperSettingsImpl implements FacetMapperSettings {

    private final FacetMapperType type;
    @Nullable
    private final List<String> values;

    FacetMapperSettingsImpl(final FacetMapperType type, @Nullable final List<String> values) {
        this.type = type;
        this.values = values;
    }

    @Override
    public FacetMapperType getType() {
        return type;
    }

    @Nullable
    @Override
    public List<String> getValues() {
        return values;
    }
}
