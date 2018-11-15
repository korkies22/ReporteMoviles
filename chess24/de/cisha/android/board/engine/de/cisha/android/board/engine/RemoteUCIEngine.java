/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package de.cisha.android.board.engine;

import android.util.Log;
import com.jcraft.jsch.ChannelExec;
import de.cisha.chess.engine.UCIEngine;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RemoteUCIEngine
extends UCIEngine {
    private ChannelExec _channelExec;

    public RemoteUCIEngine(ChannelExec channelExec) {
        this._channelExec = channelExec;
    }

    @Override
    protected void destroyStreams() {
        this._channelExec.disconnect();
    }

    @Override
    protected InputStream getConnectedInputStream() {
        try {
            InputStream inputStream = this._channelExec.getInputStream();
            return inputStream;
        }
        catch (IOException iOException) {
            Log.d((String)RemoteUCIEngine.class.getName(), (String)IOException.class.getName(), (Throwable)iOException);
            return null;
        }
    }

    @Override
    protected OutputStream getConnectedOutputStream() {
        try {
            OutputStream outputStream = this._channelExec.getOutputStream();
            return outputStream;
        }
        catch (IOException iOException) {
            Log.d((String)RemoteUCIEngine.class.getName(), (String)IOException.class.getName(), (Throwable)iOException);
            return null;
        }
    }

    @Override
    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Remote engine: ");
        stringBuilder.append(super.getName());
        return stringBuilder.toString();
    }
}
