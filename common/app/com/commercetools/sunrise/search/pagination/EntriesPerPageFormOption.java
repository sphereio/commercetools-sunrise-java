package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.viewmodels.forms.FormOption;

public final class EntriesPerPageFormOption extends FormOption<Integer> {

    private EntriesPerPageFormOption(final String fieldLabel, final String fieldValue, final Integer value, final boolean isDefault) {
        super(fieldLabel, fieldValue, value, isDefault);
    }

    @Override
    public String getFieldValue() {
        return super.getFieldValue();
    }

    @Override
    public String getFieldLabel() {
        return super.getFieldLabel();
    }

    @Override
    public Integer getValue() {
        return super.getValue();
    }

    @Override
    public boolean isDefault() {
        return super.isDefault();
    }

    public static EntriesPerPageFormOption of(final String fieldLabel, final String fieldValue, final Integer value, final boolean isDefault) {
        return new EntriesPerPageFormOption(fieldLabel, fieldValue, value, isDefault);
    }
}
