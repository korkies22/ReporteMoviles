/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.model;

import com.facebook.share.model.CameraEffectArguments;
import com.facebook.share.model.CameraEffectTextures;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static final class ShareCameraEffectContent.Builder
extends ShareContent.Builder<ShareCameraEffectContent, ShareCameraEffectContent.Builder> {
    private CameraEffectArguments arguments;
    private String effectId;
    private CameraEffectTextures textures;

    static /* synthetic */ String access$000(ShareCameraEffectContent.Builder builder) {
        return builder.effectId;
    }

    static /* synthetic */ CameraEffectArguments access$100(ShareCameraEffectContent.Builder builder) {
        return builder.arguments;
    }

    static /* synthetic */ CameraEffectTextures access$200(ShareCameraEffectContent.Builder builder) {
        return builder.textures;
    }

    @Override
    public ShareCameraEffectContent build() {
        return new ShareCameraEffectContent(this, null);
    }

    @Override
    public ShareCameraEffectContent.Builder readFrom(ShareCameraEffectContent shareCameraEffectContent) {
        if (shareCameraEffectContent == null) {
            return this;
        }
        return ((ShareCameraEffectContent.Builder)super.readFrom(shareCameraEffectContent)).setEffectId(this.effectId).setArguments(this.arguments);
    }

    public ShareCameraEffectContent.Builder setArguments(CameraEffectArguments cameraEffectArguments) {
        this.arguments = cameraEffectArguments;
        return this;
    }

    public ShareCameraEffectContent.Builder setEffectId(String string) {
        this.effectId = string;
        return this;
    }

    public ShareCameraEffectContent.Builder setTextures(CameraEffectTextures cameraEffectTextures) {
        this.textures = cameraEffectTextures;
        return this;
    }
}
