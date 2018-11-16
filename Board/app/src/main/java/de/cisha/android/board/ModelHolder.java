// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.Set;

public class ModelHolder<Type>
{
    private Type _model;
    private Set<ModelChangeListener<Type>> _modelListeners;
    
    public ModelHolder(final Type model) {
        this._model = model;
        this._modelListeners = Collections.newSetFromMap(new WeakHashMap<ModelChangeListener<Type>, Boolean>());
    }
    
    private void notifyListeners() {
        final Iterator<ModelChangeListener<Type>> iterator = this._modelListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().modelChanged(this._model);
        }
    }
    
    public void addModelChangeListener(final ModelChangeListener<Type> modelChangeListener) {
        this._modelListeners.add(modelChangeListener);
    }
    
    public Type getModel() {
        return this._model;
    }
    
    public void setModel(final Type model) {
        this._model = model;
        this.notifyListeners();
    }
    
    public interface ModelChangeListener<ModelType>
    {
        void modelChanged(final ModelType p0);
    }
}
