/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.events;

import java.io.IOException;

public interface FileRollOverManager {
    public void cancelTimeBasedFileRollOver();

    public boolean rollFileOver() throws IOException;

    public void scheduleTimeBasedRollOverIfNeeded();
}
