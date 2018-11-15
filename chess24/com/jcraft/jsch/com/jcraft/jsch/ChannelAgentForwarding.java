/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Identity;
import com.jcraft.jsch.IdentityRepository;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.jcraft.jsch.Util;
import java.io.IOException;
import java.util.Vector;

class ChannelAgentForwarding
extends Channel {
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private final byte SSH2_AGENTC_ADD_IDENTITY = (byte)17;
    private final byte SSH2_AGENTC_REMOVE_ALL_IDENTITIES = (byte)19;
    private final byte SSH2_AGENTC_REMOVE_IDENTITY = (byte)18;
    private final byte SSH2_AGENTC_REQUEST_IDENTITIES = (byte)11;
    private final byte SSH2_AGENTC_SIGN_REQUEST = (byte)13;
    private final byte SSH2_AGENT_FAILURE = (byte)30;
    private final byte SSH2_AGENT_IDENTITIES_ANSWER = (byte)12;
    private final byte SSH2_AGENT_SIGN_RESPONSE = (byte)14;
    private final byte SSH_AGENTC_ADD_RSA_IDENTITY = (byte)7;
    private final byte SSH_AGENTC_REMOVE_ALL_RSA_IDENTITIES = (byte)9;
    private final byte SSH_AGENTC_REMOVE_RSA_IDENTITY = (byte)8;
    private final byte SSH_AGENTC_REQUEST_RSA_IDENTITIES = 1;
    private final byte SSH_AGENTC_RSA_CHALLENGE = (byte)3;
    private final byte SSH_AGENT_FAILURE = (byte)5;
    private final byte SSH_AGENT_RSA_IDENTITIES_ANSWER = (byte)2;
    private final byte SSH_AGENT_RSA_RESPONSE = (byte)4;
    private final byte SSH_AGENT_SUCCESS = (byte)6;
    boolean init = true;
    private Buffer mbuf = null;
    private Packet packet = null;
    private Buffer rbuf = null;
    private Buffer wbuf = null;

    ChannelAgentForwarding() {
        this.setLocalWindowSizeMax(131072);
        this.setLocalWindowSize(131072);
        this.setLocalPacketSize(16384);
        this.type = Util.str2byte("auth-agent@openssh.com");
        this.rbuf = new Buffer();
        this.rbuf.reset();
        this.mbuf = new Buffer();
        this.connected = true;
    }

    private void send(byte[] arrby) {
        this.packet.reset();
        this.wbuf.putByte((byte)94);
        this.wbuf.putInt(this.recipient);
        this.wbuf.putInt(arrby.length + 4);
        this.wbuf.putString(arrby);
        try {
            this.getSession().write(this.packet, this, 4 + arrby.length);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    @Override
    void eof_remote() {
        super.eof_remote();
        this.eof();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        try {
            this.sendOpenConfirmation();
            return;
        }
        catch (Exception exception) {}
        this.close = true;
        this.disconnect();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    void write(byte[] var1_1, int var2_3, int var3_4) throws IOException {
        block38 : {
            block36 : {
                block37 : {
                    if (this.packet == null) {
                        this.wbuf = new Buffer(this.rmpsize);
                        this.packet = new Packet(this.wbuf);
                    }
                    this.rbuf.shift();
                    var8_5 = this.rbuf.buffer;
                    var5_6 = 0;
                    var6_7 = 0;
                    if (((byte[])var8_5).length < this.rbuf.index + var3_4) {
                        var8_5 = new byte[this.rbuf.s + var3_4];
                        System.arraycopy(this.rbuf.buffer, 0, var8_5, 0, this.rbuf.buffer.length);
                        this.rbuf.buffer = var8_5;
                    }
                    this.rbuf.putByte((byte[])var1_1, var2_3, var3_4);
                    if (this.rbuf.getInt() > this.rbuf.getLength()) {
                        var1_1 = this.rbuf;
                        var1_1.s -= 4;
                        return;
                    }
                    var2_3 = this.rbuf.getByte();
                    try {
                        var8_5 = this.getSession();
                        var1_1 = var8_5.getIdentityRepository();
                        var11_8 = var8_5.getUserInfo();
                        this.mbuf.reset();
                        if (var2_3 == 11) break block36;
                    }
                    catch (JSchException var1_2) {
                        throw new IOException(var1_2.toString());
                    }
                    if (var2_3 != 1) break block37;
                    this.mbuf.putByte((byte)2);
                    this.mbuf.putInt(0);
                    ** GOTO lbl125
                }
                if (var2_3 == 13) break block38;
                var4_16 = 6;
                if (var2_3 == 18) {
                    var1_1.remove(this.rbuf.getString());
                    this.mbuf.putByte((byte)6);
                } else if (var2_3 == 9) {
                    this.mbuf.putByte((byte)6);
                } else if (var2_3 == 19) {
                    var1_1.removeAll();
                    this.mbuf.putByte((byte)6);
                } else if (var2_3 == 17) {
                    var8_5 = new byte[this.rbuf.getLength()];
                    this.rbuf.getByte((byte[])var8_5);
                    var7_13 = var1_1.add((byte[])var8_5);
                    var1_1 = this.mbuf;
                    if (!var7_13) {
                        var4_16 = 5;
                    }
                    var1_1.putByte(var4_16);
                } else {
                    this.rbuf.skip(this.rbuf.getLength() - 1);
                    this.mbuf.putByte((byte)5);
                }
                ** GOTO lbl125
            }
            this.mbuf.putByte((byte)12);
            var1_1 = var1_1.getIdentities();
            // MONITORENTER : var1_1
            var3_4 = var2_3 = 0;
            do {
                if (var2_3 >= var1_1.size()) break;
                var5_6 = var3_4;
                if (((Identity)var1_1.elementAt(var2_3)).getPublicKeyBlob() != null) {
                    var5_6 = var3_4 + 1;
                }
                ++var2_3;
                var3_4 = var5_6;
            } while (true);
            this.mbuf.putInt(var3_4);
            var2_3 = var6_7;
            do {
                block39 : {
                    if (var2_3 < var1_1.size()) break block39;
                    // MONITOREXIT : var1_1
                    ** GOTO lbl125
                }
                var8_5 = ((Identity)var1_1.elementAt(var2_3)).getPublicKeyBlob();
                if (var8_5 != null) {
                    this.mbuf.putString((byte[])var8_5);
                    this.mbuf.putString(Util.empty);
                }
                ++var2_3;
            } while (true);
        }
        var12_9 = this.rbuf.getString();
        var9_10 = this.rbuf.getString();
        this.rbuf.getInt();
        var10_11 = var1_1.getIdentities();
        // MONITORENTER : var10_11
        var2_3 = var5_6;
        do {
            block41 : {
                block44 : {
                    block42 : {
                        block43 : {
                            block40 : {
                                var3_4 = var10_11.size();
                                var8_5 = null;
                                if (var2_3 >= var3_4) break block40;
                                var1_1 = (Identity)var10_11.elementAt(var2_3);
                                if (var1_1.getPublicKeyBlob() == null || !Util.array_equals(var12_9, var1_1.getPublicKeyBlob())) break block41;
                                if (!var1_1.isEncrypted()) break block42;
                                if (var11_8 != null) break block43;
                                break block41;
                            }
                            var1_1 = null;
                            break block44;
                        }
                        while (var1_1.isEncrypted()) {
                            var13_14 = new StringBuilder();
                            var13_14.append("Passphrase for ");
                            var13_14.append(var1_1.getName());
                            if (!var11_8.promptPassphrase(var13_14.toString()) || (var13_14 = var11_8.getPassphrase()) == null) break;
                            var13_14 = Util.str2byte((String)var13_14);
                            try {
                                var7_12 = var1_1.setPassphrase(var13_14);
                                if (!var7_12) continue;
                            }
                            catch (JSchException var13_15) {}
                            break;
                        }
                    }
                    if (var1_1.isEncrypted()) break block41;
                }
                // MONITOREXIT : var10_11
                if (var1_1 != null) {
                    var8_5 = var1_1.getSignature(var9_10);
                }
                if (var8_5 == null) {
                    this.mbuf.putByte((byte)30);
                } else {
                    this.mbuf.putByte((byte)14);
                    this.mbuf.putString((byte[])var8_5);
                }
lbl125: // 9 sources:
                var1_1 = new byte[this.mbuf.getLength()];
                this.mbuf.getByte((byte[])var1_1);
                this.send((byte[])var1_1);
                return;
            }
            ++var2_3;
        } while (true);
    }
}
