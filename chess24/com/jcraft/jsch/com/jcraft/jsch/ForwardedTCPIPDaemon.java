/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ChannelForwardedTCPIP;
import java.io.InputStream;
import java.io.OutputStream;

public interface ForwardedTCPIPDaemon
extends Runnable {
    public void setArg(Object[] var1);

    public void setChannel(ChannelForwardedTCPIP var1, InputStream var2, OutputStream var3);
}
