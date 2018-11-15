/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonmapping;

import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.service.jsonmapping.JSONAPIMapping;
import de.cisha.android.board.user.Subscription;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Rating;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONMappingUser
implements JSONAPIMapping<User> {
    @Override
    public JSONObject mapToJSON(User user) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        Object object = user.getCountry();
        object = object != null ? object.getAlpha2() : "";
        jSONObject.put("country", object);
        if (user.getBirthday() != null) {
            object = new GregorianCalendar();
            object.setTime(user.getBirthday());
            jSONObject.put("day", object.get(5));
            jSONObject.put("month", object.get(2));
            jSONObject.put("year", object.get(1));
        }
        jSONObject.put("email", (Object)user.getEmail());
        jSONObject.put("firstname", (Object)user.getFirstname());
        if (user.getGender() != null) {
            jSONObject.put("gender", user.getGender().toInt());
        }
        jSONObject.put("nick", (Object)user.getNickname());
        if (user.getProfileImageCouchId() != null) {
            jSONObject.put("profile_image_couch_id", (Object)user.getProfileImageCouchId().getUuid());
        }
        object = new JSONObject();
        if (user.getRatingClassical() != null) {
            object.put("classical", (Object)user.getRatingClassical());
        }
        if (user.getRatingBlitz() != null) {
            object.put("blitz", (Object)user.getRatingBlitz());
        }
        if (user.getRatingBullet() != null) {
            object.put("bullet", (Object)user.getRatingBullet());
        }
        if (user.getRatingTacticClassic() != null) {
            object.put("trainerClassic", (Object)user.getRatingTacticClassic());
        }
        if (user.getRatingTacticSpeed() != null) {
            object.put("trainerSpeed", (Object)user.getRatingTacticSpeed());
        }
        if (object.length() > 0) {
            jSONObject.put("rating", object);
        }
        jSONObject.put("surname", (Object)user.getSurname());
        jSONObject.put("title", (Object)user.getTitle());
        if (user.getUuid() != null) {
            jSONObject.put("uuid", (Object)user.getUuid().getUuid());
        }
        jSONObject.put("website", (Object)user.getWebsite());
        jSONObject.put("isGuest", user.isGuest());
        object = new JSONArray();
        for (Subscription subscription : user.getSubscriptions()) {
            JSONObject jSONObject2 = new JSONObject();
            int n = .$SwitchMap$de$cisha$android$board$service$SubscriptionType[subscription.getSubscriptionType().ordinal()] != 1 ? 0 : 1;
            jSONObject2.put("premiumType", n);
            jSONObject2.put("absoluteDateValue", true);
            jSONObject2.put("expires", subscription.getExpirationDate().getTime() / 1000L);
            jSONObject2.put("type", (Object)subscription.getOrigin().jsonValue());
            jSONObject2.put("provider", (Object)subscription.getProvider().jsonValue());
            jSONObject2.put("isRecurring", subscription.isRecurring());
            object.put((Object)jSONObject2);
        }
        jSONObject.put("subscriptions", object);
        jSONObject.put("premiumExpires_abs", user.getPremiumExpireTime().getTime() / 1000L);
        return jSONObject;
    }

}
