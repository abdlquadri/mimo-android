package com.mimo.service.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mimo.service.api.MimoAPI;
import com.mimo.service.api.MimoHttpConnection;

public class MimoTransactions extends Activity 
{
	private TextView m_tvToken,m_tvDetails;
	private MimoAPI m_api;
	private Button m_btnSearch,m_btnSearchEmail,m_btnSearchPhone,m_btnSearchAccount,m_btnTransfer;
	private EditText m_etSearchParameter,m_etEmail,m_etPhone,m_etAccount,m_etNote,m_etAmount;
	private Context m_context;
	String m_sDetails,m_str,m_Url;
	CommonUtility m_commnUtils;
	MimoHttpConnection m_httpcont;
	@Override
	protected void onCreate(Bundle p_savedInstanceState)
	{
		super.onCreate(p_savedInstanceState);
		setContentView(R.layout.mimo_transaction_screen);
		m_context=MimoTransactions.this;
		m_httpcont=new MimoHttpConnection(m_context);
		m_commnUtils=new CommonUtility(m_context);
		
		m_btnSearch=(Button)findViewById(R.id.mts_btnSearch);
		m_btnSearchEmail=(Button)findViewById(R.id.mts_btnSearchEmail);
		m_btnSearchPhone=(Button)findViewById(R.id.mts_btnSearchPhone);
		m_btnSearchAccount=(Button)findViewById(R.id.mts_btnSearchAccount);
		m_btnTransfer=(Button)findViewById(R.id.mts_btnTransfer);
		
		m_etSearchParameter=(EditText)findViewById(R.id.mts_etusername);
		m_etEmail=(EditText)findViewById(R.id.mts_etEmail);
		m_etPhone=(EditText)findViewById(R.id.mts_etPhone);
		m_etAccount=(EditText)findViewById(R.id.mts_etAccountNumber);
		m_etNote=(EditText)findViewById(R.id.mts_etNote);
		m_etAmount=(EditText)findViewById(R.id.mts_etAmount);
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null)
		{
			String token = extras.getString(AppConstants.KEY_TOKEN);
			 
			m_api = new MimoAPI();
			m_api.setAccessToken(token);
		}
		
