package com.example.demo.api.qoo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ResultObject {

	private String shippingStatus;//配送状況
	private String orderDate;//注文日
	private String estShippingDate;//発送予定日
	private String shippingDate;//発送日
	private String itemTitle;//商品名
	private int orderNo;//注文番号
	private String itemCode;//商品番号（Qoo10）
	private String receiver;//受取人名
	private String receiver_gata;//受取人名（カタカナ）
	private String shippingCountry;//宛先の国
	private String zipCode;//郵便番号
	private String shippingAddr;//宛先住所
	private String add1;//住所1
	private String add2;//住所2
	private String subAddr;//サブアドレス
	private String receiverTel;//受取人電話番号
	private String receiverMobile;//受取人携帯電話番号
	private String hopeDate;//配達希望日
    private String buyer;//購入者名
    private String buyer_gata;//購入者名（カタカナ）
	private String buyerTel;//購入者電話番号
	private String buyerMobile;//購入者携帯電話
	private String buyerEmail;//購入者メールアドレス
	private BigDecimal orderPrice;//注文価格
	private String strOrderPrice;//String型とBigDecimal型を一列で表示したい（表示のときだけ使う）
	private int orderQty;//注文数量
	private String strOrderQty;//String型とint型を一列で表示したい（表示ときだけ使う）
	private BigDecimal discount;//割引金額
	private String strDiscount;//上記と同じ理由
	private BigDecimal total;//合計金額（商品価格+オプション価格-割引額）

}
