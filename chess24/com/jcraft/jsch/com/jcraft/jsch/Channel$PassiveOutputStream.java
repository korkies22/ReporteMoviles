/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Channel;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

class Channel.PassiveOutputStream
extends PipedOutputStream {
    private Channel.MyPipedInputStream _sink;

    Channel.PassiveOutputStream(PipedInputStream pipedInputStream, boolean bl) throws IOException {
        super(pipedInputStream);
        this._sink = null;
        if (bl && pipedInputStream instanceof Channel.MyPipedInputStream) {
            this._sink = (Channel.MyPipedInputStream)pipedInputStream;
        }
    }

    @Override
    public void write(int n) throws IOException {
        if (this._sink != null) {
            this._sink.checkSpace(1);
        }
        super.write(n);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (this._sink != null) {
            this._sink.checkSpace(n2);
        }
        super.write(arrby, n, n2);
    }
}
