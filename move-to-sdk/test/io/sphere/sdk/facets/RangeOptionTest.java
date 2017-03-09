package io.sphere.sdk.facets;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RangeOptionTest {

    @Test
    public void createsInstance() throws Exception {
        final RangeOption option = RangeOption.of("50", "100", 10L, true);
        assertThat(option.getLowerEndpoint()).isEqualTo("50");
        assertThat(option.getUpperEndpoint()).isEqualTo("100");
        assertThat(option.getCount()).isEqualTo(10L);
        assertThat(option.isSelected()).isTrue();
    }

    @Test
    public void createsInstanceWithDifferentLowerEndpoint() throws Exception {
        final RangeOption option = RangeOption.of("50", "100", 10L, true);
        assertThat(option.getLowerEndpoint()).isNotEqualTo("70");
        assertThat(option.withLowerEndpoint("70").getLowerEndpoint()).isEqualTo("70");
    }

    @Test
    public void createsInstanceWithDifferentUpperEndpoint() throws Exception {
        final RangeOption option = RangeOption.of("50", "100", 10L, true);
        assertThat(option.getUpperEndpoint()).isNotEqualTo("150");
        assertThat(option.withUpperEndpoint("150").getUpperEndpoint()).isEqualTo("150");
    }

    @Test
    public void createsInstanceWithDifferentCount() throws Exception {
        final RangeOption option = RangeOption.of("50", "100", 10L, true);
        assertThat(option.getCount()).isNotEqualTo(20L);
        assertThat(option.withCount(20L).getCount()).isEqualTo(20L);
    }

    @Test
    public void createsInstanceWithDifferentSelected() throws Exception {
        final RangeOption option = RangeOption.of("50", "100", 10L, true);
        assertThat(option.isSelected()).isTrue();
        assertThat(option.withSelected(false).isSelected()).isFalse();
    }
}
