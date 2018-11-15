/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.content;

import android.support.v4.content.ModernAsyncTask;

static class ModernAsyncTask {
    static final /* synthetic */ int[] $SwitchMap$android$support$v4$content$ModernAsyncTask$Status;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$android$support$v4$content$ModernAsyncTask$Status = new int[ModernAsyncTask.Status.values().length];
        try {
            ModernAsyncTask.$SwitchMap$android$support$v4$content$ModernAsyncTask$Status[ModernAsyncTask.Status.RUNNING.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            ModernAsyncTask.$SwitchMap$android$support$v4$content$ModernAsyncTask$Status[ModernAsyncTask.Status.FINISHED.ordinal()] = 2;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
