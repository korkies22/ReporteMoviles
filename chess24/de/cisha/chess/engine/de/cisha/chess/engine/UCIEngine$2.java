/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.util.Logger;
import java.io.PrintWriter;

class UCIEngine
implements Runnable {
    final /* synthetic */ String val$command;

    UCIEngine(String string) {
        this.val$command = string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        if (!UCIEngine.this._outWriter.checkError()) {
            PrintWriter printWriter = UCIEngine.this._outWriter;
            synchronized (printWriter) {
                UCIEngine.this._outWriter.println(this.val$command);
                return;
            }
        }
        Logger logger = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("not able to send command to engine - engine closed:");
        stringBuilder.append(this.val$command);
        logger.error("Engine", stringBuilder.toString());
    }
}
