package com.commercetools.sunrise.framework.localization;

import com.commercetools.sunrise.sessions.country.CountryInSession;
import com.neovisionaries.i18n.CountryCode;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Optional;

/**
 * Provides the {@link CountryCode} instance of the country saved in the user's session.
 * If not set, it provides the default country from the project.
 */
public final class CountryFromSessionProvider implements Provider<CountryCode> {

    private final ProjectLocalization projectLocalization;
    private final CountryInSession countryInSession;

    @Inject
    CountryFromSessionProvider(final ProjectLocalization projectLocalization, final CountryInSession countryInSession) {
        this.projectLocalization = projectLocalization;
        this.countryInSession = countryInSession;
    }

    @Override
    public CountryCode get() {
        return findCurrentCountry()
                .filter(projectLocalization::isCountrySupported)
                .orElseGet(projectLocalization::defaultCountry);
    }


    private Optional<CountryCode> findCurrentCountry() {
        return countryInSession.findCountry();
    }
}
