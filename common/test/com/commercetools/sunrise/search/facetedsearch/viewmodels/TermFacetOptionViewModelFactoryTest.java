package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import io.sphere.sdk.search.TermStats;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class TermFacetOptionViewModelFactoryTest {

    private static final TermStats TERM_ONE = TermStats.of("one", 30L);
    private static final TermStats TERM_TWO = TermStats.of("two", 20L);
    private static final TermStats TERM_THREE = TermStats.of("three", 10L);

    @Test
    public void createsInstanceFromTermStats() throws Exception {
        final FacetOptionViewModel viewModel = viewModelFactory(TERM_TWO, asList(TERM_TWO, TERM_THREE));
        assertThat(viewModel.getValue()).isEqualTo(TERM_TWO.getTerm());
        assertThat(viewModel.getCount()).isEqualTo(TERM_TWO.getCount());
        assertThat(viewModel.isSelected()).isTrue();
    }

    @Test
    public void createsInstanceFromTermStatsWithUnselectedValue() throws Exception {
        final FacetOptionViewModel viewModel = viewModelFactory(TERM_THREE, asList(TERM_ONE, TERM_TWO));
        assertThat(viewModel.getValue()).isEqualTo(TERM_THREE.getTerm());
        assertThat(viewModel.getCount()).isEqualTo(TERM_THREE.getCount());
        assertThat(viewModel.isSelected()).isFalse();
    }

    private FacetOptionViewModel viewModelFactory(final TermStats termStats, final List<TermStats> selectedTermStats) {
        final List<String> selectedValues = selectedTermStats.stream()
                .map(TermStats::getTerm)
                .collect(toList());
        return new TermFacetOptionViewModelFactory().create(termStats, selectedValues);
    }
}
