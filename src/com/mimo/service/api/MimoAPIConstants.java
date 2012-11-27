package com.mimo.service.api;

public final class MimoAPIConstants
{
	// CLIENT INFO Constants:
	public static final String CLIENT_ID = "NfXwj_-nso1NYdpZ";
	public static final String CLIENT_SECRET = "xv-lHx9FusqgBWbEWkjDSn5x";
	public static final String REDIRECT_URL = "http://google.com";
	
	// Url Constants
	public static final String URL_KEY_CLIENT_ID = "client_id=";
	public static final String URL_KEY_CLIENT_SECRET = "&client_secret=";
	public static final String URL_KEY_REDIRECT_URL = "&url=";
	public static final String URL_KEY_CODE = "&code=";
	
	public static final String URL_KEY_TOKEN = "?token=";
	
	// Authenticate Url Constants
	public static final String AUTHENTICATE_BASE_URL =
			"https://staging.mimo.com.ng/oauth/v2/authenticate?";
	
	public static final String AUTHENTICATE_KEY_RESPONSE_TYPE =
			"&response_type=code";
	
	// GetAccess Token Url Constants
	public static final String GET_ACCESSTOKEN_BASE_URL =
			"https://staging.mimo.com.ng/oauth/v2/token?";
	
	public static final String GET_ACCESSTOKEN_KEY_GRANT_TYPE =
			"&grant_type=authorization_code";
	
	// Searching URL Constants
	public static final String GET_PROFILE_URL =
			"https://staging.mimo.ng/partner/user/card_id?";
	
	public static final String SEARCH_USERNAME = "username=";
	public static final String SEARCH_EMAIL = "email=";
	public static final String SEARCH_PHONE = "phone=";
	public static final String SEARCH_ACCOUNT_NUMBER = "account_number=";
	public static final String ACCESS_TOKEN = "&access_token=";
	
}
