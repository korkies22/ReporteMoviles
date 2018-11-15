/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package de.cisha.chess.util;

import android.util.Base64;
import de.cisha.chess.util.HttpResponse;
import de.cisha.chess.util.Logger;
import de.cisha.chess.util.TlsSocketFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HTTPHelper {
    private static final boolean BETA_TEST = false;
    private static final int CONNECTION_TIMEOUT = 20000;
    private static String _logtag = "HTTPHelper";

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String createUrlBody(URL object, Map<String, String> map) {
        object = "";
        for (Object object2 : map.entrySet()) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append(object2.getKey());
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(object2.getValue(), "UTF-8"));
                stringBuilder.append("&");
                object = object2 = stringBuilder.toString();
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {}
        }
        if (map.size() > 0) {
            object.substring(0, object.length() - 1);
        }
        return object;
    }

    public static HttpResponse loadUrlGet(URL uRL) {
        return HTTPHelper.loadUrlGet(uRL, null, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static HttpResponse loadUrlGet(URL var0, String var1_1, String var2_3) {
        block7 : {
            if (var0 == null) {
                throw new NullPointerException("url must not be null");
            }
            var5_4 = new HttpResponse.Builder();
            var6_5 = (HttpsURLConnection)var0.openConnection();
            var6_5.setSSLSocketFactory(new TlsSocketFactory(var6_5.getSSLSocketFactory()));
            var6_5.setConnectTimeout(20000);
            if (var1_1 /* !! */  != null && var2_3 != null) {
                var7_6 /* !! */  = new StringBuilder();
                var7_6 /* !! */ .append((String)var1_1 /* !! */ );
                var7_6 /* !! */ .append(":");
                var7_6 /* !! */ .append((String)var2_3);
                var1_1 /* !! */  = Base64.encodeToString((byte[])var7_6 /* !! */ .toString().getBytes(), (int)2);
                var2_3 = new StringBuilder();
                var2_3.append("Basic ");
                var2_3.append((String)var1_1 /* !! */ );
                var6_5.setRequestProperty("Authorization", var2_3.toString());
            }
            var1_1 /* !! */  = "ISO-8859-1";
            var2_3 = var6_5.getContentType();
            var3_7 = true;
            if (var2_3 != null) {
                var1_1 /* !! */  = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*").matcher(var6_5.getContentType());
                var1_1 /* !! */  = var1_1 /* !! */ .matches() != false ? var1_1 /* !! */ .group(1) : "ISO-8859-1";
            }
            if ((var4_8 = var6_5.getResponseCode()) >= 400) break block7;
            var3_7 = false;
        }
        if (!var3_7) ** GOTO lbl31
        try {
            block8 : {
                var2_3 = var6_5.getErrorStream();
                break block8;
lbl31: // 1 sources:
                var2_3 = var6_5.getInputStream();
            }
            var1_1 /* !! */  = new InputStreamReader((InputStream)var2_3, (String)var1_1 /* !! */ );
            var6_5 = new BufferedReader((Reader)var1_1 /* !! */ );
            var2_3 = new StringBuffer("");
            var7_6 /* !! */  = System.getProperty("line.separator");
            while ((var8_9 = var6_5.readLine()) != null) {
                var9_10 = new StringBuilder();
                var9_10.append(var8_9);
                var9_10.append((String)var7_6 /* !! */ );
                var2_3.append(var9_10.toString());
            }
            var1_1 /* !! */ .close();
            var1_1 /* !! */  = var3_7 != false ? HttpResponse.State.HTTP_ERROR : HttpResponse.State.OK;
            var5_4.setState((HttpResponse.State)var1_1 /* !! */ );
            var5_4.setHttpErrorCode(var4_8);
            var5_4.setBody(var2_3.toString());
            return var5_4.create();
        }
        catch (IOException var1_2) {
            var2_3 = Logger.getInstance();
            var6_5 = HTTPHelper._logtag;
            var7_6 /* !! */  = new StringBuilder();
            var7_6 /* !! */ .append("Error loading url: ");
            var7_6 /* !! */ .append(var0.getPath());
            var2_3.debug((String)var6_5, var7_6 /* !! */ .toString(), var1_2);
            var5_4.setState(HttpResponse.State.IO_ERROR);
            var5_4.setHttpErrorCode(503);
            return var5_4.create();
        }
    }

    public static HttpResponse loadUrlGet(URL uRL, Map<String, String> map) {
        return HTTPHelper.loadUrlGet(uRL, map, null, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static HttpResponse loadUrlGet(URL uRL, Map<String, String> object, String string, String string2) {
        object = HTTPHelper.createUrlBody(uRL, (Map<String, String>)object);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(uRL.toString());
            stringBuilder.append("?");
            stringBuilder.append((String)object);
            uRL = new URL(stringBuilder.toString());
        }
        catch (MalformedURLException malformedURLException) {
            throw new RuntimeException("url is invalid");
        }
        return HTTPHelper.loadUrlGet(uRL, string, string2);
    }

    public static HttpResponse loadUrlPost(URL uRL, Map<String, String> map) {
        return HTTPHelper.loadUrlPost(uRL, map, null, null);
    }

    public static HttpResponse loadUrlPost(URL uRL, Map<String, String> map, String string, String string2) {
        return HTTPHelper.loadUrlPost(uRL, map, null, string2, string);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static HttpResponse loadUrlPost(URL var0, Map<String, String> var1_2, Map<String, FileUploadInformation> var2_3, String var3_4, String var4_5) {
        block11 : {
            block12 : {
                block14 : {
                    block13 : {
                        var7_6 = new HttpResponse.Builder();
                        try {
                            var8_7 = (HttpsURLConnection)var0 /* !! */ .openConnection();
                            var8_7.setSSLSocketFactory(new TlsSocketFactory(var8_7.getSSLSocketFactory()));
                            var8_7.setRequestMethod("POST");
                            if (var3_4 != null && var4_5 != null) {
                                var9_8 = new StringBuilder();
                                var9_8.append(var3_4);
                                var9_8.append(":");
                                var9_8.append((String)var4_5);
                                var3_4 = Base64.encodeToString((byte[])var9_8.toString().getBytes(), (int)2);
                                var4_5 = new StringBuilder();
                                var4_5.append("Basic ");
                                var4_5.append(var3_4);
                                var8_7.setRequestProperty("Authorization", var4_5.toString());
                            }
                            var5_9 = true;
                            var8_7.setDoInput(true);
                            var8_7.setDoOutput(true);
                            var8_7.setUseCaches(false);
                            var8_7.setConnectTimeout(20000);
                            if (var2_3 == null) {
                                var0 /* !! */  = var1_2 != null ? HTTPHelper.createUrlBody((URL)var0 /* !! */ , (Map<String, String>)var1_2) : "";
                                var8_7.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                var8_7.setRequestProperty("Content-Length", String.valueOf(var0 /* !! */ .length()));
                                var1_2 = new OutputStreamWriter(var8_7.getOutputStream());
                                var1_2.write((String)var0 /* !! */ );
                                var1_2.flush();
                                var1_2.close();
                                break block11;
                            }
                            var0 /* !! */  = new StringBuilder();
                            var0 /* !! */ .append("***");
                            var0 /* !! */ .append(new Date().getTime());
                            var0 /* !! */ .append("***");
                            var3_4 = var0 /* !! */ .toString();
                            var0 /* !! */  = new StringBuilder();
                            var0 /* !! */ .append("--");
                            var0 /* !! */ .append(var3_4);
                            var0 /* !! */ .append("\r\n");
                            var4_5 = var0 /* !! */ .toString();
                            var8_7.setRequestProperty("Cache-Control", "no-cache");
                            var0 /* !! */  = new StringBuilder();
                            var0 /* !! */ .append("multipart/form-data;boundary=");
                            var0 /* !! */ .append(var3_4);
                            var8_7.setRequestProperty("Content-Type", var0 /* !! */ .toString());
                            var9_8 = new DataOutputStream(var8_7.getOutputStream());
                            if (var1_2 != null) {
                                for (Object var10_10 : var1_2.keySet()) {
                                    var9_8.writeBytes((String)var4_5);
                                    var11_11 = new StringBuilder();
                                    var11_11.append("Content-Disposition: form-data; name=\"");
                                    var11_11.append((String)var10_10);
                                    var11_11.append("\"");
                                    var11_11.append("\r\n");
                                    var9_8.writeBytes(var11_11.toString());
                                    var9_8.writeBytes("\r\n");
                                    var9_8.writeBytes((String)var1_2.get(var10_10));
                                    var9_8.writeBytes("\r\n");
                                }
                            }
                            if (var2_3 == null) break block12;
                            var10_10 = var2_3.keySet().iterator();
lbl61: // 2 sources:
                            if (!var10_10.hasNext()) break block12;
                            var12_13 = (String)var10_10.next();
                            var11_11 = (FileUploadInformation)var2_3.get(var12_13);
                            var1_2 = var11_11.getMimeType();
                            if (var1_2 == null) break block13;
                            var0 /* !! */  = var1_2;
                            if (!var1_2.trim().equals("")) break block14;
                        }
                        catch (IOException var0_1) {
                            Logger.getInstance().debug("de.cisha.chess.util.HTTPHelper", "loading failed", var0_1);
                            var7_6.setState(HttpResponse.State.IO_ERROR);
                            return var7_6.create();
                        }
                    }
                    var0 /* !! */  = "application/octet-stream";
                }
                var9_8.writeBytes((String)var4_5);
                var1_2 = new StringBuilder();
                var1_2.append("Content-Disposition: form-data; name=\"");
                var1_2.append(var12_13);
                var1_2.append("\"; filename=\"");
                var1_2.append(var11_11.getName());
                var1_2.append("\"");
                var1_2.append("\r\n");
                var9_8.writeBytes(var1_2.toString());
                var1_2 = new StringBuilder();
                var1_2.append("Content-Type: ");
                var1_2.append((String)var0 /* !! */ );
                var1_2.append("\r\n");
                var9_8.writeBytes(var1_2.toString());
                var9_8.writeBytes("\r\n");
                var0 /* !! */  = new byte[1024];
                var1_2 = var11_11.getStream();
                while ((var6_12 = var1_2.read(var0 /* !! */ )) >= 0) {
                    var9_8.write(var0 /* !! */ , 0, var6_12);
                }
                var9_8.writeBytes("\r\n");
                ** GOTO lbl61
            }
            var0 /* !! */  = new StringBuilder();
            var0 /* !! */ .append("--");
            var0 /* !! */ .append(var3_4);
            var0 /* !! */ .append("--");
            var0 /* !! */ .append("\r\n");
            var9_8.writeBytes(var0 /* !! */ .toString());
            var9_8.flush();
            var9_8.close();
        }
        if (var8_7.getResponseCode() < 400) {
            var5_9 = false;
        }
        var0 /* !! */  = var5_9 != false ? var8_7.getErrorStream() : var8_7.getInputStream();
        var0 /* !! */  = new BufferedReader(new InputStreamReader((InputStream)var0 /* !! */ , Charset.forName("UTF-8")));
        var1_2 = new StringBuilder();
        while ((var2_3 = var0 /* !! */ .readLine()) != null) {
            var1_2.append((String)var2_3);
        }
        var0 /* !! */ .close();
        var7_6.setHttpErrorCode(var8_7.getResponseCode());
        var0 /* !! */  = var5_9 != false ? HttpResponse.State.HTTP_ERROR : HttpResponse.State.OK;
        var7_6.setState((HttpResponse.State)var0 /* !! */ );
        var7_6.setBody(var1_2.toString());
        var8_7.disconnect();
        return var7_6.create();
    }

    public static HttpResponse loadUrlPostWithFile(URL uRL, Map<String, String> map, String string, InputStream inputStream) {
        return HTTPHelper.loadUrlPost(uRL, map, null, null);
    }

    public static interface FileUploadInformation {
        public String getMimeType();

        public String getName();

        public InputStream getStream();
    }

}
