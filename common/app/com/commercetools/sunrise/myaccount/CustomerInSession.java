package com.commercetools.sunrise.myaccount;

import com.commercetools.sunrise.common.contexts.RequestScoped;
import com.commercetools.sunrise.common.sessions.AbstractSerializedObjectInSession;
import com.google.inject.Injector;
import io.sphere.sdk.customers.Customer;
import play.Configuration;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.Optional;

@RequestScoped
public class CustomerInSession extends AbstractSerializedObjectInSession<Customer> {

    private static final String DEFAULT_CUSTOMER_ID_SESSION_KEY = "sunrise-customer-id";
    private static final String DEFAULT_CUSTOMER_EMAIL_SESSION_KEY = "sunrise-customer-email";
    private static final String DEFAULT_USER_INFO_SESSION_KEY = "sunrise-user-info";
    private final String customerIdSessionKey;
    private final String customerEmailSessionKey;
    private final String userInfoSessionKey;
    @Inject
    private Injector injector;

    @Inject
    public CustomerInSession(final Http.Context context, final Configuration configuration) {
        super(context.session());
        this.customerIdSessionKey = configuration.getString("session.customer.customerId", DEFAULT_CUSTOMER_ID_SESSION_KEY);
        this.customerEmailSessionKey = configuration.getString("session.customer.customerEmail", DEFAULT_CUSTOMER_EMAIL_SESSION_KEY);
        this.userInfoSessionKey = configuration.getString("session.customer.userInfo", DEFAULT_USER_INFO_SESSION_KEY);
    }

    public Optional<String> findCustomerId() {
        return findValueByKey(customerIdSessionKey);
    }

    public Optional<String> findCustomerEmail() {
        return findValueByKey(customerEmailSessionKey);
    }

    public Optional<UserInfoBean> findUserInfo() {
        return findObjectByKey(userInfoSessionKey, UserInfoBean.class);
    }

    @Override
    protected void overwriteRelatedValuesInSession(final Customer customer) {
        final UserInfoBean userInfoBean = injector.getInstance(UserInfoBeanFactory.class).create(customer);
        overwriteObjectByKey(userInfoSessionKey, userInfoBean);
        overwriteValueByKey(customerIdSessionKey, customer.getId());
        overwriteValueByKey(customerEmailSessionKey, customer.getEmail());
    }

    @Override
    protected void removeRelatedValuesFromSession() {
        removeObjectByKey(userInfoSessionKey);
        removeValueByKey(customerIdSessionKey);
        removeValueByKey(customerEmailSessionKey);
    }
}
