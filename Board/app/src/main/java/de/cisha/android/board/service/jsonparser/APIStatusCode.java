// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

public enum APIStatusCode
{
    API_ERROR_API_NOT_VALID_PLEASE_UPDATE(11), 
    API_ERROR_CODE_INVALID_DEVICE(10), 
    API_ERROR_FORBIDDEN(3), 
    API_ERROR_INVALID_AUTHTOKEN(8), 
    API_ERROR_INVALID_CREDENTIALS(6), 
    API_ERROR_MISSING_ARGUMENT(2), 
    API_ERROR_NOT_FOUND(4), 
    API_ERROR_NOT_SET(-1), 
    API_ERROR_PAYMENT_FAILED(12), 
    API_ERROR_SERVICE_UNAVAILABLE(5), 
    API_ERROR_TOO_MANY_REQUESTS(9), 
    API_ERROR_UNCONFIRMED_ACCOUNT(7), 
    API_ERROR_UNKNOWN(1), 
    API_OK(0), 
    INTERNAL_INCORRECT_SERVER_JSON(33003), 
    INTERNAL_INCORRECT_SERVER_JSON_NO_DATA_OBJECT(33005), 
    INTERNAL_MALFORMED_API_URL(33002), 
    INTERNAL_NOT_LOGGED_IN(33001), 
    INTERNAL_PURCHASE_CONSUMPTION_ERROR(35002), 
    INTERNAL_PURCHASE_ERROR(35001), 
    INTERNAL_TACTICTRAINER_DAILY_LIMIT_REACHED(34003), 
    INTERNAL_TACTICTRAINER_GUEST_LIMIT_REACHED(34002), 
    INTERNAL_TACTICTRAINER_NO_MORE_EXERCISES(34001), 
    INTERNAL_TIMEOUT(33004), 
    INTERNAL_UNKNOWN(32999);
    
    private int _statusCode;
    
    private APIStatusCode(final int statusCode) {
        this._statusCode = statusCode;
    }
    
    public static APIStatusCode statusCodeFor(final int n) {
        final APIStatusCode[] values = values();
        for (int i = 0; i < values.length; ++i) {
            final APIStatusCode apiStatusCode = values[i];
            if (apiStatusCode._statusCode == n) {
                return apiStatusCode;
            }
        }
        return APIStatusCode.API_ERROR_UNKNOWN;
    }
}
