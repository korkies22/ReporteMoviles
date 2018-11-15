/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.drafts;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.exceptions.LimitExedeedException;
import org.java_websocket.exceptions.NotSendableException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.HandshakeBuilder;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.util.Charsetfunctions;

public class Draft_75
extends Draft {
    public static final byte CR = 13;
    public static final byte END_OF_FRAME = -1;
    public static final byte LF = 10;
    public static final byte START_OF_FRAME = 0;
    protected ByteBuffer currentFrame;
    protected boolean readingState = false;
    protected List<Framedata> readyframes = new LinkedList<Framedata>();
    private final Random reuseableRandom = new Random();

    @Override
    public Draft.HandshakeState acceptHandshakeAsClient(ClientHandshake clientHandshake, ServerHandshake serverHandshake) {
        if (clientHandshake.getFieldValue("WebSocket-Origin").equals(serverHandshake.getFieldValue("Origin")) && this.basicAccept(serverHandshake)) {
            return Draft.HandshakeState.MATCHED;
        }
        return Draft.HandshakeState.NOT_MATCHED;
    }

    @Override
    public Draft.HandshakeState acceptHandshakeAsServer(ClientHandshake clientHandshake) {
        if (clientHandshake.hasFieldValue("Origin") && this.basicAccept(clientHandshake)) {
            return Draft.HandshakeState.MATCHED;
        }
        return Draft.HandshakeState.NOT_MATCHED;
    }

    @Override
    public Draft copyInstance() {
        return new Draft_75();
    }

    @Override
    public ByteBuffer createBinaryFrame(Framedata object) {
        if (object.getOpcode() != Framedata.Opcode.TEXT) {
            throw new RuntimeException("only text frames supported");
        }
        object = object.getPayloadData();
        ByteBuffer byteBuffer = ByteBuffer.allocate(object.remaining() + 2);
        byteBuffer.put((byte)0);
        object.mark();
        byteBuffer.put((ByteBuffer)object);
        object.reset();
        byteBuffer.put((byte)-1);
        byteBuffer.flip();
        return byteBuffer;
    }

    public ByteBuffer createBuffer() {
        return ByteBuffer.allocate(INITIAL_FAMESIZE);
    }

    @Override
    public List<Framedata> createFrames(String string, boolean bl) {
        FramedataImpl1 framedataImpl1 = new FramedataImpl1();
        try {
            framedataImpl1.setPayload(ByteBuffer.wrap(Charsetfunctions.utf8Bytes(string)));
            framedataImpl1.setFin(true);
            framedataImpl1.setOptcode(Framedata.Opcode.TEXT);
            framedataImpl1.setTransferemasked(bl);
        }
        catch (InvalidDataException invalidDataException) {
            throw new NotSendableException(invalidDataException);
        }
        return Collections.singletonList(framedataImpl1);
    }

    @Override
    public List<Framedata> createFrames(ByteBuffer byteBuffer, boolean bl) {
        throw new RuntimeException("not yet implemented");
    }

    @Override
    public Draft.CloseHandshakeType getCloseHandshakeType() {
        return Draft.CloseHandshakeType.NONE;
    }

    public ByteBuffer increaseBuffer(ByteBuffer byteBuffer) throws LimitExedeedException, InvalidDataException {
        byteBuffer.flip();
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(this.checkAlloc(byteBuffer.capacity() * 2));
        byteBuffer2.put(byteBuffer);
        return byteBuffer2;
    }

    @Override
    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder clientHandshakeBuilder) throws InvalidHandshakeException {
        clientHandshakeBuilder.put("Upgrade", "WebSocket");
        clientHandshakeBuilder.put("Connection", "Upgrade");
        if (!clientHandshakeBuilder.hasFieldValue("Origin")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("random");
            stringBuilder.append(this.reuseableRandom.nextInt());
            clientHandshakeBuilder.put("Origin", stringBuilder.toString());
        }
        return clientHandshakeBuilder;
    }

    @Override
    public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake clientHandshake, ServerHandshakeBuilder serverHandshakeBuilder) throws InvalidHandshakeException {
        serverHandshakeBuilder.setHttpStatusMessage("Web Socket Protocol Handshake");
        serverHandshakeBuilder.put("Upgrade", "WebSocket");
        serverHandshakeBuilder.put("Connection", clientHandshake.getFieldValue("Connection"));
        serverHandshakeBuilder.put("WebSocket-Origin", clientHandshake.getFieldValue("Origin"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ws://");
        stringBuilder.append(clientHandshake.getFieldValue("Host"));
        stringBuilder.append(clientHandshake.getResourceDescriptor());
        serverHandshakeBuilder.put("WebSocket-Location", stringBuilder.toString());
        return serverHandshakeBuilder;
    }

    @Override
    public void reset() {
        this.readingState = false;
        this.currentFrame = null;
    }

    @Override
    public List<Framedata> translateFrame(ByteBuffer object) throws InvalidDataException {
        if ((object = this.translateRegularFrame((ByteBuffer)object)) == null) {
            throw new InvalidDataException(1002);
        }
        return object;
    }

    protected List<Framedata> translateRegularFrame(ByteBuffer object) throws InvalidDataException {
        while (object.hasRemaining()) {
            byte by = object.get();
            if (by == 0) {
                if (this.readingState) {
                    throw new InvalidFrameException("unexpected START_OF_FRAME");
                }
                this.readingState = true;
                continue;
            }
            if (by == -1) {
                if (!this.readingState) {
                    throw new InvalidFrameException("unexpected END_OF_FRAME");
                }
                if (this.currentFrame != null) {
                    this.currentFrame.flip();
                    FramedataImpl1 framedataImpl1 = new FramedataImpl1();
                    framedataImpl1.setPayload(this.currentFrame);
                    framedataImpl1.setFin(true);
                    framedataImpl1.setOptcode(Framedata.Opcode.TEXT);
                    this.readyframes.add(framedataImpl1);
                    this.currentFrame = null;
                    object.mark();
                }
                this.readingState = false;
                continue;
            }
            if (this.readingState) {
                if (this.currentFrame == null) {
                    this.currentFrame = this.createBuffer();
                } else if (!this.currentFrame.hasRemaining()) {
                    this.currentFrame = this.increaseBuffer(this.currentFrame);
                }
                this.currentFrame.put(by);
                continue;
            }
            return null;
        }
        object = this.readyframes;
        this.readyframes = new LinkedList<Framedata>();
        return object;
    }
}
