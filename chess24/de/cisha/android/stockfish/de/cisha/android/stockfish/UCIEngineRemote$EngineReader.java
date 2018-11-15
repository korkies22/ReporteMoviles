/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Messenger
 */
package de.cisha.android.stockfish;

import android.os.Messenger;
import de.cisha.android.board.IChessServiceContext;
import de.cisha.android.stockfish.UCIEngineRemote;
import de.cisha.chess.util.Logger;
import java.io.BufferedReader;
import java.io.IOException;

private class UCIEngineRemote.EngineReader
extends Thread {
    private UCIEngineRemote.EngineReader() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        while (UCIEngineRemote.this._alive) {
            try {
                String string = UCIEngineRemote.this._engineOutputReader.readLine();
                if (string != null) {
                    sChessServiceContext.sendChessCommand(string, UCIEngineRemote.this.mMessenger);
                }
            }
            catch (IOException iOException) {
                Logger.getInstance().error(UCIEngineRemote.TAG, "Exception", iOException);
            }
            try {
                Thread.sleep(33L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }
}
