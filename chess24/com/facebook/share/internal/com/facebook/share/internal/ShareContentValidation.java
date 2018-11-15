/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 */
package com.facebook.share.internal;

import android.graphics.Bitmap;
import android.net.Uri;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerGenericTemplateElement;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareMessengerURLActionButton;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.ShareOpenGraphValueContainer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ShareContentValidation {
    private static Validator ApiValidator;
    private static Validator DefaultValidator;
    private static Validator WebShareValidator;

    private static Validator getApiValidator() {
        if (ApiValidator == null) {
            ApiValidator = new ApiValidator();
        }
        return ApiValidator;
    }

    private static Validator getDefaultValidator() {
        if (DefaultValidator == null) {
            DefaultValidator = new Validator();
        }
        return DefaultValidator;
    }

    private static Validator getWebShareValidator() {
        if (WebShareValidator == null) {
            WebShareValidator = new WebShareValidator();
        }
        return WebShareValidator;
    }

    private static void validate(ShareContent shareContent, Validator validator) throws FacebookException {
        if (shareContent == null) {
            throw new FacebookException("Must provide non-null content to share");
        }
        if (shareContent instanceof ShareLinkContent) {
            validator.validate((ShareLinkContent)shareContent);
            return;
        }
        if (shareContent instanceof SharePhotoContent) {
            validator.validate((SharePhotoContent)shareContent);
            return;
        }
        if (shareContent instanceof ShareVideoContent) {
            validator.validate((ShareVideoContent)shareContent);
            return;
        }
        if (shareContent instanceof ShareOpenGraphContent) {
            validator.validate((ShareOpenGraphContent)shareContent);
            return;
        }
        if (shareContent instanceof ShareMediaContent) {
            validator.validate((ShareMediaContent)shareContent);
            return;
        }
        if (shareContent instanceof ShareCameraEffectContent) {
            validator.validate((ShareCameraEffectContent)shareContent);
            return;
        }
        if (shareContent instanceof ShareMessengerOpenGraphMusicTemplateContent) {
            validator.validate((ShareMessengerOpenGraphMusicTemplateContent)shareContent);
            return;
        }
        if (shareContent instanceof ShareMessengerMediaTemplateContent) {
            validator.validate((ShareMessengerMediaTemplateContent)shareContent);
            return;
        }
        if (shareContent instanceof ShareMessengerGenericTemplateContent) {
            validator.validate((ShareMessengerGenericTemplateContent)shareContent);
        }
    }

    private static void validateCameraEffectContent(ShareCameraEffectContent shareCameraEffectContent, Validator validator) {
        if (Utility.isNullOrEmpty(shareCameraEffectContent.getEffectId())) {
            throw new FacebookException("Must specify a non-empty effectId");
        }
    }

    public static void validateForApiShare(ShareContent shareContent) {
        ShareContentValidation.validate(shareContent, ShareContentValidation.getApiValidator());
    }

    public static void validateForMessage(ShareContent shareContent) {
        ShareContentValidation.validate(shareContent, ShareContentValidation.getDefaultValidator());
    }

    public static void validateForNativeShare(ShareContent shareContent) {
        ShareContentValidation.validate(shareContent, ShareContentValidation.getDefaultValidator());
    }

    public static void validateForWebShare(ShareContent shareContent) {
        ShareContentValidation.validate(shareContent, ShareContentValidation.getWebShareValidator());
    }

    private static void validateLinkContent(ShareLinkContent shareLinkContent, Validator validator) {
        if ((shareLinkContent = shareLinkContent.getImageUrl()) != null && !Utility.isWebUri((Uri)shareLinkContent)) {
            throw new FacebookException("Image Url must be an http:// or https:// url");
        }
    }

    private static void validateMediaContent(ShareMediaContent iterator, Validator validator) {
        if ((iterator = iterator.getMedia()) != null && !iterator.isEmpty()) {
            if (iterator.size() > 6) {
                throw new FacebookException(String.format(Locale.ROOT, "Cannot add more than %d media.", 6));
            }
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                validator.validate((ShareMedia)iterator.next());
            }
            return;
        }
        throw new FacebookException("Must specify at least one medium in ShareMediaContent.");
    }

    public static void validateMedium(ShareMedia shareMedia, Validator validator) {
        if (shareMedia instanceof SharePhoto) {
            validator.validate((SharePhoto)shareMedia);
            return;
        }
        if (shareMedia instanceof ShareVideo) {
            validator.validate((ShareVideo)shareMedia);
            return;
        }
        throw new FacebookException(String.format(Locale.ROOT, "Invalid media type: %s", shareMedia.getClass().getSimpleName()));
    }

    private static void validateMessengerOpenGraphMusicTemplate(ShareMessengerOpenGraphMusicTemplateContent shareMessengerOpenGraphMusicTemplateContent) {
        if (Utility.isNullOrEmpty(shareMessengerOpenGraphMusicTemplateContent.getPageId())) {
            throw new FacebookException("Must specify Page Id for ShareMessengerOpenGraphMusicTemplateContent");
        }
        if (shareMessengerOpenGraphMusicTemplateContent.getUrl() == null) {
            throw new FacebookException("Must specify url for ShareMessengerOpenGraphMusicTemplateContent");
        }
        ShareContentValidation.validateShareMessengerActionButton(shareMessengerOpenGraphMusicTemplateContent.getButton());
    }

    private static void validateOpenGraphAction(ShareOpenGraphAction shareOpenGraphAction, Validator validator) {
        if (shareOpenGraphAction == null) {
            throw new FacebookException("Must specify a non-null ShareOpenGraphAction");
        }
        if (Utility.isNullOrEmpty(shareOpenGraphAction.getActionType())) {
            throw new FacebookException("ShareOpenGraphAction must have a non-empty actionType");
        }
        validator.validate(shareOpenGraphAction, false);
    }

    private static void validateOpenGraphContent(ShareOpenGraphContent object, Validator object2) {
        object2.validate(object.getAction());
        object2 = object.getPreviewPropertyName();
        if (Utility.isNullOrEmpty((String)object2)) {
            throw new FacebookException("Must specify a previewPropertyName.");
        }
        if (object.getAction().get((String)object2) == null) {
            object = new StringBuilder();
            object.append("Property \"");
            object.append((String)object2);
            object.append("\" was not found on the action. The name of the preview property must match the name of an action property.");
            throw new FacebookException(object.toString());
        }
    }

    private static void validateOpenGraphKey(String string, boolean bl) {
        if (!bl) {
            return;
        }
        String[] arrstring = string.split(":");
        if (arrstring.length < 2) {
            throw new FacebookException("Open Graph keys must be namespaced: %s", string);
        }
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!arrstring[i].isEmpty()) continue;
            throw new FacebookException("Invalid key found in Open Graph dictionary: %s", string);
        }
    }

    private static void validateOpenGraphObject(ShareOpenGraphObject shareOpenGraphObject, Validator validator) {
        if (shareOpenGraphObject == null) {
            throw new FacebookException("Cannot share a null ShareOpenGraphObject");
        }
        validator.validate(shareOpenGraphObject, true);
    }

    private static void validateOpenGraphValueContainer(ShareOpenGraphValueContainer shareOpenGraphValueContainer, Validator validator, boolean bl) {
        for (String string : shareOpenGraphValueContainer.keySet()) {
            ShareContentValidation.validateOpenGraphKey(string, bl);
            Object object = shareOpenGraphValueContainer.get(string);
            if (object instanceof List) {
                for (Object e : (List)object) {
                    if (e == null) {
                        throw new FacebookException("Cannot put null objects in Lists in ShareOpenGraphObjects and ShareOpenGraphActions");
                    }
                    ShareContentValidation.validateOpenGraphValueContainerObject(e, validator);
                }
                continue;
            }
            ShareContentValidation.validateOpenGraphValueContainerObject(object, validator);
        }
    }

    private static void validateOpenGraphValueContainerObject(Object object, Validator validator) {
        if (object instanceof ShareOpenGraphObject) {
            validator.validate((ShareOpenGraphObject)object);
            return;
        }
        if (object instanceof SharePhoto) {
            validator.validate((SharePhoto)object);
        }
    }

    private static void validatePhoto(SharePhoto sharePhoto) {
        if (sharePhoto == null) {
            throw new FacebookException("Cannot share a null SharePhoto");
        }
        Bitmap bitmap = sharePhoto.getBitmap();
        sharePhoto = sharePhoto.getImageUrl();
        if (bitmap == null && sharePhoto == null) {
            throw new FacebookException("SharePhoto does not have a Bitmap or ImageUrl specified");
        }
    }

    private static void validatePhotoContent(SharePhotoContent iterator, Validator validator) {
        if ((iterator = iterator.getPhotos()) != null && !iterator.isEmpty()) {
            if (iterator.size() > 6) {
                throw new FacebookException(String.format(Locale.ROOT, "Cannot add more than %d photos.", 6));
            }
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                validator.validate((SharePhoto)iterator.next());
            }
            return;
        }
        throw new FacebookException("Must specify at least one Photo in SharePhotoContent.");
    }

    private static void validatePhotoForApi(SharePhoto sharePhoto, Validator validator) {
        ShareContentValidation.validatePhoto(sharePhoto);
        Bitmap bitmap = sharePhoto.getBitmap();
        sharePhoto = sharePhoto.getImageUrl();
        if (bitmap == null && Utility.isWebUri((Uri)sharePhoto) && !validator.isOpenGraphContent()) {
            throw new FacebookException("Cannot set the ImageUrl of a SharePhoto to the Uri of an image on the web when sharing SharePhotoContent");
        }
    }

    private static void validatePhotoForNativeDialog(SharePhoto sharePhoto, Validator validator) {
        ShareContentValidation.validatePhotoForApi(sharePhoto, validator);
        if (sharePhoto.getBitmap() != null || !Utility.isWebUri(sharePhoto.getImageUrl())) {
            Validate.hasContentProvider(FacebookSdk.getApplicationContext());
        }
    }

    private static void validatePhotoForWebDialog(SharePhoto sharePhoto, Validator validator) {
        ShareContentValidation.validatePhoto(sharePhoto);
    }

    private static void validateShareMessengerActionButton(ShareMessengerActionButton shareMessengerActionButton) {
        if (shareMessengerActionButton == null) {
            return;
        }
        if (Utility.isNullOrEmpty(shareMessengerActionButton.getTitle())) {
            throw new FacebookException("Must specify title for ShareMessengerActionButton");
        }
        if (shareMessengerActionButton instanceof ShareMessengerURLActionButton) {
            ShareContentValidation.validateShareMessengerURLActionButton((ShareMessengerURLActionButton)shareMessengerActionButton);
        }
    }

    private static void validateShareMessengerGenericTemplateContent(ShareMessengerGenericTemplateContent shareMessengerGenericTemplateContent) {
        if (Utility.isNullOrEmpty(shareMessengerGenericTemplateContent.getPageId())) {
            throw new FacebookException("Must specify Page Id for ShareMessengerGenericTemplateContent");
        }
        if (shareMessengerGenericTemplateContent.getGenericTemplateElement() == null) {
            throw new FacebookException("Must specify element for ShareMessengerGenericTemplateContent");
        }
        if (Utility.isNullOrEmpty(shareMessengerGenericTemplateContent.getGenericTemplateElement().getTitle())) {
            throw new FacebookException("Must specify title for ShareMessengerGenericTemplateElement");
        }
        ShareContentValidation.validateShareMessengerActionButton(shareMessengerGenericTemplateContent.getGenericTemplateElement().getButton());
    }

    private static void validateShareMessengerMediaTemplateContent(ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent) {
        if (Utility.isNullOrEmpty(shareMessengerMediaTemplateContent.getPageId())) {
            throw new FacebookException("Must specify Page Id for ShareMessengerMediaTemplateContent");
        }
        if (shareMessengerMediaTemplateContent.getMediaUrl() == null && Utility.isNullOrEmpty(shareMessengerMediaTemplateContent.getAttachmentId())) {
            throw new FacebookException("Must specify either attachmentId or mediaURL for ShareMessengerMediaTemplateContent");
        }
        ShareContentValidation.validateShareMessengerActionButton(shareMessengerMediaTemplateContent.getButton());
    }

    private static void validateShareMessengerURLActionButton(ShareMessengerURLActionButton shareMessengerURLActionButton) {
        if (shareMessengerURLActionButton.getUrl() == null) {
            throw new FacebookException("Must specify url for ShareMessengerURLActionButton");
        }
    }

    private static void validateVideo(ShareVideo shareVideo, Validator validator) {
        if (shareVideo == null) {
            throw new FacebookException("Cannot share a null ShareVideo");
        }
        if ((shareVideo = shareVideo.getLocalUrl()) == null) {
            throw new FacebookException("ShareVideo does not have a LocalUrl specified");
        }
        if (!Utility.isContentUri((Uri)shareVideo) && !Utility.isFileUri((Uri)shareVideo)) {
            throw new FacebookException("ShareVideo must reference a video that is on the device");
        }
    }

    private static void validateVideoContent(ShareVideoContent shareModel, Validator validator) {
        validator.validate(shareModel.getVideo());
        shareModel = shareModel.getPreviewPhoto();
        if (shareModel != null) {
            validator.validate((SharePhoto)shareModel);
        }
    }

    private static class ApiValidator
    extends Validator {
        private ApiValidator() {
            super();
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

    private static class Validator {
        private boolean isOpenGraphContent = false;

        private Validator() {
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

    private static class WebShareValidator
    extends Validator {
        private WebShareValidator() {
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

}
