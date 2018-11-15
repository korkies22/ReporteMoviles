/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.AbstractContentFragment;
import de.cisha.chess.model.CishaUUID;

public interface ITrackingService {
    public void disableUserTracking();

    public void enableUserTracking();

    public boolean isTrackingEnabled();

    public void setDebugMode(boolean var1);

    public void trackEvent(TrackingCategory var1, String var2, String var3);

    public void trackException(String var1, Throwable var2);

    public void trackFatalException(String var1, Throwable var2);

    public void trackScreenOpen(AbstractContentFragment var1);

    public void trackScreenOpen(String var1);

    public void trackUserLogin(CishaUUID var1, String var2);

    public static enum TrackingCategory {
        PLAYZONE("Play Now"),
        ANALYSIS("Analysis"),
        TACTICS("Tactics"),
        USER("UX"),
        SHOP("Shop"),
        BROADCAST("Broadcast"),
        SOCIAL("Social"),
        PROFILE("Profile");
        
        private String _name;

        private TrackingCategory(String string2) {
            this._name = string2;
        }

        public String getName() {
            return this._name;
        }
    }

}
