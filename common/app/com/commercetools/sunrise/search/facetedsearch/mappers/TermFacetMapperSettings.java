package com.commercetools.sunrise.search.facetedsearch.mappers;

import javax.annotation.Nullable;
import java.util.List;

public interface TermFacetMapperSettings {

    TermFacetMapperType getType();

    @Nullable
    List<String> getValues();

    static TermFacetMapperSettings of(final TermFacetMapperType type, @Nullable final List<String> values) {
        return new TermTermFacetMapperSettingsImpl(type, values);
    }
}
