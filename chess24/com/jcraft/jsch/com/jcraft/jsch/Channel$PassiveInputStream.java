/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Channel;
import java.io.IOException;
import java.io.PipedOutputStream;

class Channel.PassiveInputStream
extends Channel.MyPipedInputStream {
    PipedOutputStream out;

    Channel.PassiveInputStream(PipedOutputStream pipedOutputStream) throws IOException {
        super(Channel.this, pipedOutputStream);
        this.out = pipedOutputStream;
    }

    Channel.PassiveInputStream(PipedOutputStream pipedOutputStream, int n) throws IOException {
        super(Channel.this, pipedOutputStream, n);
        this.out = pipedOutputStream;
    }

    @Override
    public void close() throws IOException {
        if (this.out != null) {
            this.out.close();
        }
        this.out = null;
    }
}
