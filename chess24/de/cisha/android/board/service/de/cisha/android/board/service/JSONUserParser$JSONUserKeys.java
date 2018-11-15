/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.JSONUserParser;

public static class JSONUserParser.JSONUserKeys {
    public static final String COUNTRY_ISO2 = "country";
    public static final String DAY = "day";
    public static final String DAY_OF_BIRTH = "dob";
    public static final String EMAIL = "email";
    public static final String FIRSTNAME = "firstname";
    public static final String GENDER = "gender";
    public static final String GROUPS_OBJ = "groups";
    public static final String GROUPS_OBJ_ABS = "groups_abs";
    public static final String IS_GUEST = "isGuest";
    public static final String IS_PREMIUM = "isPremium";
    public static final String MONTH = "month";
    public static final String NEW_PASSWORD = "new_password";
    public static final String NICKNAME = "nick";
    public static final String OLD_PASSWORD = "old_password";
    public static final String PREMIUM_EXPIRE_TIME_ABS = "premiumExpires_abs";
    public static final String PREMIUM_EXPIRE_TIME_REL = "premiumExpires";
    public static final String PROFILE_IMAGE_COUCH_ID = "profile_image_couch_id";
    public static final String RATING_OBJ = "rating";
    public static final String REPEAT_PASSWORD = "repeat_password";
    public static final String SUBSCRIPTIONS_ARRAY = "subscriptions";
    public static final String SURNAME = "surname";
    public static final String TITLE = "title";
    public static final String UUID = "uuid";
    public static final String WEBSITE = "website";
    public static final String YEAR = "year";

    public static class JSONGroupsKeys {
        public static final String PREMIUM = "premium";
        public static final String PREMIUM_MOBILE = "premium_mobile";
    }

    public static class JSONRatingKeys {
        public static final String BLITZ = "blitz";
        public static final String BULLET = "bullet";
        public static final String CLASSICAL = "classical";
        public static final String TRAINER_CLASSIC = "trainerClassic";
        public static final String TRAINER_SPEED = "trainerSpeed";
    }

    public static class JSONSubscriptionKeys {
        public static final String SUBSCRIPTION_EXPIRES = "expires";
        public static final String SUBSCRIPTION_EXPIRES_ABSOLUTE_VALUE_FLAG = "absoluteDateValue";
        public static final String SUBSCRIPTION_IS_RECURRING = "isRecurring";
        public static final String SUBSCRIPTION_ORIGIN = "type";
        public static final String SUBSCRIPTION_PROVIDER = "provider";
        public static final String SUBSCRIPTION_TYPE = "premiumType";
    }

}
