package com.commercetools.sunrise.search.facetedsearch.terms;

import javax.annotation.Nullable;
import java.util.List;

class TermTermFacetMapperSettingsImpl implements TermFacetMapperSettings {

    private final String name;
    @Nullable
    private final List<String> values;

    TermTermFacetMapperSettingsImpl(final String name, @Nullable final List<String> values) {
        this.name = name;
        this.values = values;
    }

    @Override
    public String getName() {
        return name;
    }

    @Nullable
    @Override
    public List<String> getValues() {
        return values;
    }
}
