/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine.model;

import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.android.board.playzone.engine.model.IEngineHumanization;
import de.cisha.android.stockfish.StockfishEngineStrength;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Rating;
import de.cisha.chess.util.Logger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class AbstractSimpleEngineHumanization
implements IEngineHumanization {
    private List<Float> _allEvals;
    private int _elo;
    private boolean _engineIsWhite;
    private int _lastDrawOffer;
    private StockfishEngineStrength _strength;

    public AbstractSimpleEngineHumanization(int n, boolean bl) {
        this._engineIsWhite = bl;
        this._allEvals = new LinkedList<Float>();
        this._elo = n;
        this._strength = new StockfishEngineStrength(this._elo);
    }

    private float diameterOfValueList(List<Float> object) {
        object = object.iterator();
        float f = 1000000.0f;
        float f2 = -1000000.0f;
        while (object.hasNext()) {
            float f3 = ((Float)object.next()).floatValue();
            f = Math.min(f, f3);
            f2 = Math.max(f2, f3);
        }
        return f2 - f;
    }

    private long getShortThinkingTime() {
        Random random = new Random();
        long l = this.getRemainingMillis() < 10000L ? this.getRemainingMillis() / 5L : 2000L;
        return 100 + random.nextInt((int)l);
    }

    private long getSleepingTime(List<EvaluationInfo> object) {
        long l;
        long l2;
        long l3 = l = this.getMaximumThinkingTimeForNextMove();
        if (object.size() > 0) {
            int n = 0;
            float f = object.get(0).getEval();
            boolean bl = this._engineIsWhite;
            int n2 = -1;
            int n3 = bl ? 1 : -1;
            float f2 = n3;
            if (object.size() > 1) {
                float f3 = object.get(1).getEval();
                n3 = n2;
                if (this._engineIsWhite) {
                    n3 = 1;
                }
                l2 = l;
                n2 = n;
                if (f * f2 - f3 * (float)n3 > 1.0f) {
                    l2 = l;
                    n2 = n;
                    if (((EvaluationInfo)object.get(0)).getMove().isTaking()) {
                        n2 = 1;
                        l2 = l;
                    }
                }
            } else {
                l2 = Math.min(this.getShortThinkingTime(), l);
                n2 = n;
            }
            l3 = l2;
            if (n2 != 0) {
                l3 = Math.min(this.getShortThinkingTime(), l2);
            }
        }
        double d = l3;
        l3 = (long)(0.7 * d + 0.6 * d * new Random().nextDouble()) - this.getMillisSinceLastMove();
        l2 = 0L;
        if (l3 > 0L) {
            l2 = l3;
        }
        object = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sleeping for ");
        stringBuilder.append(l2);
        stringBuilder.append(" millis");
        object.debug("AbstracSimpleEngineHumanization", stringBuilder.toString());
        return l2;
    }

    private long getThinkingTimeForOpeningMoves(int n) {
        long l;
        long l2 = this.getBaseTime() / 50L;
        if (l2 < (l = (long)(1000 * n))) {
            return l2;
        }
        return l;
    }

    @Override
    public boolean acceptDrawOffer() {
        int n = this._allEvals.size();
        int n2 = this._lastDrawOffer;
        this._lastDrawOffer = n;
        if (n - n2 <= 2) {
            return false;
        }
        if (n < 15) {
            return false;
        }
        float f = this._allEvals.get(this._allEvals.size() - 1).floatValue();
        float f2 = new Random().nextFloat();
        boolean bl = f < 0.0f && -1.0f < f && (double)f2 < 0.1 || f <= -1.0f && -2.0f < f && (double)f2 < 0.5 || f <= -2.0f && (double)f2 < 0.9;
        boolean bl2 = bl;
        if (!bl) {
            bl2 = bl;
            if (f < 3.0f) {
                n = n >= 30 ? 1 : 0;
                n2 = this.getActionCounter() > 20 ? 1 : 0;
                double d = 0.5 * (double)(this._elo - 2700) / (double)-1600;
                boolean bl3 = (double)this.diameterOfValueList(this._allEvals) > 0.3 + d;
                if (!(bl || n != 0 && n2 != 0 && bl3)) {
                    return false;
                }
                bl2 = true;
            }
        }
        return bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Move chooseMove(List<EvaluationInfo> list) {
        long l;
        if (list == null || list.size() == 0) throw new RuntimeException("moveInfos cant be empty!");
        int n = 0;
        Object object2 = list.get(0);
        Move move = object2.getMove();
        boolean bl = this._engineIsWhite;
        int n2 = 1;
        int n3 = bl ? 1 : -1;
        float f = object2.getEval();
        float f2 = n3;
        this._allEvals.add(Float.valueOf(f *= f2));
        Object object = move;
        if (list.size() > 1) {
            float f3 = this._strength.getSmallBlunderProbability();
            float f4 = new Random().nextFloat();
            n3 = f4 < f3 ? 1 : 0;
            object2 = move;
            if (n3 != 0) {
                object = list.get(1);
                f3 = this._strength.getSmallBlunderEvalDiff();
                object2 = move;
                if (f - object.getEval() * f2 < f3) {
                    object2 = object.getMove();
                }
            }
            n3 = n;
            if (f4 <= this._strength.getBigBlunderProbability()) {
                n3 = 1;
            }
            object = object2;
            if (n3 != 0) {
                n3 = n2;
                do {
                    object = object2;
                    if (n3 >= list.size()) break;
                    object = list.get(n3);
                    f4 = this._strength.getBigBlunderEvalDiff();
                    if (f - object.getEval() * f2 < f4) {
                        object2 = object.getMove();
                    }
                    ++n3;
                } while (true);
            }
        }
        if ((l = this.getSleepingTime(list)) <= 0L) return object;
        try {
            Thread.sleep(l);
        }
        catch (InterruptedException interruptedException) {
            return object;
        }
        return object;
    }

    protected abstract int getActionCounter();

    protected abstract long getBaseTime();

    @Override
    public long getMaximumThinkingTimeForNextMove() {
        long l = this.getBaseTime();
        long l2 = l / 50L;
        long l3 = this._allEvals.size() < 10 ? this.getThinkingTimeForOpeningMoves(this._allEvals.size() + 1) : l2;
        if (this.getRemainingMillis() < l / 5L) {
            l3 = this.getRemainingMillis() / 10L;
        }
        l = l3;
        if (l3 > l2) {
            l = l2;
        }
        float f = new Random().nextFloat();
        double d = l;
        l3 = l2 = (long)(0.8 * d + 0.4 * (double)f * d);
        if (l2 < 200L) {
            l3 = 200L;
        }
        Logger logger = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("desired thinkingTime: ");
        stringBuilder.append(l3);
        stringBuilder.append(" millis");
        logger.debug("AbstractSimpleEngineHumanization", stringBuilder.toString());
        return l3;
    }

    protected abstract long getMillisSinceLastMove();

    @Override
    public Rating getRating() {
        return new Rating(this._elo);
    }

    protected abstract long getRemainingMillis();

    @Override
    public boolean resign() {
        if (this._elo < 1100) {
            return false;
        }
        float f = -4 - (2700 - this._elo) / 300;
        if (this._allEvals.size() >= 5) {
            Object object = Logger.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" elo:");
            stringBuilder.append(this._elo);
            stringBuilder.append(", thres:");
            stringBuilder.append(f);
            object.debug("chess.resign", stringBuilder.toString());
            boolean bl = true;
            object = this._allEvals.subList(this._allEvals.size() - 5, this._allEvals.size()).iterator();
            while (object.hasNext()) {
                if (((Float)object.next()).floatValue() <= f) continue;
                bl = false;
            }
            return bl;
        }
        return false;
    }
}
