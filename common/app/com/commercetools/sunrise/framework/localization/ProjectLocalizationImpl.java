package com.commercetools.sunrise.framework.localization;

import com.commercetools.sunrise.ctp.project.ProjectSettings;
import com.commercetools.sunrise.play.configuration.SunriseConfigurationException;
import com.google.inject.Inject;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.projects.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import java.util.List;
import java.util.function.Supplier;

@Singleton
final class ProjectLocalizationImpl implements ProjectLocalization {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectLocalization.class);

    private final List<CountryCode> countryCodes;
    private final List<CurrencyUnit> currencies;

    @Inject
    ProjectLocalizationImpl(final ProjectSettings settings, final Provider<Project> projectProvider) {
        this.countryCodes = valueIfNotEmptyOr(settings.countries(), () -> projectProvider.get().getCountries());
        this.currencies = valueIfNotEmptyOr(settings.currencies(), () -> projectProvider.get().getCurrencyUnits());
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

    private static <T> List<T> valueIfNotEmptyOr(final List<T> valuesFromConfig, final Supplier<List<T>> fallbackValuesSupplier) {
        final List<T> values = valuesFromConfig.isEmpty() ? fallbackValuesSupplier.get() : valuesFromConfig;
        if (values.isEmpty()) {
            throw new SunriseConfigurationException("CTP project is missing countries or currencies");
        }
        return values;
    }
}
