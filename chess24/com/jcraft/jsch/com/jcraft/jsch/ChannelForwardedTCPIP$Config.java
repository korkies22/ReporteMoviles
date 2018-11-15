/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ChannelForwardedTCPIP;
import com.jcraft.jsch.Session;

static abstract class ChannelForwardedTCPIP.Config {
    String address_to_bind;
    int allocated_rport;
    int rport;
    Session session;
    String target;

    ChannelForwardedTCPIP.Config() {
    }
}
