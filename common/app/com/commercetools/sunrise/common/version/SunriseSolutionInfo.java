package com.commercetools.sunrise.common.version;

import io.sphere.sdk.client.SolutionInfo;
import io.sphere.sdk.json.SphereJsonUtils;
import org.apache.commons.io.IOUtils;
import play.api.Application;
import play.api.Play;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SunriseSolutionInfo extends SolutionInfo {


    public SunriseSolutionInfo() {

        setName("sunrise-java-shop-framework");
        setVersion(getVersionFromJson());
        setEmergencyContact("");
        setWebsite("");


    }

    private static String getVersionFromJson(){

        String version ;
        try {
            final InputStream versionAsStream = Play.current().resourceAsStream("internal/version.json").get();
            final String versionAsString = IOUtils.toString(versionAsStream, StandardCharsets.UTF_8);
            version = SphereJsonUtils.parse(versionAsString).get("version").asText();
        }
        catch (IOException e){
            version = "UNKNOWN";
        }
        return version;
    }


}
