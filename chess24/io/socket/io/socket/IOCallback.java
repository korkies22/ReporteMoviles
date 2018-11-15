/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package io.socket;

import io.socket.IOAcknowledge;
import io.socket.SocketIOException;
import org.json.JSONObject;

public interface IOCallback {
    public /* varargs */ void on(String var1, IOAcknowledge var2, Object ... var3);

    public void onConnect();

    public void onDisconnect();

    public void onError(SocketIOException var1);

    public void onMessage(String var1, IOAcknowledge var2);

    public void onMessage(JSONObject var1, IOAcknowledge var2);
}
