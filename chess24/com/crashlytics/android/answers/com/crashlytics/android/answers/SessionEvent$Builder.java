/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.SessionEvent;
import com.crashlytics.android.answers.SessionEventMetadata;
import java.util.Map;

static class SessionEvent.Builder {
    Map<String, Object> customAttributes;
    String customType;
    Map<String, String> details;
    Map<String, Object> predefinedAttributes;
    String predefinedType;
    final long timestamp;
    final SessionEvent.Type type;

    public SessionEvent.Builder(SessionEvent.Type type) {
        this.type = type;
        this.timestamp = System.currentTimeMillis();
        this.details = null;
        this.customType = null;
        this.customAttributes = null;
        this.predefinedType = null;
        this.predefinedAttributes = null;
    }

    public SessionEvent build(SessionEventMetadata sessionEventMetadata) {
        return new SessionEvent(sessionEventMetadata, this.timestamp, this.type, this.details, this.customType, this.customAttributes, this.predefinedType, this.predefinedAttributes, null);
    }

    public SessionEvent.Builder customAttributes(Map<String, Object> map) {
        this.customAttributes = map;
        return this;
    }

    public SessionEvent.Builder customType(String string) {
        this.customType = string;
        return this;
    }

    public SessionEvent.Builder details(Map<String, String> map) {
        this.details = map;
        return this;
    }

    public SessionEvent.Builder predefinedAttributes(Map<String, Object> map) {
        this.predefinedAttributes = map;
        return this;
    }

    public SessionEvent.Builder predefinedType(String string) {
        this.predefinedType = string;
        return this;
    }
}
