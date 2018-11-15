/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.text.TextUtils
 */
package io.fabric.sdk.android.services.common;

import android.os.Build;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

static enum CommonUtils.Architecture {
    X86_32,
    X86_64,
    ARM_UNKNOWN,
    PPC,
    PPC64,
    ARMV6,
    ARMV7,
    UNKNOWN,
    ARMV7S,
    ARM64;
    
    private static final Map<String, CommonUtils.Architecture> matcher;

    static {
        matcher = new HashMap<String, CommonUtils.Architecture>(4);
        matcher.put("armeabi-v7a", ARMV7);
        matcher.put("armeabi", ARMV6);
        matcher.put("arm64-v8a", ARM64);
        matcher.put("x86", X86_32);
    }

    private CommonUtils.Architecture() {
    }

    static CommonUtils.Architecture getValue() {
        Object object = Build.CPU_ABI;
        if (TextUtils.isEmpty((CharSequence)object)) {
            Fabric.getLogger().d("Fabric", "Architecture#getValue()::Build.CPU_ABI returned null or empty");
            return UNKNOWN;
        }
        object = object.toLowerCase(Locale.US);
        CommonUtils.Architecture architecture = matcher.get(object);
        object = architecture;
        if (architecture == null) {
            object = UNKNOWN;
        }
        return object;
    }
}
