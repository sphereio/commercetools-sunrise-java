package com.commercetools.sunrise.common.template.engine.handlebars;

import com.github.jknack.handlebars.Options;
import io.sphere.sdk.models.Base;

import java.io.IOException;

public class HandlebarsMoneyHelper extends Base  {

    public CharSequence priceFmt(Object context, Options options) throws IOException {
        String price = context instanceof String ? (String) context : context.toString();
        return (new Integer(Integer.valueOf(price)/100)).toString();
    }
}
