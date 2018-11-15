/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

public class ScannerException
extends Exception {
    public Type type;

    public ScannerException(Type type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type: ");
        stringBuilder.append(type.name());
        super(stringBuilder.toString());
        this.type = type;
    }

    public ScannerException(Type type, Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type: ");
        stringBuilder.append(type.name());
        super(stringBuilder.toString(), exception);
        this.type = type;
    }

    public ScannerException(Type type, String string) {
        super(string);
        this.type = type;
    }

    public static enum Type {
        NOT_SUPPORTED,
        PERMISSION_DENIED,
        DISABLED,
        SCAN_ALREADY_IN_PROGRESS,
        UNKNOWN_ERROR,
        TIMEOUT;
        

        private Type() {
        }
    }

}
