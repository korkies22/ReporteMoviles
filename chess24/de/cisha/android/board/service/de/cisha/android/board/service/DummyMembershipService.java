/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.IMembershipService;

public class DummyMembershipService
implements IMembershipService {
    private boolean _premium;

    public DummyMembershipService() {
        this(false);
    }

    public DummyMembershipService(boolean bl) {
        this._premium = bl;
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
    public IMembershipService.MembershipLevel getMembershipLevel() {
        if (this._premium) {
            return IMembershipService.MembershipLevel.MembershipLevelPremiumLight;
        }
        return IMembershipService.MembershipLevel.MembershipLevelRegistered;
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
