// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.user.Subscription;
import java.util.Date;
import de.cisha.android.board.user.User;
import android.content.Context;

public class UserMembershipService implements IMembershipService
{
    private static IMembershipService _instance;
    
    public static IMembershipService getInstance(final Context context) {
        synchronized (UserMembershipService.class) {
            if (UserMembershipService._instance == null) {
                UserMembershipService._instance = new UserMembershipService();
            }
            return UserMembershipService._instance;
        }
    }
    
    private User getUser() {
        return ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
    }
    
    @Override
    public int getMaximumEngineEvaluationDepth() {
        switch (UserMembershipService.1..SwitchMap.de.cisha.android.board.service.IMembershipService.MembershipLevel[this.getMembershipLevel().ordinal()]) {
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
            case 1: {
                return 8;
            }
        }
    }
    
    @Override
    public int getMaximumEngineVariations() {
        switch (UserMembershipService.1..SwitchMap.de.cisha.android.board.service.IMembershipService.MembershipLevel[this.getMembershipLevel().ordinal()]) {
            default: {
                return 1;
            }
            case 3:
            case 4: {
                return Integer.MAX_VALUE;
            }
            case 1:
            case 2: {
                return 1;
            }
        }
    }
    
    @Override
    public MembershipLevel getMembershipLevel() {
        final ILoginService loginService = ServiceProvider.getInstance().getLoginService();
        final User user = this.getUser();
        if (loginService.isLoggedIn() && user != null) {
            final Subscription subscription = user.getSubscription(SubscriptionType.PREMIUM_MOBILE);
            final Subscription subscription2 = user.getSubscription(SubscriptionType.PREMIUM);
            Date expirationDate = null;
            Date expirationDate2;
            if (subscription != null) {
                expirationDate2 = subscription.getExpirationDate();
            }
            else {
                expirationDate2 = null;
            }
            if (subscription2 != null) {
                expirationDate = subscription2.getExpirationDate();
            }
            final Date date = new Date();
            if (user != null && !user.isGuest()) {
                if (expirationDate != null && expirationDate.after(date)) {
                    return MembershipLevel.MembershipLevelWebpremium;
                }
                if (expirationDate2 != null && expirationDate2.after(date)) {
                    return MembershipLevel.MembershipLevelPremiumLight;
                }
                return MembershipLevel.MembershipLevelRegistered;
            }
        }
        return MembershipLevel.MembershipLevelGuest;
    }
    
    @Override
    public int getNumberOfOpeningTreeMovesAvailable() {
        switch (UserMembershipService.1..SwitchMap.de.cisha.android.board.service.IMembershipService.MembershipLevel[this.getMembershipLevel().ordinal()]) {
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
            case 1: {
                return 3;
            }
        }
    }
    
    @Override
    public int getNumberOfSavedAnalysisAccessible() {
        switch (UserMembershipService.1..SwitchMap.de.cisha.android.board.service.IMembershipService.MembershipLevel[this.getMembershipLevel().ordinal()]) {
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
            case 1: {
                return 1;
            }
        }
    }
    
    @Override
    public int getNumberOfSavedGamesAccessible() {
        return Integer.MAX_VALUE;
    }
}
