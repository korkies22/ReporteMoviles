/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.View
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionInfo
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionItemInfo
 *  android.view.accessibility.AccessibilityNodeInfo$RangeInfo
 *  android.view.accessibility.AccessibilityWindowInfo
 */
package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.accessibility.AccessibilityWindowInfoCompat;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";
    private final AccessibilityNodeInfo mInfo;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public int mParentVirtualDescendantId = -1;

    private AccessibilityNodeInfoCompat(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mInfo = accessibilityNodeInfo;
    }

    @Deprecated
    public AccessibilityNodeInfoCompat(Object object) {
        this.mInfo = (AccessibilityNodeInfo)object;
    }

    private static String getActionSymbolicName(int n) {
        switch (n) {
            default: {
                return "ACTION_UNKNOWN";
            }
            case 131072: {
                return "ACTION_SET_SELECTION";
            }
            case 65536: {
                return "ACTION_CUT";
            }
            case 32768: {
                return "ACTION_PASTE";
            }
            case 16384: {
                return "ACTION_COPY";
            }
            case 8192: {
                return "ACTION_SCROLL_BACKWARD";
            }
            case 4096: {
                return "ACTION_SCROLL_FORWARD";
            }
            case 2048: {
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            }
            case 1024: {
                return "ACTION_NEXT_HTML_ELEMENT";
            }
            case 512: {
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            }
            case 256: {
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            }
            case 128: {
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            }
            case 64: {
                return "ACTION_ACCESSIBILITY_FOCUS";
            }
            case 32: {
                return "ACTION_LONG_CLICK";
            }
            case 16: {
                return "ACTION_CLICK";
            }
            case 8: {
                return "ACTION_CLEAR_SELECTION";
            }
            case 4: {
                return "ACTION_SELECT";
            }
            case 2: {
                return "ACTION_CLEAR_FOCUS";
            }
            case 1: 
        }
        return "ACTION_FOCUS";
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return AccessibilityNodeInfoCompat.wrap(AccessibilityNodeInfo.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        return AccessibilityNodeInfoCompat.wrap(AccessibilityNodeInfo.obtain((AccessibilityNodeInfo)accessibilityNodeInfoCompat.mInfo));
    }

    public static AccessibilityNodeInfoCompat obtain(View view) {
        return AccessibilityNodeInfoCompat.wrap(AccessibilityNodeInfo.obtain((View)view));
    }

    public static AccessibilityNodeInfoCompat obtain(View view, int n) {
        if (Build.VERSION.SDK_INT >= 16) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)AccessibilityNodeInfo.obtain((View)view, (int)n));
        }
        return null;
    }

    public static AccessibilityNodeInfoCompat wrap(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        return new AccessibilityNodeInfoCompat(accessibilityNodeInfo);
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object object) {
        if (object != null) {
            return new AccessibilityNodeInfoCompat(object);
        }
        return null;
    }

    public void addAction(int n) {
        this.mInfo.addAction(n);
    }

    public void addAction(AccessibilityActionCompat accessibilityActionCompat) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInfo.addAction((AccessibilityNodeInfo.AccessibilityAction)accessibilityActionCompat.mAction);
        }
    }

    public void addChild(View view) {
        this.mInfo.addChild(view);
    }

    public void addChild(View view, int n) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.addChild(view, n);
        }
    }

    public boolean canOpenPopup() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.canOpenPopup();
        }
        return false;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AccessibilityNodeInfoCompat)object;
        if (this.mInfo == null ? object.mInfo != null : !this.mInfo.equals((Object)object.mInfo)) {
            return false;
        }
        return true;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String object) {
        ArrayList<AccessibilityNodeInfoCompat> arrayList = new ArrayList<AccessibilityNodeInfoCompat>();
        object = this.mInfo.findAccessibilityNodeInfosByText((String)object);
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            arrayList.add(AccessibilityNodeInfoCompat.wrap((AccessibilityNodeInfo)object.get(i)));
        }
        return arrayList;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String object) {
        if (Build.VERSION.SDK_INT >= 18) {
            Object object2 = this.mInfo.findAccessibilityNodeInfosByViewId((String)object);
            object = new ArrayList();
            object2 = object2.iterator();
            while (object2.hasNext()) {
                object.add(AccessibilityNodeInfoCompat.wrap((AccessibilityNodeInfo)object2.next()));
            }
            return object;
        }
        return Collections.emptyList();
    }

    public AccessibilityNodeInfoCompat findFocus(int n) {
        if (Build.VERSION.SDK_INT >= 16) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.findFocus(n));
        }
        return null;
    }

    public AccessibilityNodeInfoCompat focusSearch(int n) {
        if (Build.VERSION.SDK_INT >= 16) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.focusSearch(n));
        }
        return null;
    }

    public List<AccessibilityActionCompat> getActionList() {
        List list = Build.VERSION.SDK_INT >= 21 ? this.mInfo.getActionList() : null;
        if (list != null) {
            ArrayList<AccessibilityActionCompat> arrayList = new ArrayList<AccessibilityActionCompat>();
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                arrayList.add(new AccessibilityActionCompat(list.get(i)));
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    public int getActions() {
        return this.mInfo.getActions();
    }

    public void getBoundsInParent(Rect rect) {
        this.mInfo.getBoundsInParent(rect);
    }

    public void getBoundsInScreen(Rect rect) {
        this.mInfo.getBoundsInScreen(rect);
    }

    public AccessibilityNodeInfoCompat getChild(int n) {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getChild(n));
    }

    public int getChildCount() {
        return this.mInfo.getChildCount();
    }

    public CharSequence getClassName() {
        return this.mInfo.getClassName();
    }

    public CollectionInfoCompat getCollectionInfo() {
        AccessibilityNodeInfo.CollectionInfo collectionInfo;
        if (Build.VERSION.SDK_INT >= 19 && (collectionInfo = this.mInfo.getCollectionInfo()) != null) {
            return new CollectionInfoCompat((Object)collectionInfo);
        }
        return null;
    }

    public CollectionItemInfoCompat getCollectionItemInfo() {
        AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo;
        if (Build.VERSION.SDK_INT >= 19 && (collectionItemInfo = this.mInfo.getCollectionItemInfo()) != null) {
            return new CollectionItemInfoCompat((Object)collectionItemInfo);
        }
        return null;
    }

    public CharSequence getContentDescription() {
        return this.mInfo.getContentDescription();
    }

    public int getDrawingOrder() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mInfo.getDrawingOrder();
        }
        return 0;
    }

    public CharSequence getError() {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.getError();
        }
        return null;
    }

    public Bundle getExtras() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.getExtras();
        }
        return new Bundle();
    }

    @Deprecated
    public Object getInfo() {
        return this.mInfo;
    }

    public int getInputType() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.getInputType();
        }
        return 0;
    }

    public AccessibilityNodeInfoCompat getLabelFor() {
        if (Build.VERSION.SDK_INT >= 17) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getLabelFor());
        }
        return null;
    }

    public AccessibilityNodeInfoCompat getLabeledBy() {
        if (Build.VERSION.SDK_INT >= 17) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getLabeledBy());
        }
        return null;
    }

    public int getLiveRegion() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.getLiveRegion();
        }
        return 0;
    }

    public int getMaxTextLength() {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.getMaxTextLength();
        }
        return -1;
    }

    public int getMovementGranularities() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInfo.getMovementGranularities();
        }
        return 0;
    }

    public CharSequence getPackageName() {
        return this.mInfo.getPackageName();
    }

    public AccessibilityNodeInfoCompat getParent() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getParent());
    }

    public RangeInfoCompat getRangeInfo() {
        AccessibilityNodeInfo.RangeInfo rangeInfo;
        if (Build.VERSION.SDK_INT >= 19 && (rangeInfo = this.mInfo.getRangeInfo()) != null) {
            return new RangeInfoCompat((Object)rangeInfo);
        }
        return null;
    }

    @Nullable
    public CharSequence getRoleDescription() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.getExtras().getCharSequence(ROLE_DESCRIPTION_KEY);
        }
        return null;
    }

    public CharSequence getText() {
        return this.mInfo.getText();
    }

    public int getTextSelectionEnd() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.mInfo.getTextSelectionEnd();
        }
        return -1;
    }

    public int getTextSelectionStart() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.mInfo.getTextSelectionStart();
        }
        return -1;
    }

    public AccessibilityNodeInfoCompat getTraversalAfter() {
        if (Build.VERSION.SDK_INT >= 22) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getTraversalAfter());
        }
        return null;
    }

    public AccessibilityNodeInfoCompat getTraversalBefore() {
        if (Build.VERSION.SDK_INT >= 22) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getTraversalBefore());
        }
        return null;
    }

    public String getViewIdResourceName() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.mInfo.getViewIdResourceName();
        }
        return null;
    }

    public AccessibilityWindowInfoCompat getWindow() {
        if (Build.VERSION.SDK_INT >= 21) {
            return AccessibilityWindowInfoCompat.wrapNonNullInstance((Object)this.mInfo.getWindow());
        }
        return null;
    }

    public int getWindowId() {
        return this.mInfo.getWindowId();
    }

    public int hashCode() {
        if (this.mInfo == null) {
            return 0;
        }
        return this.mInfo.hashCode();
    }

    public boolean isAccessibilityFocused() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInfo.isAccessibilityFocused();
        }
        return false;
    }

    public boolean isCheckable() {
        return this.mInfo.isCheckable();
    }

    public boolean isChecked() {
        return this.mInfo.isChecked();
    }

    public boolean isClickable() {
        return this.mInfo.isClickable();
    }

    public boolean isContentInvalid() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.isContentInvalid();
        }
        return false;
    }

    public boolean isContextClickable() {
        if (Build.VERSION.SDK_INT >= 23) {
            return this.mInfo.isContextClickable();
        }
        return false;
    }

    public boolean isDismissable() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.isDismissable();
        }
        return false;
    }

    public boolean isEditable() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.mInfo.isEditable();
        }
        return false;
    }

    public boolean isEnabled() {
        return this.mInfo.isEnabled();
    }

    public boolean isFocusable() {
        return this.mInfo.isFocusable();
    }

    public boolean isFocused() {
        return this.mInfo.isFocused();
    }

    public boolean isImportantForAccessibility() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mInfo.isImportantForAccessibility();
        }
        return true;
    }

    public boolean isLongClickable() {
        return this.mInfo.isLongClickable();
    }

    public boolean isMultiLine() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.mInfo.isMultiLine();
        }
        return false;
    }

    public boolean isPassword() {
        return this.mInfo.isPassword();
    }

    public boolean isScrollable() {
        return this.mInfo.isScrollable();
    }

    public boolean isSelected() {
        return this.mInfo.isSelected();
    }

    public boolean isVisibleToUser() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInfo.isVisibleToUser();
        }
        return false;
    }

    public boolean performAction(int n) {
        return this.mInfo.performAction(n);
    }

    public boolean performAction(int n, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.mInfo.performAction(n, bundle);
        }
        return false;
    }

    public void recycle() {
        this.mInfo.recycle();
    }

    public boolean refresh() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.mInfo.refresh();
        }
        return false;
    }

    public boolean removeAction(AccessibilityActionCompat accessibilityActionCompat) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction)accessibilityActionCompat.mAction);
        }
        return false;
    }

    public boolean removeChild(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.removeChild(view);
        }
        return false;
    }

    public boolean removeChild(View view, int n) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.removeChild(view, n);
        }
        return false;
    }

    public void setAccessibilityFocused(boolean bl) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.setAccessibilityFocused(bl);
        }
    }

    public void setBoundsInParent(Rect rect) {
        this.mInfo.setBoundsInParent(rect);
    }

    public void setBoundsInScreen(Rect rect) {
        this.mInfo.setBoundsInScreen(rect);
    }

    public void setCanOpenPopup(boolean bl) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setCanOpenPopup(bl);
        }
    }

    public void setCheckable(boolean bl) {
        this.mInfo.setCheckable(bl);
    }

    public void setChecked(boolean bl) {
        this.mInfo.setChecked(bl);
    }

    public void setClassName(CharSequence charSequence) {
        this.mInfo.setClassName(charSequence);
    }

    public void setClickable(boolean bl) {
        this.mInfo.setClickable(bl);
    }

    public void setCollectionInfo(Object object) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo)((CollectionInfoCompat)object).mInfo);
        }
    }

    public void setCollectionItemInfo(Object object) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo)((CollectionItemInfoCompat)object).mInfo);
        }
    }

    public void setContentDescription(CharSequence charSequence) {
        this.mInfo.setContentDescription(charSequence);
    }

    public void setContentInvalid(boolean bl) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setContentInvalid(bl);
        }
    }

    public void setContextClickable(boolean bl) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.mInfo.setContextClickable(bl);
        }
    }

    public void setDismissable(boolean bl) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setDismissable(bl);
        }
    }

    public void setDrawingOrder(int n) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mInfo.setDrawingOrder(n);
        }
    }

    public void setEditable(boolean bl) {
        if (Build.VERSION.SDK_INT >= 18) {
            this.mInfo.setEditable(bl);
        }
    }

    public void setEnabled(boolean bl) {
        this.mInfo.setEnabled(bl);
    }

    public void setError(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInfo.setError(charSequence);
        }
    }

    public void setFocusable(boolean bl) {
        this.mInfo.setFocusable(bl);
    }

    public void setFocused(boolean bl) {
        this.mInfo.setFocused(bl);
    }

    public void setImportantForAccessibility(boolean bl) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mInfo.setImportantForAccessibility(bl);
        }
    }

    public void setInputType(int n) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setInputType(n);
        }
    }

    public void setLabelFor(View view) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInfo.setLabelFor(view);
        }
    }

    public void setLabelFor(View view, int n) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInfo.setLabelFor(view, n);
        }
    }

    public void setLabeledBy(View view) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInfo.setLabeledBy(view);
        }
    }

    public void setLabeledBy(View view, int n) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mInfo.setLabeledBy(view, n);
        }
    }

    public void setLiveRegion(int n) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setLiveRegion(n);
        }
    }

    public void setLongClickable(boolean bl) {
        this.mInfo.setLongClickable(bl);
    }

    public void setMaxTextLength(int n) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInfo.setMaxTextLength(n);
        }
    }

    public void setMovementGranularities(int n) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.setMovementGranularities(n);
        }
    }

    public void setMultiLine(boolean bl) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setMultiLine(bl);
        }
    }

    public void setPackageName(CharSequence charSequence) {
        this.mInfo.setPackageName(charSequence);
    }

    public void setParent(View view) {
        this.mInfo.setParent(view);
    }

    public void setParent(View view, int n) {
        this.mParentVirtualDescendantId = n;
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.setParent(view, n);
        }
    }

    public void setPassword(boolean bl) {
        this.mInfo.setPassword(bl);
    }

    public void setRangeInfo(RangeInfoCompat rangeInfoCompat) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.setRangeInfo((AccessibilityNodeInfo.RangeInfo)rangeInfoCompat.mInfo);
        }
    }

    public void setRoleDescription(@Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mInfo.getExtras().putCharSequence(ROLE_DESCRIPTION_KEY, charSequence);
        }
    }

    public void setScrollable(boolean bl) {
        this.mInfo.setScrollable(bl);
    }

    public void setSelected(boolean bl) {
        this.mInfo.setSelected(bl);
    }

    public void setSource(View view) {
        this.mInfo.setSource(view);
    }

    public void setSource(View view, int n) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.setSource(view, n);
        }
    }

    public void setText(CharSequence charSequence) {
        this.mInfo.setText(charSequence);
    }

    public void setTextSelection(int n, int n2) {
        if (Build.VERSION.SDK_INT >= 18) {
            this.mInfo.setTextSelection(n, n2);
        }
    }

    public void setTraversalAfter(View view) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalAfter(view);
        }
    }

    public void setTraversalAfter(View view, int n) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalAfter(view, n);
        }
    }

    public void setTraversalBefore(View view) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalBefore(view);
        }
    }

    public void setTraversalBefore(View view, int n) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalBefore(view, n);
        }
    }

    public void setViewIdResourceName(String string) {
        if (Build.VERSION.SDK_INT >= 18) {
            this.mInfo.setViewIdResourceName(string);
        }
    }

    public void setVisibleToUser(boolean bl) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mInfo.setVisibleToUser(bl);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        Object object = new Rect();
        this.getBoundsInParent((Rect)object);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("; boundsInParent: ");
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        this.getBoundsInScreen((Rect)object);
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("; boundsInScreen: ");
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("; packageName: ");
        stringBuilder.append(this.getPackageName());
        stringBuilder.append("; className: ");
        stringBuilder.append(this.getClassName());
        stringBuilder.append("; text: ");
        stringBuilder.append(this.getText());
        stringBuilder.append("; contentDescription: ");
        stringBuilder.append(this.getContentDescription());
        stringBuilder.append("; viewId: ");
        stringBuilder.append(this.getViewIdResourceName());
        stringBuilder.append("; checkable: ");
        stringBuilder.append(this.isCheckable());
        stringBuilder.append("; checked: ");
        stringBuilder.append(this.isChecked());
        stringBuilder.append("; focusable: ");
        stringBuilder.append(this.isFocusable());
        stringBuilder.append("; focused: ");
        stringBuilder.append(this.isFocused());
        stringBuilder.append("; selected: ");
        stringBuilder.append(this.isSelected());
        stringBuilder.append("; clickable: ");
        stringBuilder.append(this.isClickable());
        stringBuilder.append("; longClickable: ");
        stringBuilder.append(this.isLongClickable());
        stringBuilder.append("; enabled: ");
        stringBuilder.append(this.isEnabled());
        stringBuilder.append("; password: ");
        stringBuilder.append(this.isPassword());
        object = new StringBuilder();
        object.append("; scrollable: ");
        object.append(this.isScrollable());
        stringBuilder.append(object.toString());
        stringBuilder.append("; [");
        int n = this.getActions();
        while (n != 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            int n3 = n & ~ n2;
            stringBuilder.append(AccessibilityNodeInfoCompat.getActionSymbolicName(n2));
            n = n3;
            if (n3 == 0) continue;
            stringBuilder.append(", ");
            n = n3;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public AccessibilityNodeInfo unwrap() {
        return this.mInfo;
    }

    public static class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS;
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS;
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS;
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION;
        public static final AccessibilityActionCompat ACTION_CLICK;
        public static final AccessibilityActionCompat ACTION_COLLAPSE;
        public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK;
        public static final AccessibilityActionCompat ACTION_COPY;
        public static final AccessibilityActionCompat ACTION_CUT;
        public static final AccessibilityActionCompat ACTION_DISMISS;
        public static final AccessibilityActionCompat ACTION_EXPAND;
        public static final AccessibilityActionCompat ACTION_FOCUS;
        public static final AccessibilityActionCompat ACTION_LONG_CLICK;
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY;
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT;
        public static final AccessibilityActionCompat ACTION_PASTE;
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY;
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT;
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD;
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN;
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD;
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT;
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT;
        public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION;
        public static final AccessibilityActionCompat ACTION_SCROLL_UP;
        public static final AccessibilityActionCompat ACTION_SELECT;
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS;
        public static final AccessibilityActionCompat ACTION_SET_SELECTION;
        public static final AccessibilityActionCompat ACTION_SET_TEXT;
        public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN;
        final Object mAction;

        static {
            Object var1 = null;
            ACTION_FOCUS = new AccessibilityActionCompat(1, null);
            ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);
            ACTION_SELECT = new AccessibilityActionCompat(4, null);
            ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, null);
            ACTION_CLICK = new AccessibilityActionCompat(16, null);
            ACTION_LONG_CLICK = new AccessibilityActionCompat(32, null);
            ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, null);
            ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, null);
            ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, null);
            ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, null);
            ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, null);
            ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, null);
            ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
            ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
            ACTION_COPY = new AccessibilityActionCompat(16384, null);
            ACTION_PASTE = new AccessibilityActionCompat(32768, null);
            ACTION_CUT = new AccessibilityActionCompat(65536, null);
            ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, null);
            ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
            ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
            ACTION_DISMISS = new AccessibilityActionCompat(1048576, null);
            ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, null);
            Object object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN : null;
            ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(object);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION : null;
            ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(object);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP : null;
            ACTION_SCROLL_UP = new AccessibilityActionCompat(object);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT : null;
            ACTION_SCROLL_LEFT = new AccessibilityActionCompat(object);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN : null;
            ACTION_SCROLL_DOWN = new AccessibilityActionCompat(object);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT : null;
            ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(object);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK : null;
            ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(object);
            object = var1;
            if (Build.VERSION.SDK_INT >= 24) {
                object = AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS;
            }
            ACTION_SET_PROGRESS = new AccessibilityActionCompat(object);
        }

        public AccessibilityActionCompat(int n, CharSequence charSequence) {
            charSequence = Build.VERSION.SDK_INT >= 21 ? new AccessibilityNodeInfo.AccessibilityAction(n, charSequence) : null;
            this(charSequence);
        }

        AccessibilityActionCompat(Object object) {
            this.mAction = object;
        }

        public int getId() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.AccessibilityAction)this.mAction).getId();
            }
            return 0;
        }

        public CharSequence getLabel() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.AccessibilityAction)this.mAction).getLabel();
            }
            return null;
        }
    }

    public static class CollectionInfoCompat {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        final Object mInfo;

        CollectionInfoCompat(Object object) {
            this.mInfo = object;
        }

        public static CollectionInfoCompat obtain(int n, int n2, boolean bl) {
            if (Build.VERSION.SDK_INT >= 19) {
                return new CollectionInfoCompat((Object)AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl));
            }
            return new CollectionInfoCompat(null);
        }

        public static CollectionInfoCompat obtain(int n, int n2, boolean bl, int n3) {
            if (Build.VERSION.SDK_INT >= 21) {
                return new CollectionInfoCompat((Object)AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl, (int)n3));
            }
            if (Build.VERSION.SDK_INT >= 19) {
                return new CollectionInfoCompat((Object)AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl));
            }
            return new CollectionInfoCompat(null);
        }

        public int getColumnCount() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getColumnCount();
            }
            return 0;
        }

        public int getRowCount() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getRowCount();
            }
            return 0;
        }

        public int getSelectionMode() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getSelectionMode();
            }
            return 0;
        }

        public boolean isHierarchical() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).isHierarchical();
            }
            return false;
        }
    }

    public static class CollectionItemInfoCompat {
        final Object mInfo;

        CollectionItemInfoCompat(Object object) {
            this.mInfo = object;
        }

        public static CollectionItemInfoCompat obtain(int n, int n2, int n3, int n4, boolean bl) {
            if (Build.VERSION.SDK_INT >= 19) {
                return new CollectionItemInfoCompat((Object)AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl));
            }
            return new CollectionItemInfoCompat(null);
        }

        public static CollectionItemInfoCompat obtain(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
            if (Build.VERSION.SDK_INT >= 21) {
                return new CollectionItemInfoCompat((Object)AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl, (boolean)bl2));
            }
            if (Build.VERSION.SDK_INT >= 19) {
                return new CollectionItemInfoCompat((Object)AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl));
            }
            return new CollectionItemInfoCompat(null);
        }

        public int getColumnIndex() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getColumnIndex();
            }
            return 0;
        }

        public int getColumnSpan() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getColumnSpan();
            }
            return 0;
        }

        public int getRowIndex() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getRowIndex();
            }
            return 0;
        }

        public int getRowSpan() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getRowSpan();
            }
            return 0;
        }

        public boolean isHeading() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).isHeading();
            }
            return false;
        }

        public boolean isSelected() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).isSelected();
            }
            return false;
        }
    }

    public static class RangeInfoCompat {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        final Object mInfo;

        RangeInfoCompat(Object object) {
            this.mInfo = object;
        }

        public static RangeInfoCompat obtain(int n, float f, float f2, float f3) {
            if (Build.VERSION.SDK_INT >= 19) {
                return new RangeInfoCompat((Object)AccessibilityNodeInfo.RangeInfo.obtain((int)n, (float)f, (float)f2, (float)f3));
            }
            return new RangeInfoCompat(null);
        }

        public float getCurrent() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getCurrent();
            }
            return 0.0f;
        }

        public float getMax() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getMax();
            }
            return 0.0f;
        }

        public float getMin() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getMin();
            }
            return 0.0f;
        }

        public int getType() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getType();
            }
            return 0;
        }
    }

}
