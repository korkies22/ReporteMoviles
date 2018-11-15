/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.connection;

import de.cisha.android.board.broadcast.connection.IConnection;

public interface ConnectionLifecycleManager {
    public void addConnection(IConnection var1);

    public void removeConnection(IConnection var1, boolean var2);
}
