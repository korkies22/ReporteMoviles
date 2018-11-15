/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

public class BroadcastGameHolder
extends GameHolder {
    private boolean _isInLiveMode = true;
    private Set<LiveModeChangeListener> _liveModeListener;
    private int _numberOfLiveMoves = -1;

    public BroadcastGameHolder(Game game) {
        super(game);
    }

    private void incrementNumberOfLiveMoves(boolean bl, int n) {
        int n2 = this._numberOfLiveMoves;
        int n3 = 0;
        n2 = n2 > 0 ? this._numberOfLiveMoves : 0;
        int n4 = n3;
        if (!bl) {
            n4 = this._isInLiveMode ? n3 : n2 + n;
        }
        if (n4 != this._numberOfLiveMoves) {
            this._numberOfLiveMoves = n4;
            Iterator<LiveModeChangeListener> iterator = this._liveModeListener.iterator();
            while (iterator.hasNext()) {
                iterator.next().numberOfNewMovesChanged(this._numberOfLiveMoves);
            }
        }
    }

    private void initListenersLazy() {
        if (this._liveModeListener == null) {
            this._liveModeListener = Collections.newSetFromMap(new WeakHashMap());
        }
    }

    private void notifyObserversLiveModeChanged(boolean bl) {
        this.initListenersLazy();
        Iterator<LiveModeChangeListener> iterator = this._liveModeListener.iterator();
        while (iterator.hasNext()) {
            iterator.next().liveModeChanged(bl);
        }
        if (bl) {
            this.incrementNumberOfLiveMoves(true, 0);
        }
    }

    private void updateIsInLiveMode() {
        MoveContainer moveContainer = this.getCurrentMove();
        boolean bl = true;
        if (moveContainer == null) {
            moveContainer = this.getRootMoveContainer();
            if (moveContainer.hasChildren()) {
                bl = moveContainer.getFirstMove().isUserMove();
            }
        } else if (moveContainer.isUserMove()) {
            bl = false;
        } else if (((AbstractMoveContainer)moveContainer).hasChildren()) {
            bl = ((AbstractMoveContainer)moveContainer).getNextMove().isUserMove();
        }
        if (this._isInLiveMode != bl) {
            this._isInLiveMode = bl;
            this.notifyObserversLiveModeChanged(bl);
        }
    }

    public void addLiveModeChangeListener(LiveModeChangeListener liveModeChangeListener) {
        this.initListenersLazy();
        this._liveModeListener.add(liveModeChangeListener);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Move addNewServerMove(int var1_1, SEP var2_2, int var3_4, int var4_5) {
        block11 : {
            // MONITORENTER : this
            if (var1_1 != 0) ** GOTO lbl6
            var5_6 = this.getRootMoveContainer();
            break block11;
lbl6: // 1 sources:
            var5_6 = this.getLastLiveGameMove();
        }
        var6_7 = null;
        var7_8 = null;
        if (var5_6 != null) {
            if (var5_6.hasChild((SEP)var2_2)) {
                var2_2 = var5_6.getChild((SEP)var2_2);
                var2_2.setMoveId(var4_5);
                var2_2.setMoveTimeInMills(var3_4);
                var2_2.setUserGenerated(false);
                this.incrementNumberOfLiveMoves(false, 1);
                if (var2_2.hasSiblings()) {
                    var5_6.promoteMove((Move)var2_2);
                }
                this.notifyAllMovesChanged();
                super.notifyMoveChanged();
            } else {
                var6_7 = var5_6.getResultingPosition().createMoveFrom((SEP)var2_2);
                var2_2 = var7_8;
                if (var6_7 != null) {
                    var5_6.addMove((Move)var6_7);
                    var6_7.setMoveId(var4_5);
                    var6_7.setMoveTimeInMills(var3_4);
                    var6_7.setUserGenerated(false);
                    this.incrementNumberOfLiveMoves(false, 1);
                    if (var6_7.hasSiblings()) {
                        var5_6.promoteMove((Move)var6_7);
                        this.notifyAllMovesChanged();
                        super.notifyMoveChanged();
                        var2_2 = var6_7;
                    } else {
                        this.notifyObserversNewMove((Move)var6_7);
                        var2_2 = var6_7;
                    }
                }
            }
            var6_7 = var2_2;
            if (this._isInLiveMode) {
                this.gotoLiveGamePosition();
                var6_7 = var2_2;
            }
        }
        // MONITOREXIT : this
        return var6_7;
    }

    public void deleteMoveInMainlineWithId(int n) {
        Move move;
        Object object;
        Move move2;
        block6 : {
            Iterator<Move> iterator;
            block5 : {
                move = this.getCurrentMove();
                iterator = this.getRootMoveContainer().getAllMainLineMoves().iterator();
                do {
                    boolean bl = iterator.hasNext();
                    object = null;
                    move2 = null;
                    if (!bl) break block5;
                } while ((object = iterator.next()).getMoveId() != n);
                if (object.getParent() instanceof Move) {
                    move2 = (Move)object.getParent();
                }
                object.removeFromParent();
                iterator = object.getAllMainLineMoves().iterator();
                n = 1;
                while (iterator.hasNext() && !iterator.next().isUserMove()) {
                    ++n;
                }
                this.incrementNumberOfLiveMoves(false, - n);
                this.notifyAllMovesChanged();
                break block6;
            }
            iterator = null;
            move2 = object;
            object = iterator;
        }
        if (object != null) {
            if (object != move && !object.getChildrenOfAllLevels().contains(move)) {
                this.selectMove(move);
                return;
            }
            this.selectMove(move2);
        }
    }

    @Override
    public Move doMoveInCurrentPosition(SEP serializable) {
        serializable = super.doMoveInCurrentPosition((SEP)serializable, true);
        this.updateIsInLiveMode();
        return serializable;
    }

    public void enableLiveMode() {
        this.selectMove(this.getLastLiveGameMove());
    }

    public Move getLastLiveGameMove() {
        Move move;
        Iterator<Move> iterator = this.getRootMoveContainer().getAllMainLineMoves().iterator();
        Move move2 = null;
        while (iterator.hasNext() && !(move = iterator.next()).isUserMove()) {
            move2 = move;
        }
        return move2;
    }

    public void gotoLastNonUserMove() {
        boolean bl;
        MoveContainer moveContainer = this.getCurrentMove();
        while ((bl = moveContainer instanceof Move) && moveContainer.isUserMove()) {
            moveContainer = moveContainer.getParent();
        }
        if (bl) {
            this.selectMove((Move)moveContainer);
            return;
        }
        this.gotoStartingPosition();
    }

    public void gotoLiveGamePosition() {
        this.selectMove(this.getLastLiveGameMove());
    }

    public boolean isInLiveMode() {
        return this._isInLiveMode;
    }

    @Override
    protected void notifyMoveChanged() {
        super.notifyMoveChanged();
        this.updateIsInLiveMode();
    }

    @Override
    public void setNewGame(Game game) {
        super.setNewGame(game);
        this.enableLiveMode();
    }

    public void setNewGameAndMergeOldUserMoves(Game game) {
        boolean bl;
        Object object;
        Object var7_4;
        LinkedList<SEP> linkedList = new LinkedList<SEP>();
        Object object2 = this.getCurrentMove();
        do {
            var7_4 = null;
            if (object2 == null || !object2.hasParent()) break;
            linkedList.addFirst(object2.getSEP());
            if (object2.getParent() instanceof Move) {
                object2 = (Move)object2.getParent();
                continue;
            }
            object2 = null;
        } while (true);
        Serializable serializable = new LinkedList<Move>();
        object2 = new LinkedList();
        object2.addAll(this.getRootMoveContainer().getChildren());
        while (!object2.isEmpty()) {
            object = (Move)object2.removeFirst();
            if (object.isUserMove() && !game.containsMove((Move)object, this.getRootMoveContainer())) {
                serializable.addFirst((Move)object);
                continue;
            }
            object2.addAll(object.getChildren());
        }
        do {
            block16 : {
                boolean bl2 = serializable.isEmpty();
                boolean bl3 = true;
                bl = false;
                if (bl2) break;
                LinkedList<SEP> linkedList2 = new LinkedList<SEP>();
                for (object2 = object = (MoveContainer)serializable.pollFirst(); object2 != null && object2 instanceof Move; object2 = object2.getParent()) {
                    linkedList2.addFirst(((Move)object2).getSEP());
                }
                object2 = game;
                do {
                    bl = bl3;
                    if (linkedList2.isEmpty()) break block16;
                    SEP sEP = (SEP)linkedList2.pollFirst();
                    if (object2.hasChild(sEP)) {
                        if (linkedList2.isEmpty()) continue;
                        object2 = object2.getChild(sEP);
                        continue;
                    }
                    if (!linkedList2.isEmpty()) break;
                } while (true);
                bl = false;
            }
            if (!bl || !(object instanceof Move)) continue;
            object = (Move)object;
            object.removeFromParent();
            object2.addMove((Move)object);
        } while (true);
        if (!linkedList.isEmpty()) {
            serializable = (SEP)linkedList.getLast();
            object = (SEP)linkedList.pollFirst();
            bl = false;
            object2 = game;
            while (object2.hasChild((SEP)object)) {
                object2 = object2.getChild((SEP)object);
                if (!linkedList.isEmpty()) {
                    bl = serializable == linkedList.getFirst();
                }
                object = (SEP)linkedList.pollFirst();
            }
        } else {
            object2 = game;
        }
        object = var7_4;
        if (bl) {
            object = var7_4;
            if (object2 instanceof Move) {
                object = (Move)object2;
            }
        }
        if (object == null) {
            this.setNewGame(game);
        } else {
            super.setNewGame(game);
            this.selectMove((Move)object);
        }
        this.updateIsInLiveMode();
    }

    public static interface LiveModeChangeListener {
        public void liveModeChanged(boolean var1);

        public void numberOfNewMovesChanged(int var1);
    }

}
