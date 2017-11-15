package com.commercetools.sunrise.framework.cms;

import play.i18n.MessagesApi;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CmsMessagesApi extends MessagesApi {

    @Inject
    CmsMessagesApi(final DefaultCmsMessagesApi messages) {
        super(messages);
    }
}
