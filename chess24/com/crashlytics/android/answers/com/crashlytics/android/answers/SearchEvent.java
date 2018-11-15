/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.PredefinedEvent;

public class SearchEvent
extends PredefinedEvent<SearchEvent> {
    static final String QUERY_ATTRIBUTE = "query";
    static final String TYPE = "search";

    @Override
    String getPredefinedType() {
        return TYPE;
    }

    public SearchEvent putQuery(String string) {
        this.predefinedAttributes.put(QUERY_ATTRIBUTE, string);
        return this;
    }
}
