/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.ShareOpenGraphValueContainer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;

private static class ShareContentValidation.Validator {
    private boolean isOpenGraphContent = false;

    private ShareContentValidation.Validator() {
    }

    public boolean isOpenGraphContent() {
        return this.isOpenGraphContent;
    }

    public void validate(ShareCameraEffectContent shareCameraEffectContent) {
        ShareContentValidation.validateCameraEffectContent(shareCameraEffectContent, this);
    }

    public void validate(ShareLinkContent shareLinkContent) {
        ShareContentValidation.validateLinkContent(shareLinkContent, this);
    }

    public void validate(ShareMedia shareMedia) {
        ShareContentValidation.validateMedium(shareMedia, this);
    }

    public void validate(ShareMediaContent shareMediaContent) {
        ShareContentValidation.validateMediaContent(shareMediaContent, this);
    }

    public void validate(ShareMessengerGenericTemplateContent shareMessengerGenericTemplateContent) {
        ShareContentValidation.validateShareMessengerGenericTemplateContent(shareMessengerGenericTemplateContent);
    }

    public void validate(ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent) {
        ShareContentValidation.validateShareMessengerMediaTemplateContent(shareMessengerMediaTemplateContent);
    }

    public void validate(ShareMessengerOpenGraphMusicTemplateContent shareMessengerOpenGraphMusicTemplateContent) {
        ShareContentValidation.validateMessengerOpenGraphMusicTemplate(shareMessengerOpenGraphMusicTemplateContent);
    }

    public void validate(ShareOpenGraphAction shareOpenGraphAction) {
        ShareContentValidation.validateOpenGraphAction(shareOpenGraphAction, this);
    }

    public void validate(ShareOpenGraphContent shareOpenGraphContent) {
        this.isOpenGraphContent = true;
        ShareContentValidation.validateOpenGraphContent(shareOpenGraphContent, this);
    }

    public void validate(ShareOpenGraphObject shareOpenGraphObject) {
        ShareContentValidation.validateOpenGraphObject(shareOpenGraphObject, this);
    }

    public void validate(ShareOpenGraphValueContainer shareOpenGraphValueContainer, boolean bl) {
        ShareContentValidation.validateOpenGraphValueContainer(shareOpenGraphValueContainer, this, bl);
    }

    public void validate(SharePhoto sharePhoto) {
        ShareContentValidation.validatePhotoForNativeDialog(sharePhoto, this);
    }

    public void validate(SharePhotoContent sharePhotoContent) {
        ShareContentValidation.validatePhotoContent(sharePhotoContent, this);
    }

    public void validate(ShareVideo shareVideo) {
        ShareContentValidation.validateVideo(shareVideo, this);
    }

    public void validate(ShareVideoContent shareVideoContent) {
        ShareContentValidation.validateVideoContent(shareVideoContent, this);
    }
}
