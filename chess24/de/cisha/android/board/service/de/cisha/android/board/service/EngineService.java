/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.util.Log
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import de.cisha.android.board.service.IEngineService;
import de.cisha.android.stockfish.UCIEngineRemote;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.util.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class EngineService
implements IEngineService {
    public static final String CRITTER = "critter-14-arm";
    public static final String STOCKFISH = "stockfish";
    public static final String STOCKFISH_ARM = "stockfish-231-android-ja";
    public static final String STOCKFISH_X86 = "stockfish-231-android-ja";
    private static IEngineService _instance;
    private Context _context;

    private EngineService(Context context) {
        this._context = context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void copyEngine(Context object, String object2) {
        String string = this.getEngineExecName((Context)object, (String)object2);
        byte[] arrby = new byte[](string);
        if (!arrby.exists()) {
            try {
                object = object.getAssets();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("engines/");
                stringBuilder.append((String)object2);
                object = object.open(stringBuilder.toString());
                object2 = new FileOutputStream((File)arrby);
                arrby = new byte[1024];
                do {
                    int n;
                    if ((n = object.read(arrby)) < 0) {
                        object.close();
                        object2.close();
                        object = Runtime.getRuntime();
                        object2 = new StringBuilder();
                        object2.append("chmod 777 ");
                        object2.append(string);
                        object.exec(object2.toString());
                        return;
                    }
                    object2.write(arrby, 0, n);
                } while (true);
            }
            catch (Exception exception) {
                Log.e((String)"Test", (String)"Copy failure", (Throwable)exception);
            }
        }
    }

    private String getEngineExecName(Context object, String string) {
        object = new StringBuilder();
        object.append("/data/data/de.cisha.android.board/");
        object.append(string);
        return object.toString();
    }

    static IEngineService getInstance(Context object) {
        synchronized (EngineService.class) {
            if (_instance == null) {
                _instance = new EngineService((Context)object);
            }
            object = _instance;
            return object;
        }
    }

    private ProcessorArchitecture getProcessorArchitecture() {
        String string = System.getProperty("os.arch");
        Object object = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Found out Processor Architecture: ");
        stringBuilder.append(string);
        object.debug("EngineService", stringBuilder.toString());
        object = ProcessorArchitecture.ARCH_ARM;
        if (string.startsWith("arm")) {
            return ProcessorArchitecture.ARCH_ARM;
        }
        if (string.endsWith("86")) {
            object = ProcessorArchitecture.ARCH_X86;
        }
        return object;
    }

    @Override
    public UCIEngine getDefaultSingleEngine() {
        UCIEngineRemote uCIEngineRemote = UCIEngineRemote.getInstance();
        if (!uCIEngineRemote.isBooted()) {
            ((UCIEngine)uCIEngineRemote).startup();
        }
        return uCIEngineRemote;
    }

    @Deprecated
    @Override
    public UCIEngine getEngineWithName(String object) {
        String string = object;
        if (STOCKFISH.equals(object)) {
            string = this.getProcessorArchitecture() == ProcessorArchitecture.ARCH_ARM ? "stockfish-231-android-ja" : "stockfish-231-android-ja";
        }
        this.copyEngine(this._context, string);
        object = null;
        if (new File(this.getEngineExecName(this._context, string)).exists()) {
            object = UCIEngineRemote.getInstance();
        }
        return object;
    }

    private static enum ProcessorArchitecture {
        ARCH_X86,
        ARCH_ARM;
        

        private ProcessorArchitecture() {
        }
    }

}
