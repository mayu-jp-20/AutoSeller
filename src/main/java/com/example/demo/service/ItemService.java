package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.domain.CheckBrowseDTO;
import com.example.demo.dao.ItemDao;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.SearchModel;

@Service
public class ItemService {

	@Autowired
	ItemDao dao;

	public List<ItemModel> selectAll(){
		return dao.selectAll();
	}

	public ItemModel selectOne(String asin) {
		return dao.selectOne(asin);
	}

	public List<ItemModel>selectMany(List<String>asinList){

		List<ItemModel>itemList=new ArrayList<>();
		for(String asin:asinList) {
			ItemModel item=dao.selectOne(asin);
			itemList.add(item);
		}
		return itemList;
	}

	/*
	 * よくわからないメソッド
	 * どこでどう使うのか判明したら、可能であれば引数のasinListをasinにしてその他の処理は
	 * サービスクラスで扱う
	 * SearchModelもできればString keywardにして受け取る
	 */
	public List<ItemModel>selectExhibitListBySearch(List<String>asinList,SearchModel searchModel){
		return dao.selectExhibitListBySearch(asinList, searchModel);
	}

	//selectExhibitListBySearchとなにが違うのかわかったら修正する
	public List<ItemModel> selectAllItemBySearch(String keyword) {
		return dao.selectAllItemBySearch(keyword);
	}

	public int insertItem(ItemModel item) {

		int rowNumber=dao.insertItem(item);

		return rowNumber;
	}

	public int deleteNgAsin(List<String>removeAsinList) {

		int result=0;

		for(String asin:removeAsinList) {
			int rowNumber=dao.deleteNgAsin(asin);
			result=result + rowNumber;
		}
		return result;
	}

	public int deleteNgName(List<String>removeNameList) {

		int result=0;

		for (String name:removeNameList) {
			int rowNumber = dao.deleteNgName(name);
			result=result+rowNumber;
		}
		return result;
	}

	public int deleteNgFeature(List<String>removeInfoList) {

		int result=0;

		for(String keyword:removeInfoList) {
			int rowNumber=dao.deleteNgFeature(keyword);
			result=result+rowNumber;
		}
		return result;
	}

	public int updateAnalytics(List<ExhibitModel>list) {

		int result=0;

		for(ExhibitModel model:list) {
			int rowNumber=dao.updateAnalytics(model);
			result=result+rowNumber;
		}
		return result;
	}

	public int updatePrice(ItemModel item) {

		int rowNumber=dao.updatePrice(item);

		return rowNumber;
	}

	public int updateNewCategory(ItemModel item) {

		int rowNumber=dao.updateNewCategory(item);

		return rowNumber;
	}

	public List<CheckBrowseDTO>selectNewBrowse(){
		return dao.selectNewBrowse();
	}

	public int updateNotNew(String category) {

		int rowNumber=dao.updateNotNew(category);

		return rowNumber;
	}

	public List<String>selectAllAsin(){

		List<String>asinList=new ArrayList<>();
		List<ItemModel> itemList=dao.selectAll();

		for(ItemModel item:itemList) {
			asinList.add(item.getAsin());
		}
		return asinList;
	}

	public int countMatchCategory(String category) {
		return dao.countMatchCategory(category);
	}


}
