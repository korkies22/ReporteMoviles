/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.widget;

import com.facebook.share.widget.ShareDialog;

static class ShareDialog {
    static final /* synthetic */ int[] $SwitchMap$com$facebook$share$widget$ShareDialog$Mode;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$com$facebook$share$widget$ShareDialog$Mode = new int[ShareDialog.Mode.values().length];
        try {
            ShareDialog.$SwitchMap$com$facebook$share$widget$ShareDialog$Mode[ShareDialog.Mode.AUTOMATIC.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            ShareDialog.$SwitchMap$com$facebook$share$widget$ShareDialog$Mode[ShareDialog.Mode.WEB.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            ShareDialog.$SwitchMap$com$facebook$share$widget$ShareDialog$Mode[ShareDialog.Mode.NATIVE.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
