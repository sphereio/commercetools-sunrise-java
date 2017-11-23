package com.commercetools.sunrise.ctp.project;

import com.neovisionaries.i18n.CountryCode;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Singleton
class ProjectSettingsImpl implements ProjectSettings {

    private final String cacheKey;
    private final Integer cacheExpiration;
    private final List<Locale> languages;
    private final List<CountryCode> countries;
    private final List<CurrencyUnit> currencies;

    @Inject
    ProjectSettingsImpl(final Configuration configuration) {
        this(configuration, "sunrise.ctp.project");
    }

    ProjectSettingsImpl(final Configuration globalConfig, final String configPath) {
        final Configuration config = globalConfig.getConfig(configPath);
        this.cacheKey = config.getString("cacheKey");
        this.cacheExpiration = config.getInt("cacheExpiration");
        this.languages = config.getStringList("languages", emptyList()).stream()
                .map(Locale::forLanguageTag)
                .collect(toList());
        this.countries = config.getStringList("countries", emptyList()).stream()
                .map(CountryCode::getByCode)
                .collect(toList());
        this.currencies = config.getStringList("currencies", emptyList()).stream()
                .map(Monetary::getCurrency)
                .collect(toList());
    }

    @Override
    public String cacheKey() {
        return cacheKey;
    }

    @Override
    public Optional<Integer> cacheExpiration() {
        return Optional.ofNullable(cacheExpiration);
    }

    @Override
    public List<Locale> languages() {
        return languages;
    }

    @Override
    public List<CountryCode> countries() {
        return countries;
    }

    @Override
    public List<CurrencyUnit> currencies() {
        return currencies;
    }
}
