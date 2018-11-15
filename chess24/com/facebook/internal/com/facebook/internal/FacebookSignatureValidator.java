/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.os.Build
 */
package com.facebook.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import com.facebook.internal.Utility;
import java.util.HashSet;

public class FacebookSignatureValidator {
    private static final String FBF_HASH = "2438bce1ddb7bd026d5ff89f598b3b5e5bb824b3";
    private static final String FBI_HASH = "a4b7452e2ed8f5f191058ca7bbfd26b0d3214bfc";
    private static final String FBL2_HASH = "df6b721c8b4d3b6eb44c861d4415007e5a35fc95";
    private static final String FBL_HASH = "5e8f16062ea3cd2c4a0d547876baa6f38cabf625";
    private static final String FBR2_HASH = "cc2751449a350f668590264ed76692694a80308a";
    private static final String FBR_HASH = "8a3c4b262d721acd49a4bf97d5213199c86fa2b9";
    private static final String MSR_HASH = "9b8f518b086098de3d77736f9458a3d2f6f95a37";
    private static final HashSet<String> validAppSignatureHashes = FacebookSignatureValidator.buildAppSignatureHashes();

    private static HashSet<String> buildAppSignatureHashes() {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add(FBR_HASH);
        hashSet.add(FBR2_HASH);
        hashSet.add(FBI_HASH);
        hashSet.add(FBL_HASH);
        hashSet.add(FBL2_HASH);
        hashSet.add(MSR_HASH);
        hashSet.add(FBF_HASH);
        return hashSet;
    }

    public static boolean validateSignature(Context arrsignature, String string) {
        block5 : {
            String string2 = Build.BRAND;
            int n = arrsignature.getApplicationInfo().flags;
            if (string2.startsWith("generic") && (n & 2) != 0) {
                return true;
            }
            try {
                arrsignature = arrsignature.getPackageManager().getPackageInfo(string, 64);
                if (arrsignature.signatures == null) break block5;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return false;
            }
            if (arrsignature.signatures.length <= 0) {
                return false;
            }
            arrsignature = arrsignature.signatures;
            int n2 = arrsignature.length;
            for (n = 0; n < n2; ++n) {
                string = Utility.sha1hash(arrsignature[n].toByteArray());
                if (validAppSignatureHashes.contains(string)) continue;
                return false;
            }
            return true;
        }
        return false;
    }
}
