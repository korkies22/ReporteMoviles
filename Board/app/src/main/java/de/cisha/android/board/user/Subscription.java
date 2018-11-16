// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.user;

import de.cisha.android.board.JSONValue;
import de.cisha.android.board.service.SubscriptionType;
import java.util.Date;

public class Subscription
{
    private Date _expirationDate;
    private boolean _isRecurring;
    private SubscriptionOrigin _origin;
    private SubscriptionProvider _provider;
    private SubscriptionType _subscriptionType;
    
    public Subscription(final SubscriptionType subscriptionType, final Date expirationDate, final boolean isRecurring, final SubscriptionOrigin origin, final SubscriptionProvider provider) {
        this._subscriptionType = subscriptionType;
        this._expirationDate = expirationDate;
        this._isRecurring = isRecurring;
        this._origin = origin;
        this._provider = provider;
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
    
    public enum SubscriptionOrigin implements JSONValue<SubscriptionOrigin>
    {
        MOBILE, 
        UNKNOWN, 
        WEB;
        
        @Override
        public SubscriptionOrigin enumValue() {
            return this;
        }
        
        @Override
        public String jsonValue() {
            switch (Subscription.1..SwitchMap.de.cisha.android.board.user.Subscription.SubscriptionOrigin[this.ordinal()]) {
                default: {
                    return "";
                }
                case 3: {
                    return "web";
                }
                case 2: {
                    return "mobile";
                }
                case 1: {
                    return "";
                }
            }
        }
    }
    
    public enum SubscriptionProvider implements JSONValue<SubscriptionProvider>
    {
        APPLE, 
        GOOGLE, 
        UNKNOWN;
        
        @Override
        public SubscriptionProvider enumValue() {
            return this;
        }
        
        @Override
        public String jsonValue() {
            switch (Subscription.1..SwitchMap.de.cisha.android.board.user.Subscription.SubscriptionProvider[this.ordinal()]) {
                default: {
                    return "";
                }
                case 3: {
                    return "google";
                }
                case 2: {
                    return "apple";
                }
                case 1: {
                    return "";
                }
            }
        }
    }
}
