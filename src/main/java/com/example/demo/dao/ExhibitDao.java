package com.example.demo.dao;

import java.util.List;

import com.example.demo.domain.ExhibitModel;

//出品情報テーブルの読み書き用インターフェース
public interface ExhibitDao {

	//1商品の出品者数カウント
	public int countSeller(String asin);

	//1ユーザーの全ての項目を抽出
	public List<ExhibitModel> selectExhibitAll(String mailAddress);

	//登録
	public int insert(ExhibitModel exhibit);

	//ユーザーの出品数を取得
	public int countExhibiNum(String mailAddress);

	//ユーザーの集計情報を登録する
	public int insertAnalytics(String mailAddress,ExhibitModel list);

	public List<ExhibitModel>selectByAsin(String asin);

	public int deleteItem(String mailAddress,String asin);

	//ASINとメールアドレスからitemCodeを取得する
	public List<String> getItemCode(String mailAddress,String asin);

	//メールアドレスとASINからアナリティクスを取得する
	public ExhibitModel selectOne(String mailAddress,String asin);


}
