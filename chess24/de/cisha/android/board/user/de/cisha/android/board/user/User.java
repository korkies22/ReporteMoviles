/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.user;

import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.user.Subscription;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Rating;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class User {
    private Date _birthday;
    private CountryCode _countryCode;
    private Rating _elo = new Rating(0);
    private String _email;
    private String _firstname;
    private Gender _gender;
    private boolean _isGuest;
    private String _nickname;
    private Date _premiumExpireTime;
    private CishaUUID _profileImageCouchId;
    private Rating _ratingBlitz;
    private Rating _ratingBullet;
    private Rating _ratingClassical;
    private Rating _ratingTacticClassic;
    private Rating _ratingTacticSpeed;
    private Map<SubscriptionType, Subscription> _subscriptions;
    private String _surname;
    private String _title;
    private CishaUUID _uuid;
    private String _website;

    @Deprecated
    public User() {
        this("noname", "default@email.com");
    }

    public User(String string, String string2) {
        this._nickname = string;
        this._email = string2;
        this._subscriptions = new TreeMap<SubscriptionType, Subscription>();
    }

    public Date getBirthday() {
        return this._birthday;
    }

    public Country getCountry() {
        return this._countryCode;
    }

    @Deprecated
    public Rating getElo() {
        return this._elo;
    }

    public String getEmail() {
        return this._email;
    }

    public String getFirstname() {
        return this._firstname;
    }

    public Gender getGender() {
        return this._gender;
    }

    public String getNickname() {
        return this._nickname;
    }

    public Date getPremiumExpireTime() {
        return this._premiumExpireTime;
    }

    public CishaUUID getProfileImageCouchId() {
        return this._profileImageCouchId;
    }

    public Rating getRatingBlitz() {
        return this._ratingBlitz;
    }

    public Rating getRatingBullet() {
        return this._ratingBullet;
    }

    public Rating getRatingClassical() {
        return this._ratingClassical;
    }

    public Rating getRatingTacticClassic() {
        return this._ratingTacticClassic;
    }

    public Rating getRatingTacticSpeed() {
        return this._ratingTacticSpeed;
    }

    public Subscription getSubscription(SubscriptionType subscriptionType) {
        return this._subscriptions.get((Object)subscriptionType);
    }

    public List<Subscription> getSubscriptions() {
        return new LinkedList<Subscription>(this._subscriptions.values());
    }

    public String getSurname() {
        return this._surname;
    }

    public String getTitle() {
        return this._title;
    }

    public CishaUUID getUuid() {
        return this._uuid;
    }

    public String getWebsite() {
        return this._website;
    }

    public boolean isGuest() {
        return this._isGuest;
    }

    public boolean isPremium() {
        if (this.getPremiumExpireTime() != null && this.getPremiumExpireTime().after(new Date())) {
            return true;
        }
        return false;
    }

    public void setBirthday(Date date) {
        this._birthday = date;
    }

    public void setCountryCode(CountryCode countryCode) {
        this._countryCode = countryCode;
    }

    @Deprecated
    public void setElo(Rating rating) {
        this._elo = rating;
    }

    public void setEmail(String string) {
        this._email = string;
    }

    public void setFirstname(String string) {
        this._firstname = string;
    }

    public void setGender(Gender gender) {
        this._gender = gender;
    }

    public void setIsGuest(boolean bl) {
        this._isGuest = bl;
    }

    public void setNickname(String string) {
        this._nickname = string;
    }

    public void setPremiumExpireTime(Date date) {
        this._premiumExpireTime = date;
    }

    public void setProfileImageCouchId(CishaUUID cishaUUID) {
        this._profileImageCouchId = cishaUUID;
    }

    public void setRatingBlitz(Rating rating) {
        this._ratingBlitz = rating;
    }

    public void setRatingBullet(Rating rating) {
        this._ratingBullet = rating;
    }

    public void setRatingClassical(Rating rating) {
        this._ratingClassical = rating;
    }

    public void setRatingTacticClassic(Rating rating) {
        this._ratingTacticClassic = rating;
    }

    public void setRatingTacticSpeed(Rating rating) {
        this._ratingTacticSpeed = rating;
    }

    public void setSubscriptions(List<Subscription> object) {
        object = object.iterator();
        while (object.hasNext()) {
            Subscription subscription = (Subscription)object.next();
            this._subscriptions.put(subscription.getSubscriptionType(), subscription);
        }
    }

    public void setSurname(String string) {
        this._surname = string;
    }

    public void setTitle(String string) {
        this._title = string;
    }

    public void setUuid(CishaUUID cishaUUID) {
        this._uuid = cishaUUID;
    }

    public void setWebsite(String string) {
        this._website = string;
    }

    public static enum Gender {
        MALE(1, 2131690226),
        FEMALE(2, 2131690225);
        
        private int _id;
        private int _resTitleId;

        private Gender(int n2, int n3) {
            this._id = n2;
            this._resTitleId = n3;
        }

        public static Gender from(int n) {
            switch (n) {
                default: {
                    return MALE;
                }
                case 2: {
                    return FEMALE;
                }
                case 1: 
            }
            return MALE;
        }

        public int getNameResId() {
            return this._resTitleId;
        }

        public int toInt() {
            return this._id;
        }
    }

}
