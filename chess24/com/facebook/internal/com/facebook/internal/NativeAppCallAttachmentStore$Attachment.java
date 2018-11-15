/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 */
package com.facebook.internal;

import android.graphics.Bitmap;
import android.net.Uri;
import com.facebook.FacebookContentProvider;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.Utility;
import java.util.UUID;

public static final class NativeAppCallAttachmentStore.Attachment {
    private final String attachmentName;
    private final String attachmentUrl;
    private Bitmap bitmap;
    private final UUID callId;
    private boolean isContentUri;
    private Uri originalUri;
    private boolean shouldCreateFile;

    private NativeAppCallAttachmentStore.Attachment(UUID object, Bitmap object2, Uri uri) {
        block12 : {
            block11 : {
                block10 : {
                    this.callId = object;
                    this.bitmap = object2;
                    this.originalUri = uri;
                    boolean bl = true;
                    if (uri == null) break block10;
                    object2 = uri.getScheme();
                    if ("content".equalsIgnoreCase((String)object2)) {
                        this.isContentUri = true;
                        if (uri.getAuthority() == null || uri.getAuthority().startsWith("media")) {
                            bl = false;
                        }
                        this.shouldCreateFile = bl;
                    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                        this.shouldCreateFile = true;
                    } else if (!Utility.isWebUri(uri)) {
                        object = new StringBuilder();
                        object.append("Unsupported scheme for media Uri : ");
                        object.append((String)object2);
                        throw new FacebookException(object.toString());
                    }
                    break block11;
                }
                if (object2 == null) break block12;
                this.shouldCreateFile = true;
            }
            object2 = !this.shouldCreateFile ? null : UUID.randomUUID().toString();
            this.attachmentName = object2;
            object = !this.shouldCreateFile ? this.originalUri.toString() : FacebookContentProvider.getAttachmentUrl(FacebookSdk.getApplicationId(), (UUID)object, this.attachmentName);
            this.attachmentUrl = object;
            return;
        }
        throw new FacebookException("Cannot share media without a bitmap or Uri set");
    }

    static /* synthetic */ boolean access$100(NativeAppCallAttachmentStore.Attachment attachment) {
        return attachment.shouldCreateFile;
    }

    static /* synthetic */ UUID access$200(NativeAppCallAttachmentStore.Attachment attachment) {
        return attachment.callId;
    }

    static /* synthetic */ String access$300(NativeAppCallAttachmentStore.Attachment attachment) {
        return attachment.attachmentName;
    }

    static /* synthetic */ Bitmap access$400(NativeAppCallAttachmentStore.Attachment attachment) {
        return attachment.bitmap;
    }

    static /* synthetic */ Uri access$500(NativeAppCallAttachmentStore.Attachment attachment) {
        return attachment.originalUri;
    }

    static /* synthetic */ boolean access$600(NativeAppCallAttachmentStore.Attachment attachment) {
        return attachment.isContentUri;
    }

    public String getAttachmentUrl() {
        return this.attachmentUrl;
    }
}
