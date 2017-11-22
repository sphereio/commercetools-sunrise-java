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
        findCurrentLanguage().ifPresent(languageTag -> changeLanguage(ctx, languageTag));
        return delegate.call(ctx);
    }

    private void changeLanguage(final Http.Context ctx, final String languageTag) {
        if (!ctx.changeLang(languageTag)) {
            LOGGER.debug("Could not change language to '{}'", languageTag);
        }
    }

    private Optional<String> findCurrentLanguage() {
        return Optional.ofNullable(Http.Context.current.get())
                .flatMap(httpContext -> indexOfLanguageTagInRoutePattern(httpContext)
                        .map(index -> httpContext.request().path().split("/")[index]));
    }

    private Optional<Integer> indexOfLanguageTagInRoutePattern(final Http.Context httpContext) {
        return Optional.ofNullable(httpContext.args.get("ROUTE_PATTERN"))
                .map(routePattern -> routePattern.toString().replaceAll("<[^>]+>", "")) // Remove regex because splitting '$languageTag<[^/]+>' with '/' would create more words
                .map(routePattern -> {
                    final List<String> paths = asList(routePattern.split("/"));
                    return paths.indexOf("$" + ROUTE_LANGUAGE_VAR);
                })
                .filter(index -> index >= 0);
    }
}
