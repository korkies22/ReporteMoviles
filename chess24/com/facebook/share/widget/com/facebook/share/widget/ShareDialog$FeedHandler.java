/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.AppCall;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

private class ShareDialog.FeedHandler
extends FacebookDialogBase<ShareContent, Sharer.Result> {
    private ShareDialog.FeedHandler() {
        super(ShareDialog.this);
    }

    public boolean canShow(ShareContent shareContent, boolean bl) {
        if (!(shareContent instanceof ShareLinkContent) && !(shareContent instanceof ShareFeedContent)) {
            return false;
        }
        return true;
    }

    public AppCall createAppCall(ShareContent shareContent) {
        ShareDialog.this.logDialogShare((Context)ShareDialog.this.getActivityContext(), shareContent, ShareDialog.Mode.FEED);
        AppCall appCall = ShareDialog.this.createBaseAppCall();
        if (shareContent instanceof ShareLinkContent) {
            shareContent = (ShareLinkContent)shareContent;
            ShareContentValidation.validateForWebShare(shareContent);
            shareContent = WebDialogParameters.createForFeed((ShareLinkContent)shareContent);
        } else {
            shareContent = WebDialogParameters.createForFeed((ShareFeedContent)shareContent);
        }
        DialogPresenter.setupAppCallForWebDialog(appCall, ShareDialog.FEED_DIALOG, (Bundle)shareContent);
        return appCall;
    }

    public Object getMode() {
        return ShareDialog.Mode.FEED;
    }
}
