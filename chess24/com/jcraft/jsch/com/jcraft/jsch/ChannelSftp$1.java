/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import java.io.IOException;
import java.io.OutputStream;

class ChannelSftp
extends OutputStream {
    private int _ackid = 0;
    byte[] _data = new byte[1];
    private int ackcount = 0;
    private int[] ackid = new int[1];
    private ChannelSftp.Header header = new ChannelSftp.Header(ChannelSftp.this);
    private boolean init = true;
    private boolean isClosed = false;
    private int startid = 0;
    final /* synthetic */ long[] val$_offset;
    final /* synthetic */ byte[] val$handle;
    final /* synthetic */ SftpProgressMonitor val$monitor;
    private int writecount = 0;

    ChannelSftp(byte[] arrby, long[] arrl, SftpProgressMonitor sftpProgressMonitor) {
        this.val$handle = arrby;
        this.val$_offset = arrl;
        this.val$monitor = sftpProgressMonitor;
    }

    @Override
    public void close() throws IOException {
        if (this.isClosed) {
            return;
        }
        this.flush();
        if (this.val$monitor != null) {
            this.val$monitor.end();
        }
        try {
            ChannelSftp.this._sendCLOSE(this.val$handle, this.header);
            this.isClosed = true;
            return;
        }
        catch (Exception exception) {
            throw new IOException(exception.toString());
        }
        catch (IOException iOException) {
            throw iOException;
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.isClosed) {
            throw new IOException("stream already closed");
        }
        if (!this.init) {
            try {
                while (this.writecount > this.ackcount) {
                    if (!ChannelSftp.this.checkStatus(null, this.header)) {
                        return;
                    }
                    ++this.ackcount;
                }
            }
            catch (SftpException sftpException) {
                throw new IOException(sftpException.toString());
            }
        }
    }

    @Override
    public void write(int n) throws IOException {
        this._data[0] = (byte)n;
        this.write(this._data, 0, 1);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void write(byte[] var1_1, int var2_4, int var3_5) throws IOException {
        if (this.init) {
            this.startid = com.jcraft.jsch.ChannelSftp.access$000(ChannelSftp.this);
            this._ackid = com.jcraft.jsch.ChannelSftp.access$000(ChannelSftp.this);
            this.init = false;
        }
        if (this.isClosed) {
            throw new IOException("stream already closed");
        }
        var4_6 = var2_4;
        var2_4 = var3_5;
        block4 : do lbl-1000: // 4 sources:
        {
            block9 : {
                if (var2_4 <= 0) ** GOTO lbl24
                var6_8 = com.jcraft.jsch.ChannelSftp.access$100(ChannelSftp.this, this.val$handle, this.val$_offset[0], var1_1, var4_6, var2_4);
                ++this.writecount;
                var7_9 = this.val$_offset;
                var7_9[0] = var7_9[0] + (long)var6_8;
                var5_7 = var4_6 + var6_8;
                var6_8 = var2_4 - var6_8;
                try {
                    if (com.jcraft.jsch.ChannelSftp.access$000(ChannelSftp.this) - 1 == this.startid) break block9;
                    var4_6 = var5_7;
                    var2_4 = var6_8;
                    if (com.jcraft.jsch.ChannelSftp.access$200(ChannelSftp.this).available() < 1024) ** GOTO lbl-1000
                    break block9;
lbl24: // 1 sources:
                    if (this.val$monitor == null) return;
                    if (this.val$monitor.count(var3_5) != false) return;
                    this.close();
                    throw new IOException("canceled");
                }
                catch (Exception var1_2) {
                    throw new IOException(var1_2.toString());
                }
                catch (IOException var1_3) {
                    throw var1_3;
                }
            }
            do {
                var4_6 = var5_7;
                var2_4 = var6_8;
                if (com.jcraft.jsch.ChannelSftp.access$200(ChannelSftp.this).available() <= 0) ** GOTO lbl-1000
                var4_6 = var5_7;
                var2_4 = var6_8;
                if (!com.jcraft.jsch.ChannelSftp.access$300(ChannelSftp.this, this.ackid, this.header)) continue block4;
                this._ackid = this.ackid[0];
                if (this.startid > this._ackid) throw new SftpException(4, "");
                if (this._ackid > com.jcraft.jsch.ChannelSftp.access$000(ChannelSftp.this) - 1) {
                    throw new SftpException(4, "");
                }
                ++this.ackcount;
            } while (true);
            break;
        } while (true);
    }
}
