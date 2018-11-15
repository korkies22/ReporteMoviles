/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public interface ServerSocketFactory {
    public ServerSocket createServerSocket(int var1, int var2, InetAddress var3) throws IOException;
}
