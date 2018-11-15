/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Messenger
 */
package de.cisha.android.stockfish;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import de.cisha.android.board.IChessServiceContext;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.util.Logger;
import de.cisha.chess.util.WritableInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;

public class UCIEngineRemote
extends UCIEngine {
    private static final String TAG = "Chess24";
    private static UCIEngineRemote _instance;
    private static IChessServiceContext sChessServiceContext;
    private boolean _alive = true;
    private PipedOutputStream _engineOutput = new PipedOutputStream();
    private BufferedReader _engineOutputReader;
    private EngineReader _engineReaderThread;
    private WritableInputStream _inputStream = new WritableInputStream();
    private Messenger mMessenger = new Messenger((Handler)new IncomingHandler());

    private UCIEngineRemote() {
        try {
            this._engineOutputReader = new BufferedReader(new InputStreamReader(new PipedInputStream(this._engineOutput)));
        }
        catch (IOException iOException) {
            Logger.getInstance().error(TAG, "Error connecting engine OutputStream:", iOException);
        }
        this._engineReaderThread = new EngineReader();
        this._engineReaderThread.start();
        sChessServiceContext.bindToChessService();
    }

    private void checkEngineState() {
        if (this._status == 5 || this._status == 1) {
            this.sendLineToEngine("isready");
            if (!this.waitForCommandAnswer("readyok", 2000L)) {
                Logger.getInstance().error(TAG, "engine seems to not respond - restarting");
                this.restartNative();
            }
        }
    }

    public static UCIEngineRemote getInstance() {
        synchronized (UCIEngineRemote.class) {
            if (_instance == null) {
                _instance = new UCIEngineRemote();
            }
            UCIEngineRemote uCIEngineRemote = _instance;
            return uCIEngineRemote;
        }
    }

    private void restartNative() {
        sChessServiceContext.unbindChessService();
        this._status = -1;
        sChessServiceContext.bindToChessService();
    }

    public static void setChessServiceContext(IChessServiceContext iChessServiceContext) {
        sChessServiceContext = iChessServiceContext;
    }

    @Override
    public void destroy() {
        this.stop();
    }

    @Override
    protected void destroyStreams() {
        this._alive = false;
    }

    @Override
    protected void finalize() throws Throwable {
        super.destroy();
        super.finalize();
    }

    @Override
    protected InputStream getConnectedInputStream() {
        return this._inputStream;
    }

    @Override
    protected OutputStream getConnectedOutputStream() {
        return this._engineOutput;
    }

    @Override
    public void setVariations(int n) {
        boolean bl;
        if (this.isRunning()) {
            this.stop();
            bl = true;
        } else {
            bl = false;
        }
        super.setVariations(n);
        if (bl) {
            this.start();
        }
    }

    @Override
    public boolean start() {
        synchronized (this) {
            this.checkEngineState();
            boolean bl = super.start();
            return bl;
        }
    }

    @Override
    public void startup() {
        sChessServiceContext.bindToChessService();
        super.startup();
    }

    private class EngineReader
    extends Thread {
        private EngineReader() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            while (UCIEngineRemote.this._alive) {
                try {
                    String string = UCIEngineRemote.this._engineOutputReader.readLine();
                    if (string != null) {
                        sChessServiceContext.sendChessCommand(string, UCIEngineRemote.this.mMessenger);
                    }
                }
                catch (IOException iOException) {
                    Logger.getInstance().error(UCIEngineRemote.TAG, "Exception", iOException);
                }
                try {
                    Thread.sleep(33L);
                }
                catch (InterruptedException interruptedException) {}
            }
        }
    }

    class IncomingHandler
    extends Handler {
        IncomingHandler() {
        }

        public void handleMessage(Message message) {
            Object object = message.peekData();
            if (object == null) {
                super.handleMessage(message);
                return;
            }
            object.setClassLoader(Thread.currentThread().getContextClassLoader());
            object = object.getString("MSG_KEY");
            if (object == null) {
                super.handleMessage(message);
                return;
            }
            UCIEngineRemote.this._inputStream.addStringToBuffer((String)object);
        }
    }

}
