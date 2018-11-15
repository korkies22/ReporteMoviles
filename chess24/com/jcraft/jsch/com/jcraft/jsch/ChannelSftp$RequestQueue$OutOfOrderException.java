/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ChannelSftp;

class ChannelSftp.RequestQueue.OutOfOrderException
extends Exception {
    long offset;

    ChannelSftp.RequestQueue.OutOfOrderException(long l) {
        this.offset = l;
    }
}
