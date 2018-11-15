/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Identity;
import com.jcraft.jsch.IdentityFile;
import com.jcraft.jsch.IdentityRepository;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Util;
import java.util.Vector;

class LocalIdentityRepository
implements IdentityRepository {
    private static final String name = "Local Identity Repository";
    private Vector identities = new Vector();
    private JSch jsch;

    LocalIdentityRepository(JSch jSch) {
        this.jsch = jSch;
    }

    private void removeDupulicates() {
        int n;
        Vector<byte[]> vector = new Vector<byte[]>();
        int n2 = this.identities.size();
        if (n2 == 0) {
            return;
        }
        int n3 = 0;
        int n4 = 0;
        do {
            if (n4 >= n2) break;
            Identity identity = (Identity)this.identities.elementAt(n4);
            byte[] arrby = identity.getPublicKeyBlob();
            if (arrby != null) {
                for (n = n4 + 1; n < n2; ++n) {
                    Identity identity2 = (Identity)this.identities.elementAt(n);
                    byte[] arrby2 = identity2.getPublicKeyBlob();
                    if (arrby2 == null || !Util.array_equals(arrby, arrby2) || identity.isEncrypted() != identity2.isEncrypted()) continue;
                    vector.addElement(arrby);
                    break;
                }
            }
            ++n4;
        } while (true);
        for (n = n3; n < vector.size(); ++n) {
            this.remove((byte[])vector.elementAt(n));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void add(Identity identity) {
        synchronized (this) {
            if (!this.identities.contains(identity)) {
                byte[] arrby = identity.getPublicKeyBlob();
                if (arrby == null) {
                    this.identities.addElement(identity);
                    return;
                }
                for (int i = 0; i < this.identities.size(); ++i) {
                    byte[] arrby2 = ((Identity)this.identities.elementAt(i)).getPublicKeyBlob();
                    if (arrby2 == null || !Util.array_equals(arrby, arrby2)) continue;
                    if (!identity.isEncrypted() && ((Identity)this.identities.elementAt(i)).isEncrypted()) {
                        this.remove(arrby2);
                        continue;
                    }
                    return;
                }
                this.identities.addElement(identity);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean add(byte[] arrby) {
        synchronized (this) {
            try {
                this.add(IdentityFile.newInstance("from remote:", arrby, null, this.jsch));
                return true;
            }
            catch (JSchException jSchException) {}
            return false;
        }
    }

    @Override
    public Vector getIdentities() {
        synchronized (this) {
            this.removeDupulicates();
            Vector vector = new Vector();
            int n = 0;
            do {
                if (n >= this.identities.size()) break;
                vector.addElement(this.identities.elementAt(n));
                ++n;
            } while (true);
            return vector;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getStatus() {
        return 2;
    }

    void remove(Identity identity) {
        synchronized (this) {
            if (this.identities.contains(identity)) {
                this.identities.removeElement(identity);
                identity.clear();
            } else {
                this.remove(identity.getPublicKeyBlob());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean remove(byte[] arrby) {
        synchronized (this) {
            if (arrby == null) {
                return false;
            }
            int n = 0;
            while (n < this.identities.size()) {
                Identity identity = (Identity)this.identities.elementAt(n);
                byte[] arrby2 = identity.getPublicKeyBlob();
                if (arrby2 != null && Util.array_equals(arrby, arrby2)) {
                    this.identities.removeElement(identity);
                    identity.clear();
                    return true;
                }
                ++n;
            }
            return false;
        }
    }

    @Override
    public void removeAll() {
        synchronized (this) {
            int n = 0;
            do {
                if (n >= this.identities.size()) break;
                ((Identity)this.identities.elementAt(n)).clear();
                ++n;
            } while (true);
            this.identities.removeAllElements();
            return;
        }
    }
}
