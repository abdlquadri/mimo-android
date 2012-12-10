package com.mimo.service.api;

import java.io.IOException;

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
	static Context m_context;
	
	public MimoHttpConnection(Context p_context)
	{
		m_context = p_context;
	}
	
	/**
	 * Function for getting HTTP URL connection object using get method.
	 * 
	 * @param p_url
	 *            - Http Url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @return HttpResponse- Returns the HttpResponse .
	 */
	public static synchronized HttpResponse getHttpUrlConnection(String p_url) throws ClientProtocolException, IOException // throws
																															// CustomException
	{
		
		DefaultHttpClient m_httpClient = new DefaultHttpClient();
		HttpGet m_get = new HttpGet(p_url);
		
		String m_authString =
				MimoAPIConstants.USERNAME + ":" + MimoAPIConstants.PASSWORD;
		String m_authStringEnc =
				Base64.encodeToString(m_authString.getBytes(), Base64.NO_WRAP);
		m_get.addHeader(MimoAPIConstants.HEADER_TEXT_AUTHORIZATION, MimoAPIConstants.HEADER_TEXT_BASIC + m_authStringEnc);
		HttpResponse m_response = null;
		
		try
		{
			m_response = m_httpClient.execute(m_get);
		}
		catch (IllegalStateException e)
		{
			if (MimoAPIConstants.DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		return m_response;
	}
	
	/**
	 * Function for getting HTTP URL connection object using the post method.
	 * 
	 * @param p_url
	 *            - Http Url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @return HttpResponse- Returns the HttpResponse .
	 */
	public static synchronized HttpResponse getHttpTransferUrlConnection(String p_url) throws ClientProtocolException, IOException // throws
																																	// CustomException
	{
		
		DefaultHttpClient m_httpClient = new DefaultHttpClient();
		HttpPost m_post = new HttpPost(p_url);
		
		String m_authString =
				MimoAPIConstants.USERNAME + ":" + MimoAPIConstants.PASSWORD;
		String m_authStringEnc =
				Base64.encodeToString(m_authString.getBytes(), Base64.NO_WRAP);
		m_post.addHeader(MimoAPIConstants.HEADER_TEXT_AUTHORIZATION, MimoAPIConstants.HEADER_TEXT_BASIC + m_authStringEnc);
		HttpResponse m_response = null;
		
		try
		{
			m_response = m_httpClient.execute(m_post);
		}
		catch (IllegalStateException e)
		{
			if (MimoAPIConstants.DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		return m_response;
	}
	
}