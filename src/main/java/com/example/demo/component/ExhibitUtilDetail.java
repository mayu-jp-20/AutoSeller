package com.example.demo.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.api.amazon.model.MyMatchingProductForIdResult;
import com.example.demo.api.amazon.model.Product;
import com.example.demo.api.qoo.QooService;
import com.example.demo.api.qoo.RequestMakerQoo;
import com.example.demo.api.qoo.model.ItemResponse;
import com.example.demo.api.qoo.model.SetNewGoodsRequest;
import com.example.demo.domain.Category;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ExhibitService;
import com.example.demo.service.ItemService;
import com.example.demo.service.NgService;

import io.swagger.client.model.GetOffersResponse;
import io.swagger.client.model.OfferDetail;
import io.swagger.client.model.OfferDetailList;
import io.swagger.client.model.SalesRankList;

@Component
public class ExhibitUtilDetail implements ExhibitUtilDao {

	@Autowired
	NgService ng;

	@Autowired
	ItemService item;

	@Autowired
	CategoryService category;

	@Autowired
	RequestMakerQoo reqMaker;

	@Autowired
	QooService qooService;

	@Autowired
	ExhibitService exhibitService;

	//NG_ASINを削除
	@Override
	public List<String> removeNgAsin(List<String> asinList) {

		List<String> ngAsinList=ng.selectNgContents("ASIN");
		List<String>removeList=new ArrayList<>();

		for(String ngAsin:ngAsinList) {//NGASINの調査
			for (String asin:asinList) {
				if (asin.equals(ngAsin)) {
					removeList.add(asin);
				}
			}
		}
		asinList.removeAll(removeList);
		return asinList;
	}

	//DB登録されているのもを選択
	@Override
	public List<String> selectInsertedAsin(List<String> asinList) {

		List<ItemModel> dbItemList=item.selectAll();
		List<String>insertedAsin=new ArrayList<>();

		for(ItemModel dbItem:dbItemList) {
			for (String asin:asinList) {
				if (asin.equals(dbItem.getAsin())) {
					insertedAsin.add(asin);
				}
			}
		}
		return insertedAsin;
	}

	@Override
	public List<String> selectNotInsertedAsin(List<String> asinList,List<String> inseretedAsinList) {

		asinList.removeAll(inseretedAsinList);
		return asinList;
	}

	@Override
	public List<Product> removeLotOfShippingTime(List<Product> productList) {

		List<Product>removeList=new ArrayList<>();

		for(Product product:productList) {
			String max=product.getMax();
			max.replace("days", "");
			String last=max.substring(max.length()-1);
			int i=Integer.parseInt(last);

			if(i > 3) {
				removeList.add(product);
			}
		}
		productList.removeAll(removeList);
		return productList;
	}

	@Override
	public List<GetOffersResponse> removeManyShippingTime(List<GetOffersResponse> offersList){

		List<GetOffersResponse>removeList=new ArrayList<>();

		for(GetOffersResponse offers:offersList) {
			OfferDetailList offerDetailsList=offers.getPayload().getOffers();

			for(OfferDetail offerDetail:offerDetailsList) {
				//check shipping time
				long max=offerDetail.getShippingTime().getMaximumHours();

				int maximumHours = (int)max;

				if(maximumHours > 72) {
					removeList.add(offers);
				}

				//check shipping cost
				BigDecimal shipping=offerDetail.getShipping().getAmount();

				BigDecimal upperLimit=new BigDecimal(390);

				if(shipping.compareTo(upperLimit)==1) {
					removeList.add(offers);
				}
			}
		}

		offersList.removeAll(removeList);
		return offersList;
	}

	@Override
	public List<String> createAsinList(List<String> asinList) {
		return null;
	}

	@Override
	public List<ItemModel> setOffersAttr(List<GetOffersResponse> offersList){

		List<ItemModel> itemList = new ArrayList<>();

		for(GetOffersResponse offers:offersList) {

			ItemModel item=new ItemModel();

			//Set asin
			item.setAsin(offers.getPayload().getASIN());

			/*
			 * Asinのみセットする
			 */
			//Set shipping time
			//Set shipping cost

			itemList.add(item);
		}
		return itemList;
	}

	@Override
	public List<MyMatchingProductForIdResult> removeNgBrowse(List<MyMatchingProductForIdResult>resultList) {

		List<String>ngBrowseList=ng.selectNgContents("CATEGORY");
		List<MyMatchingProductForIdResult>removeList=new ArrayList<>();

		for(String ngBrowse:ngBrowseList) {
			for(MyMatchingProductForIdResult result:resultList) {
				String category=result.getAttrList().getProductTypeSubcategory();
				if(category.equals(ngBrowse)) {
					removeList.add(result);
				}
			}
		}
		resultList.removeAll(removeList);
		return resultList;
	}

