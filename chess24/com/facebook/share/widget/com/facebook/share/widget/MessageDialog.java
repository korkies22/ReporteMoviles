/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.share.Sharer;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.MessageDialogFeature;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.OpenGraphMessageDialogFeature;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MessageDialog
extends FacebookDialogBase<ShareContent, Sharer.Result>
implements Sharer {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Message.toRequestCode();
    private boolean shouldFailOnDataError = false;

    public MessageDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    MessageDialog(Activity activity, int n) {
        super(activity, n);
        ShareInternalUtility.registerStaticShareCallback(n);
    }

    public MessageDialog(Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    MessageDialog(Fragment fragment, int n) {
        this(new FragmentWrapper(fragment), n);
    }

    public MessageDialog(android.support.v4.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
    }

    MessageDialog(android.support.v4.app.Fragment fragment, int n) {
        this(new FragmentWrapper(fragment), n);
    }

    private MessageDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    private MessageDialog(FragmentWrapper fragmentWrapper, int n) {
        super(fragmentWrapper, n);
        ShareInternalUtility.registerStaticShareCallback(n);
    }

    public static boolean canShow(Class<? extends ShareContent> object) {
        if ((object = MessageDialog.getFeature(object)) != null && DialogPresenter.canPresentNativeDialogWithFeature((DialogFeature)object)) {
            return true;
        }
        return false;
    }

    private static DialogFeature getFeature(Class<? extends ShareContent> class_) {
        if (ShareLinkContent.class.isAssignableFrom(class_)) {
            return MessageDialogFeature.MESSAGE_DIALOG;
        }
        if (SharePhotoContent.class.isAssignableFrom(class_)) {
            return MessageDialogFeature.PHOTOS;
        }
        if (ShareVideoContent.class.isAssignableFrom(class_)) {
            return MessageDialogFeature.VIDEO;
        }
        if (ShareOpenGraphContent.class.isAssignableFrom(class_)) {
            return OpenGraphMessageDialogFeature.OG_MESSAGE_DIALOG;
        }
        if (ShareMessengerGenericTemplateContent.class.isAssignableFrom(class_)) {
            return MessageDialogFeature.MESSENGER_GENERIC_TEMPLATE;
        }
        if (ShareMessengerOpenGraphMusicTemplateContent.class.isAssignableFrom(class_)) {
            return MessageDialogFeature.MESSENGER_OPEN_GRAPH_MUSIC_TEMPLATE;
        }
        if (ShareMessengerMediaTemplateContent.class.isAssignableFrom(class_)) {
            return MessageDialogFeature.MESSENGER_MEDIA_TEMPLATE;
        }
        return null;
    }

    private static void logDialogShare(Context object, ShareContent shareContent, AppCall appCall) {
        Object object2 = MessageDialog.getFeature(shareContent.getClass());
        object2 = object2 == MessageDialogFeature.MESSAGE_DIALOG ? "status" : (object2 == MessageDialogFeature.PHOTOS ? "photo" : (object2 == MessageDialogFeature.VIDEO ? "video" : (object2 == OpenGraphMessageDialogFeature.OG_MESSAGE_DIALOG ? "open_graph" : (object2 == MessageDialogFeature.MESSENGER_GENERIC_TEMPLATE ? "GenericTemplate" : (object2 == MessageDialogFeature.MESSENGER_MEDIA_TEMPLATE ? "MediaTemplate" : (object2 == MessageDialogFeature.MESSENGER_OPEN_GRAPH_MUSIC_TEMPLATE ? "OpenGraphMusicTemplate" : "unknown"))))));
        object = AppEventsLogger.newLogger((Context)object);
        Bundle bundle = new Bundle();
        bundle.putString("fb_share_dialog_content_type", (String)object2);
        bundle.putString("fb_share_dialog_content_uuid", appCall.getCallId().toString());
        bundle.putString("fb_share_dialog_content_page_id", shareContent.getPageId());
        object.logSdkEvent("fb_messenger_share_dialog_show", null, bundle);
    }

    public static void show(Activity activity, ShareContent shareContent) {
        new MessageDialog(activity).show(shareContent);
    }

    public static void show(Fragment fragment, ShareContent shareContent) {
        MessageDialog.show(new FragmentWrapper(fragment), shareContent);
    }

    public static void show(android.support.v4.app.Fragment fragment, ShareContent shareContent) {
        MessageDialog.show(new FragmentWrapper(fragment), shareContent);
    }

    private static void show(FragmentWrapper fragmentWrapper, ShareContent shareContent) {
        new MessageDialog(fragmentWrapper).show(shareContent);
    }

    @Override
    protected AppCall createBaseAppCall() {
        return new AppCall(this.getRequestCode());
    }

    @Override
    protected List<FacebookDialogBase<ShareContent, Sharer.Result>> getOrderedModeHandlers() {
        ArrayList<FacebookDialogBase<ShareContent, Sharer.Result>> arrayList = new ArrayList<FacebookDialogBase<ShareContent, Sharer.Result>>();
        arrayList.add(new NativeHandler());
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

    private class NativeHandler
    extends FacebookDialogBase<ShareContent, Sharer.Result> {
        private NativeHandler() {
            super(MessageDialog.this);
        }

        public boolean canShow(ShareContent shareContent, boolean bl) {
            if (shareContent != null && MessageDialog.canShow(shareContent.getClass())) {
                return true;
            }
            return false;
        }

        public AppCall createAppCall(final ShareContent shareContent) {
            ShareContentValidation.validateForMessage(shareContent);
            final AppCall appCall = MessageDialog.this.createBaseAppCall();
            final boolean bl = MessageDialog.this.getShouldFailOnDataError();
            MessageDialog.logDialogShare((Context)MessageDialog.this.getActivityContext(), shareContent, appCall);
            DialogPresenter.setupAppCallForNativeDialog(appCall, new DialogPresenter.ParameterProvider(){

                @Override
                public Bundle getLegacyParameters() {
                    return LegacyNativeDialogParameters.create(appCall.getCallId(), shareContent, bl);
                }

                @Override
                public Bundle getParameters() {
                    return NativeDialogParameters.create(appCall.getCallId(), shareContent, bl);
                }
            }, MessageDialog.getFeature(shareContent.getClass()));
            return appCall;
        }

    }

}
