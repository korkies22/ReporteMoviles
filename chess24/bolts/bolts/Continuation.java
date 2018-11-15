/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.Task;

public interface Continuation<TTaskResult, TContinuationResult> {
    public TContinuationResult then(Task<TTaskResult> var1) throws Exception;
}
