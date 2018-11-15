/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.drafts;

import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_75;
import org.java_websocket.exceptions.IncompleteHandshakeException;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.CloseFrameBuilder;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.HandshakeBuilder;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;

public class Draft_76
extends Draft_75 {
    private static final byte[] closehandshake = new byte[]{-1, 0};
    private boolean failed = false;
    private final Random reuseableRandom = new Random();

    public static byte[] createChallenge(String object, String arrby, byte[] arrby2) throws InvalidHandshakeException {
        object = Draft_76.getPart((String)object);
        arrby = Draft_76.getPart((String)arrby);
        byte by = object[0];
        byte by2 = object[1];
        byte by3 = object[2];
        byte by4 = object[3];
        byte by5 = arrby[0];
        byte by6 = arrby[1];
        byte by7 = arrby[2];
        byte by8 = arrby[3];
        byte by9 = arrby2[0];
        byte by10 = arrby2[1];
        byte by11 = arrby2[2];
        byte by12 = arrby2[3];
        byte by13 = arrby2[4];
        byte by14 = arrby2[5];
        byte by15 = arrby2[6];
        byte by16 = arrby2[7];
        try {
            object = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
        return object.digest(new byte[]{by, by2, by3, by4, by5, by6, by7, by8, by9, by10, by11, by12, by13, by14, by15, by16});
    }

    private static String generateKey() {
        int n;
        String string;
        Random random = new Random();
        long l = random.nextInt(12) + 1;
        String string2 = Long.toString((long)(random.nextInt(Math.abs(new Long(0xFFFFFFFFL / l).intValue())) + 1) * l);
        int n2 = random.nextInt(12);
        int n3 = 0;
        int n4 = 0;
        do {
            char c;
            n = n3;
            string = string2;
            if (n4 >= n2 + 1) break;
            n = Math.abs(random.nextInt(string2.length()));
            char c2 = c = (char)(random.nextInt(95) + 33);
            if (c >= '0') {
                c2 = c;
                if (c <= '9') {
                    c2 = (char)(c - 15);
                }
            }
            string2 = new StringBuilder(string2).insert(n, c2).toString();
            ++n4;
        } while (true);
        while ((long)n < l) {
            n4 = Math.abs(random.nextInt(string.length() - 1) + 1);
            string = new StringBuilder(string).insert(n4, " ").toString();
            ++n;
        }
        return string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static byte[] getPart(String string) throws InvalidHandshakeException {
        long l;
        try {
            l = Long.parseLong(string.replaceAll("[^0-9]", ""));
            long l2 = string.split(" ").length - 1;
            if (l2 == 0L) {
                throw new InvalidHandshakeException("invalid Sec-WebSocket-Key (/key2/)");
            }
            l = new Long(l / l2);
        }
        catch (NumberFormatException numberFormatException) {
            throw new InvalidHandshakeException("invalid Sec-WebSocket-Key (/key1/ or /key2/)");
        }
        byte by = (byte)(l >> 24);
        byte by2 = (byte)(l << 8 >> 24);
        byte by3 = (byte)(l << 16 >> 24);
        byte by4 = (byte)(l << 24 >> 24);
        return new byte[]{by, by2, by3, by4};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Draft.HandshakeState acceptHandshakeAsClient(ClientHandshake object, ServerHandshake arrby) {
        if (this.failed) {
            return Draft.HandshakeState.NOT_MATCHED;
        }
        try {
            if (!arrby.getFieldValue("Sec-WebSocket-Origin").equals(object.getFieldValue("Origin"))) return Draft.HandshakeState.NOT_MATCHED;
            if (!this.basicAccept((Handshakedata)arrby)) return Draft.HandshakeState.NOT_MATCHED;
            if ((arrby = arrby.getContent()) == null) throw new IncompleteHandshakeException();
        }
        catch (InvalidHandshakeException invalidHandshakeException) {
            throw new RuntimeException("bad handshakerequest", invalidHandshakeException);
        }
        if (arrby.length == 0) throw new IncompleteHandshakeException();
        if (!Arrays.equals(arrby, Draft_76.createChallenge(object.getFieldValue("Sec-WebSocket-Key1"), object.getFieldValue("Sec-WebSocket-Key2"), object.getContent()))) return Draft.HandshakeState.NOT_MATCHED;
        return Draft.HandshakeState.MATCHED;
    }

    @Override
    public Draft.HandshakeState acceptHandshakeAsServer(ClientHandshake clientHandshake) {
        if (clientHandshake.getFieldValue("Upgrade").equals("WebSocket") && clientHandshake.getFieldValue("Connection").contains("Upgrade") && clientHandshake.getFieldValue("Sec-WebSocket-Key1").length() > 0 && !clientHandshake.getFieldValue("Sec-WebSocket-Key2").isEmpty() && clientHandshake.hasFieldValue("Origin")) {
            return Draft.HandshakeState.MATCHED;
        }
        return Draft.HandshakeState.NOT_MATCHED;
    }

    @Override
    public Draft copyInstance() {
        return new Draft_76();
    }

    @Override
    public ByteBuffer createBinaryFrame(Framedata framedata) {
        if (framedata.getOpcode() == Framedata.Opcode.CLOSING) {
            return ByteBuffer.wrap(closehandshake);
        }
        return super.createBinaryFrame(framedata);
    }

    @Override
    public Draft.CloseHandshakeType getCloseHandshakeType() {
        return Draft.CloseHandshakeType.ONEWAY;
    }

    @Override
    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder clientHandshakeBuilder) {
        Object object;
        clientHandshakeBuilder.put("Upgrade", "WebSocket");
        clientHandshakeBuilder.put("Connection", "Upgrade");
        clientHandshakeBuilder.put("Sec-WebSocket-Key1", Draft_76.generateKey());
        clientHandshakeBuilder.put("Sec-WebSocket-Key2", Draft_76.generateKey());
        if (!clientHandshakeBuilder.hasFieldValue("Origin")) {
            object = new StringBuilder();
            object.append("random");
            object.append(this.reuseableRandom.nextInt());
            clientHandshakeBuilder.put("Origin", object.toString());
        }
        object = new byte[8];
        this.reuseableRandom.nextBytes((byte[])object);
        clientHandshakeBuilder.setContent((byte[])object);
        return clientHandshakeBuilder;
    }

    @Override
    public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake arrby, ServerHandshakeBuilder serverHandshakeBuilder) throws InvalidHandshakeException {
        serverHandshakeBuilder.setHttpStatusMessage("WebSocket Protocol Handshake");
        serverHandshakeBuilder.put("Upgrade", "WebSocket");
        serverHandshakeBuilder.put("Connection", arrby.getFieldValue("Connection"));
        serverHandshakeBuilder.put("Sec-WebSocket-Origin", arrby.getFieldValue("Origin"));
        CharSequence charSequence = new StringBuilder();
        charSequence.append("ws://");
        charSequence.append(arrby.getFieldValue("Host"));
        charSequence.append(arrby.getResourceDescriptor());
        serverHandshakeBuilder.put("Sec-WebSocket-Location", charSequence.toString());
        charSequence = arrby.getFieldValue("Sec-WebSocket-Key1");
        String string = arrby.getFieldValue("Sec-WebSocket-Key2");
        arrby = arrby.getContent();
        if (charSequence != null && string != null && arrby != null && arrby.length == 8) {
            serverHandshakeBuilder.setContent(Draft_76.createChallenge((String)charSequence, string, arrby));
            return serverHandshakeBuilder;
        }
        throw new InvalidHandshakeException("Bad keys");
    }

    @Override
    public List<Framedata> translateFrame(ByteBuffer byteBuffer) throws InvalidDataException {
        byteBuffer.mark();
        List list = super.translateRegularFrame(byteBuffer);
        if (list == null) {
            byteBuffer.reset();
            list = this.readyframes;
            this.readingState = true;
            if (this.currentFrame == null) {
                this.currentFrame = ByteBuffer.allocate(2);
                if (byteBuffer.remaining() > this.currentFrame.remaining()) {
                    throw new InvalidFrameException();
                }
                this.currentFrame.put(byteBuffer);
                if (!this.currentFrame.hasRemaining()) {
                    if (Arrays.equals(this.currentFrame.array(), closehandshake)) {
                        list.add(new CloseFrameBuilder(1000));
                        return list;
                    }
                    throw new InvalidFrameException();
                }
                this.readyframes = new LinkedList();
                return list;
            }
            throw new InvalidFrameException();
        }
        return list;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Handshakedata translateHandshake(ByteBuffer byteBuffer) throws InvalidHandshakeException {
        HandshakeBuilder handshakeBuilder = Draft_76.translateHandshakeHttp(byteBuffer, this.role);
        if (!handshakeBuilder.hasFieldValue("Sec-WebSocket-Key1") && this.role != WebSocket.Role.CLIENT || handshakeBuilder.hasFieldValue("Sec-WebSocket-Version")) return handshakeBuilder;
        int n = this.role == WebSocket.Role.SERVER ? 8 : 16;
        byte[] arrby = new byte[n];
        try {
            byteBuffer.get(arrby);
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            throw new IncompleteHandshakeException(byteBuffer.capacity() + 16);
        }
        handshakeBuilder.setContent(arrby);
        return handshakeBuilder;
    }
}
