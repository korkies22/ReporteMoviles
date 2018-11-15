/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.hardware.fingerprint;

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

public static abstract class FingerprintManagerCompat.AuthenticationCallback {
    public void onAuthenticationError(int n, CharSequence charSequence) {
    }

    public void onAuthenticationFailed() {
    }

    public void onAuthenticationHelp(int n, CharSequence charSequence) {
    }

    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult authenticationResult) {
    }
}
