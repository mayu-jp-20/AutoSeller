package com.example.demo.api.spapi;

import java.io.IOException;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.aop.LogAspect;
import com.example.demo.component.PropertyUtil;

import io.swagger.client.ApiException;
import io.swagger.client.api.CatalogApi;
import io.swagger.client.api.ProductPricingApi;
import io.swagger.client.model.GetCatalogItemResponse;
import io.swagger.client.model.GetOffersResponse;
import io.swagger.client.model.GetPricingResponse;
import io.swagger.client.model.ListCatalogCategoriesResponse;

@Component
public class SPAPIService {

	@Autowired
	SPAPIConfig spapi;

	private static CatalogApi catalogApi=SPAPIConfig.catalogApi;

	private static ProductPricingApi pricingApi=SPAPIConfig.pricingApi;

	/*
	 * SP-APIとの接続開始ログ
	 */
	@Before("execution(* com.example.demo.api.SPAPIService.*(..))")
	public void sPAPIServiceLog(JoinPoint jp) {
		Signature method=jp.getSignature();
		LogAspect.logger.info("CONNECT SP-API method:{}", method);
	}

	private static GetCatalogItemResponse catalogItemResponse;

	private static GetPricingResponse pricingResponse;

	private static ListCatalogCategoriesResponse listCatalogCategoriesResponse;

	private static GetOffersResponse offersResponse;

	public static GetCatalogItemResponse callCatalogItem(String asin)throws IOException,ApiException{

		//request parameter
		String marketplaceId = PropertyUtil.getMWSMarketPlaceId();

		//get catalog item
		try {
			catalogItemResponse = catalogApi.getCatalogItem(marketplaceId,asin);
			return catalogItemResponse;
		} catch (ApiException e) {
			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Code:"+e.getCode());
			LogAspect.logger.error("Cause:"+e.getCause());
			throw e;
		}
	}

	public static GetPricingResponse callPricing(List<String> asins) throws ApiException{
		//request parameter
		String marketplaceId=PropertyUtil.getMWSMarketPlaceId();

		String itemType="Asin";

		//get Pricing
		try {
			pricingResponse = pricingApi.getPricing(marketplaceId, itemType, asins, null, null);
			return pricingResponse;
		}catch(ApiException e) {
			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Code:"+e.getCode());
			LogAspect.logger.error("Cause:"+e.getCause());
			throw e;
		}
	}

	public static ListCatalogCategoriesResponse callCategories(String asin) throws ApiException {
		//request parameter
		String marketplaceId=PropertyUtil.getMWSMarketPlaceId();

		//get categories
		try {
			listCatalogCategoriesResponse = catalogApi.listCatalogCategories(marketplaceId, asin, null);
			return listCatalogCategoriesResponse;
		} catch (ApiException e) {
			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Code:"+e.getCode());
			LogAspect.logger.error("Cause:"+e.getCause());
			throw e;
		}
	}

	public static GetOffersResponse getOffers(String asin) throws ApiException {
		//request parameter
		String marketplaceId=PropertyUtil.getMWSMarketPlaceId();

		String itemCondition="New";

		try {
			offersResponse=pricingApi.getItemOffers(marketplaceId, itemCondition, asin);
			return offersResponse;
		} catch (ApiException e) {
			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Code:"+e.getCode());
			LogAspect.logger.error("Cause:"+e.getCause());
			throw e;
		}
	}
}
