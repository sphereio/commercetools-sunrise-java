package com.commercetools.sunrise.search.facetedsearch.mappers;

import javax.annotation.Nullable;
import java.util.List;

public interface FacetMapperSettings {

    FacetMapperType getType();

    @Nullable
    List<String> getValues();

    static FacetMapperSettings of(final FacetMapperType type, @Nullable final List<String> values) {
        return new FacetMapperSettingsImpl(type, values);
    }
}
