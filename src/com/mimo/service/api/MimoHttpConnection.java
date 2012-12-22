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

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

/**
 * Class used to support all the Data Access layer methods like fetching data
 * from the server or storing/retrieving using HttpConnection.
 * 
 */
public class MimoHttpConnection {
	static Context m_context;

	private static final String TAG = MimoHttpConnection.class.getName();

	public MimoHttpConnection(Context p_context) {
		m_context = p_context;
	}

	/**
	 * Function for Making HTTP "get" request and getting server response.
	 * 
	 * @param p_url
	 *            - Http Url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @return HttpResponse- Returns the HttpResponse.
	 */
	public static synchronized HttpResponse getHttpUrlConnection(String p_url)
			throws ClientProtocolException, IOException // throws
														// CustomException
	{

		DefaultHttpClient m_httpClient = new DefaultHttpClient();
		HttpGet m_get = new HttpGet(p_url);

		String m_authString = MimoAPIConstants.USERNAME + ":"
				+ MimoAPIConstants.PASSWORD;
		String m_authStringEnc = Base64.encodeToString(m_authString.getBytes(),
				Base64.NO_WRAP);
		m_get.addHeader(MimoAPIConstants.HEADER_TEXT_AUTHORIZATION,
				MimoAPIConstants.HEADER_TEXT_BASIC + m_authStringEnc);
		HttpResponse m_response = null;

		try {
			m_response = m_httpClient.execute(m_get);
		} catch (IllegalStateException e) {
			if (MimoAPIConstants.DEBUG) {
				Log.e(TAG, e.getMessage());
			}
		}

		return m_response;
	}

	/**
	 * Function for Making HTTP "post" request and getting server response.
	 * 
	 * @param p_url
	 *            - Http Url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @return HttpResponse- Returns the HttpResponse.
	 */
	public static synchronized HttpResponse getHttpTransferUrlConnection(
			String p_url) throws ClientProtocolException, IOException // throws
																		// CustomException
	{
		
		DefaultHttpClient m_httpClient = new DefaultHttpClient();
		HttpPost m_post = new HttpPost(p_url);
		
		String m_authString = MimoAPIConstants.USERNAME + ":"
				+ MimoAPIConstants.PASSWORD;
		String m_authStringEnc = Base64.encodeToString(m_authString.getBytes(),
				Base64.NO_WRAP);
		m_post.addHeader(MimoAPIConstants.HEADER_TEXT_AUTHORIZATION,
				MimoAPIConstants.HEADER_TEXT_BASIC + m_authStringEnc);
		HttpResponse m_response = null;

		try {
			m_response = m_httpClient.execute(m_post);
		} catch (IllegalStateException e) {
			if (MimoAPIConstants.DEBUG) {
				Log.e(TAG, e.getMessage());
			}
		}

		return m_response;
	}

}