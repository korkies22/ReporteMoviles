// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonmapping;

import org.json.JSONException;
import java.util.Iterator;
import de.cisha.chess.model.Country;
import de.cisha.android.board.user.Subscription;
import org.json.JSONArray;
import java.util.GregorianCalendar;
import org.json.JSONObject;
import de.cisha.android.board.user.User;

public class JSONMappingUser implements JSONAPIMapping<User>
{
    @Override
    public JSONObject mapToJSON(final User user) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        final Country country = user.getCountry();
        String alpha2;
        if (country != null) {
            alpha2 = country.getAlpha2();
        }
        else {
            alpha2 = "";
        }
        jsonObject.put("country", (Object)alpha2);
        if (user.getBirthday() != null) {
            final GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(user.getBirthday());
            jsonObject.put("day", gregorianCalendar.get(5));
            jsonObject.put("month", gregorianCalendar.get(2));
            jsonObject.put("year", gregorianCalendar.get(1));
        }
        jsonObject.put("email", (Object)user.getEmail());
        jsonObject.put("firstname", (Object)user.getFirstname());
        if (user.getGender() != null) {
            jsonObject.put("gender", user.getGender().toInt());
        }
        jsonObject.put("nick", (Object)user.getNickname());
        if (user.getProfileImageCouchId() != null) {
            jsonObject.put("profile_image_couch_id", (Object)user.getProfileImageCouchId().getUuid());
        }
        final JSONObject jsonObject2 = new JSONObject();
        if (user.getRatingClassical() != null) {
            jsonObject2.put("classical", (Object)user.getRatingClassical());
        }
        if (user.getRatingBlitz() != null) {
            jsonObject2.put("blitz", (Object)user.getRatingBlitz());
        }
        if (user.getRatingBullet() != null) {
            jsonObject2.put("bullet", (Object)user.getRatingBullet());
        }
        if (user.getRatingTacticClassic() != null) {
            jsonObject2.put("trainerClassic", (Object)user.getRatingTacticClassic());
        }
        if (user.getRatingTacticSpeed() != null) {
            jsonObject2.put("trainerSpeed", (Object)user.getRatingTacticSpeed());
        }
        if (jsonObject2.length() > 0) {
            jsonObject.put("rating", (Object)jsonObject2);
        }
        jsonObject.put("surname", (Object)user.getSurname());
        jsonObject.put("title", (Object)user.getTitle());
        if (user.getUuid() != null) {
            jsonObject.put("uuid", (Object)user.getUuid().getUuid());
        }
        jsonObject.put("website", (Object)user.getWebsite());
        jsonObject.put("isGuest", user.isGuest());
        final JSONArray jsonArray = new JSONArray();
        for (final Subscription subscription : user.getSubscriptions()) {
            final JSONObject jsonObject3 = new JSONObject();
            int n;
            if (JSONMappingUser.1..SwitchMap.de.cisha.android.board.service.SubscriptionType[subscription.getSubscriptionType().ordinal()] != 1) {
                n = 0;
            }
            else {
                n = 1;
            }
            jsonObject3.put("premiumType", n);
            jsonObject3.put("absoluteDateValue", true);
            jsonObject3.put("expires", subscription.getExpirationDate().getTime() / 1000L);
            jsonObject3.put("type", (Object)subscription.getOrigin().jsonValue());
            jsonObject3.put("provider", (Object)subscription.getProvider().jsonValue());
            jsonObject3.put("isRecurring", subscription.isRecurring());
            jsonArray.put((Object)jsonObject3);
        }
        jsonObject.put("subscriptions", (Object)jsonArray);
        jsonObject.put("premiumExpires_abs", user.getPremiumExpireTime().getTime() / 1000L);
        return jsonObject;
    }
}
