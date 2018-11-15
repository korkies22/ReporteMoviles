/*
 * Decompiled with CFR 0_134.
 */
package io.socket;

import io.socket.IOConnection;
import io.socket.XhrTransport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

private class XhrTransport.PollThread
extends Thread {
    private static final String CHARSET = "UTF-8";

    public XhrTransport.PollThread() {
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
                    if (XhrTransport.this.connection == null || XhrTransport.PollThread.interrupted()) break block14;
                    XhrTransport.this.connection.transportError(iOException);
                    return;
                }
            }
            try {
                XhrTransport.PollThread.sleep(100L);
            }
            catch (InterruptedException interruptedException) {
            }
        } while (true);
    }
}
