package com.commercetools.sunrise.search.facetedsearch.terms.mappers;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.viewmodels.TermFacetOptionViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import io.sphere.sdk.search.TermFacetResult;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that sorts the options according to the alphabetical order of the label.
 */
@RequestScoped
public final class AlphabeticallySortedTermFacetMapper implements TermFacetMapper {

    private final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory;
    private final Http.Context httpContext;

    @Inject
    AlphabeticallySortedTermFacetMapper(final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory,
                                        final Http.Context httpContext) {
        this.termFacetOptionViewModelFactory = termFacetOptionViewModelFactory;
        this.httpContext = httpContext;
    }

    @Override
    public List<FacetOptionViewModel> apply(final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        final List<String> selectedValues = settings.getAllSelectedValues(httpContext);
        return facetResult.getTerms().stream()
                .map(term -> termFacetOptionViewModelFactory.create(term, selectedValues))
                .sorted(Comparator.comparing(FacetOptionViewModel::getLabel))
                .collect(toList());
    }
}
