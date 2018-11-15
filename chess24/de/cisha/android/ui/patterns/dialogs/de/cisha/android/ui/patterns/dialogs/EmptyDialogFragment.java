/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;

public class EmptyDialogFragment
extends AbstractDialogFragment {
    private CharSequence _title;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setCancelable(true);
        this.setAnimationEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(R.layout.empty_dialog, viewGroup, false);
        if (this._title != null) {
            ((TextView)layoutInflater.findViewById(R.id.empty_dialog_title)).setText(this._title);
        }
        return layoutInflater;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.getDialog().setCanceledOnTouchOutside(true);
    }

    public void setTitle(CharSequence charSequence) {
        this._title = charSequence;
    }
}
