/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.drafts.Draft_75;
import org.java_websocket.drafts.Draft_76;
import org.java_websocket.exceptions.IncompleteHandshakeException;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.framing.CloseFrameBuilder;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.HandshakeBuilder;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.util.Charsetfunctions;

public class WebSocketImpl
implements WebSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static boolean DEBUG = false;
    public static int RCVBUF = 16384;
    public static final List<Draft> defaultdraftlist = new ArrayList<Draft>(4);
    public ByteChannel channel;
    private Integer closecode = null;
    private Boolean closedremotely = null;
    private String closemessage = null;
    private Framedata.Opcode current_continuous_frame_opcode = null;
    private Draft draft = null;
    private volatile boolean flushandclosestate = false;
    private ClientHandshake handshakerequest = null;
    public final BlockingQueue<ByteBuffer> inQueue;
    public SelectionKey key;
    private List<Draft> knownDrafts;
    public final BlockingQueue<ByteBuffer> outQueue;
    private WebSocket.READYSTATE readystate = WebSocket.READYSTATE.NOT_YET_CONNECTED;
    private String resourceDescriptor = null;
    private WebSocket.Role role;
    private ByteBuffer tmpHandshakeBytes = ByteBuffer.allocate(0);
    public volatile WebSocketServer.WebSocketWorker workerThread;
    private final WebSocketListener wsl;

    static {
        defaultdraftlist.add(new Draft_17());
        defaultdraftlist.add(new Draft_10());
        defaultdraftlist.add(new Draft_76());
        defaultdraftlist.add(new Draft_75());
    }

    public WebSocketImpl(WebSocketListener webSocketListener, List<Draft> list) {
        this(webSocketListener, (Draft)null);
        this.role = WebSocket.Role.SERVER;
        if (list != null && !list.isEmpty()) {
            this.knownDrafts = list;
            return;
        }
        this.knownDrafts = defaultdraftlist;
    }

    @Deprecated
    public WebSocketImpl(WebSocketListener webSocketListener, List<Draft> list, Socket socket) {
        this(webSocketListener, list);
    }

    public WebSocketImpl(WebSocketListener webSocketListener, Draft draft) {
        if (webSocketListener != null && (draft != null || this.role != WebSocket.Role.SERVER)) {
            this.outQueue = new LinkedBlockingQueue<ByteBuffer>();
            this.inQueue = new LinkedBlockingQueue<ByteBuffer>();
            this.wsl = webSocketListener;
            this.role = WebSocket.Role.CLIENT;
            if (draft != null) {
                this.draft = draft.copyInstance();
            }
            return;
        }
        throw new IllegalArgumentException("parameters must not be null");
    }

    @Deprecated
    public WebSocketImpl(WebSocketListener webSocketListener, Draft draft, Socket socket) {
        this(webSocketListener, draft);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void close(int n, String string, boolean bl) {
        block14 : {
            block12 : {
                block13 : {
                    if (this.readystate == WebSocket.READYSTATE.CLOSING) return;
                    if (this.readystate == WebSocket.READYSTATE.CLOSED) return;
                    if (this.readystate != WebSocket.READYSTATE.OPEN) break block12;
                    if (n == 1006) {
                        this.readystate = WebSocket.READYSTATE.CLOSING;
                        this.flushAndClose(n, string, false);
                        return;
                    }
                    if (this.draft.getCloseHandshakeType() != Draft.CloseHandshakeType.NONE) {
                        InvalidDataException invalidDataException2222;
                        block11 : {
                            block10 : {
                                if (!bl) {
                                    try {
                                        this.wsl.onWebsocketCloseInitiated(this, n, string);
                                        break block10;
                                    }
                                    catch (InvalidDataException invalidDataException2222) {
                                        break block11;
                                    }
                                    catch (RuntimeException runtimeException) {
                                        this.wsl.onWebsocketError(this, runtimeException);
                                    }
                                }
                            }
                            this.sendFrame(new CloseFrameBuilder(n, string));
                            break block13;
                        }
                        this.wsl.onWebsocketError(this, invalidDataException2222);
                        this.flushAndClose(1006, "generated frame is invalid", false);
                    }
                }
                this.flushAndClose(n, string, bl);
                break block14;
            }
            if (n == -3) {
                this.flushAndClose(-3, string, true);
            } else {
                this.flushAndClose(-1, string, false);
            }
        }
        if (n == 1002) {
            this.flushAndClose(n, string, bl);
        }
        this.readystate = WebSocket.READYSTATE.CLOSING;
        this.tmpHandshakeBytes = null;
    }

    /*
     * Exception decompiling
     */
    private void decodeFrames(ByteBuffer var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 11[WHILELOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private boolean decodeHandshake(ByteBuffer var1_1) {
        block31 : {
            block32 : {
                block30 : {
                    if (this.tmpHandshakeBytes.capacity() == 0) {
                        var4_2 = var1_1;
                    } else {
                        if (this.tmpHandshakeBytes.remaining() < var1_1.remaining()) {
                            var4_2 = ByteBuffer.allocate(this.tmpHandshakeBytes.capacity() + var1_1.remaining());
                            this.tmpHandshakeBytes.flip();
                            var4_2.put(this.tmpHandshakeBytes);
                            this.tmpHandshakeBytes = var4_2;
                        }
                        this.tmpHandshakeBytes.put(var1_1);
                        this.tmpHandshakeBytes.flip();
                        var4_2 = this.tmpHandshakeBytes;
                    }
                    var4_2.mark();
                    if (this.draft != null || (var5_3 = this.isFlashEdgeCase(var4_2)) != (var6_9 /* !! */  = Draft.HandshakeState.MATCHED)) ** GOTO lbl24
                    this.write(ByteBuffer.wrap(Charsetfunctions.utf8Bytes(this.wsl.getFlashPolicy(this))));
                    this.close(-3, "");
                    return false;
lbl20: // 1 sources:
                    do {
                        this.close(1006, "remote peer closed connection before flashpolicy could be transmitted", true);
                        return false;
                        break;
                    } while (true);
lbl24: // 2 sources:
                    if (this.role != WebSocket.Role.SERVER) ** GOTO lbl66
                    if (this.draft != null) ** GOTO lbl57
                    var5_3 = this.knownDrafts.iterator();
lbl27: // 3 sources:
                    if (!var5_3.hasNext()) break block30;
                    var6_9 /* !! */  = ((Draft)var5_3.next()).copyInstance();
                    var6_9 /* !! */ .setParseMode(this.role);
                    var4_2.reset();
                    var7_13 = var6_9 /* !! */ .translateHandshake(var4_2);
                    if (!(var7_13 instanceof ClientHandshake)) {
                        this.flushAndClose(1002, "wrong http function", false);
                        return false;
                    }
                    if (var6_9 /* !! */ .acceptHandshakeAsServer((ClientHandshake)(var7_13 = (ClientHandshake)var7_13)) != Draft.HandshakeState.MATCHED) ** GOTO lbl27
                    this.resourceDescriptor = var7_13.getResourceDescriptor();
                    try {
                        var8_15 = this.wsl.onWebsocketHandshakeReceivedAsServer(this, (Draft)var6_9 /* !! */ , (ClientHandshake)var7_13);
                    }
                    catch (RuntimeException var6_10) {
                        this.wsl.onWebsocketError(this, var6_10);
                        this.flushAndClose(-1, var6_10.getMessage(), false);
                        return false;
                    }
                    catch (InvalidDataException var6_11) {
                        this.flushAndClose(var6_11.getCloseCode(), var6_11.getMessage(), false);
                        return false;
                    }
                    this.write(var6_9 /* !! */ .createHandshake(var6_9 /* !! */ .postProcessHandshakeResponseAsServer((ClientHandshake)var7_13, var8_15), this.role));
                    this.draft = var6_9 /* !! */ ;
                    this.open(var7_13);
                    return true;
                }
                if (this.draft == null) {
                    this.close(1002, "no draft matches");
                    return false;
                }
                break block31;
lbl57: // 1 sources:
                var5_3 = this.draft.translateHandshake(var4_2);
                if (!(var5_3 instanceof ClientHandshake)) {
                    this.flushAndClose(1002, "wrong http function", false);
                    return false;
                }
                if (this.draft.acceptHandshakeAsServer((ClientHandshake)(var5_3 = (ClientHandshake)var5_3)) == Draft.HandshakeState.MATCHED) {
                    this.open((Handshakedata)var5_3);
                    return true;
                }
                this.close(1002, "the handshake did finaly not match");
                return false;
lbl66: // 1 sources:
                if (this.role != WebSocket.Role.CLIENT) break block32;
                this.draft.setParseMode(this.role);
                var5_3 = this.draft.translateHandshake(var4_2);
                if (!(var5_3 instanceof ServerHandshake)) {
                    this.flushAndClose(1002, "wrong http function", false);
                    return false;
                }
                var6_9 /* !! */  = this.draft.acceptHandshakeAsClient(this.handshakerequest, (ServerHandshake)(var5_3 = (ServerHandshake)var5_3));
                if (var6_9 /* !! */  != (var7_14 = Draft.HandshakeState.MATCHED)) ** GOTO lbl87
                try {
                    try {
                        this.wsl.onWebsocketHandshakeReceivedAsClient(this, this.handshakerequest, (ServerHandshake)var5_3);
                    }
                    catch (RuntimeException var5_4) {
                        this.wsl.onWebsocketError(this, var5_4);
                        this.flushAndClose(-1, var5_4.getMessage(), false);
                        return false;
                    }
                    catch (InvalidDataException var5_5) {
                        this.flushAndClose(var5_5.getCloseCode(), var5_5.getMessage(), false);
                        return false;
                    }
                    this.open((Handshakedata)var5_3);
                    return true;
lbl87: // 1 sources:
                    var5_3 = new StringBuilder();
                    var5_3.append("draft ");
                    var5_3.append(this.draft);
                    var5_3.append(" refuses handshake");
                    this.close(1002, var5_3.toString());
                    return false;
                    {
                        catch (InvalidHandshakeException var5_6) {
                            this.close(var5_6);
                            return false;
                        }
                    }
                }
                catch (IncompleteHandshakeException var5_7) {
                    if (this.tmpHandshakeBytes.capacity() == 0) {
                        var4_2.reset();
                        var2_17 = var3_16 = var5_7.getPreferedSize();
                        if (var3_16 == 0) {
                            var2_17 = var4_2.capacity() + 16;
                        }
                        this.tmpHandshakeBytes = ByteBuffer.allocate(var2_17);
                        this.tmpHandshakeBytes.put(var1_1);
                        return false;
                    }
                    this.tmpHandshakeBytes.position(this.tmpHandshakeBytes.limit());
                    this.tmpHandshakeBytes.limit(this.tmpHandshakeBytes.capacity());
                }
            }
            return false;
            {
                catch (InvalidDataException var5_8) {
                    ** continue;
                }
                catch (InvalidHandshakeException var6_12) {
                    ** GOTO lbl27
                }
            }
        }
        return false;
    }

    private Draft.HandshakeState isFlashEdgeCase(ByteBuffer byteBuffer) throws IncompleteHandshakeException {
        byteBuffer.mark();
        if (byteBuffer.limit() > Draft.FLASH_POLICY_REQUEST.length) {
            return Draft.HandshakeState.NOT_MATCHED;
        }
        if (byteBuffer.limit() < Draft.FLASH_POLICY_REQUEST.length) {
            throw new IncompleteHandshakeException(Draft.FLASH_POLICY_REQUEST.length);
        }
        int n = 0;
        while (byteBuffer.hasRemaining()) {
            if (Draft.FLASH_POLICY_REQUEST[n] != byteBuffer.get()) {
                byteBuffer.reset();
                return Draft.HandshakeState.NOT_MATCHED;
            }
            ++n;
        }
        return Draft.HandshakeState.MATCHED;
    }

    private void open(Handshakedata handshakedata) {
        if (DEBUG) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("open using draft: ");
            stringBuilder.append(this.draft.getClass().getSimpleName());
            printStream.println(stringBuilder.toString());
        }
        this.readystate = WebSocket.READYSTATE.OPEN;
        try {
            this.wsl.onWebsocketOpen(this, handshakedata);
            return;
        }
        catch (RuntimeException runtimeException) {
            this.wsl.onWebsocketError(this, runtimeException);
            return;
        }
    }

    private void send(Collection<Framedata> object) {
        if (!this.isOpen()) {
            throw new WebsocketNotConnectedException();
        }
        object = object.iterator();
        while (object.hasNext()) {
            this.sendFrame((Framedata)object.next());
        }
    }

    private void write(ByteBuffer byteBuffer) {
        if (DEBUG) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("write(");
            stringBuilder.append(byteBuffer.remaining());
            stringBuilder.append("): {");
            String string = byteBuffer.remaining() > 1000 ? "too big to display" : new String(byteBuffer.array());
            stringBuilder.append(string);
            stringBuilder.append("}");
            printStream.println(stringBuilder.toString());
        }
        this.outQueue.add(byteBuffer);
        this.wsl.onWriteDemand(this);
    }

    private void write(List<ByteBuffer> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.write((ByteBuffer)object.next());
        }
    }

    @Override
    public void close() {
        this.close(1000);
    }

    @Override
    public void close(int n) {
        this.close(n, "", false);
    }

    @Override
    public void close(int n, String string) {
        this.close(n, string, false);
    }

    public void close(InvalidDataException invalidDataException) {
        this.close(invalidDataException.getCloseCode(), invalidDataException.getMessage(), false);
    }

    public void closeConnection() {
        if (this.closedremotely == null) {
            throw new IllegalStateException("this method must be used in conjuction with flushAndClose");
        }
        this.closeConnection(this.closecode, this.closemessage, this.closedremotely);
    }

    @Override
    public void closeConnection(int n, String string) {
        this.closeConnection(n, string, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void closeConnection(int n, String string, boolean bl) {
        synchronized (this) {
            Object object = this.readystate;
            WebSocket.READYSTATE rEADYSTATE = WebSocket.READYSTATE.CLOSED;
            if (object == rEADYSTATE) {
                return;
            }
            if (this.key != null) {
                this.key.cancel();
            }
            if ((object = this.channel) != null) {
                try {
                    this.channel.close();
                }
                catch (IOException iOException) {
                    this.wsl.onWebsocketError(this, iOException);
                }
            }
            try {
                void var3_4;
                this.wsl.onWebsocketClose(this, n, string, (boolean)var3_4);
            }
            catch (RuntimeException runtimeException) {
                this.wsl.onWebsocketError(this, runtimeException);
            }
            if (this.draft != null) {
                this.draft.reset();
            }
            this.handshakerequest = null;
            this.readystate = WebSocket.READYSTATE.CLOSED;
            this.outQueue.clear();
            return;
        }
    }

    protected void closeConnection(int n, boolean bl) {
        this.closeConnection(n, "", bl);
    }

    public void decode(ByteBuffer byteBuffer) {
        if (DEBUG) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("process(");
            stringBuilder.append(byteBuffer.remaining());
            stringBuilder.append("): {");
            String string = byteBuffer.remaining() > 1000 ? "too big to display" : new String(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
            stringBuilder.append(string);
            stringBuilder.append("}");
            printStream.println(stringBuilder.toString());
        }
        if (this.readystate != WebSocket.READYSTATE.NOT_YET_CONNECTED) {
            this.decodeFrames(byteBuffer);
            return;
        }
        if (this.decodeHandshake(byteBuffer)) {
            if (byteBuffer.hasRemaining()) {
                this.decodeFrames(byteBuffer);
                return;
            }
            if (this.tmpHandshakeBytes.hasRemaining()) {
                this.decodeFrames(this.tmpHandshakeBytes);
            }
        }
    }

    public void eot() {
        if (this.getReadyState() == WebSocket.READYSTATE.NOT_YET_CONNECTED) {
            this.closeConnection(-1, true);
            return;
        }
        if (this.flushandclosestate) {
            this.closeConnection(this.closecode, this.closemessage, this.closedremotely);
            return;
        }
        if (this.draft.getCloseHandshakeType() == Draft.CloseHandshakeType.NONE) {
            this.closeConnection(1000, true);
            return;
        }
        if (this.draft.getCloseHandshakeType() == Draft.CloseHandshakeType.ONEWAY) {
            if (this.role == WebSocket.Role.SERVER) {
                this.closeConnection(1006, true);
                return;
            }
            this.closeConnection(1000, true);
            return;
        }
        this.closeConnection(1006, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void flushAndClose(int n, String string, boolean bl) {
        synchronized (this) {
            void var3_4;
            boolean bl2 = this.flushandclosestate;
            if (bl2) {
                return;
            }
            this.closecode = n;
            this.closemessage = string;
            this.closedremotely = (boolean)var3_4;
            this.flushandclosestate = true;
            this.wsl.onWriteDemand(this);
            try {
                this.wsl.onWebsocketClosing(this, n, string, (boolean)var3_4);
            }
            catch (RuntimeException runtimeException) {
                this.wsl.onWebsocketError(this, runtimeException);
            }
            if (this.draft != null) {
                this.draft.reset();
            }
            this.handshakerequest = null;
            return;
        }
    }

    @Override
    public Draft getDraft() {
        return this.draft;
    }

    @Override
    public InetSocketAddress getLocalSocketAddress() {
        return this.wsl.getLocalSocketAddress(this);
    }

    @Override
    public WebSocket.READYSTATE getReadyState() {
        return this.readystate;
    }

    @Override
    public InetSocketAddress getRemoteSocketAddress() {
        return this.wsl.getRemoteSocketAddress(this);
    }

    @Override
    public String getResourceDescriptor() {
        return this.resourceDescriptor;
    }

    @Override
    public boolean hasBufferedData() {
        return this.outQueue.isEmpty() ^ true;
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean isClosed() {
        if (this.readystate == WebSocket.READYSTATE.CLOSED) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isClosing() {
        if (this.readystate == WebSocket.READYSTATE.CLOSING) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isConnecting() {
        if (this.readystate == WebSocket.READYSTATE.CONNECTING) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isFlushAndClose() {
        return this.flushandclosestate;
    }

    @Override
    public boolean isOpen() {
        if (this.readystate == WebSocket.READYSTATE.OPEN) {
            return true;
        }
        return false;
    }

    @Override
    public void send(String string) throws WebsocketNotConnectedException {
        if (string == null) {
            throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl.");
        }
        Draft draft = this.draft;
        boolean bl = this.role == WebSocket.Role.CLIENT;
        this.send(draft.createFrames(string, bl));
    }

    @Override
    public void send(ByteBuffer byteBuffer) throws IllegalArgumentException, WebsocketNotConnectedException {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl.");
        }
        Draft draft = this.draft;
        boolean bl = this.role == WebSocket.Role.CLIENT;
        this.send(draft.createFrames(byteBuffer, bl));
    }

    @Override
    public void send(byte[] arrby) throws IllegalArgumentException, WebsocketNotConnectedException {
        this.send(ByteBuffer.wrap(arrby));
    }

    @Override
    public void sendFragmentedFrame(Framedata.Opcode opcode, ByteBuffer byteBuffer, boolean bl) {
        this.send(this.draft.continuousFrame(opcode, byteBuffer, bl));
    }

    @Override
    public void sendFrame(Framedata framedata) {
        if (DEBUG) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("send frame: ");
            stringBuilder.append(framedata);
            printStream.println(stringBuilder.toString());
        }
        this.write(this.draft.createBinaryFrame(framedata));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void startHandshake(ClientHandshakeBuilder clientHandshakeBuilder) throws InvalidHandshakeException {
        this.handshakerequest = this.draft.postProcessHandshakeRequestAsClient(clientHandshakeBuilder);
        this.resourceDescriptor = clientHandshakeBuilder.getResourceDescriptor();
        try {
            this.wsl.onWebsocketHandshakeSentAsClient(this, this.handshakerequest);
        }
        catch (RuntimeException runtimeException) {
            this.wsl.onWebsocketError(this, runtimeException);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("rejected because of");
            stringBuilder.append(runtimeException);
            throw new InvalidHandshakeException(stringBuilder.toString());
        }
        catch (InvalidDataException invalidDataException) {
            throw new InvalidHandshakeException("Handshake data rejected by client.");
        }
        this.write(this.draft.createHandshake(this.handshakerequest, this.role));
        return;
    }

    public String toString() {
        return super.toString();
    }
}
