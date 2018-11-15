/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ForwardedTCPIPDaemon;
import com.jcraft.jsch.IO;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SocketFactory;
import com.jcraft.jsch.Util;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.util.Vector;

public class ChannelForwardedTCPIP
extends Channel {
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final int TIMEOUT = 10000;
    private static Vector pool = new Vector();
    private Config config = null;
    private ForwardedTCPIPDaemon daemon = null;
    private Socket socket = null;

    ChannelForwardedTCPIP() {
        this.setLocalWindowSizeMax(131072);
        this.setLocalWindowSize(131072);
        this.setLocalPacketSize(16384);
        this.io = new IO();
        this.connected = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void addPort(Session object, String object2, int n, int n2, String string, int n3, SocketFactory socketFactory) throws JSchException {
        String string2 = ChannelForwardedTCPIP.normalize((String)object2);
        object2 = pool;
        synchronized (object2) {
            if (ChannelForwardedTCPIP.getPort((Session)object, string2, n) != null) {
                object = new StringBuilder();
                object.append("PortForwardingR: remote port ");
                object.append(n);
                object.append(" is already registered.");
                throw new JSchException(object.toString());
            }
            ConfigLHost configLHost = new ConfigLHost();
            configLHost.session = object;
            configLHost.rport = n;
            configLHost.allocated_rport = n2;
            configLHost.target = string;
            configLHost.lport = n3;
            configLHost.address_to_bind = string2;
            configLHost.factory = socketFactory;
            pool.addElement(configLHost);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void addPort(Session object, String object2, int n, int n2, String string, Object[] arrobject) throws JSchException {
        String string2 = ChannelForwardedTCPIP.normalize((String)object2);
        object2 = pool;
        synchronized (object2) {
            if (ChannelForwardedTCPIP.getPort((Session)object, string2, n) != null) {
                object = new StringBuilder();
                object.append("PortForwardingR: remote port ");
                object.append(n);
                object.append(" is already registered.");
                throw new JSchException(object.toString());
            }
            ConfigDaemon configDaemon = new ConfigDaemon();
            configDaemon.session = object;
            configDaemon.rport = n;
            configDaemon.allocated_rport = n;
            configDaemon.target = string;
            configDaemon.arg = arrobject;
            configDaemon.address_to_bind = string2;
            pool.addElement(configDaemon);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void delPort(ChannelForwardedTCPIP channelForwardedTCPIP) {
        Session session;
        try {
            session = channelForwardedTCPIP.getSession();
        }
        catch (JSchException jSchException) {
            return;
        }
        if (session == null) return;
        if (channelForwardedTCPIP.config == null) return;
        ChannelForwardedTCPIP.delPort(session, channelForwardedTCPIP.config.rport);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void delPort(Session session) {
        Vector vector = pool;
        synchronized (vector) {
            int n;
            int[] arrn = new int[pool.size()];
            int n2 = 0;
            int n3 = n = 0;
            do {
                int n4;
                if (n < pool.size()) {
                    Config config = (Config)pool.elementAt(n);
                    n4 = n3;
                    if (config.session == session) {
                        arrn[n3] = config.rport;
                        n4 = n3 + 1;
                    }
                } else {
                    // MONITOREXIT [3, 4, 8] lbl15 : MonitorExitStatement: MONITOREXIT : var5_1
                    n = n2;
                    do {
                        if (n >= n3) {
                            return;
                        }
                        ChannelForwardedTCPIP.delPort(session, arrn[n]);
                        ++n;
                    } while (true);
                }
                ++n;
                n3 = n4;
            } while (true);
        }
    }

    static void delPort(Session session, int n) {
        ChannelForwardedTCPIP.delPort(session, null, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void delPort(Session session, String object, int n) {
        Object object2;
        Object object3;
        Vector vector = pool;
        synchronized (vector) {
            object2 = object3 = ChannelForwardedTCPIP.getPort(session, ChannelForwardedTCPIP.normalize((String)object), n);
            if (object3 == null) {
                object2 = ChannelForwardedTCPIP.getPort(session, null, n);
            }
            if (object2 == null) {
                return;
            }
            pool.removeElement(object2);
            object3 = object;
            if (object == null) {
                object3 = object2.address_to_bind;
            }
            object = object3;
            if (object3 == null) {
                object = "0.0.0.0";
            }
        }
        object3 = new Buffer(100);
        object2 = new Packet((Buffer)object3);
        try {
            object2.reset();
            object3.putByte((byte)80);
            object3.putString(Util.str2byte("cancel-tcpip-forward"));
            object3.putByte((byte)0);
            object3.putString(Util.str2byte((String)object));
            object3.putInt(n);
            session.write((Packet)object2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Config getPort(Session session, String string, int n) {
        Vector vector = pool;
        synchronized (vector) {
            int n2 = 0;
            while (n2 < pool.size()) {
                Config config = (Config)pool.elementAt(n2);
                if (config.session == session && (config.rport == n || config.rport == 0 && config.allocated_rport == n) && (string == null || config.address_to_bind.equals(string))) {
                    return config;
                }
                ++n2;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String[] getPortForwarding(Session object) {
        object = new Vector();
        String[] arrstring = pool;
        synchronized (arrstring) {
            int n = 0;
            int n2 = 0;
            do {
                if (n2 < pool.size()) {
                    StringBuilder stringBuilder;
                    Config config = (Config)pool.elementAt(n2);
                    if (config instanceof ConfigDaemon) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(config.allocated_rport);
                        stringBuilder.append(":");
                        stringBuilder.append(config.target);
                        stringBuilder.append(":");
                        object.addElement(stringBuilder.toString());
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(config.allocated_rport);
                        stringBuilder.append(":");
                        stringBuilder.append(config.target);
                        stringBuilder.append(":");
                        stringBuilder.append(((ConfigLHost)config).lport);
                        object.addElement(stringBuilder.toString());
                    }
                } else {
                    // MONITOREXIT [2, 3, 8] lbl25 : MonitorExitStatement: MONITOREXIT : var3_1
                    arrstring = new String[object.size()];
                    n2 = n;
                    do {
                        if (n2 >= object.size()) {
                            return arrstring;
                        }
                        arrstring[n2] = (String)object.elementAt(n2);
                        ++n2;
                    } while (true);
                }
                ++n2;
            } while (true);
        }
    }

    static String normalize(String string) {
        if (string == null) {
            return "localhost";
        }
        if (string.length() != 0 && !string.equals("*")) {
            return string;
        }
        return "";
    }

    private void setSocketFactory(SocketFactory socketFactory) {
        if (this.config != null && this.config instanceof ConfigLHost) {
            ((ConfigLHost)this.config).factory = socketFactory;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void getData(Buffer object) {
        byte[] arrby;
        int n;
        block4 : {
            this.setRecipient(object.getInt());
            this.setRemoteWindowSize(object.getUInt());
            this.setRemotePacketSize(object.getInt());
            arrby = object.getString();
            n = object.getInt();
            object.getString();
            object.getInt();
            try {
                object = this.getSession();
                break block4;
            }
            catch (JSchException jSchException) {}
            object = null;
        }
        this.config = ChannelForwardedTCPIP.getPort((Session)object, Util.byte2str(arrby), n);
        if (this.config == null) {
            this.config = ChannelForwardedTCPIP.getPort((Session)object, null, n);
        }
        if (this.config == null && JSch.getLogger().isEnabled(3)) {
            object = JSch.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ChannelForwardedTCPIP: ");
            stringBuilder.append(Util.byte2str(arrby));
            stringBuilder.append(":");
            stringBuilder.append(n);
            stringBuilder.append(" is not registered.");
            object.log(3, stringBuilder.toString());
        }
    }

    public int getRemotePort() {
        if (this.config != null) {
            return this.config.rport;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        block12 : {
            Object object;
            Session session;
            Object object2;
            try {
                if (this.config instanceof ConfigDaemon) {
                    object = (ConfigDaemon)this.config;
                    this.daemon = (ForwardedTCPIPDaemon)Class.forName(object.target).newInstance();
                    object2 = new PipedOutputStream();
                    this.io.setInputStream(new Channel.PassiveInputStream(this, (PipedOutputStream)object2, 32768), false);
                    this.daemon.setChannel(this, this.getInputStream(), (OutputStream)object2);
                    this.daemon.setArg(object.arg);
                    new Thread(this.daemon).start();
                } else {
                    object = (ConfigLHost)this.config;
                    object = object.factory == null ? Util.createSocket(object.target, object.lport, 10000) : object.factory.createSocket(object.target, object.lport);
                    this.socket = object;
                    this.socket.setTcpNoDelay(true);
                    this.io.setInputStream(this.socket.getInputStream());
                    this.io.setOutputStream(this.socket.getOutputStream());
                }
                this.sendOpenConfirmation();
            }
            catch (Exception exception) {}
            this.thread = Thread.currentThread();
            object = new Buffer(this.rmpsize);
            object2 = new Packet((Buffer)object);
            try {
                session = this.getSession();
            }
            catch (Exception exception) {
                break block12;
            }
            this.sendOpenFailure(1);
            this.close = true;
            this.disconnect();
            return;
            while (this.thread != null && this.io != null && this.io.in != null) {
                int n = this.io.in.read(object.buffer, 14, object.buffer.length - 14 - 128);
                if (n <= 0) {
                    this.eof();
                    break;
                }
                object2.reset();
                object.putByte((byte)94);
                object.putInt(this.recipient);
                object.putInt(n);
                object.skip(n);
                synchronized (this) {
                    if (this.close) {
                        break;
                    }
                    session.write((Packet)object2, this, n);
                }
            }
        }
        this.disconnect();
    }

    static abstract class Config {
        String address_to_bind;
        int allocated_rport;
        int rport;
        Session session;
        String target;

        Config() {
        }
    }

    static class ConfigDaemon
    extends Config {
        Object[] arg;

        ConfigDaemon() {
        }
    }

    static class ConfigLHost
    extends Config {
        SocketFactory factory;
        int lport;

        ConfigLHost() {
        }
    }

}
