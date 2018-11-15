/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.user;

import de.cisha.android.board.JSONValue;
import de.cisha.android.board.service.SubscriptionType;
import java.util.Date;

public class Subscription {
    private Date _expirationDate;
    private boolean _isRecurring;
    private SubscriptionOrigin _origin;
    private SubscriptionProvider _provider;
    private SubscriptionType _subscriptionType;

    public Subscription(SubscriptionType subscriptionType, Date date, boolean bl, SubscriptionOrigin subscriptionOrigin, SubscriptionProvider subscriptionProvider) {
        this._subscriptionType = subscriptionType;
        this._expirationDate = date;
        this._isRecurring = bl;
        this._origin = subscriptionOrigin;
        this._provider = subscriptionProvider;
    }

    public Date getExpirationDate() {
        return this._expirationDate;
    }

    public SubscriptionOrigin getOrigin() {
        return this._origin;
    }

    public SubscriptionProvider getProvider() {
        return this._provider;
    }

    public SubscriptionType getSubscriptionType() {
        return this._subscriptionType;
    }

    public boolean isRecurring() {
        return this._isRecurring;
    }

    public static enum SubscriptionOrigin implements JSONValue<SubscriptionOrigin>
    {
        MOBILE,
        WEB,
        UNKNOWN;
        

        private SubscriptionOrigin() {
        }

        @Override
        public SubscriptionOrigin enumValue() {
            return this;
        }

        @Override
        public String jsonValue() {
            switch (.$SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionOrigin[this.ordinal()]) {
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

    public static enum SubscriptionProvider implements JSONValue<SubscriptionProvider>
    {
        GOOGLE,
        APPLE,
        UNKNOWN;
        

        private SubscriptionProvider() {
        }

        @Override
        public SubscriptionProvider enumValue() {
            return this;
        }

        @Override
        public String jsonValue() {
            switch (.$SwitchMap$de$cisha$android$board$user$Subscription$SubscriptionProvider[this.ordinal()]) {
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

}
