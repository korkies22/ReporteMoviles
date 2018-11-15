/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.PredefinedEvent;

public class LevelEndEvent
extends PredefinedEvent<LevelEndEvent> {
    static final String LEVEL_NAME_ATTRIBUTE = "levelName";
    static final String SCORE_ATTRIBUTE = "score";
    static final String SUCCESS_ATTRIBUTE = "success";
    static final String TYPE = "levelEnd";

    @Override
    String getPredefinedType() {
        return TYPE;
    }

    public LevelEndEvent putLevelName(String string) {
        this.predefinedAttributes.put(LEVEL_NAME_ATTRIBUTE, string);
        return this;
    }

    public LevelEndEvent putScore(Number number) {
        this.predefinedAttributes.put(SCORE_ATTRIBUTE, number);
        return this;
    }

    public LevelEndEvent putSuccess(boolean bl) {
        AnswersAttributes answersAttributes = this.predefinedAttributes;
        String string = bl ? "true" : "false";
        answersAttributes.put(SUCCESS_ATTRIBUTE, string);
        return this;
    }
}
