/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.webkit.JavascriptInterface
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package bolts;

import android.content.Context;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import bolts.AppLink;
import bolts.AppLinkResolver;
import bolts.Capture;
import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewAppLinkResolver
implements AppLinkResolver {
    private static final String KEY_AL_VALUE = "value";
    private static final String KEY_ANDROID = "android";
    private static final String KEY_APP_NAME = "app_name";
    private static final String KEY_CLASS = "class";
    private static final String KEY_PACKAGE = "package";
    private static final String KEY_SHOULD_FALLBACK = "should_fallback";
    private static final String KEY_URL = "url";
    private static final String KEY_WEB = "web";
    private static final String KEY_WEB_URL = "url";
    private static final String META_TAG_PREFIX = "al";
    private static final String PREFER_HEADER = "Prefer-Html-Meta-Tags";
    private static final String TAG_EXTRACTION_JAVASCRIPT = "javascript:boltsWebViewAppLinkResolverResult.setValue((function() {  var metaTags = document.getElementsByTagName('meta');  var results = [];  for (var i = 0; i < metaTags.length; i++) {    var property = metaTags[i].getAttribute('property');    if (property && property.substring(0, 'al:'.length) === 'al:') {      var tag = { \"property\": metaTags[i].getAttribute('property') };      if (metaTags[i].hasAttribute('content')) {        tag['content'] = metaTags[i].getAttribute('content');      }      results.push(tag);    }  }  return JSON.stringify(results);})())";
    private final Context context;

    public WebViewAppLinkResolver(Context context) {
        this.context = context;
    }

    private static List<Map<String, Object>> getAlList(Map<String, Object> object, String string) {
        if ((object = (List)object.get(string)) == null) {
            return Collections.emptyList();
        }
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static AppLink makeAppLinkFromAlData(Map<String, Object> var0, Uri var1_1) {
        var7_2 = new ArrayList<AppLink.Target>();
        var5_4 = var6_3 = (List<T>)var0.get("android");
        if (var6_3 == null) {
            var5_4 = Collections.emptyList();
        }
        var6_3 = var5_4.iterator();
        block0 : do {
            block5 : {
                block6 : {
                    var4_7 = var6_3.hasNext();
                    var5_4 = null;
                    var2_5 = 0;
                    if (var4_7) break block5;
                    if ((var0 = (List)var0.get("web")) == null || var0.size() <= 0) break block6;
                    var0 = (Map)var0.get(0);
                    var6_3 = (List)var0.get("url");
                    if ((var0 = (List)var0.get("should_fallback")) == null || var0.size() <= 0) ** GOTO lbl-1000
                    var0 = (String)((Map)var0.get(0)).get("value");
                    if (Arrays.asList(new String[]{"no", "false", "0"}).contains(var0.toLowerCase())) {
                        var0 = var5_4;
                    } else lbl-1000: // 2 sources:
                    {
                        var0 = var1_1;
                    }
                    if (var0 == null) return new AppLink(var1_1, var7_2, (Uri)var0);
                    if (var6_3 == null) return new AppLink(var1_1, var7_2, (Uri)var0);
                    if (var6_3.size() <= 0) return new AppLink(var1_1, var7_2, (Uri)var0);
                    var0 = WebViewAppLinkResolver.tryCreateUrl((String)((Map)var6_3.get(0)).get("value"));
                    return new AppLink(var1_1, var7_2, (Uri)var0);
                }
                var0 = var1_1;
                return new AppLink(var1_1, var7_2, (Uri)var0);
            }
            var5_4 = (Map)var6_3.next();
            var8_8 = WebViewAppLinkResolver.getAlList((Map<String, Object>)var5_4, "url");
            var9_9 = WebViewAppLinkResolver.getAlList((Map<String, Object>)var5_4, "package");
            var10_10 = WebViewAppLinkResolver.getAlList((Map<String, Object>)var5_4, "class");
            var11_11 = WebViewAppLinkResolver.getAlList((Map<String, Object>)var5_4, "app_name");
            var3_6 = Math.max(var8_8.size(), Math.max(var9_9.size(), Math.max(var10_10.size(), var11_11.size())));
            do {
                if (var2_5 >= var3_6) continue block0;
                var5_4 = var8_8.size() > var2_5 ? var8_8.get(var2_5).get("value") : null;
                var12_12 = WebViewAppLinkResolver.tryCreateUrl((String)var5_4);
                var5_4 = var9_9.size() > var2_5 ? var9_9.get(var2_5).get("value") : null;
                var13_13 = (String)var5_4;
                var5_4 = var10_10.size() > var2_5 ? var10_10.get(var2_5).get("value") : null;
                var14_14 = (String)var5_4;
                var5_4 = var11_11.size() > var2_5 ? var11_11.get(var2_5).get("value") : null;
                var7_2.add(new AppLink.Target(var13_13, var14_14, var12_12, (String)var5_4));
                ++var2_5;
            } while (true);
            break;
        } while (true);
    }

    private static Map<String, Object> parseAlData(JSONArray jSONArray) throws JSONException {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        for (int i = 0; i < jSONArray.length(); ++i) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            String[] arrstring = jSONObject.getString("property").split(":");
            if (!arrstring[0].equals(META_TAG_PREFIX)) continue;
            Map<String, Object> map = hashMap;
            int n = 1;
            do {
                ArrayList<HashMap<String, Object>> arrayList;
                Object var6_6 = null;
                if (n >= arrstring.length) break;
                ArrayList<HashMap<String, Object>> arrayList2 = arrayList = (ArrayList<HashMap<String, Object>>)map.get(arrstring[n]);
                if (arrayList == null) {
                    arrayList2 = new ArrayList<HashMap<String, Object>>();
                    map.put(arrstring[n], arrayList2);
                }
                map = var6_6;
                if (arrayList2.size() > 0) {
                    map = (Map)arrayList2.get(arrayList2.size() - 1);
                }
                if (map == null || n == arrstring.length - 1) {
                    map = new HashMap();
                    arrayList2.add((HashMap<String, Object>)map);
                }
                ++n;
            } while (true);
            if (!jSONObject.has("content")) continue;
            if (jSONObject.isNull("content")) {
                map.put(KEY_AL_VALUE, null);
                continue;
            }
            map.put(KEY_AL_VALUE, jSONObject.getString("content"));
        }
        return hashMap;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String readFromConnection(URLConnection var0) throws IOException {
        block10 : {
            block11 : {
                if (var0 instanceof HttpURLConnection) {
                    var4_2 /* !! */  = (byte[])var0;
                    var3_3 = var0.getInputStream();
                    ** GOTO lbl10
                } else {
                    var3_3 = var0.getInputStream();
                }
                break block11;
                catch (Exception var3_4) {}
                var3_3 = var4_2 /* !! */ .getErrorStream();
            }
            try {
                var6_5 = new ByteArrayOutputStream();
                var4_2 /* !! */  = new byte[1024];
                do {
                    var2_7 = var3_3.read(var4_2 /* !! */ );
                    var1_6 = 0;
                    if (var2_7 == -1) break;
                    var6_5.write(var4_2 /* !! */ , 0, var2_7);
                } while (true);
                var5_8 = var0.getContentEncoding();
                var4_2 /* !! */  = var5_8;
                if (var5_8 != null) break block10;
                var4_2 /* !! */  = var0.getContentType().split(";");
                var2_7 = var4_2 /* !! */ .length;
            }
            catch (Throwable var0_1) {
                var3_3.close();
                throw var0_1;
            }
            do {
                var0 = var5_8;
                if (var1_6 >= var2_7) break;
                var0 = var4_2 /* !! */ [var1_6].trim();
                if (var0.startsWith("charset=")) {
                    var0 = var0.substring("charset=".length());
                    break;
                }
                ++var1_6;
            } while (true);
            var4_2 /* !! */  = var0;
            if (var0 == null) {
                var4_2 /* !! */  = "UTF-8";
            }
        }
        var0 = new String(var6_5.toByteArray(), (String)var4_2 /* !! */ );
        var3_3.close();
        return var0;
    }

    private static Uri tryCreateUrl(String string) {
        if (string == null) {
            return null;
        }
        return Uri.parse((String)string);
    }

    @Override
    public Task<AppLink> getAppLinkFromUrlInBackground(final Uri uri) {
        final Capture capture = new Capture();
        final Capture capture2 = new Capture();
        return Task.callInBackground(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                URL uRL = new URL(uri.toString());
                URLConnection uRLConnection = null;
                while (uRL != null) {
                    HttpURLConnection httpURLConnection;
                    uRLConnection = uRL.openConnection();
                    boolean bl = uRLConnection instanceof HttpURLConnection;
                    if (bl) {
                        ((HttpURLConnection)uRLConnection).setInstanceFollowRedirects(true);
                    }
                    uRLConnection.setRequestProperty(WebViewAppLinkResolver.PREFER_HEADER, WebViewAppLinkResolver.META_TAG_PREFIX);
                    uRLConnection.connect();
                    if (bl && (httpURLConnection = (HttpURLConnection)uRLConnection).getResponseCode() >= 300 && httpURLConnection.getResponseCode() < 400) {
                        uRL = new URL(httpURLConnection.getHeaderField("Location"));
                        httpURLConnection.disconnect();
                        continue;
                    }
                    uRL = null;
                }
                try {
                    capture.set(WebViewAppLinkResolver.readFromConnection(uRLConnection));
                    capture2.set(uRLConnection.getContentType());
                    return null;
                }
                finally {
                    if (uRLConnection instanceof HttpURLConnection) {
                        ((HttpURLConnection)uRLConnection).disconnect();
                    }
                }
            }
        }).onSuccessTask(new Continuation<Void, Task<JSONArray>>(){

            @Override
            public Task<JSONArray> then(Task<Void> object) throws Exception {
                final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
                WebView webView = new WebView(WebViewAppLinkResolver.this.context);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setNetworkAvailable(false);
                webView.setWebViewClient(new WebViewClient(){
                    private boolean loaded = false;

                    private void runJavaScript(WebView webView) {
                        if (!this.loaded) {
                            this.loaded = true;
                            webView.loadUrl(WebViewAppLinkResolver.TAG_EXTRACTION_JAVASCRIPT);
                        }
                    }

                    public void onLoadResource(WebView webView, String string) {
                        super.onLoadResource(webView, string);
                        this.runJavaScript(webView);
                    }

                    public void onPageFinished(WebView webView, String string) {
                        super.onPageFinished(webView, string);
                        this.runJavaScript(webView);
                    }
                });
                webView.addJavascriptInterface(new Object(){

                    @JavascriptInterface
                    public void setValue(String string) {
                        try {
                            taskCompletionSource.trySetResult(new JSONArray(string));
                            return;
                        }
                        catch (JSONException jSONException) {
                            taskCompletionSource.trySetError((Exception)jSONException);
                            return;
                        }
                    }
                }, "boltsWebViewAppLinkResolverResult");
                object = capture2.get() != null ? ((String)capture2.get()).split(";")[0] : null;
                webView.loadDataWithBaseURL(uri.toString(), (String)capture.get(), (String)object, null, null);
                return taskCompletionSource.getTask();
            }

        }, Task.UI_THREAD_EXECUTOR).onSuccess(new Continuation<JSONArray, AppLink>(){

            @Override
            public AppLink then(Task<JSONArray> task) throws Exception {
                return WebViewAppLinkResolver.makeAppLinkFromAlData(WebViewAppLinkResolver.parseAlData(task.getResult()), uri);
            }
        });
    }

}
