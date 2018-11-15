/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.model;

import com.facebook.places.model.CurrentPlaceFeedbackRequestParams;

public static class CurrentPlaceFeedbackRequestParams.Builder {
    private String placeId;
    private String tracking;
    private Boolean wasHere;

    static /* synthetic */ String access$000(CurrentPlaceFeedbackRequestParams.Builder builder) {
        return builder.tracking;
    }

    static /* synthetic */ String access$100(CurrentPlaceFeedbackRequestParams.Builder builder) {
        return builder.placeId;
    }

    static /* synthetic */ Boolean access$200(CurrentPlaceFeedbackRequestParams.Builder builder) {
        return builder.wasHere;
    }

    public CurrentPlaceFeedbackRequestParams build() {
        return new CurrentPlaceFeedbackRequestParams(this, null);
    }

    public CurrentPlaceFeedbackRequestParams.Builder setPlaceId(String string) {
        this.placeId = string;
        return this;
    }

    public CurrentPlaceFeedbackRequestParams.Builder setTracking(String string) {
        this.tracking = string;
        return this;
    }

    public CurrentPlaceFeedbackRequestParams.Builder setWasHere(boolean bl) {
        this.wasHere = bl;
        return this;
    }
}
