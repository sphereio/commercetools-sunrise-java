package com.commercetools.sunrise.myaccount.authentication.resetpassword.reset;

import com.commercetools.sunrise.framework.reverserouters.myaccount.authentication.AuthenticationReverseRouter;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.framework.viewmodels.meta.HalLink;
import com.commercetools.sunrise.framework.viewmodels.meta.PageMeta;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import play.mvc.Http;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ResetPasswordLinksControllerComponent}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordLinksControllerComponentTest {
    @Mock
    private AuthenticationReverseRouter authenticationReverseRouter;
    @Mock
    private Http.Context context;
    @Mock
    private Http.Request request;

    @InjectMocks
    private ResetPasswordLinksControllerComponent resetPasswordLinksControllerComponent;

    @Before
    public void setup() {
        Http.Context.current.set(context);
    }

    @Test
    public void getReverseRouter() {
        assertThat(resetPasswordLinksControllerComponent.getReverseRouter())
                .isEqualTo(authenticationReverseRouter);
    }

    @Test
    public void onPageDataReady() {
        final PageData pageData = new PageData();
        pageData.setMeta(new PageMeta());
        final String resetPasswordHref = "http://my.password";

        when: {
            when(context.request()).thenReturn(request);
            when(request.uri()).thenReturn(resetPasswordHref);
        }

        resetPasswordLinksControllerComponent.onPageDataReady(pageData);

        then: {
            final Map<String, HalLink> links = pageData.getMeta().get_links();
            final HalLink resetPasswordLink = links.get(ResetPasswordLinksControllerComponent.RESET_PASSWORD_REL);

            assertThat(resetPasswordLink.getHref()).isEqualTo(resetPasswordHref);
        }
    }
}
