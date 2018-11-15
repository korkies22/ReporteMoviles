/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.drafts;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.IncompleteHandshakeException;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.exceptions.LimitExedeedException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.HandshakeBuilder;
import org.java_websocket.handshake.HandshakeImpl1Client;
import org.java_websocket.handshake.HandshakeImpl1Server;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.util.Charsetfunctions;

public abstract class Draft {
    public static final byte[] FLASH_POLICY_REQUEST = Charsetfunctions.utf8Bytes("<policy-file-request/>\u0000");
    public static int INITIAL_FAMESIZE = 64;
    public static int MAX_FAME_SIZE = 1000;
    protected Framedata.Opcode continuousFrameType = null;
    protected WebSocket.Role role = null;

    public static ByteBuffer readLine(ByteBuffer byteBuffer) {
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(byteBuffer.remaining());
        int n = 48;
        while (byteBuffer.hasRemaining()) {
            byte by = byteBuffer.get();
            byteBuffer2.put(by);
            if (n == 13 && by == 10) {
                byteBuffer2.limit(byteBuffer2.position() - 2);
                byteBuffer2.position(0);
                return byteBuffer2;
            }
            n = by;
        }
        byteBuffer.position(byteBuffer.position() - byteBuffer2.position());
        return null;
    }

    public static String readStringLine(ByteBuffer byteBuffer) {
        if ((byteBuffer = Draft.readLine(byteBuffer)) == null) {
            return null;
        }
        return Charsetfunctions.stringAscii(byteBuffer.array(), 0, byteBuffer.limit());
    }

    public static HandshakeBuilder translateHandshakeHttp(ByteBuffer byteBuffer, WebSocket.Role object) throws InvalidHandshakeException, IncompleteHandshakeException {
        Object object2 = Draft.readStringLine(byteBuffer);
        if (object2 == null) {
            throw new IncompleteHandshakeException(byteBuffer.capacity() + 128);
        }
        if (((String[])(object2 = object2.split(" ", 3))).length != 3) {
            throw new InvalidHandshakeException();
        }
        if (object == WebSocket.Role.CLIENT) {
            object = new HandshakeImpl1Server();
            ServerHandshakeBuilder serverHandshakeBuilder = (ServerHandshakeBuilder)object;
            serverHandshakeBuilder.setHttpStatus(Short.parseShort(object2[1]));
            serverHandshakeBuilder.setHttpStatusMessage((String)object2[2]);
        } else {
            object = new HandshakeImpl1Client();
            object.setResourceDescriptor(object2[1]);
        }
        object2 = Draft.readStringLine(byteBuffer);
        while (object2 != null && object2.length() > 0) {
            if (((Object)(object2 = object2.split(":", 2))).length != 2) {
                throw new InvalidHandshakeException("not an http header");
            }
            object.put((String)object2[0], object2[1].replaceFirst("^ +", ""));
            object2 = Draft.readStringLine(byteBuffer);
        }
        if (object2 == null) {
            throw new IncompleteHandshakeException();
        }
        return object;
    }

    public abstract HandshakeState acceptHandshakeAsClient(ClientHandshake var1, ServerHandshake var2) throws InvalidHandshakeException;

    public abstract HandshakeState acceptHandshakeAsServer(ClientHandshake var1) throws InvalidHandshakeException;

    protected boolean basicAccept(Handshakedata handshakedata) {
        if (handshakedata.getFieldValue("Upgrade").equalsIgnoreCase("websocket") && handshakedata.getFieldValue("Connection").toLowerCase(Locale.ENGLISH).contains("upgrade")) {
            return true;
        }
        return false;
    }

    public int checkAlloc(int n) throws LimitExedeedException, InvalidDataException {
        if (n < 0) {
            throw new InvalidDataException(1002, "Negative count");
        }
        return n;
    }

    public List<Framedata> continuousFrame(Framedata.Opcode opcode, ByteBuffer byteBuffer, boolean bl) {
        if (opcode != Framedata.Opcode.BINARY && opcode != Framedata.Opcode.TEXT && opcode != Framedata.Opcode.TEXT) {
            throw new IllegalArgumentException("Only Opcode.BINARY or  Opcode.TEXT are allowed");
        }
        this.continuousFrameType = this.continuousFrameType != null ? Framedata.Opcode.CONTINUOUS : opcode;
        FramedataImpl1 framedataImpl1 = new FramedataImpl1(this.continuousFrameType);
        try {
            framedataImpl1.setPayload(byteBuffer);
            framedataImpl1.setFin(bl);
            this.continuousFrameType = bl ? null : opcode;
        }
        catch (InvalidDataException invalidDataException) {
            throw new RuntimeException(invalidDataException);
        }
        return Collections.singletonList(framedataImpl1);
    }

    public abstract Draft copyInstance();

    public abstract ByteBuffer createBinaryFrame(Framedata var1);

    public abstract List<Framedata> createFrames(String var1, boolean var2);

    public abstract List<Framedata> createFrames(ByteBuffer var1, boolean var2);

    public List<ByteBuffer> createHandshake(Handshakedata handshakedata, WebSocket.Role role) {
        return this.createHandshake(handshakedata, role, true);
    }

    public List<ByteBuffer> createHandshake(Handshakedata object, WebSocket.Role object2, boolean bl) {
        block7 : {
            Object object3;
            block6 : {
                block5 : {
                    object2 = new StringBuilder(100);
                    if (!(object instanceof ClientHandshake)) break block5;
                    object2.append("GET ");
                    object2.append(((ClientHandshake)object).getResourceDescriptor());
                    object2.append(" HTTP/1.1");
                    break block6;
                }
                if (!(object instanceof ServerHandshake)) break block7;
                object3 = new StringBuilder();
                object3.append("HTTP/1.1 101 ");
                object3.append(((ServerHandshake)object).getHttpStatusMessage());
                object2.append(object3.toString());
            }
            object2.append("\r\n");
            object3 = object.iterateHttpFields();
            while (object3.hasNext()) {
                String string = (String)object3.next();
                String string2 = object.getFieldValue(string);
                object2.append(string);
                object2.append(": ");
                object2.append(string2);
                object2.append("\r\n");
            }
            object2.append("\r\n");
            object2 = Charsetfunctions.asciiBytes(object2.toString());
            object = bl ? object.getContent() : null;
            int n = object == null ? 0 : ((byte[])object).length;
            object3 = ByteBuffer.allocate(n + ((Object)object2).length);
            object3.put((byte[])object2);
            if (object != null) {
                object3.put((byte[])object);
            }
            object3.flip();
            return Collections.singletonList(object3);
        }
        throw new RuntimeException("unknow role");
    }

    public abstract CloseHandshakeType getCloseHandshakeType();

    public WebSocket.Role getRole() {
        return this.role;
    }

    public abstract ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder var1) throws InvalidHandshakeException;

    public abstract HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake var1, ServerHandshakeBuilder var2) throws InvalidHandshakeException;

    public abstract void reset();

    public void setParseMode(WebSocket.Role role) {
        this.role = role;
    }

    public abstract List<Framedata> translateFrame(ByteBuffer var1) throws InvalidDataException;

    public Handshakedata translateHandshake(ByteBuffer byteBuffer) throws InvalidHandshakeException {
        return Draft.translateHandshakeHttp(byteBuffer, this.role);
    }

    public static enum CloseHandshakeType {
        NONE,
        ONEWAY,
        TWOWAY;
        

        private CloseHandshakeType() {
        }
    }

    public static enum HandshakeState {
        MATCHED,
        NOT_MATCHED;
        

        private HandshakeState() {
        }
    }

}
