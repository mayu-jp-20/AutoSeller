package com.example.demo.api.spapi;

import java.io.IOException;

import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentials;
import com.amazon.SellingPartnerAPIAA.LWAAuthorizationCredentials;

import io.swagger.client.ApiException;
import io.swagger.client.api.CatalogApi;
import io.swagger.client.model.GetCatalogItemResponse;

public class Catalogitem {

	//The SP-API endpoint
	private static final String sellingPartnerAPIEndpoint="https://sellingpartnerapi-na.amazon.com";

	//AWS認証情報の設定
	private static final AWSAuthenticationCredentials
	awsAuthenticationCredentials = AWSAuthenticationCredentials.builder()
	  .accessKeyId("myAccessKeyId")//TODO aws_access_key(ロールARNをアプリに登録しているなら、ロールのキーを
	  .secretKey("mySecretId")//TODO aws_secret_key（ユーザーURNをアプリに登録しているなら、、ユーザーのキーを
	  .region("us-east-")//TODO aws_region
	  .build();

	//IAM Userで登録してるなら、これは除いた方が良い
	//LWA認証情報プロバイダーの設定
	/*private static final AWSAuthenticationCredentialsProvider
	awsAuthenticationCredentialsProvider = AWSAuthenticationCredentialsProvider.builder()
			  .roleArn("myroleARN")
			  .roleSessionName("myrolesessioname")//session_name
			  .build();*/

	//LWA認証情報の設定(認可不要の場合もある）
	private static final LWAAuthorizationCredentials
	lwaAuthorizationCredentials = LWAAuthorizationCredentials.builder()
			  .clientId("myClientId")//TODO application_client_id
			  .clientSecret("myClientSecret")//TODO application_client_secret
			  .refreshToken("Aztr|...")
			  .endpoint("https://api.amazon.com/auth/o2/token")
			  .build();

	//The SP-API CatalogItem API instance used to call the getCatalogItem operation
	private static final CatalogApi catalogApi = new CatalogApi.Builder()
			  .awsAuthenticationCredentials(awsAuthenticationCredentials)
			  .lwaAuthorizationCredentials(lwaAuthorizationCredentials)
			 // .awsAuthenticationCredentialsProvider(awsAuthenticationCredentialsProvider)
			  .endpoint("https://sellingpartnerapi-na.amazon.com")
			  .build();

	//Declare a GetCatlogItemResponse variable for the item
	private static GetCatalogItemResponse catalogItemResponse;

	//リクエストを作成してオペレーション呼び出しを行う
	public static GetCatalogItemResponse callCatalogItem(String asin)throws IOException,ApiException{
		/*
		 * リクエスト作成
		 */
		// Define the path for the operation.
		//final String resourcePath="/catalog/v0/items/"+asin;

		//request parameter
		String marketplaceId="marketplaceId";//TODO marketplaceId

		//Get catalogitem
		catalogItemResponse=getCatalogItem(marketplaceId,asin);

		/*
		 * リクエストの実行
		 */
	    // Build, sign, and execute the request, specifying RestrictedResource, RDT, and RequestBody.
        // Pass a RequestBody only if required by the restricted operation. The requestBody is not required in this example.
		//Response catalogRequestResponse=buildeAndExecuteCatalogRequest(resourcePath,marketplaceId,asin,null);

		return catalogItemResponse;
	}

	/*
	 * Catalog Itemをゲットする
	 */
	//method for get catalog
	private static GetCatalogItemResponse getCatalogItem(String marketplaceId,String asin) throws ApiException{
		try {
			GetCatalogItemResponse response=catalogApi.getCatalogItem(marketplaceId, asin);
			return response;
		}catch(ApiException e) {
			System.out.println(e.getResponseHeaders());;
			throw e;
		}
	}

	//リクエストの作成と実行
	/*private static Response buildeAndExecuteCatalogRequest(String path,String marketplaceId,String asin,RequestBody requestBody)throws IOException{
		//Construct a request
		Request signedRequest=new Request.Builder()
				.url(sellingPartnerAPIEndpoint+path)//TODO path?
				.method("method", requestBody)//TODO
				.addHeader("x-amz-access-token","")//TODO
				.build();

	    // Initiate an AWSSigV4Signer instance using your AWS credentials. This example is for an application registered using an AIM Role.
        AWSSigV4Signer awsSigV4Signer = new AWSSigV4Signer(awsAuthenticationCredentials, awsAuthenticationCredentialsProvider);

        /*
        // Or, if the application was registered using an IAM User, use the following example:
        AWSSigV4Signer awsSigV4Signer = new AWSSigV4Signer(awsAuthenticationCredentials);
        *

        // Sign the request with the AWSSigV4Signer.
        signedRequest = awsSigV4Signer.sign(signedRequest);

        // Execute the signed request.
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(signedRequest).execute();

        // Check the restricted operation response status code and headers.
        System.out.println(response.code());
        System.out.println(response.headers());
        return response;
	}*/


}
