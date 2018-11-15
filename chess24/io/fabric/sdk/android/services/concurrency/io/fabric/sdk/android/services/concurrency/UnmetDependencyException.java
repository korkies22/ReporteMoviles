/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

public class UnmetDependencyException
extends RuntimeException {
    public UnmetDependencyException() {
    }

    public UnmetDependencyException(String string) {
        super(string);
    }

    public UnmetDependencyException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public UnmetDependencyException(Throwable throwable) {
        super(throwable);
    }
}
