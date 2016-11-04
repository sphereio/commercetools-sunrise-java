package com.commercetools.sunrise.myaccount;

import com.commercetools.sunrise.common.sessions.AbstractCachedSessionHandler;
import com.google.inject.Injector;
import io.sphere.sdk.customers.Customer;
import play.Configuration;
import play.cache.CacheApi;
import play.mvc.Http;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CustomerSessionHandler extends AbstractCachedSessionHandler<Customer> {

    private static final String DEFAULT_CUSTOMER_ID_SESSION_KEY = "sunrise-customer-id";
    private static final String DEFAULT_CUSTOMER_EMAIL_SESSION_KEY = "sunrise-customer-email";
    private static final String DEFAULT_USER_INFO_SESSION_KEY = "sunrise-user-info";
    private final String customerIdSessionKey;
    private final String customerEmailSessionKey;
    private final String userInfoSessionKey;
    @Inject
    private Injector injector;
    @Inject
    private CacheApi cacheApi;

    @Inject
    public CustomerSessionHandler(final Configuration configuration) {
        this.customerIdSessionKey = configuration.getString("session.customer.customerId", DEFAULT_CUSTOMER_ID_SESSION_KEY);
        this.customerEmailSessionKey = configuration.getString("session.customer.customerEmail", DEFAULT_CUSTOMER_EMAIL_SESSION_KEY);
        this.userInfoSessionKey = configuration.getString("session.customer.userInfo", DEFAULT_USER_INFO_SESSION_KEY);
    }

    public Optional<String> findCustomerId(final Http.Session session) {
        return findValueByKey(session, customerIdSessionKey);
    }

    public Optional<String> findCustomerEmail(final Http.Session session) {
        return findValueByKey(session, customerEmailSessionKey);
    }

    public Optional<UserInfoBean> findUserInfo(final Http.Session session) {
        return findCachedValueByKey(session, cacheApi, userInfoSessionKey);
    }

    @Override
    protected void overwriteRelatedValuesInSession(final Http.Session session, final Customer customer) {
        final UserInfoBean userInfoBean = injector.getInstance(UserInfoBeanFactory.class).create(customer);
        final String userInfoCacheKey = customer.getId() + userInfoSessionKey;
        overwriteCachedValueByKey(session, cacheApi, userInfoCacheKey, userInfoSessionKey, userInfoBean);
        overwriteValueByKey(session, customerIdSessionKey, customer.getId());
        overwriteValueByKey(session, customerEmailSessionKey, customer.getEmail());
    }

    @Override
    protected void removeRelatedValuesFromSession(final Http.Session session) {
        removeCachedValueByKey(session, cacheApi, userInfoSessionKey);
        removeValueByKey(session, customerIdSessionKey);
        removeValueByKey(session, customerEmailSessionKey);
    }
}
