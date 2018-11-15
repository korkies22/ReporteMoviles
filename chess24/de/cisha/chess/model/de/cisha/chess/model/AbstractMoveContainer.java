/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.BaseObject;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.SEP;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMoveContainer
extends BaseObject
implements MoveContainer,
Serializable {
    private static final int MAINLINE = 1;
    private static final int NOT_MAINLINE = -1;
    private static final int UNDEFINED = 0;
    private static final long serialVersionUID = 8310540024786007658L;
    private String _comment = "";
    private int _isMainLine = 0;
    private int _moveId;
    private int _moveTimeInMills;
    private MoveContainer _parent;
    private String _symbolMisc;
    private String _symbolMove;
    private String _symbolPosition;
    private List<Move> _variants;
    private int _variationLevel = -1;
    private String _variationPrefix = null;

    public AbstractMoveContainer() {
        this._variants = new ArrayList<Move>(1);
    }

    protected AbstractMoveContainer(MoveContainer moveContainer) {
        if (moveContainer.getClass() != this.getClass()) {
            throw new RuntimeException("assure that the instance to copied is instance from same class and not from superclass");
        }
        this._variants = new ArrayList<Move>(1);
        if (moveContainer != null) {
            Iterator<Move> iterator = moveContainer.getChildren().iterator();
            while (iterator.hasNext()) {
                this.addMove(new Move(iterator.next()));
            }
        }
        this._moveId = moveContainer.getMoveId();
        this.setComment(moveContainer.getComment());
        this.setSymbolMove(moveContainer.getSymbolMove());
        this.setSymbolPosition(moveContainer.getSymbolPosition());
        this.setSymbolMisc(moveContainer.getSymbolMisc());
        this.setMoveTimeInMills(moveContainer.getMoveTimeInMills());
    }

    private static String getCharForVariationNumber(int n) {
        if (n == 0) {
            return "";
        }
        int n2 = 97;
        for (int i = 1; i < n; ++i) {
            char c = (char)(n2 + 1);
            n2 = c;
            if (c != '{') continue;
            n2 = 97;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append((char)n2);
        return stringBuilder.toString();
    }

    private void resetMainLineStatus() {
        if (this._isMainLine != 0) {
            this._isMainLine = 0;
            Iterator<Move> iterator = this.getChildren().iterator();
            while (iterator.hasNext()) {
                ((AbstractMoveContainer)iterator.next()).resetMainLineStatus();
            }
        }
    }

    private void resetVariationLevel() {
        this._variationLevel = -1;
        this._variationPrefix = null;
        Iterator<Move> iterator = this.getChildren().iterator();
        while (iterator.hasNext()) {
            ((AbstractMoveContainer)iterator.next()).resetVariationLevel();
        }
    }

    @Override
    public void addMove(Move move) {
        if (move != null) {
            this._variants.add(move);
            move.setParent(this);
        }
    }

    @Override
    public void addMoveInMainLine(Move move) {
        if (!this.hasChildren()) {
            this.addMove(move);
            return;
        }
        this.getLastMoveinMainLine().addMove(move);
    }

    @Override
    public boolean containsMove(Move moveContainer, MoveContainer object) {
        Object object2;
        LinkedList<SEP> linkedList = new LinkedList<SEP>();
        do {
            if (moveContainer instanceof Move) {
                linkedList.addFirst(moveContainer.getSEP());
            }
            if ((object2 = moveContainer.getParent()) == null) break;
            moveContainer = object2;
        } while (!object2.equals(object));
        object = linkedList.iterator();
        moveContainer = this;
        while (object.hasNext()) {
            object2 = (SEP)object.next();
            if (moveContainer.hasChild((SEP)object2)) {
                moveContainer = moveContainer.getChild((SEP)object2);
                continue;
            }
            return false;
        }
        return true;
    }

    protected abstract AbstractMoveContainer copy();

    @Override
    public List<Move> getAllMainLineMoves() {
        ArrayList<Move> arrayList = new ArrayList<Move>();
        for (Move move = this.getNextMove(); move != null; move = move.getNextMove()) {
            arrayList.add(move);
        }
        return arrayList;
    }

    @Override
    public Move getChild(SEP sEP) {
        for (Move move : this._variants) {
            if (!move.getSEP().equals(sEP)) continue;
            return move;
        }
        return null;
    }

    @Override
    public List<Move> getChildren() {
        return this._variants;
    }

    @Override
    public List<Move> getChildrenOfAllLevels() {
        ArrayList<Move> arrayList = new ArrayList<Move>(250);
        if (this.hasChildren()) {
            LinkedList<Move> linkedList = new LinkedList<Move>();
            linkedList.addAll(0, this.getChildren());
            do {
                Move move;
                if ((move = (Move)linkedList.pollFirst()) == null) continue;
                arrayList.add(move);
                if (!move.hasChildren()) continue;
                linkedList.addAll(0, move.getChildren());
            } while (linkedList.size() > 0);
        }
        return arrayList;
    }

    @Override
    public String getComment() {
        return this._comment;
    }

    @Override
    public Move getLastMoveinMainLine() {
        Move move;
        for (move = this.getNextMove(); move != null && move.hasChildren(); move = move.getNextMove()) {
        }
        return move;
    }

    @Override
    public int getMoveId() {
        return this._moveId;
    }

    @Override
    public int getMoveTimeInMills() {
        return this._moveTimeInMills;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Move getNextMove() {
        List<Move> list = this._variants;
        synchronized (list) {
            if (this._variants.isEmpty()) return null;
            return this._variants.get(0);
        }
    }

    @Override
    public MoveContainer getParent() {
        return this._parent;
    }

    @Override
    public String getSymbolMisc() {
        return this._symbolMisc;
    }

    @Override
    public String getSymbolMove() {
        return this._symbolMove;
    }

    @Override
    public String getSymbolPosition() {
        return this._symbolPosition;
    }

    public String getSymbols() {
        CharSequence charSequence;
        CharSequence charSequence2 = "";
        if (this._symbolMove != null) {
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append(this._symbolMove);
            charSequence2 = charSequence.toString();
        }
        charSequence = charSequence2;
        if (this._symbolPosition != null) {
            charSequence = new StringBuilder();
            charSequence.append((String)charSequence2);
            charSequence.append(this._symbolPosition);
            charSequence = charSequence.toString();
        }
        charSequence2 = charSequence;
        if (this._symbolMisc != null) {
            charSequence2 = new StringBuilder();
            charSequence2.append((String)charSequence);
            charSequence2.append(this._symbolMisc);
            charSequence2 = charSequence2.toString();
        }
        return charSequence2;
    }

    @Override
    public int getTotalTimeTakenInMills() {
        MoveContainer moveContainer;
        int n = this._moveTimeInMills;
        MoveContainer moveContainer2 = moveContainer = this;
        while (moveContainer2 != null && moveContainer != null) {
            MoveContainer moveContainer3 = moveContainer2.getParent();
            moveContainer = moveContainer3;
            if (moveContainer3 == null) continue;
            MoveContainer moveContainer4 = moveContainer3.getParent();
            moveContainer2 = moveContainer4;
            moveContainer = moveContainer3;
            if (moveContainer4 == null) continue;
            n += moveContainer4.getMoveTimeInMills();
            moveContainer2 = moveContainer4;
            moveContainer = moveContainer3;
        }
        return n;
    }

    @Override
    public int getVariationLevel() {
        if (this._variationLevel == -1) {
            int n = this._parent != null ? this._parent.getVariationLevel() : 0;
            this._variationLevel = n;
            if (this.isVariationStart()) {
                ++this._variationLevel;
            }
        }
        return this._variationLevel;
    }

    @Override
    public String getVariationPrefix() {
        if (this._variationPrefix == null) {
            MoveContainer moveContainer = this.getParent();
            if (moveContainer == null) {
                this._variationPrefix = "";
            } else {
                Object object = moveContainer.getChildren();
                int n = -1;
                int n2 = 0;
                object = object.iterator();
                while (object.hasNext()) {
                    if (((Move)object.next()).equals(this)) {
                        n = n2;
                    }
                    ++n2;
                }
                object = new StringBuilder();
                object.append(moveContainer.getVariationPrefix());
                object.append(AbstractMoveContainer.getCharForVariationNumber(n));
                this._variationPrefix = object.toString();
            }
        }
        return this._variationPrefix;
    }

    @Override
    public boolean hasChild(SEP sEP) {
        if (this.getChild(sEP) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasChildren() {
        if (this._variants != null && !this._variants.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean hasComment() {
        if (this._comment != null && !this._comment.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean hasParent() {
        if (this.getParent() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasSiblings() {
        MoveContainer moveContainer = this.getParent();
        if (moveContainer != null) {
            return moveContainer.hasVariants();
        }
        return false;
    }

    @Override
    public boolean hasVariants() {
        if (this._variants != null && this._variants.size() >= 2) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isMainLine() {
        int n = this._isMainLine;
        boolean bl = false;
        boolean bl2 = false;
        if (n != 0) {
            if (this._isMainLine > 0) {
                bl2 = true;
            }
            return bl2;
        }
        bl2 = bl;
        if (this.isMainMove()) {
            bl2 = bl;
            if (this.getParent().isMainLine()) {
                bl2 = true;
            }
        }
        if (bl2) {
            this._isMainLine = 1;
            return bl2;
        }
        this._isMainLine = -1;
        return bl2;
    }

    @Override
    public boolean isMainMove() {
        MoveContainer moveContainer = this.getParent();
        if (moveContainer != null) {
            return this.equals(moveContainer.getNextMove());
        }
        return false;
    }

    public boolean isVariationStart() {
        if (this.getParent() == null) {
            return false;
        }
        return this.isMainMove() ^ true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void promoteMove(Move object) {
        List<Move> list = this._variants;
        synchronized (list) {
            Iterator<Move> iterator = this._variants.iterator();
            AbstractMoveContainer abstractMoveContainer = null;
            while (iterator.hasNext()) {
                Move move = iterator.next();
                if (!move.equals(object)) continue;
                abstractMoveContainer = move;
            }
            if (abstractMoveContainer != null) {
                this._variants.remove(abstractMoveContainer);
                this._variants.add(0, (Move)abstractMoveContainer);
            }
            object = this.getChildren().iterator();
            while (object.hasNext()) {
                abstractMoveContainer = (AbstractMoveContainer)object.next();
                AbstractMoveContainer.super.resetVariationLevel();
                abstractMoveContainer.resetMainLineStatus();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void removeChildAllMoves() {
        List<Move> list = this._variants;
        synchronized (list) {
            Iterator<Move> iterator = this._variants.iterator();
            do {
                if (!iterator.hasNext()) {
                    this._variants.clear();
                    return;
                }
                Move move = iterator.next();
                move.setParent(null);
                move.removeChildAllMoves();
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void removeMove(Move move) {
        List<Move> list = this._variants;
        synchronized (list) {
            int n = 0;
            do {
                block6 : {
                    block5 : {
                        if (n >= this._variants.size()) break block5;
                        if (!this._variants.get(n).equals(move)) break block6;
                        move.setParent(null);
                        this._variants.remove(n);
                        this.resetVariationLevel();
                        this.resetMainLineStatus();
                    }
                    return;
                }
                ++n;
            } while (true);
        }
    }

    @Override
    public void setComment(String string) {
        this._comment = string;
    }

    public void setMoveId(int n) {
        this._moveId = n;
    }

    @Override
    public void setMoveTimeInMills(int n) {
        this._moveTimeInMills = n;
    }

    protected void setParent(MoveContainer moveContainer) {
        boolean bl = moveContainer != this._parent;
        this._parent = moveContainer;
        if (bl) {
            this.resetMainLineStatus();
            this.resetVariationLevel();
        }
    }

    @Override
    public void setSymbolMisc(String string) {
        this._symbolMisc = string;
    }

    @Override
    public void setSymbolMove(String string) {
        this._symbolMove = string;
    }

    @Override
    public void setSymbolPosition(String string) {
        this._symbolPosition = string;
    }
}
