/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;

public class HttpRequest {
    private static final String BOUNDARY = "00content0boundary00";
    public static final String CHARSET_UTF8 = "UTF-8";
    private static ConnectionFactory CONNECTION_FACTORY;
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data; boundary=00content0boundary00";
    private static final String CRLF = "\r\n";
    private static final String[] EMPTY_STRINGS;
    public static final String ENCODING_GZIP = "gzip";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_DATE = "Date";
    public static final String HEADER_ETAG = "ETag";
    public static final String HEADER_EXPIRES = "Expires";
    public static final String HEADER_IF_NONE_MATCH = "If-None-Match";
    public static final String HEADER_LAST_MODIFIED = "Last-Modified";
    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";
    public static final String HEADER_REFERER = "Referer";
    public static final String HEADER_SERVER = "Server";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_TRACE = "TRACE";
    public static final String PARAM_CHARSET = "charset";
    private int bufferSize = 8192;
    private HttpURLConnection connection = null;
    private boolean form;
    private String httpProxyHost;
    private int httpProxyPort;
    private boolean ignoreCloseExceptions = true;
    private boolean multipart;
    private RequestOutputStream output;
    private final String requestMethod;
    private boolean uncompress = false;
    public final URL url;

    static {
        EMPTY_STRINGS = new String[0];
        CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
    }

    public HttpRequest(CharSequence charSequence, String string) throws HttpRequestException {
        try {
            this.url = new URL(charSequence.toString());
            this.requestMethod = string;
            return;
        }
        catch (MalformedURLException malformedURLException) {
            throw new HttpRequestException(malformedURLException);
        }
    }

    public HttpRequest(URL uRL, String string) throws HttpRequestException {
        this.url = uRL;
        this.requestMethod = string;
    }

    private static StringBuilder addParamPrefix(String string, StringBuilder stringBuilder) {
        int n = string.indexOf(63);
        int n2 = stringBuilder.length() - 1;
        if (n == -1) {
            stringBuilder.append('?');
            return stringBuilder;
        }
        if (n < n2 && string.charAt(n2) != '&') {
            stringBuilder.append('&');
        }
        return stringBuilder;
    }

    private static StringBuilder addPathSeparator(String string, StringBuilder stringBuilder) {
        if (string.indexOf(58) + 2 == string.lastIndexOf(47)) {
            stringBuilder.append('/');
        }
        return stringBuilder;
    }

    public static String append(CharSequence charSequence, Map<?, ?> object) {
        Object object2 = charSequence.toString();
        if (object != null) {
            if (object.isEmpty()) {
                return object2;
            }
            charSequence = new StringBuilder((String)object2);
            HttpRequest.addPathSeparator((String)object2, (StringBuilder)charSequence);
            HttpRequest.addParamPrefix((String)object2, (StringBuilder)charSequence);
            object = object.entrySet().iterator();
            object2 = (Map.Entry)object.next();
            charSequence.append(object2.getKey().toString());
            charSequence.append('=');
            object2 = object2.getValue();
            if (object2 != null) {
                charSequence.append(object2);
            }
            while (object.hasNext()) {
                charSequence.append('&');
                object2 = (Map.Entry)object.next();
                charSequence.append(object2.getKey().toString());
                charSequence.append('=');
                if ((object2 = object2.getValue()) == null) continue;
                charSequence.append(object2);
            }
            return ((StringBuilder)charSequence).toString();
        }
        return object2;
    }

    public static /* varargs */ String append(CharSequence charSequence, Object ... arrobject) {
        Object object = charSequence.toString();
        if (arrobject != null) {
            if (arrobject.length == 0) {
                return object;
            }
            int n = 2;
            if (arrobject.length % 2 != 0) {
                throw new IllegalArgumentException("Must specify an even number of parameter names/values");
            }
            charSequence = new StringBuilder((String)object);
            HttpRequest.addPathSeparator((String)object, (StringBuilder)charSequence);
            HttpRequest.addParamPrefix((String)object, (StringBuilder)charSequence);
            charSequence.append(arrobject[0]);
            charSequence.append('=');
            object = arrobject[1];
            int n2 = n;
            if (object != null) {
                charSequence.append(object);
                n2 = n;
            }
            while (n2 < arrobject.length) {
                charSequence.append('&');
                charSequence.append(arrobject[n2]);
                charSequence.append('=');
                object = arrobject[n2 + 1];
                if (object != null) {
                    charSequence.append(object);
                }
                n2 += 2;
            }
            return ((StringBuilder)charSequence).toString();
        }
        return object;
    }

