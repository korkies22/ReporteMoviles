/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.EngineDelegate;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.engine.UCIButtonOption;
import de.cisha.chess.engine.UCICheckOption;
import de.cisha.chess.engine.UCIComboOption;
import de.cisha.chess.engine.UCIInfo;
import de.cisha.chess.engine.UCIOption;
import de.cisha.chess.engine.UCIOptionListener;
import de.cisha.chess.engine.UCISpinOption;
import de.cisha.chess.engine.UCIStringOption;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import de.cisha.chess.util.RunnableQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public abstract class UCIEngine
implements UCIOptionListener {
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
    protected Position _currentPosition = new Position(FEN.STARTING_POSITION);
    private EngineDelegate _engineDelegate;
    private String _name = "UCI-Engine";
    private List<UCIOption> _options = new ArrayList<UCIOption>();
    private PrintWriter _outWriter;
    private RunnableQueue _outgoingLinesQueue = new RunnableQueue();
    private List<EngineOutputLineReceiver> _receivers = new ArrayList<EngineOutputLineReceiver>(10);
    protected int _status = -1;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void addEngineLineReceiver(EngineOutputLineReceiver engineOutputLineReceiver) {
        List<EngineOutputLineReceiver> list = this._receivers;
        synchronized (list) {
            this._receivers.add(engineOutputLineReceiver);
            return;
        }
    }

    private List<UCIInfo> analysePosition(int n, int n2, boolean bl) {
        Cloneable cloneable = new TreeMap();
        if (this.isRunning()) {
            this.stop();
        }
        this.addEngineLineReceiver(new EngineOutputLineReceiver((Map)((Object)cloneable)){
            final /* synthetic */ Map val$mapLineToInfo;
            {
                this.val$mapLineToInfo = map;
                super();
            }

            @Override
            boolean receivedLine(String object) {
                if (object.startsWith("info") && object.contains("multipv")) {
                    object = UCIEngine.this.parseInfoLine((String)object);
                    int n = object.getLineNumber();
                    this.val$mapLineToInfo.put(n, object);
                } else if (object.startsWith("bestmove")) {
                    return true;
                }
                return false;
            }
        });
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("go depth ");
            stringBuilder.append(n);
            this.sendLineToEngine(stringBuilder.toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("go movetime ");
            stringBuilder.append(n2);
            this.sendLineToEngine(stringBuilder.toString());
        }
        this._status = 2;
        long l = bl ? 1000000000L : (long)(n2 + 500);
        if (this.waitForCommandAnswer("bestmove", l) ^ true) {
            this.stop();
            this.waitForCommandAnswer("bestmove", 500L);
        }
        cloneable = new ArrayList(cloneable.values());
        Collections.sort(cloneable, new Comparator<UCIInfo>(){

            @Override
            public int compare(UCIInfo uCIInfo, UCIInfo uCIInfo2) {
                return uCIInfo.getLineNumber() - uCIInfo2.getLineNumber();
            }
        });
        return cloneable;
    }

    private Runnable createSendLineCommandRunnable(final String string) {
        return new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                if (!UCIEngine.this._outWriter.checkError()) {
                    PrintWriter printWriter = UCIEngine.this._outWriter;
                    synchronized (printWriter) {
                        UCIEngine.this._outWriter.println(string);
                        return;
                    }
                }
                Logger logger = Logger.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("not able to send command to engine - engine closed:");
                stringBuilder.append(string);
                logger.error("Engine", stringBuilder.toString());
            }
        };
    }

    private String getDefaultValueString(String charSequence) {
        StringTokenizer stringTokenizer = new StringTokenizer((String)charSequence, " ");
        while (stringTokenizer.hasMoreTokens()) {
            if (!stringTokenizer.nextToken().equals("default") || !stringTokenizer.hasMoreTokens()) continue;
            String string = stringTokenizer.nextToken();
            charSequence = "";
            while (stringTokenizer.hasMoreTokens() && !charSequence.equals(" var") && !charSequence.equals(" min") && !charSequence.equals(" max")) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append((String)charSequence);
                string = stringBuilder.toString();
                charSequence = new StringBuilder();
                charSequence.append(" ");
                charSequence.append(stringTokenizer.nextToken());
                charSequence = charSequence.toString();
            }
            return string;
        }
        return null;
    }

    private SEP getMove(int n, boolean bl) {
        if (this.isRunning()) {
            this.stop();
        }
        GetMoveOutPutReceiver getMoveOutPutReceiver = new GetMoveOutPutReceiver();
        this.addEngineLineReceiver(getMoveOutPutReceiver);
        this._status = 2;
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("go depth ");
            stringBuilder.append(n);
            this.sendLineToEngine(stringBuilder.toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("go movetime ");
            stringBuilder.append(n);
            this.sendLineToEngine(stringBuilder.toString());
        }
        long l = bl ? 1000000000L : (long)(n + 500);
        if (this.waitForCommandAnswer("bestmove", l) ^ true) {
            this.stop();
            this.waitForCommandAnswer("bestmove", 500L);
        }
        return getMoveOutPutReceiver.sep;
    }

    private SEP getSEP(String string) {
        int n = 0;
        Square square = Square.instanceForFileAndRank(string.charAt(0), string.charAt(1) - 49 + 1);
        Square square2 = Square.instanceForFileAndRank(string.charAt(2), string.charAt(3) - 49 + 1);
        int n2 = n;
        if (string.trim().length() == 5) {
            n2 = n;
            switch (string.charAt(4)) {
                default: {
                    n2 = n;
                    break;
                }
                case 'Q': 
                case 'q': {
                    n2 = 3;
                    break;
                }
                case 'N': 
                case 'n': {
                    n2 = 1;
                    break;
                }
                case 'B': 
                case 'b': {
                    n2 = 2;
                }
                case 'R': 
                case 'r': 
            }
        }
        return new SEP(square, square2, n2);
    }

    private UCIOption getUCIOption(String string) {
        if (string != null) {
            for (UCIOption uCIOption : this._options) {
                if (!string.equals(uCIOption.getName())) continue;
                return uCIOption;
            }
        }
        return null;
    }

    private void handleIDLine(String string) {
        if (string.startsWith("id name ")) {
            this._name = string.substring(8);
            return;
        }
        string.startsWith("id author ");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyReceiversNewLine(String string) {
        List<EngineOutputLineReceiver> list = this._receivers;
        synchronized (list) {
            LinkedList<EngineOutputLineReceiver> linkedList = null;
            for (EngineOutputLineReceiver engineOutputLineReceiver : this._receivers) {
                if (!engineOutputLineReceiver.receivedLine(string)) continue;
                LinkedList<EngineOutputLineReceiver> linkedList2 = linkedList;
                if (linkedList == null) {
                    linkedList2 = new LinkedList<EngineOutputLineReceiver>();
                }
                linkedList2.add(engineOutputLineReceiver);
                linkedList = linkedList2;
            }
            if (linkedList != null) {
                this._receivers.removeAll(linkedList);
            }
            return;
        }
    }

    private UCIOption parseButtonOption(String string, String string2) {
        return new UCIButtonOption(string, this);
    }

    private UCIOption parseCheckOption(String string, String string2) {
        return new UCICheckOption(string, this, Boolean.parseBoolean(this.getDefaultValueString(string2)));
    }

    private UCIOption parseComboOption(String string, String charSequence) {
        String string2 = this.getDefaultValueString((String)charSequence);
        ArrayList<String> arrayList = new ArrayList<String>();
        StringTokenizer stringTokenizer = new StringTokenizer((String)charSequence, " ");
        boolean bl = false;
        charSequence = null;
        while (stringTokenizer.hasMoreTokens()) {
            String string3;
            String string4;
            block8 : {
                block7 : {
                    string4 = stringTokenizer.nextToken();
                    if (bl && string4.equals("var")) break block7;
                    string3 = charSequence;
                    if (stringTokenizer.hasMoreTokens()) break block8;
                }
                arrayList.add((String)charSequence);
                string3 = null;
            }
            boolean bl2 = bl;
            String string5 = string3;
            if (!bl) {
                bl2 = bl;
                string5 = string3;
                if (string4.equals("var")) {
                    bl2 = true;
                    string5 = null;
                }
            }
            bl = bl2;
            charSequence = string5;
            if (string4.equals("var")) continue;
            if (string5 == null) {
                charSequence = string4;
                bl = bl2;
                continue;
            }
            charSequence = new StringBuilder();
            charSequence.append(string5);
            charSequence.append(" ");
            charSequence.append(string4);
            charSequence = charSequence.toString();
            bl = bl2;
        }
        return new UCIComboOption(string, this, string2, arrayList);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private UCIInfo parseInfoLine(String var1_1) {
        var15_2 = new StringTokenizer((String)var1_1, " ");
        var4_6 = var5_5 = (var6_4 = (var8_3 = 0));
        var13_7 = "";
        var1_1 = null;
        block12 : while (var15_2.hasMoreTokens() != false) {
            block23 : {
                var14_15 = var15_2.nextToken();
                if (var14_15.equals("multipv")) {
                    if (!var15_2.hasMoreTokens()) continue;
                    var14_15 = var15_2.nextToken();
                    var8_3 = var7_10 = Integer.parseInt((String)var14_15);
                    continue;
                }
                if (var14_15.equals("depth")) {
                    if (!var15_2.hasMoreTokens()) continue;
                    var14_15 = var15_2.nextToken();
                    var6_4 = var7_10 = Integer.parseInt((String)var14_15);
                    continue;
                }
                if (var14_15.equals("nodes")) {
                    if (!var15_2.hasMoreTokens()) continue;
                    var14_15 = var15_2.nextToken();
                    var5_5 = var7_10 = Integer.parseInt((String)var14_15);
                    continue;
                }
                if (var14_15.equals("time")) {
                    if (!var15_2.hasMoreTokens()) continue;
                    var14_15 = var15_2.nextToken();
                    var4_6 = var7_10 = Integer.parseInt((String)var14_15);
                    continue;
                }
                if (!var14_15.equals("score")) ** GOTO lbl50
                if (!var15_2.hasMoreTokens()) continue;
                var14_15 = var15_2.nextToken();
                var16_18 = this._currentPosition;
                var11_13 = true;
                var9_11 = var16_18 != null && this._currentPosition.getActiveColor() != false;
                if (var14_15.equals("cp")) {
                    if (!var15_2.hasMoreTokens()) continue;
                    var1_1 = var15_2.nextToken();
                    var2_8 = 0.0f;
                    var7_10 = Integer.parseInt((String)var1_1);
                    var2_8 = (float)var7_10 / 100.0f;
                } else {
                    if (!var14_15.equals("mate") || !var15_2.hasMoreTokens()) continue;
                    var1_1 = var15_2.nextToken();
                    var7_10 = Integer.parseInt((String)var1_1);
                    break block23;
lbl50: // 1 sources:
                    if (!var14_15.equals("pv") || !var15_2.hasMoreTokens()) continue;
                    var14_15 = var13_7;
                    do {
                        var13_7 = var14_15;
                        if (!var15_2.hasMoreTokens()) continue block12;
                        var13_7 = new StringBuilder();
                        var13_7.append((String)var14_15);
                        var13_7.append(" ");
                        var13_7.append(var15_2.nextToken());
                        var14_15 = var13_7.toString();
                    } while (true);
                    catch (NumberFormatException var16_19) {}
                    var16_18 = Logger.getInstance();
                    var17_23 = new StringBuilder();
                    var17_23.append("invalid multipv from engine:");
                    var17_23.append((String)var14_15);
                    var16_18.debug("UCIEngine", var17_23.toString());
                    continue;
                    catch (NumberFormatException var16_20) {}
                    var16_18 = Logger.getInstance();
                    var17_23 = new StringBuilder();
                    var17_23.append("invalid depth from engine:");
                    var17_23.append((String)var14_15);
                    var16_18.debug("UCIEngine", var17_23.toString());
                    continue;
                    catch (NumberFormatException var16_21) {}
                    var16_18 = Logger.getInstance();
                    var17_23 = new StringBuilder();
                    var17_23.append("invalid nodes from engine:");
                    var17_23.append((String)var14_15);
                    var16_18.debug("UCIEngine", var17_23.toString());
                    continue;
                    catch (NumberFormatException var16_22) {}
                    var16_18 = Logger.getInstance();
                    var17_23 = new StringBuilder();
                    var17_23.append("invalid time from engine:");
                    var17_23.append((String)var14_15);
                    var16_18.debug("UCIEngine", var17_23.toString());
                    continue;
                    catch (NumberFormatException var14_16) {}
                    var14_15 = Logger.getInstance();
                    var16_18 = new StringBuilder();
                    var16_18.append("invalid eval from engine:");
                    var16_18.append((String)var1_1);
                    var14_15.debug("UCIEngine", var16_18.toString());
                }
                var3_9 = var2_8;
                if (!var9_11) {
                    var3_9 = var2_8 * -1.0f;
                }
                var1_1 = new EngineEvaluation(var3_9);
                continue;
                catch (NumberFormatException var14_17) {}
                var14_15 = Logger.getInstance();
                var16_18 = new StringBuilder();
                var16_18.append("invalid mate from engine:");
                var16_18.append((String)var1_1);
                var14_15.debug("UCIEngine", var16_18.toString());
                var7_10 = 1;
            }
            var10_12 = var7_10;
            if (var7_10 < 0) {
                var10_12 = var7_10 * -1;
                var11_13 = false;
            }
            var12_14 = var11_13;
            if (!var9_11) {
                var12_14 = var11_13 ^ true;
            }
            var1_1 = new EngineEvaluation(var10_12, var12_14);
        }
        return new UCIInfo(this._currentPosition, var8_3, (String)var13_7, (EngineEvaluation)var1_1, var6_4, var5_5, var4_6);
    }

    private UCIOption parseOptionLine(String string) {
        int n = string.indexOf("type ");
        if (string.startsWith("option name ") && n > 0) {
            int n2;
            String string2 = string.substring(12, n - 1);
            int n3 = n + 5;
            n = n2 = string.indexOf(32, n3);
            if (n2 < 0) {
                n = n2;
                if (string.length() > n3) {
                    n = string.length();
                }
            }
            if (n > 0) {
                String string3 = string.substring(n3, n);
                string = string.substring(n);
                if (string3.equals(UCIOption.UCIOptionType.UCIOPTION_TYPE_SPIN.getType())) {
                    return this.parseSpinOption(string2, string);
                }
                if (string3.equals(UCIOption.UCIOptionType.UCIOPTION_TYPE_STRING.getType())) {
                    return this.parseStringOption(string2, string);
                }
                if (string3.equals(UCIOption.UCIOptionType.UCIOPTION_TYPE_CHECK.getType())) {
                    return this.parseCheckOption(string2, string);
                }
                if (string3.equals(UCIOption.UCIOptionType.UCIOPTION_TYPE_BUTTON.getType())) {
                    return this.parseButtonOption(string2, string);
                }
                if (string3.equals(UCIOption.UCIOptionType.UCIOPTION_TYPE_COMBO.getType())) {
                    return this.parseComboOption(string2, string);
                }
            }
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private UCIOption parseSpinOption(String var1_1, String var2_2) {
        var6_4 = new StringTokenizer(var2_2, " ");
        var3_5 = Integer.MIN_VALUE;
        var4_6 = Integer.MAX_VALUE;
        do lbl-1000: // 6 sources:
        {
            if (var6_4.hasMoreTokens()) {
                var7_8 = var6_4.nextToken();
                if (var7_8.equals("min") && var6_4.hasMoreTokens()) {
                    var7_8 = var6_4.nextToken();
                    var3_5 = var5_7 = Integer.parseInt(var7_8);
                }
                if (!var7_8.equals("max") || !var6_4.hasMoreTokens()) continue;
                var7_8 = var6_4.nextToken();
                var4_6 = var5_7 = Integer.parseInt(var7_8);
            }
            var2_2 = this.getDefaultValueString(var2_2);
            var5_7 = Integer.parseInt(var2_2);
            return new UCISpinOption(var1_1, this, var3_5, var4_6, var5_7);
            break;
        } while (true);
        catch (NumberFormatException var2_3) {}
        {
            catch (NumberFormatException var7_9) {}
            continue;
            catch (NumberFormatException var7_10) {}
            ** while (true)
        }
        var5_7 = 0;
        return new UCISpinOption(var1_1, this, var3_5, var4_6, var5_7);
    }

    private UCIOption parseStringOption(String string, String string2) {
        return new UCIStringOption(string, this, this.getDefaultValueString(string2));
    }

    private void receivedEngineLine(String object) {
        this.notifyReceiversNewLine((String)object);
        if (object.startsWith("info ")) {
            if (object.contains("score") && object.contains("multipv")) {
                if (this._engineDelegate != null) {
                    object = this.parseInfoLine((String)object);
                    this._engineDelegate.engineReceivedInfo((UCIInfo)object);
                }
                this._status = 4;
                return;
            }
        } else if (object.startsWith("bestmove")) {
            if (this._status != 2) {
                this._status = 1;
                return;
            }
        } else if (object.startsWith("readyok")) {
            if (this._status != 2) {
                this._status = 1;
                return;
            }
        } else {
            if (object.startsWith("id")) {
                this.handleIDLine((String)object);
                return;
            }
            if (object.startsWith("option")) {
                if ((object = this.parseOptionLine((String)object)) != null) {
                    this._options.add((UCIOption)object);
                    return;
                }
            } else if (object.startsWith("uciok")) {
                this._status = 1;
            }
        }
    }

    public List<UCIInfo> analysePositionWithDepth(int n) {
        return this.analysePosition(n, 0, true);
    }

    public List<UCIInfo> analysePositionWithTime(int n) {
        boolean bl;
        UCICheckOption uCICheckOption = this.getUCICheckOption("OwnBook");
        if (uCICheckOption != null) {
            bl = (Boolean)uCICheckOption.getCurrentValue();
            uCICheckOption.setValue(false);
        } else {
            bl = false;
        }
        List<UCIInfo> list = this.analysePosition(0, n, false);
        if (uCICheckOption != null) {
            uCICheckOption.setValue(bl);
        }
        return list;
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

    public SEP getMoveWithDepth(int n) {
        synchronized (this) {
            SEP sEP = this.getMove(n, true);
            return sEP;
        }
    }

    public SEP getMoveWithThinkingTime(int n) {
        synchronized (this) {
            SEP sEP = this.getMove(n, false);
            return sEP;
        }
    }

    public String getName() {
        return this._name;
    }

    public UCIButtonOption getUCIButtonOption(String object) {
        if ((object = this.getUCIOption((String)object)) instanceof UCIButtonOption) {
            return (UCIButtonOption)object;
        }
        return null;
    }

    public UCICheckOption getUCICheckOption(String object) {
        if ((object = this.getUCIOption((String)object)) instanceof UCICheckOption) {
            return (UCICheckOption)object;
        }
        return null;
    }

    public UCIComboOption getUCIComboOption(String object) {
        if ((object = this.getUCIOption((String)object)) instanceof UCIComboOption) {
            return (UCIComboOption)object;
        }
        return null;
    }

    public List<UCIOption> getUCIOptions() {
        return Collections.unmodifiableList(this._options);
    }

    public UCISpinOption getUCISpinOption(String object) {
        if ((object = this.getUCIOption((String)object)) instanceof UCISpinOption) {
            return (UCISpinOption)object;
        }
        return null;
    }

    public UCIStringOption getUCIStringOption(String object) {
        if ((object = this.getUCIOption((String)object)) instanceof UCIStringOption) {
            return (UCIStringOption)object;
        }
        return null;
    }

    public boolean isBooted() {
        if (this._status != -1) {
            return true;
        }
        return false;
    }

    public boolean isReady() {
        if (this._status == 1) {
            return true;
        }
        return false;
    }

    public boolean isRunning() {
        if (this._status != 4 && this._status != 2) {
            return false;
        }
        return true;
    }

    @Override
    public void optionChanged(UCIOption uCIOption) {
        this.sendLineToEngine(uCIOption.getUCICommand());
    }

    public void positionChanged(Position position) {
        synchronized (this) {
            this.setPosition(position.getFEN());
            return;
        }
    }

    protected void sendExitCommand() {
        this.sendLineToEngine("quit");
    }

    protected void sendIsReadyCommand() {
        this.sendLineToEngine("isready", "readyok");
    }

    protected void sendLineToEngine(String object) {
        object = this.createSendLineCommandRunnable((String)object);
        this._outgoingLinesQueue.add((Runnable)object);
    }

    protected void sendLineToEngine(String object, String object2) {
        object2 = new Runnable((String)object2){
            final /* synthetic */ String val$answerToWaitFor;
            {
                this.val$answerToWaitFor = string;
            }

            @Override
            public void run() {
                UCIEngine.this.waitForCommandAnswer(this.val$answerToWaitFor);
            }
        };
        object = this.createSendLineCommandRunnable((String)object);
        this._outgoingLinesQueue.add((Runnable)object);
        this._outgoingLinesQueue.add((Runnable)object2);
    }

    public void sendStopAndGo(String string) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("stop\ngo ");
            stringBuilder.append(string);
            this.sendLineToEngine(stringBuilder.toString());
            return;
        }
    }

    public void setAnalyzeMode(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setoption name UCI_AnalyseMode value ");
        stringBuilder.append(bl);
        this.sendLineToEngine(stringBuilder.toString());
    }

    public void setEngineDelegate(EngineDelegate engineDelegate) {
        this._engineDelegate = engineDelegate;
    }

    public void setHashsize(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setoption name Hash value ");
        stringBuilder.append(n);
        this.sendLineToEngine(stringBuilder.toString());
    }

    public void setPosition(final FEN fEN) {
        synchronized (this) {
            if (new Position(fEN).isValid()) {
                if (this.isRunning()) {
                    this.stop();
                }
                Object object = new Runnable(){

                    @Override
                    public void run() {
                        UCIEngine.this._currentPosition = new Position(fEN);
                    }
                };
                this._outgoingLinesQueue.add((Runnable)object);
                object = new StringBuilder();
                object.append("position fen ");
                object.append(fEN.getFENString());
                this.sendLineToEngine(object.toString());
            }
            return;
        }
    }

    public void setVariations(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setoption name MultiPV value ");
        stringBuilder.append(n);
        this.sendLineToEngine(stringBuilder.toString());
    }

    public boolean start() {
        synchronized (this) {
            block4 : {
                int n;
                if (this._status == 1 || (n = this._status) == 5) break block4;
                return false;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("position fen ");
            stringBuilder.append(this._currentPosition.getFEN().getFENString());
            this.sendLineToEngine(stringBuilder.toString());
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
            this._autoInReader = new InReader();
            this._autoInReader.start();
            this._outWriter = new PrintWriter(this.getConnectedOutputStream(), true);
            if (this._outgoingLinesQueue != null) {
                this._outgoingLinesQueue.destroy();
            }
            this._outgoingLinesQueue = new RunnableQueue();
            this._outgoingLinesQueue.start();
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
        if (this._status == 1) {
            return true;
        }
        return false;
    }

    protected boolean waitForCommandAnswer(String string) {
        return this.waitForCommandAnswer(string, 1000L);
    }

    protected boolean waitForCommandAnswer(String object, long l) {
        object = new Waiter((String)object);
        this.addEngineLineReceiver((EngineOutputLineReceiver)object);
        return object.waitForCommand(l);
    }

    private abstract class EngineOutputLineReceiver {
        private EngineOutputLineReceiver() {
        }

        abstract boolean receivedLine(String var1);
    }

    private class GetMoveOutPutReceiver
    extends EngineOutputLineReceiver {
        SEP sep;

        private GetMoveOutPutReceiver() {
        }

        @Override
        boolean receivedLine(String string) {
            if (string.startsWith("bestmove")) {
                String string2 = string.substring(9);
                int n = string2.indexOf(32);
                string = string2;
                if (n > 0) {
                    string = string2.substring(0, n);
                }
                if (string.length() >= 4) {
                    this.sep = UCIEngine.this.getSEP(string);
                }
                return true;
            }
            return false;
        }
    }

    private class InReader
    extends Thread {
        private boolean _destroyThread = false;
        private BufferedReader _inReader;

        public InReader() {
            this._inReader = new BufferedReader(new InputStreamReader(UCIEngine.this.getConnectedInputStream()));
        }

        private String readEngineLine() throws IOException {
            String string;
            String string2 = string = this._inReader.readLine();
            if (string == null) {
                string2 = "";
            }
            return string2;
        }

        @Override
        public void destroy() {
            this._destroyThread = true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            do {
                String string = null;
                try {
                    String string2;
                    string = string2 = this.readEngineLine();
                }
                catch (IOException iOException) {
                    Logger logger = Logger.getInstance();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error reading Engine line: status=");
                    stringBuilder.append(UCIEngine.this._status);
                    stringBuilder.append(" ");
                    logger.error(UCIEngine.LOG_TAG, stringBuilder.toString(), iOException);
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException interruptedException) {}
                }
                if (this._destroyThread) {
                    UCIEngine.this._status = 6;
                    try {
                        this._inReader.close();
                        return;
                    }
                    catch (IOException iOException) {
                        Logger.getInstance().debug(UCIEngine.LOG_TAG, "Error closing Engine Stream:", iOException);
                        return;
                    }
                }
                if (string == null) continue;
                UCIEngine.this.receivedEngineLine(string);
            } while (true);
        }
    }

    private class Waiter
    extends EngineOutputLineReceiver {
        private String _command;
        private boolean _commandReceived = false;
        private long _timeForTimeout;

        public Waiter(String string) {
            this._command = string;
        }

        @Override
        boolean receivedLine(String string) {
            if (!this._commandReceived) {
                this._commandReceived = string.startsWith(this._command);
            }
            return this._commandReceived;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean waitForCommand(long l) {
            this._timeForTimeout = System.currentTimeMillis() + l;
            do {
                if (this._commandReceived) {
                    return true;
                }
                try {
                    Thread.sleep(20L);
                }
                catch (InterruptedException interruptedException) {}
            } while (System.currentTimeMillis() < this._timeForTimeout);
            return false;
        }
    }

}
