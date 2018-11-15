/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import com.facebook.FacebookException;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.share.internal.OpenGraphJSONUtility;
import com.facebook.share.model.SharePhoto;
import java.util.ArrayList;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

static final class ShareInternalUtility
implements OpenGraphJSONUtility.PhotoJSONProcessor {
    final /* synthetic */ ArrayList val$attachments;
    final /* synthetic */ UUID val$callId;

    ShareInternalUtility(UUID uUID, ArrayList arrayList) {
        this.val$callId = uUID;
        this.val$attachments = arrayList;
    }

    @Override
    public JSONObject toJSONObject(SharePhoto sharePhoto) {
        NativeAppCallAttachmentStore.Attachment attachment = com.facebook.share.internal.ShareInternalUtility.getAttachment(this.val$callId, sharePhoto);
        if (attachment == null) {
            return null;
        }
        this.val$attachments.add(attachment);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", (Object)attachment.getAttachmentUrl());
            if (sharePhoto.getUserGenerated()) {
                jSONObject.put("user_generated", true);
            }
            return jSONObject;
        }
        catch (JSONException jSONException) {
            throw new FacebookException("Unable to attach images", (Throwable)jSONException);
        }
    }
}
