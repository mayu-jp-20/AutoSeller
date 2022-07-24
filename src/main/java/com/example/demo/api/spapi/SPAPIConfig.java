package com.example.demo.api.spapi;

import org.springframework.stereotype.Component;

import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentials;
import com.amazon.SellingPartnerAPIAA.LWAAuthorizationCredentials;

import io.swagger.client.api.CatalogApi;
import io.swagger.client.api.ProductPricingApi;


@Component
public class SPAPIConfig {

	//AWS認証情報の設定
	private static final AWSAuthenticationCredentials
	awsAuthenticationCredentials = AWSAuthenticationCredentials.builder()
	  .accessKeyId("myAccessKeyId")
	  .secretKey("mySecretId")
	  .region("us-east-")
	  .build();

	//LWA認証情報プロバイダーの設定
	/*AWSAuthenticationCredentialsProvider awsAuthenticationCredentialsProvider =
			  AWSAuthenticationCredentialsProvider.builder()
			  .roleArn("myroleARN")
			  .roleSessionName("myrolesessioname")
			  .build();*/

	//LWA認証情報の設定(認可不要の場合もある）
	private static final LWAAuthorizationCredentials lwaAuthorizationCredentials =
			  LWAAuthorizationCredentials.builder()
			  .clientId("myClientId")
			  .clientSecret("myClientSecret")
			  .refreshToken("Aztr|...")
			  .endpoint("https://api.amazon.com/auth/o2/token")
			  .build();

	//カタログAPIのインスタンスを作成し、オペレーションを呼び出す
	static final CatalogApi catalogApi = new CatalogApi.Builder()
			  .awsAuthenticationCredentials(awsAuthenticationCredentials)
			  .lwaAuthorizationCredentials(lwaAuthorizationCredentials)
			 // .awsAuthenticationCredentialsProvider(awsAuthenticationCredentialsProvider)
			  .endpoint("https://sellingpartnerapi-na.amazon.com")
			  .build();

	//ProductPricingAPIのインスタンスを作成し、オペレーションを呼び出す
	static final ProductPricingApi pricingApi=new ProductPricingApi.Builder()
			  .awsAuthenticationCredentials(awsAuthenticationCredentials)
			  .lwaAuthorizationCredentials(lwaAuthorizationCredentials)
			 // .awsAuthenticationCredentialsProvider(awsAuthenticationCredentialsProvider)
			  .endpoint("https://sellingpartnerapi-na.amazon.com")
			  .build();

}
