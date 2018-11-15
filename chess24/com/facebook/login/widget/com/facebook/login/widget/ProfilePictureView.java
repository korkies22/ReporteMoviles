/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package com.facebook.login.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.facebook.FacebookException;
import com.facebook.LoggingBehavior;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageResponse;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.login.R;

public class ProfilePictureView
extends FrameLayout {
    private static final String BITMAP_HEIGHT_KEY = "ProfilePictureView_height";
    private static final String BITMAP_KEY = "ProfilePictureView_bitmap";
    private static final String BITMAP_WIDTH_KEY = "ProfilePictureView_width";
    public static final int CUSTOM = -1;
    private static final boolean IS_CROPPED_DEFAULT_VALUE = true;
    private static final String IS_CROPPED_KEY = "ProfilePictureView_isCropped";
    public static final int LARGE = -4;
    private static final int MIN_SIZE = 1;
    public static final int NORMAL = -3;
    private static final String PENDING_REFRESH_KEY = "ProfilePictureView_refresh";
    private static final String PRESET_SIZE_KEY = "ProfilePictureView_presetSize";
    private static final String PROFILE_ID_KEY = "ProfilePictureView_profileId";
    public static final int SMALL = -2;
    private static final String SUPER_STATE_KEY = "ProfilePictureView_superState";
    public static final String TAG = "ProfilePictureView";
    private Bitmap customizedDefaultProfilePicture = null;
    private ImageView image;
    private Bitmap imageContents;
    private boolean isCropped = true;
    private ImageRequest lastRequest;
    private OnErrorListener onErrorListener;
    private int presetSizeType = -1;
    private String profileId;
    private int queryHeight = 0;
    private int queryWidth = 0;

    public ProfilePictureView(Context context) {
        super(context);
        this.initialize(context);
    }

    public ProfilePictureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initialize(context);
        this.parseAttributes(attributeSet);
    }

    public ProfilePictureView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initialize(context);
        this.parseAttributes(attributeSet);
    }

    private int getPresetSizeInPixels(boolean bl) {
        int n;
        switch (this.presetSizeType) {
            default: {
                return 0;
            }
            case -1: {
                if (!bl) {
                    return 0;
                }
                n = R.dimen.com_facebook_profilepictureview_preset_size_normal;
                break;
            }
            case -2: {
                n = R.dimen.com_facebook_profilepictureview_preset_size_small;
                break;
            }
            case -3: {
                n = R.dimen.com_facebook_profilepictureview_preset_size_normal;
                break;
            }
            case -4: {
                n = R.dimen.com_facebook_profilepictureview_preset_size_large;
            }
        }
        return this.getResources().getDimensionPixelSize(n);
    }

    private void initialize(Context context) {
        this.removeAllViews();
        this.image = new ImageView(context);
        context = new FrameLayout.LayoutParams(-1, -1);
        this.image.setLayoutParams((ViewGroup.LayoutParams)context);
        this.image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        this.addView((View)this.image);
    }

    private void parseAttributes(AttributeSet attributeSet) {
        attributeSet = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.com_facebook_profile_picture_view);
        this.setPresetSize(attributeSet.getInt(R.styleable.com_facebook_profile_picture_view_com_facebook_preset_size, -1));
        this.isCropped = attributeSet.getBoolean(R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped, true);
        attributeSet.recycle();
    }

    private void processResponse(ImageResponse object) {
        if (object.getRequest() == this.lastRequest) {
            this.lastRequest = null;
            Object object2 = object.getBitmap();
            Exception exception = object.getError();
            if (exception != null) {
                object = this.onErrorListener;
                if (object != null) {
                    object2 = new StringBuilder();
                    object2.append("Error in downloading profile picture for profileId: ");
                    object2.append(this.getProfileId());
                    object.onError(new FacebookException(object2.toString(), exception));
                    return;
                }
                Logger.log(LoggingBehavior.REQUESTS, 6, TAG, exception.toString());
                return;
            }
            if (object2 != null) {
                this.setImageBitmap((Bitmap)object2);
                if (object.isCachedRedirect()) {
                    this.sendImageRequest(false);
                }
            }
        }
    }

    private void refreshImage(boolean bl) {
        boolean bl2 = this.updateImageQueryParameters();
        if (this.profileId != null && this.profileId.length() != 0 && (this.queryWidth != 0 || this.queryHeight != 0)) {
            if (bl2 || bl) {
                this.sendImageRequest(true);
                return;
            }
        } else {
            this.setBlankProfilePicture();
        }
    }

    private void sendImageRequest(boolean bl) {
        ImageRequest imageRequest = new ImageRequest.Builder(this.getContext(), ImageRequest.getProfilePictureUri(this.profileId, this.queryWidth, this.queryHeight)).setAllowCachedRedirects(bl).setCallerTag((Object)this).setCallback(new ImageRequest.Callback(){

            @Override
            public void onCompleted(ImageResponse imageResponse) {
                ProfilePictureView.this.processResponse(imageResponse);
            }
        }).build();
        if (this.lastRequest != null) {
            ImageDownloader.cancelRequest(this.lastRequest);
        }
        this.lastRequest = imageRequest;
        ImageDownloader.downloadAsync(imageRequest);
    }

    private void setBlankProfilePicture() {
        if (this.lastRequest != null) {
            ImageDownloader.cancelRequest(this.lastRequest);
        }
        if (this.customizedDefaultProfilePicture == null) {
            int n = this.isCropped() ? R.drawable.com_facebook_profile_picture_blank_square : R.drawable.com_facebook_profile_picture_blank_portrait;
            this.setImageBitmap(BitmapFactory.decodeResource((Resources)this.getResources(), (int)n));
            return;
        }
        this.updateImageQueryParameters();
        this.setImageBitmap(Bitmap.createScaledBitmap((Bitmap)this.customizedDefaultProfilePicture, (int)this.queryWidth, (int)this.queryHeight, (boolean)false));
    }

    private void setImageBitmap(Bitmap bitmap) {
        if (this.image != null && bitmap != null) {
            this.imageContents = bitmap;
            this.image.setImageBitmap(bitmap);
        }
    }

    private boolean updateImageQueryParameters() {
        int n = this.getHeight();
        int n2 = this.getWidth();
        boolean bl = true;
        if (n2 >= 1) {
            if (n < 1) {
                return false;
            }
            int n3 = this.getPresetSizeInPixels(false);
            if (n3 != 0) {
                n2 = n = n3;
            }
            if (n2 <= n) {
                n = this.isCropped() ? n2 : 0;
            } else {
                n2 = this.isCropped() ? n : 0;
            }
            boolean bl2 = bl;
            if (n2 == this.queryWidth) {
                bl2 = n != this.queryHeight ? bl : false;
            }
            this.queryWidth = n2;
            this.queryHeight = n;
            return bl2;
        }
        return false;
    }

    public final OnErrorListener getOnErrorListener() {
        return this.onErrorListener;
    }

    public final int getPresetSize() {
        return this.presetSizeType;
    }

    public final String getProfileId() {
        return this.profileId;
    }

    public final boolean isCropped() {
        return this.isCropped;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.lastRequest = null;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.refreshImage(false);
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        int n5 = View.MeasureSpec.getSize((int)n2);
        int n6 = View.MeasureSpec.getSize((int)n);
        if (View.MeasureSpec.getMode((int)n2) != 1073741824 && layoutParams.height == -2) {
            n5 = this.getPresetSizeInPixels(true);
            n4 = View.MeasureSpec.makeMeasureSpec((int)n5, (int)1073741824);
            n2 = 1;
        } else {
            n3 = 0;
            n4 = n2;
            n2 = n3;
        }
        int n7 = n6;
        int n8 = n2;
        n3 = n;
        if (View.MeasureSpec.getMode((int)n) != 1073741824) {
            n7 = n6;
            n8 = n2;
            n3 = n;
            if (layoutParams.width == -2) {
                n7 = this.getPresetSizeInPixels(true);
                n3 = View.MeasureSpec.makeMeasureSpec((int)n7, (int)1073741824);
                n8 = 1;
            }
        }
        if (n8 != 0) {
            this.setMeasuredDimension(n7, n5);
            this.measureChildren(n3, n4);
            return;
        }
        super.onMeasure(n3, n4);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable.getClass() != Bundle.class) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (Bundle)parcelable;
        super.onRestoreInstanceState(parcelable.getParcelable(SUPER_STATE_KEY));
        this.profileId = parcelable.getString(PROFILE_ID_KEY);
        this.presetSizeType = parcelable.getInt(PRESET_SIZE_KEY);
        this.isCropped = parcelable.getBoolean(IS_CROPPED_KEY);
        this.queryWidth = parcelable.getInt(BITMAP_WIDTH_KEY);
        this.queryHeight = parcelable.getInt(BITMAP_HEIGHT_KEY);
        this.refreshImage(true);
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE_KEY, parcelable);
        bundle.putString(PROFILE_ID_KEY, this.profileId);
        bundle.putInt(PRESET_SIZE_KEY, this.presetSizeType);
        bundle.putBoolean(IS_CROPPED_KEY, this.isCropped);
        bundle.putInt(BITMAP_WIDTH_KEY, this.queryWidth);
        bundle.putInt(BITMAP_HEIGHT_KEY, this.queryHeight);
        boolean bl = this.lastRequest != null;
        bundle.putBoolean(PENDING_REFRESH_KEY, bl);
        return bundle;
    }

    public final void setCropped(boolean bl) {
        this.isCropped = bl;
        this.refreshImage(false);
    }

    public final void setDefaultProfilePicture(Bitmap bitmap) {
        this.customizedDefaultProfilePicture = bitmap;
    }

    public final void setOnErrorListener(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    public final void setPresetSize(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException("Must use a predefined preset size");
            }
            case -4: 
            case -3: 
            case -2: 
            case -1: 
        }
        this.presetSizeType = n;
        this.requestLayout();
    }

    public final void setProfileId(String string) {
        boolean bl;
        if (!Utility.isNullOrEmpty(this.profileId) && this.profileId.equalsIgnoreCase(string)) {
            bl = false;
        } else {
            this.setBlankProfilePicture();
            bl = true;
        }
        this.profileId = string;
        this.refreshImage(bl);
    }

    public static interface OnErrorListener {
        public void onError(FacebookException var1);
    }

}
