package com.example.demo.domain;

import lombok.Data;

@Data
public class SearchModel {

	private String keyward;//キーワード検索(商品名の検索のみ）
	private String asin;//asinで検索
	private String category;//カテゴリで検索

}
