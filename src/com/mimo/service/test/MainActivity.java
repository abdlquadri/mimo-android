package com.mimo.service.test;

import com.mimo.service.api.MimoOauth2Client;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener
{
	
	Button m_btnLogin;
	MimoOauth2Client m_OauthClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		m_btnLogin = (Button) findViewById(R.id.ma_btnLogin);
		m_btnLogin.setOnClickListener(this);
		m_OauthClient = new MimoOauth2Client(MainActivity.this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public void onClick(View p_v)
	{
		// TODO Auto-generated method stub
		
		// Login Button
		if (p_v.equals(m_btnLogin))
		{
			m_OauthClient.login(p_v, MainActivity.this);
		}
	}
	
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		
		setContentView(R.layout.activity_main);
		
		m_btnLogin = (Button) findViewById(R.id.ma_btnLogin);
		m_btnLogin.setOnClickListener(this);
		m_OauthClient = new MimoOauth2Client(MainActivity.this);
	}
}
