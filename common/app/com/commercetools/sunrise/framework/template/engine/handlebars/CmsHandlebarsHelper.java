package com.commercetools.sunrise.framework.template.engine.handlebars;

import com.github.jknack.handlebars.Helper;
import com.google.inject.ImplementedBy;

@ImplementedBy(CmsHandlebarsHelperImpl.class)
public interface CmsHandlebarsHelper extends Helper<String> {

    String CMS_PAGE_IN_CONTEXT_KEY = "context-cms-page";
}
