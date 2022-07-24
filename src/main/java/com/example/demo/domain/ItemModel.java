package com.example.demo.domain;

import java.math.BigDecimal;

import com.example.demo.api.amazon.model.AttributeSets;
import com.example.demo.api.amazon.model.Product;

import io.swagger.client.model.AttributeSetListType;
import io.swagger.client.model.ListCatalogCategoriesResponse;
import lombok.Data;

@Data
public class ItemModel {

	/*
	 * ランクとカテゴリの関係について
	 * 要件①：表示するカテゴリは大カテゴリで良い
	 * 要件②：ブラウズノードで判断したいから小カテゴリも必要
	 * 必要なのは、大カテゴリ、小カテゴリ、大カテゴリ内のランク
	 */

	private String asin;
	private String title;
	private AttributeSets attrSet;
	private AttributeSetListType attrList;
	private String strCategory;
	private ListCatalogCategoriesResponse category;
	private String subCategory;
	private String rank;
	private BigDecimal shipping;
	private BigDecimal price;
	private int shippingTime;
	private String imageUrlList;
	private boolean newCategory;//未確認かどうか
	private int monthlyAccess;
	private int monthlySales;
	private int allAccess;
	private int allSales;
	private Product product;
	private String feature;//説明
	//itemDetailで使う
	private String strAttrList;

}
