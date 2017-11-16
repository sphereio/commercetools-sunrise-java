package com.commercetools.sunrise.framework.template.engine.handlebars;

import com.github.jknack.handlebars.Helper;
import com.google.inject.ImplementedBy;

@ImplementedBy(I18nHandlebarsHelperImpl.class)
public interface I18nHandlebarsHelper extends Helper<String> {

}
