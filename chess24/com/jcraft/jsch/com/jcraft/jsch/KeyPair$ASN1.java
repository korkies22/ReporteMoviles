/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.KeyPair;
import java.util.Vector;

class KeyPair.ASN1 {
    byte[] buf;
    int length;
    int start;

    KeyPair.ASN1(byte[] arrby) throws KeyPair.ASN1Exception {
        this(arrby, 0, arrby.length);
    }

    KeyPair.ASN1(byte[] arrby, int n, int n2) throws KeyPair.ASN1Exception {
        this.buf = arrby;
        this.start = n;
        this.length = n2;
        if (n + n2 > arrby.length) {
            throw new KeyPair.ASN1Exception(KeyPair.this);
        }
    }

    private int getLength(int[] arrn) {
        int n;
        int n2 = arrn[0];
        byte[] arrby = this.buf;
        int n3 = n2 + 1;
        int n4 = n = arrby[n2] & 255;
        n2 = n3;
        if ((n & 128) != 0) {
            n4 = n & 127;
            n2 = 0;
            while (n4 > 0) {
                n2 = (this.buf[n3] & 255) + (n2 << 8);
                --n4;
                ++n3;
            }
            n4 = n2;
            n2 = n3;
        }
        arrn[0] = n2;
        return n4;
    }

    byte[] getContent() {
        int[] arrn = new int[]{this.start + 1};
        int n = this.getLength(arrn);
        int n2 = arrn[0];
        arrn = new byte[n];
        System.arraycopy(this.buf, n2, arrn, 0, arrn.length);
        return arrn;
    }

    KeyPair.ASN1[] getContents() throws KeyPair.ASN1Exception {
        int n = this.buf[this.start];
        Object[] arrobject = new int[1];
        int n2 = this.start;
        int n3 = 0;
        arrobject[0] = n2 + 1;
        n2 = this.getLength((int[])arrobject);
        if (n == 5) {
            return new KeyPair.ASN1[0];
        }
        n = arrobject[0];
        Vector<KeyPair.ASN1> vector = new Vector<KeyPair.ASN1>();
        while (n2 > 0) {
            arrobject[0] = ++n;
            int n4 = this.getLength((int[])arrobject);
            int n5 = arrobject[0];
            int n6 = n5 - n;
            vector.addElement(new KeyPair.ASN1(this.buf, n - 1, n6 + 1 + n4));
            n = n5 + n4;
            n2 = n2 - 1 - n6 - n4;
        }
        arrobject = new KeyPair.ASN1[vector.size()];
        for (n2 = n3; n2 < vector.size(); ++n2) {
            arrobject[n2] = (int)((KeyPair.ASN1)vector.elementAt(n2));
        }
        return arrobject;
    }

    int getType() {
        return this.buf[this.start] & 255;
    }

    boolean isINTEGER() {
        if (this.getType() == 2) {
            return true;
        }
        return false;
    }

    boolean isOBJECT() {
        if (this.getType() == 6) {
            return true;
        }
        return false;
    }

    boolean isOCTETSTRING() {
        if (this.getType() == 4) {
            return true;
        }
        return false;
    }

    boolean isSEQUENCE() {
        if (this.getType() == 48) {
            return true;
        }
        return false;
    }
}
