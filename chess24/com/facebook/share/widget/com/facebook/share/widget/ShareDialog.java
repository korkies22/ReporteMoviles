/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Bundle
 *  android.util.Log
 */
package com.facebook.share.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer;
import com.facebook.share.internal.CameraEffectFeature;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.OpenGraphActionDialogFeature;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareDialogFeature;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ShareDialog
extends FacebookDialogBase<ShareContent, Sharer.Result>
implements Sharer {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode();
    private static final String FEED_DIALOG = "feed";
    private static final String TAG = "ShareDialog";
    private static final String WEB_OG_SHARE_DIALOG = "share_open_graph";
    public static final String WEB_SHARE_DIALOG = "share";
    private boolean isAutomaticMode = true;
    private boolean shouldFailOnDataError = false;

    public ShareDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    ShareDialog(Activity activity, int n) {
        super(activity, n);
        ShareInternalUtility.registerStaticShareCallback(n);
    }

    public ShareDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    ShareDialog(Fragment fragment, int n) {
        this(new FragmentWrapper(fragment), n);
    }

    public ShareDialog(android.support.v4.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    ShareDialog(android.support.v4.app.Fragment fragment, int n) {
        this(new FragmentWrapper(fragment), n);
    }

    private ShareDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    private ShareDialog(FragmentWrapper fragmentWrapper, int n) {
        super(fragmentWrapper, n);
        ShareInternalUtility.registerStaticShareCallback(n);
    }

    public static boolean canShow(Class<? extends ShareContent> class_) {
        if (!ShareDialog.canShowWebTypeCheck(class_) && !ShareDialog.canShowNative(class_)) {
            return false;
        }
        return true;
    }

    private static boolean canShowNative(Class<? extends ShareContent> object) {
        if ((object = ShareDialog.getFeature(object)) != null && DialogPresenter.canPresentNativeDialogWithFeature((DialogFeature)object)) {
            return true;
        }
        return false;
    }

    private static boolean canShowWebCheck(ShareContent shareContent) {
        if (!ShareDialog.canShowWebTypeCheck(shareContent.getClass())) {
            return false;
        }
        if (shareContent instanceof ShareOpenGraphContent) {
            shareContent = (ShareOpenGraphContent)shareContent;
            try {
                ShareInternalUtility.toJSONObjectForWeb((ShareOpenGraphContent)shareContent);
            }
            catch (Exception exception) {
                Log.d((String)TAG, (String)"canShow returned false because the content of the Opem Graph object can't be shared via the web dialog", (Throwable)exception);
                return false;
            }
        }
        return true;
    }

    private static boolean canShowWebTypeCheck(Class<? extends ShareContent> class_) {
        boolean bl;
        block3 : {
            block2 : {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean bl2 = false;
                boolean bl3 = accessToken != null && !accessToken.isExpired();
                if (ShareLinkContent.class.isAssignableFrom(class_) || ShareOpenGraphContent.class.isAssignableFrom(class_)) break block2;
                bl = bl2;
                if (!SharePhotoContent.class.isAssignableFrom(class_)) break block3;
                bl = bl2;
                if (!bl3) break block3;
            }
            bl = true;
        }
        return bl;
    }

    private static DialogFeature getFeature(Class<? extends ShareContent> class_) {
        if (ShareLinkContent.class.isAssignableFrom(class_)) {
            return ShareDialogFeature.SHARE_DIALOG;
        }
        if (SharePhotoContent.class.isAssignableFrom(class_)) {
            return ShareDialogFeature.PHOTOS;
        }
        if (ShareVideoContent.class.isAssignableFrom(class_)) {
            return ShareDialogFeature.VIDEO;
        }
        if (ShareOpenGraphContent.class.isAssignableFrom(class_)) {
            return OpenGraphActionDialogFeature.OG_ACTION_DIALOG;
        }
        if (ShareMediaContent.class.isAssignableFrom(class_)) {
            return ShareDialogFeature.MULTIMEDIA;
        }
        if (ShareCameraEffectContent.class.isAssignableFrom(class_)) {
            return CameraEffectFeature.SHARE_CAMERA_EFFECT;
        }
        return null;
    }

    private void logDialogShare(Context object, ShareContent object2, Mode object3) {
        if (this.isAutomaticMode) {
            object3 = Mode.AUTOMATIC;
        }
        switch (.$SwitchMap$com$facebook$share$widget$ShareDialog$Mode[object3.ordinal()]) {
            default: {
                object3 = "unknown";
                break;
            }
            case 3: {
                object3 = "native";
                break;
            }
            case 2: {
                object3 = "web";
                break;
            }
            case 1: {
                object3 = "automatic";
            }
        }
        object2 = ShareDialog.getFeature(object2.getClass());
        object2 = object2 == ShareDialogFeature.SHARE_DIALOG ? "status" : (object2 == ShareDialogFeature.PHOTOS ? "photo" : (object2 == ShareDialogFeature.VIDEO ? "video" : (object2 == OpenGraphActionDialogFeature.OG_ACTION_DIALOG ? "open_graph" : "unknown")));
        object = AppEventsLogger.newLogger((Context)object);
        Bundle bundle = new Bundle();
        bundle.putString("fb_share_dialog_show", (String)object3);
        bundle.putString("fb_share_dialog_content_type", (String)object2);
        object.logSdkEvent("fb_share_dialog_show", null, bundle);
    }

    public static void show(Activity activity, ShareContent shareContent) {
        new ShareDialog(activity).show(shareContent);
    }

    public static void show(Fragment fragment, ShareContent shareContent) {
        ShareDialog.show(new FragmentWrapper(fragment), shareContent);
    }

    public static void show(android.support.v4.app.Fragment fragment, ShareContent shareContent) {
        ShareDialog.show(new FragmentWrapper(fragment), shareContent);
    }

    private static void show(FragmentWrapper fragmentWrapper, ShareContent shareContent) {
        new ShareDialog(fragmentWrapper).show(shareContent);
    }

    public boolean canShow(ShareContent shareContent, Mode mode) {
        Object object = mode;
        if (mode == Mode.AUTOMATIC) {
            object = BASE_AUTOMATIC_MODE;
        }
        return this.canShowImpl(shareContent, object);
    }

    @Override
    protected AppCall createBaseAppCall() {
        return new AppCall(this.getRequestCode());
    }

    @Override
    protected List<FacebookDialogBase<ShareContent, Sharer.Result>> getOrderedModeHandlers() {
        ArrayList<FacebookDialogBase<ShareContent, Sharer.Result>> arrayList = new ArrayList<FacebookDialogBase<ShareContent, Sharer.Result>>();
        arrayList.add(new NativeHandler());
        arrayList.add(new FeedHandler());
        arrayList.add(new WebShareHandler());
        arrayList.add(new CameraEffectHandler());
        return arrayList;
    }

    @Override
    public boolean getShouldFailOnDataError() {
        return this.shouldFailOnDataError;
    }

    @Override
    protected void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, FacebookCallback<Sharer.Result> facebookCallback) {
        ShareInternalUtility.registerSharerCallback(this.getRequestCode(), callbackManagerImpl, facebookCallback);
    }

    @Override
    public void setShouldFailOnDataError(boolean bl) {
        this.shouldFailOnDataError = bl;
    }

    public void show(ShareContent shareContent, Mode object) {
        boolean bl = object == Mode.AUTOMATIC;
        this.isAutomaticMode = bl;
        if (this.isAutomaticMode) {
            object = BASE_AUTOMATIC_MODE;
        }
        this.showImpl(shareContent, object);
    }

    private class CameraEffectHandler
    extends FacebookDialogBase<ShareContent, Sharer.Result> {
        private CameraEffectHandler() {
            super(ShareDialog.this);
        }

        public boolean canShow(ShareContent shareContent, boolean bl) {
            if (shareContent instanceof ShareCameraEffectContent && ShareDialog.canShowNative(shareContent.getClass())) {
                return true;
            }
            return false;
        }

        public AppCall createAppCall(final ShareContent shareContent) {
            ShareContentValidation.validateForNativeShare(shareContent);
            final AppCall appCall = ShareDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForNativeDialog(appCall, new DialogPresenter.ParameterProvider(ShareDialog.this.getShouldFailOnDataError()){
                final /* synthetic */ boolean val$shouldFailOnDataError;
                {
                    this.val$shouldFailOnDataError = bl;
                }

                @Override
                public Bundle getLegacyParameters() {
                    return LegacyNativeDialogParameters.create(appCall.getCallId(), shareContent, this.val$shouldFailOnDataError);
                }

                @Override
                public Bundle getParameters() {
                    return NativeDialogParameters.create(appCall.getCallId(), shareContent, this.val$shouldFailOnDataError);
                }
            }, ShareDialog.getFeature(shareContent.getClass()));
            return appCall;
        }

        public Object getMode() {
            return Mode.NATIVE;
        }

    }

    private class FeedHandler
    extends FacebookDialogBase<ShareContent, Sharer.Result> {
        private FeedHandler() {
            super(ShareDialog.this);
        }

        public boolean canShow(ShareContent shareContent, boolean bl) {
            if (!(shareContent instanceof ShareLinkContent) && !(shareContent instanceof ShareFeedContent)) {
                return false;
            }
            return true;
        }

        public AppCall createAppCall(ShareContent shareContent) {
            ShareDialog.this.logDialogShare((Context)ShareDialog.this.getActivityContext(), shareContent, Mode.FEED);
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
            return Mode.FEED;
        }
    }

    public static enum Mode {
        AUTOMATIC,
        NATIVE,
        WEB,
        FEED;
        

        private Mode() {
        }
    }

    private class NativeHandler
    extends FacebookDialogBase<ShareContent, Sharer.Result> {
        private NativeHandler() {
            super(ShareDialog.this);
        }

        public boolean canShow(ShareContent shareContent, boolean bl) {
            boolean bl2 = false;
            if (shareContent != null) {
                boolean bl3;
                if (shareContent instanceof ShareCameraEffectContent) {
                    return false;
                }
                if (!bl) {
                    bl3 = shareContent.getShareHashtag() != null ? DialogPresenter.canPresentNativeDialogWithFeature(ShareDialogFeature.HASHTAG) : true;
                    bl = bl3;
                    if (shareContent instanceof ShareLinkContent) {
                        bl = bl3;
                        if (!Utility.isNullOrEmpty(((ShareLinkContent)shareContent).getQuote())) {
                            bl = bl3 & DialogPresenter.canPresentNativeDialogWithFeature(ShareDialogFeature.LINK_SHARE_QUOTES);
                        }
                    }
                } else {
                    bl = true;
                }
                bl3 = bl2;
                if (bl) {
                    bl3 = bl2;
                    if (ShareDialog.canShowNative(shareContent.getClass())) {
                        bl3 = true;
                    }
                }
                return bl3;
            }
            return false;
        }

        public AppCall createAppCall(final ShareContent shareContent) {
            ShareDialog.this.logDialogShare((Context)ShareDialog.this.getActivityContext(), shareContent, Mode.NATIVE);
            ShareContentValidation.validateForNativeShare(shareContent);
            final AppCall appCall = ShareDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForNativeDialog(appCall, new DialogPresenter.ParameterProvider(ShareDialog.this.getShouldFailOnDataError()){
                final /* synthetic */ boolean val$shouldFailOnDataError;
                {
                    this.val$shouldFailOnDataError = bl;
                }

                @Override
                public Bundle getLegacyParameters() {
                    return LegacyNativeDialogParameters.create(appCall.getCallId(), shareContent, this.val$shouldFailOnDataError);
                }

                @Override
                public Bundle getParameters() {
                    return NativeDialogParameters.create(appCall.getCallId(), shareContent, this.val$shouldFailOnDataError);
                }
            }, ShareDialog.getFeature(shareContent.getClass()));
            return appCall;
        }

        public Object getMode() {
            return Mode.NATIVE;
        }

    }

    private class WebShareHandler
    extends FacebookDialogBase<ShareContent, Sharer.Result> {
        private WebShareHandler() {
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
            ShareDialog.this.logDialogShare((Context)ShareDialog.this.getActivityContext(), shareContent, Mode.WEB);
            AppCall appCall = ShareDialog.this.createBaseAppCall();
            ShareContentValidation.validateForWebShare(shareContent);
            Bundle bundle = shareContent instanceof ShareLinkContent ? WebDialogParameters.create((ShareLinkContent)shareContent) : (shareContent instanceof SharePhotoContent ? WebDialogParameters.create(this.createAndMapAttachments((SharePhotoContent)shareContent, appCall.getCallId())) : WebDialogParameters.create((ShareOpenGraphContent)shareContent));
            DialogPresenter.setupAppCallForWebDialog(appCall, this.getActionName(shareContent), bundle);
            return appCall;
        }

        public Object getMode() {
            return Mode.WEB;
        }
    }

}
