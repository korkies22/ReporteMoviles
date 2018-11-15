/*
 * Decompiled with CFR 0_134.
 */
package io.socket;

import io.socket.IOConnection;
import io.socket.IOTransport;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLSocketFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

class WebsocketTransport
extends WebSocketClient
implements IOTransport {
    private static final Pattern PATTERN_HTTP = Pattern.compile("^http");
    public static final String TRANSPORT_NAME = "websocket";
    private IOConnection connection;

    public WebsocketTransport(URI uRI, IOConnection object) {
        super(uRI);
        this.connection = object;
        object = IOConnection.getSslContext();
        Object object2 = object.getSocketFactory();
        if ("wss".equals(uRI.getScheme()) && object != null) {
            try {
                this.setSocket(object2.createSocket());
                return;
            }
            catch (IOException iOException) {
                object = System.err;
                object2 = new StringBuilder();
                object2.append("WebsocketTransport: error initializing secure socket:");
                object2.append(iOException.getLocalizedMessage());
                object.println(object2.toString());
                iOException.printStackTrace();
            }
        }
    }

    public static IOTransport create(URL uRL, IOConnection iOConnection) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PATTERN_HTTP.matcher(uRL.toString()).replaceFirst("ws"));
        stringBuilder.append("/socket.io/1/");
        stringBuilder.append(TRANSPORT_NAME);
        stringBuilder.append("/");
        stringBuilder.append(iOConnection.getSessionId());
        return new WebsocketTransport(URI.create(stringBuilder.toString()), iOConnection);
    }

    @Override
    public boolean canSendBulk() {
        return false;
    }

    @Override
    public void disconnect() {
        try {
            this.close();
            return;
        }
        catch (Exception exception) {
            this.connection.transportError(exception);
            return;
        }
    }

    @Override
    public String getName() {
        return TRANSPORT_NAME;
    }

    @Override
    public String getResourceDescriptor() {
        return null;
    }

    @Override
    public void invalidate() {
        this.connection = null;
    }

    @Override
    public void onClose(int n, String string, boolean bl) {
        if (this.connection != null) {
            this.connection.transportDisconnected();
        }
    }

    @Override
    public void onError(Exception exception) {
        if (this.connection != null) {
            this.connection.transportError(exception);
        }
    }

    @Override
    public void onMessage(String string) {
        if (this.connection != null) {
            this.connection.transportMessage(string);
        }
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        if (this.connection != null) {
            this.connection.transportConnected();
        }
    }

    @Override
    public void sendBulk(String[] arrstring) throws IOException {
        throw new RuntimeException("Cannot send Bulk!");
    }
}
