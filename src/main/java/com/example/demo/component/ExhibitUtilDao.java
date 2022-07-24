package com.example.demo.component;

import java.util.List;

import com.example.demo.api.amazon.model.MyMatchingProductForIdResult;
import com.example.demo.api.amazon.model.Product;
import com.example.demo.api.qoo.model.ItemResponse;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;

import io.swagger.client.model.GetOffersResponse;
import io.swagger.client.model.SalesRankList;

/*
 * 商品情報取得と出品に関わる処理のインターフェース
 * 処理の詳細はExhibitUtilDetailに記述
 */
public interface ExhibitUtilDao {

	//NG_ASINの除外,テスト済

	public List<String> removeNgAsin(List<String>asinList);

	//既にDBに登録されているのもを抽出,テスト済
	public List<String> selectInsertedAsin(List<String>asinList);

	//DB登録されていないものを抽出,テスト済み
	public List<String>selectNotInsertedAsin(List<String>asinList,List<String>inesrtedAsinList);

	//お届け日数が三日以上かかるものと送料390円以上のものを除外
	public List<Product>removeLotOfShippingTime(List<Product>productList);

	public List<GetOffersResponse> removeManyShippingTime(List<GetOffersResponse> offersList);

	//ASINリストを作成
	public List<String>createAsinList(List<String>asinList);

	//asin,shipping time,shipping costをセットする
	public List<ItemModel>setOffersAttr(List<GetOffersResponse>offersList);

	//NG_Browseを削除
	public List<MyMatchingProductForIdResult>removeNgBrowse(List<MyMatchingProductForIdResult>resultList);

	public List<ItemModel>removeNGBrowse(List<ItemModel>itemList);

	//ランクをセット
	public void setRank(SalesRankList rankList,ItemModel item);

	//イメージURLリストをセット
	public ItemModel setImageList(List<String> imageList,ItemModel item);

	//AmazonのカテゴリからQoo10のカテゴリに変換
	public String selectCategory(ItemModel item);

	//NG_WORDの除外
	public List<MyMatchingProductForIdResult> removeNgWord(List<MyMatchingProductForIdResult>resultList);

	public List<ItemModel> removeNGWord(List<ItemModel>itemList) throws IllegalArgumentException, IllegalAccessException;

	//カテゴリが新しいものかどうか判断/新しければ印をつける
	public void judgeCategory(List<ItemModel>itemList);

	//insertedAsinからItemModelを作成
	public List<ItemModel>makeInsertedItemModel(List<String>asin,List<ItemModel>itemList);

	//itemテーブルに登録
	public void insertItem(List<ItemModel>itemList);

	//Qoo10に出品
	public ItemResponse setNewGoods(UserModel user,ItemModel exhibititem) ;

	//ExhibitDBに登録する
	public int insertItem(ExhibitModel exhibit);
}
