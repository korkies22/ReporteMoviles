// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import de.cisha.android.ui.patterns.R;
import android.content.Context;
import android.view.LayoutInflater;
import de.cisha.android.ui.patterns.buttons.CustomButtonNeutral;
import java.util.LinkedList;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import java.util.List;

public class SimpleOkDialogFragment extends RookieInfoDialogFragment
{
    public SimpleOkDialogFragment() {
        this.setCancelable(true);
    }
    
    @Override
    protected List<CustomButton> getButtons() {
        final LinkedList<CustomButtonNeutral> list = (LinkedList<CustomButtonNeutral>)new LinkedList<CustomButton>();
        final CustomButtonNeutral customButtonNeutral = (CustomButtonNeutral)LayoutInflater.from((Context)this.getActivity()).inflate(R.layout.dialog_fragment_ok_button, (ViewGroup)null);
        list.add(customButtonNeutral);
        customButtonNeutral.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                SimpleOkDialogFragment.this.dismiss();
            }
        });
        return (List<CustomButton>)list;
    }
    
    @Override
    protected RookieType getRookieType() {
        return RookieType.INFO;
    }
}
