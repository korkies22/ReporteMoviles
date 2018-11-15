/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.service;

import android.content.Context;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.user.Subscription;
import de.cisha.android.board.user.User;
import java.util.Date;

public class UserMembershipService
implements IMembershipService {
    private static IMembershipService _instance;

    private UserMembershipService() {
    }

    public static IMembershipService getInstance(Context object) {
        synchronized (UserMembershipService.class) {
            if (_instance == null) {
                _instance = new UserMembershipService();
            }
            object = _instance;
            return object;
        }
    }

    private User getUser() {
        return ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
    }

    @Override
    public int getMaximumEngineEvaluationDepth() {
        switch (.$SwitchMap$de$cisha$android$board$service$IMembershipService$MembershipLevel[this.getMembershipLevel().ordinal()]) {
            default: {
                return 8;
            }
            case 3: 
            case 4: {
                return Integer.MAX_VALUE;
            }
            case 2: {
                return 12;
            }
            case 1: 
        }
        return 8;
    }

    @Override
    public int getMaximumEngineVariations() {
        switch (.$SwitchMap$de$cisha$android$board$service$IMembershipService$MembershipLevel[this.getMembershipLevel().ordinal()]) {
            default: {
                return 1;
            }
            case 3: 
            case 4: {
                return Integer.MAX_VALUE;
            }
            case 1: 
            case 2: 
        }
        return 1;
    }

    @Override
    public IMembershipService.MembershipLevel getMembershipLevel() {
        Object object = ServiceProvider.getInstance().getLoginService();
        User user = this.getUser();
        if (object.isLoggedIn() && user != null) {
            object = user.getSubscription(SubscriptionType.PREMIUM_MOBILE);
            Object object2 = user.getSubscription(SubscriptionType.PREMIUM);
            Date date = null;
            object = object != null ? object.getExpirationDate() : null;
            if (object2 != null) {
                date = object2.getExpirationDate();
            }
            object2 = new Date();
            if (user != null && !user.isGuest()) {
                if (date != null && date.after((Date)object2)) {
                    return IMembershipService.MembershipLevel.MembershipLevelWebpremium;
                }
                if (object != null && object.after((Date)object2)) {
                    return IMembershipService.MembershipLevel.MembershipLevelPremiumLight;
                }
                return IMembershipService.MembershipLevel.MembershipLevelRegistered;
            }
        }
        return IMembershipService.MembershipLevel.MembershipLevelGuest;
    }

    @Override
    public int getNumberOfOpeningTreeMovesAvailable() {
        switch (.$SwitchMap$de$cisha$android$board$service$IMembershipService$MembershipLevel[this.getMembershipLevel().ordinal()]) {
            default: {
                return 3;
            }
            case 3: 
            case 4: {
                return Integer.MAX_VALUE;
            }
            case 2: {
                return 6;
            }
            case 1: 
        }
        return 3;
    }

    @Override
    public int getNumberOfSavedAnalysisAccessible() {
        switch (.$SwitchMap$de$cisha$android$board$service$IMembershipService$MembershipLevel[this.getMembershipLevel().ordinal()]) {
            default: {
                return 1;
            }
            case 3: 
            case 4: {
                return Integer.MAX_VALUE;
            }
            case 2: {
                return 3;
            }
            case 1: 
        }
        return 1;
    }

    @Override
    public int getNumberOfSavedGamesAccessible() {
        return Integer.MAX_VALUE;
    }

}
