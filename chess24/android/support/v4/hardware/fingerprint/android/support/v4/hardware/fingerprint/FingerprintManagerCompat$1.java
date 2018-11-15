/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.hardware.fingerprint.FingerprintManager
 *  android.hardware.fingerprint.FingerprintManager$AuthenticationCallback
 *  android.hardware.fingerprint.FingerprintManager$AuthenticationResult
 *  android.hardware.fingerprint.FingerprintManager$CryptoObject
 */
package android.support.v4.hardware.fingerprint;

import android.hardware.fingerprint.FingerprintManager;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

static final class FingerprintManagerCompat
extends FingerprintManager.AuthenticationCallback {
    final /* synthetic */ FingerprintManagerCompat.AuthenticationCallback val$callback;

    FingerprintManagerCompat(FingerprintManagerCompat.AuthenticationCallback authenticationCallback) {
        this.val$callback = authenticationCallback;
    }

    public void onAuthenticationError(int n, CharSequence charSequence) {
        this.val$callback.onAuthenticationError(n, charSequence);
    }

    public void onAuthenticationFailed() {
        this.val$callback.onAuthenticationFailed();
    }

    public void onAuthenticationHelp(int n, CharSequence charSequence) {
        this.val$callback.onAuthenticationHelp(n, charSequence);
    }

    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult authenticationResult) {
        this.val$callback.onAuthenticationSucceeded(new FingerprintManagerCompat.AuthenticationResult(android.support.v4.hardware.fingerprint.FingerprintManagerCompat.unwrapCryptoObject(authenticationResult.getCryptoObject())));
    }
}
