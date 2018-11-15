/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.JSchAuthCancelException;
import com.jcraft.jsch.JSchPartialAuthException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserAuth;
import com.jcraft.jsch.UserInfo;
import com.jcraft.jsch.Util;

class UserAuthPassword
extends UserAuth {
    private final int SSH_MSG_USERAUTH_PASSWD_CHANGEREQ = 60;

    UserAuthPassword() {
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean start(Session session) throws Exception {
        Object object;
        super.start(session);
        byte[] arrby = session.password;
        byte[] arrby2 = new StringBuilder();
        arrby2.append(this.username);
        arrby2.append("@");
        arrby2.append(session.host);
        arrby2 = arrby2.toString();
        CharSequence charSequence = arrby2;
        if (session.port != 22) {
            charSequence = new StringBuilder();
            charSequence.append((String)arrby2);
            charSequence.append(":");
            charSequence.append(session.port);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        do {
            block75 : {
                int n;
                block72 : {
                    block74 : {
                        Object object2;
                        block68 : {
                            object = arrby;
                            n = session.auth_failures;
                            object = arrby;
                            int n2 = session.max_auth_tries;
                            if (n < n2) break block68;
                            if (arrby == null) return false;
                            Util.bzero(arrby);
                            return false;
                        }
                        arrby2 = arrby;
                        if (arrby == null) {
                            block71 : {
                                block70 : {
                                    block69 : {
                                        object = arrby;
                                        arrby2 = this.userinfo;
                                        if (arrby2 != null) break block69;
                                        if (arrby == null) return false;
                                        Util.bzero(arrby);
                                        return false;
                                    }
                                    object = arrby;
                                    arrby2 = this.userinfo;
                                    object = arrby;
                                    object2 = new byte[]();
                                    object = arrby;
                                    object2.append("Password for ");
                                    object = arrby;
                                    object2.append((String)charSequence);
                                    object = arrby;
                                    if (arrby2.promptPassword(object2.toString())) break block70;
                                    object = arrby;
                                    throw new JSchAuthCancelException("password");
                                }
                                object = arrby;
                                arrby2 = this.userinfo.getPassword();
                                if (arrby2 != null) break block71;
                                object = arrby;
                                throw new JSchAuthCancelException("password");
                            }
                            object = arrby;
                            arrby2 = Util.str2byte((String)arrby2);
                        }
                        object = arrby2;
                        arrby = Util.str2byte(this.username);
                        object = arrby2;
                        this.packet.reset();
                        object = arrby2;
                        this.buf.putByte((byte)50);
                        object = arrby2;
                        this.buf.putString(arrby);
                        object = arrby2;
                        this.buf.putString(Util.str2byte("ssh-connection"));
                        object = arrby2;
                        this.buf.putString(Util.str2byte("password"));
                        object = arrby2;
                        this.buf.putByte((byte)0);
                        object = arrby2;
                        this.buf.putString(arrby2);
                        object = arrby2;
                        session.write(this.packet);
                        do {
                            block73 : {
                                object = arrby2;
                                this.buf = session.read(this.buf);
                                object = arrby2;
                                n = this.buf.getCommand();
                                if ((n &= 255) == 52) {
                                    if (arrby2 == null) return true;
                                    Util.bzero(arrby2);
                                    return true;
                                }
                                if (n == 53) {
                                    object = arrby2;
                                    this.buf.getInt();
                                    object = arrby2;
                                    this.buf.getByte();
                                    object = arrby2;
                                    this.buf.getByte();
                                    object = arrby2;
                                    object2 = this.buf.getString();
                                    object = arrby2;
                                    this.buf.getString();
                                    object = arrby2;
                                    object2 = Util.byte2str(object2);
                                    object = arrby2;
                                    if (this.userinfo == null) continue;
                                    object = arrby2;
                                    this.userinfo.showMessage((String)object2);
                                    continue;
                                }
                                if (n != 60) break block72;
                                object = arrby2;
                                this.buf.getInt();
                                object = arrby2;
                                this.buf.getByte();
                                object = arrby2;
                                this.buf.getByte();
                                object = arrby2;
                                object2 = this.buf.getString();
                                object = arrby2;
                                this.buf.getString();
                                object = arrby2;
                                if (this.userinfo == null) break;
                                object = arrby2;
                                if (!(this.userinfo instanceof UIKeyboardInteractive)) break;
                                object = arrby2;
                                object2 = ((UIKeyboardInteractive)((Object)this.userinfo)).promptKeyboardInteractive((String)charSequence, "Password Change Required", Util.byte2str(object2), new String[]{"New Password: "}, new boolean[]{false});
                                if (object2 != null) break block73;
                                object = arrby2;
                                throw new JSchAuthCancelException("password");
                            }
                            object = arrby2;
                            object2 = Util.str2byte((String)object2[0]);
                            object = arrby2;
                            this.packet.reset();
                            object = arrby2;
                            this.buf.putByte((byte)50);
                            object = arrby2;
                            this.buf.putString(arrby);
                            object = arrby2;
                            this.buf.putString(Util.str2byte("ssh-connection"));
                            object = arrby2;
                            this.buf.putString(Util.str2byte("password"));
                            object = arrby2;
                            this.buf.putByte((byte)1);
                            object = arrby2;
                            this.buf.putString(arrby2);
                            object = arrby2;
                            this.buf.putString((byte[])object2);
                            object = arrby2;
                            Util.bzero(object2);
                            object = arrby2;
                            session.write(this.packet);
                            continue;
                            break;
                        } while (true);
                        object = arrby2;
                        if (this.userinfo == null) break block74;
                        object = arrby2;
                        this.userinfo.showMessage("Password must be changed.");
                    }
                    if (arrby2 == null) return false;
                    Util.bzero(arrby2);
                    return false;
                }
                if (n != 51) break;
                object = arrby2;
                this.buf.getInt();
                object = arrby2;
                this.buf.getByte();
                object = arrby2;
                this.buf.getByte();
                object = arrby2;
                arrby = this.buf.getString();
                object = arrby2;
                if (this.buf.getByte() == 0) break block75;
                object = arrby2;
                throw new JSchPartialAuthException(Util.byte2str(arrby));
            }
            object = arrby2;
            ++session.auth_failures;
            arrby = arrby2;
            if (arrby2 == null) continue;
            object = arrby2;
            Util.bzero(arrby2);
            arrby = null;
            continue;
            break;
        } while (true);
        if (arrby2 == null) return false;
        Util.bzero(arrby2);
        return false;
        catch (Throwable throwable) {
            if (object == null) throw throwable;
            Util.bzero(object);
            throw throwable;
        }
    }
}
