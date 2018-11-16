// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public abstract class AbstractMoveContainer extends BaseObject implements MoveContainer, Serializable
{
    private static final int MAINLINE = 1;
    private static final int NOT_MAINLINE = -1;
    private static final int UNDEFINED = 0;
    private static final long serialVersionUID = 8310540024786007658L;
    private String _comment;
    private int _isMainLine;
    private int _moveId;
    private int _moveTimeInMills;
    private MoveContainer _parent;
    private String _symbolMisc;
    private String _symbolMove;
    private String _symbolPosition;
    private List<Move> _variants;
    private int _variationLevel;
    private String _variationPrefix;
    
    public AbstractMoveContainer() {
        this._comment = "";
        this._variationLevel = -1;
        this._variationPrefix = null;
        this._isMainLine = 0;
        this._variants = new ArrayList<Move>(1);
    }
    
    protected AbstractMoveContainer(final MoveContainer moveContainer) {
        this._comment = "";
        this._variationLevel = -1;
        this._variationPrefix = null;
        this._isMainLine = 0;
        if (moveContainer.getClass() != this.getClass()) {
            throw new RuntimeException("assure that the instance to copied is instance from same class and not from superclass");
        }
        this._variants = new ArrayList<Move>(1);
        if (moveContainer != null) {
            final Iterator<Move> iterator = moveContainer.getChildren().iterator();
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
    
    private static String getCharForVariationNumber(final int n) {
        if (n == 0) {
            return "";
        }
        int i = 1;
        char c = 'a';
        while (i < n) {
            if (++c == '{') {
                c = 'a';
            }
            ++i;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(c);
        return sb.toString();
    }
    
    private void resetMainLineStatus() {
        if (this._isMainLine != 0) {
            this._isMainLine = 0;
            final Iterator<Move> iterator = this.getChildren().iterator();
            while (iterator.hasNext()) {
                iterator.next().resetMainLineStatus();
            }
        }
    }
    
    private void resetVariationLevel() {
        this._variationLevel = -1;
        this._variationPrefix = null;
        final Iterator<Move> iterator = this.getChildren().iterator();
        while (iterator.hasNext()) {
            iterator.next().resetVariationLevel();
        }
    }
    
    @Override
    public void addMove(final Move move) {
        if (move != null) {
            this._variants.add(move);
            move.setParent(this);
        }
    }
    
    @Override
    public void addMoveInMainLine(final Move move) {
        if (!this.hasChildren()) {
            this.addMove(move);
            return;
        }
        this.getLastMoveinMainLine().addMove(move);
    }
    
    @Override
    public boolean containsMove(Move child, final MoveContainer moveContainer) {
        final LinkedList<SEP> list = new LinkedList<SEP>();
        MoveContainer parent;
        do {
            if (child instanceof Move) {
                list.addFirst(child.getSEP());
            }
            parent = child.getParent();
            if (parent == null) {
                break;
            }
            child = (Move)parent;
        } while (!parent.equals(moveContainer));
        final Iterator<Object> iterator = list.iterator();
        child = (Move)this;
        while (iterator.hasNext()) {
            final SEP sep = iterator.next();
            if (!child.hasChild(sep)) {
                return false;
            }
            child = child.getChild(sep);
        }
        return true;
    }
    
    protected abstract AbstractMoveContainer copy();
    
    @Override
    public List<Move> getAllMainLineMoves() {
        final ArrayList<Move> list = new ArrayList<Move>();
        for (Move move = this.getNextMove(); move != null; move = move.getNextMove()) {
            list.add(move);
        }
        return list;
    }
    
    @Override
    public Move getChild(final SEP sep) {
        for (final Move move : this._variants) {
            if (move.getSEP().equals(sep)) {
                return move;
            }
        }
        return null;
    }
    
    @Override
    public List<Move> getChildren() {
        return this._variants;
    }
    
    @Override
    public List<Move> getChildrenOfAllLevels() {
        final ArrayList<Move> list = new ArrayList<Move>(250);
        if (this.hasChildren()) {
            final LinkedList<Move> list2 = new LinkedList<Move>();
            list2.addAll(0, this.getChildren());
            do {
                final Move move = list2.pollFirst();
                if (move != null) {
                    list.add(move);
                    if (!move.hasChildren()) {
                        continue;
                    }
                    list2.addAll(0, move.getChildren());
                }
            } while (list2.size() > 0);
        }
        return list;
    }
    
    @Override
    public String getComment() {
        return this._comment;
    }
    
    @Override
    public Move getLastMoveinMainLine() {
        Move move;
        for (move = this.getNextMove(); move != null && move.hasChildren(); move = move.getNextMove()) {}
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
    
    @Override
    public Move getNextMove() {
        while (true) {
            synchronized (this._variants) {
                if (!this._variants.isEmpty()) {
                    return this._variants.get(0);
                }
            }
            return null;
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
        String string = "";
        if (this._symbolMove != null) {
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(this._symbolMove);
            string = sb.toString();
        }
        String string2 = string;
        if (this._symbolPosition != null) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(string);
            sb2.append(this._symbolPosition);
            string2 = sb2.toString();
        }
        String string3 = string2;
        if (this._symbolMisc != null) {
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(string2);
            sb3.append(this._symbolMisc);
            string3 = sb3.toString();
        }
        return string3;
    }
    
    @Override
    public int getTotalTimeTakenInMills() {
        int moveTimeInMills = this._moveTimeInMills;
        AbstractMoveContainer parent;
        AbstractMoveContainer abstractMoveContainer = parent = this;
        while (parent != null && abstractMoveContainer != null) {
            final MoveContainer parent2 = parent.getParent();
            if ((abstractMoveContainer = (AbstractMoveContainer)parent2) != null) {
                final MoveContainer moveContainer = parent = (AbstractMoveContainer)parent2.getParent();
                abstractMoveContainer = (AbstractMoveContainer)parent2;
                if (moveContainer == null) {
                    continue;
                }
                moveTimeInMills += moveContainer.getMoveTimeInMills();
                parent = (AbstractMoveContainer)moveContainer;
                abstractMoveContainer = (AbstractMoveContainer)parent2;
            }
        }
        return moveTimeInMills;
    }
    
    @Override
    public int getVariationLevel() {
        if (this._variationLevel == -1) {
            int variationLevel;
            if (this._parent != null) {
                variationLevel = this._parent.getVariationLevel();
            }
            else {
                variationLevel = 0;
            }
            this._variationLevel = variationLevel;
            if (this.isVariationStart()) {
                ++this._variationLevel;
            }
        }
        return this._variationLevel;
    }
    
    @Override
    public String getVariationPrefix() {
        if (this._variationPrefix == null) {
            final MoveContainer parent = this.getParent();
            if (parent == null) {
                this._variationPrefix = "";
            }
            else {
                final List<Move> children = parent.getChildren();
                int n = -1;
                int n2 = 0;
                final Iterator<Move> iterator = children.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().equals(this)) {
                        n = n2;
                    }
                    ++n2;
                }
                final StringBuilder sb = new StringBuilder();
                sb.append(parent.getVariationPrefix());
                sb.append(getCharForVariationNumber(n));
                this._variationPrefix = sb.toString();
            }
        }
        return this._variationPrefix;
    }
    
    @Override
    public boolean hasChild(final SEP sep) {
        return this.getChild(sep) != null;
    }
    
    @Override
    public boolean hasChildren() {
        return this._variants != null && !this._variants.isEmpty();
    }
    
    public boolean hasComment() {
        return this._comment != null && !this._comment.isEmpty();
    }
    
    public boolean hasParent() {
        return this.getParent() != null;
    }
    
    @Override
    public boolean hasSiblings() {
        final MoveContainer parent = this.getParent();
        return parent != null && parent.hasVariants();
    }
    
    @Override
    public boolean hasVariants() {
        return this._variants != null && this._variants.size() >= 2;
    }
    
    @Override
    public boolean isMainLine() {
        final int isMainLine = this._isMainLine;
        final boolean b = false;
        boolean b2 = false;
        if (isMainLine != 0) {
            if (this._isMainLine > 0) {
                b2 = true;
            }
            return b2;
        }
        boolean b3 = b;
        if (this.isMainMove()) {
            b3 = b;
            if (this.getParent().isMainLine()) {
                b3 = true;
            }
        }
        if (b3) {
            this._isMainLine = 1;
            return b3;
        }
        this._isMainLine = -1;
        return b3;
    }
    
    @Override
    public boolean isMainMove() {
        final MoveContainer parent = this.getParent();
        return parent != null && this.equals(parent.getNextMove());
    }
    
    public boolean isVariationStart() {
        return this.getParent() != null && (this.isMainMove() ^ true);
    }
    
    @Override
    public void promoteMove(final Move move) {
        synchronized (this._variants) {
            final Iterator<Move> iterator = this._variants.iterator();
            Move move2 = null;
            while (iterator.hasNext()) {
                final Move move3 = iterator.next();
                if (move3.equals(move)) {
                    move2 = move3;
                }
            }
            if (move2 != null) {
                this._variants.remove(move2);
                this._variants.add(0, move2);
            }
            for (final Move move4 : this.getChildren()) {
                move4.resetVariationLevel();
                move4.resetMainLineStatus();
            }
        }
    }
    
    @Override
    public void removeChildAllMoves() {
        synchronized (this._variants) {
            for (final Move move : this._variants) {
                move.setParent(null);
                move.removeChildAllMoves();
            }
            this._variants.clear();
        }
    }
    
    @Override
    public void removeMove(final Move move) {
        final List<Move> variants = this._variants;
        // monitorenter(variants)
        int n = 0;
        while (true) {
            Label_0077: {
                try {
                    if (n < this._variants.size()) {
                        if (!this._variants.get(n).equals(move)) {
                            break Label_0077;
                        }
                        move.setParent(null);
                        this._variants.remove(n);
                        this.resetVariationLevel();
                        this.resetMainLineStatus();
                    }
                    return;
                }
                finally {
                }
                // monitorexit(variants)
            }
            ++n;
        }
    }
    
    @Override
    public void setComment(final String comment) {
        this._comment = comment;
    }
    
    public void setMoveId(final int moveId) {
        this._moveId = moveId;
    }
    
    @Override
    public void setMoveTimeInMills(final int moveTimeInMills) {
        this._moveTimeInMills = moveTimeInMills;
    }
    
    protected void setParent(final MoveContainer parent) {
        final boolean b = parent != this._parent;
        this._parent = parent;
        if (b) {
            this.resetMainLineStatus();
            this.resetVariationLevel();
        }
    }
    
    @Override
    public void setSymbolMisc(final String symbolMisc) {
        this._symbolMisc = symbolMisc;
    }
    
    @Override
    public void setSymbolMove(final String symbolMove) {
        this._symbolMove = symbolMove;
    }
    
    @Override
    public void setSymbolPosition(final String symbolPosition) {
        this._symbolPosition = symbolPosition;
    }
}
