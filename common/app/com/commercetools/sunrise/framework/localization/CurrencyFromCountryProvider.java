package com.commercetools.sunrise.framework.localization;

import com.neovisionaries.i18n.CountryCode;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Optional;

/**
 * Provides the {@link CurrencyUnit} corresponding to the injected {@link CountryCode}.
 */
public final class CurrencyFromCountryProvider implements Provider<CurrencyUnit> {

    private final CountryCode country;
    private final ProjectLocalization projectLocalization;

    @Inject
    CurrencyFromCountryProvider(final CountryCode country, final ProjectLocalization projectLocalization) {
        this.country = country;
        this.projectLocalization = projectLocalization;
    }

    @Override
    public CurrencyUnit get() {
        return findCurrentCurrency()
                .filter(projectLocalization::isCurrencySupported)
                .orElseGet(projectLocalization::defaultCurrency);
    }

    private Optional<CurrencyUnit> findCurrentCurrency() {
        return Optional.ofNullable(country.getCurrency())
                .map(countryCurrency -> Monetary.getCurrency(countryCurrency.getCurrencyCode()));
    }
}
