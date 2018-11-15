/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.PredefinedEvent;

public class RatingEvent
extends PredefinedEvent<RatingEvent> {
    static final String CONTENT_ID_ATTRIBUTE = "contentId";
    static final String CONTENT_NAME_ATTRIBUTE = "contentName";
    static final String CONTENT_TYPE_ATTRIBUTE = "contentType";
    static final String RATING_ATTRIBUTE = "rating";
    static final String TYPE = "rating";

    @Override
    String getPredefinedType() {
        return "rating";
    }

    public RatingEvent putContentId(String string) {
        this.predefinedAttributes.put(CONTENT_ID_ATTRIBUTE, string);
        return this;
    }

    public RatingEvent putContentName(String string) {
        this.predefinedAttributes.put(CONTENT_NAME_ATTRIBUTE, string);
        return this;
    }

    public RatingEvent putContentType(String string) {
        this.predefinedAttributes.put(CONTENT_TYPE_ATTRIBUTE, string);
        return this;
    }

    public RatingEvent putRating(int n) {
        this.predefinedAttributes.put("rating", n);
        return this;
    }
}
