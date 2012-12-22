package com.mimo.service.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.mimo.service.api.CustomProgressDialog;
import com.mimo.service.api.MimoAPI;
import com.mimo.service.api.MimoAPIConstants;
import com.mimo.service.api.MimoHttpConnection;

/**
 * A class demonstrates all the API methods provide by Mimo API. Like wise
 * searching User based on UserName, Email, Account#, Phone# & most important
 * money/fund transfer service method.
 * 
 */
public class MimoTransactions extends Activity {
	private MimoAPI m_api;
	private Button m_btnSearch, m_btnSearchEmail, m_btnSearchPhone,
			m_btnSearchAccount, m_btnTransfer, m_btnRefundTransfer,
			m_btnShowProfile, m_btnShowTransfer, m_btnCancelTransfer;

	private EditText m_etSearchParameter, m_etEmail, m_etPhone, m_etAccount,
			m_etNote, m_etAmount, m_etTransactionId, m_etReFundTransId,
			m_etRefundNote, m_etRefundAmount;
	private Context m_context;
	private String m_sDetails, m_Url, m_sTransId;
	private int m_iTransId = 0;
	private MimoHttpConnection m_mimoHttpConnection;
	private CommonUtility m_commonUtils;
	private CustomProgressDialog m_customProgress;
	private InputMethodManager m_imm;
	private ViewFlipper m_vfFlipView;

	private static final String TAG = MimoTransactions.class.getName();

	@Override
	protected void onCreate(Bundle p_savedInstanceState) {
		super.onCreate(p_savedInstanceState);
		setContentView(R.layout.mimo_transaction_screen);
		m_context = MimoTransactions.this;
		m_mimoHttpConnection = new MimoHttpConnection(m_context);
		m_commonUtils = new CommonUtility(m_context);
		m_customProgress = new CustomProgressDialog(m_context);
		m_imm = (InputMethodManager) m_context
				.getSystemService(INPUT_METHOD_SERVICE);
		m_customProgress.setCancelable(false);
		m_btnSearch = (Button) findViewById(R.id.mts_btnSearch);
		m_btnSearchEmail = (Button) findViewById(R.id.mts_btnSearchEmail);
		m_btnSearchPhone = (Button) findViewById(R.id.mts_btnSearchPhone);
		m_btnSearchAccount = (Button) findViewById(R.id.mts_btnSearchAccount);
		m_btnTransfer = (Button) findViewById(R.id.mts_btnTransfer);
		m_btnRefundTransfer = (Button) findViewById(R.id.mts_btnreFundTransfer);
		m_btnShowProfile = (Button) findViewById(R.id.mts_btnProfile);
		m_btnShowTransfer = (Button) findViewById(R.id.mts_btnTransferTitle);
		m_btnCancelTransfer = (Button) findViewById(R.id.mts_btnCancelTransfer);

		m_etSearchParameter = (EditText) findViewById(R.id.mts_etusername);
		m_etEmail = (EditText) findViewById(R.id.mts_etEmail);
		m_etPhone = (EditText) findViewById(R.id.mts_etPhone);
		m_etAccount = (EditText) findViewById(R.id.mts_etAccountNumber);
		m_etNote = (EditText) findViewById(R.id.mts_etNote);
		m_etAmount = (EditText) findViewById(R.id.mts_etAmount);
		m_etTransactionId = (EditText) findViewById(R.id.mts_etTransactionId);
		m_etReFundTransId = (EditText) findViewById(R.id.mts_etReFundTransId);
		m_etRefundNote = (EditText) findViewById(R.id.mts_etRefundNote);
		m_etRefundAmount = (EditText) findViewById(R.id.mts_etRefundAmount);

		m_vfFlipView = (ViewFlipper) findViewById(R.id.mts_vfflipper);
		Bundle m_extras = getIntent().getExtras();

		if (m_extras != null) {
			String m_token = m_extras.getString(MimoAPIConstants.KEY_TOKEN);

			m_api = new MimoAPI();
			m_api.settoken(m_token);
		}

		m_btnSearch.setOnClickListener(OnClickListener);
		m_btnSearchEmail.setOnClickListener(OnClickListener);
		m_btnSearchPhone.setOnClickListener(OnClickListener);
		m_btnSearchAccount.setOnClickListener(OnClickListener);
		m_btnTransfer.setOnClickListener(OnClickListener);
		m_btnRefundTransfer.setOnClickListener(OnClickListener);
		m_vfFlipView.setOnClickListener(OnClickListener);
		m_btnShowProfile.setOnClickListener(OnClickListener);
		m_btnShowTransfer.setOnClickListener(OnClickListener);
		m_btnCancelTransfer.setOnClickListener(OnClickListener);
	}

