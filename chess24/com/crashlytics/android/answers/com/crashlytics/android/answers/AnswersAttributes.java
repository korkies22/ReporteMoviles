/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersEventValidator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

class AnswersAttributes {
    final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();
    final AnswersEventValidator validator;

    public AnswersAttributes(AnswersEventValidator answersEventValidator) {
        this.validator = answersEventValidator;
    }

    void put(String string, Number number) {
        if (!this.validator.isNull(string, "key")) {
            if (this.validator.isNull(number, "value")) {
                return;
            }
            this.putAttribute(this.validator.limitStringLength(string), number);
            return;
        }
    }

    void put(String string, String string2) {
        if (!this.validator.isNull(string, "key")) {
            if (this.validator.isNull(string2, "value")) {
                return;
            }
            this.putAttribute(this.validator.limitStringLength(string), this.validator.limitStringLength(string2));
            return;
        }
    }

    void putAttribute(String string, Object object) {
        if (!this.validator.isFullMap(this.attributes, string)) {
            this.attributes.put(string, object);
        }
    }

    public String toString() {
        return new JSONObject(this.attributes).toString();
    }
}
