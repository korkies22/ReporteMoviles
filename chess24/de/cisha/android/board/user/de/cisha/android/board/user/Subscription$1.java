/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.user;

import de.cisha.android.board.user.Subscription;

static class Subscription {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionOrigin;
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionProvider;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionProvider = new int[Subscription.SubscriptionProvider.values().length];
        try {
            Subscription.$SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionProvider[Subscription.SubscriptionProvider.UNKNOWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            Subscription.$SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionProvider[Subscription.SubscriptionProvider.APPLE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            Subscription.$SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionProvider[Subscription.SubscriptionProvider.GOOGLE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionOrigin = new int[Subscription.SubscriptionOrigin.values().length];
        try {
            Subscription.$SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionOrigin[Subscription.SubscriptionOrigin.UNKNOWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            Subscription.$SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionOrigin[Subscription.SubscriptionOrigin.MOBILE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            Subscription.$SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionOrigin[Subscription.SubscriptionOrigin.WEB.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
