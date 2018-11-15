/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.Utility;
import com.facebook.share.model.SharePhoto;
import java.util.UUID;

static final class ShareInternalUtility
implements Utility.Mapper<SharePhoto, NativeAppCallAttachmentStore.Attachment> {
    final /* synthetic */ UUID val$appCallId;

    ShareInternalUtility(UUID uUID) {
        this.val$appCallId = uUID;
    }

    @Override
    public NativeAppCallAttachmentStore.Attachment apply(SharePhoto sharePhoto) {
        return com.facebook.share.internal.ShareInternalUtility.getAttachment(this.val$appCallId, sharePhoto);
    }
}
