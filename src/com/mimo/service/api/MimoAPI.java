package com.mimo.service.api;

import org.json.JSONObject;

import android.util.Log;

public class MimoAPI
{
	private static final String TAG = MimoAPI.class.getName();
	
	private String m_token; // Access Token
	
	public MimoAPI()
	{
		this.m_token = "";
	}
	
	/**
	 * checks to see if the user has a valid access token
	 * 
	 * @return YES if a valid access token is present, false otherwise
	 **/
	public boolean hasToken()
	{
		if (this.m_token.equals(""))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * gets the stored access token
	 * 
	 * @return a string representing the access token
	 **/
	public String getAccessToken()
	{
		return m_token;
	}
	
	/**
	 * sets the access token
	 * 
	 * @param p_token
	 *            the string representing the token
	 **/
	public void setAccessToken(String p_token)
	{
		this.m_token = p_token;
	}
	
	/**
	 * clears the access token
	 **/
	public void clearAccessToken()
	{
		this.m_token = null;
	}
	
	/**
	 * A function to generate the Authentication Request Url which is to be opened
	 * in the webview
	 * 
	 * @return url : url generated for making the Authentication request.
	 **/
	
	public String getAuthenticationRequestURL()
	{
		StringBuffer url = new StringBuffer();
		
		url.append(MimoAPIConstants.AUTHENTICATE_BASE_URL);
		url.append(MimoAPIConstants.URL_KEY_CLIENT_ID
				+ MimoAPIConstants.CLIENT_ID);
		url.append(MimoAPIConstants.URL_KEY_REDIRECT_URL
				+ MimoAPIConstants.REDIRECT_URL);
		url.append(MimoAPIConstants.AUTHENTICATE_KEY_RESPONSE_TYPE);
		
		Log.d(TAG, "AuthenticationRequest URL = " + url);
		
		return url.toString();
	}
	
	/**
	 * A function to generate the Accesstoken Request Url 
	 * 
	 * @param p_Code
	 *            :the code received from the application.
	 * 
	 * @return url : url generated for making the Authentication request.
	 **/
	public String getAccessTokenRequestURL(String p_Code)
	{
		StringBuffer url = new StringBuffer();
		
		url.append(MimoAPIConstants.GET_ACCESSTOKEN_BASE_URL);
		url.append(MimoAPIConstants.URL_KEY_CLIENT_ID
				+ MimoAPIConstants.CLIENT_ID);
		url.append(MimoAPIConstants.URL_KEY_CLIENT_SECRET
				+ MimoAPIConstants.CLIENT_SECRET);
		url.append(MimoAPIConstants.URL_KEY_REDIRECT_URL
				+ MimoAPIConstants.REDIRECT_URL);
		url.append(MimoAPIConstants.URL_KEY_CODE + p_Code);
		url.append(MimoAPIConstants.GET_ACCESSTOKEN_KEY_GRANT_TYPE);
		
		Log.d(TAG, "getAccessTokenRequest URL = " + url);
		
		return url.toString();
	}
	
	/**
	 * A function to generate the Accesstoken Request Url 
	 * 
	 * @param p_email
	 *            :takes the email id for searching criteria.
	 * 
	 * @return url : url generated for making the Search By User Email request.
	 **/
	
	public String getSearchByEmailRequestURL(String p_email)
	{
		StringBuffer url = new StringBuffer();
		
		url.append(MimoAPIConstants.GET_PROFILE_URL);
		url.append(MimoAPIConstants.SEARCH_USERNAME + p_email);
		url.append(MimoAPIConstants.ACCESS_TOKEN + this.m_token);
		
		Log.d(TAG, "SearchingRequest URL = " + url);
		return url.toString();
	}
	
}
