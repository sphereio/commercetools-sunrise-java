package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.facetedsearch.mappers.AlphabeticallySortedFacetMapperFactory;
import com.commercetools.sunrise.search.facetedsearch.mappers.CustomSortedFacetMapperFactory;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperFactory;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;

import javax.annotation.Nullable;

/**
 * Type of the mapper, to map from the facet result received from the platform to a form option.
 */
public enum SunriseFacetMapperType implements FacetMapperType {

    CATEGORY_TREE {
        @Override
        public String value() {
            return "categoryTree";
        }

        @Nullable
        @Override
        public Class<? extends FacetMapperFactory> factory() {
            return CategoryTreeFacetMapperFactory.class;
        }
    },

    ALPHABETICALLY_SORTED {
        @Override
        public String value() {
            return "alphabeticallySorted";
        }

        @Nullable
        @Override
        public Class<? extends FacetMapperFactory> factory() {
            return AlphabeticallySortedFacetMapperFactory.class;
        }
    },

    CUSTOM_SORTED {
        @Override
        public String value() {
            return "customSorted";
        }

        @Nullable
        @Override
        public Class<? extends FacetMapperFactory> factory() {
            return CustomSortedFacetMapperFactory.class;
        }
    }
}
