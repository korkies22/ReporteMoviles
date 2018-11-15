/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.os.IBinder
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.KeyEvent$DispatcherState
 *  android.view.View
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AutoCompleteTextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.annotation.RestrictTo;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public static class SearchView.SearchAutoComplete
extends AppCompatAutoCompleteTextView {
    private boolean mHasPendingShowSoftInputRequest;
    final Runnable mRunShowSoftInputIfNecessary = new Runnable(){

        @Override
        public void run() {
            SearchAutoComplete.this.showSoftInputIfNecessary();
        }
    };
    private SearchView mSearchView;
    private int mThreshold = this.getThreshold();

    public SearchView.SearchAutoComplete(Context context) {
        this(context, null);
    }

    public SearchView.SearchAutoComplete(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
    }

    public SearchView.SearchAutoComplete(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    static /* synthetic */ void access$000(SearchView.SearchAutoComplete searchAutoComplete, boolean bl) {
        searchAutoComplete.setImeVisibility(bl);
    }

    static /* synthetic */ boolean access$100(SearchView.SearchAutoComplete searchAutoComplete) {
        return searchAutoComplete.isEmpty();
    }

    private int getSearchViewTextMinWidthDp() {
        Configuration configuration = this.getResources().getConfiguration();
        int n = configuration.screenWidthDp;
        int n2 = configuration.screenHeightDp;
        if (n >= 960 && n2 >= 720 && configuration.orientation == 2) {
            return 256;
        }
        if (n < 600 && (n < 640 || n2 < 480)) {
            return 160;
        }
        return 192;
    }

    private boolean isEmpty() {
        if (TextUtils.getTrimmedLength((CharSequence)this.getText()) == 0) {
            return true;
        }
        return false;
    }

    private void setImeVisibility(boolean bl) {
        InputMethodManager inputMethodManager = (InputMethodManager)this.getContext().getSystemService("input_method");
        if (!bl) {
            this.mHasPendingShowSoftInputRequest = false;
            this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
            inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
            return;
        }
        if (inputMethodManager.isActive((View)this)) {
            this.mHasPendingShowSoftInputRequest = false;
            this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
            inputMethodManager.showSoftInput((View)this, 0);
            return;
        }
        this.mHasPendingShowSoftInputRequest = true;
    }

    private void showSoftInputIfNecessary() {
        if (this.mHasPendingShowSoftInputRequest) {
            ((InputMethodManager)this.getContext().getSystemService("input_method")).showSoftInput((View)this, 0);
            this.mHasPendingShowSoftInputRequest = false;
        }
    }

    public boolean enoughToFilter() {
        if (this.mThreshold > 0 && !super.enoughToFilter()) {
            return false;
        }
        return true;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        editorInfo = super.onCreateInputConnection(editorInfo);
        if (this.mHasPendingShowSoftInputRequest) {
            this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
            this.post(this.mRunShowSoftInputIfNecessary);
        }
        return editorInfo;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.setMinWidth((int)TypedValue.applyDimension((int)1, (float)this.getSearchViewTextMinWidthDp(), (DisplayMetrics)displayMetrics));
    }

    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        super.onFocusChanged(bl, n, rect);
        this.mSearchView.onTextFocusChanged();
    }

    public boolean onKeyPreIme(int n, KeyEvent keyEvent) {
        if (n == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                if (dispatcherState != null) {
                    dispatcherState.startTracking(keyEvent, (Object)this);
                }
                return true;
            }
            if (keyEvent.getAction() == 1) {
                KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                if (dispatcherState != null) {
                    dispatcherState.handleUpEvent(keyEvent);
                }
                if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    this.mSearchView.clearFocus();
                    this.setImeVisibility(false);
                    return true;
                }
            }
        }
        return super.onKeyPreIme(n, keyEvent);
    }

    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        if (bl && this.mSearchView.hasFocus() && this.getVisibility() == 0) {
            this.mHasPendingShowSoftInputRequest = true;
            if (SearchView.isLandscapeMode(this.getContext())) {
                SearchView.HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true);
            }
        }
    }

    public void performCompletion() {
    }

    protected void replaceText(CharSequence charSequence) {
    }

    void setSearchView(SearchView searchView) {
        this.mSearchView = searchView;
    }

    public void setThreshold(int n) {
        super.setThreshold(n);
        this.mThreshold = n;
    }

}
