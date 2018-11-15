/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.account.model;

public class ProductInformation {
    public static final String TYPE_VIDEO_SERIES = "series";
    private String _contentId;
    private String _contentType;
    private int _months;
    private String _sku;
    private ProductType _type;
    private boolean _webPremium;

    public ProductInformation(String object, int n, boolean bl, boolean bl2) {
        this._sku = object;
        this._months = n;
        this._webPremium = bl2;
        object = bl ? ProductType.PREMIUM_SUBSCRIPTION_AUTORENEWING : ProductType.PREMIUM_SUBSCRIPTION;
        this._type = object;
    }

    public ProductInformation(String string, String string2, String string3) {
        this._sku = string;
        this._contentId = string2;
        this._contentType = string3;
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

    public static enum ProductType {
        PRICE_TIER,
        PREMIUM_SUBSCRIPTION,
        PREMIUM_SUBSCRIPTION_AUTORENEWING;
        

        private ProductType() {
        }
    }

}
