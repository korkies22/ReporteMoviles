/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.crashlytics.android.answers;

import android.os.Bundle;

public interface EventLogger {
    public void logEvent(String var1, Bundle var2);

    public void logEvent(String var1, String var2, Bundle var3);
}
