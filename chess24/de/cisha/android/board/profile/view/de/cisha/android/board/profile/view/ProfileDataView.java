/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ProgressBar
 *  android.widget.TextView
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.cisha.android.board.PremiumIndicatorManager;
import de.cisha.android.view.CouchImageView;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Country;

public class ProfileDataView
extends LinearLayout {
    private TextView _blitzRating;
    private TextView _bulletRating;
    private TextView _classicRating;
    private TextView _countryText;
    private ImageView _flag;
    private ProgressBar _loadingAnimation;
    private TextView _name;
    private TextView _nickname;
    private CouchImageView _profileImageView;
    private TextView _tacticRating;

    public ProfileDataView(Context context) {
        super(context);
        this.init();
    }

    public ProfileDataView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
        ProfileDataView.inflate((Context)this.getContext(), (int)2131427513, (ViewGroup)this);
        this._name = (TextView)this.findViewById(2131296806);
        this._nickname = (TextView)this.findViewById(2131296810);
        PremiumIndicatorManager.getInstance().addTextViewToIndicators(this._nickname);
        this._countryText = (TextView)this.findViewById(2131296805);
        this._flag = (ImageView)this.findViewById(2131296804);
        this._classicRating = (TextView)this.findViewById(2131296813);
        this._blitzRating = (TextView)this.findViewById(2131296811);
        this._bulletRating = (TextView)this.findViewById(2131296812);
        this._tacticRating = (TextView)this.findViewById(2131296814);
        this._profileImageView = (CouchImageView)this.findViewById(2131296807);
        this._loadingAnimation = (ProgressBar)this.findViewById(2131296809);
        this.reset();
    }

    public void reset() {
        this._name.setText((CharSequence)"");
        this._nickname.setText((CharSequence)"");
        this._countryText.setText((CharSequence)"");
        this._classicRating.setText((CharSequence)"-");
        this._blitzRating.setText((CharSequence)"-");
        this._bulletRating.setText((CharSequence)"-");
        this._tacticRating.setText((CharSequence)"-");
        this._flag.setImageBitmap(null);
        this._profileImageView.setImageBitmap(null);
    }

    public void setCountry(Country country) {
        if (country != null) {
            this._countryText.setText((CharSequence)country.getDisplayName(this.getContext().getResources()));
            this._flag.setImageResource(country.getImageId());
            return;
        }
        this._countryText.setText((CharSequence)"");
        this._flag.setImageBitmap(null);
    }

    public void setImage(Bitmap bitmap) {
        this._profileImageView.setImageBitmap(bitmap);
    }

    public void setName(String string) {
        this._name.setText((CharSequence)string);
    }

    public void setNickname(String string) {
        this._nickname.setText((CharSequence)string);
    }

    public void setOnImageClickListener(View.OnClickListener onClickListener) {
        this._profileImageView.setOnClickListener(onClickListener);
    }

    public void setProfileImageCouchId(CishaUUID cishaUUID) {
        this._profileImageView.setCouchId(cishaUUID);
    }

    public void setRatingBlitzText(String string) {
        this._blitzRating.setText((CharSequence)string);
    }

    public void setRatingBulletText(String string) {
        this._bulletRating.setText((CharSequence)string);
    }

    public void setRatingStandardText(String string) {
        this._classicRating.setText((CharSequence)string);
    }

    public void setRatingTacticClassicText(String string) {
        this._tacticRating.setText((CharSequence)string);
    }

    public void showImageLoading() {
        this._loadingAnimation.setVisibility(0);
    }

    public void stopShowingImageLoading() {
        this._loadingAnimation.setVisibility(4);
    }
}
