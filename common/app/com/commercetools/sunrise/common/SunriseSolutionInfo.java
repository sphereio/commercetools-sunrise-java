package com.commercetools.sunrise.common;

import io.sphere.sdk.client.SolutionInfo;
import io.sphere.sdk.meta.BuildInfo;

public class SunriseSolutionInfo extends SolutionInfo {
    public SunriseSolutionInfo() {
        setName("JVM-SDK-integration-tests");
        setVersion(BuildInfo.version());
        setWebsite("https://github.com/commercetools/commercetools-jvm-sdk");
        setEmergencyContact("helpdesk@commercetools.com");
    }
}
