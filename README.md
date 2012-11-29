#android-sdk-oauth2: Android SDK for Mimo's API
=================================================================================

## Version

1.0

## Requirements
- [Android Developer License](http://developer.android.com/develop/index.html)
- [Mimo Application](https://staging.mimo.com.ng)

## Installation

- Download Eclipse 4.2 (http://www.eclipse.org/juno/)
- Import into Application



## Examples / Quick start

This API includes various usage examples, including:

* Authenticating with OAuth [MimoHttpConnection.java]
* Loads the URL and Authenticate user. [MimoOauth2Client.java]
* Transfer money and Search for the User Profile [MimoTransactions.java]


## Methods

MimoOauth2Client class:

	- (void)login(View p_view, Activity p_activity)view,activity context 
	==> loads the URL in web view to send the request on http.

	- (String)requesttoken(String p_code)code  
	==> get the access_token by passing the request URL and return the access_token.

	- (String)convertStreamToString(InputStream p_is)stream 
	==> take the Input Stream returned from the Server and convert into string.

MimoHttpConnection class:

	- HttpResponse getHttpUrlConnection(String p_url) strSearchUrl type:(UserSearchType)type callback:(HttpResponse)block  
	==> get user detail with  type e.g. by username, by email-id, by phone-no, by account no

	- HttpResponse getHttpTransferUrlConnection(String p_url) strFundUrl:(UserFundTransfer)type callback:(HttpResponse)block 
	==>get fund amount and note to transfer the money.

MimoTransactoin class:
	
	- String searchRequestParameter(String p_parameter)strSearchParameter :(String)type callback
	==> search for the profile based on the parameters passed. e.g. by username, by email-id, by phone-no, by account no.

	- String TransferFundRequest(String p_url) strTransferUrl :(String)type callback
	==>send the request URL to transfer the fund.

	-(String)convertStreamToString(InputStream p_is)stream 
	==> take the Input Stream returned from the Server and convert into string.


## Credits


MIMO Payment Services

## Support

Developer Support <developers@mimo.ng>
MIMO API <api@mimo.ng>

## References / Documentation

[https://www.mimo.com.ng/developer] (https://www.mimo.com.ng/developer)

## License 

The MIT License (MIT)

Copyright (c) 2012 MIMO Payment Services.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.


THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
