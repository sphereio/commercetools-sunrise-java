package com.commercetools.sunrise.common.localization;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.localization.ProjectLocalization;
import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.forms.countries.CountryFormSelectableOptionViewModel;
import com.commercetools.sunrise.framework.viewmodels.forms.countries.CountryFormSelectableOptionViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.forms.languages.LanguageFormSelectableOptionViewModel;
import com.commercetools.sunrise.framework.viewmodels.forms.languages.LanguageFormSelectableOptionViewModelFactory;
import com.neovisionaries.i18n.CountryCode;
import play.i18n.Lang;
import play.i18n.Langs;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequestScoped
public class LocalizationSelectorViewModelFactory extends SimpleViewModelFactory<LocalizationSelectorViewModel, Void> {

    private final CountryCode currentCountry;
    private final List<Lang> availableLanguages;
    private final List<CountryCode> availableCountries;
    private final CountryFormSelectableOptionViewModelFactory countryFormSelectableOptionViewModelFactory;
    private final LanguageFormSelectableOptionViewModelFactory languageFormSelectableOptionViewModelFactory;

    @Inject
    public LocalizationSelectorViewModelFactory(final Langs langs, final CountryCode currentCountry, final ProjectLocalization projectLocalization,
                                                final CountryFormSelectableOptionViewModelFactory countryFormSelectableOptionViewModelFactory,
                                                final LanguageFormSelectableOptionViewModelFactory languageFormSelectableOptionViewModelFactory) {
        this.availableLanguages = langs.availables();
        this.currentCountry = currentCountry;
        this.availableCountries = projectLocalization.countries();
        this.countryFormSelectableOptionViewModelFactory = countryFormSelectableOptionViewModelFactory;
        this.languageFormSelectableOptionViewModelFactory = languageFormSelectableOptionViewModelFactory;
    }

    protected final CountryCode getCurrentCountry() {
        return currentCountry;
    }

    protected final List<CountryCode> getAvailableCountries() {
        return availableCountries;
    }

    protected final List<Lang> getAvailableLanguages() {
        return availableLanguages;
    }

    /**
     * @return the current language
     * @deprecated use {@link Http.Context} instead to obtain current language
     */
    @Deprecated
    protected final Locale getLocale() {
        return Http.Context.current().lang().toLocale();
    }

    /**
     * @return current country
     * @deprecated use {@link #getCurrentCountry()} instead
     */
    @Deprecated
    protected final CountryCode getCountry() {
        return getCurrentCountry();
    }

    protected final CountryFormSelectableOptionViewModelFactory getCountryFormSelectableOptionViewModelFactory() {
        return countryFormSelectableOptionViewModelFactory;
    }

    protected final LanguageFormSelectableOptionViewModelFactory getLanguageFormSelectableOptionViewModelFactory() {
        return languageFormSelectableOptionViewModelFactory;
    }

    @Override
    protected LocalizationSelectorViewModel newViewModelInstance(final Void input) {
        return new LocalizationSelectorViewModel();
    }

    @Override
    public final LocalizationSelectorViewModel create(final Void input) {
        return super.create(input);
    }

    public final LocalizationSelectorViewModel create() {
        return super.create(null);
    }

    @Override
    protected final void initialize(final LocalizationSelectorViewModel viewModel, final Void input) {
        fillCountry(viewModel);
        fillLanguage(viewModel);
    }

    protected void fillCountry(final LocalizationSelectorViewModel viewModel) {
        final List<CountryFormSelectableOptionViewModel> options = new ArrayList<>();
        if (availableCountries.size() > 1) {
            availableCountries.forEach(country ->
                    options.add(countryFormSelectableOptionViewModelFactory.create(country, this.currentCountry)));
        }
        viewModel.setCountry(options);
    }

    protected void fillLanguage(final LocalizationSelectorViewModel viewModel) {
        final List<LanguageFormSelectableOptionViewModel> options = new ArrayList<>();
        if (availableLanguages.size() > 1) {
            availableLanguages.forEach(lang -> {
                final Locale currentLocale = Http.Context.current().lang().toLocale();
                options.add(languageFormSelectableOptionViewModelFactory.create(lang.toLocale(), currentLocale));
            });
        }
        viewModel.setLanguage(options);
    }
}
