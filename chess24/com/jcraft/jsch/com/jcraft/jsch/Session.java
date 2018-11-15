/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelDirectTCPIP;
import com.jcraft.jsch.ChannelForwardedTCPIP;
import com.jcraft.jsch.ChannelSession;
import com.jcraft.jsch.ChannelX11;
import com.jcraft.jsch.Cipher;
import com.jcraft.jsch.Compression;
import com.jcraft.jsch.ConfigRepository;
import com.jcraft.jsch.HASH;
import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.HostKeyRepository;
import com.jcraft.jsch.IO;
import com.jcraft.jsch.Identity;
import com.jcraft.jsch.IdentityFile;
import com.jcraft.jsch.IdentityRepository;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyExchange;
import com.jcraft.jsch.KnownHosts;
import com.jcraft.jsch.MAC;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.PortWatcher;
import com.jcraft.jsch.Proxy;
import com.jcraft.jsch.Random;
import com.jcraft.jsch.ServerSocketFactory;
import com.jcraft.jsch.Signature;
import com.jcraft.jsch.SocketFactory;
import com.jcraft.jsch.UserInfo;
import com.jcraft.jsch.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

public class Session
implements Runnable {
    private static final int PACKET_MAX_SIZE = 262144;
    static final int SSH_MSG_CHANNEL_CLOSE = 97;
    static final int SSH_MSG_CHANNEL_DATA = 94;
    static final int SSH_MSG_CHANNEL_EOF = 96;
    static final int SSH_MSG_CHANNEL_EXTENDED_DATA = 95;
    static final int SSH_MSG_CHANNEL_FAILURE = 100;
    static final int SSH_MSG_CHANNEL_OPEN = 90;
    static final int SSH_MSG_CHANNEL_OPEN_CONFIRMATION = 91;
    static final int SSH_MSG_CHANNEL_OPEN_FAILURE = 92;
    static final int SSH_MSG_CHANNEL_REQUEST = 98;
    static final int SSH_MSG_CHANNEL_SUCCESS = 99;
    static final int SSH_MSG_CHANNEL_WINDOW_ADJUST = 93;
    static final int SSH_MSG_DEBUG = 4;
    static final int SSH_MSG_DISCONNECT = 1;
    static final int SSH_MSG_GLOBAL_REQUEST = 80;
    static final int SSH_MSG_IGNORE = 2;
    static final int SSH_MSG_KEXDH_INIT = 30;
    static final int SSH_MSG_KEXDH_REPLY = 31;
    static final int SSH_MSG_KEXINIT = 20;
    static final int SSH_MSG_KEX_DH_GEX_GROUP = 31;
    static final int SSH_MSG_KEX_DH_GEX_INIT = 32;
    static final int SSH_MSG_KEX_DH_GEX_REPLY = 33;
    static final int SSH_MSG_KEX_DH_GEX_REQUEST = 34;
    static final int SSH_MSG_NEWKEYS = 21;
    static final int SSH_MSG_REQUEST_FAILURE = 82;
    static final int SSH_MSG_REQUEST_SUCCESS = 81;
    static final int SSH_MSG_SERVICE_ACCEPT = 6;
    static final int SSH_MSG_SERVICE_REQUEST = 5;
    static final int SSH_MSG_UNIMPLEMENTED = 3;
    static final int buffer_margin = 128;
    private static final byte[] keepalivemsg = Util.str2byte("keepalive@jcraft.com");
    private static final byte[] nomoresessions = Util.str2byte("no-more-sessions@openssh.com");
    static Random random;
    private byte[] Ec2s;
    private byte[] Es2c;
    private byte[] IVc2s;
    private byte[] IVs2c;
    private byte[] I_C;
    private byte[] I_S;
    private byte[] K_S;
    private byte[] MACc2s;
    private byte[] MACs2c;
    private byte[] V_C;
    private byte[] V_S;
    boolean agent_forwarding;
    int auth_failures;
    Buffer buf;
    private Cipher c2scipher;
    private int c2scipher_size;
    private MAC c2smac;
    int[] compress_len;
    private Hashtable config;
    private Thread connectThread;
    protected boolean daemon_thread;
    private Compression deflater;
    private GlobalRequestReply grr;
    String[] guess;
    String host;
    private String hostKeyAlias;
    private HostKey hostkey;
    private HostKeyRepository hostkeyRepository;
    private IdentityRepository identityRepository;
    InputStream in;
    private volatile boolean in_kex;
    private volatile boolean in_prompt;
    private Compression inflater;
    private IO io;
    private boolean isAuthed;
    private volatile boolean isConnected;
    JSch jsch;
    private long kex_start_time;
    private Object lock;
    int max_auth_tries;
    String org_host;
    OutputStream out;
    Packet packet;
    byte[] password;
    int port;
    private Proxy proxy;
    private Cipher s2ccipher;
    private int s2ccipher_size;
    private MAC s2cmac;
    private byte[] s2cmac_result1;
    private byte[] s2cmac_result2;
    private int seqi;
    private int seqo;
    private int serverAliveCountMax;
    private int serverAliveInterval;
    private byte[] session_id;
    private Socket socket;
    SocketFactory socket_factory;
    Runnable thread;
    private int timeout;
    int[] uncompress_len;
    private UserInfo userinfo;
    String username;
    boolean x11_forwarding;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Session(JSch jSch, String string, String string2, int n) throws JSchException {
        this.V_C = Util.str2byte("SSH-2.0-JSCH-0.1.54");
        this.seqi = 0;
        this.seqo = 0;
        this.guess = null;
        this.timeout = 0;
        this.isConnected = false;
        this.isAuthed = false;
        this.connectThread = null;
        this.lock = new Object();
        this.x11_forwarding = false;
        this.agent_forwarding = false;
        this.in = null;
        this.out = null;
        this.socket_factory = null;
        this.config = null;
        this.proxy = null;
        this.hostKeyAlias = null;
        this.serverAliveInterval = 0;
        this.serverAliveCountMax = 1;
        this.identityRepository = null;
        this.hostkeyRepository = null;
        this.daemon_thread = false;
        this.kex_start_time = 0L;
        this.max_auth_tries = 6;
        this.auth_failures = 0;
        this.host = "127.0.0.1";
        this.org_host = "127.0.0.1";
        this.port = 22;
        this.username = null;
        this.password = null;
        this.in_kex = false;
        this.in_prompt = false;
        this.uncompress_len = new int[1];
        this.compress_len = new int[1];
        this.s2ccipher_size = 8;
        this.c2scipher_size = 8;
        this.grr = new GlobalRequestReply();
        this.hostkey = null;
        this.jsch = jSch;
        this.buf = new Buffer();
        this.packet = new Packet(this.buf);
        this.username = string;
        this.host = string2;
        this.org_host = string2;
        this.port = n;
        this.applyConfig();
        if (this.username == null) {
            try {
                this.username = (String)System.getProperties().get("user.name");
            }
            catch (SecurityException securityException) {}
        }
        if (this.username == null) {
            throw new JSchException("username is not given.");
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private int _setPortForwardingR(String charSequence, int n) throws JSchException {
        int n3;
        int n2;
        GlobalRequestReply globalRequestReply = this.grr;
        // MONITORENTER : globalRequestReply
        Buffer buffer = new Buffer(100);
        Packet packet = new Packet(buffer);
        charSequence = ChannelForwardedTCPIP.normalize((String)charSequence);
        this.grr.setThread(Thread.currentThread());
        this.grr.setPort(n);
        try {
            packet.reset();
            buffer.putByte((byte)80);
            buffer.putString(Util.str2byte("tcpip-forward"));
            buffer.putByte((byte)1);
            buffer.putString(Util.str2byte((String)charSequence));
            buffer.putInt(n);
            this.write(packet);
            n2 = 0;
            n3 = this.grr.getReply();
        }
        catch (Exception exception) {
            this.grr.setThread(null);
            if (!(exception instanceof Throwable)) throw new JSchException(exception.toString());
            throw new JSchException(exception.toString(), exception);
        }
        do {
            if (n2 < 10 && n3 == -1) {
                Thread.sleep(1000L);
            } else {
                this.grr.setThread(null);
                if (n3 != 1) {
                    charSequence = new StringBuilder();
                    charSequence.append("remote port forwarding failed for listen port ");
                    charSequence.append(n);
                    throw new JSchException(charSequence.toString());
                }
                n = this.grr.getPort();
                // MONITOREXIT : globalRequestReply
                return n;
                catch (Exception exception) {}
            }
            ++n2;
            n3 = this.grr.getReply();
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void _write(Packet packet) throws Exception {
        Object object = this.lock;
        synchronized (object) {
            this.encode(packet);
            if (this.io != null) {
                this.io.put(packet);
                ++this.seqo;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void applyConfig() throws JSchException {
        String string;
        ConfigRepository.Config config;
        String string2;
        String string3;
        String[] arrstring;
        String string4;
        Object object;
        String string5;
        int n;
        String string6;
        ConfigRepository configRepository = this.jsch.getConfigRepository();
        if (configRepository == null) {
            return;
        }
        config = configRepository.getConfig(this.org_host);
        String string7 = config.getUser();
        if (string7 != null) {
            this.username = string7;
        }
        if ((string4 = config.getHostname()) != null) {
            this.host = string4;
        }
        if ((n = config.getPort()) != -1) {
            this.port = n;
        }
        this.checkConfig(config, "kex");
        this.checkConfig(config, "server_host_key");
        this.checkConfig(config, "cipher.c2s");
        this.checkConfig(config, "cipher.s2c");
        this.checkConfig(config, "mac.c2s");
        this.checkConfig(config, "mac.s2c");
        this.checkConfig(config, "compression.c2s");
        this.checkConfig(config, "compression.s2c");
        this.checkConfig(config, "compression_level");
        this.checkConfig(config, "StrictHostKeyChecking");
        this.checkConfig(config, "HashKnownHosts");
        this.checkConfig(config, "PreferredAuthentications");
        this.checkConfig(config, "MaxAuthTries");
        this.checkConfig(config, "ClearAllForwardings");
        String string8 = config.getValue("HostKeyAlias");
        if (string8 != null) {
            this.setHostKeyAlias(string8);
        }
        if ((string5 = config.getValue("UserKnownHostsFile")) != null) {
            object = new KnownHosts(this.jsch);
            object.setKnownHosts(string5);
            this.setHostKeyRepository((HostKeyRepository)object);
        }
        if ((arrstring = config.getValues("IdentityFile")) != null) {
            void var3_5;
            String[] arrstring2 = configRepository.getConfig("").getValues("IdentityFile");
            if (arrstring2 != null) {
                n = 0;
                do {
                    String[] arrstring3 = arrstring2;
                    if (n < arrstring2.length) {
                        this.jsch.addIdentity(arrstring2[n]);
                        ++n;
                        continue;
                    }
                    break;
                } while (true);
            } else {
                String[] arrstring4 = new String[]{};
            }
            if (arrstring.length - ((void)var3_5).length > 0) {
                IdentityRepository.Wrapper wrapper = new IdentityRepository.Wrapper(this.jsch.getIdentityRepository(), true);
                for (n = 0; n < arrstring.length; ++n) {
                    void var4_24;
                    block22 : {
                        object = arrstring[n];
                        int n2 = 0;
                        do {
                            Object object2 = object;
                            if (n2 >= ((void)var3_5).length) break block22;
                            if (object.equals(var3_5[n2])) break;
                            ++n2;
                        } while (true);
                        Object var4_23 = null;
                    }
                    if (var4_24 == null) continue;
                    wrapper.add(IdentityFile.newInstance((String)var4_24, null, this.jsch));
                }
                this.setIdentityRepository(wrapper);
            }
        }
        if ((string6 = config.getValue("ServerAliveInterval")) != null) {
            try {
                this.setServerAliveInterval(Integer.parseInt(string6));
            }
            catch (NumberFormatException numberFormatException) {}
        }
        if ((string3 = config.getValue("ConnectTimeout")) != null) {
            try {
                this.setTimeout(Integer.parseInt(string3));
            }
            catch (NumberFormatException numberFormatException) {}
        }
        if ((string2 = config.getValue("MaxAuthTries")) != null) {
            this.setConfig("MaxAuthTries", string2);
        }
        if ((string = config.getValue("ClearAllForwardings")) != null) {
            this.setConfig("ClearAllForwardings", string);
        }
    }

    private void applyConfigChannel(ChannelSession channelSession) throws JSchException {
        Object object = this.jsch.getConfigRepository();
        if (object == null) {
            return;
        }
        String string = (object = object.getConfig(this.org_host)).getValue("ForwardAgent");
        if (string != null) {
            channelSession.setAgentForwarding(string.equals("yes"));
        }
        if ((object = object.getValue("RequestTTY")) != null) {
            channelSession.setPty(object.equals("yes"));
        }
    }

    static boolean checkCipher(String object) {
        try {
            object = (Cipher)Class.forName((String)object).newInstance();
            object.init(0, new byte[object.getBlockSize()], new byte[object.getIVSize()]);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    private String[] checkCiphers(String arrstring) {
        if (arrstring != null) {
            CharSequence charSequence;
            int n;
            Object object;
            if (arrstring.length() == 0) {
                return null;
            }
            if (JSch.getLogger().isEnabled(1)) {
                object = JSch.getLogger();
                charSequence = new StringBuilder();
                charSequence.append("CheckCiphers: ");
                charSequence.append((String)arrstring);
                object.log(1, charSequence.toString());
            }
            charSequence = this.getConfig("cipher.c2s");
            String string = this.getConfig("cipher.s2c");
            object = new Vector();
            arrstring = Util.split((String)arrstring, ",");
            int n2 = 0;
            for (n = 0; n < arrstring.length; ++n) {
                String string2 = arrstring[n];
                if (string.indexOf(string2) == -1 && charSequence.indexOf(string2) == -1 || Session.checkCipher(this.getConfig(string2))) continue;
                object.addElement(string2);
            }
            if (object.size() == 0) {
                return null;
            }
            arrstring = new String[object.size()];
            System.arraycopy(object.toArray(), 0, arrstring, 0, object.size());
            if (JSch.getLogger().isEnabled(1)) {
                for (n = n2; n < arrstring.length; ++n) {
                    object = JSch.getLogger();
                    charSequence = new StringBuilder();
                    charSequence.append(arrstring[n]);
                    charSequence.append(" is not available.");
                    object.log(1, charSequence.toString());
                }
            }
            return arrstring;
        }
        return null;
    }

    private void checkConfig(ConfigRepository.Config object, String string) {
        if ((object = object.getValue(string)) != null) {
            this.setConfig(string, (String)object);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void checkHost(String var1_1, int var2_2, KeyExchange var3_3) throws JSchException {
        block38 : {
            block37 : {
                block36 : {
                    block35 : {
                        var12_4 = this.getConfig("StrictHostKeyChecking");
                        if (this.hostKeyAlias != null) {
                            var1_1 = this.hostKeyAlias;
                        }
                        var13_5 = var3_3.getHostKey();
                        var10_6 = var3_3.getKeyType();
                        var14_7 = var3_3.getFingerPrint();
                        var8_8 = var1_1;
                        if (this.hostKeyAlias == null) {
                            var8_8 = var1_1;
                            if (var2_2 != 22) {
                                var8_8 = new StringBuilder();
                                var8_8.append("[");
                                var8_8.append((String)var1_1);
                                var8_8.append("]:");
                                var8_8.append(var2_2);
                                var8_8 = var8_8.toString();
                            }
                        }
                        var11_9 = this.getHostKeyRepository();
                        this.hostkey = this.getConfig("HashKnownHosts").equals("yes") != false && var11_9 instanceof KnownHosts != false ? ((KnownHosts)var11_9).createHashedHostKey((String)var8_8, var13_5) : new HostKey((String)var8_8, var13_5);
                        // MONITORENTER : var11_9
                        var6_10 = var11_9.check((String)var8_8, var13_5);
                        // MONITOREXIT : var11_9
                        var7_11 = var12_4.equals("ask");
                        var5_12 = 0;
                        if (!var7_11 && !var12_4.equals("yes") || var6_10 != 2) break block35;
                        // MONITORENTER : var11_9
                        var9_13 = var11_9.getKnownHostsRepositoryID();
                        // MONITOREXIT : var11_9
                        var1_1 = var9_13;
                        if (var9_13 == null) {
                            var1_1 = "known_hosts";
                        }
                        if (this.userinfo == null) ** GOTO lbl56
                        var9_13 = new StringBuilder();
                        var9_13.append("WARNING: REMOTE HOST IDENTIFICATION HAS CHANGED!\nIT IS POSSIBLE THAT SOMEONE IS DOING SOMETHING NASTY!\nSomeone could be eavesdropping on you right now (man-in-the-middle attack)!\nIt is also possible that the ");
                        var9_13.append(var10_6);
                        var9_13.append(" host key has just been changed.\n");
                        var9_13.append("The fingerprint for the ");
                        var9_13.append(var10_6);
                        var9_13.append(" key sent by the remote host ");
                        var9_13.append((String)var8_8);
                        var9_13.append(" is\n");
                        var9_13.append(var14_7);
                        var9_13.append(".\n");
                        var9_13.append("Please contact your system administrator.\n");
                        var9_13.append("Add correct host key in ");
                        var9_13.append((String)var1_1);
                        var9_13.append(" to get rid of this message.");
                        var1_1 = var9_13.toString();
                        if (var12_4.equals("ask")) {
                            var9_13 = this.userinfo;
                            var15_14 = new StringBuilder();
                            var15_14.append((String)var1_1);
                            var15_14.append("\nDo you want to delete the old key and insert the new key?");
                            var7_11 = var9_13.promptYesNo(var15_14.toString());
                        } else {
                            this.userinfo.showMessage((String)var1_1);
lbl56: // 2 sources:
                            var7_11 = false;
                        }
                        if (!var7_11) {
                            var1_1 = new StringBuilder();
                            var1_1.append("HostKey has been changed: ");
                            var1_1.append((String)var8_8);
                            throw new JSchException(var1_1.toString());
                        }
                        // MONITORENTER : var11_9
                        var11_9.remove((String)var8_8, var3_3.getKeyAlgorithName(), null);
                        // MONITOREXIT : var11_9
                        var4_15 = 1;
                        break block36;
                    }
                    var4_15 = 0;
                }
                if (var12_4.equals("ask")) break block37;
                var2_2 = var4_15;
                if (!var12_4.equals("yes")) break block38;
            }
            var2_2 = var4_15;
            if (var6_10 != 0) {
                var2_2 = var4_15;
                if (var4_15 == 0) {
                    if (var12_4.equals("yes")) {
                        var1_1 = new StringBuilder();
                        var1_1.append("reject HostKey: ");
                        var1_1.append(this.host);
                        throw new JSchException(var1_1.toString());
                    }
                    if (this.userinfo != null) {
                        var1_1 = this.userinfo;
                        var9_13 = new StringBuilder();
                        var9_13.append("The authenticity of host '");
                        var9_13.append(this.host);
                        var9_13.append("' can't be established.\n");
                        var9_13.append(var10_6);
                        var9_13.append(" key fingerprint is ");
                        var9_13.append(var14_7);
                        var9_13.append(".\n");
                        var9_13.append("Are you sure you want to continue connecting?");
                        if (!var1_1.promptYesNo(var9_13.toString())) {
                            var1_1 = new StringBuilder();
                            var1_1.append("reject HostKey: ");
                            var1_1.append(this.host);
                            throw new JSchException(var1_1.toString());
                        }
                        var2_2 = 1;
                    } else {
                        if (var6_10 == 1) {
                            var1_1 = new StringBuilder();
                            var1_1.append("UnknownHostKey: ");
                            var1_1.append(this.host);
                            var1_1.append(". ");
                            var1_1.append(var10_6);
                            var1_1.append(" key fingerprint is ");
                            var1_1.append(var14_7);
                            throw new JSchException(var1_1.toString());
                        }
                        var1_1 = new StringBuilder();
                        var1_1.append("HostKey has been changed: ");
                        var1_1.append(this.host);
                        throw new JSchException(var1_1.toString());
                    }
                }
            }
        }
        var4_15 = var2_2;
        if (var12_4.equals("no")) {
            var4_15 = var2_2;
            if (1 == var6_10) {
                var4_15 = 1;
            }
        }
        if (var6_10 == 0) {
            var1_1 = var11_9.getHostKey((String)var8_8, var3_3.getKeyAlgorithName());
            var3_3 = Util.byte2str(Util.toBase64(var13_5, 0, var13_5.length));
            for (var2_2 = var5_12; var2_2 < ((Object)var1_1).length; ++var2_2) {
                if (!var1_1[var6_10].getKey().equals(var3_3) || !var1_1[var2_2].getMarker().equals("@revoked")) continue;
                if (this.userinfo != null) {
                    var1_1 = this.userinfo;
                    var3_3 = new StringBuilder();
                    var3_3.append("The ");
                    var3_3.append(var10_6);
                    var3_3.append(" host key for ");
                    var3_3.append(this.host);
                    var3_3.append(" is marked as revoked.\n");
                    var3_3.append("This could mean that a stolen key is being used to ");
                    var3_3.append("impersonate this host.");
                    var1_1.showMessage(var3_3.toString());
                }
                if (JSch.getLogger().isEnabled(1)) {
                    var1_1 = JSch.getLogger();
                    var3_3 = new StringBuilder();
                    var3_3.append("Host '");
                    var3_3.append(this.host);
                    var3_3.append("' has provided revoked key.");
                    var1_1.log(1, var3_3.toString());
                }
                var1_1 = new StringBuilder();
                var1_1.append("revoked HostKey: ");
                var1_1.append(this.host);
                throw new JSchException(var1_1.toString());
            }
        }
        if (var6_10 == 0 && JSch.getLogger().isEnabled(1)) {
            var1_1 = JSch.getLogger();
            var3_3 = new StringBuilder();
            var3_3.append("Host '");
            var3_3.append(this.host);
            var3_3.append("' is known and matches the ");
            var3_3.append(var10_6);
            var3_3.append(" host key");
            var1_1.log(1, var3_3.toString());
        }
        if (var4_15 != 0 && JSch.getLogger().isEnabled(2)) {
            var1_1 = JSch.getLogger();
            var3_3 = new StringBuilder();
            var3_3.append("Permanently added '");
            var3_3.append(this.host);
            var3_3.append("' (");
            var3_3.append(var10_6);
            var3_3.append(") to the list of known hosts.");
            var1_1.log(2, var3_3.toString());
        }
        if (var4_15 == 0) return;
        // MONITORENTER : var11_9
        var11_9.add(this.hostkey, this.userinfo);
        // MONITOREXIT : var11_9
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean checkKex(Session session, String string) {
        try {
            ((KeyExchange)Class.forName(string).newInstance()).init(session, null, null, null, null);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    private String[] checkKexes(String arrstring) {
        if (arrstring != null) {
            StringBuilder stringBuilder;
            int n;
            Object object;
            if (arrstring.length() == 0) {
                return null;
            }
            if (JSch.getLogger().isEnabled(1)) {
                object = JSch.getLogger();
                stringBuilder = new StringBuilder();
                stringBuilder.append("CheckKexes: ");
                stringBuilder.append((String)arrstring);
                object.log(1, stringBuilder.toString());
            }
            object = new Vector();
            arrstring = Util.split((String)arrstring, ",");
            int n2 = 0;
            for (n = 0; n < arrstring.length; ++n) {
                if (Session.checkKex(this, this.getConfig(arrstring[n]))) continue;
                object.addElement(arrstring[n]);
            }
            if (object.size() == 0) {
                return null;
            }
            arrstring = new String[object.size()];
            System.arraycopy(object.toArray(), 0, arrstring, 0, object.size());
            if (JSch.getLogger().isEnabled(1)) {
                for (n = n2; n < arrstring.length; ++n) {
                    object = JSch.getLogger();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(arrstring[n]);
                    stringBuilder.append(" is not available.");
                    object.log(1, stringBuilder.toString());
                }
            }
            return arrstring;
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private String[] checkSignatures(String arrstring) {
        Object object2;
        Object object;
        if (arrstring == null) return null;
        if (arrstring.length() == 0) {
            return null;
        }
        if (JSch.getLogger().isEnabled(1)) {
            object2 = JSch.getLogger();
            object = new StringBuilder();
            object.append("CheckSignatures: ");
            object.append((String)arrstring);
            object2.log(1, object.toString());
        }
        object2 = new Vector();
        arrstring = Util.split((String)arrstring, ",");
        int n = 0;
        int n2 = 0;
        do {
            if (n2 < arrstring.length) {
                object = this.jsch;
                ((Signature)Class.forName(JSch.getConfig(arrstring[n2])).newInstance()).init();
            } else {
                if (object2.size() == 0) {
                    return null;
                }
                arrstring = new String[object2.size()];
                System.arraycopy(object2.toArray(), 0, arrstring, 0, object2.size());
                if (!JSch.getLogger().isEnabled(1)) return arrstring;
                n2 = n;
                while (n2 < arrstring.length) {
                    object2 = JSch.getLogger();
                    object = new StringBuilder();
                    object.append(arrstring[n2]);
                    object.append(" is not available.");
                    object2.log(1, object.toString());
                    ++n2;
                }
                return arrstring;
                catch (Exception exception) {}
                object2.addElement(arrstring[n2]);
            }
            ++n2;
        } while (true);
    }

    private byte[] expandKey(Buffer buffer, byte[] arrby, byte[] arrby2, byte[] arrby3, HASH hASH, int n) throws Exception {
        int n2 = hASH.getBlockSize();
        while (arrby3.length < n) {
            buffer.reset();
            buffer.putMPInt(arrby);
            buffer.putByte(arrby2);
            buffer.putByte(arrby3);
            hASH.update(buffer.buffer, 0, buffer.index);
            byte[] arrby4 = new byte[arrby3.length + n2];
            System.arraycopy(arrby3, 0, arrby4, 0, arrby3.length);
            System.arraycopy(hASH.digest(), 0, arrby4, arrby3.length, n2);
            Util.bzero(arrby3);
            arrby3 = arrby4;
        }
        return arrby3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void initDeflater(String string) throws JSchException {
        if (string.equals("none")) {
            this.deflater = null;
            return;
        }
        String string2 = this.getConfig(string);
        if (string2 == null) return;
        if (!string.equals("zlib")) {
            if (!this.isAuthed) return;
            if (!string.equals("zlib@openssh.com")) return;
        }
        try {
            int n;
            this.deflater = (Compression)Class.forName(string2).newInstance();
            n = 6;
            try {
                int n2;
                n = n2 = Integer.parseInt(this.getConfig("compression_level"));
            }
            catch (Exception exception) {}
            this.deflater.init(1, n);
            return;
        }
        catch (Exception exception) {
            throw new JSchException(exception.toString(), exception);
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            throw new JSchException(noClassDefFoundError.toString(), noClassDefFoundError);
        }
    }

    private void initInflater(String string) throws JSchException {
        if (string.equals("none")) {
            this.inflater = null;
            return;
        }
        String string2 = this.getConfig(string);
        if (string2 != null && (string.equals("zlib") || this.isAuthed && string.equals("zlib@openssh.com"))) {
            try {
                this.inflater = (Compression)Class.forName(string2).newInstance();
                this.inflater.init(0, 0);
                return;
            }
            catch (Exception exception) {
                throw new JSchException(exception.toString(), exception);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Forwarding parseForwarding(String object) throws JSchException {
        Forwarding forwarding;
        Object object2;
        block10 : {
            block9 : {
                object2 = object.split(" ");
                if (((String[])object2).length > 1) {
                    int n;
                    object = new Vector<String>();
                    for (n = 0; n < ((String[])object2).length; ++n) {
                        if (object2[n].length() == 0) continue;
                        object.addElement(object2[n].trim());
                    }
                    object2 = new StringBuffer();
                    n = 0;
                    while (n < object.size()) {
                        int n2;
                        object2.append((String)object.elementAt(n));
                        n = n2 = n + 1;
                        if (n2 >= object.size()) continue;
                        object2.append(":");
                        n = n2;
                    }
                    object = object2.toString();
                }
                forwarding = new Forwarding();
                try {
                    if (object.lastIndexOf(":") == -1) {
                        object2 = new StringBuilder();
                        object2.append("parseForwarding: ");
                        object2.append((String)object);
                        throw new JSchException(object2.toString());
                    }
                    forwarding.hostport = Integer.parseInt(object.substring(object.lastIndexOf(":") + 1));
                    object2 = object.substring(0, object.lastIndexOf(":"));
                    if (object2.lastIndexOf(":") == -1) {
                        object2 = new StringBuilder();
                        object2.append("parseForwarding: ");
                        object2.append((String)object);
                        throw new JSchException(object2.toString());
                    }
                    forwarding.host = object2.substring(object2.lastIndexOf(":") + 1);
                    object = object2.substring(0, object2.lastIndexOf(":"));
                    if (object.lastIndexOf(":") == -1) {
                        forwarding.port = Integer.parseInt((String)object);
                        forwarding.bind_address = "127.0.0.1";
                        return forwarding;
                    }
                    forwarding.port = Integer.parseInt(object.substring(object.lastIndexOf(":") + 1));
                    object2 = object.substring(0, object.lastIndexOf(":"));
                    if (object2.length() == 0) break block9;
                    object = object2;
                    if (!object2.equals("*")) break block10;
                }
                catch (NumberFormatException numberFormatException) {
                    object2 = new StringBuilder();
                    object2.append("parseForwarding: ");
                    object2.append(numberFormatException.toString());
                    throw new JSchException(object2.toString());
                }
            }
            object = "0.0.0.0";
        }
        object2 = object;
        if (object.equals("localhost")) {
            object2 = "127.0.0.1";
        }
        forwarding.bind_address = object2;
        return forwarding;
    }

    private KeyExchange receive_kexinit(Buffer object) throws Exception {
        int n = object.getInt();
        if (n != object.getLength()) {
            object.getByte();
            this.I_S = new byte[object.index - 5];
        } else {
            this.I_S = new byte[n - 1 - object.getByte()];
        }
        System.arraycopy(object.buffer, object.s, this.I_S, 0, this.I_S.length);
        if (!this.in_kex) {
            this.send_kexinit();
        }
        this.guess = KeyExchange.guess(this.I_S, this.I_C);
        if (this.guess == null) {
            throw new JSchException("Algorithm negotiation fail");
        }
        if (!this.isAuthed && (this.guess[2].equals("none") || this.guess[3].equals("none"))) {
            throw new JSchException("NONE Cipher should not be chosen before authentification is successed.");
        }
        try {
            object = (KeyExchange)Class.forName(this.getConfig(this.guess[0])).newInstance();
        }
        catch (Exception exception) {
            throw new JSchException(exception.toString(), exception);
        }
        object.init(this, this.V_S, this.V_C, this.I_S, this.I_C);
        return object;
    }

    private void receive_newkeys(Buffer buffer, KeyExchange keyExchange) throws Exception {
        this.updateKeys(keyExchange);
        this.in_kex = false;
    }

    private void requestPortForwarding() throws JSchException {
        int n;
        if (this.getConfig("ClearAllForwardings").equals("yes")) {
            return;
        }
        Object object = this.jsch.getConfigRepository();
        if (object == null) {
            return;
        }
        object = object.getConfig(this.org_host);
        String[] arrstring = object.getValues("LocalForward");
        int n2 = 0;
        if (arrstring != null) {
            for (n = 0; n < arrstring.length; ++n) {
                this.setPortForwardingL(arrstring[n]);
            }
        }
        if ((object = object.getValues("RemoteForward")) != null) {
            for (n = n2; n < ((String[])object).length; ++n) {
                this.setPortForwardingR(object[n]);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void send_kexinit() throws Exception {
        Object object;
        String[] arrstring;
        String string;
        String[] arrstring2;
        String string2;
        block14 : {
            block15 : {
                if (this.in_kex) {
                    return;
                }
                arrstring = this.getConfig("cipher.c2s");
                string2 = this.getConfig("cipher.s2c");
                object = this.checkCiphers(this.getConfig("CheckCiphers"));
                arrstring2 = arrstring;
                string = string2;
                if (object == null) break block14;
                arrstring2 = arrstring;
                string = string2;
                if (((String[])object).length <= 0) break block14;
                arrstring2 = Util.diffString((String)arrstring, object);
                string2 = Util.diffString(string2, object);
                if (arrstring2 == null) break block15;
                string = string2;
                if (string2 != null) break block14;
            }
            throw new JSchException("There are not any available ciphers.");
        }
        arrstring = this.getConfig("kex");
        object = this.checkKexes(this.getConfig("CheckKexes"));
        string2 = arrstring;
        if (object != null) {
            string2 = arrstring;
            if (((String[])object).length > 0) {
                arrstring = Util.diffString((String)arrstring, object);
                string2 = arrstring;
                if (arrstring == null) {
                    throw new JSchException("There are not any available kexes.");
                }
            }
        }
        object = this.getConfig("server_host_key");
        Object object2 = this.checkSignatures(this.getConfig("CheckSignatures"));
        arrstring = object;
        if (object2 != null) {
            arrstring = object;
            if (((String[])object2).length > 0) {
                arrstring = object = Util.diffString((String)object, (String[])object2);
                if (object == null) {
                    throw new JSchException("There are not any available sig algorithm.");
                }
            }
        }
        this.in_kex = true;
        this.kex_start_time = System.currentTimeMillis();
        object2 = new Buffer();
        Packet packet = new Packet((Buffer)object2);
        packet.reset();
        object2.putByte((byte)20);
        object = random;
        synchronized (object) {
            random.fill(object2.buffer, object2.index, 16);
            object2.skip(16);
        }
        object2.putString(Util.str2byte(string2));
        object2.putString(Util.str2byte((String)arrstring));
        object2.putString(Util.str2byte((String)arrstring2));
        object2.putString(Util.str2byte(string));
        object2.putString(Util.str2byte(this.getConfig("mac.c2s")));
        object2.putString(Util.str2byte(this.getConfig("mac.s2c")));
        object2.putString(Util.str2byte(this.getConfig("compression.c2s")));
        object2.putString(Util.str2byte(this.getConfig("compression.s2c")));
        object2.putString(Util.str2byte(this.getConfig("lang.c2s")));
        object2.putString(Util.str2byte(this.getConfig("lang.s2c")));
        object2.putByte((byte)0);
        object2.putInt(0);
        object2.setOffSet(5);
        this.I_C = new byte[object2.getLength()];
        object2.getByte(this.I_C);
        this.write(packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_KEXINIT sent");
        }
    }

    private void send_newkeys() throws Exception {
        this.packet.reset();
        this.buf.putByte((byte)21);
        this.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_NEWKEYS sent");
        }
    }

    private void start_discard(Buffer buffer, Cipher cipher, MAC mAC, int n, int n2) throws JSchException, IOException {
        if (!cipher.isCBC()) {
            throw new JSchException("Packet corrupt");
        }
        if (n == 262144 || mAC == null) {
            mAC = null;
        }
        for (n = n2 - buffer.index; n > 0; n -= n2) {
            buffer.reset();
            n2 = n > buffer.buffer.length ? buffer.buffer.length : n;
            this.io.getByte(buffer.buffer, 0, n2);
            if (mAC == null) continue;
            mAC.update(buffer.buffer, 0, n2);
        }
        if (mAC != null) {
            mAC.doFinal(buffer.buffer, 0);
        }
        throw new JSchException("Packet corrupt");
    }

    private void updateKeys(KeyExchange object) throws Exception {
        byte[] arrby = object.getK();
        byte[] arrby2 = object.getH();
        object = object.getHash();
        if (this.session_id == null) {
            this.session_id = new byte[arrby2.length];
            System.arraycopy(arrby2, 0, this.session_id, 0, arrby2.length);
        }
        this.buf.reset();
        this.buf.putMPInt(arrby);
        this.buf.putByte(arrby2);
        this.buf.putByte((byte)65);
        this.buf.putByte(this.session_id);
        object.update(this.buf.buffer, 0, this.buf.index);
        this.IVc2s = object.digest();
        int n = this.buf.index - this.session_id.length - 1;
        byte[] arrby3 = this.buf.buffer;
        arrby3[n] = (byte)(arrby3[n] + 1);
        object.update(this.buf.buffer, 0, this.buf.index);
        this.IVs2c = object.digest();
        arrby3 = this.buf.buffer;
        arrby3[n] = (byte)(arrby3[n] + 1);
        object.update(this.buf.buffer, 0, this.buf.index);
        this.Ec2s = object.digest();
        arrby3 = this.buf.buffer;
        arrby3[n] = (byte)(arrby3[n] + 1);
        object.update(this.buf.buffer, 0, this.buf.index);
        this.Es2c = object.digest();
        arrby3 = this.buf.buffer;
        arrby3[n] = (byte)(arrby3[n] + 1);
        object.update(this.buf.buffer, 0, this.buf.index);
        this.MACc2s = object.digest();
        arrby3 = this.buf.buffer;
        arrby3[n] = (byte)(arrby3[n] + 1);
        object.update(this.buf.buffer, 0, this.buf.index);
        this.MACs2c = object.digest();
        try {
            byte[] arrby4;
            this.s2ccipher = (Cipher)Class.forName(this.getConfig(this.guess[3])).newInstance();
            while (this.s2ccipher.getBlockSize() > this.Es2c.length) {
                this.buf.reset();
                this.buf.putMPInt(arrby);
                this.buf.putByte(arrby2);
                this.buf.putByte(this.Es2c);
                object.update(this.buf.buffer, 0, this.buf.index);
                arrby3 = object.digest();
                arrby4 = new byte[this.Es2c.length + arrby3.length];
                System.arraycopy(this.Es2c, 0, arrby4, 0, this.Es2c.length);
                System.arraycopy(arrby3, 0, arrby4, this.Es2c.length, arrby3.length);
                this.Es2c = arrby4;
            }
            this.s2ccipher.init(1, this.Es2c, this.IVs2c);
            this.s2ccipher_size = this.s2ccipher.getIVSize();
            this.s2cmac = (MAC)Class.forName(this.getConfig(this.guess[5])).newInstance();
            this.MACs2c = this.expandKey(this.buf, arrby, arrby2, this.MACs2c, (HASH)object, this.s2cmac.getBlockSize());
            this.s2cmac.init(this.MACs2c);
            this.s2cmac_result1 = new byte[this.s2cmac.getBlockSize()];
            this.s2cmac_result2 = new byte[this.s2cmac.getBlockSize()];
            this.c2scipher = (Cipher)Class.forName(this.getConfig(this.guess[2])).newInstance();
            while (this.c2scipher.getBlockSize() > this.Ec2s.length) {
                this.buf.reset();
                this.buf.putMPInt(arrby);
                this.buf.putByte(arrby2);
                this.buf.putByte(this.Ec2s);
                object.update(this.buf.buffer, 0, this.buf.index);
                arrby3 = object.digest();
                arrby4 = new byte[this.Ec2s.length + arrby3.length];
                System.arraycopy(this.Ec2s, 0, arrby4, 0, this.Ec2s.length);
                System.arraycopy(arrby3, 0, arrby4, this.Ec2s.length, arrby3.length);
                this.Ec2s = arrby4;
            }
            this.c2scipher.init(0, this.Ec2s, this.IVc2s);
            this.c2scipher_size = this.c2scipher.getIVSize();
            this.c2smac = (MAC)Class.forName(this.getConfig(this.guess[4])).newInstance();
            this.MACc2s = this.expandKey(this.buf, arrby, arrby2, this.MACc2s, (HASH)object, this.c2smac.getBlockSize());
            this.c2smac.init(this.MACc2s);
            this.initDeflater(this.guess[6]);
            this.initInflater(this.guess[7]);
            return;
        }
        catch (Exception exception) {
            if (exception instanceof JSchException) {
                throw exception;
            }
            throw new JSchException(exception.toString(), exception);
        }
    }

    void addChannel(Channel channel) {
        channel.setSession(this);
    }

    public void connect() throws JSchException {
        this.connect(this.timeout);
    }

    /*
     * Exception decompiling
     */
    public void connect(int var1_1) throws JSchException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 60[UNCONDITIONALDOLOOP]
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

    public void delPortForwardingL(int n) throws JSchException {
        this.delPortForwardingL("127.0.0.1", n);
    }

    public void delPortForwardingL(String string, int n) throws JSchException {
        PortWatcher.delPort(this, string, n);
    }

    public void delPortForwardingR(int n) throws JSchException {
        this.delPortForwardingR(null, n);
    }

    public void delPortForwardingR(String string, int n) throws JSchException {
        ChannelForwardedTCPIP.delPort(this, string, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void disconnect() {
        block19 : {
            Object object;
            if (!this.isConnected) {
                return;
            }
            if (JSch.getLogger().isEnabled(1)) {
                object = JSch.getLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Disconnecting from ");
                stringBuilder.append(this.host);
                stringBuilder.append(" port ");
                stringBuilder.append(this.port);
                object.log(1, stringBuilder.toString());
            }
            Channel.disconnect(this);
            this.isConnected = false;
            PortWatcher.delPort(this);
            ChannelForwardedTCPIP.delPort(this);
            ChannelX11.removeFakedCookie(this);
            object = this.lock;
            synchronized (object) {
                if (this.connectThread != null) {
                    Thread.yield();
                    this.connectThread.interrupt();
                    this.connectThread = null;
                }
            }
            this.thread = null;
            try {
                if (this.io != null) {
                    if (this.io.in != null) {
                        this.io.in.close();
                    }
                    if (this.io.out != null) {
                        this.io.out.close();
                    }
                    if (this.io.out_ext != null) {
                        this.io.out_ext.close();
                    }
                }
                if (this.proxy == null) {
                    if (this.socket != null) {
                        this.socket.close();
                    }
                    break block19;
                }
                object = this.proxy;
                synchronized (object) {
                    this.proxy.close();
                }
            }
            catch (Exception exception) {}
            this.proxy = null;
        }
        this.io = null;
        this.socket = null;
        this.jsch.removeSession(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void encode(Packet packet) throws Exception {
        byte[] arrby;
        if (this.deflater != null) {
            this.compress_len[0] = packet.buffer.index;
            packet.buffer.buffer = this.deflater.compress(packet.buffer.buffer, 5, this.compress_len);
            packet.buffer.index = this.compress_len[0];
        }
        if (this.c2scipher != null) {
            packet.padding(this.c2scipher_size);
            byte by = packet.buffer.buffer[4];
            arrby = random;
            synchronized (arrby) {
                random.fill(packet.buffer.buffer, packet.buffer.index - by, by);
            }
        } else {
            packet.padding(8);
        }
        if (this.c2smac != null) {
            this.c2smac.update(this.seqo);
            this.c2smac.update(packet.buffer.buffer, 0, packet.buffer.index);
            this.c2smac.doFinal(packet.buffer.buffer, packet.buffer.index);
        }
        if (this.c2scipher != null) {
            arrby = packet.buffer.buffer;
            this.c2scipher.update(arrby, 0, packet.buffer.index, arrby, 0);
        }
        if (this.c2smac != null) {
            packet.buffer.skip(this.c2smac.getBlockSize());
        }
    }

    public String getClientVersion() {
        return Util.byte2str(this.V_C);
    }

    public String getConfig(String string) {
        JSch jSch;
        if (this.config != null && (jSch = this.config.get(string)) instanceof String) {
            return (String)((Object)jSch);
        }
        jSch = this.jsch;
        if ((string = JSch.getConfig(string)) instanceof String) {
            return string;
        }
        return null;
    }

    public String getHost() {
        return this.host;
    }

    public HostKey getHostKey() {
        return this.hostkey;
    }

    public String getHostKeyAlias() {
        return this.hostKeyAlias;
    }

    public HostKeyRepository getHostKeyRepository() {
        if (this.hostkeyRepository == null) {
            return this.jsch.getHostKeyRepository();
        }
        return this.hostkeyRepository;
    }

    IdentityRepository getIdentityRepository() {
        if (this.identityRepository == null) {
            return this.jsch.getIdentityRepository();
        }
        return this.identityRepository;
    }

    public int getPort() {
        return this.port;
    }

    public String[] getPortForwardingL() throws JSchException {
        return PortWatcher.getPortForwarding(this);
    }

    public String[] getPortForwardingR() throws JSchException {
        return ChannelForwardedTCPIP.getPortForwarding(this);
    }

    public int getServerAliveCountMax() {
        return this.serverAliveCountMax;
    }

    public int getServerAliveInterval() {
        return this.serverAliveInterval;
    }

    public String getServerVersion() {
        return Util.byte2str(this.V_S);
    }

    byte[] getSessionId() {
        return this.session_id;
    }

    public Channel getStreamForwarder(String string, int n) throws JSchException {
        ChannelDirectTCPIP channelDirectTCPIP = new ChannelDirectTCPIP();
        channelDirectTCPIP.init();
        this.addChannel(channelDirectTCPIP);
        channelDirectTCPIP.setHost(string);
        channelDirectTCPIP.setPort(n);
        return channelDirectTCPIP;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public UserInfo getUserInfo() {
        return this.userinfo;
    }

    public String getUserName() {
        return this.username;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void noMoreSessionChannels() throws Exception {
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)80);
        buffer.putString(nomoresessions);
        buffer.putByte((byte)0);
        this.write(packet);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Channel openChannel(String object) throws JSchException {
        if (!this.isConnected) {
            throw new JSchException("session is down");
        }
        try {
            object = Channel.getChannel((String)object);
            this.addChannel((Channel)object);
            object.init();
            if (!(object instanceof ChannelSession)) return object;
            this.applyConfigChannel((ChannelSession)object);
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Buffer read(Buffer arrby) throws Exception {
        block18 : {
            int n;
            do {
                Object object;
                int n2;
                StringBuilder stringBuilder;
                arrby.reset();
                this.io.getByte(arrby.buffer, arrby.index, this.s2ccipher_size);
                arrby.index += this.s2ccipher_size;
                if (this.s2ccipher != null) {
                    this.s2ccipher.update(arrby.buffer, 0, this.s2ccipher_size, arrby.buffer, 0);
                }
                if ((n2 = arrby.buffer[0] << 24 & -16777216 | arrby.buffer[1] << 16 & 16711680 | arrby.buffer[2] << 8 & 65280 | arrby.buffer[3] & 255) < 5 || n2 > 262144) {
                    this.start_discard((Buffer)arrby, this.s2ccipher, this.s2cmac, n2, 262144);
                }
                if (arrby.index + (n = n2 + 4 - this.s2ccipher_size) > arrby.buffer.length) {
                    object = new byte[arrby.index + n];
                    System.arraycopy(arrby.buffer, 0, object, 0, arrby.index);
                    arrby.buffer = object;
                }
                if (n % this.s2ccipher_size != 0) {
                    object = new StringBuilder();
                    object.append("Bad packet length ");
                    object.append(n);
                    object = object.toString();
                    if (JSch.getLogger().isEnabled(4)) {
                        JSch.getLogger().log(4, (String)object);
                    }
                    this.start_discard((Buffer)arrby, this.s2ccipher, this.s2cmac, n2, 262144 - this.s2ccipher_size);
                }
                if (n > 0) {
                    this.io.getByte(arrby.buffer, arrby.index, n);
                    arrby.index += n;
                    if (this.s2ccipher != null) {
                        this.s2ccipher.update(arrby.buffer, this.s2ccipher_size, n, arrby.buffer, this.s2ccipher_size);
                    }
                }
                if (this.s2cmac != null) {
                    this.s2cmac.update(this.seqi);
                    this.s2cmac.update(arrby.buffer, 0, arrby.index);
                    this.s2cmac.doFinal(this.s2cmac_result1, 0);
                    this.io.getByte(this.s2cmac_result2, 0, this.s2cmac_result2.length);
                    if (!Arrays.equals(this.s2cmac_result1, this.s2cmac_result2)) {
                        if (n > 262144) {
                            throw new IOException("MAC Error");
                        }
                        this.start_discard((Buffer)arrby, this.s2ccipher, this.s2cmac, n2, 262144 - n);
                        continue;
                    }
                }
                ++this.seqi;
                if (this.inflater != null) {
                    n = arrby.buffer[4];
                    this.uncompress_len[0] = arrby.index - 5 - n;
                    object = this.inflater.uncompress(arrby.buffer, 5, this.uncompress_len);
                    if (object == null) {
                        System.err.println("fail in inflater");
                        break block18;
                    }
                    arrby.buffer = object;
                    arrby.index = 5 + this.uncompress_len[0];
                }
                if ((n = arrby.getCommand() & 255) == 1) {
                    arrby.rewind();
                    arrby.getInt();
                    arrby.getShort();
                    n = arrby.getInt();
                    object = arrby.getString();
                    arrby = arrby.getString();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("SSH_MSG_DISCONNECT: ");
                    stringBuilder.append(n);
                    stringBuilder.append(" ");
                    stringBuilder.append(Util.byte2str((byte[])object));
                    stringBuilder.append(" ");
                    stringBuilder.append(Util.byte2str(arrby));
                    throw new JSchException(stringBuilder.toString());
                }
                if (n == 2) continue;
                if (n == 3) {
                    arrby.rewind();
                    arrby.getInt();
                    arrby.getShort();
                    n = arrby.getInt();
                    if (!JSch.getLogger().isEnabled(1)) continue;
                    object = JSch.getLogger();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Received SSH_MSG_UNIMPLEMENTED for ");
                    stringBuilder.append(n);
                    object.log(1, stringBuilder.toString());
                    continue;
                }
                if (n == 4) {
                    arrby.rewind();
                    arrby.getInt();
                    arrby.getShort();
                    continue;
                }
                if (n != 93) break;
                arrby.rewind();
                arrby.getInt();
                arrby.getShort();
                object = Channel.getChannel(arrby.getInt(), this);
                if (object == null) continue;
                object.addRemoteWindowSize(arrby.getUInt());
            } while (true);
            if (n == 52) {
                this.isAuthed = true;
                if (this.inflater == null && this.deflater == null) {
                    this.initDeflater(this.guess[6]);
                    this.initInflater(this.guess[7]);
                }
            }
        }
        arrby.rewind();
        return arrby;
    }

    public void rekey() throws Exception {
        this.send_kexinit();
    }

    /*
     * Exception decompiling
     */
    @Override
    public void run() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:401)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    public void sendIgnore() throws Exception {
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)2);
        this.write(packet);
    }

    public void sendKeepAliveMsg() throws Exception {
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)80);
        buffer.putString(keepalivemsg);
        buffer.putByte((byte)1);
        this.write(packet);
    }

    public void setClientVersion(String string) {
        this.V_C = Util.str2byte(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setConfig(String string, String string2) {
        Object object = this.lock;
        synchronized (object) {
            if (this.config == null) {
                this.config = new Hashtable();
            }
            this.config.put(string, string2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setConfig(Hashtable hashtable) {
        Object object = this.lock;
        synchronized (object) {
            if (this.config == null) {
                this.config = new Hashtable();
            }
            Enumeration enumeration = hashtable.keys();
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                this.config.put(string, (String)hashtable.get(string));
            }
            return;
        }
    }

    public void setConfig(Properties properties) {
        this.setConfig((Hashtable)properties);
    }

    public void setDaemonThread(boolean bl) {
        this.daemon_thread = bl;
    }

    public void setHost(String string) {
        this.host = string;
    }

    public void setHostKeyAlias(String string) {
        this.hostKeyAlias = string;
    }

    public void setHostKeyRepository(HostKeyRepository hostKeyRepository) {
        this.hostkeyRepository = hostKeyRepository;
    }

    public void setIdentityRepository(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
    }

    public void setInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void setPassword(String string) {
        if (string != null) {
            this.password = Util.str2byte(string);
        }
    }

    public void setPassword(byte[] arrby) {
        if (arrby != null) {
            this.password = new byte[arrby.length];
            System.arraycopy(arrby, 0, this.password, 0, arrby.length);
        }
    }

    public void setPort(int n) {
        this.port = n;
    }

    public int setPortForwardingL(int n, String string, int n2) throws JSchException {
        return this.setPortForwardingL("127.0.0.1", n, string, n2);
    }

    public int setPortForwardingL(String object) throws JSchException {
        object = this.parseForwarding((String)object);
        return this.setPortForwardingL(object.bind_address, object.port, object.host, object.hostport);
    }

    public int setPortForwardingL(String string, int n, String string2, int n2) throws JSchException {
        return this.setPortForwardingL(string, n, string2, n2, null);
    }

    public int setPortForwardingL(String string, int n, String string2, int n2, ServerSocketFactory serverSocketFactory) throws JSchException {
        return this.setPortForwardingL(string, n, string2, n2, serverSocketFactory, 0);
    }

    public int setPortForwardingL(String object, int n, String string, int n2, ServerSocketFactory object2, int n3) throws JSchException {
        object = PortWatcher.addPort(this, (String)object, n, string, n2, (ServerSocketFactory)object2);
        object.setConnectTimeout(n3);
        object2 = new Thread((Runnable)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PortWatcher Thread for ");
        stringBuilder.append(string);
        object2.setName(stringBuilder.toString());
        if (this.daemon_thread) {
            object2.setDaemon(this.daemon_thread);
        }
        object2.start();
        return object.lport;
    }

    public int setPortForwardingR(String object) throws JSchException {
        object = this.parseForwarding((String)object);
        int n = this._setPortForwardingR(object.bind_address, object.port);
        ChannelForwardedTCPIP.addPort(this, object.bind_address, object.port, n, object.host, object.hostport, null);
        return n;
    }

    public void setPortForwardingR(int n, String string) throws JSchException {
        this.setPortForwardingR(null, n, string, null);
    }

    public void setPortForwardingR(int n, String string, int n2) throws JSchException {
        this.setPortForwardingR(null, n, string, n2, null);
    }

    public void setPortForwardingR(int n, String string, int n2, SocketFactory socketFactory) throws JSchException {
        this.setPortForwardingR(null, n, string, n2, socketFactory);
    }

    public void setPortForwardingR(int n, String string, Object[] arrobject) throws JSchException {
        this.setPortForwardingR(null, n, string, arrobject);
    }

    public void setPortForwardingR(String string, int n, String string2, int n2) throws JSchException {
        this.setPortForwardingR(string, n, string2, n2, null);
    }

    public void setPortForwardingR(String string, int n, String string2, int n2, SocketFactory socketFactory) throws JSchException {
        ChannelForwardedTCPIP.addPort(this, string, n, this._setPortForwardingR(string, n), string2, n2, socketFactory);
    }

    public void setPortForwardingR(String string, int n, String string2, Object[] arrobject) throws JSchException {
        ChannelForwardedTCPIP.addPort(this, string, n, this._setPortForwardingR(string, n), string2, arrobject);
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setServerAliveCountMax(int n) {
        this.serverAliveCountMax = n;
    }

    public void setServerAliveInterval(int n) throws JSchException {
        this.setTimeout(n);
        this.serverAliveInterval = n;
    }

    public void setSocketFactory(SocketFactory socketFactory) {
        this.socket_factory = socketFactory;
    }

    public void setTimeout(int n) throws JSchException {
        if (this.socket == null) {
            if (n < 0) {
                throw new JSchException("invalid timeout value");
            }
            this.timeout = n;
            return;
        }
        try {
            this.socket.setSoTimeout(n);
            this.timeout = n;
            return;
        }
        catch (Exception exception) {
            if (exception instanceof Throwable) {
                throw new JSchException(exception.toString(), exception);
            }
            throw new JSchException(exception.toString());
        }
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userinfo = userInfo;
    }

    void setUserName(String string) {
        this.username = string;
    }

    public void setX11Cookie(String string) {
        ChannelX11.setCookie(string);
    }

    public void setX11Host(String string) {
        ChannelX11.setHost(string);
    }

    public void setX11Port(int n) {
        ChannelX11.setPort(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void write(Packet packet) throws Exception {
        long l = this.getTimeout();
        while (this.in_kex) {
            if (l > 0L && System.currentTimeMillis() - this.kex_start_time > l && !this.in_prompt) {
                throw new JSchException("timeout in waiting for rekeying process.");
            }
            byte by = packet.buffer.getCommand();
            if (by == 20 || by == 21 || by == 30 || by == 31 || by == 31 || by == 32 || by == 33 || by == 34 || by == 1) break;
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedException) {}
        }
        this._write(packet);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void write(Packet packet, Channel channel, int n) throws Exception {
        long l = this.getTimeout();
        do {
            block39 : {
                block38 : {
                    int n3;
                    long l3;
                    int n2;
                    long l2;
                    block37 : {
                        block36 : {
                            block35 : {
                                if (this.in_kex) {
                                    if (l > 0L && System.currentTimeMillis() - this.kex_start_time > l) {
                                        throw new JSchException("timeout in waiting for rekeying process.");
                                    }
                                    Thread.sleep(10L);
                                }
                                // MONITORENTER : channel
                                l2 = channel.rwsize;
                                l3 = n;
                                if (l2 < l3) {
                                    try {
                                        ++channel.notifyme;
                                        channel.wait(100L);
                                        n3 = channel.notifyme;
                                        break block35;
                                    }
                                    catch (Throwable throwable) {
                                        --channel.notifyme;
                                        throw throwable;
                                    }
                                }
                                break block36;
                                catch (InterruptedException interruptedException) {}
                                continue;
                                catch (InterruptedException interruptedException) {}
                                n3 = channel.notifyme;
                            }
                            channel.notifyme = n3 - 1;
                        }
                        if (this.in_kex) {
                            // MONITOREXIT : channel
                            continue;
                        }
                        if (channel.rwsize < l3) break block37;
                        channel.rwsize -= l3;
                        // MONITOREXIT : channel
                        break block38;
                    }
                    // MONITOREXIT : channel
                    if (channel.close) throw new IOException("channel is broken");
                    if (!channel.isConnected()) {
                        throw new IOException("channel is broken");
                    }
                    int n4 = -1;
                    // MONITORENTER : channel
                    l2 = channel.rwsize;
                    int n5 = 0;
                    byte by = 0;
                    n3 = 0;
                    if (l2 > 0L) {
                        long l4;
                        l2 = l4 = channel.rwsize;
                        if (l4 > l3) {
                            l2 = l3;
                        }
                        n = n5;
                        if (l2 != l3) {
                            n5 = (int)l2;
                            n = this.c2scipher != null ? this.c2scipher_size : 8;
                            if (this.c2smac != null) {
                                n3 = this.c2smac.getBlockSize();
                            }
                            n = packet.shift(n5, n, n3);
                        }
                        by = packet.buffer.getCommand();
                        n5 = channel.getRecipient();
                        n3 = (int)(l3 - l2);
                        channel.rwsize -= l2;
                        n4 = 1;
                        n2 = n;
                        n = n3;
                        n3 = n5;
                        n5 = n4;
                    } else {
                        n5 = n2 = 0;
                        n3 = n4;
                    }
                    // MONITOREXIT : channel
                    if (n5 != 0) {
                        this._write(packet);
                        if (n == 0) {
                            return;
                        }
                        packet.unshift(by, n3, n2, n);
                    }
                    // MONITORENTER : channel
                    if (this.in_kex) {
                        // MONITOREXIT : channel
                        continue;
                    }
                    l2 = channel.rwsize;
                    l3 = n;
                    if (l2 < l3) break block39;
                    channel.rwsize -= l3;
                    // MONITOREXIT : channel
                }
                this._write(packet);
                return;
            }
            // MONITOREXIT : channel
        } while (true);
    }

    private class Forwarding {
        String bind_address = null;
        String host = null;
        int hostport = -1;
        int port = -1;

        private Forwarding() {
        }
    }

    private class GlobalRequestReply {
        private int port = 0;
        private int reply = -1;
        private Thread thread = null;

        private GlobalRequestReply() {
        }

        int getPort() {
            return this.port;
        }

        int getReply() {
            return this.reply;
        }

        Thread getThread() {
            return this.thread;
        }

        void setPort(int n) {
            this.port = n;
        }

        void setReply(int n) {
            this.reply = n;
        }

        void setThread(Thread thread) {
            this.thread = thread;
            this.reply = -1;
        }
    }

}
