/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.modalfragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.modalfragments.GoPremiumLogoBackgroundDrawable;
import de.cisha.android.board.modalfragments.GoPremiumTitleBackgroundDrawable;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonPremiumSmall;
import de.cisha.android.ui.patterns.dialogs.EmptyDialogFragment;

public abstract class AbstractConversionDialogFragment
extends EmptyDialogFragment {
    private static final String PARAM_CONVERSION_CONTEXT = "PARAM_CONVERSION_CONTEXT";
    private ConversionContext _conversionContext;
    private View.OnClickListener _onConversionClickListener;
    private TextView _titleView;

    public static Bundle createFragmentParams(ConversionContext conversionContext) {
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_CONVERSION_CONTEXT, conversionContext.name());
        return bundle;
    }

    protected CustomButton createConvertButtonInstance() {
        return new CustomButtonPremiumSmall((Context)this.getActivity());
    }

    protected int getConversionButtonTitleResId() {
        return 2131690014;
    }

    protected ConversionContext getConversionContext() {
        return this._conversionContext;
    }

    protected int getTitleResId() {
        return 0;
    }

    protected abstract void inflateContentViewTo(LayoutInflater var1, LinearLayout var2);

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = this.getArguments();
        if (object != null && (object = object.getString("PARAM_CONVERSION_CONTEXT")) != null) {
            this._conversionContext = ConversionContext.valueOf((String)object);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle object) {
        viewGroup = layoutInflater.inflate(2131427406, viewGroup, false);
        object = viewGroup.findViewById(2131296541);
        float f = 10.0f * this.getActivity().getResources().getDisplayMetrics().density;
        object.setBackgroundDrawable((Drawable)new GoPremiumLogoBackgroundDrawable(f, viewGroup.getResources().getDisplayMetrics().density));
        viewGroup.findViewById(2131296458).setBackgroundDrawable((Drawable)new GoPremiumTitleBackgroundDrawable(f));
        this._titleView = (TextView)viewGroup.findViewById(2131296457);
        int n = this.getTitleResId();
        if (n > 0) {
            this._titleView.setText((CharSequence)this.getString(n));
        }
        this.inflateContentViewTo(layoutInflater, (LinearLayout)viewGroup.findViewById(2131296455));
        viewGroup.findViewById(2131296485).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AbstractConversionDialogFragment.this.dismiss();
            }
        });
        layoutInflater = (ViewGroup)viewGroup.findViewById(2131296456);
        object = this.createConvertButtonInstance();
        layoutInflater.addView((View)object);
        object.setText(this.getConversionButtonTitleResId());
        object.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (AbstractConversionDialogFragment.this._onConversionClickListener != null) {
                    AbstractConversionDialogFragment.this._onConversionClickListener.onClick(view);
                }
                AbstractConversionDialogFragment.this.dismiss();
            }
        });
        return viewGroup;
    }

    public void setConversionClickListener(View.OnClickListener onClickListener) {
        this._onConversionClickListener = onClickListener;
    }

}
