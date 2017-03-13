package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.facetedsearch.FacetUIType;

public enum SunriseFacetUIType implements FacetUIType {

    LIST {
        @Override
        public String value() {
            return "list";
        }
    },

    COLUMNS_LIST {
        @Override
        public String value() {
            return "columnList";
        }
    },

    CATEGORY_TREE {
        @Override
        public String value() {
            return "categoryTree";
        }
    },

    SLIDER_RANGE {
        @Override
        public String value() {
            return "sliderRange";
        }
    },

    BUCKET_RANGE {
        @Override
        public String value() {
            return "bucketRange";
        }
    }
}
