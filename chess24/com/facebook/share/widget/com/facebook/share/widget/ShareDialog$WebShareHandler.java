/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.internal.AppCall;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

private class ShareDialog.WebShareHandler
extends FacebookDialogBase<ShareContent, Sharer.Result> {
    private ShareDialog.WebShareHandler() {
        super(ShareDialog.this);
    }

    private SharePhotoContent createAndMapAttachments(SharePhotoContent sharePhotoContent, UUID uUID) {
        SharePhotoContent.Builder builder = new SharePhotoContent.Builder().readFrom(sharePhotoContent);
        ArrayList<SharePhoto> arrayList = new ArrayList<SharePhoto>();
        ArrayList<NativeAppCallAttachmentStore.Attachment> arrayList2 = new ArrayList<NativeAppCallAttachmentStore.Attachment>();
        for (int i = 0; i < sharePhotoContent.getPhotos().size(); ++i) {
            SharePhoto sharePhoto = sharePhotoContent.getPhotos().get(i);
            Object object = sharePhoto.getBitmap();
            SharePhoto sharePhoto2 = sharePhoto;
            if (object != null) {
                object = NativeAppCallAttachmentStore.createAttachment(uUID, (Bitmap)object);
                sharePhoto2 = new SharePhoto.Builder().readFrom(sharePhoto).setImageUrl(Uri.parse((String)object.getAttachmentUrl())).setBitmap(null).build();
                arrayList2.add((NativeAppCallAttachmentStore.Attachment)object);
            }
            arrayList.add(sharePhoto2);
        }
        builder.setPhotos(arrayList);
        NativeAppCallAttachmentStore.addAttachments(arrayList2);
        return builder.build();
    }

    private String getActionName(ShareContent shareContent) {
        if (!(shareContent instanceof ShareLinkContent) && !(shareContent instanceof SharePhotoContent)) {
            if (shareContent instanceof ShareOpenGraphContent) {
                return ShareDialog.WEB_OG_SHARE_DIALOG;
            }
            return null;
        }
        return ShareDialog.WEB_SHARE_DIALOG;
    }

    public boolean canShow(ShareContent shareContent, boolean bl) {
        if (shareContent != null && ShareDialog.canShowWebCheck(shareContent)) {
            return true;
        }
        return false;
    }

    public AppCall createAppCall(ShareContent shareContent) {
        ShareDialog.this.logDialogShare((Context)ShareDialog.this.getActivityContext(), shareContent, ShareDialog.Mode.WEB);
        AppCall appCall = ShareDialog.this.createBaseAppCall();
        ShareContentValidation.validateForWebShare(shareContent);
        Bundle bundle = shareContent instanceof ShareLinkContent ? WebDialogParameters.create((ShareLinkContent)shareContent) : (shareContent instanceof SharePhotoContent ? WebDialogParameters.create(this.createAndMapAttachments((SharePhotoContent)shareContent, appCall.getCallId())) : WebDialogParameters.create((ShareOpenGraphContent)shareContent));
        DialogPresenter.setupAppCallForWebDialog(appCall, this.getActionName(shareContent), bundle);
        return appCall;
    }

    public Object getMode() {
        return ShareDialog.Mode.WEB;
    }
}
