/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

import com.facebook.places.internal.ScannerException;

public static enum ScannerException.Type {
    NOT_SUPPORTED,
    PERMISSION_DENIED,
    DISABLED,
    SCAN_ALREADY_IN_PROGRESS,
    UNKNOWN_ERROR,
    TIMEOUT;
    

    private ScannerException.Type() {
    }
}
