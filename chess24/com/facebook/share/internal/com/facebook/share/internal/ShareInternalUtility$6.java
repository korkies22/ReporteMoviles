/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.Utility;
import com.facebook.share.model.ShareMedia;
import java.util.List;
import java.util.UUID;

static final class ShareInternalUtility
implements Utility.Mapper<ShareMedia, Bundle> {
    final /* synthetic */ UUID val$appCallId;
    final /* synthetic */ List val$attachments;

    ShareInternalUtility(UUID uUID, List list) {
        this.val$appCallId = uUID;
        this.val$attachments = list;
    }

    @Override
    public Bundle apply(ShareMedia shareMedia) {
        NativeAppCallAttachmentStore.Attachment attachment = com.facebook.share.internal.ShareInternalUtility.getAttachment(this.val$appCallId, shareMedia);
        this.val$attachments.add(attachment);
        Bundle bundle = new Bundle();
        bundle.putString("type", shareMedia.getMediaType().name());
        bundle.putString("uri", attachment.getAttachmentUrl());
        return bundle;
    }
}
