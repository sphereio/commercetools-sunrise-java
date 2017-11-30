package com.commercetools.sunrise.ctp.project;

import com.commercetools.sunrise.framework.localization.Countries;
import com.commercetools.sunrise.framework.localization.Currencies;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;
import play.i18n.Lang;
import play.i18n.Langs;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

/**
 * Uses the associated commercetools platform project to obtain the supported languages, countries and currencies.
 * Nevertheless it enables the possibility to override these values via configuration.
 */

@Singleton
@Deprecated
final class ProjectContextImpl extends Base implements ProjectContext {

    private final Langs langs;
    private final Countries countries;
    private final Currencies currencies;

    @Inject
    ProjectContextImpl(final Langs langs, final Countries countries, final Currencies currencies) {
        this.countries = countries;
        this.currencies = currencies;
        this.langs = langs;
    }

    @Override
    public List<Locale> locales() {
        return langs.availables().stream()
                .map(Lang::toLocale)
                .collect(toList());
    }

    @Override
    public List<CountryCode> countries() {
        return countries.availables();
    }

    @Override
    public List<CurrencyUnit> currencies() {
        return currencies.availables();
    }

    @Override
    public Locale defaultLocale() {
        return langs.preferred(langs.availables()).toLocale();
    }

    @Override
    public CountryCode defaultCountry() {
        return countries.preferred(countries.availables());
    }

    @Override
    public CurrencyUnit defaultCurrency() {
        return currencies.preferred(currencies.availables());
    }
}