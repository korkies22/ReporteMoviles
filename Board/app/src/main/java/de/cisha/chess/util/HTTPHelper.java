// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import java.nio.charset.Charset;
import java.io.DataOutputStream;
import java.util.Date;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import android.util.Base64;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.HttpsURLConnection;
import java.util.Iterator;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.net.URL;

public class HTTPHelper
{
    private static final boolean BETA_TEST = false;
    private static final int CONNECTION_TIMEOUT = 20000;
    private static String _logtag = "HTTPHelper";
    
    private static String createUrlBody(URL string, final Map<String, String> map) {
        string = (URL)"";
        final Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (true) {
            Label_0112: {
                if (!iterator.hasNext()) {
                    break Label_0112;
                }
                final Map.Entry<String, String> entry = iterator.next();
                try {
                    final StringBuilder sb = new StringBuilder();
                    sb.append((String)string);
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    sb.append("&");
                    string = (URL)sb.toString();
                    continue;
                    // iftrue(Label_0133:, map.size() <= 0)
                    ((String)string).substring(0, ((String)string).length() - 1);
                    Label_0133: {
                        return (String)string;
                    }
                }
                catch (UnsupportedEncodingException ex) {}
            }
        }
    }
    
    public static HttpResponse loadUrlGet(final URL url) {
        return loadUrlGet(url, null, null);
    }
    
