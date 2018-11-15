/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.AnswersEvent;
import com.crashlytics.android.answers.AnswersEventValidator;

public class CustomEvent
extends AnswersEvent<CustomEvent> {
    private final String eventName;

    public CustomEvent(String string) {
        if (string == null) {
            throw new NullPointerException("eventName must not be null");
        }
        this.eventName = this.validator.limitStringLength(string);
    }

    String getCustomType() {
        return this.eventName;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{eventName:\"");
        stringBuilder.append(this.eventName);
        stringBuilder.append('\"');
        stringBuilder.append(", customAttributes:");
        stringBuilder.append(this.customAttributes);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
