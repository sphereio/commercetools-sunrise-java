package com.commercetools.sunrise.myaccount;

import com.commercetools.sunrise.common.contexts.RequestScoped;
import com.commercetools.sunrise.common.sessions.RelatedValuesSessionWriter;
import com.commercetools.sunrise.common.sessions.ObjectSession;
import com.google.inject.Injector;
import io.sphere.sdk.customers.Customer;
import play.Configuration;

import javax.inject.Inject;
import java.util.Optional;

@RequestScoped
public class CustomerInSession extends RelatedValuesSessionWriter<Customer> {

    private static final String DEFAULT_CUSTOMER_ID_SESSION_KEY = "sunrise-customer-id";
    private static final String DEFAULT_CUSTOMER_EMAIL_SESSION_KEY = "sunrise-customer-email";
    private static final String DEFAULT_USER_INFO_SESSION_KEY = "sunrise-user-info";
    private final String customerIdSessionKey;
    private final String customerEmailSessionKey;
    private final String userInfoSessionKey;
    private final ObjectSession session;
    @Inject
    private Injector injector;

    @Inject
    public CustomerInSession(final ObjectSession session, final Configuration configuration) {
        this.customerIdSessionKey = configuration.getString("session.customer.customerId", DEFAULT_CUSTOMER_ID_SESSION_KEY);
        this.customerEmailSessionKey = configuration.getString("session.customer.customerEmail", DEFAULT_CUSTOMER_EMAIL_SESSION_KEY);
        this.userInfoSessionKey = configuration.getString("session.customer.userInfo", DEFAULT_USER_INFO_SESSION_KEY);
        this.session = session;
    }

    public Optional<String> findCustomerId() {
        return session.findValueByKey(customerIdSessionKey);
    }

    public Optional<String> findCustomerEmail() {
        return session.findValueByKey(customerEmailSessionKey);
    }

    public Optional<UserInfoBean> findUserInfo() {
        return session.findObjectByKey(userInfoSessionKey, UserInfoBean.class);
    }

    @Override
    protected void overwriteRelatedValuesInSession(final Customer customer) {
        final UserInfoBean userInfoBean = injector.getInstance(UserInfoBeanFactory.class).create(customer);
        session.overwriteObjectByKey(userInfoSessionKey, userInfoBean);
        session.overwriteValueByKey(customerIdSessionKey, customer.getId());
        session.overwriteValueByKey(customerEmailSessionKey, customer.getEmail());
    }

    @Override
    protected void removeRelatedValuesFromSession() {
        session.removeObjectByKey(userInfoSessionKey);
        session.removeValueByKey(customerIdSessionKey);
        session.removeValueByKey(customerEmailSessionKey);
    }
}