    public static HttpResponse loadUrlGet(final URL url, String s, String contentType) {
        if (url == null) {
            throw new NullPointerException("url must not be null");
        }
    Label_0204_Outer:
        while (true) {
            final HttpResponse.Builder builder = new HttpResponse.Builder();
            while (true) {
            Label_0442:
                while (true) {
                    Label_0436: {
                        try {
                            final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
                            httpsURLConnection.setSSLSocketFactory(new TlsSocketFactory(httpsURLConnection.getSSLSocketFactory()));
                            httpsURLConnection.setConnectTimeout(20000);
                            if (s != null && contentType != null) {
                                final StringBuilder sb = new StringBuilder();
                                sb.append(s);
                                sb.append(":");
                                sb.append(contentType);
                                s = Base64.encodeToString(sb.toString().getBytes(), 2);
                                final StringBuilder sb2 = new StringBuilder();
                                sb2.append("Basic ");
                                sb2.append(s);
                                httpsURLConnection.setRequestProperty("Authorization", sb2.toString());
                            }
                            s = "ISO-8859-1";
                            contentType = httpsURLConnection.getContentType();
                            final int n = 1;
                            if (contentType != null) {
                                final Matcher matcher = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*").matcher(httpsURLConnection.getContentType());
                                if (!matcher.matches()) {
                                    break Label_0436;
                                }
                                s = matcher.group(1);
                            }
                            final int responseCode = httpsURLConnection.getResponseCode();
                            if (responseCode >= 400) {
                                InputStream inputStream;
                                if (n != 0) {
                                    inputStream = httpsURLConnection.getErrorStream();
                                }
                                else {
                                    inputStream = httpsURLConnection.getInputStream();
                                }
                                final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, s);
                                final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                final StringBuffer sb3 = new StringBuffer("");
                                final String property = System.getProperty("line.separator");
                                while (true) {
                                    final String line = bufferedReader.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    final StringBuilder sb4 = new StringBuilder();
                                    sb4.append(line);
                                    sb4.append(property);
                                    sb3.append(sb4.toString());
                                }
                                inputStreamReader.close();
                                HttpResponse.State state;
                                if (n != 0) {
                                    state = HttpResponse.State.HTTP_ERROR;
                                }
                                else {
                                    state = HttpResponse.State.OK;
                                }
                                builder.setState(state);
                                builder.setHttpErrorCode(responseCode);
                                builder.setBody(sb3.toString());
                                return builder.create();
                            }
                            break Label_0442;
                        }
                        catch (IOException ex) {
                            final Logger instance = Logger.getInstance();
                            final String logtag = HTTPHelper._logtag;
                            final StringBuilder sb5 = new StringBuilder();
                            sb5.append("Error loading url: ");
                            sb5.append(url.getPath());
                            instance.debug(logtag, sb5.toString(), ex);
                            builder.setState(HttpResponse.State.IO_ERROR);
                            builder.setHttpErrorCode(503);
                            return builder.create();
                        }
                    }
                    s = "ISO-8859-1";
                    continue Label_0204_Outer;
                }
                final int n = 0;
                continue;
            }
        }
    }
    
    public static HttpResponse loadUrlGet(final URL url, final Map<String, String> map) {
        return loadUrlGet(url, map, null, null);
    }
    
    public static HttpResponse loadUrlGet(URL url, final Map<String, String> map, final String s, final String s2) {
        final String urlBody = createUrlBody(url, map);
        try {
            final StringBuilder sb = new StringBuilder();
            sb.append(url.toString());
            sb.append("?");
            sb.append(urlBody);
            url = new URL(sb.toString());
            return loadUrlGet(url, s, s2);
        }
        catch (MalformedURLException ex) {
            throw new RuntimeException("url is invalid");
        }
    }
    
    public static HttpResponse loadUrlPost(final URL url, final Map<String, String> map) {
        return loadUrlPost(url, map, null, null);
    }
    
    public static HttpResponse loadUrlPost(final URL url, final Map<String, String> map, final String s, final String s2) {
        return loadUrlPost(url, map, null, s2, s);
    }
    
    public static HttpResponse loadUrlPost(final URL url, final Map<String, String> map, final Map<String, FileUploadInformation> map2, String s, String string) {
        HttpResponse.Builder builder;
        HttpsURLConnection httpsURLConnection;
        StringBuilder sb;
        StringBuilder sb2;
        int n;
        String urlBody;
        OutputStreamWriter outputStreamWriter;
        StringBuilder sb3;
        StringBuilder sb4;
        StringBuilder sb5;
        DataOutputStream dataOutputStream;
        StringBuilder sb6;
        FileUploadInformation fileUploadInformation;
        String mimeType;
        String s4;
        StringBuilder sb7;
        StringBuilder sb8;
        byte[] array;
        InputStream stream;
        int read;
        StringBuilder sb9;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuilder sb10;
        String line;
        HttpResponse.State state;
        Label_0586_Outer:Label_0828_Outer:
        while (true) {
            builder = new HttpResponse.Builder();
            while (true) {
            Label_0998:
                while (true) {
                Label_0991:
                    while (true) {
                        Label_0985: {
                            try {
                                httpsURLConnection = (HttpsURLConnection)url.openConnection();
                                httpsURLConnection.setSSLSocketFactory(new TlsSocketFactory(httpsURLConnection.getSSLSocketFactory()));
                                httpsURLConnection.setRequestMethod("POST");
                                if (s != null && string != null) {
                                    sb = new StringBuilder();
                                    sb.append(s);
                                    sb.append(":");
                                    sb.append(string);
                                    s = Base64.encodeToString(sb.toString().getBytes(), 2);
                                    sb2 = new StringBuilder();
                                    sb2.append("Basic ");
                                    sb2.append(s);
                                    httpsURLConnection.setRequestProperty("Authorization", sb2.toString());
                                }
                                n = 1;
                                httpsURLConnection.setDoInput(true);
                                httpsURLConnection.setDoOutput(true);
                                httpsURLConnection.setUseCaches(false);
                                httpsURLConnection.setConnectTimeout(20000);
                                if (map2 == null) {
                                    if (map == null) {
                                        break Label_0985;
                                    }
                                    urlBody = createUrlBody(url, map);
                                    httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                    httpsURLConnection.setRequestProperty("Content-Length", String.valueOf(urlBody.length()));
                                    outputStreamWriter = new OutputStreamWriter(httpsURLConnection.getOutputStream());
                                    outputStreamWriter.write(urlBody);
                                    outputStreamWriter.flush();
                                    outputStreamWriter.close();
                                }
                                else {
                                    sb3 = new StringBuilder();
                                    sb3.append("***");
                                    sb3.append(new Date().getTime());
                                    sb3.append("***");
                                    s = sb3.toString();
                                    sb4 = new StringBuilder();
                                    sb4.append("--");
                                    sb4.append(s);
                                    sb4.append("\r\n");
                                    string = sb4.toString();
                                    httpsURLConnection.setRequestProperty("Cache-Control", "no-cache");
                                    sb5 = new StringBuilder();
                                    sb5.append("multipart/form-data;boundary=");
                                    sb5.append(s);
                                    httpsURLConnection.setRequestProperty("Content-Type", sb5.toString());
                                    dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
                                    if (map != null) {
                                        for (final String s2 : map.keySet()) {
                                            dataOutputStream.writeBytes(string);
                                            sb6 = new StringBuilder();
                                            sb6.append("Content-Disposition: form-data; name=\"");
                                            sb6.append(s2);
                                            sb6.append("\"");
                                            sb6.append("\r\n");
                                            dataOutputStream.writeBytes(sb6.toString());
                                            dataOutputStream.writeBytes("\r\n");
                                            dataOutputStream.writeBytes(map.get(s2));
                                            dataOutputStream.writeBytes("\r\n");
                                        }
                                    }
                                    if (map2 != null) {
                                        for (final String s3 : map2.keySet()) {
                                            fileUploadInformation = map2.get(s3);
                                            mimeType = fileUploadInformation.getMimeType();
                                            if (mimeType == null) {
                                                break Label_0991;
                                            }
                                            s4 = mimeType;
                                            if (mimeType.trim().equals("")) {
                                                break Label_0991;
                                            }
                                            dataOutputStream.writeBytes(string);
                                            sb7 = new StringBuilder();
                                            sb7.append("Content-Disposition: form-data; name=\"");
                                            sb7.append(s3);
                                            sb7.append("\"; filename=\"");
                                            sb7.append(fileUploadInformation.getName());
                                            sb7.append("\"");
                                            sb7.append("\r\n");
                                            dataOutputStream.writeBytes(sb7.toString());
                                            sb8 = new StringBuilder();
                                            sb8.append("Content-Type: ");
                                            sb8.append(s4);
                                            sb8.append("\r\n");
                                            dataOutputStream.writeBytes(sb8.toString());
                                            dataOutputStream.writeBytes("\r\n");
                                            array = new byte[1024];
                                            stream = fileUploadInformation.getStream();
                                            while (true) {
                                                read = stream.read(array);
                                                if (read < 0) {
                                                    break;
                                                }
                                                dataOutputStream.write(array, 0, read);
                                            }
                                            dataOutputStream.writeBytes("\r\n");
                                        }
                                    }
                                    sb9 = new StringBuilder();
                                    sb9.append("--");
                                    sb9.append(s);
                                    sb9.append("--");
                                    sb9.append("\r\n");
                                    dataOutputStream.writeBytes(sb9.toString());
                                    dataOutputStream.flush();
                                    dataOutputStream.close();
                                }
                                if (httpsURLConnection.getResponseCode() >= 400) {
                                    if (n != 0) {
                                        inputStream = httpsURLConnection.getErrorStream();
                                    }
                                    else {
                                        inputStream = httpsURLConnection.getInputStream();
                                    }
                                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                                    sb10 = new StringBuilder();
                                    while (true) {
                                        line = bufferedReader.readLine();
                                        if (line == null) {
                                            break;
                                        }
                                        sb10.append(line);
                                    }
                                    bufferedReader.close();
                                    builder.setHttpErrorCode(httpsURLConnection.getResponseCode());
                                    if (n != 0) {
                                        state = HttpResponse.State.HTTP_ERROR;
                                    }
                                    else {
                                        state = HttpResponse.State.OK;
                                    }
                                    builder.setState(state);
                                    builder.setBody(sb10.toString());
                                    httpsURLConnection.disconnect();
                                    return builder.create();
                                }
                                break Label_0998;
                            }
                            catch (IOException ex) {
                                Logger.getInstance().debug("de.cisha.chess.util.HTTPHelper", "loading failed", ex);
                                builder.setState(HttpResponse.State.IO_ERROR);
                                return builder.create();
                            }
                        }
                        urlBody = "";
                        continue Label_0586_Outer;
                    }
                    s4 = "application/octet-stream";
                    continue Label_0828_Outer;
                }
                n = 0;
                continue;
            }
        }
    }
    
    public static HttpResponse loadUrlPostWithFile(final URL url, final Map<String, String> map, final String s, final InputStream inputStream) {
        return loadUrlPost(url, map, null, null);
    }
    
    public interface FileUploadInformation
    {
        String getMimeType();
        
        String getName();
        
        InputStream getStream();
    }
}
