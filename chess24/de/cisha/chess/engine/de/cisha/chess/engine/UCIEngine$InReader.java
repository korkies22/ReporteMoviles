/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.util.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

private class UCIEngine.InReader
extends Thread {
    private boolean _destroyThread = false;
    private BufferedReader _inReader;

    public UCIEngine.InReader() {
        this._inReader = new BufferedReader(new InputStreamReader(UCIEngine.this.getConnectedInputStream()));
    }

    private String readEngineLine() throws IOException {
        String string;
        String string2 = string = this._inReader.readLine();
        if (string == null) {
            string2 = "";
        }
        return string2;
    }

    @Override
    public void destroy() {
        this._destroyThread = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        do {
            String string = null;
            try {
                String string2;
                string = string2 = this.readEngineLine();
            }
            catch (IOException iOException) {
                Logger logger = Logger.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error reading Engine line: status=");
                stringBuilder.append(UCIEngine.this._status);
                stringBuilder.append(" ");
                logger.error(UCIEngine.LOG_TAG, stringBuilder.toString(), iOException);
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException interruptedException) {}
            }
            if (this._destroyThread) {
                UCIEngine.this._status = 6;
                try {
                    this._inReader.close();
                    return;
                }
                catch (IOException iOException) {
                    Logger.getInstance().debug(UCIEngine.LOG_TAG, "Error closing Engine Stream:", iOException);
                    return;
                }
            }
            if (string == null) continue;
            UCIEngine.this.receivedEngineLine(string);
        } while (true);
    }
}
