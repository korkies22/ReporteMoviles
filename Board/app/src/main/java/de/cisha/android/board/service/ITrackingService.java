// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.AbstractContentFragment;

public interface ITrackingService
{
    void disableUserTracking();
    
    void enableUserTracking();
    
    boolean isTrackingEnabled();
    
    void setDebugMode(final boolean p0);
    
    void trackEvent(final TrackingCategory p0, final String p1, final String p2);
    
    void trackException(final String p0, final Throwable p1);
    
    void trackFatalException(final String p0, final Throwable p1);
    
    void trackScreenOpen(final AbstractContentFragment p0);
    
    void trackScreenOpen(final String p0);
    
    void trackUserLogin(final CishaUUID p0, final String p1);
    
    public enum TrackingCategory
    {
        ANALYSIS("Analysis"), 
        BROADCAST("Broadcast"), 
        PLAYZONE("Play Now"), 
        PROFILE("Profile"), 
        SHOP("Shop"), 
        SOCIAL("Social"), 
        TACTICS("Tactics"), 
        USER("UX");
        
        private String _name;
        
        private TrackingCategory(final String name) {
            this._name = name;
        }
        
        public String getName() {
            return this._name;
        }
    }
}
