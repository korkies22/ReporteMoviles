/*
 * Decompiled with CFR 0_134.
 */
package bolts;

public class ExecutorException
extends RuntimeException {
    public ExecutorException(Exception exception) {
        super("An exception was thrown by an Executor", exception);
    }
}
