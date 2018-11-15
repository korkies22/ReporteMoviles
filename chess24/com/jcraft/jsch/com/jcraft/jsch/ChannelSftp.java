/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSession;
import com.jcraft.jsch.IO;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Request;
import com.jcraft.jsch.RequestSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import com.jcraft.jsch.SftpStatVFS;
import com.jcraft.jsch.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Vector;

public class ChannelSftp
extends ChannelSession {
    public static final int APPEND = 2;
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 32768;
    private static final int LOCAL_WINDOW_SIZE_MAX = 2097152;
    private static final int MAX_MSG_LENGTH = 262144;
    public static final int OVERWRITE = 0;
    public static final int RESUME = 1;
    private static final int SSH_FILEXFER_ATTR_ACMODTIME = 8;
    private static final int SSH_FILEXFER_ATTR_EXTENDED = Integer.MIN_VALUE;
    private static final int SSH_FILEXFER_ATTR_PERMISSIONS = 4;
    private static final int SSH_FILEXFER_ATTR_SIZE = 1;
    private static final int SSH_FILEXFER_ATTR_UIDGID = 2;
    private static final int SSH_FXF_APPEND = 4;
    private static final int SSH_FXF_CREAT = 8;
    private static final int SSH_FXF_EXCL = 32;
    private static final int SSH_FXF_READ = 1;
    private static final int SSH_FXF_TRUNC = 16;
    private static final int SSH_FXF_WRITE = 2;
    private static final byte SSH_FXP_ATTRS = 105;
    private static final byte SSH_FXP_CLOSE = 4;
    private static final byte SSH_FXP_DATA = 103;
    private static final byte SSH_FXP_EXTENDED = -56;
    private static final byte SSH_FXP_EXTENDED_REPLY = -55;
    private static final byte SSH_FXP_FSETSTAT = 10;
    private static final byte SSH_FXP_FSTAT = 8;
    private static final byte SSH_FXP_HANDLE = 102;
    private static final byte SSH_FXP_INIT = 1;
    private static final byte SSH_FXP_LSTAT = 7;
    private static final byte SSH_FXP_MKDIR = 14;
    private static final byte SSH_FXP_NAME = 104;
    private static final byte SSH_FXP_OPEN = 3;
    private static final byte SSH_FXP_OPENDIR = 11;
    private static final byte SSH_FXP_READ = 5;
    private static final byte SSH_FXP_READDIR = 12;
    private static final byte SSH_FXP_READLINK = 19;
    private static final byte SSH_FXP_REALPATH = 16;
    private static final byte SSH_FXP_REMOVE = 13;
    private static final byte SSH_FXP_RENAME = 18;
    private static final byte SSH_FXP_RMDIR = 15;
    private static final byte SSH_FXP_SETSTAT = 9;
    private static final byte SSH_FXP_STAT = 17;
    private static final byte SSH_FXP_STATUS = 101;
    private static final byte SSH_FXP_SYMLINK = 20;
    private static final byte SSH_FXP_VERSION = 2;
    private static final byte SSH_FXP_WRITE = 6;
    public static final int SSH_FX_BAD_MESSAGE = 5;
    public static final int SSH_FX_CONNECTION_LOST = 7;
    public static final int SSH_FX_EOF = 1;
    public static final int SSH_FX_FAILURE = 4;
    public static final int SSH_FX_NO_CONNECTION = 6;
    public static final int SSH_FX_NO_SUCH_FILE = 2;
    public static final int SSH_FX_OK = 0;
    public static final int SSH_FX_OP_UNSUPPORTED = 8;
    public static final int SSH_FX_PERMISSION_DENIED = 3;
    private static final String UTF8 = "UTF-8";
    private static final String file_separator = File.separator;
    private static final char file_separatorc = File.separatorChar;
    private static boolean fs_is_bs;
    private int[] ackid = new int[1];
    private Buffer buf;
    private int client_version = 3;
    private String cwd;
    private boolean extension_hardlink = false;
    private boolean extension_posix_rename = false;
    private boolean extension_statvfs = false;
    private Hashtable extensions = null;
    private String fEncoding = "UTF-8";
    private boolean fEncoding_is_utf8 = true;
    private String home;
    private boolean interactive = false;
    private InputStream io_in = null;
    private String lcwd;
    private Buffer obuf;
    private Packet opacket;
    private Packet packet;
    private RequestQueue rq = new RequestQueue(16);
    private int seq = 1;
    private int server_version = 3;
    private String version = String.valueOf(this.client_version);

    static {
        boolean bl = (byte)File.separatorChar == 92;
        fs_is_bs = bl;
    }

    public ChannelSftp() {
        this.setLocalWindowSizeMax(2097152);
        this.setLocalWindowSize(2097152);
        this.setLocalPacketSize(32768);
    }

    /*
     * Exception decompiling
     */
    private void _get(String var1_1, OutputStream var2_3, SftpProgressMonitor var3_4, int var4_5, long var5_6) throws SftpException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 9[CATCHBLOCK]
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
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private SftpATTRS _lstat(String object) throws SftpException {
        int n;
        try {
            this.sendLSTAT(Util.str2byte((String)object, this.fEncoding));
            object = new Header();
            object = this.header(this.buf, (Header)object);
            n = object.length;
            int n2 = object.type;
            this.fill(this.buf, n);
            if (n2 == 105) return SftpATTRS.getATTR(this.buf);
            if (n2 != 101) throw new SftpException(4, "");
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (!(exception instanceof Throwable)) throw new SftpException(4, "");
            throw new SftpException(4, "", exception);
        }
        n = this.buf.getInt();
        this.throwStatusError(this.buf, n);
        throw new SftpException(4, "");
    }

    private byte[] _realpath(String arrby) throws SftpException, IOException, Exception {
        this.sendREALPATH(Util.str2byte((String)arrby, this.fEncoding));
        arrby = new Header();
        arrby = this.header(this.buf, (Header)arrby);
        int n = arrby.length;
        int n2 = arrby.type;
        this.fill(this.buf, n);
        if (n2 != 101 && n2 != 104) {
            throw new SftpException(4, "");
        }
        if (n2 == 101) {
            n = this.buf.getInt();
            this.throwStatusError(this.buf, n);
        }
        arrby = null;
        for (n = this.buf.getInt(); n > 0; --n) {
            arrby = this.buf.getString();
            if (this.server_version <= 3) {
                this.buf.getString();
            }
            SftpATTRS.getATTR(this.buf);
        }
        return arrby;
    }

    private boolean _sendCLOSE(byte[] arrby, Header header) throws Exception {
        this.sendCLOSE(arrby);
        return this.checkStatus(null, header);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void _setStat(String object, SftpATTRS sftpATTRS) throws SftpException {
        try {
            this.sendSETSTAT(Util.str2byte((String)object, this.fEncoding), sftpATTRS);
            object = new Header();
            object = this.header(this.buf, (Header)object);
            int n = object.length;
            int n2 = object.type;
            this.fill(this.buf, n);
            if (n2 != 101) {
                throw new SftpException(4, "");
            }
            n = this.buf.getInt();
            if (n != 0) {
                this.throwStatusError(this.buf, n);
            }
            return;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    private SftpATTRS _stat(String string) throws SftpException {
        return this._stat(Util.str2byte(string, this.fEncoding));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private SftpATTRS _stat(byte[] object) throws SftpException {
        int n;
        try {
            this.sendSTAT((byte[])object);
            Header header = new Header();
            Header header2 = this.header(this.buf, header);
            n = header2.length;
            int n2 = header2.type;
            this.fill(this.buf, n);
            if (n2 == 105) return SftpATTRS.getATTR(this.buf);
            if (n2 != 101) throw new SftpException(4, "");
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (!(exception instanceof Throwable)) throw new SftpException(4, "");
            throw new SftpException(4, "", exception);
        }
        n = this.buf.getInt();
        this.throwStatusError(this.buf, n);
        throw new SftpException(4, "");
    }

    private SftpStatVFS _statVFS(String string) throws SftpException {
        return this._statVFS(Util.str2byte(string, this.fEncoding));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private SftpStatVFS _statVFS(byte[] object) throws SftpException {
        int n;
        if (!this.extension_statvfs) {
            throw new SftpException(8, "statvfs@openssh.com is not supported");
        }
        try {
            this.sendSTATVFS((byte[])object);
            Header header = new Header();
            Header header2 = this.header(this.buf, header);
            n = header2.length;
            int n2 = header2.type;
            this.fill(this.buf, n);
            if (n2 == 201) return SftpStatVFS.getStatVFS(this.buf);
            if (n2 != 101) throw new SftpException(4, "");
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (!(exception instanceof Throwable)) throw new SftpException(4, "");
            throw new SftpException(4, "", exception);
        }
        n = this.buf.getInt();
        this.throwStatusError(this.buf, n);
        throw new SftpException(4, "");
    }

    static /* synthetic */ int access$000(ChannelSftp channelSftp) {
        return channelSftp.seq;
    }

    static /* synthetic */ int access$100(ChannelSftp channelSftp, byte[] arrby, long l, byte[] arrby2, int n, int n2) throws Exception {
        return channelSftp.sendWRITE(arrby, l, arrby2, n, n2);
    }

    private boolean checkStatus(int[] arrn, Header header) throws IOException, SftpException {
        header = this.header(this.buf, header);
        int n = header.length;
        int n2 = header.type;
        if (arrn != null) {
            arrn[0] = header.rid;
        }
        this.fill(this.buf, n);
        if (n2 != 101) {
            throw new SftpException(4, "");
        }
        n = this.buf.getInt();
        if (n != 0) {
            this.throwStatusError(this.buf, n);
        }
        return true;
    }

    private int fill(byte[] arrby, int n, int n2) throws IOException {
        int n3 = n;
        while (n2 > 0) {
            int n4 = this.io_in.read(arrby, n3, n2);
            if (n4 <= 0) {
                throw new IOException("inputstream is closed");
            }
            n3 += n4;
            n2 -= n4;
        }
        return n3 - n;
    }

    private void fill(Buffer buffer, int n) throws IOException {
        buffer.reset();
        this.fill(buffer.buffer, 0, n);
        buffer.skip(n);
    }

    private String getCwd() throws SftpException {
        if (this.cwd == null) {
            this.cwd = this.getHome();
        }
        return this.cwd;
    }

    private Vector glob_local(String object) throws Exception {
        StringBuilder stringBuilder;
        int n;
        Vector<Object> vector = new Vector<Object>();
        Object[] arrobject = Util.str2byte((String)object, UTF8);
        int n2 = arrobject.length - 1;
        do {
            n = n2;
            if (n2 < 0) break;
            if (arrobject[n2] != 42 && arrobject[n2] != 63) {
                --n2;
                continue;
            }
            n = n2;
            if (fs_is_bs) break;
            n = n2;
            if (n2 <= 0) break;
            n = n2;
            if (arrobject[n2 - 1] != 92) break;
            n = --n2;
            if (n2 <= 0) break;
            n = n2;
            if (arrobject[n2 - 1] != 92) break;
            n2 = n2 - 1 - 1;
        } while (true);
        if (n < 0) {
            if (!fs_is_bs) {
                object = Util.unquote((String)object);
            }
            vector.addElement(object);
            return vector;
        }
        for (n2 = n; !(n2 < 0 || arrobject[n2] == file_separatorc || fs_is_bs && arrobject[n2] == 47); --n2) {
        }
        if (n2 < 0) {
            if (!fs_is_bs) {
                object = Util.unquote((String)object);
            }
            vector.addElement(object);
            return vector;
        }
        n = 0;
        if (n2 == 0) {
            object = new byte[]{(byte)file_separatorc};
        } else {
            object = new byte[n2];
            System.arraycopy(arrobject, 0, object, 0, n2);
        }
        byte[] arrby = new byte[arrobject.length - n2 - 1];
        System.arraycopy(arrobject, n2 + 1, arrby, 0, arrby.length);
        try {
            arrobject = new File(Util.byte2str(object, UTF8)).list();
            stringBuilder = new StringBuilder();
            stringBuilder.append(Util.byte2str(object));
            stringBuilder.append(file_separator);
            object = stringBuilder.toString();
            n2 = n;
        }
        catch (Exception exception) {
            return vector;
        }
        do {
            block13 : {
                if (n2 >= arrobject.length) break;
                if (!Util.glob(arrby, Util.str2byte((String)arrobject[n2], UTF8))) break block13;
                stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append((String)arrobject[n2]);
                vector.addElement(stringBuilder.toString());
            }
            ++n2;
        } while (true);
        return vector;
    }

    private Vector glob_remote(String object) throws Exception {
        Vector<String> vector = new Vector<String>();
        int n = object.lastIndexOf(47);
        if (n < 0) {
            vector.addElement(Util.unquote((String)object));
            return vector;
        }
        int n2 = n == 0 ? 1 : n;
        CharSequence charSequence = object.substring(0, n2);
        CharSequence charSequence2 = object.substring(n + 1);
        charSequence = Util.unquote((String)charSequence);
        object = new byte[1][];
        if (!this.isPattern((String)charSequence2, (byte[][])object)) {
            object = charSequence;
            if (!charSequence.equals("/")) {
                object = new StringBuilder();
                object.append((String)charSequence);
                object.append("/");
                object = object.toString();
            }
            charSequence = new StringBuilder();
            charSequence.append((String)object);
            charSequence.append(Util.unquote((String)charSequence2));
            vector.addElement(charSequence.toString());
            return vector;
        }
        byte[] arrby = object[0];
        this.sendOPENDIR(Util.str2byte((String)charSequence, this.fEncoding));
        object = new Header();
        object = this.header(this.buf, (Header)object);
        n2 = object.length;
        n = object.type;
        this.fill(this.buf, n2);
        if (n != 101 && n != 102) {
            throw new SftpException(4, "");
        }
        if (n == 101) {
            n2 = this.buf.getInt();
            this.throwStatusError(this.buf, n2);
        }
        byte[] arrby2 = this.buf.getString();
        charSequence2 = null;
        do {
            this.sendREADDIR(arrby2);
            Header header = this.header(this.buf, (Header)object);
            n2 = header.length;
            n = header.type;
            if (n != 101 && n != 104) {
                throw new SftpException(4, "");
            }
            if (n == 101) {
                this.fill(this.buf, n2);
                if (this._sendCLOSE(arrby2, header)) {
                    return vector;
                }
                return null;
            }
            this.buf.rewind();
            this.fill(this.buf.buffer, 0, 4);
            n2 -= 4;
            this.buf.reset();
            for (n = this.buf.getInt(); n > 0; --n) {
                Object object2;
                int n3 = n2;
                if (n2 > 0) {
                    this.buf.shift();
                    n3 = this.buf.buffer.length > this.buf.index + n2 ? n2 : this.buf.buffer.length - this.buf.index;
                    n3 = this.io_in.read(this.buf.buffer, this.buf.index, n3);
                    if (n3 <= 0) break;
                    object = this.buf;
                    object.index += n3;
                    n3 = n2 - n3;
                }
                byte[] arrby3 = this.buf.getString();
                if (this.server_version <= 3) {
                    this.buf.getString();
                }
                SftpATTRS.getATTR(this.buf);
                if (!this.fEncoding_is_utf8) {
                    object = Util.byte2str(arrby3, this.fEncoding);
                    object2 = Util.str2byte((String)object, UTF8);
                } else {
                    object2 = arrby3;
                    object = null;
                }
                Object object3 = charSequence2;
                if (Util.glob(arrby, object2)) {
                    object2 = object;
                    if (object == null) {
                        object2 = Util.byte2str(arrby3, this.fEncoding);
                    }
                    object = charSequence2;
                    if (charSequence2 == null) {
                        if (!charSequence.endsWith("/")) {
                            object = new StringBuilder();
                            object.append((String)charSequence);
                            object.append("/");
                            object = object.toString();
                        } else {
                            object = charSequence;
                        }
                    }
                    charSequence2 = new StringBuilder();
                    charSequence2.append((String)object);
                    charSequence2.append((String)object2);
                    vector.addElement(charSequence2.toString());
                    object3 = object;
                }
                charSequence2 = object3;
                n2 = n3;
            }
            object = header;
        } while (true);
    }

    private Header header(Buffer buffer, Header header) throws IOException {
        buffer.rewind();
        this.fill(buffer.buffer, 0, 9);
        header.length = buffer.getInt() - 5;
        header.type = buffer.getByte() & 255;
        header.rid = buffer.getInt();
        return header;
    }

    private static boolean isLocalAbsolutePath(String string) {
        return new File(string).isAbsolute();
    }

    private boolean isPattern(String string) {
        return this.isPattern(string, null);
    }

    private boolean isPattern(String arrby, byte[][] arrby2) {
        arrby = Util.str2byte((String)arrby, UTF8);
        if (arrby2 != null) {
            arrby2[0] = arrby;
        }
        return this.isPattern(arrby);
    }

    private boolean isPattern(byte[] arrby) {
        int n = arrby.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrby[n2] != 42) {
                if (arrby[n2] == 63) {
                    return true;
                }
                int n3 = n2;
                if (arrby[n2] == 92) {
                    int n4 = n2 + 1;
                    n3 = n2;
                    if (n4 < n) {
                        n3 = n4;
                    }
                }
                n2 = n3 + 1;
                continue;
            }
            return true;
        }
        return false;
    }

    private boolean isRemoteDir(String object) {
        block3 : {
            try {
                this.sendSTAT(Util.str2byte((String)object, this.fEncoding));
                object = new Header();
                object = this.header(this.buf, (Header)object);
                int n = object.length;
                int n2 = object.type;
                this.fill(this.buf, n);
                if (n2 == 105) break block3;
                return false;
            }
            catch (Exception exception) {
                return false;
            }
        }
        boolean bl = SftpATTRS.getATTR(this.buf).isDir();
        return bl;
    }

    private String isUnique(String string) throws SftpException, Exception {
        Vector vector = this.glob_remote(string);
        if (vector.size() != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" is not unique: ");
            stringBuilder.append(vector.toString());
            throw new SftpException(4, stringBuilder.toString());
        }
        return (String)vector.elementAt(0);
    }

    private String localAbsolutePath(String string) {
        if (ChannelSftp.isLocalAbsolutePath(string)) {
            return string;
        }
        if (this.lcwd.endsWith(file_separator)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.lcwd);
            stringBuilder.append(string);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.lcwd);
        stringBuilder.append(file_separator);
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private void putHEAD(byte by, int n) throws Exception {
        this.putHEAD(this.buf, by, n);
    }

    private void putHEAD(Buffer buffer, byte by, int n) throws Exception {
        buffer.putByte((byte)94);
        buffer.putInt(this.recipient);
        buffer.putInt(n + 4);
        buffer.putInt(n);
        buffer.putByte(by);
    }

    private void read(byte[] arrby, int n, int n2) throws IOException, SftpException {
        while (n2 > 0) {
            int n3 = this.io_in.read(arrby, n, n2);
            if (n3 <= 0) {
                throw new SftpException(4, "");
            }
            n += n3;
            n2 -= n3;
        }
    }

    private String remoteAbsolutePath(String string) throws SftpException {
        if (string.charAt(0) == '/') {
            return string;
        }
        String string2 = this.getCwd();
        if (string2.endsWith("/")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(string);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("/");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private void sendCLOSE(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)4, arrby);
    }

    private void sendFSTAT(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)8, arrby);
    }

    private void sendHARDLINK(byte[] arrby, byte[] arrby2) throws Exception {
        this.sendPacketPath((byte)0, arrby, arrby2, "hardlink@openssh.com");
    }

    private void sendINIT() throws Exception {
        this.packet.reset();
        this.putHEAD((byte)1, 5);
        this.buf.putInt(3);
        this.getSession().write(this.packet, this, 9);
    }

    private void sendLSTAT(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)7, arrby);
    }

    private void sendMKDIR(byte[] arrby, SftpATTRS sftpATTRS) throws Exception {
        this.packet.reset();
        int n = arrby.length;
        int n2 = sftpATTRS != null ? sftpATTRS.length() : 4;
        this.putHEAD((byte)14, n + 9 + n2);
        Object object = this.buf;
        n2 = this.seq;
        this.seq = n2 + 1;
        object.putInt(n2);
        this.buf.putString(arrby);
        if (sftpATTRS != null) {
            sftpATTRS.dump(this.buf);
        } else {
            this.buf.putInt(0);
        }
        object = this.getSession();
        Packet packet = this.packet;
        n = arrby.length;
        n2 = sftpATTRS != null ? sftpATTRS.length() : 4;
        object.write(packet, this, 9 + n + n2 + 4);
    }

    private void sendOPEN(byte[] arrby, int n) throws Exception {
        this.packet.reset();
        this.putHEAD((byte)3, arrby.length + 17);
        Buffer buffer = this.buf;
        int n2 = this.seq;
        this.seq = n2 + 1;
        buffer.putInt(n2);
        this.buf.putString(arrby);
        this.buf.putInt(n);
        this.buf.putInt(0);
        this.getSession().write(this.packet, this, 17 + arrby.length + 4);
    }

    private void sendOPENA(byte[] arrby) throws Exception {
        this.sendOPEN(arrby, 10);
    }

    private void sendOPENDIR(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)11, arrby);
    }

    private void sendOPENR(byte[] arrby) throws Exception {
        this.sendOPEN(arrby, 1);
    }

    private void sendOPENW(byte[] arrby) throws Exception {
        this.sendOPEN(arrby, 26);
    }

    private void sendPacketPath(byte by, byte[] arrby) throws Exception {
        this.sendPacketPath(by, arrby, (String)null);
    }

    private void sendPacketPath(byte by, byte[] arrby, String object) throws Exception {
        this.packet.reset();
        int n = 9 + arrby.length;
        if (object == null) {
            this.putHEAD(by, n);
            object = this.buf;
            int n2 = this.seq;
            this.seq = n2 + 1;
            object.putInt(n2);
        } else {
            this.putHEAD((byte)-56, n += object.length() + 4);
            Buffer buffer = this.buf;
            int n3 = this.seq;
            this.seq = n3 + 1;
            buffer.putInt(n3);
            this.buf.putString(Util.str2byte((String)object));
        }
        this.buf.putString(arrby);
        this.getSession().write(this.packet, this, n + 4);
    }

    private void sendPacketPath(byte by, byte[] arrby, byte[] arrby2) throws Exception {
        this.sendPacketPath(by, arrby, arrby2, null);
    }

    private void sendPacketPath(byte by, byte[] arrby, byte[] arrby2, String object) throws Exception {
        this.packet.reset();
        int n = 13 + arrby.length + arrby2.length;
        if (object == null) {
            this.putHEAD(by, n);
            object = this.buf;
            int n2 = this.seq;
            this.seq = n2 + 1;
            object.putInt(n2);
        } else {
            this.putHEAD((byte)-56, n += object.length() + 4);
            Buffer buffer = this.buf;
            int n3 = this.seq;
            this.seq = n3 + 1;
            buffer.putInt(n3);
            this.buf.putString(Util.str2byte((String)object));
        }
        this.buf.putString(arrby);
        this.buf.putString(arrby2);
        this.getSession().write(this.packet, this, n + 4);
    }

    private void sendREAD(byte[] arrby, long l, int n) throws Exception {
        this.sendREAD(arrby, l, n, null);
    }

    private void sendREAD(byte[] arrby, long l, int n, RequestQueue requestQueue) throws Exception {
        this.packet.reset();
        this.putHEAD((byte)5, arrby.length + 21);
        Buffer buffer = this.buf;
        int n2 = this.seq;
        this.seq = n2 + 1;
        buffer.putInt(n2);
        this.buf.putString(arrby);
        this.buf.putLong(l);
        this.buf.putInt(n);
        this.getSession().write(this.packet, this, 21 + arrby.length + 4);
        if (requestQueue != null) {
            requestQueue.add(this.seq - 1, l, n);
        }
    }

    private void sendREADDIR(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)12, arrby);
    }

    private void sendREADLINK(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)19, arrby);
    }

    private void sendREALPATH(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)16, arrby);
    }

    private void sendREMOVE(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)13, arrby);
    }

    private void sendRENAME(byte[] arrby, byte[] arrby2) throws Exception {
        String string = this.extension_posix_rename ? "posix-rename@openssh.com" : null;
        this.sendPacketPath((byte)18, arrby, arrby2, string);
    }

    private void sendRMDIR(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)15, arrby);
    }

    private void sendSETSTAT(byte[] arrby, SftpATTRS sftpATTRS) throws Exception {
        this.packet.reset();
        this.putHEAD((byte)9, arrby.length + 9 + sftpATTRS.length());
        Buffer buffer = this.buf;
        int n = this.seq;
        this.seq = n + 1;
        buffer.putInt(n);
        this.buf.putString(arrby);
        sftpATTRS.dump(this.buf);
        this.getSession().write(this.packet, this, 9 + arrby.length + sftpATTRS.length() + 4);
    }

    private void sendSTAT(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)17, arrby);
    }

    private void sendSTATVFS(byte[] arrby) throws Exception {
        this.sendPacketPath((byte)0, arrby, "statvfs@openssh.com");
    }

    private void sendSYMLINK(byte[] arrby, byte[] arrby2) throws Exception {
        this.sendPacketPath((byte)20, arrby, arrby2);
    }

    private int sendWRITE(byte[] arrby, long l, byte[] arrby2, int n, int n2) throws Exception {
        this.opacket.reset();
        int n3 = n2;
        if (this.obuf.buffer.length < this.obuf.index + 13 + 21 + arrby.length + n2 + 128) {
            n3 = this.obuf.buffer.length - (this.obuf.index + 13 + 21 + arrby.length + 128);
        }
        this.putHEAD(this.obuf, (byte)6, arrby.length + 21 + n3);
        Buffer buffer = this.obuf;
        n2 = this.seq;
        this.seq = n2 + 1;
        buffer.putInt(n2);
        this.obuf.putString(arrby);
        this.obuf.putLong(l);
        if (this.obuf.buffer != arrby2) {
            this.obuf.putString(arrby2, n, n3);
        } else {
            this.obuf.putInt(n3);
            this.obuf.skip(n3);
        }
        this.getSession().write(this.opacket, this, 21 + arrby.length + n3 + 4);
        return n3;
    }

    private void setCwd(String string) {
        this.cwd = string;
    }

    private void skip(long l) throws IOException {
        while (l > 0L) {
            long l2 = this.io_in.skip(l);
            if (l2 <= 0L) {
                return;
            }
            l -= l2;
        }
    }

    private void throwStatusError(Buffer buffer, int n) throws SftpException {
        if (this.server_version >= 3 && buffer.getLength() >= 4) {
            throw new SftpException(n, Util.byte2str(buffer.getString(), UTF8));
        }
        throw new SftpException(n, "Failure");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public void _put(InputStream var1_1, String var2_3, SftpProgressMonitor var3_4, int var4_5) throws SftpException {
        block38 : {
            block36 : {
                block35 : {
                    block42 : {
                        block41 : {
                            block34 : {
                                block33 : {
                                    block32 : {
                                        block31 : {
                                            block37 : {
                                                block40 : {
                                                    block39 : {
                                                        block30 : {
                                                            block29 : {
                                                                block28 : {
                                                                    ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
                                                                    var20_6 = Util.str2byte((String)var2_3 /* !! */ , this.fEncoding);
                                                                    if (var4_5 != 1 && var4_5 != 2) break block28;
                                                                    var16_7 = this._stat(var20_6).getSize();
                                                                    break block29;
                                                                }
lbl9: // 2 sources:
                                                                var16_7 = 0L;
                                                            }
                                                            if (var4_5 != 1 || var16_7 <= 0L) ** GOTO lbl18
                                                            if (var1_1.skip(var16_7) < var16_7) {
                                                                var1_1 = new StringBuilder();
                                                                var1_1.append("failed to resume for ");
                                                                var1_1.append((String)var2_3 /* !! */ );
                                                                throw new SftpException(4, var1_1.toString());
                                                            }
lbl18: // 3 sources:
                                                            if (var4_5 == 0) {
                                                                this.sendOPENW(var20_6);
                                                            } else {
                                                                this.sendOPENA(var20_6);
                                                            }
                                                            var2_3 /* !! */  = new Header();
                                                            var21_9 = this.header(this.buf, (Header)var2_3 /* !! */ );
                                                            var5_10 = var21_9.length;
                                                            var6_11 = var21_9.type;
                                                            this.fill(this.buf, var5_10);
                                                            if (var6_11 == 101 || var6_11 == 102) break block30;
                                                            var1_1 = new StringBuilder();
                                                            var1_1.append("invalid type=");
                                                            var1_1.append(var6_11);
                                                            throw new SftpException(4, var1_1.toString());
                                                        }
                                                        if (var6_11 != 101) ** GOTO lbl38
                                                        var5_10 = this.buf.getInt();
                                                        this.throwStatusError(this.buf, var5_10);
lbl38: // 2 sources:
                                                        var20_6 = this.buf.getString();
                                                        if (var4_5 != 1 && var4_5 != 2) break block31;
                                                        break block32;
lbl41: // 2 sources:
                                                        do {
                                                            var14_12 = this.seq;
                                                            var2_3 /* !! */  = this.obuf.buffer;
                                                            var13_13 = 39 + var20_6.length;
                                                            var4_5 = this.obuf.buffer.length - var13_13 - 128;
                                                            var7_14 = this.rq.size();
                                                            var6_11 = 0;
                                                            break block33;
                                                            break;
                                                        } while (true);
lbl49: // 2 sources:
                                                        var15_20 = var1_1.read(var2_3 /* !! */ , var5_10, var12_19);
                                                        var8_15 = var5_10;
                                                        var11_18 = var12_19;
                                                        var10_17 = var9_16;
                                                        if (var15_20 <= 0) break block34;
                                                        var8_15 = var5_10 + var15_20;
                                                        var11_18 = var12_19 - var15_20;
                                                        var10_17 = var9_16 + var15_20;
                                                        break block34;
lbl58: // 2 sources:
                                                        if (var10_17 <= 0) ** GOTO lbl97
                                                        var8_15 = var4_5;
                                                        if (this.seq - 1 == var14_12) break block39;
                                                        var6_11 = var4_5;
                                                        if (this.seq - var14_12 - var4_5 < var7_14) break block40;
                                                        var8_15 = var4_5;
                                                    }
lbl66: // 2 sources:
                                                    var6_11 = var8_15;
                                                    if (this.seq - var14_12 - var8_15 >= var7_14) {
                                                        var6_11 = var8_15;
                                                        if (this.checkStatus(this.ackid, var21_9)) {
                                                            var4_5 = this.ackid[0];
                                                            if (var14_12 <= var4_5 && var4_5 <= this.seq - 1) break block35;
                                                            if (var4_5 == this.seq) {
                                                                var22_22 = System.err;
                                                                var23_23 = new StringBuilder();
                                                                var23_23.append("ack error: startid=");
                                                                var23_23.append(var14_12);
                                                                var23_23.append(" seq=");
                                                                var23_23.append(this.seq);
                                                                var23_23.append(" _ackid=");
                                                                var23_23.append(var4_5);
                                                                var22_22.println(var23_23.toString());
                                                                break block35;
                                                            }
                                                            var1_1 = new StringBuilder();
                                                            var1_1.append("ack error: startid=");
                                                            var1_1.append(var14_12);
                                                            var1_1.append(" seq=");
                                                            var1_1.append(this.seq);
                                                            var1_1.append(" _ackid=");
                                                            var1_1.append(var4_5);
                                                            throw new SftpException(4, var1_1.toString());
                                                        }
                                                    }
                                                }
                                                var10_17 -= this.sendWRITE(var20_6, var16_7, var2_3 /* !! */ , 0, var10_17);
                                                if (var2_3 /* !! */  == this.obuf.buffer) break block36;
                                                var2_3 /* !! */  = this.obuf.buffer;
                                                var5_10 = this.obuf.buffer.length - var13_13 - 128;
                                                break block36;
lbl97: // 1 sources:
                                                var18_21 = var9_16;
                                                if (var3_4 == null || var3_4.count(var18_21)) break block37;
lbl99: // 2 sources:
                                                var5_10 = this.seq;
lbl100: // 2 sources:
                                                if (var5_10 - var14_12 <= var4_5) ** GOTO lbl103
                                                try {
                                                    if (this.checkStatus(null, var21_9)) break block38;
lbl103: // 2 sources:
                                                    if (var3_4 != null) {
                                                        var3_4.end();
                                                    }
                                                    this._sendCLOSE(var20_6, var21_9);
                                                    return;
                                                }
                                                catch (Exception var1_2) {
                                                    if (var1_2 instanceof SftpException) {
                                                        throw (SftpException)var1_2;
                                                    }
                                                    if (var1_2 instanceof Throwable) {
                                                        throw new SftpException(4, var1_2.toString(), var1_2);
                                                    }
                                                    throw new SftpException(4, var1_2.toString());
                                                }
                                            }
                                            var16_7 += var18_21;
                                            var6_11 = var4_5;
                                            var4_5 = var5_10;
                                            break block33;
                                            catch (Exception var21_8) {
                                                ** GOTO lbl9
                                            }
                                        }
                                        var16_7 = 0L;
                                        ** GOTO lbl41
                                    }
                                    var16_7 = 0L + var16_7;
                                    ** while (true)
                                }
                                var12_19 = var4_5;
                                var5_10 = var13_13;
                                var9_16 = 0;
                                ** GOTO lbl49
                            }
                            var5_10 = var10_17;
                            if (var11_18 <= 0 || var15_20 <= 0) break block41;
                            var9_16 = var5_10;
                            var5_10 = var8_15;
                            var12_19 = var11_18;
                            ** GOTO lbl49
                        }
                        if (var5_10 > 0) break block42;
                        var4_5 = var6_11;
                        ** GOTO lbl99
                    }
                    var10_17 = var5_10;
                    var8_15 = var4_5;
                    var4_5 = var6_11;
                    var9_16 = var5_10;
                    var5_10 = var8_15;
                    ** GOTO lbl58
                }
                ++var8_15;
                ** GOTO lbl66
            }
            var4_5 = var6_11;
            ** GOTO lbl58
        }
        ++var4_5;
        ** GOTO lbl100
    }

    public void cd(String string) throws SftpException {
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            string = this.isUnique(this.remoteAbsolutePath(string));
            Object object = this._realpath(string);
            SftpATTRS sftpATTRS = this._stat((byte[])object);
            if ((sftpATTRS.getFlags() & 4) == 0) {
                object = new StringBuilder();
                object.append("Can't change directory: ");
                object.append(string);
                throw new SftpException(4, object.toString());
            }
            if (!sftpATTRS.isDir()) {
                object = new StringBuilder();
                object.append("Can't change directory: ");
                object.append(string);
                throw new SftpException(4, object.toString());
            }
            this.setCwd(Util.byte2str((byte[])object, this.fEncoding));
            return;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    public void chgrp(int n, String object) throws SftpException {
        int n2;
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            object = this.glob_remote(this.remoteAbsolutePath((String)object));
            n2 = object.size();
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
        for (int i = 0; i < n2; ++i) {
            String string = (String)object.elementAt(i);
            SftpATTRS sftpATTRS = this._stat(string);
            sftpATTRS.setFLAGS(0);
            sftpATTRS.setUIDGID(sftpATTRS.uid, n);
            this._setStat(string, sftpATTRS);
        }
        return;
    }

    public void chmod(int n, String object) throws SftpException {
        int n2;
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            object = this.glob_remote(this.remoteAbsolutePath((String)object));
            n2 = object.size();
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
        for (int i = 0; i < n2; ++i) {
            String string = (String)object.elementAt(i);
            SftpATTRS sftpATTRS = this._stat(string);
            sftpATTRS.setFLAGS(0);
            sftpATTRS.setPERMISSIONS(n);
            this._setStat(string, sftpATTRS);
        }
        return;
    }

    public void chown(int n, String object) throws SftpException {
        int n2;
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            object = this.glob_remote(this.remoteAbsolutePath((String)object));
            n2 = object.size();
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
        for (int i = 0; i < n2; ++i) {
            String string = (String)object.elementAt(i);
            SftpATTRS sftpATTRS = this._stat(string);
            sftpATTRS.setFLAGS(0);
            sftpATTRS.setUIDGID(n, sftpATTRS.gid);
            this._setStat(string, sftpATTRS);
        }
        return;
    }

    @Override
    public void disconnect() {
        super.disconnect();
    }

    public void exit() {
        this.disconnect();
    }

    public InputStream get(String string) throws SftpException {
        return this.get(string, null, 0L);
    }

    public InputStream get(String string, int n) throws SftpException {
        return this.get(string, null, 0L);
    }

    public InputStream get(String string, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        return this.get(string, sftpProgressMonitor, 0L);
    }

    public InputStream get(String string, SftpProgressMonitor sftpProgressMonitor, int n) throws SftpException {
        return this.get(string, sftpProgressMonitor, 0L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputStream get(String object, SftpProgressMonitor sftpProgressMonitor, long l) throws SftpException {
        try {
            void var3_10;
            void var2_9;
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            String string = this.isUnique(this.remoteAbsolutePath((String)object));
            byte[] arrby = Util.str2byte(string, this.fEncoding);
            SftpATTRS sftpATTRS = this._stat(arrby);
            if (var2_9 != null) {
                var2_9.init(1, string, "??", sftpATTRS.getSize());
            }
            this.sendOPENR(arrby);
            Header header = new Header();
            Header header2 = this.header(this.buf, header);
            int n = header2.length;
            int n2 = header2.type;
            this.fill(this.buf, n);
            if (n2 != 101 && n2 != 102) {
                throw new SftpException(4, "");
            }
            if (n2 == 101) {
                n = this.buf.getInt();
                this.throwStatusError(this.buf, n);
            }
            byte[] arrby2 = this.buf.getString();
            this.rq.init();
            return new InputStream((long)var3_10, (SftpProgressMonitor)var2_9, arrby2){
                byte[] _data;
                boolean closed;
                Header header;
                long offset;
                int request_max;
                long request_offset;
                byte[] rest_byte;
                int rest_length;
                final /* synthetic */ byte[] val$handle;
                final /* synthetic */ SftpProgressMonitor val$monitor;
                final /* synthetic */ long val$skip;
                {
                    this.val$skip = l;
                    this.val$monitor = sftpProgressMonitor;
                    this.val$handle = arrby;
                    this.offset = this.val$skip;
                    this.closed = false;
                    this.rest_length = 0;
                    this._data = new byte[1];
                    this.rest_byte = new byte[1024];
                    this.header = new Header();
                    this.request_max = 1;
                    this.request_offset = this.offset;
                }

                /*
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                @Override
                public void close() throws IOException {
                    if (this.closed) {
                        return;
                    }
                    this.closed = true;
                    if (this.val$monitor != null) {
                        this.val$monitor.end();
                    }
                    ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                    try {
                        ChannelSftp.this._sendCLOSE(this.val$handle, this.header);
                        return;
                    }
                    catch (Exception exception) {
                        throw new IOException("error");
                    }
                }

                @Override
                public int read() throws IOException {
                    if (this.closed) {
                        return -1;
                    }
                    if (this.read(this._data, 0, 1) == -1) {
                        return -1;
                    }
                    return this._data[0] & 255;
                }

                @Override
                public int read(byte[] arrby) throws IOException {
                    if (this.closed) {
                        return -1;
                    }
                    return this.read(arrby, 0, arrby.length);
                }

                /*
                 * Loose catch block
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Lifted jumps to return sites
                 */
                @Override
                public int read(byte[] arrby, int n, int n2) throws IOException {
                    int n3;
                    int n4;
                    RequestQueue$Request requestQueue$Request;
                    long l2;
                    block29 : {
                        int n5;
                        block30 : {
                            block28 : {
                                if (this.closed) {
                                    return -1;
                                }
                                if (arrby == null) {
                                    throw new NullPointerException();
                                }
                                if (n < 0) throw new IndexOutOfBoundsException();
                                if (n2 < 0) throw new IndexOutOfBoundsException();
                                if (n + n2 > arrby.length) {
                                    throw new IndexOutOfBoundsException();
                                }
                                if (n2 == 0) {
                                    return 0;
                                }
                                if (this.rest_length > 0) {
                                    int n6 = this.rest_length;
                                    if (n6 <= n2) {
                                        n2 = n6;
                                    }
                                    System.arraycopy(this.rest_byte, 0, arrby, n, n2);
                                    if (n2 != this.rest_length) {
                                        System.arraycopy(this.rest_byte, n2, this.rest_byte, 0, this.rest_length - n2);
                                    }
                                    if (this.val$monitor != null && !this.val$monitor.count(n2)) {
                                        this.close();
                                        return -1;
                                    }
                                    this.rest_length -= n2;
                                    return n2;
                                }
                                n5 = n2;
                                if (ChannelSftp.access$700((ChannelSftp)ChannelSftp.this).buffer.length - 13 < n2) {
                                    n5 = ChannelSftp.access$700((ChannelSftp)ChannelSftp.this).buffer.length - 13;
                                }
                                n2 = n5;
                                if (ChannelSftp.this.server_version == 0) {
                                    n2 = n5;
                                    if (n5 > 1024) {
                                        n2 = 1024;
                                    }
                                }
                                ChannelSftp.this.rq.count();
                                n5 = ChannelSftp.access$700((ChannelSftp)ChannelSftp.this).buffer.length - 13;
                                if (ChannelSftp.this.server_version == 0) {
                                    n5 = 1024;
                                }
                                while (ChannelSftp.this.rq.count() < this.request_max) {
                                    ChannelSftp.this.sendREAD(this.val$handle, this.request_offset, n5, ChannelSftp.this.rq);
                                    this.request_offset += (long)n5;
                                }
                                this.header = ChannelSftp.this.header(ChannelSftp.this.buf, this.header);
                                this.rest_length = this.header.length;
                                n5 = this.header.type;
                                n4 = this.header.rid;
                                try {
                                    requestQueue$Request = ChannelSftp.this.rq.get(this.header.rid);
                                    if (n5 != 101 && n5 != 103) {
                                        throw new IOException("error");
                                    }
                                    if (n5 != 101) break block28;
                                }
                                catch (SftpException sftpException) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("error: ");
                                    stringBuilder.append(sftpException.toString());
                                    throw new IOException(stringBuilder.toString());
                                }
                                catch (RequestQueue$OutOfOrderException requestQueue$OutOfOrderException) {
                                    this.request_offset = requestQueue$OutOfOrderException.offset;
                                    this.skip(this.header.length);
                                    ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                                    return 0;
                                }
                                ChannelSftp.this.fill(ChannelSftp.this.buf, this.rest_length);
                                n = ChannelSftp.this.buf.getInt();
                                this.rest_length = 0;
                                if (n != 1) throw new IOException("error");
                                this.close();
                                return -1;
                            }
                            ChannelSftp.this.buf.rewind();
                            ChannelSftp.this.fill(ChannelSftp.access$700((ChannelSftp)ChannelSftp.this).buffer, 0, 4);
                            n5 = ChannelSftp.this.buf.getInt();
                            this.rest_length -= 4;
                            n4 = this.rest_length - n5;
                            long l = this.offset;
                            l2 = n5;
                            this.offset = l + l2;
                            if (n5 <= 0) return 0;
                            if (n5 <= n2) {
                                n2 = n5;
                            }
                            n3 = ChannelSftp.this.io_in.read(arrby, n, n2);
                            if (n3 < 0) {
                                return -1;
                            }
                            this.rest_length = n = n5 - n3;
                            if (n <= 0) break block29;
                            if (this.rest_byte.length < n) {
                                this.rest_byte = new byte[n];
                            }
                            n2 = 0;
                            break block30;
                            catch (Exception exception) {
                                throw new IOException("error");
                            }
                        }
                        while (n > 0 && (n5 = ChannelSftp.this.io_in.read(this.rest_byte, n2, n)) > 0) {
                            n2 += n5;
                            n -= n5;
                        }
                    }
                    if (n4 > 0) {
                        ChannelSftp.this.io_in.skip(n4);
                    }
                    if (l2 < requestQueue$Request.length) {
                        ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                        ChannelSftp.this.sendREAD(this.val$handle, requestQueue$Request.offset + l2, (int)(requestQueue$Request.length - l2), ChannelSftp.this.rq);
                        this.request_offset = requestQueue$Request.offset + requestQueue$Request.length;
                    }
                    if (this.request_max < ChannelSftp.this.rq.size()) {
                        ++this.request_max;
                    }
                    if (this.val$monitor == null) return n3;
                    if (this.val$monitor.count(n3)) return n3;
                    this.close();
                    return -1;
                    catch (Exception exception) {
                        throw new IOException("error");
                    }
                }
            };
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (!(exception instanceof Throwable)) throw new SftpException(4, "");
            throw new SftpException(4, "", exception);
        }
    }

    public void get(String string, OutputStream outputStream) throws SftpException {
        this.get(string, outputStream, null, 0, 0L);
    }

    public void get(String string, OutputStream outputStream, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        this.get(string, outputStream, sftpProgressMonitor, 0, 0L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void get(String string, OutputStream outputStream, SftpProgressMonitor sftpProgressMonitor, int n, long l) throws SftpException {
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            string = this.isUnique(this.remoteAbsolutePath(string));
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.init(1, string, "??", this._stat(string).getSize());
                if (n == 1) {
                    sftpProgressMonitor.count(l);
                }
            }
            this._get(string, outputStream, sftpProgressMonitor, n, l);
            return;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    public void get(String string, String string2) throws SftpException {
        this.get(string, string2, null, 0);
    }

    public void get(String string, String string2, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        this.get(string, string2, sftpProgressMonitor, 0);
    }

    /*
     * Exception decompiling
     */
    public void get(String var1_1, String var2_2, SftpProgressMonitor var3_11, int var4_15) throws SftpException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [25[SIMPLE_IF_TAKEN]], but top level block is 8[TRYBLOCK]
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

    public int getBulkRequests() {
        return this.rq.size();
    }

    public String getExtension(String string) {
        if (this.extensions == null) {
            return null;
        }
        return (String)this.extensions.get(string);
    }

    public String getHome() throws SftpException {
        if (this.home == null) {
            try {
                ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
                this.home = Util.byte2str(this._realpath(""), this.fEncoding);
            }
            catch (Exception exception) {
                if (exception instanceof SftpException) {
                    throw (SftpException)exception;
                }
                if (exception instanceof Throwable) {
                    throw new SftpException(4, "", exception);
                }
                throw new SftpException(4, "");
            }
        }
        return this.home;
    }

    public int getServerVersion() throws SftpException {
        if (!this.isConnected()) {
            throw new SftpException(4, "The channel is not connected.");
        }
        return this.server_version;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void hardlink(String object, String string) throws SftpException {
        if (!this.extension_hardlink) {
            throw new SftpException(8, "hardlink@openssh.com is not supported");
        }
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            String string2 = this.remoteAbsolutePath((String)object);
            String string3 = this.remoteAbsolutePath(string);
            string = string2 = this.isUnique(string2);
            if (object.charAt(0) != '/') {
                object = this.getCwd();
                string = string2.substring(object.length() + (object.endsWith("/") ^ true));
            }
            if (this.isPattern(string3)) {
                throw new SftpException(4, string3);
            }
            object = Util.unquote(string3);
            this.sendHARDLINK(Util.str2byte(string, this.fEncoding), Util.str2byte((String)object, this.fEncoding));
            object = new Header();
            object = this.header(this.buf, (Header)object);
            int n = object.length;
            int n2 = object.type;
            this.fill(this.buf, n);
            if (n2 != 101) {
                throw new SftpException(4, "");
            }
            n = this.buf.getInt();
            if (n == 0) {
                return;
            }
            this.throwStatusError(this.buf, n);
            return;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    @Override
    void init() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void lcd(String string) throws SftpException {
        if (!new File(string = this.localAbsolutePath(string)).isDirectory()) {
            throw new SftpException(2, "No such directory");
        }
        try {
            String string2;
            string = string2 = new File(string).getCanonicalPath();
        }
        catch (Exception exception) {}
        this.lcwd = string;
    }

    public String lpwd() {
        return this.lcwd;
    }

    public Vector ls(String string) throws SftpException {
        final Vector vector = new Vector();
        this.ls(string, new LsEntrySelector(){

            @Override
            public int select(LsEntry lsEntry) {
                vector.addElement(lsEntry);
                return 0;
            }
        });
        return vector;
    }

    /*
     * Exception decompiling
     */
    public void ls(String var1_1, LsEntrySelector var2_3) throws SftpException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 9[UNCONDITIONALDOLOOP]
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

    public SftpATTRS lstat(String object) throws SftpException {
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            object = this._lstat(this.isUnique(this.remoteAbsolutePath((String)object)));
            return object;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void mkdir(String object) throws SftpException {
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            this.sendMKDIR(Util.str2byte(this.remoteAbsolutePath((String)object), this.fEncoding), null);
            object = new Header();
            object = this.header(this.buf, (Header)object);
            int n = object.length;
            int n2 = object.type;
            this.fill(this.buf, n);
            if (n2 != 101) {
                throw new SftpException(4, "");
            }
            n = this.buf.getInt();
            if (n == 0) {
                return;
            }
            this.throwStatusError(this.buf, n);
            return;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    public OutputStream put(String string) throws SftpException {
        return this.put(string, (SftpProgressMonitor)null, 0);
    }

    public OutputStream put(String string, int n) throws SftpException {
        return this.put(string, (SftpProgressMonitor)null, n);
    }

    public OutputStream put(String string, SftpProgressMonitor sftpProgressMonitor, int n) throws SftpException {
        return this.put(string, sftpProgressMonitor, n, 0L);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public OutputStream put(String var1_1, SftpProgressMonitor var2_3, int var3_4, long var4_5) throws SftpException {
        block13 : {
            block12 : {
                block11 : {
                    try {
                        ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
                        var1_2 = this.isUnique(this.remoteAbsolutePath((String)var1_1 /* !! */ ));
                        if (this.isRemoteDir(var1_2)) {
                            var2_9 = new StringBuilder();
                            var2_9.append(var1_2);
                            var2_9.append(" is a directory");
                            throw new SftpException(4, var2_9.toString());
                        }
                        var10_12 = Util.str2byte(var1_2, this.fEncoding);
                        if (var3_10 != true && var3_10 != 2) break block11;
                    }
                    catch (Exception var1_7) {
                        if (var1_7 instanceof SftpException) {
                            throw (SftpException)var1_7;
                        }
                        if (var1_7 instanceof Throwable == false) throw new SftpException(4, "");
                        throw new SftpException(4, "", var1_7);
                    }
                    try {
                        var8_13 = this._stat(var10_12).getSize();
                        break block12;
                    }
                    catch (Exception var11_16) {}
                }
                var8_13 = 0L;
            }
            if (var2_9 == null) ** GOTO lbl26
            var2_9.init(0, "-", var1_2, -1L);
lbl26: // 2 sources:
            if (var3_10 == false) {
                this.sendOPENW(var10_12);
            } else {
                this.sendOPENA(var10_12);
            }
            var1_3 = new Header();
            var1_4 = this.header(this.buf, var1_3);
            var6_14 = var1_4.length;
            var7_15 = var1_4.type;
            this.fill(this.buf, var6_14);
            if (var7_15 == 101 || var7_15 == 102) break block13;
            throw new SftpException(4, "");
        }
        if (var7_15 != 101) ** GOTO lbl43
        var6_14 = this.buf.getInt();
        this.throwStatusError(this.buf, var6_14);
lbl43: // 2 sources:
        var1_5 = this.buf.getString();
        if (var3_10 == true || var3_10 != 2) return new OutputStream(new long[]{var4_11 += var8_13}, (SftpProgressMonitor)var2_9){
            private int _ackid = 0;
            byte[] _data = new byte[1];
            private int ackcount = 0;
            private int[] ackid = new int[1];
            private Header header = new Header();
            private boolean init = true;
            private boolean isClosed = false;
            private int startid = 0;
            final /* synthetic */ long[] val$_offset;
            final /* synthetic */ SftpProgressMonitor val$monitor;
            private int writecount = 0;
            {
                this.val$_offset = arrl;
                this.val$monitor = sftpProgressMonitor;
            }

            @Override
            public void close() throws IOException {
                if (this.isClosed) {
                    return;
                }
                this.flush();
                if (this.val$monitor != null) {
                    this.val$monitor.end();
                }
                try {
                    ChannelSftp.this._sendCLOSE(var1_5, this.header);
                    this.isClosed = true;
                    return;
                }
                catch (Exception exception) {
                    throw new IOException(exception.toString());
                }
                catch (IOException iOException) {
                    throw iOException;
                }
            }

            @Override
            public void flush() throws IOException {
                if (this.isClosed) {
                    throw new IOException("stream already closed");
                }
                if (!this.init) {
                    try {
                        while (this.writecount > this.ackcount) {
                            if (!ChannelSftp.this.checkStatus(null, this.header)) {
                                return;
                            }
                            ++this.ackcount;
                        }
                    }
                    catch (SftpException sftpException) {
                        throw new IOException(sftpException.toString());
                    }
                }
            }

            @Override
            public void write(int n) throws IOException {
                this._data[0] = (byte)n;
                this.write(this._data, 0, 1);
            }

            @Override
            public void write(byte[] arrby) throws IOException {
                this.write(arrby, 0, arrby.length);
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void write(byte[] var1_1, int var2_4, int var3_5) throws IOException {
                if (this.init) {
                    this.startid = ChannelSftp.access$000(ChannelSftp.this);
                    this._ackid = ChannelSftp.access$000(ChannelSftp.this);
                    this.init = false;
                }
                if (this.isClosed) {
                    throw new IOException("stream already closed");
                }
                var4_6 = var2_4;
                var2_4 = var3_5;
                block4 : do lbl-1000: // 4 sources:
                {
                    block9 : {
                        if (var2_4 <= 0) ** GOTO lbl24
                        var6_8 = ChannelSftp.access$100(ChannelSftp.this, var1_5, this.val$_offset[0], var1_1, var4_6, var2_4);
                        ++this.writecount;
                        var7_9 = this.val$_offset;
                        var7_9[0] = var7_9[0] + (long)var6_8;
                        var5_7 = var4_6 + var6_8;
                        var6_8 = var2_4 - var6_8;
                        try {
                            if (ChannelSftp.access$000(ChannelSftp.this) - 1 == this.startid) break block9;
                            var4_6 = var5_7;
                            var2_4 = var6_8;
                            if (ChannelSftp.access$200(ChannelSftp.this).available() < 1024) ** GOTO lbl-1000
                            break block9;
lbl24: // 1 sources:
                            if (this.val$monitor == null) return;
                            if (this.val$monitor.count(var3_5) != false) return;
                            this.close();
                            throw new IOException("canceled");
                        }
                        catch (Exception var1_2) {
                            throw new IOException(var1_2.toString());
                        }
                        catch (IOException var1_3) {
                            throw var1_3;
                        }
                    }
                    do {
                        var4_6 = var5_7;
                        var2_4 = var6_8;
                        if (ChannelSftp.access$200(ChannelSftp.this).available() <= 0) ** GOTO lbl-1000
                        var4_6 = var5_7;
                        var2_4 = var6_8;
                        if (!ChannelSftp.access$300(ChannelSftp.this, this.ackid, this.header)) continue block4;
                        this._ackid = this.ackid[0];
                        if (this.startid > this._ackid) throw new SftpException(4, "");
                        if (this._ackid > ChannelSftp.access$000(ChannelSftp.this) - 1) {
                            throw new SftpException(4, "");
                        }
                        ++this.ackcount;
                    } while (true);
                    break;
                } while (true);
            }
        };
        return new /* invalid duplicate definition of identical inner class */;
    }

    public void put(InputStream inputStream, String string) throws SftpException {
        this.put(inputStream, string, null, 0);
    }

    public void put(InputStream inputStream, String string, int n) throws SftpException {
        this.put(inputStream, string, null, n);
    }

    public void put(InputStream inputStream, String string, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        this.put(inputStream, string, sftpProgressMonitor, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void put(InputStream object, String object2, SftpProgressMonitor sftpProgressMonitor, int n) throws SftpException {
        block13 : {
            block11 : {
                block12 : {
                    object3 = object2;
                    ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
                    object3 = object2;
                    object2 = this.remoteAbsolutePath((String)object2);
                    object3 = this.glob_remote((String)object2);
                    n2 = object3.size();
                    if (n2 == 1) break block11;
                    if (n2 == 0) {
                        if (this.isPattern((String)object2)) {
                            throw new SftpException(4, (String)object2);
                        }
                        object = Util.unquote((String)object2);
                        break block12;
                    }
                    object = object2;
                }
                try {
                    throw new SftpException(4, object3.toString());
                }
                catch (Exception exception) {
                    break block13;
                }
            }
            string = (String)object3.elementAt(0);
            if (sftpProgressMonitor == null) ** GOTO lbl28
            object3 = string;
            sftpProgressMonitor.init(0, "-", string, -1L);
lbl28: // 2 sources:
            object3 = string;
            this._put((InputStream)object, string, sftpProgressMonitor, n);
            return;
            catch (Exception exception) {
                object = object2;
                object2 = exception;
            }
            break block13;
            catch (Exception exception) {
                object = object3;
            }
        }
        if (object2 instanceof SftpException) {
            object2 = (SftpException)object2;
            if (object2.id != 4) throw object2;
            if (this.isRemoteDir((String)object) == false) throw object2;
            object2 = new StringBuilder();
            object2.append((String)object);
            object2.append(" is a directory");
            throw new SftpException(4, object2.toString());
        }
        if (object2 instanceof Throwable == false) throw new SftpException(4, object2.toString());
        throw new SftpException(4, object2.toString(), (Throwable)object2);
    }

    public void put(String string, String string2) throws SftpException {
        this.put(string, string2, null, 0);
    }

    public void put(String string, String string2, int n) throws SftpException {
        this.put(string, string2, null, n);
    }

    public void put(String string, String string2, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        this.put(string, string2, sftpProgressMonitor, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public void put(String var1_1, String var2_5, SftpProgressMonitor var3_6, int var4_7) throws SftpException {
        block35 : {
            block31 : {
                block33 : {
                    block29 : {
                        block30 : {
                            block34 : {
                                block32 : {
                                    ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
                                    var1_1 = this.localAbsolutePath((String)var1_1);
                                    var2_6 = this.remoteAbsolutePath((String)var2_6);
                                    var18_9 = this.glob_remote((String)var2_6);
                                    var5_10 = var18_9.size();
                                    var6_11 = true;
                                    if (var5_10 != 1) {
                                        if (var5_10 == 0) {
                                            if (this.isPattern((String)var2_6)) {
                                                throw new SftpException(4, (String)var2_6);
                                            }
                                            Util.unquote((String)var2_6);
                                        }
                                        throw new SftpException(4, var18_9.toString());
                                    }
                                    var2_6 = (String)var18_9.elementAt(0);
                                    var11_12 = this.isRemoteDir((String)var2_6);
                                    var18_9 = this.glob_local((String)var1_1);
                                    var10_13 = var18_9.size();
                                    if (var11_12) {
                                        var1_1 = var2_6;
                                        if (!var2_6.endsWith("/")) {
                                            var1_1 = new StringBuilder();
                                            var1_1.append((String)var2_6);
                                            var1_1.append("/");
                                            var1_1 = var1_1.toString();
                                        }
                                        var2_6 = new StringBuffer((String)var1_1);
                                        break block29;
                                    }
                                    if (var10_13 <= 1) break block30;
                                    throw new SftpException(4, "Copying multiple files, but the destination is missing or a file.");
lbl29: // 2 sources:
                                    if (var5_10 >= var10_13) ** GOTO lbl77
                                    var20_21 = (String)var18_9.elementAt(var5_10);
                                    if (var11_12) {
                                        var7_14 = var8_15 = var20_21.lastIndexOf(ChannelSftp.file_separatorc);
                                        if (ChannelSftp.fs_is_bs) {
                                            var9_16 = var20_21.lastIndexOf(47);
                                            var7_14 = var8_15;
                                            if (var9_16 != -1) {
                                                var7_14 = var8_15;
                                                if (var9_16 > var8_15) {
                                                    var7_14 = var9_16;
                                                }
                                            }
                                        }
                                        if (var7_14 == -1) {
                                            var2_6.append((String)var20_21);
                                        } else {
                                            var2_6.append(var20_21.substring(var7_14 + 1));
                                        }
                                        var19_20 = var2_6.toString();
                                        var2_6.delete(var1_1.length(), var19_20.length());
                                    } else {
                                        var19_20 = var1_1;
                                    }
                                    if (var4_8 != var6_11) break block31;
                                    var12_17 = this._stat((String)var19_20).getSize();
                                    break block32;
lbl52: // 1 sources:
                                    var12_17 = 0L;
                                }
                                var16_19 = new File((String)var20_21).length();
                                if (var16_19 >= var12_17) break block33;
                                var1_1 = new StringBuilder();
                                var1_1.append("failed to resume for ");
                                var1_1.append((String)var19_20);
                                throw new SftpException(4, var1_1.toString());
lbl61: // 2 sources:
                                if (var3_7 == null) break block34;
                                var3_7.init(0, (String)var20_21, (String)var19_20, new File((String)var20_21).length());
                                if (var4_8 != true) break block34;
                                var3_7.count(var14_18);
                            }
                            var20_21 = new FileInputStream((String)var20_21);
                            this._put((InputStream)var20_21, (String)var19_20, (SftpProgressMonitor)var3_7, (int)var4_8);
                            if (var20_21 == null) break block35;
                            try {
                                block36 : {
                                    break block36;
lbl73: // 2 sources:
                                    do {
                                        if (var2_6 != null) {
                                            var2_6.close();
                                        }
                                        throw var1_2;
                                        break;
                                    } while (true);
lbl77: // 1 sources:
                                    return;
                                }
                                var20_21.close();
                            }
                            catch (Exception var1_3) {
                                if (var1_3 instanceof SftpException) {
                                    throw (SftpException)var1_3;
                                }
                                if (var1_3 instanceof Throwable) {
                                    throw new SftpException(4, var1_3.toString(), var1_3);
                                }
                                throw new SftpException(4, var1_3.toString());
                            }
                            catch (Exception var21_22) {
                                ** GOTO lbl52
                            }
                        }
                        var1_1 = var2_6;
                        var2_6 = null;
                    }
                    var5_10 = 0;
                    ** GOTO lbl29
                }
                var14_18 = var12_17;
                if (var16_19 != var12_17) ** GOTO lbl61
                return;
            }
            var14_18 = 0L;
            ** GOTO lbl61
        }
        ++var5_10;
        var6_11 = true;
        ** GOTO lbl29
        catch (Throwable var1_4) {
            var2_6 = var20_21;
            ** GOTO lbl73
        }
        catch (Throwable var1_5) {
            var2_6 = null;
            ** continue;
        }
    }

    public String pwd() throws SftpException {
        return this.getCwd();
    }

    public void quit() {
        this.disconnect();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String readlink(String var1_1) throws SftpException {
        try {
            if (this.server_version < 3) {
                throw new SftpException(8, "The remote sshd is too old to support symlink operation.");
            }
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            this.sendREADLINK(Util.str2byte(this.isUnique(this.remoteAbsolutePath((String)var1_1 /* !! */ )), this.fEncoding));
            var1_1 /* !! */  = new Header();
            var1_1 /* !! */  = this.header(this.buf, (Header)var1_1 /* !! */ );
            var2_3 = var1_1 /* !! */ .length;
            var3_4 = var1_1 /* !! */ .type;
            this.fill(this.buf, var2_3);
            if (var3_4 != 101 && var3_4 != 104) {
                throw new SftpException(4, "");
            }
            var1_1 /* !! */  = null;
            if (var3_4 != 104) ** GOTO lbl-1000
            var3_4 = this.buf.getInt();
            ** GOTO lbl20
lbl-1000: // 1 sources:
            {
                var2_3 = this.buf.getInt();
                this.throwStatusError(this.buf, var2_3);
                return null;
lbl20: // 2 sources:
                for (var2_3 = 0; var2_3 < var3_4; ++var2_3) {
                    var1_1 /* !! */  = this.buf.getString();
                    if (this.server_version <= 3) {
                        this.buf.getString();
                    }
                    SftpATTRS.getATTR(this.buf);
                }
            }
        }
lbl26: // 2 sources:
        catch (Exception var1_2) {
            if (var1_2 instanceof SftpException) {
                throw (SftpException)var1_2;
            }
            if (var1_2 instanceof Throwable == false) throw new SftpException(4, "");
            throw new SftpException(4, "", var1_2);
        }
        {
            ** try [egrp 3[TRYBLOCK] [6 : 175->201)] { 
lbl32: // 1 sources:
            return Util.byte2str(var1_1 /* !! */ , this.fEncoding);
        }
    }

    public String realpath(String string) throws SftpException {
        try {
            string = Util.byte2str(this._realpath(this.remoteAbsolutePath(string)), this.fEncoding);
            return string;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void rename(String object, String string) throws SftpException {
        if (this.server_version < 2) {
            throw new SftpException(8, "The remote sshd is too old to support rename operation.");
        }
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            Object object2 = this.remoteAbsolutePath((String)object);
            object = this.remoteAbsolutePath(string);
            string = this.isUnique((String)object2);
            object2 = this.glob_remote((String)object);
            int n = object2.size();
            if (n >= 2) {
                throw new SftpException(4, object2.toString());
            }
            if (n == 1) {
                object = (String)object2.elementAt(0);
            } else {
                if (this.isPattern((String)object)) {
                    throw new SftpException(4, (String)object);
                }
                object = Util.unquote((String)object);
            }
            this.sendRENAME(Util.str2byte(string, this.fEncoding), Util.str2byte((String)object, this.fEncoding));
            object = new Header();
            object = this.header(this.buf, (Header)object);
            n = object.length;
            int n2 = object.type;
            this.fill(this.buf, n);
            if (n2 != 101) {
                throw new SftpException(4, "");
            }
            n = this.buf.getInt();
            if (n == 0) {
                return;
            }
            this.throwStatusError(this.buf, n);
            return;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void rm(String object) throws SftpException {
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            Vector vector = this.glob_remote(this.remoteAbsolutePath((String)object));
            int n = vector.size();
            object = new Header();
            for (int i = 0; i < n; ++i) {
                this.sendREMOVE(Util.str2byte((String)vector.elementAt(i), this.fEncoding));
                object = this.header(this.buf, (Header)object);
                int n2 = object.length;
                int n3 = object.type;
                this.fill(this.buf, n2);
                if (n3 != 101) {
                    throw new SftpException(4, "");
                }
                n2 = this.buf.getInt();
                if (n2 == 0) continue;
                this.throwStatusError(this.buf, n2);
            }
            return;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void rmdir(String object) throws SftpException {
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            Vector vector = this.glob_remote(this.remoteAbsolutePath((String)object));
            int n = vector.size();
            object = new Header();
            for (int i = 0; i < n; ++i) {
                this.sendRMDIR(Util.str2byte((String)vector.elementAt(i), this.fEncoding));
                object = this.header(this.buf, (Header)object);
                int n2 = object.length;
                int n3 = object.type;
                this.fill(this.buf, n2);
                if (n3 != 101) {
                    throw new SftpException(4, "");
                }
                n2 = this.buf.getInt();
                if (n2 == 0) continue;
                this.throwStatusError(this.buf, n2);
            }
            return;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    public void setBulkRequests(int n) throws JSchException {
        if (n > 0) {
            this.rq = new RequestQueue(n);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setBulkRequests: ");
        stringBuilder.append(n);
        stringBuilder.append(" must be greater than 0.");
        throw new JSchException(stringBuilder.toString());
    }

    public void setFilenameEncoding(String string) throws SftpException {
        int n = this.getServerVersion();
        if (3 <= n && n <= 5 && !string.equals(UTF8)) {
            throw new SftpException(4, "The encoding can not be changed for this sftp server.");
        }
        String string2 = string;
        if (string.equals(UTF8)) {
            string2 = UTF8;
        }
        this.fEncoding = string2;
        this.fEncoding_is_utf8 = this.fEncoding.equals(UTF8);
    }

    public void setMtime(String object, int n) throws SftpException {
        int n2;
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            object = this.glob_remote(this.remoteAbsolutePath((String)object));
            n2 = object.size();
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
        for (int i = 0; i < n2; ++i) {
            String string = (String)object.elementAt(i);
            SftpATTRS sftpATTRS = this._stat(string);
            sftpATTRS.setFLAGS(0);
            sftpATTRS.setACMODTIME(sftpATTRS.getATime(), n);
            this._setStat(string, sftpATTRS);
        }
        return;
    }

    public void setStat(String object, SftpATTRS sftpATTRS) throws SftpException {
        int n;
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            object = this.glob_remote(this.remoteAbsolutePath((String)object));
            n = object.size();
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
        for (int i = 0; i < n; ++i) {
            this._setStat((String)object.elementAt(i), sftpATTRS);
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void start() throws JSchException {
        try {
            byte[] arrby = new PipedOutputStream();
            this.io.setOutputStream((OutputStream)arrby);
            arrby = new Channel.MyPipedInputStream((Channel)this, (PipedOutputStream)arrby, this.rmpsize);
            this.io.setInputStream((InputStream)arrby);
            this.io_in = this.io.in;
            if (this.io_in == null) {
                throw new JSchException("channel is down");
            }
            ((Request)new RequestSftp()).request(this.getSession(), this);
            this.buf = new Buffer(this.lmpsize);
            this.packet = new Packet(this.buf);
            this.obuf = new Buffer(this.rmpsize);
            this.opacket = new Packet(this.obuf);
            this.sendINIT();
            arrby = new Header();
            arrby = this.header(this.buf, (Header)arrby);
            int n = arrby.length;
            if (n > 262144) {
                arrby = new StringBuilder();
                arrby.append("Received message is too long: ");
                arrby.append(n);
                throw new SftpException(4, arrby.toString());
            }
            int n2 = arrby.type;
            this.server_version = arrby.rid;
            this.extensions = new Hashtable();
            if (n > 0) {
                this.fill(this.buf, n);
                while (n > 0) {
                    arrby = this.buf.getString();
                    n2 = arrby.length;
                    byte[] arrby2 = this.buf.getString();
                    n = n - (n2 + 4) - (arrby2.length + 4);
                    this.extensions.put(Util.byte2str(arrby), Util.byte2str(arrby2));
                }
            }
            if (this.extensions.get("posix-rename@openssh.com") != null && this.extensions.get("posix-rename@openssh.com").equals("1")) {
                this.extension_posix_rename = true;
            }
            if (this.extensions.get("statvfs@openssh.com") != null && this.extensions.get("statvfs@openssh.com").equals("2")) {
                this.extension_statvfs = true;
            }
            if (this.extensions.get("hardlink@openssh.com") != null && this.extensions.get("hardlink@openssh.com").equals("1")) {
                this.extension_hardlink = true;
            }
            this.lcwd = new File(".").getCanonicalPath();
            return;
        }
        catch (Exception exception) {
            if (exception instanceof JSchException) {
                throw (JSchException)exception;
            }
            if (exception instanceof Throwable) {
                throw new JSchException(exception.toString(), exception);
            }
            throw new JSchException(exception.toString());
        }
    }

    public SftpATTRS stat(String object) throws SftpException {
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            object = this._stat(this.isUnique(this.remoteAbsolutePath((String)object)));
            return object;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    public SftpStatVFS statVFS(String object) throws SftpException {
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            object = this._statVFS(this.isUnique(this.remoteAbsolutePath((String)object)));
            return object;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void symlink(String object, String string) throws SftpException {
        if (this.server_version < 3) {
            throw new SftpException(8, "The remote sshd is too old to support symlink operation.");
        }
        try {
            ((Channel.MyPipedInputStream)this.io_in).updateReadSide();
            String string2 = this.remoteAbsolutePath((String)object);
            String string3 = this.remoteAbsolutePath(string);
            string = string2 = this.isUnique(string2);
            if (object.charAt(0) != '/') {
                object = this.getCwd();
                string = string2.substring(object.length() + (object.endsWith("/") ^ true));
            }
            if (this.isPattern(string3)) {
                throw new SftpException(4, string3);
            }
            object = Util.unquote(string3);
            this.sendSYMLINK(Util.str2byte(string, this.fEncoding), Util.str2byte((String)object, this.fEncoding));
            object = new Header();
            object = this.header(this.buf, (Header)object);
            int n = object.length;
            int n2 = object.type;
            this.fill(this.buf, n);
            if (n2 != 101) {
                throw new SftpException(4, "");
            }
            n = this.buf.getInt();
            if (n == 0) {
                return;
            }
            this.throwStatusError(this.buf, n);
            return;
        }
        catch (Exception exception) {
            if (exception instanceof SftpException) {
                throw (SftpException)exception;
            }
            if (exception instanceof Throwable) {
                throw new SftpException(4, "", exception);
            }
            throw new SftpException(4, "");
        }
    }

    public String version() {
        return this.version;
    }

    class Header {
        int length;
        int rid;
        int type;

        Header() {
        }
    }

    public class LsEntry
    implements Comparable {
        private SftpATTRS attrs;
        private String filename;
        private String longname;

        LsEntry(String string, String string2, SftpATTRS sftpATTRS) {
            this.setFilename(string);
            this.setLongname(string2);
            this.setAttrs(sftpATTRS);
        }

        public int compareTo(Object object) throws ClassCastException {
            if (object instanceof LsEntry) {
                return this.filename.compareTo(((LsEntry)object).getFilename());
            }
            throw new ClassCastException("a decendent of LsEntry must be given.");
        }

        public SftpATTRS getAttrs() {
            return this.attrs;
        }

        public String getFilename() {
            return this.filename;
        }

        public String getLongname() {
            return this.longname;
        }

        void setAttrs(SftpATTRS sftpATTRS) {
            this.attrs = sftpATTRS;
        }

        void setFilename(String string) {
            this.filename = string;
        }

        void setLongname(String string) {
            this.longname = string;
        }

        public String toString() {
            return this.longname;
        }
    }

    public static interface LsEntrySelector {
        public static final int BREAK = 1;
        public static final int CONTINUE = 0;

        public int select(LsEntry var1);
    }

    private class RequestQueue {
        int count;
        int head;
        RequestQueue$Request[] rrq = null;

        RequestQueue(int n) {
            this.rrq = new RequestQueue$Request[n];
            for (n = 0; n < this.rrq.length; ++n) {
                this.rrq[n] = new RequestQueue$Request();
            }
            this.init();
        }

        void add(int n, long l, int n2) {
            int n3;
            if (this.count == 0) {
                this.head = 0;
            }
            int n4 = n3 = this.head + this.count;
            if (n3 >= this.rrq.length) {
                n4 = n3 - this.rrq.length;
            }
            this.rrq[n4].id = n;
            this.rrq[n4].offset = l;
            this.rrq[n4].length = n2;
            ++this.count;
        }

        void cancel(Header header, Buffer buffer) throws IOException {
            int n = this.count;
            for (int i = 0; i < n; ++i) {
                header = ChannelSftp.this.header(buffer, header);
                int n2 = header.length;
                for (int j = 0; j < this.rrq.length; ++j) {
                    if (this.rrq[j].id != header.rid) continue;
                    this.rrq[j].id = 0;
                    break;
                }
                ChannelSftp.this.skip(n2);
            }
            this.init();
        }

        int count() {
            return this.count;
        }

        RequestQueue$Request get(int n) throws RequestQueue$OutOfOrderException, SftpException {
            int n2 = this.count;
            int n3 = 1;
            this.count = n2 - 1;
            n2 = this.head++;
            if (this.head == this.rrq.length) {
                this.head = 0;
            }
            if (this.rrq[n2].id != n) {
                long l;
                block4 : {
                    l = this.getOffset();
                    for (n2 = 0; n2 < this.rrq.length; ++n2) {
                        if (this.rrq[n2].id != n) continue;
                        this.rrq[n2].id = 0;
                        n2 = n3;
                        break block4;
                    }
                    n2 = 0;
                }
                if (n2 != 0) {
                    throw new RequestQueue$OutOfOrderException(l);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("RequestQueue: unknown request id ");
                stringBuilder.append(n);
                throw new SftpException(4, stringBuilder.toString());
            }
            this.rrq[n2].id = 0;
            return this.rrq[n2];
        }

        long getOffset() {
            long l = Long.MAX_VALUE;
            for (int i = 0; i < this.rrq.length; ++i) {
                long l2;
                if (this.rrq[i].id == 0) {
                    l2 = l;
                } else {
                    l2 = l;
                    if (l > this.rrq[i].offset) {
                        l2 = this.rrq[i].offset;
                    }
                }
                l = l2;
            }
            return l;
        }

        void init() {
            this.count = 0;
            this.head = 0;
        }

        int size() {
            return this.rrq.length;
        }
    }

    class RequestQueue$OutOfOrderException
    extends Exception {
        long offset;

        RequestQueue$OutOfOrderException(long l) {
            this.offset = l;
        }
    }

    class RequestQueue$Request {
        int id;
        long length;
        long offset;

        RequestQueue$Request() {
        }
    }

}
