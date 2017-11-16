package com.commercetools.sunrise.framework.viewmodels.forms.titles;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.viewmodels.forms.FormFieldViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.forms.FormFieldWithOptions;
import play.Configuration;
import play.data.Form;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@RequestScoped
public class TitleFormFieldViewModelFactory extends FormFieldViewModelFactory<TitleFormFieldViewModel, String> {

    private final List<String> defaultTitleKeys;
    private final MessagesResolver messagesResolver;
    private final TitleFormSelectableOptionViewModelFactory titleFormSelectableOptionViewModelFactory;


    @Inject
    public TitleFormFieldViewModelFactory(final Configuration configuration, final MessagesResolver messagesResolver,
                                          final TitleFormSelectableOptionViewModelFactory titleFormSelectableOptionViewModelFactory) {
        this.defaultTitleKeys = configuration.getStringList("form.titles", emptyList());
        this.messagesResolver = messagesResolver;
        this.titleFormSelectableOptionViewModelFactory = titleFormSelectableOptionViewModelFactory;
    }

    protected final List<String> getDefaultTitleKeys() {
        return defaultTitleKeys;
    }

    protected final MessagesResolver getMessagesResolver() {
        return messagesResolver;
    }

    protected final TitleFormSelectableOptionViewModelFactory getTitleFormSelectableOptionViewModelFactory() {
        return titleFormSelectableOptionViewModelFactory;
    }

    @Override
    protected TitleFormFieldViewModel newViewModelInstance(final FormFieldWithOptions<String> formFieldWithOptions) {
        return new TitleFormFieldViewModel();
    }

    @Override
    protected List<String> defaultOptions() {
        return defaultTitleKeys;
    }

    @Override
    public final TitleFormFieldViewModel create(final FormFieldWithOptions<String> formFieldWithOptions) {
        return super.create(formFieldWithOptions);
    }

    @Override
    public final TitleFormFieldViewModel createWithDefaultOptions(final Form.Field data) {
        return super.createWithDefaultOptions(data);
    }

    @Override
    protected final void initialize(final TitleFormFieldViewModel viewModel, final FormFieldWithOptions<String> formFieldWithOptions) {
        fillList(viewModel, formFieldWithOptions);
    }

    protected void fillList(final TitleFormFieldViewModel viewModel, final FormFieldWithOptions<String> formFieldWithOptions) {
        viewModel.setList(formFieldWithOptions.getFormOptions().stream()
                .map(titleKey -> titleFormSelectableOptionViewModelFactory.create(messagesResolver.getOrKey(titleKey), formFieldWithOptions.getFormField().value()))
                .collect(toList()));
    }
}
