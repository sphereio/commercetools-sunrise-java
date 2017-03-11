package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.consumers.PageDataReadyHook;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.search.pagination.viewmodels.AbstractEntriesPerPageSelectorViewModelFactory;
import com.commercetools.sunrise.search.pagination.viewmodels.AbstractPaginationViewModelFactory;
import com.commercetools.sunrise.search.pagination.viewmodels.WithPaginationViewModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.PagedResult;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class AbstractPaginationControllerComponent extends Base implements ControllerComponent, PageDataReadyHook {

    private static final int DEFAULT_LIMIT = 20;

    private final int offset;
    private final int limit;
    private final AbstractPaginationViewModelFactory paginationViewModelFactory;
    private final AbstractEntriesPerPageSelectorViewModelFactory entriesPerPageSelectorViewModelFactory;

    @Nullable
    private PagedResult<?> pagedResult;

    protected AbstractPaginationControllerComponent(final PaginationSettings paginationSettings,
                                                    final EntriesPerPageFormSettings entriesPerPageFormSettings,
                                                    final AbstractPaginationViewModelFactory paginationViewModelFactory,
                                                    final AbstractEntriesPerPageSelectorViewModelFactory entriesPerPageSelectorViewModelFactory,
                                                    final Http.Request httpRequest) {
        this.limit = Optional.ofNullable(entriesPerPageFormSettings.getSelectedValue(httpRequest)).orElse(DEFAULT_LIMIT);
        this.offset = (paginationSettings.getSelectedValue(httpRequest) - 1) * limit;
        this.paginationViewModelFactory = paginationViewModelFactory;
        this.entriesPerPageSelectorViewModelFactory = entriesPerPageSelectorViewModelFactory;
    }

    protected final int getOffset() {
        return offset;
    }

    protected final int getLimit() {
        return limit;
    }

    @Nullable
    protected final PagedResult<?> getPagedResult() {
        return pagedResult;
    }

    protected final void setPagedResult(@Nullable final PagedResult<?> pagedResult) {
        this.pagedResult = pagedResult;
    }

    @Override
    public void onPageDataReady(final PageData pageData) {
        if (pagedResult != null && pageData.getContent() instanceof WithPaginationViewModel) {
            final WithPaginationViewModel content = (WithPaginationViewModel) pageData.getContent();
            content.setPagination(paginationViewModelFactory.create(pagedResult));
            content.setDisplaySelector(entriesPerPageSelectorViewModelFactory.create(pagedResult));
        }
    }
}
