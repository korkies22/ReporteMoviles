/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.model;

import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static abstract class ShareMessengerActionButton.Builder<M extends ShareMessengerActionButton, B extends ShareMessengerActionButton.Builder>
implements ShareModelBuilder<M, B> {
    private String title;

    static /* synthetic */ String access$000(ShareMessengerActionButton.Builder builder) {
        return builder.title;
    }

    @Override
    public B readFrom(M m) {
        if (m == null) {
            return (B)this;
        }
        return this.setTitle(m.getTitle());
    }

    public B setTitle(@Nullable String string) {
        this.title = string;
        return (B)this;
    }
}
