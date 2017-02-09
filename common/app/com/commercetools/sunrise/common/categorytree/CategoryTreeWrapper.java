package com.commercetools.sunrise.common.categorytree;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Identifiable;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Thread-safe wrapper for category tree which provides capability of exchanging contained tree reference.
 */
public abstract class CategoryTreeWrapper extends Base implements CategoryTree {

    private AtomicReference<CategoryTree> categoryTree = new AtomicReference<>();

    @Override
    public List<Category> getRoots() {
        return categoryTree.get().getRoots();
    }

    @Override
    public Optional<Category> findById(final String id) {
        return categoryTree.get().findById(id);
    }

    @Override
    public Optional<Category> findByExternalId(final String externalId) {
        return categoryTree.get().findByExternalId(externalId);
    }

    @Override
    public Optional<Category> findBySlug(final Locale locale, final String slug) {
        return categoryTree.get().findBySlug(locale, slug);
    }

    @Override
    public List<Category> getAllAsFlatList() {
        return categoryTree.get().getAllAsFlatList();
    }

    @Override
    public List<Category> findChildren(final Identifiable<Category> category) {
        return categoryTree.get().findChildren(category);
    }

    @Override
    public List<Category> findSiblings(final Collection<? extends Identifiable<Category>> collection) {
        return categoryTree.get().findSiblings(collection);
    }

    @Override
    public CategoryTree getSubtree(final Collection<? extends Identifiable<Category>> collection) {
        return categoryTree.get().getSubtree(collection);
    }

    @Override
    public Category getRootAncestor(final Identifiable<Category> identifiable) {
        return categoryTree.get().getRootAncestor(identifiable);
    }

    @Override
    public List<Category> getSubtreeRoots() {
        return categoryTree.get().getSubtreeRoots();
    }

    protected void setCategoryTree(CategoryTree categoryTree) {
        this.categoryTree.set(categoryTree);
    }
}
