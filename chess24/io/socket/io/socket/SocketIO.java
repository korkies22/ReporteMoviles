/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package io.socket;

import io.socket.ConnectionListener;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.IOConnection;
import io.socket.IOTransport;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import org.json.JSONObject;

public class SocketIO {
    private IOCallback callback;
    private IOConnection connection;
    private Properties headers = new Properties();
    private String namespace;
    private URL url;

    static {
        try {
            SocketIO.setDefaultSSLSocketFactory(SSLContext.getDefault());
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }
    }

    public SocketIO() {
    }

    public SocketIO(String string) throws MalformedURLException {
        if (string == null) {
            throw new RuntimeException("url may not be null.");
        }
        this.setAndConnect(new URL(string), null, null);
    }

    public SocketIO(String string, IOCallback iOCallback, ConnectionListener connectionListener) throws MalformedURLException {
        this.connect(string, iOCallback, connectionListener);
    }

    public SocketIO(String string, Properties properties, ConnectionListener connectionListener) throws MalformedURLException {
        if (string == null) {
            throw new RuntimeException("url may not be null.");
        }
        if (properties != null) {
            this.headers = properties;
        }
        this.setAndConnect(new URL(string), null, connectionListener);
    }

    public SocketIO(URL uRL, ConnectionListener connectionListener) {
        this.setAndConnect(uRL, null, connectionListener);
    }

    public SocketIO(URL uRL, IOCallback iOCallback, ConnectionListener connectionListener) {
        if (!this.setAndConnect(uRL, iOCallback, connectionListener)) {
            throw new RuntimeException("url and callback may not be null.");
        }
    }

    private boolean setAndConnect(URL object, IOCallback iOCallback, ConnectionListener connectionListener) {
        if (this.connection != null) {
            throw new RuntimeException("You can connect your SocketIO instance only once. Use a fresh instance instead.");
        }
        if (this.url != null && object != null || this.callback != null && iOCallback != null) {
            return false;
        }
        if (object != null) {
            this.url = object;
        }
        if (iOCallback != null) {
            this.callback = iOCallback;
        }
        if (this.callback != null && this.url != null) {
            object = new StringBuilder();
            object.append(this.url.getProtocol());
            object.append("://");
            object.append(this.url.getAuthority());
            object = object.toString();
            this.namespace = this.url.getPath();
            if (this.namespace.equals("/")) {
                this.namespace = "";
            }
            this.connection = IOConnection.register((String)object, this, connectionListener);
            return true;
        }
        return false;
    }

    public static void setDefaultSSLSocketFactory(SSLContext sSLContext) {
        IOConnection.setSslContext(sSLContext);
    }

    public SocketIO addHeader(String string, String string2) {
        if (this.connection != null) {
            throw new RuntimeException("You may only set headers before connecting.\n Try to use new SocketIO().addHeader(key, value).connect(host, callback) instead of SocketIO(host, callback).addHeader(key, value)");
        }
        this.headers.setProperty(string, string2);
        return this;
    }

    public void connect(IOCallback iOCallback, ConnectionListener connectionListener) {
        if (!this.setAndConnect(null, iOCallback, connectionListener)) {
            if (iOCallback == null) {
                throw new RuntimeException("callback may not be null.");
            }
            if (this.url == null) {
                throw new RuntimeException("connect(IOCallback) can only be invoked after SocketIO(String) or SocketIO(URL)");
            }
        }
    }

    public void connect(String string, IOCallback iOCallback, ConnectionListener connectionListener) throws MalformedURLException {
        if (!this.setAndConnect(new URL(string), iOCallback, connectionListener)) {
            if (string != null && iOCallback != null) {
                throw new RuntimeException("connect(String, IOCallback) can only be invoked after SocketIO()");
            }
            throw new RuntimeException("url and callback may not be null.");
        }
    }

    public void connect(URL uRL, IOCallback iOCallback, ConnectionListener connectionListener) {
        if (!this.setAndConnect(uRL, iOCallback, connectionListener)) {
            if (uRL != null && iOCallback != null) {
                throw new RuntimeException("connect(URL, IOCallback) can only be invoked after SocketIO()");
            }
            throw new RuntimeException("url and callback may not be null.");
        }
    }

    public void disconnect() {
        this.connection.unregister(this);
    }

    public /* varargs */ void emit(String string, IOAcknowledge iOAcknowledge, Object ... arrobject) {
        this.connection.emit(this, string, iOAcknowledge, arrobject);
    }

    public /* varargs */ void emit(String string, Object ... arrobject) {
        this.connection.emit(this, string, null, arrobject);
    }

    public IOCallback getCallback() {
        return this.callback;
    }

    public String getHeader(String string) {
        if (this.headers.contains(string)) {
            return this.headers.getProperty(string);
        }
        return null;
    }

    public Properties getHeaders() {
        return this.headers;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getTransport() {
        IOTransport iOTransport = this.connection.getTransport();
        if (iOTransport != null) {
            return iOTransport.getName();
        }
        return null;
    }

    public boolean isConnected() {
        if (this.connection != null && this.connection.isConnected()) {
            return true;
        }
        return false;
    }

    public void reconnect() {
        this.connection.reconnect();
    }

    public void send(IOAcknowledge iOAcknowledge, String string) {
        this.connection.send(this, iOAcknowledge, string);
    }

    public void send(IOAcknowledge iOAcknowledge, JSONObject jSONObject) {
        this.connection.send(this, iOAcknowledge, jSONObject);
    }

    public void send(String string) {
        this.connection.send(this, null, string);
    }

    public void send(JSONObject jSONObject) {
        this.connection.send(this, null, jSONObject);
    }

    void setHeaders(Properties properties) {
        this.headers = properties;
    }
}
