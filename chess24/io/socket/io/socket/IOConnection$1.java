/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package io.socket;

import io.socket.IOAcknowledge;
import io.socket.IOMessage;
import io.socket.SocketIOException;
import org.json.JSONArray;
import org.json.JSONObject;

class IOConnection
implements IOAcknowledge {
    final /* synthetic */ String val$endPoint;
    final /* synthetic */ String val$id;

    IOConnection(String string, String string2) {
        this.val$endPoint = string;
        this.val$id = string2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public /* varargs */ void ack(Object ... var1_1) {
        var6_2 = new JSONArray();
        var2_3 = 0;
        var3_4 = ((Object)var1_1).length;
        do {
            block4 : {
                if (var2_3 >= var3_4) {
                    var1_1 = this.val$endPoint;
                    var4_5 = new StringBuilder();
                    var4_5.append(this.val$id);
                    var4_5.append(var6_2.toString());
                    var1_1 = new IOMessage(6, (String)var1_1, var4_5.toString());
                    io.socket.IOConnection.access$400(IOConnection.this, var1_1.toString());
                    return;
                }
                var4_5 = var5_7 = var1_1[var2_3];
                if (var5_7 != null) ** GOTO lbl17
                try {
                    var4_5 = JSONObject.NULL;
lbl17: // 2 sources:
                    var6_2.put(var4_5);
                    break block4;
                }
                catch (Exception var4_6) {}
                io.socket.IOConnection.access$100(IOConnection.this, new SocketIOException("You can only put values in IOAcknowledge.ack() which can be handled by JSONArray.put()", var4_6));
            }
            ++var2_3;
        } while (true);
    }
}
