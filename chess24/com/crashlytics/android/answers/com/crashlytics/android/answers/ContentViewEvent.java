/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.PredefinedEvent;

public class ContentViewEvent
extends PredefinedEvent<ContentViewEvent> {
    static final String CONTENT_ID_ATTRIBUTE = "contentId";
    static final String CONTENT_NAME_ATTRIBUTE = "contentName";
    static final String CONTENT_TYPE_ATTRIBUTE = "contentType";
    static final String TYPE = "contentView";

    @Override
    String getPredefinedType() {
        return TYPE;
    }

    public ContentViewEvent putContentId(String string) {
        this.predefinedAttributes.put(CONTENT_ID_ATTRIBUTE, string);
        return this;
    }

    public ContentViewEvent putContentName(String string) {
        this.predefinedAttributes.put(CONTENT_NAME_ATTRIBUTE, string);
        return this;
    }

    public ContentViewEvent putContentType(String string) {
        this.predefinedAttributes.put(CONTENT_TYPE_ATTRIBUTE, string);
        return this;
    }
}
