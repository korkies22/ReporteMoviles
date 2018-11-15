/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class PlaceSearchRequestParams {
    private final Set<String> categories = new HashSet<String>();
    private final int distance;
    private final Set<String> fields = new HashSet<String>();
    private final int limit;
    private final String searchText;

    private PlaceSearchRequestParams(Builder builder) {
        this.distance = builder.distance;
        this.limit = builder.limit;
        this.searchText = builder.searchText;
        this.categories.addAll(builder.categories);
        this.fields.addAll(builder.fields);
    }

    public Set<String> getCategories() {
        return this.categories;
    }

    public int getDistance() {
        return this.distance;
    }

    public Set<String> getFields() {
        return this.fields;
    }

    public int getLimit() {
        return this.limit;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public static class Builder {
        private final Set<String> categories = new HashSet<String>();
        private int distance;
        private final Set<String> fields = new HashSet<String>();
        private int limit;
        private String searchText;

        public Builder addCategory(String string) {
            this.categories.add(string);
            return this;
        }

        public Builder addField(String string) {
            this.fields.add(string);
            return this;
        }

        public PlaceSearchRequestParams build() {
            return new PlaceSearchRequestParams(this);
        }

        public Builder setDistance(int n) {
            this.distance = n;
            return this;
        }

        public Builder setLimit(int n) {
            this.limit = n;
            return this;
        }

        public Builder setSearchText(String string) {
            this.searchText = string;
            return this;
        }
    }

}
