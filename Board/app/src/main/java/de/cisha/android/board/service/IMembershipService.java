// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

public interface IMembershipService
{
    public static final int NO_LIMITATIONS = Integer.MAX_VALUE;
    
    int getMaximumEngineEvaluationDepth();
    
    int getMaximumEngineVariations();
    
    MembershipLevel getMembershipLevel();
    
    int getNumberOfOpeningTreeMovesAvailable();
    
    int getNumberOfSavedAnalysisAccessible();
    
    int getNumberOfSavedGamesAccessible();
    
    public enum MembershipLevel
    {
        MembershipLevelGuest("Guest"), 
        MembershipLevelPremiumLight("Premium"), 
        MembershipLevelRegistered("Registered"), 
        MembershipLevelWebpremium("Web-Premium");
        
        private String _trackingName;
        
        private MembershipLevel(final String trackingName) {
            this._trackingName = "Logged Out";
            this._trackingName = trackingName;
        }
        
        public String getTrackingName() {
            return this._trackingName;
        }
    }
}
