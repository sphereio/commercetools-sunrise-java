package com.commercetools.sunrise.ctp.project;

import com.commercetools.sunrise.framework.localization.ProjectLocalization;
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

    private final ProjectLocalization projectLocalization;
    private final Langs langs;

    @Inject
    ProjectContextImpl(final ProjectLocalization projectLocalization, final Langs langs) {
        this.projectLocalization = projectLocalization;
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
        return projectLocalization.countries();
    }

    @Override
    public List<CurrencyUnit> currencies() {
        return projectLocalization.currencies();
    }

    @Override
    public Locale defaultLocale() {
        return langs.preferred(langs.availables()).toLocale();
    }
}