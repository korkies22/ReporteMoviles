/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerURLActionButton;

static class MessengerShareContentUtility {
    static final /* synthetic */ int[] $SwitchMap$com$facebook$share$model$ShareMessengerGenericTemplateContent$ImageAspectRatio;
    static final /* synthetic */ int[] $SwitchMap$com$facebook$share$model$ShareMessengerMediaTemplateContent$MediaType;
    static final /* synthetic */ int[] $SwitchMap$com$facebook$share$model$ShareMessengerURLActionButton$WebviewHeightRatio;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$com$facebook$share$model$ShareMessengerMediaTemplateContent$MediaType = new int[ShareMessengerMediaTemplateContent.MediaType.values().length];
        try {
            MessengerShareContentUtility.$SwitchMap$com$facebook$share$model$ShareMessengerMediaTemplateContent$MediaType[ShareMessengerMediaTemplateContent.MediaType.VIDEO.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SwitchMap$com$facebook$share$model$ShareMessengerGenericTemplateContent$ImageAspectRatio = new int[ShareMessengerGenericTemplateContent.ImageAspectRatio.values().length];
        try {
            MessengerShareContentUtility.$SwitchMap$com$facebook$share$model$ShareMessengerGenericTemplateContent$ImageAspectRatio[ShareMessengerGenericTemplateContent.ImageAspectRatio.SQUARE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SwitchMap$com$facebook$share$model$ShareMessengerURLActionButton$WebviewHeightRatio = new int[ShareMessengerURLActionButton.WebviewHeightRatio.values().length];
        try {
            MessengerShareContentUtility.$SwitchMap$com$facebook$share$model$ShareMessengerURLActionButton$WebviewHeightRatio[ShareMessengerURLActionButton.WebviewHeightRatio.WebviewHeightRatioCompact.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            MessengerShareContentUtility.$SwitchMap$com$facebook$share$model$ShareMessengerURLActionButton$WebviewHeightRatio[ShareMessengerURLActionButton.WebviewHeightRatio.WebviewHeightRatioTall.ordinal()] = 2;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
