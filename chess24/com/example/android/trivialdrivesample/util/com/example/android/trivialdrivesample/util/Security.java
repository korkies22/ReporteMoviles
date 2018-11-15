/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.example.android.trivialdrivesample.util;

import android.text.TextUtils;
import android.util.Log;
import com.example.android.trivialdrivesample.util.Base64;
import com.example.android.trivialdrivesample.util.Base64DecoderException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Security {
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final String TAG = "IABUtil/Security";

    public static PublicKey generatePublicKey(String object) {
        try {
            object = Base64.decode((String)object);
            object = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM).generatePublic(new X509EncodedKeySpec((byte[])object));
            return object;
        }
        catch (Base64DecoderException base64DecoderException) {
            Log.e((String)TAG, (String)"Base64 decoding failed.");
            throw new IllegalArgumentException(base64DecoderException);
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            Log.e((String)TAG, (String)"Invalid key specification.");
            throw new IllegalArgumentException(invalidKeySpecException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean verify(PublicKey publicKey, String string, String string2) {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(string.getBytes());
            if (signature.verify(Base64.decode(string2))) return true;
            Log.e((String)TAG, (String)"Signature verification failed.");
            return false;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
        Log.e((String)TAG, (String)"NoSuchAlgorithmException.");
        return false;
        catch (InvalidKeyException invalidKeyException) {}
        Log.e((String)TAG, (String)"Invalid key specification.");
        return false;
        catch (SignatureException signatureException) {}
        Log.e((String)TAG, (String)"Signature exception.");
        return false;
        catch (Base64DecoderException base64DecoderException) {}
        Log.e((String)TAG, (String)"Base64 decoding failed.");
        return false;
    }

    public static boolean verifyPurchase(String string, String string2, String string3) {
        if (!(TextUtils.isEmpty((CharSequence)string2) || TextUtils.isEmpty((CharSequence)string) || TextUtils.isEmpty((CharSequence)string3))) {
            return Security.verify(Security.generatePublicKey(string), string2, string3);
        }
        Log.e((String)TAG, (String)"Purchase verification failed: missing data.");
        return false;
    }
}
