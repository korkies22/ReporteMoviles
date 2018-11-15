/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import java.io.IOException;
import java.io.InputStream;

class ChannelSftp
extends InputStream {
    byte[] _data;
    boolean closed;
    ChannelSftp.Header header;
    long offset;
    int request_max;
    long request_offset;
    byte[] rest_byte;
    int rest_length;
    final /* synthetic */ byte[] val$handle;
    final /* synthetic */ SftpProgressMonitor val$monitor;
    final /* synthetic */ long val$skip;

    ChannelSftp(long l, SftpProgressMonitor sftpProgressMonitor, byte[] arrby) {
        this.val$skip = l;
        this.val$monitor = sftpProgressMonitor;
        this.val$handle = arrby;
        this.offset = this.val$skip;
        this.closed = false;
        this.rest_length = 0;
        this._data = new byte[1];
        this.rest_byte = new byte[1024];
        this.header = new ChannelSftp.Header(ChannelSftp.this);
        this.request_max = 1;
        this.request_offset = this.offset;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        if (this.val$monitor != null) {
            this.val$monitor.end();
        }
        ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
        try {
            ChannelSftp.this._sendCLOSE(this.val$handle, this.header);
            return;
        }
        catch (Exception exception) {
            throw new IOException("error");
        }
    }

    @Override
    public int read() throws IOException {
        if (this.closed) {
            return -1;
        }
        if (this.read(this._data, 0, 1) == -1) {
            return -1;
        }
        return this._data[0] & 255;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        if (this.closed) {
            return -1;
        }
        return this.read(arrby, 0, arrby.length);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n5;
        ChannelSftp.RequestQueue$Request requestQueue$Request;
        int n3;
        long l2;
        block29 : {
            int n4;
            block30 : {
                block28 : {
                    if (this.closed) {
                        return -1;
                    }
                    if (arrby == null) {
                        throw new NullPointerException();
                    }
                    if (n < 0) throw new IndexOutOfBoundsException();
                    if (n2 < 0) throw new IndexOutOfBoundsException();
                    if (n + n2 > arrby.length) {
                        throw new IndexOutOfBoundsException();
                    }
                    if (n2 == 0) {
                        return 0;
                    }
                    if (this.rest_length > 0) {
                        int n6 = this.rest_length;
                        if (n6 <= n2) {
                            n2 = n6;
                        }
                        System.arraycopy(this.rest_byte, 0, arrby, n, n2);
                        if (n2 != this.rest_length) {
                            System.arraycopy(this.rest_byte, n2, this.rest_byte, 0, this.rest_length - n2);
                        }
                        if (this.val$monitor != null && !this.val$monitor.count(n2)) {
                            this.close();
                            return -1;
                        }
                        this.rest_length -= n2;
                        return n2;
                    }
                    n4 = n2;
                    if (com.jcraft.jsch.ChannelSftp.access$700((com.jcraft.jsch.ChannelSftp)ChannelSftp.this).buffer.length - 13 < n2) {
                        n4 = com.jcraft.jsch.ChannelSftp.access$700((com.jcraft.jsch.ChannelSftp)ChannelSftp.this).buffer.length - 13;
                    }
                    n2 = n4;
                    if (ChannelSftp.this.server_version == 0) {
                        n2 = n4;
                        if (n4 > 1024) {
                            n2 = 1024;
                        }
                    }
                    ChannelSftp.this.rq.count();
                    n4 = com.jcraft.jsch.ChannelSftp.access$700((com.jcraft.jsch.ChannelSftp)ChannelSftp.this).buffer.length - 13;
                    if (ChannelSftp.this.server_version == 0) {
                        n4 = 1024;
                    }
                    while (ChannelSftp.this.rq.count() < this.request_max) {
                        ChannelSftp.this.sendREAD(this.val$handle, this.request_offset, n4, ChannelSftp.this.rq);
                        this.request_offset += (long)n4;
                    }
                    this.header = ChannelSftp.this.header(ChannelSftp.this.buf, this.header);
                    this.rest_length = this.header.length;
                    n4 = this.header.type;
                    n5 = this.header.rid;
                    try {
                        requestQueue$Request = ChannelSftp.this.rq.get(this.header.rid);
                        if (n4 != 101 && n4 != 103) {
                            throw new IOException("error");
                        }
                        if (n4 != 101) break block28;
                    }
                    catch (SftpException sftpException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("error: ");
                        stringBuilder.append(sftpException.toString());
                        throw new IOException(stringBuilder.toString());
                    }
                    catch (ChannelSftp.RequestQueue$OutOfOrderException requestQueue$OutOfOrderException) {
                        this.request_offset = requestQueue$OutOfOrderException.offset;
                        this.skip(this.header.length);
                        ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                        return 0;
                    }
                    ChannelSftp.this.fill(ChannelSftp.this.buf, this.rest_length);
                    n = ChannelSftp.this.buf.getInt();
                    this.rest_length = 0;
                    if (n != 1) throw new IOException("error");
                    this.close();
                    return -1;
                }
                ChannelSftp.this.buf.rewind();
                ChannelSftp.this.fill(com.jcraft.jsch.ChannelSftp.access$700((com.jcraft.jsch.ChannelSftp)ChannelSftp.this).buffer, 0, 4);
                n4 = ChannelSftp.this.buf.getInt();
                this.rest_length -= 4;
                n5 = this.rest_length - n4;
                long l = this.offset;
                l2 = n4;
                this.offset = l + l2;
                if (n4 <= 0) return 0;
                if (n4 <= n2) {
                    n2 = n4;
                }
                n3 = ChannelSftp.this.io_in.read(arrby, n, n2);
                if (n3 < 0) {
                    return -1;
                }
                this.rest_length = n = n4 - n3;
                if (n <= 0) break block29;
                if (this.rest_byte.length < n) {
                    this.rest_byte = new byte[n];
                }
                n2 = 0;
                break block30;
                catch (Exception exception) {
                    throw new IOException("error");
                }
            }
            while (n > 0 && (n4 = ChannelSftp.this.io_in.read(this.rest_byte, n2, n)) > 0) {
                n2 += n4;
                n -= n4;
            }
        }
        if (n5 > 0) {
            ChannelSftp.this.io_in.skip(n5);
        }
        if (l2 < requestQueue$Request.length) {
            ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
            ChannelSftp.this.sendREAD(this.val$handle, requestQueue$Request.offset + l2, (int)(requestQueue$Request.length - l2), ChannelSftp.this.rq);
            this.request_offset = requestQueue$Request.offset + requestQueue$Request.length;
        }
        if (this.request_max < ChannelSftp.this.rq.size()) {
            ++this.request_max;
        }
        if (this.val$monitor == null) return n3;
        if (this.val$monitor.count(n3)) return n3;
        this.close();
        return -1;
        catch (Exception exception) {
            throw new IOException("error");
        }
    }
}
