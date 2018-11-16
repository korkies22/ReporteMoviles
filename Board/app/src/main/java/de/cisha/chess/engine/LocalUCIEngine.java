// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

import java.io.IOException;
import de.cisha.chess.util.Logger;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;

public class LocalUCIEngine extends UCIEngine
{
    private File _engineExecFile;
    private Process _engineProcess;
    
    public LocalUCIEngine(final File engineExecFile) {
        this._engineExecFile = null;
        this._engineExecFile = engineExecFile;
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
        catch (IOException ex) {
            Logger.getInstance().debug(LocalUCIEngine.class.getName(), IOException.class.getName(), ex);
        }
        super.startup();
    }
}
