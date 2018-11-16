// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model.filter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractSelectionSetting<Type extends SettingItem> implements Setting<Type>
{
    private List<Type> _items;
    private List<OnSettingChangeListener> _listeners;
    private Type _savedSelectedItem;
    private List<Type> _savedSelectedItems;
    private Type _selectedItem;
    private List<Type> _selectedItems;
    
    public AbstractSelectionSetting() {
        this._savedSelectedItems = new LinkedList<Type>();
        this._selectedItems = new LinkedList<Type>();
        this._listeners = new LinkedList<OnSettingChangeListener>();
        this._items = new LinkedList<Type>();
        final Iterator<Type> iterator = this.createOptions().iterator();
        while (iterator.hasNext()) {
            this._items.add(iterator.next());
        }
    }
    
    private void notifyListeners() {
        final Iterator<OnSettingChangeListener> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onSettingChanged();
        }
    }
    
    private void setSelectedMultiple(final SettingItem settingItem) {
        if (this._items.contains(settingItem)) {
            if (!this._selectedItems.remove(settingItem)) {
                this._selectedItems.add((Type)settingItem);
            }
        }
        else {
            this._selectedItems.clear();
        }
    }
    
    private void setSelectedSingle(final SettingItem selectedItem) {
        if (selectedItem == this._selectedItem) {
            this._selectedItem = null;
            return;
        }
        this._selectedItem = (Type)selectedItem;
    }
    
    @Override
    public void commit() {
        this._savedSelectedItem = this._selectedItem;
        this._savedSelectedItems = new LinkedList<Type>((Collection<? extends Type>)this._selectedItems);
        this.notifyListeners();
    }
    
    protected abstract List<Type> createOptions();
    
    @Override
    public List<Type> getOptions() {
        return this._items;
    }
    
    protected Type getSelectedItem() {
        return this._selectedItem;
    }
    
    protected List<Type> getSelectedItems() {
        return this._selectedItems;
    }
    
    @Override
    public boolean isSelected(final SettingItem settingItem) {
        return settingItem == this._selectedItem || this._selectedItems.contains(settingItem);
    }
    
    @Override
    public void registerOnSettingChangedListener(final OnSettingChangeListener onSettingChangeListener) {
        this._listeners.add(onSettingChangeListener);
    }
    
    @Override
    public void rollback() {
        this._selectedItems = new LinkedList<Type>((Collection<? extends Type>)this._savedSelectedItems);
        this._selectedItem = this._savedSelectedItem;
        this.notifyListeners();
    }
    
    @Override
    public void setSelected(final SettingItem settingItem) {
        if (this.isMultipleSelectionAllowed()) {
            this.setSelectedMultiple(settingItem);
        }
        else {
            this.setSelectedSingle(settingItem);
        }
        this.notifyListeners();
    }
    
    @Override
    public void unregisterOnSettingChangedListener(final OnSettingChangeListener onSettingChangeListener) {
        this._listeners.remove(onSettingChangeListener);
    }
}
