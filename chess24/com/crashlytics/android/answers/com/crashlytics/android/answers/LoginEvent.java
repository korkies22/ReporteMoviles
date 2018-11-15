/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.PredefinedEvent;

public class LoginEvent
extends PredefinedEvent<LoginEvent> {
    static final String METHOD_ATTRIBUTE = "method";
    static final String SUCCESS_ATTRIBUTE = "success";
    static final String TYPE = "login";

    @Override
    String getPredefinedType() {
        return TYPE;
    }

    public LoginEvent putMethod(String string) {
        this.predefinedAttributes.put(METHOD_ATTRIBUTE, string);
        return this;
    }

    public LoginEvent putSuccess(boolean bl) {
        this.predefinedAttributes.put(SUCCESS_ATTRIBUTE, Boolean.toString(bl));
        return this;
    }
}
