/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.Cipher;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

public class ARCFOUR128
implements Cipher {
    private static final int bsize = 16;
    private static final int ivsize = 8;
    private static final int skip = 1536;
    private javax.crypto.Cipher cipher;

    @Override
    public int getBlockSize() {
        return 16;
    }

    @Override
    public int getIVSize() {
        return 8;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void init(int n, byte[] object, byte[] object2) throws Exception {
        int n2 = 0;
        object2 = object;
        if (((byte[])object).length > 16) {
            object2 = new byte[16];
            System.arraycopy(object, 0, object2, 0, ((Object)object2).length);
        }
        this.cipher = javax.crypto.Cipher.getInstance("RC4");
        object = new SecretKeySpec((byte[])object2, "RC4");
        // MONITORENTER : javax.crypto.Cipher.class
        object2 = this.cipher;
        n = n == 0 ? 1 : 2;
        object2.init(n, (Key)object);
        // MONITOREXIT : javax.crypto.Cipher.class
        try {
            object = new byte[1];
            n = n2;
        }
        catch (Exception exception) {
            this.cipher = null;
            throw exception;
        }
        while (n < 1536) {
            this.cipher.update((byte[])object, 0, 1, (byte[])object, 0);
            ++n;
        }
        return;
    }

    @Override
    public boolean isCBC() {
        return false;
    }

    @Override
    public void update(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws Exception {
        this.cipher.update(arrby, n, n2, arrby2, n3);
    }
}
