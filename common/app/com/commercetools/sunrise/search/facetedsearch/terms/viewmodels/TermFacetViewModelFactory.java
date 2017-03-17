package com.commercetools.sunrise.search.facetedsearch.terms.viewmodels;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.AbstractMultiOptionFacetViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import io.sphere.sdk.search.TermFacetResult;
import play.inject.Injector;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequestScoped
public class TermFacetViewModelFactory extends AbstractMultiOptionFacetViewModelFactory<TermFacetViewModel, TermFacetedSearchFormSettings<?>, TermFacetResult> {

    private final Http.Request httpRequest;
    private final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory;
    private final Injector injector;

    @Inject
    public TermFacetViewModelFactory(final I18nIdentifierResolver i18nIdentifierResolver, final Http.Request httpRequest,
                                     final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory, final Injector injector) {
        super(i18nIdentifierResolver);
        this.httpRequest = httpRequest;
        this.termFacetOptionViewModelFactory = termFacetOptionViewModelFactory;
        this.injector = injector;
    }

    protected final TermFacetOptionViewModelFactory getTermFacetOptionViewModelFactory() {
        return termFacetOptionViewModelFactory;
    }

    @Override
    protected TermFacetViewModel newViewModelInstance(final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        return new TermFacetViewModel();
    }

    @Override
    public final TermFacetViewModel create(final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        return super.create(settings, facetResult);
    }

    @Override
    protected final void initialize(final TermFacetViewModel viewModel, final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        super.initialize(viewModel, settings, facetResult);
        fillKey(viewModel, settings, facetResult);
    }

    protected void fillKey(final TermFacetViewModel viewModel, final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        viewModel.setKey(settings.getFieldName());
    }

    @Override
    protected void fillAvailable(final TermFacetViewModel viewModel, final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        Optional.ofNullable(settings.getThreshold())
                .map(threshold -> facetResult.getTerms().size() >= threshold)
                .ifPresent(viewModel::setAvailable);
    }

    @Override
    protected void fillLimitedOptions(final TermFacetViewModel viewModel, final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        List<FacetOptionViewModel> options = createOptions(settings, facetResult);
        if (settings.getLimit() != null) {
            options = options.stream().limit(settings.getLimit()).collect(toList());
        }
        viewModel.setLimitedOptions(options);
    }

    private List<FacetOptionViewModel> createOptions(final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        final List<String> selectedValues = settings.getAllSelectedValues(httpRequest);
        return Optional.ofNullable(settings.getMapperSettings())
                .flatMap(mapperSettings -> Optional.ofNullable(mapperSettings.getType().factory()))
                .map(factoryClass -> injector.instanceOf(factoryClass).create(settings.getMapperSettings()))
                .map(mapper -> mapper.apply(facetResult, selectedValues))
                .orElseGet(() -> facetResult.getTerms().stream()
                        .map(stats -> termFacetOptionViewModelFactory.create(stats, selectedValues))
                        .collect(toList()));
    }
}
