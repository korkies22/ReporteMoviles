/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 */
package android.support.v7.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.reflect.Constructor;

private class SupportMenuInflater.MenuState {
    private static final int defaultGroupId = 0;
    private static final int defaultItemCategory = 0;
    private static final int defaultItemCheckable = 0;
    private static final boolean defaultItemChecked = false;
    private static final boolean defaultItemEnabled = true;
    private static final int defaultItemId = 0;
    private static final int defaultItemOrder = 0;
    private static final boolean defaultItemVisible = true;
    private int groupCategory;
    private int groupCheckable;
    private boolean groupEnabled;
    private int groupId;
    private int groupOrder;
    private boolean groupVisible;
    ActionProvider itemActionProvider;
    private String itemActionProviderClassName;
    private String itemActionViewClassName;
    private int itemActionViewLayout;
    private boolean itemAdded;
    private int itemAlphabeticModifiers;
    private char itemAlphabeticShortcut;
    private int itemCategoryOrder;
    private int itemCheckable;
    private boolean itemChecked;
    private CharSequence itemContentDescription;
    private boolean itemEnabled;
    private int itemIconResId;
    private ColorStateList itemIconTintList = null;
    private PorterDuff.Mode itemIconTintMode = null;
    private int itemId;
    private String itemListenerMethodName;
    private int itemNumericModifiers;
    private char itemNumericShortcut;
    private int itemShowAsAction;
    private CharSequence itemTitle;
    private CharSequence itemTitleCondensed;
    private CharSequence itemTooltipText;
    private boolean itemVisible;
    private Menu menu;

    public SupportMenuInflater.MenuState(Menu menu) {
        this.menu = menu;
        this.resetGroup();
    }

    private char getShortcut(String string) {
        if (string == null) {
            return '\u0000';
        }
        return string.charAt(0);
    }

    private <T> T newInstance(String string, Class<?>[] object, Object[] object2) {
        try {
            object = SupportMenuInflater.this.mContext.getClassLoader().loadClass(string).getConstructor((Class<?>)object);
            object.setAccessible(true);
            object = object.newInstance((Object[])object2);
        }
        catch (Exception exception) {
            object2 = new StringBuilder();
            object2.append("Cannot instantiate class: ");
            object2.append(string);
            Log.w((String)SupportMenuInflater.LOG_TAG, (String)object2.toString(), (Throwable)exception);
            return null;
        }
        return (T)object;
    }

    private void setItem(MenuItem menuItem) {
        MenuItem menuItem2 = menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled);
        int n = this.itemCheckable;
        boolean bl = false;
        boolean bl2 = n >= 1;
        menuItem2.setCheckable(bl2).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId);
        if (this.itemShowAsAction >= 0) {
            menuItem.setShowAsAction(this.itemShowAsAction);
        }
        if (this.itemListenerMethodName != null) {
            if (SupportMenuInflater.this.mContext.isRestricted()) {
                throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
            }
            menuItem.setOnMenuItemClickListener((MenuItem.OnMenuItemClickListener)new SupportMenuInflater.InflatedOnMenuItemClickListener(SupportMenuInflater.this.getRealOwner(), this.itemListenerMethodName));
        }
        if (bl2 = menuItem instanceof MenuItemImpl) {
            menuItem2 = (MenuItemImpl)menuItem;
        }
        if (this.itemCheckable >= 2) {
            if (bl2) {
                ((MenuItemImpl)menuItem).setExclusiveCheckable(true);
            } else if (menuItem instanceof MenuItemWrapperICS) {
                ((MenuItemWrapperICS)menuItem).setExclusiveCheckable(true);
            }
        }
        if (this.itemActionViewClassName != null) {
            menuItem.setActionView((View)this.newInstance(this.itemActionViewClassName, SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments));
            bl = true;
        }
        if (this.itemActionViewLayout > 0) {
            if (!bl) {
                menuItem.setActionView(this.itemActionViewLayout);
            } else {
                Log.w((String)SupportMenuInflater.LOG_TAG, (String)"Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
            }
        }
        if (this.itemActionProvider != null) {
            MenuItemCompat.setActionProvider(menuItem, this.itemActionProvider);
        }
        MenuItemCompat.setContentDescription(menuItem, this.itemContentDescription);
        MenuItemCompat.setTooltipText(menuItem, this.itemTooltipText);
        MenuItemCompat.setAlphabeticShortcut(menuItem, this.itemAlphabeticShortcut, this.itemAlphabeticModifiers);
        MenuItemCompat.setNumericShortcut(menuItem, this.itemNumericShortcut, this.itemNumericModifiers);
        if (this.itemIconTintMode != null) {
            MenuItemCompat.setIconTintMode(menuItem, this.itemIconTintMode);
        }
        if (this.itemIconTintList != null) {
            MenuItemCompat.setIconTintList(menuItem, this.itemIconTintList);
        }
    }

    public void addItem() {
        this.itemAdded = true;
        this.setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
    }

    public SubMenu addSubMenuItem() {
        this.itemAdded = true;
        SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
        this.setItem(subMenu.getItem());
        return subMenu;
    }

    public boolean hasAddedItem() {
        return this.itemAdded;
    }

    public void readGroup(AttributeSet attributeSet) {
        attributeSet = SupportMenuInflater.this.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuGroup);
        this.groupId = attributeSet.getResourceId(R.styleable.MenuGroup_android_id, 0);
        this.groupCategory = attributeSet.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
        this.groupOrder = attributeSet.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
        this.groupCheckable = attributeSet.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
        this.groupVisible = attributeSet.getBoolean(R.styleable.MenuGroup_android_visible, true);
        this.groupEnabled = attributeSet.getBoolean(R.styleable.MenuGroup_android_enabled, true);
        attributeSet.recycle();
    }

    public void readItem(AttributeSet attributeSet) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public void resetGroup() {
        this.groupId = 0;
        this.groupCategory = 0;
        this.groupOrder = 0;
        this.groupCheckable = 0;
        this.groupVisible = true;
        this.groupEnabled = true;
    }
}
