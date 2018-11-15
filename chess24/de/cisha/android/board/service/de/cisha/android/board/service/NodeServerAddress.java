/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

public class NodeServerAddress {
    private int _port;
    private String _serverAddress;

    public NodeServerAddress(String string, int n) {
        this._serverAddress = string;
        this._port = n;
    }

    public int getPort() {
        return this._port;
    }

    public String getServerAddress() {
        return this._serverAddress;
    }

    public String getURLString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = this._serverAddress.contains("://") ? "" : "https://";
        stringBuilder.append(string);
        stringBuilder.append(this._serverAddress);
        stringBuilder.append(":");
        stringBuilder.append(this._port);
        return stringBuilder.toString();
    }

    public String toString() {
        return this.getURLString();
    }
}
