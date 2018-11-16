// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine.model;

import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Move;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.engine.EvaluationInfo;
import java.util.Random;
import java.util.Iterator;
import java.util.LinkedList;
import de.cisha.android.stockfish.StockfishEngineStrength;
import java.util.List;

public abstract class AbstractSimpleEngineHumanization implements IEngineHumanization
{
    private List<Float> _allEvals;
    private int _elo;
    private boolean _engineIsWhite;
    private int _lastDrawOffer;
    private StockfishEngineStrength _strength;
    
    public AbstractSimpleEngineHumanization(final int elo, final boolean engineIsWhite) {
        this._engineIsWhite = engineIsWhite;
        this._allEvals = new LinkedList<Float>();
        this._elo = elo;
        this._strength = new StockfishEngineStrength(this._elo);
    }
    
    private float diameterOfValueList(final List<Float> list) {
        final Iterator<Float> iterator = list.iterator();
        float min = 1000000.0f;
        float max = -1000000.0f;
        while (iterator.hasNext()) {
            final float floatValue = iterator.next();
            min = Math.min(min, floatValue);
            max = Math.max(max, floatValue);
        }
        return max - min;
    }
    
    private long getShortThinkingTime() {
        final Random random = new Random();
        long n;
        if (this.getRemainingMillis() < 10000L) {
            n = this.getRemainingMillis() / 5L;
        }
        else {
            n = 2000L;
        }
        return 100 + random.nextInt((int)n);
    }
    
    private long getSleepingTime(final List<EvaluationInfo> list) {
        long n2;
        final long n = n2 = this.getMaximumThinkingTimeForNextMove();
        if (list.size() > 0) {
            final boolean b = false;
            final float eval = list.get(0).getEval();
            final boolean engineIsWhite = this._engineIsWhite;
            final int n3 = -1;
            int n4;
            if (engineIsWhite) {
                n4 = 1;
            }
            else {
                n4 = -1;
            }
            final float n5 = n4;
            long min;
            boolean b2;
            if (list.size() > 1) {
                final float eval2 = list.get(1).getEval();
                int n6 = n3;
                if (this._engineIsWhite) {
                    n6 = 1;
                }
                min = n;
                b2 = b;
                if (eval * n5 - eval2 * n6 > 1.0f) {
                    min = n;
                    b2 = b;
                    if (list.get(0).getMove().isTaking()) {
                        b2 = true;
                        min = n;
                    }
                }
            }
            else {
                min = Math.min(this.getShortThinkingTime(), n);
                b2 = b;
            }
            n2 = min;
            if (b2) {
                n2 = Math.min(this.getShortThinkingTime(), min);
            }
        }
        final double n7 = n2;
        final long n8 = (long)(0.7 * n7 + 0.6 * n7 * new Random().nextDouble()) - this.getMillisSinceLastMove();
        long n9 = 0L;
        if (n8 > 0L) {
            n9 = n8;
        }
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("sleeping for ");
        sb.append(n9);
        sb.append(" millis");
        instance.debug("AbstracSimpleEngineHumanization", sb.toString());
        return n9;
    }
    
    private long getThinkingTimeForOpeningMoves(final int n) {
        final long n2 = this.getBaseTime() / 50L;
        final long n3 = 1000 * n;
        if (n2 < n3) {
            return n2;
        }
        return n3;
    }
    
    @Override
    public boolean acceptDrawOffer() {
        final int size = this._allEvals.size();
        final int lastDrawOffer = this._lastDrawOffer;
        this._lastDrawOffer = size;
        if (size - lastDrawOffer <= 2) {
            return false;
        }
        if (size < 15) {
            return false;
        }
        final float floatValue = this._allEvals.get(this._allEvals.size() - 1);
        final float nextFloat = new Random().nextFloat();
        boolean b2;
        final boolean b = b2 = ((floatValue < 0.0f && -1.0f < floatValue && nextFloat < 0.1) || (floatValue <= -1.0f && -2.0f < floatValue && nextFloat < 0.5) || (floatValue <= -2.0f && nextFloat < 0.9));
        if (!b) {
            b2 = b;
            if (floatValue < 3.0f) {
                final boolean b3 = size >= 30;
                final boolean b4 = this.getActionCounter() > 20;
                final boolean b5 = this.diameterOfValueList(this._allEvals) > 0.3 + 0.5 * (this._elo - 2700) / -1600;
                if (!b && (!b3 || !b4 || !b5)) {
                    return false;
                }
                b2 = true;
            }
        }
        return b2;
    }
    
