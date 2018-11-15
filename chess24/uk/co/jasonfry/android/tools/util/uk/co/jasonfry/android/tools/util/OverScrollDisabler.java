/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package uk.co.jasonfry.android.tools.util;

import android.view.View;

public class OverScrollDisabler {
    public static void disableOverScroll(View view) {
        view.setOverScrollMode(2);
    }
}
