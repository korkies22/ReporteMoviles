/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.common.util;

import android.os.Build;

public final class zzs {
    public static boolean isAtLeastN() {
        if (Build.VERSION.SDK_INT >= 24) {
            return true;
        }
        return false;
    }

    public static boolean zzyA() {
        if (Build.VERSION.SDK_INT >= 14) {
            return true;
        }
        return false;
    }

    public static boolean zzyB() {
        if (Build.VERSION.SDK_INT >= 15) {
            return true;
        }
        return false;
    }

    public static boolean zzyC() {
        if (Build.VERSION.SDK_INT >= 16) {
            return true;
        }
        return false;
    }

    public static boolean zzyD() {
        if (Build.VERSION.SDK_INT >= 17) {
            return true;
        }
        return false;
    }

    public static boolean zzyE() {
        if (Build.VERSION.SDK_INT >= 18) {
            return true;
        }
        return false;
    }

    public static boolean zzyF() {
        if (Build.VERSION.SDK_INT >= 19) {
            return true;
        }
        return false;
    }

    public static boolean zzyG() {
        if (Build.VERSION.SDK_INT >= 20) {
            return true;
        }
        return false;
    }

    @Deprecated
    public static boolean zzyH() {
        return zzs.zzyI();
    }

    public static boolean zzyI() {
        if (Build.VERSION.SDK_INT >= 21) {
            return true;
        }
        return false;
    }

    public static boolean zzyJ() {
        if (Build.VERSION.SDK_INT >= 23) {
            return true;
        }
        return false;
    }

    public static boolean zzyx() {
        if (Build.VERSION.SDK_INT >= 11) {
            return true;
        }
        return false;
    }

    public static boolean zzyy() {
        if (Build.VERSION.SDK_INT >= 12) {
            return true;
        }
        return false;
    }

    public static boolean zzyz() {
        if (Build.VERSION.SDK_INT >= 13) {
            return true;
        }
        return false;
    }
}
