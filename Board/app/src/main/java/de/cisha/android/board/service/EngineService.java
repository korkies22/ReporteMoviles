// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.stockfish.UCIEngineRemote;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.util.Logger;
import java.io.InputStream;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.File;
import android.content.Context;

public class EngineService implements IEngineService
{
    public static final String CRITTER = "critter-14-arm";
    public static final String STOCKFISH = "stockfish";
    public static final String STOCKFISH_ARM = "stockfish-231-android-ja";
    public static final String STOCKFISH_X86 = "stockfish-231-android-ja";
    private static IEngineService _instance;
    private Context _context;
    
    private EngineService(final Context context) {
        this._context = context;
    }
    
    private void copyEngine(final Context context, final String s) {
        final String engineExecName = this.getEngineExecName(context, s);
        final File file = new File(engineExecName);
        if (!file.exists()) {
            try {
                final AssetManager assets = context.getAssets();
                final StringBuilder sb = new StringBuilder();
                sb.append("engines/");
                sb.append(s);
                final InputStream open = assets.open(sb.toString());
                final FileOutputStream fileOutputStream = new FileOutputStream(file);
                final byte[] array = new byte[1024];
                while (true) {
                    final int read = open.read(array);
                    if (read < 0) {
                        break;
                    }
                    fileOutputStream.write(array, 0, read);
                }
                open.close();
                fileOutputStream.close();
                final Runtime runtime = Runtime.getRuntime();
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("chmod 777 ");
                sb2.append(engineExecName);
                runtime.exec(sb2.toString());
            }
            catch (Exception ex) {
                Log.e("Test", "Copy failure", (Throwable)ex);
            }
        }
    }
    
    private String getEngineExecName(final Context context, final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append("/data/data/de.cisha.android.board/");
        sb.append(s);
        return sb.toString();
    }
    
    static IEngineService getInstance(final Context context) {
        synchronized (EngineService.class) {
            if (EngineService._instance == null) {
                EngineService._instance = new EngineService(context);
            }
            return EngineService._instance;
        }
    }
    
    private ProcessorArchitecture getProcessorArchitecture() {
        final String property = System.getProperty("os.arch");
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("Found out Processor Architecture: ");
        sb.append(property);
        instance.debug("EngineService", sb.toString());
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.ARCH_ARM;
        if (property.startsWith("arm")) {
            return ProcessorArchitecture.ARCH_ARM;
        }
        if (property.endsWith("86")) {
            processorArchitecture = ProcessorArchitecture.ARCH_X86;
        }
        return processorArchitecture;
    }
    
    @Override
    public UCIEngine getDefaultSingleEngine() {
        final UCIEngineRemote instance = UCIEngineRemote.getInstance();
        if (!instance.isBooted()) {
            instance.startup();
        }
        return instance;
    }
    
    @Deprecated
    @Override
    public UCIEngine getEngineWithName(final String s) {
        String s2 = s;
        if ("stockfish".equals(s)) {
            if (this.getProcessorArchitecture() == ProcessorArchitecture.ARCH_ARM) {
                s2 = "stockfish-231-android-ja";
            }
            else {
                s2 = "stockfish-231-android-ja";
            }
        }
        this.copyEngine(this._context, s2);
        UCIEngine instance = null;
        if (new File(this.getEngineExecName(this._context, s2)).exists()) {
            instance = UCIEngineRemote.getInstance();
        }
        return instance;
    }
    
    private enum ProcessorArchitecture
    {
        ARCH_ARM, 
        ARCH_X86;
    }
}
