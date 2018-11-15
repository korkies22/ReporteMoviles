/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.IMembershipService;

public static enum IMembershipService.MembershipLevel {
    MembershipLevelRegistered("Registered"),
    MembershipLevelPremiumLight("Premium"),
    MembershipLevelGuest("Guest"),
    MembershipLevelWebpremium("Web-Premium");
    
    private String _trackingName = "Logged Out";

    private IMembershipService.MembershipLevel(String string2) {
        this._trackingName = string2;
    }

    public String getTrackingName() {
        return this._trackingName;
    }
}
