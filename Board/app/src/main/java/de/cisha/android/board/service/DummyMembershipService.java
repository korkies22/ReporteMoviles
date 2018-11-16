// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

public class DummyMembershipService implements IMembershipService
{
    private boolean _premium;
    
    public DummyMembershipService() {
        this(false);
    }
    
    public DummyMembershipService(final boolean premium) {
        this._premium = premium;
    }
    
    @Override
    public int getMaximumEngineEvaluationDepth() {
        if (this._premium) {
            return Integer.MAX_VALUE;
        }
        return 12;
    }
    
    @Override
    public int getMaximumEngineVariations() {
        if (this._premium) {
            return Integer.MAX_VALUE;
        }
        return 1;
    }
    
    @Override
    public MembershipLevel getMembershipLevel() {
        if (this._premium) {
            return MembershipLevel.MembershipLevelPremiumLight;
        }
        return MembershipLevel.MembershipLevelRegistered;
    }
    
    @Override
    public int getNumberOfOpeningTreeMovesAvailable() {
        if (this._premium) {
            return Integer.MAX_VALUE;
        }
        return 5;
    }
    
    @Override
    public int getNumberOfSavedAnalysisAccessible() {
        if (this._premium) {
            return Integer.MAX_VALUE;
        }
        return 3;
    }
    
    @Override
    public int getNumberOfSavedGamesAccessible() {
        return Integer.MAX_VALUE;
    }
}
