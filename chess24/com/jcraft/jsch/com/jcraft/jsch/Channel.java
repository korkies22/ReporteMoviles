/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.ChannelAgentForwarding;
import com.jcraft.jsch.ChannelDirectTCPIP;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelForwardedTCPIP;
import com.jcraft.jsch.ChannelSession;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.ChannelSubsystem;
import com.jcraft.jsch.ChannelX11;
import com.jcraft.jsch.IO;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.RequestSignal;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Vector;

public abstract class Channel
implements Runnable {
    static final int SSH_MSG_CHANNEL_OPEN_CONFIRMATION = 91;
    static final int SSH_MSG_CHANNEL_OPEN_FAILURE = 92;
    static final int SSH_MSG_CHANNEL_WINDOW_ADJUST = 93;
    static final int SSH_OPEN_ADMINISTRATIVELY_PROHIBITED = 1;
    static final int SSH_OPEN_CONNECT_FAILED = 2;
    static final int SSH_OPEN_RESOURCE_SHORTAGE = 4;
    static final int SSH_OPEN_UNKNOWN_CHANNEL_TYPE = 3;
    static int index;
    private static Vector pool;
    volatile boolean close = false;
    volatile int connectTimeout = 0;
    volatile boolean connected = false;
    volatile boolean eof_local = false;
    volatile boolean eof_remote = false;
    volatile int exitstatus = -1;
    int id;
    IO io = null;
    volatile int lmpsize = 16384;
    volatile int lwsize = this.lwsize_max = 1048576;
    volatile int lwsize_max;
    int notifyme = 0;
    volatile boolean open_confirmation = false;
    volatile int recipient = -1;
    volatile int reply = 0;
    volatile int rmpsize = 0;
    volatile long rwsize = 0L;
    private Session session;
    Thread thread = null;
    protected byte[] type = Util.str2byte("foo");

    static {
        pool = new Vector();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Channel() {
        Vector vector = pool;
        synchronized (vector) {
            int n = index;
            index = n + 1;
            this.id = n;
            pool.addElement(this);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void del(Channel channel) {
        Vector vector = pool;
        synchronized (vector) {
            pool.removeElement(channel);
            return;
        }
    }

    /*
     * Exception decompiling
     */
    static void disconnect(Session var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 8[SIMPLE_IF_TAKEN]
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Channel getChannel(int n, Session session) {
        Vector vector = pool;
        synchronized (vector) {
            int n2 = 0;
            while (n2 < pool.size()) {
                Channel channel = (Channel)pool.elementAt(n2);
                if (channel.id == n && channel.session == session) {
                    return channel;
                }
                ++n2;
            }
            return null;
        }
    }

    static Channel getChannel(String string) {
        if (string.equals("session")) {
            return new ChannelSession();
        }
        if (string.equals("shell")) {
            return new ChannelShell();
        }
        if (string.equals("exec")) {
            return new ChannelExec();
        }
        if (string.equals("x11")) {
            return new ChannelX11();
        }
        if (string.equals("auth-agent@openssh.com")) {
            return new ChannelAgentForwarding();
        }
        if (string.equals("direct-tcpip")) {
            return new ChannelDirectTCPIP();
        }
        if (string.equals("forwarded-tcpip")) {
            return new ChannelForwardedTCPIP();
        }
        if (string.equals("sftp")) {
            return new ChannelSftp();
        }
        if (string.equals("subsystem")) {
            return new ChannelSubsystem();
        }
        return null;
    }

    void addRemoteWindowSize(long l) {
        synchronized (this) {
            this.rwsize += l;
            if (this.notifyme > 0) {
                this.notifyAll();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void close() {
        Packet packet;
        if (this.close) {
            return;
        }
        this.close = true;
        this.eof_remote = true;
        this.eof_local = true;
        int n = this.getRecipient();
        if (n == -1) {
            return;
        }
        try {
            Buffer buffer = new Buffer(100);
            packet = new Packet(buffer);
            packet.reset();
            buffer.putByte((byte)97);
            buffer.putInt(n);
            // MONITORENTER : this
        }
        catch (Exception exception) {
            return;
        }
        this.getSession().write(packet);
        // MONITOREXIT : this
    }

    public void connect() throws JSchException {
        this.connect(0);
    }

    public void connect(int n) throws JSchException {
        this.connectTimeout = n;
        try {
            this.sendChannelOpen();
            this.start();
            return;
        }
        catch (Exception exception) {
            this.connected = false;
            this.disconnect();
            if (exception instanceof JSchException) {
                throw (JSchException)exception;
            }
            throw new JSchException(exception.toString(), exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void disconnect() {
        if (!this.connected) {
            // MONITOREXIT : this
            Channel.del(this);
            return;
        }
        this.connected = false;
        // MONITOREXIT : this
        try {
            this.close();
            this.eof_local = true;
            this.eof_remote = true;
            this.thread = null;
            try {
                if (this.io == null) return;
                this.io.close();
                return;
            }
            catch (Exception exception) {
                return;
            }
        }
        finally {
            Channel.del(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void eof() {
        block7 : {
            Packet packet;
            if (this.eof_local) {
                return;
            }
            this.eof_local = true;
            int n = this.getRecipient();
            if (n == -1) {
                return;
            }
            try {
                Buffer buffer = new Buffer(100);
                packet = new Packet(buffer);
                packet.reset();
                buffer.putByte((byte)96);
                buffer.putInt(n);
                // MONITORENTER : this
                if (this.close) break block7;
            }
            catch (Exception exception) {
                return;
            }
            this.getSession().write(packet);
        }
        // MONITOREXIT : this
    }

    void eof_remote() {
        this.eof_remote = true;
        try {
            this.io.out_close();
            return;
        }
        catch (NullPointerException nullPointerException) {
            return;
        }
    }

    protected Packet genChannelOpenPacket() {
        Buffer buffer = new Buffer(100);
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)90);
        buffer.putString(this.type);
        buffer.putInt(this.id);
        buffer.putInt(this.lwsize);
        buffer.putInt(this.lmpsize);
        return packet;
    }

    void getData(Buffer buffer) {
        this.setRecipient(buffer.getInt());
        this.setRemoteWindowSize(buffer.getUInt());
        this.setRemotePacketSize(buffer.getInt());
    }

    public int getExitStatus() {
        return this.exitstatus;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputStream getExtInputStream() throws IOException {
        int n;
        block2 : {
            try {
                n = Integer.parseInt(this.getSession().getConfig("max_input_buffer_size"));
                break block2;
            }
            catch (Exception exception) {}
            n = 32768;
        }
        MyPipedInputStream myPipedInputStream = new MyPipedInputStream(32768, n);
        boolean bl = 32768 < n;
        this.io.setExtOutputStream(new PassiveOutputStream(myPipedInputStream, bl), false);
        return myPipedInputStream;
    }

    public int getId() {
        return this.id;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputStream getInputStream() throws IOException {
        int n;
        block2 : {
            try {
                n = Integer.parseInt(this.getSession().getConfig("max_input_buffer_size"));
                break block2;
            }
            catch (Exception exception) {}
            n = 32768;
        }
        MyPipedInputStream myPipedInputStream = new MyPipedInputStream(32768, n);
        boolean bl = 32768 < n;
        this.io.setOutputStream(new PassiveOutputStream(myPipedInputStream, bl), false);
        return myPipedInputStream;
    }

    public OutputStream getOutputStream() throws IOException {
        return new OutputStream(){
            byte[] b = new byte[1];
            private Buffer buffer = null;
            private boolean closed = false;
            private int dataLen = 0;
            private Packet packet = null;

            private void init() throws IOException {
                synchronized (this) {
                    this.buffer = new Buffer(Channel.this.rmpsize);
                    this.packet = new Packet(this.buffer);
                    if (this.buffer.buffer.length - 14 - 128 <= 0) {
                        this.buffer = null;
                        this.packet = null;
                        throw new IOException("failed to initialize the channel.");
                    }
                    return;
                }
            }

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void close() throws IOException {
                if (this.packet == null) {
                    this.init();
                }
                if (this.closed) {
                    return;
                }
                if (this.dataLen > 0) {
                    this.flush();
                }
                this.eof();
                this.closed = true;
                return;
                catch (IOException iOException) {
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void flush() throws IOException {
                block7 : {
                    int n;
                    if (this.closed) {
                        throw new IOException("Already closed");
                    }
                    if (this.dataLen == 0) {
                        return;
                    }
                    this.packet.reset();
                    this.buffer.putByte((byte)94);
                    this.buffer.putInt(Channel.this.recipient);
                    this.buffer.putInt(this.dataLen);
                    this.buffer.skip(this.dataLen);
                    try {
                        n = this.dataLen;
                        this.dataLen = 0;
                        Channel channel = this;
                        // MONITORENTER : channel
                        if (this.close) break block7;
                    }
                    catch (Exception exception) {
                        this.close();
                        throw new IOException(exception.toString());
                    }
                    Channel.this.getSession().write(this.packet, this, n);
                }
                // MONITOREXIT : channel
            }

            @Override
            public void write(int n) throws IOException {
                this.b[0] = (byte)n;
                this.write(this.b, 0, 1);
            }

            @Override
            public void write(byte[] arrby, int n, int n2) throws IOException {
                if (this.packet == null) {
                    this.init();
                }
                if (this.closed) {
                    throw new IOException("Already closed");
                }
                byte[] arrby2 = this.buffer.buffer;
                int n3 = arrby2.length;
                while (n2 > 0) {
                    int n4 = n2 > n3 - (this.dataLen + 14) - 128 ? n3 - (this.dataLen + 14) - 128 : n2;
                    if (n4 <= 0) {
                        this.flush();
                        continue;
                    }
                    System.arraycopy(arrby, n, arrby2, 14 + this.dataLen, n4);
                    this.dataLen += n4;
                    n += n4;
                    n2 -= n4;
                }
            }
        };
    }

    int getRecipient() {
        return this.recipient;
    }

    public Session getSession() throws JSchException {
        Session session = this.session;
        if (session == null) {
            throw new JSchException("session is not available");
        }
        return session;
    }

    void init() throws JSchException {
    }

    public boolean isClosed() {
        return this.close;
    }

    public boolean isConnected() {
        Session session = this.session;
        boolean bl = false;
        if (session != null) {
            boolean bl2 = bl;
            if (session.isConnected()) {
                bl2 = bl;
                if (this.connected) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public boolean isEOF() {
        return this.eof_remote;
    }

    @Override
    public void run() {
    }

    /*
     * Exception decompiling
     */
    protected void sendChannelOpen() throws Exception {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.IllegalStateException: Last catch has completely empty body
        // org.benf.cfr.reader.bytecode.analysis.parse.utils.finalhelp.FinalAnalyzer.identifyFinally(FinalAnalyzer.java:285)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.FinallyRewriter.identifyFinally(FinallyRewriter.java:40)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:476)
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

    protected void sendOpenConfirmation() throws Exception {
        Buffer buffer = new Buffer(100);
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)91);
        buffer.putInt(this.getRecipient());
        buffer.putInt(this.id);
        buffer.putInt(this.lwsize);
        buffer.putInt(this.lmpsize);
        this.getSession().write(packet);
    }

    protected void sendOpenFailure(int n) {
        try {
            Buffer buffer = new Buffer(100);
            Packet packet = new Packet(buffer);
            packet.reset();
            buffer.putByte((byte)92);
            buffer.putInt(this.getRecipient());
            buffer.putInt(n);
            buffer.putString(Util.str2byte("open failed"));
            buffer.putString(Util.empty);
            this.getSession().write(packet);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public void sendSignal(String string) throws Exception {
        RequestSignal requestSignal = new RequestSignal();
        requestSignal.setSignal(string);
        requestSignal.request(this.getSession(), this);
    }

    void setExitStatus(int n) {
        this.exitstatus = n;
    }

    public void setExtOutputStream(OutputStream outputStream) {
        this.io.setExtOutputStream(outputStream, false);
    }

    public void setExtOutputStream(OutputStream outputStream, boolean bl) {
        this.io.setExtOutputStream(outputStream, bl);
    }

    public void setInputStream(InputStream inputStream) {
        this.io.setInputStream(inputStream, false);
    }

    public void setInputStream(InputStream inputStream, boolean bl) {
        this.io.setInputStream(inputStream, bl);
    }

    void setLocalPacketSize(int n) {
        this.lmpsize = n;
    }

    void setLocalWindowSize(int n) {
        this.lwsize = n;
    }

    void setLocalWindowSizeMax(int n) {
        this.lwsize_max = n;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.io.setOutputStream(outputStream, false);
    }

    public void setOutputStream(OutputStream outputStream, boolean bl) {
        this.io.setOutputStream(outputStream, bl);
    }

    void setRecipient(int n) {
        synchronized (this) {
            this.recipient = n;
            if (this.notifyme > 0) {
                this.notifyAll();
            }
            return;
        }
    }

    void setRemotePacketSize(int n) {
        this.rmpsize = n;
    }

    void setRemoteWindowSize(long l) {
        synchronized (this) {
            this.rwsize = l;
            return;
        }
    }

    void setSession(Session session) {
        this.session = session;
    }

    public void setXForwarding(boolean bl) {
    }

    public void start() throws JSchException {
    }

    void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    void write(byte[] arrby, int n, int n2) throws IOException {
        try {
            this.io.put(arrby, n, n2);
            return;
        }
        catch (NullPointerException nullPointerException) {
            return;
        }
    }

    void write_ext(byte[] arrby, int n, int n2) throws IOException {
        try {
            this.io.put_ext(arrby, n, n2);
            return;
        }
        catch (NullPointerException nullPointerException) {
            return;
        }
    }

    class MyPipedInputStream
    extends PipedInputStream {
        private int BUFFER_SIZE;
        private int max_buffer_size;

        MyPipedInputStream() throws IOException {
            this.max_buffer_size = this.BUFFER_SIZE = 1024;
        }

        MyPipedInputStream(int n) throws IOException {
            this.max_buffer_size = this.BUFFER_SIZE = 1024;
            this.buffer = new byte[n];
            this.BUFFER_SIZE = n;
            this.max_buffer_size = n;
        }

        MyPipedInputStream(int n, int n2) throws IOException {
            this(n);
            this.max_buffer_size = n2;
        }

        MyPipedInputStream(PipedOutputStream pipedOutputStream) throws IOException {
            super(pipedOutputStream);
            this.max_buffer_size = this.BUFFER_SIZE = 1024;
        }

        MyPipedInputStream(PipedOutputStream pipedOutputStream, int n) throws IOException {
            super(pipedOutputStream);
            this.max_buffer_size = this.BUFFER_SIZE = 1024;
            this.buffer = new byte[n];
            this.BUFFER_SIZE = n;
        }

        private int freeSpace() {
            if (this.out < this.in) {
                return this.buffer.length - this.in;
            }
            if (this.in < this.out) {
                if (this.in == -1) {
                    return this.buffer.length;
                }
                return this.out - this.in;
            }
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void checkSpace(int n) throws IOException {
            synchronized (this) {
                int n2 = this.freeSpace();
                if (n2 >= n) {
                    if (this.buffer.length == n2 && n2 > this.BUFFER_SIZE) {
                        n = n2 /= 2;
                        if (n2 < this.BUFFER_SIZE) {
                            n = this.BUFFER_SIZE;
                        }
                        this.buffer = new byte[n];
                    }
                } else {
                    int n3 = this.buffer.length - n2;
                    n2 = this.buffer.length;
                    while (n2 - n3 < n) {
                        n2 *= 2;
                    }
                    int n4 = n2;
                    if (n2 > this.max_buffer_size) {
                        n4 = this.max_buffer_size;
                    }
                    if (n4 - n3 < n) {
                        return;
                    }
                    byte[] arrby = new byte[n4];
                    if (this.out < this.in) {
                        System.arraycopy(this.buffer, 0, arrby, 0, this.buffer.length);
                    } else if (this.in < this.out) {
                        if (this.in != -1) {
                            System.arraycopy(this.buffer, 0, arrby, 0, this.in);
                            System.arraycopy(this.buffer, this.out, arrby, arrby.length - (this.buffer.length - this.out), this.buffer.length - this.out);
                            this.out = arrby.length - (this.buffer.length - this.out);
                        }
                    } else if (this.in == this.out) {
                        System.arraycopy(this.buffer, 0, arrby, 0, this.buffer.length);
                        this.in = this.buffer.length;
                    }
                    this.buffer = arrby;
                }
                return;
            }
        }

        public void updateReadSide() throws IOException {
            synchronized (this) {
                int n;
                block5 : {
                    n = this.available();
                    if (n == 0) break block5;
                    return;
                }
                this.in = 0;
                this.out = 0;
                byte[] arrby = this.buffer;
                n = this.in;
                this.in = n + 1;
                arrby[n] = 0;
                this.read();
                return;
            }
        }
    }

    class PassiveInputStream
    extends MyPipedInputStream {
        PipedOutputStream out;

        PassiveInputStream(PipedOutputStream pipedOutputStream) throws IOException {
            super(pipedOutputStream);
            this.out = pipedOutputStream;
        }

        PassiveInputStream(PipedOutputStream pipedOutputStream, int n) throws IOException {
            super(pipedOutputStream, n);
            this.out = pipedOutputStream;
        }

        @Override
        public void close() throws IOException {
            if (this.out != null) {
                this.out.close();
            }
            this.out = null;
        }
    }

    class PassiveOutputStream
    extends PipedOutputStream {
        private MyPipedInputStream _sink;

        PassiveOutputStream(PipedInputStream pipedInputStream, boolean bl) throws IOException {
            super(pipedInputStream);
            this._sink = null;
            if (bl && pipedInputStream instanceof MyPipedInputStream) {
                this._sink = (MyPipedInputStream)pipedInputStream;
            }
        }

        @Override
        public void write(int n) throws IOException {
            if (this._sink != null) {
                this._sink.checkSpace(1);
            }
            super.write(n);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            if (this._sink != null) {
                this._sink.checkSpace(n2);
            }
            super.write(arrby, n, n2);
        }
    }

}
