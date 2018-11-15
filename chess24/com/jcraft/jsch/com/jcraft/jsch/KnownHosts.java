/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.HostKeyRepository;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.MAC;
import com.jcraft.jsch.Random;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.jcraft.jsch.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Vector;

public class KnownHosts
implements HostKeyRepository {
    private static final String _known_hosts = "known_hosts";
    private static final byte[] cr;
    private static final byte[] space;
    private MAC hmacsha1 = null;
    private JSch jsch = null;
    private String known_hosts = null;
    private Vector pool = null;

    static {
        space = new byte[]{32};
        cr = Util.str2byte("\n");
    }

    KnownHosts(JSch jSch) {
        this.jsch = jSch;
        this.hmacsha1 = this.getHMACSHA1();
        this.pool = new Vector();
    }

    private void addInvalidLine(String object) throws JSchException {
        object = new HostKey((String)object, 6, null);
        this.pool.addElement(object);
    }

    private String deleteSubString(String string, String charSequence) {
        int n;
        int n2 = charSequence.length();
        int n3 = string.length();
        int n4 = 0;
        while (n4 < n3 && (n = string.indexOf(44, n4)) != -1) {
            if (!charSequence.equals(string.substring(n4, n))) {
                n4 = n + 1;
                continue;
            }
            charSequence = new StringBuilder();
            charSequence.append(string.substring(0, n4));
            charSequence.append(string.substring(n + 1));
            return charSequence.toString();
        }
        if (string.endsWith((String)charSequence) && n3 - n4 == n2) {
            n4 = n2 == n3 ? 0 : n3 - n2 - 1;
            return string.substring(0, n4);
        }
        return string;
    }

    private MAC getHMACSHA1() {
        if (this.hmacsha1 == null) {
            try {
                JSch jSch = this.jsch;
                this.hmacsha1 = (MAC)Class.forName(JSch.getConfig("hmac-sha1")).newInstance();
            }
            catch (Exception exception) {
                PrintStream printStream = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("hmacsha1: ");
                stringBuilder.append(exception);
                printStream.println(stringBuilder.toString());
            }
        }
        return this.hmacsha1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void add(HostKey object, UserInfo object2) {
        int n = object.type;
        Object object3 = object.getHost();
        Object object4 = object.key;
        object4 = this.pool;
        synchronized (object4) {
            boolean bl = false;
            n = 0;
            do {
                if (n < this.pool.size()) {
                    HostKey hostKey = (HostKey)this.pool.elementAt(n);
                    if (hostKey.isMatched((String)object3)) {
                        int n2 = hostKey.type;
                    }
                } else {
                    // MONITOREXIT [3, 19, 5] lbl15 : MonitorExitStatement: MONITOREXIT : var9_6
                    this.pool.addElement(object);
                    object = this.getKnownHostsRepositoryID();
                    if (object != null) {
                        boolean bl2;
                        object3 = new File(Util.checkTilde((String)object));
                        if (!object3.exists()) {
                            bl2 = bl;
                            if (object2 != null) {
                                object4 = new StringBuilder();
                                object4.append((String)object);
                                object4.append(" does not exist.\n");
                                object4.append("Are you sure you want to create it?");
                                boolean bl3 = object2.promptYesNo(object4.toString());
                                object3 = object3.getParentFile();
                                bl2 = bl3;
                                if (bl3) {
                                    bl2 = bl3;
                                    if (object3 != null) {
                                        bl2 = bl3;
                                        if (!object3.exists()) {
                                            object4 = new StringBuilder();
                                            object4.append("The parent directory ");
                                            object4.append(object3);
                                            object4.append(" does not exist.\n");
                                            object4.append("Are you sure you want to create it?");
                                            bl2 = bl3 = object2.promptYesNo(object4.toString());
                                            if (bl3) {
                                                if (!object3.mkdirs()) {
                                                    object4 = new StringBuilder();
                                                    object4.append(object3);
                                                    object4.append(" has not been created.");
                                                    object2.showMessage(object4.toString());
                                                    bl2 = false;
                                                } else {
                                                    object4 = new StringBuilder();
                                                    object4.append(object3);
                                                    object4.append(" has been succesfully created.\nPlease check its access permission.");
                                                    object2.showMessage(object4.toString());
                                                    bl2 = bl3;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (object3 == null) {
                                    bl2 = bl;
                                }
                            }
                        } else {
                            bl2 = true;
                        }
                        if (bl2) {
                            try {
                                this.sync((String)object);
                                return;
                            }
                            catch (Exception exception) {
                                object2 = System.err;
                                object3 = new StringBuilder();
                                object3.append("sync known_hosts: ");
                                object3.append(exception);
                                object2.println(object3.toString());
                            }
                        }
                    }
                    return;
                }
                ++n;
            } while (true);
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
    public int check(String string, byte[] arrby) {
        if (string == null) {
            return 1;
        }
        try {}
        catch (JSchException jSchException) {
            return 1;
        }
        HostKey hostKey = new HostKey(string, 0, arrby);
        Vector vector = this.pool;
        // MONITORENTER : vector
        int n = 1;
        int n2 = 0;
        do {
            if (n2 >= this.pool.size()) {
                // MONITOREXIT : vector
                if (n != true) return n;
                if (!string.startsWith("[")) return n;
                if (string.indexOf("]:") <= 1) return n;
                return this.check(string.substring(1, string.indexOf("]:")), arrby);
            }
            HostKey hostKey2 = (HostKey)this.pool.elementAt(n2);
            int n3 = n;
            if (hostKey2.isMatched(string)) {
                n3 = n;
                if (hostKey2.type == hostKey.type) {
                    if (Util.array_equals(hostKey2.key, arrby)) {
                        // MONITOREXIT : vector
                        return 0;
                    }
                    n3 = 2;
                }
            }
            ++n2;
            n = n3;
        } while (true);
    }

    HostKey createHashedHostKey(String object, byte[] arrby) throws JSchException {
        object = new HashedHostKey((String)object, arrby);
        object.hash();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dump(OutputStream outputStream) throws IOException {
        int n;
        try {
            Vector vector = this.pool;
            synchronized (vector) {
                n = 0;
            }
        }
        catch (Exception exception) {
            System.err.println(exception);
            return;
        }
        {
            while (n < this.pool.size()) {
                HostKey hostKey = (HostKey)this.pool.elementAt(n);
                String string = hostKey.getMarker();
                String string2 = hostKey.getHost();
                String string3 = hostKey.getType();
                String string4 = hostKey.getComment();
                if (string3.equals("UNKNOWN")) {
                    outputStream.write(Util.str2byte(string2));
                    outputStream.write(cr);
                } else {
                    if (string.length() != 0) {
                        outputStream.write(Util.str2byte(string));
                        outputStream.write(space);
                    }
                    outputStream.write(Util.str2byte(string2));
                    outputStream.write(space);
                    outputStream.write(Util.str2byte(string3));
                    outputStream.write(space);
                    outputStream.write(Util.str2byte(hostKey.getKey()));
                    if (string4 != null) {
                        outputStream.write(space);
                        outputStream.write(Util.str2byte(string4));
                    }
                    outputStream.write(cr);
                }
                ++n;
            }
            return;
        }
    }

    @Override
    public HostKey[] getHostKey() {
        return this.getHostKey(null, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public HostKey[] getHostKey(String object, String arrhostKey) {
        Vector vector = this.pool;
        synchronized (vector) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            int n = 0;
            do {
                Object object2;
                if (n < this.pool.size()) {
                    object2 = (HostKey)this.pool.elementAt(n);
                    if (object2.type != 6 && (object == null || object2.isMatched((String)object) && (arrhostKey == null || object2.getType().equals(arrhostKey)))) {
                        arrayList.add(object2);
                    }
                } else {
                    object2 = new HostKey[arrayList.size()];
                    for (n = 0; n < arrayList.size(); ++n) {
                        object2[n] = (HostKey)arrayList.get(n);
                    }
                    if (object == null) return object2;
                    if (!object.startsWith("[")) return object2;
                    if (object.indexOf("]:") <= 1) return object2;
                    arrhostKey = this.getHostKey(object.substring(1, object.indexOf("]:")), (String)arrhostKey);
                    if (arrhostKey.length <= 0) return object2;
                    object = new HostKey[((Object)object2).length + arrhostKey.length];
                    System.arraycopy(object2, 0, object, 0, ((Object)object2).length);
                    System.arraycopy(arrhostKey, 0, object, ((Object)object2).length, arrhostKey.length);
                    return object;
                }
                ++n;
            } while (true);
        }
    }

    String getKnownHostsFile() {
        return this.known_hosts;
    }

    @Override
    public String getKnownHostsRepositoryID() {
        return this.known_hosts;
    }

    @Override
    public void remove(String string, String string2) {
        this.remove(string, string2, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void remove(String string, String string2, byte[] arrby) {
        Vector vector = this.pool;
        // MONITORENTER : vector
        int n = 0;
        boolean bl = false;
        do {
            boolean bl2;
            block10 : {
                block11 : {
                    block8 : {
                        HostKey hostKey;
                        String string3;
                        block9 : {
                            if (n >= this.pool.size()) break block8;
                            hostKey = (HostKey)this.pool.elementAt(n);
                            if (string == null) break block9;
                            bl2 = bl;
                            if (!hostKey.isMatched(string)) break block10;
                            if (string2 == null) break block9;
                            bl2 = bl;
                            if (!hostKey.getType().equals(string2)) break block10;
                            if (arrby == null) break block9;
                            bl2 = bl;
                            if (!Util.array_equals(arrby, hostKey.key)) break block10;
                        }
                        if (!((string3 = hostKey.getHost()).equals(string) || hostKey instanceof HashedHostKey && ((HashedHostKey)hostKey).isHashed())) {
                            hostKey.host = this.deleteSubString(string3, string);
                        } else {
                            this.pool.removeElement(hostKey);
                        }
                        break block11;
                    }
                    // MONITOREXIT : vector
                    if (!bl) return;
                    try {
                        this.sync();
                        return;
                    }
                    catch (Exception exception) {
                        return;
                    }
                }
                bl2 = true;
            }
            ++n;
            bl = bl2;
        } while (true);
    }

    /*
     * Exception decompiling
     */
    void setKnownHosts(InputStream var1_1) throws JSchException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 22[UNCONDITIONALDOLOOP]
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

    void setKnownHosts(String string) throws JSchException {
        try {
            this.known_hosts = string;
            this.setKnownHosts(new FileInputStream(Util.checkTilde(string)));
            return;
        }
        catch (FileNotFoundException fileNotFoundException) {
            return;
        }
    }

    protected void sync() throws IOException {
        if (this.known_hosts != null) {
            this.sync(this.known_hosts);
        }
    }

    protected void sync(String object) throws IOException {
        synchronized (this) {
            if (object == null) {
                return;
            }
            object = new FileOutputStream(Util.checkTilde((String)object));
            this.dump((OutputStream)object);
            object.close();
            return;
        }
    }

    class HashedHostKey
    extends HostKey {
        private static final String HASH_DELIM = "|";
        private static final String HASH_MAGIC = "|1|";
        byte[] hash;
        private boolean hashed;
        byte[] salt;

        HashedHostKey(String string, int n, byte[] arrby) throws JSchException {
            this("", string, n, arrby, null);
        }

        HashedHostKey(String string, String string2, int n, byte[] arrby, String string3) throws JSchException {
            super(string, string2, n, arrby, string3);
            this.hashed = false;
            this.salt = null;
            this.hash = null;
            if (this.host.startsWith(HASH_MAGIC) && this.host.substring(HASH_MAGIC.length()).indexOf(HASH_DELIM) > 0) {
                string = this.host.substring(HASH_MAGIC.length());
                KnownHosts.this = string.substring(0, string.indexOf(HASH_DELIM));
                string = string.substring(string.indexOf(HASH_DELIM) + 1);
                this.salt = Util.fromBase64(Util.str2byte((String)KnownHosts.this), 0, KnownHosts.this.length());
                this.hash = Util.fromBase64(Util.str2byte(string), 0, string.length());
                if (this.salt.length == 20 && this.hash.length == 20) {
                    this.hashed = true;
                    return;
                }
                this.salt = null;
                this.hash = null;
                return;
            }
        }

        HashedHostKey(String string, byte[] arrby) throws JSchException {
            this(string, 0, arrby);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        void hash() {
            byte[] arrby;
            if (this.hashed) {
                return;
            }
            Object object = KnownHosts.this.getHMACSHA1();
            if (this.salt == null) {
                arrby = Session.random;
                // MONITORENTER : arrby
                this.salt = new byte[object.getBlockSize()];
                arrby.fill(this.salt, 0, this.salt.length);
                // MONITOREXIT : arrby
            }
            object.init(this.salt);
            arrby = Util.str2byte(this.host);
            object.update(arrby, 0, arrby.length);
            this.hash = new byte[object.getBlockSize()];
            object.doFinal(this.hash, 0);
            // MONITOREXIT : object
            object = new StringBuilder();
            object.append(HASH_MAGIC);
            object.append(Util.byte2str(Util.toBase64(this.salt, 0, this.salt.length)));
            object.append(HASH_DELIM);
            object.append(Util.byte2str(Util.toBase64(this.hash, 0, this.hash.length)));
            this.host = object.toString();
            this.hashed = true;
        }

        boolean isHashed() {
            return this.hashed;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        boolean isMatched(String arrby) {
            if (!this.hashed) {
                return super.isMatched((String)arrby);
            }
            MAC mAC = KnownHosts.this.getHMACSHA1();
            mAC.init(this.salt);
            arrby = Util.str2byte((String)arrby);
            mAC.update(arrby, 0, arrby.length);
            arrby = new byte[mAC.getBlockSize()];
            mAC.doFinal(arrby, 0);
            boolean bl = Util.array_equals(this.hash, arrby);
            // MONITOREXIT : mAC
            return bl;
        }
    }

}
