/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.os;

public class OperationCanceledException
extends RuntimeException {
    public OperationCanceledException() {
        this(null);
    }

    public OperationCanceledException(String string) {
        if (string == null) {
            string = "The operation has been canceled.";
        }
        super(string);
    }
}
