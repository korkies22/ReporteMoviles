/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.CheckedTextView
 *  android.widget.CursorAdapter
 */
package android.support.v7.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertController;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;

class AlertController.AlertParams
extends CursorAdapter {
    private final int mIsCheckedIndex;
    private final int mLabelIndex;
    final /* synthetic */ AlertController val$dialog;
    final /* synthetic */ AlertController.RecycleListView val$listView;

    AlertController.AlertParams(Context context, Cursor cursor, boolean bl, AlertController.RecycleListView recycleListView, AlertController alertController) {
        this.val$listView = recycleListView;
        this.val$dialog = alertController;
        super(context, cursor, bl);
        AlertParams.this = this.getCursor();
        this.mLabelIndex = AlertParams.this.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
        this.mIsCheckedIndex = AlertParams.this.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
    }

    public void bindView(View object, Context context, Cursor cursor) {
        ((CheckedTextView)object.findViewById(16908308)).setText((CharSequence)cursor.getString(this.mLabelIndex));
        object = this.val$listView;
        int n = cursor.getPosition();
        int n2 = cursor.getInt(this.mIsCheckedIndex);
        boolean bl = true;
        if (n2 != 1) {
            bl = false;
        }
        object.setItemChecked(n, bl);
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return AlertParams.this.mInflater.inflate(this.val$dialog.mMultiChoiceItemLayout, viewGroup, false);
    }
}
