package com.example.demo.api.qoo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GetSellerDeliveryGroupInfoObject {

	private int resultCode;
	private String resultMsg;
	private String shippingNo;//送料グループ番号
	private BigDecimal shippingFee;//送料
	private String shippingType;//送料タイプ
	private BigDecimal freeCondition;//送料無料条件
	private String region;//地域別配送オプション
	private String oversea;//海外配送Y/N
	private String shippingOption;//送料オプションコード
}
