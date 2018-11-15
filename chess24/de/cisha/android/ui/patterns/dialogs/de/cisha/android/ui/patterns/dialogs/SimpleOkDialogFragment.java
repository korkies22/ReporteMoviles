/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 */
package de.cisha.android.ui.patterns.dialogs;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonNeutral;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;
import java.util.LinkedList;
import java.util.List;

public class SimpleOkDialogFragment
extends RookieInfoDialogFragment {
    public SimpleOkDialogFragment() {
        this.setCancelable(true);
    }

    @Override
    protected List<CustomButton> getButtons() {
        LinkedList<CustomButton> linkedList = new LinkedList<CustomButton>();
        CustomButtonNeutral customButtonNeutral = (CustomButtonNeutral)LayoutInflater.from((Context)this.getActivity()).inflate(R.layout.dialog_fragment_ok_button, null);
        linkedList.add(customButtonNeutral);
        customButtonNeutral.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SimpleOkDialogFragment.this.dismiss();
            }
        });
        return linkedList;
    }

    @Override
    protected RookieInfoDialogFragment.RookieType getRookieType() {
        return RookieInfoDialogFragment.RookieType.INFO;
    }

}
