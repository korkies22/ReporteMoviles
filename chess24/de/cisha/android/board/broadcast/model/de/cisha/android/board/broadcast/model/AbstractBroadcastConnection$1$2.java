/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.AbstractBroadcastConnection;
import de.cisha.android.board.broadcast.model.AbstractTimeoutIOAcknowledge;
import io.socket.SocketIO;
import java.util.Iterator;
import java.util.Set;

class AbstractBroadcastConnection
extends AbstractTimeoutIOAcknowledge {
    AbstractBroadcastConnection() {
    }

    @Override
    /* varargs */ void onAck(Object ... object) {
        if (((Object[])object).length > 0 && object[0] instanceof Long) {
            long l = (Long)object[0];
            1.this.this$0._timeOffset = l - System.currentTimeMillis();
        }
        1.this.this$0.connectingSuccessful();
        object = 1.this.this$0._listeners.iterator();
        while (object.hasNext()) {
            ((IConnection.IConnectionListener)object.next()).connectionEstablished(1.this.this$0);
        }
    }

    @Override
    void onTimeout() {
        1.this.this$0._tournamentsSocket.disconnect();
    }
}
