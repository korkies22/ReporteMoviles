/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

public class APIConstants {
    public static final String COMMAND_AVAILABLE_CLOCKS = "mobileAPI/GetAvailableClocks";
    public static final String COMMAND_BUY_CONTENT_GOOGLE = "shopAPI/buyContentGoogle";
    public static final String COMMAND_CHECK_STATUS_OF_API = "statusAPI/check";
    public static final String COMMAND_GET_BROADCAST_SERVER = "mobileAPI/GetBroadcastServer";
    public static final String COMMAND_GET_DASHBOARD_DATA = "mobileAPI/GetDashboardData";
    public static final String COMMAND_GET_IMAGE_URL = "mobileAPI/GetImageUrl";
    public static final String COMMAND_GET_PAIRINGSERVER = "mobileAPI/GetPairingServer";
    public static final String COMMAND_GET_PRODUCT_IDENTIFIERS = "shopAPI/GetProductIdentifiers";
    public static final String COMMAND_GET_SETTINGS = "mobileAPI/GetSettings";
    public static final String COMMAND_GET_TACTICS_PROMISE = "mobileAPI/GetTacticTrainerPromise";
    public static final String COMMAND_GET_TACTICS_RATING = "mobileAPI/GetTacticTrainerRating";
    public static final String COMMAND_GET_TACTICS_SUMMARY = "mobileAPI/GetTacticTrainerSummary";
    public static final String COMMAND_GET_TACTIC_TRAINER_INFO = "mobileAPI/GetTacticTrainerInfo";
    public static final String COMMAND_GET_TACTIC_TRAINER_PUZZLES = "mobileAPI/GetTacticTrainerPuzzles";
    public static final String COMMAND_GET_USER_DATA = "mobileAPI/GetUserData";
    public static final String COMMAND_GET_VIDEO = "videoAPI/GetVideoInfo";
    public static final String COMMAND_GET_VIDEO_SERIES = "videoAPI/GetVideoSeries";
    public static final String COMMAND_GET_VIDEO_SERIES_LIST = "videoAPI/GetVideoSeriesList";
    public static final String COMMAND_LOGIN = "mobileAPI/AuthenticateByLogin";
    public static final String COMMAND_LOGIN_BY_FACEBOOK = "mobileAPI/AuthenticateByFacebook";
    public static final String COMMAND_LOGIN_GUEST = "mobileAPI/AuthenticateGuest";
    public static final String COMMAND_LOGIN_TOKEN = "mobileAPI/VerifyAuthToken";
    public static final String COMMAND_LOGOUT = "mobileAPI/AuthenticateLogout";
    public static final String COMMAND_LOST_PASSWORD = "mobileAPI/lostPassword";
    public static final String COMMAND_OPEN_GAMES = "mobileAPI/GetOpenGames";
    public static final String COMMAND_PLAYNOW = "mobileAPI/PlayNow";
    public static final String COMMAND_REGISTER = "mobileAPI/register";
    public static final String COMMAND_REGISTER_APP = "mobileAPI/RegisterApp";
    public static final String COMMAND_REVALIDATE_SUBSCRIPTION_TOKEN = "shopAPI/RevalidateAndroidSubscriptionToken";
    public static final String COMMAND_SAVE_TACTICS_REPORT = "mobileAPI/SaveTacticTrainerReport";
    public static final String COMMAND_SET_PROFILE_IMAGE = "mobileAPI/SetProfileImage";
    public static final String COMMAND_SET_SETTINGS = "mobileAPI/SetSettings";
    public static final String COMMAND_SET_USER_DATA = "mobileAPI/SetUserData";
    public static final String COMMAND_VALIDATE_SUBSCRIPTION_TOKEN = "shopAPI/ValidateAndroidSubscriptionToken";
    public static final String COMMAND_VIDEO_GET_PRICE_CATEGORIES = "videoAPI/GetPriceCategories";
    private static final String MOBILE_API_CONTROLLER_PREFIX = "mobileAPI/";
    public static final String PARAM_AUTHTOKEN = "authToken";
    public static final String PARAM_BUY_CONTENT_GOOGLE_CONTENT_ID = "contentId";
    public static final String PARAM_BUY_CONTENT_GOOGLE_CONTENT_TYPE = "contentType";
    public static final String PARAM_CHECK_STATUS_OF_API_APPVERSION = "applicationVersion";
    public static final String PARAM_CHECK_STATUS_OF_API_PLATFORM_AGENT = "platform";
    public static final String PARAM_DEVICE_UUID = "duuid";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_FACEBOOK_ACCESS_TOKEN = "fbAuthToken";
    public static final String PARAM_GET_IMAGE_URL_HEIGHT = "height";
    public static final String PARAM_GET_IMAGE_URL_ID = "id";
    public static final String PARAM_GET_IMAGE_URL_WIDTH = "width";
    public static final String PARAM_GET_PRODUCT_IDENTIFIER_PROVIDER = "provider";
    public static final String PARAM_GET_VIDEO_SERIES_LIST_COUNT = "count";
    public static final String PARAM_GET_VIDEO_SERIES_SERIES_ID = "seriesId";
    public static final String PARAM_LOST_PASSWORD_DUUID = "duuid";
    public static final String PARAM_LOST_PASSWORD_EMAIL = "email";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_PLAYNOW_EXTENDED_ENGINE_DATA = "extendedEngineData";
    public static final String PARAM_PRODUKT_IDENTIFIER_SKU = "productIdentifier";
    public static final String PARAM_REGISTER_APP_DEVICE_NAME = "name";
    public static final String PARAM_REGISTER_DUUID = "duuid";
    public static final String PARAM_REGISTER_EMAIL = "email";
    public static final String PARAM_REGISTER_NICK = "nick";
    public static final String PARAM_REGISTER_PASSWORD = "password";
    public static final String PARAM_SET_USER_DATA_COUNTRY = "country";
    public static final String PARAM_SET_USER_DATA_DAY = "day";
    public static final String PARAM_SET_USER_DATA_DOB = "dob";
    public static final String PARAM_SET_USER_DATA_EMAIL = "email";
    public static final String PARAM_SET_USER_DATA_FIRSTNAME = "firstname";
    public static final String PARAM_SET_USER_DATA_GENDER = "gender";
    public static final String PARAM_SET_USER_DATA_MONTH = "month";
    public static final String PARAM_SET_USER_DATA_NEW_PASSWORD = "new_password";
    public static final String PARAM_SET_USER_DATA_OLD_PASSWORD = "old_password";
    public static final String PARAM_SET_USER_DATA_REPEAT_PASSWORD = "repeat_password";
    public static final String PARAM_SET_USER_DATA_SURNAME = "surname";
    public static final String PARAM_SET_USER_DATA_WEBSITE = "website";
    public static final String PARAM_SET_USER_DATA_YEAR = "year";
    public static final String PARAM_USER_UUID = "uuid";
    public static final String PARAM_VALIDATE_SUBSCRIPTION_TOKEN_TOKEN = "token";
    public static final String PARAM_VIDEO_API_GET_PRICE_CATEGORIES_PROVIDER = "provider";
    public static final String PARAM_VIDEO_ID = "videoId";
    public static final int RESULT_OK = 0;
    private static final String SHOP_API_CONTROLLER_PREFIX = "shopAPI/";
    private static final String STATUS_API_CONTROLLER_PREFIX = "statusAPI/";
    private static final String VIDEO_API_CONTROLLER_PREFIX = "videoAPI/";

    private APIConstants() {
    }
}
