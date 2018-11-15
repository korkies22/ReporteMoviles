/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.ShareVideoContent;
import java.util.List;

private static class ShareContentValidation.ApiValidator
extends ShareContentValidation.Validator {
    private ShareContentValidation.ApiValidator() {
        super(null);
    }

    @Override
    public void validate(ShareLinkContent shareLinkContent) {
        if (!Utility.isNullOrEmpty(shareLinkContent.getQuote())) {
            throw new FacebookException("Cannot share link content with quote using the share api");
        }
    }

    @Override
    public void validate(ShareMediaContent shareMediaContent) {
        throw new FacebookException("Cannot share ShareMediaContent using the share api");
    }

    @Override
    public void validate(SharePhoto sharePhoto) {
        ShareContentValidation.validatePhotoForApi(sharePhoto, this);
    }

    @Override
    public void validate(ShareVideoContent shareVideoContent) {
        if (!Utility.isNullOrEmpty(shareVideoContent.getPlaceId())) {
            throw new FacebookException("Cannot share video content with place IDs using the share api");
        }
        if (!Utility.isNullOrEmpty(shareVideoContent.getPeopleIds())) {
            throw new FacebookException("Cannot share video content with people IDs using the share api");
        }
        if (!Utility.isNullOrEmpty(shareVideoContent.getRef())) {
            throw new FacebookException("Cannot share video content with referrer URL using the share api");
        }
    }
}
