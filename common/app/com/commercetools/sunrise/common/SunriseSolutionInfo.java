package com.commercetools.sunrise.common;

import io.sphere.sdk.client.SolutionInfo;
import io.sphere.sdk.meta.BuildInfo;

public class SunriseSolutionInfo extends SolutionInfo {
    public SunriseSolutionInfo() {
        setName("");
        setVersion(BuildInfo.version());
        setWebsite("");
        setEmergencyContact("");
    }
}
