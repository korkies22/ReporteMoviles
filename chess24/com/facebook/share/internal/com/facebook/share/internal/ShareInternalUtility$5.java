/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.Utility;

static final class ShareInternalUtility
implements Utility.Mapper<NativeAppCallAttachmentStore.Attachment, String> {
    ShareInternalUtility() {
    }

    @Override
    public String apply(NativeAppCallAttachmentStore.Attachment attachment) {
        return attachment.getAttachmentUrl();
    }
}
