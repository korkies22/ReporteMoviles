/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.SSLCertificateSocketFactory
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.socket;

import android.net.SSLCertificateSocketFactory;
import android.util.Log;
import io.socket.ConnectionListener;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.IOMessage;
import io.socket.IOTransport;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import io.socket.WebsocketTransport;
import io.socket.XhrTransport;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class IOConnection
implements IOCallback {
    public static final String FRAME_DELIMITER = "\ufffd";
    public static final String SOCKET_IO_1 = "/socket.io/1/";
    private static final int STATE_CONNECTING = 2;
    private static final int STATE_HANDSHAKE = 1;
    private static final int STATE_INIT = 0;
    private static final int STATE_INTERRUPTED = 4;
    private static final int STATE_INVALID = 6;
    private static final int STATE_READY = 3;
    private static HashMap<String, List<IOConnection>> connections;
    static final Logger logger;
    private static SSLContext sslContext;
    private ConnectionListener _myDelegate;
    HashMap<Integer, IOAcknowledge> acknowledge = new HashMap();
    private final Timer backgroundTimer = new Timer("backgroundTimer");
    private long closingTimeout;
    private int connectTimeout = 10000;
    private SocketIO firstSocket = null;
    private Properties headers;
    private long heartbeatTimeout;
    private HearbeatTimeoutTask heartbeatTimeoutTask;
    private boolean keepAliveInQueue;
    private Exception lastException;
    private int nextId = 1;
    private ConcurrentLinkedQueue<String> outputBuffer = new ConcurrentLinkedQueue();
    private List<String> protocols;
    private ReconnectTask reconnectTask = null;
    private String sessionId;
    private HashMap<String, SocketIO> sockets = new HashMap();
    private int state = 0;
    private IOTransport transport;
    private URL url;
    private String urlStr;

    static {
        logger = Logger.getLogger("io.socket");
        connections = new HashMap();
    }

    private IOConnection(String string, SocketIO socketIO, ConnectionListener connectionListener) {
        try {
            this.url = new URL(string);
            this.urlStr = string;
            this._myDelegate = connectionListener;
            this.firstSocket = socketIO;
            this.headers = socketIO.getHeaders();
            this.sockets.put(socketIO.getNamespace(), socketIO);
        }
        catch (MalformedURLException malformedURLException) {
            throw new RuntimeException(malformedURLException);
        }
        new ConnectThread().start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void cleanup() {
        synchronized (this) {
            this.setState(6);
            if (this.transport != null) {
                this.transport.disconnect();
            }
            this.sockets.clear();
            HashMap<String, List<IOConnection>> hashMap = connections;
            synchronized (hashMap) {
                List<IOConnection> list = connections.get(this.urlStr);
                if (list != null && list.size() > 1) {
                    list.remove(this);
                } else {
                    connections.remove(this.urlStr);
                }
            }
            logger.info("Cleanup");
            this.backgroundTimer.cancel();
            return;
        }
    }

    private void connectTransport() {
        synchronized (this) {
            block9 : {
                block8 : {
                    block7 : {
                        block6 : {
                            int n = this.getState();
                            if (n != 6) break block6;
                            return;
                        }
                        this.setState(2);
                        if (!this.protocols.contains("websocket")) break block7;
                        this.transport = WebsocketTransport.create(this.url, this);
                        break block8;
                    }
                    if (!this.protocols.contains("xhr-polling")) break block9;
                    this.transport = XhrTransport.create(this.url, this);
                }
                this.transport.connect();
                return;
            }
            this.error(new SocketIOException("Server supports no available transports. You should reconfigure the server to support a available transport"));
            return;
        }
    }

    private void error(SocketIOException socketIOException) {
        Log.e((String)this.getClass().getName(), (String)"Exception:", (Throwable)socketIOException);
        Iterator<SocketIO> iterator = this.sockets.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().getCallback().onError(socketIOException);
        }
        this.cleanup();
    }

    private IOCallback findCallback(IOMessage iOMessage) throws SocketIOException {
        if ("".equals(iOMessage.getEndpoint())) {
            return this;
        }
        Object object = this.sockets.get(iOMessage.getEndpoint());
        if (object == null) {
            object = new StringBuilder();
            object.append("Cannot find socket for '");
            object.append(iOMessage.getEndpoint());
            object.append("'");
            throw new SocketIOException(object.toString());
        }
        return object.getCallback();
    }

    public static SSLContext getSslContext() {
        return sslContext;
    }

    private int getState() {
        synchronized (this) {
            int n = this.state;
            return n;
        }
    }

    private void handshake() {
        try {
            this.setState(1);
            String[] arrstring = new StringBuilder();
            arrstring.append(this.url.toString());
            arrstring.append(SOCKET_IO_1);
            arrstring = new URL(arrstring.toString()).openConnection();
            if (arrstring instanceof HttpsURLConnection) {
                ((HttpsURLConnection)arrstring).setSSLSocketFactory((SSLSocketFactory)SSLCertificateSocketFactory.getDefault());
            }
            arrstring.setConnectTimeout(this.connectTimeout);
            arrstring.setReadTimeout(this.connectTimeout);
            for (Map.Entry entry : this.headers.entrySet()) {
                arrstring.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
            }
            arrstring = new Scanner(arrstring.getInputStream()).nextLine().split(":");
            this.sessionId = arrstring[0];
            this.closingTimeout = this.heartbeatTimeout = Long.parseLong(arrstring[1]) * 1000L;
            this.protocols = Arrays.asList(arrstring[3].split(","));
            return;
        }
        catch (Exception exception) {
            this.error(new SocketIOException("Error while handshaking", exception));
            return;
        }
    }

    private void invalidateTransport() {
        if (this.transport != null) {
            this.transport.invalidate();
        }
        this.transport = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static IOConnection register(String object, SocketIO socketIO, ConnectionListener connectionListener) {
        List<IOConnection> list = connections.get(object);
        if (list == null) {
            list = new LinkedList<IOConnection>();
            connections.put((String)object, list);
        } else {
            synchronized (list) {
                for (IOConnection iOConnection : list) {
                    if (!iOConnection.register(socketIO)) continue;
                    return iOConnection;
                }
            }
        }
        object = new IOConnection((String)object, socketIO, connectionListener);
        list.add((IOConnection)object);
        return object;
    }

    private IOAcknowledge remoteAcknowledge(IOMessage iOMessage) {
        String string = iOMessage.getId();
        if (string.equals("")) {
            return null;
        }
        CharSequence charSequence = string;
        if (!string.endsWith("+")) {
            charSequence = new StringBuilder();
            charSequence.append(string);
            charSequence.append("+");
            charSequence = charSequence.toString();
        }
        return new IOAcknowledge(iOMessage.getEndpoint(), (String)charSequence){
            final /* synthetic */ String val$endPoint;
            final /* synthetic */ String val$id;
            {
                this.val$endPoint = string;
                this.val$id = string2;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public /* varargs */ void ack(Object ... var1_1) {
                var6_2 = new JSONArray();
                var2_3 = 0;
                var3_4 = ((Object)var1_1).length;
                do {
                    block4 : {
                        if (var2_3 >= var3_4) {
                            var1_1 = this.val$endPoint;
                            var4_5 = new StringBuilder();
                            var4_5.append(this.val$id);
                            var4_5.append(var6_2.toString());
                            var1_1 = new IOMessage(6, (String)var1_1, var4_5.toString());
                            IOConnection.access$400(IOConnection.this, var1_1.toString());
                            return;
                        }
                        var4_5 = var5_7 = var1_1[var2_3];
                        if (var5_7 != null) ** GOTO lbl17
                        try {
                            var4_5 = JSONObject.NULL;
lbl17: // 2 sources:
                            var6_2.put(var4_5);
                            break block4;
                        }
                        catch (Exception var4_6) {}
                        IOConnection.access$100(IOConnection.this, new SocketIOException("You can only put values in IOAcknowledge.ack() which can be handled by JSONArray.put()", var4_6));
                    }
                    ++var2_3;
                } while (true);
            }
        };
    }

    private void resetTimeout() {
        synchronized (this) {
            if (this.heartbeatTimeoutTask != null) {
                this.heartbeatTimeoutTask.cancel();
            }
            if (this.getState() != 6) {
                this.heartbeatTimeoutTask = new HearbeatTimeoutTask();
                this.backgroundTimer.schedule((TimerTask)this.heartbeatTimeoutTask, this.closingTimeout + this.heartbeatTimeout);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void sendPlain(String string) {
        // MONITORENTER : this
        int n = this.getState();
        if (n != 3) {
            this.outputBuffer.add(string);
            // MONITOREXIT : this
            return;
        }
        try {
            Logger logger = IOConnection.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("> ");
            stringBuilder.append(string);
            logger.info(stringBuilder.toString());
            this.transport.send(string);
            return;
        }
        catch (Exception exception) {}
        logger.info("IOEx: saving");
        this.outputBuffer.add(string);
    }

    public static void setSslContext(SSLContext sSLContext) {
        sslContext = sSLContext;
    }

    private void setState(int n) {
        synchronized (this) {
            if (this.state != 6) {
                this.state = n;
            }
            return;
        }
    }

    private void synthesizeAck(IOMessage iOMessage, IOAcknowledge object) {
        if (object != null) {
            int n = this.nextId;
            this.nextId = n + 1;
            this.acknowledge.put(n, (IOAcknowledge)object);
            object = new StringBuilder();
            object.append(n);
            object.append("+");
            iOMessage.setId(object.toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* varargs */ void emit(SocketIO object, String string, IOAcknowledge iOAcknowledge, Object ... arrobject) {
        try {
            string = new JSONObject().put("name", (Object)string).put("args", (Object)new JSONArray(Arrays.asList(arrobject)));
            object = new IOMessage(5, object.getNamespace(), string.toString());
            this.synthesizeAck((IOMessage)object, iOAcknowledge);
            this.sendPlain(object.toString());
            return;
        }
        catch (JSONException jSONException) {}
        this.error(new SocketIOException("Error while emitting an event. Make sure you only try to send arguments, which can be serialized into JSON."));
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public IOTransport getTransport() {
        return this.transport;
    }

    public boolean isConnected() {
        if (this.getState() == 3) {
            return true;
        }
        return false;
    }

    @Override
    public /* varargs */ void on(String string, IOAcknowledge iOAcknowledge, Object ... arrobject) {
        Iterator<SocketIO> iterator = this.sockets.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().getCallback().on(string, iOAcknowledge, arrobject);
        }
    }

    @Override
    public void onConnect() {
        SocketIO socketIO = this.sockets.get("");
        if (socketIO != null) {
            socketIO.getCallback().onConnect();
        }
    }

    @Override
    public void onDisconnect() {
        SocketIO socketIO = this.sockets.get("");
        if (socketIO != null) {
            socketIO.getCallback().onDisconnect();
        }
    }

    @Override
    public void onError(SocketIOException socketIOException) {
        Iterator<SocketIO> iterator = this.sockets.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().getCallback().onError(socketIOException);
        }
    }

    @Override
    public void onMessage(String string, IOAcknowledge iOAcknowledge) {
        Iterator<SocketIO> iterator = this.sockets.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().getCallback().onMessage(string, iOAcknowledge);
        }
    }

    @Override
    public void onMessage(JSONObject jSONObject, IOAcknowledge iOAcknowledge) {
        Iterator<SocketIO> iterator = this.sockets.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().getCallback().onMessage(jSONObject, iOAcknowledge);
        }
    }

    public void reconnect() {
        synchronized (this) {
            if (this.getState() != 6) {
                this.invalidateTransport();
                this.setState(4);
                if (this.reconnectTask != null) {
                    this.reconnectTask.cancel();
                }
                this.reconnectTask = new ReconnectTask();
                this.backgroundTimer.schedule((TimerTask)this.reconnectTask, 1000L);
            }
            return;
        }
    }

    public boolean register(SocketIO socketIO) {
        synchronized (this) {
            String string;
            block4 : {
                string = socketIO.getNamespace();
                boolean bl = this.sockets.containsKey(string);
                if (!bl) break block4;
                return false;
            }
            this.sockets.put(string, socketIO);
            socketIO.setHeaders(this.headers);
            this.sendPlain(new IOMessage(1, socketIO.getNamespace(), "").toString());
            return true;
        }
    }

    public void send(SocketIO object, IOAcknowledge iOAcknowledge, String string) {
        object = new IOMessage(3, object.getNamespace(), string);
        this.synthesizeAck((IOMessage)object, iOAcknowledge);
        this.sendPlain(object.toString());
    }

    public void send(SocketIO object, IOAcknowledge iOAcknowledge, JSONObject jSONObject) {
        object = new IOMessage(4, object.getNamespace(), jSONObject.toString());
        this.synthesizeAck((IOMessage)object, iOAcknowledge);
        this.sendPlain(object.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void transportConnected() {
        synchronized (this) {
            block13 : {
                ConcurrentLinkedQueue<String> concurrentLinkedQueue;
                String[] arrstring;
                int n;
                block12 : {
                    String string;
                    if (this._myDelegate != null) {
                        this._myDelegate.onConnectionStateChanged(true);
                    }
                    this.setState(3);
                    if (this.reconnectTask != null) {
                        this.reconnectTask.cancel();
                        this.reconnectTask = null;
                    }
                    this.resetTimeout();
                    if (this.transport.canSendBulk()) {
                        concurrentLinkedQueue = this.outputBuffer;
                        this.outputBuffer = new ConcurrentLinkedQueue();
                        arrstring = concurrentLinkedQueue.toArray(new String[concurrentLinkedQueue.size()]);
                        logger.info("Bulk start:");
                        n = arrstring.length;
                        break block12;
                    }
                    while ((string = this.outputBuffer.poll()) != null) {
                        this.sendPlain(string);
                    }
                    break block13;
                }
                for (int i = 0; i < n; ++i) {
                    String string = arrstring[i];
                    Logger logger = IOConnection.logger;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("> ");
                    stringBuilder.append(string);
                    logger.info(stringBuilder.toString());
                    continue;
                }
                try {
                    logger.info("Bulk end");
                    this.transport.sendBulk(arrstring);
                    break block13;
                }
                catch (IOException iOException) {}
                this.outputBuffer = concurrentLinkedQueue;
            }
            this.keepAliveInQueue = false;
            return;
        }
    }

    public void transportData(String string) {
        if (!string.startsWith(FRAME_DELIMITER)) {
            this.transportMessage(string);
            return;
        }
        Object object = Arrays.asList(string.split(FRAME_DELIMITER)).listIterator(1);
        while (object.hasNext()) {
            String string2;
            int n = Integer.parseInt((String)object.next());
            if (n != (string2 = (String)object.next()).length()) {
                object = new StringBuilder();
                object.append("Garbage from server: ");
                object.append(string);
                this.error(new SocketIOException(object.toString()));
                return;
            }
            this.transportMessage(string2);
        }
    }

    public void transportDisconnected() {
        this.lastException = null;
        this.setState(4);
        if (this._myDelegate != null) {
            this._myDelegate.onConnectionStateChanged(false);
        }
        this.reconnect();
    }

    public void transportError(Exception exception) {
        if (this._myDelegate != null) {
            this._myDelegate.onConnectionStateChanged(false);
        }
        this.lastException = exception;
        this.setState(4);
        this.reconnect();
    }

    /*
     * Exception decompiling
     */
    public void transportMessage(String var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:148)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:386)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    public void unregister(SocketIO socketIO) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0::");
            stringBuilder.append(socketIO.getNamespace());
            this.sendPlain(stringBuilder.toString());
            this.sockets.remove(socketIO.getNamespace());
            socketIO.getCallback().onDisconnect();
            if (this.sockets.size() == 0) {
                this.cleanup();
            }
            return;
        }
    }

    private class ConnectThread
    extends Thread {
        public ConnectThread() {
            super("ConnectThread");
        }

        @Override
        public void run() {
            if (IOConnection.this.getState() == 0) {
                IOConnection.this.handshake();
            }
            IOConnection.this.connectTransport();
        }
    }

    private class HearbeatTimeoutTask
    extends TimerTask {
        private HearbeatTimeoutTask() {
        }

        @Override
        public void run() {
            IOConnection.this.error(new SocketIOException("Timeout Error. No heartbeat from server within life time of the socket. closing.", IOConnection.this.lastException));
        }
    }

    private class ReconnectTask
    extends TimerTask {
        private ReconnectTask() {
        }

        @Override
        public void run() {
            IOConnection.this.connectTransport();
            if (!IOConnection.this.keepAliveInQueue) {
                IOConnection.this.sendPlain("2::");
                IOConnection.this.keepAliveInQueue = true;
            }
        }
    }

}
