/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.model;

import com.facebook.places.model.PlaceSearchRequestParams;
import java.util.HashSet;
import java.util.Set;

public static class PlaceSearchRequestParams.Builder {
    private final Set<String> categories = new HashSet<String>();
    private int distance;
    private final Set<String> fields = new HashSet<String>();
    private int limit;
    private String searchText;

    static /* synthetic */ int access$000(PlaceSearchRequestParams.Builder builder) {
        return builder.distance;
    }

    static /* synthetic */ int access$100(PlaceSearchRequestParams.Builder builder) {
        return builder.limit;
    }

    static /* synthetic */ String access$200(PlaceSearchRequestParams.Builder builder) {
        return builder.searchText;
    }

    static /* synthetic */ Set access$300(PlaceSearchRequestParams.Builder builder) {
        return builder.categories;
    }

    static /* synthetic */ Set access$400(PlaceSearchRequestParams.Builder builder) {
        return builder.fields;
    }

    public PlaceSearchRequestParams.Builder addCategory(String string) {
        this.categories.add(string);
        return this;
    }

    public PlaceSearchRequestParams.Builder addField(String string) {
        this.fields.add(string);
        return this;
    }

    public PlaceSearchRequestParams build() {
        return new PlaceSearchRequestParams(this, null);
    }

    public PlaceSearchRequestParams.Builder setDistance(int n) {
        this.distance = n;
        return this;
    }

    public PlaceSearchRequestParams.Builder setLimit(int n) {
        this.limit = n;
        return this;
    }

    public PlaceSearchRequestParams.Builder setSearchText(String string) {
        this.searchText = string;
        return this;
    }
}
