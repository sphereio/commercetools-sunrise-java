package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.terms.mappers.AlphabeticallySortedTermFacetMapperFactory;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.CustomSortedTermFacetMapperFactory;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapperFactory;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapperType;

/**
 * Type of the mapper, to map from the facet result received from the platform to a form option.
 */
public enum ProductTermFacetMapperType implements TermFacetMapperType {

    CATEGORY_TREE {
        @Override
        public String value() {
            return "categoryTree";
        }

        @Override
        public Class<? extends TermFacetMapperFactory> factory() {
            return CategoryTreeTermFacetMapperFactory.class;
        }
    },

    ALPHABETICALLY_SORTED {
        @Override
        public String value() {
            return "alphabeticallySorted";
        }

        @Override
        public Class<? extends TermFacetMapperFactory> factory() {
            return AlphabeticallySortedTermFacetMapperFactory.class;
        }
    },

    CUSTOM_SORTED {
        @Override
        public String value() {
            return "customSorted";
        }

        @Override
        public Class<? extends TermFacetMapperFactory> factory() {
            return CustomSortedTermFacetMapperFactory.class;
        }
    }
}
