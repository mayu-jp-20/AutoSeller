package com.example.demo.api.qoo.model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SetNewGoodsRequest {

	private String key;//販売者認証キー
	private String secondSubCat;//小カテゴリー/必須
	//private String manufactureNo;//メーカー番号
	//private String brandNo;//ブランド番号
	private String itemTitle;//商品名/必須
	//private String sellerCode;//販売者コード
	//private String industrialCode;//産業コード
	//private String productionPlace;//原産国
	private String adultYN;//アダルトグッズならY,アダルトグッズじゃないならN/必須
	//private String contactTel;//お客様に表示される情報
	private String StandardImage;//商品イメージURL/必須
	private String ItemDescription;//商品の詳細（Html）/必須
	private BigDecimal retailPrice;//参考価格/必須
	private BigDecimal ItemPrice;//商品価格/必須
	private int ItemQty;//販売数量/必須
	private String ExpireDate;//販売期間/必須
	private int ShippingNo;//送料番号/必須
	private String AvailableDateType;//商品発送可能日タイプ
	private String AvailableDateValue;//商品発送可能日タイプの詳細




}
