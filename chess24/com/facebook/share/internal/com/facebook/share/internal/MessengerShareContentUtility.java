/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.net.Uri;
import android.os.Bundle;
import com.facebook.internal.Utility;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerGenericTemplateElement;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareMessengerURLActionButton;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessengerShareContentUtility {
    public static final String ATTACHMENT = "attachment";
    public static final String ATTACHMENT_ID = "attachment_id";
    public static final String ATTACHMENT_PAYLOAD = "payload";
    public static final String ATTACHMENT_TEMPLATE_TYPE = "template";
    public static final String ATTACHMENT_TYPE = "type";
    public static final String BUTTONS = "buttons";
    public static final String BUTTON_TYPE = "type";
    public static final String BUTTON_URL_TYPE = "web_url";
    public static final String DEFAULT_ACTION = "default_action";
    public static final String ELEMENTS = "elements";
    public static final Pattern FACEBOOK_DOMAIN = Pattern.compile("^(.+)\\.(facebook\\.com)$");
    public static final String FALLBACK_URL = "fallback_url";
    public static final String IMAGE_ASPECT_RATIO = "image_aspect_ratio";
    public static final String IMAGE_RATIO_HORIZONTAL = "horizontal";
    public static final String IMAGE_RATIO_SQUARE = "square";
    public static final String IMAGE_URL = "image_url";
    public static final String MEDIA_IMAGE = "image";
    public static final String MEDIA_TYPE = "media_type";
    public static final String MEDIA_VIDEO = "video";
    public static final String MESSENGER_EXTENSIONS = "messenger_extensions";
    public static final String PREVIEW_DEFAULT = "DEFAULT";
    public static final String PREVIEW_OPEN_GRAPH = "OPEN_GRAPH";
    public static final String SHARABLE = "sharable";
    public static final String SHARE_BUTTON_HIDE = "hide";
    public static final String SUBTITLE = "subtitle";
    public static final String TEMPLATE_GENERIC_TYPE = "generic";
    public static final String TEMPLATE_MEDIA_TYPE = "media";
    public static final String TEMPLATE_OPEN_GRAPH_TYPE = "open_graph";
    public static final String TEMPLATE_TYPE = "template_type";
    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String WEBVIEW_RATIO = "webview_height_ratio";
    public static final String WEBVIEW_RATIO_COMPACT = "compact";
    public static final String WEBVIEW_RATIO_FULL = "full";
    public static final String WEBVIEW_RATIO_TALL = "tall";
    public static final String WEBVIEW_SHARE_BUTTON = "webview_share_button";

    private static void addActionButton(Bundle bundle, ShareMessengerActionButton shareMessengerActionButton, boolean bl) throws JSONException {
        if (shareMessengerActionButton == null) {
            return;
        }
        if (shareMessengerActionButton instanceof ShareMessengerURLActionButton) {
            MessengerShareContentUtility.addURLActionButton(bundle, (ShareMessengerURLActionButton)shareMessengerActionButton, bl);
        }
    }

    public static void addGenericTemplateContent(Bundle bundle, ShareMessengerGenericTemplateContent shareMessengerGenericTemplateContent) throws JSONException {
        MessengerShareContentUtility.addGenericTemplateElementForPreview(bundle, shareMessengerGenericTemplateContent.getGenericTemplateElement());
        Utility.putJSONValueInBundle(bundle, "MESSENGER_PLATFORM_CONTENT", (Object)MessengerShareContentUtility.serializeGenericTemplateContent(shareMessengerGenericTemplateContent));
    }

    private static void addGenericTemplateElementForPreview(Bundle bundle, ShareMessengerGenericTemplateElement shareMessengerGenericTemplateElement) throws JSONException {
        if (shareMessengerGenericTemplateElement.getButton() != null) {
            MessengerShareContentUtility.addActionButton(bundle, shareMessengerGenericTemplateElement.getButton(), false);
        } else if (shareMessengerGenericTemplateElement.getDefaultAction() != null) {
            MessengerShareContentUtility.addActionButton(bundle, shareMessengerGenericTemplateElement.getDefaultAction(), true);
        }
        Utility.putUri(bundle, "IMAGE", shareMessengerGenericTemplateElement.getImageUrl());
        Utility.putNonEmptyString(bundle, "PREVIEW_TYPE", PREVIEW_DEFAULT);
        Utility.putNonEmptyString(bundle, "TITLE", shareMessengerGenericTemplateElement.getTitle());
        Utility.putNonEmptyString(bundle, "SUBTITLE", shareMessengerGenericTemplateElement.getSubtitle());
    }

    public static void addMediaTemplateContent(Bundle bundle, ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent) throws JSONException {
        MessengerShareContentUtility.addMediaTemplateContentForPreview(bundle, shareMessengerMediaTemplateContent);
        Utility.putJSONValueInBundle(bundle, "MESSENGER_PLATFORM_CONTENT", (Object)MessengerShareContentUtility.serializeMediaTemplateContent(shareMessengerMediaTemplateContent));
    }

    private static void addMediaTemplateContentForPreview(Bundle bundle, ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent) throws JSONException {
        MessengerShareContentUtility.addActionButton(bundle, shareMessengerMediaTemplateContent.getButton(), false);
        Utility.putNonEmptyString(bundle, "PREVIEW_TYPE", PREVIEW_DEFAULT);
        Utility.putNonEmptyString(bundle, "ATTACHMENT_ID", shareMessengerMediaTemplateContent.getAttachmentId());
        if (shareMessengerMediaTemplateContent.getMediaUrl() != null) {
            Utility.putUri(bundle, MessengerShareContentUtility.getMediaUrlKey(shareMessengerMediaTemplateContent.getMediaUrl()), shareMessengerMediaTemplateContent.getMediaUrl());
        }
        Utility.putNonEmptyString(bundle, "type", MessengerShareContentUtility.getMediaType(shareMessengerMediaTemplateContent.getMediaType()));
    }

    public static void addOpenGraphMusicTemplateContent(Bundle bundle, ShareMessengerOpenGraphMusicTemplateContent shareMessengerOpenGraphMusicTemplateContent) throws JSONException {
        MessengerShareContentUtility.addOpenGraphMusicTemplateContentForPreview(bundle, shareMessengerOpenGraphMusicTemplateContent);
        Utility.putJSONValueInBundle(bundle, "MESSENGER_PLATFORM_CONTENT", (Object)MessengerShareContentUtility.serializeOpenGraphMusicTemplateContent(shareMessengerOpenGraphMusicTemplateContent));
    }

    private static void addOpenGraphMusicTemplateContentForPreview(Bundle bundle, ShareMessengerOpenGraphMusicTemplateContent shareMessengerOpenGraphMusicTemplateContent) throws JSONException {
        MessengerShareContentUtility.addActionButton(bundle, shareMessengerOpenGraphMusicTemplateContent.getButton(), false);
        Utility.putNonEmptyString(bundle, "PREVIEW_TYPE", PREVIEW_OPEN_GRAPH);
        Utility.putUri(bundle, "OPEN_GRAPH_URL", shareMessengerOpenGraphMusicTemplateContent.getUrl());
    }

    private static void addURLActionButton(Bundle bundle, ShareMessengerURLActionButton shareMessengerURLActionButton, boolean bl) throws JSONException {
        CharSequence charSequence;
        if (bl) {
            charSequence = Utility.getUriString(shareMessengerURLActionButton.getUrl());
        } else {
            charSequence = new StringBuilder();
            charSequence.append(shareMessengerURLActionButton.getTitle());
            charSequence.append(" - ");
            charSequence.append(Utility.getUriString(shareMessengerURLActionButton.getUrl()));
            charSequence = charSequence.toString();
        }
        Utility.putNonEmptyString(bundle, "TARGET_DISPLAY", (String)charSequence);
        Utility.putUri(bundle, "ITEM_URL", shareMessengerURLActionButton.getUrl());
    }

    private static String getImageRatioString(ShareMessengerGenericTemplateContent.ImageAspectRatio imageAspectRatio) {
        if (imageAspectRatio == null) {
            return IMAGE_RATIO_HORIZONTAL;
        }
        if (.$SwitchMap$com$facebook$share$model$ShareMessengerGenericTemplateContent$ImageAspectRatio[imageAspectRatio.ordinal()] != 1) {
            return IMAGE_RATIO_HORIZONTAL;
        }
        return IMAGE_RATIO_SQUARE;
    }

    private static String getMediaType(ShareMessengerMediaTemplateContent.MediaType mediaType) {
        if (mediaType == null) {
            return MEDIA_IMAGE;
        }
        if (.$SwitchMap$com$facebook$share$model$ShareMessengerMediaTemplateContent$MediaType[mediaType.ordinal()] != 1) {
            return MEDIA_IMAGE;
        }
        return MEDIA_VIDEO;
    }

    private static String getMediaUrlKey(Uri object) {
        if (!Utility.isNullOrEmpty((String)(object = object.getHost())) && FACEBOOK_DOMAIN.matcher((CharSequence)object).matches()) {
            return "uri";
        }
        return "IMAGE";
    }

    private static String getShouldHideShareButton(ShareMessengerURLActionButton shareMessengerURLActionButton) {
        if (shareMessengerURLActionButton.getShouldHideWebviewShareButton()) {
            return SHARE_BUTTON_HIDE;
        }
        return null;
    }

    private static String getWebviewHeightRatioString(ShareMessengerURLActionButton.WebviewHeightRatio webviewHeightRatio) {
        if (webviewHeightRatio == null) {
            return WEBVIEW_RATIO_FULL;
        }
        switch (.$SwitchMap$com$facebook$share$model$ShareMessengerURLActionButton$WebviewHeightRatio[webviewHeightRatio.ordinal()]) {
            default: {
                return WEBVIEW_RATIO_FULL;
            }
            case 2: {
                return WEBVIEW_RATIO_TALL;
            }
            case 1: 
        }
        return WEBVIEW_RATIO_COMPACT;
    }

    private static JSONObject serializeActionButton(ShareMessengerActionButton shareMessengerActionButton) throws JSONException {
        return MessengerShareContentUtility.serializeActionButton(shareMessengerActionButton, false);
    }

    private static JSONObject serializeActionButton(ShareMessengerActionButton shareMessengerActionButton, boolean bl) throws JSONException {
        if (shareMessengerActionButton instanceof ShareMessengerURLActionButton) {
            return MessengerShareContentUtility.serializeURLActionButton((ShareMessengerURLActionButton)shareMessengerActionButton, bl);
        }
        return null;
    }

    private static JSONObject serializeGenericTemplateContent(ShareMessengerGenericTemplateContent shareMessengerGenericTemplateContent) throws JSONException {
        JSONArray jSONArray = new JSONArray().put((Object)MessengerShareContentUtility.serializeGenericTemplateElement(shareMessengerGenericTemplateContent.getGenericTemplateElement()));
        shareMessengerGenericTemplateContent = new JSONObject().put(TEMPLATE_TYPE, (Object)TEMPLATE_GENERIC_TYPE).put(SHARABLE, shareMessengerGenericTemplateContent.getIsSharable()).put(IMAGE_ASPECT_RATIO, (Object)MessengerShareContentUtility.getImageRatioString(shareMessengerGenericTemplateContent.getImageAspectRatio())).put(ELEMENTS, (Object)jSONArray);
        shareMessengerGenericTemplateContent = new JSONObject().put("type", (Object)ATTACHMENT_TEMPLATE_TYPE).put(ATTACHMENT_PAYLOAD, (Object)shareMessengerGenericTemplateContent);
        return new JSONObject().put(ATTACHMENT, (Object)shareMessengerGenericTemplateContent);
    }

    private static JSONObject serializeGenericTemplateElement(ShareMessengerGenericTemplateElement shareMessengerGenericTemplateElement) throws JSONException {
        JSONObject jSONObject = new JSONObject().put(TITLE, (Object)shareMessengerGenericTemplateElement.getTitle()).put(SUBTITLE, (Object)shareMessengerGenericTemplateElement.getSubtitle()).put(IMAGE_URL, (Object)Utility.getUriString(shareMessengerGenericTemplateElement.getImageUrl()));
        if (shareMessengerGenericTemplateElement.getButton() != null) {
            JSONArray jSONArray = new JSONArray();
            jSONArray.put((Object)MessengerShareContentUtility.serializeActionButton(shareMessengerGenericTemplateElement.getButton()));
            jSONObject.put(BUTTONS, (Object)jSONArray);
        }
        if (shareMessengerGenericTemplateElement.getDefaultAction() != null) {
            jSONObject.put(DEFAULT_ACTION, (Object)MessengerShareContentUtility.serializeActionButton(shareMessengerGenericTemplateElement.getDefaultAction(), true));
        }
        return jSONObject;
    }

    private static JSONObject serializeMediaTemplateContent(ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent) throws JSONException {
        shareMessengerMediaTemplateContent = new JSONArray().put((Object)MessengerShareContentUtility.serializeMediaTemplateElement(shareMessengerMediaTemplateContent));
        shareMessengerMediaTemplateContent = new JSONObject().put(TEMPLATE_TYPE, (Object)TEMPLATE_MEDIA_TYPE).put(ELEMENTS, (Object)shareMessengerMediaTemplateContent);
        shareMessengerMediaTemplateContent = new JSONObject().put("type", (Object)ATTACHMENT_TEMPLATE_TYPE).put(ATTACHMENT_PAYLOAD, (Object)shareMessengerMediaTemplateContent);
        return new JSONObject().put(ATTACHMENT, (Object)shareMessengerMediaTemplateContent);
    }

    private static JSONObject serializeMediaTemplateElement(ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent) throws JSONException {
        JSONObject jSONObject = new JSONObject().put(ATTACHMENT_ID, (Object)shareMessengerMediaTemplateContent.getAttachmentId()).put(URL, (Object)Utility.getUriString(shareMessengerMediaTemplateContent.getMediaUrl())).put(MEDIA_TYPE, (Object)MessengerShareContentUtility.getMediaType(shareMessengerMediaTemplateContent.getMediaType()));
        if (shareMessengerMediaTemplateContent.getButton() != null) {
            JSONArray jSONArray = new JSONArray();
            jSONArray.put((Object)MessengerShareContentUtility.serializeActionButton(shareMessengerMediaTemplateContent.getButton()));
            jSONObject.put(BUTTONS, (Object)jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject serializeOpenGraphMusicTemplateContent(ShareMessengerOpenGraphMusicTemplateContent shareMessengerOpenGraphMusicTemplateContent) throws JSONException {
        shareMessengerOpenGraphMusicTemplateContent = new JSONArray().put((Object)MessengerShareContentUtility.serializeOpenGraphMusicTemplateElement(shareMessengerOpenGraphMusicTemplateContent));
        shareMessengerOpenGraphMusicTemplateContent = new JSONObject().put(TEMPLATE_TYPE, (Object)TEMPLATE_OPEN_GRAPH_TYPE).put(ELEMENTS, (Object)shareMessengerOpenGraphMusicTemplateContent);
        shareMessengerOpenGraphMusicTemplateContent = new JSONObject().put("type", (Object)ATTACHMENT_TEMPLATE_TYPE).put(ATTACHMENT_PAYLOAD, (Object)shareMessengerOpenGraphMusicTemplateContent);
        return new JSONObject().put(ATTACHMENT, (Object)shareMessengerOpenGraphMusicTemplateContent);
    }

    private static JSONObject serializeOpenGraphMusicTemplateElement(ShareMessengerOpenGraphMusicTemplateContent shareMessengerOpenGraphMusicTemplateContent) throws JSONException {
        JSONObject jSONObject = new JSONObject().put(URL, (Object)Utility.getUriString(shareMessengerOpenGraphMusicTemplateContent.getUrl()));
        if (shareMessengerOpenGraphMusicTemplateContent.getButton() != null) {
            JSONArray jSONArray = new JSONArray();
            jSONArray.put((Object)MessengerShareContentUtility.serializeActionButton(shareMessengerOpenGraphMusicTemplateContent.getButton()));
            jSONObject.put(BUTTONS, (Object)jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject serializeURLActionButton(ShareMessengerURLActionButton shareMessengerURLActionButton, boolean bl) throws JSONException {
        JSONObject jSONObject = new JSONObject().put("type", (Object)BUTTON_URL_TYPE);
        String string = bl ? null : shareMessengerURLActionButton.getTitle();
        return jSONObject.put(TITLE, (Object)string).put(URL, (Object)Utility.getUriString(shareMessengerURLActionButton.getUrl())).put(WEBVIEW_RATIO, (Object)MessengerShareContentUtility.getWebviewHeightRatioString(shareMessengerURLActionButton.getWebviewHeightRatio())).put(MESSENGER_EXTENSIONS, shareMessengerURLActionButton.getIsMessengerExtensionURL()).put(FALLBACK_URL, (Object)Utility.getUriString(shareMessengerURLActionButton.getFallbackUrl())).put(WEBVIEW_SHARE_BUTTON, (Object)MessengerShareContentUtility.getShouldHideShareButton(shareMessengerURLActionButton));
    }

}
