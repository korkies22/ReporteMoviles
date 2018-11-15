/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.model;

import com.facebook.share.ShareBuilder;
import com.facebook.share.model.ShareModel;

public interface ShareModelBuilder<P extends ShareModel, E extends ShareModelBuilder>
extends ShareBuilder<P, E> {
    public E readFrom(P var1);
}
