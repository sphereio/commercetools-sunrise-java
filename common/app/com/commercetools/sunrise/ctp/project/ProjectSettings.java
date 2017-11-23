package com.commercetools.sunrise.ctp.project;

import com.google.inject.ImplementedBy;
import com.neovisionaries.i18n.CountryCode;
import play.Configuration;

import javax.money.CurrencyUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ImplementedBy(ProjectSettingsImpl.class)
public interface ProjectSettings {

    /**
     * @return key from the cache where to store the project
     */
    String cacheKey();

    /**
     * @return time in seconds until the project in cache expires
     */
    Optional<Integer> cacheExpiration();

    /**
     * @return list of languages supported by the project
     */
    List<Locale> languages();

    /**
     * @return list of countries supported by the project
     */
    List<CountryCode> countries();

    /**
     * @return list of currencies supported by the project
     */
    List<CurrencyUnit> currencies();

    static ProjectSettings of(final Configuration globalConfig, final String configPath) {
        return new ProjectSettingsImpl(globalConfig, configPath);
    }
}