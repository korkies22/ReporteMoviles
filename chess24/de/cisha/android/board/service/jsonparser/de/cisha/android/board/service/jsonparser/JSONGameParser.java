/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 *  android.util.SparseBooleanArray
 *  android.util.SparseIntArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONGameParser
extends JSONAPIResultParser<Game> {
    public static final String IS_USERMOVE = "ium";
    private long _serverTimeOffset;

    public JSONGameParser() {
        this._serverTimeOffset = 0L;
    }

    public JSONGameParser(long l) {
        this._serverTimeOffset = l;
    }

    private static void buildMoveTreeNonRecursive(Game game, SparseArray<List<Integer>> object, SparseArray<String> sparseArray, SparseIntArray sparseIntArray, SparseBooleanArray sparseBooleanArray) {
        int n;
        int n2;
        Object object2;
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        for (n2 = 0; n2 < object.size(); ++n2) {
            n = object.keyAt(n2);
            object2 = ((List)object.get(n)).iterator();
            while (object2.hasNext()) {
                sparseIntArray2.put(((Integer)object2.next()).intValue(), n);
            }
        }
        object2 = new SparseArray();
        for (n2 = 0; n2 < sparseArray.size(); ++n2) {
            Serializable serializable;
            n = sparseArray.keyAt(n2);
            String string = (String)sparseArray.valueAt(n2);
            object = sparseIntArray2.get(n);
            object = object != null && object.intValue() != 0 ? (MoveContainer)object2.get(object.intValue()) : game;
            if (object != null) {
                serializable = object.getResultingPosition();
                Serializable serializable2 = serializable.createMoveFromCAN(string);
                if (serializable2 != null) {
                    serializable2.setMoveId(n);
                    serializable2.setMoveTimeInMills(sparseIntArray.get(n));
                    serializable2.setUserGenerated(sparseBooleanArray.get(n, false));
                    object.addMove((Move)serializable2);
                    object2.put(n, (Object)serializable2);
                    continue;
                }
                object = Logger.getInstance();
                serializable2 = new StringBuilder();
                serializable2.append("invalid can: ");
                serializable2.append(string);
                serializable2.append(" for id: ");
                serializable2.append(n);
                serializable2.append("in Poisition ");
                serializable2.append(serializable.getFEN().getFENString());
                object.error("JSONGameParser", serializable2.toString());
                continue;
            }
            object = Logger.getInstance();
            serializable = new StringBuilder();
            serializable.append("error finding parent for move with id: ");
            serializable.append(n);
            serializable.append(" can: ");
            serializable.append(string);
            object.error("JSONGameParser", serializable.toString());
        }
    }

    private GameResult parseGameResult(String string) {
        return GameResult.fromString(string);
    }

    /*
     * Exception decompiling
     */
    @Override
    public Game parseResult(JSONObject var1_1) throws InvalidJsonForObjectException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK]], but top level block is 10[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
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
}
