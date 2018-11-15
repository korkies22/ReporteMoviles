/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.JSONValueHelper;
import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.user.Subscription;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Rating;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUserParser
extends JSONAPIResultParser<User> {
    @Override
    public User parseResult(JSONObject object) throws InvalidJsonForObjectException {
        long l;
        long l2;
        int n;
        User user = new User(object.optString("nick", "NICK_PARSE_ERROR"), object.optString("email"));
        Object object2 = object.optJSONObject("dob");
        if (object2 != null) {
            n = object2.optInt("day");
            int n2 = object2.optInt("month");
            user.setBirthday(new GregorianCalendar(object2.optInt("year"), n2 - 1, n).getTime());
        }
        user.setSurname(object.optString("surname"));
        user.setFirstname(object.optString("firstname"));
        user.setTitle(object.optString("title"));
        user.setUuid(new CishaUUID(object.optString("uuid"), true));
        user.setProfileImageCouchId(new CishaUUID(object.optString("profile_image_couch_id"), true));
        user.setGender(User.Gender.from(object.optInt("gender")));
        user.setWebsite(object.optString("website"));
        user.setCountryCode(CountryCode.getByCode(object.optString("country")));
        object2 = object.optJSONObject("rating");
        if (object2 != null) {
            user.setRatingBullet(new Rating(object2.optInt("bullet", -1)));
            user.setRatingBlitz(new Rating(object2.optInt("blitz", -1)));
            user.setRatingClassical(new Rating(object2.optInt("classical", -1)));
            user.setRatingTacticClassic(new Rating(object2.optInt("trainerClassic", -1)));
            user.setRatingTacticSpeed(new Rating(object2.optInt("trainerSpeed", -1)));
        }
        object2 = object.optJSONObject("groups_abs");
        Object object3 = object.optJSONObject("groups");
        if (object2 != null) {
            object3 = new LinkedList();
            long l3 = object2.optLong("premium", 0L);
            l = object2.optLong("premium_mobile", 0L);
            l2 = new Date().getTime();
            if (l3 != 0L && (l3 = (l3 - l2) / 1000L) > 0L) {
                object3.add(new Subscription(SubscriptionType.PREMIUM, new Date(l3), true, Subscription.SubscriptionOrigin.UNKNOWN, Subscription.SubscriptionProvider.UNKNOWN));
            }
            if (l != 0L && (l = (l - l2) / 1000L) > 0L) {
                object3.add(new Subscription(SubscriptionType.PREMIUM_MOBILE, new Date(l), true, Subscription.SubscriptionOrigin.UNKNOWN, Subscription.SubscriptionProvider.UNKNOWN));
            }
            user.setSubscriptions((List<Subscription>)object3);
        } else if (object3 != null) {
            l = object3.optLong("premium");
            l2 = object3.optLong("premium_mobile");
            object2 = new LinkedList();
            object3 = new Date(System.currentTimeMillis() + 2678400000L);
            if (l == -1L) {
                object2.add(new Subscription(SubscriptionType.PREMIUM, (Date)object3, true, Subscription.SubscriptionOrigin.UNKNOWN, Subscription.SubscriptionProvider.UNKNOWN));
            } else if (l2 == -1L) {
                object2.add(new Subscription(SubscriptionType.PREMIUM_MOBILE, (Date)object3, true, Subscription.SubscriptionOrigin.UNKNOWN, Subscription.SubscriptionProvider.UNKNOWN));
            }
            user.setSubscriptions((List<Subscription>)object2);
        }
        boolean bl = object.optBoolean("isPremium", false);
        user.setIsGuest(object.optBoolean("isGuest", false));
        l = l2 = object.optLong("premiumExpires", 0L);
        if (bl) {
            l = l2;
            if (l2 == -1L) {
                l = 2678400L;
            }
        }
        if ((l2 = object.optLong("premiumExpires_abs", 0L)) > 0L) {
            user.setPremiumExpireTime(new Date(l2 * 1000L));
        } else {
            user.setPremiumExpireTime(new Date(System.currentTimeMillis() + l * 1000L));
        }
        object3 = object.optJSONArray("subscriptions");
        if (object3 != null) {
            LinkedList<Subscription> linkedList = new LinkedList<Subscription>();
            for (n = 0; n < object3.length(); ++n) {
                object2 = object3.optJSONObject(n);
                if (object2 == null) continue;
                object = SubscriptionType.PREMIUM;
                object = object2.optInt("premiumType", 1) != 0 ? SubscriptionType.PREMIUM : SubscriptionType.PREMIUM_MOBILE;
                Subscription.SubscriptionOrigin subscriptionOrigin = (Subscription.SubscriptionOrigin)JSONValueHelper.enumValueFromJsonStringValue(object2.optString("type", ""), Subscription.SubscriptionOrigin.values());
                Subscription.SubscriptionProvider subscriptionProvider = (Subscription.SubscriptionProvider)JSONValueHelper.enumValueFromJsonStringValue(object2.optString("provider", ""), Subscription.SubscriptionProvider.values());
                bl = object2.optBoolean("isRecurring", false);
                boolean bl2 = object2.optBoolean("absoluteDateValue", false);
                l2 = object2.optLong("expires");
                l = !bl2 ? new Date().getTime() : 0L;
                object2 = new Date(l + l2 * 1000L);
                if (l2 == -1L) {
                    object2 = new Date(new Date().getTime() + 2678400000L);
                }
                linkedList.add(new Subscription((SubscriptionType)((Object)object), (Date)object2, bl, subscriptionOrigin, subscriptionProvider));
            }
            user.setSubscriptions(linkedList);
        }
        return user;
    }

    public static class JSONUserKeys {
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
    }

    public static class JSONUserKeys$JSONGroupsKeys {
        public static final String PREMIUM = "premium";
        public static final String PREMIUM_MOBILE = "premium_mobile";
    }

    public static class JSONUserKeys$JSONRatingKeys {
        public static final String BLITZ = "blitz";
        public static final String BULLET = "bullet";
        public static final String CLASSICAL = "classical";
        public static final String TRAINER_CLASSIC = "trainerClassic";
        public static final String TRAINER_SPEED = "trainerSpeed";
    }

    public static class JSONUserKeys$JSONSubscriptionKeys {
        public static final String SUBSCRIPTION_EXPIRES = "expires";
        public static final String SUBSCRIPTION_EXPIRES_ABSOLUTE_VALUE_FLAG = "absoluteDateValue";
        public static final String SUBSCRIPTION_IS_RECURRING = "isRecurring";
        public static final String SUBSCRIPTION_ORIGIN = "type";
        public static final String SUBSCRIPTION_PROVIDER = "provider";
        public static final String SUBSCRIPTION_TYPE = "premiumType";
    }

}
