// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.engine;

import java.io.OutputStream;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import com.jcraft.jsch.ChannelExec;
import de.cisha.chess.engine.UCIEngine;

public class RemoteUCIEngine extends UCIEngine
{
    private ChannelExec _channelExec;
    
    public RemoteUCIEngine(final ChannelExec channelExec) {
        this._channelExec = channelExec;
    }
    
    @Override
    protected void destroyStreams() {
        this._channelExec.disconnect();
    }
    
    @Override
    protected InputStream getConnectedInputStream() {
        try {
            return this._channelExec.getInputStream();
        }
        catch (IOException ex) {
            Log.d(RemoteUCIEngine.class.getName(), IOException.class.getName(), (Throwable)ex);
            return null;
        }
    }
    
    @Override
    protected OutputStream getConnectedOutputStream() {
        try {
            return this._channelExec.getOutputStream();
        }
        catch (IOException ex) {
            Log.d(RemoteUCIEngine.class.getName(), IOException.class.getName(), (Throwable)ex);
            return null;
        }
    }
    
    @Override
    public String getName() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Remote engine: ");
        sb.append(super.getName());
        return sb.toString();
    }
}
