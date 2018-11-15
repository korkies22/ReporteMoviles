/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.Arrays;
import java.util.List;

public static class GameRequestContent.Builder
implements ShareModelBuilder<GameRequestContent, GameRequestContent.Builder> {
    private GameRequestContent.ActionType actionType;
    private String data;
    private GameRequestContent.Filters filters;
    private String message;
    private String objectId;
    private List<String> recipients;
    private List<String> suggestions;
    private String title;

    static /* synthetic */ String access$000(GameRequestContent.Builder builder) {
        return builder.message;
    }

    static /* synthetic */ List access$100(GameRequestContent.Builder builder) {
        return builder.recipients;
    }

    static /* synthetic */ String access$200(GameRequestContent.Builder builder) {
        return builder.title;
    }

    static /* synthetic */ String access$300(GameRequestContent.Builder builder) {
        return builder.data;
    }

    static /* synthetic */ GameRequestContent.ActionType access$400(GameRequestContent.Builder builder) {
        return builder.actionType;
    }

    static /* synthetic */ String access$500(GameRequestContent.Builder builder) {
        return builder.objectId;
    }

    static /* synthetic */ GameRequestContent.Filters access$600(GameRequestContent.Builder builder) {
        return builder.filters;
    }

    static /* synthetic */ List access$700(GameRequestContent.Builder builder) {
        return builder.suggestions;
    }

    @Override
    public GameRequestContent build() {
        return new GameRequestContent(this, null);
    }

    @Override
    GameRequestContent.Builder readFrom(Parcel parcel) {
        return this.readFrom((GameRequestContent)parcel.readParcelable(GameRequestContent.class.getClassLoader()));
    }

    @Override
    public GameRequestContent.Builder readFrom(GameRequestContent gameRequestContent) {
        if (gameRequestContent == null) {
            return this;
        }
        return this.setMessage(gameRequestContent.getMessage()).setRecipients(gameRequestContent.getRecipients()).setTitle(gameRequestContent.getTitle()).setData(gameRequestContent.getData()).setActionType(gameRequestContent.getActionType()).setObjectId(gameRequestContent.getObjectId()).setFilters(gameRequestContent.getFilters()).setSuggestions(gameRequestContent.getSuggestions());
    }

    public GameRequestContent.Builder setActionType(GameRequestContent.ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public GameRequestContent.Builder setData(String string) {
        this.data = string;
        return this;
    }

    public GameRequestContent.Builder setFilters(GameRequestContent.Filters filters) {
        this.filters = filters;
        return this;
    }

    public GameRequestContent.Builder setMessage(String string) {
        this.message = string;
        return this;
    }

    public GameRequestContent.Builder setObjectId(String string) {
        this.objectId = string;
        return this;
    }

    public GameRequestContent.Builder setRecipients(List<String> list) {
        this.recipients = list;
        return this;
    }

    public GameRequestContent.Builder setSuggestions(List<String> list) {
        this.suggestions = list;
        return this;
    }

    public GameRequestContent.Builder setTitle(String string) {
        this.title = string;
        return this;
    }

    public GameRequestContent.Builder setTo(String string) {
        if (string != null) {
            this.recipients = Arrays.asList(string.split(","));
        }
        return this;
    }
}
