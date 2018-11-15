/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.PredefinedEvent;

public class ShareEvent
extends PredefinedEvent<ShareEvent> {
    static final String CONTENT_ID_ATTRIBUTE = "contentId";
    static final String CONTENT_NAME_ATTRIBUTE = "contentName";
    static final String CONTENT_TYPE_ATTRIBUTE = "contentType";
    static final String METHOD_ATTRIBUTE = "method";
    static final String TYPE = "share";

    @Override
    String getPredefinedType() {
        return TYPE;
    }

    public ShareEvent putContentId(String string) {
        this.predefinedAttributes.put(CONTENT_ID_ATTRIBUTE, string);
        return this;
    }

    public ShareEvent putContentName(String string) {
        this.predefinedAttributes.put(CONTENT_NAME_ATTRIBUTE, string);
        return this;
    }

    public ShareEvent putContentType(String string) {
        this.predefinedAttributes.put(CONTENT_TYPE_ATTRIBUTE, string);
        return this;
    }

    public ShareEvent putMethod(String string) {
        this.predefinedAttributes.put(METHOD_ATTRIBUTE, string);
        return this;
    }
}
