/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KnownHosts;
import com.jcraft.jsch.MAC;
import com.jcraft.jsch.Random;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;

class KnownHosts.HashedHostKey
extends HostKey {
    private static final String HASH_DELIM = "|";
    private static final String HASH_MAGIC = "|1|";
    byte[] hash;
    private boolean hashed;
    byte[] salt;

    KnownHosts.HashedHostKey(String string, int n, byte[] arrby) throws JSchException {
        this("", string, n, arrby, null);
    }

    KnownHosts.HashedHostKey(String string, String string2, int n, byte[] arrby, String string3) throws JSchException {
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

    KnownHosts.HashedHostKey(String string, byte[] arrby) throws JSchException {
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
