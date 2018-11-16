// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.connection;

public interface IConnection
{
    void addConnectionListener(final IConnectionListener p0);
    
    void close();
    
    void connect();
    
    boolean isConnected();
    
    public interface IConnectionListener
    {
        void connectionClosed(final IConnection p0);
        
        void connectionEstablished(final IConnection p0);
        
        void connectionFailed(final IConnection p0);
    }
}
