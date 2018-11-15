/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.IMembershipService;

static class UserMembershipService {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$service$IMembershipService$MembershipLevel;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$service$IMembershipService$MembershipLevel = new int[IMembershipService.MembershipLevel.values().length];
        try {
            UserMembershipService.$SwitchMap$de$cisha$android$board$service$IMembershipService$MembershipLevel[IMembershipService.MembershipLevel.MembershipLevelGuest.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            UserMembershipService.$SwitchMap$de$cisha$android$board$service$IMembershipService$MembershipLevel[IMembershipService.MembershipLevel.MembershipLevelRegistered.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            UserMembershipService.$SwitchMap$de$cisha$android$board$service$IMembershipService$MembershipLevel[IMembershipService.MembershipLevel.MembershipLevelPremiumLight.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            UserMembershipService.$SwitchMap$de$cisha$android$board$service$IMembershipService$MembershipLevel[IMembershipService.MembershipLevel.MembershipLevelWebpremium.ordinal()] = 4;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
