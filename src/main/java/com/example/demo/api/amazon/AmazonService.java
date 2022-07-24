package com.example.demo.api.amazon;

import org.springframework.stereotype.Service;

@Service
public class AmazonService {

	/*
	 * MWSからSP-APIに以降
	 */

	/*@Autowired
	RequestMakerAmazon requestMaker;

	/*
	 * mwsとの接続開始ログ
	 *
	@Before("execution(* com.example.demo.api.AmazonService.*(..))")
	public void mwsConnectLog(JoinPoint jp) {
		Signature method=jp.getSignature();
		LogAspect.logger.info("CONNECT MWS method:{}",method);
	}

	/*
	 * mwsとの接続終了ログは出力しない方針
	 *

	//ASINをもとに商品情報を取得する
	public  GetMatchingProductForIdResponse invokeGetMatchingProductForId(IdListType asinList) {
	        try {
	        	MarketplaceWebServiceProductsClient client = MyMarketplaceWebServiceProductsConfig.getClient();
	        	GetMatchingProductForIdRequest request=requestMaker.makeGetMatchingProductForIdRequestRequest(asinList);
	        	LogAspect.logger.info("request[idList:{},idType:{},marketPlaceId:{},MWSAuthToken:{},sellerId{}]"
	           			,request.getIdList()
	        			,request.getIdType()
	        			,request.getMarketplaceId()
	        			,request.getMWSAuthToken()
	        			,request.getSellerId());
	            GetMatchingProductForIdResponse response = client.getMatchingProductForId(request);
	            ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
	            // We recommend logging every the request id and timestamp of every call.
	            LogAspect.logger.info("RequestId:{},Timestamp:{}",rhmd.getRequestId(),rhmd.getTimestamp());
	            LogAspect.logger.info("Response:{}", response.toString());
	            return response;
	        } catch (MarketplaceWebServiceProductsException ex) {
	            // Exception properties are important for diagnostics.
	            ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
	            if(rhmd != null) {
	                LogAspect.logger.info("Response[QuotaMax:{],QuotaRemaining:{},QuotaResetAt:{},RequestId:{},ResponseContext:{},Timestamp:{}]"
	                		,rhmd.getQuotaMax()
	                		,rhmd.getQuotaRemaining()
	                		,rhmd.getQuotaResetsAt()
	                		,rhmd.getRequestId()
	                		,rhmd.getResponseContext()
	                		,rhmd.getTimestamp());
	            }
	            LogAspect.logger.error("Message: "+ex.getMessage());
	            LogAspect.logger.error("StatusCode: "+ex.getStatusCode());
	            LogAspect.logger.error("ErrorCode: "+ex.getErrorCode());
	            LogAspect.logger.error("ErrorType: "+ex.getErrorType());
	            throw ex;
	        }
	   }

	 //ASINをもとにした金額の取得
	 public GetCompetitivePricingForASINResponse invokeGetCompetitivePricingForASIN(
			    ASINListType asinList) {
	        try {
	        	MarketplaceWebServiceProductsClient client = MyMarketplaceWebServiceProductsConfig.getClient();
	        	GetCompetitivePricingForASINRequest request=requestMaker.makeGetCopetitivePriceForASINRequest(asinList);
	        	LogAspect.logger.info("request[ASINList:{},marketPlaceId:{},MWSAuthToken:{},sellerId:{}]"
	           			,request.getASINList()
	        			,request.getMarketplaceId()
	        			,request.getMWSAuthToken()
	        			,request.getSellerId());
	            // Call the service.
	            GetCompetitivePricingForASINResponse response = client.getCompetitivePricingForASIN(request);
	            ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
	            // We recommend logging every the request id and timestamp of every call.
	            LogAspect.logger.info("RequestId:{},Timestamp:{}",rhmd.getRequestId(),rhmd.getTimestamp());
	            LogAspect.logger.info("Response:{}", response.toString());
	            return response;
	        } catch (MarketplaceWebServiceProductsException ex) {
	            // Exception properties are important for diagnostics.
	            ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
	            if(rhmd != null) {
	                LogAspect.logger.info("Response[QuotaMax:{],QuotaRemaining:{},QuotaResetAt:{},RequestId:{},ResponseContext:{},Timestamp:{}]"
	                		,rhmd.getQuotaMax()
	                		,rhmd.getQuotaRemaining()
	                		,rhmd.getQuotaResetsAt()
	                		,rhmd.getRequestId()
	                		,rhmd.getResponseContext()
	                		,rhmd.getTimestamp());
	            }
	            LogAspect.logger.error("Message: "+ex.getMessage());
	            LogAspect.logger.error("StatusCode: "+ex.getStatusCode());
	            LogAspect.logger.error("ErrorCode: "+ex.getErrorCode());
	            LogAspect.logger.error("ErrorType: "+ex.getErrorType());
	            throw ex;
	        }
	  }

	 //ASINを使ってカテゴリID取得/GetmatchingProductForIdでカテゴリ取得できるっぽいからいらないはず
	 //改定：必要.後にAmazon小カテゴリID,カテゴリ名,Qoo10小カテゴリを1レコードとしたテーブルを作成する
	 //ランク取得するときに小カテゴリIDをもとにするため
	  public  GetProductCategoriesForASINResponse invokeGetProductCategories(String asin) {
	        try {
	        	MarketplaceWebServiceProductsClient client=MyMarketplaceWebServiceProductsConfig.getClient();
	        	GetProductCategoriesForASINRequest request=requestMaker.makeGetProductCategoriesForASINRequest(asin);
	        	LogAspect.logger.info("request:{}", request.toString());

	            // Call the service.
	            GetProductCategoriesForASINResponse response = client.getProductCategoriesForASIN(request);
	            ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
	            // We recommend logging every the request id and timestamp of every call.
	            System.out.println("Response:");
	            System.out.println("RequestId: "+rhmd.getRequestId());
	            System.out.println("Timestamp: "+rhmd.getTimestamp());
	            String responseXml = response.toXML();
	            System.out.println(responseXml);
	            return response;
	        } catch (MarketplaceWebServiceProductsException ex) {
	            // Exception properties are important for diagnostics.
	            System.out.println("Service Exception:");
	            ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
	            if(rhmd != null) {
	                System.out.println("RequestId: "+rhmd.getRequestId());
	                System.out.println("Timestamp: "+rhmd.getTimestamp());
	            }
	            System.out.println("Message: "+ex.getMessage());
	            System.out.println("StatusCode: "+ex.getStatusCode());
	            System.out.println("ErrorCode: "+ex.getErrorCode());
	            System.out.println("ErrorType: "+ex.getErrorType());
	            throw ex;
	        }
	   }

	  //お届け日数と配送料を調べる
	  public GetLowestOfferListingsForASINResponse getLowestOfferListingsForASIN(ASINListType asinList) {

		  try {
			  MarketplaceWebServiceProductsClient client=MyMarketplaceWebServiceProductsConfig.getClient();
	          GetLowestOfferListingsForASINRequest request=requestMaker.makeGetLowestOfferListingsForASINRequest(asinList);
	          LogAspect.logger.info("request[ASINList:{},ExcludeMe:{},ItemCondition:{},marketPlaceId:{},MWSAuthToken:{},sellerId:{}]"
	         			,request.getASINList()
	        			,request.getExcludeMe()
	        			,request.getItemCondition()
	        			,request.getMarketplaceId()
	        			,request.getMWSAuthToken()
	        			,request.getSellerId());

	          // Call the service.
	          GetLowestOfferListingsForASINResponse response = client.getLowestOfferListingsForASIN(request);
	          ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
	          // We recommend logging every the request id and timestamp of every call.
	          LogAspect.logger.info("RequestId: "+rhmd.getRequestId());
	          LogAspect.logger.info("Timestamp: "+rhmd.getTimestamp());
	          return response;
	      } catch (MarketplaceWebServiceProductsException ex) {
	            // Exception properties are important for diagnostics.
	            ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
	            if(rhmd != null) {
	                LogAspect.logger.info("Response[QuotaMax:{],QuotaRemaining:{},QuotaResetAt:{},RequestId:{},ResponseContext:{},Timestamp:{}]"
	                		,rhmd.getQuotaMax()
	                		,rhmd.getQuotaRemaining()
	                		,rhmd.getQuotaResetsAt()
	                		,rhmd.getRequestId()
	                		,rhmd.getResponseContext()
	                		,rhmd.getTimestamp());
	            }
	            LogAspect.logger.error("Message: "+ex.getMessage());
	            LogAspect.logger.error("StatusCode: "+ex.getStatusCode());
	            LogAspect.logger.error("ErrorCode: "+ex.getErrorCode());
	            LogAspect.logger.error("ErrorType: "+ex.getErrorType());
	            throw ex;
	        }
	  }*/
}
