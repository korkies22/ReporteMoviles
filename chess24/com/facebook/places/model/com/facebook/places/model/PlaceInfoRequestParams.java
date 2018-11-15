/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class PlaceInfoRequestParams {
    private final Set<String> fields = new HashSet<String>();
    private final String placeId;

    private PlaceInfoRequestParams(Builder builder) {
        this.placeId = builder.placeId;
        this.fields.addAll(builder.fields);
    }

    public Set<String> getFields() {
        return this.fields;
    }

    public String getPlaceId() {
        return this.placeId;
    }

    public static class Builder {
        private final Set<String> fields = new HashSet<String>();
        private String placeId;

        public Builder addField(String string) {
            this.fields.add(string);
            return this;
        }

        public Builder addFields(String[] arrstring) {
            for (String string : arrstring) {
                this.fields.add(string);
            }
            return this;
        }

        public PlaceInfoRequestParams build() {
            return new PlaceInfoRequestParams(this);
        }

        public Builder setPlaceId(String string) {
            this.placeId = string;
            return this;
        }
    }

}
