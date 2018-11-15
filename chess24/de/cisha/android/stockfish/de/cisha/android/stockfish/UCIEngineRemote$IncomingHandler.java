/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 */
package de.cisha.android.stockfish;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import de.cisha.android.stockfish.UCIEngineRemote;
import de.cisha.chess.util.WritableInputStream;

class UCIEngineRemote.IncomingHandler
extends Handler {
    UCIEngineRemote.IncomingHandler() {
    }

    public void handleMessage(Message message) {
        Object object = message.peekData();
        if (object == null) {
            super.handleMessage(message);
            return;
        }
        object.setClassLoader(Thread.currentThread().getContextClassLoader());
        object = object.getString("MSG_KEY");
        if (object == null) {
            super.handleMessage(message);
            return;
        }
        UCIEngineRemote.this._inputStream.addStringToBuffer((String)object);
    }
}
