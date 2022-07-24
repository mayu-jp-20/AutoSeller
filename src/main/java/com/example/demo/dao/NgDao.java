package com.example.demo.dao;

import java.util.List;

public interface NgDao {

	//NGキーワードテーブルのインターフェース
	//select系のメソッド(selectNgAsin,selectNgWord,selectNgBrowse)を統合して、引数でタイプ指定、SQLを発行する

	public int insert(String ngContents,String contentsType);

	public List<String> selectNgContents(String type);
}
