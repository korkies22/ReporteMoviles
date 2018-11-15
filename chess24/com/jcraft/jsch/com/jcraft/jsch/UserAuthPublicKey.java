/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Identity;
import com.jcraft.jsch.IdentityRepository;
import com.jcraft.jsch.JSchAuthCancelException;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.JSchPartialAuthException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserAuth;
import com.jcraft.jsch.UserInfo;
import com.jcraft.jsch.Util;
import java.util.Vector;

class UserAuthPublicKey
extends UserAuth {
    UserAuthPublicKey() {
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
    public boolean start(Session var1_1) throws Exception {
        super.start(var1_1);
        var8_2 = var1_1.getIdentityRepository().getIdentities();
        // MONITORENTER : var8_2
        if (var8_2.size() <= 0) {
            // MONITOREXIT : var8_2
            return false;
        }
        var9_3 = Util.str2byte(this.username);
        var2_4 = 0;
        do {
            block33 : {
                block31 : {
                    block32 : {
                        if (var2_4 >= var8_2.size()) {
                            // MONITOREXIT : var8_2
                            return false;
                        }
                        if (var1_1.auth_failures >= var1_1.max_auth_tries) {
                            // MONITOREXIT : var8_2
                            return false;
                        }
                        var10_10 = (Identity)var8_2.elementAt(var2_4);
                        var7_9 = var10_10.getPublicKeyBlob();
                        if (var7_9 == null) break block32;
                        this.packet.reset();
                        this.buf.putByte((byte)50);
                        this.buf.putString(var9_3);
                        this.buf.putString(Util.str2byte("ssh-connection"));
                        this.buf.putString(Util.str2byte("publickey"));
                        this.buf.putByte((byte)0);
                        this.buf.putString(Util.str2byte(var10_10.getAlgName()));
                        this.buf.putString(var7_9);
                        var1_1.write(this.packet);
                        do {
                            this.buf = var1_1.read(this.buf);
                            var3_5 = this.buf.getCommand() & 255;
                            if (var3_5 == 60 || var3_5 == 51 || var3_5 != 53) break;
                            this.buf.getInt();
                            this.buf.getByte();
                            this.buf.getByte();
                            var5_7 /* !! */  = this.buf.getString();
                            this.buf.getString();
                            var5_7 /* !! */  = Util.byte2str(var5_7 /* !! */ );
                            if (this.userinfo == null) continue;
                            this.userinfo.showMessage((String)var5_7 /* !! */ );
                        } while (true);
                        if (var3_5 != 60) break block33;
                    }
                    var3_5 = 5;
                    do {
                        if (!var10_10.isEncrypted()) ** GOTO lbl-1000
                        if (this.userinfo == null) {
                            throw new JSchException("USERAUTH fail");
                        }
                        if (var10_10.isEncrypted()) {
                            var5_7 /* !! */  = this.userinfo;
                            var6_8 = new StringBuilder();
                            var6_8.append("Passphrase for ");
                            var6_8.append(var10_10.getName());
                            if (!var5_7 /* !! */ .promptPassphrase(var6_8.toString())) {
                                throw new JSchAuthCancelException("publickey");
                            }
                        }
                        if ((var5_7 /* !! */  = this.userinfo.getPassphrase()) != null) {
                            var5_7 /* !! */  = Util.str2byte((String)var5_7 /* !! */ );
                        } else lbl-1000: // 2 sources:
                        {
                            var5_7 /* !! */  = null;
                        }
                        if ((!var10_10.isEncrypted() || var5_7 /* !! */  != null) && var10_10.setPassphrase(var5_7 /* !! */ )) {
                            var6_8 = var5_7 /* !! */ ;
                            if (var5_7 /* !! */  != null) {
                                var6_8 = var5_7 /* !! */ ;
                                if (var1_1.getIdentityRepository() instanceof IdentityRepository.Wrapper) {
                                    ((IdentityRepository.Wrapper)var1_1.getIdentityRepository()).check();
                                    var6_8 = var5_7 /* !! */ ;
                                }
                            }
                            break block31;
                        }
                        Util.bzero(var5_7 /* !! */ );
                    } while (--var3_5 != 0);
                    var6_8 = null;
                }
                Util.bzero(var6_8);
                if (!var10_10.isEncrypted()) {
                    var5_7 /* !! */  = var7_9;
                    if (var7_9 == null) {
                        var5_7 /* !! */  = var10_10.getPublicKeyBlob();
                    }
                    if (var5_7 /* !! */  != null) {
                        this.packet.reset();
                        this.buf.putByte((byte)50);
                        this.buf.putString(var9_3);
                        this.buf.putString(Util.str2byte("ssh-connection"));
                        this.buf.putString(Util.str2byte("publickey"));
                        this.buf.putByte((byte)1);
                        this.buf.putString(Util.str2byte(var10_10.getAlgName()));
                        this.buf.putString(var5_7 /* !! */ );
                        var5_7 /* !! */  = var1_1.getSessionId();
                        var3_5 = var5_7 /* !! */ .length;
                        var4_6 = 4 + var3_5;
                        var6_8 = new byte[this.buf.index + var4_6 - 5];
                        var6_8[0] = (byte)(var3_5 >>> 24);
                        var6_8[1] = (byte)(var3_5 >>> 16);
                        var6_8[2] = (byte)(var3_5 >>> 8);
                        var6_8[3] = (byte)var3_5;
                        System.arraycopy(var5_7 /* !! */ , 0, var6_8, 4, var3_5);
                        System.arraycopy(this.buf.buffer, 5, var6_8, var4_6, this.buf.index - 5);
                        var5_7 /* !! */  = var10_10.getSignature(var6_8);
                        if (var5_7 /* !! */  == null) {
                            return false;
                        }
                        this.buf.putString(var5_7 /* !! */ );
                        var1_1.write(this.packet);
                        do {
                            this.buf = var1_1.read(this.buf);
                            var3_5 = this.buf.getCommand() & 255;
                            if (var3_5 == 52) {
                                // MONITOREXIT : var8_2
                                return true;
                            }
                            if (var3_5 != 53) break;
                            this.buf.getInt();
                            this.buf.getByte();
                            this.buf.getByte();
                            var5_7 /* !! */  = this.buf.getString();
                            this.buf.getString();
                            var5_7 /* !! */  = Util.byte2str(var5_7 /* !! */ );
                            if (this.userinfo == null) continue;
                            this.userinfo.showMessage((String)var5_7 /* !! */ );
                        } while (true);
                        if (var3_5 == 51) {
                            this.buf.getInt();
                            this.buf.getByte();
                            this.buf.getByte();
                            var5_7 /* !! */  = this.buf.getString();
                            if (this.buf.getByte() != 0) {
                                throw new JSchPartialAuthException(Util.byte2str(var5_7 /* !! */ ));
                            }
                            ++var1_1.auth_failures;
                        }
                    }
                }
            }
            ++var2_4;
        } while (true);
    }
}
