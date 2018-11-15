/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  com.jcraft.jzlib.ZStream
 */
package com.jcraft.jsch.jcraft;

import com.jcraft.jzlib.ZStream;
import java.io.PrintStream;

public class Compression
implements com.jcraft.jsch.Compression {
    private static final int BUF_SIZE = 4096;
    private final int buffer_margin = 52;
    private byte[] inflated_buf;
    private ZStream stream = new ZStream();
    private byte[] tmpbuf = new byte[4096];
    private int type;

    @Override
    public byte[] compress(byte[] arrby, int n, int[] arrn) {
        byte[] arrby2;
        int n2;
        this.stream.next_in = arrby;
        this.stream.next_in_index = n;
        this.stream.avail_in = arrn[0] - n;
        do {
            this.stream.next_out = this.tmpbuf;
            this.stream.next_out_index = 0;
            this.stream.avail_out = 4096;
            n2 = this.stream.deflate(1);
            if (n2 != 0) {
                arrby2 = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("compress: deflate returnd ");
                stringBuilder.append(n2);
                arrby2.println(stringBuilder.toString());
                arrby2 = arrby;
                n2 = n;
            } else {
                int n3 = 4096 - this.stream.avail_out;
                int n4 = arrby.length;
                n2 = n + n3;
                int n5 = n2 + 52;
                arrby2 = arrby;
                if (n4 < n5) {
                    arrby2 = new byte[n5 * 2];
                    System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
                }
                System.arraycopy(this.tmpbuf, 0, arrby2, n, n3);
            }
            arrby = arrby2;
            n = n2;
        } while (this.stream.avail_out == 0);
        arrn[0] = n2;
        return arrby2;
    }

    @Override
    public void init(int n, int n2) {
        if (n == 1) {
            this.stream.deflateInit(n2);
            this.type = 1;
            return;
        }
        if (n == 0) {
            this.stream.inflateInit();
            this.inflated_buf = new byte[4096];
            this.type = 0;
        }
    }

    @Override
    public byte[] uncompress(byte[] object, int n, int[] object2) {
        byte[] arrby;
        this.stream.next_in = object;
        this.stream.next_in_index = n;
        this.stream.avail_in = object2[0];
        int n2 = 0;
        do {
            this.stream.next_out = this.tmpbuf;
            this.stream.next_out_index = 0;
            this.stream.avail_out = 4096;
            int n3 = this.stream.inflate(1);
            if (n3 == -5) break;
            if (n3 != 0) {
                object = System.err;
                object2 = new StringBuilder();
                object2.append("uncompress: inflate returnd ");
                object2.append(n3);
                object.println(object2.toString());
                return null;
            }
            n3 = this.inflated_buf.length;
            int n4 = n2 + 4096;
            if (n3 < n4 - this.stream.avail_out) {
                int n5;
                n3 = n5 = this.inflated_buf.length * 2;
                if (n5 < n4 - this.stream.avail_out) {
                    n3 = n4 - this.stream.avail_out;
                }
                arrby = new byte[n3];
                System.arraycopy(this.inflated_buf, 0, arrby, 0, n2);
                this.inflated_buf = arrby;
            }
            System.arraycopy(this.tmpbuf, 0, this.inflated_buf, n2, 4096 - this.stream.avail_out);
            object2[0] = n2 += 4096 - this.stream.avail_out;
        } while (true);
        if (n2 > ((byte[])object).length - n) {
            arrby = new byte[n2 + n];
            System.arraycopy(object, 0, arrby, 0, n);
            System.arraycopy(this.inflated_buf, 0, arrby, n, n2);
            object = arrby;
        } else {
            System.arraycopy(this.inflated_buf, 0, object, n, n2);
        }
        object2[0] = n2;
        return object;
    }
}
