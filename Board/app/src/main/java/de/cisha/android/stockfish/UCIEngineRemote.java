// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.stockfish;

import android.os.Bundle;
import android.os.Message;
import java.io.OutputStream;
import java.io.IOException;
import de.cisha.chess.util.Logger;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import android.os.Handler;
import android.os.Messenger;
import de.cisha.chess.util.WritableInputStream;
import java.io.BufferedReader;
import java.io.PipedOutputStream;
import de.cisha.android.board.IChessServiceContext;
import de.cisha.chess.engine.UCIEngine;

public class UCIEngineRemote extends UCIEngine
{
    private static final String TAG = "Chess24";
    private static UCIEngineRemote _instance;
    private static IChessServiceContext sChessServiceContext;
    private boolean _alive;
    private PipedOutputStream _engineOutput;
    private BufferedReader _engineOutputReader;
    private EngineReader _engineReaderThread;
    private WritableInputStream _inputStream;
    private Messenger mMessenger;
    
    private UCIEngineRemote() {
        this._alive = true;
        this.mMessenger = new Messenger((Handler)new IncomingHandler());
        this._inputStream = new WritableInputStream();
        this._engineOutput = new PipedOutputStream();
        try {
            this._engineOutputReader = new BufferedReader(new InputStreamReader(new PipedInputStream(this._engineOutput)));
        }
        catch (IOException ex) {
            Logger.getInstance().error("Chess24", "Error connecting engine OutputStream:", ex);
        }
        (this._engineReaderThread = new EngineReader()).start();
        UCIEngineRemote.sChessServiceContext.bindToChessService();
    }
    
    private void checkEngineState() {
        if (this._status == 5 || this._status == 1) {
            this.sendLineToEngine("isready");
            if (!this.waitForCommandAnswer("readyok", 2000L)) {
                Logger.getInstance().error("Chess24", "engine seems to not respond - restarting");
                this.restartNative();
            }
        }
    }
    
    public static UCIEngineRemote getInstance() {
        synchronized (UCIEngineRemote.class) {
            if (UCIEngineRemote._instance == null) {
                UCIEngineRemote._instance = new UCIEngineRemote();
            }
            return UCIEngineRemote._instance;
        }
    }
    
    private void restartNative() {
        UCIEngineRemote.sChessServiceContext.unbindChessService();
        this._status = -1;
        UCIEngineRemote.sChessServiceContext.bindToChessService();
    }
    
    public static void setChessServiceContext(final IChessServiceContext sChessServiceContext) {
        UCIEngineRemote.sChessServiceContext = sChessServiceContext;
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
    public void setVariations(final int variations) {
        boolean b;
        if (this.isRunning()) {
            this.stop();
            b = true;
        }
        else {
            b = false;
        }
        super.setVariations(variations);
        if (b) {
            this.start();
        }
    }
    
    @Override
    public boolean start() {
        synchronized (this) {
            this.checkEngineState();
            return super.start();
        }
    }
    
    @Override
    public void startup() {
        UCIEngineRemote.sChessServiceContext.bindToChessService();
        super.startup();
    }
    
    private class EngineReader extends Thread
    {
        @Override
        public void run() {
            while (true) {
                if (!UCIEngineRemote.this._alive) {
                    return;
                }
                try {
                    final String line = UCIEngineRemote.this._engineOutputReader.readLine();
                    if (line != null) {
                        UCIEngineRemote.sChessServiceContext.sendChessCommand(line, UCIEngineRemote.this.mMessenger);
                    }
                }
                catch (IOException ex) {
                    Logger.getInstance().error("Chess24", "Exception", ex);
                }
                try {
                    Thread.sleep(33L);
                    continue;
                }
                catch (InterruptedException ex2) {}
            }
        }
    }
    
    class IncomingHandler extends Handler
    {
        public void handleMessage(final Message message) {
            final Bundle peekData = message.peekData();
            if (peekData == null) {
                super.handleMessage(message);
                return;
            }
            peekData.setClassLoader(Thread.currentThread().getContextClassLoader());
            final String string = peekData.getString("MSG_KEY");
            if (string == null) {
                super.handleMessage(message);
                return;
            }
            UCIEngineRemote.this._inputStream.addStringToBuffer(string);
        }
    }
}
