package com.commercetools.sunrise.common.version;

import io.sphere.sdk.client.SolutionInfo;
import io.sphere.sdk.json.SphereJsonUtils;
import org.apache.commons.io.IOUtils;
import play.ApplicationLoader;
import play.api.Play;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SunriseSolutionInfo extends SolutionInfo {


    public SunriseSolutionInfo() {

        setName("sunrise-java-shop-framework");
        setEmergencyContact("");
        setWebsite("");


    }

    @Override
    public String getVersion() {

        if (super.getVersion() != null) {
            return super.getVersion();
        }

        String version;
        try {
            final InputStream versionAsStream = Play.current().resourceAsStream("internal/version.json").get();
            ApplicationLoader.class.getResourceAsStream("internal/version.json");
            final String versionAsString = IOUtils.toString(versionAsStream, StandardCharsets.UTF_8);
            version = SphereJsonUtils.parse(versionAsString).get("version").asText();
        } catch (IOException e) {
            version = "UNKNOWN";
        }

        setVersion(version);

        return version;
    }


}
