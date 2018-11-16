// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

import java.io.Serializable;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Iterator;
import de.cisha.chess.model.Square;
import java.util.StringTokenizer;
import de.cisha.chess.util.Logger;
import java.util.Collections;
import java.util.Comparator;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import de.cisha.chess.model.SEP;
import java.util.ArrayList;
import de.cisha.chess.model.FEN;
import de.cisha.chess.util.RunnableQueue;
import java.io.PrintWriter;
import java.util.List;
import de.cisha.chess.model.position.Position;

public abstract class UCIEngine implements UCIOptionListener
{
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "UCIEngine";
    public static final int STATUS_BOOTING = 0;
    public static final int STATUS_DISCONNECTED = -1;
    public static final int STATUS_ENDED = 6;
    public static final int STATUS_READY = 1;
    public static final int STATUS_RUNNING = 4;
    public static final int STATUS_STARTING_TO_RUN = 2;
    public static final int STATUS_STOPPING = 5;
    private InReader _autoInReader;
    protected Position _currentPosition;
    private EngineDelegate _engineDelegate;
    private String _name;
    private List<UCIOption> _options;
    private PrintWriter _outWriter;
    private RunnableQueue _outgoingLinesQueue;
    private List<EngineOutputLineReceiver> _receivers;
    protected int _status;
    
    public UCIEngine() {
        this._status = -1;
        this._name = "UCI-Engine";
        this._currentPosition = new Position(FEN.STARTING_POSITION);
        this._options = new ArrayList<UCIOption>();
        this._status = -1;
        this._receivers = new ArrayList<EngineOutputLineReceiver>(10);
        this._outgoingLinesQueue = new RunnableQueue();
    }
    
    private void addEngineLineReceiver(final EngineOutputLineReceiver engineOutputLineReceiver) {
        synchronized (this._receivers) {
            this._receivers.add(engineOutputLineReceiver);
        }
    }
    
    private List<UCIInfo> analysePosition(final int n, final int n2, final boolean b) {
        final TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
        if (this.isRunning()) {
            this.stop();
        }
        this.addEngineLineReceiver((EngineOutputLineReceiver)new EngineOutputLineReceiver() {
            @Override
            boolean receivedLine(final String s) {
                if (s.startsWith("info") && s.contains("multipv")) {
                    final UCIInfo access.400 = UCIEngine.this.parseInfoLine(s);
                    treeMap.put(access.400.getLineNumber(), access.400);
                }
                else if (s.startsWith("bestmove")) {
                    return true;
                }
                return false;
            }
        });
        if (b) {
            final StringBuilder sb = new StringBuilder();
            sb.append("go depth ");
            sb.append(n);
            this.sendLineToEngine(sb.toString());
        }
        else {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("go movetime ");
            sb2.append(n2);
            this.sendLineToEngine(sb2.toString());
        }
        this._status = 2;
        long n3;
        if (b) {
            n3 = 1000000000L;
        }
        else {
            n3 = n2 + 500;
        }
        if (this.waitForCommandAnswer("bestmove", n3) ^ true) {
            this.stop();
            this.waitForCommandAnswer("bestmove", 500L);
        }
        final ArrayList list = new ArrayList<Object>((Collection<? extends T>)treeMap.values());
        Collections.sort((List<E>)list, (Comparator<? super E>)new Comparator<UCIInfo>() {
            @Override
            public int compare(final UCIInfo uciInfo, final UCIInfo uciInfo2) {
                return uciInfo.getLineNumber() - uciInfo2.getLineNumber();
            }
        });
        return (List<UCIInfo>)list;
    }
    
    private Runnable createSendLineCommandRunnable(final String s) {
        return new Runnable() {
            @Override
            public void run() {
                if (!UCIEngine.this._outWriter.checkError()) {
                    synchronized (UCIEngine.this._outWriter) {
                        UCIEngine.this._outWriter.println(s);
                        return;
                    }
                }
                final Logger instance = Logger.getInstance();
                final StringBuilder sb = new StringBuilder();
                sb.append("not able to send command to engine - engine closed:");
                sb.append(s);
                instance.error("Engine", sb.toString());
            }
        };
    }
    
    private String getDefaultValueString(String string) {
        final StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
        while (stringTokenizer.hasMoreTokens()) {
            if (stringTokenizer.nextToken().equals("default") && stringTokenizer.hasMoreTokens()) {
                String s = stringTokenizer.nextToken();
                StringBuilder sb;
                StringBuilder sb2;
                for (string = ""; stringTokenizer.hasMoreTokens() && !string.equals(" var") && !string.equals(" min") && !string.equals(" max"); string = sb2.toString()) {
                    sb = new StringBuilder();
                    sb.append(s);
                    sb.append(string);
                    s = sb.toString();
                    sb2 = new StringBuilder();
                    sb2.append(" ");
                    sb2.append(stringTokenizer.nextToken());
                }
                return s;
            }
        }
        return null;
    }
    
