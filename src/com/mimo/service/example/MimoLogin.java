package com.mimo.service.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mimo.service.api.MimoAPIConstants;
import com.mimo.service.api.MimoOauth2Client;

public class MimoLogin extends Activity implements OnClickListener
{
	
	private Button m_btnLogin;
	private MimoOauth2Client m_oauthClient;
	
	@SuppressWarnings("unused")
	private CommonUtility m_commonUtils;
	private Context m_context;
	
	@Override
	protected void onCreate(Bundle p_savedInstanceState)
	{
		super.onCreate(p_savedInstanceState);
		setContentView(R.layout.login);
		m_context = MimoLogin.this;
		m_commonUtils = new CommonUtility(m_context);
		
		m_btnLogin = (Button) findViewById(R.id.lg_btnLogin);
		m_btnLogin.setOnClickListener(this);
		m_oauthClient = new MimoOauth2Client(MimoLogin.this);
	}
	
	@Override
	public void onClick(View p_v)
	{
		// Login Button
		if (p_v.equals(m_btnLogin))
		{
			if (MimoAPIConstants.CLIENT_ID
					.equalsIgnoreCase("<Provide Client Id Here>")
					|| MimoAPIConstants.CLIENT_SECRET
							.equalsIgnoreCase("<Provide Client Secret Here>"))
			{
				CommonUtility.showOneButtonDialog(
						getResources().getString(R.string.lbl_warning),
						getResources().getString(R.string.error_secret),
						m_context);
			}
			else
			{
				m_oauthClient.login(p_v, MimoLogin.this);
			}
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		setContentView(R.layout.login);
		
		m_btnLogin = (Button) findViewById(R.id.lg_btnLogin);
		m_btnLogin.setOnClickListener(this);
		m_oauthClient = new MimoOauth2Client(MimoLogin.this);
	}
	
	/**
	 * free up the memory by clearing the instances of the views.
	 */
	private void clearViews()
	{
		m_btnLogin = null;
		m_oauthClient = null;
		m_commonUtils = null;
		m_context = null;
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		clearViews();
	}
}
