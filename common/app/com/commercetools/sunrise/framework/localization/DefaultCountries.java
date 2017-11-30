package com.commercetools.sunrise.framework.localization;

import com.google.inject.ProvisionException;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.projects.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;
import play.i18n.Lang;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Singleton
public class DefaultCountries implements Countries {

    private static final Logger LOGGER = LoggerFactory.getLogger(Countries.class);
    private static final CountryCode SYSTEM_DEFAULT_COUNTRY = CountryCode.getByLocale(Lang.defaultLang().toLocale());

    private final List<CountryCode> availables;

    @Inject
    DefaultCountries(final Configuration configuration, final Provider<Project> projectProvider) {
        this.availables = configuredCountries(configuration)
                .map(countries -> countries.stream()
                        .map(CountryCode::getByCodeIgnoreCase)
                        .collect(toList()))
                .orElseGet(() -> loadFallbackCountries(projectProvider));
    }

    @Override
    public List<CountryCode> availables() {
        return availables;
    }

    @Override
    public CountryCode preferred(final List<CountryCode> candidates) {
        return candidates.stream()
                .filter(availables::contains)
                .findFirst()
                .orElseGet(() -> availables.stream()
                        .findFirst()
                        .orElse(SYSTEM_DEFAULT_COUNTRY));
    }

    private static Optional<List<String>> configuredCountries(final Configuration configuration) {
        return Optional.ofNullable(configuration.getStringList("sunrise.ctp.project.countries"))
                .map(countries -> {
                    LOGGER.warn("sunrise.ctp.project.countries is deprecated, use sunrise.localization.countries instead");
                    return Optional.of(countries);
                }).orElseGet(() -> Optional.ofNullable(configuration.getStringList("sunrise.localization.countries")));
    }

    private static List<CountryCode> loadFallbackCountries(final Provider<Project> projectProvider) {
        try {
            final List<CountryCode> projectCountries = projectProvider.get().getCountries();
            return projectCountries.isEmpty() ? singletonList(SYSTEM_DEFAULT_COUNTRY) : projectCountries;
        } catch (ProvisionException e) {
            LOGGER.warn("Countries from CTP could not be provided, falling back to default country");
            return singletonList(SYSTEM_DEFAULT_COUNTRY);
        }
    }
}
