package com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist;

import com.commercetools.sunrise.framework.reverserouters.AbstractLocalizedReverseRouter;
import play.mvc.Call;

import javax.inject.Inject;
import java.util.Locale;

public class DefaultWishlistReverseRouter extends AbstractLocalizedReverseRouter implements WishlistReverseRouter {
    private final SimpleWishlistReverseRouter delegate;

    @Inject
    public DefaultWishlistReverseRouter(final Locale locale, final SimpleWishlistReverseRouter delegate) {
        super(locale);
        this.delegate = delegate;
    }

    @Override
    public Call addToWishlistProcess(final String languageTag) {
        return delegate.addToWishlistProcess(languageTag);
    }

    @Override
    public Call removeFromWishlistProcess(final String languageTag) {
        return delegate.removeFromWishlistProcess(languageTag);
    }

    @Override
    public Call clearWishlistProcess(final String languageTag) {
        return delegate.clearWishlistProcess(languageTag);
    }

    @Override
    public Call wishlistPageCall(final String languageTag) {
        return delegate.wishlistPageCall(languageTag);
    }
}
