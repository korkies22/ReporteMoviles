// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

public class NodeServerAddress
{
    private int _port;
    private String _serverAddress;
    
    public NodeServerAddress(final String serverAddress, final int port) {
        this._serverAddress = serverAddress;
        this._port = port;
    }
    
    public int getPort() {
        return this._port;
    }
    
    public String getServerAddress() {
        return this._serverAddress;
    }
    
    public String getURLString() {
        final StringBuilder sb = new StringBuilder();
        String s;
        if (this._serverAddress.contains("://")) {
            s = "";
        }
        else {
            s = "https://";
        }
        sb.append(s);
        sb.append(this._serverAddress);
        sb.append(":");
        sb.append(this._port);
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return this.getURLString();
    }
}
