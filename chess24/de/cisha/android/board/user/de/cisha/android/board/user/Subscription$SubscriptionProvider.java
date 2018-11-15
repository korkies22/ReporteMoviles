/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.user;

import de.cisha.android.board.JSONValue;
import de.cisha.android.board.user.Subscription;

public static enum Subscription.SubscriptionProvider implements JSONValue<Subscription.SubscriptionProvider>
{
    GOOGLE,
    APPLE,
    UNKNOWN;
    

    private Subscription.SubscriptionProvider() {
    }

    @Override
    public Subscription.SubscriptionProvider enumValue() {
        return this;
    }

    @Override
    public String jsonValue() {
        switch (Subscription.$SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionProvider[this.ordinal()]) {
            default: {
                return "";
            }
            case 3: {
                return "google";
            }
            case 2: {
                return "apple";
            }
            case 1: 
        }
        return "";
    }
}
