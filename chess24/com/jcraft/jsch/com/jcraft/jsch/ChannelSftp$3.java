/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ChannelSftp;
import java.util.Vector;

class ChannelSftp
implements ChannelSftp.LsEntrySelector {
    final /* synthetic */ Vector val$v;

    ChannelSftp(Vector vector) {
        this.val$v = vector;
    }

    @Override
    public int select(ChannelSftp.LsEntry lsEntry) {
        this.val$v.addElement(lsEntry);
        return 0;
    }
}