    @Override
    public Move chooseMove(final List<EvaluationInfo> list) {
        Label_0320: {
            if (list == null || list.size() == 0) {
                break Label_0320;
            }
            final boolean b = false;
            final EvaluationInfo evaluationInfo = list.get(0);
            final Move move = evaluationInfo.getMove();
            final boolean engineIsWhite = this._engineIsWhite;
            final int n = 1;
            int n2;
            if (engineIsWhite) {
                n2 = 1;
            }
            else {
                n2 = -1;
            }
            final float eval = evaluationInfo.getEval();
            final float n3 = n2;
            final float n4 = eval * n3;
            this._allEvals.add(n4);
            Move move2 = move;
            if (list.size() > 1) {
                final float smallBlunderProbability = this._strength.getSmallBlunderProbability();
                final float nextFloat = new Random().nextFloat();
                final boolean b2 = nextFloat < smallBlunderProbability;
                Move move3 = move;
                if (b2) {
                    final EvaluationInfo evaluationInfo2 = list.get(1);
                    final float smallBlunderEvalDiff = this._strength.getSmallBlunderEvalDiff();
                    move3 = move;
                    if (n4 - evaluationInfo2.getEval() * n3 < smallBlunderEvalDiff) {
                        move3 = evaluationInfo2.getMove();
                    }
                }
                boolean b3 = b;
                if (nextFloat <= this._strength.getBigBlunderProbability()) {
                    b3 = true;
                }
                move2 = move3;
                if (b3) {
                    int n5 = n;
                    while (true) {
                        move2 = move3;
                        if (n5 >= list.size()) {
                            break;
                        }
                        final EvaluationInfo evaluationInfo3 = list.get(n5);
                        if (n4 - evaluationInfo3.getEval() * n3 < this._strength.getBigBlunderEvalDiff()) {
                            move3 = evaluationInfo3.getMove();
                        }
                        ++n5;
                    }
                }
            }
            final long sleepingTime = this.getSleepingTime(list);
            if (sleepingTime <= 0L) {
                return move2;
            }
            try {
                Thread.sleep(sleepingTime);
                return move2;
                throw new RuntimeException("moveInfos cant be empty!");
            }
            catch (InterruptedException ex) {
                return move2;
            }
        }
    }
    
    protected abstract int getActionCounter();
    
    protected abstract long getBaseTime();
    
    @Override
    public long getMaximumThinkingTimeForNextMove() {
        final long baseTime = this.getBaseTime();
        final long n = baseTime / 50L;
        long thinkingTimeForOpeningMoves;
        if (this._allEvals.size() < 10) {
            thinkingTimeForOpeningMoves = this.getThinkingTimeForOpeningMoves(this._allEvals.size() + 1);
        }
        else {
            thinkingTimeForOpeningMoves = n;
        }
        if (this.getRemainingMillis() < baseTime / 5L) {
            thinkingTimeForOpeningMoves = this.getRemainingMillis() / 10L;
        }
        long n2 = thinkingTimeForOpeningMoves;
        if (thinkingTimeForOpeningMoves > n) {
            n2 = n;
        }
        final float nextFloat = new Random().nextFloat();
        final double n3 = n2;
        long n4;
        if ((n4 = (long)(0.8 * n3 + 0.4 * nextFloat * n3)) < 200L) {
            n4 = 200L;
        }
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("desired thinkingTime: ");
        sb.append(n4);
        sb.append(" millis");
        instance.debug("AbstractSimpleEngineHumanization", sb.toString());
        return n4;
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
        final float n = -4 - (2700 - this._elo) / 300;
        if (this._allEvals.size() >= 5) {
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append(" elo:");
            sb.append(this._elo);
            sb.append(", thres:");
            sb.append(n);
            instance.debug("chess.resign", sb.toString());
            boolean b = true;
            final Iterator<Float> iterator = this._allEvals.subList(this._allEvals.size() - 5, this._allEvals.size()).iterator();
            while (iterator.hasNext()) {
                if (iterator.next() > n) {
                    b = false;
                }
            }
            return b;
        }
        return false;
    }
}
