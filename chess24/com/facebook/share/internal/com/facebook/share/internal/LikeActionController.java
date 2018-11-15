/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppCall;
import com.facebook.internal.BundleJSONConverter;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.Logger;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.Utility;
import com.facebook.internal.WorkQueue;
import com.facebook.share.internal.LikeContent;
import com.facebook.share.internal.LikeDialog;
import com.facebook.share.internal.LikeStatusClient;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.widget.LikeView;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class LikeActionController {
    @Deprecated
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR = "com.facebook.sdk.LikeActionController.DID_ERROR";
    @Deprecated
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_RESET = "com.facebook.sdk.LikeActionController.DID_RESET";
    @Deprecated
    public static final String ACTION_LIKE_ACTION_CONTROLLER_UPDATED = "com.facebook.sdk.LikeActionController.UPDATED";
    @Deprecated
    public static final String ACTION_OBJECT_ID_KEY = "com.facebook.sdk.LikeActionController.OBJECT_ID";
    private static final int ERROR_CODE_OBJECT_ALREADY_LIKED = 3501;
    @Deprecated
    public static final String ERROR_INVALID_OBJECT_ID = "Invalid Object Id";
    @Deprecated
    public static final String ERROR_PUBLISH_ERROR = "Unable to publish the like/unlike action";
    private static final String JSON_BOOL_IS_OBJECT_LIKED_KEY = "is_object_liked";
    private static final String JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE = "facebook_dialog_analytics_bundle";
    private static final String JSON_INT_OBJECT_TYPE_KEY = "object_type";
    private static final String JSON_INT_VERSION_KEY = "com.facebook.share.internal.LikeActionController.version";
    private static final String JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY = "like_count_string_without_like";
    private static final String JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY = "like_count_string_with_like";
    private static final String JSON_STRING_OBJECT_ID_KEY = "object_id";
    private static final String JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY = "social_sentence_without_like";
    private static final String JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY = "social_sentence_with_like";
    private static final String JSON_STRING_UNLIKE_TOKEN_KEY = "unlike_token";
    private static final String LIKE_ACTION_CONTROLLER_STORE = "com.facebook.LikeActionController.CONTROLLER_STORE_KEY";
    private static final String LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY = "OBJECT_SUFFIX";
    private static final String LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY = "PENDING_CONTROLLER_KEY";
    private static final int LIKE_ACTION_CONTROLLER_VERSION = 3;
    private static final String LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY = "like_count_string";
    private static final String LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY = "object_is_liked";
    private static final String LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY = "social_sentence";
    private static final String LIKE_DIALOG_RESPONSE_UNLIKE_TOKEN_KEY = "unlike_token";
    private static final int MAX_CACHE_SIZE = 128;
    private static final int MAX_OBJECT_SUFFIX = 1000;
    private static final String TAG = "LikeActionController";
    private static AccessTokenTracker accessTokenTracker;
    private static final ConcurrentHashMap<String, LikeActionController> cache;
    private static FileLruCache controllerDiskCache;
    private static WorkQueue diskIOWorkQueue;
    private static Handler handler;
    private static boolean isInitialized;
    private static WorkQueue mruCacheWorkQueue;
    private static String objectIdForPendingController;
    private static volatile int objectSuffix;
    private AppEventsLogger appEventsLogger;
    private Bundle facebookDialogAnalyticsBundle;
    private boolean isObjectLiked;
    private boolean isObjectLikedOnServer;
    private boolean isPendingLikeOrUnlike;
    private String likeCountStringWithLike;
    private String likeCountStringWithoutLike;
    private String objectId;
    private boolean objectIsPage;
    private LikeView.ObjectType objectType;
    private String socialSentenceWithLike;
    private String socialSentenceWithoutLike;
    private String unlikeToken;
    private String verifiedObjectId;

    static {
        cache = new ConcurrentHashMap();
        mruCacheWorkQueue = new WorkQueue(1);
        diskIOWorkQueue = new WorkQueue(1);
    }

    private LikeActionController(String string, LikeView.ObjectType objectType) {
        this.objectId = string;
        this.objectType = objectType;
    }

    private static void broadcastAction(LikeActionController likeActionController, String string) {
        LikeActionController.broadcastAction(likeActionController, string, null);
    }

    private static void broadcastAction(LikeActionController likeActionController, String string, Bundle bundle) {
        Intent intent = new Intent(string);
        string = bundle;
        if (likeActionController != null) {
            string = bundle;
            if (bundle == null) {
                string = new Bundle();
            }
            string.putString(ACTION_OBJECT_ID_KEY, likeActionController.getObjectId());
        }
        if (string != null) {
            intent.putExtras((Bundle)string);
        }
        LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()).sendBroadcast(intent);
    }

    private boolean canUseOGPublish() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (!this.objectIsPage && this.verifiedObjectId != null && accessToken != null && accessToken.getPermissions() != null && accessToken.getPermissions().contains("publish_actions")) {
            return true;
        }
        return false;
    }

    private void clearState() {
        this.facebookDialogAnalyticsBundle = null;
        LikeActionController.storeObjectIdForPendingController(null);
    }

    private static void createControllerForObjectIdAndType(String string, LikeView.ObjectType objectType, CreationCallback creationCallback) {
        LikeActionController likeActionController;
        final LikeActionController likeActionController2 = LikeActionController.getControllerFromInMemoryCache(string);
        if (likeActionController2 != null) {
            LikeActionController.verifyControllerAndInvokeCallback(likeActionController2, objectType, creationCallback);
            return;
        }
        likeActionController2 = likeActionController = LikeActionController.deserializeFromDiskSynchronously(string);
        if (likeActionController == null) {
            likeActionController2 = new LikeActionController(string, objectType);
            LikeActionController.serializeToDiskAsync(likeActionController2);
        }
        LikeActionController.putControllerInMemoryCache(string, likeActionController2);
        handler.post(new Runnable(){

            @Override
            public void run() {
                likeActionController2.refreshStatusAsync();
            }
        });
        LikeActionController.invokeCallbackWithController(creationCallback, likeActionController2, null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static LikeActionController deserializeFromDiskSynchronously(String object) {
        Object object2;
        void var0_3;
        block9 : {
            Object object3;
            block10 : {
                Object var5_5;
                Object var4_4;
                block8 : {
                    block7 : {
                        var4_4 = null;
                        var5_5 = null;
                        Object var3_6 = null;
                        object = LikeActionController.getCacheKeyForObjectId((String)object);
                        object3 = controllerDiskCache.get((String)object);
                        object = var3_6;
                        if (object3 == null) break block7;
                        object2 = object3;
                        try {
                            String string = Utility.readStreamToString((InputStream)object3);
                            object = var3_6;
                            object2 = object3;
                            if (Utility.isNullOrEmpty(string)) break block7;
                            object2 = object3;
                            object = LikeActionController.deserializeFromJson(string);
                        }
                        catch (IOException iOException) {
                            object = object3;
                            break block8;
                        }
                    }
                    object2 = object;
                    if (object3 == null) return object2;
                    object2 = object3;
                    object3 = object;
                    break block10;
                    catch (Throwable throwable) {
                        object2 = null;
                        break block9;
                    }
                    catch (IOException iOException) {
                        object = null;
                    }
                }
                object2 = object;
                try {
                    void var3_9;
                    Log.e((String)TAG, (String)"Unable to deserialize controller from disk", (Throwable)var3_9);
                    object2 = var5_5;
                    if (object == null) return object2;
                    object3 = var4_4;
                    object2 = object;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            Utility.closeQuietly((Closeable)object2);
            return object3;
        }
        if (object2 == null) throw var0_3;
        Utility.closeQuietly((Closeable)object2);
        throw var0_3;
    }

    private static LikeActionController deserializeFromJson(String object) {
        block5 : {
            JSONObject jSONObject;
            block4 : {
                try {
                    jSONObject = new JSONObject((String)object);
                    if (jSONObject.optInt(JSON_INT_VERSION_KEY, -1) == 3) break block4;
                    return null;
                }
                catch (JSONException jSONException) {
                    Log.e((String)TAG, (String)"Unable to deserialize controller from JSON", (Throwable)jSONException);
                    return null;
                }
            }
            object = new LikeActionController(jSONObject.getString(JSON_STRING_OBJECT_ID_KEY), LikeView.ObjectType.fromInt(jSONObject.optInt(JSON_INT_OBJECT_TYPE_KEY, LikeView.ObjectType.UNKNOWN.getValue())));
            object.likeCountStringWithLike = jSONObject.optString(JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY, null);
            object.likeCountStringWithoutLike = jSONObject.optString(JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY, null);
            object.socialSentenceWithLike = jSONObject.optString(JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, null);
            object.socialSentenceWithoutLike = jSONObject.optString(JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, null);
            object.isObjectLiked = jSONObject.optBoolean(JSON_BOOL_IS_OBJECT_LIKED_KEY);
            object.unlikeToken = jSONObject.optString("unlike_token", null);
            jSONObject = jSONObject.optJSONObject(JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE);
            if (jSONObject == null) break block5;
            object.facebookDialogAnalyticsBundle = BundleJSONConverter.convertToBundle(jSONObject);
        }
        return object;
    }

    private void fetchVerifiedObjectId(final RequestCompletionCallback requestCompletionCallback) {
        if (!Utility.isNullOrEmpty(this.verifiedObjectId)) {
            if (requestCompletionCallback != null) {
                requestCompletionCallback.onComplete();
            }
            return;
        }
        final GetOGObjectIdRequestWrapper getOGObjectIdRequestWrapper = new GetOGObjectIdRequestWrapper(this.objectId, this.objectType);
        final GetPageIdRequestWrapper getPageIdRequestWrapper = new GetPageIdRequestWrapper(this.objectId, this.objectType);
        GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
        getOGObjectIdRequestWrapper.addToBatch(graphRequestBatch);
        getPageIdRequestWrapper.addToBatch(graphRequestBatch);
        graphRequestBatch.addCallback(new GraphRequestBatch.Callback(){

            @Override
            public void onBatchCompleted(GraphRequestBatch object) {
                LikeActionController.this.verifiedObjectId = getOGObjectIdRequestWrapper.verifiedObjectId;
                if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                    LikeActionController.this.verifiedObjectId = getPageIdRequestWrapper.verifiedObjectId;
                    LikeActionController.this.objectIsPage = getPageIdRequestWrapper.objectIsPage;
                }
                if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                    Logger.log(LoggingBehavior.DEVELOPER_ERRORS, TAG, "Unable to verify the FB id for '%s'. Verify that it is a valid FB object or page", LikeActionController.this.objectId);
                    LikeActionController likeActionController = LikeActionController.this;
                    object = getPageIdRequestWrapper.getError() != null ? getPageIdRequestWrapper.getError() : getOGObjectIdRequestWrapper.getError();
                    likeActionController.logAppEventForError("get_verified_id", (FacebookRequestError)object);
                }
                if (requestCompletionCallback != null) {
                    requestCompletionCallback.onComplete();
                }
            }
        });
        graphRequestBatch.executeAsync();
    }

    private AppEventsLogger getAppEventsLogger() {
        if (this.appEventsLogger == null) {
            this.appEventsLogger = AppEventsLogger.newLogger(FacebookSdk.getApplicationContext());
        }
        return this.appEventsLogger;
    }

    private static String getCacheKeyForObjectId(String string) {
        Object object = AccessToken.getCurrentAccessToken();
        object = object != null ? object.getToken() : null;
        Object object2 = object;
        if (object != null) {
            object2 = Utility.md5hash((String)object);
        }
        return String.format(Locale.ROOT, "%s|%s|com.fb.sdk.like|%d", string, Utility.coerceValueIfNullOrEmpty((String)object2, ""), objectSuffix);
    }

    @Deprecated
    public static void getControllerForObjectId(String string, LikeView.ObjectType objectType, CreationCallback creationCallback) {
        LikeActionController likeActionController;
        if (!isInitialized) {
            LikeActionController.performFirstInitialize();
        }
        if ((likeActionController = LikeActionController.getControllerFromInMemoryCache(string)) != null) {
            LikeActionController.verifyControllerAndInvokeCallback(likeActionController, objectType, creationCallback);
            return;
        }
        diskIOWorkQueue.addActiveWorkItem(new CreateLikeActionControllerWorkItem(string, objectType, creationCallback));
    }

    private static LikeActionController getControllerFromInMemoryCache(String string) {
        LikeActionController likeActionController = cache.get(string = LikeActionController.getCacheKeyForObjectId(string));
        if (likeActionController != null) {
            mruCacheWorkQueue.addActiveWorkItem(new MRUCacheWorkItem(string, false));
        }
        return likeActionController;
    }

    private ResultProcessor getResultProcessor(final Bundle bundle) {
        return new ResultProcessor(null){

            @Override
            public void onCancel(AppCall appCall) {
                this.onError(appCall, new FacebookOperationCanceledException());
            }

            @Override
            public void onError(AppCall appCall, FacebookException facebookException) {
                Logger.log(LoggingBehavior.REQUESTS, TAG, "Like Dialog failed with error : %s", facebookException);
                Bundle bundle2 = bundle == null ? new Bundle() : bundle;
                bundle2.putString("call_id", appCall.getCallId().toString());
                LikeActionController.this.logAppEventForError("present_dialog", bundle2);
                LikeActionController.broadcastAction(LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, NativeProtocol.createBundleForException(facebookException));
            }

            @Override
            public void onSuccess(AppCall appCall, Bundle object) {
                if (object != null) {
                    if (!object.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY)) {
                        return;
                    }
                    boolean bl = object.getBoolean(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY);
                    String string = LikeActionController.this.likeCountStringWithLike;
                    String string2 = LikeActionController.this.likeCountStringWithoutLike;
                    if (object.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY)) {
                        string2 = string = object.getString(LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY);
                    }
                    String string3 = LikeActionController.this.socialSentenceWithLike;
                    String string4 = LikeActionController.this.socialSentenceWithoutLike;
                    if (object.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY)) {
                        string4 = string3 = object.getString(LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY);
                    }
                    object = object.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY) ? object.getString("unlike_token") : LikeActionController.this.unlikeToken;
                    Bundle bundle2 = bundle == null ? new Bundle() : bundle;
                    bundle2.putString("call_id", appCall.getCallId().toString());
                    LikeActionController.this.getAppEventsLogger().logSdkEvent("fb_like_control_dialog_did_succeed", null, bundle2);
                    LikeActionController.this.updateState(bl, string, string2, string3, string4, (String)object);
                    return;
                }
            }
        };
    }

    @Deprecated
    public static boolean handleOnActivityResult(final int n, final int n2, final Intent intent) {
        if (Utility.isNullOrEmpty(objectIdForPendingController)) {
            objectIdForPendingController = FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).getString(LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY, null);
        }
        if (Utility.isNullOrEmpty(objectIdForPendingController)) {
            return false;
        }
        LikeActionController.getControllerForObjectId(objectIdForPendingController, LikeView.ObjectType.UNKNOWN, new CreationCallback(){

            @Override
            public void onComplete(LikeActionController likeActionController, FacebookException facebookException) {
                if (facebookException == null) {
                    likeActionController.onActivityResult(n, n2, intent);
                    return;
                }
                Utility.logd(TAG, facebookException);
            }
        });
        return true;
    }

    private static void invokeCallbackWithController(final CreationCallback creationCallback, final LikeActionController likeActionController, final FacebookException facebookException) {
        if (creationCallback == null) {
            return;
        }
        handler.post(new Runnable(){

            @Override
            public void run() {
                creationCallback.onComplete(likeActionController, facebookException);
            }
        });
    }

    private void logAppEventForError(String string, Bundle bundle) {
        bundle = new Bundle(bundle);
        bundle.putString(JSON_STRING_OBJECT_ID_KEY, this.objectId);
        bundle.putString(JSON_INT_OBJECT_TYPE_KEY, this.objectType.toString());
        bundle.putString("current_action", string);
        this.getAppEventsLogger().logSdkEvent("fb_like_control_error", null, bundle);
    }

    private void logAppEventForError(String string, FacebookRequestError facebookRequestError) {
        Bundle bundle = new Bundle();
        if (facebookRequestError != null && (facebookRequestError = facebookRequestError.getRequestResult()) != null) {
            bundle.putString("error", facebookRequestError.toString());
        }
        this.logAppEventForError(string, bundle);
    }

    private void onActivityResult(int n, int n2, Intent intent) {
        ShareInternalUtility.handleActivityResult(n, n2, intent, this.getResultProcessor(this.facebookDialogAnalyticsBundle));
        this.clearState();
    }

    private static void performFirstInitialize() {
        synchronized (LikeActionController.class) {
            block5 : {
                boolean bl = isInitialized;
                if (!bl) break block5;
                return;
            }
            handler = new Handler(Looper.getMainLooper());
            objectSuffix = FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).getInt(LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, 1);
            controllerDiskCache = new FileLruCache(TAG, new FileLruCache.Limits());
            LikeActionController.registerAccessTokenTracker();
            CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode(), new CallbackManagerImpl.Callback(){

                @Override
                public boolean onActivityResult(int n, Intent intent) {
                    return LikeActionController.handleOnActivityResult(CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode(), n, intent);
                }
            });
            isInitialized = true;
            return;
        }
    }

    private void presentLikeDialog(Activity activity, FragmentWrapper fragmentWrapper, Bundle bundle) {
        Object object;
        if (LikeDialog.canShowNativeDialog()) {
            object = "fb_like_control_did_present_dialog";
        } else if (LikeDialog.canShowWebFallback()) {
            object = "fb_like_control_did_present_fallback_dialog";
        } else {
            this.logAppEventForError("present_dialog", bundle);
            Utility.logd(TAG, "Cannot show the Like Dialog on this device.");
            LikeActionController.broadcastAction(null, ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
            object = null;
        }
        if (object != null) {
            object = this.objectType != null ? this.objectType.toString() : LikeView.ObjectType.UNKNOWN.toString();
            object = new LikeContent.Builder().setObjectId(this.objectId).setObjectType((String)object).build();
            if (fragmentWrapper != null) {
                new LikeDialog(fragmentWrapper).show((LikeContent)object);
            } else {
                new LikeDialog(activity).show((LikeContent)object);
            }
            this.saveState(bundle);
            this.getAppEventsLogger().logSdkEvent("fb_like_control_did_present_dialog", null, bundle);
        }
    }

    private void publishAgainIfNeeded(Bundle bundle) {
        if (this.isObjectLiked != this.isObjectLikedOnServer && !this.publishLikeOrUnlikeAsync(this.isObjectLiked, bundle)) {
            this.publishDidError(this.isObjectLiked ^ true);
        }
    }

    private void publishDidError(boolean bl) {
        this.updateLikeState(bl);
        Bundle bundle = new Bundle();
        bundle.putString("com.facebook.platform.status.ERROR_DESCRIPTION", ERROR_PUBLISH_ERROR);
        LikeActionController.broadcastAction(this, ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, bundle);
    }

    private void publishLikeAsync(final Bundle bundle) {
        this.isPendingLikeOrUnlike = true;
        this.fetchVerifiedObjectId(new RequestCompletionCallback(){

            @Override
            public void onComplete() {
                if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("com.facebook.platform.status.ERROR_DESCRIPTION", LikeActionController.ERROR_INVALID_OBJECT_ID);
                    LikeActionController.broadcastAction(LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, bundle2);
                    return;
                }
                GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
                final PublishLikeRequestWrapper publishLikeRequestWrapper = new PublishLikeRequestWrapper(LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType);
                publishLikeRequestWrapper.addToBatch(graphRequestBatch);
                graphRequestBatch.addCallback(new GraphRequestBatch.Callback(){

                    @Override
                    public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
                        LikeActionController.this.isPendingLikeOrUnlike = false;
                        if (publishLikeRequestWrapper.getError() != null) {
                            LikeActionController.this.publishDidError(false);
                            return;
                        }
                        LikeActionController.this.unlikeToken = Utility.coerceValueIfNullOrEmpty(publishLikeRequestWrapper.unlikeToken, null);
                        LikeActionController.this.isObjectLikedOnServer = true;
                        LikeActionController.this.getAppEventsLogger().logSdkEvent("fb_like_control_did_like", null, bundle);
                        LikeActionController.this.publishAgainIfNeeded(bundle);
                    }
                });
                graphRequestBatch.executeAsync();
            }

        });
    }

    private boolean publishLikeOrUnlikeAsync(boolean bl, Bundle bundle) {
        if (this.canUseOGPublish()) {
            if (bl) {
                this.publishLikeAsync(bundle);
                return true;
            }
            if (!Utility.isNullOrEmpty(this.unlikeToken)) {
                this.publishUnlikeAsync(bundle);
                return true;
            }
        }
        return false;
    }

    private void publishUnlikeAsync(final Bundle bundle) {
        this.isPendingLikeOrUnlike = true;
        GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
        final PublishUnlikeRequestWrapper publishUnlikeRequestWrapper = new PublishUnlikeRequestWrapper(this.unlikeToken);
        publishUnlikeRequestWrapper.addToBatch(graphRequestBatch);
        graphRequestBatch.addCallback(new GraphRequestBatch.Callback(){

            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
                LikeActionController.this.isPendingLikeOrUnlike = false;
                if (publishUnlikeRequestWrapper.getError() != null) {
                    LikeActionController.this.publishDidError(true);
                    return;
                }
                LikeActionController.this.unlikeToken = null;
                LikeActionController.this.isObjectLikedOnServer = false;
                LikeActionController.this.getAppEventsLogger().logSdkEvent("fb_like_control_did_unlike", null, bundle);
                LikeActionController.this.publishAgainIfNeeded(bundle);
            }
        });
        graphRequestBatch.executeAsync();
    }

    private static void putControllerInMemoryCache(String string, LikeActionController likeActionController) {
        string = LikeActionController.getCacheKeyForObjectId(string);
        mruCacheWorkQueue.addActiveWorkItem(new MRUCacheWorkItem(string, true));
        cache.put(string, likeActionController);
    }

    private void refreshStatusAsync() {
        if (AccessToken.getCurrentAccessToken() == null) {
            this.refreshStatusViaService();
            return;
        }
        this.fetchVerifiedObjectId(new RequestCompletionCallback(){

            @Override
            public void onComplete() {
                AbstractRequestWrapper abstractRequestWrapper = .$SwitchMap$com$facebook$share$widget$LikeView$ObjectType[LikeActionController.this.objectType.ordinal()] != 1 ? new GetOGObjectLikesRequestWrapper(LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType) : new GetPageLikesRequestWrapper(LikeActionController.this.verifiedObjectId);
                GetEngagementRequestWrapper getEngagementRequestWrapper = new GetEngagementRequestWrapper(LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType);
                GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
                abstractRequestWrapper.addToBatch(graphRequestBatch);
                getEngagementRequestWrapper.addToBatch(graphRequestBatch);
                graphRequestBatch.addCallback(new GraphRequestBatch.Callback((LikeRequestWrapper)((Object)abstractRequestWrapper), getEngagementRequestWrapper){
                    final /* synthetic */ GetEngagementRequestWrapper val$engagementRequest;
                    final /* synthetic */ LikeRequestWrapper val$likeRequestWrapper;
                    {
                        this.val$likeRequestWrapper = likeRequestWrapper;
                        this.val$engagementRequest = getEngagementRequestWrapper;
                    }

                    @Override
                    public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
                        if (this.val$likeRequestWrapper.getError() == null && this.val$engagementRequest.getError() == null) {
                            LikeActionController.this.updateState(this.val$likeRequestWrapper.isObjectLiked(), this.val$engagementRequest.likeCountStringWithLike, this.val$engagementRequest.likeCountStringWithoutLike, this.val$engagementRequest.socialSentenceStringWithLike, this.val$engagementRequest.socialSentenceStringWithoutLike, this.val$likeRequestWrapper.getUnlikeToken());
                            return;
                        }
                        Logger.log(LoggingBehavior.REQUESTS, TAG, "Unable to refresh like state for id: '%s'", LikeActionController.this.objectId);
                    }
                });
                graphRequestBatch.executeAsync();
            }

        });
    }

    private void refreshStatusViaService() {
        LikeStatusClient likeStatusClient = new LikeStatusClient(FacebookSdk.getApplicationContext(), FacebookSdk.getApplicationId(), this.objectId);
        if (!likeStatusClient.start()) {
            return;
        }
        likeStatusClient.setCompletedListener(new PlatformServiceClient.CompletedListener(){

            @Override
            public void completed(Bundle object) {
                if (object != null) {
                    if (!object.containsKey("com.facebook.platform.extra.OBJECT_IS_LIKED")) {
                        return;
                    }
                    boolean bl = object.getBoolean("com.facebook.platform.extra.OBJECT_IS_LIKED");
                    String string = object.containsKey("com.facebook.platform.extra.LIKE_COUNT_STRING_WITH_LIKE") ? object.getString("com.facebook.platform.extra.LIKE_COUNT_STRING_WITH_LIKE") : LikeActionController.this.likeCountStringWithLike;
                    String string2 = object.containsKey("com.facebook.platform.extra.LIKE_COUNT_STRING_WITHOUT_LIKE") ? object.getString("com.facebook.platform.extra.LIKE_COUNT_STRING_WITHOUT_LIKE") : LikeActionController.this.likeCountStringWithoutLike;
                    String string3 = object.containsKey("com.facebook.platform.extra.SOCIAL_SENTENCE_WITH_LIKE") ? object.getString("com.facebook.platform.extra.SOCIAL_SENTENCE_WITH_LIKE") : LikeActionController.this.socialSentenceWithLike;
                    String string4 = object.containsKey("com.facebook.platform.extra.SOCIAL_SENTENCE_WITHOUT_LIKE") ? object.getString("com.facebook.platform.extra.SOCIAL_SENTENCE_WITHOUT_LIKE") : LikeActionController.this.socialSentenceWithoutLike;
                    object = object.containsKey("com.facebook.platform.extra.UNLIKE_TOKEN") ? object.getString("com.facebook.platform.extra.UNLIKE_TOKEN") : LikeActionController.this.unlikeToken;
                    LikeActionController.this.updateState(bl, string, string2, string3, string4, (String)object);
                    return;
                }
            }
        });
    }

    private static void registerAccessTokenTracker() {
        accessTokenTracker = new AccessTokenTracker(){

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                accessToken = FacebookSdk.getApplicationContext();
                if (accessToken2 == null) {
                    objectSuffix = (objectSuffix + 1) % 1000;
                    accessToken.getSharedPreferences(LikeActionController.LIKE_ACTION_CONTROLLER_STORE, 0).edit().putInt(LikeActionController.LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, objectSuffix).apply();
                    cache.clear();
                    controllerDiskCache.clearCache();
                }
                LikeActionController.broadcastAction(null, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
            }
        };
    }

    private void saveState(Bundle bundle) {
        LikeActionController.storeObjectIdForPendingController(this.objectId);
        this.facebookDialogAnalyticsBundle = bundle;
        LikeActionController.serializeToDiskAsync(this);
    }

    private static void serializeToDiskAsync(LikeActionController object) {
        String string = LikeActionController.serializeToJson((LikeActionController)object);
        object = LikeActionController.getCacheKeyForObjectId(object.objectId);
        if (!Utility.isNullOrEmpty(string) && !Utility.isNullOrEmpty((String)object)) {
            diskIOWorkQueue.addActiveWorkItem(new SerializeToDiskWorkItem((String)object, string));
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void serializeToDiskSynchronously(String object, String string) {
        Object object2;
        block7 : {
            block8 : {
                Object var3_7 = null;
                object2 = null;
                object = controllerDiskCache.openPutStream((String)object);
                try {
                    object.write(string.getBytes());
                    if (object == null) return;
                }
                catch (Throwable throwable) {
                    object2 = object;
                    object = throwable;
                    break block7;
                }
                catch (IOException iOException) {
                    break block8;
                }
                Utility.closeQuietly((Closeable)object);
                return;
                catch (Throwable throwable) {
                    break block7;
                }
                catch (IOException iOException) {
                    object = var3_7;
                }
            }
            object2 = object;
            {
                void var1_6;
                Log.e((String)TAG, (String)"Unable to serialize controller to disk", (Throwable)var1_6);
                if (object == null) return;
            }
            Utility.closeQuietly((Closeable)object);
            return;
        }
        if (object2 == null) throw object;
        Utility.closeQuietly((Closeable)object2);
        throw object;
    }

    private static String serializeToJson(LikeActionController likeActionController) {
        JSONObject jSONObject;
        block3 : {
            jSONObject = new JSONObject();
            try {
                jSONObject.put(JSON_INT_VERSION_KEY, 3);
                jSONObject.put(JSON_STRING_OBJECT_ID_KEY, (Object)likeActionController.objectId);
                jSONObject.put(JSON_INT_OBJECT_TYPE_KEY, likeActionController.objectType.getValue());
                jSONObject.put(JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY, (Object)likeActionController.likeCountStringWithLike);
                jSONObject.put(JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY, (Object)likeActionController.likeCountStringWithoutLike);
                jSONObject.put(JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, (Object)likeActionController.socialSentenceWithLike);
                jSONObject.put(JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, (Object)likeActionController.socialSentenceWithoutLike);
                jSONObject.put(JSON_BOOL_IS_OBJECT_LIKED_KEY, likeActionController.isObjectLiked);
                jSONObject.put("unlike_token", (Object)likeActionController.unlikeToken);
                if (likeActionController.facebookDialogAnalyticsBundle == null || (likeActionController = BundleJSONConverter.convertToJSON(likeActionController.facebookDialogAnalyticsBundle)) == null) break block3;
            }
            catch (JSONException jSONException) {
                Log.e((String)TAG, (String)"Unable to serialize controller to JSON", (Throwable)jSONException);
                return null;
            }
            jSONObject.put(JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE, (Object)likeActionController);
        }
        return jSONObject.toString();
    }

    private static void storeObjectIdForPendingController(String string) {
        objectIdForPendingController = string;
        FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).edit().putString(LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY, objectIdForPendingController).apply();
    }

    private void updateLikeState(boolean bl) {
        this.updateState(bl, this.likeCountStringWithLike, this.likeCountStringWithoutLike, this.socialSentenceWithLike, this.socialSentenceWithoutLike, this.unlikeToken);
    }

    private void updateState(boolean bl, String string, String string2, String string3, String string4, String string5) {
        string = Utility.coerceValueIfNullOrEmpty(string, null);
        string2 = Utility.coerceValueIfNullOrEmpty(string2, null);
        string3 = Utility.coerceValueIfNullOrEmpty(string3, null);
        string4 = Utility.coerceValueIfNullOrEmpty(string4, null);
        string5 = Utility.coerceValueIfNullOrEmpty(string5, null);
        boolean bl2 = !(bl == this.isObjectLiked && Utility.areObjectsEqual(string, this.likeCountStringWithLike) && Utility.areObjectsEqual(string2, this.likeCountStringWithoutLike) && Utility.areObjectsEqual(string3, this.socialSentenceWithLike) && Utility.areObjectsEqual(string4, this.socialSentenceWithoutLike) && Utility.areObjectsEqual(string5, this.unlikeToken));
        if (!bl2) {
            return;
        }
        this.isObjectLiked = bl;
        this.likeCountStringWithLike = string;
        this.likeCountStringWithoutLike = string2;
        this.socialSentenceWithLike = string3;
        this.socialSentenceWithoutLike = string4;
        this.unlikeToken = string5;
        LikeActionController.serializeToDiskAsync(this);
        LikeActionController.broadcastAction(this, ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
    }

    private static void verifyControllerAndInvokeCallback(LikeActionController object, LikeView.ObjectType object2, CreationCallback creationCallback) {
        LikeView.ObjectType objectType = ShareInternalUtility.getMostSpecificObjectType((LikeView.ObjectType)((Object)object2), object.objectType);
        if (objectType == null) {
            object = new FacebookException("Object with id:\"%s\" is already marked as type:\"%s\". Cannot change the type to:\"%s\"", object.objectId, object.objectType.toString(), object2.toString());
            object2 = null;
        } else {
            object.objectType = objectType;
            objectType = null;
            object2 = object;
            object = objectType;
        }
        LikeActionController.invokeCallbackWithController(creationCallback, (LikeActionController)object2, (FacebookException)object);
    }

    @Deprecated
    public String getLikeCountString() {
        if (this.isObjectLiked) {
            return this.likeCountStringWithLike;
        }
        return this.likeCountStringWithoutLike;
    }

    @Deprecated
    public String getObjectId() {
        return this.objectId;
    }

    @Deprecated
    public String getSocialSentence() {
        if (this.isObjectLiked) {
            return this.socialSentenceWithLike;
        }
        return this.socialSentenceWithoutLike;
    }

    @Deprecated
    public boolean isObjectLiked() {
        return this.isObjectLiked;
    }

    @Deprecated
    public boolean shouldEnableView() {
        return false;
    }

    @Deprecated
    public void toggleLike(Activity activity, FragmentWrapper fragmentWrapper, Bundle bundle) {
        boolean bl = this.isObjectLiked;
        boolean bl2 = true;
        bl ^= true;
        if (this.canUseOGPublish()) {
            this.updateLikeState(bl);
            if (this.isPendingLikeOrUnlike) {
                this.getAppEventsLogger().logSdkEvent("fb_like_control_did_undo_quickly", null, bundle);
                return;
            }
            if (!this.publishLikeOrUnlikeAsync(bl, bundle)) {
                if (bl) {
                    bl2 = false;
                }
                this.updateLikeState(bl2);
                this.presentLikeDialog(activity, fragmentWrapper, bundle);
                return;
            }
        } else {
            this.presentLikeDialog(activity, fragmentWrapper, bundle);
        }
    }

    private abstract class AbstractRequestWrapper
    implements RequestWrapper {
        protected FacebookRequestError error;
        protected String objectId;
        protected LikeView.ObjectType objectType;
        private GraphRequest request;

        protected AbstractRequestWrapper(String string, LikeView.ObjectType objectType) {
            this.objectId = string;
            this.objectType = objectType;
        }

        @Override
        public void addToBatch(GraphRequestBatch graphRequestBatch) {
            graphRequestBatch.add(this.request);
        }

        @Override
        public FacebookRequestError getError() {
            return this.error;
        }

        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, TAG, "Error running request for object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
        }

        protected abstract void processSuccess(GraphResponse var1);

        protected void setRequest(GraphRequest graphRequest) {
            this.request = graphRequest;
            graphRequest.setVersion(FacebookSdk.getGraphApiVersion());
            graphRequest.setCallback(new GraphRequest.Callback(){

                @Override
                public void onCompleted(GraphResponse graphResponse) {
                    AbstractRequestWrapper.this.error = graphResponse.getError();
                    if (AbstractRequestWrapper.this.error != null) {
                        AbstractRequestWrapper.this.processError(AbstractRequestWrapper.this.error);
                        return;
                    }
                    AbstractRequestWrapper.this.processSuccess(graphResponse);
                }
            });
        }

    }

    private static class CreateLikeActionControllerWorkItem
    implements Runnable {
        private CreationCallback callback;
        private String objectId;
        private LikeView.ObjectType objectType;

        CreateLikeActionControllerWorkItem(String string, LikeView.ObjectType objectType, CreationCallback creationCallback) {
            this.objectId = string;
            this.objectType = objectType;
            this.callback = creationCallback;
        }

        @Override
        public void run() {
            LikeActionController.createControllerForObjectIdAndType(this.objectId, this.objectType, this.callback);
        }
    }

    @Deprecated
    public static interface CreationCallback {
        public void onComplete(LikeActionController var1, FacebookException var2);
    }

    private class GetEngagementRequestWrapper
    extends AbstractRequestWrapper {
        String likeCountStringWithLike;
        String likeCountStringWithoutLike;
        String socialSentenceStringWithLike;
        String socialSentenceStringWithoutLike;

        GetEngagementRequestWrapper(String string, LikeView.ObjectType objectType) {
            super(string, objectType);
            this.likeCountStringWithLike = LikeActionController.this.likeCountStringWithLike;
            this.likeCountStringWithoutLike = LikeActionController.this.likeCountStringWithoutLike;
            this.socialSentenceStringWithLike = LikeActionController.this.socialSentenceWithLike;
            this.socialSentenceStringWithoutLike = LikeActionController.this.socialSentenceWithoutLike;
            LikeActionController.this = new Bundle();
            LikeActionController.this.putString("fields", "engagement.fields(count_string_with_like,count_string_without_like,social_sentence_with_like,social_sentence_without_like)");
            LikeActionController.this.putString("locale", Locale.getDefault().toString());
            this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), string, (Bundle)LikeActionController.this, HttpMethod.GET));
        }

        @Override
        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, TAG, "Error fetching engagement for object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
            LikeActionController.this.logAppEventForError("get_engagement", facebookRequestError);
        }

        @Override
        protected void processSuccess(GraphResponse graphResponse) {
            if ((graphResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), "engagement")) != null) {
                this.likeCountStringWithLike = graphResponse.optString("count_string_with_like", this.likeCountStringWithLike);
                this.likeCountStringWithoutLike = graphResponse.optString("count_string_without_like", this.likeCountStringWithoutLike);
                this.socialSentenceStringWithLike = graphResponse.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, this.socialSentenceStringWithLike);
                this.socialSentenceStringWithoutLike = graphResponse.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, this.socialSentenceStringWithoutLike);
            }
        }
    }

    private class GetOGObjectIdRequestWrapper
    extends AbstractRequestWrapper {
        String verifiedObjectId;

        GetOGObjectIdRequestWrapper(String string, LikeView.ObjectType objectType) {
            super(string, objectType);
            LikeActionController.this = new Bundle();
            LikeActionController.this.putString("fields", "og_object.fields(id)");
            LikeActionController.this.putString("ids", string);
            this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "", (Bundle)LikeActionController.this, HttpMethod.GET));
        }

        @Override
        protected void processError(FacebookRequestError facebookRequestError) {
            if (facebookRequestError.getErrorMessage().contains("og_object")) {
                this.error = null;
                return;
            }
            Logger.log(LoggingBehavior.REQUESTS, TAG, "Error getting the FB id for object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
        }

        @Override
        protected void processSuccess(GraphResponse graphResponse) {
            if ((graphResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), this.objectId)) != null && (graphResponse = graphResponse.optJSONObject("og_object")) != null) {
                this.verifiedObjectId = graphResponse.optString("id");
            }
        }
    }

    private class GetOGObjectLikesRequestWrapper
    extends AbstractRequestWrapper
    implements LikeRequestWrapper {
        private final String objectId;
        private boolean objectIsLiked;
        private final LikeView.ObjectType objectType;
        private String unlikeToken;

        GetOGObjectLikesRequestWrapper(String string, LikeView.ObjectType objectType) {
            super(string, objectType);
            this.objectIsLiked = LikeActionController.this.isObjectLiked;
            this.objectId = string;
            this.objectType = objectType;
            LikeActionController.this = new Bundle();
            LikeActionController.this.putString("fields", "id,application");
            LikeActionController.this.putString("object", this.objectId);
            this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/og.likes", (Bundle)LikeActionController.this, HttpMethod.GET));
        }

        @Override
        public String getUnlikeToken() {
            return this.unlikeToken;
        }

        @Override
        public boolean isObjectLiked() {
            return this.objectIsLiked;
        }

        @Override
        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, TAG, "Error fetching like status for object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
            LikeActionController.this.logAppEventForError("get_og_object_like", facebookRequestError);
        }

        @Override
        protected void processSuccess(GraphResponse graphResponse) {
            if ((graphResponse = Utility.tryGetJSONArrayFromResponse(graphResponse.getJSONObject(), "data")) != null) {
                for (int i = 0; i < graphResponse.length(); ++i) {
                    JSONObject jSONObject = graphResponse.optJSONObject(i);
                    if (jSONObject == null) continue;
                    this.objectIsLiked = true;
                    JSONObject jSONObject2 = jSONObject.optJSONObject("application");
                    AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    if (jSONObject2 == null || accessToken == null || !Utility.areObjectsEqual(accessToken.getApplicationId(), jSONObject2.optString("id"))) continue;
                    this.unlikeToken = jSONObject.optString("id");
                }
            }
        }
    }

    private class GetPageIdRequestWrapper
    extends AbstractRequestWrapper {
        boolean objectIsPage;
        String verifiedObjectId;

        GetPageIdRequestWrapper(String string, LikeView.ObjectType objectType) {
            super(string, objectType);
            LikeActionController.this = new Bundle();
            LikeActionController.this.putString("fields", "id");
            LikeActionController.this.putString("ids", string);
            this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "", (Bundle)LikeActionController.this, HttpMethod.GET));
        }

        @Override
        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, TAG, "Error getting the FB id for object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
        }

        @Override
        protected void processSuccess(GraphResponse graphResponse) {
            if ((graphResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), this.objectId)) != null) {
                this.verifiedObjectId = graphResponse.optString("id");
                this.objectIsPage = Utility.isNullOrEmpty(this.verifiedObjectId) ^ true;
            }
        }
    }

    private class GetPageLikesRequestWrapper
    extends AbstractRequestWrapper
    implements LikeRequestWrapper {
        private boolean objectIsLiked;
        private String pageId;

        GetPageLikesRequestWrapper(String string) {
            super(string, LikeView.ObjectType.PAGE);
            this.objectIsLiked = LikeActionController.this.isObjectLiked;
            this.pageId = string;
            LikeActionController.this = new Bundle();
            LikeActionController.this.putString("fields", "id");
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("me/likes/");
            stringBuilder.append(string);
            this.setRequest(new GraphRequest(accessToken, stringBuilder.toString(), (Bundle)LikeActionController.this, HttpMethod.GET));
        }

        @Override
        public String getUnlikeToken() {
            return null;
        }

        @Override
        public boolean isObjectLiked() {
            return this.objectIsLiked;
        }

        @Override
        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, TAG, "Error fetching like status for page id '%s': %s", this.pageId, facebookRequestError);
            LikeActionController.this.logAppEventForError("get_page_like", facebookRequestError);
        }

        @Override
        protected void processSuccess(GraphResponse graphResponse) {
            if ((graphResponse = Utility.tryGetJSONArrayFromResponse(graphResponse.getJSONObject(), "data")) != null && graphResponse.length() > 0) {
                this.objectIsLiked = true;
            }
        }
    }

    private static interface LikeRequestWrapper
    extends RequestWrapper {
        public String getUnlikeToken();

        public boolean isObjectLiked();
    }

    private static class MRUCacheWorkItem
    implements Runnable {
        private static ArrayList<String> mruCachedItems = new ArrayList();
        private String cacheItem;
        private boolean shouldTrim;

        MRUCacheWorkItem(String string, boolean bl) {
            this.cacheItem = string;
            this.shouldTrim = bl;
        }

        @Override
        public void run() {
            if (this.cacheItem != null) {
                mruCachedItems.remove(this.cacheItem);
                mruCachedItems.add(0, this.cacheItem);
            }
            if (this.shouldTrim && mruCachedItems.size() >= 128) {
                while (64 < mruCachedItems.size()) {
                    String string = mruCachedItems.remove(mruCachedItems.size() - 1);
                    cache.remove(string);
                }
            }
        }
    }

    private class PublishLikeRequestWrapper
    extends AbstractRequestWrapper {
        String unlikeToken;

        PublishLikeRequestWrapper(String string, LikeView.ObjectType objectType) {
            super(string, objectType);
            LikeActionController.this = new Bundle();
            LikeActionController.this.putString("object", string);
            this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/og.likes", (Bundle)LikeActionController.this, HttpMethod.POST));
        }

        @Override
        protected void processError(FacebookRequestError facebookRequestError) {
            if (facebookRequestError.getErrorCode() == 3501) {
                this.error = null;
                return;
            }
            Logger.log(LoggingBehavior.REQUESTS, TAG, "Error liking object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
            LikeActionController.this.logAppEventForError("publish_like", facebookRequestError);
        }

        @Override
        protected void processSuccess(GraphResponse graphResponse) {
            this.unlikeToken = Utility.safeGetStringFromResponse(graphResponse.getJSONObject(), "id");
        }
    }

    private class PublishUnlikeRequestWrapper
    extends AbstractRequestWrapper {
        private String unlikeToken;

        PublishUnlikeRequestWrapper(String string) {
            super(null, null);
            this.unlikeToken = string;
            this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), string, null, HttpMethod.DELETE));
        }

        @Override
        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, TAG, "Error unliking object with unlike token '%s' : %s", this.unlikeToken, facebookRequestError);
            LikeActionController.this.logAppEventForError("publish_unlike", facebookRequestError);
        }

        @Override
        protected void processSuccess(GraphResponse graphResponse) {
        }
    }

    private static interface RequestCompletionCallback {
        public void onComplete();
    }

    private static interface RequestWrapper {
        public void addToBatch(GraphRequestBatch var1);

        public FacebookRequestError getError();
    }

    private static class SerializeToDiskWorkItem
    implements Runnable {
        private String cacheKey;
        private String controllerJson;

        SerializeToDiskWorkItem(String string, String string2) {
            this.cacheKey = string;
            this.controllerJson = string2;
        }

        @Override
        public void run() {
            LikeActionController.serializeToDiskSynchronously(this.cacheKey, this.controllerJson);
        }
    }

}
