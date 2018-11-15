/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Packet;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

public class IO {
    InputStream in;
    private boolean in_dontclose = false;
    OutputStream out;
    private boolean out_dontclose = false;
    OutputStream out_ext;
    private boolean out_ext_dontclose = false;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() {
        try {
            if (this.in != null && !this.in_dontclose) {
                this.in.close();
            }
            this.in = null;
        }
        catch (Exception exception) {}
        this.out_close();
        try {
            if (this.out_ext != null && !this.out_ext_dontclose) {
                this.out_ext.close();
            }
            this.out_ext = null;
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    int getByte() throws IOException {
        return this.in.read();
    }

    void getByte(byte[] arrby) throws IOException {
        this.getByte(arrby, 0, arrby.length);
    }

    void getByte(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        do {
            if ((n3 = this.in.read(arrby, n, n2)) < 0) {
                throw new IOException("End of IO Stream Read");
            }
            n += n3;
            n2 = n3 = n2 - n3;
        } while (n3 > 0);
    }

    void out_close() {
        try {
            if (this.out != null && !this.out_dontclose) {
                this.out.close();
            }
            this.out = null;
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public void put(Packet packet) throws IOException, SocketException {
        this.out.write(packet.buffer.buffer, 0, packet.buffer.index);
        this.out.flush();
    }

    void put(byte[] arrby, int n, int n2) throws IOException {
        this.out.write(arrby, n, n2);
        this.out.flush();
    }

    void put_ext(byte[] arrby, int n, int n2) throws IOException {
        this.out_ext.write(arrby, n, n2);
        this.out_ext.flush();
    }

    void setExtOutputStream(OutputStream outputStream) {
        this.out_ext = outputStream;
    }

    void setExtOutputStream(OutputStream outputStream, boolean bl) {
        this.out_ext_dontclose = bl;
        this.setExtOutputStream(outputStream);
    }

    void setInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    void setInputStream(InputStream inputStream, boolean bl) {
        this.in_dontclose = bl;
        this.setInputStream(inputStream);
    }

    void setOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    void setOutputStream(OutputStream outputStream, boolean bl) {
        this.out_dontclose = bl;
        this.setOutputStream(outputStream);
    }
}
