/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.PredefinedEvent;

public class LevelStartEvent
extends PredefinedEvent<LevelStartEvent> {
    static final String LEVEL_NAME_ATTRIBUTE = "levelName";
    static final String TYPE = "levelStart";

    @Override
    String getPredefinedType() {
        return TYPE;
    }

    public LevelStartEvent putLevelName(String string) {
        this.predefinedAttributes.put(LEVEL_NAME_ATTRIBUTE, string);
        return this;
    }
}
