/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.framing;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.framing.FrameBuilder;
import org.java_websocket.framing.Framedata;
import org.java_websocket.util.Charsetfunctions;

public class FramedataImpl1
implements FrameBuilder {
    protected static byte[] emptyarray = new byte[0];
    protected boolean fin;
    protected Framedata.Opcode optcode;
    protected boolean transferemasked;
    private ByteBuffer unmaskedpayload;

    public FramedataImpl1() {
    }

    public FramedataImpl1(Framedata.Opcode opcode) {
        this.optcode = opcode;
        this.unmaskedpayload = ByteBuffer.wrap(emptyarray);
    }

    public FramedataImpl1(Framedata framedata) {
        this.fin = framedata.isFin();
        this.optcode = framedata.getOpcode();
        this.unmaskedpayload = framedata.getPayloadData();
        this.transferemasked = framedata.getTransfereMasked();
    }

    @Override
    public void append(Framedata framedata) throws InvalidFrameException {
        ByteBuffer byteBuffer = framedata.getPayloadData();
        if (this.unmaskedpayload == null) {
            this.unmaskedpayload = ByteBuffer.allocate(byteBuffer.remaining());
            byteBuffer.mark();
            this.unmaskedpayload.put(byteBuffer);
            byteBuffer.reset();
        } else {
            byteBuffer.mark();
            this.unmaskedpayload.position(this.unmaskedpayload.limit());
            this.unmaskedpayload.limit(this.unmaskedpayload.capacity());
            if (byteBuffer.remaining() > this.unmaskedpayload.remaining()) {
                ByteBuffer byteBuffer2 = ByteBuffer.allocate(byteBuffer.remaining() + this.unmaskedpayload.capacity());
                this.unmaskedpayload.flip();
                byteBuffer2.put(this.unmaskedpayload);
                byteBuffer2.put(byteBuffer);
                this.unmaskedpayload = byteBuffer2;
            } else {
                this.unmaskedpayload.put(byteBuffer);
            }
            this.unmaskedpayload.rewind();
            byteBuffer.reset();
        }
        this.fin = framedata.isFin();
    }

    @Override
    public Framedata.Opcode getOpcode() {
        return this.optcode;
    }

    @Override
    public ByteBuffer getPayloadData() {
        return this.unmaskedpayload;
    }

    @Override
    public boolean getTransfereMasked() {
        return this.transferemasked;
    }

    @Override
    public boolean isFin() {
        return this.fin;
    }

    @Override
    public void setFin(boolean bl) {
        this.fin = bl;
    }

    @Override
    public void setOptcode(Framedata.Opcode opcode) {
        this.optcode = opcode;
    }

    @Override
    public void setPayload(ByteBuffer byteBuffer) throws InvalidDataException {
        this.unmaskedpayload = byteBuffer;
    }

    @Override
    public void setTransferemasked(boolean bl) {
        this.transferemasked = bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Framedata{ optcode:");
        stringBuilder.append((Object)this.getOpcode());
        stringBuilder.append(", fin:");
        stringBuilder.append(this.isFin());
        stringBuilder.append(", payloadlength:[pos:");
        stringBuilder.append(this.unmaskedpayload.position());
        stringBuilder.append(", len:");
        stringBuilder.append(this.unmaskedpayload.remaining());
        stringBuilder.append("], payload:");
        stringBuilder.append(Arrays.toString(Charsetfunctions.utf8Bytes(new String(this.unmaskedpayload.array()))));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
