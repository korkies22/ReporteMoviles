/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import de.cisha.android.board.engine.RemoteUCIEngine;
import de.cisha.android.board.service.IEngineService;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.util.Logger;
import java.io.IOException;

public class RemoteEngineService
implements IEngineService {
    private static RemoteEngineService _instance;
    private String _host = "54.247.191.106";
    private String _password = "LVq3tj40S";
    private String _username = "engineServer";

    private ChannelExec connect(String object, String string, String string2) throws JSchException {
        object = new JSch().getSession((String)object, string, 22);
        object.setPassword(string2);
        object.setConfig("StrictHostKeyChecking", "no");
        if (!object.isConnected()) {
            object.connect();
        }
        return (ChannelExec)object.openChannel("exec");
    }

    private ChannelExec connectToSSHServer() throws IOException {
        return this.connectToSSHServer(this._username, this._host, this._password, "./houdini");
    }

    private ChannelExec connectToSSHServer(String object, String string, String string2, String string3) throws IOException {
        block3 : {
            try {
                object = this.connect((String)object, string, string2);
                object.setCommand(string3);
                if (!object.isConnected()) break block3;
                return object;
            }
            catch (JSchException jSchException) {
                throw new IOException("Error connecting to the server", jSchException);
            }
        }
        object.connect();
        return object;
    }

    public static RemoteEngineService getInstance() {
        synchronized (RemoteEngineService.class) {
            if (_instance == null) {
                _instance = new RemoteEngineService();
            }
            RemoteEngineService remoteEngineService = _instance;
            return remoteEngineService;
        }
    }

    @Override
    public UCIEngine getDefaultSingleEngine() {
        try {
            RemoteUCIEngine remoteUCIEngine = new RemoteUCIEngine(this.connectToSSHServer());
            return remoteUCIEngine;
        }
        catch (IOException iOException) {
            Logger.getInstance().debug(RemoteEngineService.class.getName(), IOException.class.getName(), iOException);
            return null;
        }
    }

    public UCIEngine getEngineFromServer(String string, String string2, String string3, String string4) throws IOException {
        return new RemoteUCIEngine(this.connectToSSHServer(string, string2, string3, string4));
    }

    @Override
    public UCIEngine getEngineWithName(String string) {
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
