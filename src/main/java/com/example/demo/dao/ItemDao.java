package com.example.demo.dao;

import java.util.List;

import com.example.demo.admin.domain.CheckBrowseDTO;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.SearchModel;

public interface ItemDao {

	//商品情報テーブルの読み書き用インターフェース

	public int insertItem(ItemModel item);

	public int updateAnalytics(ExhibitModel analytics);

	public int updatePrice(ItemModel item);

	public int updateNewCategory(ItemModel item);

	public int updateNotNew(String category);

	public List<ItemModel> selectAll();

	public ItemModel selectOne(String asin);

	public int countMatchCategory(String category);

	public List<ItemModel> selectExhibitListBySearch(List<String> asinList,SearchModel searchModel);

	public List<ItemModel> selectAllItemBySearch(String keyword);

	//新しいブラウズノードを検索
	public List<CheckBrowseDTO>selectNewBrowse();

	public int deleteNgAsin(String removeAsin);

	public int deleteNgName(String emoveName);

	public int deleteNgFeature(String removeFeature);
}
