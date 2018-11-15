/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.crashlytics.android.answers;

import android.app.Activity;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.PredefinedEvent;
import com.crashlytics.android.answers.SessionEventMetadata;
import java.util.Collections;
import java.util.Map;

final class SessionEvent {
    static final String ACTIVITY_KEY = "activity";
    static final String EXCEPTION_NAME_KEY = "exceptionName";
    static final String INSTALLED_AT_KEY = "installedAt";
    static final String SESSION_ID_KEY = "sessionId";
    public final Map<String, Object> customAttributes;
    public final String customType;
    public final Map<String, String> details;
    public final Map<String, Object> predefinedAttributes;
    public final String predefinedType;
    public final SessionEventMetadata sessionEventMetadata;
    private String stringRepresentation;
    public final long timestamp;
    public final Type type;

    private SessionEvent(SessionEventMetadata sessionEventMetadata, long l, Type type, Map<String, String> map, String string, Map<String, Object> map2, String string2, Map<String, Object> map3) {
        this.sessionEventMetadata = sessionEventMetadata;
        this.timestamp = l;
        this.type = type;
        this.details = map;
        this.customType = string;
        this.customAttributes = map2;
        this.predefinedType = string2;
        this.predefinedAttributes = map3;
    }

    public static Builder crashEventBuilder(String object) {
        object = Collections.singletonMap(SESSION_ID_KEY, object);
        return new Builder(Type.CRASH).details((Map<String, String>)object);
    }

    public static Builder crashEventBuilder(String string, String string2) {
        return SessionEvent.crashEventBuilder(string).customAttributes(Collections.singletonMap(EXCEPTION_NAME_KEY, string2));
    }

    public static Builder customEventBuilder(CustomEvent customEvent) {
        return new Builder(Type.CUSTOM).customType(customEvent.getCustomType()).customAttributes(customEvent.getCustomAttributes());
    }

    public static Builder installEventBuilder(long l) {
        return new Builder(Type.INSTALL).details(Collections.singletonMap(INSTALLED_AT_KEY, String.valueOf(l)));
    }

    public static Builder lifecycleEventBuilder(Type type, Activity object) {
        object = Collections.singletonMap(ACTIVITY_KEY, object.getClass().getName());
        return new Builder(type).details((Map<String, String>)object);
    }

    public static Builder predefinedEventBuilder(PredefinedEvent<?> predefinedEvent) {
        return new Builder(Type.PREDEFINED).predefinedType(predefinedEvent.getPredefinedType()).predefinedAttributes(predefinedEvent.getPredefinedAttributes()).customAttributes(predefinedEvent.getCustomAttributes());
    }

    public String toString() {
        if (this.stringRepresentation == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append(this.getClass().getSimpleName());
            stringBuilder.append(": ");
            stringBuilder.append("timestamp=");
            stringBuilder.append(this.timestamp);
            stringBuilder.append(", type=");
            stringBuilder.append((Object)this.type);
            stringBuilder.append(", details=");
            stringBuilder.append(this.details);
            stringBuilder.append(", customType=");
            stringBuilder.append(this.customType);
            stringBuilder.append(", customAttributes=");
            stringBuilder.append(this.customAttributes);
            stringBuilder.append(", predefinedType=");
            stringBuilder.append(this.predefinedType);
            stringBuilder.append(", predefinedAttributes=");
            stringBuilder.append(this.predefinedAttributes);
            stringBuilder.append(", metadata=[");
            stringBuilder.append(this.sessionEventMetadata);
            stringBuilder.append("]]");
            this.stringRepresentation = stringBuilder.toString();
        }
        return this.stringRepresentation;
    }

    static class Builder {
        Map<String, Object> customAttributes;
        String customType;
        Map<String, String> details;
        Map<String, Object> predefinedAttributes;
        String predefinedType;
        final long timestamp;
        final Type type;

        public Builder(Type type) {
            this.type = type;
            this.timestamp = System.currentTimeMillis();
            this.details = null;
            this.customType = null;
            this.customAttributes = null;
            this.predefinedType = null;
            this.predefinedAttributes = null;
        }

        public SessionEvent build(SessionEventMetadata sessionEventMetadata) {
            return new SessionEvent(sessionEventMetadata, this.timestamp, this.type, this.details, this.customType, this.customAttributes, this.predefinedType, this.predefinedAttributes);
        }

        public Builder customAttributes(Map<String, Object> map) {
            this.customAttributes = map;
            return this;
        }

        public Builder customType(String string) {
            this.customType = string;
            return this;
        }

        public Builder details(Map<String, String> map) {
            this.details = map;
            return this;
        }

        public Builder predefinedAttributes(Map<String, Object> map) {
            this.predefinedAttributes = map;
            return this;
        }

        public Builder predefinedType(String string) {
            this.predefinedType = string;
            return this;
        }
    }

    static enum Type {
        START,
        RESUME,
        PAUSE,
        STOP,
        CRASH,
        INSTALL,
        CUSTOM,
        PREDEFINED;
        

        private Type() {
        }
    }

}
