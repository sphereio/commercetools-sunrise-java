package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettingsList;
import io.sphere.sdk.search.PagedSearchResult;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public abstract class AbstractFacetSelectorListViewModelFactory<T> extends SimpleViewModelFactory<FacetSelectorListViewModel, PagedSearchResult<T>> {

    private final Locale locale;
    private final FacetedSearchFormSettingsList<T> settingsList;
    private final TermFacetSelectorViewModelFactory termFacetSelectorViewModelFactory;
    private final BucketRangeFacetSelectorViewModelFactory bucketRangeFacetSelectorViewModelFactory;
    private final SliderRangeFacetSelectorViewModelFactory sliderRangeFacetSelectorViewModelFactory;

    protected AbstractFacetSelectorListViewModelFactory(final Locale locale, final FacetedSearchFormSettingsList<T> settingsList,
                                                        final TermFacetSelectorViewModelFactory termFacetSelectorViewModelFactory,
                                                        final BucketRangeFacetSelectorViewModelFactory bucketRangeFacetSelectorViewModelFactory,
                                                        final SliderRangeFacetSelectorViewModelFactory sliderRangeFacetSelectorViewModelFactory) {
        this.locale = locale;
        this.settingsList = settingsList;
        this.termFacetSelectorViewModelFactory = termFacetSelectorViewModelFactory;
        this.bucketRangeFacetSelectorViewModelFactory = bucketRangeFacetSelectorViewModelFactory;
        this.sliderRangeFacetSelectorViewModelFactory = sliderRangeFacetSelectorViewModelFactory;
    }

    protected final Locale getLocale() {
        return locale;
    }

    protected final FacetedSearchFormSettingsList<T> getSettingsList() {
        return settingsList;
    }

    protected final TermFacetSelectorViewModelFactory getTermFacetSelectorViewModelFactory() {
        return termFacetSelectorViewModelFactory;
    }

    protected final BucketRangeFacetSelectorViewModelFactory getBucketRangeFacetSelectorViewModelFactory() {
        return bucketRangeFacetSelectorViewModelFactory;
    }

    protected final SliderRangeFacetSelectorViewModelFactory getSliderRangeFacetSelectorViewModelFactory() {
        return sliderRangeFacetSelectorViewModelFactory;
    }

    @Override
    protected final FacetSelectorListViewModel newViewModelInstance(final PagedSearchResult<T> pagedSearchResult) {
        return new FacetSelectorListViewModel();
    }

    @Override
    public final FacetSelectorListViewModel create(final PagedSearchResult<T> pagedSearchResult) {
        final FacetSelectorListViewModel viewModel = newViewModelInstance(pagedSearchResult);
        initialize(viewModel, pagedSearchResult);
        return viewModel;
    }

    @Override
    protected final void initialize(final FacetSelectorListViewModel viewModel, final PagedSearchResult<T> pagedSearchResult) {
        fillList(viewModel, pagedSearchResult);
    }

    protected void fillList(final FacetSelectorListViewModel viewModel, final PagedSearchResult<T> pagedSearchResult) {
        viewModel.setList(concat(createTermFacets(pagedSearchResult), concat(createBucketRangeFacets(pagedSearchResult), createSliderRangeFacets(pagedSearchResult)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparingDouble(Pair::getLeft))
                .map(Pair::getRight)
                .collect(toList()));
    }

    private Stream<Optional<Pair<Integer, FacetSelectorViewModel>>> createTermFacets(final PagedSearchResult<T> pagedSearchResult) {
        return settingsList.getTermSettings().stream()
                .map(settings -> settings.findFacetResult(pagedSearchResult, locale)
                        .map(facetResult -> createPair(settings, termFacetSelectorViewModelFactory.create(settings, facetResult))));
    }

    private Stream<Optional<Pair<Integer, FacetSelectorViewModel>>> createBucketRangeFacets(final PagedSearchResult<T> pagedSearchResult) {
        return settingsList.getBucketRangeSettings().stream()
                .map(settings -> settings.findFacetResult(pagedSearchResult, locale)
                        .map(facetResult -> createPair(settings, bucketRangeFacetSelectorViewModelFactory.create(settings, facetResult))));
    }

    private Stream<Optional<Pair<Integer, FacetSelectorViewModel>>> createSliderRangeFacets(final PagedSearchResult<T> pagedSearchResult) {
        return settingsList.getSliderRangeSettings().stream()
                .map(settings -> settings.findFacetResult(pagedSearchResult, locale)
                        .map(facetResult -> createPair(settings, sliderRangeFacetSelectorViewModelFactory.create(settings, facetResult))));
    }

    private ImmutablePair<Integer, FacetSelectorViewModel> createPair(final FacetedSearchFormSettings<T> settings, final FacetSelectorViewModel viewModel) {
        return ImmutablePair.of(settings.getPosition(), viewModel);
    }
}
