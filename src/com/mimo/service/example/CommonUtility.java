package com.mimo.service.example;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.mimo.service.api.MimoAPIConstants;

/**
 * Common class which contains common utility functions to be used in
 * application.
 * 
 */
public class CommonUtility
{
	
	public static AlertDialog m_alertDialog;
	public static AlertDialog.Builder m_builder;
	public static boolean m_isError = false;
	
	private static final String TAG = CommonUtility.class.getName();
	
	public CommonUtility(Context p_context)
	{
		m_builder = new AlertDialog.Builder(p_context);
	}
	
	/**
	 * This is common method to display alert dialog with one
	 * button("Ok button")
	 * 
	 * @param p_title
	 *            : is the title to be set in the Alert Dialog.
	 * @param p_mesg
	 *            : is the Message String to be set in the Alert Dialog.
	 * @param p_context
	 *            : is the Context of the Activity From which the Dialog is
	 *            requested.
	 */
	public static void showOneButtonDialog(String p_title, String p_mesg, final Context p_context)
	{
		m_builder.setTitle(p_title);
		m_builder.setMessage(p_mesg);
		m_builder.setCancelable(false);
		m_builder.setPositiveButton(
				p_context.getResources().getString(R.string.lbl_ok),
				new DialogInterface.OnClickListener(){
					
					@Override
					public void onClick(DialogInterface p_dialog, int p_which)
					{
					}
				});
		
		m_alertDialog = m_builder.create();
		m_alertDialog.show();
	}
	
	/**
	 * Checks <code>p_editText</code> is empty or not. If empty then set error
	 * message for <code>p_editText</code>
	 * 
	 * @param p_editText
	 *            {@link EditText} to validate
	 * @param p_nullMsg
	 *            message to be display when field is empty
	 * @throws Throwable
	 *             when any exception occurs during validation
	 */
	public static void validateForEmptyValue(EditText p_editText, String p_nullMsg)
	{
		try
		{
			if (p_editText != null && p_nullMsg != null)
			{
				// use trim() while checking for blank values
				if (TextUtils.isEmpty(p_editText.getText().toString().trim()))
				{
					m_isError = true;
					p_editText.setError(p_nullMsg);
				}
			}
		}
		catch (Throwable e)
		{
			if (MimoAPIConstants.DEBUG)
			{
				Log.e(TAG, e.getMessage());
			}
		}
	}
}