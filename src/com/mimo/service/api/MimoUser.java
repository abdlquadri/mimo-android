package com.mimo.service.api;

public class MimoUser
{
	
	private String m_account_number;
	private String m_account_type;
	private String m_company_name;
	private String m_first_name;
	private String m_id;
	private String m_middle_name;
	private String m_surname;
	private String m_username;
	private String m_photo_url;
	private String m_email;
	private String m_level;
	
	public String getAccount_Number()
	{
		return m_account_number;
	}
	
	public void setAccount_Number(String p_accountNumber)
	{
		m_account_number = p_accountNumber;
	}
	
	public String getAccount_type()
	{
		return m_account_type;
	}
	
	public void setAccount_Type(String p_accountType)
	{
		m_account_type = p_accountType;
	}
	
	public String getCompany_Name()
	{
		return m_company_name;
	}
	
	public void setCompany_Name(String p_companyName)
	{
		m_company_name = p_companyName;
	}
	
	public String getFirst_Name()
	{
		return m_first_name;
	}
	
	public void setFirst_Name(String p_firstName)
	{
		m_first_name = p_firstName;
	}
	
	public String getID()
	{
		return m_id;
	}
	
	public void setID(String p_id)
	{
		m_id = p_id;
	}
	
	public String getMiddle_Name()
	{
		return m_middle_name;
	}
	
	public void setMiddle_Name(String p_middleName)
	{
		m_middle_name = p_middleName;
	}
	
	public String getSurname()
	{
		return m_surname;
	}
	
	public void setSurname(String p_surname)
	{
		m_surname = p_surname;
	}
	
	public String getPhoto_Url()
	{
		return m_photo_url;
	}
	
	public void setPhoto_Url(String p_photoUrl)
	{
		m_photo_url = p_photoUrl;
	}
	
	public String getEmail()
	{
		return m_email;
	}
	
	public void setEmail(String p_email)
	{
		m_email = p_email;
	}
	
	public String getLevel()
	{
		return m_level;
	}
	
	public void setLevel(String p_level)
	{
		m_level = p_level;
	}
	
}