	@Override
	public List<ItemModel>removeNGBrowse(List<ItemModel>itemList){

		List<String>ngBrowseList=ng.selectNgContents("CATEGORY");
		List<ItemModel>removeList=new ArrayList<>();

		for(ItemModel item:itemList) {
			String category=item.getAttrList().getProductTypeSubcategory();

			for(String ngBrowse:ngBrowseList) {
				if(category.equals(ngBrowse)) {
					removeList.add(item);
					break;
				}
			}
		}
		itemList.removeAll(removeList);

		return itemList;
	}

	@Override
	public void setRank(SalesRankList rankList,ItemModel item) {

		StringBuilder builder=new StringBuilder();
		for(io.swagger.client.model.SalesRankType rankType:rankList) {
			int rank=rankType.getRank();
			builder.append(String.valueOf(rank)+",");
		}

		item.setRank(builder.toString());
	}

	@Override
	public ItemModel setImageList(List<String> imageList,ItemModel item) {

		StringBuilder builder=new StringBuilder();
		for(String image:imageList) {
			builder.append(image+",");
		}

		item.setImageUrlList(builder.toString());

		return item;
	}

	@Override
	public String selectCategory(ItemModel item) {
		String title=item.getTitle();

		//大分類でフィルターにかける
		List<Category>categoryList=category.selectByCategory(item.getAttrList().getProductGroup());

		//大分類でのフィルタリングに失敗した場合
		if(categoryList.size()==0) {
			List<String>list=new ArrayList<>();

			list=category.selectCategory();

			for(String obj:list) {
				if(title.contains(obj)) {
					categoryList=category.selectByQCategory(obj);
					break;
				}
			}
		}

		//中分類カテゴリの文字を含むものだけ残す
		List<Category>removeList=new ArrayList<>();

		for(Category obj:categoryList) {

			if((title.contains(obj.getSubCategory())==false)) {//部分一致しない場合
				removeList.add(obj);//リストから削除
			}
		}
		categoryList.removeAll(removeList);

		if(categoryList.size()==0) {//部分一致するものが残らなかった場合リストを復元する
			categoryList.addAll(removeList);
		}

		//小分類を絞り込む
		for(Category target:categoryList) {

			if(title.contains(target.getSubsubCategory())) {
				return target.getSubsubCategoryCode();
			}
		}

		//最後まで絞れなかった場合、リストの0番目のコードにする（大→中→小って絞ってるからできるだけ狭まってる
		String random=categoryList.get(0).getSubsubCategoryCode();

		return random;
	}

	@Override
	public List<MyMatchingProductForIdResult> removeNgWord(List<MyMatchingProductForIdResult> resultList) {
		return null;
	}

	@Override
	public List<ItemModel>removeNGWord(List<ItemModel>itemList) throws IllegalArgumentException, IllegalAccessException{

		List<String>ngWordList=ng.selectNgContents("WORD");
		List<ItemModel>removeList=new ArrayList<>();

		for(ItemModel item:itemList) {

			String title=item.getAttrList().getTitle();
			List<String> features=item.getAttrList().getFeature();

			for(String ngWord:ngWordList) {

				//check title
				if(title.contains(ngWord)) {
					removeList.add(item);
					break;
				}

				boolean stop=false;

				//check feature
				for(String feature:features) {

					if(feature.contains(ngWord)) {
						removeList.add(item);
						stop=true;
						break;
					}
				}

				if(stop==true) {
					break;
				}
			}
		}
		itemList.removeAll(removeList);
		return itemList;
	}

	@Override
	public void judgeCategory(List<ItemModel> itemList) {
		for(ItemModel itemModel:itemList) {
			int count=item.countMatchCategory(itemModel.getAttrList().getProductTypeSubcategory());

			//新しいものだった場合newの印をつける
			if(count == 0) {
				item.updateNewCategory(itemModel);
			}
		}

	}

	@Override
	public List<ItemModel> makeInsertedItemModel(List<String> asinList,List<ItemModel>itemList) {
		List<ItemModel>addItemList=item.selectMany(asinList);
		itemList.addAll(addItemList);

		return itemList;
	}

	@Override
	public void insertItem(List<ItemModel> itemList) {
		for (ItemModel addItem:itemList) {
			item.insertItem(addItem);
		}
	}

	@Override
	public ItemResponse setNewGoods(UserModel user, ItemModel exhibitItem) {

		SetNewGoodsRequest item=reqMaker.setNewGoodsRequest(exhibitItem, user);
		ItemResponse response=qooService.setNewGoods(item);

		return response;
	}

	@Override
	public int insertItem(ExhibitModel exhibit) {
		return exhibitService.insert(exhibit);
	}

}
