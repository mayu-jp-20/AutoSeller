package com.example.demo.api.qoo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.aop.LogAspect;
import com.example.demo.api.qoo.model.CreateCertificationKeyRequest;
import com.example.demo.api.qoo.model.CreateCertificationKeyResponse;
import com.example.demo.api.qoo.model.EditGoodsMultiImageRequest;
import com.example.demo.api.qoo.model.GetSellerDeliveryGroupInfoRequest;
import com.example.demo.api.qoo.model.GetSellerDeliveryGroupInfoResponse;
import com.example.demo.api.qoo.model.GetShippingInfoRequest;
import com.example.demo.api.qoo.model.GetShippingInfoResult;
import com.example.demo.api.qoo.model.GoodsStatusRequest;
import com.example.demo.api.qoo.model.ItemResponse;
import com.example.demo.api.qoo.model.SetGoodsPriceRequest;
import com.example.demo.api.qoo.model.SetNewGoodsRequest;
import com.example.demo.component.PropertyUtil;
import com.example.demo.component.ResponseHeaderInterceptor;
import com.example.demo.domain.UserModel;

@Service
public class QooService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ResponseHeaderInterceptor interceptor;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Autowired
	RequestMakerQoo reqMaker;;

	/*
	 * Qoo10との接続開始ログ
	 */
	@Before("execution(* com.example.demo.api.QooService.*(..))")
	public void qooConnectLog(JoinPoint jp) {
		Signature method=jp.getSignature();
		LogAspect.logger.info("CONNECT Qoo10 method:{}", method);
	}

	/*
	 * Qoo10との接続終了ログは出力しない方針
	 */

	//contents-typeが合わない場合はResposeHeaderInterceptorで設定しているのを使う（郵便番号API参照）

	/*
	 * 販売者認証キー生成
	 * 他のQoo10APIを呼び出す旅にキーを生成する
	 */
	protected CreateCertificationKeyResponse createCertificationKey(CreateCertificationKeyRequest certification) throws RestClientException{

		final String URL=PropertyUtil.getCreateCertificationKeyURL();
		LogAspect.logger.info("Request[url:{}]",URL);

		CreateCertificationKeyResponse result=restTemplate.getForObject(URL, CreateCertificationKeyResponse.class,certification);

		if(result.getResultCode()!=0) {
			LogAspect.logger.warn("Failed to Qoo10.createCertificationKey errorCode:{} ,{}"
					, result.getResultCode(),result.getResultMsg());
		}

		//本番環境では出力しない
		LogAspect.logger.info("createCertificationKey Result code:{}, msg:{}"
				,result.getResultCode()
				,result.getResultMsg());

		return result;
	}

	//Qoo10情報入力時に使用できるかたしかめるために一度キーを生成する
	public int testKey(UserModel user) {
		CreateCertificationKeyRequest keyRequest=reqMaker.createCertificationKeyRequest(user);

		final String URL=PropertyUtil.getCreateCertificationKeyURL();
		CreateCertificationKeyResponse response=restTemplate.getForObject(URL, CreateCertificationKeyResponse.class,keyRequest);

		//本番環境では出力しない
		LogAspect.logger.info("createCertificationKey Result code:{}, msg:{},{}"
				,response.getResultCode()
				,response.getResultMsg());

		return response.getResultCode();
	}



	//商品登録
	public ItemResponse setNewGoods(SetNewGoodsRequest item)throws RestClientException{

		final String URL=PropertyUtil.getSetNewGoodsURL();
		LogAspect.logger.info("Request:{},url:{}",item,URL);

		ItemResponse result=restTemplate.getForObject(URL, ItemResponse.class,item);

		if(result.getResultCode()!=0) {
			LogAspect.logger.warn("Failed to Qoo10.setNewGoods errorCode:{} {}"
					, result.getResultCode(),result.getResultMsg());
		}

		//本番環境では出力しない
		LogAspect.logger.info("setNewGoods Result code:{}, msg:{},{}"
				,result.getResultCode()
				,result.getResultMsg());

		return result;
	}

	//送料グループ取得
	public GetSellerDeliveryGroupInfoResponse getSellerDeliveryGroupInfo(GetSellerDeliveryGroupInfoRequest dto) throws RestClientException{

		final String URL=PropertyUtil.getSellerDeliveryGroupInfoURL();
		LogAspect.logger.info("Request:{},url:{}",dto,URL);

		GetSellerDeliveryGroupInfoResponse result=restTemplate.getForObject(URL, GetSellerDeliveryGroupInfoResponse.class, dto);
		if(result.getResultCode()!=0) {
			LogAspect.logger.warn("Failed to Qoo10.getSellerDeliveryGroupInfo errorCode:{} {}"
					, result.getResultCode(),result.getResultMsg());
		}

		//本番環境では出力しない
		LogAspect.logger.info("createCertificationKey Result code:{}, msg:{},{}"
				,result.getResultCode()
				,result.getResultMsg()
				,result.toString());

		return result;
	}

	//販売価格修正
	public ItemResponse setGoodsPrice(SetGoodsPriceRequest dto)throws RestClientException {

		final String URL=PropertyUtil.getGoodsPriceURL();
		LogAspect.logger.info("Request:{},url:{}",dto,URL);

		ItemResponse result=restTemplate.getForObject(URL, ItemResponse.class,dto);
		if(result.getResultCode()!=0) {
			LogAspect.logger.warn("Failed to Qoo10.getSellerDeliveryGroupInfo errorCode:{} {}"
					, result.getResultCode(),result.getResultMsg());
		}

		//本番環境では出力しない
		LogAspect.logger.info("createCertificationKey Result code:{}, msg:{},{}"
				,result.getResultCode()
				,result.getResultMsg()
				,result.toString());

		return result;
	}

	//商品イメージ追加
	public ItemResponse editGoodsMaltiImage(EditGoodsMultiImageRequest dto) throws RestClientException{

		//英語の方のAPIに載っている
		final String URL=PropertyUtil.getEditGoodsMultiImageURL();
		LogAspect.logger.info("Request:{},url:{}",dto,URL);

		ItemResponse result=restTemplate.getForObject(URL, ItemResponse.class, dto);
		if(result.getResultCode()!=0) {
			LogAspect.logger.warn("Failed to Qoo10.editGoodsMaltiImage errorCode:{} {}"
					, result.getResultCode(),result.getResultMsg());
		}
		//本番環境では出力しない
		LogAspect.logger.info("editGoodsMaltiImage Result code:{}, msg:{}"
				,result.getResultCode()
				,result.getResultMsg());

		return restTemplate.getForObject(URL, ItemResponse.class, dto);
	}

	//商品状態の修正（出品削除）
	public ItemResponse editGoodsStatus(GoodsStatusRequest dto) throws RestClientException{

		final String URL=PropertyUtil.getEditGoodsStatusURL();
		LogAspect.logger.info("Request:{},url:{}",dto,URL);

		ItemResponse result=restTemplate.getForObject(URL, ItemResponse.class, dto);
		if(result.getResultCode()!=0) {
			LogAspect.logger.warn("Failed to Qoo10.editGoodsStatus errorCode:{} {}"
					, result.getResultCode(),result.getResultMsg());
		}

		//本番環境では出力しない
		LogAspect.logger.info("editGoodsStatus Result code:{}, msg:{}"
				,result.getResultCode()
				,result.getResultMsg());

		return result;
	}

	//注文情報取得（英語のページのほう）
	public GetShippingInfoResult getShippingInfo(GetShippingInfoRequest dto)throws RestClientException{

		final String URL=PropertyUtil.getShippingInfo();
		LogAspect.logger.info("Request:{},url:{}", dto,URL);

		GetShippingInfoResult result=restTemplate.getForObject(URL, GetShippingInfoResult.class, dto);
		if(result.getResultCode()!=0) {
			LogAspect.logger.warn("Failed to Qoo10.getShippingInfo errorCode:{} {}"
					, result.getResultCode(),result.getResultMsg());
		}

		//本番環境では出力しない
		LogAspect.logger.info("getShippingInfo Result code:{}, msg:{},{}"
				,result.getResultCode()
				,result.getResultMsg()
				,result.getResultObject());

		return result;
	}

	//郵便番号（テスト済み）テスト用
	/*public String test(String zip) {

		final String URL="https://zipcloud.ibsnet.co.jp/api/search?zipcode={zipcode}";

		zip="0530806";

	    restTemplate=restTemplateBuilder.additionalInterceptors(interceptor).build();

		return restTemplate.getForObject(URL,String.class , zip);
	}*/


	@Bean
	public RestTemplate restTemplate(
			//RestTemplateBuilder restTemplateBuilder
			//,ResponseHeaderInterceptor interceptor
			) {

			//restTemplateBuilder.additionalInterceptors(interceptor).build();

			return new RestTemplate();
		}



}
