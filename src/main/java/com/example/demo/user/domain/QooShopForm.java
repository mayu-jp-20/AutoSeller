package com.example.demo.user.domain;

import lombok.Data;

//バリデーションなし
@Data
public class QooShopForm {

	//商品名固定追加キーワード
	private String fixingKeywardForItem;

	//販売数量
	private int itemQty;

	//送料設定グループ
	private  String shippingCode;

	//商品ページヘッダー記入
	private String itemPageHeader;

	//商品ページフッター
	private String itemPageFooter;


}