    private HttpURLConnection createConnection() {
        try {
            HttpURLConnection httpURLConnection = this.httpProxyHost != null ? CONNECTION_FACTORY.create(this.url, this.createProxy()) : CONNECTION_FACTORY.create(this.url);
            httpURLConnection.setRequestMethod(this.requestMethod);
            return httpURLConnection;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    private Proxy createProxy() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.httpProxyHost, this.httpProxyPort));
    }

    public static HttpRequest delete(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, METHOD_DELETE);
    }

    public static HttpRequest delete(CharSequence object, Map<?, ?> object2, boolean bl) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.delete((CharSequence)object);
    }

    public static /* varargs */ HttpRequest delete(CharSequence object, boolean bl, Object ... object2) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.delete((CharSequence)object);
    }

    public static HttpRequest delete(URL uRL) throws HttpRequestException {
        return new HttpRequest(uRL, METHOD_DELETE);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String encode(CharSequence object) throws HttpRequestException {
        int n;
        URL uRL;
        Object object2;
        block5 : {
            try {
                uRL = new URL(object.toString());
                object2 = uRL.getHost();
                n = uRL.getPort();
                object = object2;
                if (n == -1) break block5;
                object = new StringBuilder();
                object.append((String)object2);
                object.append(':');
                object.append(Integer.toString(n));
                object = object.toString();
            }
            catch (IOException iOException) {
                throw new HttpRequestException(iOException);
            }
        }
        try {
            object2 = new URI(uRL.getProtocol(), (String)object, uRL.getPath(), uRL.getQuery(), null).toASCIIString();
            n = object2.indexOf(63);
            object = object2;
            if (n <= 0) return object;
            object = object2;
            if (++n >= object2.length()) return object;
            object = new StringBuilder();
            object.append(object2.substring(0, n));
            object.append(object2.substring(n).replace("+", "%2B"));
            return object.toString();
        }
        catch (URISyntaxException uRISyntaxException) {
            object2 = new IOException("Parsing URI failed");
            object2.initCause(uRISyntaxException);
            throw new HttpRequestException((IOException)object2);
        }
    }

    public static HttpRequest get(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, METHOD_GET);
    }

    public static HttpRequest get(CharSequence object, Map<?, ?> object2, boolean bl) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.get((CharSequence)object);
    }

    public static /* varargs */ HttpRequest get(CharSequence object, boolean bl, Object ... object2) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.get((CharSequence)object);
    }

    public static HttpRequest get(URL uRL) throws HttpRequestException {
        return new HttpRequest(uRL, METHOD_GET);
    }

    private static String getValidCharset(String string) {
        if (string != null && string.length() > 0) {
            return string;
        }
        return CHARSET_UTF8;
    }

    public static HttpRequest head(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, METHOD_HEAD);
    }

    public static HttpRequest head(CharSequence object, Map<?, ?> object2, boolean bl) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.head((CharSequence)object);
    }

    public static /* varargs */ HttpRequest head(CharSequence object, boolean bl, Object ... object2) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.head((CharSequence)object);
    }

    public static HttpRequest head(URL uRL) throws HttpRequestException {
        return new HttpRequest(uRL, METHOD_HEAD);
    }

    public static void keepAlive(boolean bl) {
        HttpRequest.setProperty("http.keepAlive", Boolean.toString(bl));
    }

    public static /* varargs */ void nonProxyHosts(String ... arrstring) {
        if (arrstring != null && arrstring.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            int n = arrstring.length - 1;
            for (int i = 0; i < n; ++i) {
                stringBuilder.append(arrstring[i]);
                stringBuilder.append('|');
            }
            stringBuilder.append(arrstring[n]);
            HttpRequest.setProperty("http.nonProxyHosts", stringBuilder.toString());
            return;
        }
        HttpRequest.setProperty("http.nonProxyHosts", null);
    }

    public static HttpRequest options(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, METHOD_OPTIONS);
    }

    public static HttpRequest options(URL uRL) throws HttpRequestException {
        return new HttpRequest(uRL, METHOD_OPTIONS);
    }

    public static HttpRequest post(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, METHOD_POST);
    }

    public static HttpRequest post(CharSequence object, Map<?, ?> object2, boolean bl) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.post((CharSequence)object);
    }

    public static /* varargs */ HttpRequest post(CharSequence object, boolean bl, Object ... object2) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.post((CharSequence)object);
    }

    public static HttpRequest post(URL uRL) throws HttpRequestException {
        return new HttpRequest(uRL, METHOD_POST);
    }

    public static void proxyHost(String string) {
        HttpRequest.setProperty("http.proxyHost", string);
        HttpRequest.setProperty("https.proxyHost", string);
    }

    public static void proxyPort(int n) {
        String string = Integer.toString(n);
        HttpRequest.setProperty("http.proxyPort", string);
        HttpRequest.setProperty("https.proxyPort", string);
    }

    public static HttpRequest put(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, METHOD_PUT);
    }

    public static HttpRequest put(CharSequence object, Map<?, ?> object2, boolean bl) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.put((CharSequence)object);
    }

    public static /* varargs */ HttpRequest put(CharSequence object, boolean bl, Object ... object2) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.put((CharSequence)object);
    }

    public static HttpRequest put(URL uRL) throws HttpRequestException {
        return new HttpRequest(uRL, METHOD_PUT);
    }

    public static void setConnectionFactory(ConnectionFactory connectionFactory) {
        if (connectionFactory == null) {
            CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
            return;
        }
        CONNECTION_FACTORY = connectionFactory;
    }

    private static String setProperty(String privilegedAction, String string) {
        privilegedAction = string != null ? new PrivilegedAction<String>((String)((Object)privilegedAction), string){
            final /* synthetic */ String val$name;
            final /* synthetic */ String val$value;
            {
                this.val$name = string;
                this.val$value = string2;
            }

            @Override
            public String run() {
                return System.setProperty(this.val$name, this.val$value);
            }
        } : new PrivilegedAction<String>((String)((Object)privilegedAction)){
            final /* synthetic */ String val$name;
            {
                this.val$name = string;
            }

            @Override
            public String run() {
                return System.clearProperty(this.val$name);
            }
        };
        return (String)AccessController.doPrivileged(privilegedAction);
    }

    public static HttpRequest trace(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, METHOD_TRACE);
    }

    public static HttpRequest trace(URL uRL) throws HttpRequestException {
        return new HttpRequest(uRL, METHOD_TRACE);
    }

    public HttpRequest accept(String string) {
        return this.header(HEADER_ACCEPT, string);
    }

    public HttpRequest acceptCharset(String string) {
        return this.header(HEADER_ACCEPT_CHARSET, string);
    }

    public HttpRequest acceptEncoding(String string) {
        return this.header(HEADER_ACCEPT_ENCODING, string);
    }

    public HttpRequest acceptGzipEncoding() {
        return this.acceptEncoding(ENCODING_GZIP);
    }

    public HttpRequest acceptJson() {
        return this.accept(CONTENT_TYPE_JSON);
    }

    public HttpRequest authorization(String string) {
        return this.header(HEADER_AUTHORIZATION, string);
    }

    public boolean badRequest() throws HttpRequestException {
        if (400 == this.code()) {
            return true;
        }
        return false;
    }

    public HttpRequest basic(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Basic ");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(string);
        stringBuilder2.append(':');
        stringBuilder2.append(string2);
        stringBuilder.append(Base64.encode(stringBuilder2.toString()));
        return this.authorization(stringBuilder.toString());
    }

    public HttpRequest body(AtomicReference<String> atomicReference) throws HttpRequestException {
        atomicReference.set(this.body());
        return this;
    }

    public HttpRequest body(AtomicReference<String> atomicReference, String string) throws HttpRequestException {
        atomicReference.set(this.body(string));
        return this;
    }

    public String body() throws HttpRequestException {
        return this.body(this.charset());
    }

    public String body(String string) throws HttpRequestException {
        ByteArrayOutputStream byteArrayOutputStream = this.byteStream();
        try {
            this.copy(this.buffer(), byteArrayOutputStream);
            string = byteArrayOutputStream.toString(HttpRequest.getValidCharset(string));
            return string;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public BufferedInputStream buffer() throws HttpRequestException {
        return new BufferedInputStream(this.stream(), this.bufferSize);
    }

    public int bufferSize() {
        return this.bufferSize;
    }

    public HttpRequest bufferSize(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Size must be greater than zero");
        }
        this.bufferSize = n;
        return this;
    }

    public BufferedReader bufferedReader() throws HttpRequestException {
        return this.bufferedReader(this.charset());
    }

    public BufferedReader bufferedReader(String string) throws HttpRequestException {
        return new BufferedReader(this.reader(string), this.bufferSize);
    }

    protected ByteArrayOutputStream byteStream() {
        int n = this.contentLength();
        if (n > 0) {
            return new ByteArrayOutputStream(n);
        }
        return new ByteArrayOutputStream();
    }

    public byte[] bytes() throws HttpRequestException {
        ByteArrayOutputStream byteArrayOutputStream = this.byteStream();
        try {
            this.copy(this.buffer(), byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public String cacheControl() {
        return this.header(HEADER_CACHE_CONTROL);
    }

    public String charset() {
        return this.parameter(HEADER_CONTENT_TYPE, PARAM_CHARSET);
    }

    public HttpRequest chunk(int n) {
        this.getConnection().setChunkedStreamingMode(n);
        return this;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected HttpRequest closeOutput() throws IOException {
        block5 : {
            if (this.output == null) {
                return this;
            }
            if (this.multipart) {
                this.output.write("\r\n--00content0boundary00--\r\n");
            }
            if (this.ignoreCloseExceptions) {
                this.output.close();
            }
            this.output.close();
            break block5;
            catch (IOException iOException) {}
        }
        this.output = null;
        return this;
    }

    protected HttpRequest closeOutputQuietly() throws HttpRequestException {
        try {
            HttpRequest httpRequest = this.closeOutput();
            return httpRequest;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public int code() throws HttpRequestException {
        try {
            this.closeOutput();
            int n = this.getConnection().getResponseCode();
            return n;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public HttpRequest code(AtomicInteger atomicInteger) throws HttpRequestException {
        atomicInteger.set(this.code());
        return this;
    }

    public HttpRequest connectTimeout(int n) {
        this.getConnection().setConnectTimeout(n);
        return this;
    }

    public String contentEncoding() {
        return this.header(HEADER_CONTENT_ENCODING);
    }

    public int contentLength() {
        return this.intHeader(HEADER_CONTENT_LENGTH);
    }

    public HttpRequest contentLength(int n) {
        this.getConnection().setFixedLengthStreamingMode(n);
        return this;
    }

    public HttpRequest contentLength(String string) {
        return this.contentLength(Integer.parseInt(string));
    }

    public HttpRequest contentType(String string) {
        return this.contentType(string, null);
    }

    public HttpRequest contentType(String string, String string2) {
        if (string2 != null && string2.length() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("; charset=");
            stringBuilder.append(string2);
            return this.header(HEADER_CONTENT_TYPE, stringBuilder.toString());
        }
        return this.header(HEADER_CONTENT_TYPE, string);
    }

    public String contentType() {
        return this.header(HEADER_CONTENT_TYPE);
    }

    protected HttpRequest copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        return (HttpRequest)new CloseOperation<HttpRequest>((Closeable)inputStream, this.ignoreCloseExceptions){

            @Override
            public HttpRequest run() throws IOException {
                int n;
                byte[] arrby = new byte[HttpRequest.this.bufferSize];
                while ((n = inputStream.read(arrby)) != -1) {
                    outputStream.write(arrby, 0, n);
                }
                return HttpRequest.this;
            }
        }.call();
    }

    protected HttpRequest copy(final Reader reader, final Writer writer) throws IOException {
        return (HttpRequest)new CloseOperation<HttpRequest>((Closeable)reader, this.ignoreCloseExceptions){

            @Override
            public HttpRequest run() throws IOException {
                int n;
                char[] arrc = new char[HttpRequest.this.bufferSize];
                while ((n = reader.read(arrc)) != -1) {
                    writer.write(arrc, 0, n);
                }
                return HttpRequest.this;
            }
        }.call();
    }

    public boolean created() throws HttpRequestException {
        if (201 == this.code()) {
            return true;
        }
        return false;
    }

    public long date() {
        return this.dateHeader(HEADER_DATE);
    }

    public long dateHeader(String string) throws HttpRequestException {
        return this.dateHeader(string, -1L);
    }

    public long dateHeader(String string, long l) throws HttpRequestException {
        this.closeOutputQuietly();
        return this.getConnection().getHeaderFieldDate(string, l);
    }

    public HttpRequest disconnect() {
        this.getConnection().disconnect();
        return this;
    }

    public String eTag() {
        return this.header(HEADER_ETAG);
    }

    public long expires() {
        return this.dateHeader(HEADER_EXPIRES);
    }

    public HttpRequest followRedirects(boolean bl) {
        this.getConnection().setInstanceFollowRedirects(bl);
        return this;
    }

    public HttpRequest form(Object object, Object object2) throws HttpRequestException {
        return this.form(object, object2, CHARSET_UTF8);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HttpRequest form(Object object, Object object2, String string) throws HttpRequestException {
        boolean bl = this.form ^ true;
        if (bl) {
            this.contentType(CONTENT_TYPE_FORM, string);
            this.form = true;
        }
        string = HttpRequest.getValidCharset(string);
        try {
            this.openOutput();
            if (!bl) {
                this.output.write(38);
            }
            this.output.write(URLEncoder.encode(object.toString(), string));
            this.output.write(61);
            if (object2 != null) {
                this.output.write(URLEncoder.encode(object2.toString(), string));
            }
            return this;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public HttpRequest form(Map.Entry<?, ?> entry) throws HttpRequestException {
        return this.form(entry, CHARSET_UTF8);
    }

    public HttpRequest form(Map.Entry<?, ?> entry, String string) throws HttpRequestException {
        return this.form(entry.getKey(), entry.getValue(), string);
    }

    public HttpRequest form(Map<?, ?> map) throws HttpRequestException {
        return this.form(map, CHARSET_UTF8);
    }

    public HttpRequest form(Map<?, ?> object, String string) throws HttpRequestException {
        if (!object.isEmpty()) {
            object = object.entrySet().iterator();
            while (object.hasNext()) {
                this.form((Map.Entry)object.next(), string);
            }
        }
        return this;
    }

    public HttpURLConnection getConnection() {
        if (this.connection == null) {
            this.connection = this.createConnection();
        }
        return this.connection;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected String getParam(String var1_1, String var2_2) {
        if (var1_1 == null) return null;
        if (var1_1.length() == 0) {
            return null;
        }
        var6_3 = var1_1.length();
        var5_4 = var1_1.indexOf(59) + 1;
        if (var5_4 == 0) return null;
        if (var5_4 == var6_3) {
            return null;
        }
        var7_5 = var1_1.indexOf(59, var5_4);
        var4_6 = var5_4;
        var3_7 = var7_5;
        if (var7_5 != -1) ** GOTO lbl16
        var4_6 = var5_4;
        do {
            var3_7 = var6_3;
lbl16: // 2 sources:
            do {
                if (var4_6 >= var3_7) return null;
                var5_4 = var1_1.indexOf(61, var4_6);
                if (var5_4 != -1 && var5_4 < var3_7 && var2_2.equals(var1_1.substring(var4_6, var5_4).trim()) && (var4_6 = (var8_8 = var1_1.substring(var5_4 + 1, var3_7).trim()).length()) != 0) {
                    if (var4_6 <= 2) return var8_8;
                    if ('\"' != var8_8.charAt(0)) return var8_8;
                    var3_7 = var4_6 - 1;
                    if ('\"' != var8_8.charAt(var3_7)) return var8_8;
                    return var8_8.substring(1, var3_7);
                }
                var5_4 = var3_7 + 1;
                var7_5 = var1_1.indexOf(59, var5_4);
                var4_6 = var5_4;
                var3_7 = var7_5;
            } while (var7_5 != -1);
            var4_6 = var5_4;
        } while (true);
    }

    protected Map<String, String> getParams(String string) {
        if (string != null && string.length() != 0) {
            int n = string.length();
            int n2 = string.indexOf(59) + 1;
            if (n2 != 0 && n2 != n) {
                int n3;
                int n4 = n3 = string.indexOf(59, n2);
                if (n3 == -1) {
                    n4 = n;
                }
                LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
                while (n2 < n4) {
                    String string2;
                    String string3;
                    n3 = string.indexOf(61, n2);
                    if (n3 != -1 && n3 < n4 && (string3 = string.substring(n2, n3).trim()).length() > 0 && (n2 = (string2 = string.substring(n3 + 1, n4).trim()).length()) != 0) {
                        if (n2 > 2 && '\"' == string2.charAt(0) && '\"' == string2.charAt(--n2)) {
                            linkedHashMap.put(string3, string2.substring(1, n2));
                        } else {
                            linkedHashMap.put(string3, string2);
                        }
                    }
                    n3 = n4 + 1;
                    int n5 = string.indexOf(59, n3);
                    n2 = n3;
                    n4 = n5;
                    if (n5 != -1) continue;
                    n4 = n;
                    n2 = n3;
                }
                return linkedHashMap;
            }
            return Collections.emptyMap();
        }
        return Collections.emptyMap();
    }

    public HttpRequest header(String string, Number object) {
        object = object != null ? object.toString() : null;
        return this.header(string, (String)object);
    }

    public HttpRequest header(String string, String string2) {
        this.getConnection().setRequestProperty(string, string2);
        return this;
    }

    public HttpRequest header(Map.Entry<String, String> entry) {
        return this.header(entry.getKey(), entry.getValue());
    }

    public String header(String string) throws HttpRequestException {
        this.closeOutputQuietly();
        return this.getConnection().getHeaderField(string);
    }

    public HttpRequest headers(Map<String, String> object) {
        if (!object.isEmpty()) {
            object = object.entrySet().iterator();
            while (object.hasNext()) {
                this.header((Map.Entry)object.next());
            }
        }
        return this;
    }

    public Map<String, List<String>> headers() throws HttpRequestException {
        this.closeOutputQuietly();
        return this.getConnection().getHeaderFields();
    }

    public String[] headers(String object) {
        Map<String, List<String>> map = this.headers();
        if (map != null && !map.isEmpty()) {
            if ((object = map.get(object)) != null && !object.isEmpty()) {
                return object.toArray(new String[object.size()]);
            }
            return EMPTY_STRINGS;
        }
        return EMPTY_STRINGS;
    }

    public HttpRequest ifModifiedSince(long l) {
        this.getConnection().setIfModifiedSince(l);
        return this;
    }

    public HttpRequest ifNoneMatch(String string) {
        return this.header(HEADER_IF_NONE_MATCH, string);
    }

    public HttpRequest ignoreCloseExceptions(boolean bl) {
        this.ignoreCloseExceptions = bl;
        return this;
    }

    public boolean ignoreCloseExceptions() {
        return this.ignoreCloseExceptions;
    }

    public int intHeader(String string) throws HttpRequestException {
        return this.intHeader(string, -1);
    }

    public int intHeader(String string, int n) throws HttpRequestException {
        this.closeOutputQuietly();
        return this.getConnection().getHeaderFieldInt(string, n);
    }

    public boolean isBodyEmpty() throws HttpRequestException {
        if (this.contentLength() == 0) {
            return true;
        }
        return false;
    }

    public long lastModified() {
        return this.dateHeader(HEADER_LAST_MODIFIED);
    }

    public String location() {
        return this.header(HEADER_LOCATION);
    }

    public String message() throws HttpRequestException {
        try {
            this.closeOutput();
            String string = this.getConnection().getResponseMessage();
            return string;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public String method() {
        return this.getConnection().getRequestMethod();
    }

    public boolean notFound() throws HttpRequestException {
        if (404 == this.code()) {
            return true;
        }
        return false;
    }

    public boolean notModified() throws HttpRequestException {
        if (304 == this.code()) {
            return true;
        }
        return false;
    }

    public boolean ok() throws HttpRequestException {
        if (200 == this.code()) {
            return true;
        }
        return false;
    }

    protected HttpRequest openOutput() throws IOException {
        if (this.output != null) {
            return this;
        }
        this.getConnection().setDoOutput(true);
        String string = this.getParam(this.getConnection().getRequestProperty(HEADER_CONTENT_TYPE), PARAM_CHARSET);
        this.output = new RequestOutputStream(this.getConnection().getOutputStream(), string, this.bufferSize);
        return this;
    }

    public String parameter(String string, String string2) {
        return this.getParam(this.header(string), string2);
    }

    public Map<String, String> parameters(String string) {
        return this.getParams(this.header(string));
    }

    public HttpRequest part(String string, File file) throws HttpRequestException {
        return this.part(string, null, file);
    }

    public HttpRequest part(String string, InputStream inputStream) throws HttpRequestException {
        return this.part(string, null, null, inputStream);
    }

    public HttpRequest part(String string, Number number) throws HttpRequestException {
        return this.part(string, null, number);
    }

    public HttpRequest part(String string, String string2) {
        return this.part(string, null, string2);
    }

    public HttpRequest part(String string, String string2, File file) throws HttpRequestException {
        return this.part(string, string2, null, file);
    }

    public HttpRequest part(String string, String string2, Number object) throws HttpRequestException {
        object = object != null ? object.toString() : null;
        return this.part(string, string2, (String)object);
    }

    public HttpRequest part(String string, String string2, String string3) throws HttpRequestException {
        return this.part(string, string2, null, string3);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public HttpRequest part(String object, String string, String string2, File object2) throws HttpRequestException {
        Object object3;
        void var1_6;
        block11 : {
            InputStream inputStream = null;
            object3 = null;
            object2 = new BufferedInputStream(new FileInputStream((File)object2));
            object = this.part((String)object, string, string2, (InputStream)object2);
            if (object2 == null) return object;
            try {
                object2.close();
                return object;
            }
            catch (IOException iOException) {
                return object;
            }
            catch (Throwable throwable) {
                object3 = object2;
                break block11;
            }
            catch (IOException iOException) {
                object3 = object2;
                throw new HttpRequestException((IOException)object);
            }
            catch (Throwable throwable) {
                break block11;
            }
            catch (IOException iOException) {
                object3 = inputStream;
            }
            {
                throw new HttpRequestException((IOException)object);
            }
        }
        if (object3 == null) throw var1_6;
        try {
            object3.close();
        }
        catch (IOException iOException) {
            throw var1_6;
        }
        throw var1_6;
    }

    public HttpRequest part(String string, String string2, String string3, InputStream inputStream) throws HttpRequestException {
        try {
            this.startPart();
            this.writePartHeader(string, string2, string3);
            this.copy(inputStream, this.output);
            return this;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public HttpRequest part(String string, String string2, String string3, String string4) throws HttpRequestException {
        try {
            this.startPart();
            this.writePartHeader(string, string2, string3);
            this.output.write(string4);
            return this;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public HttpRequest partHeader(String string, String string2) throws HttpRequestException {
        return this.send(string).send(": ").send(string2).send(CRLF);
    }

    public HttpRequest proxyAuthorization(String string) {
        return this.header(HEADER_PROXY_AUTHORIZATION, string);
    }

    public HttpRequest proxyBasic(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Basic ");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(string);
        stringBuilder2.append(':');
        stringBuilder2.append(string2);
        stringBuilder.append(Base64.encode(stringBuilder2.toString()));
        return this.proxyAuthorization(stringBuilder.toString());
    }

    public HttpRequest readTimeout(int n) {
        this.getConnection().setReadTimeout(n);
        return this;
    }

    public InputStreamReader reader() throws HttpRequestException {
        return this.reader(this.charset());
    }

    public InputStreamReader reader(String object) throws HttpRequestException {
        try {
            object = new InputStreamReader(this.stream(), HttpRequest.getValidCharset((String)object));
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new HttpRequestException(unsupportedEncodingException);
        }
    }

    public HttpRequest receive(File object) throws HttpRequestException {
        try {
            object = new BufferedOutputStream(new FileOutputStream((File)object), this.bufferSize);
        }
        catch (FileNotFoundException fileNotFoundException) {
            throw new HttpRequestException(fileNotFoundException);
        }
        return (HttpRequest)new CloseOperation<HttpRequest>((Closeable)object, this.ignoreCloseExceptions, (OutputStream)object){
            final /* synthetic */ OutputStream val$output;
            {
                this.val$output = outputStream;
                super(closeable, bl);
            }

            @Override
            protected HttpRequest run() throws HttpRequestException, IOException {
                return HttpRequest.this.receive(this.val$output);
            }
        }.call();
    }

    public HttpRequest receive(OutputStream object) throws HttpRequestException {
        try {
            object = this.copy(this.buffer(), (OutputStream)object);
            return object;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public HttpRequest receive(PrintStream printStream) throws HttpRequestException {
        return this.receive((OutputStream)printStream);
    }

    public HttpRequest receive(final Writer writer) throws HttpRequestException {
        final BufferedReader bufferedReader = this.bufferedReader();
        return (HttpRequest)new CloseOperation<HttpRequest>((Closeable)bufferedReader, this.ignoreCloseExceptions){

            @Override
            public HttpRequest run() throws IOException {
                return HttpRequest.this.copy(bufferedReader, writer);
            }
        }.call();
    }

    public HttpRequest receive(final Appendable appendable) throws HttpRequestException {
        final BufferedReader bufferedReader = this.bufferedReader();
        return (HttpRequest)new CloseOperation<HttpRequest>((Closeable)bufferedReader, this.ignoreCloseExceptions){

            @Override
            public HttpRequest run() throws IOException {
                int n;
                CharBuffer charBuffer = CharBuffer.allocate(HttpRequest.this.bufferSize);
                while ((n = bufferedReader.read(charBuffer)) != -1) {
                    charBuffer.rewind();
                    appendable.append(charBuffer, 0, n);
                    charBuffer.rewind();
                }
                return HttpRequest.this;
            }
        }.call();
    }

    public HttpRequest referer(String string) {
        return this.header(HEADER_REFERER, string);
    }

    public HttpRequest send(File object) throws HttpRequestException {
        try {
            object = new BufferedInputStream(new FileInputStream((File)object));
            return this.send((InputStream)object);
        }
        catch (FileNotFoundException fileNotFoundException) {
            throw new HttpRequestException(fileNotFoundException);
        }
    }

    public HttpRequest send(InputStream inputStream) throws HttpRequestException {
        try {
            this.openOutput();
            this.copy(inputStream, this.output);
            return this;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public HttpRequest send(final Reader reader) throws HttpRequestException {
        OutputStreamWriter outputStreamWriter;
        try {
            this.openOutput();
            outputStreamWriter = new OutputStreamWriter((OutputStream)this.output, this.output.encoder.charset());
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
        return (HttpRequest)new FlushOperation<HttpRequest>((Flushable)outputStreamWriter){

            @Override
            protected HttpRequest run() throws IOException {
                return HttpRequest.this.copy(reader, outputStreamWriter);
            }
        }.call();
    }

    public HttpRequest send(CharSequence charSequence) throws HttpRequestException {
        try {
            this.openOutput();
            this.output.write(charSequence.toString());
            return this;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public HttpRequest send(byte[] arrby) throws HttpRequestException {
        return this.send(new ByteArrayInputStream(arrby));
    }

    public String server() {
        return this.header(HEADER_SERVER);
    }

    public boolean serverError() throws HttpRequestException {
        if (500 == this.code()) {
            return true;
        }
        return false;
    }

    protected HttpRequest startPart() throws IOException {
        if (!this.multipart) {
            this.multipart = true;
            this.contentType(CONTENT_TYPE_MULTIPART).openOutput();
            this.output.write("--00content0boundary00\r\n");
            return this;
        }
        this.output.write("\r\n--00content0boundary00\r\n");
        return this;
    }

    public InputStream stream() throws HttpRequestException {
        InputStream inputStream;
        InputStream inputStream2;
        if (this.code() < 400) {
            try {
                inputStream2 = this.getConnection().getInputStream();
            }
            catch (IOException iOException) {
                throw new HttpRequestException(iOException);
            }
        }
        inputStream2 = inputStream = this.getConnection().getErrorStream();
        if (inputStream == null) {
            try {
                inputStream2 = this.getConnection().getInputStream();
            }
            catch (IOException iOException) {
                throw new HttpRequestException(iOException);
            }
        }
        if (this.uncompress) {
            if (!ENCODING_GZIP.equals(this.contentEncoding())) {
                return inputStream2;
            }
            try {
                inputStream2 = new GZIPInputStream(inputStream2);
                return inputStream2;
            }
            catch (IOException iOException) {
                throw new HttpRequestException(iOException);
            }
        }
        return inputStream2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.method());
        stringBuilder.append(' ');
        stringBuilder.append(this.url());
        return stringBuilder.toString();
    }

    public HttpRequest trustAllCerts() throws HttpRequestException {
        return this;
    }

    public HttpRequest trustAllHosts() {
        return this;
    }

    public HttpRequest uncompress(boolean bl) {
        this.uncompress = bl;
        return this;
    }

    public URL url() {
        return this.getConnection().getURL();
    }

    public HttpRequest useCaches(boolean bl) {
        this.getConnection().setUseCaches(bl);
        return this;
    }

    public HttpRequest useProxy(String string, int n) {
        if (this.connection != null) {
            throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
        }
        this.httpProxyHost = string;
        this.httpProxyPort = n;
        return this;
    }

    public HttpRequest userAgent(String string) {
        return this.header(HEADER_USER_AGENT, string);
    }

    protected HttpRequest writePartHeader(String string, String string2) throws IOException {
        return this.writePartHeader(string, string2, null);
    }

    protected HttpRequest writePartHeader(String string, String string2, String string3) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("form-data; name=\"");
        stringBuilder.append(string);
        if (string2 != null) {
            stringBuilder.append("\"; filename=\"");
            stringBuilder.append(string2);
        }
        stringBuilder.append('\"');
        this.partHeader("Content-Disposition", stringBuilder.toString());
        if (string3 != null) {
            this.partHeader(HEADER_CONTENT_TYPE, string3);
        }
        return this.send(CRLF);
    }

    public OutputStreamWriter writer() throws HttpRequestException {
        try {
            this.openOutput();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)this.output, this.output.encoder.charset());
            return outputStreamWriter;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public static class Base64 {
        private static final byte EQUALS_SIGN = 61;
        private static final String PREFERRED_ENCODING = "US-ASCII";
        private static final byte[] _STANDARD_ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

        private Base64() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static String encode(String arrby) {
            try {
                byte[] arrby2;
                arrby = arrby2 = arrby.getBytes(PREFERRED_ENCODING);
                return Base64.encodeBytes(arrby);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {}
            arrby = arrby.getBytes();
            return Base64.encodeBytes(arrby);
        }

        private static byte[] encode3to4(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
            byte[] arrby3 = _STANDARD_ALPHABET;
            int n4 = 0;
            int n5 = n2 > 0 ? arrby[n] << 24 >>> 8 : 0;
            int n6 = n2 > 1 ? arrby[n + 1] << 24 >>> 16 : 0;
            if (n2 > 2) {
                n4 = arrby[n + 2] << 24 >>> 24;
            }
            n = n5 | n6 | n4;
            switch (n2) {
                default: {
                    return arrby2;
                }
                case 3: {
                    arrby2[n3] = arrby3[n >>> 18];
                    arrby2[n3 + 1] = arrby3[n >>> 12 & 63];
                    arrby2[n3 + 2] = arrby3[n >>> 6 & 63];
                    arrby2[n3 + 3] = arrby3[n & 63];
                    return arrby2;
                }
                case 2: {
                    arrby2[n3] = arrby3[n >>> 18];
                    arrby2[n3 + 1] = arrby3[n >>> 12 & 63];
                    arrby2[n3 + 2] = arrby3[n >>> 6 & 63];
                    arrby2[n3 + 3] = 61;
                    return arrby2;
                }
                case 1: 
            }
            arrby2[n3] = arrby3[n >>> 18];
            arrby2[n3 + 1] = arrby3[n >>> 12 & 63];
            arrby2[n3 + 2] = 61;
            arrby2[n3 + 3] = 61;
            return arrby2;
        }

        public static String encodeBytes(byte[] arrby) {
            return Base64.encodeBytes(arrby, 0, arrby.length);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public static String encodeBytes(byte[] arrby, int n, int n2) {
            arrby = Base64.encodeBytesToBytes(arrby, n, n2);
            try {
                return new String(arrby, PREFERRED_ENCODING);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                return new String(arrby);
            }
        }

        public static byte[] encodeBytesToBytes(byte[] object, int n, int n2) {
            if (object == null) {
                throw new NullPointerException("Cannot serialize a null array.");
            }
            if (n < 0) {
                object = new StringBuilder();
                object.append("Cannot have negative offset: ");
                object.append(n);
                throw new IllegalArgumentException(object.toString());
            }
            if (n2 < 0) {
                object = new StringBuilder();
                object.append("Cannot have length offset: ");
                object.append(n2);
                throw new IllegalArgumentException(object.toString());
            }
            if (n + n2 > ((byte[])object).length) {
                throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Cannot have offset of %d and length of %d with array of length %d", n, n2, ((Object)object).length));
            }
            int n3 = n2 / 3;
            int n4 = 4;
            if (n2 % 3 <= 0) {
                n4 = 0;
            }
            byte[] arrby = new byte[n3 * 4 + n4];
            n4 = n3 = 0;
            while (n3 < n2 - 2) {
                Base64.encode3to4((byte[])object, n3 + n, 3, arrby, n4);
                n3 += 3;
                n4 += 4;
            }
            int n5 = n4;
            if (n3 < n2) {
                Base64.encode3to4((byte[])object, n + n3, n2 - n3, arrby, n4);
                n5 = n4 + 4;
            }
            if (n5 <= arrby.length - 1) {
                object = new byte[n5];
                System.arraycopy(arrby, 0, object, 0, n5);
                return object;
            }
            return arrby;
        }
    }

    protected static abstract class CloseOperation<V>
    extends Operation<V> {
        private final Closeable closeable;
        private final boolean ignoreCloseExceptions;

        protected CloseOperation(Closeable closeable, boolean bl) {
            this.closeable = closeable;
            this.ignoreCloseExceptions = bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected void done() throws IOException {
            if (this.closeable instanceof Flushable) {
                ((Flushable)((Object)this.closeable)).flush();
            }
            if (!this.ignoreCloseExceptions) {
                this.closeable.close();
                return;
            }
            try {
                this.closeable.close();
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }
    }

    public static interface ConnectionFactory {
        public static final ConnectionFactory DEFAULT = new ConnectionFactory(){

            @Override
            public HttpURLConnection create(URL uRL) throws IOException {
                return (HttpURLConnection)uRL.openConnection();
            }

            @Override
            public HttpURLConnection create(URL uRL, Proxy proxy) throws IOException {
                return (HttpURLConnection)uRL.openConnection(proxy);
            }
        };

        public HttpURLConnection create(URL var1) throws IOException;

        public HttpURLConnection create(URL var1, Proxy var2) throws IOException;

    }

    protected static abstract class FlushOperation<V>
    extends Operation<V> {
        private final Flushable flushable;

        protected FlushOperation(Flushable flushable) {
            this.flushable = flushable;
        }

        @Override
        protected void done() throws IOException {
            this.flushable.flush();
        }
    }

    public static class HttpRequestException
    extends RuntimeException {
        private static final long serialVersionUID = -1170466989781746231L;

        protected HttpRequestException(IOException iOException) {
            super(iOException);
        }

        @Override
        public IOException getCause() {
            return (IOException)super.getCause();
        }
    }

    protected static abstract class Operation<V>
    implements Callable<V> {
        protected Operation() {
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public V call() throws HttpRequestException {
            void var2_8;
            boolean bl = true;
            V v = this.run();
            try {
                this.done();
            }
            catch (IOException iOException) {
                throw new HttpRequestException(iOException);
            }
            return v;
            catch (Throwable throwable) {
                bl = false;
            }
            catch (IOException iOException) {
                try {
                    throw new HttpRequestException(iOException);
                    catch (HttpRequestException httpRequestException) {
                        throw httpRequestException;
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            try {
                this.done();
                throw var2_8;
            }
            catch (IOException iOException) {
                if (bl) throw var2_8;
                throw new HttpRequestException(iOException);
            }
        }

        protected abstract void done() throws IOException;

        protected abstract V run() throws HttpRequestException, IOException;
    }

    public static class RequestOutputStream
    extends BufferedOutputStream {
        private final CharsetEncoder encoder;

        public RequestOutputStream(OutputStream outputStream, String string, int n) {
            super(outputStream, n);
            this.encoder = Charset.forName(HttpRequest.getValidCharset(string)).newEncoder();
        }

        public RequestOutputStream write(String object) throws IOException {
            object = this.encoder.encode(CharBuffer.wrap((CharSequence)object));
            super.write(object.array(), 0, object.limit());
            return this;
        }
    }

}
