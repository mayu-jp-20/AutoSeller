package com.example.demo.api.amazon;

/*
 * MWSからSP-APIに移行
 */
/*
 * Amazonにリクエスト送る際のリクエスト頻度を制限する
 */
//@Component
public class RequestController {

	/*@Autowired
	AmazonService amazonService;

	//お届け日数のリクエスト（回数コントロールは検証済み）
	public List<GetLowestOfferListingsForASINResponse> getLowestOfferListingsForASIN(List<String>asinList) {
		ASINListType idList=new ASINListType();
		int k=0;
		boolean stop=false;
		List<GetLowestOfferListingsForASINResponse>reponseList=new ArrayList<>();
		int listSize=asinList.size();

		do {

			if(k==0) {//最初の400個

				//20リクエスト送る
				for(int i=0 ; i <20; i++){

					if((k+20) > listSize){//kから20番目の数字がない場合
						idList.setASIN(asinList.subList(k, listSize));//kから最後のASINまでを選択(listSize番目は含まれない）
						GetLowestOfferListingsForASINResponse response=amazonService.getLowestOfferListingsForASIN(idList);
						reponseList.add(response);
						k=listSize-1;
						stop=true;

						break;

					}else {

						idList.setASIN(asinList.subList(k, k + 20));///i番目-i+10番目(i+20番目は含まない）

						GetLowestOfferListingsForASINResponse response=amazonService.invokeGetLowestOfferListingsForASIN(idList);
					    reponseList.add(response);

					    k=k+20;

						idList.unsetASIN();

					    if(k > listSize) {//k番目が空欄だったらループ終了
					    	stop=true;
						    break;
					    }

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if(stop) {
					break;
				}

			}else {//401個目以降

				if((k+20) > listSize) {
					idList.setASIN(asinList.subList(k, listSize));//kから最後のASINまでを選択
					GetLowestOfferListingsForASINResponse response=amazonService.invokeGetLowestOfferListingsForASIN(idList);
					reponseList.add(response);
					k=listSize-1;
					break;
				}

				idList.setASIN(asinList.subList(k, k+20));
				GetLowestOfferListingsForASINResponse response=amazonService.invokeGetLowestOfferListingsForASIN(idList);
				reponseList.add(response);

				k=k+20;
				idList.unsetASIN();
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (k < listSize);

		return reponseList;
	}

	//ASINからMWSで情報取得するリクエスト
	public List<GetMatchingProductForIdResponse> invokeGetMatchingProductForId(List<String> asinList) {
		IdListType idList=new IdListType();
		int k=0;
		boolean stop=false;
		List<GetMatchingProductForIdResponse>reponseList=new ArrayList<>();
		int listSize=asinList.size();

		do {

			if(k==0) {//最初の100個

				//20リクエスト送る
				for(int i=0 ; i <20; i++){

					if((k+10) > listSize){//kから10番目の数字がない場合
						idList.setId(asinList.subList(k, asinList.size()-1));//kから最後のASINまでを選択
						GetMatchingProductForIdResponse response=amazonService.invokeGetMatchingProductForId(idList);
						reponseList.add(response);
						k=k+asinList.size()-1;
						stop=true;
						break;
					}

					idList.setId(asinList.subList(k, k + 10));///i番目-i+10番目(i+10番目は含まない）

					GetMatchingProductForIdResponse response=amazonService.invokeGetMatchingProductForId(idList);
					reponseList.add(response);
					idList.unsetId();
					k=k+10;
					if(k > listSize) {//k番目が空欄だったらループ終了
						stop=true;
						break;
					}
				}

				if(stop) {
					break;
				}
			}else {
				if((k+10) > listSize) {//kから10番目の数字がない場合
					idList.setId(asinList.subList(k, asinList.size()-1));//kから最後のASINまでを選択
					GetMatchingProductForIdResponse response=amazonService.invokeGetMatchingProductForId(idList);
					reponseList.add(response);
					k=k+asinList.size()-1;
					break;
				}
				idList.setId(asinList.subList(k, k+10));
				GetMatchingProductForIdResponse response=amazonService.invokeGetMatchingProductForId(idList);
				reponseList.add(response);
				idList.unsetId();
				k=k+10;
				if(k > listSize) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (k < asinList.size());

		return reponseList;
	}

	//全商品の最新Amazon価格を取得
	public List<GetPricingResponse> invokePricing(List<String>asinList) throws ApiException{
		int k=0;
		boolean stop=false;
		List<GetPricingResponse>reponseList=new ArrayList<>();
		int listSize=asinList.size();

		do {

			if(k==0) {//最初の100個

				//20リクエスト送る
				for(int i=0 ; i <20; i++){

					if((k+10) > listSize){//kから10番目の数字がない場合
						List<String> asins=new ArrayList<>();
						asins.addAll(asinList.subList(k, asinList.size()-1));//kから最後のASINまでを選択

						GetPricingResponse pricingResponse=SPAPIService.callPricing(asins);
						reponseList.add(pricingResponse);
						k=k+asinList.size()-1;
						stop=true;
						break;
					}

					List<String> asins=new ArrayList<>();
					asins.addAll(asinList.subList(k, k + 10));//i番目-i+10番目(i+10番目は含まない）

					GetPricingResponse pricingResponse=SPAPIService.callPricing(asins);
					reponseList.add(pricingResponse);

					k=k+10;
					if(k > listSize) {//k番目が空欄だったらループ終了
						stop=true;
						break;
					}
				}

				if(stop) {
					break;
				}
			}else {
				if((k+10) > listSize) {//kから10番目の数字がない場合
					List<String> asins=new ArrayList<>();
					asins.addAll(asinList.subList(k, asinList.size()-1));//kから最後のASINまでを選択

					GetPricingResponse pricingResponse=SPAPIService.callPricing(asins);
					reponseList.add(pricingResponse);
					k=k+asinList.size()-1;
					break;
				}

				List<String>asins=new ArrayList<>();
				asins.addAll(asinList.subList(k, k+10));

				GetPricingResponse pricingResponse=SPAPIService.callPricing(asins);
				reponseList.add(pricingResponse);
				k=k+10;
				if(k > listSize) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LogAspect.logger.error("Message:"+e.getMessage());
				LogAspect.logger.error("Cause:"+e.getCause());
			}
		} while (k < asinList.size());

		return reponseList;
	}

	//カテゴリ取得　//TODO 要調整
	public List<GetProductCategoriesForASINResponse> invokeGetProductCategories(List<ItemModel>itemList) {
		return amazonService.invokeGetProductCategories(asin);
	}*/


}
