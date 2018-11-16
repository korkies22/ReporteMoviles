// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONArray;
import de.cisha.android.board.JSONValue;
import de.cisha.android.board.JSONValueHelper;
import java.util.List;
import java.util.Date;
import de.cisha.android.board.user.Subscription;
import java.util.LinkedList;
import de.cisha.chess.model.Rating;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.chess.model.CishaUUID;
import java.util.GregorianCalendar;
import org.json.JSONObject;
import de.cisha.android.board.user.User;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONUserParser extends JSONAPIResultParser<User>
{
    @Override
    public User parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        final User user = new User(jsonObject.optString("nick", "NICK_PARSE_ERROR"), jsonObject.optString("email"));
        final JSONObject optJSONObject = jsonObject.optJSONObject("dob");
        if (optJSONObject != null) {
            user.setBirthday(new GregorianCalendar(optJSONObject.optInt("year"), optJSONObject.optInt("month") - 1, optJSONObject.optInt("day")).getTime());
        }
        user.setSurname(jsonObject.optString("surname"));
        user.setFirstname(jsonObject.optString("firstname"));
        user.setTitle(jsonObject.optString("title"));
        user.setUuid(new CishaUUID(jsonObject.optString("uuid"), true));
        user.setProfileImageCouchId(new CishaUUID(jsonObject.optString("profile_image_couch_id"), true));
        user.setGender(User.Gender.from(jsonObject.optInt("gender")));
        user.setWebsite(jsonObject.optString("website"));
        user.setCountryCode(CountryCode.getByCode(jsonObject.optString("country")));
        final JSONObject optJSONObject2 = jsonObject.optJSONObject("rating");
        if (optJSONObject2 != null) {
            user.setRatingBullet(new Rating(optJSONObject2.optInt("bullet", -1)));
            user.setRatingBlitz(new Rating(optJSONObject2.optInt("blitz", -1)));
            user.setRatingClassical(new Rating(optJSONObject2.optInt("classical", -1)));
            user.setRatingTacticClassic(new Rating(optJSONObject2.optInt("trainerClassic", -1)));
            user.setRatingTacticSpeed(new Rating(optJSONObject2.optInt("trainerSpeed", -1)));
        }
        final JSONObject optJSONObject3 = jsonObject.optJSONObject("groups_abs");
        final JSONObject optJSONObject4 = jsonObject.optJSONObject("groups");
        if (optJSONObject3 != null) {
            final LinkedList<Subscription> subscriptions = new LinkedList<Subscription>();
            final long optLong = optJSONObject3.optLong("premium", 0L);
            final long optLong2 = optJSONObject3.optLong("premium_mobile", 0L);
            final long time = new Date().getTime();
            if (optLong != 0L) {
                final long n = (optLong - time) / 1000L;
                if (n > 0L) {
                    subscriptions.add(new Subscription(SubscriptionType.PREMIUM, new Date(n), true, Subscription.SubscriptionOrigin.UNKNOWN, Subscription.SubscriptionProvider.UNKNOWN));
                }
            }
            if (optLong2 != 0L) {
                final long n2 = (optLong2 - time) / 1000L;
                if (n2 > 0L) {
                    subscriptions.add(new Subscription(SubscriptionType.PREMIUM_MOBILE, new Date(n2), true, Subscription.SubscriptionOrigin.UNKNOWN, Subscription.SubscriptionProvider.UNKNOWN));
                }
            }
            user.setSubscriptions(subscriptions);
        }
        else if (optJSONObject4 != null) {
            final long optLong3 = optJSONObject4.optLong("premium");
            final long optLong4 = optJSONObject4.optLong("premium_mobile");
            final LinkedList<Subscription> subscriptions2 = new LinkedList<Subscription>();
            final Date date = new Date(System.currentTimeMillis() + 2678400000L);
            if (optLong3 == -1L) {
                subscriptions2.add(new Subscription(SubscriptionType.PREMIUM, date, true, Subscription.SubscriptionOrigin.UNKNOWN, Subscription.SubscriptionProvider.UNKNOWN));
            }
            else if (optLong4 == -1L) {
                subscriptions2.add(new Subscription(SubscriptionType.PREMIUM_MOBILE, date, true, Subscription.SubscriptionOrigin.UNKNOWN, Subscription.SubscriptionProvider.UNKNOWN));
            }
            user.setSubscriptions(subscriptions2);
        }
        final boolean optBoolean = jsonObject.optBoolean("isPremium", false);
        user.setIsGuest(jsonObject.optBoolean("isGuest", false));
        long optLong5;
        final long n3 = optLong5 = jsonObject.optLong("premiumExpires", 0L);
        if (optBoolean) {
            optLong5 = n3;
            if (n3 == -1L) {
                optLong5 = 2678400L;
            }
        }
        final long optLong6 = jsonObject.optLong("premiumExpires_abs", 0L);
        if (optLong6 > 0L) {
            user.setPremiumExpireTime(new Date(optLong6 * 1000L));
        }
        else {
            user.setPremiumExpireTime(new Date(System.currentTimeMillis() + optLong5 * 1000L));
        }
        final JSONArray optJSONArray = jsonObject.optJSONArray("subscriptions");
        if (optJSONArray != null) {
            final LinkedList<Subscription> subscriptions3 = new LinkedList<Subscription>();
            for (int i = 0; i < optJSONArray.length(); ++i) {
                final JSONObject optJSONObject5 = optJSONArray.optJSONObject(i);
                if (optJSONObject5 != null) {
                    final SubscriptionType premium = SubscriptionType.PREMIUM;
                    SubscriptionType subscriptionType;
                    if (optJSONObject5.optInt("premiumType", 1) != 0) {
                        subscriptionType = SubscriptionType.PREMIUM;
                    }
                    else {
                        subscriptionType = SubscriptionType.PREMIUM_MOBILE;
                    }
                    final Subscription.SubscriptionOrigin subscriptionOrigin = JSONValueHelper.enumValueFromJsonStringValue(optJSONObject5.optString("type", ""), (JSONValue<Subscription.SubscriptionOrigin>[])Subscription.SubscriptionOrigin.values());
                    final Subscription.SubscriptionProvider subscriptionProvider = JSONValueHelper.enumValueFromJsonStringValue(optJSONObject5.optString("provider", ""), (JSONValue<Subscription.SubscriptionProvider>[])Subscription.SubscriptionProvider.values());
                    final boolean optBoolean2 = optJSONObject5.optBoolean("isRecurring", false);
                    final boolean optBoolean3 = optJSONObject5.optBoolean("absoluteDateValue", false);
                    final long optLong7 = optJSONObject5.optLong("expires");
                    long time2;
                    if (!optBoolean3) {
                        time2 = new Date().getTime();
                    }
                    else {
                        time2 = 0L;
                    }
                    Date date2 = new Date(time2 + optLong7 * 1000L);
                    if (optLong7 == -1L) {
                        date2 = new Date(new Date().getTime() + 2678400000L);
                    }
                    subscriptions3.add(new Subscription(subscriptionType, date2, optBoolean2, subscriptionOrigin, subscriptionProvider));
                }
            }
            user.setSubscriptions(subscriptions3);
        }
        return user;
    }
    
    public static class JSONUserKeys
    {
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
        
        public static class JSONGroupsKeys
        {
            public static final String PREMIUM = "premium";
            public static final String PREMIUM_MOBILE = "premium_mobile";
        }
        
        public static class JSONRatingKeys
        {
            public static final String BLITZ = "blitz";
            public static final String BULLET = "bullet";
            public static final String CLASSICAL = "classical";
            public static final String TRAINER_CLASSIC = "trainerClassic";
            public static final String TRAINER_SPEED = "trainerSpeed";
        }
        
        public static class JSONSubscriptionKeys
        {
            public static final String SUBSCRIPTION_EXPIRES = "expires";
            public static final String SUBSCRIPTION_EXPIRES_ABSOLUTE_VALUE_FLAG = "absoluteDateValue";
            public static final String SUBSCRIPTION_IS_RECURRING = "isRecurring";
            public static final String SUBSCRIPTION_ORIGIN = "type";
            public static final String SUBSCRIPTION_PROVIDER = "provider";
            public static final String SUBSCRIPTION_TYPE = "premiumType";
        }
    }
}
