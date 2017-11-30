package com.commercetools.sunrise.framework.localization;

import com.google.inject.ProvisionException;
import io.sphere.sdk.projects.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;
import play.i18n.Lang;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Singleton
public class DefaultCurrencies implements Currencies {

    private static final Logger LOGGER = LoggerFactory.getLogger(Countries.class);
    private static final CurrencyUnit SYSTEM_DEFAULT_CURRENCY = Monetary.getCurrency(Lang.defaultLang().toLocale());

    private final List<CurrencyUnit> availables;

    @Inject
    DefaultCurrencies(final Configuration configuration, final Provider<Project> projectProvider) {
        this.availables = configuredCurrencies(configuration)
                .map(countries -> countries.stream()
                        .map(Monetary::getCurrency)
                        .collect(toList()))
                .orElseGet(() -> loadFallbackCurrencies(projectProvider));
    }

    @Override
    public List<CurrencyUnit> availables() {
        return availables;
    }

    @Override
    public CurrencyUnit preferred(final List<CurrencyUnit> candidates) {
        return candidates.stream()
                .filter(availables::contains)
                .findFirst()
                .orElseGet(() -> availables.stream()
                        .findFirst()
                        .orElse(SYSTEM_DEFAULT_CURRENCY));
    }

    private static Optional<List<String>> configuredCurrencies(final Configuration configuration) {
        return Optional.ofNullable(configuration.getStringList("sunrise.ctp.project.currencies"))
                .map(countries -> {
                    LOGGER.warn("sunrise.ctp.project.currencies is deprecated, use sunrise.localization.currencies instead");
                    return Optional.of(countries);
                }).orElseGet(() -> Optional.ofNullable(configuration.getStringList("sunrise.localization.currencies")));
    }

    private static List<CurrencyUnit> loadFallbackCurrencies(final Provider<Project> projectProvider) {
        try {
            final List<CurrencyUnit> projectCurrencies = projectProvider.get().getCurrencyUnits();
            return projectCurrencies.isEmpty() ? singletonList(SYSTEM_DEFAULT_CURRENCY) : projectCurrencies;
        } catch (ProvisionException e) {
            LOGGER.warn("Currencies from CTP could not be provided, falling back to default currency");
            return singletonList(SYSTEM_DEFAULT_CURRENCY);
        }
    }
}
