/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.account.model.ProductInformation;

static class PurchaseService {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$account$model$ProductInformation$ProductType;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$account$model$ProductInformation$ProductType = new int[ProductInformation.ProductType.values().length];
        try {
            PurchaseService.$SwitchMap$de$cisha$android$board$account$model$ProductInformation$ProductType[ProductInformation.ProductType.PREMIUM_SUBSCRIPTION.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            PurchaseService.$SwitchMap$de$cisha$android$board$account$model$ProductInformation$ProductType[ProductInformation.ProductType.PRICE_TIER.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            PurchaseService.$SwitchMap$de$cisha$android$board$account$model$ProductInformation$ProductType[ProductInformation.ProductType.PREMIUM_SUBSCRIPTION_AUTORENEWING.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
