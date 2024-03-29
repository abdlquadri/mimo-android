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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mimo.service.example.MimoTransactions;
import com.mimo.service.example.R;

/**
 * 
 * This class provides the method for doing the OAuth 2.0 Authentication. Also
 * it provides methods to get the Access Token from the client web site.
 * 
 */
public class MimoOauth2Client
{
	
	private static final String TAG = MimoOauth2Client.class.getName();
	
	// Get AccessToken Url
	private static final String AUTHENTICATE_RESPONSE_CODE =
			MimoAPIConstants.REDIRECT_URL + MimoAPIConstants.URL_KEY_CODE;
	
	private String m_code;
	private MimoAPI m_api;
	private Context m_context; // Context of the Calling Activity.
	
	/**
	 * initializes an instance of the MimoAPI allowing the user to login and
	 * sets the context of the Calling Activity
	 * 
	 * @param p_context
	 *            : the context of the Web
	 * 
	 **/
	public MimoOauth2Client(Context p_context)
	{
		this.m_context = p_context;
		this.m_api = new MimoAPI();
	}
	
	/**
	 * checks to see if a valid access token is available
	 * 
	 * @return YES if a valid access token is present, false otherwise
	 **/
	public boolean isAuthorized()
	{
		return m_api.hasToken();
	}
	
	/**
	 * Instantiate a webview and allows the user to login to the Api form within
	 * the application
	 * 
	 * @param p_view
	 *            : Calling view
	 * 
	 * @param p_activity
	 *            : Calling Activity reference
	 **/
	
