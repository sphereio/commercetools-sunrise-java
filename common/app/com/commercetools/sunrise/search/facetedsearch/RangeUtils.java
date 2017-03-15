package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FilterRange;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public final class RangeUtils {

    private static final Pattern PATTERN = Pattern.compile("^\\(\\s*(?<lower>\\S+)\\s+to\\s+(?<upper>\\S+)\\s*\\)$");

    private RangeUtils() {
    }

    public static Optional<FilterRange<String>> parseFilterRange(@Nullable final String lowerEndpoint, @Nullable final String upperEndpoint) {
        final String rangeAsString = buildRange(lowerEndpoint, upperEndpoint);
        return parseFilterRange(rangeAsString);
    }

    public static Optional<FacetRange<String>> parseFacetRange(@Nullable final String lowerEndpoint, @Nullable final String upperEndpoint) {
        final String rangeAsString = buildRange(lowerEndpoint, upperEndpoint);
        return parseFacetRange(rangeAsString);
    }


    public static List<FilterRange<String>> optionsToFilterRange(final List<BucketRangeFacetedSearchFormOption> rangeOptions) {
        return rangeOptions.stream()
                .map(RangeUtils::optionToFilterRange)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    public static List<FacetRange<String>> optionsToFacetRange(final List<BucketRangeFacetedSearchFormOption> rangeOptions) {
        return rangeOptions.stream()
                .map(RangeUtils::optionToFacetRange)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    public static Optional<FilterRange<String>> optionToFilterRange(final BucketRangeFacetedSearchFormOption rangeOption) {
        return parseFilterRange(rangeOption.getValue())
                .filter(range -> range.isBounded());
    }

    public static Optional<FacetRange<String>> optionToFacetRange(final BucketRangeFacetedSearchFormOption rangeOption) {
          return parseFacetRange(rangeOption.getValue())
                .filter(range -> range.isBounded());
    }

    /**
     * Parses a range of the form {@code (x to y)} to a {@link FilterRange<String>}.
     * @param rangeAsString range of the form {@code (x to y)}
     * @return the {@link FilterRange} corresponding to that range, or empty if it could not be parsed
     */
    private static Optional<FilterRange<String>> parseFilterRange(final String rangeAsString) {
        return Optional.ofNullable(parseRangeToPair(rangeAsString))
                .filter(pair -> pair.getRight() != null || pair.getLeft() != null)
                .map(pair -> {
                    if (pair.getLeft() != null && pair.getRight() != null) {
                        return FilterRange.of(pair.getLeft(), pair.getRight());
                    } else if (pair.getLeft() != null) {
                        return FilterRange.atLeast(pair.getLeft());
                    } else {
                        return FilterRange.atMost(pair.getRight());
                    }
                });
    }

    /**
     * Parses a range of the form {@code (x to y)} to a {@link FacetRange<String>}.
     * @param rangeAsString range of the form {@code (x to y)}
     * @return the {@link FacetRange} corresponding to that range, or empty if it could not be parsed
     */
    private static Optional<FacetRange<String>> parseFacetRange(final String rangeAsString) {
        return Optional.ofNullable(parseRangeToPair(rangeAsString))
                .filter(pair -> pair.getRight() != null || pair.getLeft() != null)
                .map(pair -> {
                    if (pair.getLeft() != null && pair.getRight() != null) {
                        return FacetRange.of(pair.getLeft(), pair.getRight());
                    } else if (pair.getLeft() != null) {
                        return FacetRange.atLeast(pair.getLeft());
                    } else {
                        return FacetRange.lessThan(pair.getRight());
                    }
                });
    }

    @Nullable
    private static Pair<String, String> parseRangeToPair(final String rangeAsString) {
        final Matcher matcher = PATTERN.matcher(rangeAsString.trim());
        if (matcher.matches()) {
            final String lowerEndpoint = matcher.group("lower");
            final String upperEndpoint = matcher.group("upper");
            return ImmutablePair.of(boundEndpointOrNull(lowerEndpoint), boundEndpointOrNull(upperEndpoint));
        }
        return null;
    }

    private static String buildRange(@Nullable final String lowerEndpoint, @Nullable final String upperEndpoint) {
        return String.format("(%s to %s)",
                boundEndpointOrAsterisk(lowerEndpoint),
                boundEndpointOrAsterisk(upperEndpoint));
    }

    @Nullable
    private static String boundEndpointOrNull(@Nullable final String endpoint) {
        return endpoint == null || endpoint.equals("*") ? null : endpoint;
    }

    private static String boundEndpointOrAsterisk(@Nullable final String endpoint) {
        return endpoint == null ? "*" : endpoint;
    }
}
