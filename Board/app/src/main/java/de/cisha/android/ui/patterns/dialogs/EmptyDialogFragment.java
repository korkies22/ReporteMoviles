// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import android.widget.TextView;
import de.cisha.android.ui.patterns.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;

public class EmptyDialogFragment extends AbstractDialogFragment
{
    private CharSequence _title;
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setCancelable(true);
        this.setAnimationEnabled(false);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(R.layout.empty_dialog, viewGroup, false);
        if (this._title != null) {
            ((TextView)inflate.findViewById(R.id.empty_dialog_title)).setText(this._title);
        }
        return inflate;
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.getDialog().setCanceledOnTouchOutside(true);
    }
    
    public void setTitle(final CharSequence title) {
        this._title = title;
    }
}
