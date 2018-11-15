/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.Cipher;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class TripleDESCBC
implements Cipher {
    private static final int bsize = 24;
    private static final int ivsize = 8;
    private javax.crypto.Cipher cipher;

    @Override
    public int getBlockSize() {
        return 24;
    }

    @Override
    public int getIVSize() {
        return 8;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void init(int n, byte[] object, byte[] object2) throws Exception {
        byte[] arrby = object2;
        if (((byte[])object2).length > 8) {
            arrby = new byte[8];
            System.arraycopy(object2, 0, arrby, 0, arrby.length);
        }
        object2 = object;
        if (((byte[])object).length > 24) {
            object2 = new byte[24];
            System.arraycopy(object, 0, object2, 0, ((byte[])object2).length);
        }
        try {
            object = new StringBuilder();
            object.append("DESede/CBC/");
            object.append("NoPadding");
            this.cipher = javax.crypto.Cipher.getInstance(object.toString());
            object = new DESedeKeySpec((byte[])object2);
            object = SecretKeyFactory.getInstance("DESede").generateSecret((KeySpec)object);
            synchronized (javax.crypto.Cipher.class) {
                object2 = this.cipher;
                n = n == 0 ? 1 : 2;
            }
        }
        catch (Exception exception) {
            this.cipher = null;
            throw exception;
        }
        {
            object2.init(n, (Key)object, new IvParameterSpec(arrby));
            return;
        }
    }

    @Override
    public boolean isCBC() {
        return true;
    }

    @Override
    public void update(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws Exception {
        this.cipher.update(arrby, n, n2, arrby2, n3);
    }
}
