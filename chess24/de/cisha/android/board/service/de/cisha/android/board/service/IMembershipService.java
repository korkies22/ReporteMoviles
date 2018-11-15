/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

public interface IMembershipService {
    public static final int NO_LIMITATIONS = Integer.MAX_VALUE;

    public int getMaximumEngineEvaluationDepth();

    public int getMaximumEngineVariations();

    public MembershipLevel getMembershipLevel();

    public int getNumberOfOpeningTreeMovesAvailable();

    public int getNumberOfSavedAnalysisAccessible();

    public int getNumberOfSavedGamesAccessible();

    public static enum MembershipLevel {
        MembershipLevelRegistered("Registered"),
        MembershipLevelPremiumLight("Premium"),
        MembershipLevelGuest("Guest"),
        MembershipLevelWebpremium("Web-Premium");
        
        private String _trackingName = "Logged Out";

        private MembershipLevel(String string2) {
            this._trackingName = string2;
        }

        public String getTrackingName() {
            return this._trackingName;
        }
    }

}
