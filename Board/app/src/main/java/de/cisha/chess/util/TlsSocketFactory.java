// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import javax.net.ssl.SSLSocket;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;

public class TlsSocketFactory extends SSLSocketFactory
{
    SSLSocketFactory sslSocketFactory;
    
    public TlsSocketFactory(final SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }
    
    @Override
    public Socket createSocket(final String s, final int n) throws IOException, UnknownHostException {
        final SSLSocket sslSocket = (SSLSocket)this.sslSocketFactory.createSocket(s, n);
        sslSocket.setEnabledProtocols(new String[] { "TLSv1.2" });
        return sslSocket;
    }
    
    @Override
    public Socket createSocket(final String s, final int n, final InetAddress inetAddress, final int n2) throws IOException, UnknownHostException {
        final SSLSocket sslSocket = (SSLSocket)this.sslSocketFactory.createSocket(s, n, inetAddress, n2);
        sslSocket.setEnabledProtocols(new String[] { "TLSv1.2" });
        return sslSocket;
    }
    
    @Override
    public Socket createSocket(final InetAddress inetAddress, final int n) throws IOException {
        final SSLSocket sslSocket = (SSLSocket)this.sslSocketFactory.createSocket(inetAddress, n);
        sslSocket.setEnabledProtocols(new String[] { "TLSv1.2" });
        return sslSocket;
    }
    
    @Override
    public Socket createSocket(final InetAddress inetAddress, final int n, final InetAddress inetAddress2, final int n2) throws IOException {
        final SSLSocket sslSocket = (SSLSocket)this.sslSocketFactory.createSocket(inetAddress, n, inetAddress2, n2);
        sslSocket.setEnabledProtocols(new String[] { "TLSv1.2" });
        return sslSocket;
    }
    
    @Override
    public Socket createSocket(final Socket socket, final String s, final int n, final boolean b) throws IOException {
        final SSLSocket sslSocket = (SSLSocket)this.sslSocketFactory.createSocket(socket, s, n, b);
        sslSocket.setEnabledProtocols(new String[] { "TLSv1.2" });
        return sslSocket;
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
