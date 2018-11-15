/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.framing;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.util.Charsetfunctions;

public class CloseFrameBuilder
extends FramedataImpl1
implements CloseFrame {
    static final ByteBuffer emptybytebuffer = ByteBuffer.allocate(0);
    private int code;
    private String reason;

    public CloseFrameBuilder() {
        super(Framedata.Opcode.CLOSING);
        this.setFin(true);
    }

    public CloseFrameBuilder(int n) throws InvalidDataException {
        super(Framedata.Opcode.CLOSING);
        this.setFin(true);
        this.setCodeAndMessage(n, "");
    }

    public CloseFrameBuilder(int n, String string) throws InvalidDataException {
        super(Framedata.Opcode.CLOSING);
        this.setFin(true);
        this.setCodeAndMessage(n, string);
    }

    private void initCloseCode() throws InvalidFrameException {
        this.code = 1005;
        Object object = super.getPayloadData();
        object.mark();
        if (object.remaining() >= 2) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(4);
            byteBuffer.position(2);
            byteBuffer.putShort(object.getShort());
            byteBuffer.position(0);
            this.code = byteBuffer.getInt();
            if (this.code == 1006 || this.code == 1015 || this.code == 1005 || this.code > 4999 || this.code < 1000 || this.code == 1004) {
                object = new StringBuilder();
                object.append("closecode must not be sent over the wire: ");
                object.append(this.code);
                throw new InvalidFrameException(object.toString());
            }
        }
        object.reset();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void initMessage() throws InvalidDataException {
        Throwable throwable2222;
        if (this.code == 1005) {
            this.reason = Charsetfunctions.stringUtf8(super.getPayloadData());
            return;
        }
        ByteBuffer byteBuffer = super.getPayloadData();
        int n = byteBuffer.position();
        byteBuffer.position(byteBuffer.position() + 2);
        this.reason = Charsetfunctions.stringUtf8(byteBuffer);
        byteBuffer.position(n);
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (IllegalArgumentException illegalArgumentException) {}
            {
                throw new InvalidFrameException(illegalArgumentException);
            }
        }
        byteBuffer.position(n);
        throw throwable2222;
    }

    private void setCodeAndMessage(int n, String arrby) throws InvalidDataException {
        Object object = arrby;
        if (arrby == null) {
            object = "";
        }
        int n2 = n;
        if (n == 1015) {
            object = "";
            n2 = 1005;
        }
        if (n2 == 1005) {
            if (object.length() > 0) {
                throw new InvalidDataException(1002, "A close frame must have a closecode if it has a reason");
            }
            return;
        }
        arrby = Charsetfunctions.utf8Bytes((String)object);
        object = ByteBuffer.allocate(4);
        object.putInt(n2);
        object.position(2);
        ByteBuffer byteBuffer = ByteBuffer.allocate(2 + arrby.length);
        byteBuffer.put((ByteBuffer)object);
        byteBuffer.put(arrby);
        byteBuffer.rewind();
        this.setPayload(byteBuffer);
    }

    @Override
    public int getCloseCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.reason;
    }

    @Override
    public ByteBuffer getPayloadData() {
        if (this.code == 1005) {
            return emptybytebuffer;
        }
        return super.getPayloadData();
    }

    @Override
    public void setPayload(ByteBuffer byteBuffer) throws InvalidDataException {
        super.setPayload(byteBuffer);
        this.initCloseCode();
        this.initMessage();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("code: ");
        stringBuilder.append(this.code);
        return stringBuilder.toString();
    }
}
