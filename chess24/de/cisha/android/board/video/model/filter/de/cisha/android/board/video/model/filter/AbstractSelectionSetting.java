/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.model.filter;

import de.cisha.android.board.video.model.filter.Setting;
import de.cisha.android.board.video.model.filter.SettingItem;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractSelectionSetting<Type extends SettingItem>
implements Setting<Type> {
    private List<Type> _items = new LinkedList<Type>();
    private List<Setting.OnSettingChangeListener> _listeners = new LinkedList<Setting.OnSettingChangeListener>();
    private Type _savedSelectedItem;
    private List<Type> _savedSelectedItems = new LinkedList<Type>();
    private Type _selectedItem;
    private List<Type> _selectedItems = new LinkedList<Type>();

    public AbstractSelectionSetting() {
        for (SettingItem settingItem : this.createOptions()) {
            this._items.add(settingItem);
        }
    }

    private void notifyListeners() {
        Iterator<Setting.OnSettingChangeListener> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onSettingChanged();
        }
    }

    private void setSelectedMultiple(SettingItem settingItem) {
        if (this._items.contains(settingItem)) {
            if (!this._selectedItems.remove(settingItem)) {
                this._selectedItems.add(settingItem);
                return;
            }
        } else {
            this._selectedItems.clear();
        }
    }

    private void setSelectedSingle(SettingItem settingItem) {
        if (settingItem == this._selectedItem) {
            this._selectedItem = null;
            return;
        }
        this._selectedItem = settingItem;
    }

    @Override
    public void commit() {
        this._savedSelectedItem = this._selectedItem;
        this._savedSelectedItems = new LinkedList<Type>(this._selectedItems);
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
    public boolean isSelected(SettingItem settingItem) {
        if (settingItem != this._selectedItem && !this._selectedItems.contains(settingItem)) {
            return false;
        }
        return true;
    }

    @Override
    public void registerOnSettingChangedListener(Setting.OnSettingChangeListener onSettingChangeListener) {
        this._listeners.add(onSettingChangeListener);
    }

    @Override
    public void rollback() {
        this._selectedItems = new LinkedList<Type>(this._savedSelectedItems);
        this._selectedItem = this._savedSelectedItem;
        this.notifyListeners();
    }

    @Override
    public void setSelected(SettingItem settingItem) {
        if (this.isMultipleSelectionAllowed()) {
            this.setSelectedMultiple(settingItem);
        } else {
            this.setSelectedSingle(settingItem);
        }
        this.notifyListeners();
    }

    @Override
    public void unregisterOnSettingChangedListener(Setting.OnSettingChangeListener onSettingChangeListener) {
        this._listeners.remove(onSettingChangeListener);
    }
}
