package com.mimo.service.api;

import android.app.ProgressDialog;
import android.content.Context;

import com.mimo.service.example.R;

/**
 * Class used for display progress dialog.
 * 
 */
public class CustomProgressDialog extends ProgressDialog
{
	/**
	 * Create a new Progress dialog using the <b>p_context</b>
	 * 
	 * @param p_context
	 *            The class Context where the progress dialog is to be created
	 */
	public CustomProgressDialog(Context p_context)
	{
		super(p_context);
		setMessage(p_context.getResources().getString(R.string.ProgressViewLoading));
	}
}