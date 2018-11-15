/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.ITrackingService;

public static enum ITrackingService.TrackingCategory {
    PLAYZONE("Play Now"),
    ANALYSIS("Analysis"),
    TACTICS("Tactics"),
    USER("UX"),
    SHOP("Shop"),
    BROADCAST("Broadcast"),
    SOCIAL("Social"),
    PROFILE("Profile");
    
    private String _name;

    private ITrackingService.TrackingCategory(String string2) {
        this._name = string2;
    }

    public String getName() {
        return this._name;
    }
}
