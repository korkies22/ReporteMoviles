// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.util.Logger;
import de.cisha.android.board.engine.RemoteUCIEngine;
import de.cisha.chess.engine.UCIEngine;
import java.io.IOException;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ChannelExec;

public class RemoteEngineService implements IEngineService
{
    private static RemoteEngineService _instance;
    private String _host;
    private String _password;
    private String _username;
    
    public RemoteEngineService() {
        this._host = "54.247.191.106";
        this._username = "engineServer";
        this._password = "LVq3tj40S";
    }
    
    private ChannelExec connect(final String s, final String s2, final String password) throws JSchException {
        final Session session = new JSch().getSession(s, s2, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        if (!session.isConnected()) {
            session.connect();
        }
        return (ChannelExec)session.openChannel("exec");
    }
    
    private ChannelExec connectToSSHServer() throws IOException {
        return this.connectToSSHServer(this._username, this._host, this._password, "./houdini");
    }
    
    private ChannelExec connectToSSHServer(final String s, final String s2, final String s3, final String command) throws IOException {
        try {
            final ChannelExec connect = this.connect(s, s2, s3);
            connect.setCommand(command);
            if (connect.isConnected()) {
                return connect;
            }
            connect.connect();
            return connect;
        }
        catch (JSchException ex) {
            throw new IOException("Error connecting to the server", ex);
        }
    }
    
    public static RemoteEngineService getInstance() {
        synchronized (RemoteEngineService.class) {
            if (RemoteEngineService._instance == null) {
                RemoteEngineService._instance = new RemoteEngineService();
            }
            return RemoteEngineService._instance;
        }
    }
    
    @Override
    public UCIEngine getDefaultSingleEngine() {
        try {
            return new RemoteUCIEngine(this.connectToSSHServer());
        }
        catch (IOException ex) {
            Logger.getInstance().debug(RemoteEngineService.class.getName(), IOException.class.getName(), ex);
            return null;
        }
    }
    
    public UCIEngine getEngineFromServer(final String s, final String s2, final String s3, final String s4) throws IOException {
        return new RemoteUCIEngine(this.connectToSSHServer(s, s2, s3, s4));
    }
    
    @Override
    public UCIEngine getEngineWithName(final String s) {
        return this.getDefaultSingleEngine();
    }
    
    public String getHost() {
        return this._host;
    }
    
    public String getPassword() {
        return this._password;
    }
    
    public String getUsername() {
        return this._username;
    }
}
