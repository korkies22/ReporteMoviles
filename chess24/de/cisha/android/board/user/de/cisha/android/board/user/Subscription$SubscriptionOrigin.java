/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.user;

import de.cisha.android.board.JSONValue;
import de.cisha.android.board.user.Subscription;

public static enum Subscription.SubscriptionOrigin implements JSONValue<Subscription.SubscriptionOrigin>
{
    MOBILE,
    WEB,
    UNKNOWN;
    

    private Subscription.SubscriptionOrigin() {
    }

    @Override
    public Subscription.SubscriptionOrigin enumValue() {
        return this;
    }

    @Override
    public String jsonValue() {
        switch (Subscription.$SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionOrigin[this.ordinal()]) {
            default: {
                return "";
            }
            case 3: {
                return "web";
            }
            case 2: {
                return "mobile";
            }
            case 1: 
        }
        return "";
    }
}
