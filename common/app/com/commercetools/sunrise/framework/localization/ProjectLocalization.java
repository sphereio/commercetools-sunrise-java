package com.commercetools.sunrise.framework.localization;

import com.google.inject.ImplementedBy;
import com.neovisionaries.i18n.CountryCode;

import javax.money.CurrencyUnit;
import java.util.List;
import java.util.Objects;

@ImplementedBy(ProjectLocalizationImpl.class)
public interface ProjectLocalization {

    /**
     * Countries associated to the project.
     * @return the list of country codes
     */
    List<CountryCode> countries();

    /**
     * Currencies associated to the project.
     * @return the list of currency units
     */
    List<CurrencyUnit> currencies();

    default CountryCode defaultCountry() {
        return countries().stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new NoCountryFoundException("Project does not have any valid country code associated"));
    }

    default CurrencyUnit defaultCurrency() {
        return currencies().stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new NoCurrencyFoundException("Project does not have any valid currency unit associated"));
    }

    default boolean isCountrySupported(final CountryCode countryCode) {
        return countries().contains(countryCode);
    }

    default boolean isCurrencySupported(final CurrencyUnit currency) {
        return currencies().contains(currency);
    }
}
