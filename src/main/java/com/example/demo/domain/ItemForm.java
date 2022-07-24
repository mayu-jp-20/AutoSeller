package com.example.demo.domain;

import lombok.Data;

//商品情報を画面に出力するときのフォーム
@Data
public class ItemForm {

	private String asin;
	private String itemName;
	private String sizeList;
	private String colorList;
	private String explanation;
	private String info;
	private String category;
	private String rank;
	private int numberOfSeller;
	private int allAccess;
	private int monthlyAccess;
	private int allSales;
	private int monthlySales;
	private int allAccessPerSeller;
	private int monthlyAccessPerSeller;
	private int allSalesPerSeller;
	private int monthlySalesPerSeller;
}
