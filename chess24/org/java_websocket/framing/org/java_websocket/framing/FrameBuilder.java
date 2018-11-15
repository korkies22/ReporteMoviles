/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.framing;

import java.nio.ByteBuffer;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;

public interface FrameBuilder
extends Framedata {
    public void setFin(boolean var1);

    public void setOptcode(Framedata.Opcode var1);

    public void setPayload(ByteBuffer var1) throws InvalidDataException;

    public void setTransferemasked(boolean var1);
}
