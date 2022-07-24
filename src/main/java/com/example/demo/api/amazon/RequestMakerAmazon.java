package com.example.demo.api.amazon;

import org.springframework.stereotype.Component;

import com.amazonservices.mws.products.model.ASINListType;
import com.amazonservices.mws.products.model.GetCompetitivePricingForASINRequest;
import com.amazonservices.mws.products.model.GetLowestOfferListingsForASINRequest;
import com.amazonservices.mws.products.model.GetMatchingProductForIdRequest;
import com.amazonservices.mws.products.model.IdListType;
import com.example.demo.component.PropertyUtil;

@Component
public class RequestMakerAmazon {

	public GetMatchingProductForIdRequest makeGetMatchingProductForIdRequestRequest(IdListType asinList){

        // Create a request.
        GetMatchingProductForIdRequest request = new GetMatchingProductForIdRequest();
        String sellerId =PropertyUtil.getMWSSellerId();//出品者ID
        String marketPlaceId=PropertyUtil.getMWSMarketPlaceId();
        request.setSellerId(sellerId);
        //String mwsAuthToken = "";//TODO
        //request.setMWSAuthToken(mwsAuthToken);
        request.setMarketplaceId(marketPlaceId);
        request.setIdType("ASIN");
        request.setIdList(asinList);

		return request;
	}

	public GetCompetitivePricingForASINRequest makeGetCopetitivePriceForASINRequest(ASINListType asinList) {

        // Create a request.
        GetCompetitivePricingForASINRequest request = new GetCompetitivePricingForASINRequest();
        String sellerId =PropertyUtil.getMWSSellerId();
        String marketPlaceId=PropertyUtil.getMWSMarketPlaceId();
        request.setSellerId(sellerId);
        //String mwsAuthToken = "example";
        //request.setMWSAuthToken(mwsAuthToken);
        request.setMarketplaceId(marketPlaceId);
        request.setASINList(asinList);

		return request;
	}

/*	public GetProductCategoriesForASINRequest makeGetProductCategoriesForASINRequest(String asin) {

		//Create a Request
		GetProductCategoriesForASINRequest request=new GetProductCategoriesForASINRequest();
		String sellerId =PropertyUtil.getMWSSellerId();
        String marketPlaceId=PropertyUtil.getMWSMarketPlaceId();
        request.setSellerId(sellerId);
        request.setMarketplaceId(marketPlaceId);
        request.setASIN(asin);

		return request;
	}
*/
	public GetLowestOfferListingsForASINRequest makeGetLowestOfferListingsForASINRequest(ASINListType asinList) {

        // Create a request.
        GetLowestOfferListingsForASINRequest request = new GetLowestOfferListingsForASINRequest();
        request.setSellerId(PropertyUtil.getMWSSellerId());
        request.setMarketplaceId(PropertyUtil.getMWSMarketPlaceId());
        request.setASINList(asinList);
        request.setItemCondition(null);
        Boolean excludeMe = Boolean.valueOf(true);
        request.setExcludeMe(excludeMe);

        return request;
	}



}
