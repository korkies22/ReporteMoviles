/*
 * Decompiled with CFR 0_134.
 */
package io.socket;

import io.socket.IOConnection;
import io.socket.IOTransport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

class XhrTransport
implements IOTransport {
    public static final String TRANSPORT_NAME = "xhr-polling";
    private boolean blocked;
    private boolean connect;
    private IOConnection connection;
    PollThread pollThread = null;
    ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();
    private URL url;
    HttpURLConnection urlConnection;

    public XhrTransport(URL uRL, IOConnection iOConnection) {
        this.connection = iOConnection;
        this.url = uRL;
    }

    public static IOTransport create(URL object, IOConnection iOConnection) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object.toString());
            stringBuilder.append("/socket.io/1/");
            stringBuilder.append(TRANSPORT_NAME);
            stringBuilder.append("/");
            stringBuilder.append(iOConnection.getSessionId());
            object = new XhrTransport(new URL(stringBuilder.toString()), iOConnection);
            return object;
        }
        catch (MalformedURLException malformedURLException) {
            throw new RuntimeException("Malformed Internal url. This should never happen. Please report a bug.", malformedURLException);
        }
    }

    private boolean isBlocked() {
        synchronized (this) {
            boolean bl = this.blocked;
            return bl;
        }
    }

    private boolean isConnect() {
        synchronized (this) {
            boolean bl = this.connect;
            return bl;
        }
    }

    private void setBlocked(boolean bl) {
        synchronized (this) {
            this.blocked = bl;
            return;
        }
    }

    private void setConnect(boolean bl) {
        synchronized (this) {
            this.connect = bl;
            return;
        }
    }

    @Override
    public boolean canSendBulk() {
        return true;
    }

    @Override
    public void connect() {
        this.setConnect(true);
        this.pollThread = new PollThread();
        this.pollThread.start();
    }

    @Override
    public void disconnect() {
        this.setConnect(false);
        this.pollThread.interrupt();
    }

    @Override
    public String getName() {
        return TRANSPORT_NAME;
    }

    @Override
    public void invalidate() {
        this.connection = null;
    }

    @Override
    public void send(String string) throws IOException {
        this.sendBulk(new String[]{string});
    }

    @Override
    public void sendBulk(String[] arrstring) throws IOException {
        this.queue.addAll(Arrays.asList(arrstring));
        if (this.isBlocked()) {
            this.pollThread.interrupt();
            this.urlConnection.disconnect();
        }
    }

    private class PollThread
    extends Thread {
        private static final String CHARSET = "UTF-8";

        public PollThread() {
            super(XhrTransport.TRANSPORT_NAME);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            XhrTransport.this.connection.transportConnected();
            do {
                block14 : {
                    if (!XhrTransport.this.isConnect()) {
                        XhrTransport.this.connection.transportDisconnected();
                        return;
                    }
                    try {
                        byte[] arrby;
                        Object object = new StringBuilder();
                        object.append(XhrTransport.this.url.toString());
                        object.append("?t=");
                        object.append(System.currentTimeMillis());
                        object = new URL(object.toString());
                        XhrTransport.this.urlConnection = (HttpURLConnection)object.openConnection();
                        object = IOConnection.getSslContext();
                        if (XhrTransport.this.urlConnection instanceof HttpsURLConnection && object != null) {
                            ((HttpsURLConnection)XhrTransport.this.urlConnection).setSSLSocketFactory(object.getSocketFactory());
                        }
                        if (!XhrTransport.this.queue.isEmpty()) {
                            XhrTransport.this.urlConnection.setDoOutput(true);
                            object = XhrTransport.this.urlConnection.getOutputStream();
                            if (XhrTransport.this.queue.size() == 1) {
                                object.write(XhrTransport.this.queue.poll().getBytes(CHARSET));
                            } else {
                                arrby = XhrTransport.this.queue.iterator();
                                while (arrby.hasNext()) {
                                    String string = (String)arrby.next();
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("\ufffd");
                                    stringBuilder.append(string.length());
                                    stringBuilder.append("\ufffd");
                                    stringBuilder.append(string);
                                    object.write(stringBuilder.toString().getBytes(CHARSET));
                                    arrby.remove();
                                }
                            }
                            object.close();
                            object = XhrTransport.this.urlConnection.getInputStream();
                            arrby = new byte[1024];
                            while (object.read(arrby) > 0) {
                            }
                            object.close();
                        } else {
                            XhrTransport.this.setBlocked(true);
                            object = new BufferedReader(new InputStreamReader(XhrTransport.this.urlConnection.getInputStream(), CHARSET));
                            while ((arrby = object.readLine()) != null) {
                                if (XhrTransport.this.connection == null) continue;
                                XhrTransport.this.connection.transportData((String)arrby);
                            }
                            XhrTransport.this.setBlocked(false);
                        }
                    }
                    catch (IOException iOException) {
                        if (XhrTransport.this.connection == null || PollThread.interrupted()) break block14;
                        XhrTransport.this.connection.transportError(iOException);
                        return;
                    }
                }
                try {
                    PollThread.sleep(100L);
                }
                catch (InterruptedException interruptedException) {
                }
            } while (true);
        }
    }

}
