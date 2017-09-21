package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.content.PageContentFactory;
import com.commercetools.sunrise.framework.viewmodels.PageTitleResolver;

import javax.inject.Inject;

public class SuccessfulRecoveryPageContentFactory extends PageContentFactory<SuccessfulRecoveryPageContent, Void> {

    private final PageTitleResolver pageTitleResolver;

    @Inject
    public SuccessfulRecoveryPageContentFactory(final PageTitleResolver pageTitleResolver) {
        this.pageTitleResolver = pageTitleResolver;
    }

    protected final PageTitleResolver getPageTitleResolver() {
        return pageTitleResolver;
    }

    @Override
    protected SuccessfulRecoveryPageContent newViewModelInstance(final Void input) {
        return new SuccessfulRecoveryPageContent();
    }

    @Override
    public final SuccessfulRecoveryPageContent create(final Void input) {
        return super.create(input);
    }

    public final SuccessfulRecoveryPageContent create() {
        return super.create(null);
    }

    @Override
    protected final void initialize(final SuccessfulRecoveryPageContent viewModel, final Void input) {
        super.initialize(viewModel, input);
    }

    @Override
    protected void fillTitle(final SuccessfulRecoveryPageContent viewModel, final Void input) {
        viewModel.setTitle(pageTitleResolver.getOrEmpty("my-account:paswordRecovery.title"));
    }
}
