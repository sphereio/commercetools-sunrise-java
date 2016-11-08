package com.commercetools.sunrise.common.sessions;

import com.google.inject.ImplementedBy;

import java.util.Optional;

@ImplementedBy(SerializableObjectSession.class)
public interface ObjectSession extends SimpleSession {

    <U> Optional<U> findObjectByKey(final String sessionKey, final Class<U> clazz);

    <U> void overwriteObjectByKey(final String sessionKey, final U value);

    void removeObjectByKey(final String sessionKey);
}
