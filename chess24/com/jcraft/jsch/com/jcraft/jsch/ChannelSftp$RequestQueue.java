/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import java.io.IOException;

private class ChannelSftp.RequestQueue {
    int count;
    int head;
    Request[] rrq = null;

    ChannelSftp.RequestQueue(int n) {
        this.rrq = new Request[n];
        for (n = 0; n < this.rrq.length; ++n) {
            this.rrq[n] = new Request();
        }
        this.init();
    }

    void add(int n, long l, int n2) {
        int n3;
        if (this.count == 0) {
            this.head = 0;
        }
        int n4 = n3 = this.head + this.count;
        if (n3 >= this.rrq.length) {
            n4 = n3 - this.rrq.length;
        }
        this.rrq[n4].id = n;
        this.rrq[n4].offset = l;
        this.rrq[n4].length = n2;
        ++this.count;
    }

    void cancel(ChannelSftp.Header header, Buffer buffer) throws IOException {
        int n = this.count;
        for (int i = 0; i < n; ++i) {
            header = ChannelSftp.this.header(buffer, header);
            int n2 = header.length;
            for (int j = 0; j < this.rrq.length; ++j) {
                if (this.rrq[j].id != header.rid) continue;
                this.rrq[j].id = 0;
                break;
            }
            ChannelSftp.this.skip(n2);
        }
        this.init();
    }

    int count() {
        return this.count;
    }

    Request get(int n) throws OutOfOrderException, SftpException {
        int n2 = this.count;
        int n3 = 1;
        this.count = n2 - 1;
        n2 = this.head++;
        if (this.head == this.rrq.length) {
            this.head = 0;
        }
        if (this.rrq[n2].id != n) {
            long l;
            block4 : {
                l = this.getOffset();
                for (n2 = 0; n2 < this.rrq.length; ++n2) {
                    if (this.rrq[n2].id != n) continue;
                    this.rrq[n2].id = 0;
                    n2 = n3;
                    break block4;
                }
                n2 = 0;
            }
            if (n2 != 0) {
                throw new OutOfOrderException(l);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RequestQueue: unknown request id ");
            stringBuilder.append(n);
            throw new SftpException(4, stringBuilder.toString());
        }
        this.rrq[n2].id = 0;
        return this.rrq[n2];
    }

    long getOffset() {
        long l = Long.MAX_VALUE;
        for (int i = 0; i < this.rrq.length; ++i) {
            long l2;
            if (this.rrq[i].id == 0) {
                l2 = l;
            } else {
                l2 = l;
                if (l > this.rrq[i].offset) {
                    l2 = this.rrq[i].offset;
                }
            }
            l = l2;
        }
        return l;
    }

    void init() {
        this.count = 0;
        this.head = 0;
    }

    int size() {
        return this.rrq.length;
    }

    class OutOfOrderException
    extends Exception {
        long offset;

        OutOfOrderException(long l) {
            this.offset = l;
        }
    }

    class Request {
        int id;
        long length;
        long offset;

        Request() {
        }
    }

}
