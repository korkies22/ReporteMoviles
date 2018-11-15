/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.facebook.places.internal;

import android.location.Location;
import com.facebook.places.internal.ScannerException;

public interface LocationScanner {
    public Location getLocation() throws ScannerException;

    public void initAndCheckEligibility() throws ScannerException;
}
