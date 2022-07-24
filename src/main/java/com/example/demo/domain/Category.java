package com.example.demo.domain;

import lombok.Data;

@Data
public class Category {

	private String categroyCode;//大分類コード
	private String category;//大分類
	private String amazonCategory;//Amazonでの大分類
	private String subCategoryCode;//中分類コード
	private String subCategory;//中分類
	private String subsubCategoryCode;//小分類カテゴリコード
	private String subsubCategory;//小分類カテゴリ
}