	/**
	 * Common click listener
	 */
	OnClickListener OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View p_v) {
			switch (p_v.getId()) 
			{
			//Search By User name Click Listener.
			case R.id.mts_btnSearch:

				m_imm.hideSoftInputFromWindow(m_btnSearch.getWindowToken(), 0);
				CommonUtility.m_isError = false;
				CommonUtility.validateForEmptyValue(m_etSearchParameter,
						getResources().getString(R.string.error_username));
				if (!CommonUtility.m_isError) {
					try {
						m_Url = MimoAPI
								.getSearchByUsernameRequestURL(m_etSearchParameter
										.getText().toString());
						search1(m_Url);
					} catch (ClientProtocolException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					} catch (IOException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					}

					m_etSearchParameter.setText("");
				}
				break;
				
				//Search By Email address Click Listener.
			case R.id.mts_btnSearchEmail:
				m_imm.hideSoftInputFromWindow(
						m_btnSearchEmail.getWindowToken(), 0);
				CommonUtility.m_isError = false;
				CommonUtility.validateForEmptyValue(m_etEmail, getResources()
						.getString(R.string.error_email));
				if (!CommonUtility.m_isError) {
					try {
						m_Url = MimoAPI.getSearchByEmailRequestURL(m_etEmail
								.getText().toString());
						search1(m_Url);
					} catch (ClientProtocolException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					} catch (IOException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					}

					m_etEmail.setText("");
				}
				break;
				//Search By Phone Number Click Listener.
			case R.id.mts_btnSearchPhone:
				m_imm.hideSoftInputFromWindow(
						m_btnSearchPhone.getWindowToken(), 0);
				CommonUtility.m_isError = false;
				CommonUtility.validateForEmptyValue(m_etPhone, getResources()
						.getString(R.string.error_phone));
				if (!CommonUtility.m_isError) {
					try {
						m_Url = MimoAPI.getSearchByPhoneRequestURL(m_etPhone
								.getText().toString());
						search1(m_Url);
					} catch (ClientProtocolException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					} catch (IOException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					}

					m_etPhone.setText("");
				}
				break;
				//Search By Account Number Click Listener.
			case R.id.mts_btnSearchAccount:
				m_imm.hideSoftInputFromWindow(
						m_btnSearchAccount.getWindowToken(), 0);
				CommonUtility.m_isError = false;
				CommonUtility.validateForEmptyValue(m_etAccount, getResources()
						.getString(R.string.error_account_number));

				if (!CommonUtility.m_isError) {
					try {
						m_Url = MimoAPI
								.getSearchByAccountRequestURL(m_etAccount
										.getText().toString());
						search1(m_Url);
					} catch (ClientProtocolException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					} catch (IOException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					}

					m_etAccount.setText("");
				}
				break;

				//Fund Transfer click listener.
			case R.id.mts_btnTransfer:
				m_imm.hideSoftInputFromWindow(m_btnTransfer.getWindowToken(), 0);
				CommonUtility.m_isError = false;
				CommonUtility.validateForEmptyValue(m_etNote, getResources()
						.getString(R.string.error_note));

				CommonUtility.validateForEmptyValue(m_etAmount, getResources()
						.getString(R.string.error_amount));
				if (!CommonUtility.m_isError) {
					try {
						m_Url = MimoAPI.getTransferRequestURL(m_etNote
								.getText().toString(), Integer
								.parseInt(m_etAmount.getText().toString()));
						transaction(m_Url);
					} catch (ClientProtocolException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					} catch (IOException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					}

					m_etAmount.setText("");
					m_etNote.setText("");
				}
				break;

				//Refund Trasnfer ClickListener.
			case R.id.mts_btnreFundTransfer:

				m_imm.hideSoftInputFromWindow(
						m_btnRefundTransfer.getWindowToken(), 0);
				CommonUtility.m_isError = false;
				CommonUtility.validateForEmptyValue(m_etRefundNote,
						getResources().getString(R.string.error_note));

				CommonUtility.validateForEmptyValue(m_etRefundAmount,
						getResources().getString(R.string.error_amount));
				CommonUtility.validateForEmptyValue(m_etReFundTransId,
						getResources().getString(R.string.error_id));

				if (!CommonUtility.m_isError) {
					try {
						m_Url = MimoAPI.getRefundTransferUrl(m_etRefundNote.getText()
								.toString(),Integer.parseInt(m_etRefundAmount
								.getText().toString()), Integer
								.parseInt(m_etReFundTransId.getText()
										.toString()));
						transaction(m_Url);
					} catch (ClientProtocolException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					} catch (IOException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					}

					m_etAmount.setText("");
					m_etNote.setText("");
				}
				break;

				//Switch on the Profile view on click of the Profile button.
			case R.id.mts_btnProfile:
				m_vfFlipView.setDisplayedChild(0);

				break;
				//Switch on the Transfer view on click of the Trasfer button.
			case R.id.mts_btnTransferTitle:
				m_vfFlipView.setDisplayedChild(1);

				break;
				//Cancel the Fund Transfer.
			case R.id.mts_btnCancelTransfer:
				CommonUtility.validateForEmptyValue(m_etTransactionId,
						getResources().getString(R.string.error_id));
				if (!CommonUtility.m_isError) {
					try {
						m_Url = MimoAPI.getCancelTransferUrl(Integer
								.parseInt(m_etTransactionId.getText()
										.toString()));
						transaction(m_Url);
					} catch (ClientProtocolException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					} catch (IOException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					}
					m_etTransactionId.setText("");
				}
				break;
			}
		}
	};

	/**
	 * Method that search for the Parameter provided as a parameter to get the
	 * details.
	 * 
	 * @param p_parameter
	 *            - contains the searching parameter
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void search1(final String p_parameter)
			throws ClientProtocolException, IOException {
		class FetchData extends AsyncTask {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				m_customProgress.show();
			}

			@Override
			protected String doInBackground(Object... p_params) {
				HttpResponse m_response = null;
				final JSONObject m_jsonResp;
				try {
					m_response = MimoHttpConnection
							.getHttpUrlConnection(p_parameter);
					try {
						m_jsonResp = new JSONObject(
								convertStreamToString(m_response.getEntity()
										.getContent()));
						if (m_jsonResp.toString().contains(
								MimoAPIConstants.KEY_ERROR)) {
							m_sDetails = m_jsonResp
									.getString(MimoAPIConstants.KEY_ERROR);
						} else {
							m_sDetails = m_jsonResp
									.getString(MimoAPIConstants.KEY_ACCOUNT_NUMBER)
									+ "\n"
									+ m_jsonResp
											.getString(MimoAPIConstants.KEY_USERNAME)
									+ "\n"
									+ m_jsonResp
											.getString(MimoAPIConstants.KEY_SURNAME);
						}
					} catch (IllegalStateException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					} catch (JSONException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					}
				} catch (ClientProtocolException e) {
					if (MimoAPIConstants.DEBUG) {
						Log.e(TAG, e.getMessage());
					}
				} catch (IOException e) {
					if (MimoAPIConstants.DEBUG) {
						Log.e(TAG, e.getMessage());
					}
				}

				return m_sDetails;
			}

			@Override
			protected void onPostExecute(Object p_result) {
				super.onPostExecute(p_result);
				m_customProgress.dismiss();
				m_commonUtils.showOneButtonDialog(
						getResources().getString(R.string.app_name),
						m_sDetails, m_context);
				m_sDetails = "";
			}
		}
		new FetchData().execute(null);
	}

	/**
	 * Method that is used to call "Fund Transfer, Cancel Transfer & Re-Fund Transfer API Calls.
	 * 
	 * @param p_url
	 *            - contains the fund transfer url
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void transaction(final String p_url) throws ClientProtocolException,
			IOException {
		class TransactionData extends AsyncTask {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				m_customProgress.show();
			}

			@Override
			protected String doInBackground(Object... p_params) {
				HttpResponse m_response = null;

				final JSONObject m_jsonResp;
				try {
					m_response = MimoHttpConnection
							.getHttpTransferUrlConnection(p_url);
					try {
						m_jsonResp = new JSONObject(
								convertStreamToString(m_response.getEntity()
										.getContent()));

						if (m_jsonResp.toString().contains(
								MimoAPIConstants.KEY_ERROR)) {
							m_sDetails = m_jsonResp
									.getString(MimoAPIConstants.KEY_ERROR);
						} else {
							m_sDetails = m_jsonResp
									.getString(MimoAPIConstants.KEY_MESSAGE);

							m_iTransId = Integer
									.parseInt(m_jsonResp
											.getString(MimoAPIConstants.KEY_TRANSACTION_ID));

							if (MimoAPIConstants.DEBUG) {
								System.err.println("Transaction Id---->"
										+ m_iTransId);
							}
						}
					} catch (IllegalStateException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					} catch (JSONException e) {
						if (MimoAPIConstants.DEBUG) {
							Log.e(TAG, e.getMessage());
						}
					}
				} catch (ClientProtocolException e) {
					if (MimoAPIConstants.DEBUG) {
						Log.e(TAG,""+e.getMessage());
					}
				} catch (IOException e) {
					if (MimoAPIConstants.DEBUG) {
						Log.e(TAG,""+e.getMessage());
					}
				}

				return m_sDetails;
			}

			@Override
			protected void onPostExecute(Object p_result) {
				super.onPostExecute(p_result);
				m_customProgress.dismiss();
				m_commonUtils.showOneButtonDialog(
						getResources().getString(R.string.app_name),
						m_sDetails, m_context);
				m_etTransactionId.setText(String.valueOf(m_iTransId));
				m_sDetails = "";
			}
		}
		new TransactionData().execute(null);

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
		if (p_is != null) {
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
			if (MimoAPIConstants.DEBUG) {
				Log.e("TAG", m_sb.toString());
			}
			return m_sb.toString();
		} else {
			return "";
		}
	}

	/**
	 * free up the memory by clearing the instances of the views.
	 */
	private void clearViews() {
		m_btnSearch = null;
		m_btnSearchEmail = null;
		m_btnSearchPhone = null;
		m_btnSearchAccount = null;
		m_btnTransfer = null;
		m_etSearchParameter = null;
		m_etEmail = null;
		m_etPhone = null;
		m_etAccount = null;
		m_etNote = null;
		m_etAmount = null;
		m_context = null;
		m_sDetails = null;
		m_Url = null;
		m_commonUtils = null;
		m_customProgress = null;
		m_imm = null;
	}

	/**
	 * default method called with the Activity is closed.
	 */

	@Override
	protected void onDestroy() {
		super.onDestroy();
		clearViews();
	}

}
