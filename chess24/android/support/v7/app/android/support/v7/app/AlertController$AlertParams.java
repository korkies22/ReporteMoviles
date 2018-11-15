/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.content.DialogInterface$OnMultiChoiceClickListener
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.os.Message
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.CheckedTextView
 *  android.widget.CursorAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.SimpleCursorAdapter
 */
package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.app.AlertController;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public static class AlertController.AlertParams {
    public ListAdapter mAdapter;
    public boolean mCancelable;
    public int mCheckedItem = -1;
    public boolean[] mCheckedItems;
    public final Context mContext;
    public Cursor mCursor;
    public View mCustomTitleView;
    public boolean mForceInverseBackground;
    public Drawable mIcon;
    public int mIconAttrId = 0;
    public int mIconId = 0;
    public final LayoutInflater mInflater;
    public String mIsCheckedColumn;
    public boolean mIsMultiChoice;
    public boolean mIsSingleChoice;
    public CharSequence[] mItems;
    public String mLabelColumn;
    public CharSequence mMessage;
    public Drawable mNegativeButtonIcon;
    public DialogInterface.OnClickListener mNegativeButtonListener;
    public CharSequence mNegativeButtonText;
    public Drawable mNeutralButtonIcon;
    public DialogInterface.OnClickListener mNeutralButtonListener;
    public CharSequence mNeutralButtonText;
    public DialogInterface.OnCancelListener mOnCancelListener;
    public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
    public DialogInterface.OnClickListener mOnClickListener;
    public DialogInterface.OnDismissListener mOnDismissListener;
    public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    public DialogInterface.OnKeyListener mOnKeyListener;
    public OnPrepareListViewListener mOnPrepareListViewListener;
    public Drawable mPositiveButtonIcon;
    public DialogInterface.OnClickListener mPositiveButtonListener;
    public CharSequence mPositiveButtonText;
    public boolean mRecycleOnMeasure = true;
    public CharSequence mTitle;
    public View mView;
    public int mViewLayoutResId;
    public int mViewSpacingBottom;
    public int mViewSpacingLeft;
    public int mViewSpacingRight;
    public boolean mViewSpacingSpecified = false;
    public int mViewSpacingTop;

    public AlertController.AlertParams(Context context) {
        this.mContext = context;
        this.mCancelable = true;
        this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    private void createListView(final AlertController alertController) {
        Object object;
        final AlertController.RecycleListView recycleListView = (AlertController.RecycleListView)this.mInflater.inflate(alertController.mListLayout, null);
        if (this.mIsMultiChoice) {
            object = this.mCursor == null ? new ArrayAdapter<CharSequence>(this.mContext, alertController.mMultiChoiceItemLayout, 16908308, this.mItems){

                public View getView(int n, View view, ViewGroup viewGroup) {
                    view = super.getView(n, view, viewGroup);
                    if (AlertParams.this.mCheckedItems != null && AlertParams.this.mCheckedItems[n]) {
                        recycleListView.setItemChecked(n, true);
                    }
                    return view;
                }
            } : new CursorAdapter(this.mContext, this.mCursor, false){
                private final int mIsCheckedIndex;
                private final int mLabelIndex;
                {
                    super(context, cursor, bl);
                    AlertParams.this = this.getCursor();
                    this.mLabelIndex = AlertParams.this.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                    this.mIsCheckedIndex = AlertParams.this.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                }

                public void bindView(View object, Context context, Cursor cursor) {
                    ((CheckedTextView)object.findViewById(16908308)).setText((CharSequence)cursor.getString(this.mLabelIndex));
                    object = recycleListView;
                    int n = cursor.getPosition();
                    int n2 = cursor.getInt(this.mIsCheckedIndex);
                    boolean bl = true;
                    if (n2 != 1) {
                        bl = false;
                    }
                    object.setItemChecked(n, bl);
                }

                public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                    return AlertParams.this.mInflater.inflate(alertController.mMultiChoiceItemLayout, viewGroup, false);
                }
            };
        } else {
            int n = this.mIsSingleChoice ? alertController.mSingleChoiceItemLayout : alertController.mListItemLayout;
            object = this.mCursor != null ? new SimpleCursorAdapter(this.mContext, n, this.mCursor, new String[]{this.mLabelColumn}, new int[]{16908308}) : (this.mAdapter != null ? this.mAdapter : new AlertController.CheckedItemAdapter(this.mContext, n, 16908308, this.mItems));
        }
        if (this.mOnPrepareListViewListener != null) {
            this.mOnPrepareListViewListener.onPrepareListView(recycleListView);
        }
        alertController.mAdapter = object;
        alertController.mCheckedItem = this.mCheckedItem;
        if (this.mOnClickListener != null) {
            recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                    AlertParams.this.mOnClickListener.onClick((DialogInterface)alertController.mDialog, n);
                    if (!AlertParams.this.mIsSingleChoice) {
                        alertController.mDialog.dismiss();
                    }
                }
            });
        } else if (this.mOnCheckboxClickListener != null) {
            recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                    if (AlertParams.this.mCheckedItems != null) {
                        AlertParams.this.mCheckedItems[n] = recycleListView.isItemChecked(n);
                    }
                    AlertParams.this.mOnCheckboxClickListener.onClick((DialogInterface)alertController.mDialog, n, recycleListView.isItemChecked(n));
                }
            });
        }
        if (this.mOnItemSelectedListener != null) {
            recycleListView.setOnItemSelectedListener(this.mOnItemSelectedListener);
        }
        if (this.mIsSingleChoice) {
            recycleListView.setChoiceMode(1);
        } else if (this.mIsMultiChoice) {
            recycleListView.setChoiceMode(2);
        }
        alertController.mListView = recycleListView;
    }

    public void apply(AlertController alertController) {
        if (this.mCustomTitleView != null) {
            alertController.setCustomTitle(this.mCustomTitleView);
        } else {
            if (this.mTitle != null) {
                alertController.setTitle(this.mTitle);
            }
            if (this.mIcon != null) {
                alertController.setIcon(this.mIcon);
            }
            if (this.mIconId != 0) {
                alertController.setIcon(this.mIconId);
            }
            if (this.mIconAttrId != 0) {
                alertController.setIcon(alertController.getIconAttributeResId(this.mIconAttrId));
            }
        }
        if (this.mMessage != null) {
            alertController.setMessage(this.mMessage);
        }
        if (this.mPositiveButtonText != null || this.mPositiveButtonIcon != null) {
            alertController.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null, this.mPositiveButtonIcon);
        }
        if (this.mNegativeButtonText != null || this.mNegativeButtonIcon != null) {
            alertController.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null, this.mNegativeButtonIcon);
        }
        if (this.mNeutralButtonText != null || this.mNeutralButtonIcon != null) {
            alertController.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null, this.mNeutralButtonIcon);
        }
        if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
            this.createListView(alertController);
        }
        if (this.mView != null) {
            if (this.mViewSpacingSpecified) {
                alertController.setView(this.mView, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
                return;
            }
            alertController.setView(this.mView);
            return;
        }
        if (this.mViewLayoutResId != 0) {
            alertController.setView(this.mViewLayoutResId);
        }
    }

    public static interface OnPrepareListViewListener {
        public void onPrepareListView(ListView var1);
    }

}
