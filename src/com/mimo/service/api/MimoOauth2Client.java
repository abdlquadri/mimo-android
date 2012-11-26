package com.mimo.service.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mimo.service.test.AppConstants;
import com.mimo.service.test.MimoTransactions;
import com.mimo.service.test.R;

public class MimoOauth2Client
{
	
	private static final String TAG = MimoOauth2Client.class.getName();
	
	// Get AccessToken Url
	private static final String AUTHENTICATE_RESPONSE_CODE =
			MimoAPIConstants.REDIRECT_URL + MimoAPIConstants.URL_KEY_CODE;
	
	private String m_Code;
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
	 * @param p_view : Calling view
	 * 
	 * @param p_activity : Calling Activity reference  
	 **/
	
	public void login(View p_view, Activity p_activity)
	{
		
		final Activity activity;
		activity = p_activity;
		String url = this.m_api.getAuthenticationRequestURL();
		WebView m_webview = new WebView(p_view.getContext());
		m_webview.getSettings().setJavaScriptEnabled(true);
		m_webview.setVisibility(View.VISIBLE);
		activity.setContentView(m_webview);
		
		m_webview.requestFocus(View.FOCUS_DOWN);
		m_webview.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch (event.getAction())
					{
						case MotionEvent.ACTION_DOWN:
						case MotionEvent.ACTION_UP:
							if (!v.hasFocus())
							{
								v.requestFocus();
							}
							break;
					}
				return false;
			}
		});
		
		m_webview.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView p_view, int p_newProgress)
			{
				((Activity) m_context).setProgress(p_newProgress * 100);
				((Activity) m_context).setTitle("Loading...");
				
				if (p_newProgress == 100)
					((Activity) m_context).setTitle(m_context
							.getString(R.string.app_name));
			}
		});
		
		m_webview.setWebViewClient(new WebViewClient(){
			
			@Override
			public void onPageStarted(WebView _view, String url, Bitmap favicon)
			{
				Log.d(TAG, "Page Url = " + url);
				
				if (url.contains("?code="))
				{
					if (url.indexOf("code=") != -1)
					{
						String[] urlSplit = url.split("=");
						
						String TempString1 = urlSplit[1];
						
						Log.d(TAG, "TempString1 = " + TempString1);
						
						String[] urlSplit1 = TempString1.split("&");
						
						String code = urlSplit1[0];
						
						Log.d(TAG, "code = " + code);
						
						MimoOauth2Client.this.m_Code = code;
						Thread thread = new Thread(){
							public void run()
							{
								String token =
										requestAccessToken(MimoOauth2Client.this.m_Code);
								Intent NavigateIntent =
										new Intent(activity,
												MimoTransactions.class);
								NavigateIntent.putExtra(AppConstants.KEY_TOKEN,
										token);
								activity.startActivity(NavigateIntent);
							}
						};
						thread.start();
					}
					else
					{
						Log.d(TAG, "going in else");
					}
				}
				else if (url.contains(MimoAPIConstants.URL_KEY_TOKEN))
				{
					if (url.indexOf(MimoAPIConstants.URL_KEY_TOKEN) != -1)
					{
						String[] urlSplit = url.split("=");
						final String token = urlSplit[1];
						
						Thread thread = new Thread(){
							public void run()
							{
								Intent NavigateIntent =
										new Intent(activity,
												MimoTransactions.class);
								NavigateIntent.putExtra(AppConstants.KEY_TOKEN,
										token);
								activity.startActivity(NavigateIntent);
							}
						};
						thread.start();
					}
				}
			}
			
			@Override
			public void onReceivedHttpAuthRequest(WebView p_view, HttpAuthHandler p_handler, String p_url, String p_realm)
			{
				p_handler.proceed("mimo", "mimo");
			}
		});
		
		m_webview.loadUrl(url);
	}
	
	
	
	/**
	 * This function calls the Mimo Server along with the client info and server authenticates the client and returns a valid access_token
	 * @param p_code : code received from the Mimo Server
	 * 
	 * @return <b>m_token</b> : is the access token returned from the server
	 **/
	private String requestAccessToken(String p_code)
	{
		String token = null;
		
		String m_loadUrl = m_api.getAccessTokenRequestURL(p_code);
		
		String m_token = null;
		
		try
		{
			URL m_requestUrl = new URL(m_loadUrl);
			try
			{
				HttpsURLConnection m_httpConnt =
						(HttpsURLConnection) m_requestUrl.openConnection();
				// m_httpConnt.setRequestMethod("POST");
				// m_httpConnt.setRequestProperty("Content-Type",
				// "application/json");
				// m_httpConnt.setConnectTimeout(20000);
				// m_httpConnt.setDoOutput(true);
				// m_httpConnt.connect();
				
				if (m_httpConnt.getResponseCode() != 200)
				{
					if (m_httpConnt.getResponseCode() == 400)
					{
						Log.d(TAG, "API Response Code is 400");
						String m_errorStream =
								convertStreamToString(m_httpConnt
										.getErrorStream());
						Log.d(TAG, "Parse the error :" + m_errorStream);
					}
					else
					{
						Log.d(TAG, "API Response Code is not 200 or 400");
					}
				}
				else
				{
					
//					InputStreamReader m_insReader =
//							new InputStreamReader(m_httpConnt.getInputStream());					
//					BufferedReader m_bufRead =
//							new BufferedReader(m_insReader, 2048);//					
//					StringBuilder m_sb = new StringBuilder();
//					String m_cur;
//					while ((m_cur = m_bufRead.readLine()) != null)
//						m_sb.append(m_cur + "\n");
//					m_insReader.close();
					
					JSONObject m_jsonResp = new JSONObject(convertStreamToString(m_httpConnt.getInputStream()));
					
//					m_jsonResp = new JSONObject(m_sb.toString());
					m_token = m_jsonResp.getString("access_token");
				}
				
				return m_token;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * This function takes the Input Stream returned from the Server and convert that into String.
	 * @param p_is : code received from the Mimo Server
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
			return m_sb.toString();
		}
		else
		{
			return "";
		}
	}
	
}