/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.drafts;

import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.exceptions.LimitExedeedException;
import org.java_websocket.exceptions.NotSendableException;
import org.java_websocket.framing.CloseFrameBuilder;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.HandshakeBuilder;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.util.Base64;
import org.java_websocket.util.Charsetfunctions;

public class Draft_10
extends Draft {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Framedata fragmentedframe = null;
    private ByteBuffer incompleteframe;
    private final Random reuseableRandom = new Random();

    private byte fromOpcode(Framedata.Opcode opcode) {
        if (opcode == Framedata.Opcode.CONTINUOUS) {
            return 0;
        }
        if (opcode == Framedata.Opcode.TEXT) {
            return 1;
        }
        if (opcode == Framedata.Opcode.BINARY) {
            return 2;
        }
        if (opcode == Framedata.Opcode.CLOSING) {
            return 8;
        }
        if (opcode == Framedata.Opcode.PING) {
            return 9;
        }
        if (opcode == Framedata.Opcode.PONG) {
            return 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Don't know how to handle ");
        stringBuilder.append(opcode.toString());
        throw new RuntimeException(stringBuilder.toString());
    }

    private String generateFinalKey(String string) {
        string = string.trim();
        Object object = new StringBuilder();
        object.append(string);
        object.append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
        string = object.toString();
        try {
            object = MessageDigest.getInstance("SHA1");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
        return Base64.encodeBytes(object.digest(string.getBytes()));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int readVersion(Handshakedata object) {
        if ((object = object.getFieldValue("Sec-WebSocket-Version")).length() <= 0) return -1;
        try {
            return new Integer(object.trim());
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }

    private byte[] toByteArray(long l, int n) {
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby[i] = (byte)(l >>> 8 * n - 8 - 8 * i);
        }
        return arrby;
    }

    private Framedata.Opcode toOpcode(byte by) throws InvalidFrameException {
        switch (by) {
            default: {
                switch (by) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unknow optcode ");
                        stringBuilder.append(by);
                        throw new InvalidFrameException(stringBuilder.toString());
                    }
                    case 10: {
                        return Framedata.Opcode.PONG;
                    }
                    case 9: {
                        return Framedata.Opcode.PING;
                    }
                    case 8: 
                }
                return Framedata.Opcode.CLOSING;
            }
            case 2: {
                return Framedata.Opcode.BINARY;
            }
            case 1: {
                return Framedata.Opcode.TEXT;
            }
            case 0: 
        }
        return Framedata.Opcode.CONTINUOUS;
    }

    @Override
    public Draft.HandshakeState acceptHandshakeAsClient(ClientHandshake clientHandshake, ServerHandshake object) throws InvalidHandshakeException {
        if (clientHandshake.hasFieldValue("Sec-WebSocket-Key") && object.hasFieldValue("Sec-WebSocket-Accept")) {
            object = object.getFieldValue("Sec-WebSocket-Accept");
            if (this.generateFinalKey(clientHandshake.getFieldValue("Sec-WebSocket-Key")).equals(object)) {
                return Draft.HandshakeState.MATCHED;
            }
            return Draft.HandshakeState.NOT_MATCHED;
        }
        return Draft.HandshakeState.NOT_MATCHED;
    }

    @Override
    public Draft.HandshakeState acceptHandshakeAsServer(ClientHandshake clientHandshake) throws InvalidHandshakeException {
        int n = Draft_10.readVersion(clientHandshake);
        if (n != 7 && n != 8) {
            return Draft.HandshakeState.NOT_MATCHED;
        }
        if (this.basicAccept(clientHandshake)) {
            return Draft.HandshakeState.MATCHED;
        }
        return Draft.HandshakeState.NOT_MATCHED;
    }

    @Override
    public Draft copyInstance() {
        return new Draft_10();
    }

    @Override
    public ByteBuffer createBinaryFrame(Framedata object) {
        block12 : {
            int n;
            boolean bl;
            Object object2;
            ByteBuffer byteBuffer;
            int n2;
            block10 : {
                int n3;
                block11 : {
                    block9 : {
                        byteBuffer = object.getPayloadData();
                        object2 = this.role;
                        WebSocket.Role role = WebSocket.Role.CLIENT;
                        n2 = 0;
                        bl = object2 == role;
                        n = byteBuffer.remaining() <= 125 ? 1 : (byteBuffer.remaining() <= 65535 ? 2 : 8);
                        n3 = n > 1 ? n + 1 : n;
                        int n4 = bl ? 4 : 0;
                        object2 = ByteBuffer.allocate(n3 + 1 + n4 + byteBuffer.remaining());
                        byte by = this.fromOpcode(object.getOpcode());
                        boolean bl2 = object.isFin();
                        n3 = -128;
                        n4 = bl2 ? -128 : 0;
                        object2.put((byte)((byte)n4 | by));
                        object = this.toByteArray(byteBuffer.remaining(), n);
                        if (n != 1) break block9;
                        n = object[0];
                        if (!bl) {
                            n3 = 0;
                        }
                        object2.put((byte)(n | n3));
                        break block10;
                    }
                    if (n != 2) break block11;
                    if (!bl) {
                        n3 = 0;
                    }
                    object2.put((byte)(126 | n3));
                    object2.put((byte[])object);
                    break block10;
                }
                if (n != 8) break block12;
                if (!bl) {
                    n3 = 0;
                }
                object2.put((byte)(127 | n3));
                object2.put((byte[])object);
            }
            if (bl) {
                object = ByteBuffer.allocate(4);
                object.putInt(this.reuseableRandom.nextInt());
                object2.put(object.array());
                n = n2;
                while (byteBuffer.hasRemaining()) {
                    object2.put((byte)(byteBuffer.get() ^ object.get(n % 4)));
                    ++n;
                }
            } else {
                object2.put(byteBuffer);
            }
            object2.flip();
            return object2;
        }
        throw new RuntimeException("Size representation not supported/specified");
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
        FramedataImpl1 framedataImpl1 = new FramedataImpl1();
        try {
            framedataImpl1.setPayload(byteBuffer);
            framedataImpl1.setFin(true);
            framedataImpl1.setOptcode(Framedata.Opcode.BINARY);
            framedataImpl1.setTransferemasked(bl);
        }
        catch (InvalidDataException invalidDataException) {
            throw new NotSendableException(invalidDataException);
        }
        return Collections.singletonList(framedataImpl1);
    }

    @Override
    public Draft.CloseHandshakeType getCloseHandshakeType() {
        return Draft.CloseHandshakeType.TWOWAY;
    }

    @Override
    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder clientHandshakeBuilder) {
        clientHandshakeBuilder.put("Upgrade", "websocket");
        clientHandshakeBuilder.put("Connection", "Upgrade");
        clientHandshakeBuilder.put("Sec-WebSocket-Version", "8");
        byte[] arrby = new byte[16];
        this.reuseableRandom.nextBytes(arrby);
        clientHandshakeBuilder.put("Sec-WebSocket-Key", Base64.encodeBytes(arrby));
        return clientHandshakeBuilder;
    }

    @Override
    public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake object, ServerHandshakeBuilder serverHandshakeBuilder) throws InvalidHandshakeException {
        serverHandshakeBuilder.put("Upgrade", "websocket");
        serverHandshakeBuilder.put("Connection", object.getFieldValue("Connection"));
        serverHandshakeBuilder.setHttpStatusMessage("Switching Protocols");
        object = object.getFieldValue("Sec-WebSocket-Key");
        if (object == null) {
            throw new InvalidHandshakeException("missing Sec-WebSocket-Key");
        }
        serverHandshakeBuilder.put("Sec-WebSocket-Accept", this.generateFinalKey((String)object));
        return serverHandshakeBuilder;
    }

    @Override
    public void reset() {
        this.incompleteframe = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<Framedata> translateFrame(ByteBuffer byteBuffer) throws LimitExedeedException, InvalidDataException {
        LinkedList<Framedata> linkedList = new LinkedList<Framedata>();
        if (this.incompleteframe != null) {
            try {
                byteBuffer.mark();
                int n = byteBuffer.remaining();
                int n2 = this.incompleteframe.remaining();
                if (n2 > n) {
                    this.incompleteframe.put(byteBuffer.array(), byteBuffer.position(), n);
                    byteBuffer.position(byteBuffer.position() + n);
                    return Collections.emptyList();
                }
                this.incompleteframe.put(byteBuffer.array(), byteBuffer.position(), n2);
                byteBuffer.position(byteBuffer.position() + n2);
                linkedList.add(this.translateSingleFrame((ByteBuffer)this.incompleteframe.duplicate().position(0)));
                this.incompleteframe = null;
            }
            catch (IncompleteException incompleteException) {
                this.incompleteframe.limit();
                ByteBuffer byteBuffer2 = ByteBuffer.allocate(this.checkAlloc(incompleteException.getPreferedSize()));
                this.incompleteframe.rewind();
                byteBuffer2.put(this.incompleteframe);
                this.incompleteframe = byteBuffer2;
                return this.translateFrame(byteBuffer);
            }
        }
        while (byteBuffer.hasRemaining()) {
            byteBuffer.mark();
            try {
                linkedList.add(this.translateSingleFrame(byteBuffer));
            }
            catch (IncompleteException incompleteException) {
                byteBuffer.reset();
                this.incompleteframe = ByteBuffer.allocate(this.checkAlloc(incompleteException.getPreferedSize()));
                this.incompleteframe.put(byteBuffer);
                return linkedList;
            }
        }
        return linkedList;
    }

    public Framedata translateSingleFrame(ByteBuffer object) throws IncompleteException, InvalidDataException {
        block19 : {
            int n;
            Object object2;
            int n2;
            int n3;
            boolean bl;
            int n4;
            boolean bl2;
            Framedata.Opcode opcode;
            block18 : {
                n = object.remaining();
                n4 = 2;
                if (n < 2) {
                    throw new IncompleteException(2);
                }
                n2 = object.get();
                int n5 = 0;
                bl = n2 >> 8 != 0;
                n3 = (n2 & 127) >> 4;
                if (n3 != 0) {
                    object = new StringBuilder();
                    object.append("bad rsv ");
                    object.append(n3);
                    throw new InvalidFrameException(object.toString());
                }
                n3 = object.get();
                bl2 = (n3 & -128) != 0;
                n3 = (byte)(n3 & 127);
                opcode = this.toOpcode((byte)(n2 & 15));
                if (!(bl || opcode != Framedata.Opcode.PING && opcode != Framedata.Opcode.PONG && opcode != Framedata.Opcode.CLOSING)) {
                    throw new InvalidFrameException("control frames may no be fragmented");
                }
                if (n3 >= 0 && n3 <= 125) break block18;
                if (opcode == Framedata.Opcode.PING || opcode == Framedata.Opcode.PONG || opcode == Framedata.Opcode.CLOSING) break block19;
                if (n3 == 126) {
                    if (n < 4) {
                        throw new IncompleteException(4);
                    }
                    object2 = new byte[3];
                    object2[1] = object.get();
                    object2[2] = object.get();
                    n3 = new BigInteger((byte[])object2).intValue();
                    n4 = 4;
                } else {
                    n4 = 10;
                    if (n < 10) {
                        throw new IncompleteException(10);
                    }
                    object2 = new byte[8];
                    for (n3 = 0; n3 < 8; ++n3) {
                        object2[n3] = object.get();
                    }
                    long l = new BigInteger((byte[])object2).longValue();
                    if (l > Integer.MAX_VALUE) {
                        throw new LimitExedeedException("Payloadsize is to big...");
                    }
                    n3 = (int)l;
                }
            }
            n2 = bl2 ? 4 : 0;
            if (n < (n4 = n4 + n2 + n3)) {
                throw new IncompleteException(n4);
            }
            object2 = ByteBuffer.allocate(this.checkAlloc(n3));
            if (bl2) {
                byte[] arrby = new byte[4];
                object.get(arrby);
                for (n4 = n5; n4 < n3; ++n4) {
                    object2.put((byte)(object.get() ^ arrby[n4 % 4]));
                }
            } else {
                object2.put(object.array(), object.position(), object2.limit());
                object.position(object.position() + object2.limit());
            }
            if (opcode == Framedata.Opcode.CLOSING) {
                object = new CloseFrameBuilder();
            } else {
                object = new FramedataImpl1();
                object.setFin(bl);
                object.setOptcode(opcode);
            }
            object2.flip();
            object.setPayload((ByteBuffer)object2);
            return object;
        }
        throw new InvalidFrameException("more than 125 octets");
    }

    private class IncompleteException
    extends Throwable {
        private static final long serialVersionUID = 7330519489840500997L;
        private int preferedsize;

        public IncompleteException(int n) {
            this.preferedsize = n;
        }

        public int getPreferedSize() {
            return this.preferedsize;
        }
    }

}
