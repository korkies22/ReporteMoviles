/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelDirectTCPIP;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ServerSocketFactory;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

class PortWatcher
implements Runnable {
    private static InetAddress anyLocalAddress;
    private static Vector pool;
    InetAddress boundaddress;
    int connectTimeout = 0;
    String host;
    int lport;
    int rport;
    Session session;
    ServerSocket ss;
    Runnable thread;

    static {
        pool = new Vector();
        try {
            anyLocalAddress = InetAddress.getByName("0.0.0.0");
        }
        catch (UnknownHostException unknownHostException) {}
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    PortWatcher(Session object, String string, int n, String charSequence, int n2, ServerSocketFactory serverSocketFactory) throws JSchException {
        this.session = object;
        this.lport = n;
        this.host = charSequence;
        this.rport = n2;
        try {
            this.boundaddress = InetAddress.getByName(string);
            object = serverSocketFactory == null ? new ServerSocket(n, 0, this.boundaddress) : serverSocketFactory.createServerSocket(n, 0, this.boundaddress);
            this.ss = object;
            if (n == 0 && (n = this.ss.getLocalPort()) != -1) {
                this.lport = n;
            }
            return;
        }
        catch (Exception exception) {
            charSequence = new StringBuilder();
            charSequence.append("PortForwardingL: local port ");
            charSequence.append(string);
            charSequence.append(":");
            charSequence.append(n);
            charSequence.append(" cannot be bound.");
            string = charSequence.toString();
            if (exception instanceof Throwable) {
                throw new JSchException(string, exception);
            }
            throw new JSchException(string);
        }
    }

    static PortWatcher addPort(Session object, String string, int n, String string2, int n2, ServerSocketFactory serverSocketFactory) throws JSchException {
        if (PortWatcher.getPort((Session)object, string = PortWatcher.normalize(string), n) != null) {
            object = new StringBuilder();
            object.append("PortForwardingL: local port ");
            object.append(string);
            object.append(":");
            object.append(n);
            object.append(" is already registered.");
            throw new JSchException(object.toString());
        }
        object = new PortWatcher((Session)object, string, n, string2, n2, serverSocketFactory);
        pool.addElement(object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void delPort(Session runnable) {
        Vector vector = pool;
        synchronized (vector) {
            int n;
            PortWatcher[] arrportWatcher = new PortWatcher[pool.size()];
            int n2 = 0;
            int n3 = n = 0;
            do {
                int n4 = n2;
                if (n < pool.size()) {
                    PortWatcher portWatcher = (PortWatcher)pool.elementAt(n);
                    n4 = n3;
                    if (portWatcher.session == runnable) {
                        portWatcher.delete();
                        arrportWatcher[n3] = portWatcher;
                        n4 = n3 + 1;
                    }
                } else {
                    do {
                        if (n4 >= n3) {
                            return;
                        }
                        runnable = arrportWatcher[n4];
                        pool.removeElement(runnable);
                        ++n4;
                    } while (true);
                }
                ++n;
                n3 = n4;
            } while (true);
        }
    }

    static void delPort(Session object, String string, int n) throws JSchException {
        if ((object = PortWatcher.getPort((Session)object, string = PortWatcher.normalize(string), n)) == null) {
            object = new StringBuilder();
            object.append("PortForwardingL: local port ");
            object.append(string);
            object.append(":");
            object.append(n);
            object.append(" is not registered.");
            throw new JSchException(object.toString());
        }
        object.delete();
        pool.removeElement(object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    static PortWatcher getPort(Session session, String object, int n) throws JSchException {
        try {}
        catch (UnknownHostException unknownHostException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PortForwardingL: invalid address ");
            stringBuilder.append((String)object);
            stringBuilder.append(" specified.");
            throw new JSchException(stringBuilder.toString(), unknownHostException);
        }
        InetAddress inetAddress = InetAddress.getByName((String)object);
        object = pool;
        // MONITORENTER : object
        int n2 = 0;
        do {
            if (n2 >= pool.size()) {
                // MONITOREXIT : object
                return null;
            }
            PortWatcher portWatcher = (PortWatcher)pool.elementAt(n2);
            if (portWatcher.session == session && portWatcher.lport == n && (anyLocalAddress != null && portWatcher.boundaddress.equals(anyLocalAddress) || portWatcher.boundaddress.equals(inetAddress))) {
                // MONITOREXIT : object
                return portWatcher;
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String[] getPortForwarding(Session arrstring) {
        Vector<String> vector = new Vector<String>();
        Vector vector2 = pool;
        synchronized (vector2) {
            int n = 0;
            int n2 = 0;
            do {
                if (n2 < pool.size()) {
                    PortWatcher portWatcher = (PortWatcher)pool.elementAt(n2);
                    if (portWatcher.session == arrstring) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(portWatcher.lport);
                        stringBuilder.append(":");
                        stringBuilder.append(portWatcher.host);
                        stringBuilder.append(":");
                        stringBuilder.append(portWatcher.rport);
                        vector.addElement(stringBuilder.toString());
                    }
                } else {
                    // MONITOREXIT [2, 3, 7] lbl19 : MonitorExitStatement: MONITOREXIT : var4_2
                    arrstring = new String[vector.size()];
                    n2 = n;
                    do {
                        if (n2 >= vector.size()) {
                            return arrstring;
                        }
                        arrstring[n2] = (String)vector.elementAt(n2);
                        ++n2;
                    } while (true);
                }
                ++n2;
            } while (true);
        }
    }

    private static String normalize(String string) {
        String string2 = string;
        if (string != null) {
            if (string.length() != 0 && !string.equals("*")) {
                string2 = string;
                if (string.equals("localhost")) {
                    return "127.0.0.1";
                }
            } else {
                string2 = "0.0.0.0";
            }
        }
        return string2;
    }

    void delete() {
        this.thread = null;
        try {
            if (this.ss != null) {
                this.ss.close();
            }
            this.ss = null;
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
    @Override
    public void run() {
        this.thread = this;
        try {
            while (this.thread != null) {
                Socket socket = this.ss.accept();
                socket.setTcpNoDelay(true);
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                ChannelDirectTCPIP channelDirectTCPIP = new ChannelDirectTCPIP();
                channelDirectTCPIP.init();
                channelDirectTCPIP.setInputStream(inputStream);
                channelDirectTCPIP.setOutputStream(outputStream);
                this.session.addChannel(channelDirectTCPIP);
                channelDirectTCPIP.setHost(this.host);
                channelDirectTCPIP.setPort(this.rport);
                channelDirectTCPIP.setOrgIPAddress(socket.getInetAddress().getHostAddress());
                channelDirectTCPIP.setOrgPort(socket.getPort());
                channelDirectTCPIP.connect(this.connectTimeout);
                int n = channelDirectTCPIP.exitstatus;
            }
        }
        catch (Exception exception) {}
        this.delete();
    }

    void setConnectTimeout(int n) {
        this.connectTimeout = n;
    }
}
