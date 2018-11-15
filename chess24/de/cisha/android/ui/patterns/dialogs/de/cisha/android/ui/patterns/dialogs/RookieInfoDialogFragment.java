/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import de.cisha.android.ui.patterns.text.CustomTextView;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RookieInfoDialogFragment
extends AbstractDialogFragment {
    private List<CustomButton> _buttonCache = new LinkedList<CustomButton>();
    private LinearLayout _buttonViewGroup;
    private ViewGroup _contentContainerView;
    private TextView _headerText;
    private ImageView _rookieImage;
    private RookieType _rookieType = RookieType.INFO;
    private CharSequence _text;
    private CustomTextView _textView;
    private CharSequence _title;

    public void addButton(CustomButton customButton) {
        if (this._buttonViewGroup != null) {
            int n;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.rightMargin = n = (int)(10.0f * customButton.getContext().getResources().getDisplayMetrics().density);
            layoutParams.leftMargin = n;
            this._buttonViewGroup.addView((View)customButton, (ViewGroup.LayoutParams)layoutParams);
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
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(R.layout.rookie_info_dialog, (ViewGroup)object, false);
        this._headerText = (TextView)layoutInflater.findViewById(R.id.rookie_info_title);
        this._textView = (CustomTextView)layoutInflater.findViewById(R.id.rookie_info_text);
        this._textView.setVisibility(8);
        this._rookieImage = (ImageView)layoutInflater.findViewById(R.id.rookie_info_view_rookie_image);
        this._buttonViewGroup = (LinearLayout)layoutInflater.findViewById(R.id.rookie_info_view_button_container);
        object = this._buttonCache.iterator();
        while (object.hasNext()) {
            this.addButton((CustomButton)((Object)object.next()));
        }
        this._buttonCache.clear();
        this._contentContainerView = (ViewGroup)layoutInflater.findViewById(R.id.rookie_content_container);
        this._rookieImage.setImageResource(this.getRookieType()._imgResId);
        object = this.getButtons().iterator();
        while (object.hasNext()) {
            this.addButton((CustomButton)((Object)object.next()));
        }
        if (this._buttonViewGroup.getChildCount() == 0) {
            this._buttonViewGroup.setVisibility(8);
        }
        return layoutInflater;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this._title != null) {
            this._headerText.setText(this._title);
        }
        if (this._text != null) {
            this._textView.setText(this._text);
        }
    }

    public void setRookieType(RookieType rookieType) {
        this._rookieType = rookieType;
    }

    public void setText(final CharSequence charSequence) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                RookieInfoDialogFragment.this._text = charSequence;
                RookieInfoDialogFragment.this._textView.setText(charSequence);
                CustomTextView customTextView = RookieInfoDialogFragment.this._textView;
                int n = charSequence.length() > 0 ? 0 : 8;
                customTextView.setVisibility(n);
            }
        });
    }

    public void setTitle(final CharSequence charSequence) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                RookieInfoDialogFragment.this._title = charSequence;
                RookieInfoDialogFragment.this._headerText.setText(charSequence);
                TextView textView = RookieInfoDialogFragment.this._headerText;
                int n = charSequence.length() > 0 ? 0 : 8;
                textView.setVisibility(n);
            }
        });
    }

    public static enum RookieType {
        NO_INTERNET(R.drawable.rookie_no_internet),
        NO_LOGIN(R.drawable.rookie_nologin),
        PROMOTION(R.drawable.rookie_promotiondialog),
        LOST_SERVER_CONNECTION(R.drawable.rookie_serverconnection),
        MORE(R.drawable.rookie_promotiondialog),
        INFO(R.drawable.rookie_info),
        RUNNING_GAME(R.drawable.rookie_running_game),
        RUNNING_ENGINE(R.drawable.rookie_running_engine);
        
        private int _imgResId;

        private RookieType(int n2) {
            this._imgResId = n2;
        }
    }

}
