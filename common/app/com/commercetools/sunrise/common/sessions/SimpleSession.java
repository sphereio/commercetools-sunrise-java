package com.commercetools.sunrise.common.sessions;

import com.google.inject.ImplementedBy;

import java.util.Optional;

@ImplementedBy(PlaySession.class)
public interface SimpleSession {

    Optional<String> findValueByKey(final String sessionKey);

    void overwriteValueByKey(final String sessionKey, final String value);

    void removeValueByKey(final String sessionKey);
}
