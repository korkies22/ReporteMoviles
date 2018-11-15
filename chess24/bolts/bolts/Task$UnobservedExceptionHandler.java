/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.Task;
import bolts.UnobservedTaskException;

public static interface Task.UnobservedExceptionHandler {
    public void unobservedException(Task<?> var1, UnobservedTaskException var2);
}