	@SuppressLint("SetJavaScriptEnabled")
	public void login(View p_view, Activity p_activity)
	{
		
		final Activity m_activity;
		m_activity = p_activity;
		String m_url = this.m_api.getAuthUrl();
		WebView m_webview = new WebView(p_view.getContext());
		m_webview.getSettings().setJavaScriptEnabled(true);
		m_webview.setVisibility(View.VISIBLE);
		m_activity.setContentView(m_webview);
		
		m_webview.requestFocus(View.FOCUS_DOWN);
		/**
		 * Open the softkeyboard of the device to input the text in form which
		 * loads in webview.
		 */
		m_webview.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View p_v, MotionEvent p_event)
			{
				switch (p_event.getAction())
					{
						case MotionEvent.ACTION_DOWN:
						case MotionEvent.ACTION_UP:
							if (!p_v.hasFocus())
							{
								p_v.requestFocus();
							}
							break;
					}
				return false;
			}
		});
		
		/**
		 * Show the progressbar in the title of the activity untill the page
		 * loads the give url.
		 */
		m_webview.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView p_view, int p_newProgress)
			{
				((Activity) m_context).setProgress(p_newProgress * 100);
				((Activity) m_context)
						.setTitle(MimoAPIConstants.DIALOG_TEXT_LOADING);
				
				if (p_newProgress == 100)
					((Activity) m_context).setTitle(m_context
							.getString(R.string.app_name));
			}
		});
		
		m_webview.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView p_view, String p_url, Bitmap p_favicon)
			{
			}
			
			@Override
			public void onReceivedHttpAuthRequest(WebView p_view, HttpAuthHandler p_handler, String p_url, String p_realm)
			{
				p_handler.proceed(MimoAPIConstants.USERNAME,
						MimoAPIConstants.PASSWORD);
			}
			
			public void onPageFinished(WebView p_view, String p_url)
			{
				if (MimoAPIConstants.DEBUG)
				{
					Log.d(TAG, "Page Url = " + p_url);
				}
				if (p_url.contains("?code="))
				{
					if (p_url.indexOf("code=") != -1)
					{
						String[] m_urlSplit = p_url.split("=");
						
						String m_tempString1 = m_urlSplit[1];
						if (MimoAPIConstants.DEBUG)
						{
							Log.d(TAG, "TempString1 = " + m_tempString1);
						}
						String[] m_urlSplit1 = m_tempString1.split("&");
						
						String m_code = m_urlSplit1[0];
						if (MimoAPIConstants.DEBUG)
						{
							Log.d(TAG, "code = " + m_code);
						}
						MimoOauth2Client.this.m_code = m_code;
						Thread m_thread = new Thread(){
							public void run()
							{
								String m_token =
										requesttoken(MimoOauth2Client.this.m_code);
								
								Log.d(TAG, "Token = " + m_token);
								
								Intent m_navigateIntent =
										new Intent(m_activity,
												MimoTransactions.class);
								
								m_navigateIntent.putExtra(
										MimoAPIConstants.KEY_TOKEN, m_token);
								
								m_activity.startActivity(m_navigateIntent);
							}
						};
						m_thread.start();
					}
					else
					{
						if (MimoAPIConstants.DEBUG)
						{
							Log.d(TAG, "going in else");
						}
					}
				}
				else if (p_url.contains(MimoAPIConstants.URL_KEY_TOKEN))
				{
					if (p_url.indexOf(MimoAPIConstants.URL_KEY_TOKEN) != -1)
					{
						String[] m_urlSplit = p_url.split("=");
						final String m_token = m_urlSplit[1];
						
						Thread m_thread = new Thread(){
							public void run()
							{
								Intent m_navigateIntent =
										new Intent(m_activity,
												MimoTransactions.class);
								m_navigateIntent.putExtra(
										MimoAPIConstants.KEY_TOKEN, m_token);
								m_activity.startActivity(m_navigateIntent);
							}
						};
						m_thread.start();
					}
				}
			};
		});
		
		m_webview.loadUrl(m_url);
	}
	
	/**
	 * This function calls the Mimo Server along with the client info and server
	 * authenticates the client and returns a valid access_token
	 * 
	 * @param p_code
	 *            : code received from the Mimo Server
	 * 
	 * @return <b>m_token</b> : is the access token returned from the server
	 **/
	private String requesttoken(String p_code)
	{
		String m_loadUrl = m_api.requesttoken(p_code);
		
		DefaultHttpClient m_httpClient = new DefaultHttpClient();
		HttpPost m_post = new HttpPost(m_loadUrl);
		
		// String m_authString = "mimo:mimo";
		String m_authString =
				MimoAPIConstants.USERNAME + ":" + MimoAPIConstants.PASSWORD;
		
		String m_authStringEnc =
				Base64.encodeToString(m_authString.getBytes(), Base64.NO_WRAP);
		m_post.addHeader(MimoAPIConstants.HEADER_TEXT_AUTHORIZATION,
				MimoAPIConstants.HEADER_TEXT_BASIC + m_authStringEnc);
		
		HttpResponse m_response = null;
		
		String m_token = null;
		
		try
		{
			m_response = m_httpClient.execute(m_post);
			
			JSONObject m_jsonResp;
			try
			{
				m_jsonResp =
						new JSONObject(convertStreamToString(m_response
								.getEntity().getContent()));
				// m_token = m_jsonResp.getString("access_token");
				m_token =
						m_jsonResp.getString(MimoAPIConstants.GET_ACCESS_TOKEN);
				if (MimoAPIConstants.DEBUG)
				{
					Log.d(TAG + "Access Token", m_token);
				}
				return m_token;
			}
			catch (IllegalStateException p_e)
			{
				if (MimoAPIConstants.DEBUG)
				{
					Log.e(TAG, p_e.getMessage());
				}
			}
			catch (JSONException p_e)
			{
				if (MimoAPIConstants.DEBUG)
				{
					Log.e(TAG, p_e.getMessage());
				}
			}
			
		}
		catch (ClientProtocolException p_e)
		{
			if (MimoAPIConstants.DEBUG)
			{
				Log.e(TAG, p_e.getMessage());
			}
		}
		catch (IOException p_e)
		{
			if (MimoAPIConstants.DEBUG)
			{
				Log.e(TAG, p_e.getMessage());
			}
		}
		
		return "";
	}
	
	/**
	 * This function takes the Input Stream returned from the Server and convert
	 * that into String.
	 * 
	 * @param p_is
	 *            : code received from the Mimo Server
	 * 
	 * @return <b>m_sb</b> : Server response
	 **/
	
	public String convertStreamToString(InputStream p_is) throws IOException
	{
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		if (p_is != null)
		{
			
			StringBuilder m_sb = new StringBuilder();
			String m_line;
			
			try
			{
				BufferedReader m_reader =
						new BufferedReader(new InputStreamReader(p_is));
				while ((m_line = m_reader.readLine()) != null)
				{
					m_sb.append(m_line).append("\n");
				}
			}
			finally
			{
				p_is.close();
			}
			if (MimoAPIConstants.DEBUG)
			{
				Log.e(TAG, m_sb.toString());
			}
			return m_sb.toString();
		}
		else
		{
			return "";
		}
	}
}