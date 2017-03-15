package com.commercetools.sunrise.search.facetedsearch.mappers;

import javax.annotation.Nullable;
import java.util.List;

class TermTermFacetMapperSettingsImpl implements TermFacetMapperSettings {

    private final TermFacetMapperType type;
    @Nullable
    private final List<String> values;

    TermTermFacetMapperSettingsImpl(final TermFacetMapperType type, @Nullable final List<String> values) {
        this.type = type;
        this.values = values;
    }

    @Override
    public TermFacetMapperType getType() {
        return type;
    }

    @Nullable
    @Override
    public List<String> getValues() {
        return values;
    }
}
