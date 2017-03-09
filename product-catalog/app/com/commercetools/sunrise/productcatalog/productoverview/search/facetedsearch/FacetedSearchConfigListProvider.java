package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.common.SunriseConfigurationException;
import io.sphere.sdk.facets.*;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.TermFacetedSearchSearchModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public final class FacetedSearchConfigListProvider implements Provider<FacetedSearchConfigList> {
    private static final Logger logger = LoggerFactory.getLogger(FacetedSearchConfigListProvider.class);
    private static final String CONFIG_FACETS = "pop.facets";

    private static final String TYPE_ATTR = "type";
    private static final String KEY_ATTR = "key";
    private static final String LABEL_ATTR = "label";
    private static final String EXPR_ATTR = "expr";
    private static final String COUNT_ATTR = "count";
    private static final String MULTI_SELECT_ATTR = "multiSelect";
    private static final String MATCHING_ALL_ATTR = "matchingAll";
    private static final String LIMIT_ATTR = "limit";
    private static final String THRESHOLD_ATTR = "threshold";
    private static final String INITIAL_RANGES = "initialRanges";

    private static final String MAPPER_ATTR = "mapper";
    private static final String MAPPER_TYPE_ATTR = "type";
    private static final String MAPPER_VALUES_ATTR = "values";

    @Inject
    private Configuration configuration;

    @Override
    public FacetedSearchConfigList get() {
        final List<SelectFacetedSearchConfig> selectFacetConfigs = new ArrayList<>();
        final List<RangeFacetedSearchConfig> rangeFacetConfigs = new ArrayList<>();
        final List<Configuration> configList = configuration.getConfigList(CONFIG_FACETS, emptyList());
        IntStream.range(0, configList.size()).forEach(i -> {
            final Configuration config = configList.get(i);
            final SunriseFacetType type = getFacetType(config);
            switch (type) {
                case CATEGORY_TREE:
                case COLUMNS_LIST:
                case LIST:
                    selectFacetConfigs.add(getSelectFacetConfig(type, config, i));
                    break;
                case BUCKET_RANGE:
                case SLIDER_RANGE:
                    rangeFacetConfigs.add(getRangeFacetConfig(type, config, i));
                    break;
                default:
                    throw new SunriseConfigurationException("Unsupported facet type \"" + type + "\"", TYPE_ATTR, CONFIG_FACETS);
            }
        });
        logger.debug("Provide SelectFacetConfigs: {}", selectFacetConfigs.stream()
                .map(config -> config.getFacetBuilder().getKey())
                .collect(toList()));
        return FacetedSearchConfigList.of(selectFacetConfigs, rangeFacetConfigs);
    }

    private static SunriseFacetType getFacetType(final Configuration facetConfig) {
        final String configType = facetConfig.getString(TYPE_ATTR, "").toUpperCase();
        return Arrays.stream(SunriseFacetType.values())
                .filter(typeValue -> typeValue.name().equals(configType))
                .findFirst()
                .orElseThrow(() -> new SunriseConfigurationException("Unrecognized facet type \"" + configType + "\"", TYPE_ATTR, CONFIG_FACETS));
    }

    private static SelectFacetedSearchConfig getSelectFacetConfig(final FacetType type, final Configuration facetConfig, final int position) {
        return SelectFacetedSearchConfig.of(getSelectFacetBuilder(type, facetConfig), position);
    }

    private static RangeFacetedSearchConfig getRangeFacetConfig(final FacetType type, final Configuration facetConfig, final int position) {
        return RangeFacetedSearchConfig.of(getRangeFacetBuilder(type, facetConfig), position);
    }

    private static SelectFacetBuilder<ProductProjection> getSelectFacetBuilder(final FacetType type, final Configuration facetConfig) {
        final String key = Optional.ofNullable(facetConfig.getString(KEY_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing facet key", KEY_ATTR, CONFIG_FACETS));
        final String label = facetConfig.getString(LABEL_ATTR, "");
        final String attrPath = Optional.ofNullable(facetConfig.getString(EXPR_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing facet attribute path expression", EXPR_ATTR, CONFIG_FACETS));
        final boolean countHidden = !facetConfig.getBoolean(COUNT_ATTR, true);
        final boolean matchingAll = facetConfig.getBoolean(MATCHING_ALL_ATTR, false);
        final boolean multiSelect = facetConfig.getBoolean(MULTI_SELECT_ATTR, true);
        final Long limit = facetConfig.getLong(LIMIT_ATTR);
        final Long threshold = facetConfig.getLong(THRESHOLD_ATTR);
        final FacetOptionMapper mapper = getMapper(facetConfig).orElse(null);
        return initializeSelectFacet(type, key, label, attrPath, countHidden, matchingAll, multiSelect, limit, threshold, mapper);
    }

    private static RangeFacetBuilder<ProductProjection> getRangeFacetBuilder(final FacetType type, final Configuration facetConfig) {
        final String key = Optional.ofNullable(facetConfig.getString(KEY_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing facet key", KEY_ATTR, CONFIG_FACETS));
        final String label = facetConfig.getString(LABEL_ATTR, "");
        final String attrPath = Optional.ofNullable(facetConfig.getString(EXPR_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing facet attribute path expression", EXPR_ATTR, CONFIG_FACETS));
        final boolean countHidden = !facetConfig.getBoolean(COUNT_ATTR, true);
        List<FacetRange<String>> initialFacetRanges = new ArrayList<>();
        if (type == SunriseFacetType.BUCKET_RANGE) {
            String initialRanges = Optional.ofNullable(facetConfig.getString(INITIAL_RANGES))
                    .orElseThrow(() -> new SunriseConfigurationException("Missing initial ranges", INITIAL_RANGES, CONFIG_FACETS));
            initialFacetRanges.addAll(convertInitialRangesToFacetRanges(initialRanges));
        } else {
            initialFacetRanges.add(FacetRange.of(null, null));
        }
        return initializeRangeFacet(type, key, label, attrPath, countHidden, initialFacetRanges);
    }

    private static List<FacetRange<String>> convertInitialRangesToFacetRanges(String initialRanges) {
        List<FacetRange<String>> result = new ArrayList<>();
        for(String initialRange : initialRanges.split(",")) {
            String[] tempArr = initialRange.split("-");
            if(tempArr[0].equals("*")) {
                result.add(FacetRange.lessThan(tempArr[1]));
            } else if (tempArr[1].equals("*")){
                result.add(FacetRange.atLeast(tempArr[0]));
            } else {
                result.add(FacetRange.of(tempArr[0], tempArr[1]));
            }
        }
        return result;
    }

    private static Optional<FacetOptionMapper> getMapper(final Configuration facetConfig) {
        return Optional.ofNullable(facetConfig.getConfig(MAPPER_ATTR))
                .map(config -> {
                    final String type = config.getString(MAPPER_TYPE_ATTR, "");
                    final List<String> values = config.getStringList(MAPPER_VALUES_ATTR, emptyList());
                    return initializeMapper(type, values);
                });
    }

    @Nullable
    private static FacetOptionMapper initializeMapper(final String type, final List<String> values) {
        switch (type) {
            case "category_tree":
                return CategoryTreeFacetOptionMapper.ofEmptyTree();
            case "alphabetically_sorted":
                return AlphabeticallySortedFacetOptionMapper.of();
            case "custom_sorted":
                return CustomSortedFacetOptionMapper.of(values);
            default:
                return null;
        }
    }

    private static SelectFacetBuilder<ProductProjection> initializeSelectFacet(final FacetType type, final String key,
                                                                               final String label, final String attrPath,
                                                                               final boolean countHidden, final boolean matchingAll,
                                                                               final boolean multiSelect, @Nullable final Long limit,
                                                                               @Nullable final Long threshold, @Nullable final FacetOptionMapper mapper) {
        final FacetedSearchSearchModel<ProductProjection> searchModel = TermFacetedSearchSearchModel.of(attrPath);
        return SelectFacetBuilder.of(key, searchModel)
                .countHidden(countHidden)
                .type(type)
                .label(label)
                .matchingAll(matchingAll)
                .multiSelect(multiSelect)
                .limit(limit)
                .threshold(threshold)
                .mapper(mapper);
    }

    private static RangeFacetBuilder<ProductProjection> initializeRangeFacet(final FacetType type, final String key,
                                                                             final String label, final String attrPath,
                                                                             final boolean countHidden,
                                                                             final List<FacetRange<String>> initialFacetRanges) {
        final RangeTermFacetedSearchSearchModel<ProductProjection> rangeSearchModel = RangeTermFacetedSearchSearchModel.of(attrPath);
        return RangeFacetBuilder.of(key, rangeSearchModel)
                .type(type)
                .label(label)
                .countHidden(countHidden)
                .initialRanges(initialFacetRanges);
    }
}
