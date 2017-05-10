package com.commercetools.sunrise.myaccount.wishlist.pagination;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.pagination.PaginationSettings;
import com.commercetools.sunrise.search.pagination.PaginationSettingsFactory;
import play.Configuration;

import javax.inject.Inject;
import java.util.Optional;

public class WishlistPaginationSettings implements PaginationSettings {

    private static final String CONFIG = "pop.pagination";

    private final PaginationSettings settings;

    @Inject
    WishlistPaginationSettings(final Configuration configuration,
                               final PaginationSettingsFactory paginationSettingsFactory) {
        this.settings = paginationSettingsFactory.create(configuration(configuration));
    }

    @Override
    public Long getDefaultValue() {
        return settings.getDefaultValue();
    }

    @Override
    public String getFieldName() {
        return settings.getFieldName();
    }

    @Override
    public int getDisplayedPages() {
        return settings.getDisplayedPages();
    }

    private static Configuration configuration(final Configuration configuration) {
        return Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find wishlist pagination configuration", CONFIG));
    }
}
