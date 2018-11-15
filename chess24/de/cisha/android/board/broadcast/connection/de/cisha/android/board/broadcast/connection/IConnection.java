/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.connection;

public interface IConnection {
    public void addConnectionListener(IConnectionListener var1);

    public void close();

    public void connect();

    public boolean isConnected();

    public static interface IConnectionListener {
        public void connectionClosed(IConnection var1);

        public void connectionEstablished(IConnection var1);

        public void connectionFailed(IConnection var1);
    }

}
