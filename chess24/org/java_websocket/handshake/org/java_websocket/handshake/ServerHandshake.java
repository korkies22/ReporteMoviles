/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.handshake;

import org.java_websocket.handshake.Handshakedata;

public interface ServerHandshake
extends Handshakedata {
    public short getHttpStatus();

    public String getHttpStatusMessage();
}
