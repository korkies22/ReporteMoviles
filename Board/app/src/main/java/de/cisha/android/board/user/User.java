// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.user;

import java.util.Iterator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import de.cisha.chess.model.Country;
import java.util.TreeMap;
import de.cisha.android.board.service.SubscriptionType;
import java.util.Map;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Rating;
import com.neovisionaries.i18n.CountryCode;
import java.util.Date;

public class User
{
    private Date _birthday;
    private CountryCode _countryCode;
    private Rating _elo;
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
    
    public User() {
        this("noname", "default@email.com");
    }
    
    public User(final String nickname, final String email) {
        this._elo = new Rating(0);
        this._nickname = nickname;
        this._email = email;
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
    
    public Subscription getSubscription(final SubscriptionType subscriptionType) {
        return this._subscriptions.get(subscriptionType);
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
        return this.getPremiumExpireTime() != null && this.getPremiumExpireTime().after(new Date());
    }
    
    public void setBirthday(final Date birthday) {
        this._birthday = birthday;
    }
    
    public void setCountryCode(final CountryCode countryCode) {
        this._countryCode = countryCode;
    }
    
    @Deprecated
    public void setElo(final Rating elo) {
        this._elo = elo;
    }
    
    public void setEmail(final String email) {
        this._email = email;
    }
    
    public void setFirstname(final String firstname) {
        this._firstname = firstname;
    }
    
    public void setGender(final Gender gender) {
        this._gender = gender;
    }
    
    public void setIsGuest(final boolean isGuest) {
        this._isGuest = isGuest;
    }
    
    public void setNickname(final String nickname) {
        this._nickname = nickname;
    }
    
    public void setPremiumExpireTime(final Date premiumExpireTime) {
        this._premiumExpireTime = premiumExpireTime;
    }
    
    public void setProfileImageCouchId(final CishaUUID profileImageCouchId) {
        this._profileImageCouchId = profileImageCouchId;
    }
    
    public void setRatingBlitz(final Rating ratingBlitz) {
        this._ratingBlitz = ratingBlitz;
    }
    
    public void setRatingBullet(final Rating ratingBullet) {
        this._ratingBullet = ratingBullet;
    }
    
    public void setRatingClassical(final Rating ratingClassical) {
        this._ratingClassical = ratingClassical;
    }
    
    public void setRatingTacticClassic(final Rating ratingTacticClassic) {
        this._ratingTacticClassic = ratingTacticClassic;
    }
    
    public void setRatingTacticSpeed(final Rating ratingTacticSpeed) {
        this._ratingTacticSpeed = ratingTacticSpeed;
    }
    
    public void setSubscriptions(final List<Subscription> list) {
        for (final Subscription subscription : list) {
            this._subscriptions.put(subscription.getSubscriptionType(), subscription);
        }
    }
    
    public void setSurname(final String surname) {
        this._surname = surname;
    }
    
    public void setTitle(final String title) {
        this._title = title;
    }
    
    public void setUuid(final CishaUUID uuid) {
        this._uuid = uuid;
    }
    
    public void setWebsite(final String website) {
        this._website = website;
    }
    
    public enum Gender
    {
        FEMALE(2, 2131690225), 
        MALE(1, 2131690226);
        
        private int _id;
        private int _resTitleId;
        
        private Gender(final int id, final int resTitleId) {
            this._id = id;
            this._resTitleId = resTitleId;
        }
        
        public static Gender from(final int n) {
            switch (n) {
                default: {
                    return Gender.MALE;
                }
                case 2: {
                    return Gender.FEMALE;
                }
                case 1: {
                    return Gender.MALE;
                }
            }
        }
        
        public int getNameResId() {
            return this._resTitleId;
        }
        
        public int toInt() {
            return this._id;
        }
    }
}