		m_btnSearch.setOnClickListener(OnClickListener);
		m_btnSearchEmail.setOnClickListener(OnClickListener);
		m_btnSearchPhone.setOnClickListener(OnClickListener);
		m_btnSearchAccount.setOnClickListener(OnClickListener);
		m_btnTransfer.setOnClickListener(OnClickListener);
	}
	
	OnClickListener OnClickListener=new OnClickListener()
	{
		
		@Override
		public void onClick(View p_v) {
			switch(p_v.getId())
			{
			case R.id.mts_btnSearch:
				CommonUtility.m_isError = false;
				CommonUtility.validateForEmptyValue(m_etSearchParameter,
						getResources().getString(R.string.error_username));
				if(!CommonUtility.m_isError)
				{
					try {
						m_Url=MimoAPI.getSearchByUsernameRequestURL(m_etSearchParameter.getText().toString());
						m_str=searchRequestParameter(m_Url);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					m_commnUtils.showOneButtonDialog(
							getResources().getString(R.string.app_name),
							m_str,m_context);
					m_str="";
					m_etSearchParameter.setText("");
				}
				break;
				
			case R.id.mts_btnSearchEmail:
				
				CommonUtility.m_isError = false;	
				CommonUtility.validateForEmptyValue(m_etEmail,
						getResources().getString(R.string.error_email));
				if(!CommonUtility.m_isError)
				{
					try {
						m_Url=MimoAPI.getSearchByEmailRequestURL(m_etEmail.getText().toString());
						m_str=searchRequestParameter(m_Url);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					m_commnUtils.showOneButtonDialog(
							getResources().getString(R.string.app_name),
							m_str,m_context);
					m_str="";
					m_etEmail.setText("");
				}
				break;
				

			case R.id.mts_btnSearchPhone:
				CommonUtility.m_isError = false;
				CommonUtility.validateForEmptyValue(m_etPhone,
						getResources().getString(R.string.error_phone));
				if(!CommonUtility.m_isError)
				{
					try {
						m_Url=MimoAPI.getSearchByPhoneRequestURL(m_etPhone.getText().toString());
						m_str=searchRequestParameter(m_Url);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					m_commnUtils.showOneButtonDialog(
							getResources().getString(R.string.app_name),
							m_str,m_context);
					m_str="";
					m_etPhone.setText("");
				}
				break;
				
				

			case R.id.mts_btnSearchAccount:
				CommonUtility.m_isError = false;
				CommonUtility.validateForEmptyValue(m_etAccount,
						getResources().getString(R.string.error_account_number));
				
				if(!CommonUtility.m_isError)
				{
					try {
						m_Url=MimoAPI.getSearchByAccountRequestURL(m_etAccount.getText().toString());
						m_str=searchRequestParameter(m_Url);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					m_commnUtils.showOneButtonDialog(
							getResources().getString(R.string.app_name),
							m_str,m_context);
					m_str="";
					m_etAccount.setText("");
				}
				break;
				
			case R.id.mts_btnTransfer:
				CommonUtility.m_isError = false;
				CommonUtility.validateForEmptyValue(m_etNote,
						getResources().getString(R.string.error_note));
			
				if(!CommonUtility.m_isError)
				{
					try {
						m_Url=MimoAPI.getTransferRequestURL(m_etNote.getText().toString(),
								Integer.parseInt(m_etAmount.getText().toString()));
						m_str=TransferFundRequest(m_Url);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					m_commnUtils.showOneButtonDialog(
							getResources().getString(R.string.app_name),
							m_str,m_context);
					m_str="";
					m_etAmount.setText("");
					m_etNote.setText("");
				}
				break;
			}
		}
	};
	
	/**
	 * Method that search for the Parameter provided as a parameter to get the details.
	 * @param p_username- contains the username
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @return Value of the searching details
	 */
	public String searchRequestParameter(String p_parameter) throws ClientProtocolException, IOException
	{
			HttpResponse m_response=null;
			m_response=MimoHttpConnection.getHttpUrlConnection(p_parameter);
		
		final	JSONObject m_jsonResp;
		try
		{
			try {
				m_jsonResp =
						new JSONObject(convertStreamToString(m_response.getEntity().getContent()));
				
			if(m_jsonResp.toString().equalsIgnoreCase("error"))
			{
				m_sDetails=m_jsonResp.getString("error");
			}
			else
			{
				m_sDetails=m_jsonResp.getString("account_number") +"\n"+
						m_jsonResp.getString("username")+"\n"+m_jsonResp.getString("surname");
			}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return m_sDetails;
	}
	
	
	/**
	 * Method that Transfer the fund to the provided url.
	 * @param p_url- contains the fund transfer url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @return m_sDetails- returns the success message.
	 */
	public String TransferFundRequest(String p_url) throws ClientProtocolException, IOException
	{
			HttpResponse m_response=null;
			m_response=MimoHttpConnection.getHttpTransferUrlConnection(p_url);
		
		final	JSONObject m_jsonResp;
		try
		{
			try {
				m_jsonResp =
						new JSONObject(convertStreamToString(m_response.getEntity().getContent()));
				
			if(m_jsonResp.toString().equalsIgnoreCase("error"))
			{
				m_sDetails=m_jsonResp.getString("error");
			}
			else
			{
				m_sDetails=m_jsonResp.getString("message");
			}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return m_sDetails;
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

	public String convertStreamToString(InputStream p_is) throws IOException {
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

			try {
				BufferedReader m_reader = new BufferedReader(
						new InputStreamReader(p_is));
				while ((m_line = m_reader.readLine()) != null) {
					m_sb.append(m_line).append("\n");
				}
			} finally {
				p_is.close();
			}
			Log.e("TAG", m_sb.toString());
			return m_sb.toString();
		} else {
			return "";
		}
	}
	
	
}
