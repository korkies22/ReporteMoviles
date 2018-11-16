// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

public class PlayzoneGameSessionInfo
{
    private final String gameToken;
    private final String host;
    private final int port;
    private final boolean white;
    
    public PlayzoneGameSessionInfo(final String gameToken, final boolean white, final String host, final int port) {
        this.white = white;
        this.gameToken = gameToken;
        this.host = host;
        this.port = port;
    }
    
    @Override
    public boolean equals(final Object o) {
        final boolean b = o instanceof PlayzoneGameSessionInfo;
        boolean b3;
        final boolean b2 = b3 = false;
        if (b) {
            final PlayzoneGameSessionInfo playzoneGameSessionInfo = (PlayzoneGameSessionInfo)o;
            b3 = b2;
            if (this.gameToken.equals(playzoneGameSessionInfo.getGameToken())) {
                b3 = b2;
                if (this.white == playzoneGameSessionInfo.getColor()) {
                    b3 = true;
                }
            }
        }
        return b3;
    }
    
    public boolean getColor() {
        return this.white;
    }
    
    public String getGameToken() {
        return this.gameToken;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    @Override
    public int hashCode() {
        if (this.gameToken != null) {
            return this.gameToken.hashCode();
        }
        return 0;
    }
}
