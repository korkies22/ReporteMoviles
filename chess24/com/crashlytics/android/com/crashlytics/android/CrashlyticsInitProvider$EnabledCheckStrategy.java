/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android;

import android.content.Context;
import com.crashlytics.android.CrashlyticsInitProvider;

static interface CrashlyticsInitProvider.EnabledCheckStrategy {
    public boolean isCrashlyticsEnabled(Context var1);
}
