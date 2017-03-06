package com.commercetools.sunrise.framework.reverserouters.common.assets;

import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(WebjarAssetsReverseRouter.class)
public interface AssetsReverseRouter {

    Call webJarAssetsCall(final String filepath);
}
