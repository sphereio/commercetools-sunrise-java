package io.sphere.sdk.facets;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

abstract class BaseFacet<T> extends Base implements Facet<T> {

    private final String key;
    private final FacetType type;
    private final String label;
    private final boolean countHidden;

    protected BaseFacet(final String key, final FacetType type, final boolean countHidden, @Nullable final String label) {
        this.key = key;
        this.type = type;
        this.label = label;
        this.countHidden = countHidden;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public FacetType getType() {
        return type;
    }

    @Nullable
    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean isCountHidden() {
        return countHidden;
    }
}
