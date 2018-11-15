/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.provider;

import java.util.Comparator;

static final class FontsContractCompat
implements Comparator<byte[]> {
    FontsContractCompat() {
    }

    @Override
    public int compare(byte[] arrby, byte[] arrby2) {
        if (arrby.length != arrby2.length) {
            return arrby.length - arrby2.length;
        }
        for (int i = 0; i < arrby.length; ++i) {
            if (arrby[i] == arrby2[i]) continue;
            return arrby[i] - arrby2[i];
        }
        return 0;
    }
}
