package com.commercetools.sunrise.framework.template.engine.handlebars;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.github.jknack.handlebars.Options;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class I18nHandlebarsHelperImplTest {

    private static final String MESSAGE_KEY = "foo.bar";
    private static final String MESSAGE_KEY_ANSWER = "OK";
    private static final Map<String, Object> ARGS = singletonMap("name", "John");

    @Mock
    private MessagesResolver fakeMessagesResolver;

    @InjectMocks
    private I18nHandlebarsHelperImpl i18nHandlebarsHelper;

    @Before
    public void setUp() throws Exception {
        when(fakeMessagesResolver.get(eq(MESSAGE_KEY), any())).thenReturn(Optional.of(MESSAGE_KEY_ANSWER));
        when(fakeMessagesResolver.getOrEmpty(any(), any())).thenCallRealMethod();
    }

    @Test
    public void translatesMessage() throws Exception {
        assertThat(i18nHandlebarsHelper.apply(MESSAGE_KEY, optionsWithoutArgs())).isEqualTo(MESSAGE_KEY_ANSWER);
        verify(fakeMessagesResolver).getOrEmpty(MESSAGE_KEY, emptyMap());
    }

    @Test
    public void returnsEmptyOnUndefinedMessage() throws Exception {
        assertThat(i18nHandlebarsHelper.apply("unknown", optionsWithoutArgs())).isEmpty();
        verify(fakeMessagesResolver).getOrEmpty("unknown", emptyMap());
    }

    @Test
    public void translatesMessageWithArgs() throws Exception {
        assertThat(i18nHandlebarsHelper.apply(MESSAGE_KEY, optionsWithArgs())).isEqualTo(MESSAGE_KEY_ANSWER);
        verify(fakeMessagesResolver).getOrEmpty(MESSAGE_KEY, ARGS);
    }

    private Options optionsWithoutArgs() {
        return optionsBuilder()
                .build();
    }

    private Options optionsWithArgs() {
        return optionsBuilder()
                .setHash(ARGS)
                .build();
    }

    private static Options.Builder optionsBuilder() {
        return new Options.Builder(null, null, null, null, null);
    }
}
