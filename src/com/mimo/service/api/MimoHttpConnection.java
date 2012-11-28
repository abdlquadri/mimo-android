package com.mimo.service.api;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Base64;

/**
 * Class used to support all the Data Access layer methods like fetching data
 * from the server or storing/retrieving using HttpConnection<br>
 * data from the local SQLLite server.
 * 
 */
public class MimoHttpConnection
{
	private static String TAG = MimoHttpConnection.class.getSimpleName();
	private static HttpURLConnection m_httpConnection;
	private static HttpResponse m_httpResponse;
	static Context m_context;
	public MimoHttpConnection(Context p_context)
	{
		m_context=p_context;
	}
	/**
	 * Function for getting HTTP URL connection object.
	 * 
	 * @param p_url
	 *            - Http Url
	 * @return - HttpURLConnection object.
	 * @throws ClientProtocolException 
	 * @throws IOException
	 */
	public static synchronized HttpResponse getHttpUrlConnection(String p_url) throws ClientProtocolException, IOException //throws CustomException
	{

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet get = new HttpGet(p_url);
				
				String authString = "mimo:mimo";
				String authStringEnc = Base64.encodeToString(authString.getBytes(),Base64.NO_WRAP);	
				get.addHeader("Authorization", "Basic " + authStringEnc);		
				HttpResponse response = null;
				
				try
				{
					response = httpClient.execute(get);
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
			
		return response;
	}

	/**
	 * Function for getting HTTP URL connection object.
	 * 
	 * @param p_url
	 *            - Http Url
	 * @return - HttpURLConnection object.
	 * @throws ClientProtocolException 
	 * @throws IOException
	 */
	public static synchronized HttpResponse getHttpTransferUrlConnection(String p_url) throws ClientProtocolException, IOException //throws CustomException
	{

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost post = new HttpPost(p_url);
				
				String authString = "mimo:mimo";
				String authStringEnc = Base64.encodeToString(authString.getBytes(),Base64.NO_WRAP);	
				post.addHeader("Authorization", "Basic " + authStringEnc);		
				HttpResponse response = null;
				
				try
				{
					response = httpClient.execute(post);
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
			
		return response;
	}
	
}