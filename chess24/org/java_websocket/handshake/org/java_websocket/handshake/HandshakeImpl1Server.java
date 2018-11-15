/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.handshake;

import org.java_websocket.handshake.HandshakedataImpl1;
import org.java_websocket.handshake.ServerHandshakeBuilder;

public class HandshakeImpl1Server
extends HandshakedataImpl1
implements ServerHandshakeBuilder {
    private short httpstatus;
    private String httpstatusmessage;

    @Override
    public short getHttpStatus() {
        return this.httpstatus;
    }

    @Override
    public String getHttpStatusMessage() {
        return this.httpstatusmessage;
    }

    @Override
    public void setHttpStatus(short s) {
        this.httpstatus = s;
    }

    @Override
    public void setHttpStatusMessage(String string) {
        this.httpstatusmessage = string;
    }
}
