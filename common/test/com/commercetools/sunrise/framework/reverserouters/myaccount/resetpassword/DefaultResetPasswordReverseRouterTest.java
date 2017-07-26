package com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link DefaultResetPasswordReverseRouter}.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultResetPasswordReverseRouterTest {
    @Mock
    private SimpleResetPasswordReverseRouter simpleResetPasswordReverseRouter;

    private Locale locale = Locale.ENGLISH;
    private DefaultResetPasswordReverseRouter defaultResetPasswordReverseRouter;

    @Before
    public void setup() {
        defaultResetPasswordReverseRouter = new DefaultResetPasswordReverseRouter(locale, simpleResetPasswordReverseRouter);
    }

    @Test
    public void requestRecoveryEmailPageCall() {
        defaultResetPasswordReverseRouter.requestRecoveryEmailPageCall(locale.toLanguageTag());

        verify(simpleResetPasswordReverseRouter)
                .requestRecoveryEmailPageCall(locale.toLanguageTag());
    }

    @Test
    public void requestRecoveryEmailProcessCall() {
        defaultResetPasswordReverseRouter.requestRecoveryEmailProcessCall(locale.toLanguageTag());

        verify(simpleResetPasswordReverseRouter)
                .requestRecoveryEmailProcessCall(locale.toLanguageTag());
    }

    @Test
    public void resetPasswordPageCall() {
        final String tokenValue = "taken-value";
        defaultResetPasswordReverseRouter.resetPasswordPageCall(locale.toLanguageTag(), tokenValue);

        verify(simpleResetPasswordReverseRouter)
                .resetPasswordPageCall(locale.toLanguageTag(), tokenValue);
    }

    @Test
    public void resetPasswordProcessCall() {
        final String tokenValue = "taken-value";
        defaultResetPasswordReverseRouter.resetPasswordProcessCall(locale.toLanguageTag(), tokenValue);

        verify(simpleResetPasswordReverseRouter)
                .resetPasswordProcessCall(locale.toLanguageTag(), tokenValue);
    }
}
