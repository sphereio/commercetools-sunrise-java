package com.commercetools.sunrise.common.categorytree;

/**
 * An object capable of subscribing listeners interested in reacting to refresh of the category tree which it holds.
 */
public interface CategoryTreeRefresher {
    void addListener(CategoryTreeRefreshListener refreshListener);
}
