/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.model;

public class CurrentPlaceFeedbackRequestParams {
    private final String placeId;
    private final String tracking;
    private final Boolean wasHere;

    private CurrentPlaceFeedbackRequestParams(Builder builder) {
        this.tracking = builder.tracking;
        this.placeId = builder.placeId;
        this.wasHere = builder.wasHere;
    }

    public String getPlaceId() {
        return this.placeId;
    }

    public String getTracking() {
        return this.tracking;
    }

    public Boolean wasHere() {
        return this.wasHere;
    }

    public static class Builder {
        private String placeId;
        private String tracking;
        private Boolean wasHere;

        public CurrentPlaceFeedbackRequestParams build() {
            return new CurrentPlaceFeedbackRequestParams(this);
        }

        public Builder setPlaceId(String string) {
            this.placeId = string;
            return this;
        }

        public Builder setTracking(String string) {
            this.tracking = string;
            return this;
        }

        public Builder setWasHere(boolean bl) {
            this.wasHere = bl;
            return this;
        }
    }

}
