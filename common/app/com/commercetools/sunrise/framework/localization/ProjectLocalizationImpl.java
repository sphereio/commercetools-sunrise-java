package com.commercetools.sunrise.framework.localization;

import com.commercetools.sunrise.ctp.project.ProjectSettings;
import com.google.inject.Inject;
import com.google.inject.ProvisionException;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.projects.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.i18n.Lang;

import javax.inject.Provider;
import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;

@Singleton
final class ProjectLocalizationImpl implements ProjectLocalization {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectLocalization.class);

    private final List<CountryCode> countryCodes;
    private final List<CurrencyUnit> currencies;

    @Inject
    ProjectLocalizationImpl(final ProjectSettings settings, final Provider<Project> projectProvider) {
        this.countryCodes = valueIfNotEmptyOr(settings.countries(), () -> fallbackCountries(projectProvider));
        this.currencies = valueIfNotEmptyOr(settings.currencies(), () -> fallbackCurrencies(projectProvider));
        LOGGER.debug("Localization: Countries {}, Currencies {}", countryCodes, currencies);
    }

    @Override
    public List<CountryCode> countries() {
        return countryCodes;
    }

    @Override
    public List<CurrencyUnit> currencies() {
        return currencies;
    }

    private static List<CountryCode> fallbackCountries(final Provider<Project> projectProvider) {
        try {
            final List<CountryCode> projectCountries = projectProvider.get().getCountries();
            return projectCountries.isEmpty() ? systemDefaultCountries() : projectCountries;
        } catch (ProvisionException pe) {
            LOGGER.warn("Countries from CTP project could not be provided, falling back to default locale");
            return systemDefaultCountries();
        }
    }

    private static List<CurrencyUnit> fallbackCurrencies(final Provider<Project> projectProvider) {
        try {
            return projectProvider.get().getCurrencyUnits();
        } catch (ProvisionException pe) {
            LOGGER.warn("Currencies from CTP project could not be provided, falling back to default currency");
            return systemDefaultCurrencies();
        }
    }

    private static <T> List<T> valueIfNotEmptyOr(final List<T> baseValues, final Supplier<List<T>> fallbackValuesSupplier) {
        return baseValues.isEmpty() ? fallbackValuesSupplier.get() : baseValues;
    }

    private static List<CountryCode> systemDefaultCountries() {
        final CountryCode defaultCountry = CountryCode.getByLocale(Lang.defaultLang().toLocale());
        return singletonList(defaultCountry);
    }

    private static List<CurrencyUnit> systemDefaultCurrencies() {
        final CurrencyUnit defaultCurrency = Monetary.getCurrency(Lang.defaultLang().toLocale());
        return singletonList(defaultCurrency);
    }
}
