/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.ProgressDialog
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.net.http.SslError
 *  android.os.AsyncTask
 *  android.os.AsyncTask$Status
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.webkit.SslErrorHandler
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.facebook.internal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.facebook.AccessToken;
import com.facebook.FacebookDialogException;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.common.R;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import org.json.JSONArray;
import org.json.JSONObject;

public class WebDialog
extends Dialog {
    private static final int API_EC_DIALOG_CANCEL = 4201;
    private static final int BACKGROUND_GRAY = -872415232;
    static final String CANCEL_URI = "fbconnect://cancel";
    private static final int DEFAULT_THEME = R.style.com_facebook_activity_theme;
    static final boolean DISABLE_SSL_CHECK_FOR_TESTING = false;
    private static final String DISPLAY_TOUCH = "touch";
    private static final String LOG_TAG = "FacebookSDK.WebDialog";
    private static final int MAX_PADDING_SCREEN_HEIGHT = 1280;
    private static final int MAX_PADDING_SCREEN_WIDTH = 800;
    private static final double MIN_SCALE_FACTOR = 0.5;
    private static final int NO_PADDING_SCREEN_HEIGHT = 800;
    private static final int NO_PADDING_SCREEN_WIDTH = 480;
    static final String REDIRECT_URI = "fbconnect://success";
    private static volatile int webDialogTheme;
    private FrameLayout contentFrameLayout;
    private ImageView crossImageView;
    private String expectedRedirectUrl;
    private boolean isDetached;
    private boolean isPageFinished;
    private boolean listenerCalled;
    private OnCompleteListener onCompleteListener;
    private ProgressDialog spinner;
    private UploadStagingResourcesTask uploadTask;
    private String url;
    private WebView webView;
    private WindowManager.LayoutParams windowParams;

    protected WebDialog(Context context, String string) {
        this(context, string, WebDialog.getWebDialogTheme());
    }

    private WebDialog(Context context, String string, int n) {
        int n2 = n;
        if (n == 0) {
            n2 = WebDialog.getWebDialogTheme();
        }
        super(context, n2);
        this.expectedRedirectUrl = REDIRECT_URI;
        this.listenerCalled = false;
        this.isDetached = false;
        this.isPageFinished = false;
        this.url = string;
    }

    private WebDialog(Context context, String string, Bundle object, int n, OnCompleteListener object2) {
        int n2 = n;
        if (n == 0) {
            n2 = WebDialog.getWebDialogTheme();
        }
        super(context, n2);
        this.expectedRedirectUrl = REDIRECT_URI;
        this.listenerCalled = false;
        this.isDetached = false;
        this.isPageFinished = false;
        context = object;
        if (object == null) {
            context = new Bundle();
        }
        context.putString("redirect_uri", REDIRECT_URI);
        context.putString("display", DISPLAY_TOUCH);
        context.putString("sdk", String.format(Locale.ROOT, "android-%s", FacebookSdk.getSdkVersion()));
        this.onCompleteListener = object2;
        if (string.equals("share") && context.containsKey("media")) {
            this.uploadTask = new UploadStagingResourcesTask(string, (Bundle)context);
            return;
        }
        object = ServerProtocol.getDialogAuthority();
        object2 = new StringBuilder();
        object2.append(FacebookSdk.getGraphApiVersion());
        object2.append("/");
        object2.append("dialog/");
        object2.append(string);
        this.url = Utility.buildUri((String)object, object2.toString(), (Bundle)context).toString();
    }

    private void createCrossImage() {
        this.crossImageView = new ImageView(this.getContext());
        this.crossImageView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                WebDialog.this.cancel();
            }
        });
        Drawable drawable2 = this.getContext().getResources().getDrawable(R.drawable.com_facebook_close);
        this.crossImageView.setImageDrawable(drawable2);
        this.crossImageView.setVisibility(4);
    }

    private int getScaledSize(int n, float f, int n2, int n3) {
        int n4 = (int)((float)n / f);
        double d = 0.5;
        if (n4 <= n2) {
            d = 1.0;
        } else if (n4 < n3) {
            d = 0.5 + (double)(n3 - n4) / (double)(n3 - n2) * 0.5;
        }
        return (int)((double)n * d);
    }

    public static int getWebDialogTheme() {
        Validate.sdkInitialized();
        return webDialogTheme;
    }

    protected static void initDefaultTheme(Context context) {
        block4 : {
            block5 : {
                if (context == null) {
                    return;
                }
                try {
                    context = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                    if (context == null) break block4;
                    if (context.metaData == null) {
                        return;
                    }
                    if (webDialogTheme != 0) break block5;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    return;
                }
                WebDialog.setWebDialogTheme(context.metaData.getInt("com.facebook.sdk.WebDialogTheme"));
            }
            return;
        }
    }

    public static WebDialog newInstance(Context context, String string, Bundle bundle, int n, OnCompleteListener onCompleteListener) {
        WebDialog.initDefaultTheme(context);
        return new WebDialog(context, string, bundle, n, onCompleteListener);
    }

    @SuppressLint(value={"SetJavaScriptEnabled"})
    private void setUpWebView(int n) {
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        this.webView = new WebView(this.getContext()){

            public void onWindowFocusChanged(boolean bl) {
                try {
                    super.onWindowFocusChanged(bl);
                    return;
                }
                catch (NullPointerException nullPointerException) {
                    return;
                }
            }
        };
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.setWebViewClient((WebViewClient)new DialogWebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl(this.url);
        this.webView.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
        this.webView.setVisibility(4);
        this.webView.getSettings().setSavePassword(false);
        this.webView.getSettings().setSaveFormData(false);
        this.webView.setFocusable(true);
        this.webView.setFocusableInTouchMode(true);
        this.webView.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!view.hasFocus()) {
                    view.requestFocus();
                }
                return false;
            }
        });
        linearLayout.setPadding(n, n, n, n);
        linearLayout.addView((View)this.webView);
        linearLayout.setBackgroundColor(-872415232);
        this.contentFrameLayout.addView((View)linearLayout);
    }

    public static void setWebDialogTheme(int n) {
        if (n == 0) {
            n = DEFAULT_THEME;
        }
        webDialogTheme = n;
    }

    public void cancel() {
        if (this.onCompleteListener != null && !this.listenerCalled) {
            this.sendErrorToListener(new FacebookOperationCanceledException());
        }
    }

    public void dismiss() {
        if (this.webView != null) {
            this.webView.stopLoading();
        }
        if (!this.isDetached && this.spinner != null && this.spinner.isShowing()) {
            this.spinner.dismiss();
        }
        super.dismiss();
    }

    public OnCompleteListener getOnCompleteListener() {
        return this.onCompleteListener;
    }

    protected WebView getWebView() {
        return this.webView;
    }

    protected boolean isListenerCalled() {
        return this.listenerCalled;
    }

    protected boolean isPageFinished() {
        return this.isPageFinished;
    }

    public void onAttachedToWindow() {
        this.isDetached = false;
        if (Utility.mustFixWindowParamsForAutofill(this.getContext()) && this.windowParams != null && this.windowParams.token == null) {
            this.windowParams.token = this.getOwnerActivity().getWindow().getAttributes().token;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Set token on onAttachedToWindow(): ");
            stringBuilder.append((Object)this.windowParams.token);
            Utility.logd(LOG_TAG, stringBuilder.toString());
        }
        super.onAttachedToWindow();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.spinner = new ProgressDialog(this.getContext());
        this.spinner.requestWindowFeature(1);
        this.spinner.setMessage((CharSequence)this.getContext().getString(R.string.com_facebook_loading));
        this.spinner.setCanceledOnTouchOutside(false);
        this.spinner.setOnCancelListener(new DialogInterface.OnCancelListener(){

            public void onCancel(DialogInterface dialogInterface) {
                WebDialog.this.cancel();
            }
        });
        this.requestWindowFeature(1);
        this.contentFrameLayout = new FrameLayout(this.getContext());
        this.resize();
        this.getWindow().setGravity(17);
        this.getWindow().setSoftInputMode(16);
        this.createCrossImage();
        if (this.url != null) {
            this.setUpWebView(this.crossImageView.getDrawable().getIntrinsicWidth() / 2 + 1);
        }
        this.contentFrameLayout.addView((View)this.crossImageView, new ViewGroup.LayoutParams(-2, -2));
        this.setContentView((View)this.contentFrameLayout);
    }

    public void onDetachedFromWindow() {
        this.isDetached = true;
        super.onDetachedFromWindow();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4) {
            this.cancel();
        }
        return super.onKeyDown(n, keyEvent);
    }

    protected void onStart() {
        super.onStart();
        if (this.uploadTask != null && this.uploadTask.getStatus() == AsyncTask.Status.PENDING) {
            this.uploadTask.execute((Object[])new Void[0]);
            this.spinner.show();
            return;
        }
        this.resize();
    }

    protected void onStop() {
        if (this.uploadTask != null) {
            this.uploadTask.cancel(true);
            this.spinner.dismiss();
        }
        super.onStop();
    }

    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        if (layoutParams.token == null) {
            this.windowParams = layoutParams;
        }
        super.onWindowAttributesChanged(layoutParams);
    }

    protected Bundle parseResponseUri(String string2) {
        string2 = Uri.parse((String)string2);
        Bundle bundle = Utility.parseUrlQueryString(string2.getQuery());
        bundle.putAll(Utility.parseUrlQueryString(string2.getFragment()));
        return bundle;
    }

    public void resize() {
        Display display = ((WindowManager)this.getContext().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int n = displayMetrics.widthPixels < displayMetrics.heightPixels ? displayMetrics.widthPixels : displayMetrics.heightPixels;
        int n2 = displayMetrics.widthPixels < displayMetrics.heightPixels ? displayMetrics.heightPixels : displayMetrics.widthPixels;
        n = Math.min(this.getScaledSize(n, displayMetrics.density, 480, 800), displayMetrics.widthPixels);
        n2 = Math.min(this.getScaledSize(n2, displayMetrics.density, 800, 1280), displayMetrics.heightPixels);
        this.getWindow().setLayout(n, n2);
    }

    protected void sendErrorToListener(Throwable throwable) {
        if (this.onCompleteListener != null && !this.listenerCalled) {
            this.listenerCalled = true;
            throwable = throwable instanceof FacebookException ? (FacebookException)throwable : new FacebookException(throwable);
            this.onCompleteListener.onComplete(null, (FacebookException)throwable);
            this.dismiss();
        }
    }

    protected void sendSuccessToListener(Bundle bundle) {
        if (this.onCompleteListener != null && !this.listenerCalled) {
            this.listenerCalled = true;
            this.onCompleteListener.onComplete(bundle, null);
            this.dismiss();
        }
    }

    protected void setExpectedRedirectUrl(String string2) {
        this.expectedRedirectUrl = string2;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public static class Builder {
        private AccessToken accessToken;
        private String action;
        private String applicationId;
        private Context context;
        private OnCompleteListener listener;
        private Bundle parameters;
        private int theme;

        public Builder(Context context, String string, Bundle bundle) {
            this.accessToken = AccessToken.getCurrentAccessToken();
            if (this.accessToken == null) {
                String string2 = Utility.getMetadataApplicationId(context);
                if (string2 != null) {
                    this.applicationId = string2;
                } else {
                    throw new FacebookException("Attempted to create a builder without a valid access token or a valid default Application ID.");
                }
            }
            this.finishInit(context, string, bundle);
        }

        public Builder(Context context, String string, String string2, Bundle bundle) {
            String string3 = string;
            if (string == null) {
                string3 = Utility.getMetadataApplicationId(context);
            }
            Validate.notNullOrEmpty(string3, "applicationId");
            this.applicationId = string3;
            this.finishInit(context, string2, bundle);
        }

        private void finishInit(Context context, String string, Bundle bundle) {
            this.context = context;
            this.action = string;
            if (bundle != null) {
                this.parameters = bundle;
                return;
            }
            this.parameters = new Bundle();
        }

        public WebDialog build() {
            if (this.accessToken != null) {
                this.parameters.putString("app_id", this.accessToken.getApplicationId());
                this.parameters.putString("access_token", this.accessToken.getToken());
            } else {
                this.parameters.putString("app_id", this.applicationId);
            }
            return WebDialog.newInstance(this.context, this.action, this.parameters, this.theme, this.listener);
        }

        public String getApplicationId() {
            return this.applicationId;
        }

        public Context getContext() {
            return this.context;
        }

        public OnCompleteListener getListener() {
            return this.listener;
        }

        public Bundle getParameters() {
            return this.parameters;
        }

        public int getTheme() {
            return this.theme;
        }

        public Builder setOnCompleteListener(OnCompleteListener onCompleteListener) {
            this.listener = onCompleteListener;
            return this;
        }

        public Builder setTheme(int n) {
            this.theme = n;
            return this;
        }
    }

    private class DialogWebViewClient
    extends WebViewClient {
        private DialogWebViewClient() {
        }

        public void onPageFinished(WebView webView, String string) {
            super.onPageFinished(webView, string);
            if (!WebDialog.this.isDetached) {
                WebDialog.this.spinner.dismiss();
            }
            WebDialog.this.contentFrameLayout.setBackgroundColor(0);
            WebDialog.this.webView.setVisibility(0);
            WebDialog.this.crossImageView.setVisibility(0);
            WebDialog.this.isPageFinished = true;
        }

        public void onPageStarted(WebView webView, String string, Bitmap bitmap) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Webview loading URL: ");
            stringBuilder.append(string);
            Utility.logd(WebDialog.LOG_TAG, stringBuilder.toString());
            super.onPageStarted(webView, string, bitmap);
            if (!WebDialog.this.isDetached) {
                WebDialog.this.spinner.show();
            }
        }

        public void onReceivedError(WebView webView, int n, String string, String string2) {
            super.onReceivedError(webView, n, string, string2);
            WebDialog.this.sendErrorToListener(new FacebookDialogException(string, n, string2));
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            sslErrorHandler.cancel();
            WebDialog.this.sendErrorToListener(new FacebookDialogException(null, -11, null));
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public boolean shouldOverrideUrlLoading(WebView object, String object2) {
            Object object3;
            int n;
            Bundle bundle;
            block15 : {
                object = new StringBuilder();
                object.append("Redirect URL: ");
                object.append((String)object2);
                Utility.logd(WebDialog.LOG_TAG, object.toString());
                if (object2.startsWith(WebDialog.this.expectedRedirectUrl)) {
                    bundle = WebDialog.this.parseResponseUri((String)object2);
                    object2 = object = bundle.getString("error");
                    if (object == null) {
                        object2 = bundle.getString("error_type");
                    }
                    object = object3 = bundle.getString("error_msg");
                    if (object3 == null) {
                        object = bundle.getString("error_message");
                    }
                    object3 = object;
                    if (object == null) {
                        object3 = bundle.getString("error_description");
                    }
                    if (!Utility.isNullOrEmpty((String)(object = bundle.getString("error_code")))) {
                        n = Integer.parseInt((String)object);
                        break block15;
                    }
                } else {
                    if (object2.startsWith(WebDialog.CANCEL_URI)) {
                        WebDialog.this.cancel();
                        return true;
                    }
                    if (object2.contains(WebDialog.DISPLAY_TOUCH)) {
                        return false;
                    }
                    WebDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)object2)));
                    return true;
                    catch (NumberFormatException numberFormatException) {}
                }
                n = -1;
            }
            if (Utility.isNullOrEmpty((String)object2) && Utility.isNullOrEmpty((String)object3) && n == -1) {
                WebDialog.this.sendSuccessToListener(bundle);
                return true;
            }
            if (object2 != null && (object2.equals("access_denied") || object2.equals("OAuthAccessDeniedException"))) {
                WebDialog.this.cancel();
                return true;
            }
            if (n == 4201) {
                WebDialog.this.cancel();
                return true;
            }
            object = new FacebookRequestError(n, (String)object2, (String)object3);
            WebDialog.this.sendErrorToListener(new FacebookServiceException((FacebookRequestError)object, (String)object3));
            return true;
            catch (ActivityNotFoundException activityNotFoundException) {
                return false;
            }
        }
    }

    public static interface OnCompleteListener {
        public void onComplete(Bundle var1, FacebookException var2);
    }

    private class UploadStagingResourcesTask
    extends AsyncTask<Void, Void, String[]> {
        private String action;
        private Exception[] exceptions;
        private Bundle parameters;

        UploadStagingResourcesTask(String string, Bundle bundle) {
            this.action = string;
            this.parameters = bundle;
        }

        static /* synthetic */ Exception[] access$800(UploadStagingResourcesTask uploadStagingResourcesTask) {
            return uploadStagingResourcesTask.exceptions;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected /* varargs */ String[] doInBackground(Void ... object) {
            Object object2 = this.parameters.getStringArray("media");
            final String[] arrstring = new String[((String[])object2).length];
            this.exceptions = new Exception[((String[])object2).length];
            final CountDownLatch countDownLatch = new CountDownLatch(((String[])object2).length);
            object = new ConcurrentLinkedQueue();
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            final int n = 0;
            do {
                block10 : {
                    try {
                        if (n >= ((String[])object2).length) {
                            countDownLatch.await();
                            return arrstring;
                        }
                        if (this.isCancelled()) {
                            object2 = object.iterator();
                            while (object2.hasNext()) {
                                ((AsyncTask)object2.next()).cancel(true);
                            }
                            return null;
                        }
                        Uri uri = Uri.parse((String)object2[n]);
                        if (Utility.isWebUri(uri)) {
                            arrstring[n] = uri.toString();
                            countDownLatch.countDown();
                        } else {
                            object.add(ShareInternalUtility.newUploadStagingResourceWithImageRequest(accessToken, uri, new GraphRequest.Callback(){

                                /*
                                 * Enabled aggressive block sorting
                                 * Enabled unnecessary exception pruning
                                 * Enabled aggressive exception aggregation
                                 */
                                @Override
                                public void onCompleted(GraphResponse object) {
                                    try {
                                        Object object2 = object.getError();
                                        if (object2 != null) {
                                            String string = object2.getErrorMessage();
                                            object2 = string;
                                            if (string == null) {
                                                object2 = "Error staging photo.";
                                            }
                                            throw new FacebookGraphResponseException((GraphResponse)object, (String)object2);
                                        }
                                        if ((object = object.getJSONObject()) == null) {
                                            throw new FacebookException("Error staging photo.");
                                        }
                                        if ((object = object.optString("uri")) == null) {
                                            throw new FacebookException("Error staging photo.");
                                        }
                                        arrstring[n] = object;
                                    }
                                    catch (Exception exception) {
                                        UploadStagingResourcesTask.access$800((UploadStagingResourcesTask)UploadStagingResourcesTask.this)[n] = exception;
                                    }
                                    countDownLatch.countDown();
                                }
                            }).executeAsync());
                        }
                        break block10;
                    }
                    catch (Exception exception) {}
                    object = object.iterator();
                    do {
                        if (!object.hasNext()) {
                            return null;
                        }
                        ((AsyncTask)object.next()).cancel(true);
                    } while (true);
                }
                ++n;
            } while (true);
        }

        protected void onPostExecute(String[] object) {
            WebDialog.this.spinner.dismiss();
            for (Exception exception : this.exceptions) {
                if (exception == null) continue;
                WebDialog.this.sendErrorToListener(exception);
                return;
            }
            if (object == null) {
                WebDialog.this.sendErrorToListener(new FacebookException("Failed to stage photos for web dialog"));
                return;
            }
            if ((object = Arrays.asList(object)).contains(null)) {
                WebDialog.this.sendErrorToListener(new FacebookException("Failed to stage photos for web dialog"));
                return;
            }
            Utility.putJSONValueInBundle(this.parameters, "media", (Object)new JSONArray((Collection)object));
            object = ServerProtocol.getDialogAuthority();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(FacebookSdk.getGraphApiVersion());
            stringBuilder.append("/");
            stringBuilder.append("dialog/");
            stringBuilder.append(this.action);
            object = Utility.buildUri((String)object, stringBuilder.toString(), this.parameters);
            WebDialog.this.url = object.toString();
            int n = WebDialog.this.crossImageView.getDrawable().getIntrinsicWidth();
            WebDialog.this.setUpWebView(n / 2 + 1);
        }

    }

}
