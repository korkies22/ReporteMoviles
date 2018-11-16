// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import de.cisha.chess.model.CishaUUID;
import android.view.View.OnClickListener;
import de.cisha.chess.model.Country;
import android.graphics.Bitmap;
import de.cisha.android.board.PremiumIndicatorManager;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.view.CouchImageView;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

public class ProfileDataView extends LinearLayout
{
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
    
    public ProfileDataView(final Context context) {
        super(context);
        this.init();
    }
    
    public ProfileDataView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
        inflate(this.getContext(), 2131427513, (ViewGroup)this);
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
        this._flag.setImageBitmap((Bitmap)null);
        this._profileImageView.setImageBitmap((Bitmap)null);
    }
    
    public void setCountry(final Country country) {
        if (country != null) {
            this._countryText.setText((CharSequence)country.getDisplayName(this.getContext().getResources()));
            this._flag.setImageResource(country.getImageId());
            return;
        }
        this._countryText.setText((CharSequence)"");
        this._flag.setImageBitmap((Bitmap)null);
    }
    
    public void setImage(final Bitmap imageBitmap) {
        this._profileImageView.setImageBitmap(imageBitmap);
    }
    
    public void setName(final String text) {
        this._name.setText((CharSequence)text);
    }
    
    public void setNickname(final String text) {
        this._nickname.setText((CharSequence)text);
    }
    
    public void setOnImageClickListener(final View.OnClickListener onClickListener) {
        this._profileImageView.setOnClickListener(onClickListener);
    }
    
    public void setProfileImageCouchId(final CishaUUID couchId) {
        this._profileImageView.setCouchId(couchId);
    }
    
    public void setRatingBlitzText(final String text) {
        this._blitzRating.setText((CharSequence)text);
    }
    
    public void setRatingBulletText(final String text) {
        this._bulletRating.setText((CharSequence)text);
    }
    
    public void setRatingStandardText(final String text) {
        this._classicRating.setText((CharSequence)text);
    }
    
    public void setRatingTacticClassicText(final String text) {
        this._tacticRating.setText((CharSequence)text);
    }
    
    public void showImageLoading() {
        this._loadingAnimation.setVisibility(0);
    }
    
    public void stopShowingImageLoading() {
        this._loadingAnimation.setVisibility(4);
    }
}
