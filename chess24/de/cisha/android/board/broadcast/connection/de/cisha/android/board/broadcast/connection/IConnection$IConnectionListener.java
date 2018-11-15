/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.connection;

import de.cisha.android.board.broadcast.connection.IConnection;

public static interface IConnection.IConnectionListener {
    public void connectionClosed(IConnection var1);

    public void connectionEstablished(IConnection var1);

    public void connectionFailed(IConnection var1);
}
