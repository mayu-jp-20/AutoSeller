package com.example.demo.api.qoo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SetGoodsPriceRequest {

	private String key;
	private String itemCode;
	private String sellerCode;
	private BigDecimal itemPrice;
	private int itemQty;
	private String expireDate;

}
