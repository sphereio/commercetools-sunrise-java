package controllers.common;

import com.commercetools.sunrise.common.assets.SunriseAssetsController;
import controllers.Assets;
import controllers.WebJarAssets;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class AssetsController extends SunriseAssetsController {

    @Inject
    public AssetsController(final Assets assetsController, final WebJarAssets webJarAssetsController) {
        super(assetsController, webJarAssetsController);
    }
}
