/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.framing;

import org.java_websocket.framing.Framedata;

public static enum Framedata.Opcode {
    CONTINUOUS,
    TEXT,
    BINARY,
    PING,
    PONG,
    CLOSING;
    

    private Framedata.Opcode() {
    }
}
