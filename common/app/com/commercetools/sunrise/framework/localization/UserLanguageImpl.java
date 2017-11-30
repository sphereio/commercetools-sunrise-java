package com.commercetools.sunrise.framework.localization;

import com.commercetools.sunrise.ctp.project.ProjectContext;
import com.commercetools.sunrise.framework.injection.RequestScoped;
import io.sphere.sdk.models.Base;
import play.i18n.Lang;
import play.i18n.Langs;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * Provides a distinct list with the selected {@link Locale} and the those coming from the AcceptedLanguage HTTP header.
 * Languages not supported by the {@link ProjectContext} are discarded.
 */
@RequestScoped
@Deprecated
final class UserLanguageImpl extends Base implements UserLanguage {

    private final Langs langs;

    @Inject
    UserLanguageImpl(final Langs langs) {
        this.langs = langs;
    }

    @Override
    public Locale locale() {
        return langs.preferred(candidateLanguages().orElseGet(Collections::emptyList)).toLocale();
    }

    @Override
    public List<Locale> locales() {
        return candidateLanguages().orElseGet(() -> singletonList(langs.preferred(emptyList()))).stream()
                .filter(lang -> langs.availables().contains(lang))
                .map(Lang::toLocale)
                .distinct()
                .collect(toList());
    }

    private Optional<List<Lang>> candidateLanguages() {
        return Optional.ofNullable(Http.Context.current.get())
                .map(context -> Stream.concat(Stream.of(context.lang()), context.request().acceptLanguages().stream())
                        .collect(toList()));
    }

}
