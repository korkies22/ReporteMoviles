// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.content.Context;
import de.cisha.android.ui.patterns.buttons.CustomButtonPremiumSmall;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View.OnClickListener;
import de.cisha.android.ui.patterns.dialogs.EmptyDialogFragment;

public abstract class AbstractConversionDialogFragment extends EmptyDialogFragment
{
    private static final String PARAM_CONVERSION_CONTEXT = "PARAM_CONVERSION_CONTEXT";
    private ConversionContext _conversionContext;
    private View.OnClickListener _onConversionClickListener;
    private TextView _titleView;
    
    public static Bundle createFragmentParams(final ConversionContext conversionContext) {
        final Bundle bundle = new Bundle();
        bundle.putString("PARAM_CONVERSION_CONTEXT", conversionContext.name());
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
    
    protected abstract void inflateContentViewTo(final LayoutInflater p0, final LinearLayout p1);
    
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        arguments = this.getArguments();
        if (arguments != null) {
            final String string = arguments.getString("PARAM_CONVERSION_CONTEXT");
            if (string != null) {
                this._conversionContext = ConversionContext.valueOf(string);
            }
        }
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427406, viewGroup, false);
        final View viewById = inflate.findViewById(2131296541);
        final float n = 10.0f * this.getActivity().getResources().getDisplayMetrics().density;
        viewById.setBackgroundDrawable((Drawable)new GoPremiumLogoBackgroundDrawable(n, inflate.getResources().getDisplayMetrics().density));
        inflate.findViewById(2131296458).setBackgroundDrawable((Drawable)new GoPremiumTitleBackgroundDrawable(n));
        this._titleView = (TextView)inflate.findViewById(2131296457);
        final int titleResId = this.getTitleResId();
        if (titleResId > 0) {
            this._titleView.setText((CharSequence)this.getString(titleResId));
        }
        this.inflateContentViewTo(layoutInflater, (LinearLayout)inflate.findViewById(2131296455));
        inflate.findViewById(2131296485).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AbstractConversionDialogFragment.this.dismiss();
            }
        });
        final ViewGroup viewGroup2 = (ViewGroup)inflate.findViewById(2131296456);
        final CustomButton convertButtonInstance = this.createConvertButtonInstance();
        viewGroup2.addView((View)convertButtonInstance);
        convertButtonInstance.setText(this.getConversionButtonTitleResId());
        convertButtonInstance.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (AbstractConversionDialogFragment.this._onConversionClickListener != null) {
                    AbstractConversionDialogFragment.this._onConversionClickListener.onClick(view);
                }
                AbstractConversionDialogFragment.this.dismiss();
            }
        });
        return inflate;
    }
    
    public void setConversionClickListener(final View.OnClickListener onConversionClickListener) {
        this._onConversionClickListener = onConversionClickListener;
    }
}
