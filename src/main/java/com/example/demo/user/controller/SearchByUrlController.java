package com.example.demo.user.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.xml.sax.SAXException;

import com.example.demo.aop.LogAspect;
import com.example.demo.component.ExhibitUtilService;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;
import com.example.demo.scraping.ScrapingDao;
import com.example.demo.user.controller.userAop.BaseController;

import io.swagger.client.ApiException;


//アマゾンの検索結果画面のURLを入力してもらい、スクレイピングで商品情報取得する
@Controller
public class SearchByUrlController extends BaseController{

	@Autowired
	ScrapingDao scraping;

	@Autowired
	ItemInsertController itemInsertController;

	@Autowired
	ExhibitUtilService exbUtilService;

	@GetMapping("/search_by_url")
	public String getSearchItemUrl(@ModelAttribute("url")String url,
			@AuthenticationPrincipal UserModel user,Model model)  {
		setStatus(user,model);
	return "user/search_by_url";
	}

	@PostMapping("/search_by_url")
	public String postSearchItemUrl(@ModelAttribute("url")String url,BindingResult br,
			@AuthenticationPrincipal UserModel user,
			Model model)throws IOException, IllegalArgumentException, IllegalAccessException,
	        ParserConfigurationException, SAXException, ApiException {

		//バリデーションはhtmlのtype="url"でおこなうからいらない

		LogAspect.logger.info("args:{}", url);

		/*
		 * URLからASINリストを取得する
		 */
		List<String>asinList=scraping.getAsinList(url);

		// ExhibitUtilクラスのメソッド使って、商品情報もってくる
		List<ItemModel>itemList=exbUtilService.getItemModelByAsin(asinList);

		return itemInsertController.getItemInsert(itemList, model,user);
	}
}
