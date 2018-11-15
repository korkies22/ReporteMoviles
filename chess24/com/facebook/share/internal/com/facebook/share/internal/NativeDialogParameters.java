/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.util.Pair
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.internal.CameraEffectJSONUtility;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.CameraEffectArguments;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class NativeDialogParameters {
    private static Bundle create(ShareCameraEffectContent shareCameraEffectContent, Bundle object, boolean bl) {
        Bundle bundle;
        block4 : {
            bundle = NativeDialogParameters.createBaseParameters(shareCameraEffectContent, bl);
            Utility.putNonEmptyString(bundle, "effect_id", shareCameraEffectContent.getEffectId());
            if (object != null) {
                bundle.putBundle("effect_textures", (Bundle)object);
            }
            try {
                shareCameraEffectContent = CameraEffectJSONUtility.convertToJSON(shareCameraEffectContent.getArguments());
                if (shareCameraEffectContent == null) break block4;
            }
            catch (JSONException jSONException) {
                object = new StringBuilder();
                object.append("Unable to create a JSON Object from the provided CameraEffectArguments: ");
                object.append(jSONException.getMessage());
                throw new FacebookException(object.toString());
            }
            Utility.putNonEmptyString(bundle, "effect_arguments", shareCameraEffectContent.toString());
        }
        return bundle;
    }

    private static Bundle create(ShareLinkContent shareLinkContent, boolean bl) {
        Bundle bundle = NativeDialogParameters.createBaseParameters(shareLinkContent, bl);
        Utility.putNonEmptyString(bundle, "TITLE", shareLinkContent.getContentTitle());
        Utility.putNonEmptyString(bundle, "DESCRIPTION", shareLinkContent.getContentDescription());
        Utility.putUri(bundle, "IMAGE", shareLinkContent.getImageUrl());
        Utility.putNonEmptyString(bundle, "QUOTE", shareLinkContent.getQuote());
        Utility.putUri(bundle, "MESSENGER_LINK", shareLinkContent.getContentUrl());
        Utility.putUri(bundle, "TARGET_DISPLAY", shareLinkContent.getContentUrl());
        return bundle;
    }

    private static Bundle create(ShareMediaContent shareMediaContent, List<Bundle> list, boolean bl) {
        shareMediaContent = NativeDialogParameters.createBaseParameters(shareMediaContent, bl);
        shareMediaContent.putParcelableArrayList("MEDIA", new ArrayList<Bundle>(list));
        return shareMediaContent;
    }

    private static Bundle create(ShareMessengerGenericTemplateContent shareMessengerGenericTemplateContent, boolean bl) {
        Object object = NativeDialogParameters.createBaseParameters(shareMessengerGenericTemplateContent, bl);
        try {
            MessengerShareContentUtility.addGenericTemplateContent((Bundle)object, shareMessengerGenericTemplateContent);
            return object;
        }
        catch (JSONException jSONException) {
            object = new StringBuilder();
            object.append("Unable to create a JSON Object from the provided ShareMessengerGenericTemplateContent: ");
            object.append(jSONException.getMessage());
            throw new FacebookException(object.toString());
        }
    }

    private static Bundle create(ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent, boolean bl) {
        Object object = NativeDialogParameters.createBaseParameters(shareMessengerMediaTemplateContent, bl);
        try {
            MessengerShareContentUtility.addMediaTemplateContent((Bundle)object, shareMessengerMediaTemplateContent);
            return object;
        }
        catch (JSONException jSONException) {
            object = new StringBuilder();
            object.append("Unable to create a JSON Object from the provided ShareMessengerMediaTemplateContent: ");
            object.append(jSONException.getMessage());
            throw new FacebookException(object.toString());
        }
    }

    private static Bundle create(ShareMessengerOpenGraphMusicTemplateContent shareMessengerOpenGraphMusicTemplateContent, boolean bl) {
        Object object = NativeDialogParameters.createBaseParameters(shareMessengerOpenGraphMusicTemplateContent, bl);
        try {
            MessengerShareContentUtility.addOpenGraphMusicTemplateContent((Bundle)object, shareMessengerOpenGraphMusicTemplateContent);
            return object;
        }
        catch (JSONException jSONException) {
            object = new StringBuilder();
            object.append("Unable to create a JSON Object from the provided ShareMessengerOpenGraphMusicTemplateContent: ");
            object.append(jSONException.getMessage());
            throw new FacebookException(object.toString());
        }
    }

    private static Bundle create(ShareOpenGraphContent shareOpenGraphContent, JSONObject jSONObject, boolean bl) {
        Bundle bundle = NativeDialogParameters.createBaseParameters(shareOpenGraphContent, bl);
        Utility.putNonEmptyString(bundle, "PREVIEW_PROPERTY_NAME", (String)ShareInternalUtility.getFieldNameAndNamespaceFromFullName((String)shareOpenGraphContent.getPreviewPropertyName()).second);
        Utility.putNonEmptyString(bundle, "ACTION_TYPE", shareOpenGraphContent.getAction().getActionType());
        Utility.putNonEmptyString(bundle, "ACTION", jSONObject.toString());
        return bundle;
    }

    private static Bundle create(SharePhotoContent sharePhotoContent, List<String> list, boolean bl) {
        sharePhotoContent = NativeDialogParameters.createBaseParameters(sharePhotoContent, bl);
        sharePhotoContent.putStringArrayList("PHOTOS", new ArrayList<String>(list));
        return sharePhotoContent;
    }

    private static Bundle create(ShareVideoContent shareVideoContent, String string, boolean bl) {
        Bundle bundle = NativeDialogParameters.createBaseParameters(shareVideoContent, bl);
        Utility.putNonEmptyString(bundle, "TITLE", shareVideoContent.getContentTitle());
        Utility.putNonEmptyString(bundle, "DESCRIPTION", shareVideoContent.getContentDescription());
        Utility.putNonEmptyString(bundle, "VIDEO", string);
        return bundle;
    }

    public static Bundle create(UUID uUID, ShareContent object, boolean bl) {
        Validate.notNull(object, "shareContent");
        Validate.notNull(uUID, "callId");
        if (object instanceof ShareLinkContent) {
            return NativeDialogParameters.create((ShareLinkContent)object, bl);
        }
        if (object instanceof SharePhotoContent) {
            object = (SharePhotoContent)object;
            return NativeDialogParameters.create((SharePhotoContent)object, ShareInternalUtility.getPhotoUrls((SharePhotoContent)object, uUID), bl);
        }
        if (object instanceof ShareVideoContent) {
            object = (ShareVideoContent)object;
            return NativeDialogParameters.create((ShareVideoContent)object, ShareInternalUtility.getVideoUrl((ShareVideoContent)object, uUID), bl);
        }
        if (object instanceof ShareOpenGraphContent) {
            object = (ShareOpenGraphContent)object;
            try {
                uUID = NativeDialogParameters.create((ShareOpenGraphContent)object, ShareInternalUtility.removeNamespacesFromOGJsonObject(ShareInternalUtility.toJSONObjectForCall(uUID, (ShareOpenGraphContent)object), false), bl);
                return uUID;
            }
            catch (JSONException jSONException) {
                object = new StringBuilder();
                object.append("Unable to create a JSON Object from the provided ShareOpenGraphContent: ");
                object.append(jSONException.getMessage());
                throw new FacebookException(object.toString());
            }
        }
        if (object instanceof ShareMediaContent) {
            object = (ShareMediaContent)object;
            return NativeDialogParameters.create((ShareMediaContent)object, ShareInternalUtility.getMediaInfos((ShareMediaContent)object, uUID), bl);
        }
        if (object instanceof ShareCameraEffectContent) {
            object = (ShareCameraEffectContent)object;
            return NativeDialogParameters.create((ShareCameraEffectContent)object, ShareInternalUtility.getTextureUrlBundle((ShareCameraEffectContent)object, uUID), bl);
        }
        if (object instanceof ShareMessengerGenericTemplateContent) {
            return NativeDialogParameters.create((ShareMessengerGenericTemplateContent)object, bl);
        }
        if (object instanceof ShareMessengerOpenGraphMusicTemplateContent) {
            return NativeDialogParameters.create((ShareMessengerOpenGraphMusicTemplateContent)object, bl);
        }
        if (object instanceof ShareMessengerMediaTemplateContent) {
            return NativeDialogParameters.create((ShareMessengerMediaTemplateContent)object, bl);
        }
        return null;
    }

    private static Bundle createBaseParameters(ShareContent shareModel, boolean bl) {
        Bundle bundle = new Bundle();
        Utility.putUri(bundle, "LINK", shareModel.getContentUrl());
        Utility.putNonEmptyString(bundle, "PLACE", shareModel.getPlaceId());
        Utility.putNonEmptyString(bundle, "PAGE", shareModel.getPageId());
        Utility.putNonEmptyString(bundle, "REF", shareModel.getRef());
        bundle.putBoolean("DATA_FAILURES_FATAL", bl);
        List<String> list = shareModel.getPeopleIds();
        if (!Utility.isNullOrEmpty(list)) {
            bundle.putStringArrayList("FRIENDS", new ArrayList<String>(list));
        }
        if ((shareModel = shareModel.getShareHashtag()) != null) {
            Utility.putNonEmptyString(bundle, "HASHTAG", shareModel.getHashtag());
        }
        return bundle;
    }
}
