/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.Xml
 *  android.view.InflateException
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v7.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.support.annotation.LayoutRes;
import android.support.annotation.RestrictTo;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class SupportMenuInflater
extends MenuInflater {
    static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
    static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    static final String LOG_TAG = "SupportMenuInflater";
    static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    final Object[] mActionProviderConstructorArguments;
    final Object[] mActionViewConstructorArguments;
    Context mContext;
    private Object mRealOwner;

    static {
        ACTION_VIEW_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class};
        ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    }

    public SupportMenuInflater(Context context) {
        super(context);
        this.mContext = context;
        this.mActionViewConstructorArguments = new Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    private Object findRealOwner(Object object) {
        if (object instanceof Activity) {
            return object;
        }
        if (object instanceof ContextWrapper) {
            return this.findRealOwner((Object)((ContextWrapper)object).getBaseContext());
        }
        return object;
    }

    private void parseMenu(XmlPullParser object, AttributeSet attributeSet, Menu object2) throws XmlPullParserException, IOException {
        int n;
        int n2;
        MenuState menuState;
        block17 : {
            menuState = new MenuState((Menu)object2);
            n = object.getEventType();
            do {
                if (n == 2) {
                    object2 = object.getName();
                    if (object2.equals(XML_MENU)) {
                        n = object.next();
                        break block17;
                    }
                    object = new StringBuilder();
                    object.append("Expecting menu, got ");
                    object.append((String)object2);
                    throw new RuntimeException(object.toString());
                }
                n = n2 = object.next();
            } while (n2 != 1);
            n = n2;
        }
        Object object3 = null;
        int n3 = n2 = 0;
        int n4 = n;
        while (n2 == 0) {
            int n5;
            switch (n4) {
                default: {
                    n = n3;
                    object2 = object3;
                    n5 = n2;
                    break;
                }
                case 3: {
                    String string = object.getName();
                    if (n3 != 0 && string.equals(object3)) {
                        object2 = null;
                        n = 0;
                        n5 = n2;
                        break;
                    }
                    if (string.equals(XML_GROUP)) {
                        menuState.resetGroup();
                        n = n3;
                        object2 = object3;
                        n5 = n2;
                        break;
                    }
                    if (string.equals(XML_ITEM)) {
                        n = n3;
                        object2 = object3;
                        n5 = n2;
                        if (menuState.hasAddedItem()) break;
                        if (menuState.itemActionProvider != null && menuState.itemActionProvider.hasSubMenu()) {
                            menuState.addSubMenuItem();
                            n = n3;
                            object2 = object3;
                            n5 = n2;
                            break;
                        }
                        menuState.addItem();
                        n = n3;
                        object2 = object3;
                        n5 = n2;
                        break;
                    }
                    n = n3;
                    object2 = object3;
                    n5 = n2;
                    if (!string.equals(XML_MENU)) break;
                    n5 = 1;
                    n = n3;
                    object2 = object3;
                    break;
                }
                case 2: {
                    if (n3 != 0) {
                        n = n3;
                        object2 = object3;
                        n5 = n2;
                        break;
                    }
                    object2 = object.getName();
                    if (object2.equals(XML_GROUP)) {
                        menuState.readGroup(attributeSet);
                        n = n3;
                        object2 = object3;
                        n5 = n2;
                        break;
                    }
                    if (object2.equals(XML_ITEM)) {
                        menuState.readItem(attributeSet);
                        n = n3;
                        object2 = object3;
                        n5 = n2;
                        break;
                    }
                    if (object2.equals(XML_MENU)) {
                        this.parseMenu((XmlPullParser)object, attributeSet, (Menu)menuState.addSubMenuItem());
                        n = n3;
                        object2 = object3;
                        n5 = n2;
                        break;
                    }
                    n = 1;
                    n5 = n2;
                    break;
                }
                case 1: {
                    throw new RuntimeException("Unexpected end of document");
                }
            }
            n4 = object.next();
            n3 = n;
            object3 = object2;
            n2 = n5;
        }
    }

    Object getRealOwner() {
        if (this.mRealOwner == null) {
            this.mRealOwner = this.findRealOwner((Object)this.mContext);
        }
        return this.mRealOwner;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void inflate(@LayoutRes int n, Menu menu) {
        XmlResourceParser xmlResourceParser2;
        void var2_8;
        void var2_9;
        XmlResourceParser xmlResourceParser;
        block10 : {
            if (!(menu instanceof SupportMenu)) {
                super.inflate(n, menu);
                return;
            }
            XmlResourceParser xmlResourceParser3 = null;
            xmlResourceParser2 = null;
            xmlResourceParser = null;
            XmlResourceParser xmlResourceParser4 = this.mContext.getResources().getLayout(n);
            try {
                this.parseMenu((XmlPullParser)xmlResourceParser4, Xml.asAttributeSet((XmlPullParser)xmlResourceParser4), menu);
                if (xmlResourceParser4 == null) return;
            }
            catch (Throwable throwable) {
                xmlResourceParser = xmlResourceParser4;
                break block10;
            }
            catch (IOException iOException) {
                xmlResourceParser = xmlResourceParser4;
                throw new InflateException("Error inflating menu XML", (Throwable)menu);
            }
            catch (XmlPullParserException xmlPullParserException) {
                xmlResourceParser = xmlResourceParser4;
                throw new InflateException("Error inflating menu XML", (Throwable)var2_8);
            }
            xmlResourceParser4.close();
            return;
            catch (Throwable throwable) {
                break block10;
            }
            catch (IOException iOException) {
                xmlResourceParser = xmlResourceParser3;
            }
            {
                throw new InflateException("Error inflating menu XML", (Throwable)menu);
            }
        }
        if (xmlResourceParser == null) throw var2_9;
        xmlResourceParser.close();
        throw var2_9;
        catch (XmlPullParserException xmlPullParserException) {
            xmlResourceParser = xmlResourceParser2;
            throw new InflateException("Error inflating menu XML", (Throwable)var2_8);
        }
    }

    private static class InflatedOnMenuItemClickListener
    implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = new Class[]{MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object object, String string) {
            this.mRealOwner = object;
            Class<?> class_ = object.getClass();
            try {
                this.mMethod = class_.getMethod(string, PARAM_TYPES);
                return;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't resolve menu item onClick handler ");
                stringBuilder.append(string);
                stringBuilder.append(" in class ");
                stringBuilder.append(class_.getName());
                string = new InflateException(stringBuilder.toString());
                string.initCause((Throwable)exception);
                throw string;
            }
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return (Boolean)this.mMethod.invoke(this.mRealOwner, new Object[]{menuItem});
                }
                this.mMethod.invoke(this.mRealOwner, new Object[]{menuItem});
                return true;
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private class MenuState {
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

        public MenuState(Menu menu) {
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
                menuItem.setOnMenuItemClickListener((MenuItem.OnMenuItemClickListener)new InflatedOnMenuItemClickListener(SupportMenuInflater.this.getRealOwner(), this.itemListenerMethodName));
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

}
