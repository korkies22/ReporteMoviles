/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jgss;

import com.jcraft.jsch.GSSContext;
import com.jcraft.jsch.JSchException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.MessageProp;
import org.ietf.jgss.Oid;

public class GSSContextKrb5
implements GSSContext {
    private static final String pUseSubjectCredsOnly = "javax.security.auth.useSubjectCredsOnly";
    private static String useSubjectCredsOnly = GSSContextKrb5.getSystemProperty("javax.security.auth.useSubjectCredsOnly");
    private org.ietf.jgss.GSSContext context = null;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String getSystemProperty(String string) {
        try {
            return System.getProperty(string);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static void setSystemProperty(String string, String string2) {
        try {
            System.setProperty(string, string2);
            return;
        }
        catch (Exception exception) {
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
    public void create(String charSequence, String charSequence2) throws JSchException {
        Oid oid2;
        GSSManager gSSManager;
        Oid oid;
        block4 : {
            try {
                oid = new Oid("1.2.840.113554.1.2.2");
                oid2 = new Oid("1.2.840.113554.1.2.2.1");
                gSSManager = GSSManager.getInstance();
                charSequence2 = charSequence = InetAddress.getByName((String)charSequence2).getCanonicalHostName();
                break block4;
            }
            catch (GSSException gSSException) {
                throw new JSchException(gSSException.toString());
            }
            catch (UnknownHostException unknownHostException) {}
        }
        charSequence = new StringBuilder();
        charSequence.append("host/");
        charSequence.append((String)charSequence2);
        this.context = gSSManager.createContext(gSSManager.createName(charSequence.toString(), oid2), oid, null, 0);
        this.context.requestMutualAuth(true);
        this.context.requestConf(true);
        this.context.requestInteg(true);
        this.context.requestCredDeleg(true);
        this.context.requestAnonymity(false);
    }

    @Override
    public void dispose() {
        try {
            this.context.dispose();
            return;
        }
        catch (GSSException gSSException) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public byte[] getMIC(byte[] arrby, int n, int n2) {
        try {
            MessageProp messageProp = new MessageProp(0, true);
            return this.context.getMIC(arrby, n, n2, messageProp);
        }
        catch (GSSException gSSException) {
            return null;
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public byte[] init(byte[] var1_1, int var2_5, int var3_6) throws JSchException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
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

    @Override
    public boolean isEstablished() {
        return this.context.isEstablished();
    }
}
