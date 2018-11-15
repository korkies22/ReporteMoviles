/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.util.Logger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LocalUCIEngine
extends UCIEngine {
    private File _engineExecFile = null;
    private Process _engineProcess;

    public LocalUCIEngine(File file) {
        this._engineExecFile = file;
    }

    @Override
    protected void destroyStreams() {
        if (this._engineProcess != null) {
            this._engineProcess.destroy();
        }
        this._engineProcess = null;
    }

    @Override
    protected InputStream getConnectedInputStream() {
        return this._engineProcess.getInputStream();
    }

    @Override
    protected OutputStream getConnectedOutputStream() {
        return this._engineProcess.getOutputStream();
    }

    @Override
    public void startup() {
        try {
            this._engineProcess = Runtime.getRuntime().exec(this._engineExecFile.getAbsolutePath());
        }
        catch (IOException iOException) {
            Logger.getInstance().debug(LocalUCIEngine.class.getName(), IOException.class.getName(), iOException);
        }
        super.startup();
    }
}
