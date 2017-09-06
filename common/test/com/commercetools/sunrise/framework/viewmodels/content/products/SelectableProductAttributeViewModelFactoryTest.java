package com.commercetools.sunrise.framework.viewmodels.content.products;

import com.commercetools.sunrise.ctp.ProductAttributeSettings;
import com.google.inject.AbstractModule;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeLocalRepository;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link SelectableProductAttributeViewModelFactory}.
 */
public class SelectableProductAttributeViewModelFactoryTest extends WithApplication {
    private static final String STRING_ATTRIBUTE_NAME = "stringAttribute";
    private static final String NUMBER_ATTRIBUTE_NAME = "numberAttribute";
    private static final String PRODUCT_TYPE_ID = "productTypeId";

    private ProductAttributeSettings fakeProductAttributeSettings;
    private ProductTypeLocalRepository fakeProductTypeLocalRepository;

    private SelectableProductAttributeViewModelFactory viewModelFactory;

    private Attribute stringAttribute1;
    private Attribute stringAttribute2;
    private Attribute numberAttribute1;
    private Attribute numberAttribute2;

    private ProductType fakeProductType;
    private Reference<ProductType> productTypeRef;

    @Before
    public void setup() {
        viewModelFactory = app.injector().instanceOf(SelectableProductAttributeViewModelFactory.class);
        fakeProductType = mock(ProductType.class);

        final AttributeDefinition stringAttributeDefinition =
                AttributeDefinitionBuilder.of(STRING_ATTRIBUTE_NAME, LocalizedString.of(Locale.ENGLISH, "string"), StringAttributeType.of())
                        .build();
        when(fakeProductType.getAttribute(STRING_ATTRIBUTE_NAME)).thenReturn(stringAttributeDefinition);
        when(fakeProductType.findAttribute(STRING_ATTRIBUTE_NAME)).thenReturn(Optional.of(stringAttributeDefinition));
        stringAttribute1 = Attribute.of(STRING_ATTRIBUTE_NAME, AttributeAccess.ofString(), "v1");
        stringAttribute2 = Attribute.of(STRING_ATTRIBUTE_NAME, AttributeAccess.ofString(), "v2");

        final AttributeDefinition numberAttributeDefinition =
                AttributeDefinitionBuilder.of(NUMBER_ATTRIBUTE_NAME, LocalizedString.of(Locale.ENGLISH, "number"), NumberAttributeType.of())
                        .build();
        when(fakeProductType.getAttribute(NUMBER_ATTRIBUTE_NAME)).thenReturn(numberAttributeDefinition);
        when(fakeProductType.findAttribute(NUMBER_ATTRIBUTE_NAME)).thenReturn(Optional.of(numberAttributeDefinition));

        numberAttribute1 = Attribute.of(NUMBER_ATTRIBUTE_NAME, AttributeAccess.ofDouble(), 12.0);
        numberAttribute2 = Attribute.of(NUMBER_ATTRIBUTE_NAME, AttributeAccess.ofDouble(), 13.0);

        when(fakeProductType.getId()).thenReturn(PRODUCT_TYPE_ID);
        when(fakeProductType.toReference()).thenReturn(productTypeRef);
        when(fakeProductType.getAttributes()).thenReturn(Arrays.asList(stringAttributeDefinition, numberAttributeDefinition));
        productTypeRef = Reference.ofResourceTypeIdAndObj(ProductType.resourceTypeId(), fakeProductType);
        when(fakeProductTypeLocalRepository.findById(PRODUCT_TYPE_ID)).thenReturn(Optional.of(fakeProductType));
    }

    @Override
    protected Application provideApplication() {
        fakeProductAttributeSettings = mock(ProductAttributeSettings.class);
        fakeProductTypeLocalRepository = mock(ProductTypeLocalRepository.class);
        return new GuiceApplicationBuilder()
                .overrides(new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(Locale.class).toInstance(Locale.ENGLISH);
                        bind(ProductAttributeSettings.class).toInstance(fakeProductAttributeSettings);
                        bind(ProductTypeLocalRepository.class).toInstance(fakeProductTypeLocalRepository);
                    }
                }).build();
    }

    @Test
    public void createFromSelectableStringAttributeAndSelectableNumberAttribute() {
        mockProductAttributeSettings(STRING_ATTRIBUTE_NAME, NUMBER_ATTRIBUTE_NAME);
        final List<ProductVariant> variants = Arrays.asList(mockProductVariant(stringAttribute1, numberAttribute1), mockProductVariant(stringAttribute2, numberAttribute2));
        final AttributeWithProductType attributeWithProductType = AttributeWithProductType.of(stringAttribute1, productTypeRef);

        final SelectableProductAttributeViewModel attributeViewModel = viewModelFactory.create(variants, attributeWithProductType);
        final Map<String, Map<String, List<String>>> selectData = attributeViewModel.getSelectData();
        assertThat(selectData).isNotEmpty();

        final Map<String, List<String>> attribute1Data = selectData.get(stringAttribute1.getValueAsString());
        assertThat(attribute1Data).containsKey(NUMBER_ATTRIBUTE_NAME);
        assertThat(attribute1Data.get(NUMBER_ATTRIBUTE_NAME)).containsExactly("120");

        final Map<String, List<String>> attribute2Data = selectData.get(stringAttribute2.getValueAsString());
        assertThat(attribute2Data).containsKey(NUMBER_ATTRIBUTE_NAME);
        assertThat(attribute2Data.get(NUMBER_ATTRIBUTE_NAME)).containsExactly("130");
    }

    private void mockProductAttributeSettings(final String selectablePrimaryAttribute, final String selectableSecondaryAttribute) {
        when(fakeProductAttributeSettings.getSelectablePrimaryAttributes()).thenReturn(Arrays.asList(selectablePrimaryAttribute));
        when(fakeProductAttributeSettings.getSelectableSecondaryAttributes()).thenReturn(Arrays.asList(selectableSecondaryAttribute));
        when(fakeProductAttributeSettings.getSelectableAttributes()).thenReturn(Arrays.asList(selectablePrimaryAttribute, selectableSecondaryAttribute));
    }

    private ProductVariant mockProductVariant(final Attribute... attributes) {
        final ProductVariant productVariant = mock(ProductVariant.class);

        for (final Attribute attribute : attributes) {
            when(productVariant.getAttribute(attribute.getName())).thenReturn(attribute);
        }

        return productVariant;
    }
}
