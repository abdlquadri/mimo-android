/**
* MIMO REST API Library for ANDROID
*
* MIT LICENSE
*
* Permission is hereby granted, free of charge, to any person obtaining
* a copy of this software and associated documentation files (the
* "Software"), to deal in the Software without restriction, including
* without limitation the rights to use, copy, modify, merge, publish,
* distribute, sublicense, and/or sell copies of the Software, and to
* permit persons to whom the Software is furnished to do so, subject to
* the following conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
* LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
* OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
* WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
* @package   MIMO
* @copyright Copyright (c) 2012 Mimo Inc. (http://www.mimo.com.ng)
* @license   http://opensource.org/licenses/MIT MIT
* @version   1.2.6
* @link      http://www.mimo.com.ng
*/


package com.mimo.service.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

/**
 * This class generates the URLs for various requests i.e. Search ,request token
 * etc. Also manages the Value of Access token received form the web site. this
 * token is used for making other transactions requests like searching user and
 * fund transfer etc.
 * 
 */
public class MimoAPI
{
	private static final String TAG = MimoAPI.class.getName();
	
	private static String m_token; // Access Token
	
	public MimoAPI()
	{
		MimoAPI.m_token = "";
	}
	
	/**
	 * checks to see if the user has a valid access token
	 * 
	 * @return YES if a valid access token is present, false otherwise
	 **/
	public boolean hasToken()
	{
		if (MimoAPI.m_token.equals(""))
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
	public String gettoken()
	{
		return m_token;
	}
	
	/**
	 * sets the access token
	 * 
	 * @param p_token
	 *            the string representing the token
	 **/
	public void settoken(String p_token)
	{
		MimoAPI.m_token = p_token;
	}
	
	/**
	 * clears the access token
	 **/
	public void clearAccessToken()
	{
		MimoAPI.m_token = null;
	}
	
	/**
	 * A function to generate the Authentication Request Url which is to be
	 * opened in the webview
	 * 
	 * @return url : url generated for making the Authentication request.
	 **/
	
	public String getAuthUrl()
	{
		StringBuffer m_url = new StringBuffer();
		
		m_url.append(MimoAPIConstants.AUTHENTICATE_BASE_URL);
		m_url.append(MimoAPIConstants.URL_KEY_CLIENT_ID
				+ MimoAPIConstants.CLIENT_ID);
		m_url.append(MimoAPIConstants.URL_KEY_REDIRECT_URL
				+ MimoAPIConstants.REDIRECT_URL);
		m_url.append(MimoAPIConstants.AUTHENTICATE_KEY_RESPONSE_TYPE);
		if (MimoAPIConstants.DEBUG)
		{
			Log.d(TAG, "AuthenticationRequest URL = " + m_url);
		}
		return m_url.toString();
	}
	
	/**
	 * A function to generate the Accesstoken Request Url
	 * 
	 * @param p_Code
	 *            :the code received from the application.
	 * 
	 * @return url : url generated for making the Accesstoken request.
	 **/
	public String requesttoken(String p_Code)
	{
		StringBuffer m_url = new StringBuffer();
		
		m_url.append(MimoAPIConstants.GET_ACCESSTOKEN_BASE_URL);
		m_url.append(MimoAPIConstants.URL_KEY_CLIENT_ID
				+ MimoAPIConstants.CLIENT_ID);
		m_url.append(MimoAPIConstants.URL_KEY_CLIENT_SECRET
				+ MimoAPIConstants.CLIENT_SECRET);
		m_url.append(MimoAPIConstants.URL_KEY_REDIRECT_URL
				+ MimoAPIConstants.REDIRECT_URL);
		m_url.append(MimoAPIConstants.URL_KEY_CODE + p_Code);
		m_url.append(MimoAPIConstants.GET_ACCESSTOKEN_KEY_GRANT_TYPE);
		if (MimoAPIConstants.DEBUG)
		{
			Log.d(TAG, "getAccessTokenRequest URL = " + m_url);
		}
		return m_url.toString();
	}
	
	/**
	 * A function to generate the Search by UserName Request Url
	 * 
	 * @param p_username
	 *            :takes the username for searching criteria.
	 * 
	 * @return url : url generated for making the Search By UserName request.
	 **/
	
	public static String getSearchByUsernameRequestURL(String p_username)
	{
		StringBuffer m_url = new StringBuffer();
		
		m_url.append(MimoAPIConstants.GET_PROFILE_URL);
		try
		{
			m_url.append(MimoAPIConstants.SEARCH_USERNAME
					+ URLEncoder.encode(p_username, "utf-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			m_url.append(MimoAPIConstants.SEARCH_USERNAME + p_username);
		}
		m_url.append(MimoAPIConstants.ACCESS_TOKEN + m_token);
		if (MimoAPIConstants.DEBUG)
		{
			Log.d(TAG, "SearchingRequest URL = " + m_url);
		}
		return m_url.toString();
	}
	
	/**
	 * A function to generate the Search By User Email Request Url
	 * 
	 * @param p_email
	 *            :takes the email id for searching criteria.
	 * 
	 * @return url : url generated for making the Search By User Email request.
	 **/
	
	public static String getSearchByEmailRequestURL(String p_email)
	{
		StringBuffer m_url = new StringBuffer();
		
		m_url.append(MimoAPIConstants.GET_PROFILE_URL);
		try
		{
			m_url.append(MimoAPIConstants.SEARCH_EMAIL
					+ URLEncoder.encode(p_email, "utf-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			m_url.append(MimoAPIConstants.SEARCH_EMAIL + p_email);
		}
		m_url.append(MimoAPIConstants.ACCESS_TOKEN + m_token);
		if (MimoAPIConstants.DEBUG)
		{
			Log.d(TAG, "SearchingRequest URL = " + m_url);
		}
		return m_url.toString();
	}
	
	/**
	 * A function to generate the Search By User phone# Request Url
	 * 
	 * @param p_phone
	 *            :takes the phone for searching criteria.
	 * 
	 * @return url : url generated for making the Search By User phone# request.
	 **/
	
	public static String getSearchByPhoneRequestURL(String p_phone)
	{
		StringBuffer m_url = new StringBuffer();
		
		m_url.append(MimoAPIConstants.GET_PROFILE_URL);
		try
		{
			m_url.append(MimoAPIConstants.SEARCH_PHONE
					+ URLEncoder.encode(p_phone, "utf-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			m_url.append(MimoAPIConstants.SEARCH_PHONE + p_phone);
		}
		m_url.append(MimoAPIConstants.ACCESS_TOKEN + m_token);
		
		if (MimoAPIConstants.DEBUG)
		{
			Log.d(TAG, "SearchingRequest URL = " + m_url);
		}
		return m_url.toString();
	}
	
	/**
	 * A function to generate the Search By User account number Request Url
	 * 
	 * @param p_account
	 *            :takes the account number for searching criteria.
	 * 
	 * @return url : url generated for making the Search By User account number
	 *         request.
	 **/
	
	public static String getSearchByAccountRequestURL(String p_account)
	{
		StringBuffer m_url = new StringBuffer();
		
		m_url.append(MimoAPIConstants.GET_PROFILE_URL);
		try
		{
			m_url.append(MimoAPIConstants.SEARCH_ACCOUNT_NUMBER
					+ URLEncoder.encode(p_account, "utf-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			m_url.append(MimoAPIConstants.SEARCH_ACCOUNT_NUMBER + p_account);
		}
		m_url.append(MimoAPIConstants.ACCESS_TOKEN + m_token);
		if (MimoAPIConstants.DEBUG)
		{
			Log.d(TAG, "SearchingRequest URL = " + m_url);
		}
		return m_url.toString();
	}
	
	/**
	 * A function to generate the Fund Transfer Request Url
	 * 
	 * @param p_amount
	 *            :takes the amount which is to be transfered.
	 * @param p_notes 
	 * 				:takes the notes.
	 * @return url : url generated for making the Fund Transfer Request.
	 * 
	 **/
	
	public static String getTransferRequestURL(String p_notes, int p_amount)
	{
		StringBuffer m_url = new StringBuffer();
		
		m_url.append(MimoAPIConstants.GET_TRANSFER_URL);
		m_url.append(MimoAPIConstants.TRANSFER_ACCESS_TOKEN + m_token);
		try
		{
			m_url.append(MimoAPIConstants.TRANSFER_NOTES
					+ URLEncoder.encode(p_notes, "utf-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e(TAG, e.getMessage());
			m_url.append(MimoAPIConstants.TRANSFER_NOTES + p_notes);
		}
		
		m_url.append(MimoAPIConstants.TRANSFER_AMOUNT + p_amount);
		
		if (MimoAPIConstants.DEBUG)
		{
			Log.d(TAG, "TransferRequest URL = " + m_url);
		}
		return m_url.toString();
	}
	
	/**
	 * A function to generate the Re-Fund Transfer Request Url
	 * 
	 * @param p_amount
	 *            :takes the amount to be refunded.
	 * @param p_notest 
	 * 				:takes the notes.
	 * @param p_transId
	 * 				: takes the transaction id of any transaction.
	 * @return url : url generated for making the Re-Fund Transfer Request.
	 * 
	 **/
	public static String getRefundTransferUrl(String p_notes, int p_amount,int p_transId)
	{
		StringBuffer m_url = new StringBuffer();
		
		m_url.append(MimoAPIConstants.REFUND_TRANSFER_URL);
		m_url.append(MimoAPIConstants.TRANSFER_ACCESS_TOKEN + m_token);
		try
		{
			m_url.append(MimoAPIConstants.TRANSFER_NOTES
					+ URLEncoder.encode(p_notes, "utf-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e(TAG, e.getMessage());
			m_url.append(MimoAPIConstants.TRANSFER_NOTES + p_notes);
		}
		
		m_url.append(MimoAPIConstants.TRANSFER_AMOUNT + p_amount);
		m_url.append(MimoAPIConstants.REFUND_TRANSFER_TRANSACTION_ID + p_transId);
		
		if (MimoAPIConstants.DEBUG)
		{
			Log.d(TAG, "Re-Fund TransferRequest URL = " + m_url);
		}
		return m_url.toString();
	}
	
	/**
	 * A function to generate the Url to cancel the Transfer.
	 * 
	 * @param p_transId
	 * 				: takes the transaction id of any transaction.
	 * @return url : url generated for making the Cancel Transfer Request.
	 * 
	 **/
	public static String getCancelTransferUrl(int p_transId)
	{
		StringBuffer m_url = new StringBuffer();
		
		m_url.append(MimoAPIConstants.VOID_URL);
		m_url.append(MimoAPIConstants.TRANSFER_ACCESS_TOKEN + m_token);
		m_url.append(MimoAPIConstants.REFUND_TRANSFER_TRANSACTION_ID + p_transId);
		
		if (MimoAPIConstants.DEBUG)
		{
			Log.d(TAG, "Void TransferRequest URL = " + m_url);
		}
		return m_url.toString();
	}
}
