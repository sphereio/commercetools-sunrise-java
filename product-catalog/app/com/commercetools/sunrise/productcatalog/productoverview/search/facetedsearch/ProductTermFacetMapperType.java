package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.terms.mappers.AlphabeticallySortedTermFacetMapper;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.CustomSortedTermFacetMapper;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapper;
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
        public Class<? extends TermFacetMapper> mapper() {
            return CategoryTreeTermFacetMapper.class;
        }
    },

    ALPHABETICALLY_SORTED {
        @Override
        public String value() {
            return "alphabeticallySorted";
        }

        @Override
        public Class<? extends TermFacetMapper> mapper() {
            return AlphabeticallySortedTermFacetMapper.class;
        }
    },

    CUSTOM_SORTED {
        @Override
        public String value() {
            return "customSorted";
        }

        @Override
        public Class<? extends TermFacetMapper> mapper() {
            return CustomSortedTermFacetMapper.class;
        }
    }
}
