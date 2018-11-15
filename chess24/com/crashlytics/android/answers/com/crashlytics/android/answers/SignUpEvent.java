/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.PredefinedEvent;

public class SignUpEvent
extends PredefinedEvent<SignUpEvent> {
    static final String METHOD_ATTRIBUTE = "method";
    static final String SUCCESS_ATTRIBUTE = "success";
    static final String TYPE = "signUp";

    @Override
    String getPredefinedType() {
        return TYPE;
    }

    public SignUpEvent putMethod(String string) {
        this.predefinedAttributes.put(METHOD_ATTRIBUTE, string);
        return this;
    }

    public SignUpEvent putSuccess(boolean bl) {
        this.predefinedAttributes.put(SUCCESS_ATTRIBUTE, Boolean.toString(bl));
        return this;
    }
}
