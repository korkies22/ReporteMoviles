/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ChannelSftp;

public static interface ChannelSftp.LsEntrySelector {
    public static final int BREAK = 1;
    public static final int CONTINUE = 0;

    public int select(ChannelSftp.LsEntry var1);
}
