/*
 * Decompiled with CFR 0_134.
 */
package io.socket;

import java.io.IOException;

interface IOTransport {
    public boolean canSendBulk();

    public void connect();

    public void disconnect();

    public String getName();

    public void invalidate();

    public void send(String var1) throws Exception;

    public void sendBulk(String[] var1) throws IOException;
}
