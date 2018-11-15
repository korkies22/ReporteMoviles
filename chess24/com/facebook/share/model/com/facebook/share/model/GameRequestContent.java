/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GameRequestContent
implements ShareModel {
    public static final Parcelable.Creator<GameRequestContent> CREATOR = new Parcelable.Creator<GameRequestContent>(){

        public GameRequestContent createFromParcel(Parcel parcel) {
            return new GameRequestContent(parcel);
        }

        public GameRequestContent[] newArray(int n) {
            return new GameRequestContent[n];
        }
    };
    private final ActionType actionType;
    private final String data;
    private final Filters filters;
    private final String message;
    private final String objectId;
    private final List<String> recipients;
    private final List<String> suggestions;
    private final String title;

    GameRequestContent(Parcel parcel) {
        this.message = parcel.readString();
        this.recipients = parcel.createStringArrayList();
        this.title = parcel.readString();
        this.data = parcel.readString();
        this.actionType = (ActionType)((Object)parcel.readSerializable());
        this.objectId = parcel.readString();
        this.filters = (Filters)((Object)parcel.readSerializable());
        this.suggestions = parcel.createStringArrayList();
        parcel.readStringList(this.suggestions);
    }

    private GameRequestContent(Builder builder) {
        this.message = builder.message;
        this.recipients = builder.recipients;
        this.title = builder.title;
        this.data = builder.data;
        this.actionType = builder.actionType;
        this.objectId = builder.objectId;
        this.filters = builder.filters;
        this.suggestions = builder.suggestions;
    }

    public int describeContents() {
        return 0;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public String getData() {
        return this.data;
    }

    public Filters getFilters() {
        return this.filters;
    }

    public String getMessage() {
        return this.message;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public List<String> getRecipients() {
        return this.recipients;
    }

    public List<String> getSuggestions() {
        return this.suggestions;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTo() {
        if (this.getRecipients() != null) {
            return TextUtils.join((CharSequence)",", this.getRecipients());
        }
        return null;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.message);
        parcel.writeStringList(this.recipients);
        parcel.writeString(this.title);
        parcel.writeString(this.data);
        parcel.writeSerializable((Serializable)((Object)this.actionType));
        parcel.writeString(this.objectId);
        parcel.writeSerializable((Serializable)((Object)this.filters));
        parcel.writeStringList(this.suggestions);
    }

    public static enum ActionType {
        SEND,
        ASKFOR,
        TURN;
        

        private ActionType() {
        }
    }

    public static class Builder
    implements ShareModelBuilder<GameRequestContent, Builder> {
        private ActionType actionType;
        private String data;
        private Filters filters;
        private String message;
        private String objectId;
        private List<String> recipients;
        private List<String> suggestions;
        private String title;

        @Override
        public GameRequestContent build() {
            return new GameRequestContent(this);
        }

        @Override
        Builder readFrom(Parcel parcel) {
            return this.readFrom((GameRequestContent)parcel.readParcelable(GameRequestContent.class.getClassLoader()));
        }

        @Override
        public Builder readFrom(GameRequestContent gameRequestContent) {
            if (gameRequestContent == null) {
                return this;
            }
            return this.setMessage(gameRequestContent.getMessage()).setRecipients(gameRequestContent.getRecipients()).setTitle(gameRequestContent.getTitle()).setData(gameRequestContent.getData()).setActionType(gameRequestContent.getActionType()).setObjectId(gameRequestContent.getObjectId()).setFilters(gameRequestContent.getFilters()).setSuggestions(gameRequestContent.getSuggestions());
        }

        public Builder setActionType(ActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        public Builder setData(String string) {
            this.data = string;
            return this;
        }

        public Builder setFilters(Filters filters) {
            this.filters = filters;
            return this;
        }

        public Builder setMessage(String string) {
            this.message = string;
            return this;
        }

        public Builder setObjectId(String string) {
            this.objectId = string;
            return this;
        }

        public Builder setRecipients(List<String> list) {
            this.recipients = list;
            return this;
        }

        public Builder setSuggestions(List<String> list) {
            this.suggestions = list;
            return this;
        }

        public Builder setTitle(String string) {
            this.title = string;
            return this;
        }

        public Builder setTo(String string) {
            if (string != null) {
                this.recipients = Arrays.asList(string.split(","));
            }
            return this;
        }
    }

    public static enum Filters {
        APP_USERS,
        APP_NON_USERS;
        

        private Filters() {
        }
    }

}
