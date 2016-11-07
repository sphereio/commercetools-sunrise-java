package com.commercetools.sunrise.common.sessions;

import play.mvc.Http;

import java.util.Optional;

public abstract class AbstractObjectInSession<T> extends AbstractInSession<T> {

    protected AbstractObjectInSession(final Http.Session session) {
        super(session);
    }

    protected abstract <U> void overwriteObjectByKey(final String sessionKey, final U value);

    protected abstract <U> Optional<U> findObjectByKey(final String sessionKey, final Class<U> clazz);

    protected abstract void removeObjectByKey(final String sessionKey);
}
