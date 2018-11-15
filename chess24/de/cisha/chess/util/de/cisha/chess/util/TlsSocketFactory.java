/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TlsSocketFactory
extends SSLSocketFactory {
    SSLSocketFactory sslSocketFactory;

    public TlsSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sslSocketFactory = sSLSocketFactory;
    }

    @Override
    public Socket createSocket(String object, int n) throws IOException, UnknownHostException {
        object = (SSLSocket)this.sslSocketFactory.createSocket((String)object, n);
        object.setEnabledProtocols(new String[]{"TLSv1.2"});
        return object;
    }

    @Override
    public Socket createSocket(String object, int n, InetAddress inetAddress, int n2) throws IOException, UnknownHostException {
        object = (SSLSocket)this.sslSocketFactory.createSocket((String)object, n, inetAddress, n2);
        object.setEnabledProtocols(new String[]{"TLSv1.2"});
        return object;
    }

    @Override
    public Socket createSocket(InetAddress object, int n) throws IOException {
        object = (SSLSocket)this.sslSocketFactory.createSocket((InetAddress)object, n);
        object.setEnabledProtocols(new String[]{"TLSv1.2"});
        return object;
    }

    @Override
    public Socket createSocket(InetAddress object, int n, InetAddress inetAddress, int n2) throws IOException {
        object = (SSLSocket)this.sslSocketFactory.createSocket((InetAddress)object, n, inetAddress, n2);
        object.setEnabledProtocols(new String[]{"TLSv1.2"});
        return object;
    }

    @Override
    public Socket createSocket(Socket socket, String string, int n, boolean bl) throws IOException {
        socket = (SSLSocket)this.sslSocketFactory.createSocket(socket, string, n, bl);
        socket.setEnabledProtocols(new String[]{"TLSv1.2"});
        return socket;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }
}
