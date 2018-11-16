// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.account.model;

public class ProductInformation
{
    public static final String TYPE_VIDEO_SERIES = "series";
    private String _contentId;
    private String _contentType;
    private int _months;
    private String _sku;
    private ProductType _type;
    private boolean _webPremium;
    
    public ProductInformation(final String sku, final int months, final boolean b, final boolean webPremium) {
        this._sku = sku;
        this._months = months;
        this._webPremium = webPremium;
        ProductType type;
        if (b) {
            type = ProductType.PREMIUM_SUBSCRIPTION_AUTORENEWING;
        }
        else {
            type = ProductType.PREMIUM_SUBSCRIPTION;
        }
        this._type = type;
    }
    
    public ProductInformation(final String sku, final String contentId, final String contentType) {
        this._sku = sku;
        this._contentId = contentId;
        this._contentType = contentType;
        this._type = ProductType.PRICE_TIER;
    }
    
    public String getContentId() {
        return this._contentId;
    }
    
    public String getContentTypeString() {
        return this._contentType;
    }
    
    public int getMonths() {
        return this._months;
    }
    
    public String getSku() {
        return this._sku;
    }
    
    public ProductType getType() {
        return this._type;
    }
    
    public boolean isWebPremium() {
        return this._webPremium;
    }
    
    public enum ProductType
    {
        PREMIUM_SUBSCRIPTION, 
        PREMIUM_SUBSCRIPTION_AUTORENEWING, 
        PRICE_TIER;
    }
}
