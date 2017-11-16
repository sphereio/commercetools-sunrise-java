package com.commercetools.sunrise.framework.cms.filebased;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.cms.CmsService;
import com.commercetools.sunrise.framework.cms.CmsMessagesApi;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Service that provides content data from i18n files that can be found in a local file.
 * Internally it uses a I18nResolver to resolve the requested messages.
 *
 * The mapping of the {@link CmsService} parameters to {@link CmsMessagesApi} parameters goes as follows:
 *
 * - {@code bundle} = {@code entryType} (e.g. banner)
 * - {@code messageKey} = {@code entryKey.fieldName} (e.g. homeTopLeft.subtitle.text)
 */
public final class FileBasedCmsService implements CmsService {

    private CmsMessagesApi cmsMessagesApi;

    @Inject
    FileBasedCmsService(final CmsMessagesApi cmsMessagesApi) {
        this.cmsMessagesApi = cmsMessagesApi;
    }

    @Override
    public CompletionStage<Optional<CmsPage>> page(final String pageKey, final List<Locale> locales) {
        final CmsPage cmsPage = new FileBasedCmsPage(cmsMessagesApi, pageKey, locales);
        return completedFuture(Optional.of(cmsPage));
    }
}