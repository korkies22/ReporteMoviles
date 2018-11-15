/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

public class PlayzoneGameSessionInfo {
    private final String gameToken;
    private final String host;
    private final int port;
    private final boolean white;

    public PlayzoneGameSessionInfo(String string, boolean bl, String string2, int n) {
        this.white = bl;
        this.gameToken = string;
        this.host = string2;
        this.port = n;
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = object instanceof PlayzoneGameSessionInfo;
        boolean bl3 = bl = false;
        if (bl2) {
            object = (PlayzoneGameSessionInfo)object;
            bl3 = bl;
            if (this.gameToken.equals(object.getGameToken())) {
                bl3 = bl;
                if (this.white == object.getColor()) {
                    bl3 = true;
                }
            }
        }
        return bl3;
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

    public int hashCode() {
        if (this.gameToken != null) {
            return this.gameToken.hashCode();
        }
        return 0;
    }
}
