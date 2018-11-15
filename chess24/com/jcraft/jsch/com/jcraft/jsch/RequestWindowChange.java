/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Request;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;

class RequestWindowChange
extends Request {
    int height_pixels = 480;
    int height_rows = 24;
    int width_columns = 80;
    int width_pixels = 640;

    RequestWindowChange() {
    }

    @Override
    public void request(Session object, Channel channel) throws Exception {
        super.request((Session)object, channel);
        object = new Buffer();
        Packet packet = new Packet((Buffer)object);
        packet.reset();
        object.putByte((byte)98);
        object.putInt(channel.getRecipient());
        object.putString(Util.str2byte("window-change"));
        object.putByte((byte)(this.waitForReply() ? 1 : 0));
        object.putInt(this.width_columns);
        object.putInt(this.height_rows);
        object.putInt(this.width_pixels);
        object.putInt(this.height_pixels);
        this.write(packet);
    }

    void setSize(int n, int n2, int n3, int n4) {
        this.width_columns = n;
        this.height_rows = n2;
        this.width_pixels = n3;
        this.height_pixels = n4;
    }
}
