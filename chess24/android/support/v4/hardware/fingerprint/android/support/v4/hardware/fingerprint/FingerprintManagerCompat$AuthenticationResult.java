/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.hardware.fingerprint;

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

public static final class FingerprintManagerCompat.AuthenticationResult {
    private final FingerprintManagerCompat.CryptoObject mCryptoObject;

    public FingerprintManagerCompat.AuthenticationResult(FingerprintManagerCompat.CryptoObject cryptoObject) {
        this.mCryptoObject = cryptoObject;
    }

    public FingerprintManagerCompat.CryptoObject getCryptoObject() {
        return this.mCryptoObject;
    }
}
