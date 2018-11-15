/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class ModelHolder<Type> {
    private Type _model;
    private Set<ModelChangeListener<Type>> _modelListeners;

    public ModelHolder(Type Type) {
        this._model = Type;
        this._modelListeners = Collections.newSetFromMap(new WeakHashMap());
    }

    private void notifyListeners() {
        Iterator<ModelChangeListener<Type>> iterator = this._modelListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().modelChanged(this._model);
        }
    }

    public void addModelChangeListener(ModelChangeListener<Type> modelChangeListener) {
        this._modelListeners.add(modelChangeListener);
    }

    public Type getModel() {
        return this._model;
    }

    public void setModel(Type Type) {
        this._model = Type;
        this.notifyListeners();
    }

    public static interface ModelChangeListener<ModelType> {
        public void modelChanged(ModelType var1);
    }

}
