/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF
implements com.jcraft.jsch.PBKDF {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public byte[] getKey(byte[] object, byte[] arrby, int n, int n2) {
        char[] arrc = new char[((byte[])object).length];
        for (int i = 0; i < ((byte[])object).length; ++i) {
            arrc[i] = (char)(object[i] & 255);
        }
        try {
            void var3_6;
            void var2_5;
            void var4_7;
            PBEKeySpec pBEKeySpec = new PBEKeySpec(arrc, (byte[])var2_5, (int)var3_6, (int)(var4_7 * 8));
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(pBEKeySpec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException generalSecurityException) {
            return null;
        }
    }
}
