// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import java.util.Iterator;
import de.cisha.android.ui.patterns.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import java.util.LinkedList;
import de.cisha.android.ui.patterns.text.CustomTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import java.util.List;

public class RookieInfoDialogFragment extends AbstractDialogFragment
{
    private List<CustomButton> _buttonCache;
    private LinearLayout _buttonViewGroup;
    private ViewGroup _contentContainerView;
    private TextView _headerText;
    private ImageView _rookieImage;
    private RookieType _rookieType;
    private CharSequence _text;
    private CustomTextView _textView;
    private CharSequence _title;
    
    public RookieInfoDialogFragment() {
        this._rookieType = RookieType.INFO;
        this._buttonCache = new LinkedList<CustomButton>();
    }
    
    public void addButton(final CustomButton customButton) {
        if (this._buttonViewGroup != null) {
            final LinearLayout.LayoutParams linearLayout.LayoutParams = new LinearLayout.LayoutParams(-2, -2);
            final int n = (int)(10.0f * customButton.getContext().getResources().getDisplayMetrics().density);
            linearLayout.LayoutParams.rightMargin = n;
            linearLayout.LayoutParams.leftMargin = n;
            this._buttonViewGroup.addView((View)customButton, (ViewGroup.LayoutParams)linearLayout.LayoutParams);
            this._buttonViewGroup.setVisibility(0);
            return;
        }
        this._buttonCache.add(customButton);
    }
    
    protected List<CustomButton> getButtons() {
        return new LinkedList<CustomButton>();
    }
    
    public ViewGroup getContentContainerView() {
        return this._contentContainerView;
    }
    
    protected RookieType getRookieType() {
        return this._rookieType;
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(R.layout.rookie_info_dialog, viewGroup, false);
        this._headerText = (TextView)inflate.findViewById(R.id.rookie_info_title);
        (this._textView = (CustomTextView)inflate.findViewById(R.id.rookie_info_text)).setVisibility(8);
        this._rookieImage = (ImageView)inflate.findViewById(R.id.rookie_info_view_rookie_image);
        this._buttonViewGroup = (LinearLayout)inflate.findViewById(R.id.rookie_info_view_button_container);
        final Iterator<CustomButton> iterator = this._buttonCache.iterator();
        while (iterator.hasNext()) {
            this.addButton(iterator.next());
        }
        this._buttonCache.clear();
        this._contentContainerView = (ViewGroup)inflate.findViewById(R.id.rookie_content_container);
        this._rookieImage.setImageResource(this.getRookieType()._imgResId);
        final Iterator<CustomButton> iterator2 = this.getButtons().iterator();
        while (iterator2.hasNext()) {
            this.addButton(iterator2.next());
        }
        if (this._buttonViewGroup.getChildCount() == 0) {
            this._buttonViewGroup.setVisibility(8);
        }
        return inflate;
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this._title != null) {
            this._headerText.setText(this._title);
        }
        if (this._text != null) {
            this._textView.setText(this._text);
        }
    }
    
    public void setRookieType(final RookieType rookieType) {
        this._rookieType = rookieType;
    }
    
    public void setText(final CharSequence charSequence) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                RookieInfoDialogFragment.this._text = charSequence;
                RookieInfoDialogFragment.this._textView.setText(charSequence);
                final CustomTextView access.200 = RookieInfoDialogFragment.this._textView;
                int visibility;
                if (charSequence.length() > 0) {
                    visibility = 0;
                }
                else {
                    visibility = 8;
                }
                access.200.setVisibility(visibility);
            }
        });
    }
    
    public void setTitle(final CharSequence charSequence) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                RookieInfoDialogFragment.this._title = charSequence;
                RookieInfoDialogFragment.this._headerText.setText(charSequence);
                final TextView access.400 = RookieInfoDialogFragment.this._headerText;
                int visibility;
                if (charSequence.length() > 0) {
                    visibility = 0;
                }
                else {
                    visibility = 8;
                }
                access.400.setVisibility(visibility);
            }
        });
    }
    
    public enum RookieType
    {
        INFO(R.drawable.rookie_info), 
        LOST_SERVER_CONNECTION(R.drawable.rookie_serverconnection), 
        MORE(R.drawable.rookie_promotiondialog), 
        NO_INTERNET(R.drawable.rookie_no_internet), 
        NO_LOGIN(R.drawable.rookie_nologin), 
        PROMOTION(R.drawable.rookie_promotiondialog), 
        RUNNING_ENGINE(R.drawable.rookie_running_engine), 
        RUNNING_GAME(R.drawable.rookie_running_game);
        
        private int _imgResId;
        
        private RookieType(final int imgResId) {
            this._imgResId = imgResId;
        }
    }
}
