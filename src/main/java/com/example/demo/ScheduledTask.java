package com.example.demo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.xml.sax.SAXException;

import com.example.demo.aop.LogAspect;
import com.example.demo.api.qoo.QooService;
import com.example.demo.api.qoo.RequestMakerQoo;
import com.example.demo.api.qoo.model.SetGoodsPriceRequest;
import com.example.demo.api.spapi.SPAPIService;
import com.example.demo.domain.CaptchaModel;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;
import com.example.demo.scraping.ScrapingDao;
import com.example.demo.service.ExhibitService;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;

import io.swagger.client.ApiException;
import io.swagger.client.model.AttributeSetList;
import io.swagger.client.model.AttributeSetListType;
import io.swagger.client.model.GetCatalogItemResponse;

public class ScheduledTask {

	@Autowired
	ItemService itemService;

	@Autowired
	ExhibitService exhibitService;

	@Autowired
	QooService qooService;

	@Autowired
	UserService userService;

	@Autowired
	ScrapingDao scraping;

	@Autowired
	RequestMakerQoo requestMakerQoo;

	//集計情報更新(毎週月曜日の0時0分0秒）
	@Scheduled(cron="0 0 0 * * 1")
	public void updateAnalytics()throws Exception{

		LogAspect.logger.info("START update analytics info");

		//ユーザーリストをつくる
		List<UserModel> userList = userService.selectAll();

		/*
		 * captcha部分の取得
		 */
		CaptchaModel captcha=scraping.selectCaptcha();

		//エラー時
		if(captcha==null) {
			return;
		}

		for (UserModel user:userList) {

			//Qoo10のログイン情報を登録していない人はスキップ
			if((user.getQooId()==null)||(user.getQooPass()==null)) {
				continue;
			}
			/*
			 * 集計情報を取得
			 */
			List<List<ExhibitModel>> result = scraping.getAnalytics(user,captcha);

			//何らかのエラーが発生してresultがnullだった場合/発生場所でログを出力
			if(result==null) {
				continue;
			}

			for (List<ExhibitModel> list : result) {
				exhibitService.insertAnalytics(user.getUserId(), list);
				itemService.updateAnalytics(list);
			}
		}
		LogAspect.logger.info("FINISH  updateAnalytics");

	}

	//価格情報更新（毎日3時48分24秒）
	@Scheduled(cron="24 48 3 * * *")
	public void updatePrice() throws ParserConfigurationException, SAXException, IOException, ApiException {//DataAccessException,RestClientException

		LogAspect.logger.info("START  updatePrice");
		/*
		 * DB上の価格と情報を取得する
		 */
		List<ItemModel>itemList=itemService.selectAll();

        /*
         * 全商品の最新Amazon価格を取得
         */
		for (ItemModel item:itemList) {
			//価格取得
			GetCatalogItemResponse response = SPAPIService.callCatalogItem(item.getAsin());

			AttributeSetList attrList=response.getPayload().getAttributeSets();

			for(AttributeSetListType attr:attrList) {
				//check currency code
				if(attr.getListPrice().getCurrencyCode()!="JPY") {
					continue;
				}

				//compare price
				BigDecimal newPrice=attr.getListPrice().getAmount();
				BigDecimal oldPrice=item.getPrice();

				int compareResult=newPrice.compareTo(oldPrice);

				//If price changed,update price at DB and Qoo10
				if(compareResult!=0) {
					//update DB
					itemService.updatePrice(item);

					//Select users who exhibit this item at Qoo10
					List<ExhibitModel> exhibitUserList=exhibitService.selectByAsin(item.getAsin());

					//Send update price requests that change item price to Qoo10
					for (ExhibitModel exhibit:exhibitUserList) {

						UserModel userModel=userService.selectOneAll(exhibit.getMailAddress());
						SetGoodsPriceRequest dto =requestMakerQoo.setGoodsPriceRequest(exhibit, userModel, newPrice);
						qooService.setGoodsPrice(dto);
					}
				}
		}
		}
		LogAspect.logger.info("FINISH  update price");
	}
}
