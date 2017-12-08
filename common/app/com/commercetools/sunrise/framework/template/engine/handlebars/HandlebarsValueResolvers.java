package com.commercetools.sunrise.framework.template.engine.handlebars;

import com.github.jknack.handlebars.ValueResolver;
import com.google.inject.ImplementedBy;

import java.util.List;

@ImplementedBy(HandlebarsValueResolversImpl.class)
public interface HandlebarsValueResolvers {

    List<ValueResolver> valueResolvers();
}
