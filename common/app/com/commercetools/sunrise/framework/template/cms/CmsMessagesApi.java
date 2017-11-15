package com.commercetools.sunrise.framework.template.cms;

import play.i18n.MessagesApi;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CmsMessagesApi extends MessagesApi {

    @Inject
    public CmsMessagesApi(final com.commercetools.sunrise.framework.template.cms.api.CmsMessagesApi messages) {
        super(messages);
    }
}
