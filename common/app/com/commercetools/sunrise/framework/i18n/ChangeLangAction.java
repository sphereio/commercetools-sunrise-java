package com.commercetools.sunrise.framework.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.Arrays.asList;

final class ChangeLangAction extends Action.Simple {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeLang.class);
    private static final String ROUTE_LANGUAGE_VAR = "languageTag";

    @Override
    public CompletionStage<Result> call(final Http.Context ctx) {
        findCurrentLanguage(ctx)
                .filter(languageTag -> isDifferentLanguage(ctx, languageTag))
                .ifPresent(languageTag -> changeLanguage(ctx, languageTag));
        return delegate.call(ctx);
    }

    private void changeLanguage(final Http.Context ctx, final String languageTag) {
        if (!ctx.changeLang(languageTag)) {
            LOGGER.debug("Could not change language to '{}'", languageTag);
        }
    }

    private Optional<String> findCurrentLanguage(final Http.Context ctx) {
        return indexOfLanguageTagInRoutePattern(ctx)
                .map(index -> ctx.request().path().split("/")[index]);
    }

    private Optional<Integer> indexOfLanguageTagInRoutePattern(final Http.Context ctx) {
        return Optional.ofNullable(ctx.args.get("ROUTE_PATTERN"))
                .map(routePattern -> routePattern.toString().replaceAll("<[^>]+>", "")) // Remove regex because splitting '$languageTag<[^/]+>' with '/' would create more words
                .map(routePattern -> {
                    final List<String> paths = asList(routePattern.split("/"));
                    return paths.indexOf("$" + ROUTE_LANGUAGE_VAR);
                })
                .filter(index -> index >= 0);
    }

    private boolean isDifferentLanguage(final Http.Context ctx, final String languageTag) {
        return !languageTag.equals(ctx.lang().code());
    }
}
