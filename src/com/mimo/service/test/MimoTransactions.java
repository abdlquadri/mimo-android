package com.mimo.service.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.mimo.service.api.MimoAPI;

public class MimoTransactions extends Activity
{
	TextView m_tvToken;
	MimoAPI m_api;
	
	@Override
	protected void onCreate(Bundle p_savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(p_savedInstanceState);
		setContentView(R.layout.mimo_transaction_screen);
		
		m_tvToken = (TextView) findViewById(R.id.mts_Token);
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null)
		{
			String token = extras.getString(AppConstants.KEY_TOKEN);
			m_tvToken.setText(token);
			m_api = new MimoAPI();
			m_api.setAccessToken(token);
		}
	}
}