    private SEP getMove(final int n, final boolean b) {
        if (this.isRunning()) {
            this.stop();
        }
        final GetMoveOutPutReceiver getMoveOutPutReceiver = new GetMoveOutPutReceiver();
        this.addEngineLineReceiver((EngineOutputLineReceiver)getMoveOutPutReceiver);
        this._status = 2;
        if (b) {
            final StringBuilder sb = new StringBuilder();
            sb.append("go depth ");
            sb.append(n);
            this.sendLineToEngine(sb.toString());
        }
        else {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("go movetime ");
            sb2.append(n);
            this.sendLineToEngine(sb2.toString());
        }
        long n2;
        if (b) {
            n2 = 1000000000L;
        }
        else {
            n2 = n + 500;
        }
        if (this.waitForCommandAnswer("bestmove", n2) ^ true) {
            this.stop();
            this.waitForCommandAnswer("bestmove", 500L);
        }
        return getMoveOutPutReceiver.sep;
    }
    
    private SEP getSEP(final String s) {
        final boolean b = false;
        final Square instanceForFileAndRank = Square.instanceForFileAndRank(s.charAt(0), s.charAt(1) - '1' + '\u0001');
        final Square instanceForFileAndRank2 = Square.instanceForFileAndRank(s.charAt(2), s.charAt(3) - '1' + '\u0001');
        int n = b ? 1 : 0;
        if (s.trim().length() == 5) {
            n = (b ? 1 : 0);
            switch (s.charAt(4)) {
                default: {
                    n = (b ? 1 : 0);
                    break;
                }
                case 'R':
                case 'r': {
                    break;
                }
                case 'Q':
                case 'q': {
                    n = 3;
                    break;
                }
                case 'N':
                case 'n': {
                    n = 1;
                    break;
                }
                case 'B':
                case 'b': {
                    n = 2;
                    break;
                }
            }
        }
        return new SEP(instanceForFileAndRank, instanceForFileAndRank2, n);
    }
    
    private UCIOption getUCIOption(final String s) {
        if (s != null) {
            for (final UCIOption uciOption : this._options) {
                if (s.equals(uciOption.getName())) {
                    return uciOption;
                }
            }
        }
        return null;
    }
    
    private void handleIDLine(final String s) {
        if (s.startsWith("id name ")) {
            this._name = s.substring(8);
            return;
        }
        s.startsWith("id author ");
    }
    
    private void notifyReceiversNewLine(final String s) {
        final List<EngineOutputLineReceiver> receivers = this._receivers;
        // monitorenter(receivers)
        Collection<?> collection = null;
        try {
            for (final EngineOutputLineReceiver engineOutputLineReceiver : this._receivers) {
                if (engineOutputLineReceiver.receivedLine(s)) {
                    List<EngineOutputLineReceiver> list;
                    if ((list = (List<EngineOutputLineReceiver>)collection) == null) {
                        list = new LinkedList<EngineOutputLineReceiver>();
                    }
                    list.add(engineOutputLineReceiver);
                    collection = list;
                }
            }
            if (collection != null) {
                this._receivers.removeAll(collection);
            }
        }
        finally {
        }
        // monitorexit(receivers)
    }
    
    private UCIOption parseButtonOption(final String s, final String s2) {
        return new UCIButtonOption(s, this);
    }
    
    private UCIOption parseCheckOption(final String s, final String s2) {
        return new UCICheckOption(s, this, Boolean.parseBoolean(this.getDefaultValueString(s2)));
    }
    
