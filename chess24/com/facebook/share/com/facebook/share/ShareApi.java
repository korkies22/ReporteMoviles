/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.Mutable;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.VideoUploader;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareApi {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_GRAPH_NODE = "me";
    private static final String GRAPH_PATH_FORMAT = "%s/%s";
    private static final String PHOTOS_EDGE = "photos";
    private static final String TAG = "ShareApi";
    private String graphNode;
    private String message;
    private final ShareContent shareContent;

    public ShareApi(ShareContent shareContent) {
        this.shareContent = shareContent;
        this.graphNode = DEFAULT_GRAPH_NODE;
    }

    private void addCommonParameters(Bundle bundle, ShareContent shareContent) {
        List<String> list = shareContent.getPeopleIds();
        if (!Utility.isNullOrEmpty(list)) {
            bundle.putString("tags", TextUtils.join((CharSequence)", ", list));
        }
        if (!Utility.isNullOrEmpty(shareContent.getPlaceId())) {
            bundle.putString("place", shareContent.getPlaceId());
        }
        if (!Utility.isNullOrEmpty(shareContent.getPageId())) {
            bundle.putString("page", shareContent.getPageId());
        }
        if (!Utility.isNullOrEmpty(shareContent.getRef())) {
            bundle.putString("ref", shareContent.getRef());
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getGraphPath(String string) {
        try {
            return String.format(Locale.ROOT, GRAPH_PATH_FORMAT, URLEncoder.encode(this.getGraphNode(), DEFAULT_CHARSET), string);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }

    private Bundle getSharePhotoCommonParameters(SharePhoto sharePhoto, SharePhotoContent sharePhotoContent) throws JSONException {
        Object object;
        if (!(sharePhoto = sharePhoto.getParameters()).containsKey("place") && !Utility.isNullOrEmpty(sharePhotoContent.getPlaceId())) {
            sharePhoto.putString("place", sharePhotoContent.getPlaceId());
        }
        if (!(sharePhoto.containsKey("tags") || Utility.isNullOrEmpty(sharePhotoContent.getPeopleIds()) || Utility.isNullOrEmpty(object = sharePhotoContent.getPeopleIds()))) {
            JSONArray jSONArray = new JSONArray();
            object = object.iterator();
            while (object.hasNext()) {
                String string = (String)object.next();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("tag_uid", (Object)string);
                jSONArray.put((Object)jSONObject);
            }
            sharePhoto.putString("tags", jSONArray.toString());
        }
        if (!sharePhoto.containsKey("ref") && !Utility.isNullOrEmpty(sharePhotoContent.getRef())) {
            sharePhoto.putString("ref", sharePhotoContent.getRef());
        }
        return sharePhoto;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void handleImagesOnAction(Bundle bundle) {
        JSONArray jSONArray;
        int n;
        block8 : {
            String string = bundle.getString("image");
            if (string == null) return;
            try {
                jSONArray = new JSONArray(string);
                n = 0;
                break block8;
            }
            catch (JSONException jSONException) {}
            try {
                ShareApi.putImageInBundleWithArrayFormat(bundle, 0, new JSONObject(string));
                bundle.remove("image");
                return;
            }
            catch (JSONException jSONException) {
                return;
            }
        }
        do {
            if (n >= jSONArray.length()) {
                bundle.remove("image");
                return;
            }
            JSONObject jSONObject = jSONArray.optJSONObject(n);
            if (jSONObject != null) {
                ShareApi.putImageInBundleWithArrayFormat(bundle, n, jSONObject);
            } else {
                String string = jSONArray.getString(n);
                bundle.putString(String.format(Locale.ROOT, "image[%d][url]", n), string);
            }
            ++n;
        } while (true);
    }

    private static void putImageInBundleWithArrayFormat(Bundle bundle, int n, JSONObject jSONObject) throws JSONException {
        Iterator iterator = jSONObject.keys();
        while (iterator.hasNext()) {
            String string = (String)iterator.next();
            bundle.putString(String.format(Locale.ROOT, "image[%d][%s]", n, string), jSONObject.get(string).toString());
        }
    }

    public static void share(ShareContent shareContent, FacebookCallback<Sharer.Result> facebookCallback) {
        new ShareApi(shareContent).share(facebookCallback);
    }

    private void shareLinkContent(ShareLinkContent shareLinkContent, FacebookCallback<Sharer.Result> object) {
        object = new GraphRequest.Callback((FacebookCallback)object){
            final /* synthetic */ FacebookCallback val$callback;
            {
                this.val$callback = facebookCallback;
            }

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                Object object = graphResponse.getJSONObject();
                object = object == null ? null : object.optString("id");
                ShareInternalUtility.invokeCallbackWithResults(this.val$callback, (String)object, graphResponse);
            }
        };
        Bundle bundle = new Bundle();
        this.addCommonParameters(bundle, shareLinkContent);
        bundle.putString("message", this.getMessage());
        bundle.putString("link", Utility.getUriString(shareLinkContent.getContentUrl()));
        bundle.putString("picture", Utility.getUriString(shareLinkContent.getImageUrl()));
        bundle.putString("name", shareLinkContent.getContentTitle());
        bundle.putString("description", shareLinkContent.getContentDescription());
        bundle.putString("ref", shareLinkContent.getRef());
        new GraphRequest(AccessToken.getCurrentAccessToken(), this.getGraphPath("feed"), bundle, HttpMethod.POST, (GraphRequest.Callback)object).executeAsync();
    }

    private void shareOpenGraphContent(ShareOpenGraphContent shareOpenGraphContent, final FacebookCallback<Sharer.Result> facebookCallback) {
        final GraphRequest.Callback callback = new GraphRequest.Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                Object object = graphResponse.getJSONObject();
                object = object == null ? null : object.optString("id");
                ShareInternalUtility.invokeCallbackWithResults(facebookCallback, (String)object, graphResponse);
            }
        };
        final ShareOpenGraphAction shareOpenGraphAction = shareOpenGraphContent.getAction();
        final Bundle bundle = shareOpenGraphAction.getBundle();
        this.addCommonParameters(bundle, shareOpenGraphContent);
        if (!Utility.isNullOrEmpty(this.getMessage())) {
            bundle.putString("message", this.getMessage());
        }
        this.stageOpenGraphAction(bundle, new CollectionMapper.OnMapperCompleteListener(){

            @Override
            public void onComplete() {
                try {
                    ShareApi.handleImagesOnAction(bundle);
                    new GraphRequest(AccessToken.getCurrentAccessToken(), ShareApi.this.getGraphPath(URLEncoder.encode(shareOpenGraphAction.getActionType(), ShareApi.DEFAULT_CHARSET)), bundle, HttpMethod.POST, callback).executeAsync();
                    return;
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    ShareInternalUtility.invokeCallbackWithException(facebookCallback, unsupportedEncodingException);
                    return;
                }
            }

            @Override
            public void onError(FacebookException facebookException) {
                ShareInternalUtility.invokeCallbackWithException(facebookCallback, facebookException);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sharePhotoContent(SharePhotoContent iterator, FacebookCallback<Sharer.Result> facebookCallback) {
        Mutable<Integer> mutable = new Mutable<Integer>(0);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        ArrayList<GraphRequest> arrayList = new ArrayList<GraphRequest>();
        GraphRequest.Callback callback = new GraphRequest.Callback(new ArrayList(), new ArrayList(), mutable, facebookCallback){
            final /* synthetic */ FacebookCallback val$callback;
            final /* synthetic */ ArrayList val$errorResponses;
            final /* synthetic */ Mutable val$requestCount;
            final /* synthetic */ ArrayList val$results;
            {
                this.val$results = arrayList;
                this.val$errorResponses = arrayList2;
                this.val$requestCount = mutable;
                this.val$callback = facebookCallback;
            }

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                Object object = graphResponse.getJSONObject();
                if (object != null) {
                    this.val$results.add(object);
                }
                if (graphResponse.getError() != null) {
                    this.val$errorResponses.add(graphResponse);
                }
                this.val$requestCount.value = (Integer)this.val$requestCount.value - 1;
                if ((Integer)this.val$requestCount.value == 0) {
                    if (!this.val$errorResponses.isEmpty()) {
                        ShareInternalUtility.invokeCallbackWithResults(this.val$callback, null, (GraphResponse)this.val$errorResponses.get(0));
                        return;
                    }
                    if (!this.val$results.isEmpty()) {
                        object = ((JSONObject)this.val$results.get(0)).optString("id");
                        ShareInternalUtility.invokeCallbackWithResults(this.val$callback, (String)object, graphResponse);
                    }
                }
            }
        };
        try {
            for (SharePhoto sharePhoto : iterator.getPhotos()) {
                String string;
                Bundle bundle;
                void var4_10;
                try {
                    bundle = this.getSharePhotoCommonParameters(sharePhoto, (SharePhotoContent)((Object)iterator));
                }
                catch (JSONException jSONException) {
                    ShareInternalUtility.invokeCallbackWithException(facebookCallback, (Exception)jSONException);
                    return;
                }
                Bitmap bitmap = sharePhoto.getBitmap();
                Uri uri = sharePhoto.getImageUrl();
                String string2 = string = sharePhoto.getCaption();
                if (string == null) {
                    String string3 = this.getMessage();
                }
                if (bitmap != null) {
                    arrayList.add(GraphRequest.newUploadPhotoRequest(accessToken, this.getGraphPath(PHOTOS_EDGE), bitmap, (String)var4_10, bundle, callback));
                    continue;
                }
                if (uri == null) continue;
                arrayList.add(GraphRequest.newUploadPhotoRequest(accessToken, this.getGraphPath(PHOTOS_EDGE), uri, (String)var4_10, bundle, callback));
            }
            mutable.value = (Integer)mutable.value + arrayList.size();
            iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                ((GraphRequest)iterator.next()).executeAsync();
            }
            return;
        }
        catch (FileNotFoundException fileNotFoundException) {
            ShareInternalUtility.invokeCallbackWithException(facebookCallback, fileNotFoundException);
        }
    }

    private void shareVideoContent(ShareVideoContent shareVideoContent, FacebookCallback<Sharer.Result> facebookCallback) {
        try {
            VideoUploader.uploadAsync(shareVideoContent, this.getGraphNode(), facebookCallback);
            return;
        }
        catch (FileNotFoundException fileNotFoundException) {
            ShareInternalUtility.invokeCallbackWithException(facebookCallback, fileNotFoundException);
            return;
        }
    }

    private void stageArrayList(final ArrayList arrayList, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        final JSONArray jSONArray = new JSONArray();
        this.stageCollectionValues(new CollectionMapper.Collection<Integer>(){

            @Override
            public Object get(Integer n) {
                return arrayList.get(n);
            }

            @Override
            public Iterator<Integer> keyIterator() {
                int n = arrayList.size();
                return new Iterator<Integer>(new Mutable<Integer>(0), n){
                    final /* synthetic */ Mutable val$current;
                    final /* synthetic */ int val$size;
                    {
                        this.val$current = mutable;
                        this.val$size = n;
                    }

                    @Override
                    public boolean hasNext() {
                        if ((Integer)this.val$current.value < this.val$size) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public Integer next() {
                        Integer n = (Integer)this.val$current.value;
                        Mutable mutable = this.val$current;
                        mutable.value = (Integer)mutable.value + 1;
                        return n;
                    }

                    @Override
                    public void remove() {
                    }
                };
            }

            @Override
            public void set(Integer n, Object object, CollectionMapper.OnErrorListener onErrorListener) {
                try {
                    jSONArray.put(n.intValue(), object);
                    return;
                }
                catch (JSONException jSONException) {
                    Object object2 = object = jSONException.getLocalizedMessage();
                    if (object == null) {
                        object2 = "Error staging object.";
                    }
                    onErrorListener.onError(new FacebookException((String)object2));
                    return;
                }
            }

        }, new CollectionMapper.OnMapperCompleteListener(){

            @Override
            public void onComplete() {
                onMapValueCompleteListener.onComplete((Object)jSONArray);
            }

            @Override
            public void onError(FacebookException facebookException) {
                onMapValueCompleteListener.onError(facebookException);
            }
        });
    }

    private <T> void stageCollectionValues(CollectionMapper.Collection<T> collection, CollectionMapper.OnMapperCompleteListener onMapperCompleteListener) {
        CollectionMapper.iterate(collection, new CollectionMapper.ValueMapper(){

            @Override
            public void mapValue(Object object, CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
                if (object instanceof ArrayList) {
                    ShareApi.this.stageArrayList((ArrayList)object, onMapValueCompleteListener);
                    return;
                }
                if (object instanceof ShareOpenGraphObject) {
                    ShareApi.this.stageOpenGraphObject((ShareOpenGraphObject)object, onMapValueCompleteListener);
                    return;
                }
                if (object instanceof SharePhoto) {
                    ShareApi.this.stagePhoto((SharePhoto)object, onMapValueCompleteListener);
                    return;
                }
                onMapValueCompleteListener.onComplete(object);
            }
        }, onMapperCompleteListener);
    }

    private void stageOpenGraphAction(final Bundle bundle, CollectionMapper.OnMapperCompleteListener onMapperCompleteListener) {
        this.stageCollectionValues(new CollectionMapper.Collection<String>(){

            @Override
            public Object get(String string) {
                return bundle.get(string);
            }

            @Override
            public Iterator<String> keyIterator() {
                return bundle.keySet().iterator();
            }

            @Override
            public void set(String charSequence, Object object, CollectionMapper.OnErrorListener onErrorListener) {
                if (!Utility.putJSONValueInBundle(bundle, (String)charSequence, object)) {
                    charSequence = new StringBuilder();
                    charSequence.append("Unexpected value: ");
                    charSequence.append(object.toString());
                    onErrorListener.onError(new FacebookException(charSequence.toString()));
                }
            }
        }, onMapperCompleteListener);
    }

    private void stageOpenGraphObject(final ShareOpenGraphObject shareOpenGraphObject, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        String string;
        String string2 = string = shareOpenGraphObject.getString("type");
        if (string == null) {
            string2 = shareOpenGraphObject.getString("og:type");
        }
        if (string2 == null) {
            onMapValueCompleteListener.onError(new FacebookException("Open Graph objects must contain a type value."));
            return;
        }
        string = new JSONObject();
        this.stageCollectionValues(new CollectionMapper.Collection<String>((JSONObject)string){
            final /* synthetic */ JSONObject val$stagedObject;
            {
                this.val$stagedObject = jSONObject;
            }

            @Override
            public Object get(String string) {
                return shareOpenGraphObject.get(string);
            }

            @Override
            public Iterator<String> keyIterator() {
                return shareOpenGraphObject.keySet().iterator();
            }

            @Override
            public void set(String string, Object object, CollectionMapper.OnErrorListener onErrorListener) {
                try {
                    this.val$stagedObject.put(string, object);
                    return;
                }
                catch (JSONException jSONException) {
                    Object object2 = object = jSONException.getLocalizedMessage();
                    if (object == null) {
                        object2 = "Error staging object.";
                    }
                    onErrorListener.onError(new FacebookException((String)object2));
                    return;
                }
            }
        }, new CollectionMapper.OnMapperCompleteListener((JSONObject)string, string2, new GraphRequest.Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                Object object = graphResponse.getError();
                if (object != null) {
                    String string = object.getErrorMessage();
                    object = string;
                    if (string == null) {
                        object = "Error staging Open Graph object.";
                    }
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, (String)object));
                    return;
                }
                object = graphResponse.getJSONObject();
                if (object == null) {
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, "Error staging Open Graph object."));
                    return;
                }
                if ((object = object.optString("id")) == null) {
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, "Error staging Open Graph object."));
                    return;
                }
                onMapValueCompleteListener.onComplete(object);
            }
        }, onMapValueCompleteListener){
            final /* synthetic */ String val$ogType;
            final /* synthetic */ CollectionMapper.OnMapValueCompleteListener val$onOpenGraphObjectStagedListener;
            final /* synthetic */ GraphRequest.Callback val$requestCallback;
            final /* synthetic */ JSONObject val$stagedObject;
            {
                this.val$stagedObject = jSONObject;
                this.val$ogType = string;
                this.val$requestCallback = callback;
                this.val$onOpenGraphObjectStagedListener = onMapValueCompleteListener;
            }

            @Override
            public void onComplete() {
                Object object = this.val$stagedObject.toString();
                Bundle bundle = new Bundle();
                bundle.putString("object", (String)object);
                try {
                    object = AccessToken.getCurrentAccessToken();
                    ShareApi shareApi = ShareApi.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("objects/");
                    stringBuilder.append(URLEncoder.encode(this.val$ogType, ShareApi.DEFAULT_CHARSET));
                    new GraphRequest((AccessToken)object, shareApi.getGraphPath(stringBuilder.toString()), bundle, HttpMethod.POST, this.val$requestCallback).executeAsync();
                    return;
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    Object object2 = object = unsupportedEncodingException.getLocalizedMessage();
                    if (object == null) {
                        object2 = "Error staging Open Graph object.";
                    }
                    this.val$onOpenGraphObjectStagedListener.onError(new FacebookException((String)object2));
                    return;
                }
            }

            @Override
            public void onError(FacebookException facebookException) {
                this.val$onOpenGraphObjectStagedListener.onError(facebookException);
            }
        });
    }

    private void stagePhoto(SharePhoto object, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        Object object2 = object.getBitmap();
        Uri uri = object.getImageUrl();
        if (object2 == null && uri == null) {
            onMapValueCompleteListener.onError(new FacebookException("Photos must have an imageURL or bitmap."));
            return;
        }
        object = new GraphRequest.Callback((SharePhoto)object){
            final /* synthetic */ SharePhoto val$photo;
            {
                this.val$photo = sharePhoto;
            }

            @Override
            public void onCompleted(GraphResponse object) {
                Object object2 = object.getError();
                if (object2 != null) {
                    String string = object2.getErrorMessage();
                    object2 = string;
                    if (string == null) {
                        object2 = "Error staging photo.";
                    }
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException((GraphResponse)object, (String)object2));
                    return;
                }
                if ((object = object.getJSONObject()) == null) {
                    onMapValueCompleteListener.onError(new FacebookException("Error staging photo."));
                    return;
                }
                if ((object = object.optString("uri")) == null) {
                    onMapValueCompleteListener.onError(new FacebookException("Error staging photo."));
                    return;
                }
                object2 = new JSONObject();
                try {
                    object2.put("url", object);
                    object2.put("user_generated", this.val$photo.getUserGenerated());
                }
                catch (JSONException jSONException) {
                    Object object3 = object2 = jSONException.getLocalizedMessage();
                    if (object2 == null) {
                        object3 = "Error staging photo.";
                    }
                    onMapValueCompleteListener.onError(new FacebookException((String)object3));
                    return;
                }
                onMapValueCompleteListener.onComplete(object2);
            }
        };
        if (object2 != null) {
            ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), object2, (GraphRequest.Callback)object).executeAsync();
            return;
        }
        try {
            ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), uri, (GraphRequest.Callback)object).executeAsync();
            return;
        }
        catch (FileNotFoundException fileNotFoundException) {
            Object object3 = object2 = fileNotFoundException.getLocalizedMessage();
            if (object2 == null) {
                object3 = "Error staging photo.";
            }
            onMapValueCompleteListener.onError(new FacebookException((String)object3));
            return;
        }
    }

    public boolean canShare() {
        if (this.getShareContent() == null) {
            return false;
        }
        Object object = AccessToken.getCurrentAccessToken();
        if (object == null) {
            return false;
        }
        if ((object = object.getPermissions()) == null || !object.contains("publish_actions")) {
            Log.w((String)TAG, (String)"The publish_actions permissions are missing, the share will fail unless this app was authorized to publish in another installation.");
        }
        return true;
    }

    public String getGraphNode() {
        return this.graphNode;
    }

    public String getMessage() {
        return this.message;
    }

    public ShareContent getShareContent() {
        return this.shareContent;
    }

    public void setGraphNode(String string) {
        this.graphNode = string;
    }

    public void setMessage(String string) {
        this.message = string;
    }

    public void share(FacebookCallback<Sharer.Result> facebookCallback) {
        if (!this.canShare()) {
            ShareInternalUtility.invokeCallbackWithError(facebookCallback, "Insufficient permissions for sharing content via Api.");
            return;
        }
        ShareContent shareContent = this.getShareContent();
        try {
            ShareContentValidation.validateForApiShare(shareContent);
        }
        catch (FacebookException facebookException) {
            ShareInternalUtility.invokeCallbackWithException(facebookCallback, facebookException);
            return;
        }
        if (shareContent instanceof ShareLinkContent) {
            this.shareLinkContent((ShareLinkContent)shareContent, facebookCallback);
            return;
        }
        if (shareContent instanceof SharePhotoContent) {
            this.sharePhotoContent((SharePhotoContent)shareContent, facebookCallback);
            return;
        }
        if (shareContent instanceof ShareVideoContent) {
            this.shareVideoContent((ShareVideoContent)shareContent, facebookCallback);
            return;
        }
        if (shareContent instanceof ShareOpenGraphContent) {
            this.shareOpenGraphContent((ShareOpenGraphContent)shareContent, facebookCallback);
        }
    }

}
