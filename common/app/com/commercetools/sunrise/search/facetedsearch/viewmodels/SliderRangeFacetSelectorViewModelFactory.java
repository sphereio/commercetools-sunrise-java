package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.framework.viewmodels.ViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.SliderRangeFacetedSearchFormSettings;
import io.sphere.sdk.search.model.SimpleRangeStats;
import play.mvc.Http;

import javax.inject.Inject;

@RequestScoped
public class SliderRangeFacetSelectorViewModelFactory extends ViewModelFactory {

    private final I18nIdentifierResolver i18nIdentifierResolver;
    private final Http.Request httpRequest;
    private final SliderRangeEndpointViewModelFactory sliderRangeEndpointViewModelFactory;

    @Inject
    public SliderRangeFacetSelectorViewModelFactory(final I18nIdentifierResolver i18nIdentifierResolver, final Http.Request httpRequest,
                                                    final SliderRangeEndpointViewModelFactory sliderRangeEndpointViewModelFactory) {
        this.i18nIdentifierResolver = i18nIdentifierResolver;
        this.httpRequest = httpRequest;
        this.sliderRangeEndpointViewModelFactory = sliderRangeEndpointViewModelFactory;
    }

    protected final I18nIdentifierResolver getI18nIdentifierResolver() {
        return i18nIdentifierResolver;
    }

    protected final Http.Request getHttpRequest() {
        return httpRequest;
    }

    protected final SliderRangeEndpointViewModelFactory getSliderRangeEndpointViewModelFactory() {
        return sliderRangeEndpointViewModelFactory;
    }

    protected FacetViewModel newViewModelInstance(final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        return new FacetViewModel();
    }

    public final FacetViewModel create(final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        final FacetViewModel viewModel = newViewModelInstance(settings, rangeStats);
        initialize(viewModel, settings, rangeStats);
        return viewModel;
    }

    protected final void initialize(final FacetViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        fillLabel(viewModel, settings, rangeStats);
        fillAvailable(viewModel, settings, rangeStats);
        fillCountHidden(viewModel, settings, rangeStats);
        fillEndpoints(viewModel, settings, rangeStats);
    }

    protected void fillLabel(final FacetViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setLabel(i18nIdentifierResolver.resolveOrKey(settings.getLabel()));
    }

    protected void fillCountHidden(final FacetViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setCountHidden(!settings.isCountDisplayed());
    }

    protected void fillAvailable(final FacetViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setAvailable(rangeStats.getCount() > 0);
    }

    protected void fillEndpoints(final FacetViewModel viewModel, final SliderRangeFacetedSearchFormSettings<?> settings, final SimpleRangeStats rangeStats) {
        viewModel.setEndpoints(sliderRangeEndpointViewModelFactory.create(settings, rangeStats));
    }
}