    private UCIOption parseComboOption(final String s, String string) {
        final String defaultValueString = this.getDefaultValueString(string);
        final ArrayList<String> list = new ArrayList<String>();
        final StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
        int n = 0;
        string = null;
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            String s2 = null;
            Label_0085: {
                if (n == 0 || !nextToken.equals("var")) {
                    s2 = string;
                    if (stringTokenizer.hasMoreTokens()) {
                        break Label_0085;
                    }
                }
                list.add(string);
                s2 = null;
            }
            int n2 = n;
            String s3 = s2;
            if (n == 0) {
                n2 = n;
                s3 = s2;
                if (nextToken.equals("var")) {
                    n2 = 1;
                    s3 = null;
                }
            }
            n = n2;
            string = s3;
            if (!nextToken.equals("var")) {
                if (s3 == null) {
                    string = nextToken;
                    n = n2;
                }
                else {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(s3);
                    sb.append(" ");
                    sb.append(nextToken);
                    string = sb.toString();
                    n = n2;
                }
            }
        }
        return new UCIComboOption(s, this, defaultValueString, list);
    }
    
    private UCIInfo parseInfoLine(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: new             Ljava/util/StringTokenizer;
        //     3: dup            
        //     4: aload_1        
        //     5: ldc             " "
        //     7: invokespecial   java/util/StringTokenizer.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //    10: astore          15
        //    12: iconst_0       
        //    13: istore          8
        //    15: iload           8
        //    17: istore          6
        //    19: iload           6
        //    21: istore          5
        //    23: iload           5
        //    25: istore          4
        //    27: ldc             ""
        //    29: astore          13
        //    31: aconst_null    
        //    32: astore_1       
        //    33: aload           15
        //    35: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //    38: ifeq            770
        //    41: aload           15
        //    43: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    46: astore          14
        //    48: aload           14
        //    50: ldc_w           "multipv"
        //    53: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    56: ifeq            134
        //    59: aload           15
        //    61: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //    64: ifeq            33
        //    67: aload           15
        //    69: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    72: astore          14
        //    74: aload           14
        //    76: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //    79: istore          7
        //    81: iload           7
        //    83: istore          8
        //    85: goto            33
        //    88: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //    91: astore          16
        //    93: new             Ljava/lang/StringBuilder;
        //    96: dup            
        //    97: invokespecial   java/lang/StringBuilder.<init>:()V
        //   100: astore          17
        //   102: aload           17
        //   104: ldc_w           "invalid multipv from engine:"
        //   107: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   110: pop            
        //   111: aload           17
        //   113: aload           14
        //   115: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   118: pop            
        //   119: aload           16
        //   121: ldc             "UCIEngine"
        //   123: aload           17
        //   125: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   128: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;)V
        //   131: goto            33
        //   134: aload           14
        //   136: ldc_w           "depth"
        //   139: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   142: ifeq            220
        //   145: aload           15
        //   147: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //   150: ifeq            33
        //   153: aload           15
        //   155: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   158: astore          14
        //   160: aload           14
        //   162: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   165: istore          7
        //   167: iload           7
        //   169: istore          6
        //   171: goto            217
        //   174: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   177: astore          16
        //   179: new             Ljava/lang/StringBuilder;
        //   182: dup            
        //   183: invokespecial   java/lang/StringBuilder.<init>:()V
        //   186: astore          17
        //   188: aload           17
        //   190: ldc_w           "invalid depth from engine:"
        //   193: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   196: pop            
        //   197: aload           17
        //   199: aload           14
        //   201: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   204: pop            
        //   205: aload           16
        //   207: ldc             "UCIEngine"
        //   209: aload           17
        //   211: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   214: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;)V
        //   217: goto            33
        //   220: aload           14
        //   222: ldc_w           "nodes"
        //   225: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   228: ifeq            306
        //   231: aload           15
        //   233: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //   236: ifeq            33
        //   239: aload           15
        //   241: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   244: astore          14
        //   246: aload           14
        //   248: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   251: istore          7
        //   253: iload           7
        //   255: istore          5
        //   257: goto            303
        //   260: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   263: astore          16
        //   265: new             Ljava/lang/StringBuilder;
        //   268: dup            
        //   269: invokespecial   java/lang/StringBuilder.<init>:()V
        //   272: astore          17
        //   274: aload           17
        //   276: ldc_w           "invalid nodes from engine:"
        //   279: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   282: pop            
        //   283: aload           17
        //   285: aload           14
        //   287: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   290: pop            
        //   291: aload           16
        //   293: ldc             "UCIEngine"
        //   295: aload           17
        //   297: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   300: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;)V
        //   303: goto            33
        //   306: aload           14
        //   308: ldc_w           "time"
        //   311: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   314: ifeq            392
        //   317: aload           15
        //   319: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //   322: ifeq            33
        //   325: aload           15
        //   327: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   330: astore          14
        //   332: aload           14
        //   334: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   337: istore          7
        //   339: iload           7
        //   341: istore          4
        //   343: goto            389
        //   346: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   349: astore          16
        //   351: new             Ljava/lang/StringBuilder;
        //   354: dup            
        //   355: invokespecial   java/lang/StringBuilder.<init>:()V
        //   358: astore          17
        //   360: aload           17
        //   362: ldc_w           "invalid time from engine:"
        //   365: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   368: pop            
        //   369: aload           17
        //   371: aload           14
        //   373: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   376: pop            
        //   377: aload           16
        //   379: ldc             "UCIEngine"
        //   381: aload           17
        //   383: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   386: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;)V
        //   389: goto            33
        //   392: aload           14
        //   394: ldc_w           "score"
        //   397: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   400: ifeq            689
        //   403: aload           15
        //   405: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //   408: ifeq            33
        //   411: aload           15
        //   413: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   416: astore          14
        //   418: aload_0        
        //   419: getfield        de/cisha/chess/engine/UCIEngine._currentPosition:Lde/cisha/chess/model/position/Position;
        //   422: astore          16
        //   424: iconst_1       
        //   425: istore          11
        //   427: aload           16
        //   429: ifnull          448
        //   432: aload_0        
        //   433: getfield        de/cisha/chess/engine/UCIEngine._currentPosition:Lde/cisha/chess/model/position/Position;
        //   436: invokevirtual   de/cisha/chess/model/position/Position.getActiveColor:()Z
        //   439: ifeq            448
        //   442: iconst_1       
        //   443: istore          9
        //   445: goto            451
        //   448: iconst_0       
        //   449: istore          9
        //   451: aload           14
        //   453: ldc_w           "cp"
        //   456: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   459: ifeq            562
        //   462: aload           15
        //   464: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //   467: ifeq            33
        //   470: aload           15
        //   472: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   475: astore_1       
        //   476: fconst_0       
        //   477: fstore_2       
        //   478: aload_1        
        //   479: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   482: istore          7
        //   484: iload           7
        //   486: i2f            
        //   487: ldc_w           100.0
        //   490: fdiv           
        //   491: fstore_2       
        //   492: goto            537
        //   495: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   498: astore          14
        //   500: new             Ljava/lang/StringBuilder;
        //   503: dup            
        //   504: invokespecial   java/lang/StringBuilder.<init>:()V
        //   507: astore          16
        //   509: aload           16
        //   511: ldc_w           "invalid eval from engine:"
        //   514: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   517: pop            
        //   518: aload           16
        //   520: aload_1        
        //   521: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   524: pop            
        //   525: aload           14
        //   527: ldc             "UCIEngine"
        //   529: aload           16
        //   531: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   534: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;)V
        //   537: fload_2        
        //   538: fstore_3       
        //   539: iload           9
        //   541: ifne            550
        //   544: fload_2        
        //   545: ldc_w           -1.0
        //   548: fmul           
        //   549: fstore_3       
        //   550: new             Lde/cisha/chess/engine/EngineEvaluation;
        //   553: dup            
        //   554: fload_3        
        //   555: invokespecial   de/cisha/chess/engine/EngineEvaluation.<init>:(F)V
        //   558: astore_1       
        //   559: goto            33
        //   562: aload           14
        //   564: ldc_w           "mate"
        //   567: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   570: ifeq            33
        //   573: aload           15
        //   575: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //   578: ifeq            33
        //   581: aload           15
        //   583: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   586: astore_1       
        //   587: aload_1        
        //   588: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   591: istore          7
        //   593: goto            641
        //   596: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   599: astore          14
        //   601: new             Ljava/lang/StringBuilder;
        //   604: dup            
        //   605: invokespecial   java/lang/StringBuilder.<init>:()V
        //   608: astore          16
        //   610: aload           16
        //   612: ldc_w           "invalid mate from engine:"
        //   615: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   618: pop            
        //   619: aload           16
        //   621: aload_1        
        //   622: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   625: pop            
        //   626: aload           14
        //   628: ldc             "UCIEngine"
        //   630: aload           16
        //   632: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   635: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;)V
        //   638: iconst_1       
        //   639: istore          7
        //   641: iload           7
        //   643: istore          10
        //   645: iload           7
        //   647: ifge            659
        //   650: iload           7
        //   652: iconst_m1      
        //   653: imul           
        //   654: istore          10
        //   656: iconst_0       
        //   657: istore          11
        //   659: iload           11
        //   661: istore          12
        //   663: iload           9
        //   665: ifne            674
        //   668: iload           11
        //   670: iconst_1       
        //   671: ixor           
        //   672: istore          12
        //   674: new             Lde/cisha/chess/engine/EngineEvaluation;
        //   677: dup            
        //   678: iload           10
        //   680: iload           12
        //   682: invokespecial   de/cisha/chess/engine/EngineEvaluation.<init>:(IZ)V
        //   685: astore_1       
        //   686: goto            33
        //   689: aload           14
        //   691: ldc_w           "pv"
        //   694: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   697: ifeq            33
        //   700: aload           15
        //   702: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //   705: ifeq            33
        //   708: aload           13
        //   710: astore          14
        //   712: aload           14
        //   714: astore          13
        //   716: aload           15
        //   718: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //   721: ifeq            33
        //   724: new             Ljava/lang/StringBuilder;
        //   727: dup            
        //   728: invokespecial   java/lang/StringBuilder.<init>:()V
        //   731: astore          13
        //   733: aload           13
        //   735: aload           14
        //   737: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   740: pop            
        //   741: aload           13
        //   743: ldc             " "
        //   745: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   748: pop            
        //   749: aload           13
        //   751: aload           15
        //   753: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   756: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   759: pop            
        //   760: aload           13
        //   762: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   765: astore          14
        //   767: goto            712
        //   770: new             Lde/cisha/chess/engine/UCIInfo;
        //   773: dup            
        //   774: aload_0        
        //   775: getfield        de/cisha/chess/engine/UCIEngine._currentPosition:Lde/cisha/chess/model/position/Position;
        //   778: iload           8
        //   780: aload           13
        //   782: aload_1        
        //   783: iload           6
        //   785: iload           5
        //   787: iload           4
        //   789: invokespecial   de/cisha/chess/engine/UCIInfo.<init>:(Lde/cisha/chess/model/position/Position;ILjava/lang/String;Lde/cisha/chess/engine/EngineEvaluation;III)V
        //   792: areturn        
        //   793: astore          16
        //   795: goto            88
        //   798: astore          16
        //   800: goto            174
        //   803: astore          16
        //   805: goto            260
        //   808: astore          16
        //   810: goto            346
        //   813: astore          14
        //   815: goto            495
        //   818: astore          14
        //   820: goto            596
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                             
        //  -----  -----  -----  -----  ---------------------------------
        //  74     81     793    134    Ljava/lang/NumberFormatException;
        //  160    167    798    217    Ljava/lang/NumberFormatException;
        //  246    253    803    303    Ljava/lang/NumberFormatException;
        //  332    339    808    389    Ljava/lang/NumberFormatException;
        //  478    484    813    537    Ljava/lang/NumberFormatException;
        //  587    593    818    641    Ljava/lang/NumberFormatException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 367, Size: 367
        //     at java.util.ArrayList.rangeCheck(Unknown Source)
        //     at java.util.ArrayList.get(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3303)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private UCIOption parseOptionLine(String substring) {
        final int index = substring.indexOf("type ");
        if (substring.startsWith("option name ") && index > 0) {
            final String substring2 = substring.substring(12, index - 1);
            final int n = index + 5;
            final int index2 = substring.indexOf(32, n);
            int length;
            if ((length = index2) < 0) {
                length = index2;
                if (substring.length() > n) {
                    length = substring.length();
                }
            }
            if (length > 0) {
                final String substring3 = substring.substring(n, length);
                substring = substring.substring(length);
                if (substring3.equals(UCIOption.UCIOptionType.UCIOPTION_TYPE_SPIN.getType())) {
                    return this.parseSpinOption(substring2, substring);
                }
                if (substring3.equals(UCIOption.UCIOptionType.UCIOPTION_TYPE_STRING.getType())) {
                    return this.parseStringOption(substring2, substring);
                }
                if (substring3.equals(UCIOption.UCIOptionType.UCIOPTION_TYPE_CHECK.getType())) {
                    return this.parseCheckOption(substring2, substring);
                }
                if (substring3.equals(UCIOption.UCIOptionType.UCIOPTION_TYPE_BUTTON.getType())) {
                    return this.parseButtonOption(substring2, substring);
                }
                if (substring3.equals(UCIOption.UCIOptionType.UCIOPTION_TYPE_COMBO.getType())) {
                    return this.parseComboOption(substring2, substring);
                }
            }
        }
        return null;
    }
    
    private UCIOption parseSpinOption(final String p0, final String p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: new             Ljava/util/StringTokenizer;
        //     3: dup            
        //     4: aload_2        
        //     5: ldc             " "
        //     7: invokespecial   java/util/StringTokenizer.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //    10: astore          6
        //    12: ldc_w           -2147483648
        //    15: istore_3       
        //    16: ldc_w           2147483647
        //    19: istore          4
        //    21: aload           6
        //    23: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //    26: ifeq            118
        //    29: aload           6
        //    31: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    34: astore          7
        //    36: aload           7
        //    38: ldc_w           "min"
        //    41: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    44: ifeq            78
        //    47: aload           6
        //    49: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //    52: ifeq            78
        //    55: aload           6
        //    57: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    60: astore          7
        //    62: aload           7
        //    64: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //    67: istore          5
        //    69: iload           5
        //    71: istore_3       
        //    72: goto            75
        //    75: goto            21
        //    78: aload           7
        //    80: ldc_w           "max"
        //    83: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    86: ifeq            21
        //    89: aload           6
        //    91: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //    94: ifeq            21
        //    97: aload           6
        //    99: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   102: astore          7
        //   104: aload           7
        //   106: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   109: istore          5
        //   111: iload           5
        //   113: istore          4
        //   115: goto            21
        //   118: aload_0        
        //   119: aload_2        
        //   120: invokespecial   de/cisha/chess/engine/UCIEngine.getDefaultValueString:(Ljava/lang/String;)Ljava/lang/String;
        //   123: astore_2       
        //   124: aload_2        
        //   125: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   128: istore          5
        //   130: goto            139
        //   133: iconst_0       
        //   134: istore          5
        //   136: goto            130
        //   139: new             Lde/cisha/chess/engine/UCISpinOption;
        //   142: dup            
        //   143: aload_1        
        //   144: aload_0        
        //   145: iload_3        
        //   146: iload           4
        //   148: iload           5
        //   150: invokespecial   de/cisha/chess/engine/UCISpinOption.<init>:(Ljava/lang/String;Lde/cisha/chess/engine/UCIOptionListener;III)V
        //   153: areturn        
        //   154: astore          7
        //   156: goto            75
        //   159: astore          7
        //   161: goto            21
        //   164: astore_2       
        //   165: goto            133
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                             
        //  -----  -----  -----  -----  ---------------------------------
        //  62     69     154    159    Ljava/lang/NumberFormatException;
        //  104    111    159    164    Ljava/lang/NumberFormatException;
        //  124    130    164    139    Ljava/lang/NumberFormatException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 75, Size: 75
        //     at java.util.ArrayList.rangeCheck(Unknown Source)
        //     at java.util.ArrayList.get(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3303)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private UCIOption parseStringOption(final String s, final String s2) {
        return new UCIStringOption(s, this, this.getDefaultValueString(s2));
    }
    
    private void receivedEngineLine(final String s) {
        this.notifyReceiversNewLine(s);
        if (s.startsWith("info ")) {
            if (s.contains("score") && s.contains("multipv")) {
                if (this._engineDelegate != null) {
                    this._engineDelegate.engineReceivedInfo(this.parseInfoLine(s));
                }
                this._status = 4;
            }
        }
        else if (s.startsWith("bestmove")) {
            if (this._status != 2) {
                this._status = 1;
            }
        }
        else if (s.startsWith("readyok")) {
            if (this._status != 2) {
                this._status = 1;
            }
        }
        else {
            if (s.startsWith("id")) {
                this.handleIDLine(s);
                return;
            }
            if (s.startsWith("option")) {
                final UCIOption optionLine = this.parseOptionLine(s);
                if (optionLine != null) {
                    this._options.add(optionLine);
                }
            }
            else if (s.startsWith("uciok")) {
                this._status = 1;
            }
        }
    }
    
    public List<UCIInfo> analysePositionWithDepth(final int n) {
        return this.analysePosition(n, 0, true);
    }
    
    public List<UCIInfo> analysePositionWithTime(final int n) {
        final UCICheckOption uciCheckOption = this.getUCICheckOption("OwnBook");
        boolean booleanValue;
        if (uciCheckOption != null) {
            booleanValue = uciCheckOption.getCurrentValue();
            uciCheckOption.setValue(false);
        }
        else {
            booleanValue = false;
        }
        final List<UCIInfo> analysePosition = this.analysePosition(0, n, false);
        if (uciCheckOption != null) {
            uciCheckOption.setValue(booleanValue);
        }
        return analysePosition;
    }
    
    public void destroy() {
        this._autoInReader.destroy();
        this._status = 6;
        this._outgoingLinesQueue.destroy();
        if (this._outWriter != null && !this._outWriter.checkError()) {
            this._outWriter.println("quit");
            this._outWriter.println();
            this._outWriter.flush();
        }
        if (this._outWriter != null) {
            this._outWriter.close();
        }
        this.destroyStreams();
    }
    
    protected abstract void destroyStreams();
    
    @Override
    protected void finalize() throws Throwable {
        if (this._status != 6) {
            this.destroy();
        }
        super.finalize();
    }
    
    protected abstract InputStream getConnectedInputStream();
    
    protected abstract OutputStream getConnectedOutputStream();
    
    public Position getCurrentPosition() {
        return this._currentPosition;
    }
    
    public SEP getMoveWithDepth(final int n) {
        synchronized (this) {
            return this.getMove(n, true);
        }
    }
    
    public SEP getMoveWithThinkingTime(final int n) {
        synchronized (this) {
            return this.getMove(n, false);
        }
    }
    
    public String getName() {
        return this._name;
    }
    
    public UCIButtonOption getUCIButtonOption(final String s) {
        final UCIOption uciOption = this.getUCIOption(s);
        if (uciOption instanceof UCIButtonOption) {
            return (UCIButtonOption)uciOption;
        }
        return null;
    }
    
    public UCICheckOption getUCICheckOption(final String s) {
        final UCIOption uciOption = this.getUCIOption(s);
        if (uciOption instanceof UCICheckOption) {
            return (UCICheckOption)uciOption;
        }
        return null;
    }
    
    public UCIComboOption getUCIComboOption(final String s) {
        final UCIOption uciOption = this.getUCIOption(s);
        if (uciOption instanceof UCIComboOption) {
            return (UCIComboOption)uciOption;
        }
        return null;
    }
    
    public List<UCIOption> getUCIOptions() {
        return Collections.unmodifiableList((List<? extends UCIOption>)this._options);
    }
    
    public UCISpinOption getUCISpinOption(final String s) {
        final UCIOption uciOption = this.getUCIOption(s);
        if (uciOption instanceof UCISpinOption) {
            return (UCISpinOption)uciOption;
        }
        return null;
    }
    
    public UCIStringOption getUCIStringOption(final String s) {
        final UCIOption uciOption = this.getUCIOption(s);
        if (uciOption instanceof UCIStringOption) {
            return (UCIStringOption)uciOption;
        }
        return null;
    }
    
    public boolean isBooted() {
        return this._status != -1;
    }
    
    public boolean isReady() {
        return this._status == 1;
    }
    
    public boolean isRunning() {
        return this._status == 4 || this._status == 2;
    }
    
    @Override
    public void optionChanged(final UCIOption uciOption) {
        this.sendLineToEngine(uciOption.getUCICommand());
    }
    
    public void positionChanged(final Position position) {
        synchronized (this) {
            this.setPosition(position.getFEN());
        }
    }
    
    protected void sendExitCommand() {
        this.sendLineToEngine("quit");
    }
    
    protected void sendIsReadyCommand() {
        this.sendLineToEngine("isready", "readyok");
    }
    
    protected void sendLineToEngine(final String s) {
        this._outgoingLinesQueue.add(this.createSendLineCommandRunnable(s));
    }
    
    protected void sendLineToEngine(final String s, final String s2) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                UCIEngine.this.waitForCommandAnswer(s2);
            }
        };
        this._outgoingLinesQueue.add(this.createSendLineCommandRunnable(s));
        this._outgoingLinesQueue.add(runnable);
    }
    
    public void sendStopAndGo(final String s) {
        synchronized (this) {
            final StringBuilder sb = new StringBuilder();
            sb.append("stop\ngo ");
            sb.append(s);
            this.sendLineToEngine(sb.toString());
        }
    }
    
    public void setAnalyzeMode(final boolean b) {
        final StringBuilder sb = new StringBuilder();
        sb.append("setoption name UCI_AnalyseMode value ");
        sb.append(b);
        this.sendLineToEngine(sb.toString());
    }
    
    public void setEngineDelegate(final EngineDelegate engineDelegate) {
        this._engineDelegate = engineDelegate;
    }
    
    public void setHashsize(final int n) {
        final StringBuilder sb = new StringBuilder();
        sb.append("setoption name Hash value ");
        sb.append(n);
        this.sendLineToEngine(sb.toString());
    }
    
    public void setPosition(final FEN fen) {
        synchronized (this) {
            if (new Position(fen).isValid()) {
                if (this.isRunning()) {
                    this.stop();
                }
                this._outgoingLinesQueue.add(new Runnable() {
                    @Override
                    public void run() {
                        UCIEngine.this._currentPosition = new Position(fen);
                    }
                });
                final StringBuilder sb = new StringBuilder();
                sb.append("position fen ");
                sb.append(fen.getFENString());
                this.sendLineToEngine(sb.toString());
            }
        }
    }
    
    public void setVariations(final int n) {
        final StringBuilder sb = new StringBuilder();
        sb.append("setoption name MultiPV value ");
        sb.append(n);
        this.sendLineToEngine(sb.toString());
    }
    
    public boolean start() {
        synchronized (this) {
            if (this._status != 1 && this._status != 5) {
                return false;
            }
            final StringBuilder sb = new StringBuilder();
            sb.append("position fen ");
            sb.append(this._currentPosition.getFEN().getFENString());
            this.sendLineToEngine(sb.toString());
            this.sendStopAndGo("infinite");
            this._status = 2;
            return true;
        }
    }
    
    public void startup() {
        if (!this.isBooted() && this._status != 0) {
            this._status = 0;
            if (this._autoInReader != null) {
                this._autoInReader.destroy();
            }
            (this._autoInReader = new InReader()).start();
            this._outWriter = new PrintWriter(this.getConnectedOutputStream(), true);
            if (this._outgoingLinesQueue != null) {
                this._outgoingLinesQueue.destroy();
            }
            (this._outgoingLinesQueue = new RunnableQueue()).start();
            this.sendLineToEngine("uci", "uciok");
            this.sendLineToEngine("uci", "uciok");
            this.sendIsReadyCommand();
        }
    }
    
    public boolean stop() {
        if (this.isRunning()) {
            this._status = 5;
            this.sendLineToEngine("stop", "bestmove");
            return true;
        }
        return this._status == 1;
    }
    
    protected boolean waitForCommandAnswer(final String s) {
        return this.waitForCommandAnswer(s, 1000L);
    }
    
    protected boolean waitForCommandAnswer(final String s, final long n) {
        final Waiter waiter = new Waiter(s);
        this.addEngineLineReceiver((EngineOutputLineReceiver)waiter);
        return waiter.waitForCommand(n);
    }
    
    private abstract class EngineOutputLineReceiver
    {
        abstract boolean receivedLine(final String p0);
    }
    
    private class GetMoveOutPutReceiver extends EngineOutputLineReceiver
    {
        SEP sep;
        
        @Override
        boolean receivedLine(String substring) {
            if (substring.startsWith("bestmove")) {
                final String substring2 = substring.substring(9);
                final int index = substring2.indexOf(32);
                substring = substring2;
                if (index > 0) {
                    substring = substring2.substring(0, index);
                }
                if (substring.length() >= 4) {
                    this.sep = UCIEngine.this.getSEP(substring);
                }
                return true;
            }
            return false;
        }
    }
    
    private class InReader extends Thread
    {
        private boolean _destroyThread;
        private BufferedReader _inReader;
        
        public InReader() {
            this._destroyThread = false;
            this._inReader = new BufferedReader(new InputStreamReader(UCIEngine.this.getConnectedInputStream()));
        }
        
        private String readEngineLine() throws IOException {
            String line;
            if ((line = this._inReader.readLine()) == null) {
                line = "";
            }
            return line;
        }
        
        @Override
        public void destroy() {
            this._destroyThread = true;
        }
        
        @Override
        public void run() {
        Label_0073_Outer:
            while (true) {
                Serializable engineLine = null;
                while (true) {
                    try {
                        engineLine = this.readEngineLine();
                        break Label_0073;
                    }
                    catch (IOException ex) {
                        final Logger instance = Logger.getInstance();
                        final StringBuilder sb = new StringBuilder();
                        sb.append("Error reading Engine line: status=");
                        sb.append(UCIEngine.this._status);
                        sb.append(" ");
                        instance.error("UCIEngine", sb.toString(), ex);
                        final long n = 100L;
                        Thread.sleep(n);
                    }
                    try {
                        final long n = 100L;
                        Thread.sleep(n);
                        if (this._destroyThread) {
                            UCIEngine.this._status = 6;
                            try {
                                this._inReader.close();
                                return;
                            }
                            catch (IOException engineLine) {
                                Logger.getInstance().debug("UCIEngine", "Error closing Engine Stream:", (Throwable)engineLine);
                                return;
                            }
                        }
                        if (engineLine == null) {
                            continue Label_0073_Outer;
                        }
                        UCIEngine.this.receivedEngineLine((String)engineLine);
                    }
                    catch (InterruptedException ex2) {
                        continue;
                    }
                    break;
                }
            }
        }
    }
    
    private class Waiter extends EngineOutputLineReceiver
    {
        private String _command;
        private boolean _commandReceived;
        private long _timeForTimeout;
        
        public Waiter(final String command) {
            this._commandReceived = false;
            this._command = command;
        }
        
        @Override
        boolean receivedLine(final String s) {
            if (!this._commandReceived) {
                this._commandReceived = s.startsWith(this._command);
            }
            return this._commandReceived;
        }
        
        public boolean waitForCommand(final long n) {
            this._timeForTimeout = System.currentTimeMillis() + n;
        Label_0024_Outer:
            while (!this._commandReceived) {
                while (true) {
                    try {
                        Thread.sleep(20L);
                        if (System.currentTimeMillis() >= this._timeForTimeout) {
                            return false;
                        }
                        continue Label_0024_Outer;
                    }
                    catch (InterruptedException ex) {
                        continue;
                    }
                    break;
                }
            }
            return true;
        }
    }
}
