/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.framing;

import java.nio.ByteBuffer;
import org.java_websocket.exceptions.InvalidFrameException;

public interface Framedata {
    public void append(Framedata var1) throws InvalidFrameException;

    public Opcode getOpcode();

    public ByteBuffer getPayloadData();

    public boolean getTransfereMasked();

    public boolean isFin();

    public static enum Opcode {
        CONTINUOUS,
        TEXT,
        BINARY,
        PING,
        PONG,
        CLOSING;
        

        private Opcode() {
        }
    }

}
