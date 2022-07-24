package com.example.demo.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.api.qoo.model.ItemResponse;
import com.example.demo.api.spapi.SPAPIService;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;

import io.swagger.client.ApiException;
import io.swagger.client.model.AttributeSetListType;
import io.swagger.client.model.GetCatalogItemResponse;
import io.swagger.client.model.GetOffersResponse;
import io.swagger.client.model.ListCatalogCategoriesResponse;

@Component
public class ExhibitUtilService {

	@Autowired
	ExhibitUtilDao exbUtil;

	/*
	 * ASINからItemModelに変換
	 * <処理内容>
	 *
	 */
	public List<ItemModel>getItemModelByAsin(List<String>asinList) throws IllegalArgumentException, IllegalAccessException, IOException, ApiException{

		//NG＿ASINの除外
		asinList=exbUtil.removeNgAsin(asinList);

		// 既にDB登録されているのを選択
		List<String> insertedAsinList=exbUtil.selectInsertedAsin(asinList);

		//DBに登録されていないものを選択(登録されているものを削除）
		List<String> notInsertedAsin=exbUtil.selectNotInsertedAsin(asinList, insertedAsinList);

		/*
		 * notInsertedAsinの処理
		 */
		//お届け日数と配送料を調べる
		List<GetOffersResponse>offersList=new ArrayList<>();

		for (String asin:notInsertedAsin) {
			GetOffersResponse offers = SPAPIService.getOffers(asin);
			offersList.add(offers);
		}

		//三日以上かかるもの/390円以上の送料は除外
		offersList = exbUtil.removeManyShippingTime(offersList);

		//ItemModelにasinをセットする
		List<ItemModel> itemList=exbUtil.setOffersAttr(offersList);

		//ASINからMWSで情報取得(ProductのAsinを利用する）
		for(ItemModel item : itemList) {

			//Get catalog item
			GetCatalogItemResponse catalogItem=SPAPIService.callCatalogItem(item.getAsin());

			//Set AttributeList into itemModel
			AttributeSetListType attr=catalogItem.getPayload().getAttributeSets().get(0);
			item.setAttrList(attr);

			//Set title
			item.setTitle(attr.getTitle());

			//Get categories
			ListCatalogCategoriesResponse catalogCategories=SPAPIService.callCategories(item.getAsin());

			//Set categories into ItemModel
			item.setCategory(catalogCategories);

			//Set rank
			exbUtil.setRank(catalogItem.getPayload().getSalesRankings(),item);

			//If category is new one,set true at colum of new_category
			exbUtil.judgeCategory(itemList);

		}

		//remove NG Browse item
		itemList=exbUtil.removeNGBrowse(itemList);

		//さらにNG＿WORDの調査をして
		itemList=exbUtil.removeNGWord(itemList);

		/*
		 * insertedAsinの処理
		 */
		//insertedAsinからItemModelを作成
		List<ItemModel>latestItemList=exbUtil.makeInsertedItemModel(insertedAsinList, itemList);

		//itemテーブルに登録
		exbUtil.insertItem(latestItemList);

		//TODO 成功シグナルを返す
		return null;
	}

	/*
	 * Qoo10への出品処理
	 * 出品処理後の新上限数を返す
	 */
	public int exhibitItem(List<ItemModel>itemList,UserModel user) {

		List<ExhibitModel>exhibitList=new ArrayList<>();
		int limitNum=user.getLimitNum();//上限数を取得

		for (ItemModel item:itemList) {
			//新商品登録
			ItemResponse response = exbUtil.setNewGoods(user, item);

			if(response.getResultCode()==0) {//Success

				ExhibitModel model=new ExhibitModel();
				model.setItemCode(response.getResultMsg());
				model.setAsin(item.getAsin());
				model.setMailAddress(user.getUserId());
				exhibitList.add(model);

				//マルチイメージを追加
				//TODO editGoodsMaltiImage(item.getAttrList().getSmallImage(),response.getResultMsg(),user);

				//exhibitテーブルの更新
				int result=exbUtil.insertItem(model);
				if(result==0) {
					limitNum--;
				}

				if(limitNum==0) {//上限数が0になった時点でループ抜ける
					return 0;
				}
			}
		}
		return limitNum;
	}

}
