/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.FacebookException;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.ShareVideoContent;

private static class ShareContentValidation.WebShareValidator
extends ShareContentValidation.Validator {
    private ShareContentValidation.WebShareValidator() {
        super(null);
    }

    @Override
    public void validate(ShareMediaContent shareMediaContent) {
        throw new FacebookException("Cannot share ShareMediaContent via web sharing dialogs");
    }

    @Override
    public void validate(SharePhoto sharePhoto) {
        ShareContentValidation.validatePhotoForWebDialog(sharePhoto, this);
    }

    @Override
    public void validate(ShareVideoContent shareVideoContent) {
        throw new FacebookException("Cannot share ShareVideoContent via web sharing dialogs");
    }
}
