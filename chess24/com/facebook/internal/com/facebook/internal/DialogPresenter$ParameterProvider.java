/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.internal;

import android.os.Bundle;
import com.facebook.internal.DialogPresenter;

public static interface DialogPresenter.ParameterProvider {
    public Bundle getLegacyParameters();

    public Bundle getParameters();
}
