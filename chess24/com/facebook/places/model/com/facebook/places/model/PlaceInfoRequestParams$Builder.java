/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.model;

import com.facebook.places.model.PlaceInfoRequestParams;
import java.util.HashSet;
import java.util.Set;

public static class PlaceInfoRequestParams.Builder {
    private final Set<String> fields = new HashSet<String>();
    private String placeId;

    static /* synthetic */ String access$000(PlaceInfoRequestParams.Builder builder) {
        return builder.placeId;
    }

    static /* synthetic */ Set access$100(PlaceInfoRequestParams.Builder builder) {
        return builder.fields;
    }

    public PlaceInfoRequestParams.Builder addField(String string) {
        this.fields.add(string);
        return this;
    }

    public PlaceInfoRequestParams.Builder addFields(String[] arrstring) {
        for (String string : arrstring) {
            this.fields.add(string);
        }
        return this;
    }

    public PlaceInfoRequestParams build() {
        return new PlaceInfoRequestParams(this, null);
    }

    public PlaceInfoRequestParams.Builder setPlaceId(String string) {
        this.placeId = string;
        return this;
    }
}
